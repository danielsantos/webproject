package com.aplinotech.cadastrocliente.service.impl;

import com.aplinotech.cadastrocliente.model.Usuario;
import com.aplinotech.cadastrocliente.repository.UsuarioRepository;
import com.aplinotech.cadastrocliente.service.UsuarioService;
import com.aplinotech.cadastrocliente.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public void saveOrUpdate(Usuario usuario) {
		usuario.setUsername(usuario.getEmail());
		usuario.setSituacao("I");
		usuario.setDataCadastro(new Date());
		usuario.setTokenCadastro(Util.geraToken());
		usuarioRepository.save(usuario);
	}

	@Override
	public Usuario findByToken(String token) {
		return usuarioRepository.findByToken(token);
	}

	@Override
	public void confirmaCadastro(Usuario usuario) {
		usuario.setSituacao("A");
		usuarioRepository.save(usuario);
	}

}

