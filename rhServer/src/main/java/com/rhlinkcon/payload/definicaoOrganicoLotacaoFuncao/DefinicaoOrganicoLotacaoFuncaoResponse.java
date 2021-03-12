package com.rhlinkcon.payload.definicaoOrganicoLotacaoFuncao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.DefinicaoOrganicoLotacaoFuncao;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.funcao.FuncaoResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefinicaoOrganicoLotacaoFuncaoResponse{

	private Long empresaFilialId;
	
	private Long lotacaoId;
	
	private Long funcaoId;
	
	private String nome;
	
	private Long quantPermitido;

	public DefinicaoOrganicoLotacaoFuncaoResponse(DefinicaoOrganicoLotacaoFuncao definicaoOrganicoLotacaoFuncao) {
		this.setEmpresaFilialId(definicaoOrganicoLotacaoFuncao.getEmpresaFilialId());
		this.setLotacaoId(definicaoOrganicoLotacaoFuncao.getLotacaoId());
		this.setFuncaoId(definicaoOrganicoLotacaoFuncao.getFuncaoId());
		this.setQuantPermitido(definicaoOrganicoLotacaoFuncao.getQuantPermitido());
	}

	public Long getEmpresaFilialId() {
		return empresaFilialId;
	}

	public void setEmpresaFilialId(Long empresaFilialId) {
		this.empresaFilialId = empresaFilialId;
	}

	public Long getLotacaoId() {
		return lotacaoId;
	}

	public void setLotacaoId(Long lotacaoId) {
		this.lotacaoId = lotacaoId;
	}

	public Long getFuncaoId() {
		return funcaoId;
	}

	public void setFuncaoId(Long funcaoId) {
		this.funcaoId = funcaoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getQuantPermitido() {
		return quantPermitido;
	}

	public void setQuantPermitido(Long quantPermitido) {
		this.quantPermitido = quantPermitido;
	}

}
