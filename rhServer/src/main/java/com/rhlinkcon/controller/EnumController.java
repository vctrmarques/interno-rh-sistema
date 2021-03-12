package com.rhlinkcon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.generico.EnumDto;
import com.rhlinkcon.service.EnumService;
import com.rhlinkcon.util.AppConstants;

@RestController
@RequestMapping("/api")
public class EnumController {

	@Autowired
	private EnumService enumService;

	@GetMapping("/listaEnums")
	public List<EnumDto> getAllEnums(
			@RequestParam(value = "nomeEnum", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeEnum) {
		return enumService.getAllEnums(nomeEnum);
	}

	@GetMapping("/listaEnums/{nomeEnum}")
	public List<EnumDto> getAllEnumsByPathVariable(@PathVariable String nomeEnum) {
		return enumService.getAllEnums(nomeEnum);
	}

}
