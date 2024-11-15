package com.microusers.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microusers.app.persistence.dto.UserEntityDTO;
import com.microusers.app.persistence.dto.UserVerificationDTO;
import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.persistence.entity.UserVerification;
import com.microusers.app.persistence.repository.VerificationEntityRepository;

import java.util.Optional;
import java.util.Random;
import java.time.LocalDateTime;


@Service
public class VerificationService {

    @Autowired
    VerificationEntityRepository vEntityRepository;

    public static int codeGenerator(){
        Random random = new Random();
        int code = 10000 + random.nextInt(90000); //Genera numeros de 5 cifras
        return code;
    }

    public UserVerificationDTO createVerification(UserEntity user){
        LocalDateTime actuallyDateTime = LocalDateTime.now();        
        UserVerification userVerification = new UserVerification(null, codeGenerator(), actuallyDateTime
                                                                ,1, user);
        vEntityRepository.save(userVerification);

        
        UserVerificationDTO userVerificationDTO = new UserVerificationDTO();
        userVerificationDTO.setCode(userVerification.getCode());
        userVerificationDTO.setIdUserVerification(userVerification.getIdUserVerification());
        UserEntityDTO userDTO = UserEntityDTO.builder().idUsuario(user.getIdUsuario()).build();
        userDTO.setEmail(user.getEmail());
        userVerificationDTO.setUser(userDTO);
        return userVerificationDTO;
    }

    public boolean validate(int code, UserVerification userVerification){
        if (userVerification.getCode() != code){
            return false;
        }else{
            vEntityRepository.delete(userVerification);
            return true;
        }
    }

    public Optional<UserVerification> findUserVerification(Integer code){
        return vEntityRepository.findByCode(code);
    }

}
