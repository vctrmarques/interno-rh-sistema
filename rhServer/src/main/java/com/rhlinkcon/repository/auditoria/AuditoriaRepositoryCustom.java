package com.rhlinkcon.repository.auditoria;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.AuditoriaFiltroRequest;
import com.rhlinkcon.model.Auditoria;

public interface AuditoriaRepositoryCustom {
	Page<Auditoria> filtro(AuditoriaFiltroRequest auditoriaFiltroRequest, Pageable pageable);

	List<Auditoria> filtro(String order, AuditoriaFiltroRequest auditoriaFiltroRequest);
}
