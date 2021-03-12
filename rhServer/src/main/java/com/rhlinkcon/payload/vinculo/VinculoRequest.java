package com.rhlinkcon.payload.vinculo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.payload.esocial.EsocialRequest;
import com.rhlinkcon.payload.sefip.SefipRequest;
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalRequest;
import com.rhlinkcon.payload.tipoContrato.TipoContratoRequest;

public class VinculoRequest {

	private Long id;

	@NotNull
	@NotBlank
	private String descricao;

	private VinculoRequest vinculoApos;

	@NotNull
	private String grupo;

	private SefipRequest categoriaSefip;

	private SefipRequest ocorrenciaSefip;

	private TipoContratoRequest tipoContrato;

	private EsocialRequest categoriaEsocial;

	private SituacaoFuncionalRequest situacaoInicial;

	private Date dataInicial;

	private Date dataFinal;
	
	private List<Long> verbaIds;

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public VinculoRequest getVinculoApos() {
		return vinculoApos;
	}

	public SefipRequest getCategoriaSefip() {
		return categoriaSefip;
	}

	public SefipRequest getOcorrenciaSefip() {
		return ocorrenciaSefip;
	}

	public TipoContratoRequest getTipoContrato() {
		return tipoContrato;
	}

	public SituacaoFuncionalRequest getSituacaoInicial() {
		return situacaoInicial;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setVinculoApos(VinculoRequest vinculoApos) {
		this.vinculoApos = vinculoApos;
	}

	public void setCategoriaSefip(SefipRequest categoriaSefip) {
		this.categoriaSefip = categoriaSefip;
	}

	public void setOcorrenciaSefip(SefipRequest ocorrenciaSefip) {
		this.ocorrenciaSefip = ocorrenciaSefip;
	}

	public void setTipoContrato(TipoContratoRequest tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public void setSituacaoInicial(SituacaoFuncionalRequest situacaoInicial) {
		this.situacaoInicial = situacaoInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public EsocialRequest getCategoriaEsocial() {
		return categoriaEsocial;
	}

	public void setCategoriaEsocial(EsocialRequest categoriaEsocial) {
		this.categoriaEsocial = categoriaEsocial;
	}

	public List<Long> getVerbaIds() {
		return verbaIds;
	}

	public void setVerbaIds(List<Long> verbaIds) {
		this.verbaIds = verbaIds;
	}

}
