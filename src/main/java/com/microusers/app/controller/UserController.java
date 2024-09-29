package com.microusers.app.controller;

import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
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

    @PostMapping("/verify-email")//Este endpoint recibe un string de 4 caracteres
    public boolean verifiedUser(@RequestParam String codeVerification){
        return true;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(){
        List<UserEntity> usuariosList = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuariosList);
    }
}
