package com.rhlinkcon.payload.arquivoRemessaPagamento;

import java.time.LocalDate;
import java.util.List;

public class ArquivoRemessaRelatorioResponse {
	
	private String processamento;
	
	private String codigoSecretaria;
	
	private String empresaFilial;
	
	private LocalDate dataAtual;
	
	private String usuario;
	
	private String codigoConvenio;
	
	private String numeroRemessa;
	
	private LocalDate dataPagamento;
	
	private List<ArquivoRemessaRelatorioListaResponse> lista;
	
	private String totalServidores;
	
	private String totalPensaoAlim;
	
	private String total;
	
	private Double totalValorServidores;
	
	private Double totalValorPensaoAlim;
	
	private Double totalValor;

	public String getProcessamento() {
		return processamento;
	}

	public void setProcessamento(String processamento) {
		this.processamento = processamento;
	}

	public String getEmpresaFilial() {
		return empresaFilial;
	}

	public void setEmpresaFilial(String empresaFilial) {
		this.empresaFilial = empresaFilial;
	}

	public LocalDate getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(LocalDate dataAtual) {
		this.dataAtual = dataAtual;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCodigoConvenio() {
		return codigoConvenio;
	}

	public void setCodigoConvenio(String codigoConvenio) {
		this.codigoConvenio = codigoConvenio;
	}

	public String getNumeroRemessa() {
		return numeroRemessa;
	}

	public void setNumeroRemessa(String numeroRemessa) {
		this.numeroRemessa = numeroRemessa;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public List<ArquivoRemessaRelatorioListaResponse> getLista() {
		return lista;
	}

	public void setLista(List<ArquivoRemessaRelatorioListaResponse> lista) {
		this.lista = lista;
	}

	public String getTotalServidores() {
		return totalServidores;
	}

	public void setTotalServidores(String totalServidores) {
		this.totalServidores = totalServidores;
	}

	public String getTotalPensaoAlim() {
		return totalPensaoAlim;
	}

	public void setTotalPensaoAlim(String totalPensaoAlim) {
		this.totalPensaoAlim = totalPensaoAlim;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Double getTotalValorServidores() {
		return totalValorServidores;
	}

	public void setTotalValorServidores(Double totalValorServidores) {
		this.totalValorServidores = totalValorServidores;
	}

	public Double getTotalValorPensaoAlim() {
		return totalValorPensaoAlim;
	}

	public void setTotalValorPensaoAlim(Double totalValorPensaoAlim) {
		this.totalValorPensaoAlim = totalValorPensaoAlim;
	}

	public Double getTotalValor() {
		return totalValor;
	}

	public void setTotalValor(Double totalValor) {
		this.totalValor = totalValor;
	}

	public String getCodigoSecretaria() {
		return codigoSecretaria;
	}

	public void setCodigoSecretaria(String codigoSecretaria) {
		this.codigoSecretaria = codigoSecretaria;
	} 
}
