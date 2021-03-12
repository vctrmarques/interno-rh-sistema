package com.rhlinkcon.audit;

import java.util.ArrayList;
import java.util.List;

public class AuditoriaDescricaoDto {

	private List<AuditoriaDescricaoItemDto> itemList;

	public AuditoriaDescricaoDto() {
		setItemList(new ArrayList<AuditoriaDescricaoItemDto>());
	}

	public List<AuditoriaDescricaoItemDto> getItemList() {
		return itemList;
	}

	public void setItemList(List<AuditoriaDescricaoItemDto> itemList) {
		this.itemList = itemList;
	}
}
