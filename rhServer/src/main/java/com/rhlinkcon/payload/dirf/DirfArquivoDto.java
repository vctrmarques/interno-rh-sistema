package com.rhlinkcon.payload.dirf;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirfArquivoDto {

	private DirfBeneficiarioDto beneficiario;
	
	private List<DirfValoresDto> valoresRTRT;
	private List<DirfValoresDto> valoresRTPO;
	private List<DirfValoresDto> valoresRTIRF;
	
	public DirfArquivoDto() {};
	
	public DirfArquivoDto(ProjecaoDirfBeneficiario e) {
		this.beneficiario = new DirfBeneficiarioDto(e);
	}
}
