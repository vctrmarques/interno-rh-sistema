package com.rhlinkcon.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.DataIntegretyException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Atividade;
import com.rhlinkcon.model.CategoriaProfissional;
import com.rhlinkcon.model.Cbo;
import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.ContagemTempoEspecialEnum;
import com.rhlinkcon.model.Curso;
import com.rhlinkcon.model.Funcao;
import com.rhlinkcon.model.FuncaoAcumulavelEnum;
import com.rhlinkcon.model.FuncaoHistoricoLei;
import com.rhlinkcon.model.GrupoSalarial;
import com.rhlinkcon.model.Habilidade;
import com.rhlinkcon.model.MotivoLeiEnum;
import com.rhlinkcon.model.NaturezaFuncao;
import com.rhlinkcon.model.ProcessoFuncao;
import com.rhlinkcon.model.Requisito;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.model.Vinculo;
import com.rhlinkcon.payload.atividade.AtividadeResponse;
import com.rhlinkcon.payload.curso.CursoResponse;
import com.rhlinkcon.payload.funcao.FuncaoRequest;
import com.rhlinkcon.payload.funcao.FuncaoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.habilidade.HabilidadeResponse;
import com.rhlinkcon.payload.vinculo.VinculoResponse;
import com.rhlinkcon.repository.AtividadeRepository;
import com.rhlinkcon.repository.CategoriaProfissionalRepository;
import com.rhlinkcon.repository.CboRepository;
import com.rhlinkcon.repository.ClasseSalarialRepository;
import com.rhlinkcon.repository.CursoRepository;
import com.rhlinkcon.repository.FaixaSalarialRepository;
import com.rhlinkcon.repository.FuncaoHistoricoLeiRepository;
import com.rhlinkcon.repository.FuncaoRepository;
import com.rhlinkcon.repository.GrupoSalarialRepository;
import com.rhlinkcon.repository.HabilidadeRepository;
import com.rhlinkcon.repository.NaturezaFuncaoRepository;
import com.rhlinkcon.repository.ProcessoFuncaoRepository;
import com.rhlinkcon.repository.RequisitoRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.repository.vinculo.VinculoRepository;
import com.rhlinkcon.util.Utils;

@Service
public class FuncaoService {

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private CboRepository cboRepository;

	@Autowired
	private NaturezaFuncaoRepository naturezaFuncaoRepository;

	@Autowired
	private ProcessoFuncaoRepository processoFuncaoRepository;

	@Autowired
	private CategoriaProfissionalRepository categoriaProfissionalRepository;

	@Autowired
	private VinculoRepository vinculoRepository;

	@Autowired
	private CursoRepository cursoRepository;

    @Autowired
    private HabilidadeRepository habilidadeRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoSalarialRepository grupoSalarialRepository;
	
	@Autowired
	private ClasseSalarialRepository classeSalarialRepository;
	
	@Autowired
	private FaixaSalarialRepository faixaSalarialRepository;
	
	@Autowired
	private FuncaoHistoricoLeiRepository historicoLeiRepository;
	
    @Autowired
    private RequisitoRepository requisitoRepository;
    
    @Autowired
    private VerbaRepository verbaRepository;
	
	public List<FuncaoResponse> getAllFuncoes() {
		List<Funcao> funcoes = funcaoRepository.findAll();

		List<FuncaoResponse> listFuncaoaResponse = new ArrayList<>();
		for (Funcao funcao : funcoes) {
			listFuncaoaResponse.add(new FuncaoResponse(funcao));
		}
		return listFuncaoaResponse;
	}

	public FuncaoResponse getFuncaoById(Long funcaoId) {
		Funcao funcao = funcaoRepository.findById(funcaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcao", "id", funcaoId));

		Usuario userCreated = usuarioRepository.findById(funcao.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", funcao.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(funcao.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", funcao.getUpdatedBy()));

		FuncaoResponse funcaoResponse = new FuncaoResponse(funcao, funcao.getCreatedAt(), userCreated.getNome(),
				funcao.getUpdatedAt(), userUpdated.getNome());

		List<VinculoResponse> listVinculoResponse = new ArrayList<>();

		if (Objects.nonNull(funcao.getVinculos()))
			for (Vinculo vinculo : funcao.getVinculos()) {
				listVinculoResponse.add(new VinculoResponse(vinculo));
			}

        funcaoResponse.setVinculos(listVinculoResponse);

        List<CursoResponse> listCursoResponse = new ArrayList<>();

        if (Objects.nonNull(funcao.getCursos())) {
            for (Curso curso : funcao.getCursos()) {
                listCursoResponse.add(new CursoResponse(curso));
            }
        }

        List<HabilidadeResponse> listHabilidadeResponse = new ArrayList<>();

        if (Objects.nonNull(funcao.getHabilidades()))
            for (Habilidade habilidade : funcao.getHabilidades()) {
                listHabilidadeResponse.add(new HabilidadeResponse(habilidade));
            }

        List<AtividadeResponse> listAtividadesResponse = new ArrayList<>();

        if (Objects.nonNull(funcao.getHabilidades()))
            for (Atividade atividade : funcao.getAtividades()) {
                listAtividadesResponse.add(new AtividadeResponse(atividade));
            }

		funcaoResponse.setCursos(listCursoResponse);

        funcaoResponse.setHabilidades(listHabilidadeResponse);

        funcaoResponse.setAtividades(listAtividadesResponse);

		return funcaoResponse;
	}

	public PagedResponse<FuncaoResponse> getAllFuncoes(int page, int size, String order, String nome) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Funcao> funcoes = funcaoRepository.findByNomeIgnoreCaseContaining(nome, pageable);

		if (funcoes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), funcoes.getNumber(), funcoes.getSize(),
					funcoes.getTotalElements(), funcoes.getTotalPages(), funcoes.isLast());
		}

		List<FuncaoResponse> funcoesResponses = funcoes.map(funcao -> {
			return new FuncaoResponse(funcao);
		}).getContent();
		return new PagedResponse<>(funcoesResponses, funcoes.getNumber(), funcoes.getSize(), funcoes.getTotalElements(),
				funcoes.getTotalPages(), funcoes.isLast());

	}

	public void deleteFuncao(Long id) {
		Funcao funcao = funcaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Funcao", "id", id));
		try {
			funcaoRepository.delete(funcao);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegretyException("Erro! Esta função está sendo usada em outra parte do sistema");
		}
		
	}

	public void createFuncao(FuncaoRequest funcaoRequest) {

		Funcao funcao = new Funcao(funcaoRequest);
		
		setEntidades(funcao, funcaoRequest);

		funcao.setDataCriacao(Instant.now());

        converterListaIdForEntity(funcaoRequest, funcao);

        funcaoRepository.save(funcao);

	}

	private void converterListaIdForEntity(FuncaoRequest funcaoRequest, Funcao funcao){

	    funcao.setVinculos(new HashSet<>());
        if (Utils.checkList(funcaoRequest.getVinculosIds())) {
            for (Long vinculoId : funcaoRequest.getVinculosIds()) {
                Vinculo vinculo = vinculoRepository.findById(vinculoId) .orElseThrow(() -> new ResourceNotFoundException("Vinculo", "id", vinculoId));
                funcao.getVinculos().add(vinculo);
            }
        }

        funcao.setCursos(new HashSet<>());
        if (Utils.checkList(funcaoRequest.getCursosIds())) {
            for (Long cursoId : funcaoRequest.getCursosIds()) {
                Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new ResourceNotFoundException("Curso", "id", cursoId));
                funcao.getCursos().add(curso);
            }
        }

        funcao.setHabilidades(new HashSet<>());
        if (Utils.checkList(funcaoRequest.getHabilidadesIds())) {
            for (Long habilidadeId : funcaoRequest.getHabilidadesIds()) {
                Habilidade habilidade = habilidadeRepository.findById(habilidadeId).orElseThrow(() -> new ResourceNotFoundException("Habilidades", "id", habilidadeId));
                funcao.getHabilidades().add(habilidade);
            }
        }

        funcao.setAtividades(new HashSet<>());
        if (Utils.checkList(funcaoRequest.getAtividadesIds())) {
            for (Long atividadeId : funcaoRequest.getAtividadesIds()) {
                Atividade atividade = atividadeRepository.findById(atividadeId).orElseThrow(() -> new ResourceNotFoundException("Atividades", "id", atividadeId));
                funcao.getAtividades().add(atividade);
            }
        }
        
        funcao.setRequisitos(new HashSet<>());
        if (Utils.checkList(funcaoRequest.getRequisitosIds())) {
            for (Long requisitoId : funcaoRequest.getRequisitosIds()) {
                Requisito requisito = requisitoRepository.findById(requisitoId).orElseThrow(() -> new ResourceNotFoundException("Requisito", "id", requisitoId));
                funcao.getRequisitos().add(requisito);
            }
        }
        
        funcao.setVerbas(new HashSet<>());
        if (Utils.checkList(funcaoRequest.getVerbasIds())) {
            for (Long verbaId : funcaoRequest.getVerbasIds()) {
                Verba verba = verbaRepository.findById(verbaId).orElseThrow(() -> new ResourceNotFoundException("Verba", "id", verbaId));
                funcao.getVerbas().add(verba);
            }
        }
    }

	public void updateFuncao(FuncaoRequest funcaoRequest) {

		Funcao funcao = funcaoRepository.findById(funcaoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcao", "id", funcaoRequest.getId()));
		
		if(Objects.nonNull(funcao.getMotivoLei()) && !funcao.getMotivoLei().getLabel().equals(funcaoRequest.getMotivoLei()) &&
				!funcao.getNumeroLei().equals(funcaoRequest.getNumeroLei()) )
			salvarHistorico(funcaoRequest);
		
		setEntidades(funcao, funcaoRequest);

		funcao.setFuncaoExtinta(funcaoRequest.getFuncaoExtinta());
		funcao.setNome(funcaoRequest.getNome());
		funcao.setDescricao(funcaoRequest.getDescricao());
		funcao.setDataCriacao(funcaoRequest.getDataCriacao());
		funcao.setDataExtincao(funcaoRequest.getDataExtincao());
		funcao.setDedicacaoExclusiva(funcaoRequest.getDedicacaoExclusiva());
		funcao.setNumeroLei(funcaoRequest.getNumeroLei());
		funcao.setDataLei(funcaoRequest.getDataLei());

		if (Objects.nonNull(funcaoRequest.getFuncaoAcumulavel()))
			funcao.setFuncaoAcumulavel(FuncaoAcumulavelEnum.valueOf(funcaoRequest.getFuncaoAcumulavel()));
		else
			funcao.setFuncaoAcumulavel(null);
		
		if (Objects.nonNull(funcaoRequest.getContagemTempoEspecial()))
			funcao.setContagemTempoEspecial(
					ContagemTempoEspecialEnum.valueOf(funcaoRequest.getContagemTempoEspecial()));
		else
			funcao.setContagemTempoEspecial(null);
					
		if (Objects.nonNull(funcaoRequest.getMotivoLei()))
			funcao.setMotivoLei(MotivoLeiEnum.valueOf(funcaoRequest.getMotivoLei()));

		if (!funcao.getVinculos().isEmpty()) {
			funcao.getVinculos().removeAll(funcao.getVinculos());
		}

        converterListaIdForEntity(funcaoRequest, funcao);

		funcaoRepository.save(funcao);
	}

	private void setEntidades(Funcao funcao, FuncaoRequest funcaoRequest) {

		Cbo cbo = cboRepository.findById(funcaoRequest.getCboId())
				.orElseThrow(() -> new ResourceNotFoundException("CBo", "id", funcaoRequest.getCboId()));
		funcao.setCbo(cbo);

		if (Objects.nonNull(funcaoRequest.getNaturezaFuncaoId())) {
			NaturezaFuncao naturezaFuncao = naturezaFuncaoRepository.findById(funcaoRequest.getNaturezaFuncaoId())
					.orElseThrow(() -> new ResourceNotFoundException("NaturezaFuncao", "id",
							funcaoRequest.getNaturezaFuncaoId()));

			funcao.setNaturezaFuncao(naturezaFuncao);
		}else
			funcao.setNaturezaFuncao(null);

		if (Objects.nonNull(funcaoRequest.getProcessoFuncaoId())) {
			ProcessoFuncao processoFuncao = processoFuncaoRepository.findById(funcaoRequest.getProcessoFuncaoId())
					.orElseThrow(() -> new ResourceNotFoundException("ProcessoFuncao", "id",
							funcaoRequest.getProcessoFuncaoId()));

			funcao.setProcessoFuncao(processoFuncao);
		}else
			funcao.setProcessoFuncao(null);

		if (Objects.nonNull(funcaoRequest.getCategoriaProfissionalId())) {
			CategoriaProfissional categoriaProfissional = categoriaProfissionalRepository
					.findById(funcaoRequest.getCategoriaProfissionalId())
					.orElseThrow(() -> new ResourceNotFoundException("CategoriaProfissional", "id", funcaoRequest.getCategoriaProfissionalId()));

			funcao.setCategoriaProfissional(categoriaProfissional);
		}else
			funcao.setCategoriaProfissional(null);
		
		if (Objects.nonNull(funcaoRequest.getGrupoSalarialId())) {
			GrupoSalarial grupoSalarial = grupoSalarialRepository
					.findById(funcaoRequest.getGrupoSalarialId()).orElseThrow(() -> new ResourceNotFoundException("GrupoSalarial", "id",
							funcaoRequest.getGrupoSalarialId()));

			funcao.setGrupoSalarial(grupoSalarial);
		}else
			funcao.setGrupoSalarial(null);

		
		if(Objects.nonNull(funcaoRequest.getCentroCustoId())) {
			funcao.setCentroCusto(new CentroCusto(funcaoRequest.getCentroCustoId()));
		}

	}

	public void extingueFuncao(FuncaoRequest funcaoRequest) {
		funcaoRequest.setFuncaoExtinta(true);
		funcaoRequest.setDataExtincao(Instant.now());
			salvarHistorico(funcaoRequest);
		updateFuncao(funcaoRequest);
	}
	
	private void salvarHistorico(FuncaoRequest funcaoRequest) {
		if(Strings.isNotEmpty(funcaoRequest.getMotivoLei())) {
			FuncaoHistoricoLei funcaoHistoricoLei = new FuncaoHistoricoLei();
			funcaoHistoricoLei.setDataLei(Instant.now());
			funcaoHistoricoLei.setNumeroLei(funcaoRequest.getNumeroLei());
			funcaoHistoricoLei.setFuncao(new Funcao(funcaoRequest.getId()));
			funcaoHistoricoLei.setMotivoLei(MotivoLeiEnum.getEnumByString(funcaoRequest.getMotivoLei()));
			historicoLeiRepository.save(funcaoHistoricoLei);
		}	
	}
	
	public List<FuncaoResponse> getAllFuncoesFindByNome(String search) {

		List<Funcao> funcoes = funcaoRepository.findByNomeIgnoreCaseContaining(search);
		List<FuncaoResponse> funcoesResponse = new ArrayList();

		if (!funcoes.isEmpty()) {
			for (Funcao funcao: funcoes) {
				funcoesResponse.add(new FuncaoResponse(funcao));
			}
			return funcoesResponse;
		}

		return null;
	}		

}
