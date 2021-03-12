package com.rhlinkcon.repository.banco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.BancoFiltro;
import com.rhlinkcon.model.Banco;

public interface BancoRepositoryCustom {
	Page<Banco> filtro(BancoFiltro bancoFiltro, Pageable pageable);

}
