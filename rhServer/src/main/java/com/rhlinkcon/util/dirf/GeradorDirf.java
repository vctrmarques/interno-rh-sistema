package com.rhlinkcon.util.dirf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.rhlinkcon.model.Dirf;
import com.rhlinkcon.payload.dirf.DirfArquivoDto;

public class GeradorDirf {

	public static final String LAYOUT_DIRF = "layoutArquivo/layout-dirf-2020.xml";
	
	private final Dirf dirf;
	private final List<DirfArquivoDto> lista;
	
	public GeradorDirf(Dirf dirf, List<DirfArquivoDto> listaArquivo) {
		this.dirf = dirf;
		this.lista = listaArquivo;
	}
	
	public FlatFile<Record> gerar() throws IOException {
		Header header = new Header(dirf);
		
		List<Segmento> segmentos = new ArrayList<>();
		
		for (DirfArquivoDto t : lista) {
			segmentos.add(new Segmento(t));
		}
		
		return exportar(header, segmentos);
	}

	private FlatFile<Record> exportar(Header header, List<Segmento> segmentos) throws IOException {

		Resource resource = new ClassPathResource(LAYOUT_DIRF);
		File layout = resource.getFile();
		
		FlatFile<Record> ff = Texgit.createFlatFile(layout);

		ff.addRecord(header.createDIRF(ff));
		ff.addRecord(header.createRESPO(ff));
		ff.addRecord(header.createDECPJ(ff));
		ff.addRecord(header.createIDREC(ff));
		
		for(Segmento s : segmentos) {
			ff.addRecord(s.create(ff));
		}
		
		//lista de nomes
		
		ff.addRecord(ff.createRecord("FIMDirf"));
		
		return ff;

	}
}
