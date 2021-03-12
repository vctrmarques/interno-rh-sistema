package com.rhlinkcon.repository.adiantamentoPagamento;

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

import com.rhlinkcon.filtro.AdiantamentoPagamentoFiltro;
import com.rhlinkcon.model.AdiantamentoPagamento;
import com.rhlinkcon.util.Utils;

public class AdiantamentoPagamentoRepositoryCustomImpl implements AdiantamentoPagamentoRepositoryCustom {
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Page<AdiantamentoPagamento> filtro(AdiantamentoPagamentoFiltro adiantamentoPagamentoFiltro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdiantamentoPagamento> criteriaQuery = criteriaBuilder.createQuery(AdiantamentoPagamento.class);
		Root<AdiantamentoPagamento> root = criteriaQuery.from(AdiantamentoPagamento.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);
		
		Predicate[] predicates = criarFiltro(adiantamentoPagamentoFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<AdiantamentoPagamento> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(adiantamentoPagamentoFiltro));
	}

    private Long count(AdiantamentoPagamentoFiltro adiantamentoPagamentoFiltro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<AdiantamentoPagamento> root = query.from(AdiantamentoPagamento.class);

		Predicate[] predicates = criarFiltro(adiantamentoPagamentoFiltro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(AdiantamentoPagamentoFiltro adiantamentoPagamentoFiltro, CriteriaBuilder builder, Root<AdiantamentoPagamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (Objects.nonNull(adiantamentoPagamentoFiltro.getNome())) {
			predicates.add(builder.like(builder.lower(root.join("funcionario").get("nome")), "%" + adiantamentoPagamentoFiltro.getNome().toLowerCase() + "%"));
		}
		
		if (Objects.nonNull(adiantamentoPagamentoFiltro.getMatricula())) {
			predicates.add(builder.equal(root.join("funcionario").get("matricula"), adiantamentoPagamentoFiltro.getMatricula()));
		}
		
		if(Objects.nonNull(adiantamentoPagamentoFiltro.getFilialId())) {
			predicates.add(builder.equal(root.join("empresaFilial").get("id"), adiantamentoPagamentoFiltro.getFilialId()));
		}
		
		if(Objects.nonNull(adiantamentoPagamentoFiltro.getLotacaoId())) {
			predicates.add(builder.equal(root.join("lotacao").get("id"), adiantamentoPagamentoFiltro.getLotacaoId()));
		}
		
		if(Objects.nonNull(adiantamentoPagamentoFiltro.getStatus())) {
			predicates.add(builder.equal(root.get("status"), adiantamentoPagamentoFiltro.getStatus()));
		}
		
		if(Objects.nonNull(adiantamentoPagamentoFiltro.getCompetencia())) {
			predicates.add(builder.equal(root.get("competencia"), adiantamentoPagamentoFiltro.getCompetencia()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
