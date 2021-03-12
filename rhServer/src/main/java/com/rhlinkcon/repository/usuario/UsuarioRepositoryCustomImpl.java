package com.rhlinkcon.repository.usuario;

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
import org.springframework.util.StringUtils;

import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.util.Utils;

public class UsuarioRepositoryCustomImpl implements UsuarioRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<Usuario> filtro(String nome, String cpf, String login, String idFilialFiltro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> root = criteriaQuery.from(Usuario.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);
		
		Predicate[] predicates = criarFiltro(nome, cpf, login, idFilialFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Usuario> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(nome, cpf, login, idFilialFiltro));
	}

	private Long count(String nome, String cpf, String login, String idFilial) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Usuario> root = query.from(Usuario.class);

		Predicate[] predicates = criarFiltro(nome, cpf, login, idFilial, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(String nome, String cpf, String login, String idFilialFiltro, CriteriaBuilder builder,
			Root<Usuario> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (nome != null && !StringUtils.isEmpty(nome)) {
			predicates.add(builder.like(builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
		}
		if (cpf != null && !StringUtils.isEmpty(cpf)) {
			predicates.add(builder.equal(root.get("cpf"), cpf));
		}
		if (login != null && !StringUtils.isEmpty(login)) {
			predicates.add(builder.equal(root.get("login"), login));
		}
		if (idFilialFiltro != null && !StringUtils.isEmpty(idFilialFiltro)) {
			predicates.add(builder.equal(root.get("empresaFilial"), Integer.parseInt(idFilialFiltro)));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
