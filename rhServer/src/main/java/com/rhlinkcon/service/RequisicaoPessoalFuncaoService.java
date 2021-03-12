package com.rhlinkcon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.RequisicaoPessoalFuncao;
import com.rhlinkcon.payload.requisicaoPessoalFuncao.RequisicaoPessoalFuncaoReponse;
import com.rhlinkcon.repository.requisicaoPessoal.RequisicaoPessoalFuncaoRepository;

@Service
public class RequisicaoPessoalFuncaoService {
	@Autowired
	private RequisicaoPessoalFuncaoRepository requisicaoPessoalFuncaoRepository;

	public List<RequisicaoPessoalFuncaoReponse> getAllFuncoes(Long requisicaoPessoalId) {
		List<RequisicaoPessoalFuncao> list = requisicaoPessoalFuncaoRepository.findAllByRequisicaoPessoalId(requisicaoPessoalId);
		List<RequisicaoPessoalFuncaoReponse> response = list.stream().map(rpf -> new RequisicaoPessoalFuncaoReponse(rpf)).collect(Collectors.toList());
		return response;
	}

	public void deleteFuncao(Long id) {
		RequisicaoPessoalFuncao requisicaoPessoal =  requisicaoPessoalFuncaoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Funcao", "id", id));
		requisicaoPessoalFuncaoRepository.delete(requisicaoPessoal);
		
	}

}
