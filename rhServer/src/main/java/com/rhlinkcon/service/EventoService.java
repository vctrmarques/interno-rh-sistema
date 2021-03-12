package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Evento;
import com.rhlinkcon.model.SituacaoEventoEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.evento.EventoRequest;
import com.rhlinkcon.payload.evento.EventoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EventoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createEvento(EventoRequest eventoRequest) {

		// Creating user's account
		Evento evento = new Evento(eventoRequest);

		eventoRepository.save(evento);

	}

	public void updateEvento(EventoRequest eventoRequest) {

		// Updating user's account
		Evento evento = eventoRepository.findById(eventoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Evento", "id", eventoRequest.getId()));

		evento.setDescricao(eventoRequest.getDescricao());

		if (!eventoRequest.getSituacao().isEmpty())
			evento.setSituacao(SituacaoEventoEnum.getEnumByString(eventoRequest.getSituacao()));

		eventoRepository.save(evento);

	}

	public void deleteEvento(Long id) {
		Evento evento = eventoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Evento", "id", id));

		eventoRepository.delete(evento);
	}

	public PagedResponse<EventoResponse> getAllEventos(int page, int size, String descricao, String order) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Evento> eventos = eventoRepository.findByDescricaoIgnoreCaseContaining(descricao,
				pageable);

		if (eventos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), eventos.getNumber(), eventos.getSize(),
					eventos.getTotalElements(), eventos.getTotalPages(), eventos.isLast());
		}

		List<EventoResponse> eventosResponse = eventos.map(evento -> {
			return new EventoResponse(evento);
		}).getContent();
		return new PagedResponse<>(eventosResponse, eventos.getNumber(), eventos.getSize(),
				eventos.getTotalElements(), eventos.getTotalPages(), eventos.isLast());

	}

	public EventoResponse getEventoById(Long eventoId) {
		Evento evento = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new ResourceNotFoundException("Evento", "id", eventoId));

		Usuario userCreated = usuarioRepository.findById(evento.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", evento.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(evento.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", evento.getUpdatedBy()));

		EventoResponse eventoResponse = new EventoResponse(evento, evento.getCreatedAt(),
				userCreated.getNome(), evento.getUpdatedAt(), userUpdated.getNome());

		return eventoResponse;
	}

	public List<EventoResponse> getAllEventos() {

		List<Evento> eventos = eventoRepository.findAll();
		List<EventoResponse> eventosResponse = new ArrayList();

		if (!eventos.isEmpty()) {
			for (Evento evento: eventos) {
				EventoResponse eventResponse = new EventoResponse(evento);

				eventosResponse.add(eventResponse);
			}
			return eventosResponse;
		}
		
		return null;
	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

}
