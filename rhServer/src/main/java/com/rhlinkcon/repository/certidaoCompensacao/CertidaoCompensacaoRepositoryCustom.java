package com.rhlinkcon.repository.certidaoCompensacao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.CertidaoCompensacaoFiltro;
import com.rhlinkcon.model.CertidaoCompensacao;

public interface CertidaoCompensacaoRepositoryCustom {
	Page<CertidaoCompensacao> filtro(CertidaoCompensacaoFiltro certidaoCompensacaoFiltro, Pageable pageable);

	List<CertidaoCompensacao> filtro(CertidaoCompensacaoFiltro certidaoCompensacaoFiltro);

}