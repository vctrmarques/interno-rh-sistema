package com.rhlinkcon.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Declaração Ex Servidor Dados Funcionais")
@Table(name = "declaracao_ex_servidor_dados_funcionais")
public class DeclaracaoExServidorDadosFuncionais extends UserDateAudit {

	private static final long serialVersionUID = 7767670928484839824L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "declaracao_ex_servidor_id")
	private DeclaracaoExServidor declaracaoExServidor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cargo_id", nullable = true)
	private Cargo cargo;

	@Column(name = "ato_nomeacao")
	private String atoNomeacao;

	@Column(name = "data_entrada")
	private LocalDate dataEntrada;

	@Column(name = "data_do_entrada")
	private LocalDate dataDiarioOficialEntrada;

	@Column(name = "data_encerramento")
	private LocalDate dataEncerramento;

	@Column(name = "ato_encerramento")
	private String atoEncerramento;

	@Column(name = "data_do_encerramento")
	private LocalDate dataDiarioOficialEncerramento;

	public DeclaracaoExServidorDadosFuncionais() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeclaracaoExServidor getDeclaracaoExServidor() {
		return declaracaoExServidor;
	}

	public void setDeclaracaoExServidor(DeclaracaoExServidor declaracaoExServidor) {
		this.declaracaoExServidor = declaracaoExServidor;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public String getAtoNomeacao() {
		return atoNomeacao;
	}

	public void setAtoNomeacao(String atoNomeacao) {
		this.atoNomeacao = atoNomeacao;
	}

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDate getDataDiarioOficialEntrada() {
		return dataDiarioOficialEntrada;
	}

	public void setDataDiarioOficialEntrada(LocalDate dataDiarioOficialEntrada) {
		this.dataDiarioOficialEntrada = dataDiarioOficialEntrada;
	}

	public LocalDate getDataEncerramento() {
		return dataEncerramento;
	}

	public void setDataEncerramento(LocalDate dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}

	public String getAtoEncerramento() {
		return atoEncerramento;
	}

	public void setAtoEncerramento(String atoEncerramento) {
		this.atoEncerramento = atoEncerramento;
	}

	public LocalDate getDataDiarioOficialEncerramento() {
		return dataDiarioOficialEncerramento;
	}

	public void setDataDiarioOficialEncerramento(LocalDate dataDiarioOficialEncerramento) {
		this.dataDiarioOficialEncerramento = dataDiarioOficialEncerramento;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
