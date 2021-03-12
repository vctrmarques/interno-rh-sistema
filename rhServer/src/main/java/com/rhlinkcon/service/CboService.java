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
import com.rhlinkcon.model.Cbo;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.cbo.CboDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CboRepository;
import com.rhlinkcon.util.Utils;

@Service
public class CboService extends GenericService<CboDto, Long> {

	@Autowired
	private CboRepository repository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void create(CboDto request) {
		if (repository.existsByCodigo(request.getCodigo()))
			throw new BadRequestException("CBO com código " + request.getCodigo() + " já cadastrado");
		if (repository.existsByNome(request.getNome()))
			throw new BadRequestException("O nome já está em uso!");
		Cbo cbo = new Cbo(request);
		repository.save(cbo);
	}

	@Override
	public void update(CboDto request) {
		if (repository.existsByNomeAndIdNot(request.getNome(), request.getId()))
			throw new BadRequestException("O nome já está em uso!");

		Cbo cbo = repository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Cbo", "id", request.getId()));

		cbo.setCodigo(request.getCodigo());
		cbo.setNome(request.getNome());

		repository.save(cbo);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public CboDto getById(Long id) {
		Cbo cbo = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cbo", "id", id));
		CboDto dto = new CboDto(cbo);
		usuarioService.setDadoCadastral(dto, cbo);
		return dto;
	}

	@Override
	public PagedResponse<CboDto> getPagedList(PagedRequest pagedRequest, CboDto requestFilter) {

		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Page<Cbo> pageableResult = null;

		if (Utils.checkStr(requestFilter.getNome()))
			pageableResult = repository.findByNomeIgnoreCaseContaining(requestFilter.getNome(), pageable);
		else
			pageableResult = repository.findAll(pageable);

		PagedResponse<CboDto> result = new PagedResponse<>(Collections.emptyList(), pageableResult.getNumber(),
				pageableResult.getSize(), pageableResult.getTotalElements(), pageableResult.getTotalPages(),
				pageableResult.isLast());

		if (pageableResult.getNumberOfElements() > 0) {
			List<CboDto> cboResponses = pageableResult.map(cbo -> {
				return new CboDto(cbo);
			}).getContent();
			result.setContent(cboResponses);
		}

		return result;
	}

	@Override
	public List<CboDto> getList(CboDto requestFilter) {
		List<CboDto> dtoList = new ArrayList<CboDto>();

		List<Cbo> cboList = null;

		if (Objects.nonNull(requestFilter) && Utils.checkStr(requestFilter.getNome()))
			cboList = repository.findByNomeIgnoreCaseContaining(requestFilter.getNome());
		else
			cboList = repository.findAll();

		cboList.forEach(cbo -> {
			dtoList.add(new CboDto(cbo));
		});

		return dtoList;

	}

	@Override
	public List<DadoBasicoDto> getDadoBasicoList(CboDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

}
