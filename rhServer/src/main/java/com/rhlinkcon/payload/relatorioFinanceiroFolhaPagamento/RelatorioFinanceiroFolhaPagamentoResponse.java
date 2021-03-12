package com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento;

import java.util.List;
import java.util.stream.Collectors;

import com.rhlinkcon.model.FolhaCompetencia;
import com.rhlinkcon.model.RelatorioFinanceiroFolhaPagamento;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.folhaCompetencia.FolhaCompetenciaResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalResponse;
import com.rhlinkcon.util.Projecao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RelatorioFinanceiroFolhaPagamentoResponse {

	private Long id;

	private String descricao;

	private AnexoResponse anexo;

	private RelatorioFinanceiroFolhaPagamentoHistoricoResponse historicoAtual;

	private List<RelatorioFinanceiroFolhaPagamentoHistoricoResponse> historicos;

	private List<EmpresaFilialResponse> filiais;
	
	private FolhaCompetenciaResponse folhaCompetencia;
	
	private String filiaisConcat;
	
	private List<SituacaoFuncionalResponse> situacoesFuncionais;
	
	private String situacoesFuncionaisConcat;

	private List<LotacaoResponse> lotacoes;
	
	private String lotacoesConcat;

	public RelatorioFinanceiroFolhaPagamentoResponse(RelatorioFinanceiroFolhaPagamento financeiroFolhaPagamento,
			Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {

			this.id = financeiroFolhaPagamento.getId();
			this.descricao = financeiroFolhaPagamento.getDescricao();
			this.anexo = new AnexoResponse(financeiroFolhaPagamento.getAnexo());
			this.historicoAtual = new RelatorioFinanceiroFolhaPagamentoHistoricoResponse(
					financeiroFolhaPagamento.getHistoricoAtual(), Projecao.BASICA);
			this.filiaisConcat = financeiroFolhaPagamento.getFiliais().stream().map(f -> f.getNomeFilial()).collect(Collectors.joining(", "));
			this.situacoesFuncionaisConcat = financeiroFolhaPagamento.getSituacoesFuncionais().stream().map(s -> s.getDescricao()).collect(Collectors.joining(", "));
			this.lotacoesConcat = financeiroFolhaPagamento.getLotacoes().stream().map(l -> l.getDescricao()).collect(Collectors.joining(", "));
			this.folhaCompetencia = new FolhaCompetenciaResponse(financeiroFolhaPagamento.getFolhaCompetencia());
			if (projecao.equals(Projecao.COMPLETA)) {
				this.filiais = financeiroFolhaPagamento.getFiliais().stream().map(filial -> new EmpresaFilialResponse(filial, Projecao.BASICA)).collect(Collectors.toList());
				this.situacoesFuncionais = financeiroFolhaPagamento.getSituacoesFuncionais().stream().map(situacaoFuncional -> new SituacaoFuncionalResponse(situacaoFuncional, Projecao.BASICA)).collect(Collectors.toList());
				this.lotacoes = financeiroFolhaPagamento.getLotacoes().stream().map(lotacao -> new LotacaoResponse(lotacao, Projecao.BASICA)).collect(Collectors.toList());
			}
		}
	}
}
