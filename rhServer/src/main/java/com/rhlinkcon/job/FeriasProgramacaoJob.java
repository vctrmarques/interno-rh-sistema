package com.rhlinkcon.job;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rhlinkcon.service.FeriasProgramacaoService;
import com.rhlinkcon.service.SimuladorNivelSalarialService;

@Component
public class FeriasProgramacaoJob {

    private static final Logger log = LoggerFactory.getLogger(FeriasProgramacaoJob.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Autowired
	private FeriasProgramacaoService feriasProgramacaoService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void reportCurrentTime() {
    	feriasProgramacaoService.executarJob();
    }
}
