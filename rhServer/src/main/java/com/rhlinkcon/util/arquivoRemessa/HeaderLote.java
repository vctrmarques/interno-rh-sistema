package com.rhlinkcon.util.arquivoRemessa;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;

import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.util.CampoAlfaNumerico;
import com.rhlinkcon.util.CampoNumerico;

public class HeaderLote {

	private final EmpresaFilial empresa;

	public HeaderLote(EmpresaFilial empresa) {
		this.empresa = empresa;
	}

	public Record create(FlatFile<Record> ff) {
		Record headerLote = ff.createRecord("HeaderLote");

		headerLote.setValue("9-9-TipoOperacao", empresa.getTipoOperacao().name()); // “C” = Compromisso de pagamento “D” = Compromisso de recebimento
		headerLote.setValue("10-11-TipoServico", 30); // preencher conforme tabela G025
		headerLote.setValue("18-18-TipoInscricaoEmpresa", 2); // 1 para CPF e 2 para CNPJ
		headerLote.setValue("19-32-NumeroInscricaoEmpresa", new CampoNumerico(empresa.getCnpj(), 14)); // numero CPF/CNPJ baseado no valor do registro anterior
		headerLote.setValue("33-38-CodigoConvenio", new CampoNumerico(empresa.getCodigoConvenio(), 6)); // preencher com o código do convênio informado pelo Banco
		headerLote.setValue("41-44-CodigoCompromisso", new CampoNumerico(2, 4));// 02 - Pagamento de Salários
		headerLote.setValue("45-46-ParametroTransmissao", 01); // preencher com o código informado pelo Banco
		headerLote.setValue("53-57-AgenciaConta", new CampoNumerico(empresa.getAgenciaBancaria().getNumero(), 5));
		headerLote.setValue("58-58-DigitoVerificadorAgencia", new CampoAlfaNumerico(empresa.getAgenciaBancaria().getDigito(), 1));
		headerLote.setValue("59-70-NumeroConta", new CampoNumerico("600071272", 12));
		headerLote.setValue("71-71-DigitoVerificadorConta", 5);
		headerLote.setValue("73-102-NomeEmpresa", new CampoAlfaNumerico(empresa.getNomeFilial(), 30));
		headerLote.setValue("143-172-Logradouro", new CampoAlfaNumerico(empresa.getLogradouro(), 30));
		headerLote.setValue("173-177-Numero", new CampoNumerico(empresa.getNumero(), 5));
		headerLote.setValue("178-192-Complemento", new CampoAlfaNumerico(empresa.getComplemento(), 15));
		headerLote.setValue("193-212-Cidade", new CampoAlfaNumerico(empresa.getMunicipio().getDescricao(), 20));
		headerLote.setValue("213-217-PrefixoCep", new CampoNumerico(empresa.getCep().substring(0, 5), 5));
		headerLote.setValue("218-220-SufixoCep", new CampoAlfaNumerico(empresa.getCep().substring(5), 3));
		headerLote.setValue("221-222-UF", new CampoAlfaNumerico(empresa.getMunicipio().getUf().getSigla(), 2));

		return headerLote;
	}

}
