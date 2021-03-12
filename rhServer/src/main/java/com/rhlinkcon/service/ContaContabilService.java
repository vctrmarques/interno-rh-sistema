package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.ContaContabil;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.TipoContaLotacaoEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.contaContabil.ContaContabilRequest;
import com.rhlinkcon.payload.contaContabil.ContaContabilResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.repository.CentroCustoRepository;
import com.rhlinkcon.repository.ContaContabilRepository;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.LotacaoRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ContaContabilService {

	@Autowired
	private ContaContabilRepository contaContabilRepository;

	@Autowired
	private LotacaoRepository lotacaoRepository;
	
	@Autowired
	private CentroCustoRepository centroCustoRepository;
	
	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;
	
	@Autowired
	private VerbaRepository verbaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<ContaContabilResponse> getAllContasContabeis() {
		List<ContaContabil> contasContabeis = contaContabilRepository.findAll();

		List<ContaContabilResponse> contasContabeisResponse = new ArrayList<>();
		for (ContaContabil contaContabil : contasContabeis) {
			contasContabeisResponse.add(new ContaContabilResponse(contaContabil));
		}
		return contasContabeisResponse;
	}

	public ContaContabilResponse getContaContabilById(Long contaContabilId) {
		ContaContabil contaContabil = contaContabilRepository.findById(contaContabilId)
				.orElseThrow(() -> new ResourceNotFoundException("ContaContabil", "id", contaContabilId));

		Usuario userCreated = usuarioRepository.findById(contaContabil.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", contaContabil.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(contaContabil.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", contaContabil.getUpdatedBy()));

		ContaContabilResponse contaContabilResponse = new ContaContabilResponse(contaContabil, contaContabil.getCreatedAt(), userCreated.getNome(),
				contaContabil.getUpdatedAt(), userUpdated.getNome());

		List<LotacaoResponse> listLotacaoResponse = new ArrayList<>();
		
		if (Objects.nonNull(contaContabil.getLotacoes()))
			for (Lotacao lotacao: contaContabil.getLotacoes()) {
				listLotacaoResponse.add(new LotacaoResponse(lotacao));
			}
		
		contaContabilResponse.setLotacoes(listLotacaoResponse);

		return contaContabilResponse;
	}

	public PagedResponse<ContaContabilResponse> getAllContasContabeis(int page, int size, String order, String descricao) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Page<ContaContabil> contas = null;
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);
		if(Utils.checkStr(descricao))
			contas = contaContabilRepository.findByCentroCustoDescricaoIgnoreCaseContaining(descricao, pageable);
		else
			contas = contaContabilRepository.findAll(pageable);
		
		if (contas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), contas.getNumber(), contas.getSize(),
					contas.getTotalElements(), contas.getTotalPages(), contas.isLast());
		}

		List<ContaContabilResponse> contasResponse = contas.map(conta -> {
			return new ContaContabilResponse(conta);
		}).getContent();
		return new PagedResponse<>(contasResponse, contas.getNumber(), contas.getSize(),
				contas.getTotalElements(), contas.getTotalPages(), contas.isLast());
		
	}

	public void deleteContaContabil(Long id) {
		ContaContabil contaContabil = contaContabilRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Funcao", "id", id));
		contaContabilRepository.delete(contaContabil);
	}

	public void createContaContabil(ContaContabilRequest contaContabilRequest) {

		ContaContabil contaContabil = new ContaContabil(contaContabilRequest);

		setEntidades(contaContabil, contaContabilRequest);

		if (Utils.checkList(contaContabilRequest.getLotacoesIds())) {
			for (Long lotacaoId : contaContabilRequest.getLotacoesIds()) {
				Lotacao lotacao = lotacaoRepository.findById(lotacaoId)
						.orElseThrow(() -> new ResourceNotFoundException("Lotacao", "id", lotacaoId));
				contaContabil.getLotacoes().add(lotacao);
			}
		}

		contaContabilRepository.save(contaContabil);

	}

	public void updateContaContabil(ContaContabilRequest contaContabilRequest) {

		ContaContabil contaContabil = contaContabilRepository.findById(contaContabilRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ContaContabil", "id", contaContabilRequest.getId()));

		setEntidades(contaContabil, contaContabilRequest);

		contaContabil.setTipoConta(TipoContaLotacaoEnum.valueOf(contaContabilRequest.getTipoConta()));
		contaContabil.setConta(contaContabilRequest.getConta());
		contaContabil.setRateio(contaContabilRequest.getRateio());
		contaContabil.setRateioTotal(contaContabilRequest.getRateioTotal());


		if (!contaContabil.getLotacoes().isEmpty()) {
			contaContabil.getLotacoes().removeAll(contaContabil.getLotacoes());
		}

		if (Utils.checkList(contaContabilRequest.getLotacoesIds())) {
			for (Long lotacaoId : contaContabilRequest.getLotacoesIds()) {
				Lotacao lotacao = lotacaoRepository.findById(lotacaoId)
						.orElseThrow(() -> new ResourceNotFoundException("Lotacao", "id", lotacaoId));
				contaContabil.getLotacoes().add(lotacao);
			}
		}

		contaContabilRepository.save(contaContabil);

	}

	private void setEntidades(ContaContabil contaContabil, ContaContabilRequest contaContabilRequest) {
		
		if(Objects.nonNull(contaContabilRequest.getCentroCustoId())) {
			CentroCusto centroCusto = centroCustoRepository.findById(contaContabilRequest.getCentroCustoId())
					.orElseThrow(() -> new ResourceNotFoundException("ContaContabil", "id", contaContabilRequest.getCentroCustoId()));
			
			contaContabil.setCentroCusto(centroCusto);
		}else
			contaContabil.setCentroCusto(null);
		
		if(Objects.nonNull(contaContabilRequest.getEmpresaId())) {
			EmpresaFilial empresa = empresaFilialRepository.findById(contaContabilRequest.getEmpresaId())
					.orElseThrow(() -> new ResourceNotFoundException("EmpresaFilial", "id", contaContabilRequest.getEmpresaId()));
			
			contaContabil.setEmpresa(empresa);
		}else
			contaContabil.setEmpresa(null);
		
		if(Objects.nonNull(contaContabilRequest.getFilialId())) {
			EmpresaFilial filial = empresaFilialRepository.findById(contaContabilRequest.getFilialId())
					.orElseThrow(() -> new ResourceNotFoundException("EmpresaFilial", "id", contaContabilRequest.getFilialId()));
			
			contaContabil.setFilial(filial);
		}else
			contaContabil.setFilial(null);
		
		if(Objects.nonNull(contaContabilRequest.getVerbaId())) {
			Verba verba = verbaRepository.findById(contaContabilRequest.getVerbaId())
					.orElseThrow(() -> new ResourceNotFoundException("Verba", "id", contaContabilRequest.getVerbaId()));
			
			contaContabil.setVerba(verba);
		}else
			contaContabil.setVerba(null);

		
	}

	public List<ContaContabilResponse> getAllContasContabeisPorTipo(Integer valor, String tipo) {
		List<ContaContabil> lista = contaContabilRepository.findAllByTipoContaAndContaContaining(TipoContaLotacaoEnum.getEnumByString(tipo), valor.toString());
		List<ContaContabilResponse> response = lista.stream().map(conta -> new ContaContabilResponse(conta)).collect(Collectors.toList());
		return response;
	}

}
