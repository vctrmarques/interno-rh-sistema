package com.rhlinkcon.model;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.correcao.CorrecaoRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Correção")
@Table(name = "correcao")
public class Correcao extends UserDateAudit {

	private static final long serialVersionUID = 4440215695661565547L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "dt_competencia")
	private Instant dataCompetencia;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_processamento_id")
	private TipoProcessamento tipoProcessamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	private EmpresaFilial empresa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "filial_id")
	private EmpresaFilial filial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lotacao_id")
	private Lotacao lotacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "centro_custo_id")
	private CentroCusto centroCusto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sindicato_id")
	private Sindicato sindicato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcao_id")
	private Funcao funcao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nivel_salarial_id")
	private ReferenciaSalarial nivelSalarial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "situacao_afastamento_id")
	private SituacaoFuncional situacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_folha_id")
	private TipoFolha tipoFolha;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verba_id")
	private Verba verba;

	@Column(name = "valor_faixa_I")
	private BigDecimal valorFaixaI;

	@Column(name = "valor_faixa_II")
	private BigDecimal valorFaixaII;

	@Column(name = "percentual_produto")
	private Double percentualProduto;

	@Column(name = "percentual_aumento")
	private Double percentualAumento;

	@Column(name = "valor_produto")
	private BigDecimal valorProduto;

	@Column(name = "valor_piso")
	private BigDecimal valorPiso;

	@Column(name = "tipo_arredondamento")
	private TipoArredondamentoEnum tipoArredondamento;

	@Column(name = "avos")
	private Integer avos;

	@Column(name = "valor_arrendamento")
	private BigDecimal valorArrendamento;

	@Column(name = "atualiza_mes")
	private Integer atualizaMes;

	@Column(name = "retroativo")
	private Instant retroativo;

	@Column(name = "abono")
	private Boolean abono;

	@Column(name = "verba_abono")
	private Integer verbaAbono;

	public Correcao() {
	}

	public Correcao(CorrecaoRequest correcaoRequest) {
		this.setDataCompetencia(correcaoRequest.getDataCompetencia());
		this.setValorFaixaI(correcaoRequest.getValorFaixaI());
		this.setValorFaixaII(correcaoRequest.getValorFaixaII());
		this.setPercentualAumento(correcaoRequest.getPercentualAumento());
		this.setPercentualProduto(correcaoRequest.getPercentualProduto());
		this.setValorProduto(correcaoRequest.getValorProduto());
		this.setValorPiso(correcaoRequest.getValorPiso());

		if (Utils.checkStr(correcaoRequest.getTipoArredondamento()))
			this.setTipoArredondamento(TipoArredondamentoEnum.valueOf(correcaoRequest.getTipoArredondamento()));
		this.setAvos(correcaoRequest.getAvos());
		this.setValorArrendamento(correcaoRequest.getValorArrendamento());
		this.setAtualizaMes(correcaoRequest.getAtualizaMes());
		this.setRetroativo(correcaoRequest.getRetroativo());
		this.setAbono(correcaoRequest.getAbono());
		this.setVerbaAbono(correcaoRequest.getVerbaAbono());
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public Instant getDataCompetencia() {
		return dataCompetencia;
	}

	public TipoProcessamento getTipoProcessamento() {
		return tipoProcessamento;
	}

	public EmpresaFilial getEmpresa() {
		return empresa;
	}

	public EmpresaFilial getFilial() {
		return filial;
	}

	public Lotacao getLotacao() {
		return lotacao;
	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public Sindicato getSindicato() {
		return sindicato;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public ReferenciaSalarial getNivelSalarial() {
		return nivelSalarial;
	}

	public SituacaoFuncional getSituacao() {
		return situacao;
	}

	public TipoFolha getTipoFolha() {
		return tipoFolha;
	}

	public Verba getVerba() {
		return verba;
	}

	public BigDecimal getValorFaixaI() {
		return valorFaixaI;
	}

	public BigDecimal getValorFaixaII() {
		return valorFaixaII;
	}

	public Double getPercentualProduto() {
		return percentualProduto;
	}

	public Double getPercentualAumento() {
		return percentualAumento;
	}

	public BigDecimal getValorProduto() {
		return valorProduto;
	}

	public BigDecimal getValorPiso() {
		return valorPiso;
	}

	public TipoArredondamentoEnum getTipoArredondamento() {
		return tipoArredondamento;
	}

	public Integer getAvos() {
		return avos;
	}

	public BigDecimal getValorArrendamento() {
		return valorArrendamento;
	}

	public Integer getAtualizaMes() {
		return atualizaMes;
	}

	public Instant getRetroativo() {
		return retroativo;
	}

	public Boolean getAbono() {
		return abono;
	}

	public Integer getVerbaAbono() {
		return verbaAbono;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDataCompetencia(Instant dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}

	public void setTipoProcessamento(TipoProcessamento tipoProcessamento) {
		this.tipoProcessamento = tipoProcessamento;
	}

	public void setEmpresa(EmpresaFilial empresa) {
		this.empresa = empresa;
	}

	public void setFilial(EmpresaFilial filial) {
		this.filial = filial;
	}

	public void setLotacao(Lotacao lotacao) {
		this.lotacao = lotacao;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public void setSindicato(Sindicato sindicato) {
		this.sindicato = sindicato;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public void setNivelSalarial(ReferenciaSalarial nivelSalarial) {
		this.nivelSalarial = nivelSalarial;
	}

	public void setSituacao(SituacaoFuncional situacao) {
		this.situacao = situacao;
	}

	public void setTipoFolha(TipoFolha tipoFolha) {
		this.tipoFolha = tipoFolha;
	}

	public void setVerba(Verba verba) {
		this.verba = verba;
	}

	public void setValorFaixaI(BigDecimal valorFaixaI) {
		this.valorFaixaI = valorFaixaI;
	}

	public void setValorFaixaII(BigDecimal valorFaixaII) {
		this.valorFaixaII = valorFaixaII;
	}

	public void setPercentualProduto(Double percentualProduto) {
		this.percentualProduto = percentualProduto;
	}

	public void setPercentualAumento(Double percentualAumento) {
		this.percentualAumento = percentualAumento;
	}

	public void setValorProduto(BigDecimal valorProduto) {
		this.valorProduto = valorProduto;
	}

	public void setValorPiso(BigDecimal valorPiso) {
		this.valorPiso = valorPiso;
	}

	public void setTipoArredondamento(TipoArredondamentoEnum tipoArredondamento) {
		this.tipoArredondamento = tipoArredondamento;
	}

	public void setAvos(Integer avos) {
		this.avos = avos;
	}

	public void setValorArrendamento(BigDecimal valorArrendamento) {
		this.valorArrendamento = valorArrendamento;
	}

	public void setAtualizaMes(Integer atualizaMes) {
		this.atualizaMes = atualizaMes;
	}

	public void setRetroativo(Instant retroativo) {
		this.retroativo = retroativo;
	}

	public void setAbono(Boolean abono) {
		this.abono = abono;
	}

	public void setVerbaAbono(Integer verbaAbono) {
		this.verbaAbono = verbaAbono;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
