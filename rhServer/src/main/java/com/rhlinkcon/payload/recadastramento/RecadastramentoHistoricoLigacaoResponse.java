package com.rhlinkcon.payload.recadastramento;

import com.rhlinkcon.model.RecadastramentoHistoricoLigacao;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.util.Projecao;

public class RecadastramentoHistoricoLigacaoResponse extends DadoCadastralResponse {

	private Long id;
	
	private FuncionarioResponse funcionario;
	
	private String observacao;
	
	public RecadastramentoHistoricoLigacaoResponse(RecadastramentoHistoricoLigacao obj) {
		setId(obj.getId());
		setFuncionario(new FuncionarioResponse(obj.getFuncionario(), Projecao.BASICA));
		setObservacao(obj.getObservacao());
		
		setCriadoEm(obj.getCreatedAt());
		setAlteradoEm(obj.getUpdatedAt());
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
