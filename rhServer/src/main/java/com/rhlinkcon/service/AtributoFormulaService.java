package com.rhlinkcon.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.ParametroGlobal;
import com.rhlinkcon.model.ParametroGlobalChaveEnum;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.atributoFormula.AtributoFormulaDto;
import com.rhlinkcon.util.MotorCalculoAttribute;

@Service
public class AtributoFormulaService {

	@Autowired
	private ParametroGlobalService parametroGlobalService;

	public List<AtributoFormulaDto> carregarAtributos() {
		List<AtributoFormulaDto> atributoFormulaDtoList = new ArrayList<>();

		AtributoFormulaDto atributoFormulaDto = null;

		Contracheque c = new Contracheque();
		List<Method> metodosContracheque = Arrays.asList(c.getClass().getDeclaredMethods());
		for (Method method : metodosContracheque) {
			if (method.isAnnotationPresent(MotorCalculoAttribute.class)) {
				MotorCalculoAttribute atributo = method.getAnnotation(MotorCalculoAttribute.class);

				atributoFormulaDto = AtributoFormulaDto.builder().label(atributo.label()).name(atributo.name()).build();
				atributoFormulaDtoList.add(atributoFormulaDto);
			}
		}

		Verba v = new Verba();
		List<Method> metodosVerba = Arrays.asList(v.getClass().getDeclaredMethods());
		for (Method method : metodosVerba) {
			if (method.isAnnotationPresent(MotorCalculoAttribute.class)) {
				MotorCalculoAttribute atributo = method.getAnnotation(MotorCalculoAttribute.class);

				atributoFormulaDto = AtributoFormulaDto.builder().label(atributo.label()).name(atributo.name()).build();
				atributoFormulaDtoList.add(atributoFormulaDto);
			}
		}

		// Busca de Parametros Globais usados nas Fórmulas.
		ParametroGlobal parametroGlobal = null;

		// Parametro Global de Teto Prefeitura
		parametroGlobal = parametroGlobalService.getParametroByChaveAndAtivo(ParametroGlobalChaveEnum.TETO_PREFEITURA,
				true);
		if (Objects.nonNull(parametroGlobal)) {
			atributoFormulaDto = AtributoFormulaDto.builder().label(parametroGlobal.getChave().getLabel())
					.name(parametroGlobal.getChave().name()).build();
			atributoFormulaDtoList.add(atributoFormulaDto);
		}

		// Parametro Global de Teto INSS
		parametroGlobal = parametroGlobalService.getParametroByChaveAndAtivo(ParametroGlobalChaveEnum.TETO_INSS, true);
		if (Objects.nonNull(parametroGlobal)) {
			atributoFormulaDto = AtributoFormulaDto.builder().label(parametroGlobal.getChave().getLabel())
					.name(parametroGlobal.getChave().name()).build();
			atributoFormulaDtoList.add(atributoFormulaDto);
		}

		// Parametro Global de Valor Dep IRRF
		parametroGlobal = parametroGlobalService
				.getParametroByChaveAndAtivo(ParametroGlobalChaveEnum.VALOR_DEPENDENTE_IRRF, true);
		if (Objects.nonNull(parametroGlobal)) {
			atributoFormulaDto = AtributoFormulaDto.builder().label(parametroGlobal.getChave().getLabel())
					.name(parametroGlobal.getChave().name()).build();
			atributoFormulaDtoList.add(atributoFormulaDto);
		}

		Collections.sort(atributoFormulaDtoList);
		return atributoFormulaDtoList;
	}
}
