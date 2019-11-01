package com.aplinotech.cadastrocliente.service;

import java.util.Date;
import java.util.List;

import com.aplinotech.cadastrocliente.model.Entrada;
import com.aplinotech.cadastrocliente.model.Produto;
import com.aplinotech.cadastrocliente.model.dto.PesquisarProdutoDTO;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface EntradaService {

    void saveOrUpdate(Entrada entrada);
 
    List<Entrada> findByDates(Date dataInicio, Date dataFim, HttpServletRequest req);

    ModelAndView entrada();

    ModelAndView entradaPesquisar(PesquisarProdutoDTO dto, HttpServletRequest req);

    ModelAndView entradaSalvar(Produto produto);

    ModelAndView removeProdutoBaixa(Long id, HttpSession session);

    ModelAndView registrarBaixa(HttpSession session);

    String consultaProduto(PesquisarProdutoDTO dto, HttpServletRequest req, ModelMap modelMap, HttpSession session);

    String baixaAddProd(@ModelAttribute("produto") Produto produto, ModelMap modelMap, HttpSession session);

    ModelAndView consultaProdutoForm();

    String baixa(ModelMap modelMap, HttpSession session);

    String retornaProdutoPesquisadoPorNome(@PathVariable(value = "codigo") String codigo, HttpServletRequest req,
                                           ModelMap modelMap, HttpSession session);

    String baixa(@ModelAttribute("dto") PesquisarProdutoDTO dto, HttpServletRequest req, ModelMap modelMap,
                 HttpSession session);
    
}