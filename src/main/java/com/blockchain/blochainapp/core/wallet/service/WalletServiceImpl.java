package com.blockchain.blochainapp.core.wallet.service;


import com.blockchain.blochainapp.api.wallet.model.WalletResponse;
import com.blockchain.blochainapp.core.blockchain.config.BlockChainConfig;
import com.blockchain.blochainapp.core.blockchain.model.BlockchainAppKit;
import com.blockchain.blochainapp.core.wallet.model.WalletTransactions;
import com.blockchain.blochainapp.data.functionality.user.domain.User;
import com.blockchain.blochainapp.data.functionality.user.service.UserCudService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.AllArgsConstructor;
import org.bitcoinj.core.*;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
@AllArgsConstructor
@Component
public class WalletServiceImpl implements WalletService {

    private NetworkParameters networkParameters;
    private final BlockChainConfig blockChainConfig;
    private final UserCudService userCudService;
    private final Map<String, BlockchainAppKit> walletAppKits = new HashMap<>();

    @Override
    public void initWallet(User user) {
        var walletAppKit = getBlockchainAppKit(user);

        walletAppKit.wallet().addCoinsReceivedEventListener(
                (wallet, tx, prevBalance, newBalance) -> {
                    Coin value = tx.getValueSentToMe(wallet);
                    System.out.println("Received tx for " + value.toFriendlyString());
                    Futures.addCallback(tx.getConfidence().getDepthFuture(1),
                            new FutureCallback<TransactionConfidence>() {
                                @Override
                                public void onSuccess(TransactionConfidence result) {
                                    System.out.println("Received tx " +
                                            value.toFriendlyString() + " is confirmed. ");
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                }
                            }, MoreExecutors.directExecutor());
                });

        Address walletAddress = LegacyAddress.fromKey(networkParameters, walletAppKit.wallet().currentReceiveKey());
        user.setWalletAddress(walletAddress.toString());
        user.setPublicKey(walletAppKit.wallet().currentReceiveKey().getPublicKeyAsHex());
        walletAppKits.put(user.getUsername(), walletAppKit);
    }

    @Override
    public void send(String value, String to, User user) {
        try {
            var walletAppKit = getBlockchainAppKit(user);
            Address toAddress = LegacyAddress.fromBase58(networkParameters, to);
            SendRequest sendRequest = SendRequest.to(toAddress, Coin.parseCoin(value));
            sendRequest.feePerKb = Coin.parseCoin("0.0005");
            Wallet.SendResult sendResult = walletAppKit.wallet().sendCoins(walletAppKit.peerGroup(), sendRequest);
            sendResult.broadcastComplete.addListener(() ->
                            System.out.println("Sent coins onwards! Transaction hash is " + sendResult.tx.getTxId()),
                    MoreExecutors.directExecutor());
        } catch (InsufficientMoneyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Coin getBalance(User user) {
        var walletAppKit = getBlockchainAppKit(user);
        return walletAppKit.wallet()
                .getBalance();
    }

    @Override
    public List<WalletTransactions> getTransactions(User user) {
        var walletAppKit = getBlockchainAppKit(user);
        var transactions = walletAppKit.wallet().getTransactions(false);

        var walletTransactions = new ArrayList<WalletTransactions>();
        for (var transaction : transactions) {
            var transactionId = transaction.getTxId();
            var fee = Objects.nonNull(transaction.getFee()) ? transaction.getFee().toFriendlyString() : "0 BTC";
            var value = transaction.getValue(walletAppKit.wallet()).toFriendlyString();

            var people = new ArrayList<WalletTransactions.Person>();

            for (TransactionOutput output : transaction.getOutputs()) {
                var address = Objects.requireNonNull(output.getAddressFromP2PKHScript(walletAppKit.params())).toString();
                people.add(new WalletTransactions.Person(address, output.getValue().toFriendlyString()));
            }

            var walletTransaction = WalletTransactions.builder()
                    .id(transactionId.toString())
                    .from(people.get(0))
                    .to(people.get(1))
                    .fee(fee)
                    .date(transaction.getUpdateTime())
                    .amount(value)
                    .build();
            walletTransactions.add(walletTransaction);
        }


        return walletTransactions.stream()
                .sorted(Comparator.comparing(WalletTransactions::date).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public WalletResponse info(User user) {
        var bitcoins = getBalance(user).toFriendlyString();

        return WalletResponse.builder()
                .address(user.getWalletAddress())
                .bitcoins(bitcoins)
                .build();
    }

    private BlockchainAppKit getBlockchainAppKit(User user) {
        if (walletAppKits.containsKey(user.getUsername())) {
            return walletAppKits.get(user.getUsername());
        }

        var walletAppKit = blockChainConfig.walletAppKit(networkParameters, "D:\\blocks", user.getUsername());
        walletAppKit.startAsync();
        walletAppKit.awaitRunning();
        walletAppKits.put(user.getUsername(), walletAppKit);
        return walletAppKit;
    }

}
