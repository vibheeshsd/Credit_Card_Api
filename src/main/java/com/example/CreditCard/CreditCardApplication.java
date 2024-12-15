package com.example.CreditCard;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CreditCardApplication {

	static Logger log = Logger.getLogger(CreditCardApplication.class);
	public static void main(String[] args) {

		PropertyConfigurator.configure("C:\\Users\\Admin\\Desktop\\Synergech\\Spring\\CreditCard\\src\\Properties\\Log4j.properties");
		log.info("Application Started");
		ApplicationContext context = SpringApplication.run(CreditCardApplication.class, args);

	}
}
