package com.rhlinkcon.payload.requisicaoPessoal;

public class RequisicaoPessoalCandidatoRequest {
	
	private Long id;
	
	private String nome;
	
	private String comentario;
	
	private String comentarioCurriculo;
	
	private String situacao;
	
	private Long anexoId;

	private Long requisicaoPessoalId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getComentarioCurriculo() {
		return comentarioCurriculo;
	}

	public void setComentarioCurriculo(String comentarioCurriculo) {
		this.comentarioCurriculo = comentarioCurriculo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Long getAnexoId() {
		return anexoId;
	}

	public void setAnexoId(Long anexoId) {
		this.anexoId = anexoId;
	}

	public Long getRequisicaoPessoalId() {
		return requisicaoPessoalId;
	}

	public void setRequisicaoPessoalId(Long requisicaoPessoalId) {
		this.requisicaoPessoalId = requisicaoPessoalId;
	}


}
