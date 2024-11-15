package com.microusers.app.controller;

import com.microusers.app.http.EmailService;
import com.microusers.app.persistence.dto.UserDetailDto;
import com.microusers.app.persistence.dto.UserLoginRequest;
import com.microusers.app.persistence.dto.UserVerificationDTO;
import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.persistence.mapper.UserMapper;
import com.microusers.app.service.UserService;
import com.microusers.app.service.VerificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    VerificationService verificationService;

    @Autowired
    EmailService emailService;



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
            UserVerificationDTO uVerificationDTO = verificationService.createVerification(userEntity);
            System.err.println(uVerificationDTO);;
            emailService.sendVerificationEmail(uVerificationDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario guardado con exito");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el usuario "+ e.getMessage());
        }
    }

    @PostMapping("/verify-email")//Este endpoint recibe un int de 4 caracteres
    public String verifiedUser(@RequestBody UserVerificationDTO userVerificationDTO){
        return emailService.sendVerificationEmail(userVerificationDTO);
    }

    @GetMapping //Este endpoint solo lo podrá utilizar el admin
    public ResponseEntity<?> getUsers(){
        List<UserEntity> usuariosList = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuariosList);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDetailDto> login(@RequestBody UserLoginRequest userLoginRequest) {
        Optional<UserEntity> userFind = userService.findUserByEmail(userLoginRequest.getEmail());

        if (userFind.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (passwordEncoder.matches(userFind.get().getPassword(), passwordEncoder.encode(userLoginRequest.getPassword()) )) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(UserMapper.toUserDetailDTO(userFind.get()), HttpStatus.OK);
    }
    
    @PostMapping("/recover")
    public ResponseEntity<?> recoverPassword(@RequestParam String email){
        Optional<UserEntity> user = userService.findUserByEmail(email);

        if (user.isEmpty()) return ResponseEntity.badRequest()
                                                 .body("Email invalido, ese correo no existe en nuestra base de datos");

        UserVerificationDTO dto = verificationService.createVerification(user.get());

        emailService.sendVerificationEmail(dto);
        return ResponseEntity.accepted().build();
    }
}
