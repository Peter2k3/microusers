package com.microusers.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users/v1/verify")
public class UserVerificationController {

    @PostMapping
    public String postMethodName(@RequestBody String entity) {
        
        
        return entity;
    }
    

}
