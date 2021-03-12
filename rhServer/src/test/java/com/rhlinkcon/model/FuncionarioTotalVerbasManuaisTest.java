package com.rhlinkcon.model;

public class FuncionarioTotalVerbasManuaisTest {

	private static final double PRECISAO = 0.00001;

	// TODO Refatorar
//
//	@Test
//	public void testGetTotalVerbasManuaisSemComFormulas() {
//		Funcionario funcionario = new Funcionario();
//
//		List<VerbaFormula> formulas = new ArrayList<>();
//		formulas.add(getVerbaFormula("o{xxxx}"));
//		formulas.add(getVerbaFormula("o{xxxx} - r{yyyy}"));
//
//		Verba verba = new Verba();
//		verba.setFormulas(formulas);
//
//		Set<FuncionarioVerba> verbas = new HashSet<>();
//		verbas.add(getFuncionarVerba(verba, 200.0));
//		verbas.add(getFuncionarVerba(verba, 200.0));
//
//		funcionario.setVerbas(verbas);
//
//		Double total = funcionario.getTotalVerbasManuais();
//		assertNotNull(total);
//		assertEquals(0.0, total, PRECISAO);
//	}

//	@Test
//	public void testGetTotalVerbasManuaisSemFormulas() {
//		Funcionario funcionario = new Funcionario();
//
//		Verba verba = new Verba();
//		verba.setFormulas(new ArrayList<>());
//
//		Set<FuncionarioVerba> verbas = new HashSet<>();
//		verbas.add(getFuncionarVerba(verba, 200.0));
//		verbas.add(getFuncionarVerba(verba, 200.0));
//
//		funcionario.setVerbas(verbas);
//
//		Double total = funcionario.getTotalVerbasManuais();
//		assertNotNull(total);
//		assertEquals(400.0, total, PRECISAO);
//	}

	private FuncionarioVerba getFuncionarVerba(Verba verba, Double valor) {
		FuncionarioVerba funcVerba = new FuncionarioVerba();

		funcVerba.setVerba(verba);
		funcVerba.setValor(valor);

		return funcVerba;
	}

//	private VerbaFormula getVerbaFormula(String formula) {
//		VerbaFormula verbaFormula = new VerbaFormula();
//
//		verbaFormula.setFormula(formula);
//		verbaFormula.setDescricao("");
//
//		return verbaFormula;
//	}
}
