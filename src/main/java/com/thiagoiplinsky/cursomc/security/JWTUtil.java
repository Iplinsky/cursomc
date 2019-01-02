package com.thiagoiplinsky.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// Anotação indica que a classe pode ser injetada em outras classes como componente
@Component
public class JWTUtil {
	
	@Value("${jwt.secret}") // Anotação que captura o chave secret dentro do application.properties
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	
	// Método para a construção do Token
	public String generateToken(String username) {
		return Jwts.builder()                    
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) // Características de assinatura do Token __ Conversão do (secret) para um array de bytes
				.compact();
	}
	
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject(); // Método para retornar um usuário
			Date expirateDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirateDate != null && now.before(expirateDate)) {
				return true;
			}
		}
		return false;
	}
	
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	// Função que recupera o Claim a partir de um Token
	private Claims getClaims(String token) { 
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} 
		catch (Exception e) {
			return null;
		}
	}
}
