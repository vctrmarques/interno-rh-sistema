package com.rhlinkcon.service;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.HistoricoSituacaoFuncional;
import com.rhlinkcon.model.SituacaoFuncional;
import com.rhlinkcon.model.TipoAposentadoria;
import com.rhlinkcon.model.TipoHistoricoSituacaoFuncionalEnum;
import com.rhlinkcon.model.TipoProporcaoEnum;
import com.rhlinkcon.model.TransferenciaFuncionario;
import com.rhlinkcon.model.TransferenciaPrevidenciaFuncionario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.historicoSituacaoFuncional.HistoricoSituacaoFuncionalRequest;
import com.rhlinkcon.payload.tranferenciaFuncionario.TransferenciaFuncionarioRequest;
import com.rhlinkcon.payload.transferenciaPrevidenciaFuncionario.TransferenciaPrevidenciaFuncionarioRequest;
import com.rhlinkcon.payload.transferenciaPrevidenciaFuncionario.TransferenciaPrevidenciaFuncionarioResponse;
import com.rhlinkcon.repository.SituacaoFuncionalRepository;
import com.rhlinkcon.repository.TipoAposentadoriaRepository;
import com.rhlinkcon.repository.TransferenciaPrevidenciaFuncionarioRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class TransferenciaPrevidenciaFuncionarioService {
	
	@Autowired
	private TransferenciaPrevidenciaFuncionarioRepository transferenciaPrevidenciaFuncionarioRepository;
	
	@Autowired
	private TransferenciaFuncionarioService transferenciaFuncionarioService;
	
	@Autowired
	private TipoAposentadoriaRepository tipoAposentadoriaRepository;
	
	@Autowired
	private HistoricoSituacaoFuncionalService historicoSituacaoFuncionalService;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private SituacaoFuncionalRepository situacaoFuncionalRepository;

	public PagedResponse<TransferenciaPrevidenciaFuncionarioResponse> getAll(int page, int size, String descricao,
			String order) {

		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<TransferenciaPrevidenciaFuncionario> transferencias = transferenciaPrevidenciaFuncionarioRepository.findByFuncionarioNomeIgnoreCaseContaining(descricao, pageable);

		if (transferencias.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), transferencias.getNumber(), transferencias.getSize(),
					transferencias.getTotalElements(), transferencias.getTotalPages(), transferencias.isLast());
		}

		List<TransferenciaPrevidenciaFuncionarioResponse> transferenciasResponse = transferencias.map(item -> {
			return new TransferenciaPrevidenciaFuncionarioResponse(item);
		}).getContent();
		return new PagedResponse<>(transferenciasResponse, transferencias.getNumber(), transferencias.getSize(),
				transferencias.getTotalElements(), transferencias.getTotalPages(), transferencias.isLast());
	}
	@Transactional
	public void create(@Valid TransferenciaPrevidenciaFuncionarioRequest request) {
		
		try {
			
			Funcionario funcionario = funcionarioRepository.findById(request.getFuncionarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", request.getFuncionarioId()));
			
			SituacaoFuncional situacaoFuncional = situacaoFuncionalRepository.findById(request.getSituacaoFuncionalId())
					.orElseThrow(() -> new ResourceNotFoundException("SituacaoFuncional", "id", request.getSituacaoFuncionalId()));
			
//			EmpresaFilial filialDestino = empresaFilialRepository.findById(request.getFilialDestinoId())
//					.orElseThrow(() -> new ResourceNotFoundException("EmpresaFilial", "id", request.getFilialDestinoId()));
//			
//			Lotacao lotacaoDestino = lotacaoRepository.findById(request.getFilialDestinoId())
//					.orElseThrow(() -> new ResourceNotFoundException("Lotacao", "id", request.getLotacaoDestinoId()));
			
			TipoAposentadoria tipoAposentadoria = tipoAposentadoriaRepository.findById(request.getTipoAposentadoriaId())
					.orElseThrow(() -> new ResourceNotFoundException("TipoAposentadoria", "id", request.getTipoAposentadoriaId()));
			
			// Transferencia funcionario
			TransferenciaFuncionarioRequest tf = new TransferenciaFuncionarioRequest();
			tf.setFuncionarioId(request.getFuncionarioId());
//			TransferenciaFuncionario tf = new TransferenciaFuncionario();
//			tf.setFuncionario(funcionario);
			// todo refatorar tabela para adicao de campo destino da transferencia.
			tf.setEmpresaAnteriorId(funcionario.getEmpresa().getId());
			tf.setLotacaoAnteriorId(funcionario.getLotacao().getId());
			tf.setEmpresaId(request.getFilialDestinoId());
			tf.setLotacaoId(request.getLotacaoDestinoId());
//			tf.setEmpresa(filialDestino);
//			tf.setLotacao(lotacaoDestino);
			
			TransferenciaFuncionario transferencia =  transferenciaFuncionarioService.createTransferenciaFuncionario(tf);;
			
			// Historico Situacao funcional
			HistoricoSituacaoFuncionalRequest hsf = new HistoricoSituacaoFuncionalRequest();
			hsf.setFuncionarioId(funcionario.getId());
			hsf.setSituacaoFuncionalId(situacaoFuncional.getId());
			hsf.setSituacaoFuncionalAnteriorId(funcionario.getTipoSituacaoFuncional().getId());;
			hsf.setDataAto(request.getDataSolicitacao().atStartOfDay(ZoneId.systemDefault()).toInstant());
			hsf.setDataDiarioOficial(request.getDataAposentadoria().atStartOfDay(ZoneId.systemDefault()).toInstant());
			hsf.setDataInicio(request.getDataAposentadoria().atStartOfDay(ZoneId.systemDefault()).toInstant());
			hsf.setTipoSituacaoFuncional(TipoHistoricoSituacaoFuncionalEnum.SITUACAO_AFASTAMENTO.toString());
			
			HistoricoSituacaoFuncional hsfUpdate = historicoSituacaoFuncionalService.createHistoricoSituacaoFuncional(hsf);
			
			// Transferencia pRevidencia
			TransferenciaPrevidenciaFuncionario tpf = new TransferenciaPrevidenciaFuncionario();
			tpf.setFuncionario(funcionario);
			tpf.setDataAposentadoria(request.getDataAposentadoria().atStartOfDay().toInstant(ZoneOffset.UTC));
			tpf.setDataSolicitacao(request.getDataSolicitacao().atStartOfDay().toInstant(ZoneOffset.UTC));
			tpf.setTipoAposentadoria(tipoAposentadoria);
			tpf.setTipoProporcao(TipoProporcaoEnum.getEnumByString(request.getTipoProporcao()));
			tpf.setProporcao(request.getProporcao());
			tpf.setProcesso(request.getProcesso());
			tpf.setObservacao(request.getObservacao());
			tpf.setHistoricoSituacaoFuncional(hsfUpdate);
			tpf.setTransferenciaFuncionario(transferencia);
			
			transferenciaPrevidenciaFuncionarioRepository.save(tpf);
			
		} catch (Exception e) {
			throw new ServiceException("Ocorreu um erro, procure o administrador do sistema");
		}
		
		
	}

	public TransferenciaPrevidenciaFuncionarioResponse getById(Long id) {
		TransferenciaPrevidenciaFuncionario tpf = transferenciaPrevidenciaFuncionarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TransferenciaPrevidenciaFuncionario", "id", id)); 
		TransferenciaPrevidenciaFuncionarioResponse tpfr = new TransferenciaPrevidenciaFuncionarioResponse(tpf);
		return  tpfr;
	}

	public List<TransferenciaPrevidenciaFuncionarioResponse> findExSeguradoByNome(String search) {
		List<String> values = new ArrayList<>();
		values.add("FALECIMENTO");
		values.add("FALECIDO");
		values.add("FALECIDA");
		List<TransferenciaPrevidenciaFuncionario> tpf = transferenciaPrevidenciaFuncionarioRepository.findByFuncionarioNomeIgnoreCaseContainingAndHistoricoSituacaoFuncionalSituacaoFuncionalDescricaoIn(search, values);
		List<TransferenciaPrevidenciaFuncionarioResponse> responseList = new ArrayList<>();
		
		if(!tpf.isEmpty()) {
			for(TransferenciaPrevidenciaFuncionario t : tpf) {
				TransferenciaPrevidenciaFuncionarioResponse response = new TransferenciaPrevidenciaFuncionarioResponse(t);
				responseList.add(response);
			}
		}
		
		return responseList;
	}

	public List<TransferenciaPrevidenciaFuncionarioResponse> findExSeguradoByFiltro(String searchFuncionario, Integer searchMatricula, String searchCPF, String searchPis) {
		List<String> values = new ArrayList<>();
		values.add("APOSENTADO");
		values.add("APOSENTADA");

		List<TransferenciaPrevidenciaFuncionario> tpf = transferenciaPrevidenciaFuncionarioRepository.findAposentados(searchFuncionario, searchMatricula, searchCPF, searchPis, values);
		List<TransferenciaPrevidenciaFuncionarioResponse> responseList = new ArrayList<>();
		
		if(!tpf.isEmpty()) {
			for(TransferenciaPrevidenciaFuncionario t : tpf) {
				TransferenciaPrevidenciaFuncionarioResponse response = new TransferenciaPrevidenciaFuncionarioResponse(t);
				responseList.add(response);
			}
		}
		
		return responseList;
	}
}
