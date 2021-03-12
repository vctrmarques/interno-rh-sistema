package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Curso;
import com.rhlinkcon.model.Habilidade;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.curso.CursoRequest;
import com.rhlinkcon.payload.curso.CursoResponse;
import com.rhlinkcon.payload.generico.FiltroRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.habilidade.HabilidadeResponse;
import com.rhlinkcon.repository.AreaFormacaoRepository;
import com.rhlinkcon.repository.CursoRepository;
import com.rhlinkcon.repository.GrauAcademicoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class CursoService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private GrauAcademicoRepository grauAcademicoRepository;

	@Autowired
	private AreaFormacaoRepository areaFormacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FiltroPersonalizadoService filtroPersonalizadoService;

	public List<CursoResponse> searchByNomeCursoOrCodigoMec(String search) {
		List<CursoResponse> itensResponses = new ArrayList<CursoResponse>();
		cursoRepository.findAll().forEach(item -> itensResponses.add(new CursoResponse(item)));
		return itensResponses;
	}

	public List<CursoResponse> listCurso() {
		List<Curso> cursos = cursoRepository.findAll();

		List<CursoResponse> listCursoResponse = new ArrayList<>();
		for (Curso curso : cursos) {
			listCursoResponse.add(new CursoResponse(curso));
		}
		return listCursoResponse;
	}

	public CursoResponse getCursoById(Long cursoId) {
		Curso curso = cursoRepository.findById(cursoId)
				.orElseThrow(() -> new ResourceNotFoundException("Curso", "id", cursoId));

		Usuario userCreated = usuarioRepository.findById(curso.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", curso.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(curso.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", curso.getUpdatedBy()));

		CursoResponse cursoResponse = new CursoResponse(curso, curso.getCreatedAt(), userCreated.getNome(),
				curso.getUpdatedAt(), userUpdated.getNome());

		return cursoResponse;
	}

	public PagedResponse<CursoResponse> getAllCursos(int page, int size, String order, String nomeCurso,
			String codigoMec) {
		Utils.validatePageNumberAndSize(page, size);

		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Curso> cursos = null;

		if (!codigoMec.isEmpty() && !nomeCurso.isEmpty()) {
			cursos = cursoRepository.findByNomeCursoOrCodigoMec(nomeCurso, codigoMec, pageable);
		} else if (Utils.checkStr(nomeCurso)) {
			cursos = cursoRepository.findByNomeCursoIgnoreCaseContaining(nomeCurso, pageable);
		} else if (Utils.checkStr(codigoMec)) {
			cursos = cursoRepository.findByCodigoMecIgnoreCaseContaining(codigoMec, pageable);
		} else {
			cursos = cursoRepository.findAll(pageable);
		}

		if (cursos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), cursos.getNumber(), cursos.getSize(),
					cursos.getTotalElements(), cursos.getTotalPages(), cursos.isLast());
		}

		List<CursoResponse> cursoResponses = cursos.map(curso -> {
			return new CursoResponse(curso);
		}).getContent();
		return new PagedResponse<>(cursoResponses, cursos.getNumber(), cursos.getSize(), cursos.getTotalElements(),
				cursos.getTotalPages(), cursos.isLast());

	}

	public void deleteCurso(Long id) {
		Curso curso = cursoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Curso", "id", id));
		cursoRepository.delete(curso);
	}

	public void createCurso(CursoRequest cursoRequest) {

		Curso curso = new Curso(cursoRequest);

		curso.setGrauAcademico(grauAcademicoRepository.findById(cursoRequest.getGrauAcademicoId())
				.orElseThrow(() -> new ResourceNotFoundException("Curso", "id", cursoRequest.getGrauAcademicoId())));

		curso.setAreaFormacao(areaFormacaoRepository.findById(cursoRequest.getAreaFormacaoId())
				.orElseThrow(() -> new ResourceNotFoundException("Curso", "id", cursoRequest.getAreaFormacaoId())));

		cursoRepository.save(curso);

	}

	public void updateCurso(CursoRequest cursoRequest) {

		Curso curso = cursoRepository.findById(cursoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Curso", "id", cursoRequest.getId()));

		curso.setNomeCurso(cursoRequest.getNomeCurso());
		curso.setCodigoMec(cursoRequest.getCodigoMec());
		curso.setCargaHoraria(cursoRequest.getCargaHoraria());
		
		curso.setGrauAcademico(grauAcademicoRepository.findById(cursoRequest.getGrauAcademicoId())
				.orElseThrow(() -> new ResourceNotFoundException("Curso", "id", cursoRequest.getGrauAcademicoId())));

		curso.setAreaFormacao(areaFormacaoRepository.findById(cursoRequest.getAreaFormacaoId())
				.orElseThrow(() -> new ResourceNotFoundException("Curso", "id", cursoRequest.getAreaFormacaoId())));

		cursoRepository.save(curso);

	}

	public PagedResponse<CursoResponse> getAllCursosByFilter(int page, int size, String order,
			FiltroRequest filtroRequest) {
		return filtroPersonalizadoService.getAllCursosByFilter(page, size, order, filtroRequest);
	}
	
	public List<CursoResponse> getAllCursos() {

		List<Curso> cursos = cursoRepository.findAll();
		List<CursoResponse> cursoResponse = new ArrayList<>();

		if (!cursos.isEmpty()) {
			for (Curso curso : cursos) {
				CursoResponse cursoResp = new CursoResponse(curso);

				cursoResponse.add(cursoResp);
			}
			return cursoResponse;
		}
		
		return null;
	}
}
