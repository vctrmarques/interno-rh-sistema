package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
import com.rhlinkcon.model.Atividade;
import com.rhlinkcon.model.Cargo;
import com.rhlinkcon.model.ContagemTempoEspecialEnum;
import com.rhlinkcon.model.Curso;
import com.rhlinkcon.model.GrupoSalarial;
import com.rhlinkcon.model.Habilidade;
import com.rhlinkcon.model.MotivoLeiEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.model.Vinculo;
import com.rhlinkcon.payload.cargo.CargoRequest;
import com.rhlinkcon.payload.cargo.CargoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.AtividadeRepository;
import com.rhlinkcon.repository.CargoRepository;
import com.rhlinkcon.repository.CategoriaProfissionalRepository;
import com.rhlinkcon.repository.CboRepository;
import com.rhlinkcon.repository.CursoRepository;
import com.rhlinkcon.repository.HabilidadeRepository;
import com.rhlinkcon.repository.NaturezaFuncaoRepository;
import com.rhlinkcon.repository.ProcessoFuncaoRepository;
import com.rhlinkcon.repository.SindicatoRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.repository.vinculo.VinculoRepository;
import com.rhlinkcon.util.Utils;

@Service
public class CargoService {

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private CboRepository cboRepository;

	@Autowired
	private NaturezaFuncaoRepository naturezaFuncaoRepository;

	@Autowired
	private ProcessoFuncaoRepository processoFuncaoRepository;

	@Autowired
	private CategoriaProfissionalRepository categoriaProfissionalRepository;

	@Autowired
	private SindicatoRepository sindicatoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private VinculoRepository vinculoRepository;

	@Autowired
	private AtividadeRepository atividadeRepository;

	@Autowired
	private HabilidadeRepository habilidadeRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private VerbaRepository verbaRepository;

	public List<CargoResponse> getAllCargos() {
		List<Cargo> cargos = cargoRepository.findAll();

		List<CargoResponse> listCargoResponse = new ArrayList<>();
		for (Cargo cargo : cargos) {
			listCargoResponse.add(new CargoResponse(cargo));
		}
		return listCargoResponse;
	}

	public List<CargoResponse> getAllCargosBasic() {
		List<Cargo> cargos = cargoRepository.findAll();

		List<CargoResponse> listCargoResponse = new ArrayList<>();
		for (Cargo cargo : cargos) {
			listCargoResponse.add(
					new CargoResponse(cargo.getId(), cargo.getNome(), cargo.getCodigo(), cargo.getGrupoSalarial()));
		}
		return listCargoResponse;
	}

	public CargoResponse getCargoById(Long cargoId) {
		Cargo cargo = cargoRepository.findById(cargoId)
				.orElseThrow(() -> new ResourceNotFoundException("Cargo", "id", cargoId));

		CargoResponse cargoResponse = new CargoResponse(cargo);
		Usuario criadoPor = usuarioRepository.findById(cargo.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", cargo.getCreatedBy()));
		cargoResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(cargo.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", cargo.getUpdatedBy()));
		cargoResponse.setAlteradoPor(alteradoPor.getNome());

		return cargoResponse;
	}

	public PagedResponse<CargoResponse> getAllCargos(int page, int size, String order, String nome) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Cargo> cargos = cargoRepository.findByNomeIgnoreCaseContaining(nome, pageable);

		if (cargos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), cargos.getNumber(), cargos.getSize(),
					cargos.getTotalElements(), cargos.getTotalPages(), cargos.isLast());
		}

		List<CargoResponse> cargoResponses = cargos.map(cargo -> {
			return new CargoResponse(cargo);
		}).getContent();
		return new PagedResponse<>(cargoResponses, cargos.getNumber(), cargos.getSize(), cargos.getTotalElements(),
				cargos.getTotalPages(), cargos.isLast());

	}

	public void deleteCargo(Long id) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo", "id", id));

		// Deletando as relação de cargo com atividade, habilidade, curso, vinculo, e
		// lotacaoCargo
		cargoRepository.deleteCargoAtividade(cargo.getId());
		cargoRepository.deleteCargoHabilidade(cargo.getId());
		cargoRepository.deleteCargoCurso(cargo.getId());
		cargoRepository.deleteCargoVinculo(cargo.getId());
		cargoRepository.deleteLotacaoCargo(cargo.getId());

		cargoRepository.delete(cargo);
	}

	public void createCargo(CargoRequest cargoRequest) {

		Cargo cargo = new Cargo(cargoRequest);

		setEntidades(cargo, cargoRequest);

		cargoRepository.save(cargo);

	}

	public void updateCargo(CargoRequest cargoRequest) {

		Cargo cargo = cargoRepository.findById(cargoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Cargo", "id", cargoRequest.getId()));

		cargo.setNome(cargoRequest.getNome());
		cargo.setDescricao(cargoRequest.getDescricao());
		cargo.setLeiRespaldo(cargoRequest.getLeiRespaldo());

		setEntidades(cargo, cargoRequest);

		cargoRepository.save(cargo);

	}

	private void setEntidades(Cargo cargo, CargoRequest cargoRequest) {

		cargo.setDedicacaoExclusiva(Utils.setBool(cargoRequest.isDedicacaoExclusiva()));

		if (Objects.nonNull(cargoRequest.getCboId()))
			cargo.setCbo(cboRepository.findById(cargoRequest.getCboId())
					.orElseThrow(() -> new ResourceNotFoundException("CBO", "id", cargoRequest.getCboId())));

		if (Objects.nonNull(cargoRequest.getGrupoSalarialId()))
			cargo.setGrupoSalarial(new GrupoSalarial(cargoRequest.getGrupoSalarialId()));
		else
			cargo.setGrupoSalarial(null);

		if (Objects.nonNull(cargoRequest.getNaturezaFuncaoId()))
			cargo.setNaturezaFuncao(naturezaFuncaoRepository.findById(cargoRequest.getNaturezaFuncaoId()).orElseThrow(
					() -> new ResourceNotFoundException("NaturezaFuncao", "id", cargoRequest.getNaturezaFuncaoId())));

		if (Objects.nonNull(cargoRequest.getProcessoFuncaoId()))
			cargo.setProcessoFuncao(processoFuncaoRepository.findById(cargoRequest.getProcessoFuncaoId()).orElseThrow(
					() -> new ResourceNotFoundException("ProcessoFuncao", "id", cargoRequest.getProcessoFuncaoId())));

		if (Objects.nonNull(cargoRequest.getCategoriaProfissionalId()))
			cargo.setCategoriaProfissional(
					categoriaProfissionalRepository.findById(cargoRequest.getCategoriaProfissionalId())
							.orElseThrow(() -> new ResourceNotFoundException("CategoriaProfissional", "id",
									cargoRequest.getCategoriaProfissionalId())));

		if (Objects.nonNull(cargoRequest.getSindicatoId()))
			cargo.setSindicato(sindicatoRepository.findById(cargoRequest.getSindicatoId())
					.orElseThrow(() -> new ResourceNotFoundException("Sincato", "id", cargoRequest.getSindicatoId())));

		if (Objects.nonNull(cargoRequest.getContagemTempoEspecial()))
			cargo.setContagemTempoEspecial(ContagemTempoEspecialEnum.valueOf(cargoRequest.getContagemTempoEspecial()));

		if (Objects.nonNull(cargoRequest.getMotivoLei()))
			cargo.setMotivoLei(MotivoLeiEnum.valueOf(cargoRequest.getMotivoLei()));

		cargo.getVinculos().clear();
		if (Utils.checkList(cargoRequest.getVinculosIds())) {
			for (Long vinculoId : cargoRequest.getVinculosIds()) {
				Vinculo vinculo = vinculoRepository.findById(vinculoId)
						.orElseThrow(() -> new ResourceNotFoundException("Vinculo", "id", vinculoId));
				cargo.getVinculos().add(vinculo);
			}
		}

		cargo.setAtividades(new HashSet<>());
		if (Utils.checkList(cargoRequest.getAtividadesIds())) {
			for (Long atividadeId : cargoRequest.getAtividadesIds()) {
				Atividade atividade = atividadeRepository.findById(atividadeId)
						.orElseThrow(() -> new ResourceNotFoundException("Atividade", "id", atividadeId));
				cargo.getAtividades().add(atividade);
			}
		}

		cargo.setHabilidades(new HashSet<>());
		if (Utils.checkList(cargoRequest.getHabilidadesIds())) {
			for (Long habilidadesId : cargoRequest.getHabilidadesIds()) {
				Habilidade habilidade = habilidadeRepository.findById(habilidadesId)
						.orElseThrow(() -> new ResourceNotFoundException("Habilidade", "id", habilidadesId));
				cargo.getHabilidades().add(habilidade);
			}
		}

		cargo.setCursos(new HashSet<>());
		if (Utils.checkList(cargoRequest.getCursosIds())) {
			for (Long cursoId : cargoRequest.getCursosIds()) {
				Curso curso = cursoRepository.findById(cursoId)
						.orElseThrow(() -> new ResourceNotFoundException("Curso", "id", cursoId));
				cargo.getCursos().add(curso);
			}
		}

		cargo.setVerbas(new HashSet<>());
		if (Utils.checkList(cargoRequest.getVerbasIds())) {
			for (Long verbaId : cargoRequest.getVerbasIds()) {
				Verba verba = verbaRepository.findById(verbaId)
						.orElseThrow(() -> new ResourceNotFoundException("Verba", "id", verbaId));
				cargo.getVerbas().add(verba);
			}
		}
	}

	public List<CargoResponse> getAllCargosFindByNome(String search) {

		List<Cargo> cargos = cargoRepository.findByNomeIgnoreCaseContaining(search);
		List<CargoResponse> cargosResponse = new ArrayList<>();

		if (!cargos.isEmpty()) {
			for (Cargo cargo : cargos) {
				cargosResponse.add(new CargoResponse(cargo));
			}
			return cargosResponse;
		}

		return null;
	}

}
