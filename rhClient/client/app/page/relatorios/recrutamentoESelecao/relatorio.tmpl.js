function getDocDefinitionRecrutamentoESelecao(content, dataURL) {
    var docDefinition = {};

    docDefinition.content = [];
    docDefinition.styles = [];

    var brasao = {
        image: dataURL,
        width: 70,
        alignment: 'center'
    };
    docDefinition.content.push(brasao);

    var orgao = {
        text: 'RH',
        alignment: 'center',
        margin: [0, 10],
        bold: true
    };
    docDefinition.content.push(orgao);

    var titulo = {
        text: 'Relatório de Recrutamento e Seleção',
        alignment: 'center',
    };
    docDefinition.content.push(titulo);
    docDefinition.content.push('\n\n\n');

    if (content.dataInicio != undefined || content.dataFim != undefined) {
        var periodo = {
            text: 'Período',
            alignment: 'center',
            style: 'tituloTopico'
        };
        docDefinition.content.push(periodo);

        var dataInicio = moment(content.dataInicio).format("DD/MM/YYYY");
        var dataFim = moment(content.dataInicio).format("DD/MM/YYYY");

        var conteudo = {
            text: `${dataInicio} à ${dataFim}`,
            alignment: 'center',
            style: 'datas'
        };
        docDefinition.content.push(conteudo);
    }

    docDefinition.content.push('\n\n\n');

    docDefinition.content.push({
        text: 'Tempo de Atendimento',
        alignment: 'center',
        style: 'tituloTopico'
    });

    docDefinition.content.push({
        alignment: 'justify',
        columns: [
            { text: 'Concluído antes da data limite:' },
            { text: `${content.totalConcluidosAntes}` }
        ]
    });

    docDefinition.content.push({
        alignment: 'justify',
        columns: [
            { text: 'Concluído na data limite:' },
            { text: `${content.totalConcluidosNoLimite}` }
        ]
    });

    docDefinition.content.push({
        alignment: 'justify',
        columns: [
            { text: 'Concluído depois da data limite:' },
            { text: `${content.totalConcluidosApos}` }
        ]
    });

    var mostrarRelatorioAnalitico = content.tipoSintetico;
    if (mostrarRelatorioAnalitico) {
        docDefinition.content.push('\n\n\n');

        docDefinition.content.push({
            text: 'Modelos de Relatórios',
            alignment: 'center',
            style: 'tituloTopico'
        });
        docDefinition.content.push('\n\n');

        docDefinition.content.push({
            alignment: 'justify',
            columns: [
                { text: 'Total de Processos (Concl, Susp, Cancel):' },
                { text: `${content.totalProcessos} Processos` }
            ]
        });
        docDefinition.content.push('\n');

        docDefinition.content.push({
            alignment: 'justify',
            columns: [
                { text: 'Tempo de Atendimento as Vagas:' },
                { text: `${content.mediaDiasTempoAtendimento} dias` }
            ]
        });
        docDefinition.content.push('\n',);

        docDefinition.content.push({
            alignment: 'justify',
            columns: [
                { text: 'Vagas em Aberto:' },
                { text: `${content.totalVagasAbertas} Vagas (até o momento)` }
            ]
        });
        docDefinition.content.push('\n');

        docDefinition.content.push({
            alignment: 'justify',
            columns: [
                { text: 'Efetivadas após controle de experiência:' },
                { text: `${content.totalEfetivadosAposContratoExperiencia} efetivados` }
            ]
        });
    }

    docDefinition.content.push('\n\n\n\n\n\n\n\n\n\n');
    docDefinition.content.push({
        text: '----------------------------------------------------------------------------',
        alignment: 'center'
    });

    docDefinition.content.push({
        text: 'Responsável RH',
        alignment: 'center'
    });

    docDefinition.styles.push({
        header: {
            fontSize: 26,
            bold: true
        },
        tituloTopico: {
            fontSize: 18
        },
        datas: {
            fontSize: 9
        }
    });

    return docDefinition;
}
