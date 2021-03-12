package com.rhlinkcon.payload.tranferenciaFuncionario;

import java.util.Objects;

import com.rhlinkcon.model.TransferenciaFuncionario;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;

public class TransferenciaFuncionarioResponse extends DadoCadastralResponse {

	private Long id;

	private FuncionarioResponse funcionario;

	private EmpresaFilialResponse empresa;

	private EmpresaFilialResponse empresaAnterior;

	private LotacaoResponse lotacaoAnterior;

	private LotacaoResponse lotacao;

	public TransferenciaFuncionarioResponse(TransferenciaFuncionario tf) {
		this.funcionario = new FuncionarioResponse(tf.getFuncionario());
		if (Objects.nonNull(tf.getEmpresa()))
			this.empresa = new EmpresaFilialResponse(tf.getEmpresa());
		if (Objects.nonNull(tf.getLotacao()))
			this.lotacao = new LotacaoResponse(tf.getLotacao());
		if (Objects.nonNull(tf.getEmpresa()))
			this.empresaAnterior = new EmpresaFilialResponse(tf.getEmpresaAnterior());
		if (Objects.nonNull(tf.getLotacao()))
			this.lotacaoAnterior = new LotacaoResponse(tf.getLotacaoAnterior());
		this.setAlteradoEm(tf.getUpdatedAt());
		this.setCriadoEm(tf.getCreatedAt());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public EmpresaFilialResponse getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFilialResponse empresa) {
		this.empresa = empresa;
	}

	public LotacaoResponse getLotacao() {
		return lotacao;
	}

	public void setLotacao(LotacaoResponse lotacao) {
		this.lotacao = lotacao;
	}

	public EmpresaFilialResponse getEmpresaAnterior() {
		return empresaAnterior;
	}

	public void setEmpresaAnterior(EmpresaFilialResponse empresaAnterior) {
		this.empresaAnterior = empresaAnterior;
	}

	public LotacaoResponse getLotacaoAnterior() {
		return lotacaoAnterior;
	}

	public void setLotacaoAnterior(LotacaoResponse lotacaoAnterior) {
		this.lotacaoAnterior = lotacaoAnterior;
	}
}
