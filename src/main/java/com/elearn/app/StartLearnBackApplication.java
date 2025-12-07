package com.elearn.app;

import com.elearn.app.config.AppConstants;
import com.elearn.app.entities.Role;
import com.elearn.app.entities.User;
import com.elearn.app.repositories.RoleRepo;
import com.elearn.app.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class StartLearnBackApplication implements CommandLineRunner {



	public static void main(String[] args) {
		SpringApplication.run(StartLearnBackApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepo userRepo;

	@Autowired
    private RoleRepo roleRepo;


	@Override
	public void run(String... args) throws Exception {

		Role role1 = new Role();
		role1.setRoleName(AppConstants.ROLE_ADMIN);
		role1.setRole_Id(UUID.randomUUID().toString());

		Role role2 = new Role();
		role2.setRoleName(AppConstants.ROLE_GUEST);
		role2.setRole_Id(UUID.randomUUID().toString());


//		Creating Admin role
		roleRepo.findByRoleName(AppConstants.ROLE_ADMIN).ifPresentOrElse(
				role -> {
					role1.setRole_Id(role.getRole_Id());
					System.out.println(role.getRoleName() + " is already in the database");
				},
				() -> {
					roleRepo.save(role1);
				}
		);
//creating guest role
		roleRepo.findByRoleName(AppConstants.ROLE_GUEST).ifPresentOrElse(
				role -> {
					role2.setRole_Id(role.getRole_Id());
					System.out.println(role.getRoleName() + " is already in the database");
				},
				() -> {
					roleRepo.save(role2);
				}
		);
		User user = new User();
		user.setName("Priyesh");
		user.setEmail("abc1@gmail.com");
		user.setPassword(passwordEncoder.encode("abc"));
		user.setCreated_at(new Date());
		user.setEmailVerified(true);
		user.setUserId(UUID.randomUUID().toString());
		user.setAbout("This is new user");

		user.assignRole(role1);
		user.assignRole(role2);

		userRepo.findByEmail("abc1@gmail.com").ifPresentOrElse(user11 -> {
					System.out.println(user.getEmail());
				},
				() -> {
					userRepo.save(user);
					System.out.println("user created");
				});

		User user1 = new User();
		user1.setName("Nitu");
		user1.setEmail("nitu@gmail.com");
		user1.setPassword(passwordEncoder.encode("abc"));
		user1.setCreated_at(new Date());
		user1.setEmailVerified(true);
		user1.setUserId(UUID.randomUUID().toString());
		user1.setAbout("This is new user");

		user1.assignRole(role1);

		userRepo.findByEmail("nitu@gmail.com").ifPresentOrElse(user2 -> {
					System.out.println(user1.getEmail());
				},
				() -> {
					userRepo.save(user1);
					System.out.println("user1 created");
				});
/*
		User user2 = new User();
		user2.setName("Ayush");
		user2.setEmail("Ayush@gmail.com");
		user2.setPassword(passwordEncoder.encode("abc"));
		user2.setCreated_at(new Date());
		user2.setEmailVerified(true);
		user2.setUserId(UUID.randomUUID().toString());
		user2.setAbout("This is Ayush");

		user2.assignRole(role1);

		userRepo.findByEmail("Ayush@gmail.com").ifPresentOrElse(user3 -> {
					System.out.println(user2.getEmail());
				},
				() -> {
					userRepo.save(user2);
					System.out.println("user2 created");
				});

 */

	}
}
