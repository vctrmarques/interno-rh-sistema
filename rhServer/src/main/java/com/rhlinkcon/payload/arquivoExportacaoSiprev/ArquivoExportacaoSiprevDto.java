package com.rhlinkcon.payload.arquivoExportacaoSiprev;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ArquivoExportacaoSiprevDto {

	@NotNull
	private Long situacaoFuncionalId;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String tipoExportacaoSelecionada;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String nomeArquivo;

	@NotNull
	@NotBlank
	@Size(max = 1)
	private String operacaoSelecionada;

}
