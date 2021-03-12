package com.rhlinkcon.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Notificação")
@Table(name = "NOTIFICACAO")
public class Notificacao extends UserDateAudit {

	private static final long serialVersionUID = -6789275277404894698L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_notificacao", length = 100)
	private TipoNotificacao tipoNotificacao;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_destinatario")
	private Usuario destinatario;

	@Column(name = "visualizada")
	private Boolean visualizada;

	@Column(name = "id_entidade")
	private Long idEntidade;

	@Column(name = "envia_email")
	private Boolean enviaEmail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoNotificacao getTipoNotificacao() {
		return tipoNotificacao;
	}

	public void setTipoNotificacao(TipoNotificacao tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}

	public Boolean getVisualizada() {
		return visualizada;
	}

	public void setVisualizada(Boolean visualizada) {
		this.visualizada = visualizada;
	}

	public Long getIdEntidade() {
		return idEntidade;
	}

	public void setIdEntidade(Long idEntidade) {
		this.idEntidade = idEntidade;
	}

	public Boolean getEnviaEmail() {
		return enviaEmail;
	}

	public void setEnviaEmail(Boolean enviaEmail) {
		this.enviaEmail = enviaEmail;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
