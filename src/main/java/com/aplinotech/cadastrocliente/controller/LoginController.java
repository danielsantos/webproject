package com.aplinotech.cadastrocliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aplinotech.cadastrocliente.service.impl.SetupServiceImpl;

@Controller
public class LoginController {
	
	@Autowired
	private SetupServiceImpl setupServiceImpl;

	@RequestMapping(value = "/login")
	public String login(@AuthenticationPrincipal User user) {
		return "login/login";
	}
	
}