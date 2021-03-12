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
import com.rhlinkcon.payload.definicaoOrganicoLotacaoCargo.DefinicaoOrganicoLotacaoCargoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Lotação Cargo")
@Table(name = "lotacao_cargo")
public class DefinicaoOrganicoLotacaoCargo {

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
	@JoinColumn(name = "cargo_id")
	private Long cargoId;

	@NotNull
	@JoinColumn(name = "quant_permitido")
	private Long quantPermitido;

	public DefinicaoOrganicoLotacaoCargo() {

	}

	public DefinicaoOrganicoLotacaoCargo(DefinicaoOrganicoLotacaoCargoRequest definicaoOrganicoLotacaoCargoRequest) {
		this.setEmpresaFilialId(definicaoOrganicoLotacaoCargoRequest.getEmpresaFilialId());
		this.setLotacaoId(definicaoOrganicoLotacaoCargoRequest.getLotacaoId());
		this.setCargoId(definicaoOrganicoLotacaoCargoRequest.getCargoId());
		this.setQuantPermitido(definicaoOrganicoLotacaoCargoRequest.getQuantPermitido());

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

	public Long getCargoId() {
		return cargoId;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	public Long getQuantPermitido() {
		return quantPermitido;
	}

	public void setQuantPermitido(Long quantPermitido) {
		this.quantPermitido = quantPermitido;
	}

}
