package com.thiagoiplinsky.cursomc.resource.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thiagoiplinsky.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
	
//	Método que busca no banco de dados um cliente passando uma (String Email) como parâmetro de busca
	
	@Transactional(readOnly=true) // readOnly -> Anotação que indica que o método não necessita ser envolvida com uma transação de banco de dados
	Cliente findByEmail(String email);
}
