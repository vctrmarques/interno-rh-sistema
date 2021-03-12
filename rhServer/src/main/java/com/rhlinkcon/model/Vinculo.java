package com.rhlinkcon.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.vinculo.VinculoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Vínculo")
@Table(name = "vinculo")
public class Vinculo extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6711572737571157313L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "descricao", unique = true)
	private String descricao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vinculo_apos_id")
	private Vinculo vinculoApos;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "grupo")
	private GrupoVinculoEnum grupo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_sefip_id")
	private Sefip categoriaSefip;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ocorrencia_sefip_id")
	private Sefip ocorrenciaSefip;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_contrato_id")
	private TipoContrato tipoContrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_esocial_id")
	private Esocial categoriaEsocial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "situacao_inicial_afastamento_id")
	private SituacaoFuncional situacaoInicial;

	@Column(name = "data_inicial")
	private Date dataInicial;

	@Column(name = "data_final")
	private Date dataFinal;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "vinculo_verba", joinColumns = @JoinColumn(name = "vinculo_id"), inverseJoinColumns = @JoinColumn(name = "verba_id"))
	private Set<Verba> verbas = new HashSet<Verba>();

	public Vinculo() {
	}

	public Vinculo(Long id) {
		this.id = id;
	}
	
	public Vinculo(VinculoRequest vinculoRequest) {
		this.setDescricao(vinculoRequest.getDescricao());
		this.setGrupo(GrupoVinculoEnum.valueOf(vinculoRequest.getGrupo()));
		this.setDataFinal(vinculoRequest.getDataFinal());
		this.setDataInicial(vinculoRequest.getDataInicial());
	}

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

	public Vinculo getVinculoApos() {
		return vinculoApos;
	}

	public void setVinculoApos(Vinculo vinculoApos) {
		this.vinculoApos = vinculoApos;
	}

	public GrupoVinculoEnum getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoVinculoEnum grupo) {
		this.grupo = grupo;
	}

	public Sefip getCategoriaSefip() {
		return categoriaSefip;
	}

	public void setCategoriaSefip(Sefip categoriaSefip) {
		this.categoriaSefip = categoriaSefip;
	}

	public Sefip getOcorrenciaSefip() {
		return ocorrenciaSefip;
	}

	public void setOcorrenciaSefip(Sefip ocorrenciaSefip) {
		this.ocorrenciaSefip = ocorrenciaSefip;
	}

	public TipoContrato getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(TipoContrato tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public Esocial getCategoriaEsocial() {
		return categoriaEsocial;
	}

	public void setCategoriaEsocial(Esocial categoriaEsocial) {
		this.categoriaEsocial = categoriaEsocial;
	}

	public SituacaoFuncional getSituacaoInicial() {
		return situacaoInicial;
	}

	public void setSituacaoInicial(SituacaoFuncional situacaoInicial) {
		this.situacaoInicial = situacaoInicial;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Set<Verba> getVerbas() {
		return verbas;
	}

	public void setVerbas(Set<Verba> verbas) {
		this.verbas = verbas;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
