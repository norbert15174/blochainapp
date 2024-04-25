package com.blockchain.blochainapp.core.blockchain.config;

import com.blockchain.blochainapp.core.blockchain.model.BlockchainAppKit;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class BlockChainConfig {

    private NetworkParameters networkParameters;

    public BlockChainConfig() {
        BriefLogFormatter.init();
    }

    @Bean
    public NetworkParameters networkParameters() {
        return RegTestParams.get();
    }

    public BlockchainAppKit walletAppKit(final NetworkParameters networkParameters, final String fileLocation, final String filePrefix) {
        return new BlockchainAppKit(networkParameters, new File(fileLocation), filePrefix);
    }

}
