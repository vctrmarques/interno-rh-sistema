package com.rhlinkcon.payload.classeSalarial;

import java.time.Instant;

import com.rhlinkcon.model.ClasseSalarial;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class ClasseSalarialResponse extends DadoCadastralResponse {
	
	private Long id;

	private String nome;
	
	public ClasseSalarialResponse classeSalarial;
	
	public ClasseSalarialResponse(ClasseSalarial classeSalarial) {
		setId(classeSalarial.getId());
		setNome(classeSalarial.getNome());
	}
	
//	public ClasseSalarialResponse(ClasseSalarial classeSalarial , Instant criadoEm, String criadoPor,
//			Instant alteradoEm, String alteradoPor) {
//		this.setId(classeSalarial.getId());
//		this.setNome(classeSalarial.getNome());
//		this.setAlteradoEm(alteradoEm);
//		this.setAlteradoPor(alteradoPor);
//		this.setCriadoEm(criadoEm);
//		this.setCriadoPor(criadoPor);
//	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
