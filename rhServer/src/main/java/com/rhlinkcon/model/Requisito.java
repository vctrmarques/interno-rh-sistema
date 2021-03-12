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
import com.rhlinkcon.payload.requisito.RequisitoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Requisito")
@Table(name = "requisito")
public class Requisito extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "dado_comparativo")
	private DadoComparativoEnum dadoComparativo;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "comparacao")
	private TipoComparacaoEnum comparacao;

	@Column(name = "valor")
	private String valor;

	@Column(name = "inicio_intervalo")
	private String inicioIntervalo;

	@Column(name = "fim_intervalo")
	private String fimIntervalo;

	public Requisito() {
	}

	public Requisito(Long id) {
		this.id = id;
	}

	public Requisito(RequisitoRequest requisitoRequest) {
		this.setDescricao(requisitoRequest.getDescricao());
		this.setDadoComparativo(DadoComparativoEnum.getEnumByString(requisitoRequest.getDadoComparativo()));
		this.setComparacao(TipoComparacaoEnum.getEnumByString(requisitoRequest.getComparacao()));
		this.setValor(requisitoRequest.getValor());
		this.setInicioIntervalo(requisitoRequest.getInicioIntervalo());
		this.setFimIntervalo(requisitoRequest.getFimIntervalo());
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public DadoComparativoEnum getDadoComparativo() {
		return dadoComparativo;
	}

	public TipoComparacaoEnum getComparacao() {
		return comparacao;
	}

	public String getValor() {
		return valor;
	}

	public String getInicioIntervalo() {
		return inicioIntervalo;
	}

	public String getFimIntervalo() {
		return fimIntervalo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setDadoComparativo(DadoComparativoEnum dadoComparativo) {
		this.dadoComparativo = dadoComparativo;
	}

	public void setComparacao(TipoComparacaoEnum comparacao) {
		this.comparacao = comparacao;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setInicioIntervalo(String inicioIntervalo) {
		this.inicioIntervalo = inicioIntervalo;
	}

	public void setFimIntervalo(String fimIntervalo) {
		this.fimIntervalo = fimIntervalo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}