package com.rhlinkcon.payload.simuladorAposentadoria;

import java.util.Date;

public class PossibilidadeAposentadoria implements Comparable<PossibilidadeAposentadoria> {

	private Date dataAposentadoria;
	private String modalidade;
	private String fundamentoLegal;
	private String proventos;
	private String reajuste;
	private String artigo;
	private Boolean abonoPermanencia;

	public PossibilidadeAposentadoria() {
	}

	public Date getDataAposentadoria() {
		return dataAposentadoria;
	}

	public void setDataAposentadoria(Date dataAposentadoria) {
		this.dataAposentadoria = dataAposentadoria;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public String getFundamentoLegal() {
		return fundamentoLegal;
	}

	public void setFundamentoLegal(String fundamentoLegal) {
		this.fundamentoLegal = fundamentoLegal;
	}

	public String getProventos() {
		return proventos;
	}

	public void setProventos(String proventos) {
		this.proventos = proventos;
	}

	@Override
	public int compareTo(PossibilidadeAposentadoria arg0) {
		return this.dataAposentadoria.compareTo(arg0.getDataAposentadoria());
	}

	public String getArtigo() {
		return artigo;
	}

	public void setArtigo(String artigo) {
		this.artigo = artigo;
	}

	public Boolean getAbonoPermanencia() {
		return abonoPermanencia;
	}

	public void setAbonoPermanencia(Boolean abonoPermanencia) {
		this.abonoPermanencia = abonoPermanencia;
	}

	public String getReajuste() {
		return reajuste;
	}

	public void setReajuste(String reajuste) {
		this.reajuste = reajuste;
	}

}
