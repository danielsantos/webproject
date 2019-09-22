package com.aplinotech.cadastrocliente.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aplinotech.cadastrocliente.model.Produto;

public interface ProdutoService {

    void saveOrUpdate(Produto produto);
 
    void deleteLogic(String codigo, HttpServletRequest req);
    
    Produto findById(Long id);
    
    Produto findByCodigoAndActive(String codigo, HttpServletRequest req);
 
    List<Produto> findAll();
    
    List<Produto> findAllActive(HttpServletRequest req);
    
    List<Produto> findByNome(String nome);
    
    Produto findByCodigo(String codigo);
 
}
