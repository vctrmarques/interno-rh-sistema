package com.rhlinkcon.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Eduardo Costa
 * */
public class TabelaCSV {
	
	/** Tabela contendo os dados CSV. */
	private LinkedList<LinkedList<Object>> tabela;
	
	/** Delimitador padrão de dados. Por padrão, o delimitador é a vírgula. */
	private char delimitador;
	
	private List<String> nomeColunas = new ArrayList<String>();
	

	/**
	 * Construtor padrão.
	 */
	public TabelaCSV() {
		tabela = new LinkedList<LinkedList<Object>>();
		delimitador = ',';
	}
	
	/** Construtor parametrizado
	 * @param delimitador
	 */
	public TabelaCSV(char delimitador) {
		this();
		this.delimitador = delimitador;
	}
	
	/** Retorna o número de linhas da tabela CSV.
	 * @return
	 */
	public int getNumLinhas() {
		return tabela.size();
	}

	/** Retorna o número de colunas da tabela CSV.
	 * @return
	 */
	public int getNumColunas() {
		return tabela.getFirst().size();
	}

	/** Retorna um array de String com os dados de uma linha da tabela CSV.
	 * @param index
	 * @return
	 */
	public String[] getLinha(int index) {
		return tabela.get(index).toArray(new String[getNumColunas()]);
	}
	
	/** Adiciona uma linha nova a tabela CSV.
	 * @param linha
	 */
	public void addLinha(Object[] linha) {
		LinkedList<Object> novaLinha = new LinkedList<Object>();
		for (Object elemento : linha)
			novaLinha.add(elemento);
		tabela.add(novaLinha);
	}

	public LinkedList<LinkedList<Object>> getTabela() {
		return tabela;
	}

	public void setTabela(LinkedList<LinkedList<Object>> tabela) {
		this.tabela = tabela;
	}

	public char getDelimitador() {
		return delimitador;
	}

	public void setDelimitador(char delimitador) {
		this.delimitador = delimitador;
	}

	public List<String> getNomeColunas() {
		return nomeColunas;
	}

	public void setNomeColunas(List<String> nomeColunas) {
		this.nomeColunas = nomeColunas;
	}
	
	

}
