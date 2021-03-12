package com.rhlinkcon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.papel.PapelResponse;
import com.rhlinkcon.service.PapelService;

@RestController
@RequestMapping("/api")
public class PapelController {

	@Autowired
	private PapelService papelService;

	@GetMapping("/papeis/{menuId}")
	public List<PapelResponse> listPapeis(@PathVariable Long menuId) {
		return papelService.listPapeis(menuId);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/papeis/usuario/{usuarioId}")
	public List<PapelResponse> listPapeisUsuario(@PathVariable Long usuarioId) {
		return papelService.listPapeisUsuario(usuarioId);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/papeis/usuario/{usuarioId}/menu/{menuId}")
	public List<PapelResponse> listPapeisUsuarioMenu(@PathVariable Long usuarioId, @PathVariable Long menuId) {
		return papelService.listPapeisUsuarioMenu(usuarioId, menuId);
	}

}

