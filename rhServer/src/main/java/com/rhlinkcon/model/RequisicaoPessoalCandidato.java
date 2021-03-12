package com.rhlinkcon.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalCandidatoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Requisição Pessoal Candidato")
@Table(name = "requisicao_pessoal_candidato")
public class RequisicaoPessoalCandidato extends UserDateAudit {

	private static final long serialVersionUID = 7255318546222043262L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nome;

	@Column
	private String comentario;

	@Column(name = "comentario_curriculo")
	private String comentarioCurriculo;

	@NotNull
	@Enumerated(EnumType.STRING)
	private RequisicaoPessoalAnaliseEnum situacao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_anexo")
	private Anexo anexo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_req_pessoal")
	private RequisicaoPessoal requisicaoPessoal;

	public RequisicaoPessoalCandidato() {
		situacao = RequisicaoPessoalAnaliseEnum.PENDENTE;
	}

	public RequisicaoPessoalCandidato(RequisicaoPessoalCandidatoRequest request) {
		this.id = request.getId();
		this.nome = request.getNome();
		this.comentario = request.getComentario();
		this.comentarioCurriculo = request.getComentarioCurriculo();
		this.situacao = RequisicaoPessoalAnaliseEnum.getEnumByString(request.getSituacao());
		if (Objects.nonNull(request.getAnexoId())) {
			this.anexo = new Anexo(request.getAnexoId());
		}
		if (Objects.nonNull(request.getRequisicaoPessoalId())) {
			this.requisicaoPessoal = new RequisicaoPessoal(request.getRequisicaoPessoalId());
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

	public Anexo getAnexo() {
		return anexo;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	public RequisicaoPessoal getRequisicaoPessoal() {
		return requisicaoPessoal;
	}

	public void setRequisicaoPessoal(RequisicaoPessoal requisicaoPessoal) {
		this.requisicaoPessoal = requisicaoPessoal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
		result = prime * result + ((comentarioCurriculo == null) ? 0 : comentarioCurriculo.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequisicaoPessoalCandidato other = (RequisicaoPessoalCandidato) obj;
		if (comentario == null) {
			if (other.comentario != null)
				return false;
		} else if (!comentario.equals(other.comentario))
			return false;
		if (comentarioCurriculo == null) {
			if (other.comentarioCurriculo != null)
				return false;
		} else if (!comentarioCurriculo.equals(other.comentarioCurriculo))
			return false;
		if (situacao != other.situacao)
			return false;
		return true;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
