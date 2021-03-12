package com.rhlinkcon.payload.correcao;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Correcao;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrecaoResponse extends DadoCadastralResponse {

	private Long id;

	@NotNull
	private Instant dataCompetencia;

	@NotNull
	private Long tipoProcessamentoId;

	private EmpresaFilialResponse empresa;

	private EmpresaFilialResponse filial;

	private Long lotacaoId;

	private Long centroCustoId;

	private Long sindicatoId;

	private Long funcaoId;

	private Long nivelSalarialId;

	private Long situacaoId;

	private Long tipoFolhaId;

	private Long verbaId;

	private BigDecimal valorFaixaI;

	private BigDecimal valorFaixaII;

	private Double percentualProduto;

	private Double percentualAumento;

	private BigDecimal valorProduto;

	private BigDecimal valorPiso;

	private String tipoArredondamento;

	private Integer avos;

	private BigDecimal valorArrendamento;

	private Integer atualizaMes;

	private Instant retroativo;

	private Boolean abono;

	private Integer verbaAbono;

	public CorrecaoResponse(Correcao correcao) {
		setCorrecao(correcao);
	}

	public CorrecaoResponse(Correcao correcao, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setCorrecao(correcao);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setCorrecao(Correcao correcao) {
		this.setId(correcao.getId());
		this.setDataCompetencia(correcao.getDataCompetencia());

		if (Objects.nonNull(correcao.getTipoProcessamento()))
			this.setTipoProcessamentoId(correcao.getTipoProcessamento().getId());

		if (Objects.nonNull(correcao.getEmpresa()))
			this.setEmpresa(new EmpresaFilialResponse(correcao.getEmpresa()));

		if (Objects.nonNull(correcao.getFilial()))
			this.setFilial(new EmpresaFilialResponse(correcao.getFilial()));

		if (Objects.nonNull(correcao.getLotacao()))
			this.setLotacaoId(correcao.getLotacao().getId());

		if (Objects.nonNull(correcao.getCentroCusto()))
			this.setCentroCustoId(correcao.getCentroCusto().getId());

		if (Objects.nonNull(correcao.getSindicato()))
			this.setSindicatoId(correcao.getSindicato().getId());

		if (Objects.nonNull(correcao.getFuncao()))
			this.setFuncaoId(correcao.getFuncao().getId());

		if (Objects.nonNull(correcao.getNivelSalarial()))
			this.setNivelSalarialId(correcao.getNivelSalarial().getId());

		if (Objects.nonNull(correcao.getSituacao()))
			this.setSituacaoId(correcao.getSituacao().getId());

		if (Objects.nonNull(correcao.getTipoFolha()))
			this.setTipoFolhaId(correcao.getTipoFolha().getId());

		if (Objects.nonNull(correcao.getVerba()))
			this.setVerbaId(correcao.getVerba().getId());

		this.setValorFaixaI(correcao.getValorFaixaI());
		this.setValorFaixaII(correcao.getValorFaixaII());
		this.setPercentualProduto(correcao.getPercentualProduto());
		this.setPercentualAumento(correcao.getPercentualAumento());
		this.setValorPiso(correcao.getValorPiso());
		
		if (Objects.nonNull(correcao.getTipoArredondamento()))
			this.setTipoArredondamento(correcao.getTipoArredondamento().toString());
		
		this.setAvos(correcao.getAvos());
		this.setValorArrendamento(correcao.getValorArrendamento());
		this.setAtualizaMes(correcao.getAtualizaMes());
		this.setRetroativo(correcao.getRetroativo());
		this.setAbono(correcao.getAbono());
		this.setVerbaAbono(correcao.getVerbaAbono());

	}

	public Long getId() {
		return id;
	}

	public Instant getDataCompetencia() {
		return dataCompetencia;
	}

	public Long getTipoProcessamentoId() {
		return tipoProcessamentoId;
	}

	public Long getLotacaoId() {
		return lotacaoId;
	}

	public Long getCentroCustoId() {
		return centroCustoId;
	}

	public Long getSindicatoId() {
		return sindicatoId;
	}

	public Long getFuncaoId() {
		return funcaoId;
	}

	public Long getNivelSalarialId() {
		return nivelSalarialId;
	}

	public Long getSituacaoId() {
		return situacaoId;
	}

	public Long getTipoFolhaId() {
		return tipoFolhaId;
	}

	public Long getVerbaId() {
		return verbaId;
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

	public String getTipoArredondamento() {
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

	public void setTipoProcessamentoId(Long tipoProcessamentoId) {
		this.tipoProcessamentoId = tipoProcessamentoId;
	}

	public void setLotacaoId(Long lotacaoId) {
		this.lotacaoId = lotacaoId;
	}

	public void setCentroCustoId(Long centroCustoId) {
		this.centroCustoId = centroCustoId;
	}

	public void setSindicatoId(Long sindicatoId) {
		this.sindicatoId = sindicatoId;
	}

	public void setFuncaoId(Long funcaoId) {
		this.funcaoId = funcaoId;
	}

	public void setNivelSalarialId(Long nivelSalarialId) {
		this.nivelSalarialId = nivelSalarialId;
	}

	public void setSituacaoId(Long situcaoId) {
		this.situacaoId = situcaoId;
	}

	public void setTipoFolhaId(Long tipoFolhaId) {
		this.tipoFolhaId = tipoFolhaId;
	}

	public void setVerbaId(Long verbaId) {
		this.verbaId = verbaId;
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

	public void setTipoArredondamento(String tipoArredondamento) {
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

	public EmpresaFilialResponse getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFilialResponse empresa) {
		this.empresa = empresa;
	}

	public EmpresaFilialResponse getFilial() {
		return filial;
	}

	public void setFilial(EmpresaFilialResponse filial) {
		this.filial = filial;
	}

}
