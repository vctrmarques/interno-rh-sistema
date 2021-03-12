package com.rhlinkcon.payload.recadastramento;

import java.util.Objects;

import com.rhlinkcon.model.RecadastramentoDados;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.payload.nacionalidade.NacionalidadeResponse;

public class RecadastramentoDadosResponse {

	private Long id;
	
	private MunicipioResponse municipio;
	
	private NacionalidadeResponse nacionalidade;
	
	private String nome;

	private String nomeMae;
	
	private String nomePai;
	
	private String genero;
	
	private String estadoCivil;
	
	private String racaCor;
	
	private String tipoSanguineo;
	
	private String grauInstrucao;
	
	public RecadastramentoDadosResponse(RecadastramentoDados obj) {
		
		setId(obj.getId());
		
		if(Objects.nonNull(obj.getMunicipio()))
			setMunicipio(new MunicipioResponse(obj.getMunicipio()));
		
		if(Objects.nonNull(obj.getNacionalidade()))
			setNacionalidade(new NacionalidadeResponse(obj.getNacionalidade()));
		
		setNome(obj.getNome());
		setNomeMae(obj.getNomeMae());
		setNomePai(obj.getNomePai());
		
		if(Objects.nonNull(obj.getGenero()))
			setGenero(obj.getGenero().name());
		
		if(Objects.nonNull(obj.getEstadoCivil()))
			setEstadoCivil(obj.getEstadoCivil().name());
		
		if(Objects.nonNull(obj.getRacaCor()))
			setRacaCor(obj.getRacaCor().name());
		
		setTipoSanguineo(obj.getTipoSanguineo());
		
		if(Objects.nonNull(obj.getGrauInstrucao()))
			setGrauInstrucao(obj.getGrauInstrucao().name());
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MunicipioResponse getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioResponse municipio) {
		this.municipio = municipio;
	}

	public NacionalidadeResponse getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(NacionalidadeResponse nacionalidade) {
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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getRacaCor() {
		return racaCor;
	}

	public void setRacaCor(String racaCor) {
		this.racaCor = racaCor;
	}

	public String getTipoSanguineo() {
		return tipoSanguineo;
	}

	public void setTipoSanguineo(String tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public String getGrauInstrucao() {
		return grauInstrucao;
	}

	public void setGrauInstrucao(String grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

}
