package com.blockchain.blochainapp.core.blockchain.model;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;

import java.io.File;

public class BlockchainAppKit extends WalletAppKit {
    public BlockchainAppKit(NetworkParameters params, File directory, String filePrefix) {
        super(params, directory, filePrefix);
    }

    @Override
    protected void onSetupCompleted() {
        if (wallet().getKeyChainGroupSize() < 1) {
            wallet().importKey(new ECKey());
        }
    }

}
