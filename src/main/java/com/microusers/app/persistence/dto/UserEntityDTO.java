package com.microusers.app.persistence.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microusers.app.persistence.entity.RoleEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
public class UserEntityDTO {

    private Integer idUsuario;

    private String email;

}
