package com.rhlinkcon.util;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.Papel;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.papel.PapelResponse;
import com.rhlinkcon.payload.usuario.UsuarioResponse;

public class ModelMapper {

	public static AnexoResponse mapAnexoToAnexoResponse(Anexo anexo) {
		AnexoResponse anexoResponse = new AnexoResponse();
		anexoResponse.setId(anexo.getId());
		anexoResponse.setFileDownloadUri(getFileDownloadUri(anexo.getFileDownloadUri()));
		anexoResponse.setFileName(anexo.getFileName());
		anexoResponse.setSize(anexo.getSize());
		anexoResponse.setFileType(anexo.getFileType());
		anexoResponse.setDescription(anexo.getDescription());
		anexoResponse.setCreatedAt(anexo.getCreatedAt());
		return anexoResponse;
	}

	private static String getFileDownloadUri(String fileDownloadUri) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(fileDownloadUri).toUriString();
	}

	public static UsuarioResponse mapUsuarioToUsuarioResponse(Usuario usuario) {
		UsuarioResponse userResponse = new UsuarioResponse();
		userResponse.setId(usuario.getId());
		userResponse.setNome(usuario.getNome());
		userResponse.setLogin(usuario.getLogin());
		userResponse.setAtivo(usuario.getAtivo());
		userResponse.setCpf(usuario.getCpf());
		if (Objects.nonNull(usuario.getEmpresaFilial())) {
			userResponse.setEmpresaFilialId(usuario.getEmpresaFilial().getId());
			userResponse.setEmpresaFilial(new DadoBasicoDto(usuario.getEmpresaFilial()));
		}
		if (usuario.getAnexo() != null) {
			Anexo anexo = usuario.getAnexo();
			userResponse.setAnexoFileDownloadUri(getFileDownloadUri(anexo.getFileDownloadUri()));
			userResponse.setAnexoId(anexo.getId());
		}
		return userResponse;
	}

	public static UsuarioResponse mapUsuarioToUsuarioDetalhesResponse(Usuario usuario, String criadoPor,
			String atualizadoPor) {
		UsuarioResponse userResponse = new UsuarioResponse();
		userResponse.setEmail(usuario.getEmail());
		userResponse.setId(usuario.getId());
		userResponse.setNome(usuario.getNome());
		userResponse.setLogin(usuario.getLogin());
		userResponse.setAtivo(usuario.getAtivo());
		userResponse.setCpf(usuario.getCpf());
		if (usuario.getEmpresaFilial() != null)
			userResponse.setEmpresaFilialId(usuario.getEmpresaFilial().getId());
		userResponse.setCriadoEm(usuario.getCreatedAt());
		userResponse.setAlteradoEm(usuario.getUpdatedAt());
		userResponse.setCriadoPor(criadoPor);
		userResponse.setAlteradoPor(atualizadoPor);

		userResponse.setPapeis(new ArrayList<PapelResponse>());
		for (Papel papel : usuario.getPapeis()) {
			userResponse.getPapeis().add(new PapelResponse(papel, Projecao.COMPLETA));
		}

		return userResponse;
	}

}