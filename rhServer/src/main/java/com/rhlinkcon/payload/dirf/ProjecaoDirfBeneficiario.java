package com.rhlinkcon.payload.dirf;

public interface ProjecaoDirfBeneficiario {

	Long getId();
	String getNome(); 
	String getCpf();
	Long getPeId();
	String getPeNome(); 
	String getPeCpf(); 
	Long getFpId();
	Double getVantagens(); 
	Double getDescontos();
	Double getLiquidos();
	String getSituacao();
	
}
