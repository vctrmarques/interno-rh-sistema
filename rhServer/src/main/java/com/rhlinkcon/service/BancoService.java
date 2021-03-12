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

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.BancoFiltro;
import com.rhlinkcon.model.Agencia;
import com.rhlinkcon.model.Banco;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.banco.BancoRequest;
import com.rhlinkcon.payload.banco.BancoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.agencia.AgenciaRepository;
import com.rhlinkcon.repository.banco.BancoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class BancoService {

	@Autowired
	private BancoRepository bancoRepository;

	@Autowired
	private AgenciaRepository agenciaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<BancoResponse> getAllBancos() {
		List<Banco> bancos = bancoRepository.findAll();

		List<BancoResponse> listBancoResponse = new ArrayList<>();
		for (Banco banco : bancos) {
			listBancoResponse.add(new BancoResponse(banco, Projecao.BASICA));
		}
		return listBancoResponse;
	}

	public BancoResponse getBancoById(Long bancoId) {
		Banco banco = bancoRepository.findById(bancoId)
				.orElseThrow(() -> new ResourceNotFoundException("Banco", "id", bancoId));

		BancoResponse bancoResponse = new BancoResponse(banco, Projecao.COMPLETA);
		Usuario criadoPor = usuarioRepository.findById(banco.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", banco.getCreatedBy()));
		bancoResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(banco.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", banco.getUpdatedBy()));
		bancoResponse.setAlteradoPor(alteradoPor.getNome());

		return bancoResponse;
	}

	public PagedResponse<BancoResponse> getAllBancos(int page, int size, String order, BancoFiltro bancoFiltro,
			String projecaoStr) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Banco> bancos = bancoRepository.filtro(bancoFiltro, pageable);

		if (bancos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), bancos.getNumber(), bancos.getSize(),
					bancos.getTotalElements(), bancos.getTotalPages(), bancos.isLast());
		}

		List<BancoResponse> bancoResponses = bancos.map(banco -> {
			Projecao projecao = Projecao.BASICA;
			if (projecaoStr.equals(Projecao.MEDIA.toString())) {
				projecao = Projecao.MEDIA;
			} else if (projecaoStr.equals(Projecao.COMPLETA.toString())) {
				projecao = Projecao.COMPLETA;
			}
			return new BancoResponse(banco, projecao);
		}).getContent();
		return new PagedResponse<>(bancoResponses, bancos.getNumber(), bancos.getSize(), bancos.getTotalElements(),
				bancos.getTotalPages(), bancos.isLast());

	}

	public void deleteBanco(Long id) {
		Banco banco = bancoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Banco", "id", id));
		bancoRepository.delete(banco);
	}

	public void createBanco(BancoRequest bancoRequest) {

		Banco banco = new Banco(bancoRequest);

		setEntidades(banco, bancoRequest);

		bancoRepository.save(banco);

	}

	public void updateBanco(BancoRequest bancoRequest) {

		Banco banco = bancoRepository.findById(bancoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Banco", "id", bancoRequest.getId()));

		banco.setBloqueado(Utils.setBool(bancoRequest.isBloqueado()));
		banco.setCodigo(bancoRequest.getCodigo());
		banco.setNome(bancoRequest.getNome());
		banco.setPrincipal(bancoRequest.getPrincipal());
		banco.getAgencias().clear();
		if (bancoRequest.getAgenciasIds() != null && !bancoRequest.getAgenciasIds().isEmpty()) {
			for (Long agenciaId : bancoRequest.getAgenciasIds()) {
				Agencia agencia = agenciaRepository.findById(agenciaId)
						.orElseThrow(() -> new ResourceNotFoundException("Agencia", "id", agenciaId));
				banco.getAgencias().add(agencia);
			}
		}

		bancoRepository.save(banco);

	}

	public void setEntidades(Banco banco, BancoRequest bancoRequest) {

		if (bancoRequest.getAgenciasIds() != null && !bancoRequest.getAgenciasIds().isEmpty()) {
			for (Long agenciaId : bancoRequest.getAgenciasIds()) {
				Agencia agencia = agenciaRepository.findById(agenciaId)
						.orElseThrow(() -> new ResourceNotFoundException("Agencia", "id", agenciaId));
				banco.getAgencias().add(agencia);
			}
		}
	}

	public List<BancoResponse> findByNomeOrCodigo(String search) {
		List<Banco> bancos = bancoRepository.findByNomeIgnoreCaseContainingOrCodigoIgnoreCaseContaining(search, search);
		List<BancoResponse> response = new ArrayList<>();
		for (Banco banco : bancos) {
			response.add(new BancoResponse(banco));
		}
		return response;
	}

	public List<DadoBasicoDto> getDadoBasicoList() {
		List<DadoBasicoDto> dadoBasicoList = new ArrayList<DadoBasicoDto>();
		List<Banco> bancos = bancoRepository.findAll();
		for (Banco banco : bancos) {
			dadoBasicoList.add(new DadoBasicoDto(banco));
		}
		return dadoBasicoList;
	}
	
	public List<DadoBasicoDto> getDadoBasicoList(String search) {
		List<DadoBasicoDto> dadoBasicoList = new ArrayList<DadoBasicoDto>();
		List<Banco> bancos = bancoRepository.findByNomeIgnoreCaseContainingOrCodigoIgnoreCaseContaining(search, search);
		for (Banco banco : bancos) {
			dadoBasicoList.add(new DadoBasicoDto(banco));
		}
		return dadoBasicoList;
	}
}