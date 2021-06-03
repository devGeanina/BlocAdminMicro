package com.blocadminmicro.operationservice;

import java.util.Date;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blocadminmicro.operationservice.entity.Expense;
import com.blocadminmicro.operationservice.entity.Household;
import com.blocadminmicro.operationservice.entity.Request;
import com.blocadminmicro.operationservice.repository.ExpenseRepository;
import com.blocadminmicro.operationservice.repository.HouseholdRepository;
import com.blocadminmicro.operationservice.repository.RequestRepository;

@SpringBootApplication
public class OperationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperationServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ExpenseRepository expenseRepository, RequestRepository reqRepository,
			HouseholdRepository houseRepository) {
		return args -> {
			expenseRepository.save(new Expense(1, (short) 1, 22.0, 0.0, true, "", new Date()));
			expenseRepository.save(new Expense(2, (short) 2, 50.0, 30.0, false, "", new Date()));
			expenseRepository.save(new Expense(3, (short) 3, 30.0, 0.0, true, "", new Date()));
			expenseRepository.save(new Expense(4, (short) 4, 100.5, 30.0, false, "", new Date()));
			expenseRepository.save(new Expense(5, (short) 5, 200.0, 90.0, false, "", new Date()));

			reqRepository.save(new Request((short) 1, "Broken door", "Broken door knob.", true, new Date()));
			reqRepository.save(new Request((short) 2, "Owner change", "", false, new Date()));
			reqRepository.save(new Request((short) 3, "Insurance docs prep", "", true, new Date()));
			reqRepository.save(new Request((short) 1, "Mailbox malfunction", "Doesn't close.", false, new Date()));
			reqRepository.save(new Request((short) 2, "May receipt", "Make a copy of the may receipt.", true, new Date()));

			houseRepository.save(new Household(1, "Geanina Chiricuta", 1, "", 3, 2, 3));
			houseRepository.save(new Household(2, "Paul Rudd", 1, "", 1, 1, 1));
			houseRepository.save(new Household(1, "Stephen Talasu", 2, "", 2, 2, 2));
			houseRepository.save(new Household(2, "John Bush", 2, "", 1, 2, 1));
			houseRepository.save(new Household(1, "Alan Michel", 3, "", 2, 2, 1));
		};
	}
}
