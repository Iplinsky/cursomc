package com.thiagoiplinsky.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

// Erro personalizado para a validação

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> erros = new ArrayList<>();   // Erro de validação com uma lista de mensagem
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);		
	}

	public List<FieldMessage> getErros() {
		return erros;
	}

	public void addError(String fieldName, String message) {
		erros.add(new FieldMessage(fieldName, message));
	}
}
