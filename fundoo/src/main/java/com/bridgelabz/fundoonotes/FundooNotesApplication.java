package com.bridgelabz.fundoonotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

//@SpringBootApplication(scanBasePackages = {"com.bridgelabz.fundoonotes"})
//@EnableJpaRepositories
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@SpringBootApplication
@EnableCaching
public class FundooNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooNotesApplication.class, args);
	}


}
