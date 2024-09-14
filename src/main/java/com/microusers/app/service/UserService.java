package com.microusers.app.service;

import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserEntity userEntity){
        userRepository.save(userEntity);
    }

    public Optional<UserEntity> findUserById(Integer id){
        return userRepository.findById(id);
    }
}
