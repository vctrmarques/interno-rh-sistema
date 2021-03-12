package com.rhlinkcon.payload.importadorLancamentoManual;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoArquivoImportacaoResponse {

	private List<ErroImportData> erroList;

	public ValidacaoArquivoImportacaoResponse() {
		erroList = new ArrayList<ErroImportData>();
	}

	public List<ErroImportData> getErroList() {
		return erroList;
	}

	public void setErroList(List<ErroImportData> erroList) {
		this.erroList = erroList;
	}

}
