package com.microusers.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@PreAuthorize("denyAll()")
public class LoginController {

    @GetMapping("/hola")
    @PreAuthorize("permitAll()")
    public String hola(){
        return "hola";
    }

    @GetMapping("/hola-secured")
    @PreAuthorize("hasAuthority('READ')")
    public String holaSecured(){
        return "hola";
    }

}
