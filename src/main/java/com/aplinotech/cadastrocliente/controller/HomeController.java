package com.aplinotech.cadastrocliente.controller;

import com.aplinotech.cadastrocliente.model.Usuario;
import com.aplinotech.cadastrocliente.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "login/home";
	}
	
	@RequestMapping(value = "/cadastrese/form", method = RequestMethod.GET)
	public ModelAndView cadastreseForm() {

		ModelAndView mv = new ModelAndView("login/cadastrese");
		mv.addObject("usuario", new Usuario());
		return mv;

	}

	@RequestMapping(value = "/cadastrese", method = RequestMethod.POST)
	public String cadastrese(@ModelAttribute(value = "usuario") Usuario usuario) {
		usuarioServiceImpl.saveOrUpdate(usuario);
		return "login/cadastrese";
	}

}
