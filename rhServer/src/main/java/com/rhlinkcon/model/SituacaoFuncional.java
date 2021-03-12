package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Situação Funcional")
@Table(name = "situacao_funcional")
public class SituacaoFuncional extends UserDateAudit {

	private static final long serialVersionUID = -5076828154736857130L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@Column(name = "rais_situacao")
	private Integer raisSituacao;

	@Column(name = "rais")
	private Integer rais;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoAfastamentoEnum tipo;

	@Enumerated(EnumType.STRING)
	@Column(name = "modalidade")
	private ModalidadeAfastamentoEnum modalidade;

	@Column(name = "qtd_dias")
	private Integer qtdDias;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_motivo_afastamento")
	private MotivoAfastamento motivoAfastamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_motivo_desligamento")
	private MotivoDesligamento motivoDesligamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_causa_afastamento")
	private CausaAfastamento causaAfastamento;

	@Column(name = "entra_folha")
	private boolean entraFolha;

	public SituacaoFuncional() {
	}

	public SituacaoFuncional(Long id) {
		this.id = id;
	}

	public SituacaoFuncional(SituacaoFuncionalRequest situacaoFuncionalRequest) {
		this.setCodigo(situacaoFuncionalRequest.getCodigo());
		this.setDescricao(situacaoFuncionalRequest.getDescricao());
		this.setRaisSituacao(situacaoFuncionalRequest.getRaisSituacao());
		this.setRais(situacaoFuncionalRequest.getRais());

		if (situacaoFuncionalRequest.getTipo() != null && !situacaoFuncionalRequest.getTipo().isEmpty())
			this.setTipo(TipoAfastamentoEnum.getEnumByString(situacaoFuncionalRequest.getTipo()));

		if (situacaoFuncionalRequest.getModalidade() != null && !situacaoFuncionalRequest.getModalidade().isEmpty())
			this.setModalidade(ModalidadeAfastamentoEnum.getEnumByString(situacaoFuncionalRequest.getModalidade()));

		this.setQtdDias(situacaoFuncionalRequest.getQtdDias());
		this.entraFolha = situacaoFuncionalRequest.isEntraFolha();

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getRaisSituacao() {
		return raisSituacao;
	}

	public void setRaisSituacao(Integer raisSituacao) {
		this.raisSituacao = raisSituacao;
	}

	public Integer getRais() {
		return rais;
	}

	public void setRais(Integer rais) {
		this.rais = rais;
	}

	public TipoAfastamentoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoAfastamentoEnum tipo) {
		this.tipo = tipo;
	}

	public ModalidadeAfastamentoEnum getModalidade() {
		return modalidade;
	}

	public void setModalidade(ModalidadeAfastamentoEnum modalidade) {
		this.modalidade = modalidade;
	}

	public Integer getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}

	public MotivoAfastamento getMotivoAfastamento() {
		return motivoAfastamento;
	}

	public void setMotivoAfastamento(MotivoAfastamento motivoAfastamento) {
		this.motivoAfastamento = motivoAfastamento;
	}

	public MotivoDesligamento getMotivoDesligamento() {
		return motivoDesligamento;
	}

	public void setMotivoDesligamento(MotivoDesligamento motivoDesligamento) {
		this.motivoDesligamento = motivoDesligamento;
	}

	public CausaAfastamento getCausaAfastamento() {
		return causaAfastamento;
	}

	public void setCausaAfastamento(CausaAfastamento causaAfastamento) {
		this.causaAfastamento = causaAfastamento;
	}

	public boolean isEntraFolha() {
		return entraFolha;
	}

	public void setEntraFolha(boolean entraFolha) {
		this.entraFolha = entraFolha;
	}

	@Override
	public String getLabel() {
		return this.descricao;
	}

}