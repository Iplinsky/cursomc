package com.thiagoiplinsky.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.thiagoiplinsky.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido); // Define o instante do pedido
		cal.add(Calendar.DAY_OF_MONTH, 7); // Delimita um prazo de 7 dias
		pagto.setDataVencimento(cal.getTime());	// Determina o vencimento com 7 dias após o instante do pedido
	}
}
