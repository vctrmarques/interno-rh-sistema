package com.rhlinkcon.payload.dirf;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirfInformesDto {

	// item 1
	private DirfDto dirf;
	
	
	// item 2
	private String nome;
	
	private String cpf;
	

	//item 3
	
	private Double totalRendimentos;
	
	private Double previdenciaOficial;
	
	private Double previdenciaComplementar;
	
	private Double pensaoAlimenticia;
	
	private Double impostoRetido;
	
	//item 4
	
	private Double parcelaIsenta;
	
	private Double diariasAjudaCusto;
	
	private Double pensaoProventosAposentadoria;
	
	private String textoOutros;
	
	private Double valorOutros;
	
	//item 5
	
	private Double decimoTerceiro;
	
	private Double impostoSobreDecimoTerceiro;
	
	private String textoOutrosDecimoTerceiro;
	
	private Double outrosDecimoTerceiro;
	
	//item 7
	
	private String informacoes;
	
	public DirfInformesDto() {}
	
	public Integer getAnoExercicio() {
		return this.dirf.getAnoBase() + 1;
	}
	
}
