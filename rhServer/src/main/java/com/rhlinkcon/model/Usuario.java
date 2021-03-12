package com.rhlinkcon.model;

import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.ExcludeFromAudit;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.usuario.UsuarioRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Usuário")
@Table(name = "usuario", uniqueConstraints = { @UniqueConstraint(columnNames = { "login" }) })
public class Usuario extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 40)
	private String nome;

	@NotBlank
	@Size(min = 11, max = 11)
	private String cpf;

	private Boolean ativo;

	@NotBlank
	@Size(max = 15)
	private String login;

	@Size(max = 100)
	@ExcludeFromAudit
	private String senha;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_papel", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "papel_id"))
	private Set<Papel> papeis = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_anexo")
	private Anexo anexo;

	@NotBlank
	@Size(max = 40)
	@Email
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_filial_id")
	@NotNull
	private EmpresaFilial empresaFilial;

	public Usuario() {}

	public Usuario(Long id) {
		this.id = id;
	}

	public Usuario(UsuarioRequest userRequest) {
		this.nome = userRequest.getNome();
		this.login = userRequest.getLogin();
		this.cpf = userRequest.getCpf();
		this.ativo = userRequest.getAtivo();
		this.email = userRequest.getEmail();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Papel> getPapeis() {
		return papeis;
	}

	public void setPapeis(Set<Papel> papeis) {
		this.papeis = papeis;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Anexo getAnexo() {
		return anexo;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EmpresaFilial getEmpresaFilial() {
		return empresaFilial;
	}

	public void setEmpresaFilial(EmpresaFilial empresaFilial) {
		this.empresaFilial = empresaFilial;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}