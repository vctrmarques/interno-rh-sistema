package com.rhlinkcon.payload.recadastramento;

import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.Recadastramento;

public interface ProjectionRecadastramento {
	Recadastramento getRecadastramento();
	Funcionario getFuncionario();
	Pensionista getPensao();
}
