package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.perfil.PerfilRequest;
import com.rhlinkcon.payload.perfil.PerfilResponse;
import com.rhlinkcon.payload.perfil.PerfilUsuarioRequest;
import com.rhlinkcon.service.PerfilService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class PerfilController {

	@Autowired
	private PerfilService perfilService;

	@GetMapping("/perfis/search")
	public List<PerfilResponse> getPerfisSearch(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return perfilService.getPerfisSearch(search);
	}

	@GetMapping("/perfis")
	@PreAuthorize("hasAnyRole('ADMIN','PERFIS_DE_ACESSO_VISUALIZAR','PERFIS_DE_ACESSO_CADASTRAR','PERFIS_DE_ACESSO_ATUALIZAR','PERFIS_DE_ACESSO_EXCLUIR')")
	public PagedResponse<PerfilResponse> getPerfis(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome) {
		return perfilService.getPerfis(page, size, order, nome);
	}

	@GetMapping("/perfil/{perfilId}/usuarios")
	@PreAuthorize("hasAnyRole('ADMIN','PERFIS_DE_ACESSO_VISUALIZAR','PERFIS_DE_ACESSO_CADASTRAR','PERFIS_DE_ACESSO_ATUALIZAR','PERFIS_DE_ACESSO_EXCLUIR')")
	public List<Long> getUsuarioIdsByPerfil(@PathVariable Long perfilId) {
		return perfilService.getUsuarioIdsByPerfil(perfilId);
	}

	@GetMapping("/perfil/usuario/{usuarioId}")
	@PreAuthorize("hasAnyRole('ADMIN','PERFIS_DE_ACESSO_VISUALIZAR','PERFIS_DE_ACESSO_CADASTRAR','PERFIS_DE_ACESSO_ATUALIZAR','PERFIS_DE_ACESSO_EXCLUIR')")
	public List<PerfilResponse> getPerfisByUser(@PathVariable Long usuarioId) {
		return perfilService.getPerfisByUser(usuarioId);
	}

	@GetMapping("/perfil/{perfilId}")
	@PreAuthorize("hasAnyRole('ADMIN','PERFIS_DE_ACESSO_VISUALIZAR','PERFIS_DE_ACESSO_CADASTRAR','PERFIS_DE_ACESSO_ATUALIZAR','PERFIS_DE_ACESSO_EXCLUIR')")
	public PerfilResponse getPerfilById(@PathVariable Long perfilId) {
		return perfilService.getPerfilById(perfilId);
	}

	@PostMapping("/perfil")
	@PreAuthorize("hasAnyRole('ADMIN','PERFIS_DE_ACESSO_CADASTRAR')")
	public ResponseEntity<?> createPerfil(@Valid @RequestBody PerfilRequest perfilRequest) {
		perfilService.createPerfil(perfilRequest);
		return Utils.created(true, "Perfil de Acesso criado com sucesso.");
	}

	@PutMapping("/perfil")
	@PreAuthorize("hasAnyRole('ADMIN','PERFIS_DE_ACESSO_ATUALIZAR')")
	public ResponseEntity<?> updatePerfil(@Valid @RequestBody PerfilRequest perfilRequest) {
		perfilService.updatePerfil(perfilRequest);
		return Utils.created(true, "Perfil de Acesso atualizado com sucesso.");
	}

	@PutMapping("/perfil/usuario")
	@PreAuthorize("hasAnyRole('ADMIN','PERFIS_DE_ACESSO_ATUALIZAR')")
	public ResponseEntity<?> updatePerfilUsuario(@Valid @RequestBody PerfilUsuarioRequest perfilUsuarioRequest) {
		perfilService.updatePerfilUsuario(perfilUsuarioRequest);
		return Utils.created(true, "Perfil de Acesso atualizado com sucesso.");
	}

	@PutMapping("/perfil/usuario/adicionar")
	@PreAuthorize("hasAnyRole('ADMIN','PERFIS_DE_ACESSO_ATUALIZAR')")
	public ResponseEntity<?> adicionarUsuarioAoPerfil(@Valid @RequestBody PerfilUsuarioRequest perfilUsuarioRequest) {
		perfilService.adicionarUsuarioAoPerfil(perfilUsuarioRequest);
		return Utils.created(true, "Usuário adicionado ao perfil de acesso com sucesso.");
	}

	@PutMapping("/perfil/usuario/remover")
	@PreAuthorize("hasAnyRole('ADMIN','PERFIS_DE_ACESSO_ATUALIZAR')")
	public ResponseEntity<?> removerUsuarioDoPerfil(@Valid @RequestBody PerfilUsuarioRequest perfilUsuarioRequest) {
		perfilService.removerUsuarioDoPerfil(perfilUsuarioRequest);
		return Utils.created(true, "Usuário removido do perfil de acesso com sucesso.");
	}

	@DeleteMapping("/perfil/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','PERFIS_DE_ACESSO_EXCLUIR')")
	public ResponseEntity<?> deletePerfil(@PathVariable("id") Long id) {
		perfilService.deletePerfil(id);

		return Utils.deleted(true, "Perfil de Acesso deletado com sucesso.");
	}

}
