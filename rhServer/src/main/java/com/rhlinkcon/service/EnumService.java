package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rhlinkcon.model.AcaoAuditoriaEnum;
import com.rhlinkcon.model.ArquivoRemessaPagamentoSituacaoEnum;
import com.rhlinkcon.model.ArrecadacaoAliquotaEncargoEnum;
import com.rhlinkcon.model.AvaliacaoDesempenhoItemFormaAvaliacaoEnum;
import com.rhlinkcon.model.AvaliacaoDesempenhoItemTipoQuestaoEnum;
import com.rhlinkcon.model.AvaliacaoDesempenhoModeloEnum;
import com.rhlinkcon.model.ClassificacaoCertidaoCompensacaoEnum;
import com.rhlinkcon.model.ColetivoEnum;
import com.rhlinkcon.model.CondicaoIsencaoEnum;
import com.rhlinkcon.model.ContagemTempoEspecialEnum;
import com.rhlinkcon.model.CorPeleEnum;
import com.rhlinkcon.model.DadoBancarioTipoContaEnum;
import com.rhlinkcon.model.DependenteTipoDependenteEnum;
import com.rhlinkcon.model.DestinacaoExterna;
import com.rhlinkcon.model.DirfTipoDeclaracaoEnum;
import com.rhlinkcon.model.EstadoCivilEnum;
import com.rhlinkcon.model.FaixaEnum;
import com.rhlinkcon.model.FeriasProgramacaoSituacaoEnum;
import com.rhlinkcon.model.FeriasProgramacaoTipoFeriasEnum;
import com.rhlinkcon.model.FeriasProgramacaoTipoProcessamentoEnum;
import com.rhlinkcon.model.FuncaoAcumulavelEnum;
import com.rhlinkcon.model.FuncaoCertidaoCompensacaoEnum;
import com.rhlinkcon.model.FuncaoDeclaracaoAposentadoriaEnum;
import com.rhlinkcon.model.FundoCertidaoCompensacaoEnum;
import com.rhlinkcon.model.FundoEnum;
import com.rhlinkcon.model.GeneroEnum;
import com.rhlinkcon.model.GrauDeficienciaEnum;
import com.rhlinkcon.model.GrauInstrucaoEnum;
import com.rhlinkcon.model.GrauParentescoEnum;
import com.rhlinkcon.model.IdentificacaoVerbaEnum;
import com.rhlinkcon.model.IndiceContribuicaoPrevidenciaEnum;
import com.rhlinkcon.model.MesAdiantamentoEnum;
import com.rhlinkcon.model.MesEnum;
import com.rhlinkcon.model.MotivoLeiEnum;
import com.rhlinkcon.model.MotivoPensionistaEnum;
import com.rhlinkcon.model.OperacaoContaBancariaEnum;
import com.rhlinkcon.model.OperadoresFormulaEnum;
import com.rhlinkcon.model.PeriodicidadeMesEnum;
import com.rhlinkcon.model.PorcentagemAdiantamentoEnum;
import com.rhlinkcon.model.RecadastramentoFiltroEnum;
import com.rhlinkcon.model.RecadastramentoTipoEnum;
import com.rhlinkcon.model.RecorrenciaEnum;
import com.rhlinkcon.model.SituacaoProcessamentoEnum;
import com.rhlinkcon.model.SituacaoProcessoEnum;
import com.rhlinkcon.model.SituacaoRequisicaoPessoalEnum;
import com.rhlinkcon.model.SituacaoSolAdiantamentoEnum;
import com.rhlinkcon.model.SituacaoTreinamentoSugeridoEnum;
import com.rhlinkcon.model.StatusCertidaoCompensacaoEnum;
import com.rhlinkcon.model.StatusDeclaracaoExServidorEnum;
import com.rhlinkcon.model.StatusFolhaEnum;
import com.rhlinkcon.model.StatusRelatorioFinanceiroEnum;
import com.rhlinkcon.model.StatusSituacaoCertidaoExSeguradoEnum;
import com.rhlinkcon.model.TipoAcaoPericiaMedicaEnum;
import com.rhlinkcon.model.TipoAnaliseEnum;
import com.rhlinkcon.model.TipoArredondamentoEnum;
import com.rhlinkcon.model.TipoAverbacaoEnum;
import com.rhlinkcon.model.TipoContaLotacaoEnum;
import com.rhlinkcon.model.TipoContratacaoEnum;
import com.rhlinkcon.model.TipoCotaEnum;
import com.rhlinkcon.model.TipoEstabilidadeEnum;
import com.rhlinkcon.model.TipoExoneracaoDemissaoEnum;
import com.rhlinkcon.model.TipoFamiliaEnum;
import com.rhlinkcon.model.TipoHistoricoSituacaoFuncionalEnum;
import com.rhlinkcon.model.TipoIncidenciaPrincipalPensaoEnum;
import com.rhlinkcon.model.TipoLotacaoEnum;
import com.rhlinkcon.model.TipoOperacaoEnum;
import com.rhlinkcon.model.TipoPensaoEnum;
import com.rhlinkcon.model.TipoRateioEnum;
import com.rhlinkcon.model.TipoReflexoEnum;
import com.rhlinkcon.model.TipoResponsavelEnum;
import com.rhlinkcon.model.TipoSanguineoEnum;
import com.rhlinkcon.model.TipoTelefoneEnum;
import com.rhlinkcon.model.TipoTempoEspecialEnum;
import com.rhlinkcon.model.TipoTreinamentoSugerido;
import com.rhlinkcon.model.TipoValorEnum;
import com.rhlinkcon.model.TipoVerba;
import com.rhlinkcon.payload.generico.EnumDto;
import com.rhlinkcon.payload.generico.SortByEnumLabel;

@Service
public class EnumService {

	public List<EnumDto> getAllEnums(String nomeEnum) {
		List<EnumDto> enumsResponses = new ArrayList<>();
		switch (nomeEnum) {
		case "DependenteTipoDependenteEnum":
			for (DependenteTipoDependenteEnum enu : DependenteTipoDependenteEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "DestinacaoExterna":
			for (DestinacaoExterna enu : DestinacaoExterna.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoVerba":
			for (TipoVerba enu : TipoVerba.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "IndiceContribuicaoPrevidenciaEnum":
			for (IndiceContribuicaoPrevidenciaEnum enu : IndiceContribuicaoPrevidenciaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoAverbacaoEnum":
			for (TipoAverbacaoEnum enu : TipoAverbacaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoValorEnum":
			for (TipoValorEnum enu : TipoValorEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "RecorrenciaEnum":
			for (RecorrenciaEnum enu : RecorrenciaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "SituacaoProcessoEnum":
			for (SituacaoProcessoEnum enu : SituacaoProcessoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoReflexoEnum":
			for (TipoReflexoEnum enu : TipoReflexoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "FeriasProgramacaoSituacaoEnum":
			for (FeriasProgramacaoSituacaoEnum enu : FeriasProgramacaoSituacaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "FeriasProgramacaoTipoFeriasEnum":
			for (FeriasProgramacaoTipoFeriasEnum enu : FeriasProgramacaoTipoFeriasEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "FeriasProgramacaoTipoProcessamentoEnum":
			for (FeriasProgramacaoTipoProcessamentoEnum enu : FeriasProgramacaoTipoProcessamentoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoPensao":
			for (TipoPensaoEnum enu : TipoPensaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "DadoBancarioTipoContaEnum":
			for (DadoBancarioTipoContaEnum enu : DadoBancarioTipoContaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoIncidenciaPrincipalPensaoEnum":
			for (TipoIncidenciaPrincipalPensaoEnum enu : TipoIncidenciaPrincipalPensaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoResponsavelEnum":
			for (TipoResponsavelEnum enu : TipoResponsavelEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoHistoricoSituacaoFuncionalEnum":
			for (TipoHistoricoSituacaoFuncionalEnum enu : TipoHistoricoSituacaoFuncionalEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoExoneracaoDemissaoEnum":
			for (TipoExoneracaoDemissaoEnum enu : TipoExoneracaoDemissaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "FaixaEnum":
			for (FaixaEnum enu : FaixaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoLotacaoEnum":
			for (TipoLotacaoEnum enu : TipoLotacaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoEstabilidadeEnum":
			for (TipoEstabilidadeEnum enu : TipoEstabilidadeEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "AvaliacaoDesempenhoModeloEnum":
			for (AvaliacaoDesempenhoModeloEnum enu : AvaliacaoDesempenhoModeloEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "AvaliacaoDesempenhoItemTipoQuestaoEnum":
			for (AvaliacaoDesempenhoItemTipoQuestaoEnum enu : AvaliacaoDesempenhoItemTipoQuestaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "AvaliacaoDesempenhoItemFormaAvaliacaoEnum":
			for (AvaliacaoDesempenhoItemFormaAvaliacaoEnum enu : AvaliacaoDesempenhoItemFormaAvaliacaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "MotivoLeiEnum":
			for (MotivoLeiEnum enu : MotivoLeiEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "FuncaoAcumulavelEnum":
			for (FuncaoAcumulavelEnum enu : FuncaoAcumulavelEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "ContagemTempoEspecialEnum":
			for (ContagemTempoEspecialEnum enu : ContagemTempoEspecialEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoContaLotacaoEnum":
			for (TipoContaLotacaoEnum enu : TipoContaLotacaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoArredondamentoEnum":
			for (TipoArredondamentoEnum enu : TipoArredondamentoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "StatusFolhaEnum":
			for (StatusFolhaEnum enu : StatusFolhaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "SituacaoRequisicaoPessoalEnum":
			for (SituacaoRequisicaoPessoalEnum enu : SituacaoRequisicaoPessoalEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoContratacaoEnum":
			for (TipoContratacaoEnum enu : TipoContratacaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoTreinamentoSugerido":
			for (TipoTreinamentoSugerido enu : TipoTreinamentoSugerido.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "SituacaoTreinamentoSugeridoEnum":
			for (SituacaoTreinamentoSugeridoEnum enu : SituacaoTreinamentoSugeridoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "PorcentagemAdiantamentoEnum":
			for (PorcentagemAdiantamentoEnum enu : PorcentagemAdiantamentoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "MesAdiantamentoEnum":
			for (MesAdiantamentoEnum enu : MesAdiantamentoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "SituacaoSolAdiantamentoEnum":
			for (SituacaoSolAdiantamentoEnum enu : SituacaoSolAdiantamentoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "OperadoresFormulaEnum":
			for (OperadoresFormulaEnum enu : OperadoresFormulaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "GeneroEnum":
			for (GeneroEnum enu : GeneroEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "EstadoCivilEnum":
			for (EstadoCivilEnum enu : EstadoCivilEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "GrauInstrucaoEnum":
			for (GrauInstrucaoEnum enu : GrauInstrucaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoFamiliaEnum":
			for (TipoFamiliaEnum enu : TipoFamiliaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "GrauParentescoEnum":
			for (GrauParentescoEnum enu : GrauParentescoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "MotivoPensionistaEnum":
			for (MotivoPensionistaEnum enu : MotivoPensionistaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoRateioEnum":
			for (TipoRateioEnum enu : TipoRateioEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoPensaoEnum":
			for (TipoPensaoEnum enu : TipoPensaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoCotaEnum":
			for (TipoCotaEnum enu : TipoCotaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "FuncaoDeclaracaoAposentadoriaEnum":
			for (FuncaoDeclaracaoAposentadoriaEnum enu : FuncaoDeclaracaoAposentadoriaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "CondicaoIsencaoEnum":
			for (CondicaoIsencaoEnum enu : CondicaoIsencaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "ClassificacaoCertidaoCompensacaoEnum":
			for (ClassificacaoCertidaoCompensacaoEnum enu : ClassificacaoCertidaoCompensacaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "StatusCertidaoCompensacaoEnum":
			for (StatusCertidaoCompensacaoEnum enu : StatusCertidaoCompensacaoEnum.values()) {
				if (!enu.equals(StatusCertidaoCompensacaoEnum.CRIADA)
						|| !enu.equals(StatusCertidaoCompensacaoEnum.EXCLUIDA)) {
					enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
				}
			}
			break;
		case "TipoTempoEspecialEnum":
			for (TipoTempoEspecialEnum enu : TipoTempoEspecialEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "GrauDeficienciaEnum":
			for (GrauDeficienciaEnum enu : GrauDeficienciaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "FuncaoCertidaoCompensacaoEnum":
			for (FuncaoCertidaoCompensacaoEnum enu : FuncaoCertidaoCompensacaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "FundoCertidaoCompensacaoEnum":
			for (FundoCertidaoCompensacaoEnum enu : FundoCertidaoCompensacaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "StatusSituacaoCertidaoExSeguradoEnum":
			for (StatusSituacaoCertidaoExSeguradoEnum enu : StatusSituacaoCertidaoExSeguradoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "StatusDeclaracaoExServidorEnum":
			for (StatusDeclaracaoExServidorEnum enu : StatusDeclaracaoExServidorEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "CorPeleEnum":
			for (CorPeleEnum enu : CorPeleEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoTelefoneEnum":
			for (TipoTelefoneEnum enu : TipoTelefoneEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "FundoEnum":
			for (FundoEnum enu : FundoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "RecadastramentoTipoEnum":
			for (RecadastramentoTipoEnum enu : RecadastramentoTipoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "RecadastramentoFiltroEnum":
			for (RecadastramentoFiltroEnum enu : RecadastramentoFiltroEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "ArquivoRemessaPagamentoSituacaoEnum":
			for (ArquivoRemessaPagamentoSituacaoEnum enu : ArquivoRemessaPagamentoSituacaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoOperacaoEnum":
			for (TipoOperacaoEnum enu : TipoOperacaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "StatusRelatorioFinanceiroEnum":
			for (StatusRelatorioFinanceiroEnum enu : StatusRelatorioFinanceiroEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "AcaoAuditoriaEnum":
			for (AcaoAuditoriaEnum enu : AcaoAuditoriaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "SituacaoProcessamentoFolha":
			for (SituacaoProcessamentoEnum enu : SituacaoProcessamentoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "MesEnum":
			for (MesEnum enu : MesEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "DirfTipoDeclaracaoEnum":
			for (DirfTipoDeclaracaoEnum enu : DirfTipoDeclaracaoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoSanguineoEnum":
			for (TipoSanguineoEnum enu : TipoSanguineoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;	
		case "OperacaoContaBancariaEnum":
			for (OperacaoContaBancariaEnum enu : OperacaoContaBancariaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "ArrecadacaoAliquotaEncargoEnum":
			for (ArrecadacaoAliquotaEncargoEnum enu : ArrecadacaoAliquotaEncargoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel(), enu.getOther()));
			}
			break;
		case "IdentificacaoVerbaEnum":
			for (IdentificacaoVerbaEnum enu : IdentificacaoVerbaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "ColetivoEnum":
			for (ColetivoEnum enu : ColetivoEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "PeriodicidadeMesEnum":
			for (PeriodicidadeMesEnum enu : PeriodicidadeMesEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoAnaliseEnum":
			for (TipoAnaliseEnum enu : TipoAnaliseEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		case "TipoAcaoPericiaMedicaEnum":
			for (TipoAcaoPericiaMedicaEnum enu : TipoAcaoPericiaMedicaEnum.values()) {
				enumsResponses.add(new EnumDto(enu.toString(), enu.getLabel()));
			}
			break;
		default:
			break;
		}
		
		if(!nomeEnum.equals("MesEnum") && !nomeEnum.equals("ArrecadacaoAliquotaEncargoEnum") && !nomeEnum.equals("PeriodicidadeMesEnum")) {
			Collections.sort(enumsResponses, new SortByEnumLabel());
		}

		return enumsResponses;
	}
}