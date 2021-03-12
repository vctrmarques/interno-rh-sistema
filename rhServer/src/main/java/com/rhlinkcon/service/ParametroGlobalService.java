package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.model.ParametroGlobal;
import com.rhlinkcon.model.ParametroGlobalChaveEnum;
import com.rhlinkcon.repository.parametroGlobal.ParametroGlobalRepository;

@Service
public class ParametroGlobalService {

	@Autowired
	private ParametroGlobalRepository parametroGlobalRepository;

	public ParametroGlobal getParametroByChaveAndAtivo(ParametroGlobalChaveEnum chave, boolean ativo) {
		return parametroGlobalRepository.findByChaveAndAtivo(chave, ativo).isPresent()
				? parametroGlobalRepository.findByChaveAndAtivo(chave, ativo).get()
				: null;
	}

}
