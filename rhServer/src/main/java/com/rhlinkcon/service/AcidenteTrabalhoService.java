package com.rhlinkcon.service;

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
import com.rhlinkcon.model.AcidenteTrabalho;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.TomadorServico;
import com.rhlinkcon.payload.acidenteTrabalho.AcidenteTrabalhoRequest;
import com.rhlinkcon.payload.acidenteTrabalho.AcidenteTrabalhoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.AcidenteTrabalhoRepository;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.repository.TomadorServicoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class AcidenteTrabalhoService {

	@Autowired
	private AcidenteTrabalhoRepository acidenteTrabalhoRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private TomadorServicoRepository tomadorServicoRepository;

	@Autowired
	private AnexoRepository anexoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public AcidenteTrabalhoResponse getAcidenteTrabalhoById(Long acidenteTrabalhoId) {
		AcidenteTrabalho acidenteTrabalho = acidenteTrabalhoRepository.findById(acidenteTrabalhoId)
				.orElseThrow(() -> new ResourceNotFoundException("AcidenteTrabalho", "id", acidenteTrabalhoId));

		AcidenteTrabalhoResponse acidenteTrabalhoResponse = new AcidenteTrabalhoResponse(acidenteTrabalho);

		acidenteTrabalhoResponse.setCriadoPor(usuarioService.criadoPor(acidenteTrabalho));
		acidenteTrabalhoResponse.setAlteradoPor(usuarioService.alteradoPor(acidenteTrabalho));

		return acidenteTrabalhoResponse;
	}

	public PagedResponse<AcidenteTrabalhoResponse> getAllAcidentesDeTrabalho(int page, int size, String order,
			String nome) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<AcidenteTrabalho> acidenteTrabalhos = null;

//		if (!Objects.isNull(nome) && !nome.isEmpty()) {
//			acidenteTrabalhos = acidenteTrabalhoRepository.findByNomeIgnoreCaseContaining(nome, pageable);
//		} else {
		acidenteTrabalhos = acidenteTrabalhoRepository.findAll(pageable);
//		}

		if (acidenteTrabalhos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), acidenteTrabalhos.getNumber(),
					acidenteTrabalhos.getSize(), acidenteTrabalhos.getTotalElements(),
					acidenteTrabalhos.getTotalPages(), acidenteTrabalhos.isLast());
		}

		List<AcidenteTrabalhoResponse> acidenteTrabalhoResponses = acidenteTrabalhos.map(acidenteTrabalho -> {
			return new AcidenteTrabalhoResponse(acidenteTrabalho);
		}).getContent();
		return new PagedResponse<>(acidenteTrabalhoResponses, acidenteTrabalhos.getNumber(),
				acidenteTrabalhos.getSize(), acidenteTrabalhos.getTotalElements(), acidenteTrabalhos.getTotalPages(),
				acidenteTrabalhos.isLast());

	}

	public void deleteAcidenteTrabalho(Long id) {
		AcidenteTrabalho acidenteTrabalho = acidenteTrabalhoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("AcidenteTrabalho", "id", id));
		acidenteTrabalhoRepository.delete(acidenteTrabalho);
	}

	public void createAcidenteTrabalho(AcidenteTrabalhoRequest acidenteTrabalhoRequest) {

		AcidenteTrabalho acidenteTrabalho = new AcidenteTrabalho();

		carregarDados(acidenteTrabalho, acidenteTrabalhoRequest);

		acidenteTrabalhoRepository.save(acidenteTrabalho);

	}

	public void updateAcidenteTrabalho(AcidenteTrabalhoRequest acidenteTrabalhoRequest) {

		AcidenteTrabalho acidenteTrabalho = acidenteTrabalhoRepository.findById(acidenteTrabalhoRequest.getId())
				.orElseThrow(
						() -> new ResourceNotFoundException("AcidenteTrabalho", "id", acidenteTrabalhoRequest.getId()));

		carregarDados(acidenteTrabalho, acidenteTrabalhoRequest);

		acidenteTrabalhoRepository.save(acidenteTrabalho);

	}

	private AcidenteTrabalho carregarDados(AcidenteTrabalho acidenteTrabalho,
			AcidenteTrabalhoRequest acidenteTrabalhoRequest) {
		acidenteTrabalho.setAviso(acidenteTrabalhoRequest.getAviso());
		acidenteTrabalho.setSetorLocal(acidenteTrabalhoRequest.getSetorLocal());
		acidenteTrabalho.setCausa(acidenteTrabalhoRequest.getCausa());
		acidenteTrabalho.setDataHoraAcidente(acidenteTrabalhoRequest.getDataHoraAcidente());
		acidenteTrabalho.setDiasAfastado(acidenteTrabalhoRequest.getDiasAfastado());
		acidenteTrabalho.setDataPrevistaVolta(acidenteTrabalhoRequest.getDataPrevistaVolta());
		acidenteTrabalho.setResultado(acidenteTrabalhoRequest.getResultado());
		acidenteTrabalho.setNumeroCat(acidenteTrabalhoRequest.getNumeroCat());
		acidenteTrabalho.setDocumentoEmitido(acidenteTrabalhoRequest.isDocumentoEmitido());
		acidenteTrabalho.setDataEmissaoDocumento(acidenteTrabalhoRequest.getDataEmissaoDocumento());

		// set de entidade
		if (Objects.nonNull(acidenteTrabalhoRequest.getEntidadeId())) {
			TomadorServico entidade = tomadorServicoRepository.findById(acidenteTrabalhoRequest.getEntidadeId())
					.orElseThrow(() -> new ResourceNotFoundException("TomadorServico", "id",
							acidenteTrabalhoRequest.getEntidadeId()));
			acidenteTrabalho.setEntidade(entidade);
		}

		// set de tomador de serviço
		if (Objects.nonNull(acidenteTrabalhoRequest.getTomadorServicoId())) {
			TomadorServico tomadorServico = tomadorServicoRepository
					.findById(acidenteTrabalhoRequest.getTomadorServicoId())
					.orElseThrow(() -> new ResourceNotFoundException("TomadorServico", "id",
							acidenteTrabalhoRequest.getTomadorServicoId()));
			acidenteTrabalho.setTomadorServico(tomadorServico);
		}

		// set de anexo
		if (Objects.nonNull(acidenteTrabalhoRequest.getAnexoId())) {
			Anexo anexo = anexoRepository.findById(acidenteTrabalhoRequest.getAnexoId()).orElseThrow(
					() -> new ResourceNotFoundException("Anexo", "id", acidenteTrabalhoRequest.getAnexoId()));
			acidenteTrabalho.setAnexo(anexo);
		}

		// set de funcionario
		Funcionario funcionario = funcionarioRepository.findById(acidenteTrabalhoRequest.getFuncionarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id",
						acidenteTrabalhoRequest.getFuncionarioId()));
		acidenteTrabalho.setFuncionario(funcionario);

		return acidenteTrabalho;

	}

}
