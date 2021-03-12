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

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Nacionalidade;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.nacionalidade.NacionalidadeRequest;
import com.rhlinkcon.payload.nacionalidade.NacionalidadeResponse;
import com.rhlinkcon.repository.NacionalidadeRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class NacionalidadeService {

	@Autowired
	private NacionalidadeRepository nacionalidadeRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createNacionalidade(NacionalidadeRequest nacionalidadeRequest) {

		// Creating user's account
		Nacionalidade nacionalidade = new Nacionalidade(nacionalidadeRequest);

		nacionalidadeRepository.save(nacionalidade);

	}

	public void updateNacionalidade(NacionalidadeRequest nacionalidadeRequest) {

		// Updating user's account
		Nacionalidade nacionalidade = nacionalidadeRepository.findById(nacionalidadeRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Nacionalidade", "id", nacionalidadeRequest.getId()));

		nacionalidade.setSigla(nacionalidadeRequest.getSigla());
		nacionalidade.setNacionalidadeFeminina(nacionalidadeRequest.getNacionalidadeFeminina());
		nacionalidade.setNacionalidadeMasculina(nacionalidadeRequest.getNacionalidadeMasculina());
		nacionalidade.setCodigoSiprev(nacionalidadeRequest.getCodigoSiprev());

		nacionalidadeRepository.save(nacionalidade);

	}

	public void deleteNacionalidade(Long id) {
		Nacionalidade nacionalidade = nacionalidadeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nacionalidade", "id", id));

		nacionalidadeRepository.delete(nacionalidade);
	}

	public PagedResponse<NacionalidadeResponse> getAllNacionalidades(int page, int size, String sigla, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Nacionalidade> nacionalidades = nacionalidadeRepository.findBySiglaIgnoreCaseContaining(sigla, pageable);

		if (nacionalidades.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), nacionalidades.getNumber(), nacionalidades.getSize(),
					nacionalidades.getTotalElements(), nacionalidades.getTotalPages(), nacionalidades.isLast());
		}

		List<NacionalidadeResponse> nacionalidasdeResponse = nacionalidades.map(nacionalidade -> {
			return new NacionalidadeResponse(nacionalidade);
		}).getContent();
		return new PagedResponse<>(nacionalidasdeResponse, nacionalidades.getNumber(), nacionalidades.getSize(),
				nacionalidades.getTotalElements(), nacionalidades.getTotalPages(), nacionalidades.isLast());

	}

	public NacionalidadeResponse getNacionalidadeById(Long nacionalidadeId) {
		Nacionalidade nacionalidade = nacionalidadeRepository.findById(nacionalidadeId)
				.orElseThrow(() -> new ResourceNotFoundException("Nacionalidade", "id", nacionalidadeId));

		Usuario userCreated = usuarioRepository.findById(nacionalidade.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", nacionalidade.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(nacionalidade.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", nacionalidade.getUpdatedBy()));

		NacionalidadeResponse nacionalidadeResponse = new NacionalidadeResponse(nacionalidade,
				nacionalidade.getCreatedAt(), userCreated.getNome(), nacionalidade.getUpdatedAt(),
				userUpdated.getNome());

		return nacionalidadeResponse;
	}

	public List<NacionalidadeResponse> getAllNacionalidades() {

		List<Nacionalidade> nacionalidades = nacionalidadeRepository.findAll();
		List<NacionalidadeResponse> nacionalidadeResponse = new ArrayList<>();

		if (!nacionalidades.isEmpty()) {
			for (Nacionalidade nacionalidade : nacionalidades) {
				NacionalidadeResponse nacionalidadesResp = new NacionalidadeResponse(nacionalidade);

				nacionalidadeResponse.add(nacionalidadesResp);
			}
			return nacionalidadeResponse;
		}

		return null;
	}

	public List<DadoBasicoDto> getDadoBasicoList(String search) {

		List<DadoBasicoDto> itensResponses = null;

		if (Utils.checkStr(search))
			itensResponses = nacionalidadeRepository.getDadoBasicoListByNacionalidadeFeminina(search);
		else
			itensResponses = nacionalidadeRepository.getDadoBasicoList();

		return itensResponses;
	}

}
