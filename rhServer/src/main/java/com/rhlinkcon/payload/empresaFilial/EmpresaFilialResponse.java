package com.rhlinkcon.payload.empresaFilial;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.agencia.AgenciaResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.banco.BancoResponse;
import com.rhlinkcon.payload.cnae.CnaeResponse;
import com.rhlinkcon.payload.codigoPagamentoGps.CodigoPagamentoGpsResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.payload.nacionalidade.NacionalidadeResponse;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaResponse;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpresaFilialResponse extends DadoCadastralResponse {

	private Long id;

	private String sigla;

	private String nomeFilial;

	private Boolean empresaMatriz;

	private String tipoFilial;

	private CnaeResponse cnae;

	private Long cnaeId;

	private CodigoPagamentoGpsResponse codigoGps;

	private String situacao;

	private String esferaOrgao;

	private Integer qtdProprietario;

	private String tipoEstabelecimento;

	private Double capitalSocialAnual;

	private Instant dataInicialAtividade;

	private String referenciaContribuicao;

	private String atividadePrimaria;

	private String naturezaEstabelecimento;

	private BancoResponse banco;
	
	private AgenciaResponse agenciaBancaria;

	private Integer agencia;
	
	private Integer codigoConvenio;
	
	private String tipoOperacao;

	private Integer fpas;

	private Integer codigoGrpasAcidenteTrabalho;

	private String cnpj;

	private String cei;

	private Integer codigoEmpregador;

	private Integer rNegativa;

	private Double percentualEmpregador;

	private Double sat;

	private Double salarioEducacao;

	private Double senai;

	private Double sesi;

	private Double senac;

	private Double sesc;

	private Double sebrae;

	private Double senar;

	private Double senat;

	private Double set;

	private Double secoop;

	private Double dpc;

	private Double forcaAerea;

	private Integer fap;

	private String logradouro;

	private String numero;

	private String complemento;

	private String cep;

	private UnidadeFederativaResponse uf;

	private String bairro;

	private MunicipioResponse municipio;

	private String telefone;

	private AnexoResponse anexo;

	private String tipoInscricao;

	private List<LotacaoResponse> lotacoes;

	private Long definicaoOrganicoId;

	private NacionalidadeResponse pais;

	private List<EmpresaFilialResponse> filiais;

	private String nomeResponsavel;

	private Date vigenciaInicial;

	private Date vigenciaFinal;

	private String emailResponsavel;

	/**
	 * 
	 * @author Júlio Galvão Construtor generico
	 */
	public EmpresaFilialResponse() {
	}

	public EmpresaFilialResponse(EmpresaFilial empresaFilial) {
		setEmpresaFilial(empresaFilial);
	}

	public EmpresaFilialResponse(EmpresaFilial empresaFilial, Projecao projecao) {

		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.APOSENTADORIA)
				|| projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)
				|| projecao.equals(Projecao.CERTIDAO_COMPENSACAO)) {
			this.setId(empresaFilial.getId());
			this.setNomeFilial(empresaFilial.getNomeFilial());
			this.setCnpj(empresaFilial.getCnpj());
			this.setCodigoEmpregador(empresaFilial.getCodigoEmpregador());
			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA) || projecao.equals(Projecao.CERTIDAO_COMPENSACAO)) {
				this.setNomeResponsavel(empresaFilial.getNomeResponsavel());
				this.setEmailResponsavel(empresaFilial.getEmailResponsavel());
				this.setVigenciaFinal(empresaFilial.getVigenciaFinal());
				this.setVigenciaInicial(empresaFilial.getVigenciaInicial());
				if (projecao.equals(Projecao.COMPLETA) || projecao.equals(Projecao.CERTIDAO_COMPENSACAO)) {
					this.setLogradouro(empresaFilial.getLogradouro());
					this.setNumero(empresaFilial.getNumero());
					this.setComplemento(empresaFilial.getComplemento());
					this.setBairro(empresaFilial.getBairro());
					this.setCep(empresaFilial.getCep());
				}
			}
		}

	}

	public EmpresaFilialResponse(EmpresaFilial empresaFilial, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setEmpresaFilial(empresaFilial);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	public void setEmpresaFilial(EmpresaFilial empresaFilial) {
		this.setId(empresaFilial.getId());
		this.setSigla(empresaFilial.getSigla());
		this.setNomeFilial(empresaFilial.getNomeFilial());
		this.setEmpresaMatriz(empresaFilial.getEmpresaMatriz());

		if (Utils.checkStr(empresaFilial.getTipoFilial().getLabel()))
			this.setTipoFilial(empresaFilial.getTipoFilial().getLabel());

		if (Utils.checkStr(empresaFilial.getSituacao().getLabel()))
			this.setSituacao(empresaFilial.getSituacao().getLabel());

		if (Utils.checkStr(empresaFilial.getEsferaOrgao().getLabel()))
			this.setEsferaOrgao(empresaFilial.getEsferaOrgao().getLabel());

		this.setQtdProprietario(empresaFilial.getQtdProprietario());

		if (Utils.checkStr(empresaFilial.getTipoEstabelecimento().getLabel()))
			this.setTipoEstabelecimento(empresaFilial.getTipoEstabelecimento().getLabel());

		this.setCapitalSocialAnual(empresaFilial.getCapitalSocialAnual());
		this.setDataInicialAtividade(empresaFilial.getDataInicialAtividade());

		if (Utils.checkStr(empresaFilial.getReferenciaContribuicao().getLabel()))
			this.setReferenciaContribuicao(empresaFilial.getReferenciaContribuicao().getLabel());

		this.setAtividadePrimaria(empresaFilial.getAtividadePrimaria());
		this.setNaturezaEstabelecimento(empresaFilial.getNaturezaEstabelecimento());
		
		if(Objects.nonNull(empresaFilial.getAgenciaBancaria()))
			this.setAgenciaBancaria(new AgenciaResponse(empresaFilial.getAgenciaBancaria(), Projecao.BASICA));
		
		this.setAgencia(empresaFilial.getAgencia());
		this.setFpas(empresaFilial.getFpas());
		this.setCodigoGrpasAcidenteTrabalho(empresaFilial.getCodigoGrpasAcidenteTrabalho());
		this.setCnpj(empresaFilial.getCnpj());
		this.setCei(empresaFilial.getCei());
		this.setCodigoEmpregador(empresaFilial.getCodigoEmpregador());
		this.setrNegativa(empresaFilial.getrNegativa());
		this.setPercentualEmpregador(empresaFilial.getPercentualEmpregador());
		this.setSat(empresaFilial.getSat());
		this.setSalarioEducacao(empresaFilial.getSalarioEducacao());
		this.setSenai(empresaFilial.getSenai());
		this.setSesi(empresaFilial.getSesi());
		this.setSenac(empresaFilial.getSenac());
		this.setSesc(empresaFilial.getSesc());
		this.setSebrae(empresaFilial.getSebrae());
		this.setSenar(empresaFilial.getSenar());
		this.setSenat(empresaFilial.getSenat());
		this.setSet(empresaFilial.getSet());
		this.setSecoop(empresaFilial.getSecoop());
		this.setDpc(empresaFilial.getDpc());
		this.setForcaAerea(empresaFilial.getForcaAerea());
		this.setFap(empresaFilial.getFap());
		this.setLogradouro(empresaFilial.getLogradouro());
		this.setNumero(empresaFilial.getNumero());
		this.setComplemento(empresaFilial.getComplemento());
		this.setCep(empresaFilial.getCep());
		this.setBairro(empresaFilial.getBairro());
		this.setTelefone(empresaFilial.getTelefone());
		this.setNomeResponsavel(empresaFilial.getNomeResponsavel());
		this.setEmailResponsavel(empresaFilial.getEmailResponsavel());
		this.setVigenciaFinal(empresaFilial.getVigenciaFinal());
		this.setVigenciaInicial(empresaFilial.getVigenciaInicial());

		if (Utils.checkStr(empresaFilial.getTipoInscricao().getLabel()))
			this.setTipoInscricao(empresaFilial.getTipoInscricao().getLabel());

		this.setCnae(new CnaeResponse(empresaFilial.getCnae()));
		this.setCnaeId(empresaFilial.getCnae().getId());

		this.setCodigoGps(new CodigoPagamentoGpsResponse(empresaFilial.getCodigoGps()));
		this.setBanco(new BancoResponse(empresaFilial.getBanco(), Projecao.BASICA));
		
		this.setCodigoConvenio(empresaFilial.getCodigoConvenio());
		
		if(Objects.nonNull(empresaFilial.getTipoOperacao()))
			this.setTipoOperacao(empresaFilial.getTipoOperacao().name());

		if (empresaFilial.getPais() != null)
			this.setPais(new NacionalidadeResponse(empresaFilial.getPais()));

		if (empresaFilial.getUf() != null)
			this.setUf(new UnidadeFederativaResponse(empresaFilial.getUf()));

		if (empresaFilial.getMunicipio() != null)
			this.setMunicipio(new MunicipioResponse(empresaFilial.getMunicipio()));

		if (empresaFilial.getAnexo() != null)
			this.setAnexo(new AnexoResponse(empresaFilial.getAnexo()));

		lotacoes = new ArrayList<>();
		for (Lotacao lotacao : empresaFilial.getLotacoes()) {
			lotacoes.add(new LotacaoResponse(lotacao));
		}

		this.setLotacoes(lotacoes);

	}

	public Long getId() {
		return id;
	}

	public String getSigla() {
		return sigla;
	}

	public String getNomeFilial() {
		return nomeFilial;
	}

	public Boolean getEmpresaMatriz() {
		return empresaMatriz;
	}

	public String getTipoFilial() {
		return tipoFilial;
	}

	public CnaeResponse getCnae() {
		return cnae;
	}

	public CodigoPagamentoGpsResponse getCodigoGps() {
		return codigoGps;
	}

	public String getSituacao() {
		return situacao;
	}

	public String getEsferaOrgao() {
		return esferaOrgao;
	}

	public Integer getQtdProprietario() {
		return qtdProprietario;
	}

	public String getTipoEstabelecimento() {
		return tipoEstabelecimento;
	}

	public Double getCapitalSocialAnual() {
		return capitalSocialAnual;
	}

	public Instant getDataInicialAtividade() {
		return dataInicialAtividade;
	}

	public String getReferenciaContribuicao() {
		return referenciaContribuicao;
	}

	public String getAtividadePrimaria() {
		return atividadePrimaria;
	}

	public String getNaturezaEstabelecimento() {
		return naturezaEstabelecimento;
	}

	public BancoResponse getBanco() {
		return banco;
	}

	public Integer getAgencia() {
		return agencia;
	}

	public Integer getFpas() {
		return fpas;
	}

	public Integer getCodigoGrpasAcidenteTrabalho() {
		return codigoGrpasAcidenteTrabalho;
	}

	public String getCnpj() {
		return cnpj;
	}

	public Integer getCodigoEmpregador() {
		return codigoEmpregador;
	}

	public Double getPercentualEmpregador() {
		return percentualEmpregador;
	}

	public Double getSat() {
		return sat;
	}

	public Double getSalarioEducacao() {
		return salarioEducacao;
	}

	public Double getSenai() {
		return senai;
	}

	public Double getSesi() {
		return sesi;
	}

	public Double getSenac() {
		return senac;
	}

	public Double getSesc() {
		return sesc;
	}

	public Double getSebrae() {
		return sebrae;
	}

	public Double getSenar() {
		return senar;
	}

	public Double getSenat() {
		return senat;
	}

	public Double getSet() {
		return set;
	}

	public Double getSecoop() {
		return secoop;
	}

	public Double getDpc() {
		return dpc;
	}

	public Double getForcaAerea() {
		return forcaAerea;
	}

	public Integer getFap() {
		return fap;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getCep() {
		return cep;
	}

	public UnidadeFederativaResponse getUf() {
		return uf;
	}

	public String getBairro() {
		return bairro;
	}

	public String getTelefone() {
		return telefone;
	}

	public AnexoResponse getAnexo() {
		return anexo;
	}

	public String getTipoInscricao() {
		return tipoInscricao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public void setNomeFilial(String nomeFilial) {
		this.nomeFilial = nomeFilial;
	}

	public void setEmpresaMatriz(Boolean empresaMatriz) {
		this.empresaMatriz = empresaMatriz;
	}

	public void setTipoFilial(String tipoFilial) {
		this.tipoFilial = tipoFilial;
	}

	public void setCnae(CnaeResponse cnae) {
		this.cnae = cnae;
	}

	public void setCodigoGps(CodigoPagamentoGpsResponse codigoGps) {
		this.codigoGps = codigoGps;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public void setEsferaOrgao(String esferaOrgao) {
		this.esferaOrgao = esferaOrgao;
	}

	public void setQtdProprietario(Integer qtdProprietario) {
		this.qtdProprietario = qtdProprietario;
	}

	public void setTipoEstabelecimento(String tipoEstabelecimento) {
		this.tipoEstabelecimento = tipoEstabelecimento;
	}

	public void setCapitalSocialAnual(Double capitalSocialAnual) {
		this.capitalSocialAnual = capitalSocialAnual;
	}

	public void setDataInicialAtividade(Instant dataInicialAtividade) {
		this.dataInicialAtividade = dataInicialAtividade;
	}

	public void setReferenciaContribuicao(String referenciaContribuicao) {
		this.referenciaContribuicao = referenciaContribuicao;
	}

	public void setAtividadePrimaria(String atividadePrimaria) {
		this.atividadePrimaria = atividadePrimaria;
	}

	public void setNaturezaEstabelecimento(String naturezaEstabelecimento) {
		this.naturezaEstabelecimento = naturezaEstabelecimento;
	}

	public void setBanco(BancoResponse banco) {
		this.banco = banco;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public void setFpas(Integer fpas) {
		this.fpas = fpas;
	}

	public void setCodigoGrpasAcidenteTrabalho(Integer codigoGrpasAcidenteTrabalho) {
		this.codigoGrpasAcidenteTrabalho = codigoGrpasAcidenteTrabalho;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setCodigoEmpregador(Integer codigoEmpregador) {
		this.codigoEmpregador = codigoEmpregador;
	}

	public void setPercentualEmpregador(Double percentualEmpregador) {
		this.percentualEmpregador = percentualEmpregador;
	}

	public void setSat(Double sat) {
		this.sat = sat;
	}

	public void setSalarioEducacao(Double salarioEducacao) {
		this.salarioEducacao = salarioEducacao;
	}

	public void setSenai(Double senai) {
		this.senai = senai;
	}

	public void setSesi(Double sesi) {
		this.sesi = sesi;
	}

	public void setSenac(Double senac) {
		this.senac = senac;
	}

	public void setSesc(Double sesc) {
		this.sesc = sesc;
	}

	public void setSebrae(Double sebrae) {
		this.sebrae = sebrae;
	}

	public void setSenar(Double senar) {
		this.senar = senar;
	}

	public void setSenat(Double senat) {
		this.senat = senat;
	}

	public void setSet(Double set) {
		this.set = set;
	}

	public void setSecoop(Double secoop) {
		this.secoop = secoop;
	}

	public void setDpc(Double dpc) {
		this.dpc = dpc;
	}

	public void setForcaAerea(Double forcaAerea) {
		this.forcaAerea = forcaAerea;
	}

	public void setFap(Integer fap) {
		this.fap = fap;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setUf(UnidadeFederativaResponse uf) {
		this.uf = uf;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setAnexo(AnexoResponse anexo) {
		this.anexo = anexo;
	}

	public void setTipoInscricao(String tipoInscricao) {
		this.tipoInscricao = tipoInscricao;
	}

	public MunicipioResponse getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioResponse municipio) {
		this.municipio = municipio;
	}

	public Integer getrNegativa() {
		return rNegativa;
	}

	public void setrNegativa(Integer rNegativa) {
		this.rNegativa = rNegativa;
	}

	public List<LotacaoResponse> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(List<LotacaoResponse> lotacoes) {
		this.lotacoes = lotacoes;
	}

	public Long getDefinicaoOrganicoId() {
		return definicaoOrganicoId;
	}

	public void setDefinicaoOrganicoId(Long definicaoOrganicoId) {
		this.definicaoOrganicoId = definicaoOrganicoId;
	}

	public NacionalidadeResponse getPais() {
		return pais;
	}

	public void setPais(NacionalidadeResponse pais) {
		this.pais = pais;
	}

	public String getCei() {
		return cei;
	}

	public void setCei(String cei) {
		this.cei = cei;
	}

	public Long getCnaeId() {
		return cnaeId;
	}

	public void setCnaeId(Long cnaeId) {
		this.cnaeId = cnaeId;
	}

	public List<EmpresaFilialResponse> getFiliais() {
		return filiais;
	}

	public void setFiliais(List<EmpresaFilialResponse> filiais) {
		this.filiais = filiais;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public Date getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Date vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public Date getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(Date vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public String getEmailResponsavel() {
		return emailResponsavel;
	}

	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}

	public Integer getCodigoConvenio() {
		return codigoConvenio;
	}

	public void setCodigoConvenio(Integer codigoConvenio) {
		this.codigoConvenio = codigoConvenio;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	public AgenciaResponse getAgenciaBancaria() {
		return agenciaBancaria;
	}

	public void setAgenciaBancaria(AgenciaResponse agenciaBancaria) {
		this.agenciaBancaria = agenciaBancaria;
	}

}
