package com.microusers.app.persistence.repository;


import com.microusers.app.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer>{

    // Buscar un proyecto por su Id
    Optional<UserEntity> findByIdUsuario(Integer id);

    // Buscar todos los proyectos por nombre
    Optional<UserEntity> findByEmail(String email);

    // Eliminar un usuario
    void deleteByIdUsuario(Integer id);


}
