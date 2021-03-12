package com.rhlinkcon.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.AnexoPastaEnum;
import com.rhlinkcon.model.FolhaCompetencia;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.GeneroEnum;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.Recadastramento;
import com.rhlinkcon.model.RecadastramentoContato;
import com.rhlinkcon.model.RecadastramentoDados;
import com.rhlinkcon.model.RecadastramentoEndereco;
import com.rhlinkcon.model.RecadastramentoHistoricoLigacao;
import com.rhlinkcon.model.Telefone;
import com.rhlinkcon.model.TipoTelefoneEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.recadastramento.ProjectionRecadastramentoResponse;
import com.rhlinkcon.payload.recadastramento.RecadastramentoHistoricoLigacaoResponse;
import com.rhlinkcon.payload.recadastramento.RecadastramentoRequest;
import com.rhlinkcon.payload.recadastramento.RecadastramentoResponse;
import com.rhlinkcon.payload.recadastramento.RecadastroRequest;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.repository.TelefoneRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.pensionista.PensionistaRepository;
import com.rhlinkcon.repository.recadastramento.RecadastramentoContatoRepository;
import com.rhlinkcon.repository.recadastramento.RecadastramentoDadosRepository;
import com.rhlinkcon.repository.recadastramento.RecadastramentoEnderecoRepository;
import com.rhlinkcon.repository.recadastramento.RecadastramentoHistoricoLigacaoRepository;
import com.rhlinkcon.repository.recadastramento.RecadastramentoRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class RecadastramentoService {

	@Autowired
	private RecadastramentoRepository repository;

	@Autowired
	private RecadastramentoDadosRepository dadosRepository;

	@Autowired
	private RecadastramentoEnderecoRepository enderecoRepository;

	@Autowired
	private RecadastramentoContatoRepository contatoRepository;

	@Autowired
	private AnexoService anexoService;

	@Autowired
	private AnexoRepository anexoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private PensionistaRepository pensaoRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private RecadastramentoHistoricoLigacaoRepository historicoLigacaoRepository;

	@Autowired
	private UsuarioService usuarioService;

	public PagedResponse<ProjectionRecadastramentoResponse> getAll(PagedRequest pagedRequest, String descricao,
			List<String> filial, String tipo) {

//		RecadastramentoFiltro filtro = new RecadastramentoFiltro(descricao, filial, tipo);

		Pageable pageable = Utils.generatePegeableNoOrderGeneric(pagedRequest);

		if (!Utils.checkStr(descricao))
			descricao = null;

		if (tipo.equals("PENSIONISTA")) {
			Page<Pensionista> projecao = repository.findByFiltrosPensionista(descricao, pageable);
			if (projecao.getNumberOfElements() == 0) {
				return new PagedResponse<>(Collections.emptyList(), projecao.getNumber(), projecao.getSize(),
						projecao.getTotalElements(), projecao.getTotalPages(), projecao.isLast());
			}
			List<ProjectionRecadastramentoResponse> response = projecao.map(pensionista -> {
				Recadastramento rec = repository.findTopByPensaoIdAndStatusOrderByDataDesc(pensionista.getId(), true);
				return new ProjectionRecadastramentoResponse(rec, null, pensionista);
			}).getContent();
			return new PagedResponse<>(response, projecao.getNumber(), projecao.getSize(), projecao.getTotalElements(),
					projecao.getTotalPages(), projecao.isLast());
		} else {
			Page<Funcionario> projecao = repository.findByFiltrosFuncionario(descricao, pageable);
			if (projecao.getNumberOfElements() == 0) {
				return new PagedResponse<>(Collections.emptyList(), projecao.getNumber(), projecao.getSize(),
						projecao.getTotalElements(), projecao.getTotalPages(), projecao.isLast());
			}
			List<ProjectionRecadastramentoResponse> response = projecao.map(funcionario -> {
				Recadastramento rec = repository.findTopByFuncionarioIdAndStatusOrderByDataDesc(funcionario.getId(),
						true);
				return new ProjectionRecadastramentoResponse(rec, funcionario, null);
			}).getContent();
			return new PagedResponse<>(response, projecao.getNumber(), projecao.getSize(), projecao.getTotalElements(),
					projecao.getTotalPages(), projecao.isLast());
		}

	}

	public PagedResponse<RecadastramentoHistoricoLigacaoResponse> getAllHistoricoLigacoes(int page, int size,
			String funcionarioId, String order) {
		Utils.validatePageNumberAndSize(page, size);

		Pageable pageable = Utils.getPageRequest(page, size, order);

		Long id = Long.valueOf(funcionarioId);

		Page<RecadastramentoHistoricoLigacao> projecao = historicoLigacaoRepository.findAllByFuncionarioId(id,
				pageable);

		if (projecao.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), projecao.getNumber(), projecao.getSize(),
					projecao.getTotalElements(), projecao.getTotalPages(), projecao.isLast());
		}

		List<RecadastramentoHistoricoLigacaoResponse> response = projecao.map(item -> {
			RecadastramentoHistoricoLigacaoResponse rh = new RecadastramentoHistoricoLigacaoResponse(item);
			rh.setCriadoPor(usuarioService.criadoPor(item));
			rh.setAlteradoPor(usuarioService.alteradoPor(item));
			return rh;
		}).getContent();

		return new PagedResponse<>(response, projecao.getNumber(), projecao.getSize(), projecao.getTotalElements(),
				projecao.getTotalPages(), projecao.isLast());
	}

	public PagedResponse<RecadastramentoResponse> getAllHistorico(int page, int size, String funcionarioId, String data,
			String order) {

		Utils.validatePageNumberAndSize(page, size);

		Pageable pageable = Utils.getPageRequest(page, size, order);

		Long id = Long.valueOf(funcionarioId);

		LocalDate dt = Strings.isNotBlank(data) ? LocalDate.parse(data) : null;

		Page<Recadastramento> projecao = Objects.nonNull(dt)
				? repository.findAllByFuncionarioIdAndData(id, dt, pageable)
				: repository.findAllByFuncionarioId(id, pageable);

		if (projecao.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), projecao.getNumber(), projecao.getSize(),
					projecao.getTotalElements(), projecao.getTotalPages(), projecao.isLast());
		}

		List<RecadastramentoResponse> response = projecao.map(item -> {
			RecadastramentoResponse r = new RecadastramentoResponse(item, Projecao.MEDIA);
			r.setCriadoPor(usuarioService.criadoPor(item));
			r.setAlteradoPor(usuarioService.alteradoPor(item));
			return r;
		}).getContent();

		return new PagedResponse<>(response, projecao.getNumber(), projecao.getSize(), projecao.getTotalElements(),
				projecao.getTotalPages(), projecao.isLast());
	}

	@Transactional
	public void create(@Valid RecadastramentoRequest request) {
		Recadastramento obj = loadRecadastramento(request);

		inativarAnteriores(obj.getFuncionario(), obj.getPensao());

		obj.setStatus(true);

		repository.save(obj);
	}

	private void inativarAnteriores(Funcionario funcionario, Pensionista pensao) {
		repository.inativarAnteriores(funcionario.getId(), pensao.getId());
	}

	private Recadastramento loadRecadastramento(@Valid RecadastramentoRequest request) {
		Recadastramento obj = new Recadastramento();

		if (Objects.nonNull(request.getId()))
			obj.setId(request.getId());

		if (Objects.nonNull(request.getFuncionarioId())) {
			Funcionario funcionario = funcionarioRepository.findById(request.getFuncionarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", request.getFuncionarioId()));
			obj.setFuncionario(funcionario);
		}

		if (Objects.nonNull(request.getPensaoId())) {
			Pensionista pensao = pensaoRepository.findById(request.getPensaoId())
					.orElseThrow(() -> new ResourceNotFoundException("Pensionista", "id", request.getPensaoId()));
			obj.setPensao(pensao);
		}

		if (Objects.nonNull(request.getDados()))
			obj.setDados(new RecadastramentoDados(request.getDados()));

		if (Objects.nonNull(request.getEndereco()))
			obj.setEndereco(new RecadastramentoEndereco(request.getEndereco()));

		if (Objects.nonNull(request.getContato()))
			obj.setContato(new RecadastramentoContato(request.getContato()));

		obj.setData(LocalDate.now());

		obj.setStatus(true);

		// Anexos
		if (Utils.checkList(request.getAnexos())) {
			obj.setAnexos(anexoRepository.findAllByIdIn(request.getAnexos()));
		}

		return obj;
	}

	@Transactional
	public void update(@Valid RecadastramentoRequest request) {
		Recadastramento obj = loadRecadastramento(request);

		dadosRepository.save(obj.getDados());

		if (Objects.nonNull(obj.getEndereco().getTelefones())) {
			for (Telefone t : obj.getEndereco().getTelefones()) {
				telefoneRepository.save(t);
			}
		}

		if (Objects.nonNull(obj.getContato().getTelefones())) {
			for (Telefone t : obj.getContato().getTelefones()) {
				telefoneRepository.save(t);
			}
		}

		// Anexos
		if (Utils.checkList(request.getAnexos())) {
			obj.setAnexos(anexoRepository.findAllByIdIn(request.getAnexos()));
		}

		enderecoRepository.save(obj.getEndereco());
		contatoRepository.save(obj.getContato());

		repository.save(obj);
	}

	public void delete(Long id) {
		Recadastramento obj = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recadastramento", "id", id));
		repository.delete(obj);
	}

	public void deleteAnexo(Long id) {
		Recadastramento obj = repository.findAllByAnexosId(id);
		if (Objects.nonNull(obj)) {
			obj.getAnexos().removeIf(a -> a.getId().equals(id));
			repository.save(obj);
		}

		anexoService.deleteAnexo(id);
	}

	public RecadastramentoResponse getById(Long id) {

		Recadastramento obj = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recadastramento", "id", id));

		RecadastramentoResponse response = new RecadastramentoResponse(obj, Projecao.COMPLETA);

		if (Utils.checkList(obj.getAnexos())) {
			response.setAnexos(new ArrayList<>());
			obj.getAnexos().forEach(e -> response.getAnexos().add(new AnexoResponse(e)));
		}

		DadoCadastralResponse auditoria = usuarioService.preencheAuditoria(obj);

		response.setAlteradoPor(auditoria.getAlteradoPor());
		response.setCriadoPor(auditoria.getCriadoPor());
		response.setCriadoEm(obj.getCreatedAt());
		response.setAlteradoEm(obj.getUpdatedAt());

		return response;
	}

	@Transactional
	public Long novo(@Valid RecadastroRequest request) {
		Recadastramento novo;

		if (Objects.nonNull(request.getRecadastramentoId())) {
			Recadastramento obj = repository.findById(request.getRecadastramentoId()).orElseThrow(
					() -> new ResourceNotFoundException("Recadastramento", "id", request.getRecadastramentoId()));

			obj.setStatus(false);

			repository.save(obj);

			novo = loadRecadastramento(obj);
			novo.setData(LocalDate.now());
			novo.setStatus(true);

			if (Objects.nonNull(novo.getDados()))
				dadosRepository.save(novo.getDados());

			if (Objects.nonNull(novo.getEndereco()))
				enderecoRepository.save(novo.getEndereco());

			if (Objects.nonNull(novo.getContato()))
				contatoRepository.save(novo.getContato());

			repository.save(novo);

		} else {
			if (Objects.nonNull(request.getPensaoId())) {
				Pensionista obj = pensaoRepository.findById(request.getPensaoId())
						.orElseThrow(() -> new ResourceNotFoundException("Pensionista", "id", request.getPensaoId()));

				/*
				 * Para salvar as primeiras informações e gerar histórico com os primeiros dados
				 * trazidos no formulário
				 */
				Recadastramento primeiroHistorico = loadRecadastramentoPensao(obj);
				primeiroHistorico.setData(LocalDate.now());
				primeiroHistorico.setStatus(false);
				dadosRepository.save(primeiroHistorico.getDados());
				enderecoRepository.save(primeiroHistorico.getEndereco());
				repository.save(primeiroHistorico);

				novo = loadRecadastramentoPensao(obj);

				novo.setData(LocalDate.now());
				novo.setStatus(true);

				dadosRepository.save(novo.getDados());
				enderecoRepository.save(novo.getEndereco());

				repository.save(novo);

			} else {
				Funcionario obj = funcionarioRepository.findById(request.getFuncionarioId()).orElseThrow(
						() -> new ResourceNotFoundException("Funcionario", "id", request.getFuncionarioId()));

				/*
				 * Para salvar as primeiras informações e gerar histórico com os primeiros dados
				 * trazidos no formulário
				 */
				Recadastramento primeiroHistorico = loadRecadastramentoFuncionario(obj);
				primeiroHistorico.setData(LocalDate.now());
				primeiroHistorico.setStatus(false);
				dadosRepository.save(primeiroHistorico.getDados());
				enderecoRepository.save(primeiroHistorico.getEndereco());
				repository.save(primeiroHistorico);

				novo = loadRecadastramentoFuncionario(obj);

				novo.setData(LocalDate.now());
				novo.setStatus(true);

				dadosRepository.save(novo.getDados());
				enderecoRepository.save(novo.getEndereco());

				repository.save(novo);
			}
		}

		return novo.getId();
	}

	private Recadastramento loadRecadastramentoFuncionario(Funcionario obj) {
		Recadastramento novo = new Recadastramento();
		novo.setFuncionario(obj);

		RecadastramentoDados d = new RecadastramentoDados();
		d.setMunicipio(obj.getMunicipio());
		d.setNacionalidade(obj.getNacionalidade());
		d.setNome(obj.getNome());
		d.setNomeMae(obj.getNomeMae());
		d.setNomePai(obj.getNomePai());
		d.setGenero(obj.getSexo() == 'F' ? GeneroEnum.FEMININO : GeneroEnum.MASCULINO);
		d.setEstadoCivil(obj.getEstadoCivil());
		d.setGrauInstrucao(obj.getGrauInstrucao());
		d.setRacaCor(obj.getCorPele());
		novo.setDados(d);

		RecadastramentoEndereco e = new RecadastramentoEndereco();
		e.setMunicipio(obj.getMunicipio());
		e.setLogradouro(obj.getLogradouro());
		e.setNumero(obj.getNumero());
		e.setComplemento(obj.getComplemento());
		e.setCep(obj.getCep());
		e.setBairro(obj.getBairro());
		e.setEmail(obj.getEmailPessoal());

		if (Objects.nonNull(obj.getTelefonePrincipal()) || Objects.nonNull(obj.getTelefoneComercial())
				|| Objects.nonNull(obj.getTelefoneOpcional())) {
			e.setTelefones(new ArrayList<>());

			if (Objects.nonNull(obj.getTelefonePrincipal())) {
				Telefone tel = new Telefone();
				tel.setNumero(obj.getTelefonePrincipal());
				tel.setTipo(TipoTelefoneEnum.FIXO);

				telefoneRepository.save(tel);

				e.getTelefones().add(tel);
			}

			if (Objects.nonNull(obj.getTelefoneComercial())) {
				Telefone tel = new Telefone();
				tel.setNumero(obj.getTelefoneComercial());
				tel.setTipo(TipoTelefoneEnum.COMERCIAL);

				telefoneRepository.save(tel);

				e.getTelefones().add(tel);
			}

			if (Objects.nonNull(obj.getTelefoneOpcional())) {
				Telefone tel = new Telefone();
				tel.setNumero(obj.getTelefoneOpcional());
				tel.setTipo(TipoTelefoneEnum.MOVEL);

				telefoneRepository.save(tel);

				e.getTelefones().add(tel);
			}

		}

		novo.setEndereco(e);

		return novo;
	}

	private Recadastramento loadRecadastramentoPensao(Pensionista obj) {
		Recadastramento novo = new Recadastramento();

		novo.setPensao(obj);
		novo.setFuncionario(obj.getFuncionario());

		RecadastramentoDados d = new RecadastramentoDados();
		d.setMunicipio(obj.getMunicipio());
		d.setNome(obj.getNome());
		d.setNomeMae(obj.getNomeMae());
		d.setNomePai(obj.getNomePai());
		d.setGenero(obj.getGenero());
		d.setEstadoCivil(obj.getEstadoCivil());
		d.setGrauInstrucao(obj.getGrauInstrucao());
		novo.setDados(d);

		RecadastramentoEndereco e = new RecadastramentoEndereco();
		e.setMunicipio(obj.getMunicipio());
		e.setLogradouro(obj.getLogradouro());
		e.setNumero(obj.getNumeroLogradouro());
		e.setComplemento(obj.getComplementoLogradouro());
		e.setCep(obj.getCep().toString());
		e.setBairro(obj.getBairro());

		if (Objects.nonNull(obj.getTelefoneFixo()) || Objects.nonNull(obj.getFuncionario().getTelefonePrincipal())
				|| Objects.nonNull(obj.getFuncionario().getTelefoneComercial())
				|| Objects.nonNull(obj.getFuncionario().getTelefoneOpcional())) {
			e.setTelefones(new ArrayList<>());

			if (Objects.nonNull(obj.getTelefoneFixo())) {
				Telefone tel = new Telefone();
				tel.setNumero(obj.getTelefoneFixo());
				tel.setTipo(TipoTelefoneEnum.FIXO);

				telefoneRepository.save(tel);

				e.getTelefones().add(tel);
			}

			if (Objects.nonNull(obj.getFuncionario().getTelefonePrincipal())) {
				Telefone tel = new Telefone();
				tel.setNumero(obj.getFuncionario().getTelefonePrincipal());
				tel.setTipo(TipoTelefoneEnum.FIXO);

				telefoneRepository.save(tel);

				e.getTelefones().add(tel);
			}

			if (Objects.nonNull(obj.getFuncionario().getTelefoneComercial())) {
				Telefone tel = new Telefone();
				tel.setNumero(obj.getFuncionario().getTelefoneComercial());
				tel.setTipo(TipoTelefoneEnum.COMERCIAL);

				telefoneRepository.save(tel);

				e.getTelefones().add(tel);
			}

			if (Objects.nonNull(obj.getFuncionario().getTelefoneOpcional())) {
				Telefone tel = new Telefone();
				tel.setNumero(obj.getFuncionario().getTelefoneOpcional());
				tel.setTipo(TipoTelefoneEnum.MOVEL);

				telefoneRepository.save(tel);

				e.getTelefones().add(tel);
			}

		}

		novo.setEndereco(e);

		return novo;
	}

	private Recadastramento loadRecadastramento(Recadastramento obj) {
		Recadastramento novo = new Recadastramento();

		if (Objects.nonNull(obj.getPensao()))
			novo.setPensao(obj.getPensao());

		if (Objects.nonNull(obj.getFuncionario()))
			novo.setFuncionario(obj.getFuncionario());

		if (Objects.nonNull(obj.getDados())) {
			RecadastramentoDados d = new RecadastramentoDados();
			d.setMunicipio(obj.getDados().getMunicipio());
			d.setNacionalidade(obj.getDados().getNacionalidade());
			d.setNome(obj.getDados().getNome());
			d.setNomeMae(obj.getDados().getNomeMae());
			d.setNomePai(obj.getDados().getNomePai());
			d.setGenero(obj.getDados().getGenero());
			d.setEstadoCivil(obj.getDados().getEstadoCivil());
			d.setRacaCor(obj.getDados().getRacaCor());
			d.setTipoSanguineo(obj.getDados().getTipoSanguineo());
			d.setGrauInstrucao(obj.getDados().getGrauInstrucao());
			novo.setDados(d);
		}

		if (Objects.nonNull(obj.getEndereco())) {
			RecadastramentoEndereco e = new RecadastramentoEndereco();
			e.setMunicipio(obj.getEndereco().getMunicipio());
			e.setLogradouro(obj.getEndereco().getLogradouro());
			e.setNumero(obj.getEndereco().getNumero());
			e.setComplemento(obj.getEndereco().getComplemento());
			e.setCep(obj.getEndereco().getCep());
			e.setBairro(obj.getEndereco().getBairro());
			e.setEmail(obj.getEndereco().getEmail());
			e.setObservacao(obj.getEndereco().getObservacao());

			if (Objects.nonNull(obj.getEndereco().getTelefones()) && !obj.getEndereco().getTelefones().isEmpty()) {
				e.setTelefones(new ArrayList<>());

				for (Telefone t : obj.getEndereco().getTelefones()) {
					Telefone tr = new Telefone();
					tr.setNumero(t.getNumero());
					tr.setTipo(t.getTipo());

					telefoneRepository.save(tr);

					e.getTelefones().add(tr);
				}
			}

			novo.setEndereco(e);
		}

		if (Objects.nonNull(obj.getContato())) {
			RecadastramentoContato c = new RecadastramentoContato();
			c.setNome(obj.getContato().getNome());
			c.setEmail(obj.getContato().getEmail());
			c.setObservacao(obj.getContato().getObservacao());

			if (Objects.nonNull(obj.getContato().getTelefones()) && !obj.getContato().getTelefones().isEmpty()) {
				c.setTelefones(new ArrayList<>());

				for (Telefone t : obj.getContato().getTelefones()) {
					Telefone tr = new Telefone();
					tr.setNumero(t.getNumero());
					tr.setTipo(t.getTipo());

					telefoneRepository.save(tr);

					c.getTelefones().add(tr);
				}
			}

			novo.setContato(c);
		}

		// Anexos
		if (Utils.checkList(obj.getAnexos())) {
			novo.setAnexos(new ArrayList<>());

			for (Anexo a : obj.getAnexos()) {
				try {
					Anexo ane = anexoService.createAnexo(a, AnexoPastaEnum.RECADASTRAMENTO);
					novo.getAnexos().add(ane);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return novo;
	}

	public boolean checkRecadastramentoEmDia(Funcionario funcionario, FolhaCompetencia folhaCompetencia) {

		if (!folhaCompetencia.isChecarRecad())
			return true;

		LocalDate proxima = null;
		LocalDate dataBaseRecadastramento = null;

		Recadastramento recadastramento = repository.findTopByFuncionarioIdAndStatusOrderByDataDesc(funcionario.getId(),
				true);

		if (Objects.isNull(recadastramento)) {

			if (Objects.isNull(funcionario.getDataInicioSituacaoFuncional()))
				return false;

			LocalDate inicioSitFuncional = funcionario.getDataInicioSituacaoFuncional().atZone(ZoneOffset.UTC)
					.toLocalDate();

			// Calculando o último aniversário que será usado como data base para o calculo
			LocalDate dataNascLocalDate = funcionario.getDataNascimento().atZone(ZoneOffset.UTC).toLocalDate();
			Period period = Period.between(dataNascLocalDate, inicioSitFuncional);
			LocalDate ultimoAniversario = dataNascLocalDate.plusYears(period.getYears());

			dataBaseRecadastramento = ultimoAniversario;

		} else {
			dataBaseRecadastramento = recadastramento.getData();
		}

		funcionario.setDataBaseRecadastramento(dataBaseRecadastramento);

		proxima = dataBaseRecadastramento.plusYears(1);

		LocalDate dataCompetencia = LocalDate.of(folhaCompetencia.getAnoCompetencia(),
				folhaCompetencia.getMesCompetencia(), 1);

		LocalDate atual = dataCompetencia;

		if (atual.isBefore(proxima) || atual.isEqual(proxima))
			return true;

		return false;
	}

	public boolean checkRecadastramentoEmDia(Pensionista pensionista, FolhaCompetencia folhaCompetencia) {

		if (!folhaCompetencia.isChecarRecad())
			return true;

		LocalDate proxima = null;
		LocalDate dataBaseRecadastramento = null;

		Recadastramento recadastramento = repository.findTopByPensaoIdAndStatusOrderByDataDesc(pensionista.getId(),
				true);

		if (Objects.isNull(recadastramento)) {

			if (Objects.isNull(pensionista.getPensaoPagamento().getDataPrimeiroPagamento()))
				return false;

			dataBaseRecadastramento = pensionista.getPensaoPagamento().getDataPrimeiroPagamento();

		} else {
			dataBaseRecadastramento = recadastramento.getData();
		}

		pensionista.setDataBaseRecadastramento(dataBaseRecadastramento);

		proxima = dataBaseRecadastramento.plusMonths(6);

		LocalDate dataCompetencia = LocalDate.of(folhaCompetencia.getAnoCompetencia(),
				folhaCompetencia.getMesCompetencia(), 1);

		LocalDate atual = dataCompetencia;

		if (atual.isBefore(proxima) || atual.isEqual(proxima))
			return true;

		return false;
	}

	public RecadastramentoResponse getByIdVisualizar(Long id) {

		Recadastramento objRecadastramento = repository.findTopByFuncionarioIdAndStatusOrderByDataDesc(id, true);

		if (Objects.isNull(objRecadastramento)) {

			Pensionista objPensionista = pensaoRepository.findTopByFuncionarioId(id);

			if (Objects.nonNull(objPensionista)) {
				objRecadastramento = loadRecadastramentoPensao(objPensionista);
			} else {
				Funcionario objFuncionario = funcionarioRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", id));

				objRecadastramento = loadRecadastramentoFuncionario(objFuncionario);
			}
		}

		RecadastramentoResponse response = new RecadastramentoResponse(objRecadastramento, Projecao.COMPLETA);

		if (Utils.checkList(objRecadastramento.getAnexos())) {
			response.setAnexos(new ArrayList<>());
			objRecadastramento.getAnexos().forEach(e -> response.getAnexos().add(new AnexoResponse(e)));
		}

		return response;

	}

}
