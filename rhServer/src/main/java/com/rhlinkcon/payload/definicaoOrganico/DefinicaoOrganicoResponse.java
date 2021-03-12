package com.rhlinkcon.payload.definicaoOrganico;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.DefinicaoOrganico;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;

public class DefinicaoOrganicoResponse extends DadoCadastralResponse {

	private Long id;

	@NotNull
	private EmpresaFilialResponse empresaFilial;
	
	private List<LotacaoResponse> lotacoes;
	
	@NotNull
	
	private String primeiroSubstitutoNome;
	
	private String primeiroSubstitutoEmail;	

	private String segundoSubstitutoNome;	
	
	private String segundoSubstitutoEmail;
	
	private Long previsaoFuncionarios;	
	
	private Long funcionariosAtuais;	
	
	private Long totalFuncionarios;	
	
	private BigDecimal previsaoCustos;	
	
	private BigDecimal custosAtuais;	
	
	private BigDecimal custoTotal;
	
	private Boolean confCriticaAvisar;	
	
	private Boolean confCriticaCriticar;	
	
	private Boolean confCriticaNenhum;

	public DefinicaoOrganicoResponse(DefinicaoOrganico definicaoOrganico) {
		setDefinicaoOrganico(definicaoOrganico);
	}

	public DefinicaoOrganicoResponse(DefinicaoOrganico definicaoOrganico, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setDefinicaoOrganico(definicaoOrganico);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setDefinicaoOrganico(DefinicaoOrganico definicaoOrganico) {
		this.setId(definicaoOrganico.getId());
		if(Objects.nonNull(definicaoOrganico.getEmpresaFilial()))
			this.setEmpresaFilial(new EmpresaFilialResponse(definicaoOrganico.getEmpresaFilial()));		
		
		
		this.setPrimeiroSubstitutoNome(definicaoOrganico.getPrimeiroSubstitutoNome());
		this.setPrimeiroSubstitutoEmail(definicaoOrganico.getPrimeiroSubstitutoEmail());
		this.setSegundoSubstitutoNome(definicaoOrganico.getSegundoSubstitutoNome());
		this.setSegundoSubstitutoEmail(definicaoOrganico.getSegundoSubstitutoEmail());
		this.setPrevisaoFuncionarios(definicaoOrganico.getPrevisaoFuncionarios());
		this.setFuncionariosAtuais(definicaoOrganico.getFuncionariosAtuais());
		this.setTotalFuncionarios(definicaoOrganico.getTotalFuncionarios());
		this.setPrevisaoCustos(definicaoOrganico.getPrevisaoCustos());
		this.setCustosAtuais(definicaoOrganico.getCustosAtuais());
		this.setCustoTotal(definicaoOrganico.getCustoTotal());
		this.setConfCriticaAvisar(definicaoOrganico.getConfCriticaAvisar());
		this.setConfCriticaCriticar(definicaoOrganico.getConfCriticaCriticar());	
		this.setConfCriticaNenhum(definicaoOrganico.getConfCriticaNenhum());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmpresaFilialResponse getEmpresaFilial() {
		return empresaFilial;
	}

	public void setEmpresaFilial(EmpresaFilialResponse empresaFilial) {
		this.empresaFilial = empresaFilial;
	}
	
	

	public List<LotacaoResponse> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(List<LotacaoResponse> lotacoes) {
		this.lotacoes = lotacoes;
	}

	public String getPrimeiroSubstitutoNome() {
		return primeiroSubstitutoNome;
	}

	public void setPrimeiroSubstitutoNome(String primeiroSubstitutoNome) {
		this.primeiroSubstitutoNome = primeiroSubstitutoNome;
	}

	public String getPrimeiroSubstitutoEmail() {
		return primeiroSubstitutoEmail;
	}

	public void setPrimeiroSubstitutoEmail(String primeiroSubstitutoEmail) {
		this.primeiroSubstitutoEmail = primeiroSubstitutoEmail;
	}

	public String getSegundoSubstitutoNome() {
		return segundoSubstitutoNome;
	}

	public void setSegundoSubstitutoNome(String segundoSubstitutoNome) {
		this.segundoSubstitutoNome = segundoSubstitutoNome;
	}

	public String getSegundoSubstitutoEmail() {
		return segundoSubstitutoEmail;
	}

	public void setSegundoSubstitutoEmail(String segundoSubstitutoEmail) {
		this.segundoSubstitutoEmail = segundoSubstitutoEmail;
	}

	public Long getPrevisaoFuncionarios() {
		return previsaoFuncionarios;
	}

	public void setPrevisaoFuncionarios(Long previsaoFuncionarios) {
		this.previsaoFuncionarios = previsaoFuncionarios;
	}

	public Long getFuncionariosAtuais() {
		return funcionariosAtuais;
	}

	public void setFuncionariosAtuais(Long funcionariosAtuais) {
		this.funcionariosAtuais = funcionariosAtuais;
	}

	public Long getTotalFuncionarios() {
		return totalFuncionarios;
	}

	public void setTotalFuncionarios(Long totalFuncionarios) {
		this.totalFuncionarios = totalFuncionarios;
	}

	public BigDecimal getPrevisaoCustos() {
		return previsaoCustos;
	}

	public void setPrevisaoCustos(BigDecimal previsaoCustos) {
		this.previsaoCustos = previsaoCustos;
	}

	public BigDecimal getCustosAtuais() {
		return custosAtuais;
	}

	public void setCustosAtuais(BigDecimal custosAtuais) {
		this.custosAtuais = custosAtuais;
	}

	public BigDecimal getCustoTotal() {
		return custoTotal;
	}

	public void setCustoTotal(BigDecimal custoTotal) {
		this.custoTotal = custoTotal;
	}

	public Boolean getConfCriticaAvisar() {
		return confCriticaAvisar;
	}

	public void setConfCriticaAvisar(Boolean confCriticaAvisar) {
		this.confCriticaAvisar = confCriticaAvisar;
	}

	public Boolean getConfCriticaCriticar() {
		return confCriticaCriticar;
	}

	public void setConfCriticaCriticar(Boolean confCriticaCriticar) {
		this.confCriticaCriticar = confCriticaCriticar;
	}

	public Boolean getConfCriticaNenhum() {
		return confCriticaNenhum;
	}

	public void setConfCriticaNenhum(Boolean confCriticaNenhum) {
		this.confCriticaNenhum = confCriticaNenhum;
	}

	
	

}
