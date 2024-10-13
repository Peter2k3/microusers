package com.microusers.app.persistence.mapper;

import com.microusers.app.persistence.dto.UserDetailDto;
import com.microusers.app.persistence.dto.UserEntityDTO;
import com.microusers.app.persistence.entity.UserEntity;

public class UserMapper {

    public static UserEntity toUserEntity(UserEntityDTO userEntityDTO) {

        return UserEntity.builder()
                .email(userEntityDTO.getEmail())
                .idUsuario(userEntityDTO.getIdUsuario())

                .build();
    }

    public static UserEntityDTO toUserEntityDTo(UserEntity userEntity) {
        return UserEntityDTO.builder()
                .email(userEntity.getEmail())
                .idUsuario(userEntity.getIdUsuario())
                .build();
    }

    public static UserDetailDto toUserDetailDTO(UserEntity user) {

        return UserDetailDto.builder()
                .email(user.getEmail())
                .accountNoExpired(user.isAccountNoExpired())
                .idUsuario(user.getIdUsuario())
                .accountNoLocket(user.isAccountNoLocket())
                .credentialNoExpired(user.isCredentialNoExpired())
                .password(user.getPassword())
                .idUsuario(user.getIdUsuario())
                .rol(user.getRoles().iterator().next().getRoleEnum().name())
                .build();
    }

}
