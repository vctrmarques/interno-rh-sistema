package com.rhlinkcon.payload.dirf;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.model.DirfResponsavelReceita;
import com.rhlinkcon.payload.DadoCadastralResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirfResponsavelReceitaDto extends DadoCadastralResponse {


	private Long id;
	
	@NotNull
	@Size(min = 11, max = 11)
	private String cpf;
	
	private String nome;
	
	@Size(min = 2, max = 3)
	private String ddd;
	
	@Size(min = 9, max = 9)
	private String numeroTelefone;
	
	private String ramal;
	
	private String email;
	
	public DirfResponsavelReceitaDto() {}

	public DirfResponsavelReceitaDto(DirfResponsavelReceita obj) {
		this.id = obj.getId();
		this.cpf = obj.getCpf();
		this.nome = obj.getNome();
		this.ddd = obj.getDdd();
		this.numeroTelefone = obj.getNumeroTelefone();
		this.ramal = obj.getRamal();
		this.email = obj.getEmail();
	}
}
