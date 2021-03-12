package com.rhlinkcon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Eduardo Costa
 * 
 * */
public class CSVUtils {
	
	/** Separador de coluna padrão. */
	public static final String SEPARADOR_DEFAULT = ";";

	/** Lê um inputStream e retorna uma tabela CSV. Pode ser utilizado para importar arquivos .CSV
	 * @param inputStream
	 *            <b>Obrigatório.</b> InputStream que será lido e processado
	 * @param possuiCabecalho
	 *            Indica se a primeira linha contem um cabecalho. Caso possua,
	 *            este será utilizado como chave no mapeamento da coluna. Caso
	 *            contrário, a chave será: CAMPO<b>N</b>, onde <b>N</b> é o índice da
	 *            coluna iniciando em 1. Ex.: CAMPO1, CAMPO2, ..., CAMPO10,
	 *            CAMPO11, ..., CAMPON.
	 * @param codificacao
	 *            Codificação do arquivo. As codificacoes aceitas sso as listadas no <i>IANA Charset Registry</i>. Exemplo: UTF-8, ISO-8859-1, etc.
	 * @param separador
	 *            Caso não informado, adotar-se-a a vírgula como padrão.
	 * @return
	 * @throws IOException
	 * 
	 * @see http://www.iana.org/assignments/character-sets
	 */
	public static TabelaCSV readInputStream(InputStream inputStream, String codificacao, String separador, Boolean removerCabecalho) throws IOException {
		int linhaCounter = 0;
		TabelaCSV tabelaCSV = new TabelaCSV();
		try{
			if (separador == null) separador = SEPARADOR_DEFAULT;
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String linhaOrigem;
			linhaCounter++;
			
			if(null != (removerCabecalho) && removerCabecalho){
				tabelaCSV.setNomeColunas(extrairNomeColunas(in.readLine(),codificacao,separador));
				linhaCounter++;
			}
			
			while ((linhaOrigem = in.readLine()) != null) {
				linhaOrigem = new String(linhaOrigem.getBytes(), codificacao);
				if (linhaOrigem.length() > 0) {
					tabelaCSV.addLinha(splitCSV(linhaOrigem, separador));
				}
				linhaCounter ++;
			}
		} catch (IOException e) {
			throw new IOException(
					"Não foi possível ler os dados. Por favor, verifique se o formato CSV está correto (linha "
							+ linhaCounter + ").", e);
		}
		return tabelaCSV;
	}
	
	private static List<String> extrairNomeColunas(String linha, String codificacao, String separador) throws IOException{
		List<String> nomeColunas = new ArrayList<String>();
		
		linha = new String(linha.getBytes(), codificacao);
		
		if (linha.length() > 0) 
			nomeColunas = Arrays.asList(splitCSV(linha, separador));
		
		return nomeColunas;
	}
	
	/** Divide uma String CSV em um array de String.
	 * @param linha linha no formato CSV
	 * @param separador separador utilizado entre os campos. Caso não informado, será adotado como padrão a vírgula.
	 * @return
	 */
	public static String[] splitCSV(String linha, String separador) {
		LinkedList<String> dados = new LinkedList<String>();
		boolean abreAspas = false;
		StringBuilder dado = new StringBuilder();
		if (separador == null) separador = SEPARADOR_DEFAULT;
		for (char c : linha.toCharArray()){
			if (c == '\"') {
				abreAspas = !abreAspas;
				if (dado.length() > 0)
					dado.append(c);
			} else if (c == separador.charAt(0) && !abreAspas){
				if (dado.length() > 0 && dado.charAt(dado.length() - 1) == '\"')
					dados.add(dado.substring(0, dado.length() - 1));
				else
					dados.add(dado.toString());
				dado = new StringBuilder();
			} else {
				dado.append(c);
			}
		}
		if (dado.length() > 0 && dado.charAt(dado.length() - 1) == '\"')
			dados.add(dado.substring(0, dado.length() - 1));
		else
			dados.add(dado.toString());
		return dados.toArray(new String[dados.size()]);
	}
	

}
