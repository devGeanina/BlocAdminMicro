package com.blocadminmicro.userservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.blocadminmicro.userservice.entity.User;
import com.blocadminmicro.userservice.repository.UserRepository;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class })
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository repository) {
		return args -> {
			repository.save(new User("geanina_c", "passC", "Geanina", "Chiricuta", (short) 1, 1, "", 1));
			repository.save(new User("paul_r", "passP", "Paul", "Rudd", (short) 2, 2, "", 1));
			repository.save(new User("stephen_t", "passS", "Stephen", "Talasu", (short) 3, 1, "", 2));
			repository.save(new User("john_b", "passB", "John", "Bush", (short) 4, 2, "", 2));
			repository.save(new User("alan_m", "passM", "Alan", "Michel", (short) 2, 1, "", 3));
		};
	}

}
