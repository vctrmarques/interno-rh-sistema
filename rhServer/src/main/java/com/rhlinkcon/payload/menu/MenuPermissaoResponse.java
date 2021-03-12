package com.rhlinkcon.payload.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rhlinkcon.util.Utils;

public class MenuPermissaoResponse {

	private List<String> papeis;

	private boolean usuarioAdm;

	private boolean podeCadastrar;

	private boolean podeAtualizar;

	private boolean podeVisualizar;

	private boolean podeExcluir;
	
	/**
	 * Verifica se possui alguma permissão comum.<br>
	 * 
	 * @since 29/07/2020
	 * @return {@link Boolean}
	 */
	
	public boolean isPodeGerenciar() {
		return isUsuarioAdm() || isPodeCadastrar() || isPodeAtualizar() || isPodeVisualizar() || isPodeExcluir();
	}
	
	/**
	 * Verifica se possui alguma permissão baseado numa lista de papeis.<br>
	 * 
	 * @since 29/07/2020
	 * @param {@link List}
	 * @return {@link Boolean}
	 */
	public boolean isPodeGerenciar(List<String> valores) {
		if(Utils.checkList(valores)) {
			Iterator<String> it = valores.iterator();
			while(it.hasNext()) {
				String v = it.next();
				if(isPodeGerenciar(v))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifica se possui alguma permissão baseado em um papel específico.<br>
	 * 
	 * @since 29/07/2020
	 * @param {@link String}
	 * @return {@link Boolean}
	 */
	public boolean isPodeGerenciar(String valor) {
		if(Utils.checkList(this.papeis)) {
			Iterator<String> it = this.papeis.iterator();
			while(it.hasNext()) {
				String v = it.next();
				if(v.equals(getPapelCompleto(valor)))
					return true;
			}
		}
		return false;
	}

	private String getPapelCompleto(String valor) {
		if(valor.contains("ROLE_")) {
			return valor;
		} else {
			return "ROLE_" + valor;
		}
	}

	public MenuPermissaoResponse() {
		this.papeis = new ArrayList<String>();
	}

	public boolean isPodeCadastrar() {
		return podeCadastrar;
	}

	public void setPodeCadastrar(boolean podeCadastrar) {
		this.podeCadastrar = podeCadastrar;
	}

	public boolean isPodeAtualizar() {
		return podeAtualizar;
	}

	public void setPodeAtualizar(boolean podeAtualizar) {
		this.podeAtualizar = podeAtualizar;
	}

	public boolean isPodeVisualizar() {
		return podeVisualizar;
	}

	public void setPodeVisualizar(boolean podeVisualizar) {
		this.podeVisualizar = podeVisualizar;
	}

	public boolean isPodeExcluir() {
		return podeExcluir;
	}

	public void setPodeExcluir(boolean podeExcluir) {
		this.podeExcluir = podeExcluir;
	}

	public List<String> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<String> papeis) {
		this.papeis = papeis;
	}

	public boolean isUsuarioAdm() {
		return usuarioAdm;
	}

	public void setUsuarioAdm(boolean usuarioAdm) {
		this.usuarioAdm = usuarioAdm;
	}

}
