package com.aplinotech.cadastrocliente.service;

import com.aplinotech.cadastrocliente.model.Usuario;


public interface UsuarioService {

    void saveOrUpdate(Usuario usuario);

    Usuario findByToken(String token);

    void confirmaCadastro(Usuario usuario);

}
