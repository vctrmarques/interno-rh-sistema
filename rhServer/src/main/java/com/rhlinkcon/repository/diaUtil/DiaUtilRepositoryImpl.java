package com.rhlinkcon.repository.diaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.rhlinkcon.payload.diaUtil.DtoDiaUtilConsulta;

public class DiaUtilRepositoryImpl implements DiaUtilRepositoryQuery{

	@PersistenceContext
	EntityManager manager;
	
	
	@Override
	public List<DtoDiaUtilConsulta> diasUteisDoAno(String ano) {
		String jpql = "select new  com.rhlinkcon.payload.diaUtil.DtoDiaUtilConsulta(count(d.diaDaSemana), d.ano, d.mes, d.diaDaSemana) from DiaUtil d where d.ano = :ano  group by d.ano, d.mes, d.diaDaSemana order by cast(d.mes as int)";
		TypedQuery<DtoDiaUtilConsulta> lista = manager.createQuery(jpql, DtoDiaUtilConsulta.class);
		lista.setParameter("ano", ano);
		return lista.getResultList();
		
	}

}
