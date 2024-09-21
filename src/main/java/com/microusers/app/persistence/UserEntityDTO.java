package com.microusers.app.persistence;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microusers.app.persistence.entity.RoleEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


public class UserEntityDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(unique = true)
    private String email;

    private boolean isEnabled;

    private boolean accountNoExpired;

    private boolean accountNoLocket;

    private boolean credentialNoExpired;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private final Set<RoleEntity> roles = new HashSet<>();

}
