package com.rhlinkcon.payload.anexo;

import java.time.Instant;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rhlinkcon.model.Anexo;

public class AnexoResponse {

	private Long id;
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;
	private String description;
	private Instant createdAt;

	public AnexoResponse() {
	};

	public AnexoResponse(Anexo anexo) {
		this.id = anexo.getId();
		this.description = anexo.getDescription();
		this.fileName = anexo.getFileName();
		this.fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(anexo.getFileDownloadUri())
				.toUriString();
		this.fileType = anexo.getFileType();
		this.size = anexo.getSize();
		this.createdAt = anexo.getCreatedAt();
	};

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

}
