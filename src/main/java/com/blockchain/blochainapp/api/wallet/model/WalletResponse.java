package com.blockchain.blochainapp.api.wallet.model;

import lombok.Builder;

@Builder
public record WalletResponse(String bitcoins, String address) {

}
