package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.ArrecadacaoIndiceFiltro;
import com.rhlinkcon.model.ArrecadacaoIndice;
import com.rhlinkcon.model.ArrecadacaoIndiceAnoPeriodicidade;
import com.rhlinkcon.model.ArrecadacaoIndiceMes;
import com.rhlinkcon.model.MesEnum;
import com.rhlinkcon.model.PeriodicidadeMesEnum;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.arrecadacaoIndice.ArrecadacaoIndiceAnoPeriodicidadeRepository;
import com.rhlinkcon.repository.arrecadacaoIndice.ArrecadacaoIndiceMesRepository;
import com.rhlinkcon.repository.arrecadacaoIndice.ArrecadacaoIndiceRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ArrecadacaoIndiceService extends GenericService<ArrecadacaoIndiceDto, Long> {

	@Autowired
	private ArrecadacaoIndiceRepository repository;
	
	@Autowired
	private ArrecadacaoIndiceAnoPeriodicidadeRepository anoRepository;
	
	@Autowired
	private ArrecadacaoIndiceMesRepository mesRepository;
	
	@Autowired
	private UsuarioService usuarioService;


	@Transactional
	public ResponseEntity<?> createIndice(ArrecadacaoIndiceDto dto) {

		if (repository.existsByIndice(dto.getIndice())) {
			return Utils.badRequest(false, "Já existe um índice com este nome.");
		}
		
		ArrecadacaoIndice arrecadacaoIndice = new ArrecadacaoIndice(dto);
		repository.save(arrecadacaoIndice);

		if(Utils.checkList(dto.getAnos())) {
			for(ArrecadacaoIndiceAnoPeriodicidadeDto e : dto.getAnos()) {
				
				ArrecadacaoIndiceAnoPeriodicidade arrecadacaoIndiceAnoPeriodicidade = new ArrecadacaoIndiceAnoPeriodicidade(e, arrecadacaoIndice.getId());
				anoRepository.save(arrecadacaoIndiceAnoPeriodicidade);
			
				if(Utils.checkList(e.getMeses())) {
					for(ArrecadacaoIndiceMesDto arrecadacaoIndiceMesDto : e.getMeses()) {

						String[] mes = arrecadacaoIndiceMesDto.getMes().split("/");
						for (int i = 0; i < mes.length; i++) {
							if(mes[i].equals("MARÇO")) {
								arrecadacaoIndiceMesDto.setMes("MARCO");
							}else{
								arrecadacaoIndiceMesDto.setMes(mes[i]);
							}
							mesRepository.save(new ArrecadacaoIndiceMes(arrecadacaoIndiceMesDto, arrecadacaoIndiceAnoPeriodicidade.getId()));
						}
						
					}

				}
			}

		}
		return Utils.created(true, "Índice criado com sucesso!");	
	}
	
	public PagedResponse<ArrecadacaoIndiceDto> getPagedList(PagedRequest pagedRequest, ArrecadacaoIndiceFiltro filtro) {
		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Page<ArrecadacaoIndice> pageableResult = repository.filtro(filtro, pageable);
		
		PagedResponse<ArrecadacaoIndiceDto> result = new PagedResponse<>(Collections.emptyList(), pageableResult.getNumber(),
				pageableResult.getSize(), pageableResult.getTotalElements(), pageableResult.getTotalPages(),
				pageableResult.isLast());

		if (pageableResult.getNumberOfElements() > 0) {
			List<ArrecadacaoIndiceDto> responses = pageableResult.map(e -> {
				return new ArrecadacaoIndiceDto(e);
			}).getContent();
			result.setContent(responses);
		}

		return result;
	}
	
	public List<ArrecadacaoIndiceAnoPeriodicidadeDto> getAnoList(String ano) {
		List<ArrecadacaoIndiceAnoPeriodicidadeDto> anoList = null;
		if (Utils.checkStr(ano)) {
			anoList = anoRepository.getAnoList(Long.parseLong(ano));
		}
		return anoList;
	}
	
	public List<ArrecadacaoIndiceDto> getIndiceList(String indice) {
		List<ArrecadacaoIndiceDto> arrecadacaoIndiceDtoList = null;
		if (Utils.checkStr(indice)) {
			arrecadacaoIndiceDtoList = repository.findByIndice(indice);
		}
		return arrecadacaoIndiceDtoList;
	}
	
	@Transactional
	public ResponseEntity<?> updateIndice(ArrecadacaoIndiceDto dto) {
		
		ArrecadacaoIndice arrecadacaoIndice = repository.findById(dto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ArrecadacaoIndice", "id", dto.getId()));
		
		if(!arrecadacaoIndice.getIndice().equals(dto.getIndice())) {
			if (repository.existsByIndice(dto.getIndice())) {
				return Utils.badRequest(false, "Já existe um índice com este nome.");
			}
		}
		
		arrecadacaoIndice.setIndice(dto.getIndice());
		repository.save(arrecadacaoIndice);

		if(Utils.checkList(dto.getAnos())) {
			if(Objects.nonNull(arrecadacaoIndice.getId())) {
				List<ArrecadacaoIndiceAnoPeriodicidade> obj2 = anoRepository.findAllByArrecadacaoIndiceId(arrecadacaoIndice.getId());
				for(ArrecadacaoIndiceAnoPeriodicidade arrecadacaoIndiceAnoPeriodicidade :obj2 ) {
					anoRepository.delete(arrecadacaoIndiceAnoPeriodicidade);
				}
			} 
				
			for(ArrecadacaoIndiceAnoPeriodicidadeDto arrecadacaoIndiceAnoPeriodicidadeDto : dto.getAnos()) {
				
				ArrecadacaoIndiceAnoPeriodicidade arrecadacaoIndiceAnoPeriodicidade = new ArrecadacaoIndiceAnoPeriodicidade();
				arrecadacaoIndiceAnoPeriodicidade.setArrecadacaoIndice(arrecadacaoIndice);
				arrecadacaoIndiceAnoPeriodicidade.setAno(arrecadacaoIndiceAnoPeriodicidadeDto.getAno());
				arrecadacaoIndiceAnoPeriodicidade.setPeriodicidadeMes(PeriodicidadeMesEnum.getEnumByString(arrecadacaoIndiceAnoPeriodicidadeDto.getPeriodicidade()));
				anoRepository.save(arrecadacaoIndiceAnoPeriodicidade);
				
				if(Utils.checkList(arrecadacaoIndiceAnoPeriodicidadeDto.getMeses())) {
					
					for(ArrecadacaoIndiceMesDto dtoMes : arrecadacaoIndiceAnoPeriodicidadeDto.getMeses()) {
						
						String[] mes = dtoMes.getMes().split("/");
						for (int i = 0; i < mes.length; i++) {
							if(mes[i].equals("MARÇO")) {
								dtoMes.setMes("MARCO");
							}else{
								dtoMes.setMes(mes[i]);
							}
						
							ArrecadacaoIndiceMes arrecadacaoIndiceMes = new ArrecadacaoIndiceMes();
							arrecadacaoIndiceMes.setMes(MesEnum.getEnumByLabel(dtoMes.getMes()));
							arrecadacaoIndiceMes.setAliquota(dtoMes.getAliquota());
							arrecadacaoIndiceMes.setArrecadacaoIndiceAnoPeriodicidade(new ArrecadacaoIndiceAnoPeriodicidade(arrecadacaoIndiceAnoPeriodicidade.getId()));
							mesRepository.save(arrecadacaoIndiceMes);
						}
					}
				}
			}
		}
		
		return Utils.created(true, "Índice alterado com sucesso!");
	}
	
	@Override
	public void delete(Long id) {
		repository.deleteById(id);		
	}

	@Override
	public ArrecadacaoIndiceDto getById(Long id) {
		ArrecadacaoIndice arrecadacaoIndice = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ArrecadacaoIndice", "id", id));
		ArrecadacaoIndiceDto arrecadacaoIndiceDto = new ArrecadacaoIndiceDto(arrecadacaoIndice);
		usuarioService.setDadoCadastral(arrecadacaoIndiceDto, arrecadacaoIndice);
		return arrecadacaoIndiceDto;
	}

	public List<ArrecadacaoIndiceDto> getList(ArrecadacaoIndiceFiltro requestFilter) {
		List<ArrecadacaoIndiceDto> dtoList = new ArrayList<>();

		List<ArrecadacaoIndice> list = new ArrayList<>();

		list = repository.filtro(requestFilter);
		
		list.forEach(e -> {
			dtoList.add(new ArrecadacaoIndiceDto(e));
		});

		return dtoList;
		
	}
	
	@Override
	public PagedResponse<ArrecadacaoIndiceDto> getPagedList(PagedRequest pagedRequest,
			ArrecadacaoIndiceDto requestFilter) {
		return null;
	}

	@Override
	public List<ArrecadacaoIndiceDto> getList(ArrecadacaoIndiceDto requestFilter) {
		return null;
	}

	@Override
	public List<DadoBasicoDto> getDadoBasicoList(ArrecadacaoIndiceDto requestFilter) {
		return null;
	}

	@Override
	public void create(ArrecadacaoIndiceDto request) {}
	
	@Override
	public void update(ArrecadacaoIndiceDto dto) {}

}
