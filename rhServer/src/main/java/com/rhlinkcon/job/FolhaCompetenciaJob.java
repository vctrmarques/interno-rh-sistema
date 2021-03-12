package com.rhlinkcon.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rhlinkcon.service.FolhaCompetenciaService;

@Component
public class FolhaCompetenciaJob {
	
	@Autowired
	private FolhaCompetenciaService folhaCompetenciaService;

	
//	Job para executar o fechamento de competencia
	@Scheduled(cron = "00 59 23 1/1 * ?")
	public void reportCurrentTime() {
		folhaCompetenciaService.executarProgramacaoFechamentoCompetenciaJob();
	}
}
