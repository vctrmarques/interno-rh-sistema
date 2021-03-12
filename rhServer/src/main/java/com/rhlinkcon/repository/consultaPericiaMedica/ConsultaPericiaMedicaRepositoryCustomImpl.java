package com.rhlinkcon.repository.consultaPericiaMedica;

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

import com.rhlinkcon.filtro.ConsultaPericiaMedicaFiltro;
import com.rhlinkcon.model.ConsultaPericiaMedica;
import com.rhlinkcon.util.Utils;

public class ConsultaPericiaMedicaRepositoryCustomImpl implements ConsultaPericiaMedicaRepositoryCustom {
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<ConsultaPericiaMedica> filtro(ConsultaPericiaMedicaFiltro filtro) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ConsultaPericiaMedica> criteriaQuery = criteriaBuilder.createQuery(ConsultaPericiaMedica.class);
		Root<ConsultaPericiaMedica> root = criteriaQuery.from(ConsultaPericiaMedica.class);

		List<Order> orderList = Utils.criarOrdenacao("nome", criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<ConsultaPericiaMedica> typedQuery = entityManager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}
	
	@Override
	public Page<ConsultaPericiaMedica> filtro(ConsultaPericiaMedicaFiltro filtro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ConsultaPericiaMedica> criteriaQuery = criteriaBuilder.createQuery(ConsultaPericiaMedica.class);
		Root<ConsultaPericiaMedica> root = criteriaQuery.from(ConsultaPericiaMedica.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<ConsultaPericiaMedica> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(filtro));
	}
	
	private Long count(ConsultaPericiaMedicaFiltro filtro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<ConsultaPericiaMedica> root = query.from(ConsultaPericiaMedica.class);

		Predicate[] predicates = criarFiltro(filtro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] criarFiltro(ConsultaPericiaMedicaFiltro consultaPericiaMedicaFiltro, CriteriaBuilder builder, Root<ConsultaPericiaMedica> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (consultaPericiaMedicaFiltro != null) {

			if (Objects.nonNull(consultaPericiaMedicaFiltro.getSearch())) {
				predicates.add(builder.or(builder.like(
								root.get("pacientePericiaMedica").get("nome"), "%" + consultaPericiaMedicaFiltro.getSearch().toLowerCase() + "%"),
								builder.like(root.get("pacientePericiaMedica").get("cpf"), "%" + consultaPericiaMedicaFiltro.getSearch().toLowerCase() + "%")));
			}
			
			if (Objects.nonNull(consultaPericiaMedicaFiltro.getDataAgendamento())) {
				predicates.add(builder.equal(root.join("agendaMedicaData").get("data"), consultaPericiaMedicaFiltro.getDataAgendamento()));
			}
			
			if (Objects.nonNull(consultaPericiaMedicaFiltro.getMedicoId())) {
				predicates.add(builder.equal(root.get("medico"), consultaPericiaMedicaFiltro.getMedicoId()));
			}
			
			if (Objects.nonNull(consultaPericiaMedicaFiltro.getDtAgendamento())) {
				LocalDate dataAgendamento = LocalDate.parse(consultaPericiaMedicaFiltro.getDtAgendamento());
				predicates.add(builder.equal(root.join("agendaMedicaData").get("data"), dataAgendamento));
			}
			
			if (Objects.nonNull(consultaPericiaMedicaFiltro.getCompareceu())) {
				predicates.add(builder.equal(root.get("compareceu"), consultaPericiaMedicaFiltro.getCompareceu()));
			}
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
