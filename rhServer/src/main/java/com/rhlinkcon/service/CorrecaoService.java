package com.rhlinkcon.service;

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
import com.rhlinkcon.model.SituacaoFuncional;
import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.Correcao;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Funcao;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.ReferenciaSalarial;
import com.rhlinkcon.model.Sindicato;
import com.rhlinkcon.model.TipoArredondamentoEnum;
import com.rhlinkcon.model.TipoFolha;
import com.rhlinkcon.model.TipoProcessamento;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.correcao.CorrecaoRequest;
import com.rhlinkcon.payload.correcao.CorrecaoResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.SituacaoFuncionalRepository;
import com.rhlinkcon.repository.CentroCustoRepository;
import com.rhlinkcon.repository.CorrecaoRepository;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.FuncaoRepository;
import com.rhlinkcon.repository.LotacaoRepository;
import com.rhlinkcon.repository.ReferenciaSalarialRepository;
import com.rhlinkcon.repository.SindicatoRepository;
import com.rhlinkcon.repository.TipoFolhaRepository;
import com.rhlinkcon.repository.TipoProcessamentoRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class CorrecaoService {

	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CorrecaoRepository correcaoRepository;
	
	@Autowired
	private TipoProcessamentoRepository tipoProcessamentoRepository;
	
	@Autowired
	private LotacaoRepository lotacaoRepository;
	
	@Autowired
	private CentroCustoRepository centroCustoRepository;
	
	@Autowired
	private SindicatoRepository sindicatoRepository;
	
	@Autowired
	private FuncaoRepository funcaoRepository;
	
	@Autowired
	private ReferenciaSalarialRepository nivelSalarialRepository;
	
	@Autowired
	private SituacaoFuncionalRepository situacaoFuncionalRepository;
	
	@Autowired
	private TipoFolhaRepository tipoFolhaRepository;
	
	@Autowired
	private VerbaRepository verbaRepository;

	public void createCorrecao(CorrecaoRequest correcaoRequest) {

		// Creating user's account
		Correcao correcao = new Correcao(correcaoRequest);
		
		setEntidades(correcao, correcaoRequest);
		
		if(Objects.nonNull(correcao.getAbono()))
			correcao.setAbono(false);

		correcaoRepository.save(correcao);

	}

	public void updateCorrecao(CorrecaoRequest correcaoRequest) {

		// Updating user's account
		Correcao correcao = correcaoRepository.findById(correcaoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Correcao", "id", correcaoRequest.getId()));
		
		setEntidades(correcao, correcaoRequest);
		
		correcao.setDataCompetencia(correcaoRequest.getDataCompetencia());
		correcao.setValorFaixaI(correcaoRequest.getValorFaixaI());
		correcao.setValorFaixaII(correcaoRequest.getValorFaixaII());
		correcao.setPercentualAumento(correcaoRequest.getPercentualAumento());
		correcao.setPercentualProduto(correcaoRequest.getPercentualProduto());
		correcao.setValorProduto(correcaoRequest.getValorProduto());
		correcao.setValorPiso(correcaoRequest.getValorPiso());
		
		if(Utils.checkStr(correcaoRequest.getTipoArredondamento()))
			correcao.setTipoArredondamento(TipoArredondamentoEnum.valueOf(correcaoRequest.getTipoArredondamento()));
		else
			correcao.setTipoArredondamento(null);
		
		correcao.setAvos(correcaoRequest.getAvos());
		correcao.setValorArrendamento(correcaoRequest.getValorArrendamento());
		correcao.setAtualizaMes(correcaoRequest.getAtualizaMes());
		correcao.setRetroativo(correcaoRequest.getRetroativo());
		correcao.setAbono(correcaoRequest.getAbono());
		correcao.setVerbaAbono(correcaoRequest.getVerbaAbono());
		
		if(Objects.nonNull(correcao.getAbono()))
			correcao.setAbono(false);
		
		correcaoRepository.save(correcao);

	}

	public void deleteCorrecao(Long id) {
		Correcao correcao = correcaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Correcao", "id", id));
		
		correcaoRepository.delete(correcao);
	}

	public PagedResponse<CorrecaoResponse> getAllCorrecoes(int page, int size, String nomeFilial, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);
		
		Page<Correcao> correcoes = null;
		
		if(Utils.checkStr(nomeFilial))
			correcoes = correcaoRepository.findByEmpresaNomeFilialIgnoreCaseContaining(nomeFilial, pageable);
		else
			correcoes = correcaoRepository.findAll(pageable);			

		if (correcoes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), correcoes.getNumber(), correcoes.getSize(),
					correcoes.getTotalElements(), correcoes.getTotalPages(), correcoes.isLast());
		}

		List<CorrecaoResponse> correcoesResponse = correcoes.map(correcao -> {
			return new CorrecaoResponse(correcao);
		}).getContent();
		return new PagedResponse<>(correcoesResponse, correcoes.getNumber(), correcoes.getSize(),
				correcoes.getTotalElements(), correcoes.getTotalPages(), correcoes.isLast());

	}

	public CorrecaoResponse getCorrecaoById(Long correcaoId) {
		Correcao correcao = correcaoRepository.findById(correcaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Correcao", "id", correcaoId));
		
		Usuario userCreated = usuarioRepository.findById(correcao.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", correcao.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(correcao.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", correcao.getUpdatedBy()));

		CorrecaoResponse correcaoResponse = new CorrecaoResponse(correcao, correcao.getCreatedAt(),
				userCreated.getNome(), correcao.getUpdatedAt(), userUpdated.getNome());

		return correcaoResponse;
	}
	
	private void setEntidades(Correcao correcao, CorrecaoRequest correcaoRequest) {
	
		if(Objects.nonNull(correcaoRequest.getTipoProcessamentoId())) {
			TipoProcessamento tipoProcessamento = tipoProcessamentoRepository.findById(correcaoRequest.getTipoProcessamentoId())
				.orElseThrow(() -> new ResourceNotFoundException("TipoProcessamento", "id", correcaoRequest.getTipoProcessamentoId()));
			correcao.setTipoProcessamento(tipoProcessamento);
		}else
			correcao.setTipoProcessamento(null);
		
		
		if(Objects.nonNull(correcaoRequest.getEmpresaId())) {
			EmpresaFilial empresa = empresaFilialRepository.findById(correcaoRequest.getEmpresaId())
					.orElseThrow(() -> new ResourceNotFoundException("EmpresaFilial", "id", correcaoRequest.getEmpresaId()));
			correcao.setEmpresa(empresa);
		}else
			correcao.setEmpresa(null);

		
		if(Objects.nonNull(correcaoRequest.getFilialId())) {
			EmpresaFilial filial = empresaFilialRepository.findById(correcaoRequest.getFilialId())
					.orElseThrow(() -> new ResourceNotFoundException("EmpresaFilial", "id", correcaoRequest.getFilialId()));
			correcao.setFilial(filial);
		}else
			correcao.setFilial(null);

		if(Objects.nonNull(correcaoRequest.getLotacaoId())) {
			Lotacao lotacao = lotacaoRepository.findById(correcaoRequest.getLotacaoId())
					.orElseThrow(() -> new ResourceNotFoundException("Lotacao", "id", correcaoRequest.getLotacaoId()));
			correcao.setLotacao(lotacao);
		}else
			correcao.setLotacao(null);
		
		if(Objects.nonNull(correcaoRequest.getCentroCustoId())) {
			CentroCusto centroCusto = centroCustoRepository.findById(correcaoRequest.getCentroCustoId())
					.orElseThrow(() -> new ResourceNotFoundException("CentroCusto", "id", correcaoRequest.getCentroCustoId()));
			correcao.setCentroCusto(centroCusto);
		}else
			correcao.setCentroCusto(null);
		
		if(Objects.nonNull(correcaoRequest.getSindicatoId())) {
			Sindicato sindicato = sindicatoRepository.findById(correcaoRequest.getSindicatoId())
					.orElseThrow(() -> new ResourceNotFoundException("Sindicato", "id", correcaoRequest.getSindicatoId()));
			correcao.setSindicato(sindicato);
		}else
			correcao.setSindicato(null);
		
			
		if(Objects.nonNull(correcaoRequest.getFuncaoId())) {
			Funcao funcao  = funcaoRepository.findById(correcaoRequest.getFuncaoId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcao", "id", correcaoRequest.getFuncaoId()));
			correcao.setFuncao(funcao);
		}else
			correcao.setFuncao(null);
		
		if(Objects.nonNull(correcaoRequest.getNivelSalarialId())) {
			ReferenciaSalarial nivelSalarial = nivelSalarialRepository.findById(correcaoRequest.getNivelSalarialId())
					.orElseThrow(() -> new ResourceNotFoundException("NivelSalarial", "id", correcaoRequest.getNivelSalarialId()));
			correcao.setNivelSalarial(nivelSalarial);
		}else
			correcao.setNivelSalarial(null);
		
		
		if(Objects.nonNull(correcaoRequest.getSituacaoId())) {
			SituacaoFuncional situacao = situacaoFuncionalRepository.findById(correcaoRequest.getSituacaoId())
					.orElseThrow(() -> new ResourceNotFoundException("Afastamento", "id", correcaoRequest.getSituacaoId()));
			correcao.setSituacao(situacao);
		}else
			correcao.setSituacao(null);
		
		
		if(Objects.nonNull(correcaoRequest.getTipoFolhaId())) {
			TipoFolha tipoFolha = tipoFolhaRepository.findById(correcaoRequest.getTipoFolhaId())
					.orElseThrow(() -> new ResourceNotFoundException("TipoFolha", "id", correcaoRequest.getTipoFolhaId()));
			correcao.setTipoFolha(tipoFolha);
		}else
			correcao.setTipoFolha(null);
		
		if(Objects.nonNull(correcaoRequest.getVerbaId())) {
			Verba verba = verbaRepository.findById(correcaoRequest.getVerbaId())
					.orElseThrow(() -> new ResourceNotFoundException("Verba", "id", correcaoRequest.getVerbaId()));
			correcao.setVerba(verba);
		}else
			correcao.setVerba(null);
		
	}

}
