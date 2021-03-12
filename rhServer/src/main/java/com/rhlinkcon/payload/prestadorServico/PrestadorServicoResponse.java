package com.rhlinkcon.payload.prestadorServico;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.PrestadorServico;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.categoriaProfissional.CategoriaProfissionalResponse;
import com.rhlinkcon.payload.cbo.CboDto;
import com.rhlinkcon.payload.convenio.ConvenioResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.payload.nacionalidade.NacionalidadeResponse;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaResponse;
import com.rhlinkcon.payload.verba.VerbaResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrestadorServicoResponse extends DadoCadastralResponse {

	// DADOS PESSOAIS

	private Long id;

	private String nomeCivil;

	private String nomeSocial;

	private String cpf;

	private Instant dataNascimento;

	private String estadoCivil;

	private String sexo;

	private String cor;

	private NacionalidadeResponse nacionalidade;

	private String nomeMae;

	private String nomePai;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private UnidadeFederativaResponse ufEndereco;

	private MunicipioResponse municipio;

	private String cep;

	private Integer dddCelular;

	private String numeroCelular;

	private Integer dddTelefone;

	private String telefone;

	private String email;

	// DADOS TRABALHISTAS

	private String locacao;

	private EmpresaFilialResponse empresaFilial;

	private CategoriaProfissionalResponse categoriaProfissional;

	private CboDto cbo;

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

	private String cnpj;

	private Double irRetido;

	private Double inssPago;

	private String cnpjEmpresaPagadora;

	private Integer diaPagamento;

	private VerbaResponse verba;

	private ConvenioResponse convenio;

	private Integer numeroEmpenho;

	private Instant dataEmpenho;

	private Integer numeroNotaFiscal;

	private Instant dataEmissaoNf;

	// DOCUMENTOS PESSOAIS

	private String pisPasep;

	private Integer numeroCtps;

	private Integer serieCtps;

	private UnidadeFederativaResponse ufCtps;

	private Integer numeroIdentCivilNac;

	private String orgEmissorIdentCivilNac;

	private Instant dataEmissaoIdentCivilNac;

	private Integer numeroRg;

	private String orgaoEmissorRg;

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

	private UnidadeFederativaResponse ufCnh;

	// SEÇÃO DEPENDENTES

	private String tipoDependente;

	private String nomeDependente;

	private String cpfDependente;

	private Boolean impostoRetidoFonte;

	private Boolean planoSaudePrivado;

	private Boolean recebeBeneficio;

	private Boolean capacitacaoProfissional;

	public PrestadorServicoResponse(PrestadorServico prestadorServico) {
		setPrestadorServico(prestadorServico);
	}

	public PrestadorServicoResponse(PrestadorServico prestadorServico, Instant criadoEm, String criadoPor,
			Instant alteradoEm, String alteradoPor) {
		setPrestadorServico(prestadorServico);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	public void setPrestadorServico(PrestadorServico prestadorServico) {
		this.setId(prestadorServico.getId());
		this.setNomeCivil(prestadorServico.getNomeCivil());
		this.setNomeSocial(prestadorServico.getNomeSocial());
		this.setCpf(prestadorServico.getCpf());
		this.setDataNascimento(prestadorServico.getDataNascimento());

		if (prestadorServico.getEstadoCivil() != null)
			this.setEstadoCivil(prestadorServico.getEstadoCivil().getLabel());

		if (prestadorServico.getSexo() != null)
			this.setSexo(prestadorServico.getSexo().getLabel());

		if (prestadorServico.getCor() != null)
			this.setCor(prestadorServico.getCor().getLabel());

		if (prestadorServico.getNacionalidade() != null)
			this.setNacionalidade(new NacionalidadeResponse(prestadorServico.getNacionalidade()));

		this.setNomeMae(prestadorServico.getNomeMae());
		this.setNomePai(prestadorServico.getNomePai());
		this.setLogradouro(prestadorServico.getLogradouro());
		this.setNumero(prestadorServico.getNumero());
		this.setComplemento(prestadorServico.getComplemento());
		this.setBairro(prestadorServico.getBairro());

		if (prestadorServico.getUfEndereco() != null)
			this.setUfEndereco(new UnidadeFederativaResponse(prestadorServico.getUfEndereco()));

		if (prestadorServico.getMunicipio() != null)
			this.setMunicipio(new MunicipioResponse(prestadorServico.getMunicipio()));

		this.setCep(prestadorServico.getCep());
		this.setDddCelular(prestadorServico.getDddCelular());
		this.setNumeroCelular(prestadorServico.getNumeroCelular());
		this.setDddTelefone(prestadorServico.getDddTelefone());
		this.setTelefone(prestadorServico.getTelefone());
		this.setEmail(prestadorServico.getEmail());

		if (prestadorServico.getLocacao() != null)
			this.setLocacao(prestadorServico.getLocacao().getLabel());

		if (prestadorServico.getEmpresaFilial() != null)
			this.setEmpresaFilial(new EmpresaFilialResponse(prestadorServico.getEmpresaFilial()));

		if (prestadorServico.getCategoriaProfissional() != null)
			this.setCategoriaProfissional(
					new CategoriaProfissionalResponse(prestadorServico.getCategoriaProfissional()));

		if (prestadorServico.getCbo() != null)
			this.setCbo(new CboDto(prestadorServico.getCbo()));

		this.setDataInicial(prestadorServico.getDataInicial());
		this.setDataFinal(prestadorServico.getDataFinal());

		if (prestadorServico.getUnidadePagamento() != null)
			this.setUnidadePagamento(prestadorServico.getUnidadePagamento().getLabel());

		if (prestadorServico.getNaturezaAtividade() != null)
			this.setNaturezaAtividade(prestadorServico.getNaturezaAtividade().getLabel());

		this.setAgenteNocivo(prestadorServico.isAgenteNocivo());
		this.setExposicaoPassado(prestadorServico.isExposicaoPassado());

		if (prestadorServico.getTempoContribuicao() != null)
			this.setTempoContribuicao(prestadorServico.getTempoContribuicao().getLabel());

		this.setRecolheInss(prestadorServico.isRecolheInss());

		if (prestadorServico.getModoPagamento() != null)
			this.setModoPagamento(prestadorServico.getModoPagamento().getLabel());

		this.setValorPago(prestadorServico.getValorPago());
		this.setDescontoInss(prestadorServico.getDescontoInss());
		this.setBaseCalculo(prestadorServico.getBaseCalculo());
		this.setDescontoIr(prestadorServico.getDescontoIr());
		this.setRegistroFgts(prestadorServico.getRegistroFgts());
		this.setCnpj(prestadorServico.getCnpj());
		this.setIrRetido(prestadorServico.getIrRetido());
		this.setInssPago(prestadorServico.getInssPago());
		this.setCnpjEmpresaPagadora(prestadorServico.getCnpjEmpresaPagadora());
		this.setDiaPagamento(prestadorServico.getDiaPagamento());

		if (prestadorServico.getVerba() != null)
			this.setVerba(new VerbaResponse(prestadorServico.getVerba()));

		if (prestadorServico.getConvenio() != null)
			this.setConvenio(new ConvenioResponse(prestadorServico.getConvenio()));

		this.setNumeroEmpenho(prestadorServico.getNumeroEmpenho());
		this.setDataEmpenho(prestadorServico.getDataEmpenho());
		this.setNumeroNotaFiscal(prestadorServico.getNumeroNotaFiscal());
		this.setDataEmissaoNf(prestadorServico.getDataEmissaoNf());
		this.setPisPasep(prestadorServico.getPisPasep());
		this.setNumeroCtps(prestadorServico.getNumeroCtps());
		this.setSerieCtps(prestadorServico.getSerieCtps());

		if (prestadorServico.getUfCtps() != null)
			this.setUfCtps(new UnidadeFederativaResponse(prestadorServico.getUfCtps()));

		this.setNumeroIdentCivilNac(prestadorServico.getNumeroIdentCivilNac());
		this.setOrgEmissorIdentCivilNac(prestadorServico.getOrgEmissorIdentCivilNac());
		this.setDataEmissaoIdentCivilNac(prestadorServico.getDataEmissaoIdentCivilNac());
		this.setNumeroRg(prestadorServico.getNumeroRg());
		this.setOrgaoEmissorRg(prestadorServico.getOrgaoEmissorRg());
		this.setDataEmissaoRg(prestadorServico.getDataEmissaoRg());
		this.setNumeroRegNacEstrangeiro(prestadorServico.getNumeroRegNacEstrangeiro());
		this.setOrgaoEmissorRegNacEstrangeiro(prestadorServico.getOrgaoEmissorRegNacEstrangeiro());
		this.setDataEmissaoRegNacEstrangeiro(prestadorServico.getDataEmissaoRegNacEstrangeiro());
		this.setRegistroProfissional(prestadorServico.getRegistroProfissional());
		this.setDataEmissaoRegProfissional(prestadorServico.getDataEmissaoRegProfissional());
		this.setDataValidadeRegProfissional(prestadorServico.getDataValidadeRegProfissional());
		this.setNumeroCnh(prestadorServico.getNumeroCnh());
		this.setDataValidadeCnh(prestadorServico.getDataValidadeCnh());
		this.setDataPrimeiraCnh(prestadorServico.getDataPrimeiraCnh());
		this.setDataEmissaoCnh(prestadorServico.getDataEmissaoCnh());

		if (prestadorServico.getCategoriaCnh() != null)
			this.setCategoriaCnh(prestadorServico.getCategoriaCnh().getLabel());

		if (prestadorServico.getUfCnh() != null)
			this.setUfCnh(new UnidadeFederativaResponse(prestadorServico.getUfCnh()));

		if (prestadorServico.getTipoDependente() != null)
			this.setTipoDependente(prestadorServico.getTipoDependente().getLabel());

		this.setNomeDependente(prestadorServico.getNomeDependente());
		this.setCpfDependente(prestadorServico.getCpfDependente());
		this.setImpostoRetidoFonte(prestadorServico.isImpostoRetidoFonte());
		this.setPlanoSaudePrivado(prestadorServico.isPlanoSaudePrivado());
		this.setRecebeBeneficio(prestadorServico.isRecebeBeneficio());
		this.setCapacitacaoProfissional(prestadorServico.isCapacitacaoProfissional());

	}

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

	public NacionalidadeResponse getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(NacionalidadeResponse nacionalidade) {
		this.nacionalidade = nacionalidade;
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

	public UnidadeFederativaResponse getUfEndereco() {
		return ufEndereco;
	}

	public void setUfEndereco(UnidadeFederativaResponse ufEndereco) {
		this.ufEndereco = ufEndereco;
	}

	public MunicipioResponse getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioResponse municipio) {
		this.municipio = municipio;
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

	public EmpresaFilialResponse getEmpresaFilial() {
		return empresaFilial;
	}

	public void setEmpresaFilial(EmpresaFilialResponse empresaFilial) {
		this.empresaFilial = empresaFilial;
	}

	public CategoriaProfissionalResponse getCategoriaProfissional() {
		return categoriaProfissional;
	}

	public void setCategoriaProfissional(CategoriaProfissionalResponse categoriaProfissional) {
		this.categoriaProfissional = categoriaProfissional;
	}

	public CboDto getCbo() {
		return cbo;
	}

	public void setCbo(CboDto cbo) {
		this.cbo = cbo;
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

	public Boolean getAgenteNocivo() {
		return agenteNocivo;
	}

	public void setAgenteNocivo(Boolean agenteNocivo) {
		this.agenteNocivo = agenteNocivo;
	}

	public Boolean getExposicaoPassado() {
		return exposicaoPassado;
	}

	public void setExposicaoPassado(Boolean exposicaoPassado) {
		this.exposicaoPassado = exposicaoPassado;
	}

	public String getTempoContribuicao() {
		return tempoContribuicao;
	}

	public void setTempoContribuicao(String tempoContribuicao) {
		this.tempoContribuicao = tempoContribuicao;
	}

	public Boolean getRecolheInss() {
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

	public VerbaResponse getVerba() {
		return verba;
	}

	public void setVerba(VerbaResponse verba) {
		this.verba = verba;
	}

	public ConvenioResponse getConvenio() {
		return convenio;
	}

	public void setConvenio(ConvenioResponse convenio) {
		this.convenio = convenio;
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

	public UnidadeFederativaResponse getUfCtps() {
		return ufCtps;
	}

	public void setUfCtps(UnidadeFederativaResponse ufCtps) {
		this.ufCtps = ufCtps;
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

	public UnidadeFederativaResponse getUfCnh() {
		return ufCnh;
	}

	public void setUfCnh(UnidadeFederativaResponse ufCnh) {
		this.ufCnh = ufCnh;
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

	public Boolean getImpostoRetidoFonte() {
		return impostoRetidoFonte;
	}

	public void setImpostoRetidoFonte(Boolean impostoRetidoFonte) {
		this.impostoRetidoFonte = impostoRetidoFonte;
	}

	public Boolean getPlanoSaudePrivado() {
		return planoSaudePrivado;
	}

	public void setPlanoSaudePrivado(Boolean planoSaudePrivado) {
		this.planoSaudePrivado = planoSaudePrivado;
	}

	public Boolean getRecebeBeneficio() {
		return recebeBeneficio;
	}

	public void setRecebeBeneficio(Boolean recebeBeneficio) {
		this.recebeBeneficio = recebeBeneficio;
	}

	public Boolean getCapacitacaoProfissional() {
		return capacitacaoProfissional;
	}

	public void setCapacitacaoProfissional(Boolean capacitacaoProfissional) {
		this.capacitacaoProfissional = capacitacaoProfissional;
	}

}
