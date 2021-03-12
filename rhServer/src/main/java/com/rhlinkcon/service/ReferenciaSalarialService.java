package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.rhlinkcon.model.ReferenciaSalarial;
import com.rhlinkcon.model.NivelSalarialHistorico;
import com.rhlinkcon.model.NivelSalarialHistoricoOrigemAjusteEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.referenciaSalarial.ReferenciaSalarialRequest;
import com.rhlinkcon.payload.referenciaSalarial.ReferenciaSalarialResponse;
import com.rhlinkcon.repository.NivelSalarialHistoricoRepository;
import com.rhlinkcon.repository.ReferenciaSalarialRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ReferenciaSalarialService {

	@Autowired
	private ReferenciaSalarialRepository referenciaSalarialRepository;

	@Autowired
	private NivelSalarialHistoricoRepository nivelSalarialHistoricoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public ReferenciaSalarialResponse getReferenciaSalarialById(Long nivelSalarialId) {
		ReferenciaSalarial nivelSalarial = referenciaSalarialRepository.findById(nivelSalarialId)
				.orElseThrow(() -> new ResourceNotFoundException("NivelSalarial", "id", nivelSalarialId));

		ReferenciaSalarialResponse nivelSalarialResponse = new ReferenciaSalarialResponse(nivelSalarial);

		Usuario criadoPor = usuarioRepository.findById(nivelSalarial.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", nivelSalarial.getCreatedBy()));
		nivelSalarialResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(nivelSalarial.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", nivelSalarial.getUpdatedBy()));
		nivelSalarialResponse.setAlteradoPor(alteradoPor.getNome());

		return nivelSalarialResponse;
	}

	public List<ReferenciaSalarialResponse> getAllReferenciasSalariais() {
		List<ReferenciaSalarial> list = referenciaSalarialRepository.findAll();

		List<ReferenciaSalarialResponse> listResultResponse = new ArrayList<>();
		for (ReferenciaSalarial nivelSalarial : list) {
			listResultResponse.add(new ReferenciaSalarialResponse(nivelSalarial));
		}
		return listResultResponse;
	}

	public PagedResponse<ReferenciaSalarialResponse> getAllReferenciasSalariaisPage(int page, int size, String order,
			String descricao) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		Direction direction = Sort.Direction.ASC;
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ReferenciaSalarial> nivelSalarials = null;

		if (!Objects.isNull(descricao) && !descricao.isEmpty()) {
			nivelSalarials = referenciaSalarialRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);
		} else {
			nivelSalarials = referenciaSalarialRepository.findAll(pageable);
		}

		if (nivelSalarials.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), nivelSalarials.getNumber(), nivelSalarials.getSize(),
					nivelSalarials.getTotalElements(), nivelSalarials.getTotalPages(), nivelSalarials.isLast());
		}

		List<ReferenciaSalarialResponse> nivelSalarialResponses = nivelSalarials.map(nivelSalarial -> {
			ReferenciaSalarialResponse nivelSalarialResponse = new ReferenciaSalarialResponse(nivelSalarial);
			// nivelSalarialResponse.setHistorico(getHistorico(nivelSalarial));
			return nivelSalarialResponse;
		}).getContent();
		return new PagedResponse<>(nivelSalarialResponses, nivelSalarials.getNumber(), nivelSalarials.getSize(),
				nivelSalarials.getTotalElements(), nivelSalarials.getTotalPages(), nivelSalarials.isLast());

	}

	public void deleteNivelSalarial(Long id) {
		ReferenciaSalarial nivelSalarial = referenciaSalarialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("NivelSalarial", "id", id));

		//List<NivelSalarialHistorico> historico = nivelSalarialHistoricoRepository.findByNivelSalarial(nivelSalarial);
		//if (historico.size() <= 0) {
			referenciaSalarialRepository.delete(nivelSalarial);
		//}

	}

	public void createReferenciaSalarial(ReferenciaSalarialRequest referenciaSalarialRequest) {

		ReferenciaSalarial nivelSalarial = new ReferenciaSalarial(referenciaSalarialRequest);
		
		referenciaSalarialRepository.save(nivelSalarial);

	}

	public void updateReferenciaSalarial(ReferenciaSalarialRequest nivelSalarialRequest) {

		ReferenciaSalarial referenciaSalarial = referenciaSalarialRepository.findById(nivelSalarialRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("NivelSalarial", "id", nivelSalarialRequest.getId()));

		boolean historico = !nivelSalarialRequest.getValor().equals(referenciaSalarial.getValor());
		double valorAntigoProHistorico = referenciaSalarial.getValor();

		referenciaSalarial.setCodigo(nivelSalarialRequest.getCodigo());
		referenciaSalarial.setDescricao(nivelSalarialRequest.getDescricao());
		referenciaSalarial.setValor(nivelSalarialRequest.getValor());
		referenciaSalarial.setNivelReferencia(Utils.setBool(nivelSalarialRequest.isNivelReferencia()));
		referenciaSalarial.setMesAnoCompetencia(nivelSalarialRequest.getMesAnoCompetencia());
		referenciaSalarialRepository.save(referenciaSalarial);

		if (historico) {
			createHistorico(referenciaSalarial, valorAntigoProHistorico);
		}
	}

	private void createHistorico(ReferenciaSalarial nivelSalarial, double valorAntigoProHistorico) {
		NivelSalarialHistorico historico = new NivelSalarialHistorico();
		historico.setNivelSalarial(nivelSalarial);
		historico.setOrigemAjuste(NivelSalarialHistoricoOrigemAjusteEnum.AJUSTE_MANUAL);
		historico.setDataAjuste(new Date());
		historico.setValorOriginal(valorAntigoProHistorico);
		historico.setValorAjustado(nivelSalarial.getValor());

		nivelSalarialHistoricoRepository.save(historico);
	}

}
