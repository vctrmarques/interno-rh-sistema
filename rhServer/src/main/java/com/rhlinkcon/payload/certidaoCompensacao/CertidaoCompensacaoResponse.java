package com.rhlinkcon.payload.certidaoCompensacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.rhlinkcon.model.CertidaoCompensacao;
import com.rhlinkcon.model.CertidaoCompensacaoTipoEnum;
import com.rhlinkcon.model.ClassificacaoCertidaoCompensacaoEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.certidaoCompensacaoAssinatura.CertidaoCompensacaoAssinaturaResponse;
import com.rhlinkcon.payload.certidaoCompensacaoPeriodo.CertidaoCompensacaoPeriodoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.util.Projecao;

public class CertidaoCompensacaoResponse extends DadoCadastralResponse {

	private Long id;

	private Long numero;

	private Integer ano;

	private FuncionarioResponse funcionario;

	private String statusAtual;

	private List<String> classificacoes;

	private String fundo;

	private LotacaoResponse lotacao;

	private String processo;

	private List<AnexoResponse> anexos;

	private List<CertidaoCompensacaoPeriodoResponse> periodos;

	private List<CertidaoCompensacaoAssinaturaResponse> assinaturas;

	private Long numeroRetificacao;

	private Boolean arquivada;

	private CertidaoCompensacaoTipoEnum tipoCertidaoCompensacao;

	private String tipoCertidaoCompensacaoLabel;

	private Boolean rascunho;

	public CertidaoCompensacaoResponse(CertidaoCompensacao obj, Projecao projecao) {

		this.id = obj.getId();
		this.numero = obj.getNumero();
		this.ano = obj.getAno();

		this.arquivada = obj.getArquivada();
		this.tipoCertidaoCompensacao = obj.getTipoCertidaoCompensacao();
		this.tipoCertidaoCompensacaoLabel = obj.getTipoCertidaoCompensacao().getLabel();
		this.rascunho = !this.tipoCertidaoCompensacao.equals(CertidaoCompensacaoTipoEnum.RASCUNHO) ? false : true;
		this.numeroRetificacao = obj.getNumeroRetificacao();

		if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {

			if (Objects.nonNull(obj.getFuncionario()))
				this.funcionario = new FuncionarioResponse(obj.getFuncionario(), Projecao.CERTIDAO_COMPENSACAO);

			if (Objects.nonNull(obj.getStatusAtual()))
				this.statusAtual = obj.getStatusAtual().getLabel();

			if (Objects.nonNull(obj.getLotacao()))
				this.lotacao = new LotacaoResponse(obj.getLotacao(), Projecao.BASICA);
			this.processo = obj.getProcesso();

			if (Objects.nonNull(obj.getFundo()))
				this.fundo = obj.getFundo().getLabel();
			if (Objects.nonNull(obj.getClassificacoes())) {
				this.classificacoes = new ArrayList<String>();
				for (ClassificacaoCertidaoCompensacaoEnum enu : obj.getClassificacoes())
					this.classificacoes.add(enu.toString());
			}
			setAlteradoEm(obj.getUpdatedAt());
			setCriadoEm(obj.getCreatedAt());
		}

		if (projecao.equals(Projecao.COMPLETA)) {
			if (!obj.getAnexos().isEmpty())
				this.anexos = obj.getAnexos().stream().map(anexo -> new AnexoResponse(anexo))
						.collect(Collectors.toList());

			if (!obj.getPeriodos().isEmpty())
				this.periodos = obj.getPeriodos().stream().map(a -> new CertidaoCompensacaoPeriodoResponse(a))
						.collect(Collectors.toList());

			if (!obj.getAssinaturas().isEmpty())
				this.assinaturas = obj.getAssinaturas().stream().map(a -> new CertidaoCompensacaoAssinaturaResponse(a))
						.collect(Collectors.toList());
		}

	}

	public Long getNumeroRetificacao() {
		return numeroRetificacao;
	}

	public void setNumeroRetificacao(Long numeroRetificacao) {
		this.numeroRetificacao = numeroRetificacao;
	}

	public Boolean getArquivada() {
		return arquivada;
	}

	public void setArquivada(Boolean arquivada) {
		this.arquivada = arquivada;
	}

	public CertidaoCompensacaoTipoEnum getTipoCertidaoCompensacao() {
		return tipoCertidaoCompensacao;
	}

	public void setTipoCertidaoCompensacao(CertidaoCompensacaoTipoEnum tipoCertidaoCompensacao) {
		this.tipoCertidaoCompensacao = tipoCertidaoCompensacao;
	}

	public String getTipoCertidaoCompensacaoLabel() {
		return tipoCertidaoCompensacaoLabel;
	}

	public void setTipoCertidaoCompensacaoLabel(String tipoCertidaoCompensacaoLabel) {
		this.tipoCertidaoCompensacaoLabel = tipoCertidaoCompensacaoLabel;
	}

	public Boolean getRascunho() {
		return rascunho;
	}

	public void setRascunho(Boolean rascunho) {
		this.rascunho = rascunho;
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

	public String getStatusAtual() {
		return statusAtual;
	}

	public void setStatusAtual(String statusAtual) {
		this.statusAtual = statusAtual;
	}

	public LotacaoResponse getLotacao() {
		return lotacao;
	}

	public void setLotacao(LotacaoResponse lotacao) {
		this.lotacao = lotacao;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public List<AnexoResponse> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<AnexoResponse> anexos) {
		this.anexos = anexos;
	}

	public List<CertidaoCompensacaoPeriodoResponse> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<CertidaoCompensacaoPeriodoResponse> periodos) {
		this.periodos = periodos;
	}

	public List<CertidaoCompensacaoAssinaturaResponse> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(List<CertidaoCompensacaoAssinaturaResponse> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public String getFundo() {
		return fundo;
	}

	public void setFundo(String fundo) {
		this.fundo = fundo;
	}

	public List<String> getClassificacoes() {
		return classificacoes;
	}

	public void setClassificacoes(List<String> classificacoes) {
		this.classificacoes = classificacoes;
	}
}
