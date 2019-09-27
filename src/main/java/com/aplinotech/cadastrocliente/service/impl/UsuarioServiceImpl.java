package com.aplinotech.cadastrocliente.service.impl;

import com.aplinotech.cadastrocliente.model.Usuario;
import com.aplinotech.cadastrocliente.repository.UsuarioRepository;
import com.aplinotech.cadastrocliente.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public void saveOrUpdate(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

}
