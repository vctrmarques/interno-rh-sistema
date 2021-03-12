package com.rhlinkcon.payload.tomadorServico;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.TomadorServico;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.codigoPagamentoGps.CodigoPagamentoGpsResponse;
import com.rhlinkcon.payload.codigoRecolhimento.CodigoRecolhimentoResponse;
import com.rhlinkcon.payload.generico.EnderecoDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TomadorServicoResponse extends DadoCadastralResponse {

	private Long id;

	private EnderecoDto endereco;

	private CodigoRecolhimentoResponse codigoRecolhimento;

	private CodigoPagamentoGpsResponse codigoPagamentoGps;

	private String cnpj;

	private String tipoInscricao;

	private String razaoSocial;

	private Long compensacaoId;

	public TomadorServicoResponse() {

	}

	public TomadorServicoResponse(TomadorServico tomadorServico) {
		setTomadorServico(tomadorServico);
	}

	public TomadorServicoResponse(TomadorServico tomadorServico, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setTomadorServico(tomadorServico);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setTomadorServico(TomadorServico tomadorServico) {
		this.setId(tomadorServico.getId());
		this.setEndereco(new EnderecoDto(tomadorServico.getEndereco()));
		this.setCnpj(tomadorServico.getCnpj());
		this.setTipoInscricao(tomadorServico.getTipoInscricao().getLabel());
		this.setRazaoSocial(tomadorServico.getRazaoSocial());

		if (tomadorServico.getCodigoRecolhimento() != null)
			this.setCodigoRecolhimento(new CodigoRecolhimentoResponse(tomadorServico.getCodigoRecolhimento()));

		if (tomadorServico.getCodigoPagamentoGps() != null)
			this.setCodigoPagamentoGps(new CodigoPagamentoGpsResponse(tomadorServico.getCodigoPagamentoGps()));

		if (tomadorServico.getCompensacao() != null)
			this.setCompensacaoId(tomadorServico.getCompensacao().getId());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDto endereco) {
		this.endereco = endereco;
	}

	public CodigoRecolhimentoResponse getCodigoRecolhimento() {
		return codigoRecolhimento;
	}

	public void setCodigoRecolhimento(CodigoRecolhimentoResponse codigoRecolhimento) {
		this.codigoRecolhimento = codigoRecolhimento;
	}

	public CodigoPagamentoGpsResponse getCodigoPagamentoGps() {
		return codigoPagamentoGps;
	}

	public void setCodigoPagamentoGps(CodigoPagamentoGpsResponse codigoPagamentoGps) {
		this.codigoPagamentoGps = codigoPagamentoGps;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTipoInscricao() {
		return tipoInscricao;
	}

	public void setTipoInscricao(String tipoInscricao) {
		this.tipoInscricao = tipoInscricao;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public Long getCompensacaoId() {
		return compensacaoId;
	}

	public void setCompensacaoId(Long compensacaoId) {
		this.compensacaoId = compensacaoId;
	}

}
