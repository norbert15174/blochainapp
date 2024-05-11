package com.blockchain.blochainapp.core.wallet.model;

import lombok.Builder;

import java.util.Date;

@Builder
public record WalletTransactions(String id, Person from, Person to, String fee, String amount, Date date) {

    public record Person(String address, String valueAfter){}

}
