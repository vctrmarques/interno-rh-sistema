package com.rhlinkcon.repository.pensionista;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.PensionistaFiltro;
import com.rhlinkcon.model.Pensionista;

public interface PensionistaRepositoryCustom {

	@Autowired
	Page<Pensionista> filtro(PensionistaFiltro pensionistaFiltro, Pageable pageable);

	@Autowired
	List<Pensionista> filtro(PensionistaFiltro pensionistaFiltro);

}
