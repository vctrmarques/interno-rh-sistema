package com.rhlinkcon.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.payload.arquivoExportacaoSiprev.ArquivoExportacaoSiprevDto;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;

@Service
public class ArquivoExportacaoSiprevService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public ArquivoExportacaoSiprevService() {

	}

	public ByteArrayInputStream getArquivoExportacaoXml(ArquivoExportacaoSiprevDto filtro) {

		// Processamento do tipo de exportação Servidor
		if (filtro.getTipoExportacaoSelecionada().equals("Servidor")) {

			Optional<List<Funcionario>> funcionariosOpt = funcionarioRepository
					.findByTipoSituacaoFuncionalId(filtro.getSituacaoFuncionalId());

			if (!funcionariosOpt.isPresent()) {
				throw new BadRequestException("A situação funcional não retornou servidores.");
			}

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dBuilder = documentFactory.newDocumentBuilder();
				Document doc = dBuilder.newDocument();
				doc.setXmlStandalone(true);

				// Elemento principal "ns2:siprev"
				Element ns2Siprev = doc.createElement("ns2:siprev");
				ns2Siprev.setAttribute("xmlns:ns2", "http://www.dataprev.gov.br/siprev");
				doc.appendChild(ns2Siprev);

				// Elemento "ente" dentro do "ns2:siprev"
				Element ente = doc.createElement("ente");
				ente.setAttribute("siafi", "9373");
				ente.setAttribute("cnpj", "01612092000123");
				ns2Siprev.appendChild(ente);

				for (Funcionario funcionario : funcionariosOpt.get()) {
					// Elemento "servidores" dentro do "ns2:siprev"
					Element servidores = doc.createElement("servidores");
					servidores.setAttribute("operacao", filtro.getOperacaoSelecionada());
					ns2Siprev.appendChild(servidores);

					// Elemento "dadosPessoais" dentro do "servidores"
					servidores.appendChild(dadosPessoais(doc, funcionario));

					// Elemento "documentos" dentro do "servidores"
					servidores.appendChild(documentos(doc, funcionario));

					// Elemento "contato" dentro do "servidores"
					servidores.appendChild(contato(doc, funcionario));

					// Elemento "endereco" dentro do "servidores"
					servidores.appendChild(endereco(doc, funcionario));

					// Elemento "certidaoObito" dentro do "servidores"
//					servidores.appendChild(certidaoObito(doc));

					// Elemento "representatividade" dentro do "servidores"
//					servidores.appendChild(representatividade(doc));
				}

				ByteArrayOutputStream output = new ByteArrayOutputStream();

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();

				transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
				transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

				DOMSource source = new DOMSource(doc);

				StreamResult result = new StreamResult(output);
				transformer.transform(source, result);

				return new ByteArrayInputStream(output.toByteArray());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private static String formatStr(String str, int max) {
		int endIndex = str.length() > max ? max - 1 : str.length();
		return str.substring(0, endIndex);
	}

	private static Node dadosPessoais(Document doc, Funcionario funcionario) {
		Element no = doc.createElement("dadosPessoais");

		// Obrigatório: Sim; Tipo: Caracter; Max: 80
		no.setAttribute("nome", formatStr(funcionario.getNome(), 80));

		// Obrigatório: Não; Tipo: Numérico; Max: 1
		if (Objects.nonNull(funcionario.getEstadoCivil()))
			no.setAttribute("estadoCivil", funcionario.getEstadoCivil().getOther());

		// Obrigatório: Não; Tipo: Data; Max: 10 - AAAA-MM-DD
		if (Objects.nonNull(funcionario.getDataNascimento()))
			no.setAttribute("dataNascimento", formatDate(funcionario.getDataNascimento()));

		// Obrigatório: Não; Tipo: Data; Max: 10 - AAAA-MM-DD
		if (Objects.nonNull(funcionario.getDadoCadastral())
				&& Objects.nonNull(funcionario.getDadoCadastral().getDataFalecimento()))
			no.setAttribute("dataFalecimento", formatDate(funcionario.getDadoCadastral().getDataFalecimento()));

		// Obrigatório: Não; Tipo: Numérico; Max: 10
		if (Objects.nonNull(funcionario.getNacionalidade())
				&& Objects.nonNull(funcionario.getNacionalidade().getCodigoSiprev()))
			no.setAttribute("nacionalidade", funcionario.getNacionalidade().getCodigoSiprev().toString());

		// Obrigatório: Não; Tipo: Numérico; Max: 6
		if (Objects.nonNull(funcionario.getNaturalidade())
				&& Objects.nonNull(funcionario.getNaturalidade().getCodigoIbge()))
			no.setAttribute("naturalidade", formatStr(funcionario.getNaturalidade().getCodigoIbge().toString(), 6));

		// Obrigatório: Não; Tipo: Numérico; Max: 1
		if (Objects.nonNull(funcionario.getNaturalizado()))
			no.setAttribute("naturalizado", funcionario.getNaturalizado() ? "1" : "0");

		// Obrigatório: Não; Tipo: Numérico; Max: 2
		if (Objects.nonNull(funcionario.getGrauInstrucao()))
			no.setAttribute("escolaridade", funcionario.getGrauInstrucao().getOther());

		// Obrigatório: Não; Tipo: Caracter; Max: 1
		if (Objects.nonNull(funcionario.getSexo()) && Character.toString(funcionario.getSexo()).equals("N"))
			no.setAttribute("sexo", Character.toString(funcionario.getSexo()));

		// Obrigatório: Não; Tipo: Caracter; Max: 50
		if (Objects.nonNull(funcionario.getNomeMae()))
			no.setAttribute("nomeMae", formatStr(funcionario.getNomeMae(), 50));

		// Obrigatório: Não; Tipo: Caracter; Max: 50
		if (Objects.nonNull(funcionario.getNomePai()))
			no.setAttribute("nomePai", formatStr(funcionario.getNomePai(), 50));

		// Obrigatório: Não; Tipo: Data; Max: 10 - AAAA-MM-DD
		if (Objects.nonNull(funcionario.getDataAdmissao()))
			no.setAttribute("dataIngressoServicoPublico", formatDate(funcionario.getDataAdmissao()));

		return no;
	}

	private static String formatDate(Instant instant) {
		LocalDateTime nasc = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return nasc.format(formatter);

	}

	private static Node documentos(Document doc, Funcionario funcionario) {
		Element no = doc.createElement("documentos");

		// Campo obrigatório, máximo 11 Numérico
		no.setAttribute("numeroCPF", funcionario.getCpf());

		// Obrigatório: Não; Tipo: Numérico; Max: 11
		if (Objects.nonNull(funcionario.getPisPasep()))
			no.setAttribute("numeroNIT", formatStr(funcionario.getPisPasep(), 11));

		// Obrigatório: Não; Tipo: Caracter; Max: 20
		if (Objects.nonNull(funcionario.getIdentidade()))
			no.setAttribute("numeroRG", formatStr(funcionario.getIdentidade(), 20));

		// Obrigatório: Não; Tipo: Data; Max: 10 - AAAA-MM-DD
		if (Objects.nonNull(funcionario.getDataExpedicaoRg()))
			no.setAttribute("dataEmissaoRG", formatDate(funcionario.getDataExpedicaoRg()));

		// TODO Pendente, pois não existe tabela ou enum, impossibilitando o de para.
//		no.setAttribute("orgaoExpedidorRG", "1");

		// Obrigatório: Não; Tipo: Caracter; Max: 2
		if (Objects.nonNull(funcionario.getUfOrgaoExpeditor()))
			no.setAttribute("ufRG", funcionario.getUfOrgaoExpeditor().getSigla().toUpperCase());

		// Obrigatório: Não; Tipo: Numérico; Max: 12
		if (Objects.nonNull(funcionario.getNumeroCtps()))
			no.setAttribute("numeroCTPS", formatStr(funcionario.getNumeroCtps(), 12));

		// Obrigatório: Não; Tipo: Numérico; Max: 10
		if (Objects.nonNull(funcionario.getSerieCtps()))
			no.setAttribute("serieCTPS", formatStr(funcionario.getSerieCtps(), 10));

		// TODO Pendente, pois não existe o atributo dataEmissaoCTPS, impossibilitando o
		// de para.
//		no.setAttribute("dataEmissaoCTPS", "1");

		// Obrigatório: Não; Tipo: Numérico; Max: 14
		if (Objects.nonNull(funcionario.getTituloEleitor()))
			no.setAttribute("numeroTituloEleitor", formatStr(funcionario.getTituloEleitor(), 14));

		// Obrigatório: Não; Tipo: Numérico; Max: 8
		if (Objects.nonNull(funcionario.getZona()))
			no.setAttribute("zonaTituloEleitor", formatStr(funcionario.getZona().toString(), 8));

		// Obrigatório: Não; Tipo: Numérico; Max: 8
		if (Objects.nonNull(funcionario.getSecao()))
			no.setAttribute("secaoTituloEleitor", formatStr(funcionario.getSecao().toString(), 8));

		// Obrigatório: Não; Tipo: Caracter; Max: 2
		if (Objects.nonNull(funcionario.getUfTituloEleitor()))
			no.setAttribute("ufTituloEleitor", formatStr(funcionario.getUfTituloEleitor().getSigla(), 2));

		return no;
	}

	private static Node contato(Document doc, Funcionario funcionario) {
		Element no = doc.createElement("contato");

		// TODO Estrutura de contato incompatível com a estrutura de funcionário.
		// Refactory necessário.
//		// Obrigatório: Não; Tipo: Caracter; Max: 2
//		no.setAttribute("dddTelefone", "");
//		// Obrigatório: Não; Tipo: Caracter; Max: 2
//		no.setAttribute("telefone", "");
//		// Obrigatório: Não; Tipo: Caracter; Max: 2
//		no.setAttribute("dddCelular", "");
//		// Obrigatório: Não; Tipo: Caracter; Max: 2
//		no.setAttribute("celular", "");

		// Obrigatório: Não; Tipo: Caracter; Max: 40
		if (Objects.nonNull(funcionario.getEmailPessoal()))
			no.setAttribute("email", formatStr(funcionario.getEmailPessoal(), 40));

		return no;
	}

	private static Node endereco(Document doc, Funcionario funcionario) {
		Element no = doc.createElement("endereco");

		// Obrigatório: Não; Tipo: Numérico; Max: 10
//		if (Objects.nonNull(funcionario.getEmailPessoal()))
//			no.setAttribute("tipoLogradouro", formatStr(funcionario.getEmailPessoal(), 40));

		// Obrigatório: Não; Tipo: Caracter; Max: 40
		if (Objects.nonNull(funcionario.getLogradouro()))
			no.setAttribute("logradouro", formatStr(funcionario.getLogradouro(), 40));

		// Obrigatório: Não; Tipo: Caracter; Max: 5
		if (Objects.nonNull(funcionario.getNumero()))
			no.setAttribute("numero", formatStr(funcionario.getNumero(), 5));

		// Obrigatório: Não; Tipo: Caracter; Max: 30
		if (Objects.nonNull(funcionario.getComplemento()))
			no.setAttribute("complemento", formatStr(funcionario.getComplemento(), 30));

		// Obrigatório: Não; Tipo: Caracter; Max: 30
		if (Objects.nonNull(funcionario.getBairro()))
			no.setAttribute("bairro", formatStr(funcionario.getBairro(), 30));

		// Obrigatório: Não; Tipo: Caracter; Max: 8
		if (Objects.nonNull(funcionario.getCep()))
			no.setAttribute("cep", formatStr(funcionario.getCep(), 8));

		// Obrigatório: Não; Tipo: Numérico; Max: 6
		if (Objects.nonNull(funcionario.getMunicipio()) && Objects.nonNull(funcionario.getMunicipio().getCodigoIbge()))
			no.setAttribute("municipio", formatStr(funcionario.getMunicipio().getCodigoIbge().toString(), 6));

		return no;
	}

	// TODO NÃO EXISTEM DADOS PARA PROVER OS CAMPOS ABAIXO.
//	private static Node certidaoObito(Document doc, Funcionario funcionario) {
//		Element no = doc.createElement("certidaoObito");
//
//		// Obrigatório: Não; Tipo: Numérico; Max: 15
//		if (Objects.nonNull(funcionario.getDadoCadastral())
//				&& Objects.nonNull(funcionario.getDadoCadastral().getNumeroProcessoAposentadoria()))
//			no.setAttribute("numeroTermoCertidao", formatStr(funcionario.getLogradouro(), 40));
//
//		// Obrigatório: Não; Tipo: Numérico; Max: 6
//		if (Objects.nonNull(funcionario.getLogradouro()))
//			no.setAttribute("numeroFolhaCertidao", formatStr(funcionario.getLogradouro(), 40));
//
//		// Obrigatório: Não; Tipo: Numérico; Max: 15
//		if (Objects.nonNull(funcionario.getLogradouro()))
//			no.setAttribute("numeroLivroCertidao", formatStr(funcionario.getLogradouro(), 40));
//
//		// Obrigatório: Não; Tipo: Data; Max: 10
//		if (Objects.nonNull(funcionario.getLogradouro()))
//			no.setAttribute("dataEmissaoCertidao", formatStr(funcionario.getLogradouro(), 40));
//
//		Element no2 = doc.createElement("cartorio");
//		no2.setAttribute("cnpj", "");
//		no2.setAttribute("nome", "");
//
//		Element no3 = doc.createElement("cartorioEndereco");
//		no3.setAttribute("bairro", "");
//		no3.setAttribute("cep", "");
//		no3.setAttribute("complemento", "");
//		no3.setAttribute("logradouro", "");
//		no3.setAttribute("municipio", "");
//		no3.setAttribute("numero", "");
//		no3.setAttribute("tipoLogradouro", "501");
//
//		no2.appendChild(no3);
//
//		no.appendChild(no2);
//
//		return no;
//	}

	// TODO 
//	private static Node representatividade(Document doc) {
//		Element no = doc.createElement("representatividade");
//
//		no.setAttribute("tipoRepresentatividade", "1");
//		no.setAttribute("dataInicio", "");
//		no.setAttribute("dataTerminoPrevisto", "");
//
//		Element no2 = doc.createElement("representanteLegal");
//		no2.setAttribute("dataEmissaoRG", "");
//		no2.setAttribute("dataNascimento", "");
//		no2.setAttribute("nome", "");
//		no2.setAttribute("numeroCPF", "");
//		no2.setAttribute("numeroRG", "");
//		no2.setAttribute("orgaoExpedidorRG", "1");
//		no2.setAttribute("ufRG", "");
//		no2.setAttribute("sexo", "M");
//
//		Element no3 = doc.createElement("representanteLegalEndereco");
//		no3.setAttribute("bairro", "");
//		no3.setAttribute("cep", "");
//		no3.setAttribute("complemento", "");
//		no3.setAttribute("logradouro", "");
//		no3.setAttribute("municipio", "");
//		no3.setAttribute("numero", "");
//		no3.setAttribute("tipoLogradouro", "501");
//
//		no2.appendChild(no3);
//
//		Element no4 = doc.createElement("representanteLegalContato");
//		no4.setAttribute("celular", "");
//		no4.setAttribute("dddCelular", "");
//		no4.setAttribute("dddTelefone", "");
//		no4.setAttribute("email", "");
//		no4.setAttribute("telefone", "");
//
//		no2.appendChild(no4);
//
//		no.appendChild(no2);
//
//		return no;
//	}

}
