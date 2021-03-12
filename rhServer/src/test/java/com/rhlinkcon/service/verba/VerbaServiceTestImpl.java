package com.rhlinkcon.service.verba;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rhlinkcon.model.FuncionarioVerba;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.verba.VerbaRequest;
import com.rhlinkcon.payload.verba.VerbaResponse;
import com.rhlinkcon.service.VerbaService;
import com.rhlinkcon.teste.AbstractTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VerbaServiceTestImpl extends AbstractTest {

	@Autowired
	private VerbaService verbaService;

	// TODO Refatorar
//	public void shouldCreateANewVerbaWithVerbaFormula() {
//		try {
//			Verba verba = getSingleVerbaWithoutInsert();
//			List<VerbaFormula> formulas = new ArrayList<VerbaFormula>();
//			formulas.add(getSingleVerbaFormulaWihoutInsert());
//			
//			verba.setFormulas(formulas);
//			verbaService.createVerba(toRequest(verba));
//			assertNotNull(verba.getId());
//		} catch (Exception e) {
//			Assert.assertTrue(Objects.isNull(e.getMessage()));
//		}
//	}

	@Test
	public void whenFindAll() {
		try {
			List<VerbaResponse> verbas = verbaService.getAllVerbas();
			assertNotNull(verbas);
		} catch (Exception e) {
			Assert.assertTrue(Objects.isNull(e.getMessage()));
		}
	}
	public VerbaRequest toRequest(Verba verba) {
		VerbaRequest vr = new VerbaRequest();
		vr.setId(verba.getId());
		vr.setCodigo(verba.getCodigo());
		vr.setDescricaoVerba(verba.getDescricaoVerba());
		vr.setDescricaoResumida(verba.getDescricaoResumida());
		vr.setTipoValor(verba.getTipoValor().getLabel());
		vr.setTipoVerba(verba.getTipoVerba().getLabel());
		vr.setRecorrencia(verba.getRecorrencia().getLabel());
		vr.setCentroCustoId(new Long(1));
		vr.setComentario(verba.getComentario());
		vr.setContaDebito(verba.getContaDebito());
		vr.setContaCredito(verba.getContaCredito());
		return vr;
	}

}
