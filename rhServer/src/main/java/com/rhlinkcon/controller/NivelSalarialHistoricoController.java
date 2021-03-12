package com.rhlinkcon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.referenciaSalarial.NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse;
import com.rhlinkcon.payload.referenciaSalarial.NivelSalarialHistoricoResponse;
import com.rhlinkcon.service.NivelSalarialHistoricoService;
import com.rhlinkcon.util.AppConstants;

@RestController
@RequestMapping("/api")
public class NivelSalarialHistoricoController {

	@Autowired
	private NivelSalarialHistoricoService nivelSalarialHistoricoService;

	@GetMapping("/nivelSalarialHistoricosAgrupadosPorNivelSalarial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse> getAllNivelSalarialHistoricosAgrupadosPorNivelSalarial(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nivelSalarialDescricao", defaultValue = AppConstants.DEFAULT_EMPTY) String nivelSalarialDescricao) {
		return nivelSalarialHistoricoService.getAllNivelSalarialHistoricosAgrupadosPorNivelSalarial(page, size, order,
				nivelSalarialDescricao);
	}

	@GetMapping("/nivelSalarialHistoricos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<NivelSalarialHistoricoResponse> getAllNivelSalarialHistoricos(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nivelSalarialId", defaultValue = AppConstants.DEFAULT_EMPTY) String nivelSalarialId,
			@RequestParam(value = "origemAjuste", defaultValue = AppConstants.DEFAULT_EMPTY) String origemAjuste) {
		return nivelSalarialHistoricoService.getAllNivelSalarialHistoricos(page, size, order, nivelSalarialId,
				origemAjuste);
	}

}
