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
import com.rhlinkcon.model.Endereco;
import com.rhlinkcon.model.TipoInscricaoEnum;
import com.rhlinkcon.model.TomadorServico;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.tomadorServico.TomadorServicoRequest;
import com.rhlinkcon.payload.tomadorServico.TomadorServicoResponse;
import com.rhlinkcon.repository.CodigoPagamentoGpsRepository;
import com.rhlinkcon.repository.CodigoRecolhimentoRepository;
import com.rhlinkcon.repository.TomadorServicoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class TomadorServicoService {

	@Autowired
	private TomadorServicoRepository tomadorServicoRepository;

	@Autowired
	private CodigoRecolhimentoRepository codigoRecolhimentoRepository;

	@Autowired
	private CodigoPagamentoGpsRepository codigoPagamentoGpsRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CompensacaoService compensacaoService;

	public void createTomadorServico(TomadorServicoRequest tomadorServicoRequest) {

		TomadorServico tomadorServico = new TomadorServico(tomadorServicoRequest);

		if (tomadorServicoRequest.getCodigoRecolhimentoId() != null)
			tomadorServico.setCodigoRecolhimento(
					codigoRecolhimentoRepository.findById(tomadorServicoRequest.getCodigoRecolhimentoId())
							.orElseThrow(() -> new ResourceNotFoundException("CodigoReclhimento", "id",
									tomadorServicoRequest.getCodigoRecolhimentoId())));
		else
			tomadorServico.setCodigoRecolhimento(null);
		if (tomadorServicoRequest.getCodigoPagamentoGpsId() != null)
			tomadorServico.setCodigoPagamentoGps(
					codigoPagamentoGpsRepository.findById(tomadorServicoRequest.getCodigoPagamentoGpsId())
							.orElseThrow(() -> new ResourceNotFoundException("CodigoPagamentoGps", "id",
									tomadorServicoRequest.getCodigoPagamentoGpsId())));
		else
			tomadorServico.setCodigoPagamentoGps(null);
		tomadorServicoRepository.save(tomadorServico);

	}

	public List<TomadorServicoResponse> searchAllByRazaoSocial(String search, String essencial) {
		List<TomadorServicoResponse> itensResponses = new ArrayList<TomadorServicoResponse>();

		List<TomadorServico> tomadorServicos = tomadorServicoRepository.findByRazaoSocialIgnoreCaseContaining(search);

		if (!tomadorServicos.isEmpty()) {
			for (TomadorServico tomadorServico : tomadorServicos) {
				TomadorServicoResponse tomadorServicoResponse = null;
				if (Utils.checkStr(essencial)) {
					tomadorServicoResponse = new TomadorServicoResponse();
					tomadorServicoResponse.setId(tomadorServico.getId());
					tomadorServicoResponse.setRazaoSocial(tomadorServico.getRazaoSocial());
				} else {
					tomadorServicoResponse = new TomadorServicoResponse(tomadorServico);
				}
				itensResponses.add(tomadorServicoResponse);
			}
		}

		return itensResponses;
	}

	public void updateTomadorServico(TomadorServicoRequest tomadorServicoRequest) {

		// Updating user's account
		TomadorServico tomadorServico = tomadorServicoRepository.findById(tomadorServicoRequest.getId()).orElseThrow(
				() -> new ResourceNotFoundException("TomadorServico", "id", tomadorServicoRequest.getId()));

		tomadorServico.setTipoInscricao(TipoInscricaoEnum.getEnumByString(tomadorServicoRequest.getTipoInscricao()));
		tomadorServico.setEndereco(new Endereco(tomadorServicoRequest.getEndereco()));
		tomadorServico.setRazaoSocial(tomadorServicoRequest.getRazaoSocial());
		tomadorServico.setCnpj(tomadorServicoRequest.getCnpj());
		tomadorServico.setEndereco(new Endereco(tomadorServicoRequest.getEndereco()));

		if (tomadorServicoRequest.getCodigoRecolhimentoId() != null)
			tomadorServico.setCodigoRecolhimento(
					codigoRecolhimentoRepository.findById(tomadorServicoRequest.getCodigoRecolhimentoId())
							.orElseThrow(() -> new ResourceNotFoundException("CodigoReclhimento", "id",
									tomadorServicoRequest.getCodigoRecolhimentoId())));
		else
			tomadorServico.setCodigoRecolhimento(null);
		
		if (tomadorServicoRequest.getCodigoPagamentoGpsId() != null)
			tomadorServico.setCodigoPagamentoGps(
					codigoPagamentoGpsRepository.findById(tomadorServicoRequest.getCodigoPagamentoGpsId())
							.orElseThrow(() -> new ResourceNotFoundException("CodigoPagamentoGps", "id",
									tomadorServicoRequest.getCodigoPagamentoGpsId())));
		else
			tomadorServico.setCodigoPagamentoGps(null);
		
		tomadorServicoRepository.save(tomadorServico);

	}

	public void deleteTomadorServico(Long id) {
		TomadorServico tomadorServico = tomadorServicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TomadorServico", "id", id));

		compensacaoService.deleteCompensacao(id);

		tomadorServicoRepository.delete(tomadorServico);
	}

	public PagedResponse<TomadorServicoResponse> getAllTomadoresServico(int page, int size, String cnpj, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<TomadorServico> tomadores = tomadorServicoRepository.findByCnpjIgnoreCaseContaining(cnpj, pageable);

		if (tomadores.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), tomadores.getNumber(), tomadores.getSize(),
					tomadores.getTotalElements(), tomadores.getTotalPages(), tomadores.isLast());
		}

		List<TomadorServicoResponse> tomadoresServicoResponse = tomadores.map(tomador -> {
			return new TomadorServicoResponse(tomador);
		}).getContent();
		return new PagedResponse<>(tomadoresServicoResponse, tomadores.getNumber(), tomadores.getSize(),
				tomadores.getTotalElements(), tomadores.getTotalPages(), tomadores.isLast());

	}

	public TomadorServicoResponse getTomadorServicoById(Long tomadorServicoId) {
		TomadorServico tomadorServico = tomadorServicoRepository.findById(tomadorServicoId)
				.orElseThrow(() -> new ResourceNotFoundException("TomadorServico", "id", tomadorServicoId));

		Usuario userCreated = usuarioRepository.findById(tomadorServico.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", tomadorServico.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(tomadorServico.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", tomadorServico.getUpdatedBy()));

		TomadorServicoResponse tomadorServicoResponse = new TomadorServicoResponse(tomadorServico,
				tomadorServico.getCreatedAt(), userCreated.getNome(), tomadorServico.getUpdatedAt(),
				userUpdated.getNome());

		return tomadorServicoResponse;
	}

}
