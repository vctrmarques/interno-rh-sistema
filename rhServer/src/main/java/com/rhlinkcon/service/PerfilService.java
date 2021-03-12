package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Papel;
import com.rhlinkcon.model.Perfil;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.perfil.PerfilRequest;
import com.rhlinkcon.payload.perfil.PerfilResponse;
import com.rhlinkcon.payload.perfil.PerfilUsuarioRequest;
import com.rhlinkcon.repository.PapelRepository;
import com.rhlinkcon.repository.PerfilRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private PapelRepository papelRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Long> getUsuarioIdsByPerfil(Long perfilId) {
		Perfil perfil = perfilRepository.findById(perfilId)
				.orElseThrow(() -> new ResourceNotFoundException("Perfil", "id", perfilId));

		List<Long> usuarioIds = new ArrayList<Long>();

		perfil.getUsuarios().forEach(usuario -> {
			usuarioIds.add(usuario.getId());
		});

		return usuarioIds;

	}

	public List<PerfilResponse> getPerfisByUser(Long usuarioId) {
		Optional<List<Perfil>> perfis = perfilRepository.findByUsuariosIdAndAtivo(usuarioId, true);
		List<PerfilResponse> perfilResponses = new ArrayList<PerfilResponse>();

		if (perfis.isPresent()) {
			for (Perfil perfil : perfis.get()) {
				PerfilResponse perfilResponse = new PerfilResponse(perfil);
				perfilResponse.setAlteradoPor(usuarioService.alteradoPor(perfil));
				perfilResponse.setCriadoPor(usuarioService.alteradoPor(perfil));
				perfilResponses.add(perfilResponse);
			}
			return perfilResponses;
		} else {
			return null;
		}

	}

	public PagedResponse<PerfilResponse> getPerfis(int page, int size, String order, String nome) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Perfil> perfils = null;

		if (Utils.checkStr(nome))
			perfils = perfilRepository.findByAtivoAndNomeIgnoreCaseContaining(true, nome, pageable);
		else
			perfils = perfilRepository.findByAtivo(true, pageable);

		if (perfils.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), perfils.getNumber(), perfils.getSize(),
					perfils.getTotalElements(), perfils.getTotalPages(), perfils.isLast());
		}

		List<PerfilResponse> perfilResponses = perfils.map(perfil -> {
			PerfilResponse perfilResponse = new PerfilResponse(perfil);
			perfilResponse.setAlteradoPor(usuarioService.alteradoPor(perfil));
			perfilResponse.setCriadoPor(usuarioService.alteradoPor(perfil));
			return perfilResponse;
		}).getContent();
		return new PagedResponse<>(perfilResponses, perfils.getNumber(), perfils.getSize(), perfils.getTotalElements(),
				perfils.getTotalPages(), perfils.isLast());

	}

	public List<PerfilResponse> getPerfisSearch(String search) {

		Optional<List<Perfil>> perfils = null;
		if (Utils.checkStr(search))
			perfils = perfilRepository.findByAtivoAndNomeIgnoreCaseContaining(true, search);
		else
			perfils = perfilRepository.findByAtivo(true);

		if (perfils.isPresent()) {
			List<PerfilResponse> perfilResponses = new ArrayList<PerfilResponse>();
			perfils.get().forEach(perfil -> {
				PerfilResponse perfilResponse = new PerfilResponse();
				perfilResponse.setId(perfil.getId());
				perfilResponse.setNome(perfil.getNome());
				perfilResponses.add(perfilResponse);
			});
			return perfilResponses;
		} else {
			return null;
		}
	}

	public PerfilResponse getPerfilById(Long perfilId) {
		Perfil perfil = perfilRepository.findById(perfilId)
				.orElseThrow(() -> new ResourceNotFoundException("Perfil", "id", perfilId));

		PerfilResponse perfilResponse = new PerfilResponse(perfil);
		perfilResponse.setAlteradoPor(usuarioService.alteradoPor(perfil));
		perfilResponse.setCriadoPor(usuarioService.alteradoPor(perfil));

		return perfilResponse;
	}

	public void createPerfil(PerfilRequest perfilRequest) {
		if (perfilRepository.existsByNomeAndAtivo(perfilRequest.getNome(), true))
			throw new BadRequestException("O nome já está em uso!");
		Perfil perfil = new Perfil();
		perfil.setNome(perfilRequest.getNome());
		perfil.setAtivo(true);
		for (Long papelId : perfilRequest.getPapelIds()) {
			Papel papel = new Papel();
			papel.setId(papelId);
			perfil.getPapeis().add(papel);
		}
		perfilRepository.save(perfil);

	}

	public void updatePerfil(PerfilRequest perfilRequest) {
		Perfil perfil = perfilRepository.findById(perfilRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil", "id", perfilRequest.getId()));

		if (perfilRepository.existsByNomeAndAtivoAndIdNot(perfilRequest.getNome(), true, perfil.getId()))
			throw new BadRequestException("O nome já está em uso!");

		perfil.setNome(perfilRequest.getNome());

		if (perfil.getPapeis() != null && perfil.getUsuarios() != null) {
			for (Usuario usuario : perfil.getUsuarios()) {
				boolean papeisAlterados = false;
				for (Papel papel : perfil.getPapeis()) {
					if (usuario.getPapeis().contains(papel)) {
						usuario.getPapeis().remove(papel);
						papeisAlterados = true;
					}
				}
				if (papeisAlterados)
					usuarioRepository.save(usuario);
			}
		}

		perfil.getPapeis().clear();

		for (Long papelId : perfilRequest.getPapelIds()) {
			Papel papel = papelRepository.findById(papelId)
					.orElseThrow(() -> new ResourceNotFoundException("Perfil", "id", papelId));
			perfil.getPapeis().add(papel);
		}

		perfilRepository.save(perfil);

		if (perfil.getUsuarios() != null) {
			for (Usuario usuario : perfil.getUsuarios()) {
				boolean papeisAlterados = false;
				for (Papel papel : perfil.getPapeis()) {
					if (!usuario.getPapeis().contains(papel)) {
						usuario.getPapeis().add(papel);
						papeisAlterados = true;
					}
				}
				if (papeisAlterados)
					usuarioRepository.save(usuario);
			}
		}

	}

	public void adicionarUsuarioAoPerfil(PerfilUsuarioRequest perfilUsuarioRequest) {
		Perfil perfil = perfilRepository.findById(perfilUsuarioRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil", "id", perfilUsuarioRequest.getId()));

		Usuario usuario = usuarioRepository.findById(perfilUsuarioRequest.getUsuarioId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", perfilUsuarioRequest.getUsuarioId()));

		// Adicionando o usuário no perfil
		if (!perfil.getUsuarios().contains(usuario)) {
			perfil.getUsuarios().add(usuario);
		}
		perfilRepository.save(perfil);

		// Adicionando os papeis do perfil ao usuário
		for (Papel papel : perfil.getPapeis()) {
			if (!usuario.getPapeis().contains(papel)) {
				usuario.getPapeis().add(papel);
			}
		}
		usuarioRepository.save(usuario);

	}

	public void removerUsuarioDoPerfil(PerfilUsuarioRequest perfilUsuarioRequest) {
		Perfil perfil = perfilRepository.findById(perfilUsuarioRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil", "id", perfilUsuarioRequest.getId()));

		Usuario usuario = usuarioRepository.findById(perfilUsuarioRequest.getUsuarioId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", perfilUsuarioRequest.getUsuarioId()));

		// Removendo o usuário do perfil
		if (perfil.getUsuarios().contains(usuario)) {
			perfil.getUsuarios().remove(usuario);
		}
		perfilRepository.save(perfil);

		// Removendo os papeis do perfil do usuário
		for (Papel papel : perfil.getPapeis()) {
			if (usuario.getPapeis().contains(papel)) {
				usuario.getPapeis().remove(papel);
			}
		}
		usuarioRepository.save(usuario);

	}

	public void updatePerfilUsuario(PerfilUsuarioRequest perfilUsuarioRequest) {
		Perfil perfil = perfilRepository.findById(perfilUsuarioRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil", "id", perfilUsuarioRequest.getId()));

		if (perfil.getUsuarios() != null) {
			for (Usuario usuario : perfil.getUsuarios()) {
				boolean papeisAlterados = false;
				for (Papel papel : perfil.getPapeis()) {
					if (usuario.getPapeis().contains(papel)) {
						usuario.getPapeis().remove(papel);
						papeisAlterados = true;
					}
				}
				if (papeisAlterados)
					usuarioRepository.save(usuario);
			}
		}

		perfil.getUsuarios().clear();

		if (perfilUsuarioRequest.getUsuarioIds() != null) {
			for (Long usuarioId : perfilUsuarioRequest.getUsuarioIds()) {

				Usuario usuario = usuarioRepository.findById(usuarioId)
						.orElseThrow(() -> new ResourceNotFoundException("User", "id", usuarioId));
				perfil.getUsuarios().add(usuario);

				boolean papeisAlterados = false;
				for (Papel papel : perfil.getPapeis()) {
					if (!usuario.getPapeis().contains(papel)) {
						usuario.getPapeis().add(papel);
						papeisAlterados = true;
					}
				}
				if (papeisAlterados)
					usuarioRepository.save(usuario);
			}
		}

		perfilRepository.save(perfil);

	}

	public void deletePerfil(Long id) {
		Perfil perfil = perfilRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Perfil", "id", id));
		perfil.setAtivo(false);
		perfilRepository.save(perfil);

		if (perfil.getUsuarios() != null) {
			for (Usuario usuario : perfil.getUsuarios()) {
				boolean papeisAlterados = false;
				for (Papel papel : perfil.getPapeis()) {
					if (usuario.getPapeis().contains(papel)) {
						usuario.getPapeis().remove(papel);
						papeisAlterados = true;
					}
				}
				if (papeisAlterados)
					usuarioRepository.save(usuario);
			}
		}
	}

}
