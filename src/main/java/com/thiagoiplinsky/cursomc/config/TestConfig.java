package com.thiagoiplinsky.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.thiagoiplinsky.cursomc.services.DBService;

@Configuration
@Profile("test") // Configuração específica do profile de teste
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateDatabase();
		return true;
	}
}