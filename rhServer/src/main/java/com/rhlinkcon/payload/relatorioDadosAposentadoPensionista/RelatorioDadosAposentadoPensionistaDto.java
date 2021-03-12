package com.rhlinkcon.payload.relatorioDadosAposentadoPensionista;

import java.util.List;

import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Pensionista;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioDadosAposentadoPensionistaDto {
	List<Funcionario> aposentados;
	List<Pensionista> pensionistas;
}
