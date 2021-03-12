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
import com.rhlinkcon.filtro.RecisaoContratoFiltro;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.MotivoDesligamento;
import com.rhlinkcon.model.RecisaoContrato;
import com.rhlinkcon.model.RecisaoContratoEnum;
import com.rhlinkcon.payload.cnae.CnaeResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.payload.recisaoContrato.RecisaoContratoRequest;
import com.rhlinkcon.payload.recisaoContrato.RecisaoContratoResponse;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaResponse;
import com.rhlinkcon.repository.CnaeRepository;
import com.rhlinkcon.repository.MunicipioRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.recisaoContrato.RecisaoContratoRepository;
import com.rhlinkcon.util.UfCTPSNull;
import com.rhlinkcon.util.Utils;

@Service
public class RecisaoContratoService {
	@Autowired
	private CnaeRepository cnaeRepository;
	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private RecisaoContratoRepository recisaoContratoRepository;
	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;

	public List<RecisaoContratoResponse> getAll() {
		List<RecisaoContratoResponse> list = new ArrayList<RecisaoContratoResponse>();

		for (RecisaoContrato recisaoContrato : recisaoContratoRepository.findAll()) {
			list.add(new RecisaoContratoResponse(recisaoContrato));
		}

		return list;
	}

	public RecisaoContratoResponse getRecisaoContratoById(Long recisaoContratoId) {
		RecisaoContrato recisaoContrato = recisaoContratoRepository.findById(recisaoContratoId)
				.orElseThrow(() -> new ResourceNotFoundException("RecisaoContrato", "id", recisaoContratoId));
		RecisaoContratoResponse recisaoContratoResponse = new RecisaoContratoResponse(recisaoContrato);
		recisaoContratoResponse.setFuncionarioId(recisaoContrato.getFuncionario().getId());

		// new FuncionarioResponse
		FuncionarioResponse funcionarioResponse = new FuncionarioResponse();
		funcionarioResponse.setId(recisaoContrato.getFuncionario().getId());
		funcionarioResponse.setMatricula(recisaoContrato.getFuncionario().getMatricula());
		funcionarioResponse.setNome(recisaoContrato.getFuncionario().getNome());

		// funcionarioResponse.setFilial
		EmpresaFilialResponse empresaFilialResponse = new EmpresaFilialResponse();
		empresaFilialResponse.setNomeFilial(recisaoContrato.getFuncionario().getFilial().getNomeFilial());
		funcionarioResponse.setFilial(empresaFilialResponse);

		// funcionarioResponse.setLotacao
		LotacaoResponse lotacaoResponse = new LotacaoResponse();
		lotacaoResponse.setDescricaoCompleta(recisaoContrato.getFuncionario().getLotacao().getDescricaoCompleta());
		funcionarioResponse.setLotacao(lotacaoResponse);

		recisaoContratoResponse.setFuncionarioResponse(funcionarioResponse);
		return recisaoContratoResponse;
	}

	public PagedResponse<RecisaoContratoResponse> getAllRecisaoContrato(int page, int size, String order,
			String matricula, String nome, String status) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<RecisaoContrato> recisoesContratos = null;
		RecisaoContratoFiltro filtro = new RecisaoContratoFiltro();

		if (!matricula.isEmpty()) {
			filtro.setMatricula(matricula);
		}

		if (!nome.isEmpty()) {
			filtro.setNome(nome);
		}

		if (!status.isEmpty()) {
			filtro.setRecisaoContratoEnum(RecisaoContratoEnum.getEnumByString(status));
		}

		recisoesContratos = recisaoContratoRepository.filtro(filtro, pageable);

		if (recisoesContratos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), recisoesContratos.getNumber(),
					recisoesContratos.getSize(), recisoesContratos.getTotalElements(),
					recisoesContratos.getTotalPages(), recisoesContratos.isLast());
		}

		List<RecisaoContratoResponse> recisaoContratoResponses = new ArrayList<>();

		for (RecisaoContrato recisaoContrato : recisoesContratos) {
			RecisaoContratoResponse recisaoContratoResponse = new RecisaoContratoResponse(recisaoContrato);
			recisaoContratoResponse.setFuncionarioId(recisaoContrato.getFuncionario().getId());
			recisaoContratoResponse.setMotivoNome(recisaoContrato.getMotivo().getDescricao());
			recisaoContratoResponse.setCodMotivo(recisaoContrato.getMotivo().getCodigo());
			recisaoContratoResponse.setDataAviso(recisaoContrato.getDataAviso());
			recisaoContratoResponse.setDataDesligamento(recisaoContrato.getDataDesligamento());

			// new FuncionarioResponse
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse();
			funcionarioResponse.setId(recisaoContrato.getFuncionario().getId());
			funcionarioResponse.setMatricula(recisaoContrato.getFuncionario().getMatricula());
			funcionarioResponse.setNome(recisaoContrato.getFuncionario().getNome());
			funcionarioResponse.setCpf(recisaoContrato.getFuncionario().getCpf());
			funcionarioResponse.setPisPasep(recisaoContrato.getFuncionario().getPisPasep());
			funcionarioResponse.setSerieCtps(recisaoContrato.getFuncionario().getSerieCtps());
			funcionarioResponse.setUfCtpsNome(retornarSiglaCTPS(recisaoContrato));
			funcionarioResponse.setDataNascimento(recisaoContrato.getFuncionario().getDataNascimento());
			funcionarioResponse.setNomeMae(recisaoContrato.getFuncionario().getNomeMae());
			funcionarioResponse.setDataAdmissao(recisaoContrato.getFuncionario().getDataAdmissao()); 
			
			if(Objects.nonNull(recisaoContrato.getFuncionario().getVinculo())) {
				if(Objects.nonNull(recisaoContrato.getFuncionario().getVinculo().getTipoContrato())) {
					funcionarioResponse.setTipoContratoNome(recisaoContrato.getFuncionario().getVinculo().getDescricao());
				}
			}

			/** Endereco do funcionario **/
			funcionarioResponse.setLogradouro(recisaoContrato.getFuncionario().getLogradouro());
			funcionarioResponse.setNumero(recisaoContrato.getFuncionario().getNumero());
			funcionarioResponse.setComplemento(recisaoContrato.getFuncionario().getComplemento());
			funcionarioResponse.setBairro(recisaoContrato.getFuncionario().getBairro());
			funcionarioResponse.setMunicipioNome(recisaoContrato.getFuncionario().getMunicipio().getDescricao());
			funcionarioResponse.setUfNome(recisaoContrato.getFuncionario().getUf().getSigla());
			funcionarioResponse.setCep(recisaoContrato.getFuncionario().getCep());

			// funcionarioResponse.setFilial
			EmpresaFilialResponse empresaFilialResponse = new EmpresaFilialResponse();
			empresaFilialResponse.setNomeFilial(recisaoContrato.getFuncionario().getFilial().getNomeFilial());
			empresaFilialResponse.setCnpj(recisaoContrato.getFuncionario().getFilial().getCnpj());

			/** Endereco da empresa **/
			empresaFilialResponse.setLogradouro(recisaoContrato.getFuncionario().getFilial().getLogradouro());
			empresaFilialResponse.setNumero(recisaoContrato.getFuncionario().getFilial().getNumero());
			empresaFilialResponse.setComplemento(recisaoContrato.getFuncionario().getFilial().getComplemento());
			empresaFilialResponse.setBairro(recisaoContrato.getFuncionario().getFilial().getBairro());

			if (Objects.nonNull(recisaoContrato.getFuncionario().getFilial().getMunicipio())) {
				empresaFilialResponse.setMunicipio(new MunicipioResponse(municipioRepository
						.findById(recisaoContrato.getFuncionario().getFilial().getMunicipio().getId()).get()));
			}

			if (Objects.nonNull(recisaoContrato.getFuncionario().getFilial().getUf())) {
				empresaFilialResponse.setUf(new UnidadeFederativaResponse(unidadeFederativaRepository
						.findById(recisaoContrato.getFuncionario().getFilial().getUf().getId()).get()));
			}

			empresaFilialResponse.setCep(recisaoContrato.getFuncionario().getFilial().getCep());

			if (Objects.nonNull(recisaoContrato.getFuncionario().getFilial().getCnae())) {
				empresaFilialResponse.setCnae(new CnaeResponse(
						cnaeRepository.findById(recisaoContrato.getFuncionario().getFilial().getCnae().getId()).get()));
			}

			funcionarioResponse.setFilial(empresaFilialResponse);

			// funcionarioResponse.setLotacao
			LotacaoResponse lotacaoResponse = new LotacaoResponse();
			lotacaoResponse.setDescricao(recisaoContrato.getFuncionario().getLotacao().getDescricao());
			funcionarioResponse.setLotacao(lotacaoResponse);

			recisaoContratoResponse.setFuncionarioResponse(funcionarioResponse);
			recisaoContratoResponses.add(recisaoContratoResponse);
		}

		return new PagedResponse<>(recisaoContratoResponses, recisoesContratos.getNumber(), recisoesContratos.getSize(),
				recisoesContratos.getTotalElements(), recisoesContratos.getTotalPages(), recisoesContratos.isLast());
	}

	public void createRecisaoContrato(RecisaoContratoRequest recisaoContratoRequest) {
		RecisaoContrato recisaoContrato = new RecisaoContrato(recisaoContratoRequest);
		recisaoContrato.setStatus(RecisaoContratoEnum.SIMULADA);
		setEntidades(recisaoContrato, recisaoContratoRequest);
		recisaoContratoRepository.save(recisaoContrato);
	}

	public void updateRecisaoContrato(RecisaoContratoRequest recisaoContratoRequest) {
		RecisaoContrato recisaoContrato = recisaoContratoRepository.findById(recisaoContratoRequest.getId())
				.orElseThrow(
						() -> new ResourceNotFoundException("RecisaoContrato", "id", recisaoContratoRequest.getId()));
		setEntidades(recisaoContrato, recisaoContratoRequest);

		recisaoContrato.setAvisoPrevio(recisaoContratoRequest.getAvisoPrevio());
		recisaoContrato.setDataAviso(recisaoContratoRequest.getDataAviso());
		recisaoContrato.setDiasAviso(recisaoContratoRequest.getDiasAviso());
		recisaoContrato.setDataDesligamento(recisaoContratoRequest.getDataDesligamento());
		recisaoContrato.setDataHomologacao(recisaoContratoRequest.getDataDesligamento());
		recisaoContrato.setDataPagamento(recisaoContratoRequest.getDataPagamento());

		if (Objects.nonNull(recisaoContrato.getDataPagamento())) {
			recisaoContrato.setStatus(RecisaoContratoEnum.PAGA);
		} else {
			recisaoContrato.setStatus(RecisaoContratoEnum.getEnumByString(recisaoContratoRequest.getStatus()));
		}

		recisaoContratoRepository.save(recisaoContrato);
	}

	public void cancelarRecisaoContrato(RecisaoContratoRequest recisaoContratoRequest) {
		RecisaoContrato recisaoContrato = recisaoContratoRepository.findById(recisaoContratoRequest.getId())
				.orElseThrow(
						() -> new ResourceNotFoundException("RecisaoContrato", "id", recisaoContratoRequest.getId()));
		recisaoContrato.setStatus(RecisaoContratoEnum.CANCELADA);
		recisaoContratoRepository.save(recisaoContrato);
	}

	public void efetivarRecisaoContrato(RecisaoContratoRequest recisaoContratoRequest) {
		RecisaoContrato recisaoContrato = recisaoContratoRepository.findById(recisaoContratoRequest.getId())
				.orElseThrow(
						() -> new ResourceNotFoundException("RecisaoContrato", "id", recisaoContratoRequest.getId()));
		recisaoContrato.setStatus(RecisaoContratoEnum.EFETIVADA);

		for (RecisaoContrato contrato : recisaoContratoRepository.findByFuncionario(recisaoContrato.getFuncionario())) {
			contrato.setStatus(RecisaoContratoEnum.CANCELADA);
			recisaoContratoRepository.save(contrato);
		}

		recisaoContratoRepository.save(recisaoContrato);
	}

	public void deleteRecisaoContrato(Long id) {
		RecisaoContrato recisaoContrato = recisaoContratoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("RecisaoContrato", "id", id));
		recisaoContratoRepository.delete(recisaoContrato);
	}

	private void setEntidades(RecisaoContrato recisaoContrato, RecisaoContratoRequest recisaoContratoRequest) {
		recisaoContrato.setFuncionario(new Funcionario(recisaoContratoRequest.getFuncionarioId()));
		recisaoContrato.setMotivo(new MotivoDesligamento(recisaoContratoRequest.getMotivoId()));
	}
	
	private String retornarSiglaCTPS(RecisaoContrato recisaoContrato) {
		if(recisaoContrato.getFuncionario().getUfCtps() != null) {
			return recisaoContrato.getFuncionario().getUfCtps().getSigla();
		} else {
			return UfCTPSNull.retornarUnidadeFederativaNula().getSigla();
		}
	}
}
