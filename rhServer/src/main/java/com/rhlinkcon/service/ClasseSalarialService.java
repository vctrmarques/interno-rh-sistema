package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.model.ClasseSalarial;
import com.rhlinkcon.model.GrupoSalarial;
import com.rhlinkcon.payload.classeSalarial.ClasseSalarialResponse;
import com.rhlinkcon.payload.grupoSalarial.GrupoSalarialResponse;
import com.rhlinkcon.repository.ClasseSalarialRepository;

@Service
public class ClasseSalarialService {
	
	@Autowired
	private ClasseSalarialRepository classeSalarialRepository;

	public List<ClasseSalarialResponse> getAllClasseSalarial() {

		List<ClasseSalarial> classesSalariais = classeSalarialRepository.findAll();
		List<ClasseSalarialResponse> classesSalariaisResponse = new ArrayList<>();

		if (!classesSalariais.isEmpty()) {
			for (ClasseSalarial classeSalarial : classesSalariais) {
				ClasseSalarialResponse classeSalarialResponse = new ClasseSalarialResponse(classeSalarial);
				classesSalariaisResponse.add(classeSalarialResponse);
			}
		}
		return classesSalariaisResponse;
	}
	
	public List<ClasseSalarialResponse> getAllClassesSalariais() {
		List<ClasseSalarial> classeSalarialList = classeSalarialRepository.findAll();

		List<ClasseSalarialResponse> classeSalarialResponseList = new ArrayList<>();
		for (ClasseSalarial classeSalarial : classeSalarialList) {
			classeSalarialResponseList.add(new ClasseSalarialResponse(classeSalarial));
		}
		return classeSalarialResponseList;
	}

	public List<ClasseSalarialResponse> getAllClassesSalariaisByGrupo(Long codigo) {
		List<ClasseSalarial> classeSalarialList = classeSalarialRepository.getListClassesSalariaisIdByGrupoSalarialId(codigo);

		List<ClasseSalarialResponse> classeSalarialResponseList = new ArrayList<>();
		for (ClasseSalarial classeSalarial : classeSalarialList) {
			classeSalarialResponseList.add(new ClasseSalarialResponse(classeSalarial));
		}
		return classeSalarialResponseList;
	}
}
