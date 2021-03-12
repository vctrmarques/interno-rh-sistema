package com.rhlinkcon.repository.arrecadacaoAliquota;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.filtro.ArrecadacaoAliquotaFiltro;
import com.rhlinkcon.model.ArrecadacaoAliquotaEncargoEnum;
import com.rhlinkcon.model.ArrecadacaoAliquotaPeriodo;
import com.rhlinkcon.util.Utils;

public class ArrecadacaoAliquotaCustomImpl implements ArrecadacaoAliquotaCustom {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<ArrecadacaoAliquotaPeriodo> filtro(ArrecadacaoAliquotaFiltro filtro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ArrecadacaoAliquotaPeriodo> criteriaQuery = criteriaBuilder.createQuery(ArrecadacaoAliquotaPeriodo.class);
		Root<ArrecadacaoAliquotaPeriodo> root = criteriaQuery.from(ArrecadacaoAliquotaPeriodo.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<ArrecadacaoAliquotaPeriodo> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(filtro));
	}
	
	@Override
	public List<ArrecadacaoAliquotaPeriodo> filtro(ArrecadacaoAliquotaFiltro filtro) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ArrecadacaoAliquotaPeriodo> criteriaQuery = criteriaBuilder.createQuery(ArrecadacaoAliquotaPeriodo.class);
		Root<ArrecadacaoAliquotaPeriodo> root = criteriaQuery.from(ArrecadacaoAliquotaPeriodo.class);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<ArrecadacaoAliquotaPeriodo> typedQuery = entityManager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}
	
	private Long count(ArrecadacaoAliquotaFiltro filtro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<ArrecadacaoAliquotaPeriodo> root = query.from(ArrecadacaoAliquotaPeriodo.class);

		Predicate[] predicates = criarFiltro(filtro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(ArrecadacaoAliquotaFiltro filtro, CriteriaBuilder builder, Root<ArrecadacaoAliquotaPeriodo> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filtro != null) {

			if (Objects.nonNull(filtro.getInicioVigencia())) {
				LocalDate inicio = LocalDate.parse(filtro.getInicioVigencia());
				predicates.add(builder.greaterThanOrEqualTo(root.get("inicioVigencia"), inicio));
			}

			if (Objects.nonNull(filtro.getFimVigencia())) {
				LocalDate fim = LocalDate.parse(filtro.getFimVigencia());
				predicates.add(builder.lessThanOrEqualTo(root.get("fimVigencia"), fim));
			}
			
			if (Utils.checkList(filtro.getEncargos())) {
				Expression<Collection<ArrecadacaoAliquotaEncargoEnum>> encargos = root.join("encargos").get("aliquotaEncargo");
				for (String enumStr : filtro.getEncargos()) {
					predicates.add(builder.isMember(ArrecadacaoAliquotaEncargoEnum.getEnumByString(enumStr),
							encargos));
				}
			}

		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}


}
