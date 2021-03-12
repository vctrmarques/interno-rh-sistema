package com.rhlinkcon.util.dirf;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;

import com.rhlinkcon.model.MesEnum;
import com.rhlinkcon.payload.dirf.DirfArquivoDto;
import com.rhlinkcon.payload.dirf.DirfValoresDto;
import com.rhlinkcon.util.CampoAlfaNumerico;
import com.rhlinkcon.util.CampoNumerico;
import com.rhlinkcon.util.Utils;

public class Segmento {
	
	private final DirfArquivoDto dto;
	
	public Segmento(DirfArquivoDto dto) {
		this.dto = dto;
	}

	public Record create(FlatFile<Record> ff) {
		Record a = ff.createRecord("BPFDEC");
		
		a.setValue("cpf", new CampoNumerico(dto.getBeneficiario().getCpf(), 11));
		a.setValue("nome", new CampoAlfaNumerico(dto.getBeneficiario().getNome(), 60));
		a.setValue("indicadorAlimentando", new CampoAlfaNumerico("N", 1));
		a.setValue("indicadorPrevidenciaComplementar", new CampoAlfaNumerico("N", 1));
		
		if(Utils.checkList(dto.getValoresRTRT())) {
			Record b = ff.createRecord("RTRT");
			for(DirfValoresDto v : dto.getValoresRTRT()) {
				MesEnum mes = MesEnum.getEnumByInteger(v.getMes());
				b.setValue(mes.name().toLowerCase(), new CampoNumerico(v.getValor(), 13));
			}
			a.addInnerRecord(b);
		}
		
		if(Utils.checkList(dto.getValoresRTPO())) {
			Record b = ff.createRecord("RTPO");
			for(DirfValoresDto v : dto.getValoresRTPO()) {
				MesEnum mes = MesEnum.getEnumByInteger(v.getMes());
				b.setValue(mes.name().toLowerCase(), new CampoNumerico(v.getValor(), 13));
			}
			a.addInnerRecord(b);
		}
		
		if(Utils.checkList(dto.getValoresRTIRF())) {
			Record b = ff.createRecord("RTIRF");
			for(DirfValoresDto v : dto.getValoresRTIRF()) {
				MesEnum mes = MesEnum.getEnumByInteger(v.getMes());
				b.setValue(mes.name().toLowerCase(), new CampoNumerico(v.getValor(), 13));
			}
			a.addInnerRecord(b);
		}
		
		
		return a;
	}
}
