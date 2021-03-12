package com.rhlinkcon.model;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.verba.VerbaRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.MotorCalculoAttribute;

@Entity
//@EntityListeners(AuditListener.class)
//TODO FOI IDENTIFICADA FALHA NA AUDITORIA DE EXCLUSÃO. ISSUE 264 ABERTA
@AuditLabelClass(label = "Verba")
@Table(name = "verba")
public class Verba extends UserDateAudit {

	private static final long serialVersionUID = -3802800237849422503L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	@Size(max = 255)
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@NotBlank
	@Size(max = 255)
	@Column(name = "descricao_verba")
	private String descricaoVerba;

	@NotNull
	@NotBlank
	@Size(max = 1000)
	@Column(name = "descricao_resumida")
	private String descricaoResumida;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_verba")
	private TipoVerba tipoVerba;

	@Enumerated(EnumType.STRING)
	@Column(name = "destinacao_externa")
	private DestinacaoExterna destinacaoExterna;

	@Column(name = "valor_maximo")
	private Double valorMaximo;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_valor")
	private TipoValorEnum tipoValor;

	@Size(max = 2000)
	@Column(name = "comentario")
	private String comentario;

	@Column(name = "conta_debito")
	private Integer contaDebito;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verba_associada_id")
	private Verba verbaAssociada;

	@Enumerated(EnumType.STRING)
	@Column(name = "identificador_verba")
	private IdentificacaoVerbaEnum identificadorVerba;

	@Column(name = "conta_credito")
	private Integer contaCredito;

	@Column(name = "conta_auxiliar_primaria")
	private Integer contaAuxiliarPrimaria;

	@Column(name = "conta_auxiliar_secundaria")
	private Integer contaAuxiliarSecundaria;

	@Column(name = "vigencia_inicial")
	private Instant vigenciaInicial;

	@Column(name = "vigencia_final")
	private Instant vigenciaFinal;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "recorrencia")
	private RecorrenciaEnum recorrencia;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "centro_custo_id")
	private CentroCusto centroCusto;

	@Column(name = "referencia")
	private Double referencia;

	@OneToMany(mappedBy = "verba", orphanRemoval = true)
	private List<ItemFormula> formulas;

	@Enumerated(EnumType.STRING)
	@Column(name = "faixa_aliquota")
	private FaixaEnum faixaAliquota;

	@ManyToMany(mappedBy = "verbas", fetch = FetchType.LAZY)
	private List<CategoriaProfissional> categoriaProfissionais;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "verba_incidencia", joinColumns = @JoinColumn(name = "verba_id"), inverseJoinColumns = @JoinColumn(name = "verba_incidencia_id"))
	private Set<Verba> incidencias = new HashSet<>();

	// TODO ANALIASR
	/** Usado apenas para inserção de verbas manuais que possuem valor definido. */
	@Transient
	private Double valor;

	// TODO ANALIASR
	@Transient
	private boolean verbaManual;

	@Transient
	private Double totalProventosIncidentes;

	@Transient
	private Double totalDescontosIncidentes;

	@MotorCalculoAttribute(name = "verba.totalProventosIncidentes", label = "Total de Proventos Incidentes da Verba")
	public Double getTotalProventosIncidentes() {
		return (Objects.nonNull(this.totalProventosIncidentes) ? this.totalProventosIncidentes : 0.0);
	}

	@MotorCalculoAttribute(name = "verba.totalDescontosIncidentes", label = "Total de Descontos Incidentes da Verba")
	public Double getTotalDescontosIncidentes() {
		return (Objects.nonNull(this.totalDescontosIncidentes) ? this.totalDescontosIncidentes : 0.0);
	}

	public Verba() {

	}

	public Verba(Long id) {
		this.id = id;
	}

	public Verba(Verba verba) {
		this.setCodigo(verba.getCodigo());
		this.setComentario(verba.getComentario());
		this.setDescricaoVerba(verba.getDescricaoVerba());
		this.setDescricaoResumida(verba.getDescricaoResumida());
		this.setValorMaximo(verba.getValorMaximo());
		this.setTipoVerba(verba.getTipoVerba());
		this.setRecorrencia(verba.getRecorrencia());
		this.setTipoValor((verba.getTipoValor()));

		this.setContaAuxiliarPrimaria(verba.getContaAuxiliarPrimaria());
		this.setContaAuxiliarSecundaria(verba.getContaAuxiliarSecundaria());
		this.setContaCredito(verba.getContaCredito());
		this.setContaDebito(verba.getContaDebito());

		if (Objects.nonNull(verba.getDestinacaoExterna()))
			this.setDestinacaoExterna(verba.getDestinacaoExterna());

		this.setVigenciaFinal(verba.getVigenciaFinal());
		this.setVigenciaInicial(verba.getVigenciaInicial());
		this.setCentroCusto(verba.getCentroCusto());

		if (null != verba.getReferencia()) {
			this.setReferencia(verba.getReferencia());
		}

		if (null != verba.getValor()) {
			this.setValor(verba.getValor());
		}

		this.setId(verba.getId());

		if (Objects.nonNull(verba.getIdentificadorVerba())) {
			this.setIdentificadorVerba(verba.getIdentificadorVerba());
			this.setVerbaAssociada(verba.getVerbaAssociada());
		}
	}

	public Verba(VerbaRequest verbaRequest) {
		this.setCodigo(verbaRequest.getCodigo());
		this.setComentario(verbaRequest.getComentario());
		this.setDescricaoVerba(verbaRequest.getDescricaoVerba());
		this.setDescricaoResumida(verbaRequest.getDescricaoResumida());
		this.setValorMaximo(verbaRequest.getValorMaximo());
		this.setTipoVerba(TipoVerba.getEnumByString(verbaRequest.getTipoVerba()));
		this.setRecorrencia(RecorrenciaEnum.getEnumByString(verbaRequest.getRecorrencia()));
		this.setTipoValor(TipoValorEnum.getEnumByString(verbaRequest.getTipoValor()));

		this.setContaAuxiliarPrimaria(verbaRequest.getContaAuxiliarPrimaria());
		this.setContaAuxiliarSecundaria(verbaRequest.getContaAuxiliarSecundaria());
		this.setContaCredito(verbaRequest.getContaCredito());
		this.setContaDebito(verbaRequest.getContaDebito());

		if (Objects.nonNull(verbaRequest.getDestinacaoExterna()))
			this.setDestinacaoExterna(DestinacaoExterna.getEnumByString(verbaRequest.getDestinacaoExterna()));

		this.setVigenciaFinal(verbaRequest.getVigenciaFinal());
		this.setVigenciaInicial(verbaRequest.getVigenciaInicial());
		this.setCentroCusto(new CentroCusto(verbaRequest.getCentroCustoId()));

		if (null != verbaRequest.getReferencia()) {
			this.setReferencia(verbaRequest.getReferencia());
		}

		if (Objects.nonNull(verbaRequest.getFaixaAliquota()))
			this.setFaixaAliquota(FaixaEnum.getEnumByString(verbaRequest.getFaixaAliquota()));
		else
			this.setFaixaAliquota(null);

		if (null != verbaRequest.getValor()) {
			this.setValor(verbaRequest.getValor());
		}

		if (null != verbaRequest.getId()) {
			this.setId(verbaRequest.getId());
		}

		if (Objects.nonNull(verbaRequest.getIdentificadorVerba())) {
			this.setIdentificadorVerba(IdentificacaoVerbaEnum.getEnumByString(verbaRequest.getIdentificadorVerba()));
			this.setVerbaAssociada(new Verba(verbaRequest.getVerbaAssociadaId()));
		} else {
			this.setIdentificadorVerba(null);
			this.setVerbaAssociada(null);
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricaoVerba() {
		return descricaoVerba;
	}

	public void setDescricaoVerba(String descricaoVerba) {
		this.descricaoVerba = descricaoVerba;
	}

	public String getDescricaoResumida() {
		return descricaoResumida;
	}

	public void setDescricaoResumida(String descricaoResumida) {
		this.descricaoResumida = descricaoResumida;
	}

	public TipoVerba getTipoVerba() {
		return tipoVerba;
	}

	public void setTipoVerba(TipoVerba tipoVerba) {
		this.tipoVerba = tipoVerba;
	}

	public DestinacaoExterna getDestinacaoExterna() {
		return destinacaoExterna;
	}

	public void setDestinacaoExterna(DestinacaoExterna destinacaoExterna) {
		this.destinacaoExterna = destinacaoExterna;
	}

	public Double getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(Double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getContaDebito() {
		return contaDebito;
	}

	public void setContaDebito(Integer contaDebito) {
		this.contaDebito = contaDebito;
	}

	public Integer getContaCredito() {
		return contaCredito;
	}

	public void setContaCredito(Integer contaCredito) {
		this.contaCredito = contaCredito;
	}

	public Integer getContaAuxiliarPrimaria() {
		return contaAuxiliarPrimaria;
	}

	public void setContaAuxiliarPrimaria(Integer contaAuxiliarPrimaria) {
		this.contaAuxiliarPrimaria = contaAuxiliarPrimaria;
	}

	public Integer getContaAuxiliarSecundaria() {
		return contaAuxiliarSecundaria;
	}

	public void setContaAuxiliarSecundaria(Integer contaAuxiliarSecundaria) {
		this.contaAuxiliarSecundaria = contaAuxiliarSecundaria;
	}

	public Instant getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Instant vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public Instant getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(Instant vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public RecorrenciaEnum getRecorrencia() {
		return recorrencia;
	}

	public void setRecorrencia(RecorrenciaEnum recorrencia) {
		this.recorrencia = recorrencia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TipoValorEnum getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(TipoValorEnum tipoValor) {
		this.tipoValor = tipoValor;
	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public List<CategoriaProfissional> getCategoriaProfissionais() {
		return categoriaProfissionais;
	}

	public void setCategoriaProfissionais(List<CategoriaProfissional> categoriaProfissionais) {
		this.categoriaProfissionais = categoriaProfissionais;
	}

	@Transient
	public boolean hasFormula() {
		return Objects.nonNull(formulas) && !formulas.isEmpty();
	}

	@Transient
	public boolean hasNotFormula() {
		return !hasFormula();
	}

	public Double getReferencia() {
		return referencia;
	}

	public void setReferencia(Double referencia) {
		this.referencia = referencia;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Set<Verba> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(Set<Verba> incidencias) {
		this.incidencias = incidencias;
	}

	public void setTotalProventosIncidentes(Double totalProventosIncidentes) {
		this.totalProventosIncidentes = totalProventosIncidentes;
	}

	public void setTotalDescontosIncidentes(Double totalDescontosIncidentes) {
		this.totalDescontosIncidentes = totalDescontosIncidentes;
	}

	public boolean isVerbaManual() {
		return verbaManual;
	}

	public void setVerbaManual(boolean verbaManual) {
		this.verbaManual = verbaManual;
	}

	public Verba getVerbaAssociada() {
		return verbaAssociada;
	}

	public void setVerbaAssociada(Verba verbaAssociada) {
		this.verbaAssociada = verbaAssociada;
	}

	public IdentificacaoVerbaEnum getIdentificadorVerba() {
		return identificadorVerba;
	}

	public void setIdentificadorVerba(IdentificacaoVerbaEnum identificadorVerba) {
		this.identificadorVerba = identificadorVerba;
	}

	@Override
	public String getLabel() {
		return this.descricaoVerba + " - " + this.codigo;
	}

	public List<ItemFormula> getFormulas() {
		// Ordenando os itens de fórmula em ordem ascendente do atributo ordem.
		Collections.sort(formulas);
		return formulas;

	}

	public void setFormulas(List<ItemFormula> formulas) {
		this.formulas = formulas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Verba other = (Verba) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public FaixaEnum getFaixaAliquota() {
		return faixaAliquota;
	}

	public void setFaixaAliquota(FaixaEnum faixaAliquota) {
		this.faixaAliquota = faixaAliquota;
	}

}
