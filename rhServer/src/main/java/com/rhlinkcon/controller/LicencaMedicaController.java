package com.rhlinkcon.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.crmCrea.CrmCreaRequest;
import com.rhlinkcon.payload.crmCrea.CrmCreaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.licencaMedica.LicencaMedicaRequest;
import com.rhlinkcon.payload.licencaMedica.LicencaMedicaResponse;
import com.rhlinkcon.service.LicencaMedicaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class LicencaMedicaController{
	@Autowired
	private LicencaMedicaService licencaMedicaService;
	
	
	@GetMapping("/licencasMedicas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<LicencaMedicaResponse> getAllLicencas(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nomeFuncionario", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeFuncionario) {
		return licencaMedicaService.getAllLicencasMedicas(page, size, order, nomeFuncionario);
	}
	
	@GetMapping("/licencaMedica/{licendaMediaId}")
	public LicencaMedicaResponse getLicencaById(@PathVariable Long licendaMediaId) {
		return licencaMedicaService.getLicencaById(licendaMediaId);
	}
	
	@PostMapping("/licencaMedica")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createLicencaMedica(@Valid @RequestBody LicencaMedicaRequest licencaMedicaRequest) {
		licencaMedicaService.createLicencaMedica(licencaMedicaRequest);
		return Utils.created(true, "Licença Médica criada com sucesso.");
	}
	
	@PutMapping("/licencaMedica")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateLicencaMedica(@Valid @RequestBody LicencaMedicaRequest licencaMedicaRequest) {
		licencaMedicaService.createLicencaMedica(licencaMedicaRequest);
		return Utils.created(true, "Licença Médica atualizado com sucesso.");
	}

	@DeleteMapping("/licencaMedica/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCrmCrea(@PathVariable("id") Long id) {
		licencaMedicaService.deleteLicencaMedica(id);
		return Utils.deleted(true, "Licença Médica  deletado com sucesso.");
	}
}
