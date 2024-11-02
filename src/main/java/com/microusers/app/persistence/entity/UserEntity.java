package com.microusers.app.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "usuarios")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean isEnabled;

    private boolean accountNoExpired;

    private boolean accountNoLocket;

    private boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
    @Builder.Default
    private Set<RoleEntity> roles = new HashSet<>();

    @ManyToMany(mappedBy = "usuarios")
    @Builder.Default
    private Set<ProjectEntity> projecs = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private UserVerification userVerification;

}
