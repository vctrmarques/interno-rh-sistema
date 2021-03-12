package com.rhlinkcon.payload.acidenteTrabalho;

import java.util.Date;

import com.rhlinkcon.model.AcidenteTrabalho;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.tomadorServico.TomadorServicoResponse;

public class AcidenteTrabalhoResponse extends DadoCadastralResponse {

	private Long id;

	private String aviso;

	private FuncionarioResponse funcionario;

	private String setorLocal;

	private String causa;

	private TomadorServicoResponse entidade;

	private TomadorServicoResponse tomadorServico;

	private Date dataHoraAcidente;

	private Integer diasAfastado;

	private Date dataPrevistaVolta;

	private String resultado;

	private Integer numeroCat;

	private boolean documentoEmitido;

	private Date dataEmissaoDocumento;

	private AnexoResponse anexo;

	public AcidenteTrabalhoResponse() {

	}

	public AcidenteTrabalhoResponse(AcidenteTrabalho acidenteTrabalho) {
		this.id = acidenteTrabalho.getId();
		this.aviso = acidenteTrabalho.getAviso();
		this.setorLocal = acidenteTrabalho.getSetorLocal();
		this.causa = acidenteTrabalho.getCausa();
		this.dataHoraAcidente = acidenteTrabalho.getDataHoraAcidente();
		this.diasAfastado = acidenteTrabalho.getDiasAfastado();
		this.dataPrevistaVolta = acidenteTrabalho.getDataPrevistaVolta();
		this.resultado = acidenteTrabalho.getResultado();
		this.numeroCat = acidenteTrabalho.getNumeroCat();
		this.documentoEmitido = acidenteTrabalho.isDocumentoEmitido();
		this.dataEmissaoDocumento = acidenteTrabalho.getDataEmissaoDocumento();

		this.funcionario = new FuncionarioResponse();
		this.funcionario.setId(acidenteTrabalho.getFuncionario().getId());
		this.funcionario.setNome(acidenteTrabalho.getFuncionario().getNome());

		this.entidade = new TomadorServicoResponse();
		this.entidade.setId(acidenteTrabalho.getEntidade().getId());
		this.entidade.setRazaoSocial(acidenteTrabalho.getEntidade().getRazaoSocial());

		this.tomadorServico = new TomadorServicoResponse();
		this.tomadorServico.setId(acidenteTrabalho.getTomadorServico().getId());
		this.tomadorServico.setRazaoSocial(acidenteTrabalho.getTomadorServico().getRazaoSocial());

		if (acidenteTrabalho.getAnexo() != null) {
			this.anexo = new AnexoResponse(acidenteTrabalho.getAnexo());
		}

		setCriadoEm(acidenteTrabalho.getCreatedAt());
		setAlteradoEm(acidenteTrabalho.getUpdatedAt());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public String getSetorLocal() {
		return setorLocal;
	}

	public void setSetorLocal(String setorLocal) {
		this.setorLocal = setorLocal;
	}

	public String getCausa() {
		return causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public TomadorServicoResponse getEntidade() {
		return entidade;
	}

	public void setEntidade(TomadorServicoResponse entidade) {
		this.entidade = entidade;
	}

	public TomadorServicoResponse getTomadorServico() {
		return tomadorServico;
	}

	public void setTomadorServico(TomadorServicoResponse tomadorServico) {
		this.tomadorServico = tomadorServico;
	}

	public Date getDataHoraAcidente() {
		return dataHoraAcidente;
	}

	public void setDataHoraAcidente(Date dataHoraAcidente) {
		this.dataHoraAcidente = dataHoraAcidente;
	}

	public Integer getDiasAfastado() {
		return diasAfastado;
	}

	public void setDiasAfastado(Integer diasAfastado) {
		this.diasAfastado = diasAfastado;
	}

	public Date getDataPrevistaVolta() {
		return dataPrevistaVolta;
	}

	public void setDataPrevistaVolta(Date dataPrevistaVolta) {
		this.dataPrevistaVolta = dataPrevistaVolta;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Integer getNumeroCat() {
		return numeroCat;
	}

	public void setNumeroCat(Integer numeroCat) {
		this.numeroCat = numeroCat;
	}

	public boolean isDocumentoEmitido() {
		return documentoEmitido;
	}

	public void setDocumentoEmitido(boolean documentoEmitido) {
		this.documentoEmitido = documentoEmitido;
	}

	public Date getDataEmissaoDocumento() {
		return dataEmissaoDocumento;
	}

	public void setDataEmissaoDocumento(Date dataEmissaoDocumento) {
		this.dataEmissaoDocumento = dataEmissaoDocumento;
	}

	public AnexoResponse getAnexo() {
		return anexo;
	}

	public void setAnexo(AnexoResponse anexo) {
		this.anexo = anexo;
	}

}
