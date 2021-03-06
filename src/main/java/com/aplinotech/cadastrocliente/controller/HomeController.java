package com.aplinotech.cadastrocliente.controller;

import com.aplinotech.cadastrocliente.model.Usuario;
import com.aplinotech.cadastrocliente.service.impl.EmailServiceImpl;
import com.aplinotech.cadastrocliente.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	//@Autowired
	//private EmailServiceImpl emailServiceImpl;
	
	
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
	public ModelAndView cadastrese(@ModelAttribute(value = "usuario") Usuario usuario) {
		usuarioServiceImpl.saveOrUpdate(usuario);
		//emailServiceImpl.confirmarCadastro(usuario);
		
		ModelAndView mv = new ModelAndView("/login/login");
		mv.addObject("msgSucesso", "Enviamos para você um email com um link para confirmar seu cadastro.");
		return mv;
	}

	@GetMapping("/confirmaCadastro/{token}")
	public ModelAndView confirmaCadastro(@PathVariable("token") String token) {

		Usuario usuario = usuarioServiceImpl.findByToken(token);
		if ( usuario != null ) {
			usuarioServiceImpl.confirmaCadastro(usuario);

			ModelAndView mv = new ModelAndView("/login/login");
			mv.addObject("msgSucesso", "Cadastro confirmado com sucesso!");
			return mv;
		} else {
			//ModelAndView mv = serviceLeilao.paginaInicial();
			//mv.addObject("msgErro", "Link inválido.");
			//return mv;
		}

		return null;

	}

}
