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
import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.TipoCentroCustoEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.centroCusto.CentroCustoRequest;
import com.rhlinkcon.payload.centroCusto.CentroCustoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CentroCustoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class CentroCustoService {

	@Autowired
	private CentroCustoRepository centroCustoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<DadoBasicoDto> search() {
		List<DadoBasicoDto> itensResponses = new ArrayList<DadoBasicoDto>();

		List<CentroCusto> listResult = centroCustoRepository.findAll();

		if (Objects.nonNull(listResult) && !listResult.isEmpty()) {
			for (CentroCusto centroCusto : listResult) {
				itensResponses.add(new DadoBasicoDto(centroCusto));
			}
		}

		return itensResponses;
	}

	public void createCentroCusto(CentroCustoRequest centroCustoRequest) {

		// Creating user's account
		CentroCusto centroCusto = new CentroCusto(centroCustoRequest);

		centroCustoRepository.save(centroCusto);

	}

	public void updateCentroCusto(CentroCustoRequest centroCustoRequest) {

		// Updating user's account
		CentroCusto centroCusto = centroCustoRepository.findById(centroCustoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("CentroCusto", "id", centroCustoRequest.getId()));

		centroCusto.setCodigo(centroCustoRequest.getCodigo());
		centroCusto.setDescricao(centroCustoRequest.getDescricao());
		centroCusto.setContaCredito(centroCustoRequest.getContaCredito());
		centroCusto.setContaDebito(centroCustoRequest.getContaDebito());
		centroCusto.setEfetivo(centroCustoRequest.getEfetivo());
		centroCusto.setNivel(centroCustoRequest.getNivel());
		centroCusto.setTipoCredito(centroCustoRequest.getTipoCredito());
		centroCusto.setTipoDebito(centroCustoRequest.getTipoDebito());
		centroCusto.setCnpj(centroCustoRequest.getCnpj());

		if (Objects.nonNull(centroCustoRequest.getTipoCentroCusto()))
			centroCusto.setTipoCentroCusto(TipoCentroCustoEnum.getEnumByString(centroCustoRequest.getTipoCentroCusto()));
		else
			centroCusto.setTipoCentroCusto(null);
		
		centroCustoRepository.save(centroCusto);

	}

	public void deleteCentroCusto(Long id) {
		CentroCusto centroCusto = centroCustoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CentroCusto", "id", id));

		centroCustoRepository.delete(centroCusto);
	}

	public PagedResponse<CentroCustoResponse> getAllCentrosCustos(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<CentroCusto> centrosCustos = centroCustoRepository.findByDescricaoIgnoreCaseContaining(descricao,
				pageable);

		if (centrosCustos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), centrosCustos.getNumber(), centrosCustos.getSize(),
					centrosCustos.getTotalElements(), centrosCustos.getTotalPages(), centrosCustos.isLast());
		}

		List<CentroCustoResponse> centrosCustosResponse = centrosCustos.map(centroCustoResponse -> {
			return new CentroCustoResponse(centroCustoResponse);
		}).getContent();
		return new PagedResponse<>(centrosCustosResponse, centrosCustos.getNumber(), centrosCustos.getSize(),
				centrosCustos.getTotalElements(), centrosCustos.getTotalPages(), centrosCustos.isLast());

	}

	public CentroCustoResponse getCentroCustoById(Long centroCustoId) {
		CentroCusto centroCusto = centroCustoRepository.findById(centroCustoId)
				.orElseThrow(() -> new ResourceNotFoundException("CentroCusto", "id", centroCustoId));

		Usuario userCreated = usuarioRepository.findById(centroCusto.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", centroCusto.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(centroCusto.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", centroCusto.getUpdatedBy()));

		CentroCustoResponse centroCustoResponse = new CentroCustoResponse(centroCusto, centroCusto.getCreatedAt(),
				userCreated.getNome(), centroCusto.getUpdatedAt(), userUpdated.getNome());

		return centroCustoResponse;
	}

	public List<CentroCustoResponse> getAllCentrosCustos() {

		List<CentroCusto> centrosCustos = centroCustoRepository.findAll();
		List<CentroCustoResponse> centroCustoResponse = new ArrayList();

		if (!centrosCustos.isEmpty()) {
			for (CentroCusto centroCusto : centrosCustos) {
				CentroCustoResponse centroCustoResp = new CentroCustoResponse(centroCusto);

				centroCustoResponse.add(centroCustoResp);
			}
			return centroCustoResponse;
		}
		
		return null;
	}

	public Integer getNumeroContaCreditoDebito(Long id, String creditoDebito) {
			
			if(creditoDebito.equals("Crédito"))
				return centroCustoRepository.getContaCreditoById(id);
			
			if(creditoDebito.equals("Débito"))
				return centroCustoRepository.getContaDebitoById(id);
		
		return null;
	}
}
