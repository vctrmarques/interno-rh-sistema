package com.rhlinkcon.repository.pensionista;

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

import com.rhlinkcon.filtro.PensionistaFiltro;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.util.Utils;

public class PensionistaRepositoryCustomImpl implements PensionistaRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Pensionista> filtro(PensionistaFiltro pensionistaFiltro) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pensionista> criteriaQuery = criteriaBuilder.createQuery(Pensionista.class);
		Root<Pensionista> root = criteriaQuery.from(Pensionista.class);

		List<Order> orderList = Utils.criarOrdenacao("nome", criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(pensionistaFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Pensionista> typedQuery = entityManager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}

	@Override
	public Page<Pensionista> filtro(PensionistaFiltro pensionistaFiltro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pensionista> criteriaQuery = criteriaBuilder.createQuery(Pensionista.class);
		Root<Pensionista> root = criteriaQuery.from(Pensionista.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(pensionistaFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Pensionista> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(pensionistaFiltro));
	}

	private Long count(PensionistaFiltro pensionistaFiltro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Pensionista> root = query.from(Pensionista.class);

		Predicate[] predicates = criarFiltro(pensionistaFiltro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(PensionistaFiltro pensionistaFiltro, CriteriaBuilder builder,
			Root<Pensionista> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (Objects.nonNull(pensionistaFiltro.getSearchPensionista())) {
			predicates.add(builder.or(
					builder.like(root.get("nome"), "%" + pensionistaFiltro.getSearchPensionista().toLowerCase() + "%"),
					builder.like(root.get("cpf"), "%" + pensionistaFiltro.getSearchPensionista() + "%"),
					builder.like(root.get("matricula").as(String.class),
							"%" + pensionistaFiltro.getSearchPensionista().toLowerCase() + "%")));
		}

		if (Objects.nonNull(pensionistaFiltro.getSearchFuncionario())) {
			predicates.add(builder.or(
					builder.like(root.join("funcionario").get("nome"),
							"%" + pensionistaFiltro.getSearchFuncionario().toLowerCase() + "%"),
					builder.like(root.join("funcionario").get("cpf"),
							"%" + pensionistaFiltro.getSearchFuncionario().toLowerCase() + "%"),
					builder.like(root.join("funcionario").get("matricula").as(String.class),
							"%" + pensionistaFiltro.getSearchFuncionario().toLowerCase() + "%")));
		}

		if (pensionistaFiltro.getPensionistaVerbaAssociada() != null) {
			if (pensionistaFiltro.getPensionistaVerbaAssociada()) {
				predicates.add(builder.isNotEmpty(root.get("verbas")));
			} else {
				predicates.add(builder.isEmpty(root.get("verbas")));
			}
		}

		if (Objects.nonNull(pensionistaFiltro.getFilialId())) {
			predicates.add(builder.equal(root.get("pensionista.filial"), pensionistaFiltro.getFilialId()));
		}

		if (Objects.nonNull(pensionistaFiltro.getLotacaoId())) {
			predicates.add(builder.equal(root.get("funcionario.lotacao"), pensionistaFiltro.getLotacaoId()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
