package com.rhlinkcon.repository.agencia;

import java.util.ArrayList;
import java.util.List;

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

import com.rhlinkcon.filtro.AgenciaFiltro;
import com.rhlinkcon.model.Agencia;
import com.rhlinkcon.util.Utils;

public class AgenciaRepositoryCustomImpl implements AgenciaRepositoryCustom {
	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<Agencia> filtro(AgenciaFiltro agenciaFiltro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Agencia> criteriaQuery = criteriaBuilder.createQuery(Agencia.class);
		Root<Agencia> root = criteriaQuery.from(Agencia.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(agenciaFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Agencia> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(agenciaFiltro));
	}

	private Long count(AgenciaFiltro agenciaFiltro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Agencia> root = query.from(Agencia.class);

		Predicate[] predicates = criarFiltro(agenciaFiltro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(AgenciaFiltro agenciaFiltro, CriteriaBuilder builder, Root<Agencia> root) {

		List<Predicate> predicates = new ArrayList<>();
		if (agenciaFiltro != null) {
			if (agenciaFiltro.getBanco() != null) {
				predicates.add(builder.equal(root.get("banco"), agenciaFiltro.getBanco()));
			}
			if (!agenciaFiltro.getNome().isEmpty()) {
				predicates.add(builder.like(builder.lower(root.get("nome")), "%" + agenciaFiltro.getNome() + "%"));
			}
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
