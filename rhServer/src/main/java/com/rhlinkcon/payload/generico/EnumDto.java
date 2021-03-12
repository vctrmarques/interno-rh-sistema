package com.rhlinkcon.payload.generico;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnumDto {

	private String value;

	private String label;
	
	private String other;
	
	public EnumDto() {

	}

	public EnumDto(String value, String label) {
		this.setValue(value);
		this.setLabel(label);
	}
	
	public EnumDto(String value, String label, String other) {
		this.setValue(value);
		this.setLabel(label);
		this.setOther(other);
	}

}
