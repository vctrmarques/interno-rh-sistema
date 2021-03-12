package com.rhlinkcon.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Recadastramento")
@Table(name = "recadastramento")
public class Recadastramento extends UserDateAudit {

	private static final long serialVersionUID = -8447864143961269676L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", nullable = true)
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pensionista_id", nullable = true)
	private Pensionista pensao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dados_id", nullable = true)
	private RecadastramentoDados dados;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_id", nullable = true)
	private RecadastramentoEndereco endereco;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contato_id", nullable = true)
	private RecadastramentoContato contato;

	@NotNull
	private LocalDate data;

	private boolean status;

	@ManyToMany
	@JoinTable(name = "recadastramento_anexo", joinColumns = {
			@JoinColumn(name = "recadastramento_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "anexo_id", referencedColumnName = "id") })
	private List<Anexo> anexos;

	public Recadastramento() {
	}

	public Recadastramento(Long id) {
		this.id = id;
		this.data = LocalDate.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Pensionista getPensao() {
		return pensao;
	}

	public void setPensao(Pensionista pensao) {
		this.pensao = pensao;
	}

	public RecadastramentoDados getDados() {
		return dados;
	}

	public void setDados(RecadastramentoDados dados) {
		this.dados = dados;
	}

	public RecadastramentoEndereco getEndereco() {
		return endereco;
	}

	public void setEndereco(RecadastramentoEndereco endereco) {
		this.endereco = endereco;
	}

	public RecadastramentoContato getContato() {
		return contato;
	}

	public void setContato(RecadastramentoContato contato) {
		this.contato = contato;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
