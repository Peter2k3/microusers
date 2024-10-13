package com.microusers.app.persistence.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailDto {

    private Integer idUsuario;

    private String email;

    private String password;

    private boolean isEnabled;

    private boolean accountNoExpired;

    private boolean accountNoLocket;

    private boolean credentialNoExpired;

    private String rol;

}
