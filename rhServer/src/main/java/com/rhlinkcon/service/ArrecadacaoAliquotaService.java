package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.ArrecadacaoAliquotaFiltro;
import com.rhlinkcon.model.ArrecadacaoAliquotaEncargo;
import com.rhlinkcon.model.ArrecadacaoAliquotaEncargoEnum;
import com.rhlinkcon.model.ArrecadacaoAliquotaPeriodo;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.arrecadacaoAliquota.ArrecadacaoAliquotaDto;
import com.rhlinkcon.payload.arrecadacaoAliquota.ArrecadacaoAliquotaEncargoDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.arrecadacaoAliquota.ArrecadacaoAliquotaEncargoRepository;
import com.rhlinkcon.repository.arrecadacaoAliquota.ArrecadacaoAliquotaRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ArrecadacaoAliquotaService extends GenericService<ArrecadacaoAliquotaDto, Long> {

	@Autowired
	private ArrecadacaoAliquotaRepository repository;
	
	@Autowired
	private ArrecadacaoAliquotaEncargoRepository encargoRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	@Transactional
	public void create(ArrecadacaoAliquotaDto dto) {
		if (repository.existsByInicioVigenciaBetweenOrFimVigenciaBetween(dto.getInicioVigencia(), dto.getFimVigencia(), dto.getInicioVigencia(), dto.getFimVigencia()))
			throw new BadRequestException("O período de vigência é inválido, verifique o período informado");
		ArrecadacaoAliquotaPeriodo obj = new ArrecadacaoAliquotaPeriodo(dto);
		repository.save(obj);
		
		if(Utils.checkList(dto.getEncargos())) {
			for(ArrecadacaoAliquotaEncargoDto e : dto.getEncargos()) {
				encargoRepository.save(new ArrecadacaoAliquotaEncargo(e, obj.getId()));
			}
			
		}
	}

	@Override
	@Transactional
	public void update(ArrecadacaoAliquotaDto dto) {
		if (repository.existsByInicioVigenciaBetweenAndIdIsNotOrFimVigenciaBetweenAndIdIsNot(dto.getInicioVigencia(), dto.getFimVigencia(), dto.getId(), dto.getInicioVigencia(), dto.getFimVigencia(), dto.getId()))
			throw new BadRequestException("O período de vigência é inválido, verifique o período informado");
		ArrecadacaoAliquotaPeriodo obj = new ArrecadacaoAliquotaPeriodo(dto);
		repository.save(obj);
		
		if(Utils.checkList(dto.getEncargos())) {
			for(ArrecadacaoAliquotaEncargoDto e : dto.getEncargos()) {
				if(Objects.nonNull(e.getId())) {
					encargoRepository.save(new ArrecadacaoAliquotaEncargo(e));
				} else {
					ArrecadacaoAliquotaEncargoEnum valueEnum = ArrecadacaoAliquotaEncargoEnum.getEnumByString(e.getAliquotaEncargo());
					if(encargoRepository.existsByArrecadacaoAliquotaPeriodoIdAndAliquotaEncargo(obj.getId(), valueEnum)) {
						ArrecadacaoAliquotaEncargo encargo = encargoRepository.findFirstByArrecadacaoAliquotaPeriodoIdAndAliquotaEncargo(obj.getId(), valueEnum);
						encargo.setAliquota(e.getAliquota());
						encargoRepository.save(encargo);
					} else {
						encargoRepository.save(new ArrecadacaoAliquotaEncargo(e, obj.getId()));
					}
				}
			}
		deleteEncargos(dto.getEncargos(), obj.getId());
			
		}
	}

	//Deleta os encargos que não estão na lista enviada e estão salvos no banco
	@Transactional
	private void deleteEncargos(List<ArrecadacaoAliquotaEncargoDto> encargos, Long periodoId) {
		List<ArrecadacaoAliquotaEncargoEnum> listaEnum = new ArrayList<>();
		for(ArrecadacaoAliquotaEncargoDto e : encargos) {
			listaEnum.add(ArrecadacaoAliquotaEncargoEnum.getEnumByString(e.getAliquotaEncargo()));
		}
		if(Utils.checkList(listaEnum)) {
			List<ArrecadacaoAliquotaEncargo> lista = encargoRepository.findAllByArrecadacaoAliquotaPeriodoIdAndAliquotaEncargoNotIn(periodoId, listaEnum);
			if(Utils.checkList(lista)) {
				for(ArrecadacaoAliquotaEncargo e : lista) {
					encargoRepository.deleteById(e.getId());
				}
			}
		}
		
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public ArrecadacaoAliquotaDto getById(Long id) {
		ArrecadacaoAliquotaPeriodo obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ArrecadacaoAliquotaPeriodo", "id", id));
		ArrecadacaoAliquotaDto dto = new ArrecadacaoAliquotaDto(obj);
		usuarioService.setDadoCadastral(dto, obj);
		return dto;
	}

	public PagedResponse<ArrecadacaoAliquotaDto> getPagedList(PagedRequest pagedRequest, ArrecadacaoAliquotaFiltro requestFilter) {
		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);
		Page<ArrecadacaoAliquotaPeriodo> pageableResult = null;

		pageableResult = repository.filtro(requestFilter, pageable);

		PagedResponse<ArrecadacaoAliquotaDto> result = new PagedResponse<>(Collections.emptyList(), pageableResult.getNumber(),
				pageableResult.getSize(), pageableResult.getTotalElements(), pageableResult.getTotalPages(),
				pageableResult.isLast());

		if (pageableResult.getNumberOfElements() > 0) {
			List<ArrecadacaoAliquotaDto> cboResponses = pageableResult.map(e -> {
				return new ArrecadacaoAliquotaDto(e);
			}).getContent();
			result.setContent(cboResponses);
		}

		return result;
	}

	public List<ArrecadacaoAliquotaDto> getList(ArrecadacaoAliquotaFiltro requestFilter) {
		List<ArrecadacaoAliquotaDto> dtoList = new ArrayList<>();

		List<ArrecadacaoAliquotaPeriodo> list = new ArrayList<>();

		list = repository.filtro(requestFilter);
		
		list.forEach(e -> {
			dtoList.add(new ArrecadacaoAliquotaDto(e));
		});

		return dtoList;
	}

	@Override
	public List<DadoBasicoDto> getDadoBasicoList(ArrecadacaoAliquotaDto requestFilter) {
		return null;
	}

	@Override
	public PagedResponse<ArrecadacaoAliquotaDto> getPagedList(PagedRequest pagedRequest, ArrecadacaoAliquotaDto requestFilter) {
		return null;
	}
	
	@Override
	public List<ArrecadacaoAliquotaDto> getList(ArrecadacaoAliquotaDto requestFilter) {
		return null;
	}

}
