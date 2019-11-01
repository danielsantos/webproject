package com.aplinotech.cadastrocliente.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplinotech.cadastrocliente.model.Produto;
import com.aplinotech.cadastrocliente.model.Usuario;
import com.aplinotech.cadastrocliente.repository.ProdutoRepository;
import com.aplinotech.cadastrocliente.service.ProdutoService;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	

	@Override
	public void saveOrUpdate(Produto produto) {
		produtoRepository.save(produto);
	}

	@Override
	public void deleteLogic(String codigo, HttpServletRequest req) {
		Usuario usuario = userServiceImpl.findByNome(req.getRemoteUser()).get(0);
		Produto produto = produtoRepository.findByCodigoAndActive(codigo, usuario.getId());
		produto.setStatus("I");
		saveOrUpdate(produto);
	}

	@Override
	public Produto findById(Long id) {
		return produtoRepository.findOne(id);
	}
	
	@Override
	public Produto findByCodigoAndActive(String codigo, HttpServletRequest req) {
		Usuario usuario = userServiceImpl.findByUsernameAndActive(req.getRemoteUser());
		return produtoRepository.findByCodigoAndActive(codigo, usuario.getId());
	}

	@Override
	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}
	
	@Override
	public List<Produto> findAllActive(HttpServletRequest req) {
		Usuario usuario = userServiceImpl.findByUsernameAndActive(req.getRemoteUser());
		return produtoRepository.findAllActive(usuario.getId());
		/*List<Produto> produtos = produtoRepository.findAllActive(usuario.getId());
		
		if (produtos.isEmpty()) {
			return new ArrayList<Produto>();
		} else {
			return produtos;
		}*/
	}
	
	@Override
	public List<Produto> findByNome(String nome) {
		return produtoRepository.findByNome(nome);
	}
	
	@Override
	public Produto findByCodigo(String codigo) {
		return produtoRepository.findByCodigo(codigo);
	}

}
