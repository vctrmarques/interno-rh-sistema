package com.rhlinkcon.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Turno;
import com.rhlinkcon.model.TurnoFolga;
import com.rhlinkcon.model.TurnoFolgaDiaEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.turno.TurnoFolgaRequest;
import com.rhlinkcon.payload.turno.TurnoFolgaResponse;
import com.rhlinkcon.payload.turno.TurnoRequest;
import com.rhlinkcon.payload.turno.TurnoResponse;
import com.rhlinkcon.repository.TurnoFolgaRepository;
import com.rhlinkcon.repository.TurnoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class TurnoService {

	@Autowired
	private TurnoRepository turnoRepository;
	
	@Autowired
	private TurnoFolgaRepository turnoFolgaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	
	public PagedResponse<TurnoResponse> getAllTurnoPage(int page, int size, String order, String codigo) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		Direction direction = Sort.Direction.ASC;
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Turno> turnos = null;
		if (!Objects.isNull(codigo) && !codigo.isEmpty()) {
			turnos = turnoRepository.findByCodigoIgnoreCaseContaining(codigo, pageable);
		} else {
			turnos = turnoRepository.findAll(pageable);
		}

		if (turnos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), turnos.getNumber(),
					turnos.getSize(), turnos.getTotalElements(), turnos.getTotalPages(),
					turnos.isLast());
		}

		List<TurnoResponse> turnoResponses = turnos.map(turno -> {
			
			List<TurnoFolga> turnosFolga = turnoFolgaRepository.findByTurno(turno);
			
			if(turnosFolga.size() > 0) {
				
				List<TurnoFolgaResponse> turnoFolgaResponses = new ArrayList<TurnoFolgaResponse>();
				turnosFolga.forEach((TurnoFolga turnoFolga) -> {
					TurnoFolgaResponse turnoFolgaResponse = new TurnoFolgaResponse(turnoFolga);
					turnoFolgaResponses.add(turnoFolgaResponse);
				});
				
				return new TurnoResponse(turno, turnoFolgaResponses);
				
			}else {
				return new TurnoResponse(turno);
			}
			
		}).getContent();
		
		return new PagedResponse<>(turnoResponses, turnos.getNumber(), turnos.getSize(),
				turnos.getTotalElements(), turnos.getTotalPages(), turnos.isLast());

	}
	
	public TurnoResponse getTurnoResponseId(Long turnoId) {
		
		Turno turno = turnoRepository.findById(turnoId)
				.orElseThrow(() -> new ResourceNotFoundException("Turno", "id", turnoId));

		TurnoResponse turnoResponse = new TurnoResponse(turno);
		Usuario criadoPor = usuarioRepository.findById(turno.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", turno.getCreatedBy()));
		turnoResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(turno.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", turno.getUpdatedBy()));
		turnoResponse.setAlteradoPor(alteradoPor.getNome());
		
		
		List<TurnoFolgaResponse> turnoFolgaResponses = new ArrayList<TurnoFolgaResponse>();
		List<TurnoFolga> turnosFolga = turnoFolgaRepository.findByTurno(turno);
		
		if(turnosFolga.size() > 0) {
			turnosFolga.forEach((TurnoFolga turnoFolga) -> {
				TurnoFolgaResponse turnoFolgaResponse = new TurnoFolgaResponse(turnoFolga);
				turnoFolgaResponses.add(turnoFolgaResponse);
			});
		}
		
		turnoResponse.setTurnoFolgaResponse(turnoFolgaResponses);

		return turnoResponse;
		
	}
	
	public void createTurno(TurnoRequest turnoRequest) {
		
		Turno turno = new Turno(turnoRequest);

		turnoRepository.save(turno);
		
		createTurnoFolga(turnoRequest.getTurnoFolga(), turno);
		
	}
	
	public void updateTurno(TurnoRequest turnoRequest) {
		
		Turno turno = turnoRepository.findById(turnoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Turno", "id", turnoRequest.getId()));
		
		turno.setCodigo(turnoRequest.getCodigo());
		turno.setDataInicio(turnoRequest.getDataInicio());
		turno.setDataFim(turnoRequest.getDataFim());
		turno.setHorarioFlexivel(Utils.setBool(turnoRequest.isHorarioFlexivel()));
		turno.setJornada(turnoRequest.getJornada());
		turno.setHorarioFlexivelInicio(turnoRequest.getHorarioFlexivelInicio());
		turno.setHorarioFlexivelFim(turnoRequest.getHorarioFlexivelFim());
		turno.setIntervaloFlexivel(Utils.setBool(turnoRequest.isIntervaloFlexivel()));
		turno.setIntervalo(turnoRequest.getIntervalo());
		turno.setIntervaloFlexivelInicio(turnoRequest.getIntervaloFlexivelInicio());
		turno.setIntervaloFlexivelFim(turnoRequest.getIntervaloFlexivelFim());
		
		turnoRepository.save(turno);
		
		deleteTurnoFolga(turno);
		
		createTurnoFolga(turnoRequest.getTurnoFolga(), turno);
		
	}
	
	public void deleteTurno(Long turnoId) {
		
		Turno turno = turnoRepository.findById(turnoId)
				.orElseThrow(() -> new ResourceNotFoundException("Turno", "id", turnoId));
		
		deleteTurnoFolga(turno);
		
		turnoRepository.delete(turno);
	}
	
	private void createTurnoFolga(List<TurnoFolgaRequest> listaTurnoFolgaRequest, Turno turno) {
		
		if(listaTurnoFolgaRequest.size() > 0) {
			listaTurnoFolgaRequest.forEach((TurnoFolgaRequest turnoFolgaRequest) -> {
				TurnoFolga turnoFolga = new TurnoFolga();
				turnoFolga.setTurno(turno);
				turnoFolga.setDia(TurnoFolgaDiaEnum.getEnumByString(turnoFolgaRequest.getDia()));
				turnoFolgaRepository.save(turnoFolga);
			});
		}
	}
	
	private void deleteTurnoFolga(Turno turno) {
		List<TurnoFolga> turnosFolga = turnoFolgaRepository.findByTurno(turno);
		if(turnosFolga.size() > 0) {
			
			List<TurnoFolgaResponse> turnoFolgaResponses = new ArrayList<TurnoFolgaResponse>();
			turnosFolga.forEach((TurnoFolga turnoFolga) -> {
				turnoFolgaRepository.delete(turnoFolga);
			});
			
		}
	}
	
	public List<TurnoResponse> getAllTurnos() {

		List<Turno> turnos = turnoRepository.findAll();
		List<TurnoResponse> turnosResponse = new ArrayList<>();

		if (!turnos.isEmpty()) {
			for (Turno turno: turnos) {

				turnosResponse.add(new TurnoResponse(turno));
			}
			return turnosResponse;
		}

		return null;
	}
	
}
