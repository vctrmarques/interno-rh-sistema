package com.rhlinkcon.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Folha de Competência")
@Table(name = "folha_competencia")
public class FolhaCompetencia extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3531967022937446960L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "mes_competencia")
	private Integer mesCompetencia;

	@Column(name = "ano_competencia")
	private Integer anoCompetencia;

	@NotNull
	@Column(name = "inicio_competencia")
	private Date inicioCompetencia;

	@Column(name = "fim_competencia")
	private Date fimCompetencia;

	@Column(name = "programacao_fechamento")
	private LocalDate programacaoFechamento;

	@Column(name = "checar_recad")
	private boolean checarRecad;

	@Column(name = "checar_recad_amp_legal")
	private String checarRecadAmpLegal;

	public FolhaCompetencia(Long id) {
		this.id = id;
	}

	public FolhaCompetencia() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMesCompetencia() {
		return mesCompetencia;
	}

	public void setMesCompetencia(Integer mesCompetencia) {
		this.mesCompetencia = mesCompetencia;
	}

	public Integer getAnoCompetencia() {
		return anoCompetencia;
	}

	public void setAnoCompetencia(Integer anoCompetencia) {
		this.anoCompetencia = anoCompetencia;
	}

	public Date getInicioCompetencia() {
		return inicioCompetencia;
	}

	public void setInicioCompetencia(Date inicioCompetencia) {
		this.inicioCompetencia = inicioCompetencia;
	}

	public Date getFimCompetencia() {
		return fimCompetencia;
	}

	public void setFimCompetencia(Date fimCompetencia) {
		this.fimCompetencia = fimCompetencia;
	}

	public String getMesAnoCompetencia() {
		return (this.getMesCompetencia() < 10 ? "0" + this.getMesCompetencia().toString()
				: this.getMesCompetencia().toString()) + "/" + this.getAnoCompetencia().toString();
	}

	public LocalDate getProgramacaoFechamento() {
		return programacaoFechamento;
	}

	public void setProgramacaoFechamento(LocalDate programacaoFechamento) {
		this.programacaoFechamento = programacaoFechamento;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isChecarRecad() {
		return checarRecad;
	}

	public void setChecarRecad(boolean checarRecad) {
		this.checarRecad = checarRecad;
	}

	public String getChecarRecadAmpLegal() {
		return checarRecadAmpLegal;
	}

	public void setChecarRecadAmpLegal(String checarRecadAmpLegal) {
		this.checarRecadAmpLegal = checarRecadAmpLegal;
	}
}
