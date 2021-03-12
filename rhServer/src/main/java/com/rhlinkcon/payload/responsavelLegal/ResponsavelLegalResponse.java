package com.rhlinkcon.payload.responsavelLegal;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.ResponsavelLegal;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.agencia.AgenciaResponse;
import com.rhlinkcon.payload.banco.BancoResponse;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaResponse;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsavelLegalResponse extends DadoCadastralResponse {

	private Long id;
	
	private String codigoResponsavel;

	private String nome;
	
	private BancoResponse banco;
	
	private AgenciaResponse agenciaBancaria;

	private Integer agencia;
	
	private Integer contaCorrente;
	
	private String digitoConta;
	
	private String tipoResponsavel;
	
	private String cpf;
	
	private Integer identidade;
	
	private String tipoConta;
	
	private String logradouro;
	
	private String numero;

	private String complemento;
	
	private String bairro;
	
	private MunicipioResponse municipio;
	
	private String cep;
	
	private UnidadeFederativaResponse unidadeFederativa;

	private String telefone;

	
	public ResponsavelLegalResponse(ResponsavelLegal responsavelLegal) {
		setResponsavelLegal(responsavelLegal);
	}
	
	public ResponsavelLegalResponse(ResponsavelLegal responsavelLegal, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setResponsavelLegal(responsavelLegal);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	
	public void setResponsavelLegal(ResponsavelLegal responsavelLegal) {
		this.setId(responsavelLegal.getId());
		this.setCodigoResponsavel(responsavelLegal.getCodigoResponsavel());
		this.setNome(responsavelLegal.getNome());
		this.setBanco(new BancoResponse(responsavelLegal.getBanco(), Projecao.BASICA));
		
		if(Objects.nonNull(responsavelLegal.getAgenciaBancaria()))
			this.setAgenciaBancaria(new AgenciaResponse(responsavelLegal.getAgenciaBancaria(), Projecao.BASICA));
		
		this.setAgencia(responsavelLegal.getAgencia());
		this.setContaCorrente(responsavelLegal.getContaCorrente());
		this.setDigitoConta(responsavelLegal.getDigitoConta());

		if (Utils.checkStr(responsavelLegal.getTipoResponsavel().getLabel()))
			this.setTipoResponsavel(responsavelLegal.getTipoResponsavel().getLabel());
		
		this.setCpf(responsavelLegal.getCpf());
		this.setIdentidade(responsavelLegal.getIdentidade());

		if (Utils.checkStr(responsavelLegal.getTipoConta().getLabel()))
			this.setTipoConta(responsavelLegal.getTipoConta().getLabel());

		this.setLogradouro(responsavelLegal.getLogradouro());
		this.setNumero(responsavelLegal.getNumero());
		this.setComplemento(responsavelLegal.getComplemento());
		this.setBairro(responsavelLegal.getBairro());
		
		if(responsavelLegal.getUnidadeFederativa() != null)
			this.setUnidadeFederativa(new UnidadeFederativaResponse(responsavelLegal.getUnidadeFederativa()));
		
		this.setCep(responsavelLegal.getCep());

		if(responsavelLegal.getMunicipio() != null)
			this.setMunicipio(new MunicipioResponse(responsavelLegal.getMunicipio()));
		
		this.setTelefone(responsavelLegal.getTelefone());
		
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodigoResponsavel() {
		return codigoResponsavel;
	}
	
	public void setCodigoResponsavel(String codigoResponsavel) {
		this.codigoResponsavel = codigoResponsavel;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public BancoResponse getBanco() {
		return banco;
	}
	
	public void setBanco(BancoResponse banco) {
		this.banco = banco;
	}

	public Integer getAgencia() {
		return agencia;
	}
	
	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}
	
	public Integer getContaCorrente() {
		return contaCorrente;
	}
	
	public void setContaCorrente(Integer contaCorrente) {
		this.contaCorrente = contaCorrente;
	}
	
	public String getTipoResponsavel() {
		return tipoResponsavel;
	}
	
	public void setTipoResponsavel(String tipoResponsavel) {
		this.tipoResponsavel = tipoResponsavel;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Integer getIdentidade() {
		return identidade;
	}
	
	public void setIdentidade(Integer identidade) {
		this.identidade = identidade;
	}

	public String getTipoConta() {
		return tipoConta;
	}
	
	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
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

	public UnidadeFederativaResponse getUnidadeFederativa() {
		return unidadeFederativa;
	}
	
	public void setUnidadeFederativa(UnidadeFederativaResponse unidadeFederativa) {
		this.unidadeFederativa= unidadeFederativa;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public AgenciaResponse getAgenciaBancaria() {
		return agenciaBancaria;
	}

	public void setAgenciaBancaria(AgenciaResponse agenciaBancaria) {
		this.agenciaBancaria = agenciaBancaria;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}
	
}
