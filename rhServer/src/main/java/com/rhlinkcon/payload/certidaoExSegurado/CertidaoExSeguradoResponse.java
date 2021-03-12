package com.rhlinkcon.payload.certidaoExSegurado;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.rhlinkcon.model.AbaAssinaturaCertidao;
import com.rhlinkcon.model.CertidaoExSegurado;
import com.rhlinkcon.model.GrauDeficienciaEnum;
import com.rhlinkcon.model.StatusSituacaoCertidaoExSeguradoEnum;
import com.rhlinkcon.model.TipoTempoEspecialEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.util.Projecao;

public class CertidaoExSeguradoResponse extends DadoCadastralResponse {
	
	private Long id;
	
	private FuncionarioResponse funcionario;
	
	private Integer numeroCertidao;
	
	private Long numeroRetificacao;

	private Integer anoCertidao;

	private String statusSituacaoCertidao;

	private LotacaoResponse lotacao;

	private Instant dataExoneracao;
	
	private String fonteInformacao;
	
	private boolean rascunho;
	
	private boolean arquivado;
	
	private List<AnexoResponse> anexos;
	
	private List<AssinaturaCertidaoExSeguradoResponse> assinaturas;
	private List<FrequenciaCertidaoExSeguradoResponse> frequencias;
	private List<FrequenciaCertidaoExServidorDetalhamentoResponse> detalhamentosFrequencia;
	private List<PeriodoCertidaoExSeguradoResponse> periodos;
	private List<RelacaoRemuneracaoCertidaoExSeguradoResponse> relacaoRemuneracoes;
	private List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecial;
	private List<CertidaoExServidorCargoResponse> cargos;
	private List<CertidaoExServidorOrgaoLotacaoResponse> orgaosLotacao;
	
	
	//Utilizado no relatório
	private List<AssinaturaCertidaoExSeguradoResponse> assinaturasCertidao;
	private List<AssinaturaCertidaoExSeguradoResponse> assinaturasDetalhamento;
	private List<AssinaturaCertidaoExSeguradoResponse> assinaturasRelacao;
	
	private List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecialGrauLeve;
	private List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecialGrauModerado;
	private List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecialGrauGrave;
	
	private List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecialAtividadeRisco;
	private List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecialCondicoesEspeciais;
	
	public CertidaoExSeguradoResponse(CertidaoExSegurado obj, Projecao projecao) {
		
		setId(obj.getId());
		setFuncionario(new FuncionarioResponse(obj.getFuncionario(), Projecao.CERTIDAO_EXSEGURADO));
		setNumeroCertidao(obj.getNumeroCertidao());
		setNumeroRetificacao(obj.getNumeroRetificacao());
		setAnoCertidao(obj.getAnoCertidao());
		setStatusSituacaoCertidao(obj.getStatusSituacaoCertidao().getLabel());
		setLotacao(new LotacaoResponse(obj.getLotacao(), Projecao.BASICA));
		setDataExoneracao(obj.getDataExoneracao());
		setFonteInformacao(obj.getFonteInformacao());
		setRascunho(obj.getStatusSituacaoCertidao().equals(StatusSituacaoCertidaoExSeguradoEnum.RASCUNHO));
		setArquivado(obj.getStatusSituacaoCertidao().equals(StatusSituacaoCertidaoExSeguradoEnum.ARQUIVADO));
		
		setCriadoEm(obj.getCreatedAt());
		
		if(projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			if (Objects.nonNull(obj.getAnexos()))
				this.anexos = obj.getAnexos().stream().map(anexo -> new AnexoResponse(anexo)).collect(Collectors.toList());
		}
		
		if(projecao.equals(Projecao.COMPLETA) || projecao.equals(Projecao.RELATORIO_EXSEGURADO)) {
			if (Objects.nonNull(obj.getAssinaturas()))
				this.assinaturas = obj.getAssinaturas().stream().map(a -> new AssinaturaCertidaoExSeguradoResponse(a)).collect(Collectors.toList());
			
			if (Objects.nonNull(obj.getFrequencias()))
				this.frequencias = obj.getFrequencias().stream().map(a -> new FrequenciaCertidaoExSeguradoResponse(a, Projecao.COMPLETA)).collect(Collectors.toList());
			
			if (Objects.nonNull(obj.getDetalhamentosFrequencia()))
				this.detalhamentosFrequencia = obj.getDetalhamentosFrequencia().stream().map(a -> new FrequenciaCertidaoExServidorDetalhamentoResponse(a)).collect(Collectors.toList());
			
			if (Objects.nonNull(obj.getPeriodos()))
				this.periodos = obj.getPeriodos().stream().map(a -> new PeriodoCertidaoExSeguradoResponse(a)).collect(Collectors.toList());
			
			if (Objects.nonNull(obj.getRelacaoRemuneracoes()))
				this.relacaoRemuneracoes = obj.getRelacaoRemuneracoes().stream().map(a -> new RelacaoRemuneracaoCertidaoExSeguradoResponse(a)).collect(Collectors.toList());
			
			if (Objects.nonNull(obj.getTempoEspecial())) {
				this.tempoEspecial = obj.getTempoEspecial().stream().map(a -> new TempoEspecialCertidaoExSeguradoResponse(a)).collect(Collectors.toList());
				
				this.tempoEspecialGrauLeve = new ArrayList<>();
				this.tempoEspecialGrauModerado = new ArrayList<>();
				this.tempoEspecialGrauGrave = new ArrayList<>();
				this.tempoEspecialAtividadeRisco = new ArrayList<>();
				this.tempoEspecialCondicoesEspeciais = new ArrayList<>();
				
				for (TempoEspecialCertidaoExSeguradoResponse t : getTempoEspecial()) {
					if(TipoTempoEspecialEnum.getEnumByString(t.getTipoTempo()).equals(TipoTempoEspecialEnum.EXERCIDO_CONDICAO_DEFICIENCIA)) {
						if (GrauDeficienciaEnum.getEnumByString(t.getGrauDeficiencia()).equals(GrauDeficienciaEnum.LEVE)) {
							this.tempoEspecialGrauLeve.add(t);
						}
						if (GrauDeficienciaEnum.getEnumByString(t.getGrauDeficiencia()).equals(GrauDeficienciaEnum.MODERADA)) {
							this.tempoEspecialGrauModerado.add(t);
						}
						if (GrauDeficienciaEnum.getEnumByString(t.getGrauDeficiencia()).equals(GrauDeficienciaEnum.GRAVE)) {
							this.tempoEspecialGrauGrave.add(t);
						}
					}
					if(TipoTempoEspecialEnum.getEnumByString(t.getTipoTempo()).equals(TipoTempoEspecialEnum.EXERCIDO_ATIVIDADE_RISCO)) {
						this.tempoEspecialAtividadeRisco.add(t);
					}
					if(TipoTempoEspecialEnum.getEnumByString(t.getTipoTempo()).equals(TipoTempoEspecialEnum.EXERCIDO_ATIVIDADE_CONDICAO_ESPECIAL_PREJUDICIAL_SAUDE)) {
						this.tempoEspecialCondicoesEspeciais.add(t);
					}
					
					
				}
			}
			
			if (Objects.nonNull(obj.getCargos()))
				setCargos(obj.getCargos().stream().map(a -> new CertidaoExServidorCargoResponse(a)).collect(Collectors.toList()));
			
			if (Objects.nonNull(obj.getOrgaosLotacao()))
				setOrgaosLotacao(obj.getOrgaosLotacao().stream().map(a -> new CertidaoExServidorOrgaoLotacaoResponse(a)).collect(Collectors.toList()));
			
		}
		
		if(projecao.equals(Projecao.RELATORIO_EXSEGURADO)) {
			if(Objects.nonNull(getAssinaturas())) {
				this.assinaturasCertidao = new ArrayList<>();
				this.assinaturasDetalhamento = new ArrayList<>();
				this.assinaturasRelacao = new ArrayList<>();
				
				for (AssinaturaCertidaoExSeguradoResponse a : getAssinaturas()) {
					if(AbaAssinaturaCertidao.getEnumByString(a.getAbaAssinaturaCertidao()).equals(AbaAssinaturaCertidao.CERTIDAO)) {
						this.assinaturasCertidao.add(a);
					}
					if(AbaAssinaturaCertidao.getEnumByString(a.getAbaAssinaturaCertidao()).equals(AbaAssinaturaCertidao.DETALHAMENTO)) {
						this.assinaturasDetalhamento.add(a);
					}
					if(AbaAssinaturaCertidao.getEnumByString(a.getAbaAssinaturaCertidao()).equals(AbaAssinaturaCertidao.RELACAO)) {
						this.assinaturasRelacao.add(a);
					}
				}
			}
			
		}
	}

	public CertidaoExSeguradoResponse() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public Integer getNumeroCertidao() {
		return numeroCertidao;
	}

	public void setNumeroCertidao(Integer numeroCertidao) {
		this.numeroCertidao = numeroCertidao;
	}

	public Integer getAnoCertidao() {
		return anoCertidao;
	}

	public void setAnoCertidao(Integer anoCertidao) {
		this.anoCertidao = anoCertidao;
	}

	public String getStatusSituacaoCertidao() {
		return statusSituacaoCertidao;
	}

	public void setStatusSituacaoCertidao(String statusSituacaoCertidao) {
		this.statusSituacaoCertidao = statusSituacaoCertidao;
	}

	public LotacaoResponse getLotacao() {
		return lotacao;
	}

	public void setLotacao(LotacaoResponse lotacao) {
		this.lotacao = lotacao;
	}

	public Instant getDataExoneracao() {
		return dataExoneracao;
	}

	public void setDataExoneracao(Instant dataExoneracao) {
		this.dataExoneracao = dataExoneracao;
	}

	public String getFonteInformacao() {
		return fonteInformacao;
	}

	public void setFonteInformacao(String fonteInformacao) {
		this.fonteInformacao = fonteInformacao;
	}

	public List<AnexoResponse> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<AnexoResponse> anexos) {
		this.anexos = anexos;
	}

	public List<AssinaturaCertidaoExSeguradoResponse> getAssinaturasCertidao() {
		return assinaturasCertidao;
	}

	public void setAssinaturasCertidao(List<AssinaturaCertidaoExSeguradoResponse> assinaturasCertidao) {
		this.assinaturasCertidao = assinaturasCertidao;
	}

	public List<AssinaturaCertidaoExSeguradoResponse> getAssinaturasDetalhamento() {
		return assinaturasDetalhamento;
	}

	public void setAssinaturasDetalhamento(List<AssinaturaCertidaoExSeguradoResponse> assinaturasDetalhamento) {
		this.assinaturasDetalhamento = assinaturasDetalhamento;
	}

	public List<AssinaturaCertidaoExSeguradoResponse> getAssinaturasRelacao() {
		return assinaturasRelacao;
	}

	public void setAssinaturasRelacao(List<AssinaturaCertidaoExSeguradoResponse> assinaturasRelacao) {
		this.assinaturasRelacao = assinaturasRelacao;
	}

	public List<TempoEspecialCertidaoExSeguradoResponse> getTempoEspecialGrauLeve() {
		return tempoEspecialGrauLeve;
	}

	public void setTempoEspecialGrauLeve(List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecialGrauLeve) {
		this.tempoEspecialGrauLeve = tempoEspecialGrauLeve;
	}

	public List<TempoEspecialCertidaoExSeguradoResponse> getTempoEspecialGrauModerado() {
		return tempoEspecialGrauModerado;
	}

	public void setTempoEspecialGrauModerado(List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecialGrauModerado) {
		this.tempoEspecialGrauModerado = tempoEspecialGrauModerado;
	}

	public List<TempoEspecialCertidaoExSeguradoResponse> getTempoEspecialGrauGrave() {
		return tempoEspecialGrauGrave;
	}

	public void setTempoEspecialGrauGrave(List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecialGrauGrave) {
		this.tempoEspecialGrauGrave = tempoEspecialGrauGrave;
	}

	public List<TempoEspecialCertidaoExSeguradoResponse> getTempoEspecialAtividadeRisco() {
		return tempoEspecialAtividadeRisco;
	}

	public void setTempoEspecialAtividadeRisco(List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecialAtividadeRisco) {
		this.tempoEspecialAtividadeRisco = tempoEspecialAtividadeRisco;
	}

	public List<TempoEspecialCertidaoExSeguradoResponse> getTempoEspecialCondicoesEspeciais() {
		return tempoEspecialCondicoesEspeciais;
	}

	public void setTempoEspecialCondicoesEspeciais(List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecialCondicoesEspeciais) {
		this.tempoEspecialCondicoesEspeciais = tempoEspecialCondicoesEspeciais;
	}

	public Long getNumeroRetificacao() {
		return numeroRetificacao;
	}

	public void setNumeroRetificacao(Long numeroRetificacao) {
		this.numeroRetificacao = numeroRetificacao;
	}

	public boolean isRascunho() {
		return rascunho;
	}

	public void setRascunho(boolean rascunho) {
		this.rascunho = rascunho;
	}

	public List<AssinaturaCertidaoExSeguradoResponse> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(List<AssinaturaCertidaoExSeguradoResponse> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public List<FrequenciaCertidaoExSeguradoResponse> getFrequencias() {
		return frequencias;
	}

	public void setFrequencias(List<FrequenciaCertidaoExSeguradoResponse> frequencias) {
		this.frequencias = frequencias;
	}

	public List<FrequenciaCertidaoExServidorDetalhamentoResponse> getDetalhamentosFrequencia() {
		return detalhamentosFrequencia;
	}

	public void setDetalhamentosFrequencia(List<FrequenciaCertidaoExServidorDetalhamentoResponse> detalhamentosFrequencia) {
		this.detalhamentosFrequencia = detalhamentosFrequencia;
	}

	public List<PeriodoCertidaoExSeguradoResponse> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<PeriodoCertidaoExSeguradoResponse> periodos) {
		this.periodos = periodos;
	}

	public List<RelacaoRemuneracaoCertidaoExSeguradoResponse> getRelacaoRemuneracoes() {
		return relacaoRemuneracoes;
	}

	public void setRelacaoRemuneracoes(List<RelacaoRemuneracaoCertidaoExSeguradoResponse> relacaoRemuneracoes) {
		this.relacaoRemuneracoes = relacaoRemuneracoes;
	}

	public List<TempoEspecialCertidaoExSeguradoResponse> getTempoEspecial() {
		return tempoEspecial;
	}

	public void setTempoEspecial(List<TempoEspecialCertidaoExSeguradoResponse> tempoEspecial) {
		this.tempoEspecial = tempoEspecial;
	}

	public List<CertidaoExServidorCargoResponse> getCargos() {
		return cargos;
	}

	public void setCargos(List<CertidaoExServidorCargoResponse> cargos) {
		this.cargos = cargos;
	}

	public List<CertidaoExServidorOrgaoLotacaoResponse> getOrgaosLotacao() {
		return orgaosLotacao;
	}

	public void setOrgaosLotacao(List<CertidaoExServidorOrgaoLotacaoResponse> orgaosLotacao) {
		this.orgaosLotacao = orgaosLotacao;
	}

	public boolean isArquivado() {
		return arquivado;
	}

	public void setArquivado(boolean arquivado) {
		this.arquivado = arquivado;
	}

}
