package com.rhlinkcon.util.dirf;

import java.util.Objects;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;

import com.rhlinkcon.model.Dirf;
import com.rhlinkcon.model.DirfTipoDeclaracaoEnum;
import com.rhlinkcon.util.CampoAlfaNumerico;
import com.rhlinkcon.util.CampoNumerico;

public class Header {

	private final Dirf dirf;
	
	public Header(Dirf dirf) {
		this.dirf = dirf;
	}
	
	public Record createDIRF(FlatFile<Record> ff) {
		Record r = ff.createRecord("DIRF");

		r.setValue("anoReferencia", dirf.getAnoExercicio());
		r.setValue("anoCalendario", dirf.getAnoBase());
		if(dirf.getTipoDeclaracao().equals(DirfTipoDeclaracaoEnum.RETIFICADORA)) {
			r.setValue("retificadora", "S");
			r.setValue("numeroRecibo", dirf.getNumeroDeclaracao());
		} else {
			r.setValue("retificadora", "N");
		}

		return r;
	}
	
	public Record createRESPO(FlatFile<Record> ff) {
		Record r = ff.createRecord("RESPO");

		r.setValue("cpf", new CampoNumerico(dirf.getResponsavelReceita().getCpf(), 11));
		r.setValue("nome", new CampoAlfaNumerico(dirf.getResponsavelReceita().getNome(), 60));
		r.setValue("ddd", new CampoNumerico(dirf.getResponsavelReceita().getDdd(), 2));
		r.setValue("telefone", new CampoNumerico(dirf.getResponsavelReceita().getNumeroTelefone(), 9));
		if(Objects.nonNull(dirf.getResponsavelReceita().getRamal()))
			r.setValue("ramal", new CampoNumerico(dirf.getResponsavelReceita().getRamal(), 6));
		if(Objects.nonNull(dirf.getResponsavelReceita().getEmail()))
			r.setValue("correioEletronico", new CampoAlfaNumerico(dirf.getResponsavelReceita().getEmail(), 50));

		return r;
	}
	
	public Record createDECPJ(FlatFile<Record> ff) {
		Record r = ff.createRecord("DECPJ");

		r.setValue("cnpj", new CampoNumerico(dirf.getFilial().getCnpj(), 14));
		r.setValue("nome", new CampoAlfaNumerico(dirf.getFilial().getNomeFilial(), 150));
		r.setValue("naturezaDeclarante", new CampoNumerico(dirf.getNaturezaDeclarante(), 1));
		r.setValue("cpfResponsavel", new CampoNumerico(dirf.getResponsavelReceita().getCpf(), 11));

		return r;
	}
	
	public Record createIDREC(FlatFile<Record> ff) {
		return ff.createRecord("IDREC");
	}
}
