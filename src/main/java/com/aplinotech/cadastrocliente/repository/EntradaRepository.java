package com.aplinotech.cadastrocliente.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aplinotech.cadastrocliente.model.Entrada;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long>{

    @Query(value="SELECT e FROM Entrada e WHERE data BETWEEN :dataInicio AND :dataFim AND e.produto.usuario.id = :idUsuario")
    List<Entrada> findByDates(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim, @Param("idUsuario") Long idUsuario);
	
}
