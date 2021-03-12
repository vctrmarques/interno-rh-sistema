package com.rhlinkcon.model;

import java.io.Serializable;
import java.util.Objects;

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

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.PersistentId;
import com.rhlinkcon.payload.recadastramento.RecadastramentoDadosRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Recadastramento Dados")
@Table(name = "recadastramento_dados")
public class RecadastramentoDados implements Serializable, PersistentId {

	private static final long serialVersionUID = -5398475006908715042L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio_id", nullable = true)
	private Municipio municipio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nacionalidade_id", nullable = true)
	private Nacionalidade nacionalidade;

	private String nome;

	@Column(name = "nome_mae")
	private String nomeMae;

	@Column(name = "nome_pai")
	private String nomePai;

	@Enumerated(EnumType.STRING)
	private GeneroEnum genero;

	@Column(name = "estado_civil")
	@Enumerated(EnumType.STRING)
	private EstadoCivilEnum estadoCivil;

	@Column(name = "raca_cor")
	@Enumerated(EnumType.STRING)
	private CorPeleEnum racaCor;

	@Column(name = "tipo_sanguineo")
	private String tipoSanguineo;

	@Column(name = "grau_instrucao")
	@Enumerated(EnumType.STRING)
	private GrauInstrucaoEnum grauInstrucao;

	public RecadastramentoDados() {
	}

	public RecadastramentoDados(RecadastramentoDadosRequest request) {
		setId(request.getId());
		if (Objects.nonNull(request.getMunicipioId()))
			setMunicipio(new Municipio(request.getMunicipioId()));

		if (Objects.nonNull(request.getNacionalidadeId()))
			setNacionalidade(new Nacionalidade(request.getNacionalidadeId()));

		setNome(request.getNome());
		setNomeMae(request.getNomeMae());
		setNomePai(request.getNomePai());

		if (Objects.nonNull(request.getGenero()))
			setGenero(GeneroEnum.getEnumByString(request.getGenero()));

		if (Objects.nonNull(request.getEstadoCivil()))
			setEstadoCivil(EstadoCivilEnum.getEnumByString(request.getEstadoCivil()));

		if (Objects.nonNull(request.getRacaCor()))
			setRacaCor(CorPeleEnum.getEnumByString(request.getRacaCor()));

		setTipoSanguineo(request.getTipoSanguineo());

		if (Objects.nonNull(request.getGrauInstrucao()))
			setGrauInstrucao(GrauInstrucaoEnum.getEnumByString(request.getGrauInstrucao()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public GeneroEnum getGenero() {
		return genero;
	}

	public void setGenero(GeneroEnum genero) {
		this.genero = genero;
	}

	public EstadoCivilEnum getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public CorPeleEnum getRacaCor() {
		return racaCor;
	}

	public void setRacaCor(CorPeleEnum racaCor) {
		this.racaCor = racaCor;
	}

	public String getTipoSanguineo() {
		return tipoSanguineo;
	}

	public void setTipoSanguineo(String tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public GrauInstrucaoEnum getGrauInstrucao() {
		return grauInstrucao;
	}

	public void setGrauInstrucao(GrauInstrucaoEnum grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
