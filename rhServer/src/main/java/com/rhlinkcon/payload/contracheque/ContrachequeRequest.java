package com.rhlinkcon.payload.contracheque;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContrachequeRequest {

	@NotNull
	private Long folhaPagamentoId;

	@NotEmpty
	private List<Long> funcionariosId;

}
