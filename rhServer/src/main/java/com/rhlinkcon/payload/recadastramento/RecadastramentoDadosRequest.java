package com.rhlinkcon.payload.recadastramento;

public class RecadastramentoDadosRequest {

	private Long id;
	
	private Long municipioId;
	
	private Long nacionalidadeId;
	
	private String nome;

	private String nomeMae;
	
	private String nomePai;
	
	private String genero;
	
	private String estadoCivil;
	
	private String racaCor;
	
	private String tipoSanguineo;
	
	private String grauInstrucao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
	}

	public Long getNacionalidadeId() {
		return nacionalidadeId;
	}

	public void setNacionalidadeId(Long nacionalidadeId) {
		this.nacionalidadeId = nacionalidadeId;
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
