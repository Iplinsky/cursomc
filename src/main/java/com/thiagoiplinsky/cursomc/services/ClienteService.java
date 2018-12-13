package com.thiagoiplinsky.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagoiplinsky.cursomc.domain.Categoria;
import com.thiagoiplinsky.cursomc.domain.Cliente;
import com.thiagoiplinsky.cursomc.resource.repositories.ClienteRepository;
import com.thiagoiplinsky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Cliente obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
		}
		return obj;
	}
}
