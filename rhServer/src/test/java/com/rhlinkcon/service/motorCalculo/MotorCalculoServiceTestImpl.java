package com.rhlinkcon.service.motorCalculo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rhlinkcon.model.Aliquota;
import com.rhlinkcon.model.FaixaEnum;
import com.rhlinkcon.service.AliquotaService;
import com.rhlinkcon.teste.AbstractTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MotorCalculoServiceTestImpl extends AbstractTest {

//	@Autowired
//	private VerbaService verbaService;

//	@Autowired
//	private FuncionarioService funcionarioService;

	@Autowired
	private AliquotaService aliquotaService;

//	@Autowired
//	private Interpretador interpretador;

//	@Autowired
//	private FuncionarioRepository funcionarioRepository;

	// TODO Refatorar
//	@Test
//	public void shouldReturnACalculatedValue() throws ScriptException {
//		
//		Funcionario func = new Funcionario();
//		func.setReferenciaSalarialFuncao(new ReferenciaSalarial());
//		func.getReferenciaSalarialFuncao().setValor(1200D);
//		
//		//String value =" salario = o{referenciaSalarialFuncao.valor} - r{rotina_inss} /n"
//    	//		+ "salario*1";
//
//		Interpretador interpretador = new Interpretador();
//		
//		VerbaFormula vf = verbaService.findByFormula("rotina_inss");
//		HashMap<Verba, Object> mapaVerbasAliquotas = new HashMap<>();
//		Object result = vf.getResultadoCalculo(func, interpretador,mapaVerbasAliquotas);
//		
//		//String result = interpretador.translate(func, value);
//		System.out.println("Resultado Verba INSS : " + result);
//		assertTrue(Double.valueOf(result.toString()) == 96D);
//	}

//	@Test
//	public void shouldValidateAFormula() throws ScriptException {
//
//		Interpretador interpretador = new Interpretador();
//
//		String value = " salario = o{1200} - r{rotina_inss} /n" + "salario*1";
//
//		VerbaRequest vr = new VerbaRequest();
//		vr.setFormulaASerValidada(value);
//
//		Object result = verbaService.validaFormula(vr.getFormulaASerValidada());
//
//	}

	@Test
	public void shouldReturnARightAliquotaINSS() {

		Double valorReferencia = 2000D;
		Aliquota aliquota = new Aliquota();

		aliquota = aliquotaService.getAliquotaByFaixaAndValorReferencia(valorReferencia, FaixaEnum.INSS);
		assertEquals(new Double(9D), aliquota.getAliquota());
	}

	@Test
	public void shouldReturnARightAliquotaIRPF() {

		Double valorReferencia = 2000D;
		Aliquota aliquota = new Aliquota();

		aliquota = aliquotaService.getAliquotaByFaixaAndValorReferencia(valorReferencia, FaixaEnum.IRRF);
		assertEquals(new Double(142.8D), aliquota.getDeducao());
	}

	// TODO REFATORAR
//	@Test
//	public void shouldReturnAllVerbasAndAliquotas() {
//		String calculo = "salario = r{vencimento} + r{titulacao_aperfeicoamento} /n "
//				+ "salario = salario - (r{vale_transporte} + r{inss}) /n" + " salario*1 /n";
//		Funcionario funcionario = new Funcionario();
//		funcionario.setReferenciaSalarialCargo(new ReferenciaSalarial());
//		funcionario.getReferenciaSalarialCargo().setValor(5839.45);
//		funcionario.setGrauInstrucao(GrauInstrucaoEnum.SUPERIOR_COMPLETO);
//		Map<Verba, Object> mapaVerbas = new HashMap<>();
//		String convertido = interpretador.translate(funcionario, calculo, mapaVerbas);
//		assertTrue(!convertido.isEmpty());
//		assertTrue(!mapaVerbas.isEmpty());
//
//	}

	// TODO Refatorar
//	@Test
//	public void gerandoFolhaCalculos() {
//		List<Funcionario> funcionarios = funcionarioRepository.findAll();
//		ScriptEngineManager manager = new ScriptEngineManager();
//		ScriptEngine engine = manager.getEngineByName("js");
//		List<FolhaPagamentoProcessamentoResponse> response = new ArrayList<FolhaPagamentoProcessamentoResponse>();
//
//		funcionarios.forEach(item -> {
//
//			Map<Verba, Object> mapasValores = new HashMap<>();
//			FolhaPagamentoProcessamentoResponse rep = new FolhaPagamentoProcessamentoResponse();
//			rep.setNomeFuncionario(item.getNome());
//			rep.setVerbasProcessadas(new HashMap<>());
//			item.getVerbas().forEach(verba -> {
//				verba.getVerba().getFormulas().forEach(verbaFormula -> {
//					try {
//						Object resultado = verbaFormula.getResultadoCalculo(item, interpretador, mapasValores);
//
//						mapasValores.forEach((v, va) -> {
//							rep.getVerbasProcessadas().put(new VerbaResponse(v), va);
//						});
//						rep.getVerbasProcessadas().put(new VerbaResponse(verbaFormula.getVerba()), resultado);
//					} catch (ScriptException e) {
//						e.printStackTrace();
//					}
//				});
//				response.add(rep);
//			});
//
//		});
//
//		assertTrue(!response.isEmpty());
//	}

}
