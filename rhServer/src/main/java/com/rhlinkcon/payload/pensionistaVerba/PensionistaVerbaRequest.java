package com.rhlinkcon.payload.pensionistaVerba;

import java.util.List;

import com.rhlinkcon.payload.pensionista.PensionistaRequest;;

public class PensionistaVerbaRequest {
	
	private PensionistaRequest pensionista;

	private List<PensionistaVerbasRequest> verbasPensionista;

	public List<PensionistaVerbasRequest> getVerbasPensionista() {
		return verbasPensionista;
	}

	public void setVerbasPensionista(List<PensionistaVerbasRequest> verbasPensionista) {
		this.verbasPensionista = verbasPensionista;
	}

	public PensionistaRequest getPensionista() {
		return pensionista;
	}

	public void setPensionista(PensionistaRequest pensionista) {
		this.pensionista = pensionista;
	}

	
}
