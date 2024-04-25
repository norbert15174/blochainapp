package com.blockchain.blochainapp.core.wallet.listener;

import com.blockchain.blochainapp.core.wallet.model.WalletEvent;
import com.blockchain.blochainapp.core.wallet.service.WalletService;
import com.blockchain.blochainapp.data.functionality.user.service.UserQueryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Transactional
@AllArgsConstructor
@Component
public class WalletEventListener {

    private final WalletService walletService;
    private final UserQueryService userQueryService;

    @Async
    @EventListener
    public void handleImageScanCreate(WalletEvent event) {
        @SuppressWarnings("OptionalGetWithoutIsPresent") var user = userQueryService.getOptByUsername(event.username()).get();
        walletService.initWallet(user);
    }

}
