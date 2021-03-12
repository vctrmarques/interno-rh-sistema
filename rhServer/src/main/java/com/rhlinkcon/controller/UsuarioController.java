package com.rhlinkcon.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.ApiResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.menu.MenuPermissaoResponse;
import com.rhlinkcon.payload.usuario.UsuarioLdap;
import com.rhlinkcon.payload.usuario.UsuarioPapelPerfilRequest;
import com.rhlinkcon.payload.usuario.UsuarioPerfilRequest;
import com.rhlinkcon.payload.usuario.UsuarioRequest;
import com.rhlinkcon.payload.usuario.UsuarioResponse;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.JwtTokenProvider;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.UsuarioService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@GetMapping("/publico/teste")
	public ResponseEntity<?> getTeste() {
		return Utils.created(true, "Sucesso.");
	}

	@GetMapping("/usuario/logado")
	public UsuarioResponse getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UsuarioResponse usuarioResponse = new UsuarioResponse(currentUser.getId(), currentUser.getUsername(),
				currentUser.getName(), userRepository.getFileDownloadUriByLoggedUser(currentUser.getId()),
				currentUser.getEmail());
		return usuarioResponse;
	}

	@GetMapping("/usuarios")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<UsuarioResponse> getUsuarios(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome,
			@RequestParam(value = "login", defaultValue = AppConstants.DEFAULT_EMPTY) String login,
			@RequestParam(value = "cpf", defaultValue = AppConstants.DEFAULT_EMPTY) String cpf,
			@RequestParam(value = "idFilialFiltro", defaultValue = AppConstants.DEFAULT_EMPTY) String idFilialFiltro) {
		return usuarioService.getAllUsers(currentUser, page, size, nome, cpf, login, order, idFilialFiltro);
	}

	@GetMapping("/usuario/{usuarioId}")
	public UsuarioResponse getUserById(@PathVariable Long usuarioId) {
		return usuarioService.getUserById(usuarioId);
	}

	@PostMapping("/usuario")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createUser(@Valid @RequestBody UsuarioRequest userRequest) {
		if (userRepository.existsByLogin(userRequest.getLogin())) {
			return Utils.badRequest(false, "O login já está em uso!");
		}

		if (userRepository.existsByEmail(userRequest.getEmail())) {
			return Utils.badRequest(false, "O e-mail já está em uso!");
		}

		usuarioService.createUser(userRequest);

		return Utils.created(true, "Usuário criado com sucesso.");
	}

	@PutMapping("/usuario")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UsuarioRequest userRequest) {
		if (userRepository.existsByLoginAndIdNot(userRequest.getLogin(), userRequest.getId())) {
			return Utils.badRequest(false, "O login já está em uso!");
		}

		if (userRepository.existsByEmailAndIdNot(userRequest.getEmail(), userRequest.getId())) {
			return Utils.badRequest(false, "O e-mail já está em uso!");
		}

		usuarioService.updateUser(userRequest);

		return Utils.created(true, "Usuário atualizado com sucesso.");
	}

	@PostMapping("/usuario/atualizacao/ldap")
	public ResponseEntity<?> atualizaUsuariosLdap() {
		List<UsuarioLdap> resultList = usuarioService.atualizaUsuariosLdap();
		return ResponseEntity.ok().body(new ApiResponse(true, "Usuários atualizados com sucesso.", resultList));
	}

	@PutMapping("/usuario/atualizar/papeis/perfis")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> atualizarPapelPerfilUsuario(
			@Valid @RequestBody UsuarioPapelPerfilRequest usuarioPapelPerfilRequest) {
		usuarioService.atualizarPapelPerfilUsuario(usuarioPapelPerfilRequest);
		return Utils.created(true, "Papeis atualizados com sucesso.");
	}

	@PutMapping("/usuario/perfil")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateUserPerfil(@Valid @RequestBody UsuarioPerfilRequest userPerfilRequest,
			@CurrentUser UserPrincipal currentUser) {

		Usuario user = usuarioService.updateUserPerfil(userPerfilRequest, currentUser);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}").buildAndExpand(user.getId())
				.toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "Usuário atualizado com sucesso."));
	}

	@DeleteMapping("/usuario/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		usuarioService.deleteUser(id);

		return Utils.deleted(true, "Usuário deletado com sucesso.");
	}

	@GetMapping("/usuario/verificaPermissao/por/menu")
	public MenuPermissaoResponse verificaUsuarioLogadoPermissoesPorMenu(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "menu", defaultValue = AppConstants.DEFAULT_EMPTY, required = true) String menu) {
		return usuarioService.verificaUsuarioLogadoPermissoesPorMenu(currentUser, menu);
	}

	@GetMapping("/usuario/verificaPermissao")
	public ResponseEntity<?> VerificaPermissao(
			@RequestParam(value = "role", defaultValue = AppConstants.DEFAULT_EMPTY) String role,
			@CurrentUser UserPrincipal currentUser) {
		try {
			usuarioService.verificaUsuarioLogadoPermissoes(currentUser, role);

			return Utils.ok(true, "Acesso concedido");
		} catch (Exception ex) {
			return Utils.badRequest(true, "Usuário não possui acesso a esta tela");
		}
	}
	
	@GetMapping("/usuario/search")
	public List<DadoBasicoDto> getDadoBasicoList(@RequestParam(value = "search", required = true) String search) {
		return usuarioService.getDadoBasicoList(search);
	}

}
