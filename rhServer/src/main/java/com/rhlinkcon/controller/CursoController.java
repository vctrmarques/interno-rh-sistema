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

import com.rhlinkcon.payload.curso.CursoRequest;
import com.rhlinkcon.payload.curso.CursoResponse;
import com.rhlinkcon.payload.generico.FiltroRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.habilidade.HabilidadeResponse;
import com.rhlinkcon.repository.CursoRepository;
import com.rhlinkcon.service.CursoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CursoService cursoService;

	@GetMapping("/cursos/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CursoResponse> searchByNomeCursoOrCodigoMec(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return cursoService.searchByNomeCursoOrCodigoMec(search);
	}

	@GetMapping("/cursos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CursoResponse> getCursos(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nomeCurso", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeCurso,
			@RequestParam(value = "codigoMec", defaultValue = AppConstants.DEFAULT_EMPTY) String codigoMec) {
		return cursoService.getAllCursos(page, size, order, nomeCurso, codigoMec);
	}

	@PostMapping("/filtrarCursos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CursoResponse> getCursosFiltrados(@Valid @RequestBody FiltroRequest filtroRequest) {
		return cursoService.getAllCursosByFilter(filtroRequest.getPage(), filtroRequest.getSize(),
				filtroRequest.getOrder(), filtroRequest);
	}

	@GetMapping("/curso/{cursoId}")
	public CursoResponse getCursoById(@PathVariable Long cursoId) {
		return cursoService.getCursoById(cursoId);
	}

	@PostMapping("/curso")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCurso(@Valid @RequestBody CursoRequest cursoRequest) {
		if (cursoRepository.existsByNomeCurso(cursoRequest.getNomeCurso())) {
			return Utils.badRequest(false, "O Nome do Curso já está em uso!");
		}

		if (cursoRepository.existsByCodigoMec(cursoRequest.getCodigoMec())) {
			return Utils.badRequest(false, "O Código do Mec já está em uso!");
		}

		cursoService.createCurso(cursoRequest);

		return Utils.created(true, "Curso criado com sucesso.");
	}

	@PutMapping("/curso")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCurso(@Valid @RequestBody CursoRequest cursoRequest) {
		if (cursoRepository.existsByNomeCursoAndIdNot(cursoRequest.getNomeCurso(), cursoRequest.getId())) {
			return Utils.badRequest(false, "O Nome do Curso já está em uso!");
		}

		if (cursoRepository.existsByCodigoMecAndIdNot(cursoRequest.getCodigoMec(), cursoRequest.getId())) {
			return Utils.badRequest(false, "O Códigodo Mec já está em uso!");
		}

		cursoService.updateCurso(cursoRequest);

		return Utils.created(true, "Curso atualizado com sucesso.");
	}

	@DeleteMapping("/curso/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCurso(@PathVariable("id") Long id) {
		cursoService.deleteCurso(id);

		return Utils.deleted(true, "Curso deletado com sucesso.");
	}

	@GetMapping("/listaCursos")
	public List<CursoResponse> getAllHabilidades() {
		return cursoService.getAllCursos();
	}
}
