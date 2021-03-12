package com.rhlinkcon.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.CondicaoRetornoTrabalhoEnum;
import com.rhlinkcon.model.DadoCadastralComplementar;
import com.rhlinkcon.model.FardamentoEnum;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.TipoAposentadoriaEnum;
import com.rhlinkcon.model.TipoProporcaoEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.dadoCadastralComplementar.DadoCadastralComplementarRequest;
import com.rhlinkcon.payload.dadoCadastralComplementar.DadoCadastralComplementarResponse;
import com.rhlinkcon.repository.DadoCadastralComplementarRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;

@Service
public class DadoCadastralComplementarService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;


	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private DadoCadastralComplementarRepository dadoCadastralComplementarRepository;
	

	public void createDadoCadastralComplementar(DadoCadastralComplementarRequest dadoCadastralComplementarRequest) {

		DadoCadastralComplementar dadoCadastral = new DadoCadastralComplementar(dadoCadastralComplementarRequest);
		
		setEntidades(dadoCadastralComplementarRequest, dadoCadastral);

		dadoCadastralComplementarRepository.save(dadoCadastral);
		
		Funcionario funcionario = funcionarioRepository.findById(dadoCadastralComplementarRequest.getFuncionarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", dadoCadastralComplementarRequest.getFuncionarioId()));
		
		funcionario.setDadoCadastral(dadoCadastral);
		
		funcionarioRepository.save(funcionario);
	}
	
	public void deleteDadoCadastralComplementar(Long funcionarioId) {

		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));
		
		funcionario.setDadoCadastral(null);
		funcionarioRepository.save(funcionario);
		
		DadoCadastralComplementar dadoCadastral = dadoCadastralComplementarRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("DadoCadastralComplementar", "id", funcionarioId));
		
		dadoCadastralComplementarRepository.delete(dadoCadastral);
		
	}
	
	public void updateDadoCadastralComplementar(DadoCadastralComplementarRequest dadoCadastralComplementarRequest) {
		DadoCadastralComplementar dadoCadastral = dadoCadastralComplementarRepository.findById(dadoCadastralComplementarRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("DadoCadastralComplementar", "id", dadoCadastralComplementarRequest.getId()));

		setEntidades(dadoCadastralComplementarRequest, dadoCadastral);

		if (Objects.nonNull(dadoCadastralComplementarRequest.getFardamento()))
			dadoCadastral.setFardamento(FardamentoEnum.valueOf(dadoCadastralComplementarRequest.getFardamento()));
		else
			dadoCadastral.setFardamento(null);
		
		dadoCadastral.setAltura(dadoCadastralComplementarRequest.getAltura());
		dadoCadastral.setPeso(dadoCadastralComplementarRequest.getPeso());
		dadoCadastral.setCalcado(dadoCadastralComplementarRequest.getCalcado());

		if (Objects.nonNull(dadoCadastralComplementarRequest.getCondicaoRetornoTrabalho()))
			dadoCadastral.setCondicaoRetornoTrabalho(CondicaoRetornoTrabalhoEnum.valueOf(
					dadoCadastralComplementarRequest.getCondicaoRetornoTrabalho()));
		
		dadoCadastral.setDataInicioDeficiencia(dadoCadastralComplementarRequest.getDataInicioDeficiencia());;
		dadoCadastral.setDataFimDeficiencia(dadoCadastralComplementarRequest.getDataFimDeficiencia());
		dadoCadastral.setReabilitado(dadoCadastralComplementarRequest.getReabilitado());;
		dadoCadastral.setCotista(dadoCadastralComplementarRequest.getCotista());
		dadoCadastral.setDescricaoDeficiencia(dadoCadastralComplementarRequest.getDescricaoDeficiencia());
		dadoCadastral.setDataAposentadoria(dadoCadastralComplementarRequest.getDataAposentadoria());
		dadoCadastral.setTipoAposentadoria(TipoAposentadoriaEnum.valueOf(dadoCadastralComplementarRequest.getTipoAposentadoria()));
		dadoCadastral.setProporcionalidade(dadoCadastralComplementarRequest.getProporcionalidade());;
		dadoCadastral.setTipoProporcao(TipoProporcaoEnum.valueOf(dadoCadastralComplementarRequest.getTipoProporcao()));
		
//		dadoCadastral.setContribuicaoInss(dadoCadastralComplementarRequest.getContribuicaoInss());
//		dadoCadastral.setConsignadoBloqueado(dadoCadastralComplementarRequest.getConsignadoBloqueado());
//		dadoCadastral.setContribuicaoIr(dadoCadastralComplementarRequest.getContribuicaoIr());
		
		dadoCadastral.setNumeroProcessoAposentadoria(dadoCadastralComplementarRequest.getNumeroProcessoAposentadoria());
		
		dadoCadastral.setDataInicialIsencaoIr(dadoCadastralComplementarRequest.getDataInicialIsencaoIr());
		dadoCadastral.setDataFinalIsencaoIr(dadoCadastralComplementarRequest.getDataFinalIsencaoIr());
		dadoCadastral.setDataInicialIsencaoPrevidencia(dadoCadastralComplementarRequest.getDataInicialIsencaoPrevidencia());
		dadoCadastral.setDataFinalIsencaoPrevidencia(dadoCadastralComplementarRequest.getDataFinalIsencaoPrevidencia());
		dadoCadastral.setPrevidenciaEspecial(dadoCadastralComplementarRequest.getPrevidenciaEspecial());
		
		dadoCadastral.setLocalRedistribuicao(dadoCadastralComplementarRequest.getLocalRedistribuicao());
		dadoCadastral.setDataDistribuicaoCedencia(dadoCadastralComplementarRequest.getDataDistribuicaoCedencia());
		
//		dadoCadastral.setDataInicialIsencaoIrPrevidencia(dadoCadastralComplementarRequest.getDataInicialIsencaoIrPrevidencia());
//		dadoCadastral.setDataFinalIsencaoIrPrevidencia(dadoCadastralComplementarRequest.getDataFinalIsencaoIrPrevidencia());
		
		dadoCadastral.setDataFalecimento(dadoCadastralComplementarRequest.getDataFalecimento());
		dadoCadastral.setTempoServidorProfessor(dadoCadastralComplementarRequest.getTempoServidorProfessor());
		dadoCadastral.setEmProcessoDeAposentadoria(dadoCadastralComplementarRequest.getEmProcessoDeAposentadoria());
		dadoCadastralComplementarRepository.save(dadoCadastral);
				
	}
	
	public DadoCadastralComplementarResponse getDadoCadastralComplementarByFuncionarioId(Long funcionarioId) {
		
		DadoCadastralComplementar dadoCadastral = dadoCadastralComplementarRepository.findByFuncionarioId(funcionarioId);

		DadoCadastralComplementarResponse dadoCadastralResponse;
		
		if (dadoCadastral != null){
			Usuario userCreated = usuarioRepository.findById(dadoCadastral.getCreatedBy())
					.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", dadoCadastral.getCreatedBy()));

			Usuario userUpdated = usuarioRepository.findById(dadoCadastral.getUpdatedBy())
					.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", dadoCadastral.getUpdatedBy()));

			dadoCadastralResponse = new DadoCadastralComplementarResponse(dadoCadastral, dadoCadastral.getCreatedAt(),
					userCreated.getNome(), dadoCadastral.getUpdatedAt(), userUpdated.getNome());
		}else{
			dadoCadastralResponse = new DadoCadastralComplementarResponse(funcionarioService.getFuncionarioById(funcionarioId));
		}

		return dadoCadastralResponse;
	}
	
	private void setEntidades(DadoCadastralComplementarRequest dadoCadastralComplementarRequest, DadoCadastralComplementar dadoCadastralComplementar) {
		
		if(Objects.nonNull(dadoCadastralComplementarRequest.getFuncionarioId())) {
			Funcionario funcionario = funcionarioRepository.findById(dadoCadastralComplementarRequest.getFuncionarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", dadoCadastralComplementarRequest.getFuncionarioId()));
			
			dadoCadastralComplementar.setFuncionario(funcionario);
		}
		
	}

}
