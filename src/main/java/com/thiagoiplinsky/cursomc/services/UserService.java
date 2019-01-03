package com.thiagoiplinsky.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.thiagoiplinsky.cursomc.security.UserSS;

public class UserService {
	
//	Método para retornar um usuário logado no sistema
	
	public static UserSS authenticated() {				
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		}
		catch (Exception e) {
			return null;
		}
	}
}
