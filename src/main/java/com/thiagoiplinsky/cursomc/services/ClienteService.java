package com.thiagoiplinsky.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.thiagoiplinsky.cursomc.domain.Cliente;
import com.thiagoiplinsky.cursomc.dto.ClienteDTO;
import com.thiagoiplinsky.cursomc.resource.repositories.ClienteRepository;
import com.thiagoiplinsky.cursomc.services.exceptions.DataIntegrityException;
import com.thiagoiplinsky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
//	Função utilizada para atualizar um Cliente
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		//método auxiliar para salvar os dados do (newObj) com base no (obj)
		updateData(newObj, obj);		
		return repo.save(newObj);
	}
	
//	Função utilizada para deletar um item do banco de dados através do parâmetro (id)
	
	public void delete(Integer id) {
		find(id);
		try {				// Tentativa de deleção
			repo.delete(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas.");
		}
		
	}
	
//	Função utilizada para retornar todos os Clientes 
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
//	Função para retornar uma paginação
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDto(ClienteDTO objDto) {
		
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail()); 
	}
}
