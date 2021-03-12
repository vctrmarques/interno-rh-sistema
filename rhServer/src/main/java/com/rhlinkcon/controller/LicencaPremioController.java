package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.dependente.DependenteRequest;
import com.rhlinkcon.payload.dependente.DependenteResponse;
import com.rhlinkcon.payload.licencaPremio.LicencaPremioRequest;
import com.rhlinkcon.payload.licencaPremio.LicencaPremioResponse;
import com.rhlinkcon.repository.DependenteRepository;
import com.rhlinkcon.repository.FuncionarioExercicioRepository;
import com.rhlinkcon.repository.LicencaPremioRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.service.DependenteService;
import com.rhlinkcon.service.LicencaPremioService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class LicencaPremioController {

	@Autowired
	private LicencaPremioRepository licencaPremioRepository;
	
	@Autowired
	private FuncionarioExercicioRepository funcionarioExercicioRepository;

	@Autowired
	private LicencaPremioService licencaPremioService;

	@GetMapping("/licencasPremio/{funcionarioExercicioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<LicencaPremioResponse> getAllLicencasPremio(@PathVariable Long funcionarioExercicioId) { 
		return licencaPremioService.getAllLicencasPremioByFuncionarioExercicio(funcionarioExercicioId);
	}
	
	
//	@GetMapping("/dependente/{dependenteId}")
//	@PreAuthorize("hasAnyRole('ADMIN')")
//	public DependenteResponse getDependenteById(@PathVariable Long dependenteId) { 
//		return dependenteService.getDependenteById(dependenteId);
//	}

	@PostMapping("/licencaPremio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createLicencaPremio(@Valid @RequestBody List<LicencaPremioRequest> licencaPremioRequest) {

		licencaPremioService.createLicencaPremio(licencaPremioRequest);
		
		return Utils.created(true, "Licença Prêmio criada com sucesso.");
	}

	@PutMapping("/licencaPremio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateDependente(@Valid @RequestBody List<LicencaPremioRequest> licencaPremioRequest) {

		licencaPremioService.updateLicencaPremio(licencaPremioRequest);

		return Utils.created(true, "Licença Prêmio atualizada com sucesso.");
	}

	@DeleteMapping("/licencaPremio/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteLicencaPremio(@PathVariable("id") Long id) {
		licencaPremioService.deleteLicencaPremio(id);

		return Utils.deleted(true, "Licença Prêmio deletada com sucesso.");
	}
	

}
