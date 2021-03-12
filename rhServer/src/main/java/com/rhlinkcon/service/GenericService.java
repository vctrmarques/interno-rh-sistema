package com.rhlinkcon.service;

import java.util.List;

import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;

public abstract class GenericService<Payload, ID> {
	public abstract void create(Payload request);

	public abstract void update(Payload request);

	public abstract void delete(ID id);

	public abstract Payload getById(ID id);

	public abstract PagedResponse<Payload> getPagedList(PagedRequest pagedRequest, Payload requestFilter);

	public abstract List<Payload> getList(Payload requestFilter);

	public abstract List<DadoBasicoDto> getDadoBasicoList(Payload requestFilter);

}