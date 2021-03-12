package com.rhlinkcon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.TreinamentoSugerido;
import com.rhlinkcon.model.TreinamentoSugeridoComplemento;
import com.rhlinkcon.model.TreinamentoSugeridoValores;
import com.rhlinkcon.payload.treinamentoSugerido.TreinamentoSugeridoRequest;
import com.rhlinkcon.payload.treinamentoSugerido.TreinamentoSugeridoResponse;
import com.rhlinkcon.payload.treinamentoSugeridoComplemento.TreinamentoSugeridoComplementoResponse;
import com.rhlinkcon.payload.treinamentoSugeridoValores.TreinamentoSugeridoValoresResponse;
import com.rhlinkcon.repository.TreinamentoSugeridoComplementoRepository;
import com.rhlinkcon.repository.TreinamentoSugeridoRespoitory;
import com.rhlinkcon.repository.TreinamentoSugeridoValoresRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class TreinamentoSugeridoService {

	@Autowired
	private TreinamentoSugeridoRespoitory tSugeridoRespoitory;
	
	@Autowired
	private TreinamentoSugeridoComplementoRepository complementoRepository;
	
	@Autowired
	private TreinamentoSugeridoValoresRepository valoresRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public Page<TreinamentoSugeridoResponse> getAll(int page, int size, String orderBy, String nomeFuncionario) {
		Utils.validatePageNumberAndSize(page, size);
		Pageable pageable = Utils.getPageRequest(page, size, orderBy);	
		Page<TreinamentoSugerido> treinamentos = null;
		List<Funcionario> listaFuncionarios = null;
		if (Utils.checkStr(nomeFuncionario)) {
			listaFuncionarios = funcionarioRepository.findByNomeIgnoreCaseContaining(nomeFuncionario);
			treinamentos = tSugeridoRespoitory.findAllByFuncionariosIn(listaFuncionarios, pageable);
		} else
			treinamentos = tSugeridoRespoitory.findAll(pageable);		
		return treinamentos.map(e -> new TreinamentoSugeridoResponse(e));
	}

	@Transactional
	public void create(TreinamentoSugeridoRequest request) {
		TreinamentoSugerido ts = new TreinamentoSugerido(request);
		tSugeridoRespoitory.save(ts);
	}

	public void delete(Long treinamentoSurgeridoId) {
		TreinamentoSugerido treinamento = tSugeridoRespoitory.findById(treinamentoSurgeridoId)
				.orElseThrow(() -> new ResourceNotFoundException("Treinamento Sugerido", "id", treinamentoSurgeridoId));	
		tSugeridoRespoitory.delete(treinamento);
	}

	public TreinamentoSugeridoResponse getTreinamento(Long id) {
		TreinamentoSugerido ts = tSugeridoRespoitory.findById(id).orElseThrow(() -> new ResourceNotFoundException("Treinamento", "id", id)); 
		TreinamentoSugeridoResponse tsr = new TreinamentoSugeridoResponse(ts);
		tsr.setAlteradoPor(usuarioService.alteradoPor(ts));
		tsr.setCriadoPor(usuarioService.criadoPor(ts));
		return  tsr;
	}

	public TreinamentoSugeridoComplementoResponse getComplemento(Long id) {
		TreinamentoSugerido ts = tSugeridoRespoitory.findById(id).orElseThrow(() -> new ResourceNotFoundException("Treinamento", "id", id));
		TreinamentoSugeridoComplemento tsc = complementoRepository.findFirstByTreinamentoSugerido(ts).orElseThrow(() -> new ResourceNotFoundException("Complemento", "id", id)); 
		
		TreinamentoSugeridoComplementoResponse response = new TreinamentoSugeridoComplementoResponse(tsc);
		response.setAlteradoPor(usuarioService.alteradoPor(tsc));
		response.setCriadoPor(usuarioService.criadoPor(tsc));
		return  response;
	}

	public TreinamentoSugeridoValoresResponse getValores(Long id) {
		TreinamentoSugerido ts = tSugeridoRespoitory.findById(id).orElseThrow(() -> new ResourceNotFoundException("Treinamento", "id", id));
		TreinamentoSugeridoValores tsv = valoresRepository.findFirstByTreinamentoSugerido(ts).orElseThrow(() -> new ResourceNotFoundException("Valores", "id", id)); 
		
		TreinamentoSugeridoValoresResponse response = new TreinamentoSugeridoValoresResponse(tsv);
		response.setAlteradoPor(usuarioService.alteradoPor(tsv));
		response.setCriadoPor(usuarioService.criadoPor(tsv));
		return  response;
	}

}
