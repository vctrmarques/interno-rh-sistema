package com.rhlinkcon.payload.batimentoFolhaPagamento;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.Lancamento;
import com.rhlinkcon.payload.pensionista.PensionistaResponse;
import com.rhlinkcon.util.Projecao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatimentoDadosFolhaDto {

	private String matricula;

	private String nome;

	private String lotacao;

	private String cargo;

	private String funcao;

	private String situacaoFuncional;

	private String dataAdmissao;

	private String codigoBanco;

	private String agencia;

	private String numeroConta;

	private String digitoConta;

	private PensionistaResponse pensionista;

	private HashMap<String, Double> listProventos;

	private HashMap<String, Double> listDescontos;

	private Double totalProventos;

	private Double totalDescontos;

	private Double totalLiquido;

	public BatimentoDadosFolhaDto() {
		this.setTotalProventos(0.0);
		this.setTotalLiquido(0.0);
		this.setTotalDescontos(0.0);
		this.setListProventos(new HashMap<String, Double>());
		this.setListDescontos(new HashMap<String, Double>());
	}

	public BatimentoDadosFolhaDto(Contracheque e) {
		this.matricula = check(e.getMatricula());
		this.nome = check(e.getNome());
		this.lotacao = check(e.getLotacao());
		this.cargo = check(e.getCargoEfetivo());
		this.funcao = check(e.getCargoFuncao());
		this.situacaoFuncional = check(e.getTipoSituacaoFuncional());
		this.dataAdmissao = dataInstant("dd/MM/yyyy", e.getDataAdmissao());

		if (Objects.nonNull(e.getFuncionario())) {
			if (Objects.nonNull(e.getFuncionario().getAgenciaBancaria())) {
				this.codigoBanco = check(e.getFuncionario().getAgenciaBancaria().getBanco().getCodigo());
			} else {
				this.codigoBanco = check(e.getFuncionario().getBanco().getCodigo());
			}
			if (Objects.nonNull(e.getFuncionario().getAgenciaBancaria())) {
				this.agencia = check(e.getFuncionario().getAgenciaBancaria().getNumero());
			} else {
				this.agencia = check(e.getFuncionario().getAgencia());
			}
			this.numeroConta = check(e.getFuncionario().getNumeroConta());
			this.digitoConta = check(e.getFuncionario().getDigitoConta());
		} else {
			// TODO
		}

		if (Objects.nonNull(e.getPensionista())) {
			this.pensionista = new PensionistaResponse(e.getPensionista(), Projecao.BASICA);
		}
		this.listProventos = valores(e.getLancamentoProventos());
		this.listDescontos = valores(e.getLancamentoDescontos());
		this.totalProventos = e.getTotalProventos();
		this.totalDescontos = e.getValorDesconto();
		this.totalLiquido = e.getValorLiquido();
	}

	private HashMap<String, Double> valores(List<Lancamento> lista) {
		HashMap<String, Double> resultado = new HashMap<String, Double>();
		for (Lancamento v : lista) {
			String key = v.getVerba().getCodigo() + " - " + v.getVerba().getDescricaoResumida();
			resultado.put(key, v.getValor());
		}
		return resultado;
	}

	private String check(String valor) {
		return Objects.nonNull(valor) ? valor : "";
	}

	private String check(Integer valor) {
		return Objects.nonNull(valor) ? valor.toString() : "";
	}

	private String dataInstant(String valor, Instant data) {
		SimpleDateFormat formato = new SimpleDateFormat(valor);
		Date result = Date.from(data);
		return formato.format(result);
	}

}
