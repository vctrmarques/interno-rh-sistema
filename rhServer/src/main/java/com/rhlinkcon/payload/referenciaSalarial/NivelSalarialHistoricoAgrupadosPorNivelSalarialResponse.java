package com.rhlinkcon.payload.referenciaSalarial;

public class NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse {

	private Long nivelSalarialId;

	private String nivelSalarialDescricao;

	private String nivelSalarialCodigo;

	private Long totalAjustes;

	public NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse() {
	}

	public NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse(Long nivelSalarialId, String nivelSalarialDescricao,
			String nivelSalarialCodigo, Long totalAjustes) {
		this.nivelSalarialId = nivelSalarialId;
		this.nivelSalarialDescricao = nivelSalarialDescricao;
		this.nivelSalarialCodigo = nivelSalarialCodigo;
		this.totalAjustes = totalAjustes;
	}

	public Long getNivelSalarialId() {
		return nivelSalarialId;
	}

	public void setNivelSalarialId(Long nivelSalarialId) {
		this.nivelSalarialId = nivelSalarialId;
	}

	public String getNivelSalarialDescricao() {
		return nivelSalarialDescricao;
	}

	public void setNivelSalarialDescricao(String nivelSalarialDescricao) {
		this.nivelSalarialDescricao = nivelSalarialDescricao;
	}

	public String getNivelSalarialCodigo() {
		return nivelSalarialCodigo;
	}

	public void setNivelSalarialCodigo(String nivelSalarialCodigo) {
		this.nivelSalarialCodigo = nivelSalarialCodigo;
	}

	public Long getTotalAjustes() {
		return totalAjustes;
	}

	public void setTotalAjustes(Long totalAjustes) {
		this.totalAjustes = totalAjustes;
	}

}
