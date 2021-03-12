package com.rhlinkcon.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "CRM CREA")
@Table(name = "crm_crea")
public class CrmCrea extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4440215695661565547L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	@Column(name = "nome_conveniado", unique = true)
	private String nomeConveniado;

	@NotNull
	@NotEmpty
	@Column(name = "numero_crm_crea", unique = true)
	private String numeroCrmCrea;

	@Column(name = "coordenador_pcmso")
	private boolean coordenadorPcmso;

	@Column(name = "responsavel_ltcat")
	private boolean responsavelLtcat;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private CrmCreaEnum tipo;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "crm_crea_convenio", joinColumns = @JoinColumn(name = "crm_crea_id"), inverseJoinColumns = @JoinColumn(name = "convenio_id"))
	private Set<Convenio> convenios = new HashSet<>();

	public CrmCrea() {

	}

	public CrmCrea(Long id) {
		this.id = id;
	}

	public CrmCreaEnum getTipo() {
		return tipo;
	}

	public void setTipo(CrmCreaEnum tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeConveniado() {
		return nomeConveniado;
	}

	public void setNomeConveniado(String nomeConveniado) {
		this.nomeConveniado = nomeConveniado;
	}

	public String getNumeroCrmCrea() {
		return numeroCrmCrea;
	}

	public void setNumeroCrmCrea(String numeroCrmCrea) {
		this.numeroCrmCrea = numeroCrmCrea;
	}

	public boolean isCoordenadorPcmso() {
		return coordenadorPcmso;
	}

	public void setCoordenadorPcmso(boolean coordenadorPcmso) {
		this.coordenadorPcmso = coordenadorPcmso;
	}

	public boolean isResponsavelLtcat() {
		return responsavelLtcat;
	}

	public void setResponsavelLtcat(boolean responsavelLtcat) {
		this.responsavelLtcat = responsavelLtcat;
	}

	public Set<Convenio> getConvenios() {
		return convenios;
	}

	public void setConvenios(Set<Convenio> convenios) {
		this.convenios = convenios;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
