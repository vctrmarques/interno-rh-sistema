package com.rhlinkcon.controller;

import java.time.Instant;
import java.time.LocalDate;

import com.rhlinkcon.model.ConsultaPericiaMedica;
import com.rhlinkcon.model.PacientePericiaMedica;
import com.rhlinkcon.model.ProntuarioPericiaMedica;
import com.rhlinkcon.model.TipoAcaoPericiaMedicaEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProntuarioPericiaMedicaDto {

	private Long id;
	
	private String nomePaciente;

	private String nomeMae;
	
	private char sexo;
	
	private Long pacientePericiaMedicaId;
	
	private Long consultaPericiaMedicaId;
	
	private Long especialidadeMedicaId;
	
	private Long medicoId;

	private Integer numeroPericia;
	
	private Integer anoPericia;
	
	private String motivoPericia;
	
	private String cid;
	
	private String hda;
	
	private String exameFisico;
	
	private String diagnostico;
	
	private String acao;
	
	private LocalDate dataProximaPericia;
	
	private String periodoProximaPericia;
	
	private Integer diasProximaConsulta;
	
	private String observacaoMedico;
	
	private Instant dataInicioAposentadoria;
	
	private String perito;
	
	private boolean laudo;
	
	private Instant dataNascimento;
	
	private TipoAcaoPericiaMedicaEnum resultado;
	
	private ConsultaPericiaMedica consultaPericiaMedica;
	
	public ProntuarioPericiaMedicaDto() {}

	public ProntuarioPericiaMedicaDto(ProntuarioPericiaMedica obj) {
		setId(obj.getId());
		setNumeroPericia(obj.getNumeroPericia());
		setDataInicioAposentadoria(obj.getUpdatedAt());
		setDataProximaPericia(obj.getDataProximaPericia());
		setPerito(obj.getMedico().getNome());
		if(obj.getAcao().equals(TipoAcaoPericiaMedicaEnum.APONSENTAR)){
			setDataProximaPericia(obj.getDataProximaPericia());
		}
		setMotivoPericia(obj.getMotivoPericia());
		setCid(obj.getCid());
		setLaudo(obj.isLaudo());
		setAcao(obj.getAcao().toString());
		
		setAnoPericia(obj.getAnoPericia());
		setDataProximaPericia(obj.getDataProximaPericia());
		setDiasProximaConsulta(obj.getDiasProximaConsulta());
		setMotivoPericia(obj.getMotivoPericia());
		setDiagnostico(obj.getDiagnostico());
		setExameFisico(obj.getExameFisico());
		setHda(obj.getHda());
		setObservacaoMedico(obj.getObservacaoMedico());
		
		if(obj.getAcao().equals(TipoAcaoPericiaMedicaEnum.APONSENTAR) || obj.getAcao().equals(TipoAcaoPericiaMedicaEnum.DESAPOSENTAR) 
				|| obj.getAcao().equals(TipoAcaoPericiaMedicaEnum.NAO_APONSETAR)){
			setResultado(obj.getAcao());
		}
		setConsultaPericiaMedicaId(obj.getConsultaPericiaMedica().getId());
		setPacientePericiaMedicaId(obj.getPacientePericiaMedica().getId());
		setEspecialidadeMedicaId(obj.getEspecialidadeMedica().getId());
		setNomePaciente(obj.getConsultaPericiaMedica().getPacientePericiaMedica().getNome());
		setSexo(obj.getConsultaPericiaMedica().getPacientePericiaMedica().getFuncionario().getSexo());
		setDataNascimento(obj.getConsultaPericiaMedica().getPacientePericiaMedica().getFuncionario().getDataNascimento());
		setNomeMae(obj.getConsultaPericiaMedica().getPacientePericiaMedica().getFuncionario().getNomeMae());
		
	}
}
