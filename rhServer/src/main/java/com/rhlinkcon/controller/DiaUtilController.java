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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.model.DiaUtil;
import com.rhlinkcon.payload.diaUtil.DiaUtilRequest;
import com.rhlinkcon.payload.diaUtil.DiaUtilResponse;
import com.rhlinkcon.repository.DiaUtilRepository;
import com.rhlinkcon.service.DiaUtilService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class DiaUtilController {

	@Autowired
	private DiaUtilRepository diaUtilRepository;

	@Autowired
	private DiaUtilService diaUtilService;

	@GetMapping("/diasUteis")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public DiaUtilResponse getAllDiasUteisPage(
			@RequestParam(value = "ano", defaultValue = AppConstants.DEFAULT_EMPTY) String ano) {
		return diaUtilService.getAllDiasUteis(ano);
	}

	@GetMapping("/diasUteisParaCadastrar")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<DiaUtil> getAllDiasUteisFromForm() {
		return diaUtilService.getDiasUteis();
	}

	@PostMapping("/diaUtil")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createDiaUtil( @RequestBody DiaUtilRequest diasUteisRequest) {
		diaUtilService.createDiaUtil(diasUteisRequest);

		return Utils.created(true, "Dia Util Criado com sucesso.");
	}

	@GetMapping("/diasUteisParaDetalhar")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<DiaUtil> getAllDiasUteisFromDetail(@RequestParam(value = "ano", defaultValue = AppConstants.DEFAULT_EMPTY) String ano, @RequestParam(value = "mes", defaultValue = AppConstants.DEFAULT_EMPTY) String mes){
		return diaUtilService.getDiasUteisPorMes(ano, mes);
	}

	 @PostMapping("/atualiarDiasUteisPorMes")
	 @PreAuthorize("hasAnyRole('ADMIN')")
	 public ResponseEntity<?> updateDiasUteisPorMes(@Valid @RequestBody DiaUtilRequest diasUteisRequest) {
	 
		 diaUtilService.deleteDiasUteisPorMes(diasUteisRequest.getAno(), diasUteisRequest.getMes());
		 diaUtilService.createDiaUtil(diasUteisRequest); 
	
	 return Utils.created(true, "Dias Uteis atualizado com sucesso.");
	 }

	@DeleteMapping("/diaUtil/{ano}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteDiaUtil(@PathVariable("ano") String ano) {
		diaUtilService.deleteDiaUtil(ano);

		return Utils.deleted(true, "Datas do ano deletado com sucesso.");
	}
	
	@DeleteMapping("/diasUteis/")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteDiaUteis() {
		diaUtilService.deleteAllDiasUteis();

		return Utils.deleted(true, "Datas do ano deletado com sucesso.");
	}		

}
