package com.rhlinkcon.payload.atributoFormula;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AtributoFormulaDto implements Comparable<AtributoFormulaDto> {

	private String name;
	private String label;

	@Override
	public int compareTo(AtributoFormulaDto arg0) {
		return this.label.compareTo(arg0.getLabel());
	}

}
