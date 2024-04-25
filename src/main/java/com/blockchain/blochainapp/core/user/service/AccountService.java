package com.blockchain.blochainapp.core.user.service;


import com.blockchain.blochainapp.core.user.dto.RegisterDTO;

public interface AccountService {
    RegisterDTO register(RegisterDTO dto);
}
