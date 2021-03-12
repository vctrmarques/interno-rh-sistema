package com.rhlinkcon.model;

import java.util.List;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Paciente Perícia Médica")
@Table(name = "paciente_pericia_medica")
public class PacientePericiaMedica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;
	
	@Column(name = "numero_processo")
	private Long numeroProcesso;
	
	@NotNull
	@NotBlank
	private String cid;
	
	@NotNull
	@NotBlank
	private String cpf;
	
	@NotNull
	@NotBlank
	private String nome;
	
	@Column(name = "contador_nao_compareceu")
	private Integer contadorComparecimento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_analise")
	private TipoAnaliseEnum tipoAnalise;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusAgendamentoPericiaEnum status;
	
	@ElementCollection
	@OneToMany(mappedBy = "pacientePericiaMedica", orphanRemoval = true)
	private List<ConsultaPericiaMedica> consultasPericiasMedicas;
	
	@ElementCollection
	@OneToMany(mappedBy = "pacientePericiaMedica", orphanRemoval = true)
	private List<PacientePericiaTelefone> pacientePericiaTelefone;
	
	public PacientePericiaMedica() {}
	
	public PacientePericiaMedica(Long id) {
		this.id = id;
	}

}
