package com.rhlinkcon.payload.periciaMedica;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.ConsultaPericiaMedica;
import com.rhlinkcon.model.PacientePericiaTelefone;
import com.rhlinkcon.payload.DadoCadastralResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PericiaMedicaResponse extends DadoCadastralResponse {

	private Long id;

	private String cpf;

	private String nome;
	
	private String tipoAnalise;
	
	private String tipoConsulta;
	
	private String ultimoAgendamento;
	
	private boolean compareceu;
	
	private LocalDate dtAgendamento;
	
	private Time horario;
	
	private String especialidade;
	
	private Integer numeroProcesso;
	
	private Integer contadorComparecimento;
	
	private List<String> telefones;
	
	private String status;
	
	private String nomeMedico; 
	
	private boolean paginaVisualizar;
	
	public PericiaMedicaResponse() {}
	
	public PericiaMedicaResponse(ConsultaPericiaMedica consultaPericiaMedica) {
		setPericiaMedicaResponse(consultaPericiaMedica);
	}
	
	public PericiaMedicaResponse(ConsultaPericiaMedica consultaPericiaMedica, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setPericiaMedicaResponse(consultaPericiaMedica);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}
	
	public void setPericiaMedicaResponse(ConsultaPericiaMedica consultaPericiaMedica) {

		setId(consultaPericiaMedica.getId());
		setCpf(consultaPericiaMedica.getPacientePericiaMedica().getCpf());
		setNome(consultaPericiaMedica.getPacientePericiaMedica().getNome());
		setDtAgendamento(consultaPericiaMedica.getAgendaMedicaData().getData());
		setHorario(consultaPericiaMedica.getAgendaMedicaData().getHorario());
		setEspecialidade(consultaPericiaMedica.getEspecialidadeMedica().getNome());
		if(Objects.nonNull(consultaPericiaMedica.getPacientePericiaMedica().getPacientePericiaTelefone())) {
			List<String> telefoneList = new ArrayList<>();
			List<PacientePericiaTelefone> pacientePericiaTelefone = consultaPericiaMedica.getPacientePericiaMedica().getPacientePericiaTelefone();
			for (PacientePericiaTelefone ppt : pacientePericiaTelefone) {
				telefoneList.add(ppt.getTelefone());
			}
			setTelefones(telefoneList);
		}
		setCompareceu(consultaPericiaMedica.isCompareceu());
		
	}
}
