package com.microusers.app.controller;

import com.microusers.app.persistence.dto.UserVerificationDTO;
import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.persistence.entity.UserVerification;
import com.microusers.app.service.UserService;
import com.microusers.app.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/users/v1/verify")
public class UserVerificationController {

    @Autowired
    VerificationService verifyService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> verifyEmail(@RequestBody UserVerificationDTO userVerify) {
        Optional<UserVerification> user = verifyService.findUserVerification(userVerify.getCode());

        if (user.isEmpty()) return ResponseEntity.badRequest().body("El codigo es invalido");

        if (user.get().getUser().getEmail().equals(userVerify.getUser().getEmail())) { //valida que coincidan los correos
            verifyService.validate(userVerify.getCode(), user.get());
        }

        return ResponseEntity.accepted().body("Codigo correcto");
    }

    @PostMapping("/recuperation")
    public ResponseEntity<?> recoverEmail(@RequestParam Integer code, @RequestParam String email, @RequestParam String newPassword){
        Optional<UserVerification> userV = verifyService.findUserVerification(code);

        if (userV.isEmpty()) return ResponseEntity.badRequest().body("Codigo o correo invalido");

        if (!userV.get().getUser().getEmail().equals(email)) {
            return ResponseEntity.badRequest().body("Codigo invalido");
        }
        UserEntity user = userV.get().getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(user);
        return ResponseEntity.accepted().body("se verifico con exito");
    }

}
