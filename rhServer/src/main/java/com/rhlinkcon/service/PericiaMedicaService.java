package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.repository.periciaMedica.PericiaMedicaRepository;

@Service
public class PericiaMedicaService {

	
	@Autowired
	private PericiaMedicaRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;

}
