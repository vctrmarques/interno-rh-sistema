package com.rhlinkcon.payload.equipamentoProtecaoColetiva;

import java.time.Instant;

//import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.EquipamentoProtecaoColetiva;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class EquipamentoProtecaoColetivaResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String tipoProtecao;

	private String descricao;

	private Instant validade;

	private String certificado;

	private String referencia;

	private Integer minimo;

	private Integer livre;

	private Integer atual;

	public EquipamentoProtecaoColetivaResponse(EquipamentoProtecaoColetiva equipamentoProtecaoColetiva) {
		setEquipamentoProtecaoColetiva(equipamentoProtecaoColetiva);
	}

	public EquipamentoProtecaoColetivaResponse(EquipamentoProtecaoColetiva equipamentoProtecaoColetiva, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setEquipamentoProtecaoColetiva(equipamentoProtecaoColetiva);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setEquipamentoProtecaoColetiva(EquipamentoProtecaoColetiva equipamentoProtecaoColetiva) {
		this.setId(equipamentoProtecaoColetiva.getId());
		this.setCodigo(equipamentoProtecaoColetiva.getCodigo());
		this.setTipoProtecao(equipamentoProtecaoColetiva.getTipoProtecao());
		this.setDescricao(equipamentoProtecaoColetiva.getDescricao());
		this.setValidade(equipamentoProtecaoColetiva.getValidade());
		this.setCertificado(equipamentoProtecaoColetiva.getCertificado());
		this.setReferencia(equipamentoProtecaoColetiva.getReferencia());
		this.setMinimo(equipamentoProtecaoColetiva.getMinimo());
		this.setLivre(equipamentoProtecaoColetiva.getLivre());
		this.setAtual(equipamentoProtecaoColetiva.getAtual());
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

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

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

}
