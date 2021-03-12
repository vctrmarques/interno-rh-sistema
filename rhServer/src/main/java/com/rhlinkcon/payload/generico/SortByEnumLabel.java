package com.rhlinkcon.payload.generico;

import java.util.Comparator;

public class SortByEnumLabel implements Comparator<EnumDto>{

	@Override
	public int compare(EnumDto o1, EnumDto o2) {
		return o1.getLabel().compareTo(o2.getLabel());
	}

}
