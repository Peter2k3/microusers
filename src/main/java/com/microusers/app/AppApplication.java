package com.microusers.app;

import com.microusers.app.persistence.entity.ProjectEntity;
import com.microusers.app.persistence.entity.RoleEntity;
import com.microusers.app.persistence.entity.RoleEnum;
import com.microusers.app.persistence.entity.UserEntity;
import com.microusers.app.service.ProjectService;
import com.microusers.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;
@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Autowired(required = true)
	private UserService userService;
	

	@Bean
	CommandLineRunner commandLineRunner(){
        return args -> {
			
            new UserEntity();
            UserEntity user = UserEntity.builder()
					.email("email@email.com")
					.password("contraseña")
					.roles(Set.of(RoleEntity.builder().roleEnum(RoleEnum.USER).build()))
					.build();

			new ProjectEntity();
			ProjectEntity project = ProjectEntity.builder()
					.usuarios(Set.of(user))
					.customEmail("custom@email.com")
					.nombre("Projecto pepito")
					.build();
					
			userService.saveUser(user);
        };
	}

}
