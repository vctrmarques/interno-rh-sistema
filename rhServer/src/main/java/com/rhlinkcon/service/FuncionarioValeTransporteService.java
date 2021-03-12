package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.FuncionarioValeTransporte;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.funcionario.FuncionarioValeTransporteRequest;
import com.rhlinkcon.payload.funcionario.FuncionarioValeTransporteResponse;
import com.rhlinkcon.repository.FuncionarioValeTransporteRepository;
import com.rhlinkcon.repository.ValeTransporteRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;

@Service
public class FuncionarioValeTransporteService {

	@Autowired
	private FuncionarioValeTransporteRepository funcionarioValeTransporteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private ValeTransporteRepository valeTransporteRepository;

	public void createFuncionarioValeTransporte(FuncionarioValeTransporteRequest funcionarioValeTransporteRequest) {

		FuncionarioValeTransporte funcionarioValeTransporte = new FuncionarioValeTransporte(funcionarioValeTransporteRequest);

		setEntidades(funcionarioValeTransporteRequest, funcionarioValeTransporte);
		funcionarioValeTransporteRepository.save(funcionarioValeTransporte);

	}

	public void deleteFuncionarioValeTransporte(Long funcionarioId) {

		funcionarioValeTransporteRepository.deleteByFuncionarioId(funcionarioId);
	}

	public FuncionarioValeTransporteResponse getFuncionarioValeTransporteById(Long funcionarioValeTransporteId) {
		FuncionarioValeTransporte funcionarioValeTransporte = funcionarioValeTransporteRepository.findById(funcionarioValeTransporteId)
				.orElseThrow(() -> new ResourceNotFoundException("FuncionarioValeTransporte", "id", funcionarioValeTransporteId));

		Usuario userCreated = usuarioRepository.findById(funcionarioValeTransporte.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", funcionarioValeTransporte.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(funcionarioValeTransporte.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", funcionarioValeTransporte.getUpdatedBy()));

		FuncionarioValeTransporteResponse funcionarioValeTransporteResponse = new FuncionarioValeTransporteResponse(funcionarioValeTransporte);
		
		funcionarioValeTransporteResponse.setCriadoEm(funcionarioValeTransporte.getCreatedAt());
		funcionarioValeTransporteResponse.setCriadoPor(userCreated.getNome());
		funcionarioValeTransporteResponse.setAlteradoPor(userUpdated.getNome());
		funcionarioValeTransporteResponse.setAlteradoEm(funcionarioValeTransporte.getUpdatedAt());

		return funcionarioValeTransporteResponse;
	}

	public List<FuncionarioValeTransporteResponse> getAllFuncionarioValesTransportes(Long funcionarioId) {

		List<FuncionarioValeTransporte> funcionariosValesTransportes = funcionarioValeTransporteRepository.findByFuncionarioId(funcionarioId);
		List<FuncionarioValeTransporteResponse> funcionariosValesTransportesResponse = new ArrayList<>();

		if (!funcionariosValesTransportes.isEmpty()) {
			for (FuncionarioValeTransporte funcionarioValeTransporte: funcionariosValesTransportes) {
				FuncionarioValeTransporteResponse funcionarioValeTransporteResponse = new FuncionarioValeTransporteResponse(funcionarioValeTransporte);

				funcionariosValesTransportesResponse.add(funcionarioValeTransporteResponse);
			}
			return funcionariosValesTransportesResponse ;
		}
		
		return null;
	}
	
	public void setEntidades(FuncionarioValeTransporteRequest funcionarioValeTransporteRequest, FuncionarioValeTransporte funcionarioValeTransporte) {
		funcionarioValeTransporte.setFuncionario(
				funcionarioRepository.findById(funcionarioValeTransporteRequest.getFuncionarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioValeTransporteRequest.getFuncionarioId())));
		
		funcionarioValeTransporte.setValeTransporte(
				valeTransporteRepository.findById(funcionarioValeTransporteRequest.getValeTransporteId())
				.orElseThrow(() -> new ResourceNotFoundException("ValeTransporte", "id",funcionarioValeTransporteRequest.getValeTransporteId())));
	}

}
