package com.rhlinkcon.filtro;

import java.time.Instant;
import java.util.List;

public class FuncionarioFiltro {

	private String search;

	private Long filialId;

	private Long lotacaoId;

	boolean comExperiencia = false;

	private Instant dataAdmissao;

	private Boolean funcionarioVerbaAssociada;

	private List<Long> tipoSituacaoFuncionalIds;
	
	private List<Long> motivoAfastamentoIds;
	
	public FuncionarioFiltro() {
		
	}

	public Instant getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Instant dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Long getFilialId() {
		return filialId;
	}

	public void setFilialId(Long filialId) {
		this.filialId = filialId;
	}

	public boolean isComExperiencia() {
		return comExperiencia;
	}

	public void setComExperiencia(boolean comExperiencia) {
		this.comExperiencia = comExperiencia;
	}

	public Long getLotacaoId() {
		return lotacaoId;
	}

	public void setLotacaoId(Long lotacaoId) {
		this.lotacaoId = lotacaoId;
	}

	public List<Long> getTipoSituacaoFuncionalIds() {
		return tipoSituacaoFuncionalIds;
	}

	public void setTipoSituacaoFuncionalIds(List<Long> tipoSituacaoFuncionalIds) {
		this.tipoSituacaoFuncionalIds = tipoSituacaoFuncionalIds;
	}

	public Boolean getFuncionarioVerbaAssociada() {
		return funcionarioVerbaAssociada;
	}

	public void setFuncionarioVerbaAssociada(Boolean funcionarioVerbaAssociada) {
		this.funcionarioVerbaAssociada = funcionarioVerbaAssociada;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<Long> getMotivoAfastamentoIds() {
		return motivoAfastamentoIds;
	}

	public void setMotivoAfastamentoIds(List<Long> motivoAfastamentoIds) {
		this.motivoAfastamentoIds = motivoAfastamentoIds;
	}

}
