package com.rhlinkcon.repository.banco;

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

import com.rhlinkcon.filtro.BancoFiltro;
import com.rhlinkcon.model.Banco;
import com.rhlinkcon.util.Utils;

public class BancoRepositoryCustomImpl implements BancoRepositoryCustom {
	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<Banco> filtro(BancoFiltro banco, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Banco> criteriaQuery = criteriaBuilder.createQuery(Banco.class);
		Root<Banco> root = criteriaQuery.from(Banco.class);

		List<Order> orderList = new ArrayList<>();
		orderList.add(criteriaBuilder.desc(root.get("principal")));
		orderList.add(criteriaBuilder.asc(root.get("nome")));
		orderList.addAll(Utils.criarOrdenacao(pageable, criteriaBuilder, root));
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(banco, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Banco> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(banco));
	}

	private Long count(BancoFiltro banco) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Banco> root = query.from(Banco.class);

		Predicate[] predicates = criarFiltro(banco, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(BancoFiltro bancoFiltro, CriteriaBuilder builder, Root<Banco> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (Objects.nonNull(bancoFiltro.getNome())) {
			predicates.add(builder.or(builder.like(root.get("nome"), "%" + bancoFiltro.getNome().toLowerCase() + "%"),
					builder.like(root.get("codigo").as(String.class),
							"%" + bancoFiltro.getNome().toLowerCase() + "%")));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
