package com.rhlinkcon.filtro;

import com.rhlinkcon.model.ModalidadeAposentadoriaEnum;
import com.rhlinkcon.model.TipoAposentadoriaEnum;

public class RegraAposentadoriaFiltro {
	private ModalidadeAposentadoriaEnum modalidade;
	private TipoAposentadoriaEnum tipo;
	private String leibase;

	public RegraAposentadoriaFiltro(String modalidade, String tipo, String leibase) {
		if (modalidade.equals("GERAL"))
			this.modalidade = ModalidadeAposentadoriaEnum.GERAL;
		else if (modalidade.equals("ESPECIFICA"))
			this.modalidade = ModalidadeAposentadoriaEnum.ESPECIFICA;

		if (tipo.equals("COMPULSORIA"))
			this.tipo = TipoAposentadoriaEnum.COMPULSORIA;
		else if (tipo.equals("INVALIDEZ"))
			this.tipo = TipoAposentadoriaEnum.INVALIDEZ;
		else if (tipo.equals("VOLUNTARIA"))
			this.tipo = TipoAposentadoriaEnum.VOLUNTARIA;

		this.leibase = leibase;
	}

	public ModalidadeAposentadoriaEnum getModalidade() {
		return modalidade;
	}

	public void setModalidade(ModalidadeAposentadoriaEnum modalidade) {
		this.modalidade = modalidade;
	}

	public TipoAposentadoriaEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoAposentadoriaEnum tipo) {
		this.tipo = tipo;
	}

	public String getLeibase() {
		return leibase;
	}

	public void setLeibase(String leibase) {
		this.leibase = leibase;
	}

}
