package com.rhlinkcon.repository.arrecadacaoIndice;

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

import com.rhlinkcon.filtro.ArrecadacaoIndiceFiltro;
import com.rhlinkcon.model.ArrecadacaoIndice;
import com.rhlinkcon.model.PeriodicidadeMesEnum;
import com.rhlinkcon.util.Utils;

public class ArrecadacaoIndiceCustomImpl implements ArrecadacaoIndiceCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<ArrecadacaoIndice> filtro(ArrecadacaoIndiceFiltro filtro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ArrecadacaoIndice> criteriaQuery = criteriaBuilder.createQuery(ArrecadacaoIndice.class);
		Root<ArrecadacaoIndice> root = criteriaQuery.from(ArrecadacaoIndice.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<ArrecadacaoIndice> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(filtro));
	}

	@Override
	public List<ArrecadacaoIndice> filtro(ArrecadacaoIndiceFiltro filtro) {
		return null;
	}

	private Long count(ArrecadacaoIndiceFiltro filtro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<ArrecadacaoIndice> root = query.from(ArrecadacaoIndice.class);
		Predicate[] predicates = criarFiltro(filtro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(ArrecadacaoIndiceFiltro filtro, CriteriaBuilder builder,
			Root<ArrecadacaoIndice> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filtro != null) {
			if (Objects.nonNull(filtro.getIndice())) {
				predicates.add(builder.equal(root.get("indice"), filtro.getIndice()));
			}

			if (Objects.nonNull(filtro.getAno())) {
				predicates.add(builder.equal(root.join("anos").get("ano"), Long.parseLong(filtro.getAno())));
			}

			if (Utils.checkList(filtro.getPeriodicidades())) {
				List<PeriodicidadeMesEnum> statusList = new ArrayList<PeriodicidadeMesEnum>();
				for (String statusStr : filtro.getPeriodicidades())
					statusList.add(PeriodicidadeMesEnum.getEnumByString(statusStr));
				predicates.add(builder.and(root.join("anos").get("periodicidadeMes").in(statusList)));
			}

		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
