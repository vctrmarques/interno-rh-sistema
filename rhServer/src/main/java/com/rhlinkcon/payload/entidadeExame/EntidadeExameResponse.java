package com.rhlinkcon.payload.entidadeExame;

import java.util.ArrayList;
import java.util.List;

import com.rhlinkcon.model.EntidadeExame;
import com.rhlinkcon.model.Exame;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.exame.ExameResponse;
import com.rhlinkcon.payload.generico.EnderecoDto;
import com.rhlinkcon.payload.tomadorServico.TomadorServicoResponse;

public class EntidadeExameResponse extends DadoCadastralResponse {

	private Long id;

	private TomadorServicoResponse tomadorServico;

	private String tipo;

	private String tipoLabel;

	private List<ExameResponse> exames;

	private EnderecoDto endereco;

	private String telefone;

	public EntidadeExameResponse(EntidadeExame entidadeExame) {
		this.id = entidadeExame.getId();
		this.tomadorServico = new TomadorServicoResponse(entidadeExame.getTomadorServico());
		this.tipoLabel = entidadeExame.getTipo().getLabel();
		this.tipo = entidadeExame.getTipo().toString();
		this.exames = new ArrayList<>();
		for (Exame exame : entidadeExame.getExames()) {
			this.exames.add(new ExameResponse(exame));
		}

		this.setTelefone(entidadeExame.getTelefone());

		this.endereco = new EnderecoDto();
		this.endereco.setLogradouro(entidadeExame.getLogradouro());
		this.endereco.setNumero(entidadeExame.getNumero());
		this.endereco.setComplemento(entidadeExame.getComplemento());
		this.endereco.setCep(entidadeExame.getCep());
		this.endereco.setBairro(entidadeExame.getBairro());
		if (entidadeExame.getUf() != null)
			this.endereco.setUf(new DadoBasicoDto(entidadeExame.getUf()));
		if (entidadeExame.getMunicipio() != null)
			this.endereco.setMunicipio(new DadoBasicoDto(entidadeExame.getMunicipio()));
		this.setCriadoEm(entidadeExame.getCreatedAt());
		this.setAlteradoEm(entidadeExame.getUpdatedAt());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TomadorServicoResponse getTomadorServico() {
		return tomadorServico;
	}

	public void setTomadorServico(TomadorServicoResponse tomadorServico) {
		this.tomadorServico = tomadorServico;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDto endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<ExameResponse> getExames() {
		return exames;
	}

	public void setExames(List<ExameResponse> exames) {
		this.exames = exames;
	}

	public String getTipoLabel() {
		return tipoLabel;
	}

	public void setTipoLabel(String tipoLabel) {
		this.tipoLabel = tipoLabel;
	}

}
