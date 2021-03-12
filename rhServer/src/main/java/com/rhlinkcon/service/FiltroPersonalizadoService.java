package com.rhlinkcon.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.AreaFormacao;
import com.rhlinkcon.model.Curso;
import com.rhlinkcon.model.GrauAcademico;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.areaFormacao.AreaFormacaoRequest;
import com.rhlinkcon.payload.curso.CursoRequest;
import com.rhlinkcon.payload.curso.CursoResponse;
import com.rhlinkcon.payload.generico.FiltroRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.grauAcademico.GrauAcademicoRequest;
import com.rhlinkcon.repository.AreaFormacaoRepository;
import com.rhlinkcon.repository.CursoRepository;
import com.rhlinkcon.repository.GrauAcademicoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class FiltroPersonalizadoService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private GrauAcademicoRepository grauAcademicoRepository;

	@Autowired
	private AreaFormacaoRepository areaFormacaoRepository;

	public PagedResponse<CursoResponse> getAllCursosByFilter(int page, int size, String order,
			FiltroRequest filtroRequest) {
		Utils.validatePageNumberAndSize(page, size);

		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Curso> cursos = null;

		if (!filtroRequest.getAreasFormacao().isEmpty() && filtroRequest.getGrausAcademicos().isEmpty()) {

			List<Long> areasFormacaoIds = new ArrayList<>();
			List<BigInteger> cursoIds = new ArrayList<>();
			List<Curso> cursoObj = new ArrayList<>();

			for (AreaFormacaoRequest areaFormacaoRequest : filtroRequest.getAreasFormacao()) {
				AreaFormacao areaFormacao = areaFormacaoRepository.findById(areaFormacaoRequest.getId()).orElseThrow(
						() -> new ResourceNotFoundException("AreaFormacao", "id", areaFormacaoRequest.getId()));

				areasFormacaoIds.add(areaFormacao.getId());
			}

			cursoIds = cursoRepository.findByListAreaFormacao(areasFormacaoIds);

			for (BigInteger id : cursoIds) {
				Curso curso = cursoRepository.findById(id.longValue())
						.orElseThrow(() -> new ResourceNotFoundException("Curso", "id", id.longValue()));
				cursoObj.add(curso);
			}

			cursos = new PageImpl<>(cursoObj, pageable, cursoObj.size());

		} else if (!filtroRequest.getGrausAcademicos().isEmpty() && filtroRequest.getAreasFormacao().isEmpty()) {

			List<Long> grausAcademicos = new ArrayList<>();
			List<BigInteger> cursoIds = new ArrayList<>();
			List<Curso> cursoObj = new ArrayList<>();
			

			for (GrauAcademicoRequest grauAcademicoRequest : filtroRequest.getGrausAcademicos()) {
				GrauAcademico grauAcademico = grauAcademicoRepository.findById(grauAcademicoRequest.getId())
						.orElseThrow(() -> new ResourceNotFoundException("CurGrauAcademicoso", "id", 
								grauAcademicoRequest.getId()));

				grausAcademicos.add(grauAcademico.getId());
			}

			cursoIds = cursoRepository.findByListGrauAcademico(grausAcademicos);
			
			for (BigInteger id : cursoIds) {
				Curso curso = cursoRepository.findById(id.longValue())
						.orElseThrow(() -> new ResourceNotFoundException("Curso", "id", id.longValue()));
				cursoObj.add(curso);
			}

			cursos = new PageImpl<>(cursoObj, pageable, cursoObj.size());

		} else if (!filtroRequest.getGrausAcademicos().isEmpty() && !filtroRequest.getAreasFormacao().isEmpty()) {

			List<Long> areasFormacao = new ArrayList<>();
			List<BigInteger> cursoIds = new ArrayList<>();
			List<Curso> cursoObj = new ArrayList<>();

			for (AreaFormacaoRequest areaFormacaoRequest : filtroRequest.getAreasFormacao()) {
				AreaFormacao areaFormacao = areaFormacaoRepository.findById(areaFormacaoRequest.getId()).orElseThrow(
						() -> new ResourceNotFoundException("AreaFormacao", "id", areaFormacaoRequest.getId()));

				areasFormacao.add(areaFormacao.getId());
			}

			List<Long> grausAcademicos = new ArrayList<>();

			for (GrauAcademicoRequest grauAcademicoRequest : filtroRequest.getGrausAcademicos()) {
				GrauAcademico grauAcademico = grauAcademicoRepository.findById(grauAcademicoRequest.getId())
						.orElseThrow(() -> new ResourceNotFoundException("CurGrauAcademicoso", "id",
								grauAcademicoRequest.getId()));

				grausAcademicos.add(grauAcademico.getId());
			}
			
			cursoIds = cursoRepository.findByListAreaFormacaoAndGrauAcademico(areasFormacao,grausAcademicos);

			for (BigInteger id : cursoIds) {
				Curso curso = cursoRepository.findById(id.longValue())
						.orElseThrow(() -> new ResourceNotFoundException("Curso", "id", id.longValue()));
				cursoObj.add(curso);
			}

			cursos = new PageImpl<>(cursoObj, pageable, cursoObj.size());
		}

		if (Objects.isNull(cursos)) {
			return new PagedResponse<>(Collections.emptyList(), 0, 0,
					0l, 0, false);
		}

		List<CursoResponse> cursoResponses = cursos.map(curso -> {
			return new CursoResponse(curso);
		}).getContent();
		return new PagedResponse<>(cursoResponses, cursos.getNumber(), cursos.getSize(), cursos.getTotalElements(),
				cursos.getTotalPages(), cursos.isLast());
	}
}
