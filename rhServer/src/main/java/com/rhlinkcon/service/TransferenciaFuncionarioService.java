package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.TransferenciaFuncionario;
import com.rhlinkcon.payload.tranferenciaFuncionario.TransferenciaFuncionarioRequest;
import com.rhlinkcon.payload.tranferenciaFuncionario.TransferenciaFuncionarioResponse;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.LotacaoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.tranferenciaFuncionario.TransferenciaFuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;

@Service
public class TransferenciaFuncionarioService {

	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	EmpresaFilialRepository empresaRepository;
	
	@Autowired
	LotacaoRepository lotacaoRepository;
	
	@Autowired
	TransferenciaFuncionarioRepository transferenciaFuncionarioRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	public TransferenciaFuncionario createTransferenciaFuncionario(TransferenciaFuncionarioRequest tf) {
		Funcionario funcionario = funcionarioRepository.findById(tf.getFuncionarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", tf.getFuncionarioId()));

	
		
		TransferenciaFuncionario transferencia = new TransferenciaFuncionario();
		transferencia.setFuncionario(funcionario);
		
		if(!Objects.isNull(tf.getEmpresaId())) {
			transferencia.setEmpresa(new EmpresaFilial(tf.getEmpresaId()));
			funcionario.setFilial(new EmpresaFilial(tf.getEmpresaId()));
		}
		if(!Objects.isNull(tf.getLotacaoId())) {
			transferencia.setLotacao(new Lotacao(tf.getLotacaoId()));
			funcionario.setLotacao(new Lotacao(tf.getLotacaoId()));
		}
		if(!Objects.isNull(funcionario.getEmpresa())) {
			transferencia.setEmpresaAnterior(new EmpresaFilial(funcionario.getEmpresa().getId()));
		}
		if(!Objects.isNull(funcionario.getLotacao())) {
			transferencia.setLotacaoAnterior(new Lotacao(funcionario.getLotacao().getId()));
		}
		
		
		funcionarioRepository.save(funcionario);
		transferenciaFuncionarioRepository.save(transferencia);
		
		return transferencia;
	}
	
	@SuppressWarnings("null")
	public List<TransferenciaFuncionarioResponse> getAllTransferenciaByFuncionario(Long funcionarioId){
		List<TransferenciaFuncionario> transferencias = transferenciaFuncionarioRepository.getAllTransferenciaByFuncionario(funcionarioId);
		List<TransferenciaFuncionarioResponse> transferenciasResponse = new ArrayList<TransferenciaFuncionarioResponse>();
		for(TransferenciaFuncionario t : transferencias) {
			TransferenciaFuncionarioResponse tResponse = new TransferenciaFuncionarioResponse(t);
			tResponse.setCriadoPor(usuarioService.criadoPor(t));
			tResponse.setAlteradoPor(usuarioService.alteradoPor(t));
			transferenciasResponse.add(tResponse);
		}		
		return transferenciasResponse;
	}
}
