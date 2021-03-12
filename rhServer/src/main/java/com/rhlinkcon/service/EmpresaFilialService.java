package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Agencia;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.Banco;
import com.rhlinkcon.model.Cnae;
import com.rhlinkcon.model.CodigoPagamentoGps;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.EsferaOrgaoEnum;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.Nacionalidade;
import com.rhlinkcon.model.ReferenciaContribuicaoEnum;
import com.rhlinkcon.model.SituacaoFilialEnum;
import com.rhlinkcon.model.TipoEstabelecimentoEnum;
import com.rhlinkcon.model.TipoFilialEnum;
import com.rhlinkcon.model.TipoInscricaoEnum;
import com.rhlinkcon.model.TipoOperacaoEnum;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.definicaoOrganico.DefinicaoOrganicoResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialRequest;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.repository.CnaeRepository;
import com.rhlinkcon.repository.CodigoPagamentoGpsRepository;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.MunicipioRepository;
import com.rhlinkcon.repository.NacionalidadeRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.agencia.AgenciaRepository;
import com.rhlinkcon.repository.banco.BancoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class EmpresaFilialService {

	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CnaeRepository cnaeRepository;

	@Autowired
	private CodigoPagamentoGpsRepository codigoPagamentoGpsRepository;

	@Autowired
	private BancoRepository bancoRepository;

	@Autowired
	private UnidadeFederativaRepository ufRepository;

	@Autowired
	private NacionalidadeRepository nacionalidadeRepository;

	@Autowired
	private AnexoRepository anexoRepository;

	@Autowired
	private MunicipioRepository municipioRepository;

	@Autowired
	private AgenciaService agenciaService;

	@Autowired
	private AgenciaRepository agenciaRepository;

	@Autowired
	DefinicaoOrganicoService definicaoOrganicoService;

	public void createEmpresaFilial(EmpresaFilialRequest empresaFilialRequest) {

		// Creating user's account
		EmpresaFilial empresaFilial = new EmpresaFilial(empresaFilialRequest);

		if (!Objects.nonNull(empresaFilial.getEmpresaMatriz()))
			empresaFilial.setEmpresaMatriz(false);

		setEntidades(empresaFilial, empresaFilialRequest);

		empresaFilialRepository.save(empresaFilial);

	}

	public void updateEmpresaFilial(EmpresaFilialRequest empresaFilialRequest) {

		// Updating user's account
		EmpresaFilial empresaFilial = empresaFilialRepository.findById(empresaFilialRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("EmpresaFilial", "id", empresaFilialRequest.getId()));

		setEntidades(empresaFilial, empresaFilialRequest);

		empresaFilial.setSigla(empresaFilialRequest.getSigla());
		empresaFilial.setNomeFilial(empresaFilialRequest.getNomeFilial());
		empresaFilial.setEmpresaMatriz(Utils.setBool(empresaFilialRequest.getEmpresaMatriz()));

		if (Utils.checkStr(empresaFilialRequest.getTipoFilial()))
			empresaFilial.setTipoFilial(TipoFilialEnum.getEnumByString(empresaFilialRequest.getTipoFilial()));

		if (Utils.checkStr(empresaFilialRequest.getSituacao()))
			empresaFilial.setSituacao(SituacaoFilialEnum.getEnumByString(empresaFilialRequest.getSituacao()));

		if (Utils.checkStr(empresaFilialRequest.getEsferaOrgao()))
			empresaFilial.setEsferaOrgao(EsferaOrgaoEnum.getEnumByString(empresaFilialRequest.getEsferaOrgao()));

		empresaFilial.setQtdProprietario(empresaFilialRequest.getQtdProprietario());

		if (Utils.checkStr(empresaFilialRequest.getTipoEstabelecimento()))
			empresaFilial.setTipoEstabelecimento(
					TipoEstabelecimentoEnum.getEnumByString(empresaFilialRequest.getTipoEstabelecimento()));

		empresaFilial.setCapitalSocialAnual(empresaFilialRequest.getCapitalSocialAnual());
		empresaFilial.setDataInicialAtividade(empresaFilialRequest.getDataInicialAtividade());

		if (Utils.checkStr(empresaFilialRequest.getReferenciaContribuicao()))
			empresaFilial.setReferenciaContribuicao(
					ReferenciaContribuicaoEnum.getEnumByString(empresaFilialRequest.getReferenciaContribuicao()));

		empresaFilial.setAtividadePrimaria(empresaFilialRequest.getAtividadePrimaria());
		empresaFilial.setNaturezaEstabelecimento(empresaFilialRequest.getNaturezaEstabelecimento());
		empresaFilial.setAgencia(empresaFilialRequest.getAgencia());
		empresaFilial.setFpas(empresaFilialRequest.getFpas());
		empresaFilial.setCodigoGrpasAcidenteTrabalho(empresaFilialRequest.getCodigoGrpasAcidenteTrabalho());
		empresaFilial.setCnpj(empresaFilialRequest.getCnpj());
		empresaFilial.setCei(empresaFilialRequest.getCei());
		empresaFilial.setCodigoEmpregador(empresaFilialRequest.getCodigoEmpregador());
		empresaFilial.setrNegativa(empresaFilialRequest.getrNegativa());
		empresaFilial.setPercentualEmpregador(empresaFilialRequest.getPercentualEmpregador());
		empresaFilial.setSat(empresaFilialRequest.getSat());
		empresaFilial.setSalarioEducacao(empresaFilialRequest.getSalarioEducacao());
		empresaFilial.setSenai(empresaFilialRequest.getSenai());
		empresaFilial.setSesi(empresaFilialRequest.getSesi());
		empresaFilial.setSenac(empresaFilialRequest.getSenac());
		empresaFilial.setSesc(empresaFilialRequest.getSesc());
		empresaFilial.setSebrae(empresaFilialRequest.getSebrae());
		empresaFilial.setSenar(empresaFilialRequest.getSenar());
		empresaFilial.setSenat(empresaFilialRequest.getSenat());
		empresaFilial.setSet(empresaFilialRequest.getSet());
		empresaFilial.setSecoop(empresaFilialRequest.getSecoop());
		empresaFilial.setDpc(empresaFilialRequest.getDpc());
		empresaFilial.setForcaAerea(empresaFilialRequest.getForcaAerea());
		empresaFilial.setFap(empresaFilialRequest.getFap());
		empresaFilial.setLogradouro(empresaFilialRequest.getLogradouro());
		empresaFilial.setNumero(empresaFilialRequest.getNumero());
		empresaFilial.setComplemento(empresaFilialRequest.getComplemento());
		empresaFilial.setCep(empresaFilialRequest.getCep());
		empresaFilial.setBairro(empresaFilialRequest.getBairro());
		empresaFilial.setTelefone(empresaFilialRequest.getTelefone());
		empresaFilial.setNomeResponsavel(empresaFilialRequest.getNomeResponsavel());
		empresaFilial.setVigenciaFinal(empresaFilialRequest.getVigenciaFinal());
		empresaFilial.setVigenciaInicial(empresaFilialRequest.getVigenciaInicial());
		empresaFilial.setEmailResponsavel(empresaFilialRequest.getEmailResponsavel());

		empresaFilial.setCodigoConvenio(empresaFilialRequest.getCodigoConvenio());
		empresaFilial.setTipoOperacao(TipoOperacaoEnum.getEnumByString(empresaFilialRequest.getTipoOperacao()));

		empresaFilialRepository.save(empresaFilial);

	}

	public void deleteEmpresaFilial(Long id) {
		EmpresaFilial empresaFilial = empresaFilialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("EmpresaFilial", "id", id));

		empresaFilialRepository.delete(empresaFilial);
	}

	public List<EmpresaFilialResponse> getAllEmpresasFiliais(Boolean empresaMatriz) {

		List<EmpresaFilial> empresasFiliais = empresaFilialRepository.findByEmpresaMatriz(empresaMatriz);
		List<EmpresaFilialResponse> empresaFilialResponse = new ArrayList<>();

		if (!empresasFiliais.isEmpty()) {
			for (EmpresaFilial empresaFilial : empresasFiliais) {

				empresaFilialResponse.add(new EmpresaFilialResponse(empresaFilial));
			}
			return empresaFilialResponse;
		}

		return null;
	}

//	CÓDIGO TEMPORÁRIO POIS AINDA NÃO TEM A LIGAÇÃO DE EMPRESA COM FILIAL.
	public List<EmpresaFilialResponse> getAllEmpresasMatrizWithFiliaisToDropDown() {
		List<EmpresaFilial> empresasFiliais = empresaFilialRepository.findByEmpresaMatriz(true);
		List<EmpresaFilialResponse> empresaFilialResponse = new ArrayList<>();
		if (!empresasFiliais.isEmpty()) {
			for (EmpresaFilial empresaFilial : empresasFiliais) {
				EmpresaFilialResponse matrizResponse = new EmpresaFilialResponse(empresaFilial, Projecao.BASICA);
				matrizResponse.setFiliais(new ArrayList<EmpresaFilialResponse>());
				for (EmpresaFilial filial : empresaFilialRepository.findByEmpresaMatriz(false)) {
					matrizResponse.getFiliais().add(new EmpresaFilialResponse(filial, Projecao.BASICA));
				}
				empresaFilialResponse.add(matrizResponse);
			}
		}
		return empresaFilialResponse;
	}

	public PagedResponse<EmpresaFilialResponse> getAllEmpresasFiliais(int page, int size, String siglaNome,
			String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<EmpresaFilial> empresasFiliais = null;

		if (Objects.nonNull(siglaNome))
			empresasFiliais = empresaFilialRepository.findBySiglaOrNomeFilialIgnoreCaseContaining(siglaNome, pageable);
		else
			empresasFiliais = empresaFilialRepository.findAll(pageable);

		if (empresasFiliais.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), empresasFiliais.getNumber(), empresasFiliais.getSize(),
					empresasFiliais.getTotalElements(), empresasFiliais.getTotalPages(), empresasFiliais.isLast());
		}

		List<EmpresaFilialResponse> empresasFiliaisResponse = empresasFiliais.map(empresaFilial -> {
			return new EmpresaFilialResponse(empresaFilial);
		}).getContent();

		for (EmpresaFilialResponse empresaFilialResponse : empresasFiliaisResponse) {
			if (Objects.nonNull(
					definicaoOrganicoService.selectDefinicaoOrganicoByEmpresaFilialId(empresaFilialResponse.getId()))) {
				DefinicaoOrganicoResponse definicaoOrganica = definicaoOrganicoService
						.selectDefinicaoOrganicoByEmpresaFilialId(empresaFilialResponse.getId());
				if (Objects.nonNull(definicaoOrganica)) {
					empresaFilialResponse.setDefinicaoOrganicoId(definicaoOrganica.getId());
				}
			}
		}

		return new PagedResponse<>(empresasFiliaisResponse, empresasFiliais.getNumber(), empresasFiliais.getSize(),
				empresasFiliais.getTotalElements(), empresasFiliais.getTotalPages(), empresasFiliais.isLast());

	}

	public PagedResponse<EmpresaFilialResponse> getAllEmpresasFiliaisNoEmpresaMatriz(int page, int size,
			String siglaNome, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<EmpresaFilial> empresasFiliais = null;

		if (Objects.nonNull(siglaNome))
			empresasFiliais = empresaFilialRepository.findByEmpresaMatriz(false, siglaNome, pageable);
		else
			empresasFiliais = empresaFilialRepository.findAll(pageable);

		if (empresasFiliais.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), empresasFiliais.getNumber(), empresasFiliais.getSize(),
					empresasFiliais.getTotalElements(), empresasFiliais.getTotalPages(), empresasFiliais.isLast());
		}

		List<EmpresaFilialResponse> empresasFiliaisResponse = empresasFiliais.map(empresaFilial -> {
			return new EmpresaFilialResponse(empresaFilial);
		}).getContent();

		for (EmpresaFilialResponse empresaFilialResponse : empresasFiliaisResponse) {
			if (Objects.nonNull(
					definicaoOrganicoService.selectDefinicaoOrganicoByEmpresaFilialId(empresaFilialResponse.getId()))) {
				DefinicaoOrganicoResponse definicaoOrganica = definicaoOrganicoService
						.selectDefinicaoOrganicoByEmpresaFilialId(empresaFilialResponse.getId());
				if (Objects.nonNull(definicaoOrganica)) {
					empresaFilialResponse.setDefinicaoOrganicoId(definicaoOrganica.getId());
				}
			}
		}

		return new PagedResponse<>(empresasFiliaisResponse, empresasFiliais.getNumber(), empresasFiliais.getSize(),
				empresasFiliais.getTotalElements(), empresasFiliais.getTotalPages(), empresasFiliais.isLast());

	}

	public EmpresaFilialResponse getEmpresaFilialById(Long empresaFilialId) {
		EmpresaFilial empresaFilial = empresaFilialRepository.findById(empresaFilialId)
				.orElseThrow(() -> new ResourceNotFoundException("EmpresaFilial", "id", empresaFilialId));

		if (Objects.isNull(empresaFilial.getAgenciaBancaria()) && Objects.nonNull(empresaFilial.getAgencia())
				&& Objects.nonNull(empresaFilial.getBanco())) {
			Agencia agencia = agenciaService.findByNumeroAndBanco(empresaFilial.getBanco(), empresaFilial.getAgencia());
			if (Objects.nonNull(agencia)) {
				empresaFilial.setAgenciaBancaria(agencia);
			}
		}

		Usuario userCreated = usuarioRepository.findById(empresaFilial.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", empresaFilial.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(empresaFilial.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", empresaFilial.getUpdatedBy()));

		EmpresaFilialResponse empresaFilialResponse = new EmpresaFilialResponse(empresaFilial,
				empresaFilial.getCreatedAt(), userCreated.getNome(), empresaFilial.getUpdatedAt(),
				userUpdated.getNome());

		return empresaFilialResponse;
	}

	public List<LotacaoResponse> getLotacoesByEmpresaFilialId(Long empresaFilialId) {
		EmpresaFilial empresaFilial = empresaFilialRepository.findById(empresaFilialId)
				.orElseThrow(() -> new ResourceNotFoundException("EmpresaFilial", "id", empresaFilialId));

		List<LotacaoResponse> lotacoes = new ArrayList<>();

		if (Utils.checkSetList(empresaFilial.getLotacoes()))
			empresaFilial.getLotacoes().forEach(lotacao -> lotacoes.add(new LotacaoResponse(lotacao)));

		return lotacoes;
	}

	public List<LotacaoResponse> getLotacoesByEmpresaFiliaisId(List<Long> listEmpresaFiliaisId) {
		List<EmpresaFilial> empresaFilial = empresaFilialRepository.findByIdIn(listEmpresaFiliaisId);

		List<LotacaoResponse> lotacoes = new ArrayList<>();

		if (!empresaFilial.isEmpty())
			for (EmpresaFilial filial : empresaFilial) {
				for (Lotacao lotacao : filial.getLotacoes()) {
					lotacoes.add(new LotacaoResponse(lotacao, Projecao.BASICA));
				}
			}

		return lotacoes;
	}

	private void setEntidades(EmpresaFilial empresaFilial, EmpresaFilialRequest empresaFilialRequest) {

		Cnae cnae = cnaeRepository.findById(empresaFilialRequest.getCnaeId())
				.orElseThrow(() -> new ResourceNotFoundException("Cnae", "id", empresaFilialRequest.getCnaeId()));

		CodigoPagamentoGps codigoPagamentoGps = codigoPagamentoGpsRepository
				.findById(empresaFilialRequest.getCodigoGpsId())
				.orElseThrow(() -> new ResourceNotFoundException("CodigoPagamentoGps", "id",
						empresaFilialRequest.getCodigoGpsId()));

		Banco banco = bancoRepository.findById(empresaFilialRequest.getBancoId())
				.orElseThrow(() -> new ResourceNotFoundException("Banco", "id", empresaFilialRequest.getBancoId()));

		Agencia agencia = agenciaRepository.findById(empresaFilialRequest.getAgenciaBancariaId()).orElseThrow(
				() -> new ResourceNotFoundException("Agencia", "id", empresaFilialRequest.getAgenciaBancariaId()));

		empresaFilial.setCnae(cnae);
		empresaFilial.setCodigoGps(codigoPagamentoGps);
		empresaFilial.setBanco(banco);
		empresaFilial.setAgenciaBancaria(agencia);

		empresaFilial.setCodigoConvenio(empresaFilialRequest.getCodigoConvenio());
		empresaFilial.setTipoOperacao(TipoOperacaoEnum.getEnumByString(empresaFilialRequest.getTipoOperacao()));

		if (Objects.nonNull(empresaFilialRequest.getUfId())) {
			UnidadeFederativa uf = ufRepository.findById(empresaFilialRequest.getUfId()).orElseThrow(
					() -> new ResourceNotFoundException("UnidadeFederativa", "id", empresaFilialRequest.getUfId()));

			empresaFilial.setUf(uf);
		} else
			empresaFilial.setUf(null);

		if (Objects.nonNull(empresaFilialRequest.getPaisId())) {
			Nacionalidade pais = nacionalidadeRepository.findById(empresaFilialRequest.getPaisId()).orElseThrow(
					() -> new ResourceNotFoundException("Nacionalidade", "id", empresaFilialRequest.getPaisId()));

			empresaFilial.setPais(pais);
		} else
			empresaFilial.setPais(null);

		if (Objects.nonNull(empresaFilialRequest.getAnexoId())) {
			Anexo anexo = anexoRepository.findById(empresaFilialRequest.getAnexoId())
					.orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", empresaFilialRequest.getAnexoId()));
			empresaFilial.setAnexo(anexo);
		}

		if (Objects.nonNull(empresaFilialRequest.getMunicipioId())) {
			Municipio municipio = municipioRepository.findById(empresaFilialRequest.getMunicipioId()).orElseThrow(
					() -> new ResourceNotFoundException("Municipio", "id", empresaFilialRequest.getMunicipioId()));

			empresaFilial.setMunicipio(municipio);
		} else
			empresaFilial.setMunicipio(null);

		if (Objects.nonNull(empresaFilialRequest.getCnpj()) && !empresaFilialRequest.getCnpj().isEmpty()
				&& Objects.nonNull(empresaFilialRequest.getCei()) && !empresaFilialRequest.getCei().isEmpty()) {
			empresaFilial.setTipoInscricao(TipoInscricaoEnum.CNPJ_CEI);
		} else if (Objects.nonNull(empresaFilialRequest.getCnpj()) && !empresaFilialRequest.getCnpj().isEmpty()) {
			empresaFilial.setTipoInscricao(TipoInscricaoEnum.CNPJ);
		} else if (Objects.nonNull(empresaFilialRequest.getCei()) && !empresaFilialRequest.getCei().isEmpty()) {
			empresaFilial.setTipoInscricao(TipoInscricaoEnum.CEI);
		}

	}

	public List<EmpresaFilialResponse> getAllEmpresasFiliais() {

		List<EmpresaFilial> empresasFiliais = empresaFilialRepository.findAll();
		List<EmpresaFilialResponse> empresaFilialResponse = new ArrayList<>();

		if (!empresasFiliais.isEmpty()) {
			for (EmpresaFilial empresaFilial : empresasFiliais) {
				EmpresaFilialResponse empresasFiliaisResp = new EmpresaFilialResponse(empresaFilial);

				empresaFilialResponse.add(empresasFiliaisResp);
			}
			return empresaFilialResponse;
		}

		return null;
	}

	public List<DadoBasicoDto> getEmpresaFilialSearchList() {
		List<EmpresaFilial> empresasFiliais = empresaFilialRepository.findByEmpresaMatriz(false);
		List<DadoBasicoDto> dtoList = new ArrayList<>();

		if (!empresasFiliais.isEmpty()) {
			for (EmpresaFilial empresaFilial : empresasFiliais) {
				dtoList.add(new DadoBasicoDto(empresaFilial));
			}
			return dtoList;
		}

		return null;
	}

	public List<EmpresaFilialResponse> getAllFiliais() {
		List<EmpresaFilial> empresasFiliais = empresaFilialRepository.findByEmpresaMatriz(false);
		List<EmpresaFilialResponse> empresaFilialResponse = new ArrayList<>();

		if (!empresasFiliais.isEmpty()) {
			for (EmpresaFilial empresaFilial : empresasFiliais) {
				EmpresaFilialResponse empresasFiliaisResp = new EmpresaFilialResponse(empresaFilial);
				empresaFilialResponse.add(empresasFiliaisResp);
			}
			return empresaFilialResponse;
		}

		return null;
	}

	public PagedResponse<EmpresaFilialResponse> getAllEmpresasFiliaisSemMatrizPaginado(int page, int size, String order,
			String siglaNome) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<EmpresaFilial> empresasFiliais = null;

		if (Utils.checkStr(siglaNome))
			empresasFiliais = empresaFilialRepository.findByEmpresaMatriz(false, siglaNome, pageable);
		else
			empresasFiliais = empresaFilialRepository.findByEmpresaMatriz(false, pageable);

		if (empresasFiliais.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), empresasFiliais.getNumber(), empresasFiliais.getSize(),
					empresasFiliais.getTotalElements(), empresasFiliais.getTotalPages(), empresasFiliais.isLast());
		}

		List<EmpresaFilialResponse> empresasFiliaisResponse = empresasFiliais.map(empresaFilial -> {
			return new EmpresaFilialResponse(empresaFilial);
		}).getContent();

		for (EmpresaFilialResponse empresaFilialResponse : empresasFiliaisResponse) {
			if (Objects.nonNull(
					definicaoOrganicoService.selectDefinicaoOrganicoByEmpresaFilialId(empresaFilialResponse.getId()))) {
				DefinicaoOrganicoResponse definicaoOrganica = definicaoOrganicoService
						.selectDefinicaoOrganicoByEmpresaFilialId(empresaFilialResponse.getId());
				if (Objects.nonNull(definicaoOrganica)) {
					empresaFilialResponse.setDefinicaoOrganicoId(definicaoOrganica.getId());
				}
			}
		}

		return new PagedResponse<>(empresasFiliaisResponse, empresasFiliais.getNumber(), empresasFiliais.getSize(),
				empresasFiliais.getTotalElements(), empresasFiliais.getTotalPages(), empresasFiliais.isLast());

	}

	public List<EmpresaFilialResponse> getAllEmpresasFiliaisSemMatriz() {

		List<EmpresaFilial> empresasFiliais = empresaFilialRepository.findByEmpresaMatriz(false);
		List<EmpresaFilialResponse> empresaFilialResponse = new ArrayList<>();

		if (!empresasFiliais.isEmpty()) {
			for (EmpresaFilial empresaFilial : empresasFiliais) {
				EmpresaFilialResponse empresasFiliaisResp = new EmpresaFilialResponse(empresaFilial);

				empresaFilialResponse.add(empresasFiliaisResp);
			}
			return empresaFilialResponse;
		}

		return null;
	}

	public List<EmpresaFilialResponse> getAllEmpresaFilialByNome(String search) {
		List<EmpresaFilial> empresasFiliais = empresaFilialRepository.findBySiglaOrNomeFilial(search);
		List<EmpresaFilialResponse> response = new ArrayList<>();

		if (!empresasFiliais.isEmpty()) {
			for (EmpresaFilial empresaFilial : empresasFiliais) {

				response.add(new EmpresaFilialResponse(empresaFilial));
			}
		}

		return response;
	}

	public EmpresaFilial getEmpresaMatriz() {
		return empresaFilialRepository.findFirstByEmpresaMatriz(true);
	}

	public Page<EmpresaFilial> findAllFiliais(Pageable pageable) {
		return empresaFilialRepository.findByEmpresaMatriz(false, pageable);
	}

}