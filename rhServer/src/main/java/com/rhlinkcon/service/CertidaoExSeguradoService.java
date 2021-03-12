package com.rhlinkcon.service;

import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.exception.RHServiceException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.AbaAssinaturaCertidao;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.AssinaturaCertidaoExSegurado;
import com.rhlinkcon.model.CertidaoExSegurado;
import com.rhlinkcon.model.CertidaoExServidorCargo;
import com.rhlinkcon.model.CertidaoExServidorOrgaoLotacao;
import com.rhlinkcon.model.FrequenciaCertidaoExSegurado;
import com.rhlinkcon.model.FrequenciaCertidaoExServidorDetalhamento;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.HistoricoCertidaoExSegurado;
import com.rhlinkcon.model.HistoricoSituacaoFuncional;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.PeriodoCertidaoExSegurado;
import com.rhlinkcon.model.RelacaoRemuneracaoCertidaoExSegurado;
import com.rhlinkcon.model.StatusSituacaoCertidaoExSeguradoEnum;
import com.rhlinkcon.model.TempoEspecialCertidaoExSegurado;
import com.rhlinkcon.model.TipoHistoricoSituacaoFuncionalEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.certidaoExSegurado.AssinaturaCertidaoExSeguradoRequest;
import com.rhlinkcon.payload.certidaoExSegurado.CertidaoExSeguradoRequest;
import com.rhlinkcon.payload.certidaoExSegurado.CertidaoExSeguradoResponse;
import com.rhlinkcon.payload.certidaoExSegurado.CertidaoExServidorCargoRequest;
import com.rhlinkcon.payload.certidaoExSegurado.CertidaoExServidorOrgaoLotacaoRequest;
import com.rhlinkcon.payload.certidaoExSegurado.FrequenciaCertidaoExSeguradoRequest;
import com.rhlinkcon.payload.certidaoExSegurado.FrequenciaCertidaoExServidorDetalhamentoRequest;
import com.rhlinkcon.payload.certidaoExSegurado.HistoricoCertidaoExSeguradoResponse;
import com.rhlinkcon.payload.certidaoExSegurado.PeriodoCertidaoExSeguradoRequest;
import com.rhlinkcon.payload.certidaoExSegurado.RelacaoRemuneracaoCertidaoExSeguradoRequest;
import com.rhlinkcon.payload.certidaoExSegurado.TempoEspecialCertidaoExSeguradoRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.historicoSituacaoFuncional.HistoricoSituacaoFuncionalResponse;
import com.rhlinkcon.payload.usuario.UsuarioResponse;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.repository.AssinaturaCertidaoExSeguradoRepository;
import com.rhlinkcon.repository.CertidaoExSeguradoRepository;
import com.rhlinkcon.repository.CertidaoExServidorCargoRepository;
import com.rhlinkcon.repository.CertidaoExServidorOrgaoLotacaoRepository;
import com.rhlinkcon.repository.FrequenciaCertidaoExSeguradoRepository;
import com.rhlinkcon.repository.FrequenciaCertidaoExServidorDetalhamentoRepository;
import com.rhlinkcon.repository.HistoricoCertidaoExSeguradoRepository;
import com.rhlinkcon.repository.HistoricoSituacaoFuncionalRepository;
import com.rhlinkcon.repository.LotacaoRepository;
import com.rhlinkcon.repository.PeriodoCertidaoExSeguradoRepository;
import com.rhlinkcon.repository.RelacaoRemuneracaoCertidaoExSeguradoRepository;
import com.rhlinkcon.repository.TempoEspecialCertidaoExSeguradoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.ModelMapper;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class CertidaoExSeguradoService {

	@Autowired
	private CertidaoExSeguradoRepository certidaoExSeguradoRepository;

	@Autowired
	private AssinaturaCertidaoExSeguradoRepository assinaturaCertidaoExSeguradoRepository;

	@Autowired
	private PeriodoCertidaoExSeguradoRepository periodoCertidaoExSeguradoRepository;

	@Autowired
	private FrequenciaCertidaoExSeguradoRepository frequenciaCertidaoExSeguradoRepository;
	
	@Autowired
	private FrequenciaCertidaoExServidorDetalhamentoRepository frequenciaCertidaoExServidorDetalhamentoRepository;
	
	@Autowired
	private TempoEspecialCertidaoExSeguradoRepository tempoEspecialRepository;

	@Autowired
	private HistoricoCertidaoExSeguradoRepository historicoCertidaoExSeguradoRepository;

	@Autowired
	private CertidaoExServidorCargoRepository cargoRepository;
	
	@Autowired
	private LotacaoRepository lotacaoRepository;

	@Autowired
	private CertidaoExServidorOrgaoLotacaoRepository orgaoLotacaoRepository;

	@Autowired
	private RelacaoRemuneracaoCertidaoExSeguradoRepository relacaoRemuneracaoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private HistoricoSituacaoFuncionalRepository hsfRepository;

	@Autowired
	private AnexoRepository anexoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public PagedResponse<CertidaoExSeguradoResponse> getAll(int page, int size, String descricao, String status, String order) {
		Utils.validatePageNumberAndSize(page, size);
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, "createdAt");

		Page<CertidaoExSegurado> lista = certidaoExSeguradoRepository.getByFiltro(descricao, StatusSituacaoCertidaoExSeguradoEnum.getEnumByString(status), pageable);

		if (lista.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), lista.getNumber(), lista.getSize(), lista.getTotalElements(), lista.getTotalPages(), lista.isLast());
		}

		List<CertidaoExSeguradoResponse> listaResponse = lista.map(item -> {
			return new CertidaoExSeguradoResponse(item, Projecao.BASICA);
		}).getContent();

		return new PagedResponse<>(listaResponse, lista.getNumber(), lista.getSize(), lista.getTotalElements(), lista.getTotalPages(), lista.isLast());
	}

	@Transactional
	public Long create(CertidaoExSeguradoRequest request) {
		try {
			CertidaoExSegurado certidaoExSegurado = new CertidaoExSegurado();
			
			if(Objects.nonNull(request.getId())) {
				certidaoExSegurado = certidaoExSeguradoRepository.findById(request.getId())
						.orElseThrow(() -> new ResourceNotFoundException("CertidaoExSegurado", "id", request.getId()));
			} else {
				certidaoExSegurado = new CertidaoExSegurado(request);
			}
			
			// Anexos
			if (Utils.checkList(request.getAnexosRequest())) {
				certidaoExSegurado.setAnexos(anexoRepository.findAllByIdIn(request.getAnexosRequest()));
			}
			
			if(Objects.nonNull(certidaoExSegurado.getFuncionario())) {
				Optional<Funcionario> func = funcionarioRepository.findById(certidaoExSegurado.getFuncionario().getId());
				if(Objects.nonNull(func))
					certidaoExSegurado.setFuncionario(func.get());
			}
			if(Objects.nonNull(certidaoExSegurado.getLotacao())) {
				Optional<Lotacao> lot = lotacaoRepository.findById(certidaoExSegurado.getLotacao().getId());
				if(Objects.nonNull(lot))
					certidaoExSegurado.setLotacao(lot.get());
			} else {
				certidaoExSegurado.setLotacao(certidaoExSegurado.getFuncionario().getLotacao());
			}

			if(request.isRascunho()) {
				certidaoExSegurado.setStatusSituacaoCertidao(StatusSituacaoCertidaoExSeguradoEnum.RASCUNHO);
			} else {
				validate(certidaoExSegurado);
			}
			
			if(Objects.isNull(certidaoExSegurado.getAnoCertidao())) {
				certidaoExSegurado.setAnoCertidao(new GregorianCalendar().get(Calendar.YEAR));
			}

			if(Objects.isNull(certidaoExSegurado.getNumeroCertidao())) {
				certidaoExSegurado.setNumeroCertidao(gerarNumeracaoCertidaoExSegurado(certidaoExSegurado.getAnoCertidao()));
			}
			
			if(Objects.isNull(certidaoExSegurado.getFonteInformacao())) {
				certidaoExSegurado.setFonteInformacao("site");
			}
				
			certidaoExSegurado = certidaoExSeguradoRepository.save(certidaoExSegurado);

			if(!Objects.isNull(request.getCargos()) && !request.getCargos().isEmpty() ) {
				List<Long> idsExistentes = new ArrayList<>();
				if(Objects.nonNull(certidaoExSegurado.getCargos()) && !certidaoExSegurado.getCargos().isEmpty())
					certidaoExSegurado.getCargos().forEach(a -> idsExistentes.add(a.getId()));
				
				List<Long> idsInseridas = new ArrayList<>();
				
				for (CertidaoExServidorCargoRequest item : request.getCargos()) {
					CertidaoExServidorCargo a = new CertidaoExServidorCargo(item);
					a.setCertidaoExServidor(certidaoExSegurado);
					cargoRepository.save(a);
					idsInseridas.add(a.getId());
				}
					
				for (Long id : idsExistentes) {
					if (!idsInseridas.contains(id)) {
						cargoRepository.deleteById(id);
					}
				}
			}
				
			if(!Objects.isNull(request.getOrgaosLotacao()) && !request.getOrgaosLotacao().isEmpty() ) {
				List<Long> idsExistentes = new ArrayList<>();
				
				if(Objects.nonNull(certidaoExSegurado.getOrgaosLotacao()) && !certidaoExSegurado.getOrgaosLotacao().isEmpty())
					certidaoExSegurado.getOrgaosLotacao().forEach(a -> idsExistentes.add(a.getId()));
				List<Long> idsInseridas = new ArrayList<>();
				
				for (CertidaoExServidorOrgaoLotacaoRequest item : request.getOrgaosLotacao()) {
					CertidaoExServidorOrgaoLotacao a = new CertidaoExServidorOrgaoLotacao(item);
					a.setCertidaoExServidor(certidaoExSegurado);
					orgaoLotacaoRepository.save(a);
					idsInseridas.add(a.getId());
				}
					
				for (Long id : idsExistentes) {
					if (!idsInseridas.contains(id)) {
						orgaoLotacaoRepository.deleteById(id);
					}
				}
			}
			
			if (!Objects.isNull(request.getAssinaturas())) {
				
				List<Long> idsExistentes = new ArrayList<>();
				if(Objects.nonNull(certidaoExSegurado.getAssinaturas()) && !certidaoExSegurado.getAssinaturas().isEmpty())
					certidaoExSegurado.getAssinaturas().forEach(a -> idsExistentes.add(a.getId()));

				List<Long> idsInseridas = new ArrayList<>();
				for (AssinaturaCertidaoExSeguradoRequest item : request.getAssinaturas()) {
					AssinaturaCertidaoExSegurado a = new AssinaturaCertidaoExSegurado(item);
					
					Optional<Funcionario> func = funcionarioRepository.findById(a.getFuncionario().getId());
					if(Objects.nonNull(func))
						a.setFuncionario(func.get());
					
					a.setCertidaoExSegurado(certidaoExSegurado);
					
					assinaturaCertidaoExSeguradoRepository.save(a);
					
					idsInseridas.add(a.getId());
				}

				for (Long id : idsExistentes) {
					if (!idsInseridas.contains(id)) {
						assinaturaCertidaoExSeguradoRepository.deleteById(id);
					}
				}
				
			}
	
			if (!Objects.isNull(request.getPeriodos())) {
				List<Long> idsExistentes = new ArrayList<>();
				if(Objects.nonNull(certidaoExSegurado.getPeriodos()) && !certidaoExSegurado.getPeriodos().isEmpty())
					certidaoExSegurado.getPeriodos().forEach(a -> idsExistentes.add(a.getId()));
				List<Long> idsInseridas = new ArrayList<>();
				
				for (PeriodoCertidaoExSeguradoRequest item : request.getPeriodos()) {
					PeriodoCertidaoExSegurado p = new PeriodoCertidaoExSegurado(item);
					p.setCertidaoExSegurado(certidaoExSegurado);
					periodoCertidaoExSeguradoRepository.save(p);
					idsInseridas.add(p.getId());
				}
					
				for (Long id : idsExistentes) {
					if (!idsInseridas.contains(id)) {
						periodoCertidaoExSeguradoRepository.deleteById(id);
					}
				}
			}
	
			if (!Objects.isNull(request.getFrequencias())) {
				List<Long> idsExistentes = new ArrayList<>();
				if(Objects.nonNull(certidaoExSegurado.getFrequencias()) && !certidaoExSegurado.getFrequencias().isEmpty())
					certidaoExSegurado.getFrequencias().forEach(a -> idsExistentes.add(a.getId()));
				List<Long> idsInseridas = new ArrayList<>();
				
				for (FrequenciaCertidaoExSeguradoRequest item : request.getFrequencias()) {
					FrequenciaCertidaoExSegurado f = new FrequenciaCertidaoExSegurado(item);
					f.setCertidaoExSegurado(certidaoExSegurado);
					frequenciaCertidaoExSeguradoRepository.save(f);
					idsInseridas.add(f.getId());
				}
				
				for (Long id : idsExistentes) {
					if (!idsInseridas.contains(id)) {
						frequenciaCertidaoExSeguradoRepository.deleteById(id);
					}
				}
			}
			
			if (!Objects.isNull(request.getDetalhamentosFrequencia())) {
				List<Long> idsExistentes = new ArrayList<>();
				if(Objects.nonNull(certidaoExSegurado.getDetalhamentosFrequencia()) && !certidaoExSegurado.getDetalhamentosFrequencia().isEmpty())
					certidaoExSegurado.getDetalhamentosFrequencia().forEach(a -> idsExistentes.add(a.getId()));
				List<Long> idsInseridas = new ArrayList<>();
				
				for (FrequenciaCertidaoExServidorDetalhamentoRequest item : request.getDetalhamentosFrequencia()) {
					FrequenciaCertidaoExServidorDetalhamento d = new FrequenciaCertidaoExServidorDetalhamento(item);
					d.setCertidaoExSegurado(certidaoExSegurado);
					frequenciaCertidaoExServidorDetalhamentoRepository.save(d);
					idsInseridas.add(d.getId());
				}
				for (Long id : idsExistentes) {
					if (!idsInseridas.contains(id)) {
						frequenciaCertidaoExServidorDetalhamentoRepository.deleteById(id);
					}
				}
			}
			
			if (!Objects.isNull(request.getTempoEspecial())) {
				List<Long> idsExistentes = new ArrayList<>();
				if(Objects.nonNull(certidaoExSegurado.getTempoEspecial()) && !certidaoExSegurado.getTempoEspecial().isEmpty())
					certidaoExSegurado.getTempoEspecial().forEach(a -> idsExistentes.add(a.getId()));
				List<Long> idsInseridas = new ArrayList<>();
				
				for (TempoEspecialCertidaoExSeguradoRequest item : request.getTempoEspecial()) {
					TempoEspecialCertidaoExSegurado t = new TempoEspecialCertidaoExSegurado(item);
					t.setCertidaoExSegurado(certidaoExSegurado);
					tempoEspecialRepository.save(t);
					idsInseridas.add(t.getId());
				}
				for (Long id : idsExistentes) {
					if (!idsInseridas.contains(id)) {
						tempoEspecialRepository.deleteById(id);
					}
				}
			}
	
			if (!Objects.isNull(request.getRelacaoRemuneracoes())) {
				List<Long> idsExistentes = new ArrayList<Long>();
				if(Objects.nonNull(certidaoExSegurado.getRelacaoRemuneracoes()) && !certidaoExSegurado.getRelacaoRemuneracoes().isEmpty())
					certidaoExSegurado.getRelacaoRemuneracoes().forEach(a -> idsExistentes.add(a.getId()));
				
				for(Long id : idsExistentes) {
					relacaoRemuneracaoRepository.deleteById(id);
				}
				
				for (RelacaoRemuneracaoCertidaoExSeguradoRequest item : request.getRelacaoRemuneracoes()) {
					RelacaoRemuneracaoCertidaoExSegurado r = new RelacaoRemuneracaoCertidaoExSegurado(item);
					r.setCertidaoExSegurado(certidaoExSegurado);
					relacaoRemuneracaoRepository.save(r);
				}
			}
	
			if (!certidaoExSegurado.getStatusSituacaoCertidao().equals(StatusSituacaoCertidaoExSeguradoEnum.RASCUNHO)) {
				HistoricoCertidaoExSegurado hces = new HistoricoCertidaoExSegurado(certidaoExSegurado);
				historicoCertidaoExSeguradoRepository.save(hces);
			}
			
			return certidaoExSegurado.getId();

		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	
	/***
	 * Método que gera um novo número para a retificação na soma dos anteriores
	 * 
	 * @param numero
	 * @return
	 */
	private Long gerarNumeracaoRetificacaoCertidaoExSegurado(Long certidaoId) {
		Integer numeroRetificacao = historicoCertidaoExSeguradoRepository.maxNumeroRetificacaoByAnoAndNumero(certidaoId);
		if (Objects.isNull(numeroRetificacao))
			numeroRetificacao = 0;
		return Long.valueOf(numeroRetificacao + 1L);
	}

	/***
	 * Método que gera um novo número para a certidão baseado no ano de cadastro do
	 * mesmo.
	 * 
	 * @param numero
	 * @return
	 */
	private Integer gerarNumeracaoCertidaoExSegurado(Integer ano) {
		Integer numeroCertidao = certidaoExSeguradoRepository.maxNumeroByAno(ano);
		if(Objects.isNull(numeroCertidao))
			numeroCertidao = 0;
		return  numeroCertidao + 1;
	}

	private void validate(CertidaoExSegurado certidaoExSegurado) throws RHServiceException {
		validateSituacaoCertidao(certidaoExSegurado);
		validadeDetalhamentoFrequencia(certidaoExSegurado);
	}

	private boolean validadeDetalhamentoFrequencia(CertidaoExSegurado certidaoExSegurado) {
		int somaTempoLiquido = 0;
		if(Objects.nonNull(certidaoExSegurado.getFrequencias())) {
			for(FrequenciaCertidaoExSegurado f : certidaoExSegurado.getFrequencias()) {
				somaTempoLiquido += f.getTempoLiquido();
			}
		}
		
		int somaDetalhamentoTempo = 0;
		if(Objects.nonNull(certidaoExSegurado.getDetalhamentosFrequencia())) {
			for(FrequenciaCertidaoExServidorDetalhamento d : certidaoExSegurado.getDetalhamentosFrequencia()) {
				somaDetalhamentoTempo += d.getTempo();
			}
		}
		
		return (somaTempoLiquido == somaDetalhamentoTempo);
		
	}

	/***
	 * <p>
	 * No caso de existirem campos obrigatórios das abas de cadastro não tenham sido
	 * preenchidos o ato de salvamento deverá inserir os dados preenchidos em suas
	 * respectivas tabelas, porém o status da certidão deverá passar a ser:
	 * "Rascunho". Quando todos os dados básicos obrigatórios estiverem preenchidos
	 * o status deverá ser: "Aguardando impressão"
	 * </p>
	 * <p>
	 * Situações
	 * </p>
	 * <ol>
	 * <li>Certidao_exsegurado</li>
	 * <li>Certidao_assinaturas</li>
	 * <li>Certidao_exsegurado_periodos</li>
	 * <li>frequencia_certidao_exsegurado</li>
	 * <li>frequencia_deducoes_certidao_exsegurado</li>
	 * <li>frequência_licencas_certidao_exsegurado</li>
	 * <li>relacao_remuneracoes_certidao_exsegurado</li>
	 * </ol>
	 * <p>
	 * Condições
	 * </p>
	 * <ol>
	 * <li><b>Tabela certidao_assinaturas</b> - Deve possuir ao menos duas
	 * assinaturas cadastradas para cada aba (Atributo: "Aba" ENUM)</li>
	 * <li><b>Certidao_exsegurado_periodos</b> - Deve possuir ao menos uma entrada
	 * nessa tabela</li>
	 * <li><b>frequencia_certidao_exsegurado</b> - Deve possuir uma quantidade de
	 * entradas iguais a quantidade de anos de contribuição registrados, baseados na
	 * "data_admissao" do funcionário (tabela de funcionário) e a "data_exoneracao"
	 * (tabela "certidao_exsegurado"</li>
	 * </ol>
	 * 
	 * @param certidaoExSegurado
	 * 
	 */
	private void validateSituacaoCertidao(CertidaoExSegurado certidaoExSegurado) {
		if (!Objects.isNull(certidaoExSegurado)) {
			if (assinaturaValidaPorAba(certidaoExSegurado.getAssinaturas())) {
				if (validarPeriodo(certidaoExSegurado)) {
					if (validarFrequencia(certidaoExSegurado)) {
						if(validadeDetalhamentoFrequencia(certidaoExSegurado)) {
							certidaoExSegurado.setStatusSituacaoCertidao(StatusSituacaoCertidaoExSeguradoEnum.AGUARDANDO_IMPRESSAO);
						} else {
							throw new ServiceException("O tempo informado em Detalhamento frequência difere do informado em frequência");
						}
					}
				} else {
					throw new ServiceException("Deve haver pelo menos 1 registro de detalhamento do período de Contribuição.");
				}
			} else {
				throw new ServiceException("Deve haver pelo menos 2 ( duas ) assinaturas por abas.");
			}
		}

	}

	/***
	 * <p>
	 * Deve possuir uma quantidade de entradas iguais a quantidade de anos de
	 * contribuição registrados, baseados na "data_admissao" do funcionário (tabela
	 * de funcionário) e a "data_exoneracao" (tabela "certidao_exsegurado"
	 * </p>
	 * <p>
	 * <i>Por exemplo:</i>
	 * </p>
	 * <p>
	 * Admissão: 10/10/2000<br/>
	 * Exoneração: 30/12/2017<br/>
	 * Quantidade de registros esperada = 18
	 * </p>
	 * 
	 * @param certidaoExSegurado
	 * @return
	 */
	private boolean validarFrequencia(CertidaoExSegurado certidaoExSegurado) {
		Integer diferencaAnos = Period.between(certidaoExSegurado.getFuncionario().getDataAdmissao().atZone(ZoneId.systemDefault()).toLocalDate(),
				certidaoExSegurado.getDataExoneracao().atZone(ZoneId.systemDefault()).toLocalDate()).getYears();
		return !(Objects.isNull(certidaoExSegurado) || Objects.isNull(certidaoExSegurado.getFrequencias()) || certidaoExSegurado.getFrequencias().isEmpty()
				|| certidaoExSegurado.getFrequencias().size() < diferencaAnos);
	}


	/***
	 * <p>
	 * Deve possuir ao menos uma entrada nessa tabela
	 * </p>
	 * 
	 * @param certidaoExSegurado
	 * @return
	 */
	private boolean validarPeriodo(CertidaoExSegurado certidaoExSegurado) {
		return !(Objects.isNull(certidaoExSegurado.getPeriodos()) || certidaoExSegurado.getPeriodos().isEmpty());
	}

	/***
	 * <p>
	 * Deve possuir ao menos duas assinaturas cadastradas para cada aba (Atributo:
	 * "Aba" ENUM)
	 * </p>
	 * 
	 * @param assinaturaCertidaoExSegurados
	 * @return
	 */
	private boolean assinaturaValidaPorAba(Set<AssinaturaCertidaoExSegurado> assinaturaCertidaoExSegurados) {
		for (AbaAssinaturaCertidao abaAssinatura : AbaAssinaturaCertidao.values()) {
			int count = 0;
			if (!Objects.isNull(assinaturaCertidaoExSegurados)) {
				for (AssinaturaCertidaoExSegurado assinaturaCertidaoExSegurado : assinaturaCertidaoExSegurados) {
					if (assinaturaCertidaoExSegurado.getAbaAssinaturaCertidao().equals(abaAssinatura)) {
						count++;
					}
					if (count == 2) {
						break;
					}
				}
				if (count < 2) {
					return false;
				}
			} else {
				return false;
			}

		}
		return true;
	}

	/***
	 * <p>
	 * Deve possuir uma quantidade de entradas iguais a quantidade de anos de
	 * contribuição registrados, baseados na "data_admissao" do funcionário (tabela
	 * de funcionário) e a "data_exoneracao" (tabela "certidao_exsegurado"
	 * </p>
	 * <p>
	 * <i>Por exemplo:</i>
	 * </p>
	 * <p>
	 * Admissão: 10/10/2000<br/>
	 * Exoneração: 30/12/2017<br/>
	 * Quantidade de registros esperada = 18
	 * </p>
	 * 
	 * @param certidaoExSegurado
	 * @return
	 */
	private boolean validarRelacaoRemuneraçãoCertidao(CertidaoExSegurado certidaoExSegurado) {
		Integer diferencaAnos = Period.between(certidaoExSegurado.getFuncionario().getDataAdmissao().atZone(ZoneId.systemDefault()).toLocalDate(),
				certidaoExSegurado.getDataExoneracao().atZone(ZoneId.systemDefault()).toLocalDate()).getYears();
		return !(Objects.isNull(certidaoExSegurado) || Objects.isNull(certidaoExSegurado.getRelacaoRemuneracoes())
				|| certidaoExSegurado.getRelacaoRemuneracoes().isEmpty() || certidaoExSegurado.getRelacaoRemuneracoes().size() < diferencaAnos);

	}

	@Transactional
	public void delete(Long id) {
		
		CertidaoExSegurado certidaoExSegurado = certidaoExSeguradoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("CertidaoExSegurado", "id", id));
		cargoRepository.deleteByCertidaoExServidorId(certidaoExSegurado.getId());
		orgaoLotacaoRepository.deleteByCertidaoExServidorId(certidaoExSegurado.getId());
		periodoCertidaoExSeguradoRepository.deleteByCertidaoExSeguradoId(certidaoExSegurado.getId());
		frequenciaCertidaoExSeguradoRepository.deleteByCertidaoExSeguradoId(certidaoExSegurado.getId());
		frequenciaCertidaoExServidorDetalhamentoRepository.deleteByCertidaoExSeguradoId(certidaoExSegurado.getId());
		assinaturaCertidaoExSeguradoRepository.deleteByCertidaoExSeguradoId(certidaoExSegurado.getId());
		relacaoRemuneracaoRepository.deleteByCertidaoExSeguradoId(certidaoExSegurado.getId());
		
		if(Objects.nonNull(certidaoExSegurado.getAnexos())) {
			for (Anexo a : certidaoExSegurado.getAnexos()) {
				deleteAnexo(a.getId());
			}
		}
		if(Objects.nonNull(certidaoExSegurado.getStatusSituacaoCertidao()) && certidaoExSegurado.getStatusSituacaoCertidao().equals(StatusSituacaoCertidaoExSeguradoEnum.AGUARDANDO_IMPRESSAO)) {
			historicoCertidaoExSeguradoRepository.deleteByCertidaoExSeguradoId(certidaoExSegurado.getId());
		}
		
		certidaoExSeguradoRepository.delete(certidaoExSegurado);
	}

	public CertidaoExSeguradoResponse getCertidaoExSeguradoById(Long certidaoId) {
		Optional<CertidaoExSegurado> ces = certidaoExSeguradoRepository.findById(certidaoId);
		return ces.isPresent() ? new CertidaoExSeguradoResponse(ces.get(), Projecao.COMPLETA) : new CertidaoExSeguradoResponse();
	}

	@Transactional
	public CertidaoExSeguradoResponse getCertidaoExSeguradoByIdToRelatorio(Long certidaoId) {
		Optional<CertidaoExSegurado> ces = certidaoExSeguradoRepository.findById(certidaoId);
		if(ces.isPresent()) {
			CertidaoExSegurado certidao = ces.get();
			if(certidao.getStatusSituacaoCertidao().equals(StatusSituacaoCertidaoExSeguradoEnum.AGUARDANDO_IMPRESSAO)) {
				certidao.setStatusSituacaoCertidao(StatusSituacaoCertidaoExSeguradoEnum.ARQUIVADO);
				certidaoExSeguradoRepository.save(certidao);
			}
			return new CertidaoExSeguradoResponse(ces.get(), Projecao.RELATORIO_EXSEGURADO);
		} else {
			return new CertidaoExSeguradoResponse();
		}
	}

	public void deleteAnexo(Long id) {
		CertidaoExSegurado d = certidaoExSeguradoRepository.findAllByAnexosId(id);
		if (!Objects.isNull(d)) {
			d.getAnexos().removeIf(a -> a.getId().equals(id));
			certidaoExSeguradoRepository.save(d);
		}
		Anexo anexo = anexoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", id));
		anexoRepository.delete(anexo);

	}

	public HistoricoSituacaoFuncionalResponse getHistoricoSituacaoFuncionalByFuncionarioId(Long id) {
		HistoricoSituacaoFuncional hsf = hsfRepository.findTopByFuncionarioIdAndTipoHistoricoSituacaoFuncionalOrderByIdDesc(id, TipoHistoricoSituacaoFuncionalEnum.EXONERACAO);
		return new HistoricoSituacaoFuncionalResponse(hsf);
	}

	/***
	 * <p>
	 * Busca a certidão que será retificada, monta uma nova com todas as informações e arquiva a atual.
	 * </p>
	 * 
	 * @param id (certidão atual)
	 * @return id (certidão nova)
	 */
	
	@Transactional
	public Long retificar(Long id) {
		
		CertidaoExSegurado certidaoExSeguradoAtual = certidaoExSeguradoRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("CertidaoExSegurado", "id", id));
		
		certidaoExSeguradoAtual.setStatusSituacaoCertidao(StatusSituacaoCertidaoExSeguradoEnum.ARQUIVADO);
		
		certidaoExSeguradoRepository.save(certidaoExSeguradoAtual);
		
		HistoricoCertidaoExSegurado historicoAtual = new HistoricoCertidaoExSegurado(certidaoExSeguradoAtual);
		historicoCertidaoExSeguradoRepository.save(historicoAtual);
		
		CertidaoExSegurado certidaoExSeguradoNova = new CertidaoExSegurado();
		
		certidaoExSeguradoNova = loadDadosCertidao(certidaoExSeguradoAtual);
		
		if(Objects.nonNull(certidaoExSeguradoAtual.getCertidaoExSegurado())) {
			certidaoExSeguradoNova.setCertidaoExSegurado(certidaoExSeguradoAtual.getCertidaoExSegurado());
		} else {
			certidaoExSeguradoNova.setCertidaoExSegurado(certidaoExSeguradoAtual);
		}
		
		if(Objects.nonNull(certidaoExSeguradoAtual.getAnexos()) && !certidaoExSeguradoAtual.getAnexos().isEmpty()) {
			certidaoExSeguradoNova.setAnexos(certidaoExSeguradoAtual.getAnexos());
		}

		certidaoExSeguradoRepository.save(certidaoExSeguradoNova);
		
		
		if(Objects.nonNull(certidaoExSeguradoAtual.getCargos()) && !certidaoExSeguradoAtual.getCargos().isEmpty()) {
			for (CertidaoExServidorCargo item : certidaoExSeguradoAtual.getCargos()) {
				CertidaoExServidorCargo a = new CertidaoExServidorCargo();
				a.setCertidaoExServidor(certidaoExSeguradoNova);
				a.setDescricaoCargo(item.getDescricaoCargo());
				cargoRepository.save(a);
			}
		}
			
		if(Objects.nonNull(certidaoExSeguradoAtual.getOrgaosLotacao()) && !certidaoExSeguradoAtual.getOrgaosLotacao().isEmpty()) {
			for (CertidaoExServidorOrgaoLotacao item : certidaoExSeguradoAtual.getOrgaosLotacao()) {
				CertidaoExServidorOrgaoLotacao a = new CertidaoExServidorOrgaoLotacao();
				a.setCertidaoExServidor(certidaoExSeguradoNova);
				a.setDescricaoOrgaoLotacao(item.getDescricaoOrgaoLotacao());
				orgaoLotacaoRepository.save(a);
			}
		}
		
		if (Objects.nonNull(certidaoExSeguradoAtual.getAssinaturas()) && !certidaoExSeguradoAtual.getAssinaturas().isEmpty()) {
			for (AssinaturaCertidaoExSegurado item : certidaoExSeguradoAtual.getAssinaturas()) {
				AssinaturaCertidaoExSegurado a = new AssinaturaCertidaoExSegurado();
				a.setAbaAssinaturaCertidao(item.getAbaAssinaturaCertidao());
				a.setFuncaoAssinaturaCertidao(item.getFuncaoAssinaturaCertidao());
				a.setFuncionario(item.getFuncionario());
				a.setCertidaoExSegurado(certidaoExSeguradoNova);
				assinaturaCertidaoExSeguradoRepository.save(a);
			}
		}

		if (Objects.nonNull(certidaoExSeguradoAtual.getPeriodos()) && !certidaoExSeguradoAtual.getPeriodos().isEmpty()) {
			for (PeriodoCertidaoExSegurado item : certidaoExSeguradoAtual.getPeriodos()) {
				PeriodoCertidaoExSegurado p = new PeriodoCertidaoExSegurado();
				p.setCertidaoExSegurado(certidaoExSeguradoNova);
				p.setAproveitamento(item.getAproveitamento());
				p.setPeriodoInicio(item.getPeriodoInicio());
				p.setPeriodoFinal(item.getPeriodoFinal());
				periodoCertidaoExSeguradoRepository.save(p);
			}

		}

		if (Objects.nonNull(certidaoExSeguradoAtual.getFrequencias()) && !certidaoExSeguradoAtual.getFrequencias().isEmpty()) {
			for (FrequenciaCertidaoExSegurado item : certidaoExSeguradoAtual.getFrequencias()) {
				FrequenciaCertidaoExSegurado f = new FrequenciaCertidaoExSegurado();
				f.setAno(item.getAno());
				f.setDisponibilidade(item.getDisponibilidade());
				f.setFaltas(item.getFaltas());
				f.setLicencas(item.getLicencas());
				f.setLicencasSemVenc(item.getLicencasSemVenc());
				f.setOutros(item.getOutros());
				f.setSuspensoes(item.getSuspensoes());
				f.setTempoBruto(item.getTempoBruto());
				f.setTempoLiquido(item.getTempoLiquido());
				f.setCertidaoExSegurado(certidaoExSeguradoNova);
				frequenciaCertidaoExSeguradoRepository.save(f);
			}
		}
		
		if (Objects.nonNull(certidaoExSeguradoAtual.getDetalhamentosFrequencia()) && !certidaoExSeguradoAtual.getDetalhamentosFrequencia().isEmpty()) {
			for (FrequenciaCertidaoExServidorDetalhamento item : certidaoExSeguradoAtual.getDetalhamentosFrequencia()) {
				FrequenciaCertidaoExServidorDetalhamento d = new FrequenciaCertidaoExServidorDetalhamento();
				d.setDescricao(item.getDescricao());
				d.setPeriodoFinal(item.getPeriodoFinal());
				d.setPeriodoInicio(item.getPeriodoInicio());
				d.setTempo(item.getTempo());
				d.setTipo(item.getTipo());
				d.setCertidaoExSegurado(certidaoExSeguradoNova);
				frequenciaCertidaoExServidorDetalhamentoRepository.save(d);
				
			}
		}
		
		if(Objects.nonNull(certidaoExSeguradoAtual.getTempoEspecial()) && !certidaoExSeguradoAtual.getTempoEspecial().isEmpty()) {
			for(TempoEspecialCertidaoExSegurado item : certidaoExSeguradoAtual.getTempoEspecial()) {
				TempoEspecialCertidaoExSegurado t = new TempoEspecialCertidaoExSegurado();
				t.setPeriodoInicial(item.getPeriodoInicial());
				t.setPeriodoFinal(item.getPeriodoInicial());
				t.setTempo(item.getTempo());
				t.setTipoTempo(item.getTipoTempo());
				t.setCertidaoExSegurado(certidaoExSeguradoNova);
				t.setGrauDeficiencia(item.getGrauDeficiencia());
				tempoEspecialRepository.save(t);
			}
		}

		if (Objects.nonNull(certidaoExSeguradoAtual.getRelacaoRemuneracoes()) && !certidaoExSeguradoAtual.getRelacaoRemuneracoes().isEmpty()) {
			for (RelacaoRemuneracaoCertidaoExSegurado item : certidaoExSeguradoAtual.getRelacaoRemuneracoes()) {
				RelacaoRemuneracaoCertidaoExSegurado r = new RelacaoRemuneracaoCertidaoExSegurado();
				r.setAno(item.getAno());
				r.setJaneiro(item.getJaneiro());
				r.setFevereiro(item.getFevereiro());
				r.setMarco(item.getMarco());
				r.setAbril(item.getAbril());
				r.setMaio(item.getMaio());
				r.setJunho(item.getJunho());
				r.setJulho(item.getJulho());
				r.setAgosto(item.getAgosto());
				r.setSetembro(item.getSetembro());
				r.setOutubro(item.getOutubro());
				r.setNovembro(item.getNovembro());
				r.setDezembro(item.getDezembro());
				r.setDecimoTerceiro(item.getDecimoTerceiro());
				r.setCertidaoExSegurado(certidaoExSeguradoNova);
				relacaoRemuneracaoRepository.save(r);
			}
		}

		HistoricoCertidaoExSegurado hces = new HistoricoCertidaoExSegurado(certidaoExSeguradoNova);
		historicoCertidaoExSeguradoRepository.save(hces);
		
		
		return certidaoExSeguradoNova.getId();
	}

	private CertidaoExSegurado loadDadosCertidao(CertidaoExSegurado atual) {
		CertidaoExSegurado novo = new CertidaoExSegurado();
		
		novo.setFuncionario(atual.getFuncionario());
		novo.setNumeroCertidao(atual.getNumeroCertidao());
		novo.setAnoCertidao(atual.getAnoCertidao());
		if(Objects.nonNull(atual.getNumeroRetificacao()) && Objects.nonNull(atual.getCertidaoExSegurado())) {
			novo.setNumeroRetificacao(gerarNumeracaoRetificacaoCertidaoExSegurado(atual.getCertidaoExSegurado().getId()));
		} else {
			novo.setNumeroRetificacao(gerarNumeracaoRetificacaoCertidaoExSegurado(atual.getId()));
		}
		novo.setStatusSituacaoCertidao(StatusSituacaoCertidaoExSeguradoEnum.AGUARDANDO_IMPRESSAO);
		novo.setLotacao(atual.getLotacao());
		novo.setDataExoneracao(atual.getDataExoneracao());
		novo.setFonteInformacao(atual.getFonteInformacao());
		novo.setCertidaoExSegurado(atual);
		
		return novo;
	}

	public List<HistoricoCertidaoExSeguradoResponse> getHistoricoCertidaoExSeguradoById(Long certidaoId) {
		List<HistoricoCertidaoExSegurado> lista = historicoCertidaoExSeguradoRepository.findHistoricoByCertidaoId(certidaoId);
		List<HistoricoCertidaoExSeguradoResponse> response = new ArrayList<>();
		
		if(Utils.checkList(lista)) {
			for (HistoricoCertidaoExSegurado h : lista) {
				Usuario userCreated = usuarioRepository.findById(h.getCreatedBy())
						.orElseThrow(() -> new ResourceNotFoundException("User", "id", h.getCreatedBy()));
				
				HistoricoCertidaoExSeguradoResponse r = new HistoricoCertidaoExSeguradoResponse(h, userCreated.getNome());
				response.add(r);
			}
		}
		return response;
	}

}