package com.rhlinkcon.payload.lotacao;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.centroCusto.CentroCustoResponse;
import com.rhlinkcon.payload.definicaoOrganicoLotacaoCargo.DefinicaoOrganicoLotacaoCargoResponse;
import com.rhlinkcon.payload.definicaoOrganicoLotacaoFuncao.DefinicaoOrganicoLotacaoFuncaoResponse;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LotacaoResponse extends DadoCadastralResponse {

	private Long id;

	private String descricao;

	private CentroCustoResponse centroCusto;

	private String descricaoCompleta;

	private Integer nivel;

	private Integer efetivo;

	private String tipoConta;

	private Integer numeroConta;

	private String tipo;

	private Instant vigenciaInicial;

	private Instant vigenciaFinal;
	
	private List<DefinicaoOrganicoLotacaoCargoResponse> cargos;
	
	private List<DefinicaoOrganicoLotacaoFuncaoResponse> funcoes;

	public LotacaoResponse() {

	}

	public LotacaoResponse(Lotacao lotacao) {
		setLotacao(lotacao);
	}
	
	public LotacaoResponse(Lotacao lotacao, Projecao projecao) {
		this.setId(lotacao.getId());
		this.setDescricao(lotacao.getDescricao());	
		if (projecao.equals(Projecao.COMPLETA)) {
			this.setVigenciaInicial(lotacao.getVigenciaInicial());
			this.setVigenciaFinal(lotacao.getVigenciaFinal());
			
		}		
	}	
	
	public LotacaoResponse(Lotacao lotacao, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setLotacao(lotacao);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setLotacao(Lotacao lotacao) {
		this.setId(lotacao.getId());
		this.setDescricao(lotacao.getDescricao());
		this.setDescricaoCompleta(lotacao.getDescricaoCompleta());
		this.setNivel(lotacao.getNivel());
		this.setEfetivo(lotacao.getEfetivo());

		if (lotacao.getTipoConta() != null)
			if (Utils.checkStr(lotacao.getTipoConta().getLabel()))
				this.setTipoConta(lotacao.getTipoConta().getLabel());
		
		if(lotacao.getCentroCusto() != null)
			this.setCentroCusto(new CentroCustoResponse(lotacao.getCentroCusto()));
			
		if(Objects.nonNull(lotacao.getTipo()))
			this.setTipo(lotacao.getTipo().getLabel());
		this.setVigenciaInicial(lotacao.getVigenciaInicial());
		this.setVigenciaFinal(lotacao.getVigenciaFinal());
		this.setNumeroConta(lotacao.getNumeroConta());
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

	public CentroCustoResponse getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCustoResponse centroCusto) {
		this.centroCusto = centroCusto;
	}

	public String getDescricaoCompleta() {
		return descricaoCompleta;
	}

	public void setDescricaoCompleta(String descricaoCompleta) {
		this.descricaoCompleta = descricaoCompleta;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Integer getEfetivo() {
		return efetivo;
	}

	public void setEfetivo(Integer efetivo) {
		this.efetivo = efetivo;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Instant getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Instant vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public Instant getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(Instant vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public List<DefinicaoOrganicoLotacaoCargoResponse> getCargos() {
		return cargos;
	}

	public void setCargos(List<DefinicaoOrganicoLotacaoCargoResponse> cargos) {
		this.cargos = cargos;
	}

	public List<DefinicaoOrganicoLotacaoFuncaoResponse> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<DefinicaoOrganicoLotacaoFuncaoResponse> funcoes) {
		this.funcoes = funcoes;
	}
	
	

}
