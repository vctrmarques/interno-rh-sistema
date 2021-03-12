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

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Consignado;
import com.rhlinkcon.model.Endereco;
import com.rhlinkcon.model.ModalidadeConsignadoEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.consignado.ConsignadoRequest;
import com.rhlinkcon.payload.consignado.ConsignadoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CentroCustoRepository;
import com.rhlinkcon.repository.ConsignadoRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.banco.BancoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class ConsignadoService {

	@Autowired
	private ConsignadoRepository consignadoRepository;

	@Autowired
	private CentroCustoRepository centroCustoRepository;

	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;

	@Autowired
	private BancoRepository bancoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createConsignado(ConsignadoRequest consignadoRequest) {

		// Creating user's account
		Consignado consignado = new Consignado(consignadoRequest);

		consignado.setCentroCusto(centroCustoRepository.findById(consignadoRequest.getCentroCustoId()).orElseThrow(
				() -> new ResourceNotFoundException("Consignado", "id", consignadoRequest.getCentroCustoId())));

		if(Objects.nonNull(consignadoRequest.getBancoId()))
			consignado.setBanco(bancoRepository.findById(consignadoRequest.getBancoId())
					.orElseThrow(() -> new ResourceNotFoundException("Consignado", "id", consignadoRequest.getBancoId())));

		if(Objects.nonNull(consignadoRequest.getUnidadeFederativaId()))
			consignado.setUnidadeFederativa(unidadeFederativaRepository.findById(consignadoRequest.getUnidadeFederativaId()).orElseThrow(
					() -> new ResourceNotFoundException("Consignado", "id", consignadoRequest.getUnidadeFederativaId())));
		else
			consignado.setUnidadeFederativa(null);
		consignadoRepository.save(consignado);

	}

	public void updateConsignado(ConsignadoRequest consignadoRequest) {

		// Updating user's account
		Consignado consignado = consignadoRepository.findById(consignadoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Consignado", "id", consignadoRequest.getId()));

		consignado.setDescricao(consignadoRequest.getDescricao());

		if (consignadoRequest.getEndereco() != null)
			consignado.setEndereco(new Endereco(consignadoRequest.getEndereco()));
		
		consignado.setCentroCusto(centroCustoRepository.findById(consignadoRequest.getCentroCustoId()).orElseThrow(
				() -> new ResourceNotFoundException("Consignado", "id", consignadoRequest.getCentroCustoId())));

		if(Objects.nonNull(consignadoRequest.getBancoId()))
			consignado.setBanco(bancoRepository.findById(consignadoRequest.getBancoId())
					.orElseThrow(() -> new ResourceNotFoundException("Consignado", "id", consignadoRequest.getBancoId())));
		else
			consignado.setBanco(null);
		
		consignado.setTelefone(consignadoRequest.getTelefone());
		consignado.setCnpj(consignadoRequest.getCnpj());
		consignado.setRg(consignadoRequest.getRg());
		consignado.setOrgaoExpedidor(consignadoRequest.getOrgaoExpedidor());
		consignado.setMargemConsignavel(consignadoRequest.getMargemConsignavel());
		consignado.setAgencia(consignadoRequest.getAgencia());
		consignado.setContaCorrente(consignadoRequest.getContaCorrente());
		consignado.setContrato(consignadoRequest.getContrato());
		consignado.setTipoCalculo(consignadoRequest.getTipoCalculo());
		consignado.setEmail(consignadoRequest.getEmail());
		consignado.setTaxaOperacional(consignadoRequest.getTaxaOperacional());
		consignado.setTac(consignadoRequest.getTac());
		consignado.setTaxaCadastroSiglaConsignado(consignadoRequest.getTaxaCadastroSiglaConsignado());
		consignado.setSiglaConsignado(consignadoRequest.getSiglaConsignado());
		consignado.setRegistroAns(consignadoRequest.getRegistroAns());
		consignado.setCodigoConvergencia(consignadoRequest.getCodigoConvergencia());
		consignado.setDigitoConvergencia(consignadoRequest.getDigitoConvergencia());
		consignado.setExtratoConvenente(consignadoRequest.getExtratoConvenente());
		consignado.setNomeConvenente(consignadoRequest.getNomeConvenente());
		consignado.setPvResponsavel(consignadoRequest.getPvResponsavel());
		consignado.setDataCompetenciaProcessamento(consignadoRequest.getDataCompetenciaProcessamento());
		consignado.setDigitoConvenente(consignadoRequest.getDigitoConvenente());
		consignado.setVerbaDesconto(consignadoRequest.getVerbaDesconto());

		if (!Objects.isNull(consignadoRequest.getModalidade()) && !consignadoRequest.getModalidade().isEmpty())
			consignado.setModalidade(ModalidadeConsignadoEnum.getEnumByString(consignadoRequest.getModalidade()));
		else
			consignado.setModalidade(null);
		
		if(Objects.nonNull(consignadoRequest.getUnidadeFederativaId()))
			consignado.setUnidadeFederativa(unidadeFederativaRepository.findById(consignadoRequest.getUnidadeFederativaId())
					.orElseThrow(
					() -> new ResourceNotFoundException("Consignado", "id", consignadoRequest.getUnidadeFederativaId())));
		else
			consignado.setUnidadeFederativa(null);
		consignadoRepository.save(consignado);

	}

	public void deleteConsignado(Long id) {
		Consignado consignado = consignadoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Consignado", "id", id));

		consignadoRepository.delete(consignado);
	}

	public PagedResponse<ConsignadoResponse> getAllConsignados(int page, int size, String contrato, String order) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Consignado> consignados = null;

		if (!contrato.isEmpty()) {
			consignados = consignadoRepository.findByContrato(contrato, pageable);
		} else {
			consignados = consignadoRepository.findAll(pageable);
		}

		if (consignados.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), consignados.getNumber(), consignados.getSize(),
					consignados.getTotalElements(), consignados.getTotalPages(), consignados.isLast());
		}

		List<ConsignadoResponse> consignadosResponse = consignados.map(consignadoResponse -> {
			return new ConsignadoResponse(consignadoResponse);
		}).getContent();
		return new PagedResponse<>(consignadosResponse, consignados.getNumber(), consignados.getSize(),
				consignados.getTotalElements(), consignados.getTotalPages(), consignados.isLast());

	}

	public ConsignadoResponse getConsignadoById(Long consignadoId) {
		Consignado consignado = consignadoRepository.findById(consignadoId)
				.orElseThrow(() -> new ResourceNotFoundException("Consignado", "id", consignadoId));

		Usuario userCreated = usuarioRepository.findById(consignado.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", consignado.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(consignado.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", consignado.getUpdatedBy()));

		ConsignadoResponse consignadoResponse = new ConsignadoResponse(consignado, consignado.getCreatedAt(),
				userCreated.getNome(), consignado.getUpdatedAt(), userUpdated.getNome());

		return consignadoResponse;

	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

	public List<ConsignadoResponse> getAllConsignadosFindByDescricao(String search) {

		List<Consignado> consignados = consignadoRepository.findByDescricaoIgnoreCaseContaining(search);
		List<ConsignadoResponse> consignadosResponse = new ArrayList();

		if (!consignados.isEmpty()) {
			for (Consignado consignado: consignados) {
				consignadosResponse.add(new ConsignadoResponse(consignado));
			}
			return consignadosResponse;
		}

		return null;
	}		

}
