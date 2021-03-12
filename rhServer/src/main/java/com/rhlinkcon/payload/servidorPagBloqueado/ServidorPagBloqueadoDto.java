package com.rhlinkcon.payload.servidorPagBloqueado;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ServidorPagBloqueadoDto implements Comparable<ServidorPagBloqueadoDto> {
	@NotNull
	@NotEmpty
	private List<Long> fundosSelecionados;
	private List<Long> situacaoFuncionalSelecionadas;
	private Long servidorSelecionadoId;
	private String dataNascimentoSelecionada;
	private Boolean pensaoAlimenticia;
	private Boolean pensionista;
	private String fundo;
	private String situacaoFuncional;
	private String matricula;
	private String nome;
	private String cpf;
	private Double valorLiquido;
	private String mesNascimento;
	private String telefoneContato;
	private LocalDate dataBaseRecadastramento;
	private List<ServidorPagBloqueadoTipoEnum> problemas;

	@Override
	public int compareTo(ServidorPagBloqueadoDto arg0) {
		return this.nome.compareTo(arg0.getNome());
	}
}
