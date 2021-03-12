package com.rhlinkcon.payload.verba;

import java.time.Instant;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VerbaRequest {

	private Long id;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String codigo;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String descricaoVerba;

	@NotNull
	@NotBlank
	@Size(max = 1000)
	private String descricaoResumida;

	@NotNull
	private String tipoVerba;

	private String destinacaoExterna;

	private Double valorMaximo;

	@NotNull
	private String tipoValor;

	private String comentario;

	@NotNull
	private Integer contaDebito;

	@NotNull
	private Integer contaCredito;

	private Integer contaAuxiliarPrimaria;

	private Integer contaAuxiliarSecundaria;

	private Instant vigenciaInicial;

	private Instant vigenciaFinal;

	@NotNull
	private String recorrencia;

	@NotNull
	private Long centroCustoId;

	private Double referencia;

	private Long verbaAssociadaId;

	private String identificadorVerba;

	@NotNull
	@NotEmpty
	private List<ItemFormulaDto> formulas;

	private String faixaAliquota;

	private List<Long> incidenciasIds;

	/** Usado apenas para inserção de verbas manuais que possuem valor definido. */
	@Transient
	private Double valor;

	public VerbaRequest() {

	}

	public Long getCentroCustoId() {
		return centroCustoId;
	}

	public void setCentroCustoId(Long centroCustoId) {
		this.centroCustoId = centroCustoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricaoVerba() {
		return descricaoVerba;
	}

	public void setDescricaoVerba(String descricaoVerba) {
		this.descricaoVerba = descricaoVerba;
	}

	public String getDescricaoResumida() {
		return descricaoResumida;
	}

	public void setDescricaoResumida(String descricaoResumida) {
		this.descricaoResumida = descricaoResumida;
	}

	public String getTipoVerba() {
		return tipoVerba;
	}

	public void setTipoVerba(String tipoVerba) {
		this.tipoVerba = tipoVerba;
	}

	public String getDestinacaoExterna() {
		return destinacaoExterna;
	}

	public void setDestinacaoExterna(String destinacaoExterna) {
		this.destinacaoExterna = destinacaoExterna;
	}

	public Double getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(Double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getContaDebito() {
		return contaDebito;
	}

	public void setContaDebito(Integer contaDebito) {
		this.contaDebito = contaDebito;
	}

	public Integer getContaCredito() {
		return contaCredito;
	}

	public void setContaCredito(Integer contaCredito) {
		this.contaCredito = contaCredito;
	}

	public Integer getContaAuxiliarPrimaria() {
		return contaAuxiliarPrimaria;
	}

	public void setContaAuxiliarPrimaria(Integer contaAuxiliarPrimaria) {
		this.contaAuxiliarPrimaria = contaAuxiliarPrimaria;
	}

	public Integer getContaAuxiliarSecundaria() {
		return contaAuxiliarSecundaria;
	}

	public void setContaAuxiliarSecundaria(Integer contaAuxiliarSecundaria) {
		this.contaAuxiliarSecundaria = contaAuxiliarSecundaria;
	}

	public Instant getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Instant vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public Instant getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(Instant vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public String getRecorrencia() {
		return recorrencia;
	}

	public void setRecorrencia(String recorrencia) {
		this.recorrencia = recorrencia;
	}

	public String getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getReferencia() {
		return referencia;
	}

	public void setReferencia(Double referencia) {
		this.referencia = referencia;
	}

	public List<Long> getIncidenciasIds() {
		return incidenciasIds;
	}

	public void setIncidenciasIds(List<Long> incidenciasIds) {
		this.incidenciasIds = incidenciasIds;
	}

	public Long getVerbaAssociadaId() {
		return verbaAssociadaId;
	}

	public void setVerbaAssociadaId(Long verbaAssociadaId) {
		this.verbaAssociadaId = verbaAssociadaId;
	}

	public String getIdentificadorVerba() {
		return identificadorVerba;
	}

	public void setIdentificadorVerba(String identificadorVerba) {
		this.identificadorVerba = identificadorVerba;
	}

	public List<ItemFormulaDto> getFormulas() {
		return formulas;
	}

	public void setFormulas(List<ItemFormulaDto> formulas) {
		this.formulas = formulas;
	}

	public String getFaixaAliquota() {
		return faixaAliquota;
	}

	public void setFaixaAliquota(String faixaAliquota) {
		this.faixaAliquota = faixaAliquota;
	}

}
