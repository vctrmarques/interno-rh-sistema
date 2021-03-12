package com.rhlinkcon.payload.regraAposentadoria;

import java.util.Date;

import com.rhlinkcon.model.ModalidadeAposentadoriaEnum;
import com.rhlinkcon.model.RegraAposentadoria;
import com.rhlinkcon.model.TipoAposentadoriaEnum;
import com.rhlinkcon.model.TipoRegraEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.util.Projecao;

public class RegraAposentadoriaResponse extends DadoCadastralResponse {

	private Long id;

	private ModalidadeAposentadoriaEnum modalidadeAposentadoria;

	private String modalidadeAposentadoriaLabelStr;

	private String modalidadeAposentadoriaNome;

	private TipoAposentadoriaEnum tipoAposentadoria;

	private String tipoAposentadoriaLabelStr;

	private TipoRegraEnum tipoRegra;

	private String tipoRegraLabelStr;

	private String leiBase;

	private Date vigencia;

	private Integer tempoServicoPublico;

	private Integer tempoCargoEfetivo;

	private Integer tempoCarreira;

	private String tempoContribuicaoMulher;

	private String tempoContribuicaoHomem;

	private Boolean pedagio;

	private String idadeHomem;

	private String idadeMulher;

	private Boolean licencaPremio;

	private Boolean abonoPermanencia;

	private String artigo;

	private boolean ativo;

	private String proventos;

	private String reajuste;
	
	private String tipoVigencia;

	private Boolean tempoServicoEmPlenoExercicio;

	public RegraAposentadoriaResponse() {

	}

	public RegraAposentadoriaResponse(RegraAposentadoria regraAposentadoria, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			setId(regraAposentadoria.getId());
			setModalidadeAposentadoria(regraAposentadoria.getModalidadeAposentadoria());
			setModalidadeAposentadoriaNome(regraAposentadoria.getModalidadeAposentadoriaNome());

			setTipoAposentadoria(regraAposentadoria.getTipoAposentadoria());
			setTipoRegra(regraAposentadoria.getTipoRegra());
			setLeiBase(regraAposentadoria.getLeiBase());
			setVigencia(regraAposentadoria.getVigencia());
			setTempoServicoPublico(regraAposentadoria.getTempoServicoPublico());

			setTempoCargoEfetivo(regraAposentadoria.getTempoCargoEfetivo());

			setTempoCarreira(regraAposentadoria.getTempoCarreira());

			setPedagio(regraAposentadoria.getPedagio());
			setTempoServicoEmPlenoExercicio(regraAposentadoria.getTempoServicoEmPlenoExercicio());

			if (regraAposentadoria.getIdadeHomem() != null) {
				setIdadeHomem(regraAposentadoria.getIdadeHomem().toString());
			} else {
				setIdadeHomem(regraAposentadoria.getIdadeHomemFormula().toString());
			}

			if (regraAposentadoria.getIdadeMulher() != null) {
				setIdadeMulher(regraAposentadoria.getIdadeMulher().toString());
			} else {
				setIdadeMulher(regraAposentadoria.getIdadeMulherFormula().toString());
			}

			if (regraAposentadoria.getTempoContribuicaoHomem() != null) {
				setTempoContribuicaoHomem(regraAposentadoria.getTempoContribuicaoHomem().toString());
			} else {
				setTempoContribuicaoHomem(regraAposentadoria.getTempoContribuicaoHomemFormula().toString());
			}

			if (regraAposentadoria.getTempoContribuicaoMulher() != null) {
				setTempoContribuicaoMulher(regraAposentadoria.getTempoContribuicaoMulher().toString());
			} else {
				setTempoContribuicaoMulher(regraAposentadoria.getTempoContribuicaoMulherFormula().toString());
			}

			setLicencaPremio(regraAposentadoria.getLicencaPremio());
			setAbonoPermanencia(regraAposentadoria.getAbonoPermanencia());
			setArtigo(regraAposentadoria.getArtigo());

			setCriadoEm(regraAposentadoria.getCreatedAt());
			setAlteradoEm(regraAposentadoria.getUpdatedAt());

			setProventos(regraAposentadoria.getProventos());
			setReajuste(regraAposentadoria.getReajuste());

			if (regraAposentadoria.getTipoVigencia() != null) {
				setTipoVigencia(regraAposentadoria.getTipoVigencia().toString());
			}

			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {

				if (projecao.equals(Projecao.COMPLETA)) {

				}
			}
		}
	}

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
		this.modalidadeAposentadoriaLabelStr = modalidadeAposentadoria.getLabel();
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
		this.tipoAposentadoriaLabelStr = tipoAposentadoria.getTipoAposentadoria();
	}

	public TipoRegraEnum getTipoRegra() {
		return tipoRegra;
	}

	public void setTipoRegra(TipoRegraEnum tipoRegra) {
		this.tipoRegra = tipoRegra;
		if (tipoRegra != null) {
			this.tipoRegraLabelStr = tipoRegra.getTipoRegra();
		}
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

	public String getModalidadeAposentadoriaLabelStr() {
		return modalidadeAposentadoriaLabelStr;
	}

	public void setModalidadeAposentadoriaLabelStr(String modalidadeAposentadoriaLabelStr) {
		this.modalidadeAposentadoriaLabelStr = modalidadeAposentadoriaLabelStr;
	}

	public String getTipoAposentadoriaLabelStr() {
		return tipoAposentadoriaLabelStr;
	}

	public void setTipoAposentadoriaLabelStr(String tipoAposentadoriaLabelStr) {
		this.tipoAposentadoriaLabelStr = tipoAposentadoriaLabelStr;
	}

	public String getTipoRegraLabelStr() {
		return tipoRegraLabelStr;
	}

	public void setTipoRegraLabelStr(String tipoRegraLabelStr) {
		this.tipoRegraLabelStr = tipoRegraLabelStr;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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
