package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.ContribuicaoSindical;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Sindicato;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.contribuicaoSindical.ContribuicaoSindicalRequest;
import com.rhlinkcon.payload.contribuicaoSindical.ContribuicaoSindicalResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.repository.ContribuicaoSindicalRepository;
import com.rhlinkcon.repository.SindicatoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class ContribuicaoSindicalService {
	
	@Autowired
	ContribuicaoSindicalRepository contribuicaoSindicalRepository;
	
	@Autowired
	SindicatoRepository sindicatoRepository;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	AnexoRepository anexoRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public void insertContribuicaoSindical(ContribuicaoSindicalRequest contribuicaoSindicalRequest) {

	}

	public void updateContribuicaoSindical(ContribuicaoSindicalRequest contribuicaoSindicalRequest) {
		// Updating user's account
		ContribuicaoSindical contribuicaoSindical = contribuicaoSindicalRepository.findById(contribuicaoSindicalRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ContribuicaoSindical", "id", contribuicaoSindicalRequest.getId()));
		
		Funcionario funcionario = funcionarioRepository.findById(contribuicaoSindicalRequest.getFuncionarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", contribuicaoSindicalRequest.getFuncionarioId()));
		Sindicato sindicato = sindicatoRepository.findById(contribuicaoSindicalRequest.getSindicatoId())
				.orElseThrow(() -> new ResourceNotFoundException("Sindicato", "id", contribuicaoSindicalRequest.getSindicatoId()));
		
		contribuicaoSindical.setFuncionario(funcionario);
		contribuicaoSindical.setSindicato(sindicato);
		contribuicaoSindical.setAno(contribuicaoSindicalRequest.getAno());
		contribuicaoSindical.setPermiteDesconto(contribuicaoSindicalRequest.getPermiteDesconto());
		
		if(contribuicaoSindicalRequest.getPermiteDesconto()) {
			Anexo anexo = anexoRepository.findById(contribuicaoSindicalRequest.getAnexoId())
					.orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", contribuicaoSindicalRequest.getAnexoId()));
			contribuicaoSindical.setAnexo(anexo);
		}
		
		contribuicaoSindicalRepository.save(contribuicaoSindical);
	}
	
	public List<FuncionarioResponse> listFuncionariosByContribuicaoSindical(){
		// List<ContribuicaoSindical> listContribuicaoSindical = contribuicaoSindicalRepository.findAll();
		return null;
	}
	
	public void createContribuicaoSindical(ContribuicaoSindicalRequest contribuicaoSindicalRequest) {

		ContribuicaoSindical contribuicao = new ContribuicaoSindical();
		
		Funcionario funcionario = funcionarioRepository.findById(contribuicaoSindicalRequest.getFuncionarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", contribuicaoSindicalRequest.getFuncionarioId()));
		Sindicato sindicato = sindicatoRepository.findById(contribuicaoSindicalRequest.getSindicatoId())
				.orElseThrow(() -> new ResourceNotFoundException("Sindicato", "id", contribuicaoSindicalRequest.getSindicatoId()));
		
		contribuicao.setFuncionario(funcionario);
		contribuicao.setSindicato(sindicato);
		contribuicao.setAno(contribuicaoSindicalRequest.getAno());
		contribuicao.setPermiteDesconto(contribuicaoSindicalRequest.getPermiteDesconto());
		
		if(contribuicaoSindicalRequest.getPermiteDesconto()) {
			Anexo anexo = anexoRepository.findById(contribuicaoSindicalRequest.getAnexoId())
					.orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", contribuicaoSindicalRequest.getAnexoId()));
			contribuicao.setAnexo(anexo);
		}

		contribuicaoSindicalRepository.save(contribuicao);

	}
	
	public ContribuicaoSindicalResponse getContribuicaoById(Long contribuicaoId) {
		ContribuicaoSindical contribuicao = contribuicaoSindicalRepository.findById(contribuicaoId)
				.orElseThrow(() -> new ResourceNotFoundException("ContribuicaoSindical", "id", contribuicaoId));
		
		Usuario userCreated = usuarioRepository.findById(contribuicao.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", contribuicao.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(contribuicao.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", contribuicao.getUpdatedBy()));

		ContribuicaoSindicalResponse contribuicaoSindicalResponse = new ContribuicaoSindicalResponse(contribuicao, contribuicao.getCreatedAt(),
				userCreated.getNome(), contribuicao.getUpdatedAt(), userUpdated.getNome(), Projecao.BASICA);

		return contribuicaoSindicalResponse;
	}
	
	public PagedResponse<ContribuicaoSindicalResponse> getAllContribuicoesSindicais(int page, int size, String nome, Integer matricula, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);
		
		Page<ContribuicaoSindical> contribuicoes = null;
		if(nome.equalsIgnoreCase(""))
			nome = null;
		if(Objects.nonNull(nome) && !Objects.nonNull(matricula)) {
			contribuicoes = contribuicaoSindicalRepository.findByNomeIgnoreCaseContaining(nome, pageable);			
		} else if(!Objects.nonNull(nome) && Objects.nonNull(matricula)) {
			contribuicoes = contribuicaoSindicalRepository.findByMatriculaIgnoreCaseContaining(matricula, pageable);
		} else if(Objects.nonNull(nome) && Objects.nonNull(matricula)) {
			contribuicoes = contribuicaoSindicalRepository.findByMatriculaAndNomeIgnoreCaseContaining(nome, matricula, pageable);
		}else {
			contribuicoes = contribuicaoSindicalRepository.findAll(pageable);
		}

		if (contribuicoes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), contribuicoes.getNumber(), contribuicoes.getSize(),
					contribuicoes.getTotalElements(), contribuicoes.getTotalPages(), contribuicoes.isLast());
		}

		List<ContribuicaoSindicalResponse> contribuicoesResponse = contribuicoes.map(contribuicao -> {
			return new ContribuicaoSindicalResponse(contribuicao, Projecao.BASICA);
		}).getContent();
		
		return new PagedResponse<>(contribuicoesResponse, contribuicoes.getNumber(), contribuicoes.getSize(),
				contribuicoes.getTotalElements(), contribuicoes.getTotalPages(), contribuicoes.isLast());
	}
	
	public void deleteContribuicaoSindical(Long id) {
		ContribuicaoSindical contribuicaoSindical = contribuicaoSindicalRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ContribuicaoSindical", "id", id));
		
		contribuicaoSindicalRepository.delete(contribuicaoSindical);
	}
	
	public List<ContribuicaoSindicalResponse> getAllContribuicaoByFuncionario(Long funcionarioId){
		
		List<ContribuicaoSindical> contribuicoes = contribuicaoSindicalRepository.findContribuicaoSindicalByFuncionario(funcionarioId);
		List<ContribuicaoSindicalResponse> contribuicoesResponse = new ArrayList<ContribuicaoSindicalResponse>();
		for(ContribuicaoSindical c : contribuicoes) {
			contribuicoesResponse.add(new ContribuicaoSindicalResponse(c, Projecao.BASICA));
		}
		
		return contribuicoesResponse;
	}

}
