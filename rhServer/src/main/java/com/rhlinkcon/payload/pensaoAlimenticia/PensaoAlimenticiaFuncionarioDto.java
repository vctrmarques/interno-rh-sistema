package com.rhlinkcon.payload.pensaoAlimenticia;

import com.rhlinkcon.model.Funcionario;

public class PensaoAlimenticiaFuncionarioDto {
	private Long id;

	private String nome;

	private Integer matricula;

	private String filial;

	public PensaoAlimenticiaFuncionarioDto() {

	}

	public PensaoAlimenticiaFuncionarioDto(Funcionario funcionario) {
		this.id = funcionario.getId();
		this.nome = funcionario.getNome();
		this.matricula = funcionario.getMatricula();
		this.filial = funcionario.getFilial().getNomeFilial();
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

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getFilial() {
		return filial;
	}

	public void setFilial(String filial) {
		this.filial = filial;
	}

}
