package com.microusers.app.persistence.mapper;

import com.microusers.app.persistence.dto.UserEntityDTO;
import com.microusers.app.persistence.entity.UserEntity;

public class UserMapper {

    public static UserEntity toUserEntity(UserEntityDTO userEntityDTO){

        return UserEntity.builder()
                .email(userEntityDTO.getEmail())
                .idUsuario(userEntityDTO.getIdUsuario())

                .build();
    }

    public static UserEntityDTO toUserEntityDTo(UserEntity userEntity){
        return UserEntityDTO.builder()
                .email(userEntity.getEmail())
                .idUsuario(userEntity.getIdUsuario())
                .build();
    }

}
