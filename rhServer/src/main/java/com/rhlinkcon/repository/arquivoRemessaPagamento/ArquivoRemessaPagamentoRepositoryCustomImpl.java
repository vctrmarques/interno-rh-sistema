package com.rhlinkcon.repository.arquivoRemessaPagamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jrimum.utilix.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.ArquivoRemessaPagamentoFiltro;
import com.rhlinkcon.model.ArquivoRemessaPagamento;
import com.rhlinkcon.model.ArquivoRemessaPagamentoSituacaoEnum;
import com.rhlinkcon.util.Utils;

public class ArquivoRemessaPagamentoRepositoryCustomImpl implements ArquivoRemessaPagamentoRepositoryCustom {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Page<ArquivoRemessaPagamento> filtro(ArquivoRemessaPagamentoFiltro arquivoRemessaFiltro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ArquivoRemessaPagamento> criteriaQuery = criteriaBuilder.createQuery(ArquivoRemessaPagamento.class);
		Root<ArquivoRemessaPagamento> root = criteriaQuery.from(ArquivoRemessaPagamento.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(arquivoRemessaFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<ArquivoRemessaPagamento> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(arquivoRemessaFiltro));
	}
	
	private Long count(ArquivoRemessaPagamentoFiltro arquivoRemessaFiltro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<ArquivoRemessaPagamento> root = query.from(ArquivoRemessaPagamento.class);

		Predicate[] predicates = criarFiltro(arquivoRemessaFiltro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(ArquivoRemessaPagamentoFiltro arquivoRemessaFiltro, CriteriaBuilder builder,
			Root<ArquivoRemessaPagamento> root) {

		List<Predicate> predicates = new ArrayList<>();
		
		if (arquivoRemessaFiltro != null) {

			if (Utils.checkList(arquivoRemessaFiltro.getSituacoes())) {
				List<ArquivoRemessaPagamentoSituacaoEnum> statusList = new ArrayList<>();
				for (String statusStr : arquivoRemessaFiltro.getSituacoes())
					statusList.add(ArquivoRemessaPagamentoSituacaoEnum.getEnumByString(statusStr));
				predicates.add(builder.and(root.get("situacao").in(statusList)));
			}
			
			if(Objects.isNotNull(arquivoRemessaFiltro.getTipoProcessamento())) {
				predicates.add(builder.equal(root.join("folhaPagamento").get("tipoProcessamento").get("id"), arquivoRemessaFiltro.getTipoProcessamento()));
			}

		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
