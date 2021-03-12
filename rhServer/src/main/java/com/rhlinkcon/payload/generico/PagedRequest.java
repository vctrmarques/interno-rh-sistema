package com.rhlinkcon.payload.generico;

public class PagedRequest {

	private int page;

	private int size;

	private String order;

	public PagedRequest() {

	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
