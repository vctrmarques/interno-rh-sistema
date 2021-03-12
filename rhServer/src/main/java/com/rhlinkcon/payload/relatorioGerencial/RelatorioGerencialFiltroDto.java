package com.rhlinkcon.payload.relatorioGerencial;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RelatorioGerencialFiltroDto {

	// Atributos auxiliares do relatório
	private String anoCompetencia;
	private String mesCompetencia;
	private String tipoProcessamento;

	// Atributos padrão do filtro, tanto paginação quanto relatório
	@NotNull
	@NotEmpty
	private String aba;
	
	@NotNull
	@NotEmpty
	private String abaLabel;
	
	@NotNull
	private Long folhaCompetenciaId;

	@NotNull
	private Long tipoProcessamentoId;

	private Long filialId;

	private String cargoEfetivo;

	private String cargoFuncao;

	private Long funcionarioId;

	private String lotacao;
	
	private String vinculo;

	// Atributo para mês de comparação
	private Long folhaCompetenciaComparacaoId;

}
