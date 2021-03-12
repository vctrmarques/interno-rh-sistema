package com.rhlinkcon.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.FeriasProgramacao;
import com.rhlinkcon.model.FeriasProgramacaoSituacaoEnum;
import com.rhlinkcon.model.FeriasProgramacaoTipoFeriasEnum;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.payload.feriasProgramacao.FeriasProgramacaoCancelarRequest;
import com.rhlinkcon.payload.feriasProgramacao.FeriasProgramacaoRequest;
import com.rhlinkcon.payload.feriasProgramacao.FeriasProgramacaoResponse;
import com.rhlinkcon.repository.FeriasProgramacaoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;

@Service
public class FeriasProgramacaoService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private FeriasProgramacaoRepository feriasProgramacaoRepository;
	
	
	
	public FeriasProgramacaoResponse getFeriasProgramacaoById(Long feriasProgramacaoId) {

		FeriasProgramacao feriasProgramacao = feriasProgramacaoRepository.findById(feriasProgramacaoId)
							.orElseThrow(() -> new ResourceNotFoundException("Férias Programacao", "id", feriasProgramacaoId));
		
		FeriasProgramacaoResponse feriasProgramacaoResponse = new FeriasProgramacaoResponse(feriasProgramacao);
		
		feriasProgramacaoResponse.setFuncionarioId(feriasProgramacao.getFuncionario().getId());

			return feriasProgramacaoResponse;
	}	
	
	public Date getMaxExercicioInicialFeriasProgramacaoByFuncionarioId(Long funcionarioId) {
		Date feriasProgramacaoExercicioInicialMaximo = feriasProgramacaoRepository.findByMaxExercicioInicialByFuncionarioId(funcionarioId);
		
		return feriasProgramacaoExercicioInicialMaximo;
	}	
	

	public void createFeriasProgramacao(FeriasProgramacaoRequest feriasProgramacaoRequest) {

		FeriasProgramacao feriasProgramacao = new FeriasProgramacao(feriasProgramacaoRequest);
		
		setEntidades(feriasProgramacaoRequest, feriasProgramacao);

		feriasProgramacaoRepository.save(feriasProgramacao);

	}

	public void updateFeriasProgramacao(FeriasProgramacaoRequest feriasProgramacaoRequest) {
		
		FeriasProgramacao feriasProgramacao = feriasProgramacaoRepository.findById(feriasProgramacaoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Ferias Programacao", "id", feriasProgramacaoRequest.getId()));
		
		
		feriasProgramacao.setExercicioInicio(feriasProgramacaoRequest.getExercicioInicio());	
		feriasProgramacao.setExercicioFim(feriasProgramacaoRequest.getExercicioFim());	
		feriasProgramacao.setDataLimite(feriasProgramacaoRequest.getDataLimite());	
		feriasProgramacao.setQuantFaltas(feriasProgramacaoRequest.getQuantFaltas());
		feriasProgramacao.setTipoFerias(feriasProgramacaoRequest.getTipoFerias());
		feriasProgramacao.setQuantDias(feriasProgramacaoRequest.getQuantDias());	
		feriasProgramacao.setAbonoPeculiario(feriasProgramacaoRequest.getAbonoPeculiario());
		feriasProgramacao.setDecimoTerceiro(feriasProgramacaoRequest.getDecimoTerceiro());		
		feriasProgramacao.setSituacao(feriasProgramacaoRequest.getSituacao());
		
		feriasProgramacao.setMesCompetenciaParticaoUm(feriasProgramacaoRequest.getMesCompetenciaParticaoUm());
		feriasProgramacao.setDiasAGozarUm(feriasProgramacaoRequest.getDiasAGozarUm());	
		feriasProgramacao.setTipoProcessamentoUm(feriasProgramacaoRequest.getTipoProcessamentoUm());	
		feriasProgramacao.setDataInicialUm(feriasProgramacaoRequest.getDataInicialUm());
		feriasProgramacao.setDataFinalUm(feriasProgramacaoRequest.getDataFinalUm());
		feriasProgramacao.setSituacaoUm(feriasProgramacaoRequest.getSituacaoUm());
		
		feriasProgramacao.setMesCompetenciaParticaoDois(feriasProgramacaoRequest.getMesCompetenciaParticaoDois());
		feriasProgramacao.setDiasAGozarDois(feriasProgramacaoRequest.getDiasAGozarDois());	
		feriasProgramacao.setTipoProcessamentoDois(feriasProgramacaoRequest.getTipoProcessamentoDois());	
		feriasProgramacao.setDataInicialDois(feriasProgramacaoRequest.getDataInicialDois());
		feriasProgramacao.setDataFinalDois(feriasProgramacaoRequest.getDataFinalDois());
		feriasProgramacao.setSituacaoDois(feriasProgramacaoRequest.getSituacaoDois());
		
		feriasProgramacao.setMesCompetenciaParticaoTres(feriasProgramacaoRequest.getMesCompetenciaParticaoTres());
		feriasProgramacao.setDiasAGozarTres(feriasProgramacaoRequest.getDiasAGozarTres());	
		feriasProgramacao.setTipoProcessamentoTres(feriasProgramacaoRequest.getTipoProcessamentoTres());	
		feriasProgramacao.setDataInicialTres(feriasProgramacaoRequest.getDataInicialTres());
		feriasProgramacao.setDataFinalTres(feriasProgramacaoRequest.getDataFinalTres());
		feriasProgramacao.setSituacaoTres(feriasProgramacaoRequest.getSituacaoTres());
		
		feriasProgramacaoRepository.save(feriasProgramacao);

	}
	
	public void updateFeriasProgramacaoStatusParaAgendado(Long feriasProgramacaoId) {
		
		FeriasProgramacao feriasProgramacao = feriasProgramacaoRepository.findById(feriasProgramacaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Ferias Programacao", "id", feriasProgramacaoId));
		
			
		feriasProgramacao.setSituacao(FeriasProgramacaoSituacaoEnum.AGENDADO);
		
		feriasProgramacaoRepository.save(feriasProgramacao);

	}	
	
	public void updateFeriasProgramacaoStatusParaCancelado(FeriasProgramacaoCancelarRequest feriasProgramacaoCancelarRequest) {
		
		for(Long feriasProgramacaoId : feriasProgramacaoCancelarRequest.getIds()) {
			FeriasProgramacao feriasProgramacao = feriasProgramacaoRepository.findById(feriasProgramacaoId)
					.orElseThrow(() -> new ResourceNotFoundException("Ferias Programacao", "id", feriasProgramacaoId));	
			
			feriasProgramacao.setSituacao(FeriasProgramacaoSituacaoEnum.CANCELADO);
			feriasProgramacao.setMotivo(feriasProgramacaoCancelarRequest.getMotivo());
			
			feriasProgramacaoRepository.save(feriasProgramacao);
		}
	}	


	public List<FeriasProgramacaoResponse> getAllFeriasProgramacaoByFuncionarioId(Long funcionarioId) {

		List<FeriasProgramacao> listFeriasProgramacao = feriasProgramacaoRepository.findByFuncionarioId(funcionarioId);
		List<FeriasProgramacaoResponse> listFeriasProgramacaoResponse = new ArrayList<FeriasProgramacaoResponse>();
		
		for(FeriasProgramacao feriasProgramacao : listFeriasProgramacao) {
			FeriasProgramacaoResponse feriasProgramacaoResponse = new FeriasProgramacaoResponse(feriasProgramacao);
			feriasProgramacaoResponse.setSituacaoLabel(feriasProgramacao.getSituacao().getLabel());
			listFeriasProgramacaoResponse.add(feriasProgramacaoResponse);
		}
		
		return listFeriasProgramacaoResponse;
	}
	
	private void setEntidades(FeriasProgramacaoRequest feriasProgramacaoRequest, FeriasProgramacao feriasProgramacao) {
		
		if(Objects.nonNull(feriasProgramacaoRequest.getFuncionarioId())) {
			Funcionario funcionario = funcionarioRepository.findById(feriasProgramacaoRequest.getFuncionarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", feriasProgramacaoRequest.getFuncionarioId()));
			
			feriasProgramacao.setFuncionario(funcionario);
		}

	}
	
	// Serviço de Féria para rodar todos os dias
	
	public void executarJob() {
		
		List<Funcionario> funcionarios = funcionarioRepository.findAll();
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd");
		
		//Loopar todos os funcionários
		for(Funcionario funcionario : funcionarios) {
			
			Date dataAdmissao = Date.from(funcionario.getDataAdmissao());
			Integer faltas = 0;
			Integer quantDias = 30 - faltas;
			Calendar c = Calendar.getInstance();
			
			c.setTime(dataAdmissao);
			c.add(Calendar.MONTH, 10);
			
			// data_admissao + 10 meses
			String dataProgramacao = dateFormat.format(Date.from(c.toInstant()));
			
			// periodo do exercicio_inicial 
			c.add(Calendar.MONTH, 2);
//			DateFormat dateFormatWithYear = new SimpleDateFormat("yyyy/MM/dd");
//			String dataFeriasProgramado = dateFormatWithYear.format();
			
			String dataHoje = dateFormat.format(new Date());
			
			// verificar se a data de admissão dele + 1 ANO é igual a data atual ( HOJE )
			if(dataHoje.equals(dataProgramacao)) {
			
	            Date dataExercicioInicio = Date.from(c.toInstant());
	
//				List<FeriasProgramacao> listFeriasProgramacao = feriasProgramacaoRepository.findByExercicioInicio(dataExercicioInicio);
				
				Date newDataExercicioInicio = feriasProgramacaoRepository.findByMaxExercicioInicialByFuncionarioId(funcionario.getId());
				
				FeriasProgramacao feriasProgramacao = new FeriasProgramacao();
				feriasProgramacao.setFuncionario(funcionario);
				
				if(newDataExercicioInicio == null) {
										
					feriasProgramacao.setExercicioInicio(dataExercicioInicio);
					
					Calendar exercicioFim = Calendar.getInstance();
					
					exercicioFim.setTime(dataExercicioInicio);
					exercicioFim.add(Calendar.YEAR, 1);	
					
					feriasProgramacao.setExercicioFim(Date.from(exercicioFim.toInstant()));	
												
					Calendar dataLimite = Calendar.getInstance();
					
					dataLimite.setTime(Date.from(exercicioFim.toInstant()));
					dataLimite.add(Calendar.MONTH, 11);								
					
					feriasProgramacao.setDataLimite(Date.from(dataLimite.toInstant()));
													
				}else {
							
					Calendar dataExercicioInicioNova = Calendar.getInstance();
					dataExercicioInicioNova.setTime(newDataExercicioInicio);
					dataExercicioInicioNova.add(Calendar.YEAR, 1);

					feriasProgramacao.setExercicioInicio(Date.from(dataExercicioInicioNova.toInstant()));
					
					Calendar exercicioFim = Calendar.getInstance();
					
					exercicioFim.setTime(Date.from(dataExercicioInicioNova.toInstant()));
					exercicioFim.add(Calendar.YEAR, 1);	
					
					feriasProgramacao.setExercicioFim(Date.from(exercicioFim.toInstant()));
					
												
					Calendar dataLimite = Calendar.getInstance();
					
					dataLimite.setTime(Date.from(exercicioFim.toInstant()));
					dataLimite.add(Calendar.MONTH, 11);								
					
					feriasProgramacao.setDataLimite(Date.from(dataLimite.toInstant()));					
				}
				
				feriasProgramacao.setQuantDias(quantDias.toString());
				
				feriasProgramacao.setQuantFaltas(faltas.toString());
				feriasProgramacao.setSituacao(FeriasProgramacaoSituacaoEnum.EM_ABERTO);
				
				feriasProgramacao.setTipoFerias(FeriasProgramacaoTipoFeriasEnum.SEM_ABONO_SEM_DECIMO_TERCEIRO);
				
				feriasProgramacaoRepository.save(feriasProgramacao);				

			}
		}

	}

}
