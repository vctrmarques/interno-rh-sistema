package com.rhlinkcon.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.payload.definicaoOrganicoLotacaoFuncao.DefinicaoOrganicoLotacaoFuncaoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Lotação Função")
@Table(name = "lotacao_funcao")
public class DefinicaoOrganicoLotacaoFuncao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@JoinColumn(name = "empresa_filial_id")
	private Long empresaFilialId;

	@NotNull
	@JoinColumn(name = "lotacao_id")
	private Long lotacaoId;

	@NotNull
	@JoinColumn(name = "funcao_id")
	private Long funcaoId;

	@NotNull
	@JoinColumn(name = "quant_permitido")
	private Long quantPermitido;

	public DefinicaoOrganicoLotacaoFuncao() {

	}

	public DefinicaoOrganicoLotacaoFuncao(DefinicaoOrganicoLotacaoFuncaoRequest definicaoOrganicoLotacaoFuncaoRequest) {
		this.setEmpresaFilialId(definicaoOrganicoLotacaoFuncaoRequest.getEmpresaFilialId());
		this.setLotacaoId(definicaoOrganicoLotacaoFuncaoRequest.getLotacaoId());
		this.setFuncaoId(definicaoOrganicoLotacaoFuncaoRequest.getFuncaoId());
		this.setQuantPermitido(definicaoOrganicoLotacaoFuncaoRequest.getQuantPermitido());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getQuantPermitido() {
		return quantPermitido;
	}

	public void setQuantPermitido(Long quantPermitido) {
		this.quantPermitido = quantPermitido;
	}

}
