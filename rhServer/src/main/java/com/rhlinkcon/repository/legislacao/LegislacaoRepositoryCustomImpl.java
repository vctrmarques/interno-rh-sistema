package com.rhlinkcon.repository.legislacao;

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

import com.rhlinkcon.filtro.LegislacaoFiltroRequest;
import com.rhlinkcon.model.legislacao.Legislacao;
import com.rhlinkcon.util.Utils;

public class LegislacaoRepositoryCustomImpl implements LegislacaoRepositoryCustom {
	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<Legislacao> filtro(LegislacaoFiltroRequest legislacaoFiltroRequest, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Legislacao> criteriaQuery = criteriaBuilder.createQuery(Legislacao.class);
		Root<Legislacao> root = criteriaQuery.from(Legislacao.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(legislacaoFiltroRequest, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Legislacao> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(legislacaoFiltroRequest));
	}

	private Long count(LegislacaoFiltroRequest legislacaoFiltroRequest) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Legislacao> root = query.from(Legislacao.class);

		Predicate[] predicates = criarFiltro(legislacaoFiltroRequest, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(LegislacaoFiltroRequest legislacaoFiltroRequest, CriteriaBuilder builder,
			Root<Legislacao> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (legislacaoFiltroRequest != null) {

			if (legislacaoFiltroRequest.getEnteFederadoId() != 0) {
				predicates.add(builder.equal(root.get("enteFederado"), legislacaoFiltroRequest.getEnteFederadoId()));
			}

			if (legislacaoFiltroRequest.getNormaId() != 0) {
				predicates.add(builder.equal(root.get("norma"), legislacaoFiltroRequest.getNormaId()));
			}

			if (legislacaoFiltroRequest.getDetalhamentoNormaId() != 0) {
				predicates.add(
						builder.equal(root.get("detalhamentoNorma"), legislacaoFiltroRequest.getDetalhamentoNormaId()));
			}

			if (Objects.nonNull(legislacaoFiltroRequest.getNumero()) && legislacaoFiltroRequest.getNumero() != 0) {
				predicates.add(builder.like(builder.lower(root.get("numeroNorma").as(String.class)),
						"%" + legislacaoFiltroRequest.getNumero().toString().toLowerCase() + "%"));
			}

			if (Objects.nonNull(legislacaoFiltroRequest.getAno()) && legislacaoFiltroRequest.getAno() != 0) {
				predicates.add(builder.like(builder.lower(root.get("anoNorma").as(String.class)),
						"%" + legislacaoFiltroRequest.getAno().toString().toLowerCase() + "%"));
			}

		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
