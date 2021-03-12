package com.rhlinkcon.payload.vinculo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Vinculo;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.esocial.EsocialResponse;
import com.rhlinkcon.payload.sefip.SefipResponse;
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalResponse;
import com.rhlinkcon.payload.tipoContrato.TipoContratoResponse;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VinculoResponse extends DadoCadastralResponse {

	private Long id;

	private String descricao;

	private VinculoResponse vinculoApos;

	private String grupo;

	private SefipResponse categoriaSefip;

	private SefipResponse ocorrenciaSefip;

	private TipoContratoResponse tipoContrato;

	private EsocialResponse categoriaEsocial;

	private SituacaoFuncionalResponse situacaoInicial;

	private Date dataInicial;

	private Date dataFinal;

	private List<Long> verbaIds;

	public VinculoResponse() {
	}

	public VinculoResponse(Vinculo vinculo) {
		setVinculo(vinculo);
	}

	public VinculoResponse(Vinculo vinculo, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setVinculo(vinculo);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	public VinculoResponse(Vinculo vinculo, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(null)) {
			this.setId(vinculo.getId());
			this.setDescricao(vinculo.getDescricao());
			if(projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
				if (vinculo.getVinculoApos() != null)
					this.setVinculoApos(new VinculoResponse(vinculo.getVinculoApos()));

				if (Utils.checkStr(vinculo.getGrupo().getLabel()))
					this.setGrupo(vinculo.getGrupo().toString());

				if (vinculo.getCategoriaSefip() != null)
					this.setCategoriaSefip(new SefipResponse(vinculo.getCategoriaSefip()));

				if (vinculo.getOcorrenciaSefip() != null)
					this.setOcorrenciaSefip(new SefipResponse(vinculo.getOcorrenciaSefip()));

				if (vinculo.getCategoriaEsocial() != null)
					this.setCategoriaEsocial(new EsocialResponse(vinculo.getCategoriaEsocial()));

				if (vinculo.getSituacaoInicial() != null)
					this.setSituacaoInicial(new SituacaoFuncionalResponse(vinculo.getSituacaoInicial()));

				if (vinculo.getTipoContrato() != null)
					this.setTipoContrato(new TipoContratoResponse(vinculo.getTipoContrato()));

				if (Utils.checkSetList(vinculo.getVerbas())) {
					this.setVerbaIds(new ArrayList<Long>());
					vinculo.getVerbas().forEach(verba -> this.getVerbaIds().add(verba.getId()));
				}

				this.setDataInicial(vinculo.getDataInicial());
				this.setDataFinal(vinculo.getDataFinal());
			}
		}
	}

	private void setVinculo(Vinculo vinculo) {
		this.setId(vinculo.getId());
		this.setDescricao(vinculo.getDescricao());

		if (vinculo.getVinculoApos() != null)
			this.setVinculoApos(new VinculoResponse(vinculo.getVinculoApos()));

		if (Utils.checkStr(vinculo.getGrupo().getLabel()))
			this.setGrupo(vinculo.getGrupo().toString());

		if (vinculo.getCategoriaSefip() != null)
			this.setCategoriaSefip(new SefipResponse(vinculo.getCategoriaSefip()));

		if (vinculo.getOcorrenciaSefip() != null)
			this.setOcorrenciaSefip(new SefipResponse(vinculo.getOcorrenciaSefip()));

		if (vinculo.getCategoriaEsocial() != null)
			this.setCategoriaEsocial(new EsocialResponse(vinculo.getCategoriaEsocial()));

		if (vinculo.getSituacaoInicial() != null)
			this.setSituacaoInicial(new SituacaoFuncionalResponse(vinculo.getSituacaoInicial()));

		if (vinculo.getTipoContrato() != null)
			this.setTipoContrato(new TipoContratoResponse(vinculo.getTipoContrato()));

		if (Utils.checkSetList(vinculo.getVerbas())) {
			this.setVerbaIds(new ArrayList<Long>());
			vinculo.getVerbas().forEach(verba -> this.getVerbaIds().add(verba.getId()));
		}

		this.setDataInicial(vinculo.getDataInicial());
		this.setDataFinal(vinculo.getDataFinal());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public VinculoResponse getVinculoApos() {
		return vinculoApos;
	}

	public void setVinculoApos(VinculoResponse vinculoApos) {
		this.vinculoApos = vinculoApos;
	}

	public SefipResponse getCategoriaSefip() {
		return categoriaSefip;
	}

	public void setCategoriaSefip(SefipResponse categoriaSefip) {
		this.categoriaSefip = categoriaSefip;
	}

	public SefipResponse getOcorrenciaSefip() {
		return ocorrenciaSefip;
	}

	public void setOcorrenciaSefip(SefipResponse ocorrenciaSefip) {
		this.ocorrenciaSefip = ocorrenciaSefip;
	}

	public TipoContratoResponse getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(TipoContratoResponse tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public EsocialResponse getCategoriaEsocial() {
		return categoriaEsocial;
	}

	public void setCategoriaEsocial(EsocialResponse categoriaEsocial) {
		this.categoriaEsocial = categoriaEsocial;
	}

	public SituacaoFuncionalResponse getSituacaoInicial() {
		return situacaoInicial;
	}

	public void setSituacaoInicial(SituacaoFuncionalResponse situacaoInicial) {
		this.situacaoInicial = situacaoInicial;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
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

	public List<Long> getVerbaIds() {
		return verbaIds;
	}

	public void setVerbaIds(List<Long> verbaIds) {
		this.verbaIds = verbaIds;
	}

}
