package com.rhlinkcon.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Cargo;
import com.rhlinkcon.model.DefinicaoOrganico;
import com.rhlinkcon.model.DefinicaoOrganicoLotacaoCargo;
import com.rhlinkcon.model.DefinicaoOrganicoLotacaoFuncao;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Funcao;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.definicaoOrganico.DefinicaoOrganicoRequest;
import com.rhlinkcon.payload.definicaoOrganico.DefinicaoOrganicoResponse;
import com.rhlinkcon.payload.definicaoOrganicoLotacaoCargo.DefinicaoOrganicoLotacaoCargoRequest;
import com.rhlinkcon.payload.definicaoOrganicoLotacaoCargo.DefinicaoOrganicoLotacaoCargoResponse;
import com.rhlinkcon.payload.definicaoOrganicoLotacaoFuncao.DefinicaoOrganicoLotacaoFuncaoRequest;
import com.rhlinkcon.payload.definicaoOrganicoLotacaoFuncao.DefinicaoOrganicoLotacaoFuncaoResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.lotacao.LotacaoRequest;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.repository.CargoRepository;
import com.rhlinkcon.repository.DefinicaoOrganicoLotacaoCargoRepository;
import com.rhlinkcon.repository.DefinicaoOrganicoLotacaoFuncaoRepository;
import com.rhlinkcon.repository.DefinicaoOrganicoRepository;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.FuncaoRepository;
import com.rhlinkcon.repository.LotacaoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class DefinicaoOrganicoService {

	@Autowired
	private DefinicaoOrganicoRepository definicaoOrganicoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;
	
	@Autowired
	private LotacaoRepository lotacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private FuncaoRepository funcaoRepository;	
	
	@Autowired
	private DefinicaoOrganicoLotacaoCargoRepository definicaoOrganicoLotacaoCargoRepository;
	
	@Autowired
	private DefinicaoOrganicoLotacaoFuncaoRepository definicaoOrganicoLotacaoFuncaoRepository;	
	
	
	public DefinicaoOrganicoResponse selectDefinicaoOrganicoByEmpresaFilialId(Long empresaFilialId) {
		
		DefinicaoOrganico definicaoOrganico = definicaoOrganicoRepository.findByEmpresaFilialId(empresaFilialId);
		
		if(Objects.nonNull(definicaoOrganico)){
			DefinicaoOrganicoResponse definicaoOrganicoResponse = new DefinicaoOrganicoResponse(definicaoOrganico);
			return definicaoOrganicoResponse;
		}

			
		return null;
	}

	public DefinicaoOrganicoResponse getDefinicaoOrganicaByEmpresaId(Long empresaFilialId) {

		DefinicaoOrganico definicaoOrganico = definicaoOrganicoRepository.findByEmpresaFilialId(empresaFilialId);
		
		EmpresaFilial empresaFilial = empresaFilialRepository.findById(empresaFilialId)
				.orElseThrow(() -> new ResourceNotFoundException("Empresa Filial", "id", empresaFilialId));

		Usuario userCreated = usuarioRepository.findById(definicaoOrganico.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", definicaoOrganico.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(definicaoOrganico.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", definicaoOrganico.getUpdatedBy()));

		DefinicaoOrganicoResponse definicaoOrganicoResponse = new DefinicaoOrganicoResponse(definicaoOrganico, definicaoOrganico.getCreatedAt(), userCreated.getNome(),
				definicaoOrganico.getUpdatedAt(), userUpdated.getNome());
		
		definicaoOrganicoResponse.setEmpresaFilial(new EmpresaFilialResponse(empresaFilial));
		
		List<LotacaoResponse> lotacoes = new ArrayList<LotacaoResponse>();
		
		for(Lotacao lotacao : empresaFilial.getLotacoes()){
			LotacaoResponse lotacaoResponse = new LotacaoResponse(lotacao, Projecao.COMPLETA);
			List<DefinicaoOrganicoLotacaoCargo> cargos = definicaoOrganicoLotacaoCargoRepository.findByEmpresaFilialIdAndLotacaoId(empresaFilial.getId(), lotacao.getId());
			if(!cargos.isEmpty()) {
				List<DefinicaoOrganicoLotacaoCargoResponse> listDefinicaoOrganicoLotacaoCargoResponse = new ArrayList<DefinicaoOrganicoLotacaoCargoResponse>();
				lotacaoResponse.setCargos(new ArrayList<DefinicaoOrganicoLotacaoCargoResponse>());
				for(DefinicaoOrganicoLotacaoCargo cargo : cargos) {
					
					DefinicaoOrganicoLotacaoCargoResponse definicaoOrganicoLotacaoCargoResponse = new DefinicaoOrganicoLotacaoCargoResponse(cargo);
					
					Cargo cargoTemp = cargoRepository.findById(cargo.getCargoId())
							.orElseThrow(() -> new ResourceNotFoundException("Cargo", "id", empresaFilialId));
					
					definicaoOrganicoLotacaoCargoResponse.setNome(cargoTemp.getNome());
					lotacaoResponse.getCargos().add(definicaoOrganicoLotacaoCargoResponse);
					
				}
		
			}
			
			List<DefinicaoOrganicoLotacaoFuncao> funcoes = definicaoOrganicoLotacaoFuncaoRepository.findByEmpresaFilialIdAndLotacaoId(empresaFilial.getId(), lotacao.getId());
			if(!funcoes.isEmpty()) {
				List<DefinicaoOrganicoLotacaoFuncaoResponse> listDefinicaoOrganicoLotacaoFuncaoResponse = new ArrayList<DefinicaoOrganicoLotacaoFuncaoResponse>();
				lotacaoResponse.setFuncoes(new ArrayList<DefinicaoOrganicoLotacaoFuncaoResponse>());
				for(DefinicaoOrganicoLotacaoFuncao funcao : funcoes) {
					
					DefinicaoOrganicoLotacaoFuncaoResponse definicaoOrganicoLotacaoFuncaoResponse = new DefinicaoOrganicoLotacaoFuncaoResponse(funcao);
					
					Funcao funcaoTemp = funcaoRepository.findById(funcao.getFuncaoId())
							.orElseThrow(() -> new ResourceNotFoundException("Funcao", "id", funcao.getId()));
					
					definicaoOrganicoLotacaoFuncaoResponse.setNome(funcaoTemp.getNome());
					lotacaoResponse.getFuncoes().add(definicaoOrganicoLotacaoFuncaoResponse);
				}

			}
		
			lotacoes.add(lotacaoResponse);
		}
		definicaoOrganicoResponse.setLotacoes(lotacoes);

		return definicaoOrganicoResponse;
	}



	public void deleteDefinicaoOrganico(Long empresaFilialId) {
		
		DefinicaoOrganico definicaoOrganico = definicaoOrganicoRepository.findByEmpresaFilialId(empresaFilialId);
		
		definicaoOrganicoRepository.delete(definicaoOrganico);
	}

	public void createDefinicaoOrganico(DefinicaoOrganicoRequest definicaoOrganicoRequest) {

		DefinicaoOrganico definicaoOrganico = new DefinicaoOrganico(definicaoOrganicoRequest);
		
		setEntidades(definicaoOrganico, definicaoOrganicoRequest);

		definicaoOrganico.setCreatedAt(Instant.now());

		definicaoOrganicoRepository.save(definicaoOrganico);

	}

	public void updateDefinicaoOrganico(DefinicaoOrganicoRequest definicaoOrganicoRequest) {

		DefinicaoOrganico definicaoOrganico = definicaoOrganicoRepository.findByEmpresaFilialId(definicaoOrganicoRequest.getEmpresaFilialId());
		
		

		setEntidades(definicaoOrganico, definicaoOrganicoRequest); 
		
		definicaoOrganico.setPrimeiroSubstitutoNome(definicaoOrganicoRequest.getPrimeiroSubstitutoNome());
		definicaoOrganico.setPrimeiroSubstitutoEmail(definicaoOrganicoRequest.getPrimeiroSubstitutoEmail());
		definicaoOrganico.setSegundoSubstitutoNome(definicaoOrganicoRequest.getSegundoSubstitutoNome());
		definicaoOrganico.setSegundoSubstitutoEmail(definicaoOrganicoRequest.getSegundoSubstitutoEmail());
		definicaoOrganico.setPrevisaoFuncionarios(definicaoOrganicoRequest.getPrevisaoFuncionarios());
		definicaoOrganico.setFuncionariosAtuais(definicaoOrganicoRequest.getFuncionariosAtuais());
		definicaoOrganico.setTotalFuncionarios(definicaoOrganicoRequest.getTotalFuncionarios());
		definicaoOrganico.setPrevisaoCustos(definicaoOrganicoRequest.getPrevisaoCustos());
		definicaoOrganico.setCustosAtuais(definicaoOrganicoRequest.getCustosAtuais());
		definicaoOrganico.setCustoTotal(definicaoOrganicoRequest.getCustoTotal());
		definicaoOrganico.setConfCriticaAvisar(definicaoOrganicoRequest.getConfCriticaAvisar());
		definicaoOrganico.setConfCriticaCriticar(definicaoOrganicoRequest.getConfCriticaCriticar());	
		definicaoOrganico.setConfCriticaNenhum(definicaoOrganicoRequest.getConfCriticaNenhum());		

		definicaoOrganicoRepository.save(definicaoOrganico);

	}

	private void setEntidades(DefinicaoOrganico definicaoOrganico, DefinicaoOrganicoRequest definicaoOrganicoRequest) {
		
		EmpresaFilial empresaFilial = empresaFilialRepository.findById(definicaoOrganicoRequest.getEmpresaFilialId())
				.orElseThrow(() -> new ResourceNotFoundException("Empresa Filial", "id", definicaoOrganicoRequest.getEmpresaFilialId()));		
		
		if(Objects.nonNull(definicaoOrganico.getEmpresaFilial()))
			definicaoOrganico.setEmpresaFilial(empresaFilial);			
		
		empresaFilial.getLotacoes().clear();
		if (Utils.checkList(definicaoOrganicoRequest.getLotacoes() )) {
			
			for (LotacaoRequest lotacaoRequest : definicaoOrganicoRequest.getLotacoes()) {
				
				Lotacao lotacao = lotacaoRepository.findById(lotacaoRequest.getId())
						.orElseThrow(() -> new ResourceNotFoundException("Lotacao", "id", lotacaoRequest.getId()));
				
				empresaFilial.getLotacoes().add(lotacao);
				
				if (Utils.checkList(lotacaoRequest.getCargos())) {
					for(DefinicaoOrganicoLotacaoCargoRequest definicaoOrganicoLotacaoCargoRequest : lotacaoRequest.getCargos()) {
						definicaoOrganicoLotacaoCargoRepository.deleteByEmpresaFilialIdAndLotacaoIdAndCargoId(empresaFilial.getId(), lotacao.getId(), definicaoOrganicoLotacaoCargoRequest.getCargoId());
						DefinicaoOrganicoLotacaoCargo definicaoOrganicoLotacaoCargo = new DefinicaoOrganicoLotacaoCargo(definicaoOrganicoLotacaoCargoRequest);
						definicaoOrganicoLotacaoCargoRepository.save(definicaoOrganicoLotacaoCargo);
					}
				}
				
				if (Utils.checkList(lotacaoRequest.getFuncoes())) {
					for(DefinicaoOrganicoLotacaoFuncaoRequest definicaoOrganicoLotacaoFuncaoRequest : lotacaoRequest.getFuncoes()) {
						definicaoOrganicoLotacaoFuncaoRepository.deleteByEmpresaFilialIdAndLotacaoIdAndFuncaoId(empresaFilial.getId(), lotacao.getId(), definicaoOrganicoLotacaoFuncaoRequest.getFuncaoId());
						DefinicaoOrganicoLotacaoFuncao definicaoOrganicoLotacaoFuncao = new DefinicaoOrganicoLotacaoFuncao(definicaoOrganicoLotacaoFuncaoRequest);
						definicaoOrganicoLotacaoFuncaoRepository.save(definicaoOrganicoLotacaoFuncao);
					}
				}							
				
			}
			empresaFilialRepository.save(empresaFilial);
		}else {
			
			definicaoOrganicoRepository.deleteEmpresaFilialLotacaoByEmpresaFilialId(empresaFilial.getId());
			
			for (Lotacao lotacao : empresaFilial.getLotacoes()) {
				lotacaoRepository.deleteLotacaoCargoByLotacaoId(lotacao.getId());
				lotacaoRepository.deleteLotacaoFuncaoByLotacaoId(lotacao.getId());
			}
		}		
		
		definicaoOrganico.setEmpresaFilial(empresaFilial);

	}		

}
