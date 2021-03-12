package com.rhlinkcon.payload.contracheque;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.lancamento.LancamentoResponse;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

public class ContrachequeResponse extends DadoCadastralResponse {

	private Long id;

	private FolhaPagamentoResponse folhaPagamento;

	private FuncionarioResponse funcionario;

	private Double valorBruto;

	private Double valorLiquido;

	private Double valorDesconto;

	private String situacao;

	private List<LancamentoResponse> lancamentosVantagens;

	private List<LancamentoResponse> lancamentosDescontos;

	private List<LancamentoResponse> lancamentosOutros;

	private String nome;

	private Integer matricula;

	private String lotacao;

	private String municipio;

	private Instant dataAdmissao;

	private String cargoEfetivo;

	private Instant dataNascimento;

	private String nivel;

	private String cargoFuncao;

	private String referencia;

	private String vinculo;

	private String tipoSituacaoFuncional;

	private String identidade;

	private String cpf;

	private String orgaoPagador;

	private Integer depSf;

	private Integer depIr;

	private String cargaHoraria;

	private String feedback;
	
	public ContrachequeResponse(Contracheque contracheque) {
		this.id = contracheque.getId();
		this.folhaPagamento = new FolhaPagamentoResponse(contracheque.getFolhaPagamento());

		if (Objects.nonNull(contracheque.getFuncionario()))
			this.funcionario = new FuncionarioResponse(contracheque.getFuncionario(), Projecao.BASICA);

		this.valorBruto = contracheque.getValorBruto();
		this.valorDesconto = Utils.roundValue(contracheque.getValorDesconto());
		this.valorLiquido = Utils.roundValue(contracheque.getValorLiquido());
		this.situacao = contracheque.getSituacao().getLabel();

		this.nome = contracheque.getNome();
		this.matricula = contracheque.getMatricula();
		this.lotacao = contracheque.getLotacao();
		this.municipio = contracheque.getMunicipio();
		this.dataAdmissao = contracheque.getDataAdmissao();
		this.cargoEfetivo = contracheque.getCargoEfetivo();
		this.dataNascimento = contracheque.getDataNascimento();
		this.nivel = contracheque.getNivel();
		this.cargoFuncao = contracheque.getCargoFuncao();
		this.referencia = contracheque.getReferencia();
		this.vinculo = contracheque.getVinculo();
		this.tipoSituacaoFuncional = contracheque.getTipoSituacaoFuncional();
		this.identidade = contracheque.getIdentidade();
		this.cpf = contracheque.getCpf();
		this.orgaoPagador = contracheque.getOrgaoPagador();
		this.depSf = contracheque.getDepSf();
		this.depIr = contracheque.getDepIr();
		this.cargaHoraria = contracheque.getCargaHoraria();
		this.feedback = contracheque.getFeedback();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getLotacao() {
		return lotacao;
	}

	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Instant getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Instant dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public String getCargoEfetivo() {
		return cargoEfetivo;
	}

	public void setCargoEfetivo(String cargoEfetivo) {
		this.cargoEfetivo = cargoEfetivo;
	}

	public Instant getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Instant dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getCargoFuncao() {
		return cargoFuncao;
	}

	public void setCargoFuncao(String cargoFuncao) {
		this.cargoFuncao = cargoFuncao;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}

	public String getTipoSituacaoFuncional() {
		return tipoSituacaoFuncional;
	}

	public void setTipoSituacaoFuncional(String tipoSituacaoFuncional) {
		this.tipoSituacaoFuncional = tipoSituacaoFuncional;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getOrgaoPagador() {
		return orgaoPagador;
	}

	public void setOrgaoPagador(String orgaoPagador) {
		this.orgaoPagador = orgaoPagador;
	}

	public Integer getDepSf() {
		return depSf;
	}

	public void setDepSf(Integer depSf) {
		this.depSf = depSf;
	}

	public Integer getDepIr() {
		return depIr;
	}

	public void setDepIr(Integer depIr) {
		this.depIr = depIr;
	}

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FolhaPagamentoResponse getFolhaPagamento() {
		return folhaPagamento;
	}

	public void setFolhaPagamento(FolhaPagamentoResponse folhaPagamento) {
		this.folhaPagamento = folhaPagamento;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public Double getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(Double valorBruto) {
		this.valorBruto = valorBruto;
	}

	public Double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public List<LancamentoResponse> getLancamentosOutros() {
		return lancamentosOutros;
	}

	public void setLancamentosOutros(List<LancamentoResponse> lancamentosOutros) {
		this.lancamentosOutros = lancamentosOutros;
	}

	public List<LancamentoResponse> getLancamentosVantagens() {
		return lancamentosVantagens;
	}

	public void setLancamentosVantagens(List<LancamentoResponse> lancamentosVantagens) {
		this.lancamentosVantagens = lancamentosVantagens;
	}

	public List<LancamentoResponse> getLancamentosDescontos() {
		return lancamentosDescontos;
	}

	public void setLancamentosDescontos(List<LancamentoResponse> lancamentosDescontos) {
		this.lancamentosDescontos = lancamentosDescontos;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}
