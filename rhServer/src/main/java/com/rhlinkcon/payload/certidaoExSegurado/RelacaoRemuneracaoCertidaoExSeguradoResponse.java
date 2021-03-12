package com.rhlinkcon.payload.certidaoExSegurado;

import java.math.BigDecimal;

import com.rhlinkcon.model.RelacaoRemuneracaoCertidaoExSegurado;
import com.rhlinkcon.util.Projecao;

public class RelacaoRemuneracaoCertidaoExSeguradoResponse {
	
	private Long id;

	private CertidaoExSeguradoResponse certidaoExSegurado;

	private Integer ano;

	private BigDecimal janeiro;

	private BigDecimal fevereiro;

	private BigDecimal marco;

	private BigDecimal abril;

	private BigDecimal maio;

	private BigDecimal junho;

	private BigDecimal julho;

	private BigDecimal agosto;

	private BigDecimal setembro;

	private BigDecimal outubro;

	private BigDecimal novembro;

	private BigDecimal dezembro;

	private BigDecimal decimoTerceiro;
	
	public RelacaoRemuneracaoCertidaoExSeguradoResponse(RelacaoRemuneracaoCertidaoExSegurado obj) {
		setId(obj.getId());
		setCertidaoExSegurado(new CertidaoExSeguradoResponse(obj.getCertidaoExSegurado(), Projecao.BASICA));
		setAno(obj.getAno());
		setJaneiro(obj.getJaneiro());
		setFevereiro(obj.getFevereiro());
		setMarco(obj.getMarco());
		setAbril(obj.getAbril());
		setMaio(obj.getMaio());
		setJunho(obj.getJunho());
		setJulho(obj.getJulho());
		setAgosto(obj.getAgosto());
		setSetembro(obj.getSetembro());
		setOutubro(obj.getOutubro());
		setNovembro(obj.getNovembro());
		setDezembro(obj.getDezembro());
		setDecimoTerceiro(obj.getDecimoTerceiro());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertidaoExSeguradoResponse getCertidaoExSegurado() {
		return certidaoExSegurado;
	}

	public void setCertidaoExSegurado(CertidaoExSeguradoResponse certidaoExSegurado) {
		this.certidaoExSegurado = certidaoExSegurado;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public BigDecimal getJaneiro() {
		return janeiro;
	}

	public void setJaneiro(BigDecimal janeiro) {
		this.janeiro = janeiro;
	}

	public BigDecimal getFevereiro() {
		return fevereiro;
	}

	public void setFevereiro(BigDecimal fevereiro) {
		this.fevereiro = fevereiro;
	}

	public BigDecimal getMarco() {
		return marco;
	}

	public void setMarco(BigDecimal marco) {
		this.marco = marco;
	}

	public BigDecimal getAbril() {
		return abril;
	}

	public void setAbril(BigDecimal abril) {
		this.abril = abril;
	}

	public BigDecimal getMaio() {
		return maio;
	}

	public void setMaio(BigDecimal maio) {
		this.maio = maio;
	}

	public BigDecimal getJunho() {
		return junho;
	}

	public void setJunho(BigDecimal junho) {
		this.junho = junho;
	}

	public BigDecimal getJulho() {
		return julho;
	}

	public void setJulho(BigDecimal julho) {
		this.julho = julho;
	}

	public BigDecimal getAgosto() {
		return agosto;
	}

	public void setAgosto(BigDecimal agosto) {
		this.agosto = agosto;
	}

	public BigDecimal getSetembro() {
		return setembro;
	}

	public void setSetembro(BigDecimal setembro) {
		this.setembro = setembro;
	}

	public BigDecimal getOutubro() {
		return outubro;
	}

	public void setOutubro(BigDecimal outubro) {
		this.outubro = outubro;
	}

	public BigDecimal getNovembro() {
		return novembro;
	}

	public void setNovembro(BigDecimal novembro) {
		this.novembro = novembro;
	}

	public BigDecimal getDezembro() {
		return dezembro;
	}

	public void setDezembro(BigDecimal dezembro) {
		this.dezembro = dezembro;
	}

	public BigDecimal getDecimoTerceiro() {
		return decimoTerceiro;
	}

	public void setDecimoTerceiro(BigDecimal decimoTerceiro) {
		this.decimoTerceiro = decimoTerceiro;
	}
	
	
}
