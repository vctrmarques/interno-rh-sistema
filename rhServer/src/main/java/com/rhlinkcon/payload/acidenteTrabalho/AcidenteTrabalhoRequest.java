package com.rhlinkcon.payload.acidenteTrabalho;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AcidenteTrabalhoRequest {

	private Long id;

	@NotNull
	@NotBlank
	private String aviso;

	@NotNull
	private Long funcionarioId;

	@NotNull
	@NotBlank
	private String setorLocal;

	@NotNull
	@NotBlank
	private String causa;

	private Long entidadeId;

	private Long tomadorServicoId;

	@NotNull
	private Date dataHoraAcidente;

	@NotNull
	private Integer diasAfastado;

	@NotNull
	private Date dataPrevistaVolta;

	private String resultado;

	@NotNull
	private Integer numeroCat;

	private boolean documentoEmitido;

	private Date dataEmissaoDocumento;

	private Long anexoId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getSetorLocal() {
		return setorLocal;
	}

	public void setSetorLocal(String setorLocal) {
		this.setorLocal = setorLocal;
	}

	public String getCausa() {
		return causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public Long getEntidadeId() {
		return entidadeId;
	}

	public void setEntidadeId(Long entidadeId) {
		this.entidadeId = entidadeId;
	}

	public Long getTomadorServicoId() {
		return tomadorServicoId;
	}

	public void setTomadorServicoId(Long tomadorServicoId) {
		this.tomadorServicoId = tomadorServicoId;
	}

	public Date getDataHoraAcidente() {
		return dataHoraAcidente;
	}

	public void setDataHoraAcidente(Date dataHoraAcidente) {
		this.dataHoraAcidente = dataHoraAcidente;
	}

	public Integer getDiasAfastado() {
		return diasAfastado;
	}

	public void setDiasAfastado(Integer diasAfastado) {
		this.diasAfastado = diasAfastado;
	}

	public Date getDataPrevistaVolta() {
		return dataPrevistaVolta;
	}

	public void setDataPrevistaVolta(Date dataPrevistaVolta) {
		this.dataPrevistaVolta = dataPrevistaVolta;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Integer getNumeroCat() {
		return numeroCat;
	}

	public void setNumeroCat(Integer numeroCat) {
		this.numeroCat = numeroCat;
	}

	public boolean isDocumentoEmitido() {
		return documentoEmitido;
	}

	public void setDocumentoEmitido(boolean documentoEmitido) {
		this.documentoEmitido = documentoEmitido;
	}

	public Date getDataEmissaoDocumento() {
		return dataEmissaoDocumento;
	}

	public void setDataEmissaoDocumento(Date dataEmissaoDocumento) {
		this.dataEmissaoDocumento = dataEmissaoDocumento;
	}

	public Long getAnexoId() {
		return anexoId;
	}

	public void setAnexoId(Long anexoId) {
		this.anexoId = anexoId;
	}

}
