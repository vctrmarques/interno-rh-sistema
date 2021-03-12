package com.rhlinkcon.repository.folhaPagamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.rhlinkcon.model.FolhaPagamento;

public class FolhaPagamentoRepositoryQueryImpl implements FolhaPagamentoRepositoryQuery {
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<FolhaPagamento> filterFolhaPagamento(Long filialId, Long lotacaoId, Long competenciaId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FolhaPagamento> criteriaQuery = criteriaBuilder.createQuery(FolhaPagamento.class);
		Root<FolhaPagamento> root = criteriaQuery.from(FolhaPagamento.class);

	
		Predicate[] predicates = criarFiltro(criteriaBuilder, root, filialId, lotacaoId, competenciaId);
		criteriaQuery.where(predicates);

		TypedQuery<FolhaPagamento> typedQuery = entityManager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}

	private Predicate[] criarFiltro(CriteriaBuilder builder, Root<FolhaPagamento> root, Long filialId, Long lotacaoId, Long competenciaId) {
		List<Predicate> predicates = new ArrayList<>();
		if (Objects.nonNull(filialId)) {
			predicates.add(builder.equal(root.get("filial"), filialId));
		}
//		if (Objects.nonNull(lotacaoId)) {
//			predicates.add(builder.in(root.get("lotacoes"), new ArrayList<>()));
//		}
		if (Objects.nonNull(competenciaId)) {
			predicates.add(builder.equal(root.get("folhaCompetencia"), competenciaId));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
