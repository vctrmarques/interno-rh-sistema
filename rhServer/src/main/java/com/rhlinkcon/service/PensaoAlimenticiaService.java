package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.FuncionarioFiltro;
import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.DadoBancario;
import com.rhlinkcon.model.Endereco;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.PensaoAlimenticia;
import com.rhlinkcon.model.ResponsavelLegal;
import com.rhlinkcon.model.TipoIncidenciaPrincipalPensaoEnum;
import com.rhlinkcon.model.TipoPensaoEnum;
import com.rhlinkcon.model.TipoValorEnum;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.pensaoAlimenticia.PensaoAlimenticiaDto;
import com.rhlinkcon.payload.pensaoAlimenticia.PensaoAlimenticiaFuncionarioDto;
import com.rhlinkcon.payload.pensaoAlimenticia.PensaoAlimenticiaResponsavelDto;
import com.rhlinkcon.repository.PensaoAlimenticiaRepository;
import com.rhlinkcon.repository.ResponsavelLegalRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class PensaoAlimenticiaService extends GenericService<PensaoAlimenticiaDto, Long> {
	@Autowired
	private PensaoAlimenticiaRepository repository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private ResponsavelLegalRepository responsavelLegalRepository;

	@Override
	public void create(PensaoAlimenticiaDto request) {
		PensaoAlimenticia pensaoAlimenticia = new PensaoAlimenticia();
		setEntity(pensaoAlimenticia, request);
		repository.save(pensaoAlimenticia);
	}

	@Override
	public void update(PensaoAlimenticiaDto request) {
		PensaoAlimenticia pensaoAlimenticia = repository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException("PensaoAlimenticia", "id", request.getId()));
		setEntity(pensaoAlimenticia, request);
		repository.save(pensaoAlimenticia);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public PensaoAlimenticiaDto getById(Long id) {
		PensaoAlimenticia pensaoAlimenticia = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PensaoAlimenticia", "id", id));

		PensaoAlimenticiaDto pensaoAlimenticiaResponse = new PensaoAlimenticiaDto(pensaoAlimenticia);
		usuarioService.setDadoCadastral(pensaoAlimenticiaResponse, pensaoAlimenticia);
		return pensaoAlimenticiaResponse;
	}

	@Override
	public PagedResponse<PensaoAlimenticiaDto> getPagedList(PagedRequest pagedRequest,
			PensaoAlimenticiaDto requestFilter) {

		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Page<PensaoAlimenticia> pageableResult = repository.findAll(pageable);

		PagedResponse<PensaoAlimenticiaDto> result = new PagedResponse<>(Collections.emptyList(),
				pageableResult.getNumber(), pageableResult.getSize(), pageableResult.getTotalElements(),
				pageableResult.getTotalPages(), pageableResult.isLast());

		if (pageableResult.getNumberOfElements() > 0) {
			List<PensaoAlimenticiaDto> dtoResultList = pageableResult.map(pensaoAlimenticia -> {
				return new PensaoAlimenticiaDto(pensaoAlimenticia, Projecao.BASICA);
			}).getContent();
			result.setContent(dtoResultList);
		}

		return result;
	}

	@Override
	public List<PensaoAlimenticiaDto> getList(PensaoAlimenticiaDto requestFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DadoBasicoDto> getDadoBasicoList(PensaoAlimenticiaDto requestFilter) {
		List<DadoBasicoDto> dadoBasicoList = new ArrayList<DadoBasicoDto>();

		List<PensaoAlimenticia> pensaoAlimenticiaList = repository.findAll();

		pensaoAlimenticiaList.forEach(persistentId -> {
			dadoBasicoList.add(new DadoBasicoDto(persistentId));
		});

		return dadoBasicoList;
	}

	public List<PensaoAlimenticiaFuncionarioDto> funcionarioSearch(String search) {

		// Montando filtro de busca
		FuncionarioFiltro funcionarioFiltro = new FuncionarioFiltro();
		funcionarioFiltro.setSearch(search);

		// Efetuando busca.
		List<Funcionario> funcionarios = funcionarioRepository.filtro(funcionarioFiltro);

		if (Objects.nonNull(funcionarios) && !funcionarios.isEmpty()) {
			List<PensaoAlimenticiaFuncionarioDto> dtoListResult = new ArrayList<>();
			for (Funcionario funcionario : funcionarios) {
				PensaoAlimenticiaFuncionarioDto dtoResult = new PensaoAlimenticiaFuncionarioDto(funcionario);
				dtoListResult.add(dtoResult);
			}
			return dtoListResult;
		}

		return null;
	}

	public List<PensaoAlimenticiaResponsavelDto> responsavelSearch(String search) {

		// Efetuando busca.
		List<ResponsavelLegal> responsaveis = responsavelLegalRepository.findByNomeIgnoreCaseContaining(search);

		if (Objects.nonNull(responsaveis) && !responsaveis.isEmpty()) {
			List<PensaoAlimenticiaResponsavelDto> dtoListResult = new ArrayList<>();
			for (ResponsavelLegal responsavelLegal : responsaveis) {
				PensaoAlimenticiaResponsavelDto dtoResult = new PensaoAlimenticiaResponsavelDto(responsavelLegal);
				dtoListResult.add(dtoResult);
			}
			return dtoListResult;
		}

		return null;
	}

	private void setEntity(PensaoAlimenticia pensaoAlimenticia, PensaoAlimenticiaDto request) {
		// Bloco Inicial

		pensaoAlimenticia.setFuncionario(new Funcionario(request.getFuncionario().getId()));

		// Bloco do Alimentando

		pensaoAlimenticia.setNomeAlimentando(request.getNomeAlimentando());
		pensaoAlimenticia.setNascimentoAlimentando(request.getNascimentoAlimentando());
		pensaoAlimenticia.setTipoPensao(TipoPensaoEnum.getEnumByString(request.getTipoPensao()));
		pensaoAlimenticia.setRg(request.getRg());
		pensaoAlimenticia.setOrgao(request.getOrgao());
		pensaoAlimenticia.setEstadoDocumento(new UnidadeFederativa(request.getEstadoDocumento().getId()));
		pensaoAlimenticia.setCpf(request.getCpf());
		pensaoAlimenticia.setNumeroTelefone(request.getNumeroTelefone());
		pensaoAlimenticia.setEnderecoAlimentando(new Endereco(request.getEnderecoAlimentando()));

		// Bloco do Responsável Legal

		if (Objects.nonNull(request.getResponsavel()) && !request.getResponsavel().getId().equals(0L)) {
			pensaoAlimenticia.setResponsavel(new ResponsavelLegal(request.getResponsavel().getId()));
			pensaoAlimenticia.setNumeroProcessoResponsavel(request.getNumeroProcessoResponsavel());
			pensaoAlimenticia.setDataInicial(request.getDataInicial());
			pensaoAlimenticia.setDataFinal(request.getDataFinal());
		}

		// Bloco de Pagamento Parte 1

		pensaoAlimenticia.setDataInicialPagamento(request.getDataInicialPagamento());
		pensaoAlimenticia.setDataFinalPagamento(request.getDataFinalPagamento());
		pensaoAlimenticia.setCentroCusto(new CentroCusto(request.getCentroCusto().getId()));
		pensaoAlimenticia.setVerba(new Verba(request.getVerba().getId()));
		pensaoAlimenticia.setDadoBancario(new DadoBancario(request.getDadoBancario()));

		// Bloco de Pagamento Parte 2

		pensaoAlimenticia.setNumeroProcessoPagamento(request.getNumeroProcessoPagamento());
		pensaoAlimenticia.setVencimento(request.getVencimento());
		pensaoAlimenticia.setTipoValor(TipoValorEnum.getEnumByString(request.getTipoValor()));
		pensaoAlimenticia.setValor(request.getValor());
		pensaoAlimenticia.setSalarioFamilia(request.getSalarioFamilia());

		if (Utils.checkStr(request.getTipoIncidenciaPrincipalPensao()))
			pensaoAlimenticia.setTipoIncidenciaPrincipalPensao(
					TipoIncidenciaPrincipalPensaoEnum.getEnumByString(request.getTipoIncidenciaPrincipalPensao()));

		if (!pensaoAlimenticia.getTipoValor().equals(TipoValorEnum.MOEDA)
				&& Objects.isNull(pensaoAlimenticia.getTipoIncidenciaPrincipalPensao()))
			throw new BadRequestException("Favor selecionar o tipo de incidência principal!");

		if (pensaoAlimenticia.getTipoValor().equals(TipoValorEnum.MOEDA))
			pensaoAlimenticia.setTipoIncidenciaPrincipalPensao(null);

		pensaoAlimenticia.setSalario13(request.isSalario13());
		pensaoAlimenticia.setFerias(request.isFerias());
		pensaoAlimenticia.setRecisao(request.isRecisao());

	}

	public PagedResponse<FuncionarioResponse> getFuncionariosPensoes(PagedRequest pagedRequest,
			String nomeFuncionario) {

		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Page<Funcionario> funcionarios = null;

		if (Utils.checkStr(nomeFuncionario))
			funcionarios = funcionarioRepository.findByFuncionarioCountPensaoLikeFuncionarioNome(pageable,
					nomeFuncionario);
		else
			funcionarios = funcionarioRepository.findByFuncionarioCountPensoes(pageable);

		List<FuncionarioResponse> funcionariosResponse = new ArrayList<FuncionarioResponse>();

		for (Funcionario funcionario : funcionarios) {
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse(funcionario,
					funcionario.getPensoes().stream().map(pensao -> new PensaoAlimenticiaDto(pensao, Projecao.BASICA))
							.collect(Collectors.toList()));
			funcionariosResponse.add(funcionarioResponse);
		}

		return new PagedResponse<>(funcionariosResponse, funcionarios.getNumber(), funcionarios.getSize(),
				funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());
	}

}
