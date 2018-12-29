package com.thiagoiplinsky.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.thiagoiplinsky.cursomc.domain.Categoria;
import com.thiagoiplinsky.cursomc.domain.Cidade;
import com.thiagoiplinsky.cursomc.domain.Cliente;
import com.thiagoiplinsky.cursomc.domain.Endereco;
import com.thiagoiplinsky.cursomc.domain.Estado;
import com.thiagoiplinsky.cursomc.domain.ItemPedido;
import com.thiagoiplinsky.cursomc.domain.Pagamento;
import com.thiagoiplinsky.cursomc.domain.PagamentoComBoleto;
import com.thiagoiplinsky.cursomc.domain.PagamentoComCartao;
import com.thiagoiplinsky.cursomc.domain.Pedido;
import com.thiagoiplinsky.cursomc.domain.Produto;
import com.thiagoiplinsky.cursomc.domain.enums.EstadoPagamento;
import com.thiagoiplinsky.cursomc.domain.enums.TipoCliente;
import com.thiagoiplinsky.cursomc.resource.repositories.CategoriaRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.CidadeRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.ClienteRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.EnderecoRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.EstadoRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.ItemPedidoRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.PagamentoRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.PedidoRepository;
import com.thiagoiplinsky.cursomc.resource.repositories.ProdutoRepository;

// Classe responsável por instanciar um banco de dados de teste

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepositoy;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instantiateDatabase() throws ParseException {

		// Instanciação das Categorias *********************

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		// Instanciação dos Produtos *********************

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Relógio", 200.00);
		Produto p7 = new Produto(null, "TV", 1200.00);
		Produto p8 = new Produto(null, "Pá", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Sabonete", 50.00);
		Produto p11 = new Produto(null, "Quadro", 50.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p10));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p6, p9, p11));
		cat7.getProdutos().addAll(Arrays.asList(p10));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll((Arrays.asList(cat1, cat4)));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat6));
		p7.getCategorias().addAll(Arrays.asList(cat4));

		categoriaRepository.save(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.save(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		// Instanciação dos Estados *********************

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		// Instanciação das Cidades *********************

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepositoy.save(Arrays.asList(est1, est2));
		cidadeRepository.save(Arrays.asList(c1, c2, c3));

		// Instanciação dos Clientes *********************

		Cliente cli1 = new Cliente(null, "Ana Izabel Mendes Iplinsky", "thiagoiplinsky@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA, pe.encode("123"));

		cli1.getTelefones().addAll(Arrays.asList("34996956095", "998412458"));

		// Instanciação dos endereços *********************

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.save(Arrays.asList(cli1));
		enderecoRepository.save(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("13/12/2018 20:45"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("15/10/2018 15:13"), cli1, e2);

		// Instanciação das Formas de Pagamento *********************

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2018 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.save(Arrays.asList(ped1, ped2));
		pagamentoRepository.save(Arrays.asList(pagto1, pagto2));

		// Instanciação dos Itens do Pedido *********************

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.save(Arrays.asList(ip1, ip2, ip3));
	}
}
