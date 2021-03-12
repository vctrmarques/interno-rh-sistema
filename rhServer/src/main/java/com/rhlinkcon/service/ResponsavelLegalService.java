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

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Agencia;
import com.rhlinkcon.model.Banco;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.ResponsavelLegal;
import com.rhlinkcon.model.TipoContaResponsavelEnum;
import com.rhlinkcon.model.TipoResponsavelEnum;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.responsavelLegal.ResponsavelLegalRequest;
import com.rhlinkcon.payload.responsavelLegal.ResponsavelLegalResponse;
import com.rhlinkcon.repository.MunicipioRepository;
import com.rhlinkcon.repository.ResponsavelLegalRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.agencia.AgenciaRepository;
import com.rhlinkcon.repository.banco.BancoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ResponsavelLegalService {

	@Autowired
	private ResponsavelLegalRepository responsavelLegalRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BancoRepository bancoRepository;
	
	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;
	
	@Autowired
	private MunicipioRepository municipioRepository;
	
	@Autowired
	private AgenciaRepository agenciaRepository;
	
	@Autowired
	private AgenciaService agenciaService;

	public void createResponsavelLegal(ResponsavelLegalRequest responsavelLegalRequest) {

		ResponsavelLegal responsavelLegal = new ResponsavelLegal(responsavelLegalRequest);
		
		setEntidades(responsavelLegal, responsavelLegalRequest);

		responsavelLegalRepository.save(responsavelLegal);

	}

	public void updateResponsavelLegal(ResponsavelLegalRequest responsavelLegalRequest) {

		ResponsavelLegal responsavelLegal = responsavelLegalRepository.findById(responsavelLegalRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ResponsavelLegal", "id", responsavelLegalRequest.getId()));
		
		setEntidades(responsavelLegal, responsavelLegalRequest);

		responsavelLegal.setCodigoResponsavel(responsavelLegalRequest.getCodigoResponsavel());
		responsavelLegal.setNome(responsavelLegalRequest.getNome());
		responsavelLegal.setAgencia(responsavelLegalRequest.getAgencia());
		responsavelLegal.setContaCorrente(responsavelLegalRequest.getContaCorrente());
		responsavelLegal.setDigitoConta(responsavelLegalRequest.getDigitoConta());
		
		if(Objects.nonNull(responsavelLegalRequest.getAgenciaBancariaId())) {
			Agencia agencia = agenciaRepository.findById(responsavelLegalRequest.getAgenciaBancariaId())
					.orElseThrow(() -> new ResourceNotFoundException("Agencia", "id", responsavelLegalRequest.getAgenciaBancariaId()));
			if(Objects.nonNull(agencia))
				responsavelLegal.setAgenciaBancaria(agencia);
		}
		
		if (Utils.checkStr(responsavelLegalRequest.getTipoResponsavel()))
			responsavelLegal.setTipoResponsavel(TipoResponsavelEnum.getEnumByString(responsavelLegalRequest.getTipoResponsavel()));

		responsavelLegal.setCpf(responsavelLegalRequest.getCpf());

		responsavelLegal.setIdentidade(responsavelLegalRequest.getIdentidade());

		if (Utils.checkStr(responsavelLegalRequest.getTipoConta()))
			responsavelLegal.setTipoConta(
					TipoContaResponsavelEnum.getEnumByString(responsavelLegalRequest.getTipoConta()));
		
		responsavelLegal.setLogradouro(responsavelLegalRequest.getLogradouro());
		responsavelLegal.setNumero(responsavelLegalRequest.getNumero());
		responsavelLegal.setComplemento(responsavelLegalRequest.getComplemento());
		responsavelLegal.setBairro(responsavelLegalRequest.getBairro());
		responsavelLegal.setCep(responsavelLegalRequest.getCep());
		responsavelLegal.setTelefone(responsavelLegalRequest.getTelefone());

		responsavelLegalRepository.save(responsavelLegal);

	}

	public void deleteResponsavelLegal(Long id) {
		ResponsavelLegal responsavelLegal = responsavelLegalRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ResponsavelLegal", "id", id));

		responsavelLegalRepository.delete(responsavelLegal);
	}

	public PagedResponse<ResponsavelLegalResponse> getAllResponsaveisLegais(int page, int size, String nome, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);
		
		Page<ResponsavelLegal> responsaveisLegais = null;
		
		if(Objects.nonNull(nome))
			responsaveisLegais = responsavelLegalRepository.findByNomeIgnoreCaseContaining(nome, pageable);
		else
			responsaveisLegais = responsavelLegalRepository.findAll(pageable);

		if (responsaveisLegais.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), responsaveisLegais.getNumber(), responsaveisLegais.getSize(),
					responsaveisLegais.getTotalElements(), responsaveisLegais.getTotalPages(), responsaveisLegais.isLast());
		}

		List<ResponsavelLegalResponse> responsaveisLegaisResponse = responsaveisLegais.map(responsavelLegal -> {
			return new ResponsavelLegalResponse(responsavelLegal);
		}).getContent();
		return new PagedResponse<>(responsaveisLegaisResponse, responsaveisLegais.getNumber(), responsaveisLegais.getSize(),
				responsaveisLegais.getTotalElements(), responsaveisLegais.getTotalPages(), responsaveisLegais.isLast());

	}

	public ResponsavelLegalResponse getResponsavelLegalById(Long responsavelLegalId) {
		ResponsavelLegal responsavelLegal = responsavelLegalRepository.findById(responsavelLegalId)
				.orElseThrow(() -> new ResourceNotFoundException("ResponsavelLegal", "id", responsavelLegalId));
		
		if(Objects.isNull(responsavelLegal.getAgenciaBancaria()) && Objects.nonNull(responsavelLegal.getAgencia()) && Objects.nonNull(responsavelLegal.getBanco())) {
			Agencia agencia = agenciaService.findByNumeroAndBanco(responsavelLegal.getBanco(), responsavelLegal.getAgencia());
			if (Objects.nonNull(agencia)) {
				responsavelLegal.setAgenciaBancaria(agencia);
			}
		}

		Usuario userCreated = usuarioRepository.findById(responsavelLegal.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", responsavelLegal.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(responsavelLegal.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", responsavelLegal.getUpdatedBy()));

		ResponsavelLegalResponse responsavelLegalResponse = new ResponsavelLegalResponse(responsavelLegal, responsavelLegal.getCreatedAt(),
				userCreated.getNome(), responsavelLegal.getUpdatedAt(), userUpdated.getNome());

		return responsavelLegalResponse;
	}
	
	private void setEntidades(ResponsavelLegal responsavelLegal, ResponsavelLegalRequest responsavelLegalRequest) {
		
		Banco banco = bancoRepository.findById(responsavelLegalRequest.getBancoId())
				.orElseThrow(() -> new ResourceNotFoundException("Banco", "id", responsavelLegalRequest.getBancoId()));
		
		responsavelLegal.setBanco(banco);
		
		if(Objects.nonNull(responsavelLegalRequest.getAgenciaBancariaId())) {
			Agencia agencia = agenciaRepository.findById(responsavelLegalRequest.getAgenciaBancariaId())
					.orElseThrow(() -> new ResourceNotFoundException("Agencia", "id", responsavelLegalRequest.getAgenciaBancariaId()));
			if(Objects.nonNull(agencia))
				responsavelLegal.setAgenciaBancaria(agencia);
		}
		
		if(Objects.nonNull(responsavelLegalRequest.getUnidadeFederativaId())) {
			UnidadeFederativa unidadeFederativa = unidadeFederativaRepository.findById(responsavelLegalRequest.getUnidadeFederativaId())
					.orElseThrow(() -> new ResourceNotFoundException("UnidadeFederativa", "id", responsavelLegalRequest.getUnidadeFederativaId()));
			
			responsavelLegal.setUnidadeFederativa(unidadeFederativa);
		}
		
		if(Objects.nonNull(responsavelLegalRequest.getMunicipioId())) {
			Municipio municipio = municipioRepository.findById(responsavelLegalRequest.getMunicipioId())
					.orElseThrow(() -> new ResourceNotFoundException("Municipio", "id", responsavelLegalRequest.getMunicipioId()));
			
			responsavelLegal.setMunicipio(municipio);
		}
		
	}

	public List<ResponsavelLegalResponse> findByNome(String search) {
		List<ResponsavelLegal> lista = responsavelLegalRepository.findByNomeIgnoreCaseContaining(search); 
		List<ResponsavelLegalResponse> response = new ArrayList<>();
		
		if(!lista.isEmpty()) {
			for (ResponsavelLegal rl : lista) {
				response.add(new ResponsavelLegalResponse(rl));
			}
		}
		
		return response;
	}

}
