package com.rhlinkcon.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.medico.MedicoRequest;
import com.rhlinkcon.payload.telefone.TelefoneRequest;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Entity
//@EntityListeners(AuditListener.class)
//@AuditLabelClass(label = "Médico")
@Table(name = "medico")
@Getter
@Setter
public class Medico extends UserDateAudit {

	private static final long serialVersionUID = -3455539630090535955L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "matricula")
	private Integer matricula;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	private EmpresaFilial empresa;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "filial_id")
	private EmpresaFilial filial;
	
	@NotNull
	@NotBlank
	@Column(name = "nome")
	private String nome;
	
	@NotNull
	@Column(name = "status")
	private boolean status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "naturalidade_id")
	private Municipio naturalidade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nacionalidade_id")
	private Nacionalidade nacionalidade;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_civil")
	private EstadoCivilEnum estadoCivil;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "sexo")
	private GeneroEnum sexo;
	
	@NotNull
	@Column(name = "dt_nascimento")
	private Instant dataNascimento;
	
	@NotNull
	@Column(name = "nome_mae")
	private String nomeMae;

	@Column(name = "nome_pai")
	private String nomePai;
	
	@NotNull
	@Column(name = "coordenador_medico")
	private boolean coordenadorMedico;
	
	@NotNull
	@Column(name = "clinico_geral")
	private boolean clinicoGeral;
	
	@NotNull
	@Column(name = "especialista")
	private boolean especialista;

	@NotNull
	@Column(name = "rg")
	private String identidade;
	
	@NotNull
	@Column(name = "orgao_expeditor")
	private String orgaoExpeditor;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rg_uf_id")
	private UnidadeFederativa rgUf;
	
	@NotNull
	@Column(name = "dt_expedicao_rg")
	private Instant dataExpedicaoRg;
	
	@NotNull
	@Size(min = 11, max = 11)
	@Column(name = "cpf")
	private String cpf;
	
	@NotNull
	@Column(name = "crm")
	private Integer crm;
	
	@NotNull
	@Column(name = "dt_expedicao_crm")
	private Instant dataExpedicaoCrm;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "crm_uf_id")
	private UnidadeFederativa crmUf;
	
	@NotNull
	@Column(name = "logradouro")
	private String logradouro;

	@NotNull
	@Column(name = "numero")
	private String numero;

	@Column(name = "complemento")
	private String complemento;

	@NotNull
	@Column(name = "bairro")
	private String bairro;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uf_id")
	private UnidadeFederativa enderecoUf;
	 
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_municipio_id")
	private Municipio municipio;
	
	@NotNull
	@Size(min = 8, max = 8)
	@Column(name = "cep")
	private String cep;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@ElementCollection
    @CollectionTable(name = "medico_email", joinColumns = @JoinColumn(name = "medico_id"))
    @Column(name = "email")
    private List<String> emails;
	
	@ManyToMany
	@JoinTable(name = "medico_telefone", joinColumns = {
			@JoinColumn(name = "medico_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "telefone_id", referencedColumnName = "id") })
	private List<Telefone> telefones;
	
	@ManyToMany
	@JoinTable(name = "medico_especialidade_medica", joinColumns = {
			@JoinColumn(name = "medico_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "especialidade_medica_id", referencedColumnName = "id") })
	private List<EspecialidadeMedica> especialidadesMedicas;
	
	@Override
	public String getLabel() {
		return null;
	}
	
	public Medico() {
	}

	public Medico(Long id) {
		this.id = id;
	}
	
	public Medico(MedicoRequest medicoRequest) {
		
		// Aba de Dados Cadastrais
		this.setMatricula(medicoRequest.getMatricula());
		this.setNome(medicoRequest.getNome());
		this.setEmpresa(new EmpresaFilial(medicoRequest.getEmpresaId()));
		this.setFilial(new EmpresaFilial(medicoRequest.getFilialId()));
		this.setEstadoCivil(EstadoCivilEnum.valueOf(medicoRequest.getEstadoCivil()));
		this.setSexo(GeneroEnum.valueOf(medicoRequest.getSexo()));
		this.setDataNascimento(medicoRequest.getDataNascimento());
		this.setNomeMae(medicoRequest.getNomeMae());
		this.setNomePai(medicoRequest.getNomePai());
		this.setCoordenadorMedico(medicoRequest.isCoordenadorMedico());
		this.setClinicoGeral(medicoRequest.isClinicoGeral());
		this.setEspecialista(medicoRequest.isEspecialista());
		
		//Aba Documentação
		this.setCpf(medicoRequest.getCpf());
		this.setOrgaoExpeditor(medicoRequest.getOrgaoExpeditor());
		this.setDataExpedicaoRg(medicoRequest.getDataExpedicaoRg());
		this.setIdentidade(medicoRequest.getIdentidade());
		this.setCrm(medicoRequest.getCrm());
		this.setDataExpedicaoCrm(medicoRequest.getDataExpedicaoCrm());

		//Aba Contato & Endereço	
		this.setLogradouro(medicoRequest.getLogradouro());
		this.setNumero(medicoRequest.getNumero());
		this.setComplemento(medicoRequest.getComplemento());
		this.setBairro(medicoRequest.getBairro());
		this.setCep(medicoRequest.getCep());
		
		if (Objects.nonNull(medicoRequest.getEmails())) {
			this.setEmails(medicoRequest.getEmails());
		}
		
		if (Objects.nonNull(medicoRequest.getTelefones())) {
			setTelefones(new ArrayList<>());
			for (TelefoneRequest tr : medicoRequest.getTelefones()) {
				Telefone t = new Telefone(tr);
				getTelefones().add(t);
			}
		}
	}
	
}
