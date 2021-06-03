package com.blocadminmicro.budgetservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.blocadminmicro.budgetservice.entity.Budget;
import com.blocadminmicro.budgetservice.repository.BudgetRepository;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class BudgetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetServiceApplication.class, args);
	}

	  @Bean
	    CommandLineRunner initDatabase(BudgetRepository repository) {
	        return args -> {
	            repository.save(new Budget((short)1, 200.0, 100.0, ""));
	            repository.save(new Budget((short)2, 55.0, 25.0, "Salaries"));
	            repository.save(new Budget((short)3, 77.8, 72.3, ""));
	            repository.save(new Budget((short)4, 10000, 4000, "Total capital"));
	            repository.save(new Budget((short)5, 2000, 1500, "Contributions"));
	        };
	    }

}
