package com.rhlinkcon.util.arquivoRemessa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;

import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.util.CampoAlfaNumerico;
import com.rhlinkcon.util.CampoNumerico;

public class HeaderArquivo {

	private final EmpresaFilial empresa;
	private final int numeroRemessa;
	private final LocalDateTime dataHoraGeracao;

	public HeaderArquivo(EmpresaFilial empresa, int numeroRemessa, LocalDateTime dataHoraGeracao) {
		this.empresa = empresa;
		this.numeroRemessa = numeroRemessa;
		this.dataHoraGeracao = dataHoraGeracao;
	}

	public Record create(FlatFile<Record> ff) {
		Record header = ff.createRecord("HeaderArquivo");

		header.setValue("18-18-TipoInscricaoEmpresa", 2); // 1 - CPF 2- CNPJ
		header.setValue("19-32-NumeroInscricaoEmpresa", new CampoNumerico(empresa.getCnpj(), 14)); // numero do CNPJ
		header.setValue("33-38-CodigoConvencio", new CampoNumerico(empresa.getCodigoConvenio(), 6)); // EmpresaFilial codigoConvenio
		header.setValue("39-40-ParametroTransmissao", 01); // Codigo informado pelo banco
		header.setValue("41-41-AmbienteCliente", "T"); // T - teste P - producao
		header.setValue("53-57-AgenciaConta", new CampoNumerico(empresa.getAgenciaBancaria().getNumero(), 5));// numero agencia sem digito verificador
		header.setValue("58-58-DigitoVerificadorAgencia", new CampoAlfaNumerico(empresa.getAgenciaBancaria().getDigito(), 1)); // digito da agencia
		header.setValue("59-70-NumeroConta", 000600071272); // conta corrente 4+8 se operacao e conta ou 12 se sem operacao
		header.setValue("71-71-DigitoVerificadorConta", 5); // digito da conta
		header.setValue("73-102-NomeEmpresa", new CampoAlfaNumerico(empresa.getNomeFilial(), 30));
		DateTimeFormatter data = DateTimeFormatter.ofPattern("ddMMyyyy");
		header.setValue("144-151-DataGeracao", dataHoraGeracao.format(data));
		DateTimeFormatter hora = DateTimeFormatter.ofPattern("hhmmss");
		header.setValue("152-157-HoraGeracao", dataHoraGeracao.format(hora));
		header.setValue("158-163-NumeroSequencialArquivo", new CampoNumerico(numeroRemessa, 6)); // sequencia dos arquivos de remessa do sistema

		return header;
	}

}
