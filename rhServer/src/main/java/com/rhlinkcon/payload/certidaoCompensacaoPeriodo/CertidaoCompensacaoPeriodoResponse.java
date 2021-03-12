package com.rhlinkcon.payload.certidaoCompensacaoPeriodo;

import java.time.LocalDate;

import com.rhlinkcon.model.CertidaoCompensacaoPeriodo;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.certidaoCompensacao.CertidaoCompensacaoResponse;
import com.rhlinkcon.util.Projecao;

public class CertidaoCompensacaoPeriodoResponse extends DadoCadastralResponse {

	private Long id;
	
	private CertidaoCompensacaoResponse certidaoCompensacao;
	
	private LocalDate periodoInicio;
	
	private LocalDate periodoFim;
	
	public CertidaoCompensacaoPeriodoResponse(CertidaoCompensacaoPeriodo obj) {
		this.id = obj.getId();
		this.certidaoCompensacao = new CertidaoCompensacaoResponse(obj.getCertidaoCompensacao(), Projecao.BASICA);
		this.periodoInicio = obj.getPeriodoInicio();
		this.periodoFim = obj.getPeriodoFim();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertidaoCompensacaoResponse getCertidaoCompensacao() {
		return certidaoCompensacao;
	}

	public void setCertidaoCompensacao(CertidaoCompensacaoResponse certidaoCompensacao) {
		this.certidaoCompensacao = certidaoCompensacao;
	}

	public LocalDate getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(LocalDate periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public LocalDate getPeriodoFim() {
		return periodoFim;
	}

	public void setPeriodoFim(LocalDate periodoFim) {
		this.periodoFim = periodoFim;
	}
	
}
