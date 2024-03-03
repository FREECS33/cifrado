package com.example.servicesbcrypt.service;

import com.example.servicesbcrypt.model.BeanUser;
import com.example.servicesbcrypt.model.RepositoryUser;
import com.example.servicesbcrypt.utils.Encrypt;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public BeanUser insertUser(BeanUser user) throws Exception {
//        String hashPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(Encrypt.encrypt(user.getPassword()));
        return repositoryUser.saveAndFlush(user);
    }

    public BeanUser getUser(String email){
        return repositoryUser.findBeanUserByEmail(email);
    }

    public boolean userExist(String email) {
        return repositoryUser.existsBeanUserByEmail(email);
    }
}
