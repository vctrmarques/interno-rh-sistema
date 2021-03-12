package com.rhlinkcon.payload.relatorioGerencial;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.audit.PersistentId;
import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.funcionario.FuncionarioDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelatorioGerencialDto {

	private DadoBasicoDto tipo;

	private FuncionarioDto funcionario;

	private long qtdFuncionario;

	private Double totalProvento;

	private Double totalDesconto;

	private Double totalLiquido;

	private Double valorMedio;

	private Double valor;

	private String tipoVerba;

	private String codigoVerba;

	private String descricaoVerba;

	private long qtdFuncionarioCompare;

	private Double totalProventoCompare;

	private Double totalDescontoCompare;

	private Double totalLiquidoCompare;

	private Double valorMedioCompare;

	private Double valorCompare;

	public RelatorioGerencialDto(Double totalProvento, Double totalDesconto, Double valorMedio, long qtdFuncionario) {
		this.totalProvento = totalProvento == null ? 0.0 : totalProvento;
		this.totalDesconto = totalDesconto == null ? 0.0 : totalDesconto;
		this.totalLiquido = this.totalProvento - this.totalDesconto;
		this.valorMedio = valorMedio;
		this.qtdFuncionario = qtdFuncionario;

	}

	public RelatorioGerencialDto(Double totalProvento, Double totalDesconto, Double valorMedio, long qtdFuncionario,
			PersistentId persistentId) {
		this.totalProvento = totalProvento == null ? 0.0 : totalProvento;
		this.totalDesconto = totalDesconto == null ? 0.0 : totalDesconto;
		this.totalLiquido = this.totalProvento - this.totalDesconto;
		this.valorMedio = valorMedio;
		this.qtdFuncionario = qtdFuncionario;
		this.tipo = new DadoBasicoDto(persistentId);

	}

	public RelatorioGerencialDto(Double totalProvento, Double totalDesconto, Double valorMedio, long qtdFuncionario,
			String tipo) {
		this.totalProvento = totalProvento == null ? 0.0 : totalProvento;
		this.totalDesconto = totalDesconto == null ? 0.0 : totalDesconto;
		this.totalLiquido = this.totalProvento - this.totalDesconto;
		this.valorMedio = valorMedio;
		this.qtdFuncionario = qtdFuncionario;
		this.tipo = new DadoBasicoDto();
		this.tipo.setDescricao(tipo);

	}

	public RelatorioGerencialDto(Contracheque contracheque) {
		this.totalProvento = contracheque.getTotalProventos() == null ? 0.0 : contracheque.getTotalProventos();
		this.totalDesconto = contracheque.getValorDesconto() == null ? 0.0 : contracheque.getValorDesconto();
		this.totalLiquido = this.totalProvento - this.totalDesconto;
		if (Objects.nonNull(contracheque.getFuncionario()))
			this.funcionario = FuncionarioDto.builder().id(contracheque.getFuncionario().getId())
					.nome(contracheque.getNome()).matricula(contracheque.getMatricula())
					.lotacao(contracheque.getLotacao()).tipoSituacaoFuncional(contracheque.getTipoSituacaoFuncional())
					.build();

	}

	public RelatorioGerencialDto(Double valor, long qtdFuncionario, Verba verba) {
		this.valor = valor;
		this.tipoVerba = verba.getTipoVerba().toString();
		this.qtdFuncionario = qtdFuncionario;
		this.codigoVerba = verba.getCodigo();
		this.descricaoVerba = verba.getDescricaoVerba();

	}

	public void loadDataCompare(RelatorioGerencialDto relGerCompare) {
		this.qtdFuncionarioCompare = relGerCompare.getQtdFuncionario();
		this.totalProventoCompare = relGerCompare.getTotalProvento();
		this.totalDescontoCompare = relGerCompare.getTotalDesconto();
		this.totalLiquidoCompare = relGerCompare.getTotalLiquido();
		this.valorMedioCompare = relGerCompare.getValorMedio();
		this.valorCompare = relGerCompare.getValor();

	}

}
