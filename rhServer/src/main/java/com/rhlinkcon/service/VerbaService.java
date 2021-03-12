package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.DestinacaoExterna;
import com.rhlinkcon.model.FaixaEnum;
import com.rhlinkcon.model.IdentificacaoVerbaEnum;
import com.rhlinkcon.model.ItemFormula;
import com.rhlinkcon.model.ItemFormulaTipoEnum;
import com.rhlinkcon.model.RecorrenciaEnum;
import com.rhlinkcon.model.TipoValorEnum;
import com.rhlinkcon.model.TipoVerba;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.verba.VerbaRequest;
import com.rhlinkcon.payload.verba.VerbaResponse;
import com.rhlinkcon.repository.CargoRepository;
import com.rhlinkcon.repository.CategoriaProfissionalRepository;
import com.rhlinkcon.repository.ContaContabilRepository;
import com.rhlinkcon.repository.CorrecaoRepository;
import com.rhlinkcon.repository.FuncaoRepository;
import com.rhlinkcon.repository.FuncionarioVerbaRepository;
import com.rhlinkcon.repository.ItemFormulaRepository;
import com.rhlinkcon.repository.PensaoAlimenticiaRepository;
import com.rhlinkcon.repository.PensionistaVerbaRepository;
import com.rhlinkcon.repository.PrestadorServicoRepository;
import com.rhlinkcon.repository.TipoFolhaRepository;
import com.rhlinkcon.repository.TipoProcessamentoRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.lancamento.LancamentoRepository;
import com.rhlinkcon.repository.vinculo.VinculoRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class VerbaService {

	@Autowired
	private VerbaRepository verbaRepository;

	@Autowired
	private ItemFormulaRepository itemFormulaRepository;

	@Autowired
	private PensaoAlimenticiaRepository pensaoAlimenticiaRepository;

	@Autowired
	private ContaContabilRepository contaContabilRepository;

	@Autowired
	private CorrecaoRepository correcaoRepository;

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private CategoriaProfissionalRepository categoriaProfissionalRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private FuncionarioVerbaRepository funcionarioVerbaRepository;

	@Autowired
	private PensionistaVerbaRepository pensionistaVerbaRepository;

	@Autowired
	private PrestadorServicoRepository prestadorServicoRepository;

	@Autowired
	private TipoFolhaRepository tipoFolhaRepository;

	@Autowired
	private TipoProcessamentoRepository tipoProcessamentoRepository;

	@Autowired
	private VinculoRepository vinculoRepository;

	@Autowired
	private UsuarioService usuarioService;

	public List<DadoBasicoDto> verbaPensaoAlimenticiaSearch() {
		List<DadoBasicoDto> itensResponses = new ArrayList<DadoBasicoDto>();

		Optional<List<Verba>> optListResult = verbaRepository
				.findAllByDestinacaoExterna(DestinacaoExterna.PENSAO_ALIMENTICIA);

		if (optListResult.isPresent()) {
			for (Verba verba : optListResult.get()) {
				itensResponses.add(new DadoBasicoDto(verba));
			}
		}

		return itensResponses;
	}

	public List<VerbaResponse> search(String search) {
		List<Verba> verbas = verbaRepository
				.findByDescricaoVerbaIgnoreCaseContainingOrCodigoIgnoreCaseContaining(search, search);

		List<VerbaResponse> verbasResponse = new ArrayList<>();

		for (Verba verba : verbas) {
			VerbaResponse verbaResponse = new VerbaResponse(verba.getId(), verba.getDescricaoVerba(), verba.getCodigo(),
					verba.getLabel());
			verbaResponse.setTipoVerba(verba.getTipoVerba().getLabel());
			verbasResponse.add(verbaResponse);
		}
		return verbasResponse;

	}

	public VerbaResponse getVerbaById(Long id) {
		Verba verba = verbaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Verba", "id", id));

		VerbaResponse verbaResponse = new VerbaResponse(verba, Projecao.COMPLETA);
		usuarioService.setDadoCadastral(verbaResponse, verba);

		return verbaResponse;
	}

	public PagedResponse<VerbaResponse> getAllVerba(int page, int size, String order, String search) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Verba> verbas = null;

		if (Utils.checkStr(search)) {
			verbas = verbaRepository.findByDescricaoVerbaIgnoreCaseContainingOrCodigoIgnoreCaseContaining(search,
					search, pageable);
		} else {
			verbas = verbaRepository.findAll(pageable);
		}

		if (verbas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), verbas.getNumber(), verbas.getSize(),
					verbas.getTotalElements(), verbas.getTotalPages(), verbas.isLast());
		}

		List<VerbaResponse> verbaResponses = verbas.map(verba -> {
			return new VerbaResponse(verba);
		}).getContent();
		return new PagedResponse<>(verbaResponses, verbas.getNumber(), verbas.getSize(), verbas.getTotalElements(),
				verbas.getTotalPages(), verbas.isLast());

	}

	public void deleteVerba(Long id) {
		Verba verba = verbaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Verba", "id", id));

		Boolean exists = pensaoAlimenticiaRepository.existsByVerba(verba);

		if (!exists)
			exists = contaContabilRepository.existsByVerba(verba);
		if (!exists)
			exists = correcaoRepository.existsByVerba(verba);
		if (!exists)
			exists = itemFormulaRepository.existsByRotina(verba);
		if (!exists)
			exists = cargoRepository.existsByVerbasContaining(verba);
		if (!exists)
			exists = categoriaProfissionalRepository.existsByVerbasContaining(verba);
		if (!exists)
			exists = lancamentoRepository.existsByVerba(verba);
		if (!exists)
			exists = funcaoRepository.existsByVerbasContaining(verba);
		if (!exists)
			exists = funcionarioVerbaRepository.existsByVerba(verba);
		if (!exists)
			exists = pensionistaVerbaRepository.existsByVerba(verba);
		if (!exists)
			exists = prestadorServicoRepository.existsByVerba(verba);
		if (!exists)
			exists = tipoFolhaRepository.existsByVerbasContaining(verba);
		if (!exists)
			exists = tipoProcessamentoRepository.existsByVerbasContaining(verba);
		if (!exists)
			exists = vinculoRepository.existsByVerbasContaining(verba);
		if (!exists)
			exists = verbaRepository.existsByIncidenciasContaining(verba);
		if (!exists)
			exists = verbaRepository.existsByVerbaAssociada(verba);

		if (!exists)
			verbaRepository.delete(verba);
		else
			throw new BadRequestException("Verba '" + verba.getDescricaoVerba()
					+ "' não pode ser excluída, pois está vinculada a outras entidades.");

	}

	public void createVerba(VerbaRequest verbaRequest) {

		if (verbaRepository.existsByCodigo(verbaRequest.getCodigo()))
			throw new BadRequestException("Verba com código " + verbaRequest.getCodigo() + " já cadastrada");

		Verba verba = new Verba(verbaRequest);

		verba.getIncidencias().clear();
		if (Utils.checkList(verbaRequest.getIncidenciasIds())) {
			for (Long verbaId : verbaRequest.getIncidenciasIds()) {
				verba.getIncidencias().add(new Verba(verbaId));
			}
		}

		verbaRepository.save(verba);

		verbaRequest.getFormulas().forEach(formula -> {
			ItemFormula itemFormula = new ItemFormula();
			itemFormula.setVerba(verba);
			itemFormula.setOrdem(formula.getOrdem());
			itemFormula.setTipo(ItemFormulaTipoEnum.valueOf(formula.getTipo()));
			itemFormula.setValor(formula.getValor());
			if (itemFormula.getTipo().equals(ItemFormulaTipoEnum.ROTINA))
				itemFormula.setRotina(new Verba(formula.getRotina().getId()));
			itemFormulaRepository.save(itemFormula);
		});
	}

	public void updateVerba(VerbaRequest verbaRequest) {

		if (verbaRepository.existsByCodigoAndIdNot(verbaRequest.getCodigo(), verbaRequest.getId()))
			throw new BadRequestException("Verba com código " + verbaRequest.getCodigo() + " já está em uso.");

		Verba verba = verbaRepository.findById(verbaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Verba", "id", verbaRequest.getId()));

		verba.setCodigo(verbaRequest.getCodigo());
		verba.setComentario(verbaRequest.getComentario());
		verba.setDescricaoVerba(verbaRequest.getDescricaoVerba());
		verba.setDescricaoResumida(verbaRequest.getDescricaoResumida());
		verba.setValorMaximo(verbaRequest.getValorMaximo());
		verba.setTipoVerba(TipoVerba.valueOf(verbaRequest.getTipoVerba()));
		verba.setTipoValor(TipoValorEnum.getEnumByString(verbaRequest.getTipoValor()));
		verba.setRecorrencia(RecorrenciaEnum.getEnumByString(verbaRequest.getRecorrencia()));
		verba.setContaAuxiliarPrimaria(verbaRequest.getContaAuxiliarPrimaria());
		verba.setContaAuxiliarSecundaria(verbaRequest.getContaAuxiliarSecundaria());
		verba.setContaCredito(verbaRequest.getContaCredito());
		verba.setContaDebito(verbaRequest.getContaDebito());

		if (Objects.nonNull(verbaRequest.getIdentificadorVerba())) {
			verba.setIdentificadorVerba(IdentificacaoVerbaEnum.getEnumByString(verbaRequest.getIdentificadorVerba()));
			verba.setVerbaAssociada(new Verba(verbaRequest.getVerbaAssociadaId()));
		} else {
			verba.setIdentificadorVerba(null);
			verba.setVerbaAssociada(null);
		}

		if (Objects.nonNull(verbaRequest.getFaixaAliquota()))
			verba.setFaixaAliquota(FaixaEnum.getEnumByString(verbaRequest.getFaixaAliquota()));
		else
			verba.setFaixaAliquota(null);

		if (Objects.nonNull(verbaRequest.getDestinacaoExterna())) {
			verba.setDestinacaoExterna(DestinacaoExterna.getEnumByString(verbaRequest.getDestinacaoExterna()));
		}

		verba.setVigenciaFinal(verbaRequest.getVigenciaFinal());
		verba.setVigenciaInicial(verbaRequest.getVigenciaInicial());

		if (null != verbaRequest.getReferencia()) {
			verba.setReferencia(verbaRequest.getReferencia());
		}

		verba.getIncidencias().clear();
		if (Utils.checkList(verbaRequest.getIncidenciasIds())) {
			for (Long verbaId : verbaRequest.getIncidenciasIds()) {
				verba.getIncidencias().add(new Verba(verbaId));
			}
		}

		verbaRepository.save(verba);

		ArrayList<Long> formulaIds = new ArrayList<Long>();

		verbaRequest.getFormulas().forEach(formula -> {
			ItemFormula itemFormula;

			if (Objects.nonNull(formula.getId()) && !formula.getId().equals(0L)) {
				itemFormula = itemFormulaRepository.getOne(formula.getId());
			} else {
				itemFormula = new ItemFormula();
				itemFormula.setVerba(verba);
				itemFormula.setTipo(ItemFormulaTipoEnum.valueOf(formula.getTipo()));
				itemFormula.setValor(formula.getValor());
				if (itemFormula.getTipo().equals(ItemFormulaTipoEnum.ROTINA))
					itemFormula.setRotina(new Verba(formula.getRotina().getId()));
			}

			// Numa edição, apenas a ordem pode mudar.
			itemFormula.setOrdem(formula.getOrdem());

			itemFormulaRepository.save(itemFormula);

			// Pegando o id dos itens para excluir os items deletados da lista.
			formulaIds.add(itemFormula.getId());
		});

		// Apagando os itens de fórmula que foram excluídos - Não pertencem mais a lista
		// atual de itens.
		Optional<List<ItemFormula>> iteFormToDeleteOpt = itemFormulaRepository.findByVerbaAndIdNotIn(verba, formulaIds);
		if (iteFormToDeleteOpt.isPresent())
			itemFormulaRepository.deleteAll(iteFormToDeleteOpt.get());

	}

	public List<VerbaResponse> getAllVerbas() {
		List<Verba> verbas = verbaRepository.findAll();

		if (!verbas.isEmpty()) {
			List<VerbaResponse> verbasResponse = new ArrayList<>();

			for (Verba verba : verbas) {
				verbasResponse.add(new VerbaResponse(verba, Projecao.BASICA));
			}

			return verbasResponse;
		}

		return null;
	}

	public List<VerbaResponse> getVerbasPorCategoriaProfissional(Long id) {
		List<Verba> lista = verbaRepository.findAllByCategoriaProfissionaisId(id);
		return lista.stream().map(verba -> new VerbaResponse(verba)).collect(Collectors.toList());
	}

	public Verba findVerbaByCodigo(String codigo) {
		Optional<Verba> verba = verbaRepository.findByCodigo(codigo);
		if (verba.isPresent())
			return verba.get();
		return null;
	}

}
