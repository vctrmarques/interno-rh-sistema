package com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento;

import com.rhlinkcon.model.RelatorioFinanceiroFolhaPagamentoHistorico;
import com.rhlinkcon.payload.auditoria.AuditoriaResponse;
import com.rhlinkcon.util.Projecao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RelatorioFinanceiroFolhaPagamentoHistoricoResponse extends AuditoriaResponse {

	private Long id;

	private RelatorioFinanceiroFolhaPagamentoResponse relatorioFinanceiroFolhaPagamento;

	private String status;

	public RelatorioFinanceiroFolhaPagamentoHistoricoResponse(
			RelatorioFinanceiroFolhaPagamentoHistorico financeiroFolhaPagamentoHistorico, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.id = financeiroFolhaPagamentoHistorico.getId();
			this.status = financeiroFolhaPagamentoHistorico.getStatus().toString();
			this.setCriadoEm(financeiroFolhaPagamentoHistorico.getCreatedAt());
			if(projecao.equals(Projecao.COMPLETA)){
				this.relatorioFinanceiroFolhaPagamento = new RelatorioFinanceiroFolhaPagamentoResponse(financeiroFolhaPagamentoHistorico.getRelatorioFinanceiroFolhaPagamento(), Projecao.BASICA);
			}
		}
	}

}
