package com.rhlinkcon.model;

import java.util.List;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dirf")
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "DIRF")
@Getter
@Setter
public class Dirf extends UserDateAudit {

	private static final long serialVersionUID = -7331832941881242638L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "ano_base")
	private Integer anoBase;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_declaracao")
	private DirfTipoDeclaracaoEnum tipoDeclaracao;
	
	@Column(name = "numero_declaracao")
	private Integer numeroDeclaracao;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_filial_id")
	private EmpresaFilial filial;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dirf_responsavel_receita_id")
	private DirfResponsavelReceita responsavelReceita;
	
	@NotNull
	@Column(name = "natureza_declarante")
	private Integer naturezaDeclarante;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private DirfSituacao situacao;
	
	@ManyToMany
	@JoinTable(name = "dirf_anexo", joinColumns = {
		@JoinColumn(name = "dirf_id", referencedColumnName = "id") }, inverseJoinColumns = {
				@JoinColumn(name = "anexo_id", referencedColumnName = "id") })
	private List<Anexo> anexos;

	@Override
	public String getLabel() {
		return null;
	}
	
	public Integer getAnoExercicio() {
		return this.anoBase + 1;
	}
}
