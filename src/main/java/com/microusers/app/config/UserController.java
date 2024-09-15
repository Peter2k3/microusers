package com.microusers.app.config;

import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@PreAuthorize("permitAll()")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserEntity userEntity){
        try {
            Optional<UserEntity> existingUser = userService.findUserByEmail(userEntity.getEmail());
            if (existingUser.isPresent()){
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Ya existe usuario con el correo "+ userEntity.getEmail());
            }
            userService.saveUser(userEntity);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario guardado con exito");


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el usuario "+ e.getMessage());
        }

    }
}