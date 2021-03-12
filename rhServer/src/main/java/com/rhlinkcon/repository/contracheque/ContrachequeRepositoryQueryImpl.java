package com.rhlinkcon.repository.contracheque;

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

import com.rhlinkcon.filtro.ContrachequeFiltro;
import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.SituacaoProcessamentoEnum;
import com.rhlinkcon.util.Utils;

public class ContrachequeRepositoryQueryImpl implements ContrachequeRepositoryQuery {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<Contracheque> filtro(ContrachequeFiltro filtro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Contracheque> criteriaQuery = criteriaBuilder.createQuery(Contracheque.class);
		Root<Contracheque> root = criteriaQuery.from(Contracheque.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Contracheque> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(filtro));
	}

	private Long count(ContrachequeFiltro filtro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Contracheque> root = query.from(Contracheque.class);

		Predicate[] predicates = criarFiltro(filtro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	@Override
	public List<Contracheque> filtro(ContrachequeFiltro filtro) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Contracheque> criteriaQuery = criteriaBuilder.createQuery(Contracheque.class);
		Root<Contracheque> root = criteriaQuery.from(Contracheque.class);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Contracheque> typedQuery = entityManager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}

	private Predicate[] criarFiltro(ContrachequeFiltro filtro, CriteriaBuilder builder, Root<Contracheque> root) {

		List<Predicate> predicates = new ArrayList<>();
		if (filtro != null) {

			if (Objects.nonNull(filtro.getFolhaId()))
				predicates.add(builder.equal(root.get("folhaPagamento"), filtro.getFolhaId()));

			if (Utils.checkStr(filtro.getNome())) {
				predicates.add(builder.or(builder.like(root.get("nome"), "%" + filtro.getNome().toLowerCase() + "%"),
						builder.like(root.get("matricula").as(String.class),
								"%" + filtro.getNome().toLowerCase() + "%"),
						builder.like(root.get("cpf"), "%" + filtro.getNome().toLowerCase() + "%")));
			}

			if (Objects.nonNull(filtro.getFilial())) {
				predicates.add(builder.equal(root.get("orgaoPagador"), filtro.getFilial()));
			}

			if (Objects.nonNull(filtro.getLotacao())) {
				predicates.add(builder.equal(root.get("lotacao"), filtro.getLotacao()));
			}

			if (Objects.nonNull(filtro.getSituacaoFuncional())) {
				predicates.add(builder.equal(root.get("tipoSituacaoFuncional"), filtro.getSituacaoFuncional()));
			}

			if (Utils.checkStr(filtro.getSituacao())) {
				predicates
						.add(builder.equal(root.get("situacao"), SituacaoProcessamentoEnum.valueOf(filtro.getSituacao())));
			}

		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
