package com.rhlinkcon.payload.falta;

import java.time.Instant;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;

import com.rhlinkcon.model.Falta;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.motivoAfastamento.MotivoAfastamentoResponse;
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalResponse;

public class FaltaResponse extends DadoCadastralResponse {
	private Long id;

	private FuncionarioResponse funcionario;

	private Instant dataInicio;

	private Instant dataRetorno;

	private MotivoAfastamentoResponse motivoAfastamento;

	private SituacaoFuncionalResponse afastamento;

	private String diagnosticoMedico;

	private String observacaoDocumento;

	private AnexoResponse anexo;

	public FaltaResponse(Falta falta) {
		this.id = falta.getId();
		this.funcionario = new FuncionarioResponse(falta.getFuncionario());
		this.dataInicio = falta.getDataInicio();
		this.dataRetorno = falta.getDataRetorno();
		this.motivoAfastamento = new MotivoAfastamentoResponse(falta.getMotivoAfastamento());
		this.afastamento = new SituacaoFuncionalResponse(falta.getAfastamento());
		this.diagnosticoMedico = falta.getDiagnosticoMedico();
		if (!Strings.isEmpty(falta.getObservacaoDocumento()))
			this.observacaoDocumento = falta.getObservacaoDocumento();
		if (Objects.nonNull(falta.getAnexo()))
			this.anexo = new AnexoResponse(falta.getAnexo());
		setCriadoEm(falta.getCreatedAt());
		setAlteradoEm(falta.getUpdatedAt());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public Instant getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Instant dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Instant getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Instant dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public MotivoAfastamentoResponse getMotivoAfastamento() {
		return motivoAfastamento;
	}

	public void setMotivoAfastamento(MotivoAfastamentoResponse motivoAfastamento) {
		this.motivoAfastamento = motivoAfastamento;
	}

	public SituacaoFuncionalResponse getAfastamento() {
		return afastamento;
	}

	public void setAfastamento(SituacaoFuncionalResponse afastamento) {
		this.afastamento = afastamento;
	}

	public String getDiagnosticoMedico() {
		return diagnosticoMedico;
	}

	public void setDiagnosticoMedico(String diagnosticoMedico) {
		this.diagnosticoMedico = diagnosticoMedico;
	}

	public String getObservacaoDocumento() {
		return observacaoDocumento;
	}

	public void setObservacaoDocumento(String observacaoDocumento) {
		this.observacaoDocumento = observacaoDocumento;
	}

	public AnexoResponse getAnexo() {
		return anexo;
	}

	public void setAnexo(AnexoResponse anexo) {
		this.anexo = anexo;
	}

}
