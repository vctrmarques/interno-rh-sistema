package com.rhlinkcon.payload.dirf;

import java.util.Objects;

import com.rhlinkcon.util.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirfBeneficiarioDto {

	private Long id;

	private String nome;

	private String cpf;

	public DirfBeneficiarioDto() {}

	public DirfBeneficiarioDto(ProjecaoDirfBeneficiario e) {
		if (Objects.nonNull(e.getPeId())) {
			this.id = e.getFpId();
			this.nome = e.getPeNome();
			this.cpf = Utils.formatarCpf(e.getPeCpf());
		} else {
			this.id = e.getId();
			this.nome = e.getNome();
			this.cpf = Utils.formatarCpf(e.getCpf());

		}
	}

}
