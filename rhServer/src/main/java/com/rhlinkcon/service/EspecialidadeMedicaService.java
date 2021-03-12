package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.EspecialidadeMedica;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.especialidadeMedica.EspecialidadeMedicaDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EspecialidadeMedicaRepository;
import com.rhlinkcon.util.Utils;

@Service
public class EspecialidadeMedicaService extends GenericService<EspecialidadeMedicaDto, Long> {

	@Autowired
	private EspecialidadeMedicaRepository repository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void create(EspecialidadeMedicaDto request) {
		if (repository.existsByCodigo(request.getCodigo()))
			throw new BadRequestException("Especialidade Médica com código " + request.getCodigo() + " já cadastrado");
		if (repository.existsByNome(request.getNome()))
			throw new BadRequestException("O nome já está em uso!");
		EspecialidadeMedica especialidadeMedica = new EspecialidadeMedica(request);
		repository.save(especialidadeMedica);		
	}

	@Override
	public void update(EspecialidadeMedicaDto request) {
		if (repository.existsByNomeAndIdNot(request.getNome(), request.getId()))
			throw new BadRequestException("O nome já está em uso!");

		EspecialidadeMedica especialidadeMedica = repository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Especialidade Médica", "id", request.getId()));

		especialidadeMedica.setCodigo(request.getCodigo());
		especialidadeMedica.setNome(request.getNome());

		repository.save(especialidadeMedica);		
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);		
	}

	@Override
	public EspecialidadeMedicaDto getById(Long id) {
		EspecialidadeMedica especialidadeMedica = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cbo", "id", id));
		EspecialidadeMedicaDto dto = new EspecialidadeMedicaDto(especialidadeMedica);
		usuarioService.setDadoCadastral(dto, especialidadeMedica);
		return dto;
	}

	@Override
	public PagedResponse<EspecialidadeMedicaDto> getPagedList(PagedRequest pagedRequest,
			EspecialidadeMedicaDto requestFilter) {
		
		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Page<EspecialidadeMedica> pageableResult = null;

		if (Utils.checkStr(requestFilter.getNome())) {
			pageableResult = repository.findByNomeIgnoreCaseContaining(requestFilter.getNome(), pageable);
			
			if(pageableResult.getTotalPages() == 0){
				pageableResult = repository.findByCodigoContaining(requestFilter.getNome(), pageable);
			}
		}else {
			pageableResult = repository.findAll(pageable);
		}
		

		PagedResponse<EspecialidadeMedicaDto> result = new PagedResponse<>(Collections.emptyList(), pageableResult.getNumber(),
				pageableResult.getSize(), pageableResult.getTotalElements(), pageableResult.getTotalPages(),
				pageableResult.isLast());

		if (pageableResult.getNumberOfElements() > 0) {
			List<EspecialidadeMedicaDto> especialidadeMedicaResponses = pageableResult.map(especialidadeMedica -> {
				return new EspecialidadeMedicaDto(especialidadeMedica);
			}).getContent();
			result.setContent(especialidadeMedicaResponses);
		}

		return result;
	}

	@Override
	public List<EspecialidadeMedicaDto> getList(EspecialidadeMedicaDto requestFilter) {
		List<EspecialidadeMedicaDto> dtoList = new ArrayList<EspecialidadeMedicaDto>();

		List<EspecialidadeMedica> especialidadeMedicaList = null;

		if (Objects.nonNull(requestFilter) && Utils.checkStr(requestFilter.getNome()))
			especialidadeMedicaList = repository.findByNomeIgnoreCaseContaining(requestFilter.getNome());
		else
			especialidadeMedicaList = repository.findAll();

		especialidadeMedicaList.forEach(especialidadeMedica -> {
			dtoList.add(new EspecialidadeMedicaDto(especialidadeMedica));
		});

		return dtoList;
	}

	@Override
	public List<DadoBasicoDto> getDadoBasicoList(EspecialidadeMedicaDto requestFilter) {
		return null;
	}
	
	public EspecialidadeMedicaDto getEspecialidadeMedicaClinicoGeral() {
		EspecialidadeMedicaDto especialidadeMedicaDto = null;
		
		if(repository.existsByNome("Clínico Geral")) {
			especialidadeMedicaDto = new EspecialidadeMedicaDto(repository.findByNome("Clínico Geral"));
		}
		
		return especialidadeMedicaDto;
	}

}
