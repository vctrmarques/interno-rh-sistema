package com.rhlinkcon.filtro;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import com.rhlinkcon.model.FundoEnum;
import com.rhlinkcon.model.RecadastramentoTipoEnum;

public class RecadastramentoFiltro {
	
	private String descricao;
	
	private boolean funfin;
	
	private boolean funprev;
	
	private boolean aposentado;
	
	private boolean pensionista;
	
	private Instant dataNascido;
	
	private Instant dataAdmitido;
	
	public RecadastramentoFiltro() {
		setFunfin(true);
		setFunprev(true);
		setPensionista(true);
		loadDatas();
	}
	
	public RecadastramentoFiltro(String descricaoBusca, List<String> filial, List<String> tipo) {
		setDescricao(descricaoBusca);
		
		if(Objects.nonNull(filial)) {
			for (String s : filial) {
				FundoEnum f = FundoEnum.getEnumByString(s);
				if(f.equals(FundoEnum.FUNFIN)) {
					setFunfin(true);
				} else {
					setFunprev(true);
				}
			}
		} else {
			setFunfin(true);
			setFunprev(true);
		}
		
		if(Objects.nonNull(tipo)) {
			for (String s : tipo) {
				RecadastramentoTipoEnum f = RecadastramentoTipoEnum.getEnumByString(s);
				if(f.equals(RecadastramentoTipoEnum.APOSENTADO)) {
					setAposentado(true);
				} else {
					setPensionista(true);
				}
			}
		} else {
			setPensionista(true);
		}
		
		loadDatas();
	}

	private void loadDatas() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String nascidos = "31/12/1954";
		String admitidos = "31/04/2002";

		LocalDate localDateN = LocalDate.parse(nascidos, formatter);
		LocalDate localDateA = LocalDate.parse(admitidos, formatter);
		
		setDataNascido(localDateN.atStartOfDay().toInstant(ZoneOffset.UTC));
		setDataAdmitido(localDateA.atStartOfDay().toInstant(ZoneOffset.UTC));
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isFunfin() {
		return funfin;
	}

	public void setFunfin(boolean funfin) {
		this.funfin = funfin;
	}

	public boolean isFunprev() {
		return funprev;
	}

	public void setFunprev(boolean funprev) {
		this.funprev = funprev;
	}

	public boolean isAposentado() {
		return aposentado;
	}

	public void setAposentado(boolean aposentado) {
		this.aposentado = aposentado;
	}

	public boolean isPensionista() {
		return pensionista;
	}

	public void setPensionista(boolean pensionista) {
		this.pensionista = pensionista;
	}

	public Instant getDataNascido() {
		return dataNascido;
	}

	public void setDataNascido(Instant dataNascido) {
		this.dataNascido = dataNascido;
	}

	public Instant getDataAdmitido() {
		return dataAdmitido;
	}

	public void setDataAdmitido(Instant dataAdmitido) {
		this.dataAdmitido = dataAdmitido;
	}
	
	
	
}
