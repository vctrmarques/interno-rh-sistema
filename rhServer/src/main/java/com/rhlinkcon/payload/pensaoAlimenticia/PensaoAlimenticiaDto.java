package com.rhlinkcon.payload.pensaoAlimenticia;

import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.model.PensaoAlimenticia;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.generico.DadoBancarioDto;
import com.rhlinkcon.payload.generico.EnderecoDto;
import com.rhlinkcon.util.Projecao;

public class PensaoAlimenticiaDto extends DadoCadastralResponse {

	// Bloco Inicial

	private Long id;

	@NotNull
	private PensaoAlimenticiaFuncionarioDto funcionario;

	// Bloco do Alimentando

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String nomeAlimentando;

	@NotNull
	private Date nascimentoAlimentando;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String tipoPensao;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String rg;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String orgao;

	@NotNull
	private DadoBasicoDto estadoDocumento;

	@NotNull
	@NotBlank
	@Size(min = 11, max = 11)
	private String cpf;

	@Size(max = 255)
	private String numeroTelefone;

	@NotNull
	private EnderecoDto enderecoAlimentando;

	// Bloco do Responsável Legal

	private PensaoAlimenticiaResponsavelDto responsavel;

	@Size(max = 255)
	private String numeroProcessoResponsavel;

	private Date dataInicial;

	private Date dataFinal;

	// Bloco de Pagamento Parte 1

	@NotNull
	private Date dataInicialPagamento;

	private Date dataFinalPagamento;

	@NotNull
	private DadoBasicoDto centroCusto;

	@NotNull
	private DadoBasicoDto verba;

	@NotNull
	private DadoBancarioDto dadoBancario;

	// Bloco de Pagamento Parte 2

	@NotNull
	@Size(max = 255)
	private String numeroProcessoPagamento;

	@NotNull
	private Date vencimento;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String tipoValor;

	@NotNull
	private Double valor;

	private Long salarioFamilia;

	private String tipoIncidenciaPrincipalPensao;

	private boolean salario13;

	private boolean ferias;

	private boolean recisao;

	public PensaoAlimenticiaDto() {

	}

	public PensaoAlimenticiaDto(PensaoAlimenticia pensaoAlimenticia) {
		setDto(pensaoAlimenticia, Projecao.COMPLETA);
	}

	public PensaoAlimenticiaDto(PensaoAlimenticia pensaoAlimenticia, Projecao projecao) {
		setDto(pensaoAlimenticia, projecao);

	}

	private void setDto(PensaoAlimenticia pensao, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.id = pensao.getId();
			this.nomeAlimentando = pensao.getNomeAlimentando();
			this.nascimentoAlimentando = pensao.getNascimentoAlimentando();
			if (!Objects.isNull(pensao.getTipoPensao()))
				this.tipoPensao = pensao.getTipoPensao().getLabel();
			this.dataInicialPagamento = pensao.getDataInicialPagamento();
			this.dataFinalPagamento = pensao.getDataFinalPagamento();
			this.setCpf(pensao.getCpf());

			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {

				// Bloco Inicial

				this.setFuncionario(new PensaoAlimenticiaFuncionarioDto(pensao.getFuncionario()));

				// Bloco do Alimentando

				this.setNomeAlimentando(pensao.getNomeAlimentando());
				this.setNascimentoAlimentando(pensao.getNascimentoAlimentando());
				this.setTipoPensao(pensao.getTipoPensao().getLabel());
				this.setRg(pensao.getRg());
				this.setOrgao(pensao.getOrgao());
				this.setEstadoDocumento(new DadoBasicoDto(pensao.getEstadoDocumento()));
				this.setCpf(pensao.getCpf());
				this.setNumeroTelefone(pensao.getNumeroTelefone());
				this.setEnderecoAlimentando(new EnderecoDto(pensao.getEnderecoAlimentando()));

				// Bloco do Responsável Legal

				if (Objects.nonNull(pensao.getResponsavel()) && !pensao.getResponsavel().getId().equals(0L)) {
					this.setResponsavel(new PensaoAlimenticiaResponsavelDto(pensao.getResponsavel()));
					this.setNumeroProcessoResponsavel(pensao.getNumeroProcessoResponsavel());
					this.setDataInicial(pensao.getDataInicial());
					this.setDataFinal(pensao.getDataFinal());
				}

				if (projecao.equals(Projecao.COMPLETA)) {

					// Bloco de Pagamento Parte 1

					this.setDataInicialPagamento(pensao.getDataInicialPagamento());
					this.setDataFinalPagamento(pensao.getDataFinalPagamento());
					this.setCentroCusto(new DadoBasicoDto(pensao.getCentroCusto()));
					this.setVerba(new DadoBasicoDto(pensao.getVerba()));
					this.setDadoBancario(new DadoBancarioDto(pensao.getDadoBancario()));

					// Bloco de Pagamento Parte 2

					this.setNumeroProcessoPagamento(pensao.getNumeroProcessoPagamento());
					this.setVencimento(pensao.getVencimento());
					this.setTipoValor(pensao.getTipoValor().getLabel());
					this.setValor(pensao.getValor());
					this.setSalarioFamilia(pensao.getSalarioFamilia());
					if (Objects.nonNull(pensao.getTipoIncidenciaPrincipalPensao()))
						this.setTipoIncidenciaPrincipalPensao(pensao.getTipoIncidenciaPrincipalPensao().getLabel());
					this.setSalario13(pensao.isSalario13());
					this.setFerias(pensao.isFerias());
					this.setRecisao(pensao.isRecisao());

				}
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PensaoAlimenticiaFuncionarioDto getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(PensaoAlimenticiaFuncionarioDto funcionario) {
		this.funcionario = funcionario;
	}

	public String getNomeAlimentando() {
		return nomeAlimentando;
	}

	public void setNomeAlimentando(String nomeAlimentando) {
		this.nomeAlimentando = nomeAlimentando;
	}

	public Date getNascimentoAlimentando() {
		return nascimentoAlimentando;
	}

	public void setNascimentoAlimentando(Date nascimentoAlimentando) {
		this.nascimentoAlimentando = nascimentoAlimentando;
	}

	public String getTipoPensao() {
		return tipoPensao;
	}

	public void setTipoPensao(String tipoPensao) {
		this.tipoPensao = tipoPensao;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getOrgao() {
		return orgao;
	}

	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}

	public DadoBasicoDto getEstadoDocumento() {
		return estadoDocumento;
	}

	public void setEstadoDocumento(DadoBasicoDto estadoDocumento) {
		this.estadoDocumento = estadoDocumento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}

	public EnderecoDto getEnderecoAlimentando() {
		return enderecoAlimentando;
	}

	public void setEnderecoAlimentando(EnderecoDto enderecoAlimentando) {
		this.enderecoAlimentando = enderecoAlimentando;
	}

	public PensaoAlimenticiaResponsavelDto getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(PensaoAlimenticiaResponsavelDto responsavel) {
		this.responsavel = responsavel;
	}

	public String getNumeroProcessoResponsavel() {
		return numeroProcessoResponsavel;
	}

	public void setNumeroProcessoResponsavel(String numeroProcessoResponsavel) {
		this.numeroProcessoResponsavel = numeroProcessoResponsavel;
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

	public Date getDataInicialPagamento() {
		return dataInicialPagamento;
	}

	public void setDataInicialPagamento(Date dataInicialPagamento) {
		this.dataInicialPagamento = dataInicialPagamento;
	}

	public Date getDataFinalPagamento() {
		return dataFinalPagamento;
	}

	public void setDataFinalPagamento(Date dataFinalPagamento) {
		this.dataFinalPagamento = dataFinalPagamento;
	}

	public DadoBasicoDto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(DadoBasicoDto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public DadoBasicoDto getVerba() {
		return verba;
	}

	public void setVerba(DadoBasicoDto verba) {
		this.verba = verba;
	}

	public DadoBancarioDto getDadoBancario() {
		return dadoBancario;
	}

	public void setDadoBancario(DadoBancarioDto dadoBancario) {
		this.dadoBancario = dadoBancario;
	}

	public String getNumeroProcessoPagamento() {
		return numeroProcessoPagamento;
	}

	public void setNumeroProcessoPagamento(String numeroProcessoPagamento) {
		this.numeroProcessoPagamento = numeroProcessoPagamento;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Long getSalarioFamilia() {
		return salarioFamilia;
	}

	public void setSalarioFamilia(Long salarioFamilia) {
		this.salarioFamilia = salarioFamilia;
	}

	public String getTipoIncidenciaPrincipalPensao() {
		return tipoIncidenciaPrincipalPensao;
	}

	public void setTipoIncidenciaPrincipalPensao(String tipoIncidenciaPrincipalPensao) {
		this.tipoIncidenciaPrincipalPensao = tipoIncidenciaPrincipalPensao;
	}

	public boolean isSalario13() {
		return salario13;
	}

	public void setSalario13(boolean salario13) {
		this.salario13 = salario13;
	}

	public boolean isFerias() {
		return ferias;
	}

	public void setFerias(boolean ferias) {
		this.ferias = ferias;
	}

	public boolean isRecisao() {
		return recisao;
	}

	public void setRecisao(boolean recisao) {
		this.recisao = recisao;
	}

	public String getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

}
