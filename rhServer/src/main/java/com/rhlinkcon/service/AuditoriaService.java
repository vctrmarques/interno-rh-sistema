package com.rhlinkcon.service;

import java.lang.annotation.Annotation;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityListeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.AuditoriaFiltroRequest;
import com.rhlinkcon.model.Auditoria;
import com.rhlinkcon.payload.auditoria.AuditLabelClassDto;
import com.rhlinkcon.payload.auditoria.AuditoriaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.repository.auditoria.AuditoriaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Service
public class AuditoriaService {

	@Autowired
	AuditoriaRepository auditoriaRepository;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	UsuarioRepository usuarioRepository;

	public AuditoriaResponse getById(Long auditoriaId) {
		Auditoria auditoria = auditoriaRepository.findById(auditoriaId)
				.orElseThrow(() -> new ResourceNotFoundException("Auditoria", "id", auditoriaId));

		AuditoriaResponse auditoriaResponse = new AuditoriaResponse(auditoria);
		auditoriaResponse.setCriadoPor(usuarioService.criadoPor(auditoria));
		return auditoriaResponse;
	}

	public List<AuditoriaResponse> get(String order, AuditoriaFiltroRequest filtro) {

		// Filtro por nome de usuário.
		if (Utils.checkStr(filtro.getNome())) {
			Optional<List<Long>> usuarioIdsOpt = usuarioRepository.findUsuarioIdsByNome(filtro.getNome());
			if (usuarioIdsOpt.isPresent())
				filtro.setUsuarioIdsByNome(usuarioIdsOpt.get());
		}

		List<Auditoria> auditorias = auditoriaRepository.filtro(order, filtro);

		List<AuditoriaResponse> auditoriaResponses = new ArrayList<AuditoriaResponse>();

		auditorias.forEach(auditoria -> {
			AuditoriaResponse auditoriaResponse = new AuditoriaResponse(auditoria);
			auditoriaResponse.setCriadoPor(usuarioService.criadoPor(auditoria));
			auditoriaResponses.add(auditoriaResponse);
		});
		return auditoriaResponses;

	}

	public PagedResponse<AuditoriaResponse> get(PagedRequest paginacao, AuditoriaFiltroRequest filtro) {

		Pageable pageable = Utils.generatePegeableGeneric(paginacao);

		// Filtro por nome de usuário.
		if (Utils.checkStr(filtro.getNome())) {
			Optional<List<Long>> usuarioIdsOpt = usuarioRepository.findUsuarioIdsByNome(filtro.getNome());
			if (usuarioIdsOpt.isPresent())
				filtro.setUsuarioIdsByNome(usuarioIdsOpt.get());
		}

		Page<Auditoria> auditorias = auditoriaRepository.filtro(filtro, pageable);

		if (auditorias.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), auditorias.getNumber(), auditorias.getSize(),
					auditorias.getTotalElements(), auditorias.getTotalPages(), auditorias.isLast());
		}

		List<AuditoriaResponse> auditoriaResponses = auditorias.map(auditoria -> {
			AuditoriaResponse auditoriaResponse = new AuditoriaResponse(auditoria);
			auditoriaResponse.setCriadoPor(usuarioService.criadoPor(auditoria));
			return auditoriaResponse;
		}).getContent();
		return new PagedResponse<>(auditoriaResponses, auditorias.getNumber(), auditorias.getSize(),
				auditorias.getTotalElements(), auditorias.getTotalPages(), auditorias.isLast());

	}

	public List<AuditLabelClassDto> entitySearch(String search) {
		List<AuditLabelClassDto> itensResponses = new ArrayList<AuditLabelClassDto>();

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(EntityListeners.class));
		scanner.addIncludeFilter(new AnnotationTypeFilter(AuditLabelClass.class));

		for (BeanDefinition bd : scanner.findCandidateComponents("com.rhlinkcon.model")) {

			try {
				Class<?> aClass = Class.forName(bd.getBeanClassName());

				for (Annotation annotation : aClass.getDeclaredAnnotations()) {
					if (annotation instanceof AuditLabelClass) {
						AuditLabelClass auditLabelClass = (AuditLabelClass) annotation;
						itensResponses.add(new AuditLabelClassDto(aClass.getName(), auditLabelClass.label()));
					}
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

		Collator coll = Collator.getInstance(new Locale("pt", "BR"));
		coll.setStrength(Collator.PRIMARY);
		List<AuditLabelClassDto> sorted = itensResponses.stream()
				.sorted(Comparator.comparing(AuditLabelClassDto::getLabel, coll)).collect(Collectors.toList());

		return sorted;

	}

	public void insert(Auditoria auditoria) {
		auditoriaRepository.save(auditoria);
	}

}
