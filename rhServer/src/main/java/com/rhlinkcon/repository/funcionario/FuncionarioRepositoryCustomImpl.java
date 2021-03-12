package com.rhlinkcon.repository.funcionario;

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

import com.rhlinkcon.filtro.FuncionarioFiltro;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.MotivoAfastamento;
import com.rhlinkcon.model.SituacaoFuncional;
import com.rhlinkcon.util.Utils;

public class FuncionarioRepositoryCustomImpl implements FuncionarioRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Funcionario> filtro(FuncionarioFiltro funcionarioFiltro) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Funcionario> criteriaQuery = criteriaBuilder.createQuery(Funcionario.class);
		Root<Funcionario> root = criteriaQuery.from(Funcionario.class);

		List<Order> orderList = Utils.criarOrdenacao("nome", criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(funcionarioFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Funcionario> typedQuery = entityManager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}

	@Override
	public Page<Funcionario> filtro(FuncionarioFiltro funcionarioFiltro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Funcionario> criteriaQuery = criteriaBuilder.createQuery(Funcionario.class);
		Root<Funcionario> root = criteriaQuery.from(Funcionario.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(funcionarioFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Funcionario> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(funcionarioFiltro));
	}

//    @Override
//    public Page<Funcionario> getAllFuncionarioWithExperiencia(Integer matricula, String nome, Pageable pageable) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Funcionario> criteriaQuery = criteriaBuilder.createQuery(Funcionario.class);
//        Root<Funcionario> root = criteriaQuery.from(Funcionario.class);
//
//        List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
//        criteriaQuery.orderBy(orderList);
//
//        Predicate[] predicates = criarFiltro(matricula, nome, criteriaBuilder, root, true);
//        criteriaQuery.where(predicates);
//
//        TypedQuery<Funcionario> typedQuery = entityManager.createQuery(criteriaQuery);
//        Utils.adicionarPaginacao(typedQuery, pageable);
//
//        return new PageImpl<>(typedQuery.getResultList(), pageable, count(matricula, nome));
//    }

	private Long count(FuncionarioFiltro funcionarioFiltro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Funcionario> root = query.from(Funcionario.class);

		Predicate[] predicates = criarFiltro(funcionarioFiltro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(FuncionarioFiltro funcionarioFiltro, CriteriaBuilder builder,
			Root<Funcionario> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (Objects.nonNull(funcionarioFiltro.getSearch())) {
			predicates.add(
					builder.or(builder.like(root.get("nome"), "%" + funcionarioFiltro.getSearch().toLowerCase() + "%"),
							builder.like(root.get("matricula").as(String.class),
									"%" + funcionarioFiltro.getSearch() + "%"),
							builder.like(root.get("cpf"), "%" + funcionarioFiltro.getSearch() + "%")));
		}

		if (Utils.checkList(funcionarioFiltro.getTipoSituacaoFuncionalIds())) {
			List<SituacaoFuncional> tipoSituacaoFuncionalList = new ArrayList<SituacaoFuncional>();
			for (Long tipoSituacaoFuncionalId : funcionarioFiltro.getTipoSituacaoFuncionalIds()) {
				SituacaoFuncional situacaoFuncionalFilter = new SituacaoFuncional();
				situacaoFuncionalFilter.setId(tipoSituacaoFuncionalId);
				tipoSituacaoFuncionalList.add(situacaoFuncionalFilter);
			}
			predicates.add(builder.and(root.get("tipoSituacaoFuncional").in(tipoSituacaoFuncionalList)));

		}

		if (funcionarioFiltro.getFuncionarioVerbaAssociada() != null) {
			if (funcionarioFiltro.getFuncionarioVerbaAssociada())
				predicates.add(builder.isNotEmpty(root.get("funcionarioVerbas")));
			else
				predicates.add(builder.isEmpty(root.get("funcionarioVerbas")));
		}

		if (funcionarioFiltro.isComExperiencia()) {
			predicates.add(builder.isNotEmpty(root.get("experienciaProfissionais")));
		}

		if (Objects.nonNull(funcionarioFiltro.getFilialId())) {
			predicates.add(builder.equal(root.get("filial"), funcionarioFiltro.getFilialId()));
		}

		if (Objects.nonNull(funcionarioFiltro.getLotacaoId())) {
			predicates.add(builder.equal(root.get("lotacao"), funcionarioFiltro.getLotacaoId()));
		}

		if (Objects.nonNull(funcionarioFiltro.getDataAdmissao())) {
			predicates.add(builder.equal(root.get("dataAdmissao"), funcionarioFiltro.getDataAdmissao()));
		}
		
		if (Utils.checkList(funcionarioFiltro.getMotivoAfastamentoIds())) {
			//retorna os motivos de afastamento que entram na folha na tela de Percíia Médica
			predicates.add(builder.equal(root.join("motivoAfastamento").get("disponivelParaPericia"), true));

		}

//		if(funcionarioFiltro.getTipoSituacaoFuncional() != null) {
//			if(funcionarioFiltro.getTipoSituacaoFuncional().getId() != null && !funcionarioFiltro.getNome().equals("") ) {
//				predicates.add( 
//						builder.equal(root.get("tipoSituacaoFuncional"), funcionarioFiltro.getTipoSituacaoFuncional().getId()
//		));			
//			}
//		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
