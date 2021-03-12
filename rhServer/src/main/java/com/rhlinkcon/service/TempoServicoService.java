package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.ClassificacaoAto;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.IndiceContribuicaoPrevidenciaEnum;
import com.rhlinkcon.model.Sefip;
import com.rhlinkcon.model.TempoServico;
import com.rhlinkcon.model.TipoAverbacao;
import com.rhlinkcon.model.TipoAverbacaoEnum;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.tempoServico.TempoServicoRequest;
import com.rhlinkcon.payload.tempoServico.TempoServicoResponse;
import com.rhlinkcon.repository.ClassificacaoAtoRepository;
import com.rhlinkcon.repository.SefipRepository;
import com.rhlinkcon.repository.TempoServicoRepository;
import com.rhlinkcon.repository.TipoAverbacaoRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class TempoServicoService {

	@Autowired
	private TempoServicoRepository tempoServicoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private SefipRepository categoriaSefipRepository;
	
	@Autowired
	private TipoAverbacaoRepository tipoAverbacaoRepository;
	
	@Autowired
	private ClassificacaoAtoRepository classificacaoAtoRepository;
	
	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;

	public TempoServicoResponse getTempoServicoById(Long tempoServicoId) {
		TempoServico tempoServico = tempoServicoRepository.findById(tempoServicoId)
				.orElseThrow(() -> new ResourceNotFoundException("TempoServico", "id", tempoServicoId));

		Usuario criadoPor = usuarioRepository.findById(tempoServico.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", tempoServico.getCreatedBy()));

		Usuario alteradoPor = usuarioRepository.findById(tempoServico.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", tempoServico.getUpdatedBy()));
		
		TempoServicoResponse tempoServicoResponse = new TempoServicoResponse(tempoServico, tempoServico.getCreatedAt(),
				criadoPor.getNome(), tempoServico.getUpdatedAt(), alteradoPor.getNome());

		return tempoServicoResponse;
	}

	public void deleteTempoServico(Long id) {
		TempoServico tempoServico = tempoServicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TempoServico", "id", id));
		tempoServicoRepository.delete(tempoServico);
	}

	public void createTempoServico(TempoServicoRequest tempoServicoRequest) {

		TempoServico tempoServico = new TempoServico(tempoServicoRequest);
		
		setEntidades(tempoServicoRequest, tempoServico);

		tempoServicoRepository.save(tempoServico);

	}

	public void updateTempoServico(TempoServicoRequest tempoServicoRequest) {

		TempoServico tempoServico = tempoServicoRepository.findById(tempoServicoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("TempoServico", "id", tempoServicoRequest.getId()));
		
		setEntidades(tempoServicoRequest, tempoServico);
		
		tempoServico.setProtocoloCtcCts(tempoServicoRequest.getProtocoloCtcCts());
		tempoServico.setSalario(tempoServicoRequest.getSalario());
		
		tempoServico.setIndiceContribuicao(IndiceContribuicaoPrevidenciaEnum.
				valueOf(tempoServicoRequest.getIndiceContribuicao()));
		
		tempoServico.setUltimoCargo(tempoServicoRequest.getUltimoCargo());
		tempoServico.setDescricaoCertidao(tempoServicoRequest.getDescricaoCertidao());
		tempoServico.setNumeroProcesso(tempoServicoRequest.getNumeroProcesso());
		tempoServico.setData(tempoServicoRequest.getData());
		tempoServico.setAnoPublicacao(tempoServicoRequest.getAnoPublicacao());
		tempoServico.setDataDiarioOficial(tempoServicoRequest.getDataDiarioOficial());
		tempoServico.setNumeroDiarioOficial(tempoServicoRequest.getNumeroDiarioOficial());
		tempoServico.setNumeroAto(tempoServicoRequest.getNumeroAto());
		
		tempoServico.setDescricaoEmpresa(tempoServicoRequest.getDescricaoEmpresa());
		tempoServico.setTipoAverbacao(TipoAverbacaoEnum.valueOf(tempoServicoRequest.getTipoAverbacao()));
		tempoServico.setAverbado(Utils.setBool(tempoServicoRequest.getAverbado()));
		tempoServico.setDataInicio(tempoServicoRequest.getDataInicio());
		tempoServico.setDataTermino(tempoServicoRequest.getDataTermino());
		tempoServico.setQtdDias(tempoServicoRequest.getQtdDias());
		tempoServico.setEndereco(tempoServicoRequest.getEndereco());
		tempoServico.setCidade(tempoServicoRequest.getCidade());
		tempoServico.setNumero(tempoServicoRequest.getNumero());
		tempoServico.setComplemento(tempoServicoRequest.getComplemento());
		tempoServico.setBairro(tempoServicoRequest.getBairro());
		tempoServico.setCep(tempoServicoRequest.getCep());
		tempoServico.setTelefone(tempoServicoRequest.getTelefone());
		tempoServico.setCnpj(tempoServicoRequest.getCnpj());

		tempoServicoRepository.save(tempoServico);

	}
	
	private void setEntidades(TempoServicoRequest tempoServicoRequest, TempoServico tempoServico) {
		Funcionario funcionario = funcionarioRepository.findById(tempoServicoRequest.getFuncionario().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", tempoServicoRequest.getFuncionario().getId()));
		tempoServico.setFuncionario(funcionario);
		
		Sefip categoriaSefip = categoriaSefipRepository.findById(tempoServicoRequest.getCategoriaSefipId())
				.orElseThrow(() -> new ResourceNotFoundException("Sefip", "id", tempoServicoRequest.getCategoriaSefipId()));
		tempoServico.setCategoriaSefip(categoriaSefip);
				
		TipoAverbacao tipoAverbacao = tipoAverbacaoRepository.findById(tempoServicoRequest.getEfeitoAverbacaoId())
				.orElseThrow(() -> new ResourceNotFoundException("TipoAverbacao", "id", tempoServicoRequest.getEfeitoAverbacaoId()));
		tempoServico.setEfeitoAverbacao(tipoAverbacao);
		
		ClassificacaoAto classificacaoAto = classificacaoAtoRepository.findById(tempoServicoRequest.getClassificacaoAtoId())
				.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoAto", "id", tempoServicoRequest.getClassificacaoAtoId()));
		tempoServico.setClassificacaoAto(classificacaoAto);
		
		UnidadeFederativa uf = unidadeFederativaRepository.findById(tempoServicoRequest.getUfId())
				.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoAto", "id", tempoServicoRequest.getUfId()));
		tempoServico.setUf(uf);
		
		
	}

}
