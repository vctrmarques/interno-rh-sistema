package com.rhlinkcon.repository.medico;

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

import com.rhlinkcon.filtro.MedicoFiltro;
import com.rhlinkcon.model.ColetivoEnum;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Medico;
import com.rhlinkcon.util.Utils;

public class MedicoRepositoryCustomImpl implements MedicoRepositoryCustom{

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Medico> filtro(MedicoFiltro filtro) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
		Root<Medico> root = criteriaQuery.from(Medico.class);

		List<Order> orderList = Utils.criarOrdenacao("nome", criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Medico> typedQuery = entityManager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}
	
	@Override
	public Page<Medico> filtro(MedicoFiltro filtro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
		Root<Medico> root = criteriaQuery.from(Medico.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(filtro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Medico> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(filtro));
	}
	
	private Long count(MedicoFiltro filtro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Medico> root = query.from(Medico.class);

		Predicate[] predicates = criarFiltro(filtro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] criarFiltro(MedicoFiltro filtro, CriteriaBuilder builder, Root<Medico> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filtro != null) {

			if (Utils.checkList(filtro.getFilialList())) {
				List<EmpresaFilial> empresaFiliallList = new ArrayList<EmpresaFilial>();
				
				for (Long empresaFilialId : filtro.getFilialList()) {
					EmpresaFilial empresaFilialFilter = new EmpresaFilial();
					empresaFilialFilter.setId(empresaFilialId);
					empresaFiliallList.add(empresaFilialFilter);
				}
				predicates.add(builder.and(root.get("filial").in(empresaFiliallList)));

			}
			if (Objects.nonNull(filtro.getStatusMedico())) {
				if (ColetivoEnum.getEnumByLabel(filtro.getStatusMedico()).equals(ColetivoEnum.NAO)) {
					predicates.add(builder.equal(root.get("status"), false));
				}else if(ColetivoEnum.getEnumByLabel(filtro.getStatusMedico()).equals(ColetivoEnum.SIM)) {
					predicates.add(builder.equal(root.get("status"), true));
				}
			}
			
			if (Objects.nonNull(filtro.getNome())) {
				predicates.add(
						builder.or(builder.like(root.get("nome"), "%" + filtro.getNome().toLowerCase() + "%"),
								builder.like(root.get("matricula").as(String.class),
										"%" + filtro.getNome() + "%")));
			}

		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
