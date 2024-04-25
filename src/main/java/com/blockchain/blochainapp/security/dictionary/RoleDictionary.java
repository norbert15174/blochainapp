package com.blockchain.blochainapp.security.dictionary;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleDictionary {

    public final static String ROLE_DEFAULT = "hasAuthority('ROLE_DEFAULT')";
    public final static String ROLE_ADMIN = "hasAuthority('ROLE_ADMIN')";


}
