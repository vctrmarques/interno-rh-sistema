package com.rhlinkcon.repository.funcionario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.FuncionarioFiltro;
import com.rhlinkcon.model.Funcionario;

public interface FuncionarioRepositoryCustom {

	@Autowired
	Page<Funcionario> filtro(FuncionarioFiltro funcionarioFiltro, Pageable pageable);

	List<Funcionario> filtro(FuncionarioFiltro funcionarioFiltro);

}
