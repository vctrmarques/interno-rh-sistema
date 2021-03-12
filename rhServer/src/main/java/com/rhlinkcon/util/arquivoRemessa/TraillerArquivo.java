package com.rhlinkcon.util.arquivoRemessa;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;

public class TraillerArquivo {

	private final int quantidadeLotes;
	private final int quantidadeRegistros;
	
	public TraillerArquivo(int quantidadeLotes, int quantidadeRegistros) {
		this.quantidadeLotes = quantidadeLotes;
		this.quantidadeRegistros = quantidadeRegistros;
	}

	public Record create(FlatFile<Record> ff) {
		Record trailler = ff.createRecord("TraillerArquivo");
		
		// preencher com a quantidade de lotes dentro do arquivo
		trailler.setValue("18-23-QuantidadeLotesArquivo", quantidadeLotes);

		// preencher com a quantidade de registros dentro do arquivo, incluindo “HEADER”
		// e trailler do lote e do arquivo
		trailler.setValue("24-29-QuantidadeRegistrosArquivos", quantidadeRegistros);
		
		return trailler;
	}

}
