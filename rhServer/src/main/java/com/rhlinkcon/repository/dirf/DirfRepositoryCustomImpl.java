package com.rhlinkcon.repository.dirf;

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

import com.rhlinkcon.filtro.DirfFiltro;
import com.rhlinkcon.model.Dirf;
import com.rhlinkcon.model.DirfTipoDeclaracaoEnum;
import com.rhlinkcon.util.Utils;

public class DirfRepositoryCustomImpl implements DirfRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<Dirf> filtro(DirfFiltro filtro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Dirf> criteriaQuery = criteriaBuilder.createQuery(Dirf.class);
		Root<Dirf> root = criteriaQuery.from(Dirf.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Dirf> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(filtro));
	}

	private Long count(DirfFiltro filtro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Dirf> root = query.from(Dirf.class);

		Predicate[] predicates = criarFiltro(filtro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(DirfFiltro filtro, CriteriaBuilder builder, Root<Dirf> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filtro != null) {

			if (Objects.nonNull(filtro.getAnoBase())) {
				predicates.add(builder.equal(root.get("anoBase"), filtro.getAnoBase()));
			}

			if (Objects.nonNull(filtro.getTipoDeclaracao())) {
				DirfTipoDeclaracaoEnum tipo = DirfTipoDeclaracaoEnum.getEnumByString(filtro.getTipoDeclaracao());
				predicates.add(builder.equal(root.get("tipoDeclaracao"), tipo));
			}

		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
}
