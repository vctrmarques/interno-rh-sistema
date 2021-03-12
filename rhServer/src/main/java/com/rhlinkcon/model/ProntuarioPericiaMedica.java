package com.rhlinkcon.model;

import java.time.LocalDate;

import javax.persistence.Column;
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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.controller.ProntuarioPericiaMedicaDto;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Prontuário Perícia Médica")
@Table(name = "prontuario_pericia_medica")
public class ProntuarioPericiaMedica extends UserDateAudit{

	private static final long serialVersionUID = -4057947257844545457L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente_pericia_medica_id")
	private PacientePericiaMedica pacientePericiaMedica;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consulta_pericia_medica_id")
	private ConsultaPericiaMedica consultaPericiaMedica;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "especialidade_medica_id")
	private EspecialidadeMedica especialidadeMedica;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medico_id")
	private Medico medico;
	
	@NotNull
	@Column(name = "numero_pericia")
	private Integer numeroPericia;
	
	@NotNull
	@Column(name = "ano_pericia")
	private Integer anoPericia;
	
	@NotNull
	@NotBlank
	@Column(name = "motivo_pericia")
	private String motivoPericia;
	
	@NotNull
	@NotBlank
	@Column(name = "cid")
	private String cid;
	
	@NotNull
	@NotBlank
	@Column(name = "hda")
	private String hda;
	
	@NotNull
	@NotBlank
	@Column(name = "exame_fisico")
	private String exameFisico;
	
	@NotNull
	@NotBlank
	@Column(name = "diagnostico")
	private String diagnostico;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "acao")
	private TipoAcaoPericiaMedicaEnum acao;
	
	@Column(name = "data_proxima_pericia")
	private LocalDate dataProximaPericia;
	
	@Column(name = "dias_proxima_consulta")
	private Integer diasProximaConsulta;
	
	@NotNull
	@NotBlank
	@Column(name = "observacao_medico")
	private String observacaoMedico;
	
	@NotNull
	@Column(name = "laudo")
	private boolean laudo;
	
	public ProntuarioPericiaMedica() {}
	
	public ProntuarioPericiaMedica(Long id) {
		this.id = id;
	}
	
	public ProntuarioPericiaMedica(ProntuarioPericiaMedicaDto dto) {
		setId(dto.getId());
		setPacientePericiaMedica(new PacientePericiaMedica(dto.getPacientePericiaMedicaId()));
		setConsultaPericiaMedica(new ConsultaPericiaMedica(dto.getConsultaPericiaMedicaId()));
		setEspecialidadeMedica(new EspecialidadeMedica(dto.getEspecialidadeMedicaId()));
		setMedico(new Medico(dto.getMedicoId()));
		setNumeroPericia(dto.getNumeroPericia());
		setAnoPericia(dto.getAnoPericia());
		setMotivoPericia(dto.getMotivoPericia());
		setCid(dto.getCid());
		setHda(dto.getHda());		
		setExameFisico(dto.getExameFisico());
		setDiagnostico(dto.getDiagnostico());
		setAcao(TipoAcaoPericiaMedicaEnum.getEnumByString(dto.getAcao()));
		setDataProximaPericia(dto.getDataProximaPericia());
		setDiasProximaConsulta(dto.getDiasProximaConsulta());
		setObservacaoMedico(dto.getObservacaoMedico());
		setLaudo(dto.isLaudo());
		
	}
	
	@Override
	public String getLabel() {
		return null;
	}
}
