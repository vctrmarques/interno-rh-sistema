package com.rhlinkcon.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.HistoricoCertidaoExSegurado;
import com.rhlinkcon.repository.HistoricoCertidaoExSeguradoRepository;

@Service
public class HistoricoCertidaoExSeguradoService {
	@Autowired
	private HistoricoCertidaoExSeguradoRepository historicoCertidaoExSeguradoRepository;

	public void create(HistoricoCertidaoExSegurado historicoCertidaoExSegurado) {
		historicoCertidaoExSeguradoRepository.save(historicoCertidaoExSegurado);
	}

	public void delete(Long id) {
		HistoricoCertidaoExSegurado historicoCertidaoExSegurado = historicoCertidaoExSeguradoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("HistoricoCertidaoExSegurado", "id", id));
		historicoCertidaoExSeguradoRepository.delete(historicoCertidaoExSegurado);
	}
	
} 