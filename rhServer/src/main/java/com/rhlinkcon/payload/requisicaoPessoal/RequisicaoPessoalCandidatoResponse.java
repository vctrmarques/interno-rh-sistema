package com.rhlinkcon.payload.requisicaoPessoal;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.RequisicaoPessoalAnaliseEnum;
import com.rhlinkcon.model.RequisicaoPessoalCandidato;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequisicaoPessoalCandidatoResponse extends DadoCadastralResponse {

	private Long id;

	private String nome;

	private String comentario;

	private String comentarioCurriculo;

	private RequisicaoPessoalAnaliseEnum situacao;

	private AnexoResponse anexo;
	
	private Long requisicaoPessoalId;

	public RequisicaoPessoalCandidatoResponse(RequisicaoPessoalCandidato candidato) {
		this.id = candidato.getId();
		this.nome = candidato.getNome();
		this.comentario = candidato.getComentario();
		this.comentarioCurriculo = candidato.getComentarioCurriculo();
		this.situacao = candidato.getSituacao();
		if (Objects.nonNull(candidato.getAnexo())) {
			this.anexo = new AnexoResponse(candidato.getAnexo());
		}
		if (Objects.nonNull(candidato.getRequisicaoPessoal())){
			this.setRequisicaoPessoalId(candidato.getRequisicaoPessoal().getId());
		}
	}

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

	public RequisicaoPessoalAnaliseEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(RequisicaoPessoalAnaliseEnum situacao) {
		this.situacao = situacao;
	}

	public AnexoResponse getAnexo() {
		return anexo;
	}

	public void setAnexo(AnexoResponse anexo) {
		this.anexo = anexo;
	}

	public Long getRequisicaoPessoalId() {
		return requisicaoPessoalId;
	}

	public void setRequisicaoPessoalId(Long requisicaoPessoal) {
		this.requisicaoPessoalId = requisicaoPessoal;
	}
}
