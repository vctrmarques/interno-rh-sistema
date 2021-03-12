package com.rhlinkcon.payload.lotacao;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.payload.cargo.CargoResponse;
import com.rhlinkcon.payload.definicaoOrganicoLotacaoCargo.DefinicaoOrganicoLotacaoCargoRequest;
import com.rhlinkcon.payload.definicaoOrganicoLotacaoFuncao.DefinicaoOrganicoLotacaoFuncaoRequest;
import com.rhlinkcon.payload.funcao.FuncaoResponse;

public class LotacaoRequest {

	private Long id;

	@NotNull
	private String descricao;

	@NotNull
	private String descricaoCompleta;
	
	private Integer nivel;
	
	private Integer efetivo;
	
	private String tipoConta;
	
	private Integer numeroConta;
	
	private String tipo;
	
	@NotNull
	private Instant vigenciaInicial;
	
	private Instant vigenciaFinal;

	private Long centroCustoId;
	
	private List<DefinicaoOrganicoLotacaoCargoRequest> cargos;
	
	private List<DefinicaoOrganicoLotacaoFuncaoRequest> funcoes;	

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

	public Long getCentroCustoId() {
		return centroCustoId;
	}

	public void setCentroCustoId(Long centroCustoId) {
		this.centroCustoId = centroCustoId;
	}

	public List<DefinicaoOrganicoLotacaoCargoRequest> getCargos() {
		return cargos;
	}

	public void setCargos(List<DefinicaoOrganicoLotacaoCargoRequest> cargos) {
		this.cargos = cargos;
	}

	public List<DefinicaoOrganicoLotacaoFuncaoRequest> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<DefinicaoOrganicoLotacaoFuncaoRequest> funcoes) {
		this.funcoes = funcoes;
	}

	
}