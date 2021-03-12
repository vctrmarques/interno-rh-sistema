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
import com.rhlinkcon.model.Nacionalidade;
import com.rhlinkcon.model.CategoriaProfissional;
import com.rhlinkcon.model.Cbo;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.model.Vinculo;
import com.rhlinkcon.model.Convenio;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.PrestadorServico;
import com.rhlinkcon.model.ProcessoFuncao;
import com.rhlinkcon.model.EstadoCivilEnum;
import com.rhlinkcon.model.SexoPrestadorEnum;
import com.rhlinkcon.model.CorPrestadorEnum;
import com.rhlinkcon.model.UnidadePagamentoEnum;
import com.rhlinkcon.model.NaturezaAtividadeEnum;
import com.rhlinkcon.model.TempoContribuicaoEnum;
import com.rhlinkcon.model.ModoPagamentoEnum;
import com.rhlinkcon.model.CategoriaCnhEnum;
import com.rhlinkcon.model.TipoDependenteEnum;
import com.rhlinkcon.model.LocacaoPrestadorEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.prestadorServico.PrestadorServicoRequest;
import com.rhlinkcon.payload.prestadorServico.PrestadorServicoResponse;
import com.rhlinkcon.payload.vinculo.VinculoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.repository.NacionalidadeRepository;
import com.rhlinkcon.repository.CboRepository;
import com.rhlinkcon.repository.CategoriaProfissionalRepository;
import com.rhlinkcon.repository.PrestadorServicoRepository;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.MunicipioRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.ConvenioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class PrestadorServicoService {

	@Autowired
	private PrestadorServicoRepository prestadorServicoRepository;
	
	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CboRepository cboRepository;
	
	@Autowired
	private CategoriaProfissionalRepository categoriaProfissionalRepository;
	
	@Autowired
	private NacionalidadeRepository nacionalidadeRepository;
	
	@Autowired
	private UnidadeFederativaRepository ufRepository;
	
	@Autowired
	private VerbaRepository verbaRepository;
	
	@Autowired
	private ConvenioRepository convenioRepository;
	
	@Autowired
	private MunicipioRepository municipioRepository;
	
//	public List<PrestadorServicoResponse> getAllPrestadoresServicos() {
//		List<PrestadorServico> prestadoresServicos = prestadorServicoRepository.findAll();
//
//		List<PrestadorServicoResponse> listPrestadorServicoResponse = new ArrayList<>();
//		for (PrestadorServico prestadorServico : prestadoresServicos) {
//			listPrestadorServicoResponse.add(new PrestadorServicoResponse(prestadorServico));
//		}
//		return listPrestadorServicoResponse;
//	}
//	
//	public List<PrestadorServicoResponse> searchAllByNomeCivil(String search) {
//		List<PrestadorServicoResponse> itensResponses = new ArrayList<PrestadorServicoResponse>();
//		prestadorServicoRepository.findByNomeCivilIgnoreCaseContaining(search, null)
//				.forEach(item -> itensResponses.add(new PrestadorServicoResponse(item)));
//		return itensResponses;
//	}

	public void createPrestadorServico(PrestadorServicoRequest prestadorServicoRequest) {

		PrestadorServico prestadorServico = new PrestadorServico(prestadorServicoRequest);
		
		setEntidades(prestadorServicoRequest, prestadorServico);

		prestadorServicoRepository.save(prestadorServico);

	}

	public void updatePrestadorServico(PrestadorServicoRequest prestadorServicoRequest) {

		PrestadorServico prestadorServico = prestadorServicoRepository.findById(prestadorServicoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("PrestadorServico", "id", prestadorServicoRequest.getId()));
		
		setEntidades(prestadorServicoRequest, prestadorServico);
			
		prestadorServico.setNomeCivil(prestadorServicoRequest.getNomeCivil());
		prestadorServico.setNomeSocial(prestadorServicoRequest.getNomeSocial());
		prestadorServico.setCpf(prestadorServicoRequest.getCpf());
		prestadorServico.setDataNascimento(prestadorServicoRequest.getDataNascimento());

		if (Utils.checkStr(prestadorServicoRequest.getEstadoCivil()))
			prestadorServico.setEstadoCivil(EstadoCivilEnum.getEnumByString(prestadorServicoRequest.getEstadoCivil()));

		if (Utils.checkStr(prestadorServicoRequest.getSexo()))
			prestadorServico.setSexo(SexoPrestadorEnum.getEnumByString(prestadorServicoRequest.getSexo()));

		if (Utils.checkStr(prestadorServicoRequest.getCor()))
			prestadorServico.setCor(CorPrestadorEnum.getEnumByString(prestadorServicoRequest.getCor()));

		prestadorServico.setNomeMae(prestadorServicoRequest.getNomeMae());
		prestadorServico.setNomePai(prestadorServicoRequest.getNomePai());
		prestadorServico.setLogradouro(prestadorServicoRequest.getLogradouro());
		prestadorServico.setNumero(prestadorServicoRequest.getNumero());
		prestadorServico.setComplemento(prestadorServicoRequest.getComplemento());
		prestadorServico.setBairro(prestadorServicoRequest.getBairro());
		prestadorServico.setCep(prestadorServicoRequest.getCep());
		prestadorServico.setDddCelular(prestadorServicoRequest.getDddCelular());
		prestadorServico.setNumeroCelular(prestadorServicoRequest.getNumeroCelular());
		prestadorServico.setDddTelefone(prestadorServicoRequest.getDddTelefone());
		prestadorServico.setTelefone(prestadorServicoRequest.getTelefone());
		
		if (Utils.checkStr(prestadorServicoRequest.getLocacao()))
			prestadorServico.setLocacao(LocacaoPrestadorEnum.getEnumByString(prestadorServicoRequest.getLocacao()));
		
		prestadorServico.setDataInicial(prestadorServicoRequest.getDataInicial());
		prestadorServico.setDataFinal(prestadorServicoRequest.getDataFinal());

		if (Utils.checkStr(prestadorServicoRequest.getUnidadePagamento()))
			prestadorServico.setUnidadePagamento(UnidadePagamentoEnum.getEnumByString(prestadorServicoRequest.getUnidadePagamento()));
		else
			prestadorServico.setUnidadePagamento(null);
		
		if (Utils.checkStr(prestadorServicoRequest.getNaturezaAtividade()))
			prestadorServico.setNaturezaAtividade(NaturezaAtividadeEnum.getEnumByString(prestadorServicoRequest.getNaturezaAtividade()));
		else
			prestadorServico.setNaturezaAtividade(null);
		
		prestadorServico.setAgenteNocivo(Utils.setBool(prestadorServicoRequest.isAgenteNocivo()));
		prestadorServico.setExposicaoPassado(Utils.setBool(prestadorServicoRequest.isExposicaoPassado()));

		if (Utils.checkStr(prestadorServicoRequest.getTempoContribuicao()))
			prestadorServico.setTempoContribuicao(TempoContribuicaoEnum.getEnumByString(prestadorServicoRequest.getTempoContribuicao()));
		else
			prestadorServico.setTempoContribuicao(null);
			
		prestadorServico.setRecolheInss(Utils.setBool(prestadorServicoRequest.isRecolheInss()));
		
		if (Utils.checkStr(prestadorServicoRequest.getModoPagamento()))
			prestadorServico.setModoPagamento(ModoPagamentoEnum.getEnumByString(prestadorServicoRequest.getModoPagamento()));
		else
			prestadorServico.setModoPagamento(null);
		
		prestadorServico.setValorPago(prestadorServicoRequest.getValorPago());
		prestadorServico.setDescontoInss(prestadorServicoRequest.getDescontoInss());
		prestadorServico.setBaseCalculo(prestadorServicoRequest.getBaseCalculo());
		prestadorServico.setDescontoIr(prestadorServicoRequest.getDescontoIr());
		prestadorServico.setRegistroFgts(prestadorServicoRequest.getRegistroFgts());
		prestadorServico.setCnpj(prestadorServicoRequest.getCnpj());
		prestadorServico.setIrRetido(prestadorServicoRequest.getIrRetido());
		prestadorServico.setInssPago(prestadorServicoRequest.getInssPago());
		prestadorServico.setCnpjEmpresaPagadora(prestadorServicoRequest.getCnpjEmpresaPagadora());
		prestadorServico.setDiaPagamento(prestadorServicoRequest.getDiaPagamento());
		prestadorServico.setNumeroEmpenho(prestadorServicoRequest.getNumeroEmpenho());
		prestadorServico.setDataEmpenho(prestadorServicoRequest.getDataEmpenho());
		prestadorServico.setNumeroNotaFiscal(prestadorServicoRequest.getNumeroNotaFiscal());
		prestadorServico.setDataEmissaoNf(prestadorServicoRequest.getDataEmissaoNf());
		prestadorServico.setPisPasep(prestadorServicoRequest.getPisPasep());
		prestadorServico.setNumeroCtps(prestadorServicoRequest.getNumeroCtps());
		prestadorServico.setSerieCtps(prestadorServicoRequest.getSerieCtps());
		prestadorServico.setNumeroIdentCivilNac(prestadorServicoRequest.getNumeroIdentCivilNac());
		prestadorServico.setOrgEmissorIdentCivilNac(prestadorServicoRequest.getOrgEmissorIdentCivilNac());
		prestadorServico.setDataEmissaoIdentCivilNac(prestadorServicoRequest.getDataEmissaoIdentCivilNac());
		prestadorServico.setNumeroRg(prestadorServicoRequest.getNumeroRg());	
		prestadorServico.setOrgaoEmissorRg(prestadorServicoRequest.getOrgaoEmissorRg());
		prestadorServico.setDataEmissaoRg(prestadorServicoRequest.getDataEmissaoRg());
		prestadorServico.setNumeroRegNacEstrangeiro(prestadorServicoRequest.getNumeroRegNacEstrangeiro());
		prestadorServico.setOrgaoEmissorRegNacEstrangeiro(prestadorServicoRequest.getOrgaoEmissorRegNacEstrangeiro());
		prestadorServico.setDataEmissaoRegNacEstrangeiro(prestadorServicoRequest.getDataEmissaoRegNacEstrangeiro());
		prestadorServico.setRegistroProfissional(prestadorServicoRequest.getRegistroProfissional());
		prestadorServico.setDataEmissaoRegProfissional(prestadorServicoRequest.getDataEmissaoRegProfissional());
		prestadorServico.setDataValidadeRegProfissional(prestadorServicoRequest.getDataValidadeRegProfissional());
		prestadorServico.setNumeroCnh(prestadorServicoRequest.getNumeroCnh());
		prestadorServico.setDataValidadeCnh(prestadorServicoRequest.getDataValidadeCnh());
		prestadorServico.setDataPrimeiraCnh(prestadorServicoRequest.getDataPrimeiraCnh());
		prestadorServico.setDataEmissaoCnh(prestadorServicoRequest.getDataEmissaoCnh());

		if (Utils.checkStr(prestadorServicoRequest.getCategoriaCnh()))
			prestadorServico.setCategoriaCnh(CategoriaCnhEnum.getEnumByString(prestadorServicoRequest.getCategoriaCnh()));
		else
			prestadorServico.setCategoriaCnh(null);
			
		if (Utils.checkStr(prestadorServicoRequest.getTipoDependente()))
			prestadorServico.setTipoDependente(TipoDependenteEnum.getEnumByString(prestadorServicoRequest.getTipoDependente()));
		else
			prestadorServico.setTipoDependente(null);
		
		prestadorServico.setNomeDependente(prestadorServicoRequest.getNomeDependente());
		prestadorServico.setCpfDependente(prestadorServicoRequest.getCpfDependente());
		prestadorServico.setImpostoRetidoFonte(Utils.setBool(prestadorServicoRequest.isImpostoRetidoFonte()));
		prestadorServico.setPlanoSaudePrivado(Utils.setBool(prestadorServicoRequest.isPlanoSaudePrivado()));
		prestadorServico.setRecebeBeneficio(Utils.setBool(prestadorServicoRequest.isRecebeBeneficio()));
		prestadorServico.setCapacitacaoProfissional(Utils.setBool(prestadorServicoRequest.isCapacitacaoProfissional()));

		setEntidades(prestadorServicoRequest, prestadorServico);
		
		prestadorServicoRepository.save(prestadorServico);

	}

	public void deletePrestadorServico(Long id) {
		PrestadorServico prestadorServico = prestadorServicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("prestadorServico", "id", id));

		prestadorServicoRepository.delete(prestadorServico);
	}

	public PagedResponse<PrestadorServicoResponse> getAllPrestadoresServicos(int page, int size, String nomeCivil, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<PrestadorServico> prestadoresServicos = null;

		if (Utils.checkStr(nomeCivil) ) {
			prestadoresServicos = prestadorServicoRepository.findByNomeCivilIgnoreCaseContaining(nomeCivil, pageable);
		}else {
			prestadoresServicos = prestadorServicoRepository.findAll(pageable);
		}


		if (prestadoresServicos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), prestadoresServicos.getNumber(), prestadoresServicos.getSize(),
					prestadoresServicos.getTotalElements(), prestadoresServicos.getTotalPages(), prestadoresServicos.isLast());
		}

		List<PrestadorServicoResponse> prestadorServicoResponses = prestadoresServicos.map(prestadorServico -> {
			return new PrestadorServicoResponse(prestadorServico);
		}).getContent();
		return new PagedResponse<>(prestadorServicoResponses, prestadoresServicos.getNumber(), prestadoresServicos.getSize(),
				prestadoresServicos.getTotalElements(), prestadoresServicos.getTotalPages(), prestadoresServicos.isLast());

	}

	public PrestadorServicoResponse getPrestadorServicoById(Long prestadorServicoId) {
		PrestadorServico prestadorServico = prestadorServicoRepository.findById(prestadorServicoId)
				.orElseThrow(() -> new ResourceNotFoundException("PrestadorServico", "id", prestadorServicoId));

		Usuario userCreated = usuarioRepository.findById(prestadorServico.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", prestadorServico.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(prestadorServico.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", prestadorServico.getUpdatedBy()));

		PrestadorServicoResponse prestadorServicoResponse = new PrestadorServicoResponse(prestadorServico, prestadorServico.getCreatedAt(),
				userCreated.getNome(), prestadorServico.getUpdatedAt(), userUpdated.getNome());

		return prestadorServicoResponse;
	}
	
	private void setEntidades(PrestadorServicoRequest prestadorServicoRequest, PrestadorServico prestadorServico) {
		
		if(Objects.nonNull(prestadorServicoRequest.getUfIdEndereco())) {
			UnidadeFederativa ufEndereco = ufRepository.findById(prestadorServicoRequest.getUfIdEndereco())
					.orElseThrow(() -> new ResourceNotFoundException("UnidadeFederativa", "id", prestadorServicoRequest.getUfIdEndereco()));
			
			prestadorServico.setUfEndereco(ufEndereco);
		}
		
		if(Objects.nonNull(prestadorServicoRequest.getUfIdCtps())) {
			UnidadeFederativa ufCtps = ufRepository.findById(prestadorServicoRequest.getUfIdCtps())
					.orElseThrow(() -> new ResourceNotFoundException("UnidadeFederativa", "id", prestadorServicoRequest.getUfIdCtps()));
			
			prestadorServico.setUfCtps(ufCtps);
		}
		
		if(Objects.nonNull(prestadorServicoRequest.getUfIdCnh())) {
			UnidadeFederativa ufCnh = ufRepository.findById(prestadorServicoRequest.getUfIdCnh())
					.orElseThrow(() -> new ResourceNotFoundException("UnidadeFederativa", "id", prestadorServicoRequest.getUfIdCnh()));
			
			prestadorServico.setUfCnh(ufCnh);
		}else
			prestadorServico.setUfCnh(null);
		
		if(Objects.nonNull(prestadorServicoRequest.getMunicipioId())) {
			Municipio municipio = municipioRepository.findById(prestadorServicoRequest.getMunicipioId())
					.orElseThrow(() -> new ResourceNotFoundException("Municipio", "id", prestadorServicoRequest.getMunicipioId()));
			
			prestadorServico.setMunicipio(municipio);
		}
		
		if (Objects.nonNull(prestadorServicoRequest.getNacionalidadeId())) {
			Nacionalidade nacionalidade = nacionalidadeRepository.findById(prestadorServicoRequest.getNacionalidadeId())
					.orElseThrow(() -> new ResourceNotFoundException("Nacionalidade", "id", prestadorServicoRequest.getNacionalidadeId()));
			
			prestadorServico.setNacionalidade(nacionalidade);
		}
		
		if(Objects.nonNull(prestadorServicoRequest.getEmpresaFilialId())) {
			EmpresaFilial empresaFilial = empresaFilialRepository.findById(prestadorServicoRequest.getEmpresaFilialId())
					.orElseThrow(() -> new ResourceNotFoundException("EmpresaFilial", "id", prestadorServicoRequest.getEmpresaFilialId()));
			
			prestadorServico.setEmpresaFilial(empresaFilial);
		}else
			prestadorServico.setEmpresaFilial(null);
		
		if(Objects.nonNull(prestadorServicoRequest.getCategoriaProfissionalId())) {
			CategoriaProfissional categoriaProfissional = categoriaProfissionalRepository.findById(prestadorServicoRequest.getCategoriaProfissionalId())
					.orElseThrow(() -> new ResourceNotFoundException("CategoriaProfissional", "id", prestadorServicoRequest.getCategoriaProfissionalId()));
			
			prestadorServico.setCategoriaProfissional(categoriaProfissional);
		}
		
		if(Objects.nonNull(prestadorServicoRequest.getCboId())) {
			Cbo cbo = cboRepository.findById(prestadorServicoRequest.getCboId())
					.orElseThrow(() -> new ResourceNotFoundException("Cbo", "id", prestadorServicoRequest.getCboId()));
			
			prestadorServico.setCbo(cbo);
		}
		
		if(Objects.nonNull(prestadorServicoRequest.getVerbaId())) {
			Verba verba = verbaRepository.findById(prestadorServicoRequest.getVerbaId())
					.orElseThrow(() -> new ResourceNotFoundException("Verba", "id", prestadorServicoRequest.getVerbaId()));
			
			prestadorServico.setVerba(verba);
		}else
			prestadorServico.setVerba(null);
		
		if (Objects.nonNull(prestadorServicoRequest.getConvenioId())) {
			Convenio convenio = convenioRepository.findById(prestadorServicoRequest.getConvenioId())
					.orElseThrow(() -> new ResourceNotFoundException("Convenio", "id", prestadorServicoRequest.getConvenioId()));
			
			prestadorServico.setConvenio(convenio);
		}else
			prestadorServico.setConvenio(null);
		
	}

}
