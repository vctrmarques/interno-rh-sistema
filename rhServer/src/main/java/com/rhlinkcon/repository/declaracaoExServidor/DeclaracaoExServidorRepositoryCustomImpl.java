package com.rhlinkcon.repository.declaracaoExServidor;

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

import com.rhlinkcon.filtro.DeclaracaoExServidorFiltro;
import com.rhlinkcon.model.DeclaracaoExServidor;
import com.rhlinkcon.model.StatusDeclaracaoExServidorEnum;
import com.rhlinkcon.util.Utils;

public class DeclaracaoExServidorRepositoryCustomImpl implements DeclaracaoExServidorRepositoryCustom {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Page<DeclaracaoExServidor> filtro(DeclaracaoExServidorFiltro declaracaoExServidorFiltro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DeclaracaoExServidor> criteriaQuery = criteriaBuilder.createQuery(DeclaracaoExServidor.class);
		Root<DeclaracaoExServidor> root = criteriaQuery.from(DeclaracaoExServidor.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(declaracaoExServidorFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<DeclaracaoExServidor> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(declaracaoExServidorFiltro));
	}
	
	private Long count(DeclaracaoExServidorFiltro declaracaoExServidorFiltro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<DeclaracaoExServidor> root = query.from(DeclaracaoExServidor.class);

		Predicate[] predicates = criarFiltro(declaracaoExServidorFiltro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] criarFiltro(DeclaracaoExServidorFiltro declaracaoExServidorFiltro, CriteriaBuilder builder,
			Root<DeclaracaoExServidor> root) {

		List<Predicate> predicates = new ArrayList<>();
		if (declaracaoExServidorFiltro != null) {

			//predicates.add(builder.notEqual(root.get("status"), StatusDeclaracaoExServidorEnum.EXCLUIDA));
			//predicates.add(builder.equal(root.get("arquivada"), false));

			// Filtro de status Enum
			if (Utils.checkList(declaracaoExServidorFiltro.getStatusList())) {
				List<StatusDeclaracaoExServidorEnum> statusList = new ArrayList<StatusDeclaracaoExServidorEnum>();
				for (String statusStr : declaracaoExServidorFiltro.getStatusList())
					statusList.add(StatusDeclaracaoExServidorEnum.getEnumByString(statusStr));
				predicates.add(builder.and(root.get("status").in(statusList)));
			}

			// Filtro de Funcionário
			if (Utils.checkStr(declaracaoExServidorFiltro.getDescricao())) {
				predicates.add(builder.or(
						builder.like(builder.lower(root.join("funcionario").get("nome")),
								"%" + declaracaoExServidorFiltro.getDescricao().toLowerCase() + "%"),
						builder.like(builder.lower(root.join("funcionario").get("cpf")),
								"%" + declaracaoExServidorFiltro.getDescricao().toLowerCase() + "%"),
						builder.like(builder.lower(root.join("funcionario").get("pisPasep")),
								"%" + declaracaoExServidorFiltro.getDescricao().toLowerCase() + "%"),
						builder.like(builder.lower(root.join("funcionario").get("matricula").as(String.class)),
								"%" + declaracaoExServidorFiltro.getDescricao().toLowerCase() + "%")));
			}

		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
