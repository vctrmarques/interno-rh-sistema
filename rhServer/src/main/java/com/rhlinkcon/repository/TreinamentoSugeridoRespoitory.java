package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.TreinamentoSugerido;

public interface TreinamentoSugeridoRespoitory extends JpaRepository<TreinamentoSugerido, Long> {
	
	Page<TreinamentoSugerido> findAllByFuncionariosIn(List<Funcionario> listaFuncionarios, Pageable pageable);
}
