package com.microusers.app.service;

import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserEntity userEntity){

        userEntity.setCredentialNoExpired(true);
        userEntity.setAccountNoExpired(true);
        userEntity.setAccountNoLocket(true);
        userEntity.setEnabled(true);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(userEntity.getPassword()));

        userRepository.save(userEntity);
    }

    public Optional<UserEntity> findUserById(Integer id){
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
