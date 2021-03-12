package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.tipoAverbacao.TipoAverbacaoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Tipo de Averbação")
@Table(name = "tipo_averbacao")
public class TipoAverbacao extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private Integer codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "formaAverbacao")
	private FormaAverbacaoEnum formaAverbacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "deducaoAverbacao")
	private DeducaoAverbacaoEnum deducaoAverbacao;

	public TipoAverbacao() {
	}

	public TipoAverbacao(TipoAverbacaoRequest tipoAverbacao) {
		this.setCodigo(tipoAverbacao.getCodigo());
		this.setDescricao(tipoAverbacao.getDescricao());
		this.setFormaAverbacao(FormaAverbacaoEnum.getEnumByString(tipoAverbacao.getFormaAverbacao()));
		this.setDeducaoAverbacao(DeducaoAverbacaoEnum.getEnumByString(tipoAverbacao.getDeducaoAverbacao()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public FormaAverbacaoEnum getFormaAverbacao() {
		return formaAverbacao;
	}

	public void setFormaAverbacao(FormaAverbacaoEnum formaAverbacao) {
		this.formaAverbacao = formaAverbacao;
	}

	public DeducaoAverbacaoEnum getDeducaoAverbacao() {
		return deducaoAverbacao;
	}

	public void setDeducaoAverbacao(DeducaoAverbacaoEnum deducaoAverbacao) {
		this.deducaoAverbacao = deducaoAverbacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}