package com.rhlinkcon.util.arquivoRemessa;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.util.Utils;

public class Remessa {
	
	public static final String LAYOUT_CEF = "layoutArquivo/layout-cef-cnab-240-remessa.xml";
	
	static int numeroSequencial;

	private final int numeroRemessa;
	private final EmpresaFilial empresa;
	private final List<Titulo> titulos;
	private final LocalDateTime dataHoraGeracao;
	private final int quantidadeRegistros;
	private final LocalDate dataVencimento;

	public Remessa(int numeroRemessa, EmpresaFilial empresa, List<Titulo> titulos, LocalDate dataVencimento) {
		this.numeroRemessa = numeroRemessa;
		this.empresa = empresa;
		this.titulos = titulos;
		this.dataHoraGeracao = LocalDateTime.now();
		this.quantidadeRegistros = Utils.checkList(titulos) ? titulos.size()*2 + 2 : 0;
		this.dataVencimento = dataVencimento;
	}

	public FlatFile<Record> gerarRemessa() throws IOException {
		HeaderArquivo header = new HeaderArquivo(empresa, numeroRemessa, dataHoraGeracao);
		HeaderLote headerLote = new HeaderLote(empresa);
		List<Segmento> segmentos = new ArrayList<>();
		int contador = 1;
		for (Titulo t : titulos) {
			segmentos.add(new Segmento(t, dataVencimento, contador));
			contador = contador +2;
		}
		
		TraillerLote traillerLote = new TraillerLote(quantidadeRegistros, titulos);
		TraillerArquivo traillerArquivo = new TraillerArquivo(1, quantidadeRegistros + 2);
		
		return exportarRemessa(header, headerLote, segmentos, traillerLote, traillerArquivo);
	}

	private FlatFile<Record> exportarRemessa(HeaderArquivo header, HeaderLote headerLote, List<Segmento> segmentos, TraillerLote traillerLote, TraillerArquivo traillerArquivo) throws IOException {

		Resource resource = new ClassPathResource(LAYOUT_CEF);
		File layout = resource.getFile();
		
		FlatFile<Record> ff = Texgit.createFlatFile(layout);

		ff.addRecord(header.create(ff));
		ff.addRecord(headerLote.create(ff));
		for(Segmento s : segmentos) {
			ff.addRecord(s.create(ff));
		}
		ff.addRecord(traillerLote.create(ff));
		ff.addRecord(traillerArquivo.create(ff));
		
		return ff;

	}

}