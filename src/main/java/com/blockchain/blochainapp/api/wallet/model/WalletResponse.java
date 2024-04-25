package com.blockchain.blochainapp.api.wallet.model;

import lombok.Builder;

@Builder
public record WalletResponse(Double bitcoins, String address) {

}
