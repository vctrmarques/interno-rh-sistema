package com.rhlinkcon.repository.recisaoContrato;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.RecisaoContratoFiltro;
import com.rhlinkcon.model.RecisaoContrato;


public interface RecisaoContratoRepositoryCustom {
	@Autowired
	Page<RecisaoContrato> filtro(RecisaoContratoFiltro recisaoContratoFiltro, Pageable pageable);
}
