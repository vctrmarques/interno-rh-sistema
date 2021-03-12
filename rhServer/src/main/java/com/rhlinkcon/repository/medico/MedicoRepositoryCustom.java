package com.rhlinkcon.repository.medico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.FuncionarioFiltro;
import com.rhlinkcon.filtro.MedicoFiltro;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Medico;

public interface MedicoRepositoryCustom {

	@Autowired
	Page<Medico> filtro(MedicoFiltro filtro, Pageable pageable);

	List<Medico> filtro(MedicoFiltro filtro);
}
