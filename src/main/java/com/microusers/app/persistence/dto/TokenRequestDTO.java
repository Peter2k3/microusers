package com.microusers.app.persistence.dto;

import com.microusers.app.persistence.entity.ProjectEntity;
import com.microusers.app.persistence.entity.TokenEntity;
import com.microusers.app.persistence.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenRequestDTO {

    private UserEntity user;
    private ProjectEntity projectEntity;
    private TokenEntity tokenEntity;
}
