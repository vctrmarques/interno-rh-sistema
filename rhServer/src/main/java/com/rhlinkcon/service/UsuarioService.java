package com.rhlinkcon.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Menu;
import com.rhlinkcon.model.Papel;
import com.rhlinkcon.model.PapelNomeEnum;
import com.rhlinkcon.model.Perfil;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.menu.MenuPermissaoResponse;
import com.rhlinkcon.payload.usuario.UsuarioLdap;
import com.rhlinkcon.payload.usuario.UsuarioPapelPerfilRequest;
import com.rhlinkcon.payload.usuario.UsuarioPerfilRequest;
import com.rhlinkcon.payload.usuario.UsuarioRequest;
import com.rhlinkcon.payload.usuario.UsuarioResponse;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.MenuRepository;
import com.rhlinkcon.repository.PapelRepository;
import com.rhlinkcon.repository.PerfilRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.security.JwtTokenProvider;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.ModelMapper;
import com.rhlinkcon.util.Utils;

@Service
public class UsuarioService {

	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	PapelRepository roleRepository;

	@Autowired
	private AnexoRepository anexoRepository;

	@Autowired
	PapelRepository papelRepository;

	@Autowired
	EmpresaFilialRepository empresaFilialRepository;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	public void createUser(UsuarioRequest userRequest) {

		// Creating user's account
		Usuario user = new Usuario(userRequest);

		if (userRequest.getSenha() != null && !userRequest.getSenha().isEmpty()) {
			user.setSenha(passwordEncoder.encode(userRequest.getSenha()));
		}

		user.setAtivo(Utils.setBool(userRequest.getAtivo()));

		EmpresaFilial empresaFilial = empresaFilialRepository.findById(userRequest.getEmpresaFilialId()).orElseThrow(
				() -> new ResourceNotFoundException("Empresa Filial", "id", userRequest.getEmpresaFilialId()));

		user.setEmpresaFilial(empresaFilial);

		usuarioRepository.save(user);

	}

	public void updateUser(UsuarioRequest userRequest) {

		// Updating user's account
		Usuario user = usuarioRepository.findById(userRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userRequest.getId()));

		// user.setAtivo(userRequest.getAtivo());
		user.setAtivo(Utils.setBool(userRequest.getAtivo()));
		user.setLogin(userRequest.getLogin());
		user.setCpf(userRequest.getCpf());
		user.setNome(userRequest.getNome());
		user.setEmail(userRequest.getEmail());

		EmpresaFilial empresaFilial = empresaFilialRepository.findById(userRequest.getEmpresaFilialId()).orElseThrow(
				() -> new ResourceNotFoundException("Empresa Filial", "id", userRequest.getEmpresaFilialId()));

		user.setEmpresaFilial(empresaFilial);

		if (userRequest.getSenha() != null && !userRequest.getSenha().isEmpty()) {
			user.setSenha(passwordEncoder.encode(userRequest.getSenha()));
		}

		usuarioRepository.save(user);

	}

	public Usuario atualizarPapelPerfilUsuario(UsuarioPapelPerfilRequest usuarioPapelPerfilRequest) {

		// Updating user's account
		Usuario usuario = usuarioRepository.findById(usuarioPapelPerfilRequest.getUsuarioId()).orElseThrow(
				() -> new ResourceNotFoundException("User", "id", usuarioPapelPerfilRequest.getUsuarioId()));

		usuario.getPapeis().clear();

		// Pegando os papeis e colocando no usuário
		if (Utils.checkList(usuarioPapelPerfilRequest.getPapelIds())) {
			for (Long papelId : usuarioPapelPerfilRequest.getPapelIds()) {
				Optional<Papel> papel = papelRepository.findById(papelId);
				if (papel.isPresent())
					usuario.getPapeis().add(papel.get());
			}
		}

		// Pegando os papeis dos perfis e pondo no usuário
		if (Utils.checkList(usuarioPapelPerfilRequest.getPerfilIds())) {
			for (Long perfilId : usuarioPapelPerfilRequest.getPerfilIds()) {
				Perfil perfil = perfilRepository.findById(perfilId)
						.orElseThrow(() -> new ResourceNotFoundException("Perfil", "id", perfilId));
				if (perfil.getPapeis() != null)
					for (Papel papel : perfil.getPapeis())
						if (!usuario.getPapeis().contains(papel))
							usuario.getPapeis().add(papel);
			}
		}

		return usuarioRepository.save(usuario);

	}

	public Usuario updateUserPerfil(UsuarioPerfilRequest usuarioPerfilRequest, UserPrincipal currentUser) {

		// Updating user's account
		Usuario user = usuarioRepository.findById(currentUser.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));

		if (Objects.nonNull(usuarioPerfilRequest.getAnexoId())) {
			Anexo anexo = anexoRepository.findById(usuarioPerfilRequest.getAnexoId())
					.orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", usuarioPerfilRequest.getAnexoId()));
			user.setAnexo(anexo);
		}

		return usuarioRepository.save(user);

	}

	public void deleteUser(Long id) {
		Usuario user = usuarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		user.setAtivo(false);
		usuarioRepository.save(user);
	}

	public List<UsuarioLdap> atualizaUsuariosLdap() {

		System.out.println("LDAP - Iniciando atualização dos usuários");

		List<UsuarioLdap> resultList = ldapTemplate.search(query().where("msds-cloudextensionattribute11").is("SG-ADM")
				.or("msds-cloudextensionattribute11").is("SG"), new UsuarioLdapResponseAttributesMapper());

		System.out.println("LDAP - Quantidade de usuários importados: " + resultList.size());

		Papel papelAdmin = papelRepository.findByNome(PapelNomeEnum.ROLE_ADMIN)
				.orElseThrow(() -> new UsernameNotFoundException(
						"Papel not found with username or email : " + PapelNomeEnum.ROLE_ADMIN));

		for (UsuarioLdap usuarioLdap : resultList) {

			boolean existeUsuario = usuarioRepository.existsByLogin(usuarioLdap.getLogin());
			System.out.println("LDAP - Checando o usuário: " + usuarioLdap.getLogin());

			if (!existeUsuario) {

				System.out.println("LDAP - Existe na base: NÃO. Iniciando a criação do usuário.");

				Usuario usuario = new Usuario();
				usuario.setLogin(usuarioLdap.getLogin());
				usuario.setSenha(null);
				usuario.setNome(usuarioLdap.getNome());
				usuario.setAtivo(true);

				if (usuarioLdap.getLogin() != null && usuarioLdap.getLogin().length() == 11) {
					// O login do usuário é o CPF.
					usuario.setCpf(usuarioLdap.getLogin());
				} else {
					// CPF Fake - Ajustar essa regra
					usuario.setCpf("11111111111");
				}

//				usuario.setUnidade(false);
//				usuario.setImportadoLdap(true);

				if (usuarioLdap.getMsdsCloudExtensionAttribute11().equals("SG-ADM")) {
					usuario.getPapeis().add(papelAdmin);
				}

//				usuario.setPapelUsuarioOrgao(papelUsuarioOrgao);
//				usuario.setEmails(emails);

				usuarioRepository.save(usuario);
				usuarioLdap.setId(usuario.getId());

				System.out.println("LDAP - Importaçao concluída. O usuário " + usuario.getNome() + " Login: "
						+ usuario.getLogin() + " foi criado com o ID: " + usuario.getId());

			} else {

				Usuario usuario = usuarioRepository.findByLogin(usuarioLdap.getLogin())
						.orElseThrow(() -> new UsernameNotFoundException(
								"User not found with username or email : " + usuarioLdap.getLogin()));
				usuarioLdap.setId(usuario.getId());

				// Ativa o usuário caso ele tenha sido inativado anteriormente.
				usuario.setAtivo(true);

				// Atualiza o nome do usuario
				usuario.setNome(usuarioLdap.getNome());

				if (usuarioLdap.getLogin() != null && usuarioLdap.getLogin().length() == 11) {
					// O login do usuário é o CPF.
					usuario.setCpf(usuarioLdap.getLogin());
				} else {
					// CPF Fake - Ajustar essa regra
					usuario.setCpf("11111111111");
				}

				// Método que atualiza o papal adm
				if (usuarioLdap.getMsdsCloudExtensionAttribute11().equals("SG-ADM")) {
					if (usuario.getPapeis() != null && !usuario.getPapeis().contains(papelAdmin)) {
						usuario.getPapeis().add(papelAdmin);
					}
				} else {
					if (usuario.getPapeis() != null && usuario.getPapeis().contains(papelAdmin)) {
						usuario.getPapeis().remove(papelAdmin);
					}
				}

				usuarioRepository.save(usuario);

				System.out.println("LDAP - Existe na base: SIM. O usuário já existe na base. ID: " + usuario.getId());

			}

		}

		// Inativando os usuários que não estão na lista de usuário importada
		List<Long> listUsuariosImportadosIds = new ArrayList<>();
		for (UsuarioLdap usuarioLdap : resultList) {
			listUsuariosImportadosIds.add(usuarioLdap.getId());
		}
		;

		List<Usuario> listUsuariosParaInativar = usuarioRepository
				.findByIdInNotAndImportadoLdap(listUsuariosImportadosIds);

		System.out.println("LDAP - Inativando os usuários que não estão na lista de usuários importados. Quantidade: "
				+ listUsuariosParaInativar.size());

		for (Usuario usuarioImportPraInativar : listUsuariosParaInativar) {
			usuarioImportPraInativar.setAtivo(false);
			if (usuarioImportPraInativar.getPapeis() != null
					&& usuarioImportPraInativar.getPapeis().contains(papelAdmin)) {
				usuarioImportPraInativar.getPapeis().remove(papelAdmin);
			}
			usuarioRepository.save(usuarioImportPraInativar);
			System.out.println("LDAP - Usuário: " + usuarioImportPraInativar.getNome() + " - "
					+ usuarioImportPraInativar.getLogin() + " INATIVADO");

		}

		return resultList;
	}

	private class UsuarioLdapResponseAttributesMapper implements AttributesMapper<UsuarioLdap> {
		public UsuarioLdap mapFromAttributes(Attributes attrs) throws NamingException {
			UsuarioLdap usuarioLdapResponse = new UsuarioLdap();

			String msdsCloudExtensionAttribute11 = (String) attrs.get("msds-cloudextensionattribute11").get();
			usuarioLdapResponse.setMsdsCloudExtensionAttribute11(msdsCloudExtensionAttribute11);

			String userPrincipalName = (String) attrs.get("userPrincipalName").get();
			String fileNameSplit[] = userPrincipalName.split("@");
			String login = fileNameSplit[0];

			String displayName = (String) attrs.get("displayName").get();
			System.out.println("LDAP - Iniciando Mapper - : " + attrs.toString());

			System.out.println("LDAP - Iniciando Mapper - Nome: " + displayName + " Login: " + login);

			usuarioLdapResponse.setNome(displayName);
			usuarioLdapResponse.setLogin(login);

			return usuarioLdapResponse;
		}
	}

	public PagedResponse<UsuarioResponse> getAllUsers(UserPrincipal currentUser, int page, int size, String nome,
			String cpf, String login, String order, String idFilialFiltro) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Usuario> users = usuarioRepository.filtro(nome, cpf, login, idFilialFiltro, pageable);

		if (users.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), users.getNumber(), users.getSize(),
					users.getTotalElements(), users.getTotalPages(), users.isLast());
		}

		List<UsuarioResponse> userResponses = users.map(usuario -> {
			return ModelMapper.mapUsuarioToUsuarioResponse(usuario);
		}).getContent();
		return new PagedResponse<>(userResponses, users.getNumber(), users.getSize(), users.getTotalElements(),
				users.getTotalPages(), users.isLast());

	}

	public UsuarioResponse getUserById(Long userId) {
		Usuario user = usuarioRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Usuario userCreated = usuarioRepository.findById(user.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(user.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getUpdatedBy()));

		return ModelMapper.mapUsuarioToUsuarioDetalhesResponse(user, userCreated.getNome(), userUpdated.getNome());
	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

	public MenuPermissaoResponse verificaUsuarioLogadoPermissoesPorMenu(UserPrincipal currentUser, String nomeMenu) {

		MenuPermissaoResponse result = new MenuPermissaoResponse();
		Optional<Menu> menuOpt = menuRepository.findByNome(nomeMenu);

		if (menuOpt.isPresent()) {

			Boolean roleAdmin = currentUser.getAuthorities()
					.contains(new SimpleGrantedAuthority(PapelNomeEnum.ROLE_ADMIN.toString()));
			result.setUsuarioAdm(roleAdmin);

			Optional<List<Papel>> papeis = papelRepository.findByMenu(menuOpt.get());

			if (papeis.isPresent()) {

				List<Papel> papeisUsuarioLogado = usuarioRepository.findPapeisById(currentUser.getId());

				papeis.get().forEach(papel -> {
					if (papeisUsuarioLogado.contains(papel)) {
						result.getPapeis().add(papel.getNome().toString());
						if (papel.getNome().toString().contains("CADASTRAR"))
							result.setPodeCadastrar(true);
						else if (papel.getNome().toString().contains("ATUALIZAR"))
							result.setPodeAtualizar(true);
						else if (papel.getNome().toString().contains("EXCLUIR"))
							result.setPodeExcluir(true);
						if (papel.getNome().toString().contains("VISUALIZAR"))
							result.setPodeVisualizar(true);
					}

				});

			}
		} else {
			throw new BadRequestException("O menu '" + nomeMenu + "' não retornou nenhum menu.");
		}

		return result;

	}

	public Boolean verificaUsuarioLogadoPermissoes(UserPrincipal currentUser, String role) {
		String[] roles = role.split("-");
		for (String string : roles) {
			if (currentUser.getAuthorities()
					.contains(new SimpleGrantedAuthority(PapelNomeEnum.valueOf(string).toString()))) {
				return true;
			}
		}
		throw new BadRequestException("Usuário não tem permissão de acesso a esta funcionalidade");
	}

	public String criadoPor(UserDateAudit audit) {
		return usuarioRepository.findNomeById(audit.getUpdatedBy());
	}

	public String alteradoPor(UserDateAudit audit) {
		return usuarioRepository.findNomeById(audit.getUpdatedBy());
	}

	public DadoCadastralResponse preencheAuditoria(UserDateAudit audit) {
		DadoCadastralResponse response = new DadoCadastralResponse();
		response.setCriadoPor(criadoPor(audit));
		response.setAlteradoPor(alteradoPor(audit));
		return response;
	}

	public void setDadoCadastral(DadoCadastralResponse dadoCadastralResponse, UserDateAudit audit) {
		dadoCadastralResponse.setCriadoEm(audit.getCreatedAt());
		dadoCadastralResponse.setAlteradoEm(audit.getUpdatedAt());
		dadoCadastralResponse.setCriadoPor(criadoPor(audit));
		dadoCadastralResponse.setAlteradoPor(alteradoPor(audit));
	}
	
	public List<DadoBasicoDto> getDadoBasicoList(String nome) {
		List<DadoBasicoDto> dadoBasicoList = null;
		if (Utils.checkStr(nome)) {
			dadoBasicoList = usuarioRepository.getDadoBasicoList(nome);
		}
		return dadoBasicoList;
	}

}
