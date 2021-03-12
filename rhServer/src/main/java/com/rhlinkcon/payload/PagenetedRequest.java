package com.rhlinkcon.payload;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;

import com.rhlinkcon.util.Utils;

/**
 * Contém os atributos de paginação enviados pela tela. Também
 * oferesse métodos utilitários para conversão em Pageable(spring)   
 */
public class PagenetedRequest implements Serializable {
	
	private static final long serialVersionUID = 9185713805481062241L;

	private int limit;
	
	private int page;
	
	private int size;

	private String order;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
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
	
	public Pageable getPageable() {
		Utils.validatePageNumberAndSize(page, size);
		return Utils.getPageRequest(page, size, order);
	}
}
