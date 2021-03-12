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

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.EntidadeExame;
import com.rhlinkcon.model.Exame;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.TipoEntidadeExameEnum;
import com.rhlinkcon.model.TomadorServico;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.entidadeExame.EntidadeExameRequest;
import com.rhlinkcon.payload.entidadeExame.EntidadeExameResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ExameRepository;
import com.rhlinkcon.repository.TomadorServicoRepository;
import com.rhlinkcon.repository.entidadeExame.EntidadeExameRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class EntidadeExameService {

	@Autowired
	private EntidadeExameRepository entidadeExameRepository;

	@Autowired
	private ExameRepository exameRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TomadorServicoRepository tomadorServicoRepository;

	public EntidadeExameResponse getEntidadeExameById(Long entidadeExameId) {
		EntidadeExame entidadeExame = entidadeExameRepository.findById(entidadeExameId)
				.orElseThrow(() -> new ResourceNotFoundException("EntidadeExame", "id", entidadeExameId));

		EntidadeExameResponse entidadeExameResponse = new EntidadeExameResponse(entidadeExame);
		Usuario criadoPor = usuarioRepository.findById(entidadeExame.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", entidadeExame.getCreatedBy()));
		entidadeExameResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(entidadeExame.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", entidadeExame.getUpdatedBy()));
		entidadeExameResponse.setAlteradoPor(alteradoPor.getNome());

		return entidadeExameResponse;
	}

	public List<EntidadeExameResponse> getAllEntidadeExames() {
		List<EntidadeExameResponse> itensResponses = new ArrayList<EntidadeExameResponse>();
		entidadeExameRepository.findAll().forEach(item -> itensResponses.add(new EntidadeExameResponse(item)));
		return itensResponses;
	}

	public PagedResponse<EntidadeExameResponse> getAllEntidadeExames(int page, int size, String order,
			String razaoSocial) {
		validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<EntidadeExame> entidadeExames = entidadeExameRepository
				.findByTomadorServicoRazaoSocialIgnoreCaseContaining(razaoSocial, pageable);

		if (entidadeExames.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), entidadeExames.getNumber(), entidadeExames.getSize(),
					entidadeExames.getTotalElements(), entidadeExames.getTotalPages(), entidadeExames.isLast());
		}

		List<EntidadeExameResponse> entidadeExameResponses = entidadeExames.map(entidadeExame -> {
			return new EntidadeExameResponse(entidadeExame);
		}).getContent();
		return new PagedResponse<>(entidadeExameResponses, entidadeExames.getNumber(), entidadeExames.getSize(),
				entidadeExames.getTotalElements(), entidadeExames.getTotalPages(), entidadeExames.isLast());

	}

	public void deleteEntidadeExame(Long id) {
		EntidadeExame entidadeExame = entidadeExameRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("EntidadeExame", "id", id));
		entidadeExameRepository.delete(entidadeExame);
	}

	public void createEntidadeExame(EntidadeExameRequest entidadeExameRequest) {

		EntidadeExame entidadeExame = new EntidadeExame();
		entidadeExame.setTipo(TipoEntidadeExameEnum.valueOf(entidadeExameRequest.getTipo()));

		TomadorServico tomadorServico = tomadorServicoRepository.findById(entidadeExameRequest.getTomadorServicoId())
				.orElseThrow(() -> new ResourceNotFoundException("TomadorServico", "id",
						entidadeExameRequest.getTomadorServicoId()));
		entidadeExame.setTomadorServico(tomadorServico);

		if (entidadeExameRepository.existsByTomadorServicoRazaoSocial(tomadorServico.getRazaoSocial())) {
			new BadRequestException("A entidade de exame já está em uso!");
		}

		if (entidadeExameRequest.getExamesIds() != null && !entidadeExameRequest.getExamesIds().isEmpty()) {
			for (Long exameId : entidadeExameRequest.getExamesIds()) {
				Exame exame = exameRepository.findById(exameId)
						.orElseThrow(() -> new ResourceNotFoundException("Exame", "id", exameId));
				entidadeExame.getExames().add(exame);
			}
		}

		entidadeExame.setTelefone(entidadeExameRequest.getTelefone());

		if (Objects.nonNull(entidadeExameRequest.getEndereco())) {
			entidadeExame.setLogradouro(entidadeExameRequest.getEndereco().getLogradouro());
			entidadeExame.setNumero(entidadeExameRequest.getEndereco().getNumero());
			entidadeExame.setComplemento(entidadeExameRequest.getEndereco().getComplemento());
			entidadeExame.setCep(entidadeExameRequest.getEndereco().getCep());
			entidadeExame.setBairro(entidadeExameRequest.getEndereco().getBairro());
			if (Objects.nonNull(entidadeExameRequest.getEndereco().getUf()))
				entidadeExame.setUf(new UnidadeFederativa(entidadeExameRequest.getEndereco().getUf().getId()));
			else
				entidadeExame.setUf(null);

			if (Objects.nonNull(entidadeExameRequest.getEndereco().getMunicipio().getId()))
				entidadeExame.setMunicipio(new Municipio(entidadeExameRequest.getEndereco().getMunicipio().getId()));
			else
				entidadeExame.setMunicipio(null);
		}

		entidadeExameRepository.save(entidadeExame);

	}

	public void updateEntidadeExame(EntidadeExameRequest entidadeExameRequest) {

		EntidadeExame entidadeExame = entidadeExameRepository.findById(entidadeExameRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("EntidadeExame", "id", entidadeExameRequest.getId()));

		entidadeExame.setTipo(TipoEntidadeExameEnum.valueOf(entidadeExameRequest.getTipo()));

		TomadorServico tomadorServico = tomadorServicoRepository.findById(entidadeExameRequest.getTomadorServicoId())
				.orElseThrow(() -> new ResourceNotFoundException("TomadorServico", "id",
						entidadeExameRequest.getTomadorServicoId()));
		entidadeExame.setTomadorServico(tomadorServico);

		if (entidadeExameRepository.existsByTomadorServicoRazaoSocialAndIdNot(tomadorServico.getRazaoSocial(),
				entidadeExame.getId())) {
			new BadRequestException("A entidade de exame já está em uso!");
		}

		entidadeExame.getExames().clear();
		if (entidadeExameRequest.getExamesIds() != null && !entidadeExameRequest.getExamesIds().isEmpty()) {
			for (Long exameId : entidadeExameRequest.getExamesIds()) {
				Exame exame = exameRepository.findById(exameId)
						.orElseThrow(() -> new ResourceNotFoundException("Exame", "id", exameId));
				entidadeExame.getExames().add(exame);
			}
		}
		entidadeExame.setLogradouro(entidadeExameRequest.getEndereco().getLogradouro());
		entidadeExame.setNumero(entidadeExameRequest.getEndereco().getNumero());
		entidadeExame.setComplemento(entidadeExameRequest.getEndereco().getComplemento());
		entidadeExame.setCep(entidadeExameRequest.getEndereco().getCep());
		entidadeExame.setBairro(entidadeExameRequest.getEndereco().getBairro());
		entidadeExame.setTelefone(entidadeExameRequest.getTelefone());

		if (Objects.nonNull(entidadeExameRequest.getEndereco().getUf()))
			entidadeExame.setUf(new UnidadeFederativa(entidadeExameRequest.getEndereco().getUf().getId()));
		else
			entidadeExame.setUf(null);

		if (Objects.nonNull(entidadeExameRequest.getEndereco().getMunicipio().getId()))
			entidadeExame.setMunicipio(new Municipio(entidadeExameRequest.getEndereco().getMunicipio().getId()));
		else
			entidadeExame.setMunicipio(null);

		entidadeExameRepository.save(entidadeExame);

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
