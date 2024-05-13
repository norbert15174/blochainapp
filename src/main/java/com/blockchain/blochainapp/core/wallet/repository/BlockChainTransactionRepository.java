package com.blockchain.blochainapp.core.wallet.repository;

import com.blockchain.blochainapp.core.wallet.domain.BlockChainTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockChainTransactionRepository extends JpaRepository<BlockChainTransaction, Long> {

    @Query("select bct from BlockChainTransaction bct where bct.transactionAddress = :transactionAddress")
    Optional<BlockChainTransaction> findByTransactionAddress(@Param("transactionAddress") String transactionAddress);

}
