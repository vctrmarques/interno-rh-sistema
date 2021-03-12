package com.rhlinkcon.util.arquivoRemessa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;

import com.rhlinkcon.util.CampoAlfaNumerico;
import com.rhlinkcon.util.CampoNumerico;

public class Segmento {

	private final Titulo t;
	private final LocalDate dataVencimento;
	private final int contador;

	public Segmento(Titulo titulo, LocalDate dataVencimento, int contador) {
		this.t = titulo;
		this.dataVencimento = dataVencimento;
		this.contador = contador;
	}

	public Record create(FlatFile<Record> ff) {
		
			Record a = ff.createRecord("SegmentoA");
			Record b = ff.createRecord("SegmentoB");
			
			DateTimeFormatter data = DateTimeFormatter.ofPattern("ddMMyyyy");
			
			//Segmento A
			
			a.setValue("9-13-NumeroSequencialRegistroLote", contador);
			a.setValue("15-15-TipoMovimento", 0);//0 inclusao e 9 exclusão
			a.setValue("18-20-CamaraCompensacao", 700);//018 ted, 700 doc e op, 000 cc, 888 boleted
			if(Objects.nonNull(t.getAgencia())) {
				a.setValue("21-23-CodigoBancoDestino", new CampoNumerico(t.getAgencia().getBanco().getCodigo(), 3));
				a.setValue("24-28-AgenciaDestino", new CampoNumerico(t.getAgencia().getNumero(), 5));
				a.setValue("29-29-DigitoVerificadorAgencia", new CampoAlfaNumerico(t.getAgencia().getDigito(), 1));
			} else {
				a.setValue("21-23-CodigoBancoDestino", new CampoNumerico(0, 3));
				a.setValue("24-28-AgenciaDestino", new CampoNumerico(0, 5));
				a.setValue("29-29-DigitoVerificadorAgencia", new CampoAlfaNumerico(0, 1));
			}
			a.setValue("30-41-ContaDestino", new CampoNumerico(t.getContaCorrente(), 12));
			a.setValue("42-42-DigitoVerificadorConta", new CampoAlfaNumerico(t.getDigitoConta(), 1));
			a.setValue("44-73-NomeFavorecido", new CampoAlfaNumerico(t.getNome(), 30));
			a.setValue("74-79-NumeroDocumento", new CampoNumerico(contador, 6));
			a.setValue("93-93-TipoContaFinalidade", t.getTipoConta()); // 0 sem conta, 1 cc, 2 poupanca
			a.setValue("94-101-DataVencimento", dataVencimento.format(data)); //data agendamento
			a.setValue("120-134-ValorLancamento", new CampoNumerico(t.getValorLancamento(), 13)); //salario
			a.setValue("135-143-NumeroDocumentoBanco", new CampoNumerico(0, 9));
			
			//Segmento B
			
			b.setValue("9-13-NumeroSequencialRegistroLote", new CampoNumerico(contador+1, 5));
			b.setValue("18-18-TipoInscricao", 1); //1 cpf e 2 cnpj
			b.setValue("19-32-NumeroInscricao", new CampoNumerico(t.getCpf(), 14)); //numero cpf ou cnpj
			b.setValue("33-62-Logradouro", new CampoAlfaNumerico(t.getLogradouro(), 30)); 
			b.setValue("63-67-Numero", new CampoNumerico(t.getNumero(), 5));
			if(Objects.nonNull(t.getComplemento()))
				b.setValue("68-82-Complemento", new CampoAlfaNumerico(t.getComplemento(), 15));
			
			if(Objects.nonNull(t.getBairro()))
				b.setValue("83-97-Bairro", new CampoAlfaNumerico(t.getBairro(), 15));
			
			if(Objects.nonNull(t.getMunicipio()))
				b.setValue("98-117-Cidade", new CampoAlfaNumerico(t.getMunicipio().getDescricao(), 20));
			
			b.setValue("118-122-PrefixoCep", prefixoCep(t.getCep()));
			b.setValue("123-125-SufixoCep", sufixoCep(t.getCep()));
			b.setValue("126-127-UF", t.getMunicipio().getUf().getSigla());
			b.setValue("128-135-DataVencimento", dataVencimento.format(data)); //data agendamento
			
			//Inclusão do Segmento B em A
			
			a.addInnerRecord(b);
			
		return a;
	}
	
	private Integer prefixoCep(String valor) {
		String numero = valor.substring(0, 5);
		numero = numero.replace("-", "");
		numero.trim();
		return Integer.valueOf(numero);
	}
	
	private String sufixoCep(String valor) {
		String numero = valor.substring(5);
		numero.trim();
		
		return numero;
	}
	
}
