package com.rhlinkcon.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Acidente de Trabalho")
@Table(name = "acidente_trabalho")
public class AcidenteTrabalho extends UserDateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2815148030508693407L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	@Column(name = "aviso")
	private String aviso;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@NotNull
	@NotBlank
	@Column(name = "setor_local")
	private String setorLocal;

	@NotNull
	@NotBlank
	@Column(name = "causa")
	private String causa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entidade_id")
	private TomadorServico entidade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tomador_servido_id")
	private TomadorServico tomadorServico;

	@NotNull
	@Column(name = "data_hora_acidente")
	private Date dataHoraAcidente;

	@NotNull
	@Column(name = "dias_afastado")
	private Integer diasAfastado;

	@NotNull
	@Column(name = "data_previsa_volta")
	private Date dataPrevistaVolta;

	@Column(name = "resultado")
	private String resultado;

	@NotNull
	@Column(name = "numero_cat")
	private Integer numeroCat;

	@Column(name = "documento_emitido")
	private boolean documentoEmitido;

	@Column(name = "data_emissao_documento")
	private Date dataEmissaoDocumento;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_anexo")
	private Anexo anexo;

	public AcidenteTrabalho() {
	}

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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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

	public TomadorServico getEntidade() {
		return entidade;
	}

	public void setEntidade(TomadorServico entidade) {
		this.entidade = entidade;
	}

	public TomadorServico getTomadorServico() {
		return tomadorServico;
	}

	public void setTomadorServico(TomadorServico tomadorServico) {
		this.tomadorServico = tomadorServico;
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

	public Anexo getAnexo() {
		return anexo;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
