package com.rhlinkcon.repository.declaracaoAposentadoria;

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

import com.rhlinkcon.filtro.DeclaracaoAposentadoriaFiltro;
import com.rhlinkcon.model.DeclaracaoAposentadoria;
import com.rhlinkcon.util.Utils;

public class DeclaracaoAposentadoriaRepositoryCustomImpl implements DeclaracaoAposentadoriaRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<DeclaracaoAposentadoria> filtro(DeclaracaoAposentadoriaFiltro declaracaoAposentadoriaFiltro,
			Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DeclaracaoAposentadoria> criteriaQuery = criteriaBuilder
				.createQuery(DeclaracaoAposentadoria.class);
		Root<DeclaracaoAposentadoria> root = criteriaQuery.from(DeclaracaoAposentadoria.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(declaracaoAposentadoriaFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<DeclaracaoAposentadoria> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(declaracaoAposentadoriaFiltro));
	}

	private Long count(DeclaracaoAposentadoriaFiltro declaracaoAposentadoriaFiltro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<DeclaracaoAposentadoria> root = query.from(DeclaracaoAposentadoria.class);

		Predicate[] predicates = criarFiltro(declaracaoAposentadoriaFiltro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(DeclaracaoAposentadoriaFiltro declaracaoAposentadoriaFiltro,
			CriteriaBuilder builder, Root<DeclaracaoAposentadoria> root) {

		List<Predicate> predicates = new ArrayList<>();
		if (declaracaoAposentadoriaFiltro != null) {

			predicates.add(builder.equal(root.get("excluida"), false));
			predicates.add(builder.equal(root.get("arquivada"), false));

			if (Utils.checkStr(declaracaoAposentadoriaFiltro.getAno())) {
				predicates.add(builder.like(builder.lower(root.get("ano").as(String.class)),
						"%" + declaracaoAposentadoriaFiltro.getAno().toLowerCase() + "%"));
			}
			if (Utils.checkStr(declaracaoAposentadoriaFiltro.getNumero())) {
				predicates.add(builder.like(builder.lower(root.get("numero").as(String.class)),
						"%" + declaracaoAposentadoriaFiltro.getNumero().toLowerCase() + "%"));
			}
			if (Utils.checkStr(declaracaoAposentadoriaFiltro.getDescricao())) {

				predicates.add(builder.or(
						builder.like(builder.lower(root.join("funcionario").get("nome")),
								"%" + declaracaoAposentadoriaFiltro.getDescricao().toLowerCase() + "%"),
						builder.like(builder.lower(root.join("funcionario").get("cpf")),
								"%" + declaracaoAposentadoriaFiltro.getDescricao().toLowerCase() + "%"),
						builder.like(builder.lower(root.join("funcionario").get("pisPasep")),
								"%" + declaracaoAposentadoriaFiltro.getDescricao().toLowerCase() + "%"),
						builder.like(builder.lower(root.join("funcionario").get("matricula").as(String.class)),
								"%" + declaracaoAposentadoriaFiltro.getDescricao().toLowerCase() + "%")));
			}

		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
