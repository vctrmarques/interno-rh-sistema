package com.rhlinkcon.service;

import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Cargo;
import com.rhlinkcon.model.DeclaracaoExServidor;
import com.rhlinkcon.model.DeclaracaoExServidorDadosFuncionais;
import com.rhlinkcon.payload.declaracaoExServidor.DeclaracaoExServidorDadosFuncionaisRequest;
import com.rhlinkcon.repository.CargoRepository;
import com.rhlinkcon.repository.DeclaracaoExServidorDadosFuncionaisRepository;
import com.rhlinkcon.repository.declaracaoExServidor.DeclaracaoExServidorRepository;

@Service
public class DeclaracaoExServidorDadosFuncionaisService {

	@Autowired
	private DeclaracaoExServidorDadosFuncionaisRepository repository;
	
	@Autowired
	private DeclaracaoExServidorRepository declaracaoRepository;
	
	@Autowired
	private CargoRepository cargoRepository;
	
	public void create(DeclaracaoExServidorDadosFuncionaisRequest item) {
		DeclaracaoExServidorDadosFuncionais obj = new DeclaracaoExServidorDadosFuncionais();
		
		obj.setId(item.getId());
		
		DeclaracaoExServidor declaracao = declaracaoRepository.findById(item.getDeclaracaoExServidorId())
				.orElseThrow(() -> new ResourceNotFoundException("DeclaracaoExServidor", "id", item.getDeclaracaoExServidorId())); 
		obj.setDeclaracaoExServidor(declaracao);
		
		obj.setAtoNomeacao(item.getAtoNomeacao());
		obj.setAtoEncerramento(item.getAtoEncerramento());
		
		Cargo cargo = cargoRepository.findById(item.getCargoId())
				.orElseThrow(() -> new ResourceNotFoundException("Cargo", "id", item.getCargoId()));
		obj.setCargo(cargo);
		
		obj.setDataEntrada(item.getDataEntrada().atZone(ZoneOffset.UTC).toLocalDate());
		obj.setDataEncerramento(item.getDataEncerramento().atZone(ZoneOffset.UTC).toLocalDate());
		obj.setDataDiarioOficialEntrada(item.getDataDiarioOficialEntrada().atZone(ZoneOffset.UTC).toLocalDate());
		obj.setDataDiarioOficialEncerramento(item.getDataDiarioOficialEncerramento().atZone(ZoneOffset.UTC).toLocalDate());
		
		repository.save(obj);
	}

	public void delete(Long dadosFuncionaisId) {
		repository.deleteById(dadosFuncionaisId);
	}

	public List<DeclaracaoExServidorDadosFuncionais> findByDeclaracaoId(Long id) {
		return repository.findByDeclaracaoExServidorId(id);
	}

}
