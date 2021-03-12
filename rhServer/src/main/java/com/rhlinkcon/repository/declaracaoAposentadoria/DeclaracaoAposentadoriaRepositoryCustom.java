package com.rhlinkcon.repository.declaracaoAposentadoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.DeclaracaoAposentadoriaFiltro;
import com.rhlinkcon.model.DeclaracaoAposentadoria;

public interface DeclaracaoAposentadoriaRepositoryCustom {
	Page<DeclaracaoAposentadoria> filtro(DeclaracaoAposentadoriaFiltro declaracaoAposentadoriaFiltro,
			Pageable pageable);

}
