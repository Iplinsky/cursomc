package com.thiagoiplinsky.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// Anotação indica que a classe pode ser injetada em outras classes como componente
@Component
public class JWTUtil {
	
	@Value("${jwt.secret}") // Anotação que captura o chave secret dentro do application.properties
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	
//	Método para a construção do Token
	public String generateToken(String username) {
		return Jwts.builder()                     
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) // Características de assinatura do Token __ Conversão do (secret) para um array de bytes
				.compact();
	}
}
