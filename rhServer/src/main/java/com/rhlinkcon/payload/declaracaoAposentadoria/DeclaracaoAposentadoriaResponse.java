package com.rhlinkcon.payload.declaracaoAposentadoria;

import java.util.List;
import java.util.stream.Collectors;

import com.rhlinkcon.model.DeclaracaoAposentadoria;
import com.rhlinkcon.model.DeclaracaoAposentadoriaTipoEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.declaracaoAposentadoriaAssinatura.DeclaracaoAposentadoriaAssinaturaResponse;
import com.rhlinkcon.payload.declaracaoAposentadoriaAverbacao.DeclaracaoAposentadoriaAverbacaoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.util.Projecao;

public class DeclaracaoAposentadoriaResponse extends DadoCadastralResponse {

	private Long id;

	private Long numero;

	private Integer ano;

	private FuncionarioResponse funcionario;

	private List<AnexoResponse> anexos;

	private List<DeclaracaoAposentadoriaAverbacaoResponse> averbacoes;

	private List<DeclaracaoAposentadoriaAssinaturaResponse> assinaturas;

	private Long numeroRetificacao;

	private Boolean arquivada;

	private DeclaracaoAposentadoriaTipoEnum tipoDeclaracao;

	private String tipoDeclaracaoLabel;

	private Boolean rascunho;

	public DeclaracaoAposentadoriaResponse(DeclaracaoAposentadoria obj, Projecao projecao) {

		this.id = obj.getId();
		this.numero = obj.getNumero();
		this.ano = obj.getAno();
		
		this.arquivada = obj.getArquivada();
		this.tipoDeclaracao = obj.getTipoDeclaracao();
		this.tipoDeclaracaoLabel = obj.getTipoDeclaracao().getLabel();
		this.rascunho = !this.tipoDeclaracao.equals(DeclaracaoAposentadoriaTipoEnum.RASCUNHO) ? false : true;
		this.numeroRetificacao = obj.getNumeroRetificacao();

		setAlteradoEm(obj.getUpdatedAt());
		setCriadoEm(obj.getCreatedAt());

		if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA))
			if (obj.getFuncionario() != null)
				this.funcionario = new FuncionarioResponse(obj.getFuncionario(), Projecao.APOSENTADORIA);

		if (projecao.equals(Projecao.COMPLETA)) {
			
			if (obj.getAnexos() != null && !obj.getAnexos().isEmpty())
				this.anexos = obj.getAnexos().stream().map(anexo -> new AnexoResponse(anexo))
						.collect(Collectors.toList());

			if (obj.getAverbacoes() != null && !obj.getAverbacoes().isEmpty())
				this.averbacoes = obj.getAverbacoes().stream().map(a -> new DeclaracaoAposentadoriaAverbacaoResponse(a))
						.collect(Collectors.toList());

			if (obj.getAssinaturas() != null && !obj.getAssinaturas().isEmpty())
				this.assinaturas = obj.getAssinaturas().stream()
						.map(a -> new DeclaracaoAposentadoriaAssinaturaResponse(a)).collect(Collectors.toList());
		}

	}

	public DeclaracaoAposentadoriaTipoEnum getTipoDeclaracao() {
		return tipoDeclaracao;
	}

	public void setTipoDeclaracao(DeclaracaoAposentadoriaTipoEnum tipoDeclaracao) {
		this.tipoDeclaracao = tipoDeclaracao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public List<AnexoResponse> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<AnexoResponse> anexos) {
		this.anexos = anexos;
	}

	public List<DeclaracaoAposentadoriaAverbacaoResponse> getAverbacoes() {
		return averbacoes;
	}

	public void setAverbacoes(List<DeclaracaoAposentadoriaAverbacaoResponse> averbacoes) {
		this.averbacoes = averbacoes;
	}

	public List<DeclaracaoAposentadoriaAssinaturaResponse> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(List<DeclaracaoAposentadoriaAssinaturaResponse> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public Long getNumeroRetificacao() {
		return numeroRetificacao;
	}

	public void setNumeroRetificacao(Long numeroRetificacao) {
		this.numeroRetificacao = numeroRetificacao;
	}

	public Boolean getRascunho() {
		return rascunho;
	}

	public void setRascunho(Boolean rascunho) {
		this.rascunho = rascunho;
	}

	public Boolean getArquivada() {
		return arquivada;
	}

	public void setArquivada(Boolean arquivada) {
		this.arquivada = arquivada;
	}

	public String getTipoDeclaracaoLabel() {
		return tipoDeclaracaoLabel;
	}

	public void setTipoDeclaracaoLabel(String tipoDeclaracaoLabel) {
		this.tipoDeclaracaoLabel = tipoDeclaracaoLabel;
	}

}
