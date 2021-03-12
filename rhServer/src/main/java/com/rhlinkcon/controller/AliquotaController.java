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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.model.Aliquota;
import com.rhlinkcon.model.FaixaEnum;
import com.rhlinkcon.payload.aliquota.AliquotaResponse;
import com.rhlinkcon.payload.generico.EnumDto;
import com.rhlinkcon.service.AliquotaService;
import com.rhlinkcon.service.EnumService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class AliquotaController {
	@Autowired
	private AliquotaService aliquotaService;
	@Autowired
	private EnumService enumService;

	@GetMapping("/aliquotas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> searchAllByAno(Integer ano, String faixa) {
		return ResponseEntity.ok(aliquotaService.findAllByAno(faixa, ano));
	}

	@GetMapping("/aliquotas/anos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> searchAnos(@RequestParam(value = "faixa") String faixa) {
		return ResponseEntity.ok(aliquotaService.searchAnos(faixa));
	}
	
	@PostMapping("/aliquota")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> create(@Valid @RequestBody Aliquota aliquota) {
		aliquotaService.create(aliquota);
		return Utils.created(true, "Faixa criada com sucesso.");
	}

	@PutMapping("/aliquota")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> update(@Valid @RequestBody Aliquota aliquota) {
		aliquotaService.create(aliquota);	
		return Utils.created(true, "Alíquota atualizada com sucesso.");
	}
	
	@GetMapping("/aliquota/{faixa}")
	public List<AliquotaResponse> getAliquotasByFaixa(@PathVariable FaixaEnum faixa) {
		return aliquotaService.getAliquotasByFaixaAndAno(faixa, null);
	}
	
	@GetMapping("/aliquota/{faixa}/{ano}")
	public List<AliquotaResponse> getAliquotasByFaixaAndAno(@PathVariable FaixaEnum faixa, @PathVariable Integer ano) {
		return aliquotaService.getAliquotasByFaixaAndAno(faixa, ano);
	}
		
	@DeleteMapping("/aliquota/{aliquotaId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deteleAliquota(@PathVariable Long aliquotaId) {
		aliquotaService.delete(aliquotaId);
		return Utils.deleted(true, "Alíquota removida com sucesso.");
	}
	
}
