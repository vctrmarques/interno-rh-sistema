package com.rhlinkcon.payload.situacaoFuncional;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.SituacaoFuncional;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.causaAfastamento.CausaAfastamentoResponse;
import com.rhlinkcon.payload.motivoAfastamento.MotivoAfastamentoResponse;
import com.rhlinkcon.payload.motivoDesligamento.MotivoDesligamentoResponse;
import com.rhlinkcon.util.Projecao;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SituacaoFuncionalResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;

	private Integer raisSituacao;

	private Integer rais;

	private String tipo;

	private String modalidade;

	private Integer qtdDias;

	private MotivoAfastamentoResponse motivoAfastamento;

	private MotivoDesligamentoResponse motivoDesligamento;

	private CausaAfastamentoResponse causaAfastamento;

	private boolean entraFolha;

	public SituacaoFuncionalResponse(SituacaoFuncional situacaoFuncional) {
		setAfastamento(situacaoFuncional);
	}

	public SituacaoFuncionalResponse(SituacaoFuncional situacaoFuncional, Instant criadoEm, String criadoPor,
			Instant alteradoEm, String alteradoPor) {
		setAfastamento(situacaoFuncional);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	public SituacaoFuncionalResponse(SituacaoFuncional situacaoFuncional, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(null)) {
			this.setId(situacaoFuncional.getId());
			this.setCodigo(situacaoFuncional.getCodigo());
			this.setDescricao(situacaoFuncional.getDescricao());
			this.setLabel(situacaoFuncional.getDescricao());
			this.setRaisSituacao(situacaoFuncional.getRaisSituacao());
			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
				this.setRais(situacaoFuncional.getRais());
				this.entraFolha = situacaoFuncional.isEntraFolha();

				if (situacaoFuncional.getMotivoAfastamento() != null)
					this.setMotivoAfastamento(new MotivoAfastamentoResponse(situacaoFuncional.getMotivoAfastamento()));

				if (situacaoFuncional.getMotivoDesligamento() != null)
					this.setMotivoDesligamento(
							new MotivoDesligamentoResponse(situacaoFuncional.getMotivoDesligamento()));

				if (situacaoFuncional.getCausaAfastamento() != null)
					this.setCausaAfastamento(new CausaAfastamentoResponse(situacaoFuncional.getCausaAfastamento()));

				if (Objects.nonNull(situacaoFuncional.getTipo()))
					this.setTipo(situacaoFuncional.getTipo().getLabel());

				if (Objects.nonNull(situacaoFuncional.getModalidade()))
					this.setModalidade(situacaoFuncional.getModalidade().getLabel());

				this.setQtdDias(situacaoFuncional.getQtdDias());
			}
		}
	}

	private void setAfastamento(SituacaoFuncional situacaoFuncional) {
		this.setId(situacaoFuncional.getId());
		this.setCodigo(situacaoFuncional.getCodigo());
		this.setDescricao(situacaoFuncional.getDescricao());
		this.setLabel(situacaoFuncional.getDescricao());
		this.setRaisSituacao(situacaoFuncional.getRaisSituacao());
		this.setRais(situacaoFuncional.getRais());
		this.entraFolha = situacaoFuncional.isEntraFolha();

		if (situacaoFuncional.getMotivoAfastamento() != null)
			this.setMotivoAfastamento(new MotivoAfastamentoResponse(situacaoFuncional.getMotivoAfastamento()));

		if (situacaoFuncional.getMotivoDesligamento() != null)
			this.setMotivoDesligamento(new MotivoDesligamentoResponse(situacaoFuncional.getMotivoDesligamento()));

		if (situacaoFuncional.getCausaAfastamento() != null)
			this.setCausaAfastamento(new CausaAfastamentoResponse(situacaoFuncional.getCausaAfastamento()));

		if (Objects.nonNull(situacaoFuncional.getTipo()))
			this.setTipo(situacaoFuncional.getTipo().getLabel());

		if (Objects.nonNull(situacaoFuncional.getModalidade()))
			this.setModalidade(situacaoFuncional.getModalidade().getLabel());

		this.setQtdDias(situacaoFuncional.getQtdDias());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getRaisSituacao() {
		return raisSituacao;
	}

	public void setRaisSituacao(Integer raisSituacao) {
		this.raisSituacao = raisSituacao;
	}

	public Integer getRais() {
		return rais;
	}

	public void setRais(Integer rais) {
		this.rais = rais;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public Integer getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}

	public MotivoAfastamentoResponse getMotivoAfastamento() {
		return motivoAfastamento;
	}

	public void setMotivoAfastamento(MotivoAfastamentoResponse motivoAfastamento) {
		this.motivoAfastamento = motivoAfastamento;
	}

	public MotivoDesligamentoResponse getMotivoDesligamento() {
		return motivoDesligamento;
	}

	public void setMotivoDesligamento(MotivoDesligamentoResponse motivoDesligamento) {
		this.motivoDesligamento = motivoDesligamento;
	}

	public CausaAfastamentoResponse getCausaAfastamento() {
		return causaAfastamento;
	}

	public void setCausaAfastamento(CausaAfastamentoResponse causaAfastamento) {
		this.causaAfastamento = causaAfastamento;
	}

	public boolean isEntraFolha() {
		return entraFolha;
	}

	public void setEntraFolha(boolean entraFolha) {
		this.entraFolha = entraFolha;
	}

}
