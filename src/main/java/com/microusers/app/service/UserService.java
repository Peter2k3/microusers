package com.microusers.app.service;

import com.microusers.app.persistence.entity.RoleEntity;
import com.microusers.app.persistence.entity.RoleEnum;
import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired(required=true)
    private UserRepository userRepository;

    public void saveUser(UserEntity userEntity){

        userEntity.setCredentialNoExpired(true);
        userEntity.setAccountNoExpired(true);
        userEntity.setAccountNoLocket(true);
        userEntity.setEnabled(true);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(userEntity.getPassword()));
        RoleEntity rol = new RoleEntity();
        rol.setRoleEnum(RoleEnum.USER);
        userRepository.save(userEntity);
    }

    public Optional<UserEntity> findUserById(Integer id){
        return userRepository.findById(id);
    }

    public List<UserEntity> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


}
