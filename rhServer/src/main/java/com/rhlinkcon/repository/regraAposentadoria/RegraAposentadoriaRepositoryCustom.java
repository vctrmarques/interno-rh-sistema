package com.rhlinkcon.repository.regraAposentadoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.RegraAposentadoriaFiltro;
import com.rhlinkcon.model.RegraAposentadoria;

public interface RegraAposentadoriaRepositoryCustom {
	Page<RegraAposentadoria> filtro(RegraAposentadoriaFiltro regraAposentadoriaFiltro, Pageable pageable);
}
