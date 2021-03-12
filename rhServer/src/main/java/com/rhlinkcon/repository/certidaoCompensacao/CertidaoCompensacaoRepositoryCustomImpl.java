package com.rhlinkcon.repository.certidaoCompensacao;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import com.rhlinkcon.filtro.CertidaoCompensacaoFiltro;
import com.rhlinkcon.model.CertidaoCompensacao;
import com.rhlinkcon.model.ClassificacaoCertidaoCompensacaoEnum;
import com.rhlinkcon.model.FundoCertidaoCompensacaoEnum;
import com.rhlinkcon.model.StatusCertidaoCompensacaoEnum;
import com.rhlinkcon.util.Utils;

public class CertidaoCompensacaoRepositoryCustomImpl implements CertidaoCompensacaoRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<CertidaoCompensacao> filtro(CertidaoCompensacaoFiltro certidaoCompensacaoFiltro) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CertidaoCompensacao> criteriaQuery = criteriaBuilder.createQuery(CertidaoCompensacao.class);
		Root<CertidaoCompensacao> root = criteriaQuery.from(CertidaoCompensacao.class);

//		List<Order> orderList = Utils.criarOrdenacao("", criteriaBuilder, root);
//		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(certidaoCompensacaoFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<CertidaoCompensacao> typedQuery = entityManager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}

	@Override
	public Page<CertidaoCompensacao> filtro(CertidaoCompensacaoFiltro certidaoCompensacaoFiltro, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CertidaoCompensacao> criteriaQuery = criteriaBuilder.createQuery(CertidaoCompensacao.class);
		Root<CertidaoCompensacao> root = criteriaQuery.from(CertidaoCompensacao.class);

		List<Order> orderList = Utils.criarOrdenacao(pageable, criteriaBuilder, root);
		criteriaQuery.orderBy(orderList);

		Predicate[] predicates = criarFiltro(certidaoCompensacaoFiltro, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<CertidaoCompensacao> typedQuery = entityManager.createQuery(criteriaQuery);
		Utils.adicionarPaginacao(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, count(certidaoCompensacaoFiltro));
	}

	private Long count(CertidaoCompensacaoFiltro certidaoCompensacaoFiltro) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<CertidaoCompensacao> root = query.from(CertidaoCompensacao.class);

		Predicate[] predicates = criarFiltro(certidaoCompensacaoFiltro, builder, root);
		query.where(predicates);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}

	private Predicate[] criarFiltro(CertidaoCompensacaoFiltro certidaoCompensacaoFiltro, CriteriaBuilder builder,
			Root<CertidaoCompensacao> root) {

		List<Predicate> predicates = new ArrayList<>();
		if (certidaoCompensacaoFiltro != null) {

			predicates.add(builder.notEqual(root.get("statusAtual"), StatusCertidaoCompensacaoEnum.EXCLUIDA));
			predicates.add(builder.equal(root.get("arquivada"), false));

			// Filtro de status Enum
			if (Utils.checkList(certidaoCompensacaoFiltro.getStatusList())) {
				List<StatusCertidaoCompensacaoEnum> statusList = new ArrayList<StatusCertidaoCompensacaoEnum>();
				for (String statusStr : certidaoCompensacaoFiltro.getStatusList())
					statusList.add(StatusCertidaoCompensacaoEnum.getEnumByString(statusStr));
				predicates.add(builder.and(root.get("statusAtual").in(statusList)));
			}

			// Filtro de fundo Enum
			if (Utils.checkList(certidaoCompensacaoFiltro.getFundoList())) {
				List<FundoCertidaoCompensacaoEnum> fundoList = new ArrayList<FundoCertidaoCompensacaoEnum>();
				for (String fundoStr : certidaoCompensacaoFiltro.getFundoList())
					fundoList.add(FundoCertidaoCompensacaoEnum.getEnumByString(fundoStr));
				predicates.add(builder.and(root.get("fundo").in(fundoList)));
			}

			// Filtro de classificações Enum
			if (Utils.checkList(certidaoCompensacaoFiltro.getClassificacaoList())) {
				Expression<Collection<ClassificacaoCertidaoCompensacaoEnum>> classificacoes = root
						.get("classificacoes");
				for (String fundoStr : certidaoCompensacaoFiltro.getClassificacaoList()) {
					predicates.add(builder.isMember(ClassificacaoCertidaoCompensacaoEnum.getEnumByString(fundoStr),
							classificacoes));
				}
			} else {
				if (Objects.nonNull(certidaoCompensacaoFiltro.getAposentComAposentSemCompens())
						&& certidaoCompensacaoFiltro.getAposentComAposentSemCompens()) {
					Expression<Collection<ClassificacaoCertidaoCompensacaoEnum>> classificacoes = root
							.get("classificacoes");
					predicates
							.add(builder.isMember(ClassificacaoCertidaoCompensacaoEnum.APOSENTADORIA, classificacoes));
					predicates.add(builder.isNotMember(
							ClassificacaoCertidaoCompensacaoEnum.APOSENTADORIA_SEM_COMPENSACAO, classificacoes));
				}
			}

			// Filtro de Data Inicial de Atualização
			if (Objects.nonNull(certidaoCompensacaoFiltro.getDataInicialStr())) {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date dataInicial = formato.parse(certidaoCompensacaoFiltro.getDataInicialStr());

					Instant dataInicialInst = dataInicial.toInstant().atZone(ZoneOffset.UTC).withHour(0).withMinute(0)
							.withSecond(0).toInstant();
					predicates.add(builder.greaterThanOrEqualTo(root.get("updatedAt"), dataInicialInst));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Filtro de Data Final de Atualização
			if (Objects.nonNull(certidaoCompensacaoFiltro.getDataFinalStr())) {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date dataFinal = formato.parse(certidaoCompensacaoFiltro.getDataFinalStr());

					Instant dataFinalInst = dataFinal.toInstant().atZone(ZoneOffset.UTC).withHour(23).withMinute(59)
							.withSecond(59).toInstant();
					predicates.add(builder.lessThanOrEqualTo(root.get("updatedAt"), dataFinalInst));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Filtro de Funcionário
			if (Utils.checkStr(certidaoCompensacaoFiltro.getDescricao())) {
				predicates.add(builder.or(
						builder.like(builder.lower(root.join("funcionario").get("nome")),
								"%" + certidaoCompensacaoFiltro.getDescricao().toLowerCase() + "%"),
						builder.like(builder.lower(root.join("funcionario").get("cpf")),
								"%" + certidaoCompensacaoFiltro.getDescricao().toLowerCase() + "%"),
						builder.like(builder.lower(root.join("funcionario").get("pisPasep")),
								"%" + certidaoCompensacaoFiltro.getDescricao().toLowerCase() + "%"),
						builder.like(builder.lower(root.join("funcionario").get("matricula").as(String.class)),
								"%" + certidaoCompensacaoFiltro.getDescricao().toLowerCase() + "%")));
			}

		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
