package com.thiagoiplinsky.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiagoiplinsky.cursomc.domain.ItemPedido;
import com.thiagoiplinsky.cursomc.domain.PagamentoComBoleto;
import com.thiagoiplinsky.cursomc.domain.Pedido;
import com.thiagoiplinsky.cursomc.domain.enums.EstadoPagamento;
import com.thiagoiplinsky.cursomc.resource.repositories.ItemPedidoRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.PagamentoRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.PedidoRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.ProdutoRepository;
import com.thiagoiplinsky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoRepository produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.findOne(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		return obj;
	}
}
