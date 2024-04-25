package com.blockchain.blochainapp.api.wallet.controller;

import com.blockchain.blochainapp.api.wallet.model.TransactionRequest;
import com.blockchain.blochainapp.api.wallet.model.WalletResponse;
import com.blockchain.blochainapp.core.wallet.model.WalletEvent;
import com.blockchain.blochainapp.core.wallet.service.WalletService;
import com.blockchain.blochainapp.security.helper.UserHelper;
import lombok.AllArgsConstructor;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final WalletService walletService;

    @PostMapping("/wallet/init")
    public ResponseEntity<?> initWallet() {
        var user = UserHelper.getAuthenticatedUser();
        applicationEventPublisher.publishEvent(new WalletEvent(user.getUsername()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/wallet/send")
    public ResponseEntity<?> send(@RequestBody TransactionRequest transactionRequest) {
        var user = UserHelper.getAuthenticatedUser();
        walletService.send(transactionRequest.value(), transactionRequest.to(), user.getUser());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/wallet/balance")
    public ResponseEntity<Coin> getBalance() {
        var user = UserHelper.getAuthenticatedUser();
        return ResponseEntity.ok(walletService.getBalance(user.getUser()));
    }

    @GetMapping("/wallet/info")
    public ResponseEntity<WalletResponse> info() {
        var user = UserHelper.getAuthenticatedUser();
        return ResponseEntity.ok(walletService.info(user.getUser()));
    }

    @GetMapping("/wallet/transactions")
    public ResponseEntity<Set<Transaction>> getTransactions() {
        var user = UserHelper.getAuthenticatedUser();
        return ResponseEntity.ok(walletService.getTransactions(user.getUser()));
    }

}
