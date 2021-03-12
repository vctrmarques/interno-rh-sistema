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

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.AgenciaFiltro;
import com.rhlinkcon.model.Agencia;
import com.rhlinkcon.model.Banco;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.agencia.AgenciaRequest;
import com.rhlinkcon.payload.agencia.AgenciaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.MunicipioRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.agencia.AgenciaRepository;
import com.rhlinkcon.repository.banco.BancoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class AgenciaService {

	@Autowired
	private AgenciaRepository agenciaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BancoRepository bancoRepository;
	
	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository; 

	@Autowired
	private MunicipioRepository municipioRepository;
	
	public List<AgenciaResponse> getAllAgencia() {
		List<Agencia> agencias = agenciaRepository.findAll();

		List<AgenciaResponse> listAgenciaResponse = new ArrayList<>();
		for (Agencia agencia : agencias) {
			listAgenciaResponse.add(new AgenciaResponse(agencia, Projecao.BASICA));
		}
		return listAgenciaResponse;
	}

	public AgenciaResponse getAgenciaById(Long agenciaId) {
		Agencia agencia = agenciaRepository.findById(agenciaId)
				.orElseThrow(() -> new ResourceNotFoundException("Agencia", "id", agenciaId));

		AgenciaResponse agenciaResponse = new AgenciaResponse(agencia, Projecao.COMPLETA);
		Usuario criadoPor = usuarioRepository.findById(agencia.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", agencia.getCreatedBy()));
		agenciaResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(agencia.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", agencia.getUpdatedBy()));
		agenciaResponse.setAlteradoPor(alteradoPor.getNome());

		return agenciaResponse;
	}

	public PagedResponse<AgenciaResponse> getAllAgencia(int page, int size, String order, AgenciaFiltro agenciaFiltro) {
		validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Agencia> agencias = agenciaRepository.filtro(agenciaFiltro, pageable);

		if (agencias.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), agencias.getNumber(), agencias.getSize(),
					agencias.getTotalElements(), agencias.getTotalPages(), agencias.isLast());
		}

		List<AgenciaResponse> agenciaResponses = agencias.map(agencia -> {
			return new AgenciaResponse(agencia, Projecao.BASICA);
		}).getContent();
		return new PagedResponse<>(agenciaResponses, agencias.getNumber(), agencias.getSize(),
				agencias.getTotalElements(), agencias.getTotalPages(), agencias.isLast());

	}

	public void deleteAgencia(Long id) {
		Agencia agencia = agenciaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Agencia", "id", id));
		agenciaRepository.delete(agencia);
	}

	public void createAgencia(AgenciaRequest agenciaRequest) {

		Agencia agencia = new Agencia();

		agencia.setDigito(agenciaRequest.getDigito());
		UnidadeFederativa unidadeFederativa = unidadeFederativaRepository.findById(agenciaRequest.getUfId())
				.orElseThrow(() -> new ResourceNotFoundException("Unidade Federativa", "id", agenciaRequest.getUfId()));
		agencia.setUf(unidadeFederativa);
		agencia.setNome(agenciaRequest.getNome());
		agencia.setNumero(agenciaRequest.getNumero());
		agencia.setBloqueado(Utils.setBool(agenciaRequest.isBloqueado()));
		Municipio municipio = municipioRepository.findById(agenciaRequest.getMunicipioId())
				.orElseThrow(() -> new ResourceNotFoundException("Municipio", "id", agenciaRequest.getMunicipioId()));
		agencia.setMunicipio(municipio);

		Banco banco = bancoRepository.findById(agenciaRequest.getBancoId())
				.orElseThrow(() -> new ResourceNotFoundException("Banco", "id", agenciaRequest.getBancoId()));
		agencia.setBanco(banco);

		agenciaRepository.save(agencia);

	}

	public void updateAgencia(AgenciaRequest agenciaRequest) {

		Agencia agencia = agenciaRepository.findById(agenciaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Agencia", "id", agenciaRequest.getId()));

		agencia.setDigito(agenciaRequest.getDigito());
		
		UnidadeFederativa unidadeFederativa = unidadeFederativaRepository.findById(agenciaRequest.getUfId())
				.orElseThrow(() -> new ResourceNotFoundException("Unidade Federativa", "id", agenciaRequest.getUfId()));
		agencia.setUf(unidadeFederativa);
		agencia.setNome(agenciaRequest.getNome());
		agencia.setNumero(agenciaRequest.getNumero());
		agencia.setBloqueado(Utils.setBool(agenciaRequest.isBloqueado()));
		Municipio municipio = municipioRepository.findById(agenciaRequest.getMunicipioId())
				.orElseThrow(() -> new ResourceNotFoundException("Municipio", "id", agenciaRequest.getMunicipioId()));
		agencia.setMunicipio(municipio);


		Banco banco = bancoRepository.findById(agenciaRequest.getBancoId())
				.orElseThrow(() -> new ResourceNotFoundException("Banco", "id", agenciaRequest.getBancoId()));
		agencia.setBanco(banco);

		agenciaRepository.save(agencia);

	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

	public List<AgenciaResponse> findByNomeAndBanco(Integer search, Long bancoId) {
		List<Agencia> lista = agenciaRepository.findByNumeroAndBancoId(search, bancoId); 
		List<AgenciaResponse> response = new ArrayList<>();
		
		if(!lista.isEmpty()) {
			for (Agencia a : lista) {
				response.add(new AgenciaResponse(a, Projecao.BASICA));
			}
		}
		
		return response;
	}

	public List<DadoBasicoDto> getDadoBasicoList(String search, String ufId) {
		List<DadoBasicoDto> dadoBasicoList = null;
		if (Utils.checkStr(ufId))
			dadoBasicoList = agenciaRepository.getDadoBasicoListAndUfId(Integer.parseInt(search), Long.parseLong(ufId));
		else
			dadoBasicoList = agenciaRepository.getDadoBasicoList(search);
		return dadoBasicoList;
	}
	
	public Agencia findByNumeroAndBanco(Banco banco, Integer agencia) {
		String numero = agencia.toString().trim();

		if(numero.length() == 5) {
			numero = numero.substring(0, numero.length() - 1);
		}
		Integer valor = Integer.valueOf(numero);
		
		return agenciaRepository.findFirstByNumeroAndBancoId(valor, banco.getId());
	}

}
