package com.blockchain.blochainapp.data.functionality.user.service;

import com.blockchain.blochainapp.data.functionality.common.service.CudServiceImpl;
import com.blockchain.blochainapp.data.functionality.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
class UserCudServiceImpl extends CudServiceImpl<UserRepository, User, Long> implements UserCudService {
    public UserCudServiceImpl(UserRepository repository) {
        super(repository);
    }

}
