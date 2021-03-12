package com.rhlinkcon.filtro;

import java.util.ArrayList;
import java.util.List;

import com.rhlinkcon.model.PeriodicidadeMesEnum;
import com.rhlinkcon.util.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrecadacaoIndiceFiltro {
	
	private String indice;

	private List<String> periodicidades;
	
	private String ano;
	
	public List<PeriodicidadeMesEnum> periodicidadesToEnum() {
		if(Utils.checkList(this.periodicidades)) {
			List<PeriodicidadeMesEnum> listaEnum = new ArrayList<>();
			for(String e : this.periodicidades) {
				listaEnum.add(PeriodicidadeMesEnum.getEnumByString(e));
			}
			return listaEnum;
		}
		return null;
	}
	
}
