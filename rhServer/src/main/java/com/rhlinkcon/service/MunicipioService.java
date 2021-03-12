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
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.municipio.MunicipioRequest;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.repository.MunicipioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class MunicipioService {

	@Autowired
	private MunicipioRepository municipioRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createMunicipio(MunicipioRequest municipioRequest) {

		// Creating user's account
		Municipio municipio = new Municipio(municipioRequest);

		municipioRepository.save(municipio);

	}

	public void updateMunicipio(MunicipioRequest municipioRequest) {

		// Updating user's account
		Municipio municipio = municipioRepository.findById(municipioRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Municipio", "id", municipioRequest.getId()));

		municipio.setDescricao(municipioRequest.getDescricao());
		municipio.setNaturalidade(municipioRequest.getNaturalidade());
		municipio.setRegiaoFiscal(municipioRequest.getRegiaoFiscal());
		municipio.setUf(new UnidadeFederativa(municipioRequest.getUfId()));
		municipio.setCep(municipioRequest.getCep());
		municipio.setCodigoIbge(municipioRequest.getCodigoIbge());

		municipioRepository.save(municipio);

	}

	public void deleteMunicipio(Long id) {
		Municipio municipio = municipioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nacionalidade", "id", id));

		municipioRepository.delete(municipio);
	}

	public PagedResponse<MunicipioResponse> getAllMunicipios(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Municipio> municipios = municipioRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (municipios.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), municipios.getNumber(), municipios.getSize(),
					municipios.getTotalElements(), municipios.getTotalPages(), municipios.isLast());
		}

		List<MunicipioResponse> municipiosdeResponse = municipios.map(municipio -> {
			return new MunicipioResponse(municipio);
		}).getContent();
		return new PagedResponse<>(municipiosdeResponse, municipios.getNumber(), municipios.getSize(),
				municipios.getTotalElements(), municipios.getTotalPages(), municipios.isLast());

	}

	public MunicipioResponse getMunicipioById(Long municipioId) {
		Municipio municipio = municipioRepository.findById(municipioId)
				.orElseThrow(() -> new ResourceNotFoundException("Municipio", "id", municipioId));

		Usuario userCreated = usuarioRepository.findById(municipio.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", municipio.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(municipio.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", municipio.getUpdatedBy()));

		MunicipioResponse municipioResponse = new MunicipioResponse(municipio, municipio.getCreatedAt(),
				userCreated.getNome(), municipio.getUpdatedAt(), userUpdated.getNome());

		return municipioResponse;
	}

	public List<MunicipioResponse> getAllMunicipiosByUf(Long ufId) {

		List<Municipio> municipios = municipioRepository.findByUfId(ufId);
		List<MunicipioResponse> municipiosResponse = new ArrayList<MunicipioResponse>();

		if (!municipios.isEmpty()) {
			for (Municipio municipio : municipios) {
				MunicipioResponse municipiosResp = new MunicipioResponse(municipio);

				municipiosResponse.add(municipiosResp);
			}
			return municipiosResponse;
		}

		return null;
	}

	public List<MunicipioResponse> getAllMunicipiosByNaturalidade(String naturalidade) {

		List<Municipio> municipios = municipioRepository.findByNaturalidade(naturalidade);
		List<MunicipioResponse> municipiosResponse = new ArrayList();

		if (!municipios.isEmpty()) {
			for (Municipio municipio : municipios) {
				MunicipioResponse municipiosResp = new MunicipioResponse(municipio);

				municipiosResponse.add(municipiosResp);
			}
			return municipiosResponse;
		}

		return null;
	}

	public List<MunicipioResponse> getAllMunicipios() {

		List<Municipio> municipios = municipioRepository.findAll();
		List<MunicipioResponse> municipiosResponse = new ArrayList();

		if (!municipios.isEmpty()) {
			for (Municipio municipio : municipios) {
				MunicipioResponse municipiosResp = new MunicipioResponse(municipio);

				municipiosResponse.add(municipiosResp);
			}
			return municipiosResponse;
		}

		return null;
	}

	public List<DadoBasicoDto> getDadoBasicoList(String search, String ufId) {
		List<DadoBasicoDto> dadoBasicoList = null;
		if (Utils.checkStr(ufId))
			dadoBasicoList = municipioRepository.getDadoBasicoListAndUfId(search, Long.parseLong(ufId));
		else
			dadoBasicoList = municipioRepository.getDadoBasicoList(search);
		return dadoBasicoList;
	}

}
