package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.RequisicaoPessoal;
import com.rhlinkcon.model.RequisicaoPessoalAnaliseEnum;
import com.rhlinkcon.model.RequisicaoPessoalCandidato;
import com.rhlinkcon.model.RequisicaoPessoalFuncao;
import com.rhlinkcon.model.SituacaoRequisicaoPessoalEnum;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalCandidatoRequest;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalCandidatoResponse;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalReponse;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalRequest;
import com.rhlinkcon.repository.requisicaoPessoal.RequisicaoPessoalCandidatoRepository;
import com.rhlinkcon.repository.requisicaoPessoal.RequisicaoPessoalFuncaoRepository;
import com.rhlinkcon.repository.requisicaoPessoal.RequisicaoPessoalRepository;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.util.Utils;

@Service
public class RequisicaoPessoalService {
	@Autowired
	private RequisicaoPessoalRepository requisicaoPessoalRepository;
	@Autowired
	private RequisicaoPessoalFuncaoRepository requisicaoPessoalFuncaoRepository;
	@Autowired
	private RequisicaoPessoalCandidatoRepository requisicaoPessoalCandidatoRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ExecutorService executorService;
	@Autowired
	private MailService mailService;
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private AnexoService anexoService;

	public void createRequisicao(RequisicaoPessoalRequest requisicaoPessoalRequest) {
		save(requisicaoPessoalRequest);
		Runnable notificarSolicitane = () -> {
			String emailSolicitante = funcionarioService.getEmailById(requisicaoPessoalRequest.getSolicitanteId());
			mailService.sendMail(emailSolicitante, "Requisição de Pessoal", "Requisição de Pessoal criada");
		};
		executorService.submit(notificarSolicitane);
	}

	public void updateRequisicao(RequisicaoPessoalRequest requisicaoPessoalRequest) {
		save(requisicaoPessoalRequest);
	}

	public void save(RequisicaoPessoalRequest requisicaoPessoalRequest) {
		RequisicaoPessoal requisicaoPessoal = new RequisicaoPessoal(requisicaoPessoalRequest);
		requisicaoPessoalRepository.save(requisicaoPessoal);
		List<RequisicaoPessoalFuncao> requisicaoPessoalFuncoes = new ArrayList<>();
		requisicaoPessoalRequest.getFuncoes().forEach(f -> {
			RequisicaoPessoalFuncao funcao = new RequisicaoPessoalFuncao(f);
			funcao.setRequisicaoPessoal(requisicaoPessoal);
			requisicaoPessoalFuncoes.add(funcao);
		});
		requisicaoPessoalFuncaoRepository.saveAll(requisicaoPessoalFuncoes);
	}
	// Método alterado para filtrar apenas as que o usuário solicitou, ou caso seja ADMIN poderá listar tudo. Porém é necessário fazer uma generalização, pois esse procedimento
	// será aplicado em todos as listagens de telas.	
	public Page<RequisicaoPessoalReponse> getAllRequisicoes(int page, int size, String order, String processoSituacao,
			String situacao, UserPrincipal currentUser) {
		Utils.validatePageNumberAndSize(page, size);
		Pageable pageable = Utils.getPageRequest(page, size, order);
		Page<RequisicaoPessoal> requisicoes = null;

		Boolean roleAdmin = currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

		if (roleAdmin) {
			if (Utils.checkNumber(processoSituacao))
				requisicoes = requisicaoPessoalRepository.findAllById(Long.valueOf(processoSituacao), pageable);
			else if (Utils.checkStr(situacao))
				requisicoes = requisicaoPessoalRepository
						.findAllBySituacao(SituacaoRequisicaoPessoalEnum.getEnumByString(situacao), pageable);
			else
				requisicoes = requisicaoPessoalRepository.findAll(pageable);
		}else {
			if (Utils.checkNumber(processoSituacao))
				requisicoes = requisicaoPessoalRepository.findAllByIdAndCreatedBy(Long.valueOf(processoSituacao), currentUser.getId(), pageable);
			else if (Utils.checkStr(situacao))
				requisicoes = requisicaoPessoalRepository
						.findAllBySituacaoAndCreatedBy(SituacaoRequisicaoPessoalEnum.getEnumByString(situacao), currentUser.getId(), pageable);
			else
				requisicoes = requisicaoPessoalRepository.findAllByCreatedBy(currentUser.getId(), pageable);
		}
		Page<RequisicaoPessoalReponse> response = requisicoes.map(rp -> new RequisicaoPessoalReponse(rp));
		return response;
	}

	public RequisicaoPessoalReponse getRequisicao(Long id) {
		RequisicaoPessoal requisicaoPessoal = requisicaoPessoalRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Requisicao", "id", id));
		RequisicaoPessoalReponse response = new RequisicaoPessoalReponse(requisicaoPessoal);
		response.setAlteradoPor(usuarioService.alteradoPor(requisicaoPessoal));
		response.setCriadoPor(usuarioService.criadoPor(requisicaoPessoal));
		return response;
	}

	public void deleteRequisicao(Long id) {
		RequisicaoPessoal requisicaoPessoal = requisicaoPessoalRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Requisicao", "id", id));
		requisicaoPessoalFuncaoRepository.deleteByRequisicaoPessoalId(requisicaoPessoal.getId());
		requisicaoPessoalRepository.delete(requisicaoPessoal);
	}

	public void alterarSituacao(Long id, String situacao) {
		requisicaoPessoalRepository
				.updateRequisicaoPessoalSituacao(SituacaoRequisicaoPessoalEnum.getEnumByString(situacao), id);
		Runnable notificarSolicitane = () -> {
			String emailSolicitante = requisicaoPessoalRepository.findEmailSolicitanteById(id);
			if (Strings.isNotEmpty(emailSolicitante))
				mailService.sendMail(emailSolicitante, "Requisição de Pessoal", "Requisição de Pessoal " + situacao);
		};
		executorService.submit(notificarSolicitane);
	}

	public Page<RequisicaoPessoalReponse> getAllRequisicoesGestao(int page, int size, String order) {
		Utils.validatePageNumberAndSize(page, size);
		Pageable pageable = Utils.getPageRequest(page, size, order);
		Page<RequisicaoPessoal> requisicoes = null;
		List<SituacaoRequisicaoPessoalEnum> situacoes = new ArrayList<>();
		situacoes.add(SituacaoRequisicaoPessoalEnum.EM_PROCESSO);
		situacoes.add(SituacaoRequisicaoPessoalEnum.APROVADO);
		situacoes.add(SituacaoRequisicaoPessoalEnum.REJEITADO);

		if (Utils.checkList(situacoes)) {
			requisicoes = requisicaoPessoalRepository.findAllBySituacaoIn(situacoes, pageable);
		}

		Page<RequisicaoPessoalReponse> response = requisicoes.map(rp -> new RequisicaoPessoalReponse(rp));
		return response;
	}

	public List<RequisicaoPessoalCandidatoResponse> getCandidatos(Long id) {
		List<RequisicaoPessoalCandidato> listaReqPessoalCandidato = requisicaoPessoalCandidatoRepository
				.findAllByRequisicaoPessoalId(id);

		List<RequisicaoPessoalCandidatoResponse> listaResponse = new ArrayList<RequisicaoPessoalCandidatoResponse>();
		for (RequisicaoPessoalCandidato reqCandidato : listaReqPessoalCandidato) {
			listaResponse.add(new RequisicaoPessoalCandidatoResponse(reqCandidato));
		}

		return listaResponse;
	}

	public void analiseCandidato(RequisicaoPessoalCandidatoRequest req, RequisicaoPessoalAnaliseEnum situacao) {
		Long idCandidato = req.getRequisicaoPessoalId();

		RequisicaoPessoalCandidato reqCandidato = requisicaoPessoalCandidatoRepository.findById(idCandidato)
				.orElseThrow(() -> new ResourceNotFoundException("Candidato", "id", idCandidato));

		reqCandidato.setSituacao(situacao);
		reqCandidato.setComentarioCurriculo(req.getComentarioCurriculo());

		requisicaoPessoalCandidatoRepository.save(reqCandidato);
	}

	public void concluirAnalise(RequisicaoPessoalCandidatoRequest req) {
		this.requisicaoPessoalRepository.updateRequisicaoPessoalSituacao(SituacaoRequisicaoPessoalEnum.CONCLUIDO,
				req.getId());

		RequisicaoPessoal requisicao = requisicaoPessoalRepository.findById(req.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Requisicao", "id", req.getId()));

		Runnable notificarSolicitane = () -> {
			String emailSolicitante = funcionarioService.getEmailById(requisicao.getCreatedBy());
			mailService.sendMail(emailSolicitante, "Requisição de Pessoal", "Análise de curriculos realizada");
		};

		executorService.submit(notificarSolicitane);
	}

	public void createRequisicaoCandidato(RequisicaoPessoalCandidatoRequest requisicaoPessoalCandidatoRequest) {
		RequisicaoPessoalCandidato rpc = new RequisicaoPessoalCandidato(requisicaoPessoalCandidatoRequest);
		requisicaoPessoalCandidatoRepository.save(rpc);
	}

	public void deleteCandidatoRequisicao(Long id) {
		RequisicaoPessoalCandidato rpc = requisicaoPessoalCandidatoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Candidato", "id", id));
		requisicaoPessoalCandidatoRepository.delete(rpc);
		anexoService.deleteAnexo(rpc.getAnexo().getId());
	}

}
