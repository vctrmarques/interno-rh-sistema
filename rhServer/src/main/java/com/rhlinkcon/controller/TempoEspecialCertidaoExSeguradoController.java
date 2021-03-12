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
import com.rhlinkcon.payload.certidaoExSegurado.TempoEspecialCertidaoExSeguradoRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.TempoEspecialCertidaoExSeguradoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;
import com.rhlinkcon.model.TempoEspecialCertidaoExSegurado;

@RestController
@RequestMapping("/api")
public class TempoEspecialCertidaoExSeguradoController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";
	
	@Autowired
	private TempoEspecialCertidaoExSeguradoService tempoEspecialCertidaoExSeguradoService;
	
	
	@PostMapping("/tempoEspecialCertidaoExSegurado")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> create(@Valid @RequestBody TempoEspecialCertidaoExSeguradoRequest tempoEspecialCertidaoExSeguradoRequest) {
		TempoEspecialCertidaoExSegurado tempoEspecialCertidaoExSegurado = new TempoEspecialCertidaoExSegurado( tempoEspecialCertidaoExSeguradoRequest);
		tempoEspecialCertidaoExSeguradoService.create(tempoEspecialCertidaoExSegurado);
		return Utils.created(true, "Criado com sucesso.");
	}
	
	@PutMapping("/tempoEspecialCertidaoExSegurado")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> update(@Valid @RequestBody TempoEspecialCertidaoExSeguradoRequest  tempoEspecialCertidaoExSeguradoRequest) {
		TempoEspecialCertidaoExSegurado tempoEspecialCertidaoExSegurado = new TempoEspecialCertidaoExSegurado( tempoEspecialCertidaoExSeguradoRequest);
		tempoEspecialCertidaoExSeguradoService.create(tempoEspecialCertidaoExSegurado);
		return Utils.created(true, "Editado com sucesso.");
	}
	
	@DeleteMapping("/tempoEspecialCertidaoExSegurado/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		tempoEspecialCertidaoExSeguradoService.delete(id);
		return Utils.deleted(true, "deletado com sucesso.");
	}
	

	
}