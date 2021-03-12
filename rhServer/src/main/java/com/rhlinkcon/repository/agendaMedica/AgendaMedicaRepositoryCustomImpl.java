package com.rhlinkcon.repository.agendaMedica;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import com.rhlinkcon.filtro.AgendaMedicaFiltro;
import com.rhlinkcon.model.AgendaMedica;
import com.rhlinkcon.model.EspecialidadeMedica;
import com.rhlinkcon.util.Utils;

public class AgendaMedicaRepositoryCustomImpl implements AgendaMedicaRepositoryCustom{

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<AgendaMedica> filtro(AgendaMedicaFiltro filtro) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AgendaMedica> criteriaQuery = criteriaBuilder.createQuery(AgendaMedica.class);
		Root<AgendaMedica> root = criteriaQuery.from(AgendaMedica.class);

		List<Order> orderList = Utils.criarOrdenacao("-medico.nome", criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<AgendaMedica> typedQuery = entityManager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}
	
	@Override
	public Page<AgendaMedica> filtro(AgendaMedicaFiltro filtro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AgendaMedica> criteriaQuery = criteriaBuilder.createQuery(AgendaMedica.class);
		Root<AgendaMedica> root = criteriaQuery.from(AgendaMedica.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<AgendaMedica> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(filtro));
	}
	
	private Long count(AgendaMedicaFiltro filtro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<AgendaMedica> root = query.from(AgendaMedica.class);

		Predicate[] predicates = criarFiltro(filtro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] criarFiltro(AgendaMedicaFiltro filtro, CriteriaBuilder builder, Root<AgendaMedica> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (filtro != null) {
			
			if (Objects.nonNull(filtro.getId())) {
				predicates.add(builder.equal(root.get("id"), filtro.getId()));
			}
			
			if (Objects.nonNull(filtro.getNome())) {
				predicates.add(builder.equal(root.join("medico").get("nome"), filtro.getNome()));
			}

			if (Objects.nonNull(filtro.getEspecialidadeMedicaList())) {
				List<EspecialidadeMedica> statusList = new ArrayList<EspecialidadeMedica>();
				for (Long id : filtro.getEspecialidadeMedicaList())
					statusList.add(new EspecialidadeMedica(id));
				predicates.add(builder.and(root.join("especialidadesMedicas").in(statusList)));
			}
			
			if (Objects.nonNull(filtro.getDataInicio())) {
				LocalDate inicio = filtro.getDataInicio();
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataInicio"), inicio));
			}

			if (Objects.nonNull(filtro.getDataFim())) {
				LocalDate fim = filtro.getDataFim();
				predicates.add(builder.lessThanOrEqualTo(root.get("dataFim"), fim));
			}
			
			if (Objects.nonNull(filtro.getDataAgenda())) {
				predicates.add(builder.greaterThanOrEqualTo(root.join("agendaMedicaData").get("data"), filtro.getDataAgenda()));
			}
			
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
