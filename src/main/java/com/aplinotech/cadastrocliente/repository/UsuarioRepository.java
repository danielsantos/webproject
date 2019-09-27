package com.aplinotech.cadastrocliente.repository;

import com.aplinotech.cadastrocliente.model.Produto;
import com.aplinotech.cadastrocliente.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
