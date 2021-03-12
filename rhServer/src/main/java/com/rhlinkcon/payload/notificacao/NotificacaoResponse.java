package com.rhlinkcon.payload.notificacao;

import com.rhlinkcon.model.Notificacao;
import com.rhlinkcon.model.TipoNotificacao;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class NotificacaoResponse extends DadoCadastralResponse {

	private Long id;

	private TipoNotificacao tipoNotificacao;

	private String descricao;

	private Usuario destinatario;

	private Boolean visualizada;

	private Long idEntidade;

	private Boolean enviaEmail;

	public NotificacaoResponse(Notificacao notificacao) {
		this.id = notificacao.getId();
		this.tipoNotificacao = notificacao.getTipoNotificacao();
		this.descricao = notificacao.getDescricao();
		this.visualizada = notificacao.getVisualizada();
		this.idEntidade = notificacao.getIdEntidade();
		this.enviaEmail = notificacao.getEnviaEmail();
		setCriadoEm(notificacao.getCreatedAt());
	}

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

}
