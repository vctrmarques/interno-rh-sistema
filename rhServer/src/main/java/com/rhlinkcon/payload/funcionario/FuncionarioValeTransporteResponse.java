package com.rhlinkcon.payload.funcionario;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.FuncionarioValeTransporte;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.repository.ValeTransporteRepository;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FuncionarioValeTransporteResponse extends DadoCadastralResponse {

	@Autowired
	private ValeTransporteRepository valeTransporteRepository;
	
	private Long id;

	private Long funcionarioId;

	private Integer quantidade;

	private Long valeTransporteId;
	
	private Double valor;
	
	private String codigo;
	
	public FuncionarioValeTransporteResponse(FuncionarioValeTransporte funcionarioValeTransporte) {
		this.id = funcionarioValeTransporte.getId();
		this.quantidade = funcionarioValeTransporte.getQuantidade();
		this.funcionarioId = funcionarioValeTransporte.getFuncionario().getId();
		this.valeTransporteId = funcionarioValeTransporte.getValeTransporte().getId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Long getValeTransporteId() {
		return valeTransporteId;
	}

	public void setValeTransporteId(Long valeTransporteId) {
		this.valeTransporteId = valeTransporteId;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}
