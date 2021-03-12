package com.rhlinkcon.payload.pensaoAlimenticia;

import com.rhlinkcon.model.ResponsavelLegal;

public class PensaoAlimenticiaResponsavelDto {

	private Long id;

	private String nome;

	private String codigo;

	private String tipo;

//	private DadoBancarioDto dadoBancario;

	public PensaoAlimenticiaResponsavelDto() {

	}

	public PensaoAlimenticiaResponsavelDto(ResponsavelLegal responsavelLegal) {
		this.id = responsavelLegal.getId();
		this.nome = responsavelLegal.getNome();
		this.codigo = responsavelLegal.getCodigoResponsavel();
		this.tipo = responsavelLegal.getTipoResponsavel().getLabel();
		// TODO Falha de implementação e documentação referente aos dados bancários do
		// responsável legal
//		this.dadoBancario = new DadoBancarioDto(responsavelLegal);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

//	public DadoBancarioDto getDadoBancario() {
//		return dadoBancario;
//	}
//
//	public void setDadoBancario(DadoBancarioDto dadoBancario) {
//		this.dadoBancario = dadoBancario;
//	}

}
