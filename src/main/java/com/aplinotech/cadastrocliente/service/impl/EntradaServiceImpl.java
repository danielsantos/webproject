package com.aplinotech.cadastrocliente.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aplinotech.cadastrocliente.model.Baixa;
import com.aplinotech.cadastrocliente.model.ItemBaixa;
import com.aplinotech.cadastrocliente.model.Produto;
import com.aplinotech.cadastrocliente.model.dto.PesquisarProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplinotech.cadastrocliente.model.Entrada;
import com.aplinotech.cadastrocliente.repository.EntradaRepository;
import com.aplinotech.cadastrocliente.service.EntradaService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class EntradaServiceImpl implements EntradaService {
	
	@Autowired
	private EntradaRepository entradaRepository;

	@Autowired
	private ProdutoServiceImpl produtoServiceImpl;

	@Autowired
	private BaixaServiceImpl baixaServiceImpl;

	@Autowired
	private ItemBaixaServiceImpl itemBaixaServiceImpl;


	@Override
	public void saveOrUpdate(Entrada entrada) {
		entradaRepository.save(entrada);
	}

	@Override
	public List<Entrada> findByDates(Date dataInicio, Date dataFim) {
		return entradaRepository.findByDates(dataInicio, dataFim);
	}

	@Override
	public ModelAndView entrada() {
		ModelAndView mv = new ModelAndView("produto/entrada");
		mv.addObject("dto", new PesquisarProdutoDTO());
		mv.addObject("produto", new Produto());
		return mv;
	}

	@Override
	public ModelAndView entradaPesquisar(PesquisarProdutoDTO dto, HttpServletRequest req) {

		Produto produto = produtoServiceImpl.findByCodigoAndActive(dto.getCodigoProduto(), req);

		ModelAndView mv = new ModelAndView("produto/entrada");
		mv.addObject("dto", new PesquisarProdutoDTO());
		if ( produto != null ) {
			mv.addObject("produto", produto);
		} else {
			mv.addObject("msgError", "Produto não encontrado");
			mv.addObject("produto", new Produto());
		}
		return mv;
	}

	@Override
	public ModelAndView entradaSalvar(Produto produto) {
		if (produto.getId() == null) {
			ModelAndView mv = new ModelAndView("produto/entrada");
			mv.addObject("msgWarning", "Escolha primeiro o produto");
			mv.addObject("dto", new PesquisarProdutoDTO());
			mv.addObject("produto", new Produto());
			return mv;
		}

		Produto produtoBanco = produtoServiceImpl.findById(produto.getId());
		produtoBanco.setValorVendaUnitario(produto.getValorVendaUnitario());
		produtoBanco.setCustoUnitario(produto.getCustoUnitario());
		produtoBanco.setQuantidadeTotal(produto.getQtdParaBaixa() + produtoBanco.getQuantidadeTotal());
		produtoServiceImpl.saveOrUpdate(produtoBanco);

		Entrada entrada = new Entrada();
		entrada.setCustoUnitario(produto.getCustoUnitario());
		entrada.setValorVendaUnitario(produto.getValorVendaUnitario());
		entrada.setProduto(produto);
		entrada.setQuantidade(produto.getQtdParaBaixa());
		entrada.setData(new Date());
		saveOrUpdate(entrada);

		ModelAndView mv = new ModelAndView("produto/entrada");
		mv.addObject("dto", new PesquisarProdutoDTO());
		mv.addObject("produto", new Produto());
		mv.addObject("mensagem", "Entrada de Estoque efetuada com sucesso!");
		return mv;
	}

	@Override
	public ModelAndView removeProdutoBaixa(Long id, HttpSession session) {
		Baixa baixa = (Baixa) session.getAttribute("baixa");
		List<Produto> novaLista = new ArrayList<Produto>();
		for (Produto p : baixa.getProdutos()) {
			if (!p.getId().equals(id)) {
				novaLista.add(p);
			}
		}
		BigDecimal total = BigDecimal.ZERO;
		if (!novaLista.isEmpty()) {
			for (Produto p : novaLista) {
				total = p.getValorTotal().add(total);
			}
		}
		baixa.setValorTotal(total);
		baixa.setProdutos(novaLista);
		session.setAttribute("baixa", baixa);
		ModelAndView mv = new ModelAndView("produto/baixa");
		mv.addObject("produtosBaixa", baixa.getProdutos());
		mv.addObject("produto", new Produto());
		mv.addObject("dto", new PesquisarProdutoDTO());
		mv.addObject("baixa", baixa);
		return mv;
	}

	@Override
	public ModelAndView registrarBaixa(HttpSession session) {
		Baixa baixa = (Baixa) session.getAttribute("baixa");
		baixa.setData(new Date());
		baixaServiceImpl.saveOrUpdate(baixa);
		for ( Produto produto : baixa.getProdutos() ) {
			ItemBaixa item = new ItemBaixa();
			item.setProduto(produto);
			item.setQuantidade(produto.getQtdParaBaixa());
			item.setBaixa(baixa);
			item.setValorUnitario(produto.getValorVendaUnitario());
			itemBaixaServiceImpl.saveOrUpdate(item);
			Produto prod = produtoServiceImpl.findById(produto.getId());
			prod.setQuantidadeTotal(prod.getQuantidadeTotal() - produto.getQtdParaBaixa());
			produtoServiceImpl.saveOrUpdate(prod);
		}
		session.setAttribute("baixa", null);
		ModelAndView mv = new ModelAndView("produto/baixa");
		mv.addObject("mensagem", "Baixa efetuada com sucesso!");
		mv.addObject("produto", new Produto());
		mv.addObject("dto", new PesquisarProdutoDTO());
		mv.addObject("baixa", new Baixa());
		return mv;
	}

	@Override
	public String consultaProduto(PesquisarProdutoDTO dto, HttpServletRequest req, ModelMap modelMap, HttpSession session) {

		if ( !"".equals(dto.getNome()) ) {

			modelMap.addAttribute("produtos", produtoServiceImpl.findByNome(dto. getNome()));

		} else {

			modelMap.addAttribute("produtos", produtoServiceImpl.findAllActive(req));

		}

		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		return "estoque/listar";
	}

	@Override
	public String baixaAddProd(@ModelAttribute("produto") Produto produto, ModelMap modelMap, HttpSession session) {
		List<Produto> list = new ArrayList<Produto>();
		Baixa baixa = new Baixa();
		if ( session.getAttribute("baixa") == null ) {
			session.setAttribute("baixa", baixa);
		} else {
			baixa = (Baixa) session.getAttribute("baixa");
			BigDecimal total = baixa.getValorTotal();
			if ( baixa.getProdutos() != null && !baixa.getProdutos().isEmpty() ) {
				for (Produto p : list) {
					total = p.getValorTotal().add(total);
				}
				if ( !baixa.getProdutos().contains(produto) ) {
					baixa.getProdutos().add(produto);
				} else {
					int pos = baixa.getProdutos().indexOf(produto);
					Integer qtdAtual = baixa.getProdutos().get(pos).getQtdParaBaixa();
					baixa.getProdutos().get(pos).setQtdParaBaixa( qtdAtual + produto.getQtdParaBaixa() );
					baixa.getProdutos().get(pos).setValorVendaUnitario(produto.getValorVendaUnitario());
				}
				total = produto.getValorTotal().add(total);
			} else {
				baixa.setProdutos(new ArrayList<Produto>());
				baixa.getProdutos().add(produto);
				total = produto.getValorTotal().add(total);
			}
			baixa.setValorTotal(total);
			session.setAttribute("baixa", baixa);
		}
		modelMap.addAttribute("produtosBaixa", baixa.getProdutos());
		modelMap.addAttribute("produto", new Produto());
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		modelMap.addAttribute("baixa", baixa);
		return "produto/baixa";
	}

	@Override
	public ModelAndView consultaProdutoForm() {
		ModelAndView mv = new ModelAndView("estoque/listar");
		mv.addObject("produtos", new ArrayList<Produto>());
		mv.addObject("dto", new PesquisarProdutoDTO());
		return mv;
	}

	@Override
	public String baixa(ModelMap modelMap, HttpSession session) {

		Baixa baixa = new Baixa();

		if ( session.getAttribute("baixa") == null ) {

			session.setAttribute("baixa", baixa);

		} else {

			baixa = (Baixa) session.getAttribute("baixa");
			BigDecimal total = BigDecimal.ZERO;

			if ( baixa.getProdutos() != null && !baixa.getProdutos().isEmpty() ) {
				for (Produto p : baixa.getProdutos()) {
					total = p.getValorTotal().add(total);
				}
			}

			baixa.setValorTotal(total);
			session.setAttribute("baixa", baixa);

		}

		modelMap.addAttribute("produtos", produtoServiceImpl.findAll());
		modelMap.addAttribute("produtosBaixa", baixa.getProdutos());
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		modelMap.addAttribute("produto", new Produto());
		modelMap.addAttribute("baixa", baixa);
		return "produto/baixa";

	}

	@Override
	public String retornaProdutoPesquisadoPorNome(@PathVariable(value = "codigo") String codigo,
												  HttpServletRequest req, ModelMap modelMap, HttpSession session) {

		Baixa baixa = new Baixa();

		if ( session.getAttribute("baixa") == null ) {

			session.setAttribute("baixa", new Baixa());

		} else {

			BigDecimal total = BigDecimal.ZERO;
			baixa = (Baixa) session.getAttribute("baixa");

			if (baixa.getProdutos() != null && !baixa.getProdutos().isEmpty()) {
				for (Produto p : baixa.getProdutos()) {
					total = p.getValorTotal().add(total);
				}
			}

			baixa.setValorTotal(total);
			session.setAttribute("baixa", baixa);

		}

		Produto produto = produtoServiceImpl.findByCodigoAndActive(codigo, req);

		modelMap.addAttribute("produtosBaixa", baixa.getProdutos());
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		modelMap.addAttribute("produto", produto);
		modelMap.addAttribute("baixa", baixa);

		return "produto/baixa";

	}

	@Override
	public String baixa(@ModelAttribute("dto") PesquisarProdutoDTO dto, HttpServletRequest req,
						ModelMap modelMap, HttpSession session) {

		Baixa baixa = new Baixa();
		if ( session.getAttribute("baixa") == null ) {
			session.setAttribute("baixa", new Baixa());
		} else {
			BigDecimal total = BigDecimal.ZERO;
			baixa = (Baixa) session.getAttribute("baixa");
			if (baixa.getProdutos() != null && !baixa.getProdutos().isEmpty()) {
				for (Produto p : baixa.getProdutos()) {
					total = p.getValorTotal().add(total);
				}
			}
			baixa.setValorTotal(total);
			session.setAttribute("baixa", baixa);
		}

		Produto produto = produtoServiceImpl.findByCodigoAndActive(dto.getCodigoProduto(), req);

		// TODO PAREI AQUI

		if (produto == null) {
			modelMap.addAttribute("produto", new Produto());
			modelMap.addAttribute("msgError", "Produto não encontrado");
			return "produto/baixa";
		}

		modelMap.addAttribute("produtosBaixa", baixa.getProdutos());
		modelMap.addAttribute("dto", new PesquisarProdutoDTO());
		modelMap.addAttribute("produto", produto);
		modelMap.addAttribute("baixa", baixa);
		return "produto/baixa";

	}

}