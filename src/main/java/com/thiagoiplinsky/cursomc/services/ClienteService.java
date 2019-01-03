package com.thiagoiplinsky.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiagoiplinsky.cursomc.domain.Cidade;
import com.thiagoiplinsky.cursomc.domain.Cliente;
import com.thiagoiplinsky.cursomc.domain.Endereco;
import com.thiagoiplinsky.cursomc.domain.enums.Perfil;
import com.thiagoiplinsky.cursomc.domain.enums.TipoCliente;
import com.thiagoiplinsky.cursomc.dto.ClienteDTO;
import com.thiagoiplinsky.cursomc.dto.ClienteNewDTO;
import com.thiagoiplinsky.cursomc.resource.repositories.ClienteRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.EnderecoRepository;
import com.thiagoiplinsky.cursomc.security.UserSS;
import com.thiagoiplinsky.cursomc.services.exceptions.AuthorizationException;
import com.thiagoiplinsky.cursomc.services.exceptions.DataIntegrityException;
import com.thiagoiplinsky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
		
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user == null|| !user.hasHole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Cliente obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());
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
			throw new DataIntegrityException("Não é possível excluir porque há pedido relacionados.");
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
	
	public Cliente fromDTO(ClienteDTO objDto) {		
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {												//  Conversão do numero inteiro para tipo Cliente					
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		
		// Relacionando os dados do cliente com o endereço e telefone
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail()); 
	}
}
