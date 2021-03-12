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
import com.rhlinkcon.payload.certidaoExSegurado.HistoricoCertidaoExSeguradoRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.HistoricoCertidaoExSeguradoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;
import com.rhlinkcon.model.HistoricoCertidaoExSegurado;

@RestController
@RequestMapping("/api")
public class HistoricoCertidaoExSeguradoController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";
	
	@Autowired
	private HistoricoCertidaoExSeguradoService historicoCertidaoExSeguradoService;
	
	
	@PostMapping("/historicoCertidaoExSegurado")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> create(@Valid @RequestBody HistoricoCertidaoExSeguradoRequest historicoCertidaoExSeguradoRequest) {
		HistoricoCertidaoExSegurado historicoCertidaoExSegurado = new HistoricoCertidaoExSegurado( historicoCertidaoExSeguradoRequest);
		historicoCertidaoExSeguradoService.create(historicoCertidaoExSegurado);
		return Utils.created(true, "Criado com sucesso.");
	}
	
	@PutMapping("/historicoCertidaoExSegurado")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> update(@Valid @RequestBody HistoricoCertidaoExSeguradoRequest  historicoCertidaoExSeguradoRequest) {
		HistoricoCertidaoExSegurado historicoCertidaoExSegurado = new HistoricoCertidaoExSegurado( historicoCertidaoExSeguradoRequest);
		historicoCertidaoExSeguradoService.create(historicoCertidaoExSegurado);
		return Utils.created(true, "Editado com sucesso.");
	}
	
	@DeleteMapping("/historicoCertidaoExSegurado/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		historicoCertidaoExSeguradoService.delete(id);
		return Utils.deleted(true, "deletado com sucesso.");
	}
	

	
}