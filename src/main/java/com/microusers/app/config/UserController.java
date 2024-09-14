package com.microusers.app.config;

import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@PreAuthorize("permitAll()")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void saveUser(UserEntity userEntity){
        userService.saveUser(userEntity);
    }
}
