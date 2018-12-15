package com.thiagoiplinsky.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagoiplinsky.cursomc.domain.Pedido;
import com.thiagoiplinsky.cursomc.resource.repositories.PedidoRepository;
import com.thiagoiplinsky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
}
