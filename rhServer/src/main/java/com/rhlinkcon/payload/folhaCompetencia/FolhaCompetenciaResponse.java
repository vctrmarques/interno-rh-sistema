package com.rhlinkcon.payload.folhaCompetencia;

import java.time.LocalDate;
import java.util.Date;

import com.rhlinkcon.model.FolhaCompetencia;

public class FolhaCompetenciaResponse {
	private Long id;

	private Integer mesCompetencia;

	private Integer anoCompetencia;

	private Date inicioCompetencia;

	private Date fimCompetencia;

	private LocalDate programacaoFechamento;

	private String mesAno;

//	atributo criado para uso na ficha financeira relatório para exibir o valor total de cada competencia
	private Double valorTotalProventos;

	private Double valorTotalDescontos;

	private Double valorTotalLiquido;

	private boolean checarRecad;

	private String checarRecadAmpLegal;

	/**
	 * Construtor criado para atender Relatório de Ficha financeira
	 * 
	 * @param mesAno
	 */
	public FolhaCompetenciaResponse(String mesAno) {
		String[] mesAnoVector = mesAno.split("/");
		this.mesCompetencia = Integer.valueOf(mesAnoVector[0]);
		this.anoCompetencia = Integer.valueOf(mesAnoVector[1]);
		this.mesAno = (this.getMesCompetencia() < 10 ? "0" + this.getMesCompetencia().toString()
				: this.getMesCompetencia().toString()) + "/" + this.getAnoCompetencia().toString();
		this.valorTotalProventos = 0.0;
		this.valorTotalDescontos = 0.0;
		this.valorTotalLiquido = 0.0;
	}

	public FolhaCompetenciaResponse(FolhaCompetencia competencia) {
		this.id = competencia.getId();
		this.mesCompetencia = competencia.getMesCompetencia();
		this.anoCompetencia = competencia.getAnoCompetencia();
		this.inicioCompetencia = competencia.getInicioCompetencia();
		this.fimCompetencia = competencia.getFimCompetencia();
		this.mesAno = competencia.getMesCompetencia().toString() + "/" + competencia.getAnoCompetencia().toString();
		this.programacaoFechamento = competencia.getProgramacaoFechamento();
		this.checarRecad = competencia.isChecarRecad();
		this.checarRecadAmpLegal = competencia.getChecarRecadAmpLegal();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMesCompetencia() {
		return mesCompetencia;
	}

	public void setMesCompetencia(Integer mesCompetencia) {
		this.mesCompetencia = mesCompetencia;
	}

	public Integer getAnoCompetencia() {
		return anoCompetencia;
	}

	public void setAnoCompetencia(Integer anoCompetencia) {
		this.anoCompetencia = anoCompetencia;
	}

	public Date getInicioCompetencia() {
		return inicioCompetencia;
	}

	public void setInicioCompetencia(Date inicioCompetencia) {
		this.inicioCompetencia = inicioCompetencia;
	}

	public Date getFimCompetencia() {
		return fimCompetencia;
	}

	public void setFimCompetencia(Date fimCompetencia) {
		this.fimCompetencia = fimCompetencia;
	}

	public String getMesAno() {
		return mesAno;
	}

	public void setMesAno(String mesAno) {
		this.mesAno = mesAno;
	}

	public Double getValorTotalProventos() {
		return valorTotalProventos;
	}

	public void setValorTotalProventos(Double valorTotalProventos) {
		this.valorTotalProventos = valorTotalProventos;
	}

	public Double getValorTotalDescontos() {
		return valorTotalDescontos;
	}

	public void setValorTotalDescontos(Double valorTotalDescontos) {
		this.valorTotalDescontos = valorTotalDescontos;
	}

	public Double getValorTotalLiquido() {
		return valorTotalLiquido;
	}

	public void setValorTotalLiquido(Double valorTotalLiquido) {
		this.valorTotalLiquido = valorTotalLiquido;
	}

	public LocalDate getProgramacaoFechamento() {
		return programacaoFechamento;
	}

	public void setProgramacaoFechamento(LocalDate programacaoFechamento) {
		this.programacaoFechamento = programacaoFechamento;
	}

	public boolean isChecarRecad() {
		return checarRecad;
	}

	public void setChecarRecad(boolean checarRecad) {
		this.checarRecad = checarRecad;
	}

	public String getChecarRecadAmpLegal() {
		return checarRecadAmpLegal;
	}

	public void setChecarRecadAmpLegal(String checarRecadAmpLegal) {
		this.checarRecadAmpLegal = checarRecadAmpLegal;
	}

}
