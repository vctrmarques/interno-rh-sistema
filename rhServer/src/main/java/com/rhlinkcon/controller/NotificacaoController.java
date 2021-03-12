package com.rhlinkcon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.notificacao.NotificacaoResponse;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.NotificacaoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class NotificacaoController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	NotificacaoService notificacaoService;

	@GetMapping("/notificacoes")
	public PagedResponse<NotificacaoResponse> getNotificacoes(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order) {
		return notificacaoService.getAllNotificacoes(currentUser, page, size, order);
	}

	@GetMapping("/visualizaNotificacao/{id}")
	public ResponseEntity<?> visualizaNotificacao(@PathVariable("id") Long id) {
		notificacaoService.visualizaNotificacao(id);

		return Utils.ok(true, "Visualizada");
	}

	@GetMapping("/contadorNotificacao")
	public Long contaNotificacao(@CurrentUser UserPrincipal currentUser) {

		return notificacaoService.contadorNotificacaoNaoLida(currentUser);
	}
}
