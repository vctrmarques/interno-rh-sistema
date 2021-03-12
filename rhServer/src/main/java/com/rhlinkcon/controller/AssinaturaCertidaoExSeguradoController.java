package com.rhlinkcon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.model.AssinaturaCertidaoExSegurado;
import com.rhlinkcon.payload.certidaoExSegurado.AssinaturaCertidaoExSeguradoRequest;
import com.rhlinkcon.service.AssinaturaCertidaoExSeguradoService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class AssinaturaCertidaoExSeguradoController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";
	
	@Autowired
	private AssinaturaCertidaoExSeguradoService assinaturaCertidaoExSeguradoService;
	
	
	@PostMapping("/assinaturaCertidaoExSegurado")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> create(@Valid @RequestBody AssinaturaCertidaoExSeguradoRequest assinaturaCertidaoExSeguradoRequest) {
		AssinaturaCertidaoExSegurado assinaturaCertidaoExSegurado = new AssinaturaCertidaoExSegurado( assinaturaCertidaoExSeguradoRequest);
		assinaturaCertidaoExSeguradoService.create(assinaturaCertidaoExSegurado);
		return Utils.created(true, "Criado com sucesso.");
	}
	
	@PutMapping("/assinaturaCertidaoExSegurado")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> update(@Valid @RequestBody AssinaturaCertidaoExSeguradoRequest  assinaturaCertidaoExSeguradoRequest) {
		AssinaturaCertidaoExSegurado assinaturaCertidaoExSegurado = new AssinaturaCertidaoExSegurado( assinaturaCertidaoExSeguradoRequest);
		assinaturaCertidaoExSeguradoService.create(assinaturaCertidaoExSegurado);
		return Utils.created(true, "Editado com sucesso.");
	}
	
	@DeleteMapping("/assinaturaCertidaoExSegurado/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		assinaturaCertidaoExSeguradoService.delete(id);
		return Utils.deleted(true, "deletado com sucesso.");
	}
	
	
	
	
}