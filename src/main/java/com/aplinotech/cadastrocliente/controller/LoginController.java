package com.aplinotech.cadastrocliente.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/login")
	public String login(@AuthenticationPrincipal User user) {
		return "login/login";
	}
	
}