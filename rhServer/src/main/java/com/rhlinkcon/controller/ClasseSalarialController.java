package com.rhlinkcon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.classeSalarial.ClasseSalarialResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ClasseSalarialService;

@RestController
@RequestMapping("/api")
public class ClasseSalarialController {
	
	@Autowired
	public ClasseSalarialService classeSalarialService;
	
	@GetMapping("/classeSalarial/all")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ClasseSalarialResponse> getAllFaixasSalariais(@CurrentUser UserPrincipal currentUser) {
		return classeSalarialService.getAllClasseSalarial();
	}
	
	@GetMapping("/listaClassesSalariais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ClasseSalarialResponse> getAllClassesSalariais() {
		return classeSalarialService.getAllClassesSalariais();
	}
	
	@GetMapping("/listaClassesSalariais/grupo/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ClasseSalarialResponse> getAllClassesSalariaisByGrupo(@PathVariable Long id) {
		return classeSalarialService.getAllClassesSalariaisByGrupo(id);
	}

}
