package com.blockchain.blochainapp.core.wallet.service;

import com.blockchain.blochainapp.api.wallet.model.WalletResponse;
import com.blockchain.blochainapp.core.wallet.model.WalletTransactions;
import com.blockchain.blochainapp.data.functionality.user.domain.User;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;

import java.util.List;
import java.util.Set;

public interface WalletService {
    void initWallet(User user);

    void send(String value, String to, User user);

    Coin getBalance(User user);

    List<WalletTransactions> getTransactions(User user);

    WalletResponse info(User user);
}
