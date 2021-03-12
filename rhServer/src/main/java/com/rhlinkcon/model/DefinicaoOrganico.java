package com.rhlinkcon.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.definicaoOrganico.DefinicaoOrganicoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Definição Orgânico")
@Table(name = "definicao_de_organico")
public class DefinicaoOrganico extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_filial_id")
	private EmpresaFilial empresaFilial;

	@Column(name = "primeiro_substituto_nome")
	private String primeiroSubstitutoNome;

	@Column(name = "primeiro_substituto_email")
	private String primeiroSubstitutoEmail;

	@Column(name = "segundo_substituto_nome")
	private String segundoSubstitutoNome;

	@Column(name = "segundo_substituto_email")
	private String segundoSubstitutoEmail;

	@Column(name = "previsao_funcionarios")
	private Long previsaoFuncionarios;

	@Column(name = "funcionarios_atuais")
	private Long funcionariosAtuais;

	@Column(name = "total_funcionarios")
	private Long totalFuncionarios;

	@Column(name = "previsao_custos")
	private BigDecimal previsaoCustos;

	@Column(name = "custos_atuais")
	private BigDecimal custosAtuais;

	@Column(name = "custo_total")
	private BigDecimal custoTotal;

	@Column(name = "conf_critica_avisar")
	private Boolean confCriticaAvisar;

	@Column(name = "conf_critica_criticar")
	private Boolean confCriticaCriticar;

	@Column(name = "conf_critica_nenhum")
	private Boolean confCriticaNenhum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmpresaFilial getEmpresaFilial() {
		return empresaFilial;
	}

	public void setEmpresaFilial(EmpresaFilial empresaFilial) {
		this.empresaFilial = empresaFilial;
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

	public DefinicaoOrganico() {

	}

	public DefinicaoOrganico(DefinicaoOrganicoRequest definicaoOrganicooRequest) {

		this.setPrimeiroSubstitutoNome(definicaoOrganicooRequest.getPrimeiroSubstitutoNome());
		this.setPrimeiroSubstitutoEmail(definicaoOrganicooRequest.getPrimeiroSubstitutoEmail());
		this.setSegundoSubstitutoNome(definicaoOrganicooRequest.getSegundoSubstitutoNome());
		this.setSegundoSubstitutoEmail(definicaoOrganicooRequest.getSegundoSubstitutoEmail());
		this.setPrevisaoFuncionarios(definicaoOrganicooRequest.getPrevisaoFuncionarios());
		this.setFuncionariosAtuais(definicaoOrganicooRequest.getFuncionariosAtuais());
		this.setTotalFuncionarios(definicaoOrganicooRequest.getTotalFuncionarios());
		this.setPrevisaoCustos(definicaoOrganicooRequest.getPrevisaoCustos());
		this.setCustosAtuais(definicaoOrganicooRequest.getCustosAtuais());
		this.setCustoTotal(definicaoOrganicooRequest.getCustoTotal());
		this.setConfCriticaAvisar(definicaoOrganicooRequest.getConfCriticaAvisar());
		this.setConfCriticaCriticar(definicaoOrganicooRequest.getConfCriticaCriticar());
		this.setConfCriticaNenhum(definicaoOrganicooRequest.getConfCriticaNenhum());
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
