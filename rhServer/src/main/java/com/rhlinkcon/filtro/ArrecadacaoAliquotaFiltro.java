package com.rhlinkcon.filtro;

import java.util.ArrayList;
import java.util.List;

import com.rhlinkcon.model.ArrecadacaoAliquotaEncargoEnum;
import com.rhlinkcon.util.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrecadacaoAliquotaFiltro {

	private String inicioVigencia;
	
	private String fimVigencia;
	
	private List<String> encargos;
	
	public List<ArrecadacaoAliquotaEncargoEnum> encargosToEnum() {
		if(Utils.checkList(this.encargos)) {
			List<ArrecadacaoAliquotaEncargoEnum> listaEnum = new ArrayList<>();
			for(String e : this.encargos) {
				listaEnum.add(ArrecadacaoAliquotaEncargoEnum.getEnumByString(e));
			}
			return listaEnum;
		}
		return null;
	}
}
