package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
import com.rhlinkcon.model.GrupoVinculoEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.model.Vinculo;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.vinculo.VinculoRequest;
import com.rhlinkcon.payload.vinculo.VinculoResponse;
import com.rhlinkcon.repository.SituacaoFuncionalRepository;
import com.rhlinkcon.repository.EsocialRepository;
import com.rhlinkcon.repository.SefipRepository;
import com.rhlinkcon.repository.TipoContratoRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.repository.vinculo.VinculoRepository;
import com.rhlinkcon.util.Utils;

@Service
public class VinculoService {

	@Autowired
	private VinculoRepository vinculoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private SefipRepository sefipRepository;
	
	@Autowired
	private TipoContratoRepository tipoContratoRepository;
	
	@Autowired
	private EsocialRepository eSocialRepository;
	
	@Autowired
	private SituacaoFuncionalRepository situacaoFuncionalRepository;
	
	@Autowired
	private VerbaRepository verbaRepository;

	public List<VinculoResponse> getAllVinculos() {
		List<Vinculo> vinculos = vinculoRepository.findAll();

		List<VinculoResponse> listVinculoResponse = new ArrayList<>();
		for (Vinculo vinculo : vinculos) {
			listVinculoResponse.add(new VinculoResponse(vinculo));
		}
		return listVinculoResponse;
	}

	public List<VinculoResponse> searchAllByDescricao(String search) {
		List<VinculoResponse> itensResponses = new ArrayList<VinculoResponse>();
		vinculoRepository.findByDescricaoIgnoreCaseContaining(search)
				.forEach(item -> itensResponses.add(new VinculoResponse(item)));
		return itensResponses;
	}

	public VinculoResponse getVinculoById(Long vinculoId) {
		Vinculo vinculo = vinculoRepository.findById(vinculoId)
				.orElseThrow(() -> new ResourceNotFoundException("Vinculo", "id", vinculoId));

		Usuario userCreated = usuarioRepository.findById(vinculo.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", vinculo.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(vinculo.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", vinculo.getUpdatedBy()));

		VinculoResponse vinculoResponse = new VinculoResponse(vinculo, vinculo.getCreatedAt(),
				userCreated.getNome(), vinculo.getUpdatedAt(), userUpdated.getNome());

		return vinculoResponse;
	}

	public PagedResponse<VinculoResponse> getAllVinculos(int page, int size, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Vinculo> vinculos = vinculoRepository.findAll(pageable);

		if (vinculos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), vinculos.getNumber(), vinculos.getSize(),
					vinculos.getTotalElements(), vinculos.getTotalPages(), vinculos.isLast());
		}

		List<VinculoResponse> vinculoResponses = vinculos.map(vinculo -> {
			return new VinculoResponse(vinculo);
		}).getContent();
		return new PagedResponse<>(vinculoResponses, vinculos.getNumber(), vinculos.getSize(),
				vinculos.getTotalElements(), vinculos.getTotalPages(), vinculos.isLast());

	}

	public void deleteVinculo(Long id) {
		Vinculo vinculo = vinculoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Vinculo", "id", id));
		vinculoRepository.delete(vinculo);
	}

	public void createVinculo(VinculoRequest vinculoRequest) {

		Vinculo vinculo = new Vinculo(vinculoRequest);
		
		setEntidades(vinculoRequest, vinculo);

		vinculoRepository.save(vinculo);

	}

	public void updateVinculo(VinculoRequest vinculoRequest) {

		Vinculo vinculo = vinculoRepository.findById(vinculoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Vinculo", "id", vinculoRequest.getId()));
		vinculo.setDescricao(vinculoRequest.getDescricao());
		vinculo.setGrupo(GrupoVinculoEnum.valueOf(vinculoRequest.getGrupo()));
		vinculo.setDataFinal(vinculoRequest.getDataFinal());
		vinculo.setDataInicial(vinculoRequest.getDataInicial());
		
		setEntidades(vinculoRequest, vinculo);

		vinculoRepository.save(vinculo);

	}

	private void setEntidades(VinculoRequest vinculoRequest, Vinculo vinculo) {
		if (Objects.nonNull(vinculoRequest.getVinculoApos()))
			vinculo.setVinculoApos(vinculoRepository.findById(vinculoRequest.getVinculoApos().getId()).orElseThrow(
					() -> new ResourceNotFoundException("Vinculo", "id", vinculoRequest.getVinculoApos().getId())));

		if (Utils.checkStr(vinculoRequest.getGrupo()))
			vinculo.setGrupo(GrupoVinculoEnum.valueOf(vinculoRequest.getGrupo()));

		if (Objects.nonNull(vinculoRequest.getCategoriaSefip()))
			vinculo.setCategoriaSefip(sefipRepository.findById(vinculoRequest.getCategoriaSefip().getId()).orElseThrow(
					() -> new ResourceNotFoundException("Sefip", "id", vinculoRequest.getCategoriaSefip().getId())));

		if (Objects.nonNull(vinculoRequest.getOcorrenciaSefip()))
			vinculo.setOcorrenciaSefip(sefipRepository.findById(vinculoRequest.getOcorrenciaSefip().getId()).orElseThrow(
					() -> new ResourceNotFoundException("Sefip", "id", vinculoRequest.getOcorrenciaSefip().getId())));

		if (Objects.nonNull(vinculoRequest.getCategoriaEsocial()))
			vinculo.setCategoriaEsocial(eSocialRepository.findById(vinculoRequest.getCategoriaEsocial().getId()).orElseThrow(
					() -> new ResourceNotFoundException("ESocial", "id", vinculoRequest.getCategoriaEsocial().getId())));

		if (Objects.nonNull(vinculoRequest.getSituacaoInicial()))
			vinculo.setSituacaoInicial(situacaoFuncionalRepository.findById(vinculoRequest.getSituacaoInicial().getId()).orElseThrow(
					() -> new ResourceNotFoundException("Afastamento", "id", vinculoRequest.getSituacaoInicial().getId())));
		
		if(Objects.nonNull(vinculoRequest.getTipoContrato()))
			vinculo.setTipoContrato(tipoContratoRepository.findById(vinculoRequest.getTipoContrato().getId()).orElseThrow(
					() -> new ResourceNotFoundException("TipoContrato", "id", vinculoRequest.getTipoContrato().getId())));
		
		 vinculo.setVerbas(new HashSet<>());
	        if (Utils.checkList(vinculoRequest.getVerbaIds())) {
	        	vinculoRequest.getVerbaIds().forEach(id -> 
	        		vinculo.getVerbas().add(verbaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Verba", "id", id)))
	        		);	        	
	        }

	}

}
