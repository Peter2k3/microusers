package com.microusers.app.controller;

import com.microusers.app.persistence.dto.UserVerificationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@RestController
@RequestMapping("/users/v1/verify")
public class UserVerificationController {

    @PostMapping
    public ResponseEntity<?> verifyEmail(@RequestBody UserVerificationDTO userVerify) {




        return ResponseEntity.accepted().body("Codigo correcto");
    }

}
