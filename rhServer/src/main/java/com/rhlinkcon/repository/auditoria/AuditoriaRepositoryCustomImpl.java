package com.rhlinkcon.repository.auditoria;

import java.time.Instant;
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

import com.rhlinkcon.filtro.AuditoriaFiltroRequest;
import com.rhlinkcon.model.Auditoria;
import com.rhlinkcon.util.Utils;

public class AuditoriaRepositoryCustomImpl implements AuditoriaRepositoryCustom {
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Auditoria> filtro(String order, AuditoriaFiltroRequest filtro) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Auditoria> criteriaQuery = criteriaBuilder.createQuery(Auditoria.class);
		Root<Auditoria> root = criteriaQuery.from(Auditoria.class);

		if (Utils.checkStr(order)) {
			List<Order> orderList = Utils.criarOrdenacao(order, criteriaBuilder, root);
			criteriaQuery.orderBy(orderList);
		}

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Auditoria> typedQuery = entityManager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}

	@Override
	public Page<Auditoria> filtro(AuditoriaFiltroRequest filtro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Auditoria> criteriaQuery = criteriaBuilder.createQuery(Auditoria.class);
		Root<Auditoria> root = criteriaQuery.from(Auditoria.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Auditoria> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(filtro));
	}

	private Long count(AuditoriaFiltroRequest auditoriaFiltroRequest) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Auditoria> root = query.from(Auditoria.class);

		Predicate[] predicates = criarFiltro(auditoriaFiltroRequest, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(AuditoriaFiltroRequest filtro, CriteriaBuilder builder, Root<Auditoria> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filtro != null) {

			// Filtro de Data Inicial de Atualização
			if (Objects.nonNull(filtro.getPeriodoInicialStr())) {
				Instant dataInicialInst = Utils.stringDateToStartInstant(filtro.getPeriodoInicialStr());
				predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), dataInicialInst));
			}

			// Filtro de Data Inicial de Atualização
			if (Objects.nonNull(filtro.getPeriodoFinalStr())) {
				Instant dataFinalInst = Utils.stringDateToEndInstant(filtro.getPeriodoFinalStr());
				predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), dataFinalInst));
			}

			// Filtro de nome de usuario previamente feito.
			if (Utils.checkList(filtro.getUsuarioIdsByNome())) {
				predicates.add(builder.and(root.get("createdBy").in(filtro.getUsuarioIdsByNome())));
			}

			// Filtro entidade / tabelas
			if (Utils.checkList(filtro.getEntidadeNomeList())) {
				predicates.add(builder.and(root.get("entidade").in(filtro.getEntidadeNomeList())));
			}

		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
