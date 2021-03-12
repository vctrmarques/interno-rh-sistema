package com.rhlinkcon.payload;

import javax.validation.constraints.NotNull;

public class BasicRequest {

	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
