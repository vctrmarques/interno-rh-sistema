package com.rhlinkcon.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.FrequenciaCertidaoExSegurado;
import com.rhlinkcon.repository.FrequenciaCertidaoExSeguradoRepository;

@Service
public class FrequenciaCertidaoExSeguradoService {
	@Autowired
	private FrequenciaCertidaoExSeguradoRepository frequenciaCertidaoExSeguradoRepository;

	public void create(FrequenciaCertidaoExSegurado frequenciaCertidaoExSegurado) {
		frequenciaCertidaoExSeguradoRepository.save(frequenciaCertidaoExSegurado);
	}

	public void delete(Long id) {
		FrequenciaCertidaoExSegurado frequenciaCertidaoExSegurado = frequenciaCertidaoExSeguradoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("FrequenciaCertidaoExSegurado", "id", id));
		frequenciaCertidaoExSeguradoRepository.delete(frequenciaCertidaoExSegurado);
	}
	
} 