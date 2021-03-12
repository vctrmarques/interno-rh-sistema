package com.rhlinkcon.repository.agendaMedica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.AgendaMedicaDataFiltro;
import com.rhlinkcon.model.AgendaMedicaData;
import com.rhlinkcon.util.Utils;

public class AgendaMedicaDataRepositoryCustomImpl implements AgendaMedicaDataRepositoryCustom {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<AgendaMedicaData> filtro(AgendaMedicaDataFiltro filtro) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AgendaMedicaData> criteriaQuery = criteriaBuilder.createQuery(AgendaMedicaData.class);
		Root<AgendaMedicaData> root = criteriaQuery.from(AgendaMedicaData.class);

		List<Order> orderList = Utils.criarOrdenacao("id", criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<AgendaMedicaData> typedQuery = entityManager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}
	
	@Override
	public Page<AgendaMedicaData> filtro(AgendaMedicaDataFiltro filtro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AgendaMedicaData> criteriaQuery = criteriaBuilder.createQuery(AgendaMedicaData.class);
		Root<AgendaMedicaData> root = criteriaQuery.from(AgendaMedicaData.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<AgendaMedicaData> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(filtro));
	}
	
	private Long count(AgendaMedicaDataFiltro filtro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<AgendaMedicaData> root = query.from(AgendaMedicaData.class);

		Predicate[] predicates = criarFiltro(filtro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] criarFiltro(AgendaMedicaDataFiltro filtro, CriteriaBuilder builder, Root<AgendaMedicaData> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filtro != null) {
			
			if (Objects.nonNull(filtro.getId())) {
				predicates.add(builder.equal(root.get("id"), filtro.getId()));
			}
//			
//			if (Objects.nonNull(filtro.getNome())) {
//				predicates.add(builder.equal(root.join("medico").get("nome"), filtro.getNome()));
//			}
//
//			if (Objects.nonNull(filtro.getEspecialidadeMedicaList())) {
//				List<EspecialidadeMedica> statusList = new ArrayList<EspecialidadeMedica>();
//				for (Long id : filtro.getEspecialidadeMedicaList())
//					statusList.add(new EspecialidadeMedica(id));
//				predicates.add(builder.and(root.join("especialidadesMedicas").in(statusList)));
//			}
//			
//			if (Objects.nonNull(filtro.getDataInicio())) {
//				LocalDate inicio = filtro.getDataInicio();
//				predicates.add(builder.greaterThanOrEqualTo(root.get("dataInicio"), inicio));
//			}
//
			if (Objects.nonNull(filtro.getDataAgenda())) {
				LocalDate data = filtro.getDataAgenda();
				predicates.add(builder.equal(root.get("data"), data));
			}
			
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
