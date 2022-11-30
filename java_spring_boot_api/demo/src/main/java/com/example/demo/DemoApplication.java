package com.example.demo;

import com.example.demo.entity.Item;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.ItemService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.HashSet;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner runRole(RoleService roleService){
		return args -> {
			roleService.createRole(new Role(null,"ROLE_ADMIN"));
			roleService.createRole(new Role(null,"ROLE_USER"));
		};
	}

	@Bean
	CommandLineRunner runUser(UserService userService){
		return args -> {
			userService.newUser(new User(null,"adminUsername","adminPassword",
					"adminFirstname","adminLastname","demomerttest@gmail.com",
					123456789,new HashSet<>(),new ArrayList<>()));
			userService.newUser(new User(null,"username1","password1",
					"firstname1","lastname1","hmyenilmez24@gmail.com",
					123456789,new HashSet<>(),new ArrayList<>()));
			userService.newUser(new User(null,"username2","password2",
					"firstname2","lastname2","hmyenilmez24@gmail.com",
					123456789,new HashSet<>(),new ArrayList<>()));
			userService.newUser(new User(null,"username3","password3",
					"firstname3","lastname3","hmyenilmez24@gmail.com",
					123456789,new HashSet<>(),new ArrayList<>()));
		};
	}

	@Bean
	CommandLineRunner runRoleToUser(UserService userService){
		return args -> {
			userService.addRoleToUser(100L,100L);
			userService.addRoleToUser(101L,101L);
			userService.addRoleToUser(102L,101L);
			userService.addRoleToUser(103L,101L);
		};
	}

	@Bean
	CommandLineRunner runItem(ItemService itemService){
		return args -> {
			itemService.newItem(new Item(null, "item1",
					"description1", 1000L, "category1",
					null, null, null,null,false,false,null));

			itemService.newItem(new Item(null, "item2",
					"description2", 1000L, "category1",
					null, null, null, null, false,false,null));

			itemService.newItem(new Item(null, "item3",
					"description3", 1000L, "category2",
					null, null, null, null, false,false,null));

			itemService.newItem(new Item(null, "item4",
					"description4", 1000L, "category2",
					null, null, null, null, false,false,null));
		};
	}
}
