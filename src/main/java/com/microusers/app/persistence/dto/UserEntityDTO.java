package com.microusers.app.persistence.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserEntityDTO {

    private Integer idUsuario;

    private String email;

}
