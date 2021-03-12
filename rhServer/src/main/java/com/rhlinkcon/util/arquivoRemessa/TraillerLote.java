package com.rhlinkcon.util.arquivoRemessa;

import java.util.List;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;

import com.rhlinkcon.util.CampoNumerico;
import com.rhlinkcon.util.Utils;

public class TraillerLote {

	private final int quantidadeRegistros;
	private final List<Titulo> titulos;
	
	public TraillerLote(int quantidadeRegistros, List<Titulo> titulos) {
		this.quantidadeRegistros = quantidadeRegistros;
		this.titulos = titulos;
	}

	public Record create(FlatFile<Record> ff) {
		Record trailler = ff.createRecord("TraillerLote");
		trailler.setValue("18-23-QuantidadeRegistrosLote", new CampoNumerico(quantidadeRegistros, 6));// quantitativo de todos os registros do arquivo de remessa
		trailler.setValue("24-41-SomatorioValores", new CampoNumerico(somatorioValores(), 18));// soma dos valores segmento A

		return trailler;
	}
	
	private Integer somatorioValores() {
		Double soma = 0.0;
		
		for (Titulo t : titulos) {
			soma = soma + Utils.roundValue(t.getValorLancamento()); //colocar variavel com valor
		}
		
		String numero = Utils.roundValue(soma).toString();
		numero = numero.replace(".", "");
		numero = numero.replace(",", "");
		numero.trim();
		
		return Integer.valueOf(numero);
	}

}
