package com.rhlinkcon.model;

import java.time.Instant;

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

import org.apache.logging.log4j.util.Strings;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.lotacao.LotacaoRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Lotação")
@Table(name = "lotacao")
public class Lotacao extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@Column(name = "descricao_completa")
	private String descricaoCompleta;

	@Column(name = "nivel")
	private Integer nivel;

	@Column(name = "efetivo")
	private Integer efetivo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_centro_custo")
	private CentroCusto centroCusto;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_conta")
	private TipoContaLotacaoEnum tipoConta;

	@Column(name = "numero_conta")
	private Integer numeroConta;

	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private TipoLotacaoEnum tipo;

	@Column(name = "vigencia_inicial")
	private Instant vigenciaInicial;

	@Column(name = "vigencia_final")
	private Instant vigenciaFinal;

	@Column
	private Boolean excluida;

//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinTable(name = "lotacao_cargo", joinColumns = @JoinColumn(name = "lotacao_id"), inverseJoinColumns = @JoinColumn(name = "cargo_id"))
//	private Set<Cargo> cargos = new HashSet<>();
//
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinTable(name = "lotacao_funcao", joinColumns = @JoinColumn(name = "lotacao_id"), inverseJoinColumns = @JoinColumn(name = "funcao_id"))
//	private Set<Funcao> funcoes = new HashSet<>();

	public Lotacao() {

	}

	public Lotacao(Long id) {
		this.id = id;
	}

	public Lotacao(LotacaoRequest lotacaoRequest) {
		this.setDescricao(lotacaoRequest.getDescricao());
		this.setDescricaoCompleta(lotacaoRequest.getDescricaoCompleta());
		this.setNivel(lotacaoRequest.getNivel());
		this.setEfetivo(lotacaoRequest.getEfetivo());
		this.setExcluida(false);

		if (Utils.checkStr(lotacaoRequest.getTipoConta()))
			this.setTipoConta(TipoContaLotacaoEnum.getEnumByString(lotacaoRequest.getTipoConta()));
		if (Strings.isNotEmpty(lotacaoRequest.getTipo()))
			this.setTipo(TipoLotacaoEnum.getEnumByString(lotacaoRequest.getTipo()));
		this.setVigenciaInicial(lotacaoRequest.getVigenciaInicial());
		this.setVigenciaFinal(lotacaoRequest.getVigenciaFinal());
		this.setNumeroConta(lotacaoRequest.getNumeroConta());
	}

//	public Set<Cargo> getCargos() {
//		return cargos;
//	}
//
//	public void setCargos(Set<Cargo> cargos) {
//		this.cargos = cargos;
//	}
//
//	public Set<Funcao> getFuncoes() {
//		return funcoes;
//	}
//
//	public void setFuncoes(Set<Funcao> funcoes) {
//		this.funcoes = funcoes;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricaoCompleta() {
		return descricaoCompleta;
	}

	public void setDescricaoCompleta(String descricaoCompleta) {
		this.descricaoCompleta = descricaoCompleta;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Integer getEfetivo() {
		return efetivo;
	}

	public void setEfetivo(Integer efetivo) {
		this.efetivo = efetivo;
	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public TipoContaLotacaoEnum getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoContaLotacaoEnum tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	public TipoLotacaoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoLotacaoEnum tipo) {
		this.tipo = tipo;
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

	public boolean isExcluida() {
		return excluida;
	}

	public void setExcluida(boolean excluida) {
		this.excluida = excluida;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}