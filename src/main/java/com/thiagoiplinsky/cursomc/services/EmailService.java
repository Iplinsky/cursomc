package com.thiagoiplinsky.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.thiagoiplinsky.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}
