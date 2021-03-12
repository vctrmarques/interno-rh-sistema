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

import com.rhlinkcon.model.FrequenciaCertidaoExSegurado;
import com.rhlinkcon.payload.certidaoExSegurado.FrequenciaCertidaoExSeguradoRequest;
import com.rhlinkcon.service.FrequenciaCertidaoExSeguradoService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class FrequenciaCertidaoExSeguradoController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";
	
	@Autowired
	private FrequenciaCertidaoExSeguradoService frequenciaCertidaoExSeguradoService;
	
	
	@PostMapping("/frequenciaCertidaoExSegurado")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> create(@Valid @RequestBody FrequenciaCertidaoExSeguradoRequest frequenciaCertidaoExSeguradoRequest) {
		//FrequenciaCertidaoExSegurado frequenciaCertidaoExSegurado = new FrequenciaCertidaoExSegurado( frequenciaCertidaoExSeguradoRequest);
		//frequenciaCertidaoExSeguradoService.create(frequenciaCertidaoExSegurado);
		return Utils.created(true, "Criado com sucesso.");
	}
	
	@PutMapping("/frequenciaCertidaoExSegurado")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> update(@Valid @RequestBody FrequenciaCertidaoExSeguradoRequest  frequenciaCertidaoExSeguradoRequest) {
		//FrequenciaCertidaoExSegurado frequenciaCertidaoExSegurado = new FrequenciaCertidaoExSegurado( frequenciaCertidaoExSeguradoRequest);
		//frequenciaCertidaoExSeguradoService.create(frequenciaCertidaoExSegurado);
		return Utils.created(true, "Editado com sucesso.");
	}
	
	@DeleteMapping("/frequenciaCertidaoExSegurado/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		frequenciaCertidaoExSeguradoService.delete(id);
		return Utils.deleted(true, "deletado com sucesso.");
	}
	
	
	
}