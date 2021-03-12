package com.rhlinkcon.payload.auditoria;

import java.lang.annotation.Annotation;

import com.google.gson.Gson;
import com.rhlinkcon.audit.AuditoriaDescricaoDto;
import com.rhlinkcon.model.Auditoria;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.util.AuditLabelClass;

public class AuditoriaResponse extends DadoCadastralResponse {

	private Long id;

	private String descricao;

	private AuditoriaDescricaoDto auditoriaDescricaoDto;

	private AuditLabelClassDto entidade;

	private String acao;

	private Long idObjectAuditado;

	public AuditoriaResponse(Auditoria auditoria) {
		this.id = auditoria.getId();
		this.descricao = auditoria.getDescricao();
		try {
			Gson gson = new Gson();
			this.auditoriaDescricaoDto = gson.fromJson(auditoria.getDescricao(), AuditoriaDescricaoDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Class<?> aClass = Class.forName(auditoria.getEntidade());
			for (Annotation annotation : aClass.getDeclaredAnnotations()) {
				if (annotation instanceof AuditLabelClass) {
					AuditLabelClass auditLabelClass = (AuditLabelClass) annotation;
					this.entidade = new AuditLabelClassDto(aClass.getName(), auditLabelClass.label());
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		this.acao = auditoria.getAcao().getLabel();
		this.idObjectAuditado = auditoria.getIdObjectAuditado();
		setCriadoEm(auditoria.getCreatedAt());
	}

	public AuditoriaResponse() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public AuditLabelClassDto getEntidade() {
		return entidade;
	}

	public void setEntidade(AuditLabelClassDto entidade) {
		this.entidade = entidade;
	}

	public Long getIdObjectAuditado() {
		return idObjectAuditado;
	}

	public void setIdObjectAuditado(Long idObjectAuditado) {
		this.idObjectAuditado = idObjectAuditado;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public AuditoriaDescricaoDto getAuditoriaDescricaoDto() {
		return auditoriaDescricaoDto;
	}

	public void setAuditoriaDescricaoDto(AuditoriaDescricaoDto auditoriaDescricaoDto) {
		this.auditoriaDescricaoDto = auditoriaDescricaoDto;
	}

}
