package com.rhlinkcon.repository.regraAposentadoria;

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

import com.rhlinkcon.filtro.RegraAposentadoriaFiltro;
import com.rhlinkcon.model.ModalidadeAposentadoriaEnum;
import com.rhlinkcon.model.RegraAposentadoria;
import com.rhlinkcon.util.Utils;

public class RegraAposentadoriaRepositoryCustomImpl implements RegraAposentadoriaRepositoryCustom {
	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<RegraAposentadoria> filtro(RegraAposentadoriaFiltro regraAposentadoriaFiltro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RegraAposentadoria> criteriaQuery = criteriaBuilder.createQuery(RegraAposentadoria.class);
		Root<RegraAposentadoria> root = criteriaQuery.from(RegraAposentadoria.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);
		
		Predicate[] predicates = criarFiltro(regraAposentadoriaFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<RegraAposentadoria> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(regraAposentadoriaFiltro));
	}

	private Long count(RegraAposentadoriaFiltro regraAposentadoriaFiltro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<RegraAposentadoria> root = query.from(RegraAposentadoria.class);

		Predicate[] predicates = criarFiltro(regraAposentadoriaFiltro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(RegraAposentadoriaFiltro regraAposentadoriaFiltro, CriteriaBuilder builder,
			Root<RegraAposentadoria> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (regraAposentadoriaFiltro != null) {
			if (regraAposentadoriaFiltro.getModalidade() != null) {
				predicates.add(
						builder.equal(root.get("modalidadeAposentadoria"), regraAposentadoriaFiltro.getModalidade()));
			}
			if (regraAposentadoriaFiltro.getTipo() != null) {
				predicates.add(builder.equal(root.get("tipoAposentadoria"), regraAposentadoriaFiltro.getTipo()));
			}
			if (!regraAposentadoriaFiltro.getLeibase().isEmpty()) {
				predicates.add(builder.like(builder.lower(root.get("leiBase")),
						"%" + regraAposentadoriaFiltro.getLeibase() + "%"));
			}
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
