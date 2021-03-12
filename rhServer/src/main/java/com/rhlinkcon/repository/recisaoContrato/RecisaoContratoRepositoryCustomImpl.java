package com.rhlinkcon.repository.recisaoContrato;

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

import com.rhlinkcon.filtro.RecisaoContratoFiltro;
import com.rhlinkcon.model.RecisaoContrato;
import com.rhlinkcon.util.Utils;

public class RecisaoContratoRepositoryCustomImpl implements RecisaoContratoRepositoryCustom {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Page<RecisaoContrato> filtro(RecisaoContratoFiltro recisaoContratoFiltro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecisaoContrato> criteriaQuery = criteriaBuilder.createQuery(RecisaoContrato.class);
		Root<RecisaoContrato> root = criteriaQuery.from(RecisaoContrato.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);
		
		Predicate[] predicates = criarFiltro(recisaoContratoFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<RecisaoContrato> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(recisaoContratoFiltro));
	}

    private Long count(RecisaoContratoFiltro recisaoContratoFiltro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<RecisaoContrato> root = query.from(RecisaoContrato.class);

		Predicate[] predicates = criarFiltro(recisaoContratoFiltro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(RecisaoContratoFiltro recisaoContratoFiltro, CriteriaBuilder builder, Root<RecisaoContrato> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (Objects.nonNull(recisaoContratoFiltro.getNome())) {
			predicates.add(builder.like(builder.lower(root.join("funcionario").get("nome")), "%" + recisaoContratoFiltro.getNome().toLowerCase() + "%"));
		}
		
		if (Objects.nonNull(recisaoContratoFiltro.getMatricula())) {
			predicates.add(builder.equal(root.join("funcionario").get("matricula"), recisaoContratoFiltro.getMatricula()));
		}
		
		if(Objects.nonNull(recisaoContratoFiltro.getRecisaoContratoEnum())) {
			predicates.add(builder.equal(root.get("status"), recisaoContratoFiltro.getRecisaoContratoEnum()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}
}