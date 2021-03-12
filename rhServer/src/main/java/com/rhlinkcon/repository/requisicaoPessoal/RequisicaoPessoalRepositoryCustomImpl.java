package com.rhlinkcon.repository.requisicaoPessoal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.RequisicaoPessoal;
import com.rhlinkcon.model.SituacaoRequisicaoPessoalEnum;
import com.rhlinkcon.payload.relatorio.relatorioRecrutamentoESelecao.RelatorioRecrutamentoESelecaoRequest;
import com.rhlinkcon.payload.relatorio.relatorioRecrutamentoESelecao.RelatorioRecrutamentoESelecaoResponse;
import com.rhlinkcon.util.Utils;

@Repository
public class RequisicaoPessoalRepositoryCustomImpl implements RequisicaoPessoalRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public RelatorioRecrutamentoESelecaoResponse getAllForReport(RelatorioRecrutamentoESelecaoRequest request,
			boolean grafico) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RequisicaoPessoal> query = criteriaBuilder.createQuery(RequisicaoPessoal.class);

		Root<RequisicaoPessoal> root = query.from(RequisicaoPessoal.class);

		List<Predicate> predicates = criarFiltro(request, criteriaBuilder, root);
		query.where(predicates.toArray(new Predicate[predicates.size()]));

		RelatorioRecrutamentoESelecaoResponse resposta = new RelatorioRecrutamentoESelecaoResponse();
		resposta.setStatusTempoAtendimento("Status requisicao");
		
		resposta.setDataInicio(request.getDataInicio());
		resposta.setDataFim(request.getDataFim());
		resposta.setTipoSintetico(request.getTipoSintetico());
		
		if (grafico) {
			montrarRespostaParaGrafico(resposta, request);
		} else {
			montrarRespostaParaRelatorio(resposta, request);
		}

		return resposta;
	}
	
	private void montrarRespostaParaRelatorio(RelatorioRecrutamentoESelecaoResponse resposta,
			RelatorioRecrutamentoESelecaoRequest request) {
		
		if (request.getTipoAnalitico()) {
			resposta.setTotalConcluidosAntes(countConcluidoAntes(request));
			resposta.setTotalConcluidosNoLimite(countConcluidoNoLimite(request));
			resposta.setTotalConcluidosApos(countConcluidoDepois(request));

		} else if (request.getTipoSintetico()) {
			resposta.setTipoSintetico(true);
			
			resposta.setTotalConcluidosAntes(countConcluidoAntes(request));
			resposta.setTotalConcluidosNoLimite(countConcluidoNoLimite(request));
			resposta.setTotalConcluidosApos(countConcluidoDepois(request));

			resposta.setTotalProcessos(count(request));
			resposta.setTotalVagasAbertas(countVagasAbertas(request));
			
			resposta.setMediaDiasTempoAtendimento(countMediaDiasAtendimento(request));
			resposta.setTotalEfetivadosAposContratoExperiencia(countEfetivadosAposContratoExperiencia(request));

		} else if (!request.getTipoSintetico() && !request.getTipoAnalitico()) {
			if (request.getConcluidoAntes()) {
				resposta.setTotalConcluidosAntes(countConcluidoAntes(request));
			}

			if (request.getConcluidoNoLimite()) {
				resposta.setTotalConcluidosNoLimite(countConcluidoNoLimite(request));
			}

			if (request.getConcluidoDepois()) {
				resposta.setTotalConcluidosApos(countConcluidoDepois(request));
			}

			if (request.getVagasAbertas()) {
				resposta.setTotalVagasAbertas(countVagasAbertas(request));
			}

			if (request.getTodosProcessos()) {
				resposta.setTotalProcessos(count(request));
			}

			if (request.getTempoAtendimentoVaga()) {
				resposta.setMediaDiasTempoAtendimento(countMediaDiasAtendimento(request));
			}

			if (request.getEfetivosAposContratoExperiencia()) {
				resposta.setTotalEfetivadosAposContratoExperiencia(countEfetivadosAposContratoExperiencia(request));
			}
		}
	}

	private void montrarRespostaParaGrafico(RelatorioRecrutamentoESelecaoResponse resposta,
			RelatorioRecrutamentoESelecaoRequest request) {
		resposta.setTotalConcluidosAntes(countConcluidoAntes(request));
		resposta.setTotalConcluidosNoLimite(countConcluidoNoLimite(request));
		
		resposta.setTotalAlem10diasDataLimite(countAlem10DiasDataLimite(request));
		resposta.setTotalAte10diasDataLimite(countAte10DiasDataLimite(request));
	}

	private List<Predicate> criarFiltro(RelatorioRecrutamentoESelecaoRequest request, CriteriaBuilder builder,
			Root<RequisicaoPessoal> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (Utils.checkNotEmpty(request.getProcessoOuTermo())) {

			if (Utils.checkNumber(request.getProcessoOuTermo())) {
				predicates.add(builder.equal(root.get("id"), request.getProcessoOuTermo()));
			} else {
				predicates.add(builder.like(
						builder.lower(root.get("motivoSolicitacao")),
						"%" + request.getProcessoOuTermo().toLowerCase() + "%"
					)
				);
			}
		}

		predicates.add(builder.equal(root.get("situacao"), SituacaoRequisicaoPessoalEnum.CONCLUIDO));

		if (Objects.nonNull(request.getDataInicio())) {
			predicates.add(builder.greaterThan(root.get("createdAt"), request.getDataInicio()));
		}

		if (Objects.nonNull(request.getDataFim())) {
			predicates.add(builder.lessThan(root.get("dataConclusao"), request.getDataFim()));
		}

		return predicates;
	}

	private Long count(RelatorioRecrutamentoESelecaoRequest request) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root<RequisicaoPessoal> root = query.from(RequisicaoPessoal.class);

		List<Predicate> predicates = criarFiltro(request, builder, root);

		query.where(predicates.toArray(new Predicate[predicates.size()]));
		query.select(builder.count(root));

		return entityManager.createQuery(query).getSingleResult();
	}

	private Long countConcluidoAntes(RelatorioRecrutamentoESelecaoRequest request) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root<RequisicaoPessoal> root = query.from(RequisicaoPessoal.class);
		List<Predicate> predicates = criarFiltro(request, builder, root);

		predicates.add(builder.lessThan(root.get("dataConclusao"), root.get("dataLimite")));

		query.where(predicates.toArray(new Predicate[predicates.size()]));
		query.select(builder.count(root));

		return entityManager.createQuery(query).getSingleResult();
	}

	private Long countConcluidoNoLimite(RelatorioRecrutamentoESelecaoRequest request) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root<RequisicaoPessoal> root = query.from(RequisicaoPessoal.class);
		List<Predicate> predicates = criarFiltro(request, builder, root);

		predicates.add(builder.equal(root.get("dataConclusao"), root.get("dataLimite")));

		query.where(predicates.toArray(new Predicate[predicates.size()]));
		query.select(builder.count(root));

		return entityManager.createQuery(query).getSingleResult();
	}

	private Long countConcluidoDepois(RelatorioRecrutamentoESelecaoRequest request) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root<RequisicaoPessoal> root = query.from(RequisicaoPessoal.class);
		List<Predicate> predicates = criarFiltro(request, builder, root);

		predicates.add(builder.greaterThan(root.get("dataConclusao"), root.get("dataLimite")));

		query.where(predicates.toArray(new Predicate[predicates.size()]));
		query.select(builder.count(root));

		return entityManager.createQuery(query).getSingleResult();
	}

	private Long countVagasAbertas(RelatorioRecrutamentoESelecaoRequest request) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root<RequisicaoPessoal> root = query.from(RequisicaoPessoal.class);
		List<Predicate> predicates = criarFiltro(request, builder, root);

		predicates.add(builder.equal(root.get("situacao"), SituacaoRequisicaoPessoalEnum.ABERTO));

		query.where(predicates.toArray(new Predicate[predicates.size()]));
		query.select(builder.count(root));

		return entityManager.createQuery(query).getSingleResult();
	}

	private Long countEfetivadosAposContratoExperiencia(RelatorioRecrutamentoESelecaoRequest request) {
		// Ficou definido com pessoal de negócio que esse cálculo será esclarecido posterior mesmo.
		return Long.valueOf(0);
	}
	
	private Long countMediaDiasAtendimento(RelatorioRecrutamentoESelecaoRequest request) {
		// dias entre RequisicaoPessoal.dataConclusao e Funcionario.dataAdmissao
		// Segundo o pessoal de negócio, não há um tabela que ligue o funcionario a requisição que o aprovou. 
		return Long.valueOf(0);
	}

	private Long countAlem10DiasDataLimite(RelatorioRecrutamentoESelecaoRequest request) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(*) ");
		sql.append("	FROM RequisicaoPessoal");
		sql.append("		WHERE dateadd(day, 10, dataConclusao) > dataLimite");
		sql.append("			AND createdAt > :inicio ");
		sql.append("			AND dataConclusao < :fim ");
		sql.append("			AND situacao = :situacao ");

		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("inicio", request.getDataInicio());
		query.setParameter("fim", request.getDataFim());
		query.setParameter("situacao", SituacaoRequisicaoPessoalEnum.CONCLUIDO);

		return (Long) query.getSingleResult();
	}

	private Long countAte10DiasDataLimite(RelatorioRecrutamentoESelecaoRequest request) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(*) ");
		sql.append("	FROM RequisicaoPessoal ");
		sql.append("		WHERE dateadd(day, 10, dataConclusao) < dataLimite");
		sql.append("			AND createdAt > :inicio ");
		sql.append("			AND dataConclusao < :fim ");
		sql.append("			AND situacao = :situacao ");

		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("inicio", request.getDataInicio());
		query.setParameter("fim", request.getDataFim());
		query.setParameter("situacao", SituacaoRequisicaoPessoalEnum.CONCLUIDO);

		return (Long) query.getSingleResult();
	}

}
