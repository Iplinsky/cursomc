package com.thiagoiplinsky.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.thiagoiplinsky.cursomc.domain.Categoria;
import com.thiagoiplinsky.cursomc.dto.CategoriaDTO;
import com.thiagoiplinsky.cursomc.repositories.CategoriaRepository;
import com.thiagoiplinsky.cursomc.services.exceptions.DataIntegrityException;
import com.thiagoiplinsky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
//	Função utilizada para retornar uma categoria pelo parâmetro (id)
	
	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
		}
		return obj;
	}
	
//	Função utilizada para inserir uma categoria
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
//	Função utilizada para atualizar uma categoria
	
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
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
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
		}
		
	}
	
//	Função utilizada para retornar todas as categorias 
	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
//	Função para retornar uma paginação
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}
