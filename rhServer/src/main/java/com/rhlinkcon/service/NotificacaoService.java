package com.rhlinkcon.service;

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
import com.rhlinkcon.model.Notificacao;
import com.rhlinkcon.model.TipoNotificacao;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.notificacao.NotificacaoResponse;
import com.rhlinkcon.repository.NotificacaoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.util.AppConstants;

@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository notificacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public PagedResponse<NotificacaoResponse> getAllNotificacoes(UserPrincipal currentUser, int page, int size,
			String order) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = "createdAt";
		Direction direction = Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Usuario usuario = usuarioRepository.findById(currentUser.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", currentUser.getId()));

		Page<Notificacao> notificacoes = notificacaoRepository.findByDestinatario(usuario, pageable);

		if (notificacoes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), notificacoes.getNumber(), notificacoes.getSize(),
					notificacoes.getTotalElements(), notificacoes.getTotalPages(), notificacoes.isLast());
		}

		List<NotificacaoResponse> notificaoResponses = notificacoes.map(notificacao -> {
			return new NotificacaoResponse(notificacao);
		}).getContent();
		return new PagedResponse<>(notificaoResponses, notificacoes.getNumber(), notificacoes.getSize(),
				notificacoes.getTotalElements(), notificacoes.getTotalPages(), notificacoes.isLast());

	}

	public void createNotificacao(TipoNotificacao tipoNotificacao, Boolean enviaEmail, Usuario destinatario,
			Long idEntidade) {
		Notificacao notificacao = new Notificacao();

		try {
			notificacao.setDestinatario(destinatario);
			notificacao.setTipoNotificacao(tipoNotificacao);
			notificacao.setVisualizada(false);
			notificacao.setIdEntidade(idEntidade);

			if (tipoNotificacao.toString().equals("REUNIAO")) {
				notificacao.setDescricao(TipoNotificacao.REUNIAO.getLabel());

				notificacaoRepository.save(notificacao);
			}

		} catch (Exception e) {
			throw new BadRequestException("Não foi possível criar uma notificação");
		}

	}

	public void visualizaNotificacao(Long id) {
		Notificacao notificacao = notificacaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Notificacao", "id", id));

		notificacao.setVisualizada(true);
		notificacaoRepository.save(notificacao);
	}
	
	public Long contadorNotificacaoNaoLida(UserPrincipal currentUser) {
		
		Usuario destinatario = usuarioRepository.findById(currentUser.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", currentUser.getId()));
		
		Long contador = notificacaoRepository.countByDestinatarioAndVisualizada(destinatario, false);
		
		return contador;
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
