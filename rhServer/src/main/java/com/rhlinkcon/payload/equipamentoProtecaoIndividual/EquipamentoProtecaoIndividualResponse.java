package com.rhlinkcon.payload.equipamentoProtecaoIndividual;

import java.time.Instant;

//import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.EquipamentoProtecaoIndividual;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class EquipamentoProtecaoIndividualResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String tipoProtecao;

	private String descricao;

	private Instant validade;

	private String certificado;

	private Integer minimo;

	private Integer livre;

	private Integer atual;

	public Integer getMinimo() {
		return minimo;
	}

	public void setMinimo(Integer minimo) {
		this.minimo = minimo;
	}

	public Integer getLivre() {
		return livre;
	}

	public void setLivre(Integer livre) {
		this.livre = livre;
	}

	public Integer getAtual() {
		return atual;
	}

	public void setAtual(Integer atual) {
		this.atual = atual;
	}

	public EquipamentoProtecaoIndividualResponse(EquipamentoProtecaoIndividual equipamentoProtecaoIndividual) {
		setEquipamentoProtecaoIndiviual(equipamentoProtecaoIndividual);
	}

	public EquipamentoProtecaoIndividualResponse(EquipamentoProtecaoIndividual equipamentoProtecaoIndividual, Instant criadoEm, String criadoPor,
			Instant alteradoEm, String alteradoPor) {
		setEquipamentoProtecaoIndiviual(equipamentoProtecaoIndividual);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setEquipamentoProtecaoIndiviual(EquipamentoProtecaoIndividual equipamentoProtecaoIndividual) {
		this.setId(equipamentoProtecaoIndividual.getId());
		this.setCodigo(equipamentoProtecaoIndividual.getCodigo());
		this.setTipoProtecao(equipamentoProtecaoIndividual.getTipoProtecao());
		this.setDescricao(equipamentoProtecaoIndividual.getDescricao());
		this.setValidade(equipamentoProtecaoIndividual.getValidade());
		this.setCertificado(equipamentoProtecaoIndividual.getCertificado());
		this.setMinimo(equipamentoProtecaoIndividual.getMinimo());
		this.setLivre(equipamentoProtecaoIndividual.getLivre());
		this.setAtual(equipamentoProtecaoIndividual.getAtual());
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

	public String getTipoProtecao() {
		return tipoProtecao;
	}

	public void setTipoProtecao(String tipoProtecao) {
		this.tipoProtecao = tipoProtecao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Instant getValidade() {
		return validade;
	}

	public void setValidade(Instant validade) {
		this.validade = validade;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

}
