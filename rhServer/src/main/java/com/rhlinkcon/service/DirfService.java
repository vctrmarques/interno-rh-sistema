package com.rhlinkcon.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.DirfFiltro;
import com.rhlinkcon.filtro.DirfPdfFiltro;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.AnexoPastaEnum;
import com.rhlinkcon.model.Dirf;
import com.rhlinkcon.model.DirfResponsavelReceita;
import com.rhlinkcon.model.DirfSituacao;
import com.rhlinkcon.model.DirfTipoDeclaracaoEnum;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.dirf.DirfArquivoDto;
import com.rhlinkcon.payload.dirf.DirfBeneficiarioDto;
import com.rhlinkcon.payload.dirf.DirfDto;
import com.rhlinkcon.payload.dirf.DirfEmpresaFilialDto;
import com.rhlinkcon.payload.dirf.DirfInformesDto;
import com.rhlinkcon.payload.dirf.DirfResponsavelReceitaDto;
import com.rhlinkcon.payload.dirf.ProjecaoDirfBeneficiario;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.dirf.DirfRepository;
import com.rhlinkcon.repository.dirf.DirfResponsavelReceitaRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;
import com.rhlinkcon.util.dirf.GeradorDirf;

@Service
public class DirfService extends GenericService<DirfDto, Long> {

	@Autowired
	private DirfRepository repository;

	@Autowired
	private DirfResponsavelReceitaRepository responsavelRepository;

	@Autowired
	private EmpresaFilialService empresaFilialService;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private ContrachequeService contrachequeService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AnexoService anexoService;

	@Override
	@Transactional
	public void create(DirfDto dto) {
		repository.save(mountObj(dto));
	}

	@Override
	@Transactional
	public void update(DirfDto dto) {
		repository.save(mountObj(dto));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public DirfDto getById(Long id) {
		Dirf dirf = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dirf", "id", id));

		DirfDto dto = new DirfDto(dirf);
		usuarioService.setDadoCadastral(dto, dirf);

		return dto;
	}

	@Override
	public PagedResponse<DirfDto> getPagedList(PagedRequest pagedRequest, DirfDto requestFilter) {
		return null;
	}

	public PagedResponse<DirfDto> getPagedList(PagedRequest pagedRequest, DirfFiltro filtro) {
		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Page<Dirf> pageableResult = repository.filtro(filtro, pageable);

		PagedResponse<DirfDto> result = new PagedResponse<>(Collections.emptyList(), pageableResult.getNumber(),
				pageableResult.getSize(), pageableResult.getTotalElements(), pageableResult.getTotalPages(),
				pageableResult.isLast());

		if (pageableResult.getNumberOfElements() > 0) {
			List<DirfDto> dtoResultList = pageableResult.map(dirf -> {
				return new DirfDto(dirf, Projecao.BASICA);
			}).getContent();

			result.setContent(dtoResultList);
		}

		return result;
	}

	@Override
	public List<DirfDto> getList(DirfDto requestFilter) {
		return null;
	}

	@Override
	public List<DadoBasicoDto> getDadoBasicoList(DirfDto requestFilter) {
		return null;
	}

	private Dirf mountObj(DirfDto dto) {
		Dirf obj = new Dirf();
		obj.setId(dto.getId());
		obj.setAnoBase(dto.getAnoBase());
		obj.setTipoDeclaracao(DirfTipoDeclaracaoEnum.getEnumByString(dto.getTipoDeclaracao()));
		obj.setNumeroDeclaracao(dto.getNumeroDeclaracao());
		obj.setFilial(mountFilial(dto.getFilial()));
		obj.setResponsavelReceita(mountAndSaveResponsavelReceita(dto.getResponsavelReceita()));
		obj.setNaturezaDeclarante(dto.getNaturezaDeclarante());
		if (Objects.isNull(dto.getSituacao()))
			obj.setSituacao(DirfSituacao.CRIADA);

		return obj;
	}

	private EmpresaFilial mountFilial(DirfEmpresaFilialDto filial) {
		EmpresaFilial obj = new EmpresaFilial();
		obj.setId(filial.getId());
		return obj;
	}

	@Transactional
	private DirfResponsavelReceita mountAndSaveResponsavelReceita(DirfResponsavelReceitaDto dto) {
		DirfResponsavelReceita obj = new DirfResponsavelReceita();
		obj.setId(dto.getId());
		obj.setCpf(dto.getCpf());
		obj.setNome(dto.getNome());
		obj.setDdd(dto.getDdd());
		obj.setNumeroTelefone(dto.getNumeroTelefone());
		obj.setRamal(dto.getRamal());
		obj.setEmail(dto.getEmail());

		responsavelRepository.save(obj);

		return obj;
	}

	public PagedResponse<DirfEmpresaFilialDto> getPagedListFilial(PagedRequest pagedRequest) {
		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Page<EmpresaFilial> pageableResult = empresaFilialService.findAllFiliais(pageable);

		PagedResponse<DirfEmpresaFilialDto> result = new PagedResponse<>(Collections.emptyList(),
				pageableResult.getNumber(), pageableResult.getSize(), pageableResult.getTotalElements(),
				pageableResult.getTotalPages(), pageableResult.isLast());

		if (pageableResult.getNumberOfElements() > 0) {
			List<DirfEmpresaFilialDto> dtoResultList = pageableResult.map(filial -> {
				return new DirfEmpresaFilialDto(filial);
			}).getContent();

			result.setContent(dtoResultList);
		}

		return result;
	}

	public List<DirfResponsavelReceitaDto> getAllResponsavelReceitaByCpf(String search) {
		List<DirfResponsavelReceita> lista = responsavelRepository.findByCpf(search);
		List<DirfResponsavelReceitaDto> dto = new ArrayList<>();
		if (Utils.checkList(lista)) {
			for (DirfResponsavelReceita r : lista) {
				dto.add(new DirfResponsavelReceitaDto(r));
			}
		}
		return dto;
	}

	public List<DirfInformesDto> getDirfFuncionario(DirfPdfFiltro filtro) {
		Dirf dirf = repository.findById(filtro.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Dirf", "id", filtro.getId()));

		List<ProjecaoDirfBeneficiario> projecao = contrachequeService.getProjecaoDirfFuncionarioContracheque(
				dirf.getAnoBase(), dirf.getFilial().getId(), filtro.getFuncionarioId());
		List<DirfInformesDto> lista = new ArrayList<>();

		Iterator<ProjecaoDirfBeneficiario> ite = projecao.iterator();

		while (ite.hasNext()) {
			lista.add(mountInformes(ite.next(), dirf));
		}

		return lista;
	}

	private DirfInformesDto mountInformes(ProjecaoDirfBeneficiario value, Dirf dirf) {
		// TODO - usar a validação para diferenciar valores, caso aposentado colocar
		// valores no item 4.1 e 4.3
		// boolean isAposentado = (value.getSituacao() == "APOSENTADO");

		DirfInformesDto dto = new DirfInformesDto();
		dto.setDirf(new DirfDto(dirf, Projecao.COMPLETA));
		dto.setNome(value.getNome());
		dto.setCpf(value.getCpf());
		dto.setTotalRendimentos(
				lancamentoService.dirfSomaValorVerbasFuncionarioByFiltros(dirf.getAnoBase(), dirf.getFilial().getId(),
						value.getId(), Arrays.asList("1101", "1022", "1157", "1240", "1032", "1215", "1107")));

		// item 3.2
		dto.setPrevidenciaOficial(lancamentoService.dirfSomaValorVerbasFuncionarioByFiltros(dirf.getAnoBase(),
				dirf.getFilial().getId(), value.getId(), Arrays.asList("7575", "2524")));

		// item 3.3
		dto.setPrevidenciaComplementar(0.00);

		// item 3.4
		dto.setPensaoAlimenticia(lancamentoService.dirfSomaValorVerbasFuncionarioByFiltros(dirf.getAnoBase(),
				dirf.getFilial().getId(), value.getId(), Arrays.asList("408")));

		// item 3.5
		dto.setImpostoRetido(lancamentoService.dirfSomaValorVerbasFuncionarioByFiltros(dirf.getAnoBase(),
				dirf.getFilial().getId(), value.getId(), Arrays.asList("4990")));

		dto.setParcelaIsenta(0.00); // item 4.1 - somar todos os meses de aposentadoria e multiplica por R$ 1.903,98
									// (Analisar a inclusao de uma verba para isso)
		dto.setDiariasAjudaCusto(0.00); // item 4.2
		dto.setPensaoProventosAposentadoria(0.00); // item 4.3 - somar todas as vantagens com incidência de imposto de
													// renda e
													// diminuir do item 4.1
		dto.setTextoOutros("");
		dto.setValorOutros(0.00);

		Double valorVerba656 = lancamentoService.dirfSomaValorVerbasFuncionarioByFiltros(dirf.getAnoBase(),
				dirf.getFilial().getId(), value.getId(), Arrays.asList("656"));
		Double valorVerba13Aniversario = lancamentoService.dirfSomaValorVerbasFuncionarioByFiltros(dirf.getAnoBase(),
				dirf.getFilial().getId(), value.getId(), Arrays.asList("98757", "35426"));

		dto.setDecimoTerceiro(getCalculoSubtracao(valorVerba656, valorVerba13Aniversario));

		dto.setImpostoSobreDecimoTerceiro(lancamentoService.dirfSomaValorVerbasFuncionarioByFiltros(dirf.getAnoBase(),
				dirf.getFilial().getId(), value.getId(), Arrays.asList("6356")));

		dto.setTextoOutrosDecimoTerceiro("");
		dto.setOutrosDecimoTerceiro(0.00);

		dto.setInformacoes("");

		return dto;
	}

	private Double getCalculoSubtracao(Double valor1, Double valor2) {
		if (Objects.nonNull(valor1) && Objects.nonNull(valor2)) {
			return valor1 - valor2;
		} else {
			if (Objects.nonNull(valor1)) {
				return valor1;
			} else if (Objects.nonNull(valor2)) {
				return valor2;
			} else {
				return 0.00;
			}
		}
	}

	public PagedResponse<DirfBeneficiarioDto> getPagedListBeneficiario(PagedRequest pagedRequest, Long dirfId,
			String query) {
		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Dirf dirf = repository.findById(dirfId).orElseThrow(() -> new ResourceNotFoundException("Dirf", "id", dirfId));

		List<ProjecaoDirfBeneficiario> lista = contrachequeService
				.getProjecaoDirfFuncionarioPensionista(dirf.getAnoBase(), dirf.getFilial().getId(), query);

		List<DirfBeneficiarioDto> listaDto = new ArrayList<>();

		for (ProjecaoDirfBeneficiario e : lista) {
			listaDto.add(new DirfBeneficiarioDto(e));
		}

		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > listaDto.size() ? listaDto.size()
				: (start + pageable.getPageSize());
		Page<DirfBeneficiarioDto> pageableResult = new PageImpl<>(listaDto.subList(start, end), pageable,
				listaDto.size());

		return new PagedResponse<>(
				pageableResult.getNumberOfElements() > 0 ? pageableResult.getContent() : Collections.emptyList(),
				pageableResult.getNumber(), pageableResult.getSize(), pageableResult.getTotalElements(),
				pageableResult.getTotalPages(), pageableResult.isLast());
	}

	public List<AnexoResponse> getAllOrCreateByDirfId(Long dirfId) throws IOException {
		Dirf dirf = repository.findById(dirfId).orElseThrow(() -> new ResourceNotFoundException("Dirf", "id", dirfId));

		List<ProjecaoDirfBeneficiario> lista = contrachequeService
				.getProjecaoDirfFuncionarioContracheque(dirf.getAnoBase(), dirf.getFilial().getId(), null);

		List<DirfArquivoDto> listaArquivo = new ArrayList<>();

		for (ProjecaoDirfBeneficiario e : lista) {
			listaArquivo.add(new DirfArquivoDto(e));
		}

		for (DirfArquivoDto a : listaArquivo) {
			a.setValoresRTRT(lancamentoService.dirfSomaValorMesVerbasFuncionarioByFiltros(dirf.getAnoBase(),
					dirf.getFilial().getId(), a.getBeneficiario().getId(),
					Arrays.asList("1101", "1022", "1157", "1240", "1032", "1215", "1107")));

			a.setValoresRTPO(lancamentoService.dirfSomaValorMesVerbasFuncionarioByFiltros(dirf.getAnoBase(),
					dirf.getFilial().getId(), a.getBeneficiario().getId(), Arrays.asList("7575", "2524")));

			a.setValoresRTIRF(lancamentoService.dirfSomaValorMesVerbasFuncionarioByFiltros(dirf.getAnoBase(),
					dirf.getFilial().getId(), a.getBeneficiario().getId(), Arrays.asList("4990")));
		}

		List<AnexoResponse> anexos = new ArrayList<>();

		GeradorDirf gerador = new GeradorDirf(dirf, listaArquivo);

		FlatFile<Record> record = gerador.gerar();

		Anexo anexo = anexoService.createAnexo(record, AnexoPastaEnum.DIRF, dirf.getAnoExercicio());

		if (Objects.nonNull(anexo)) {
			if (!Utils.checkList(dirf.getAnexos())) {
				dirf.setAnexos(new ArrayList<>());
			}
			dirf.getAnexos().add(anexo);
			dirf.setSituacao(DirfSituacao.GERADO);
			repository.save(dirf);
			dirf.getAnexos().forEach(e -> anexos.add(new AnexoResponse(e)));
		} else {
			dirf.setSituacao(DirfSituacao.ERRO_GERACAO);
			repository.save(dirf);
		}

		return anexos;
	}

}
