package com.rhlinkcon.teste;

import java.time.Instant;

import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.RecorrenciaEnum;
import com.rhlinkcon.model.TipoValorEnum;
import com.rhlinkcon.model.TipoVerba;
import com.rhlinkcon.model.Verba;

public abstract class AbstractTest {

	protected Verba getSingleVerbaWithoutInsert() {
		Verba verba = new Verba();
		CentroCusto centro = getSingleCentroCustoWithoutInsert();

		verba.setId(Long.valueOf("4"));
		verba.setCodigo("001");
		verba.setDescricaoVerba("Verba 1");
		verba.setDescricaoResumida("V1");
		verba.setTipoVerba(TipoVerba.VANTAGEM);
		verba.setTipoValor(TipoValorEnum.MOEDA);
		verba.setRecorrencia(RecorrenciaEnum.FIXA);
		verba.setCreatedAt(Instant.now());
		verba.setUpdatedAt(Instant.now());
		verba.setCreatedBy(Long.valueOf("1"));
		verba.setComentario("Teste");
		verba.setContaCredito(121);
		verba.setContaDebito(212);
		verba.setCentroCusto(centro);

		return verba;
	}

	protected CentroCusto getSingleCentroCustoWithoutInsert() {
		CentroCusto centro = new CentroCusto();
		centro.setId(Long.valueOf("4"));
		centro.setCodigo("001");
		centro.setDescricao("Centro Custo Padrão");
		centro.setCreatedAt(Instant.now());
		centro.setUpdatedAt(Instant.now());
		centro.setCreatedBy(Long.valueOf("1"));

		return centro;
	}

	// TODO REFATORAR
//	protected VerbaFormula getSingleVerbaFormulaWihoutInsert() {
//		VerbaFormula verbaFormula = new VerbaFormula();
//		
//		verbaFormula.setId(Long.valueOf("4"));
//		verbaFormula.setFormula("TESTE");
//		verbaFormula.setDescricao("TESTE");
//		verbaFormula.setCreatedAt(Instant.now());
//		verbaFormula.setUpdatedAt(Instant.now());
//		verbaFormula.setCreatedBy(Long.valueOf("1"));
//		
//		return verbaFormula;
//	}

}
