package com.rhlinkcon.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Regra de Aposentadoria")
@Table(name = "regra_aposentadoria")
public class RegraAposentadoria extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7877814685131845392L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// MODALIDADE DE APOSENTADORIA
	@Enumerated(EnumType.STRING)
	@Column(name = "modalidade_aposentadoria")
	@NotNull
	private ModalidadeAposentadoriaEnum modalidadeAposentadoria;

	// NOME DA MODALIDADE (CASO A MODALIDADE SEJA DO TIPO 'ESPECIFICA')
	@Column(name = "modalidade_aposentadoria_nome")
	private String modalidadeAposentadoriaNome;

	// TIPO DE APOSENTADORIA
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_aposentadoria")
	private TipoAposentadoriaEnum tipoAposentadoria;

	// TIPO DE REGRA
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_regra")
	private TipoRegraEnum tipoRegra;

	// LEI DE BASE
	@NotBlank
	@NotNull
	@Column(name = "lei_base")
	private String leiBase;

	// DATA DE VIGÊNCIA
	@NotNull
	@Column(name = "vigencia")
	private Date vigencia;

	// TEMPO SERVIÇO PÚBLICO
	@Column(name = "tempo_servico_publico")
	private Integer tempoServicoPublico;

	// TEMPO DE CARGO EFETIVO
	@Column(name = "tempo_cargo_efetivo")
	private Integer tempoCargoEfetivo;

	// TEMPO CARREIRA
	@Column(name = "tempo_carreira")
	private Integer tempoCarreira;

	// TEMPO DE CONTRIBUIÇÃO
	@Column(name = "tempo_contribuicao_mulher")
	private Integer tempoContribuicaoMulher;

	@Column(name = "tempo_contribuicao_homem")
	private Integer tempoContribuicaoHomem;

	@Column(name = "tempo_contribuicao_mulher_formula")
	private String tempoContribuicaoMulherFormula;

	@Column(name = "tempo_contribuicao_homem_formula")
	private String tempoContribuicaoHomemFormula;

	// PEDÁGIO
	@Column(name = "pedagio")
	private Boolean pedagio;

	// IDADE
	@Column(name = "idade_homem")
	private Integer idadeHomem;

	@Column(name = "idade_mulher")
	private Integer idadeMulher;

	@Column(name = "idade_homem_formula")
	private String idadeHomemFormula;

	@Column(name = "idade_mulher_formula")
	private String idadeMulherFormula;

	// LICENÇA PRÊMIO
	@Column(name = "licenca_premio")
	private Boolean licencaPremio;

	// ABONO PERMANÊNCIA
	@Column(name = "abono_permanencia")
	private Boolean abonoPermanencia;

	// ARTIGO
	@Column(name = "artigo")
	private String artigo;

	// PROVENTOS
	@Column(name = "proventos")
	private String proventos;

	// REAJUSTE
	@Column(name = "reajuste")
	private String reajuste;

	// TIPO DE VIGENCIA
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_vigencia")
	private TipoVigenciaEnum tipoVigencia;

	// TEMPO DE SERVIÇO EM PLENO EXERCICIO DA FUNÇÃO
	@Column(name = "tempo_servico_em_pleno_exercicio")
	private Boolean tempoServicoEmPlenoExercicio;

	public String getTempoContribuicaoMulherFormula() {
		return tempoContribuicaoMulherFormula;
	}

	public void setTempoContribuicaoMulherFormula(String tempoContribuicaoMulherFormula) {
		this.tempoContribuicaoMulherFormula = tempoContribuicaoMulherFormula;
	}

	public String getTempoContribuicaoHomemFormula() {
		return tempoContribuicaoHomemFormula;
	}

	public void setTempoContribuicaoHomemFormula(String tempoContribuicaoHomemFormula) {
		this.tempoContribuicaoHomemFormula = tempoContribuicaoHomemFormula;
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
	}

	public String getModalidadeAposentadoriaNome() {
		return modalidadeAposentadoriaNome;
	}

	public String getIdadeHomemFormula() {
		return idadeHomemFormula;
	}

	public void setIdadeHomemFormula(String idadeHomemFormula) {
		this.idadeHomemFormula = idadeHomemFormula;
	}

	public String getIdadeMulherFormula() {
		return idadeMulherFormula;
	}

	public void setIdadeMulherFormula(String idadeMulherFormula) {
		this.idadeMulherFormula = idadeMulherFormula;
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

	public Integer getTempoContribuicaoMulher() {
		return tempoContribuicaoMulher;
	}

	public void setTempoContribuicaoMulher(Integer tempoContribuicaoMulher) {
		this.tempoContribuicaoMulher = tempoContribuicaoMulher;
	}

	public Integer getTempoContribuicaoHomem() {
		return tempoContribuicaoHomem;
	}

	public void setTempoContribuicaoHomem(Integer tempoContribuicaoHomem) {
		this.tempoContribuicaoHomem = tempoContribuicaoHomem;
	}

	public Boolean getPedagio() {
		return pedagio;
	}

	public void setPedagio(Boolean pedagio) {
		this.pedagio = pedagio;
	}

	public Integer getIdadeHomem() {
		return idadeHomem;
	}

	public void setIdadeHomem(Integer idadeHomem) {
		this.idadeHomem = idadeHomem;
	}

	public Integer getIdadeMulher() {
		return idadeMulher;
	}

	public void setIdadeMulher(Integer idadeMulher) {
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

	public String getProventos() {
		return proventos;
	}

	public void setProventos(String proventos) {
		this.proventos = proventos;
	}

	public TipoVigenciaEnum getTipoVigencia() {
		return tipoVigencia;
	}

	public void setTipoVigencia(TipoVigenciaEnum tipoVigencia) {
		this.tipoVigencia = tipoVigencia;
	}

	public Boolean getTempoServicoEmPlenoExercicio() {
		return tempoServicoEmPlenoExercicio;
	}

	public void setTempoServicoEmPlenoExercicio(Boolean tempoServicoEmPlenoExercicio) {
		this.tempoServicoEmPlenoExercicio = tempoServicoEmPlenoExercicio;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
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
