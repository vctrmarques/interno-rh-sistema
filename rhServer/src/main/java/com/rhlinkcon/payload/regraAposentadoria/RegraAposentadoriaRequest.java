package com.rhlinkcon.payload.regraAposentadoria;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.ModalidadeAposentadoriaEnum;
import com.rhlinkcon.model.TipoAposentadoriaEnum;
import com.rhlinkcon.model.TipoRegraEnum;

public class RegraAposentadoriaRequest {

	private Long id;

	@NotNull
	private ModalidadeAposentadoriaEnum modalidadeAposentadoria;

	private String modalidadeAposentadoriaNome;

	private TipoAposentadoriaEnum tipoAposentadoria;

	private TipoRegraEnum tipoRegra;

	private String leiBase;

	private Date vigencia;

	private Integer tempoServicoPublico;

	private Integer tempoCargoEfetivo;

	private Integer tempoCarreira;

	@NotNull
	@NotBlank
	private String tempoContribuicaoMulher;

	@NotNull
	@NotBlank
	private String tempoContribuicaoHomem;

	@NotNull
	@NotBlank
	private String idadeHomem;

	@NotNull
	@NotBlank
	private String idadeMulher;

	private Boolean pedagio;

	private Boolean licencaPremio;

	private Boolean abonoPermanencia;

	private String artigo;

	private String proventos;
	
	private String reajuste;

	private String tipoVigencia;

	private Boolean tempoServicoEmPlenoExercicio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ModalidadeAposentadoriaEnum getModalidadeAposentadoria() {
		return modalidadeAposentadoria;
	}

	public void setModalidadeAposentadoria(ModalidadeAposentadoriaEnum modalidadeAposentadoria) {
		this.modalidadeAposentadoria = modalidadeAposentadoria;
	}

	public String getModalidadeAposentadoriaNome() {
		return modalidadeAposentadoriaNome;
	}

	public void setModalidadeAposentadoriaNome(String modalidadeAposentadoriaNome) {
		this.modalidadeAposentadoriaNome = modalidadeAposentadoriaNome;
	}

	public TipoAposentadoriaEnum getTipoAposentadoria() {
		return tipoAposentadoria;
	}

	public void setTipoAposentadoria(TipoAposentadoriaEnum tipoAposentadoria) {
		this.tipoAposentadoria = tipoAposentadoria;
	}

	public TipoRegraEnum getTipoRegra() {
		return tipoRegra;
	}

	public void setTipoRegra(TipoRegraEnum tipoRegra) {
		this.tipoRegra = tipoRegra;
	}

	public String getLeiBase() {
		return leiBase;
	}

	public void setLeiBase(String leiBase) {
		this.leiBase = leiBase;
	}

	public Date getVigencia() {
		return vigencia;
	}

	public void setVigencia(Date vigencia) {
		this.vigencia = vigencia;
	}

	public Integer getTempoServicoPublico() {
		return tempoServicoPublico;
	}

	public void setTempoServicoPublico(Integer tempoServicoPublico) {
		this.tempoServicoPublico = tempoServicoPublico;
	}

	public Integer getTempoCarreira() {
		return tempoCarreira;
	}

	public void setTempoCarreira(Integer tempoCarreira) {
		this.tempoCarreira = tempoCarreira;
	}

	public String getTempoContribuicaoMulher() {
		return tempoContribuicaoMulher;
	}

	public void setTempoContribuicaoMulher(String tempoContribuicaoMulher) {
		this.tempoContribuicaoMulher = tempoContribuicaoMulher;
	}

	public String getTempoContribuicaoHomem() {
		return tempoContribuicaoHomem;
	}

	public void setTempoContribuicaoHomem(String tempoContribuicaoHomem) {
		this.tempoContribuicaoHomem = tempoContribuicaoHomem;
	}

	public Boolean getPedagio() {
		return pedagio;
	}

	public void setPedagio(Boolean pedagio) {
		this.pedagio = pedagio;
	}

	public Boolean getLicencaPremio() {
		return licencaPremio;
	}

	public void setLicencaPremio(Boolean licencaPremio) {
		this.licencaPremio = licencaPremio;
	}

	public Boolean getAbonoPermanencia() {
		return abonoPermanencia;
	}

	public void setAbonoPermanencia(Boolean abonoPermanencia) {
		this.abonoPermanencia = abonoPermanencia;
	}

	public String getArtigo() {
		return artigo;
	}

	public void setArtigo(String artigo) {
		this.artigo = artigo;
	}

	public String getProventos() {
		return proventos;
	}

	public void setProventos(String proventos) {
		this.proventos = proventos;
	}

	public String getTipoVigencia() {
		return tipoVigencia;
	}

	public void setTipoVigencia(String tipoVigencia) {
		this.tipoVigencia = tipoVigencia;
	}

	public String getIdadeHomem() {
		return idadeHomem;
	}

	public void setIdadeHomem(String idadeHomem) {
		this.idadeHomem = idadeHomem;
	}

	public String getIdadeMulher() {
		return idadeMulher;
	}

	public void setIdadeMulher(String idadeMulher) {
		this.idadeMulher = idadeMulher;
	}

	public Boolean getTempoServicoEmPlenoExercicio() {
		return tempoServicoEmPlenoExercicio;
	}

	public void setTempoServicoEmPlenoExercicio(Boolean tempoServicoEmPlenoExercicio) {
		this.tempoServicoEmPlenoExercicio = tempoServicoEmPlenoExercicio;
	}

	public Integer getTempoCargoEfetivo() {
		return tempoCargoEfetivo;
	}

	public void setTempoCargoEfetivo(Integer tempoCargoEfetivo) {
		this.tempoCargoEfetivo = tempoCargoEfetivo;
	}

	public String getReajuste() {
		return reajuste;
	}

	public void setReajuste(String reajuste) {
		this.reajuste = reajuste;
	}

}
