package com.rhlinkcon.payload.prestadorServico;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrestadorServicoRequest {

	// DADOS PESSOAIS
		
		private Long id;
		
		@NotNull
		private String nomeCivil;

		private String nomeSocial;
		
		@NotNull
		private String cpf;
		
		@NotNull
		private Instant dataNascimento;
		
		@NotNull
		private String estadoCivil;
		
		@NotNull
		private String sexo;
		
		private String cor;
		
		@NotNull
		private Long nacionalidadeId;
		
		@NotNull
		private String nomeMae;

		private String nomePai;
		
		@NotNull
		private String logradouro;

		@NotNull
		private String numero;

		private String complemento;

		private String bairro;
		
		@NotNull
		private Long ufIdEndereco;
		
		@NotNull
		private Long municipioId;

		@NotNull
		private String cep;

		@NotNull
		private Integer dddCelular;

		@NotNull
		private String numeroCelular;

		private Integer dddTelefone;

		private String telefone;

		private String email;

		// DADOS TRABALHISTAS
	
		@NotNull
		private String locacao;

		private Long empresaFilialId;

		@NotNull
		private Long categoriaProfissionalId;
		
		@NotNull
		private Long cboId;
		
		private Instant dataInicial;

		private Instant dataFinal;

		private String unidadePagamento;

		private String naturezaAtividade;

		private Boolean agenteNocivo;

		private Boolean exposicaoPassado;

		private String tempoContribuicao;
		
	// DADOS FISCAIS

		private Boolean recolheInss;
				
		private String modoPagamento;
		
		private Double valorPago;
		
		private Double descontoInss;
		
		private Double baseCalculo;
		
		private Double descontoIr;

		private String registroFgts;
		
		@NotNull
		private String cnpj;
		
		private Double irRetido;
		
		private Double inssPago;
		
		@NotNull
		private String cnpjEmpresaPagadora;
		
		@NotNull
		private Integer diaPagamento;
		
		private Long verbaId;

		private Long convenioId;

		private Integer numeroEmpenho;

		private Instant dataEmpenho;

		private Integer numeroNotaFiscal;

		private Instant dataEmissaoNf;
		
	// DOCUMENTOS PESSOAIS

		private String pisPasep;

		@NotNull
		private Integer numeroCtps;

		@NotNull
		private Integer serieCtps;

		@NotNull
		private Long ufIdCtps;

		private Integer numeroIdentCivilNac;
		
		private String orgEmissorIdentCivilNac;
		
		private Instant dataEmissaoIdentCivilNac;
		
		@NotNull
		private Integer numeroRg;

		@NotNull
		private String orgaoEmissorRg;

		@NotNull
		private Instant dataEmissaoRg;

		private Integer numeroRegNacEstrangeiro;

		private String orgaoEmissorRegNacEstrangeiro;
		
		private Instant dataEmissaoRegNacEstrangeiro;
		
		private Integer registroProfissional;

		private Instant dataEmissaoRegProfissional;
		
		private Instant dataValidadeRegProfissional;

		private Integer numeroCnh;
		
		private Instant dataValidadeCnh;

		private Instant dataPrimeiraCnh;

		private Instant dataEmissaoCnh;
		
		private String categoriaCnh;

		private Long ufIdCnh;
		
	// SEÇÃO DEPENDENTES	
		
		private String tipoDependente;

		private String nomeDependente;
		
		private String cpfDependente;

		private Boolean impostoRetidoFonte;

		private Boolean planoSaudePrivado;

		private Boolean recebeBeneficio;

		private Boolean capacitacaoProfissional;	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCivil() {
		return nomeCivil;
	}

	public void setNomeCivil(String nomeCivil) {
		this.nomeCivil = nomeCivil;
	}

	public String getNomeSocial() {
		return nomeSocial;
	}

	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Instant getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Instant dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Long getNacionalidadeId() {
		return nacionalidadeId;
	}

	public void setNacionalidadeId(Long nacionalidadeId) {
		this.nacionalidadeId = nacionalidadeId;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Long getUfIdEndereco() {
		return ufIdEndereco;
	}

	public void setUfIdEndereco(Long ufIdEndereco) {
		this.ufIdEndereco = ufIdEndereco;
	}

	public Long getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getDddCelular() {
		return dddCelular;
	}

	public void setDddCelular(Integer dddCelular) {
		this.dddCelular = dddCelular;
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public Integer getDddTelefone() {
		return dddTelefone;
	}

	public void setDddTelefone(Integer dddTelefone) {
		this.dddTelefone = dddTelefone;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocacao() {
		return locacao;
	}

	public void setLocacao(String locacao) {
		this.locacao = locacao;
	}

	public Long getEmpresaFilialId() {
		return empresaFilialId;
	}

	public void setEmpresaFilialId(Long empresaFilialId) {
		this.empresaFilialId = empresaFilialId;
	}

	public Long getCategoriaProfissionalId() {
		return categoriaProfissionalId;
	}

	public void setCategoriaProfissionalId(Long categoriaProfissionalId) {
		this.categoriaProfissionalId = categoriaProfissionalId;
	}

	public Long getCboId() {
		return cboId;
	}

	public void setCboId(Long cboId) {
		this.cboId = cboId;
	}

	public Instant getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Instant dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Instant getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Instant dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getUnidadePagamento() {
		return unidadePagamento;
	}

	public void setUnidadePagamento(String unidadePagamento) {
		this.unidadePagamento = unidadePagamento;
	}

	public String getNaturezaAtividade() {
		return naturezaAtividade;
	}

	public void setNaturezaAtividade(String naturezaAtividade) {
		this.naturezaAtividade = naturezaAtividade;
	}

	public Boolean isAgenteNocivo() {
		return agenteNocivo;
	}

	public void setAgenteNocivo(Boolean agenteNocivo) {
		this.agenteNocivo = agenteNocivo;
	}

	public Boolean isExposicaoPassado() {
		return exposicaoPassado;
	}

	public void setExposicaoPassado(Boolean exposicaoPassado) {
		this.exposicaoPassado = exposicaoPassado;
	}

	public String getTempoContribuicao() {
		return tempoContribuicao;
	}

	public void setTempoContribuinte(String tempoContribuicao) {
		this.tempoContribuicao = tempoContribuicao;
	}

	public Boolean isRecolheInss() {
		return recolheInss;
	}

	public void setRecolheInss(Boolean recolheInss) {
		this.recolheInss = recolheInss;
	}

	public String getModoPagamento() {
		return modoPagamento;
	}

	public void setModoPagamento(String modoPagamento) {
		this.modoPagamento = modoPagamento;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Double getDescontoInss() {
		return descontoInss;
	}

	public void setDescontoInss(Double descontoInss) {
		this.descontoInss = descontoInss;
	}

	public Double getBaseCalculo() {
		return baseCalculo;
	}

	public void setBaseCalculo(Double baseCalculo) {
		this.baseCalculo = baseCalculo;
	}

	public Double getDescontoIr() {
		return descontoIr;
	}

	public void setDescontoIr(Double descontoIr) {
		this.descontoIr = descontoIr;
	}

	public String getRegistroFgts() {
		return registroFgts;
	}

	public void setRegistroFgts(String registroFgts) {
		this.registroFgts = registroFgts;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Double getIrRetido() {
		return irRetido;
	}

	public void setIrRetido(Double irRetido) {
		this.irRetido = irRetido;
	}

	public Double getInssPago() {
		return inssPago;
	}

	public void setInssPago(Double inssPago) {
		this.inssPago = inssPago;
	}

	public String getCnpjEmpresaPagadora() {
		return cnpjEmpresaPagadora;
	}

	public void setCnpjEmpresaPagadora(String cnpjEmpresaPagadora) {
		this.cnpjEmpresaPagadora = cnpjEmpresaPagadora;
	}

	public Integer getDiaPagamento() {
		return diaPagamento;
	}

	public void setDiaPagamento(Integer diaPagamento) {
		this.diaPagamento = diaPagamento;
	}

	public Long getVerbaId() {
		return verbaId;
	}

	public void setVerbaId(Long verbaId) {
		this.verbaId = verbaId;
	}

	public Long getConvenioId() {
		return convenioId;
	}

	public void setConvenioId(Long convenioId) {
		this.convenioId = convenioId;
	}

	public Integer getNumeroEmpenho() {
		return numeroEmpenho;
	}

	public void setNumeroEmpenho(Integer numeroEmpenho) {
		this.numeroEmpenho = numeroEmpenho;
	}

	public Instant getDataEmpenho() {
		return dataEmpenho;
	}

	public void setDataEmpenho(Instant dataEmpenho) {
		this.dataEmpenho = dataEmpenho;
	}

	public Integer getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(Integer numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public Instant getDataEmissaoNf() {
		return dataEmissaoNf;
	}

	public void setDataEmissaoNf(Instant dataEmissaoNf) {
		this.dataEmissaoNf = dataEmissaoNf;
	}

	public String getPisPasep() {
		return pisPasep;
	}

	public void setPisPasep(String pisPasep) {
		this.pisPasep = pisPasep;
	}

	public Integer getNumeroCtps() {
		return numeroCtps;
	}

	public void setNumeroCtps(Integer numeroCtps) {
		this.numeroCtps = numeroCtps;
	}

	public Integer getSerieCtps() {
		return serieCtps;
	}

	public void setSerieCtps(Integer serieCtps) {
		this.serieCtps = serieCtps;
	}

	public Long getUfIdCtps() {
		return ufIdCtps;
	}

	public void setUfIdCtps(Long ufIdCtps) {
		this.ufIdCtps = ufIdCtps;
	}

	public Integer getNumeroIdentCivilNac() {
		return numeroIdentCivilNac;
	}

	public void setNumeroIdentCivilNac(Integer numeroIdentCivilNac) {
		this.numeroIdentCivilNac = numeroIdentCivilNac;
	}

	public String getOrgEmissorIdentCivilNac() {
		return orgEmissorIdentCivilNac;
	}

	public void setOrgEmissorIdentCivilNac(String orgEmissorIdentCivilNac) {
		this.orgEmissorIdentCivilNac = orgEmissorIdentCivilNac;
	}

	public Instant getDataEmissaoIdentCivilNac() {
		return dataEmissaoIdentCivilNac;
	}

	public void setDataEmissaoIdentCivilNac(Instant dataEmissaoIdentCivilNac) {
		this.dataEmissaoIdentCivilNac = dataEmissaoIdentCivilNac;
	}

	public Integer getNumeroRg() {
		return numeroRg;
	}

	public void setNumeroRg(Integer numeroRg) {
		this.numeroRg = numeroRg;
	}

	public String getOrgaoEmissorRg() {
		return orgaoEmissorRg;
	}

	public void setOrgaoEmissorRg(String orgaoEmissorRg) {
		this.orgaoEmissorRg = orgaoEmissorRg;
	}

	public Instant getDataEmissaoRg() {
		return dataEmissaoRg;
	}

	public void setDataEmissaoRg(Instant dataEmissaoRg) {
		this.dataEmissaoRg = dataEmissaoRg;
	}

	public Integer getNumeroRegNacEstrangeiro() {
		return numeroRegNacEstrangeiro;
	}

	public void setNumeroRegNacEstrangeiro(Integer numeroRegNacEstrangeiro) {
		this.numeroRegNacEstrangeiro = numeroRegNacEstrangeiro;
	}

	public String getOrgaoEmissorRegNacEstrangeiro() {
		return orgaoEmissorRegNacEstrangeiro;
	}

	public void setOrgaoEmissorRegNacEstrangeiro(String orgaoEmissorRegNacEstrangeiro) {
		this.orgaoEmissorRegNacEstrangeiro = orgaoEmissorRegNacEstrangeiro;
	}

	public Instant getDataEmissaoRegNacEstrangeiro() {
		return dataEmissaoRegNacEstrangeiro;
	}

	public void setDataEmissaoRegNacEstrangeiro(Instant dataEmissaoRegNacEstrangeiro) {
		this.dataEmissaoRegNacEstrangeiro = dataEmissaoRegNacEstrangeiro;
	}

	public Integer getRegistroProfissional() {
		return registroProfissional;
	}

	public void setRegistroProfissional(Integer registroProfissional) {
		this.registroProfissional = registroProfissional;
	}

	public Instant getDataEmissaoRegProfissional() {
		return dataEmissaoRegProfissional;
	}

	public void setDataEmissaoRegProfissional(Instant dataEmissaoRegProfissional) {
		this.dataEmissaoRegProfissional = dataEmissaoRegProfissional;
	}

	public Instant getDataValidadeRegProfissional() {
		return dataValidadeRegProfissional;
	}

	public void setDataValidadeRegProfissional(Instant dataValidadeRegProfissional) {
		this.dataValidadeRegProfissional = dataValidadeRegProfissional;
	}

	public Integer getNumeroCnh() {
		return numeroCnh;
	}

	public void setNumeroCnh(Integer numeroCnh) {
		this.numeroCnh = numeroCnh;
	}

	public Instant getDataValidadeCnh() {
		return dataValidadeCnh;
	}

	public void setDataValidadeCnh(Instant dataValidadeCnh) {
		this.dataValidadeCnh = dataValidadeCnh;
	}

	public Instant getDataPrimeiraCnh() {
		return dataPrimeiraCnh;
	}

	public void setDataPrimeiraCnh(Instant dataPrimeiraCnh) {
		this.dataPrimeiraCnh = dataPrimeiraCnh;
	}

	public Instant getDataEmissaoCnh() {
		return dataEmissaoCnh;
	}

	public void setDataEmissaoCnh(Instant dataEmissaoCnh) {
		this.dataEmissaoCnh = dataEmissaoCnh;
	}

	public String getCategoriaCnh() {
		return categoriaCnh;
	}

	public void setCategoriaCnh(String categoriaCnh) {
		this.categoriaCnh = categoriaCnh;
	}

	public Long getUfIdCnh() {
		return ufIdCnh;
	}

	public void setUfIdCnh(Long ufIdCnh) {
		this.ufIdCnh = ufIdCnh;
	}

	public String getTipoDependente() {
		return tipoDependente;
	}

	public void setTipoDependente(String tipoDependente) {
		this.tipoDependente = tipoDependente;
	}

	public String getNomeDependente() {
		return nomeDependente;
	}

	public void setNomeDependente(String nomeDependente) {
		this.nomeDependente = nomeDependente;
	}

	public String getCpfDependente() {
		return cpfDependente;
	}

	public void setCpfDependente(String cpfDependente) {
		this.cpfDependente = cpfDependente;
	}

	public Boolean isImpostoRetidoFonte() {
		return impostoRetidoFonte;
	}

	public void setImpostoRetidoFonte(Boolean impostoRetidoFonte) {
		this.impostoRetidoFonte = impostoRetidoFonte;
	}

	public Boolean isPlanoSaudePrivado() {
		return planoSaudePrivado;
	}

	public void setPlanoSaudePrivado(Boolean planoSaudePrivado) {
		this.planoSaudePrivado = planoSaudePrivado;
	}

	public Boolean isRecebeBeneficio() {
		return recebeBeneficio;
	}

	public void setRecebeBeneficio(Boolean recebeBeneficio) {
		this.recebeBeneficio = recebeBeneficio;
	}

	public Boolean isCapacitacaoProfissional() {
		return capacitacaoProfissional;
	}

	public void setCapacitacaoProfissional(Boolean capacitacaoProfissional) {
		this.capacitacaoProfissional = capacitacaoProfissional;
	}

}