package com.rhlinkcon.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class CSVBuilder<T> {

	protected Character delimitadorCSV = ';';

	protected String specificFilePath = null;

	protected List<T> resultDTO;

	protected InputStream inputStream;;
	protected TabelaCSV buildCSV() {
		resultDTO = new ArrayList<T>();

		TabelaCSV csv = null;

		try {
			
			if (null==inputStream && specificFilePath != null){
				inputStream = new FileInputStream(specificFilePath);
			}
			

			// Remover o cabeçalho
			csv = CSVUtils.readInputStream(inputStream, "UTF-8", delimitadorCSV.toString(), true);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (csv != null) {
			csv.setDelimitador(delimitadorCSV);
			return csv;
		} else
			throw new RuntimeException("Erro durante o parse do CSV. Não foram encontrados dados.");

	}
	
	protected Map<String, Object> buildMapObject(LinkedList<Object> linha, List<String> nomesColunas) {
		Map<String,Object> objeto = new HashMap<String, Object>();
		Integer indexColum = 0;
		
		for(String nomeColuna : nomesColunas){
			objeto.put(nomeColuna, linha.get(indexColum));
			indexColum++;
		}
		
		return objeto;
	}
	
	public List<T> buildObject(){
		//Método deve ser sobrescrito no controller.
		return null;
	}

	public Character getDelimitadorCSV() {
		return delimitadorCSV;
	}

	public void setDelimitadorCSV(Character delimitadorCSV) {
		this.delimitadorCSV = delimitadorCSV;
	}

	public String getSpecificFilePath() {
		return specificFilePath;
	}

	public void setSpecificFilePath(String specificFilePath) {
		this.specificFilePath = specificFilePath;
	}

	public List<T> getResultDTO() {
		return resultDTO;
	}

	public void setResultDTO(List<T> resultDTO) {
		this.resultDTO = resultDTO;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
