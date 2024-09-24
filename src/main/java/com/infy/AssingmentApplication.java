package com.infy;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssingmentApplication implements ApplicationRunner {

	public static final Log LOGGER = LogFactory.getLog(AssingmentApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AssingmentApplication.class, args);
		
		
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		
		boolean optionArg = args.containsOption("Welcome to ApplicationRunner");
		
		List<String> nonOptionArg = args.getNonOptionArgs();
		
		LOGGER.info(optionArg);
		
		LOGGER.info(nonOptionArg);
		
	}



}
