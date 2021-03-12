package com.rhlinkcon.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.Falta;
import com.rhlinkcon.model.Frequencia;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Processo;
import com.rhlinkcon.payload.falta.FaltaRequest;
import com.rhlinkcon.payload.falta.FaltaResponse;
import com.rhlinkcon.payload.processo.ProcessoResponse;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.repository.FaltaRespository;
import com.rhlinkcon.repository.FrequenciaRepository;
import com.rhlinkcon.util.Projecao;

@Service
public class FaltaService {
	@Autowired
	private FaltaRespository faltaRespository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AnexoRepository anexoRepository;
	@Autowired
	private FrequenciaRepository frequenciaRepository;
	
	public void create(@Valid FaltaRequest faltaRequest) {
		Frequencia frequencia = new Frequencia();	
		Falta falta = new Falta(faltaRequest);	
		faltaRespository.save(falta);
		if(Objects.isNull(faltaRequest.getId())) {
			frequencia.setFalta(falta);
			frequencia.setFuncionario(new Funcionario(faltaRequest.getFuncionarioId()));
			frequencia.setData(LocalDate.now());
			frequenciaRepository.save(frequencia);
		}
	}

	public FaltaResponse getFaltaById(Long faltaId) {
		Falta falta = faltaRespository.findById(faltaId).orElseThrow(() -> new ResourceNotFoundException("Falta", "id", faltaId));
		FaltaResponse faltaResponse = new FaltaResponse(falta);
		faltaResponse.setCriadoPor(usuarioService.criadoPor(falta));
		faltaResponse.setAlteradoPor(usuarioService.alteradoPor(falta));
		return faltaResponse;
	}

	public void deleteAnexo(Long id) {
		Falta falta = faltaRespository.findFaltaByAnexoId(id);
		if (Objects.nonNull(falta)) {
			falta.setAnexo(null);
			faltaRespository.save(falta);
		}
		Anexo anexo = anexoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", id));
		anexoRepository.delete(anexo);
	}
}
