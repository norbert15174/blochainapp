package com.blockchain.blochainapp.data.functionality.common.domain;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface CheckUnique {

    default boolean isNameExist(String name, Optional<Long> idOpt){
        throw new UnsupportedOperationException("isNameExist method not defined");
    }

    default boolean isEmailExist(String name, Optional<Long> idOpt){
        throw new UnsupportedOperationException("isEmailExist method not defined");
    }

}
