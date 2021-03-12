package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.DataIntegretyException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.AreaFormacao;
import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.PapelNomeEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.areaFormacao.AreaFormacaoRequest;
import com.rhlinkcon.payload.areaFormacao.AreaFormacaoResponse;
import com.rhlinkcon.payload.centroCusto.CentroCustoResponse;
import com.rhlinkcon.payload.convenio.ConvenioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.usuario.UsuarioResponse;
import com.rhlinkcon.repository.AreaFormacaoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.ModelMapper;

@Service
public class AreaFormacaoService {

	@Autowired
	private AreaFormacaoRepository areaFormacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createAreaFormacao(AreaFormacaoRequest areaFormacaoRequest) {

		// Creating user's account
		AreaFormacao areaFormacao = new AreaFormacao(areaFormacaoRequest);

		areaFormacaoRepository.save(areaFormacao);

	}

	public void updateAreaFormacao(AreaFormacaoRequest areaFormacaoRequest) {

		// Updating user's account
		AreaFormacao areaFormacao = areaFormacaoRepository.findById(areaFormacaoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("AreaFormacao", "id", areaFormacaoRequest.getId()));

		areaFormacao.setAreaFormacao(areaFormacaoRequest.getAreaFormacao());

		areaFormacaoRepository.save(areaFormacao);

	}

	public void deleteAreaFormacao(Long id) {
		AreaFormacao areaFormacao = areaFormacaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AreaFormacao", "id", id));
		try {
			areaFormacaoRepository.delete(areaFormacao);
		} catch (DataIntegrityViolationException  e) {
			throw new DataIntegretyException("Erro! Esta Área de formação está sendo usada em outra parte do sistema");
		}
	}

	public PagedResponse<AreaFormacaoResponse> getAllAreas(int page, int size, String areaFormacao, String order) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<AreaFormacao> areas = areaFormacaoRepository.findByAreaFormacaoIgnoreCaseContaining(areaFormacao, pageable);

		if (areas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), areas.getNumber(), areas.getSize(), areas.getTotalElements(), areas.getTotalPages(),
					areas.isLast());
		}

		List<AreaFormacaoResponse> areasResponses = areas.map(area -> {
			return new AreaFormacaoResponse(area);
		}).getContent();
		return new PagedResponse<>(areasResponses, areas.getNumber(), areas.getSize(), areas.getTotalElements(), areas.getTotalPages(), areas.isLast());

	}

	public List<AreaFormacaoResponse> searchByAreaFormacao(String search) {
		List<AreaFormacaoResponse> itensResponses = new ArrayList<AreaFormacaoResponse>();

		areaFormacaoRepository.findByAreaFormacaoIgnoreCaseContaining(search).forEach(item -> itensResponses.add(new AreaFormacaoResponse(item)));
		return itensResponses;
	}

	public AreaFormacaoResponse getAreaFormacaoById(Long areaId) {
		AreaFormacao areaFormacao = areaFormacaoRepository.findById(areaId).orElseThrow(() -> new ResourceNotFoundException("User", "id", areaId));

		Usuario userCreated = usuarioRepository.findById(areaFormacao.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", areaFormacao.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(areaFormacao.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", areaFormacao.getUpdatedBy()));

		AreaFormacaoResponse areaResponse = new AreaFormacaoResponse(areaFormacao);

		areaResponse.setCriadoPor(userCreated.getNome());
		areaResponse.setCriadoEm(areaFormacao.getCreatedAt());
		areaResponse.setAlteradoPor(userUpdated.getNome());
		areaResponse.setAlteradoEm(areaFormacao.getUpdatedAt());

		return areaResponse;
	}

	public List<AreaFormacaoResponse> getAllAreas() {

		List<AreaFormacao> areasFormacao = areaFormacaoRepository.findAll();
		List<AreaFormacaoResponse> areaFormacaoResponse = new ArrayList();

		if (!areasFormacao.isEmpty()) {
			for (AreaFormacao areaFormacao : areasFormacao) {
				AreaFormacaoResponse areaFormacaoResp = new AreaFormacaoResponse(areaFormacao);

				areaFormacaoResponse.add(areaFormacaoResp);
			}
			return areaFormacaoResponse;
		}

		return null;
	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

}
