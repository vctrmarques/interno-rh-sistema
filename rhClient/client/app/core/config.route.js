(function () {
    'use strict';

    angular.module('app')
        .config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider', '$windowProvider', '$httpProvider',
            function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $windowProvider, $httpProvider) {
                var routes, setRoutes;

                routes = [
                    'ui/cards', 'ui/typography', 'ui/buttons', 'ui/icons', 'ui/grids', 'ui/widgets', 'ui/components', 'ui/timeline', 'ui/lists', 'ui/pricing-tables',
                    'table/static', 'table/responsive', 'table/data',
                    'form/elements', 'form/layouts', 'form/validation',
                    'chart/echarts', 'chart/echarts-line', 'chart/echarts-bar', 'chart/echarts-pie', 'chart/echarts-scatter', 'chart/echarts-more',
                    'page/404', 'page/403', 'page/500', 'page/blank', 'page/forgot-password', 'page/invoice', 'page/home', 'page/lock-screen', 'page/signup',
                    'app/calendar'
                ]

                setRoutes = function (route) {
                    var config, url;
                    url = '/' + route;
                    config = {
                        url: url,
                        templateUrl: 'app/' + route + '.html'
                    };
                    $stateProvider.state(route, config);
                    return $stateProvider;
                };

                routes.forEach(function (route) {
                    return setRoutes(route);
                });

                $httpProvider.interceptors.push('AuthInterceptor');

                $stateProvider

                    //ROTA PARA LOGIN
                    .state('login', {
                        url: '/login',
                        templateUrl: 'app/page/signin.html'
                    })
                    //ROTA PARA PROFILE
                    .state('perfil', {
                        url: '/perfil',
                        templateUrl: 'app/page/profile.html'
                    })

                    //ROTA PARA NOTIFICACAO
                    .state('notificacoes', {
                        url: '/notificacoes',
                        templateUrl: 'app/page/notificacao/notificacao.html'
                    })

                    //ROTA PARA AREA DE FORMAÇÃO
                    .state('areaFormacaoGestao', {
                        url: '/areaFormacao/gestao',
                        templateUrl: 'app/page/areaFormacao/areaFormacao.html'
                    })
                    .state('areaFormacaoFormulario', {
                        url: '/areaFormacao/formulario',
                        templateUrl: 'app/page/areaFormacao/areaFormacao.form.html'
                    })
                    .state('areaFormacaoFormularioEdit', {
                        url: '/areaFormacao/formulario/:id',
                        templateUrl: 'app/page/areaFormacao/areaFormacao.form.html'
                    })
                    .state('areaFormacaoFormularioDetalhes', {
                        url: '/areaFormacao/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/areaFormacao/areaFormacao.form.html'
                    })

                    //ROTA PARA TEMPO DE SERVIÇO
                    .state('tempoServicoGestao', {
                        url: '/tempoServico/gestao',
                        templateUrl: 'app/page/tempoServico/tempoServico.html'
                    })
                    .state('tempoServicoFormulario', {
                        url: '/tempoServico/formulario',
                        templateUrl: 'app/page/tempoServico/tempoServico.form.html'
                    })
                    .state('tempoServicoFormularioEdit', {
                        url: '/tempoServico/formulario/:id',
                        templateUrl: 'app/page/tempoServico/tempoServico.form.html'
                    })
                    .state('tempoServicoFormularioDetalhes', {
                        url: '/tempoServico/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/tempoServico/tempoServico.form.html'
                    })

                    //ROTA PARA HISTORICO CONTABIL
                    .state('historicoContabilGestao', {
                        url: '/historicoContabil/gestao',
                        templateUrl: 'app/page/historicoContabil/historicoContabil.html'
                    })
                    .state('historicoContabilFormulario', {
                        url: '/historicoContabil/formulario',
                        templateUrl: 'app/page/historicoContabil/historicoContabil.form.html'
                    })
                    .state('historicoContabilFormularioEdit', {
                        url: '/historicoContabil/formulario/:id',
                        templateUrl: 'app/page/historicoContabil/historicoContabil.form.html'
                    })
                    .state('historicoContabilFormularioDetalhes', {
                        url: '/historicoContabil/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/historicoContabil/historicoContabil.form.html'
                    })

                    //ROTA PARA REQUISITO
                    .state('requisitoGestao', {
                        url: '/requisito/gestao',
                        templateUrl: 'app/page/requisito/requisito.html'
                    })
                    .state('requisitoFormulario', {
                        url: '/requisito/formulario',
                        templateUrl: 'app/page/requisito/requisito.form.html'
                    })
                    .state('requisitoFormularioEdit', {
                        url: '/requisito/formulario/:id',
                        templateUrl: 'app/page/requisito/requisito.form.html'
                    })
                    .state('requisitoFormularioDetalhes', {
                        url: '/requisito/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/requisito/requisito.form.html'
                    })

                    //ROTA PARA EMPRESA FILIAL
                    .state('empresaFilialGestao', {
                        url: '/empresaFilial/gestao',
                        templateUrl: 'app/page/empresaFilial/empresaFilial.html'
                    })
                    .state('empresaFilialFormulario', {
                        url: '/empresaFilial/formulario',
                        templateUrl: 'app/page/empresaFilial/empresaFilial.form.html'
                    })
                    .state('empresaFilialFormularioEdit', {
                        url: '/empresaFilial/formulario/:id',
                        templateUrl: 'app/page/empresaFilial/empresaFilial.form.html'
                    })
                    .state('empresaFilialFormularioDetalhes', {
                        url: '/empresaFilial/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/empresaFilial/empresaFilial.form.html'
                    })

                    //ROTA PARA AREA DE NACIONALIDADE
                    .state('nacionalidadeGestao', {
                        url: '/nacionalidade/gestao',
                        templateUrl: 'app/page/nacionalidade/nacionalidade.html'
                    })
                    .state('nacionalidadeFormulario', {
                        url: '/nacionalidade/formulario',
                        templateUrl: 'app/page/nacionalidade/nacionalidade.form.html'
                    })
                    .state('nacionalidadeFormularioEdit', {
                        url: '/nacionalidade/formulario/:id',
                        templateUrl: 'app/page/nacionalidade/nacionalidade.form.html'
                    })
                    .state('nacionalidadeFormularioDetalhes', {
                        url: '/nacionalidade/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/nacionalidade/nacionalidade.form.html'
                    })

                    //ROTA PARA HISTORICO DE AJUSTE DE NIVEL SALARIAL
                    .state('nivelSalarialHistoricoGestao', {
                        url: '/nivelSalarialHistorico/gestao',
                        templateUrl: 'app/page/nivelSalarialHistorico/nivelSalarialHistorico.html'
                    })
                    .state('nivelSalarialHistoricoDetalhe', {
                        url: '/nivelSalarialHistorico/detalhe/:nivelSalarialId',
                        templateUrl: 'app/page/nivelSalarialHistorico/nivelSalarialHistorico.detalhe.html'
                    })

                    //ROTA PARA TOMADOR DE SERVIÇO
                    .state('tomadorServicoGestao', {
                        url: '/tomadorServico/gestao',
                        templateUrl: 'app/page/tomadorServico/tomadorServico.html'
                    })
                    .state('tomadorServicoFormulario', {
                        url: '/tomadorServico/formulario',
                        templateUrl: 'app/page/tomadorServico/tomadorServico.form.html'
                    })
                    .state('tomadorServicoFormularioEdit', {
                        url: '/tomadorServico/formulario/:id',
                        templateUrl: 'app/page/tomadorServico/tomadorServico.form.html'
                    })
                    .state('tomadorServicoFormularioDetalhes', {
                        url: '/tomadorServico/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/tomadorServico/tomadorServico.form.html'
                    })

                    //ROTA PARA COMPENSACAO
                    .state('compensacaoFormulario', {
                        url: '/compensacao/formulario',
                        templateUrl: 'app/page/compensacao/compensacao.form.html'
                    })
                    .state('compensacaoFormularioEdit', {
                        url: '/compensacao/formulario/:id',
                        templateUrl: 'app/page/compensacao/compensacao.form.html'
                    })
                    .state('compensacaoFormularioDetalhes', {
                        url: '/compensacao/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/compensacao/compensacao.form.html'
                    })

                    //ROTA PARA DADO CADASTRAL COMPLEMENTAR
                    .state('dadoCadastralComplementarGestao', {
                        url: '/dadoCadastralComplementar/gestao',
                        templateUrl: 'app/page/dadoCadastralComplementar/dadoCadastralComplementar.html'
                    })
                    .state('dadoCadastralComplementarFormulario', {
                        url: '/dadoCadastralComplementar/formulario',
                        templateUrl: 'app/page/dadoCadastralComplementar/dadoCadastralComplementar.form.html'
                    })
                    .state('dadoCadastralComplementarFormularioEdit', {
                        url: '/dadoCadastralComplementar/formulario/:id',
                        templateUrl: 'app/page/dadoCadastralComplementar/dadoCadastralComplementar.form.html'
                    })
                    .state('dadoCadastralComplementarFormularioDetalhes', {
                        url: '/dadoCadastralComplementar/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/dadoCadastralComplementar/dadoCadastralComplementar.form.html'
                    })

                    //ROTA PARA SITUAÇÃO FUNCIONAL
                    .state('historicoSituacaoFuncionalGestao', {
                        url: '/historicoSituacaoFuncional/gestao',
                        templateUrl: 'app/page/historicoSituacaoFuncional/historicoSituacaoFuncional.html'
                    })
                    .state('historicoSituacaoFuncionalFormulario', {
                        url: '/historicoSituacaoFuncional/formulario',
                        templateUrl: 'app/page/historicoSituacaoFuncional/historicoSituacaoFuncional.form.html'
                    })
                    .state('historicoSituacaoFuncionalFormularioAdd', {
                        url: '/historicoSituacaoFuncional/insercao/:id/:add',
                        templateUrl: 'app/page/historicoSituacaoFuncional/historicoSituacaoFuncional.form.html'
                    })
                    .state('historicoSituacaoFuncionalFormularioDetalhes', {
                        url: '/historicoSituacaoFuncional/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/historicoSituacaoFuncional/historicoSituacaoFuncional.form.html'
                    })

                    //ROTA PARA AREA DE MUNICÍPIO
                    .state('municipioGestao', {
                        url: '/municipio/gestao',
                        templateUrl: 'app/page/municipio/municipio.html'
                    })
                    .state('municipioFormulario', {
                        url: '/municipio/formulario',
                        templateUrl: 'app/page/municipio/municipio.form.html'
                    })
                    .state('municipioFormularioEdit', {
                        url: '/municipio/formulario/:id',
                        templateUrl: 'app/page/municipio/municipio.form.html'
                    })
                    .state('municipioFormularioDetalhes', {
                        url: '/municipio/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/municipio/municipio.form.html'
                    })

                    //ROTA PARA LOTACAO
                    .state('lotacaoGestao', {
                        url: '/lotacao/gestao',
                        templateUrl: 'app/page/lotacao/lotacao.html'
                    })
                    .state('lotacaoFormulario', {
                        url: '/lotacao/formulario',
                        templateUrl: 'app/page/lotacao/lotacao.form.html'
                    })
                    .state('lotacaoFormularioEdit', {
                        url: '/lotacao/formulario/:id',
                        templateUrl: 'app/page/lotacao/lotacao.form.html'
                    })
                    .state('lotacaoFormularioDetalhes', {
                        url: '/lotacao/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/lotacao/lotacao.form.html'
                    })

                    //ROTA PARA ATIVIDADE
                    .state('atividadeGestao', {
                        url: '/atividade/gestao',
                        templateUrl: 'app/page/atividade/atividade.html'
                    })
                    .state('atividadeFormulario', {
                        url: '/atividade/formulario',
                        templateUrl: 'app/page/atividade/atividade.form.html'
                    })
                    .state('atividadeFormularioEdit', {
                        url: '/atividade/formulario/:id',
                        templateUrl: 'app/page/atividade/atividade.form.html'
                    })
                    .state('atividadeFormularioDetalhes', {
                        url: '/atividade/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/atividade/atividade.form.html'
                    })

                    //ROTA PARA CATEGORIA ECONÔMICA
                    .state('categoriaEconomicaGestao', {
                        url: '/categoriaEconomica/gestao',
                        templateUrl: 'app/page/categoriaEconomica/categoriaEconomica.html'
                    })
                    .state('categoriaEconomicaFormulario', {
                        url: '/categoriaEconomica/formulario',
                        templateUrl: 'app/page/categoriaEconomica/categoriaEconomica.form.html'
                    })
                    .state('categoriaEconomicaFormularioEdit', {
                        url: '/categoriaEconomica/formulario/:id',
                        templateUrl: 'app/page/categoriaEconomica/categoriaEconomica.form.html'
                    })
                    .state('categoriaEconomicaFormularioDetalhes', {
                        url: '/categoriaEconomica/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/categoriaEconomica/categoriaEconomica.form.html'
                    })

                    //ROTA PARA CENTRO DE CUSTO
                    .state('centroCustoGestao', {
                        url: '/centroCusto/gestao',
                        templateUrl: 'app/page/centroCusto/centroCusto.html'
                    })
                    .state('centroCustoFormulario', {
                        url: '/centroCusto/formulario',
                        templateUrl: 'app/page/centroCusto/centroCusto.form.html'
                    })
                    .state('centroCustoFormularioEdit', {
                        url: '/centroCusto/formulario/:id',
                        templateUrl: 'app/page/centroCusto/centroCusto.form.html'
                    })
                    .state('centroCustoFormularioDetalhes', {
                        url: '/centroCusto/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/centroCusto/centroCusto.form.html'
                    })

                    //ROTA PARA CLASSIFICAÇÃO AGENTE NOCIVO
                    .state('classificacaoAgenteNocivoGestao', {
                        url: '/classificacaoAgenteNocivo/gestao',
                        templateUrl: 'app/page/classificacaoAgenteNocivo/classificacaoAgenteNocivo.html'
                    })
                    .state('classificacaoAgenteNocivoFormulario', {
                        url: '/classificacaoAgenteNocivo/formulario',
                        templateUrl: 'app/page/classificacaoAgenteNocivo/classificacaoAgenteNocivo.form.html'
                    })
                    .state('classificacaoAgenteNocivoFormularioEdit', {
                        url: '/classificacaoAgenteNocivo/formulario/:id',
                        templateUrl: 'app/page/classificacaoAgenteNocivo/classificacaoAgenteNocivo.form.html'
                    })
                    .state('classificacaoAgenteNocivoFormularioDetalhes', {
                        url: '/classificacaoAgenteNocivo/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/classificacaoAgenteNocivo/classificacaoAgenteNocivo.form.html'
                    })

                    //ROTA PARA CONTA CONTABIL SIMPLES
                    .state('contaContabilSimplesGestao', {
                        url: '/contaContabilSimples/gestao',
                        templateUrl: 'app/page/contaContabilSimples/contaContabilSimples.html'
                    })
                    .state('contaContabilSimplesFormulario', {
                        url: '/contaContabilSimples/formulario',
                        templateUrl: 'app/page/contaContabilSimples/contaContabilSimples.form.html'
                    })
                    .state('contaContabilSimplesFormularioEdit', {
                        url: '/contaContabilSimples/formulario/:id',
                        templateUrl: 'app/page/contaContabilSimples/contaContabilSimples.form.html'
                    })
                    .state('contaContabilSimplesFormularioDetalhes', {
                        url: '/contaContabilSimples/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/contaContabilSimples/contaContabilSimples.form.html'
                    })

                    //ROTA PARA CONVÊNIOS
                    .state('convenioGestao', {
                        url: '/convenio/gestao',
                        templateUrl: 'app/page/convenio/convenio.html'
                    })
                    .state('convenioFormulario', {
                        url: '/convenio/formulario',
                        templateUrl: 'app/page/convenio/convenio.form.html'
                    })
                    .state('convenioFormularioEdit', {
                        url: '/convenio/formulario/:id',
                        templateUrl: 'app/page/convenio/convenio.form.html'
                    })
                    .state('convenioFormularioDetalhes', {
                        url: '/convenio/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/convenio/convenio.form.html'
                    })

                    //ROTA PARA EQUIPAMENTOS DE PROTEÇÃO INDIVIDUAL
                    .state('equipamentoProtecaoIndividualGestao', {
                        url: '/equipamentoProtecaoIndividual/gestao',
                        templateUrl: 'app/page/equipamentoProtecaoIndividual/equipamentoProtecaoIndividual.html'
                    })
                    .state('equipamentoProtecaoIndividualFormulario', {
                        url: '/equipamentoProtecaoIndividual/formulario',
                        templateUrl: 'app/page/equipamentoProtecaoIndividual/equipamentoProtecaoIndividual.form.html'
                    })
                    .state('equipamentoProtecaoIndividualFormularioEdit', {
                        url: '/equipamentoProtecaoIndividual/formulario/:id',
                        templateUrl: 'app/page/equipamentoProtecaoIndividual/equipamentoProtecaoIndividual.form.html'
                    })
                    .state('equipamentoProtecaoIndividualFormularioDetalhes', {
                        url: '/equipamentoProtecaoIndividual/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/equipamentoProtecaoIndividual/equipamentoProtecaoIndividual.form.html'
                    })

                    //ROTA PARA SITUAÇÃO FUNCIONAL
                    .state('situacaoFuncionalGestao', {
                        url: '/situacaoFuncional/gestao',
                        templateUrl: 'app/page/situacaoFuncional/situacaoFuncional.html'
                    })
                    .state('situacaoFuncionalFormulario', {
                        url: '/situacaoFuncional/formulario',
                        templateUrl: 'app/page/situacaoFuncional/situacaoFuncional.form.html'
                    })
                    .state('situacaoFuncionalFormularioEdit', {
                        url: '/situacaoFuncional/formulario/:id',
                        templateUrl: 'app/page/situacaoFuncional/situacaoFuncional.form.html'
                    })
                    .state('situacaoFuncionalFormularioDetalhes', {
                        url: '/situacaoFuncional/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/situacaoFuncional/situacaoFuncional.form.html'
                    })

                    //ROTA PARA MOTIVO AFASTAMENTO
                    .state('motivoAfastamentoGestao', {
                        url: '/motivoAfastamento/gestao',
                        templateUrl: 'app/page/motivoAfastamento/motivoAfastamento.html'
                    })
                    .state('motivoAfastamentoFormulario', {
                        url: '/motivoAfastamento/formulario',
                        templateUrl: 'app/page/motivoAfastamento/motivoAfastamento.form.html'
                    })
                    .state('motivoAfastamentoFormularioEdit', {
                        url: '/motivoAfastamento/formulario/:id',
                        templateUrl: 'app/page/motivoAfastamento/motivoAfastamento.form.html'
                    })
                    .state('motivoAfastamentoFormularioDetalhes', {
                        url: '/motivoAfastamento/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/motivoAfastamento/motivoAfastamento.form.html'
                    })

                    //ROTA PARA CONSIGNADO
                    .state('consignadoGestao', {
                        url: '/consignado/gestao',
                        templateUrl: 'app/page/consignado/consignado.html'
                    })
                    .state('consignadoFormulario', {
                        url: '/consignado/formulario',
                        templateUrl: 'app/page/consignado/consignado.form.html'
                    })
                    .state('consignadoFormularioEdit', {
                        url: '/consignado/formulario/:id',
                        templateUrl: 'app/page/consignado/consignado.form.html'
                    })
                    .state('consignadoFormularioDetalhes', {
                        url: '/consignado/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/consignado/consignado.form.html'
                    })

                    //ROTA PARA EVENTO
                    .state('eventoGestao', {
                        url: '/evento/gestao',
                        templateUrl: 'app/page/evento/evento.html'
                    })
                    .state('eventoFormulario', {
                        url: '/evento/formulario',
                        templateUrl: 'app/page/evento/evento.form.html'
                    })
                    .state('eventoFormularioEdit', {
                        url: '/evento/formulario/:id',
                        templateUrl: 'app/page/evento/evento.form.html'
                    })
                    .state('eventoFormularioDetalhes', {
                        url: '/evento/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/evento/evento.form.html'
                    })

                    //ROTA PARA EQUIPAMENTOS DE PROTEÇÃO COLETIVA
                    .state('equipamentoProtecaoColetivaGestao', {
                        url: '/equipamentoProtecaoColetiva/gestao',
                        templateUrl: 'app/page/equipamentoProtecaoColetiva/equipamentoProtecaoColetiva.html'
                    })
                    .state('equipamentoProtecaoColetivaFormulario', {
                        url: '/equipamentoProtecaoColetiva/formulario',
                        templateUrl: 'app/page/equipamentoProtecaoColetiva/equipamentoProtecaoColetiva.form.html'
                    })
                    .state('equipamentoProtecaoColetivaFormularioEdit', {
                        url: '/equipamentoProtecaoColetiva/formulario/:id',
                        templateUrl: 'app/page/equipamentoProtecaoColetiva/equipamentoProtecaoColetiva.form.html'
                    })
                    .state('equipamentoProtecaoColetivaFormularioDetalhes', {
                        url: '/equipamentoProtecaoColetiva/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/equipamentoProtecaoColetiva/equipamentoProtecaoColetiva.form.html'
                    })

                    //ROTA PARA USUARIOS
                    .state('usuarioGestao', {
                        url: '/usuario/gestao',
                        templateUrl: 'app/page/usuario/usuario.html'
                    })
                    .state('usuarioFormulario', {
                        url: '/usuario/formulario',
                        templateUrl: 'app/page/usuario/usuario.form.html'
                    })
                    .state('usuarioFormularioEdit', {
                        url: '/usuario/formulario/:id',
                        templateUrl: 'app/page/usuario/usuario.form.html'
                    })
                    .state('usuarioRegrasFormularioEdit', {
                        url: '/usuario-regras/formulario/:id',
                        templateUrl: 'app/page/usuario/usuario-regras.form.html'
                    })
                    .state('usuarioFormularioDetalhes', {
                        url: '/usuario/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/usuario/usuario.form.html'
                    })

                    //ROTA PARA BANCO
                    .state('bancoGestao', {
                        url: '/banco/gestao',
                        templateUrl: 'app/page/banco/banco.html'
                    })
                    .state('bancoFormulario', {
                        url: '/banco/formulario',
                        templateUrl: 'app/page/banco/banco.form.html'
                    })
                    .state('bancoFormularioEdit', {
                        url: '/banco/formulario/:id',
                        templateUrl: 'app/page/banco/banco.form.html'
                    })
                    .state('bancoFormularioDetalhes', {
                        url: '/banco/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/banco/banco.form.html'
                    })

                    //ROTA PARA ACIDENTE DE TRABALHO
                    .state('acidenteTrabalhoGestao', {
                        url: '/acidenteTrabalho/gestao',
                        templateUrl: 'app/page/acidenteTrabalho/acidenteTrabalho.html'
                    })
                    .state('acidenteTrabalhoFormulario', {
                        url: '/acidenteTrabalho/formulario',
                        templateUrl: 'app/page/acidenteTrabalho/acidenteTrabalho.form.html'
                    })
                    .state('acidenteTrabalhoFormularioEdit', {
                        url: '/acidenteTrabalho/formulario/:id',
                        templateUrl: 'app/page/acidenteTrabalho/acidenteTrabalho.form.html'
                    })
                    .state('acidenteTrabalhoFormularioDetalhes', {
                        url: '/acidenteTrabalho/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/acidenteTrabalho/acidenteTrabalho.form.html'
                    })

                    //ROTA PARA VINCULO
                    .state('vinculoGestao', {
                        url: '/vinculo/gestao',
                        templateUrl: 'app/page/vinculo/vinculo.html'
                    })
                    .state('vinculoFormulario', {
                        url: '/vinculo/formulario',
                        templateUrl: 'app/page/vinculo/vinculo.form.html'
                    })
                    .state('vinculoFormularioEdit', {
                        url: '/vinculo/formulario/:id',
                        templateUrl: 'app/page/vinculo/vinculo.form.html'
                    })
                    .state('vinculoFormularioDetalhes', {
                        url: '/vinculo/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/vinculo/vinculo.form.html'
                    })

                    //ROTA PARA ENTIDADE EXAME
                    .state('entidadeExameGestao', {
                        url: '/entidadeExame/gestao',
                        templateUrl: 'app/page/entidadeExame/entidadeExame.html'
                    })
                    .state('entidadeExameFormulario', {
                        url: '/entidadeExame/formulario',
                        templateUrl: 'app/page/entidadeExame/entidadeExame.form.html'
                    })
                    .state('entidadeExameFormularioEdit', {
                        url: '/entidadeExame/formulario/:id',
                        templateUrl: 'app/page/entidadeExame/entidadeExame.form.html'
                    })
                    .state('entidadeExameFormularioDetalhes', {
                        url: '/entidadeExame/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/entidadeExame/entidadeExame.form.html'
                    })

                    //ROTA PARA CARGO
                    .state('cargoGestao', {
                        url: '/cargo/gestao',
                        templateUrl: 'app/page/cargo/cargo.html'
                    })
                    .state('cargoFormulario', {
                        url: '/cargo/formulario',
                        templateUrl: 'app/page/cargo/cargo.form.html'
                    })
                    .state('cargoFormularioEdit', {
                        url: '/cargo/formulario/:id',
                        templateUrl: 'app/page/cargo/cargo.form.html'
                    })
                    .state('cargoFormularioDetalhes', {
                        url: '/cargo/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/cargo/cargo.form.html'
                    })


                    //ROTA PARA AGENCIA
                    .state('agenciaFormulario', {
                        url: '/agencia/formulario/:bancoId',
                        templateUrl: 'app/page/banco/agencia.form.html'
                    })
                    .state('agenciaFormularioEdit', {
                        url: '/agencia/formulario/:id',
                        templateUrl: 'app/page/banco/agencia.form.html'
                    })
                    .state('agenciaFormularioDetalhes', {
                        url: '/agencia/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/banco/agencia.form.html'
                    })

                    //ROTA PARA REGRA APOSENTADORIA
                    .state('regraAposentadoriaGestao', {
                        url: '/regraAposentadoria/gestao',
                        templateUrl: 'app/page/regraAposentadoria/regraAposentadoria.html'
                    })
                    .state('regraAposentadoriaFormulario', {
                        url: '/regraAposentadoria/formulario',
                        templateUrl: 'app/page/regraAposentadoria/regraAposentadoria.form.html'
                    })
                    .state('regraAposentadoriaFormularioEdit', {
                        url: '/regraAposentadoria/formulario/:id',
                        templateUrl: 'app/page/regraAposentadoria/regraAposentadoria.form.html'
                    })
                    .state('regraAposentadoriaFormularioDetalhes', {
                        url: '/regraAposentadoria/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/regraAposentadoria/regraAposentadoria.form.html'
                    })

                    //ROTA PARA CATEGORIA DE DOENÇA
                    .state('categoriaDoencaGestao', {
                        url: '/categoriaDoenca/gestao',
                        templateUrl: 'app/page/categoriaDoenca/categoriaDoenca.html'
                    })

                    .state('categoriaDoencaFormulario', {
                        url: '/categoriaDoenca/formulario',
                        templateUrl: 'app/page/categoriaDoenca/categoriaDoenca.form.html'
                    })

                    .state('categoriaDoencaFormularioEdit', {
                        url: '/categoriaDoenca/formulario/:id',
                        templateUrl: 'app/page/categoriaDoenca/categoriaDoenca.form.html'
                    })
                    .state('categoriaDoencaFormularioDetalhes', {
                        url: '/categoriaDoenca/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/categoriaDoenca/categoriaDoenca.form.html'
                    })

                    //ROTA PARA CLASSIFICACAO INTERNACIONAL DE DOENÇAS
                    .state('classificacaoInternacionalDoencaGestao', {
                        url: '/classificacaoInternacionalDoenca/gestao',
                        templateUrl: 'app/page/classificacaoInternacionalDoenca/classificacaoInternacionalDoenca.html'
                    })
                    .state('classificacaoInternacionalDoencaFormulario', {
                        url: '/classificacaoInternacionalDoenca/formulario',
                        templateUrl: 'app/page/classificacaoInternacionalDoenca/classificacaoInternacionalDoenca.form.html'
                    })
                    .state('classificacaoInternacionalDoencaFormularioEdit', {
                        url: '/classificacaoInternacionalDoenca/formulario/:id',
                        templateUrl: 'app/page/classificacaoInternacionalDoenca/classificacaoInternacionalDoenca.form.html'
                    })
                    .state('classificacaoInternacionalDoencaFormularioDetalhes', {
                        url: '/classificacaoInternacionalDoenca/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/classificacaoInternacionalDoenca/classificacaoInternacionalDoenca.form.html'
                    })

                    //ROTA PARA SUBCATEGORIA DOENÇA
                    .state('subCategoriaDoencaGestao', {
                        url: '/subCategoriaDoenca/gestao',
                        templateUrl: 'app/page/subCategoriaDoenca/subCategoriaDoenca.html'
                    })
                    .state('subCategoriaDoencaFormulario', {
                        url: '/subCategoriaDoenca/formulario',
                        templateUrl: 'app/page/subCategoriaDoenca/subCategoriaDoenca.form.html'
                    })
                    .state('subCategoriaDoencaFormularioEdit', {
                        url: '/subCategoriaDoenca/formulario/:id',
                        templateUrl: 'app/page/subCategoriaDoenca/subCategoriaDoenca.form.html'
                    })

                    .state('subCategoriaDoencaFormularioDetalhes', {
                        url: '/subCategoriaDoenca/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/subCategoriaDoenca/subCategoriaDoenca.form.html'
                    })

                    //ROTA PARA AUDITORIA
                    .state('auditoriaGestao', {
                        url: '/auditoria/gestao',
                        templateUrl: 'app/page/auditoria/auditoria.html'
                    })

                    //ROTA PARA RELATORIO GERENCIAL
                    .state('relatorioGerencialGestao', {
                        url: '/relatorioGerencial/gestao',
                        templateUrl: 'app/page/relatorioGerencial/relatorioGerencial.html'
                    })

                    //ROTA PARA CBO
                    .state('cboGestao', {
                        url: '/cbo/gestao',
                        templateUrl: 'app/page/cbo/cbo.html'
                    })
                    .state('cboFormulario', {
                        url: '/cbo/formulario',
                        templateUrl: 'app/page/cbo/cbo.form.html'
                    })
                    .state('cboFormularioEdit', {
                        url: '/cbo/formulario/:id',
                        templateUrl: 'app/page/cbo/cbo.form.html'
                    })
                    .state('cboFormularioDetalhes', {
                        url: '/cbo/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/cbo/cbo.form.html'
                    })

                    //ROTA PARA LEGISLAÇÃO
                    .state('legislacaoGestao', {
                        url: '/legislacao/gestao',
                        templateUrl: 'app/page/legislacao/legislacao.html'
                    })
                    .state('legislacaoFormulario', {
                        url: '/legislacao/formulario',
                        templateUrl: 'app/page/legislacao/legislacao.form.html'
                    })
                    .state('legislacaoFormularioEdit', {
                        url: '/legislacao/formulario/:id',
                        templateUrl: 'app/page/legislacao/legislacao.form.html'
                    })
                    .state('legislacaoFormularioDetalhes', {
                        url: '/legislacao/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/legislacao/legislacao.form.html'
                    })

                    //ROTA PARA PERFIS DE ACESSO
                    .state('perfilAcessoGestao', {
                        url: '/perfilAcesso/gestao',
                        templateUrl: 'app/page/perfilAcesso/perfilAcesso.html'
                    })
                    .state('perfilAcessoFormulario', {
                        url: '/perfilAcesso/formulario',
                        templateUrl: 'app/page/perfilAcesso/perfilAcesso.form.html'
                    })
                    .state('perfilAcessoFormularioEdit', {
                        url: '/perfilAcesso/formulario/:id',
                        templateUrl: 'app/page/perfilAcesso/perfilAcesso.form.html'
                    })
                    .state('perfilAcessoFormularioDetalhes', {
                        url: '/perfilAcesso/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/perfilAcesso/perfilAcesso.form.html'
                    })
                    .state('perfilAcessoUsuarioFormulario', {
                        url: '/perfilAcesso/usuario/formulario/:perfilId',
                        templateUrl: 'app/page/perfilAcesso/perfilAcessoUsuario.form.html'
                    })

                    //ROTA PARA CRM CREA
                    .state('crmCreaGestao', {
                        url: '/crmCrea/gestao',
                        templateUrl: 'app/page/crmCrea/crmCrea.html'
                    })
                    .state('crmCreaFormulario', {
                        url: '/crmCrea/formulario',
                        templateUrl: 'app/page/crmCrea/crmCrea.form.html'
                    })
                    .state('crmCreaFormularioEdit', {
                        url: '/crmCrea/formulario/:id',
                        templateUrl: 'app/page/crmCrea/crmCrea.form.html'
                    })
                    .state('crmCreaFormularioDetalhes', {
                        url: '/crmCrea/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/crmCrea/crmCrea.form.html'
                    })


                    //ROTA PARA CATEGORIA PROFISSIONAL
                    .state('categoriaProfissionalGestao', {
                        url: '/categoriaProfissional/gestao',
                        templateUrl: 'app/page/categoriaProfissional/categoriaProfissional.html'
                    })
                    .state('categoriaProfissionalFormulario', {
                        url: '/categoriaProfissional/formulario',
                        templateUrl: 'app/page/categoriaProfissional/categoriaProfissional.form.html'
                    })
                    .state('categoriaProfissionalFormularioEdit', {
                        url: '/categoriaProfissional/formulario/:id',
                        templateUrl: 'app/page/categoriaProfissional/categoriaProfissional.form.html'
                    })
                    .state('categoriaProfissionalFormularioDetalhes', {
                        url: '/categoriaProfissional/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/categoriaProfissional/categoriaProfissional.form.html'
                    })

                    //ROTA PARA CNAE
                    .state('cnae', {
                        url: '/cnae/gestao',
                        templateUrl: 'app/page/cnae/cnae.html'
                    })
                    .state('cnaeFormulario', {
                        url: '/cnae/formulario',
                        templateUrl: 'app/page/cnae/cnae.form.html'
                    })
                    .state('cnaeFormularioEdit', {
                        url: '/cnae/formulario/:id',
                        templateUrl: 'app/page/cnae/cnae.form.html'
                    })
                    .state('cnaeFormularioDetalhes', {
                        url: '/cnae/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/cnae/cnae.form.html'
                    })

                    //ROTA PARA ETAPA
                    .state('etapaGestao', {
                        url: '/etapa/gestao',
                        templateUrl: 'app/page/etapa/etapa.html'
                    })
                    .state('etapaFormulario', {
                        url: '/etapa/formulario',
                        templateUrl: 'app/page/etapa/etapa.form.html'
                    })
                    .state('etapaFormularioEdit', {
                        url: '/etapa/formulario/:id',
                        templateUrl: 'app/page/etapa/etapa.form.html'
                    })
                    .state('etapaFormularioDetalhes', {
                        url: '/etapa/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/etapa/etapa.form.html'
                    })

                    //ROTA PARA EXAME
                    .state('exameGestao', {
                        url: '/exame/gestao',
                        templateUrl: 'app/page/exame/exame.html'
                    })
                    .state('exameFormulario', {
                        url: '/exame/formulario',
                        templateUrl: 'app/page/exame/exame.form.html'
                    })
                    .state('exameFormularioEdit', {
                        url: '/exame/formulario/:id',
                        templateUrl: 'app/page/exame/exame.form.html'
                    })
                    .state('exameFormularioDetalhes', {
                        url: '/exame/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/exame/exame.form.html'
                    })

                    //ROTA PARA GRAU ACADEMICO
                    .state('grauAcademicoGestao', {
                        url: '/grauAcademico/gestao',
                        templateUrl: 'app/page/grauAcademico/grauAcademico.html'
                    })
                    .state('grauAcademicoFormulario', {
                        url: '/grauAcademico/formulario',
                        templateUrl: 'app/page/grauAcademico/grauAcademico.form.html'
                    })
                    .state('grauAcademicoFormularioEdit', {
                        url: '/grauAcademico/formulario/:id',
                        templateUrl: 'app/page/grauAcademico/grauAcademico.form.html'
                    })
                    .state('grauAcademicoFormularioDetalhes', {
                        url: '/grauAcademico/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/grauAcademico/grauAcademico.form.html'
                    })

                    //ROTA PARA FAIXA SALARIAL
                    .state('faixaSalarialGestao', {
                        url: '/faixaSalarial/gestao',
                        templateUrl: 'app/page/faixaSalarial/faixaSalarial.html'
                    })
                    .state('faixaSalarialFormulario', {
                        url: '/faixaSalarial/formulario',
                        templateUrl: 'app/page/faixaSalarial/faixaSalarial.form.html'
                    })
                    .state('faixaSalarialFormularioEdit', {
                        url: '/faixaSalarial/formulario/:id',
                        templateUrl: 'app/page/faixaSalarial/faixaSalarial.form.html'
                    })
                    .state('faixaSalarialFormularioDetalhes', {
                        url: '/faixaSalarial/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/faixaSalarial/faixaSalarial.form.html'
                    })

                    //ROTA PARA CURSO
                    .state('cursoGestao', {
                        url: '/curso/gestao',
                        templateUrl: 'app/page/curso/curso.html'
                    })
                    .state('cursoFormulario', {
                        url: '/curso/formulario',
                        templateUrl: 'app/page/curso/curso.form.html'
                    })
                    .state('cursoFormularioEdit', {
                        url: '/curso/formulario/:id',
                        templateUrl: 'app/page/curso/curso.form.html'
                    })
                    .state('cursoFormularioDetalhes', {
                        url: '/curso/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/curso/curso.form.html'
                    })

                    //ROTA PARA CAUSA AFASTAMENTO
                    .state('causaAfastamentoGestao', {
                        url: '/causaAfastamento/gestao',
                        templateUrl: 'app/page/causaAfastamento/causaAfastamento.html'
                    })
                    .state('causaAfastamentoFormulario', {
                        url: '/causaAfastamento/formulario',
                        templateUrl: 'app/page/causaAfastamento/causaAfastamento.form.html'
                    })
                    .state('causaAfastamentoFormularioEdit', {
                        url: '/causaAfastamento/formulario/:id',
                        templateUrl: 'app/page/causaAfastamento/causaAfastamento.form.html'
                    })
                    .state('causaAfastamentoFormularioDetalhes', {
                        url: '/causaAfastamento/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/causaAfastamento/causaAfastamento.form.html'
                    })

                    //ROTA PARA MOTIVO DESLIGAMENTO
                    .state('motivoDesligamentoGestao', {
                        url: '/motivoDesligamento/gestao',
                        templateUrl: 'app/page/motivoDesligamento/motivoDesligamento.html'
                    })
                    .state('motivoDesligamentoFormulario', {
                        url: '/motivoDesligamento/formulario',
                        templateUrl: 'app/page/motivoDesligamento/motivoDesligamento.form.html'
                    })
                    .state('motivoDesligamentoFormularioEdit', {
                        url: '/motivoDesligamento/formulario/:id',
                        templateUrl: 'app/page/motivoDesligamento/motivoDesligamento.form.html'
                    })
                    .state('motivoDesligamentoFormularioDetalhes', {
                        url: '/motivoDesligamento/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/motivoDesligamento/motivoDesligamento.form.html'
                    })

                    //ROTA PARA CÓDIGO RECOLHIMENTO
                    .state('codigoRecolhimentoGestao', {
                        url: '/codigoRecolhimento/gestao',
                        templateUrl: 'app/page/codigoRecolhimento/codigoRecolhimento.html'
                    })
                    .state('codigoRecolhimentoFormulario', {
                        url: '/codigoRecolhimento/formulario',
                        templateUrl: 'app/page/codigoRecolhimento/codigoRecolhimento.form.html'
                    })
                    .state('codigoRecolhimentoFormularioEdit', {
                        url: '/codigoRecolhimento/formulario/:id',
                        templateUrl: 'app/page/codigoRecolhimento/codigoRecolhimento.form.html'
                    })
                    .state('codigoRecolhimentoFormularioDetalhes', {
                        url: '/codigoRecolhimento/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/codigoRecolhimento/codigoRecolhimento.form.html'
                    })

                    //ROTA PARA MOTIVO
                    .state('motivoGestao', {
                        url: '/motivo/gestao',
                        templateUrl: 'app/page/motivo/motivo.html'
                    })
                    .state('motivoFormulario', {
                        url: '/motivo/formulario',
                        templateUrl: 'app/page/motivo/motivo.form.html'
                    })
                    .state('motivoFormularioEdit', {
                        url: '/motivo/formulario/:id',
                        templateUrl: 'app/page/motivo/motivo.form.html'
                    })
                    .state('motivoFormularioDetalhes', {
                        url: '/motivo/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/motivo/motivo.form.html'
                    })

                    //ROTA PARA HABILIDADE
                    .state('habilidadeGestao', {
                        url: '/habilidade/gestao',
                        templateUrl: 'app/page/habilidade/habilidade.html'
                    })
                    .state('habilidadeFormulario', {
                        url: '/habilidade/formulario',
                        templateUrl: 'app/page/habilidade/habilidade.form.html'
                    })
                    .state('habilidadeFormularioEdit', {
                        url: '/habilidade/formulario/:id',
                        templateUrl: 'app/page/habilidade/habilidade.form.html'
                    })
                    .state('habilidadeFormularioDetalhes', {
                        url: '/habilidade/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/habilidade/habilidade.form.html'
                    })

                    //ROTA PARA CÓDIGO PAGAMENTO GPS
                    .state('codigoPagamentoGpsGestao', {
                        url: '/codigoPagamentoGps/gestao',
                        templateUrl: 'app/page/codigoPagamentoGps/codigoPagamentoGps.html'
                    })
                    .state('codigoPagamentoGpsFormulario', {
                        url: '/codigoPagamentoGps/formulario',
                        templateUrl: 'app/page/codigoPagamentoGps/codigoPagamentoGps.form.html'
                    })
                    .state('codigoPagamentoGpsFormularioEdit', {
                        url: '/codigoPagamentoGps/formulario/:id',
                        templateUrl: 'app/page/codigoPagamentoGps/codigoPagamentoGps.form.html'
                    })
                    .state('codigoPagamentoGpsFormularioDetalhes', {
                        url: '/codigoPagamentoGps/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/codigoPagamentoGps/codigoPagamentoGps.form.html'
                    })

                    //ROTA PARA UNIDADE FEDERATIVA
                    .state('unidadeFederativaGestao', {
                        url: '/unidadeFederativa/gestao',
                        templateUrl: 'app/page/unidadeFederativa/unidadeFederativa.html'
                    })
                    .state('unidadeFederativaFormulario', {
                        url: '/unidadeFederativa/formulario',
                        templateUrl: 'app/page/unidadeFederativa/unidadeFederativa.form.html'
                    })
                    .state('unidadeFederativaFormularioEdit', {
                        url: '/unidadeFederativa/formulario/:id',
                        templateUrl: 'app/page/unidadeFederativa/unidadeFederativa.form.html'
                    })
                    .state('unidadeFederativaFormularioDetalhes', {
                        url: '/unidadeFederativa/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/unidadeFederativa/unidadeFederativa.form.html'
                    })

                    //ROTA PARA CORREÇÃO SALARIAL
                    .state('correcaoSalarialGestao', {
                        url: '/correcaoSalarial/gestao',
                        templateUrl: 'app/page/correcaoSalarial/correcaoSalarial.html'
                    })
                    .state('correcaoSalarialFormulario', {
                        url: '/correcaoSalarial/formulario',
                        templateUrl: 'app/page/correcaoSalarial/correcaoSalarial.form.html'
                    })
                    .state('correcaoSalarialFormularioEdit', {
                        url: '/correcaoSalarial/formulario/:id',
                        templateUrl: 'app/page/correcaoSalarial/correcaoSalarial.form.html'
                    })
                    .state('correcaoSalarialFormularioDetalhes', {
                        url: '/correcaoSalarial/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/correcaoSalarial/correcaoSalarial.form.html'
                    })

                    //ROTA PARA SEFIP
                    .state('sefipGestao', {
                        url: '/sefip/gestao',
                        templateUrl: 'app/page/sefip/sefip.html'
                    })
                    .state('sefipFormulario', {
                        url: '/sefip/formulario',
                        templateUrl: 'app/page/sefip/sefip.form.html'
                    })
                    .state('sefipFormularioEdit', {
                        url: '/sefip/formulario/:id',
                        templateUrl: 'app/page/sefip/sefip.form.html'
                    })
                    .state('sefipFormularioDetalhes', {
                        url: '/sefip/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/sefip/sefip.form.html'
                    })

                    //ROTA PARA FUNCAO
                    .state('funcaoGestao', {
                        url: '/funcao/gestao',
                        templateUrl: 'app/page/funcao/funcao.html'
                    })
                    .state('funcaoFormulario', {
                        url: '/funcao/formulario',
                        templateUrl: 'app/page/funcao/funcao.form.html'
                    })
                    .state('funcaoFormularioEdit', {
                        url: '/funcao/formulario/:id',
                        templateUrl: 'app/page/funcao/funcao.form.html'
                    })
                    .state('funcaoFormularioDetalhes', {
                        url: '/funcao/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/funcao/funcao.form.html'
                    })

                    //ROTA PARA TIPO FÉRIAS
                    .state('tipoFeriasGestao', {
                        url: '/tipoFerias/gestao',
                        templateUrl: 'app/page/tipoFerias/tipoFerias.html'
                    })
                    .state('tipoFeriasFormulario', {
                        url: '/tipoFerias/formulario',
                        templateUrl: 'app/page/tipoFerias/tipoFerias.form.html'
                    })
                    .state('tipoFeriasFormularioEdit', {
                        url: '/tipoFerias/formulario/:id',
                        templateUrl: 'app/page/tipoFerias/tipoFerias.form.html'
                    })
                    .state('tipoFeriasFormularioDetalhes', {
                        url: '/tipoFerias/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/tipoFerias/tipoFerias.form.html'
                    })

                    //ROTA PARA TIPO FOLHA
                    .state('tipoFolhaGestao', {
                        url: '/tipoFolha/gestao',
                        templateUrl: 'app/page/tipoFolha/tipoFolha.html'
                    })
                    .state('tipoFolhaFormulario', {
                        url: '/tipoFolha/formulario',
                        templateUrl: 'app/page/tipoFolha/tipoFolha.form.html'
                    })
                    .state('tipoFolhaFormularioEdit', {
                        url: '/tipoFolha/formulario/:id',
                        templateUrl: 'app/page/tipoFolha/tipoFolha.form.html'
                    })
                    .state('tipoFolhaFormularioDetalhes', {
                        url: '/tipoFolha/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/tipoFolha/tipoFolha.form.html'
                    })

                    //ROTA PARA TIPO APOSENTADORIA
                    .state('tipoAposentadoriaGestao', {
                        url: '/tipoAposentadoria/gestao',
                        templateUrl: 'app/page/tipoAposentadoria/tipoAposentadoria.html'
                    })
                    .state('tipoAposentadoriaFormulario', {
                        url: '/tipoAposentadoria/formulario',
                        templateUrl: 'app/page/tipoAposentadoria/tipoAposentadoria.form.html'
                    })
                    .state('tipoAposentadoriaFormularioEdit', {
                        url: '/tipoAposentadoria/formulario/:id',
                        templateUrl: 'app/page/tipoAposentadoria/tipoAposentadoria.form.html'
                    })
                    .state('tipoAposentadoriaFormularioDetalhes', {
                        url: '/tipoAposentadoria/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/tipoAposentadoria/tipoAposentadoria.form.html'
                    })

                    //ROTA PARA CLASSIFICAÇÃO ATO
                    .state('classificacaoAtoGestao', {
                        url: '/classificacaoAto/gestao',
                        templateUrl: 'app/page/classificacaoAto/classificacaoAto.html'
                    })
                    .state('classificacaoAtoFormulario', {
                        url: '/classificacaoAto/formulario',
                        templateUrl: 'app/page/classificacaoAto/classificacaoAto.form.html'
                    })
                    .state('classificacaoAtoFormularioEdit', {
                        url: '/classificacaoAto/formulario/:id',
                        templateUrl: 'app/page/classificacaoAto/classificacaoAto.form.html'
                    })
                    .state('classificacaoAtoFormularioDetalhes', {
                        url: '/classificacaoAto/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/classificacaoAto/classificacaoAto.form.html'
                    })

                    //ROTA PARA NATUREZA JURÍDICA
                    .state('naturezaJuridicaGestao', {
                        url: '/naturezaJuridica/gestao',
                        templateUrl: 'app/page/naturezaJuridica/naturezaJuridica.html'
                    })
                    .state('naturezaJuridicaFormulario', {
                        url: '/naturezaJuridica/formulario',
                        templateUrl: 'app/page/naturezaJuridica/naturezaJuridica.form.html'
                    })
                    .state('naturezaJuridicaFormularioEdit', {
                        url: '/naturezaJuridica/formulario/:id',
                        templateUrl: 'app/page/naturezaJuridica/naturezaJuridica.form.html'
                    })
                    .state('naturezaJuridicaFormularioDetalhes', {
                        url: '/naturezaJuridica/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/naturezaJuridica/naturezaJuridica.form.html'
                    })

                    //ROTA PARA FORM DEFAULT
                    .state('formularioPadrao', {
                        url: '/formulario/padrao',
                        templateUrl: 'app/page/default/formDefault.html'
                    })

                    //ROTA PARA SIMULADOR DE APOSENTADORIA
                    .state('simuladorAposentadoria', {
                        url: '/simulador/aposentadoria',
                        templateUrl: 'app/page/simuladorAposentadoria/simuladorAposentadoria.html'
                    })
                    .state('simuladorAposentadoriaResultado', {
                        url: '/simulador/aposentadoria/resultado',
                        templateUrl: 'app/page/simuladorAposentadoria/simuladorAposentadoriaResultado.html'
                    })

                    //ROTA PARA ESOCIAL
                    .state('esocialGestao', {
                        url: '/esocial/gestao',
                        templateUrl: 'app/page/esocial/esocial.html'
                    })
                    .state('esocialFormulario', {
                        url: '/esocial/formulario',
                        templateUrl: 'app/page/esocial/esocial.form.html'
                    })
                    .state('esocialFormularioEdit', {
                        url: '/esocial/formulario/:id',
                        templateUrl: 'app/page/esocial/esocial.form.html'
                    })
                    .state('esocialFormularioDetalhes', {
                        url: '/esocial/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/esocial/esocial.form.html'
                    })

                    //ROTA PARA TIPO DE CONTRATO
                    .state('tipoContratoGestao', {
                        url: '/tipoContrato/gestao',
                        templateUrl: 'app/page/tipoContrato/tipoContrato.html'
                    })
                    .state('tipoContratoFormulario', {
                        url: '/tipoContrato/formulario',
                        templateUrl: 'app/page/tipoContrato/tipoContrato.form.html'
                    })
                    .state('tipoContratoFormularioEdit', {
                        url: '/tipoContrato/formulario/:id',
                        templateUrl: 'app/page/tipoContrato/tipoContrato.form.html'
                    })
                    .state('tipoContratoFormularioDetalhes', {
                        url: '/tipoContrato/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/tipoContrato/tipoContrato.form.html'
                    })

                    //ROTA PARA SINDICATO
                    .state('sindicatoGestao', {
                        url: '/sindicato/gestao',
                        templateUrl: 'app/page/sindicato/sindicato.html'
                    })
                    .state('sindicatoFormulario', {
                        url: '/sindicato/formulario',
                        templateUrl: 'app/page/sindicato/sindicato.form.html'
                    })
                    .state('sindicatoFormularioEdit', {
                        url: '/sindicato/formulario/:id',
                        templateUrl: 'app/page/sindicato/sindicato.form.html'
                    })
                    .state('sindicatoFormularioDetalhes', {
                        url: '/sindicato/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/sindicato/sindicato.form.html'
                    })

                    //ROTA PARA VALE TRANSPORTE

                    .state('valeTransporteGestao', {
                        url: '/valeTransporte/gestao',
                        templateUrl: 'app/page/valeTransporte/valeTransporte.html'
                    })
                    .state('valeTransporteFormulario', {
                        url: '/valeTransporte/formulario',
                        templateUrl: 'app/page/valeTransporte/valeTransporte.form.html'
                    })
                    .state('valeTransporteFormularioEdit', {
                        url: '/valeTransporte/formulario/:id',
                        templateUrl: 'app/page/valeTransporte/valeTransporte.form.html'
                    })
                    .state('valeTransporteFormularioDetalhes', {
                        url: '/valeTransporte/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/valeTransporte/valeTransporte.form.html'
                    })

                    //ROTA PARA RESPONSÁVEL LEGAL

                    .state('responsavelLegalGestao', {
                        url: '/responsavelLegal/gestao',
                        templateUrl: 'app/page/responsavelLegal/responsavelLegal.html'
                    })
                    .state('responsavelLegalFormulario', {
                        url: '/responsavelLegal/formulario',
                        templateUrl: 'app/page/responsavelLegal/responsavelLegal.form.html'
                    })
                    .state('responsavelLegalFormularioEdit', {
                        url: '/responsavelLegal/formulario/:id',
                        templateUrl: 'app/page/responsavelLegal/responsavelLegal.form.html'
                    })
                    .state('responsavelLegalFormularioDetalhes', {
                        url: '/responsavelLegal/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/responsavelLegal/responsavelLegal.form.html'
                    })

                    //ROTA PARA NATUREZA DA FUNÇÃO

                    .state('naturezaFuncaoGestao', {
                        url: '/naturezaFuncao/gestao',
                        templateUrl: 'app/page/naturezaFuncao/naturezaFuncao.html'
                    })
                    .state('naturezaFuncaoFormulario', {
                        url: '/naturezaFuncao/formulario',
                        templateUrl: 'app/page/naturezaFuncao/naturezaFuncao.form.html'
                    })
                    .state('naturezaFuncaoFormularioEdit', {
                        url: '/naturezaFuncao/formulario/:id',
                        templateUrl: 'app/page/naturezaFuncao/naturezaFuncao.form.html'
                    })
                    .state('naturezaFuncaoFormularioDetalhes', {
                        url: '/naturezaFuncao/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/naturezaFuncao/naturezaFuncao.form.html'
                    })

                    //ROTA PARA FUNCIONARIO

                    .state('funcionarioGestao', {
                        url: '/funcionario/gestao',
                        templateUrl: 'app/page/funcionario/funcionario.html'
                    })
                    .state('funcionarioFormulario', {
                        url: '/funcionario/formulario',
                        templateUrl: 'app/page/funcionario/funcionario.form.html'
                    })
                    .state('funcionarioFormularioEdit', {
                        url: '/funcionario/formulario/:id',
                        templateUrl: 'app/page/funcionario/funcionario.form.html'
                    })
                    .state('funcionarioFormularioDetalhes', {
                        url: '/funcionario/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/funcionario/funcionario.form.html'
                    })

                    // ROTA PARA VERBA PENSIONISTA
                    .state('verbasPensionistaGestao', {
                        url: '/verbasPensionistas/gestao',
                        templateUrl: 'app/page/verbasPensionistas/verbasPensionista.html'
                    })
                    .state('verbasPensionistaFormulario', {
                        url: '/verbasPensionistas/formulario/:pensionistaId',
                        templateUrl: 'app/page/verbasPensionistas/verbasPensionista.form.html'
                    })
                    .state('verbasPensionistaFormularioEdit', {
                        url: '/verbasPensionistas/formulario/:id',
                        templateUrl: 'app/page/verbasPensionistas/verbasPensionista.form.html'
                    })
                    .state('verbasPensionistaFormularioDetalhes', {
                        url: '/verbasPensionistas/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/verbasPensionistas/verbasPensionista.form.html'
                    })

                    //ROTA PARA DEPENDENTE

                    .state('dependenteGestap', {
                        url: '/dependente/gestao',
                        templateUrl: 'app/page/dependente/dependente.html'
                    })
                    .state('dependenteFormulario', {
                        url: '/dependente/formulario',
                        templateUrl: 'app/page/dependente/dependente.form.html'
                    })
                    .state('dependenteFormularioEdit', {
                        url: '/dependente/formulario/:funcionarioId',
                        templateUrl: 'app/page/dependente/dependente.form.html'
                    })
                    .state('dependenteFormularioDetalhes', {
                        url: '/dependente/detalhes/:funcionarioId/:detalhes',
                        templateUrl: 'app/page/dependente/dependente.form.html'
                    })

                    //ROTA PARA DEPENDENTE BENEFICIO

                    .state('dependenteBeneficioFormulario', {
                        url: '/dependenteBeneficio/formulario/:dependenteId',
                        templateUrl: 'app/page/dependenteBeneficio/dependenteBeneficio.form.html'
                    })
                    .state('dependenteBeneficioFormularioEdit', {
                        url: '/dependenteBeneficio/formulario/:dependenteId',
                        templateUrl: 'app/page/dependenteBeneficio/dependenteBeneficio.form.html'
                    })


                    //ROTA PARA PROCESSO DE FUNÇÃO

                    .state('processoFuncaoGestao', {
                        url: '/processoFuncao/gestao',
                        templateUrl: 'app/page/processoFuncao/processoFuncao.html'
                    })
                    .state('processoFuncaoFormulario', {
                        url: '/processoFuncao/formulario',
                        templateUrl: 'app/page/processoFuncao/processoFuncao.form.html'
                    })
                    .state('processoFuncaoFormularioEdit', {
                        url: '/processoFuncao/formulario/:id',
                        templateUrl: 'app/page/processoFuncao/processoFuncao.form.html'
                    })
                    .state('processoFuncaoFormularioDetalhes', {
                        url: '/processoFuncao/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/processoFuncao/processoFuncao.form.html'
                    })

                    //ROTA PARA PRESTADOR SERVIÇO

                    .state('prestadorServicoGestao', {
                        url: '/prestadorServico/gestao',
                        templateUrl: 'app/page/prestadorServico/prestadorServico.html'
                    })
                    .state('prestadorServicoFormulario', {
                        url: '/prestadorServico/formulario',
                        templateUrl: 'app/page/prestadorServico/prestadorServico.form.html'
                    })
                    .state('prestadorServicoFormularioEdit', {
                        url: '/prestadorServico/formulario/:id',
                        templateUrl: 'app/page/prestadorServico/prestadorServico.form.html'
                    })
                    .state('prestadorServicoFormularioDetalhes', {
                        url: '/prestadorServico/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/prestadorServico/prestadorServico.form.html'
                    })

                    //ROTA PARA CONTA CONTÁBIL

                    .state('contaContabil', {
                        url: '/contaContabil/gestao',
                        templateUrl: 'app/page/contaContabil/contaContabil.html'
                    })
                    .state('contaContabilFormulario', {
                        url: '/contaContabil/formulario',
                        templateUrl: 'app/page/contaContabil/contaContabil.form.html'
                    })
                    .state('contaContabilFormularioEdit', {
                        url: '/contaContabil/formulario/:id',
                        templateUrl: 'app/page/contaContabil/contaContabil.form.html'
                    })
                    .state('contaContabilFormularioDetalhes', {
                        url: '/contaContabil/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/contaContabil/contaContabil.form.html'
                    })

                    //ROTA PARA GRUPO SALARIAL

                    .state('grupoSalarialFormulario', {
                        url: '/grupoSalarial/formulario',
                        templateUrl: 'app/page/grupoSalarial/grupoSalarial.form.html'
                    })
                    .state('grupoSalarialFormularioEdit', {
                        url: '/grupoSalarial/formulario/:id',
                        templateUrl: 'app/page/grupoSalarial/grupoSalarial.form.html'
                    })

                    //ROTA PARA MODELO DE DOCUENTO
                    .state('modeloDocumentoGestao', {
                        url: '/modeloDocumento/gestao',
                        templateUrl: 'app/page/modeloDocumento/modeloDocumento.html'
                    })

                    .state('modeloDocumentoaFormulario', {
                        url: '/modeloDocumento/formulario',
                        templateUrl: 'app/page/modeloDocumento/modeloDocumento.form.html',
                        resolve: {
                            deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                                return $ocLazyLoad.load([
                                    'textAngular'
                                ]);
                            }]
                        }
                    })

                    .state('modeloDocumentoFormularioEdit', {
                        url: '/modeloDocumento/formulario/:id',
                        templateUrl: 'app/page/modeloDocumento/modeloDocumento.form.html',
                        resolve: {
                            deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                                return $ocLazyLoad.load([
                                    'textAngular'
                                ]);
                            }]
                        }
                    })
                    .state('modeloDocumentoFormularioDetalhes', {
                        url: '/modeloDocumento/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/modeloDocumento/modeloDocumento.form.html',
                        resolve: {
                            deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                                return $ocLazyLoad.load([
                                    'textAngular'
                                ]);
                            }]
                        }
                    })

                    //ROTA PARA TIPO DE AVERBAÇÃO
                    .state('tipoAverbacaoGestao', {
                        url: '/tipoAverbacao/gestao',
                        templateUrl: 'app/page/tipoAverbacao/tipoAverbacao.html'
                    })
                    .state('tipoAverbacaoFormulario', {
                        url: '/tipoAverbacao/formulario',
                        templateUrl: 'app/page/tipoAverbacao/tipoAverbacao.form.html'
                    })
                    .state('tipoAverbacaoFormularioEdit', {
                        url: '/tipoAverbacao/formulario/:id',
                        templateUrl: 'app/page/tipoAverbacao/tipoAverbacao.form.html'
                    })
                    .state('tipoAverbacaoFormularioDetalhes', {
                        url: '/tipoAverbacao/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/tipoAverbacao/tipoAverbacao.form.html'
                    })

                    //ROTA PARA TIPO PROCESSAMENTO
                    .state('tipoProcessamentoGestao', {
                        url: '/tipoProcessamento/gestao',
                        templateUrl: 'app/page/tipoProcessamento/tipoProcessamento.html'
                    })

                    .state('tipoProcessamentoFormulario', {
                        url: '/tipoProcessamento/formulario',
                        templateUrl: 'app/page/tipoProcessamento/tipoProcessamento.form.html'
                    })

                    .state('tipoProcessamentoFormularioEdit', {
                        url: '/tipoProcessamento/formulario/:id',
                        templateUrl: 'app/page/tipoProcessamento/tipoProcessamento.form.html'
                    })
                    .state('tipoProcessamentoFormularioDetalhes', {
                        url: '/tipoProcessamento/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/tipoProcessamento/tipoProcessamento.form.html'
                    })

                    //ROTA PARA TURNO
                    .state('turnoGestao', {
                        url: '/turno/gestao',
                        templateUrl: 'app/page/turno/turno.html'
                    })

                    .state('turnoFormulario', {
                        url: '/turno/formulario',
                        templateUrl: 'app/page/turno/turno.form.html'
                    })

                    .state('turnoFormularioEdit', {
                        url: '/turno/formulario/:id',
                        templateUrl: 'app/page/turno/turno.form.html'
                    })
                    .state('turnoFormularioDetalhes', {
                        url: '/turno/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/turno/turno.form.html'
                    })

                    //ROTA PARA DIA UTIL
                    .state('diaUtilGestao', {
                        url: '/diaUtil/gestao',
                        templateUrl: 'app/page/diaUtil/diaUtil.html'
                    })

                    .state('diaUtilFormulario', {
                        url: '/diaUtil/formulario',
                        templateUrl: 'app/page/diaUtil/diaUtil.form.html'
                    })

                    .state('diaUtilFormularioEdit', {
                        url: '/diaUtil/formulario/:ano/:mes/:editar',
                        templateUrl: 'app/page/diaUtil/diaUtil.form.html'
                    })
                    .state('diaUtilFormularioDetalhes', {
                        url: '/diaUtil/detalhes/:ano/:mes/:detalhes',
                        templateUrl: 'app/page/diaUtil/diaUtil.form.html'
                    })

                    //ROTA PARA VERBA
                    .state('verbaGestao', {
                        url: '/verba/gestao',
                        templateUrl: 'app/page/verba/verba.html'
                    })
                    .state('verbaFormulario', {
                        url: '/verba/formulario',
                        templateUrl: 'app/page/verba/verbaMotor3.form.html'
                    })

                    .state('verbaFormularioEdit', {
                        url: '/verba/formulario/:id',
                        templateUrl: 'app/page/verba/verbaMotor3.form.html'
                    })
                    .state('verbaFormularioDetalhes', {
                        url: '/verba/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/verba/verbaMotor3.form.html'
                    })
                    

                    //ROTA REFERÊNCIA SALARIAL
                    .state('referenciaSalarialGestao', {
                        url: '/referenciaSalarial/gestao',
                        templateUrl: 'app/page/referenciaSalarial/referenciaSalarial.html'
                    })

                    .state('referenciaSalarialFormulario', {
                        url: '/referenciaSalarial/formulario',
                        templateUrl: 'app/page/referenciaSalarial/referenciaSalarial.form.html'
                    })

                    .state('referenciaSalarialFormularioEdit', {
                        url: '/referenciaSalarial/formulario/:id',
                        templateUrl: 'app/page/referenciaSalarial/referenciaSalarial.form.html'
                    })
                    .state('referenciaSalarialFormularioDetalhes', {
                        url: '/referenciaSalarial/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/referenciaSalarial/referenciaSalarial.form.html'
                    })

                    //ROTA DEFINIÇÃO ORGÂNICO
                    .state('definicaoOrganicoGestao', {
                        url: '/definicaoOrganico/gestao',
                        templateUrl: 'app/page/definicaoOrganico/definicaoOrganico.html'
                    })

                    .state('definicaoOrganicoFormularioId', {
                        url: '/definicaoOrganico/formulario/:id',
                        templateUrl: 'app/page/definicaoOrganico/definicaoOrganico.form.html'
                    })

                    .state('definicaoOrganicoFormularioEdit', {
                        url: '/definicaoOrganico/formulario/:empresaFilialId/:editar',
                        templateUrl: 'app/page/definicaoOrganico/definicaoOrganico.form.html'
                    })
                    .state('definicaoOrganicoFormularioDetalhes', {
                        url: '/definicaoOrganico/detalhes/:empresaFilialId/:detalhes',
                        templateUrl: 'app/page/definicaoOrganico/definicaoOrganico.form.html'
                    })

                    //ROTA EXPERIENCIA PROFISSIONAL
                    .state('experienciaProfissional', {
                        url: '/experienciaProfissional/gestao',
                        templateUrl: 'app/page/experienciaProfissional/experienciaProfissional.html'
                    })

                    .state('experienciaProfissionalFormulario', {
                        url: '/experienciaProfissional/formulario',
                        templateUrl: 'app/page/experienciaProfissional/experienciaProfissional.form.html'
                    })

                    .state('experienciaProfissionalFormularioEdit', {
                        url: '/experienciaProfissional/formulario/:id',
                        templateUrl: 'app/page/experienciaProfissional/experienciaProfissional.form.html'
                    })

                    .state('experienciaProfissionalFormularioDetalhes', {
                        url: '/experienciaProfissional/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/experienciaProfissional/experienciaProfissional.form.html'
                    })

                    //ROTA SIMULADOR NIVEL SALARIAL
                    .state('simuladorNivelSalarialGestao', {
                        url: '/simuladorNivelSalarial/gestao',
                        templateUrl: 'app/page/simuladorNivelSalarial/simuladorNivelSalarial.html'
                    })

                    .state('simuladorNivelSalarialFormulario', {
                        url: '/simuladorNivelSalarial/formulario',
                        templateUrl: 'app/page/simuladorNivelSalarial/simuladorNivelSalarial.form.html'
                    })

                    .state('simuladorNivelSalarialFormularioEdit', {
                        url: '/simuladorNivelSalarial/formulario/:id',
                        templateUrl: 'app/page/simuladorNivelSalarial/simuladorNivelSalarial.form.html'
                    })
                    .state('simuladorNivelSalarialFormularioDetalhes', {
                        url: '/simuladorNivelSalarial/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/simuladorNivelSalarial/simuladorNivelSalarial.form.html'
                    })

                    //FREQUENCIA
                    .state('frequenciaGestao', {
                        url: '/frequencia/gestao',
                        templateUrl: 'app/page/frequencia/frequencia.html'
                    })
                    .state('frequenciaFormulario', {
                        url: '/frequencia/formulario',
                        templateUrl: 'app/page/frequencia/frequencia.form.html'
                    })
                    .state('frequenciaEdit', {
                        url: '/frequencia/formulario/:id/mes/:mes/ano/:ano',
                        templateUrl: 'app/page/frequencia/frequencia.form.html'
                    })
                    .state('frequenciaDetalhes', {
                        url: '/frequencia/detalhes/:id/mes/:mes/ano/:ano/:detalhes',
                        templateUrl: 'app/page/frequencia/frequencia.form.html'
                    })

                    //FALTA
                    .state('faltaGestao', {
                        url: '/falta/gestao',
                        templateUrl: 'app/page/falta/falta.html'
                    })
                    .state('faltaFormulario', {
                        url: '/falta/formulario',
                        templateUrl: 'app/page/falta/falta.form.html'
                    })
                    .state('faltaEdit', {
                        url: '/falta/formulario/:id',
                        templateUrl: 'app/page/falta/falta.form.html'
                    })
                    .state('faltaDetalhes', {
                        url: '/falta/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/falta/falta.form.html'
                    })

                    //LICENCA MEDICA
                    .state('licencaMedicaGestao', {
                        url: '/licencaMedica/gestao',
                        templateUrl: 'app/page/historicoLicencaMedica/licencaMedica.html'
                    })
                    .state('licencaMedicaFormulario', {
                        url: '/licencaMedica/formulario',
                        templateUrl: 'app/page/historicoLicencaMedica/licencaMedica.form.html'
                    })
                    .state('licencaMedicaEdit', {
                        url: '/licencaMedica/formulario/:id',
                        templateUrl: 'app/page/historicoLicencaMedica/licencaMedica.form.html'
                    })
                    .state('licencaMedicaDetalhes', {
                        url: '/licencaMedica/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/historicoLicencaMedica/licencaMedica.form.html'
                    })


                    //ROTA PARA OBSERVACAO DOC

                    .state('observacaoDocGestao', {
                        url: '/observacaoDoc/gestao',
                        templateUrl: 'app/page/observacaoDoc/observacaoDoc.html'
                    })
                    .state('observacaoDocListGestao', {
                        url: '/observacaoDoc/list/:funcionarioId',
                        templateUrl: 'app/page/observacaoDoc/observacaoDoc.list.html'
                    })
                    .state('observacaoDocFormulario', {
                        url: '/observacaoDoc/formulario/:funcionarioId',
                        templateUrl: 'app/page/observacaoDoc/observacaoDoc.form.html'
                    })
                    .state('observacaoDocFormularioDetalhes', {
                        url: '/observacaoDoc/detalhes/:funcionarioId/:anexoId/:detalhes',
                        templateUrl: 'app/page/observacaoDoc/observacaoDoc.form.html'
                    })
                    .state('observacaoDocFormularioEditar', {
                        url: '/observacaoDoc/editar/:funcionarioId/:anexoId/:editar',
                        templateUrl: 'app/page/observacaoDoc/observacaoDoc.form.html'
                    })


                    //PROCESSO
                    .state('processoGestao', {
                        url: '/processo/gestao',
                        templateUrl: 'app/page/processo/processo.html'
                    })
                    .state('processoFormulario', {
                        url: '/processo/formulario',
                        templateUrl: 'app/page/processo/processo.form.html'
                    })
                    .state('processoEdit', {
                        url: '/processo/formulario/:id',
                        templateUrl: 'app/page/processo/processo.form.html'
                    })
                    .state('processoDetalhes', {
                        url: '/processo/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/processo/processo.form.html'
                    })

                    //ALIQUOTA
                    .state('aliquotaGestao', {
                        url: '/aliquota/gestao',
                        templateUrl: 'app/page/aliquota/aliquota.html'
                    })
                    .state('aliquotaFormulario', {
                        url: '/aliquota/formulario',
                        templateUrl: 'app/page/aliquota/aliquota.form.html'
                    })
                    .state('aliquotaEdit', {
                        url: '/aliquota/formulario/:faixa',
                        templateUrl: 'app/page/aliquota/aliquota.form.html'
                    })
                    .state('aliquotaDetalhes', {
                        url: '/aliquota/detalhes/:faixa/:detalhes',
                        templateUrl: 'app/page/aliquota/aliquota.form.html'
                    })


                    //ROTA PARA CONTRIBUIÇÃO SINDICAL
                    .state('contriubicaoSindicalGestao', {
                        url: '/contribuicaoSindical/gestao',
                        templateUrl: 'app/page/contribuicaoSindical/contribuicaoSindical.html'
                    })

                    .state('contriubicaoSindicalFormulario', {
                        url: '/contriubicaoSindical/formulario',
                        templateUrl: 'app/page/contribuicaoSindical/contribuicaoSindical.form.html'
                    })

                    .state('contribuicaoSindicalFormularioEdit', {
                        url: '/contriubicaoSindical/formulario/:id',
                        templateUrl: 'app/page/contribuicaoSindical/contribuicaoSindical.form.html'
                    })
                    .state('contribuicaoSindicalFormularioDetalhes', {
                        url: '/contriubicaoSindical/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/contribuicaoSindical/contribuicaoSindicalDetalhes.html'
                    })

                    //ROTA PARA TRANSFERÊNCIA DE FUNCIONÁRIO
                    .state('transferenciaFuncionarioGestao', {
                        url: '/transferenciaFuncionario/gestao',
                        templateUrl: 'app/page/transferenciaFuncionario/transferenciaFuncionario.html'
                    })

                    .state('transferenciaFuncionarioFormulario', {
                        url: '/transferenciaFuncionario/formulario',
                        templateUrl: 'app/page/transferenciaFuncionario/transferenciaFuncionario.form.html'
                    })

                    .state('transferenciaFuncionarioFormularioEdit', {
                        url: '/transferenciaFuncionario/formulario/:id',
                        templateUrl: 'app/page/transferenciaFuncionario/transferenciaFuncionario.form.html'
                    })
                    .state('transferenciaFuncionarioFormularioDetalhes', {
                        url: '/transferenciaFuncionario/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/transferenciaFuncionario/transferenciaFuncionario.form.html'
                    })

                    //ROTA PARA PENSAO PREVIDENCIARIA
                    .state('pensaoPrevidenciariaGestao', {
                        url: '/pensaoPrevidenciaria/gestao',
                        templateUrl: 'app/page/pensaoPrevidenciaria/pensaoPrevidenciaria.html'
                    })

                    .state('pensaoPrevidenciariaFormulario', {
                        url: '/pensaoPrevidenciaria/formulario',
                        templateUrl: 'app/page/pensaoPrevidenciaria/pensaoPrevidenciaria.form.html'
                    })

                    .state('pensaoPrevidenciariaFormularioEdit', {
                        url: '/pensaoPrevidenciaria/formulario/:id',
                        templateUrl: 'app/page/pensaoPrevidenciaria/pensaoPrevidenciaria.form.html'
                    })
                    .state('pensaoPrevidenciariaFormularioDetalhes', {
                        url: '/pensaoPrevidenciaria/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/pensaoPrevidenciaria/pensaoPrevidenciaria.form.html'
                    })

                    //ROTA PARA DECLARAÇÃO PARA APOSENTADOS
                    .state('declaracaoParaAposentadosGestao', {
                        url: '/declaracaoParaAposentados/gestao',
                        templateUrl: 'app/page/declaracaoAposentadoria/declaracaoAposentadoria.html'
                    })

                    .state('declaracaoParaAposentadosFormulario', {
                        url: '/declaracaoParaAposentados/formulario',
                        templateUrl: 'app/page/declaracaoAposentadoria/declaracaoAposentadoria.form.html'
                    })

                    .state('declaracaoParaAposentadosFormularioEdit', {
                        url: '/declaracaoParaAposentados/formulario/:id',
                        templateUrl: 'app/page/declaracaoAposentadoria/declaracaoAposentadoria.form.html'
                    })
                    .state('declaracaoParaAposentadosFormularioDetalhes', {
                        url: '/declaracaoParaAposentados/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/declaracaoAposentadoria/declaracaoAposentadoria.form.html'
                    })

                    //ROTA PARA CERTIDÃO DE TEMPO DE CONTRIBUIÇÃO - COMPENSAÇÃO
                    .state('certidaoTempoContribuicaoCompensacaoGestao', {
                        url: '/certidaoTempoContribuicaoCompensacao/gestao',
                        templateUrl: 'app/page/certidaoCompensacao/certidaoCompensacao.html'
                    })

                    .state('certidaoTempoContribuicaoCompensacaoFormulario', {
                        url: '/certidaoTempoContribuicaoCompensacao/formulario',
                        templateUrl: 'app/page/certidaoCompensacao/certidaoCompensacao.form.html'
                    })

                    .state('certidaoTempoContribuicaoCompensacaoFormularioEdit', {
                        url: '/certidaoTempoContribuicaoCompensacao/formulario/:id',
                        templateUrl: 'app/page/certidaoCompensacao/certidaoCompensacao.form.html'
                    })
                    .state('certidaoTempoContribuicaoCompensacaoFormularioDetalhes', {
                        url: '/certidaoTempoContribuicaoCompensacao/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/certidaoCompensacao/certidaoCompensacao.form.html'
                    })

                    //ROTA PARA DECLARAÇÃO DE TEMPO DE CONTRIBUIÇÃO - EX-SERVIDOR
                    .state('declaracaoExServidorGestao', {
                        url: '/declaracaoExServidor/gestao',
                        templateUrl: 'app/page/declaracaoExServidor/declaracaoExServidor.html'
                    })

                    .state('declaracaoExServidorFormulario', {
                        url: '/declaracaoExServidor/formulario',
                        templateUrl: 'app/page/declaracaoExServidor/declaracaoExServidor.form.html'
                    })

                    .state('declaracaoExServidorFormularioEdit', {
                        url: '/declaracaoExServidor/formulario/:id',
                        templateUrl: 'app/page/declaracaoExServidor/declaracaoExServidor.form.html'
                    })
                    .state('declaracaoExServidorFormularioDetalhes', {
                        url: '/declaracaoExServidor/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/declaracaoExServidor/declaracaoExServidor.form.html'
                    })

                    //ROTA PARA RECADASTRAMENTO APOSENTADO - PENSIONISTA
                    .state('recadastramentoGestao', {
                        url: '/recadastramento/gestao',
                        templateUrl: 'app/page/recadastramento/recadastramento.html'
                    })

                    .state('recadastramentoFormulario', {
                        url: '/recadastramento/formulario',
                        templateUrl: 'app/page/recadastramento/recadastramento.form.html'
                    })

                    .state('recadastramentoFormularioEdit', {
                        url: '/recadastramento/formulario/:id',
                        templateUrl: 'app/page/recadastramento/recadastramento.form.html'
                    })
                    .state('recadastramentoFormularioDetalhes', {
                        url: '/recadastramento/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/recadastramento/recadastramento.form.html'
                    })
                    .state('recadastramentoFormularioVisualizar', {
                        url: '/recadastramento/visualizar/:idFuncionario/:visualizar',
                        templateUrl: 'app/page/recadastramento/recadastramento.form.html'
                    })

                    //ROTA PARA ARQUIVO REMESSA PAGAMENTO - FOLHA PAGAMENTO
                    .state('arquivoRemessaPagamentoGestao', {
                        url: '/arquivoRemessaPagamento/gestao',
                        templateUrl: 'app/page/arquivoRemessaPagamento/arquivoRemessaPagamento.html'
                    })

                    .state('arquivoRemessaPagamentoFormulario', {
                        url: '/arquivoRemessaPagamento/formulario',
                        templateUrl: 'app/page/arquivoRemessaPagamento/arquivoRemessaPagamento.form.html'
                    })

                    .state('arquivoRemessaPagamentoFormularioEdit', {
                        url: '/arquivoRemessaPagamento/formulario/:id',
                        templateUrl: 'app/page/arquivoRemessaPagamento/arquivoRemessaPagamento.form.html'
                    })
                    .state('arquivoRemessaPagamentoFormularioDetalhes', {
                        url: '/arquivoRemessaPagamento/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/arquivoRemessaPagamento/arquivoRemessaPagamento.form.html'
                    })

                    //ROTA PARA LICENÇA PRÊMIO
                    .state('licencaPremioGestao', {
                        url: '/licencaPremio/gestao',
                        templateUrl: 'app/page/licencaPremio/licencaPremio.html'
                    })

                    .state('funcionarioExercicioFormulario', {
                        url: '/funcionarioExercicio/formulario/:funcionarioId',
                        templateUrl: 'app/page/licencaPremio/funcionarioExercicio.form.html'
                    })

                    .state('funcionarioExercicioFormularioDetalhes', {
                        url: '/licencaPremio/formulario/funcionario/:funcionarioId/exercicio/:exercicioId/:detalhes',
                        templateUrl: 'app/page/licencaPremio/licencaPremio.form.html'
                    })

                    .state('funcionarioExercicioFormularioEdit', {
                        url: '/funcionarioExercicio/formulario/funcionario/:funcionarioId/exercicio/:exercicioId/:editar',
                        templateUrl: 'app/page/licencaPremio/funcionarioExercicio.form.html'
                    })

                    .state('licencaPremioFormulario', {
                        url: '/licencaPremio/formulario/funcionario/:funcionarioId/exercicio/:exercicioId',
                        templateUrl: 'app/page/licencaPremio/licencaPremio.form.html'
                    })

                    //ROTA PARA FÉRIAS PROGRAMAÇÃO
                    .state('feriasProgramacaoGestao', {
                        url: '/feriasProgramacao/gestao',
                        templateUrl: 'app/page/feriasProgramacao/feriasProgramacao.html'
                    })

                    .state('feriasProgramacaoFormulario', {
                        url: '/feriasProgramacao/formulario/:funcionarioId',
                        templateUrl: 'app/page/feriasProgramacao/feriasProgramacao.form.html'
                    })


                    .state('feriasProgramacaoFormularioEdit', {
                        url: '/feriasProgramacao/formulario/:feriasProgramacaoId/:editar',
                        templateUrl: 'app/page/feriasProgramacao/feriasProgramacao.form.html'
                    })

                    .state('feriasProgramacaoHistory', {
                        url: '/feriasProgramacao/history/:funcionarioId',
                        templateUrl: 'app/page/feriasProgramacao/feriasProgramacaoHistory.html'
                    })

                    .state('feriasProgramacaoFormularioDetalhes', {
                        url: '/feriasProgramacao/formulario/:feriasProgramacaoId/:detalhes',
                        templateUrl: 'app/page/feriasProgramacao/feriasProgramacao.form.html'
                    })

                    .state('feriasProgramacaoParaCancelar', {
                        url: '/feriasProgramacao/feriasParaCancelar',
                        templateUrl: 'app/page/feriasProgramacao/feriasProgramacaoParaCancelar.html'
                    })

                    // ROTA PARA PENSÃO ALIMENTÍCIA
                    .state('pensaoAlimenticiaGestao', {
                        url: '/pensaoAlimenticia/gestao',
                        templateUrl: 'app/page/pensaoAlimenticia/pensaoAlimenticia.html'
                    })
                    .state('pensaoAlimenticiaFormulario', {
                        url: '/pensaoAlimenticia/formulario',
                        templateUrl: 'app/page/pensaoAlimenticia/pensaoAlimenticia2.form.html'
                    })
                    .state('pensaoAlimenticiaFormularioEdit', {
                        url: '/pensaoAlimenticia/formulario/:id',
                        templateUrl: 'app/page/pensaoAlimenticia/pensaoAlimenticia2.form.html'
                    })
                    .state('pensaoAlimenticiaFormularioDetalhes', {
                        url: '/pensaoAlimenticia/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/pensaoAlimenticia/pensaoAlimenticia2.form.html'
                    })

                    // ROTA TREINAMENTO SUGERIDO
                    .state('treinamentoSugeridoGestao', {
                        url: '/treinamentoSugerido/gestao',
                        templateUrl: 'app/page/treinamentoSugerido/treinamentoSugerido.html'
                    })
                    .state('treinamentoSugeridoFormulario', {
                        url: '/treinamentoSugerido/formulario',
                        templateUrl: 'app/page/treinamentoSugerido/treinamentoSugerido.form.html'
                    })
                    .state('treinamentoSugeridoFormularioEdit', {
                        url: '/treinamentoSugerido/formulario/:id',
                        templateUrl: 'app/page/treinamentoSugerido/treinamentoSugerido.form.html'
                    })
                    .state('treinamentoSugeridoFormularioDetalhes', {
                        url: '/treinamentoSugerido/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/treinamentoSugerido/treinamentoSugerido.form.html'
                    })

                    // ROTA PARA VERBA FUNCIONÁRIO
                    .state('verbasFuncionarioGestao', {
                        url: '/verbasFuncionario/gestao',
                        templateUrl: 'app/page/verbasFuncionario/verbasFuncionario.html'
                    })
                    .state('verbasFuncionarioFormulario', {
                        url: '/verbasFuncionario/formulario/:funcionarioId',
                        templateUrl: 'app/page/verbasFuncionario/verbasFuncionario.form.html'
                    })
                    .state('verbasFuncionarioFormularioEdit', {
                        url: '/verbasFuncionario/formulario/:id',
                        templateUrl: 'app/page/verbasFuncionario/verbasFuncionario.form.html'
                    })
                    .state('verbasFuncionarioFormularioDetalhes', {
                        url: '/verbasFuncionario/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/verbasFuncionario/verbasFuncionario.form.html'
                    })

                    // ROTA PARA FOLHA DE PAGAMENTO
                    .state('folhaPagamentoGestao', {
                        url: '/folhaPagamento/gestao',
                        templateUrl: 'app/page/folhaPagamento/gestao/folhaPagamento.html'
                    })
                    .state('folhaPagamentoDetalhamento', {
                        url: '/folhaPagamento/detalhamento/:id',
                        templateUrl: 'app/page/folhaPagamento/detalhamento/folhaPagamentoDetalhamento.html'
                    })
                    .state('folhaPagamentoDetalhamentoHistorico', {
                        url: '/folhaPagamento/detalhamento/:id/:detalhes',
                        templateUrl: 'app/page/folhaPagamento/detalhamento/folhaPagamentoDetalhamento.html'
                    })
                    .state('folhaPagamentoAdicionarFuncionario', {
                        url: '/folhaPagamento/adicionar/funcionario/:id',
                        templateUrl: 'app/page/folhaPagamento/adicionarFuncionario/folhaPagamentoAdicionarFuncionario.html'
                    })
                    .state('folhaPagamentoAdicionarLancamento', {
                        url: '/folhaPagamento/adicionar/lancamento/:id',
                        templateUrl: 'app/page/folhaPagamento/adicionarLancamento/folhaPagamentoAdicionarLancamento.html'
                    })
                    .state('folhaPagamentoAdicionarLancamentoManual', {
                        url: '/folhaPagamento/adicionar/lancamento/manual/:folhaPagamentoId',
                        templateUrl: 'app/page/folhaPagamento/adicionarLancamentoManual/folhaPagamentoAdicionarLancamentoManual.html'
                    })
                    .state('relatorioFolhaPagamento', {
                        url: '/relatorioFolhaPagamento/gestao',
                        templateUrl: 'app/page/relatorioFolhaPagamento/relatorioFolhaPagamento.html'
                    })
                    .state('relatorioServidorPagBloqueado', {
                        url: '/relatorioServidorPagBloqueado/gestao',
                        templateUrl: 'app/page/relatorioServidorPagBloqueado/relatorioServidorPagBloqueado.html'
                    })

                    .state('arquivoExportacaoSiprev', {
                        url: '/arquivoExportacaoSiprev/gestao',
                        templateUrl: 'app/page/arquivoExportacaoSiprev/arquivoExportacaoSiprev.html'
                    })

                    // REQUISICAO DE PESSOAL
                    .state('requisicaoPessoalGestao', {
                        url: '/requisicaoPessoal/gestao',
                        templateUrl: 'app/page/requisicaoPessoal/requisicaoPessoal.html'
                    })
                    .state('requisicaoPessoalFormulario', {
                        url: '/requisicaoPessoal/formulario',
                        templateUrl: 'app/page/requisicaoPessoal/requisicaoPessoal.form.html'
                    })
                    .state('requisicaoPessoalEdit', {
                        url: '/requisicaoPessoal/formulario/:id',
                        templateUrl: 'app/page/requisicaoPessoal/requisicaoPessoal.form.html'
                    })
                    .state('requisicaoPessoalDetalhes', {
                        url: '/requisicaoPessoal/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/requisicaoPessoal/requisicaoPessoal.form.html'
                    })
                    .state('requisicaoPessoalAnaliseCurriculo', {
                        url: '/requisicaoPessoal/analiseCurriculo/:id',
                        templateUrl: 'app/page/requisicaoPessoal/analiseCurriculo.form.html'
                    })

                    // REQUISICAO DE PESSOAL GESTÃO
                    .state('requisicaoPessoalGestaoGestao', {
                        url: '/requisicaoPessoalGestao/gestao',
                        templateUrl: 'app/page/requisicaoPessoalGestao/requisicaoPessoalGestao.html'
                    })
                    .state('requisicaoPessoalGestaoFormulario', {
                        url: '/requisicaoPessoalGestao/formulario',
                        templateUrl: 'app/page/requisicaoPessoalGestao/requisicaoPessoalGestao.form.html'
                    })
                    .state('requisicaoPessoalGestaoEdit', {
                        url: '/requisicaoPessoalGestao/formulario/:id',
                        templateUrl: 'app/page/requisicaoPessoalGestao/requisicaoPessoalGestao.form.html'
                    })
                    .state('requisicaoPessoalGestaoDetalhes', {
                        url: '/requisicaoPessoalGestao/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/requisicaoPessoalGestao/requisicaoPessoalGestao.form.html'
                    })
                    .state('requisicaoPessoalCadastroCurriculo', {
                        url: '/requisicaoPessoalGestao/cadastrarCurriculo/:id',
                        templateUrl: 'app/page/requisicaoPessoalGestao/cadastrarCurriculo.form.html'
                    })

                    // ROTA PARA FOLHA FINANCEIRA
                    .state('fichaFinanceiraGestao', {
                        url: '/fichaFinanceira/gestao',
                        templateUrl: 'app/page/fichaFinanceira/fichaFinanceira.html'
                    })
                    .state('fichaFinanceiraDetalhamento', {
                        url: '/fichaFinanceira/detalhamento/:idFuncionario/ano/:ano',
                        templateUrl: 'app/page/fichaFinanceira/fichaFinanceiraDetalhamento.html'
                    })
                    // .state('dashboard', {
                    //     url: '/dashboard',
                    //     templateUrl: 'app/dashboard/dashboard.html'
                    // })

                    //ROTA PARA RECISAO CONTRATO
                    .state('recisaoContratoGestao', {
                        url: '/recisaoContrato/gestao',
                        templateUrl: 'app/page/recisaoContrato/recisaoContrato.html'
                    })
                    .state('recisaoContratoFormulario', {
                        url: '/recisaoContrato/formulario',
                        templateUrl: 'app/page/recisaoContrato/recisaoContrato.form.html'
                    })
                    .state('recisaoContratoFormularioEdit', {
                        url: '/recisaoContrato/formulario/:id',
                        templateUrl: 'app/page/recisaoContrato/recisaoContrato.form.html'
                    })

                    //ROTA PARA ADIANTAMENTO PAGAMENTO
                    .state('adiantamentoPagamentoGestao', {
                        url: '/adiantamentoPagamento/gestao',
                        templateUrl: 'app/page/adiantamentoPagamento/adiantamentoPagamento.html'
                    })
                    .state('adiantamentoPagamentoFormulario', {
                        url: '/adiantamentoPagamento/formulario',
                        templateUrl: 'app/page/adiantamentoPagamento/adiantamentoPagamento.form.html'
                    })
                    .state('adiantamentoPagamentoFormularioEdit', {
                        url: '/adiantamentoPagamento/formulario/:id',
                        templateUrl: 'app/page/adiantamentoPagamento/adiantamentoPagamento.form.html'
                    })

                    // ROTA PARA AVALIAÇÃO DESEMPENHO
                    .state('avaliacaoDesempenhoGestao', {
                        url: '/avaliacaoDesempenho/gestao',
                        templateUrl: 'app/page/avaliacaoDesempenho/avaliacaoDesempenho.html'
                    })
                    .state('avaliacaoDesempenhoFormulario', {
                        url: '/avaliacaoDesempenho/formulario',
                        templateUrl: 'app/page/avaliacaoDesempenho/avaliacaoDesempenho.form.html'
                    })
                    .state('avaliacaoDesempenhoFormularioEdit', {
                        url: '/avaliacaoDesempenho/formulario/:id',
                        templateUrl: 'app/page/avaliacaoDesempenho/avaliacaoDesempenho.form.html'
                    })
                    .state('avaliacaoDesempenhoFormularioDetalhes', {
                        url: '/avaliacaoDesempenho/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/avaliacaoDesempenho/avaliacaoDesempenho.form.html'
                    })
                    .state('respostasResultadosGestao', {
                        url: '/respostasResultados/gestao',
                        templateUrl: 'app/page/avaliacaoDesempenho/respostasResultados.html'
                    })
                    .state('respostasResultadosFormulario', {
                        url: '/respostasResultados/formulario',
                        templateUrl: 'app/page/avaliacaoDesempenho/respostasResultados.form.html'
                    })
                    .state('respostasResultadosFormularioEdit', {
                        url: '/respostasResultados/formulario/:id',
                        templateUrl: 'app/page/avaliacaoDesempenho/respostasResultados.form.html'
                    })
                    .state('respostasResultadosFormularioDetalhes', {
                        url: '/respostasResultados/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/avaliacaoDesempenho/respostasResultados.form.html'
                    })

                    // ROTA PARA PROGRAMAÇÃO ADIANTAMENTO SALARIO 13
                    .state('solAdiantamentoGestao', {
                        url: '/solAdiantamento/gestao',
                        templateUrl: 'app/page/solAdiantamento/solAdiantamento.html'
                    })
                    .state('solAdiantamentoFormulario', {
                        url: '/solAdiantamento/formulario',
                        templateUrl: 'app/page/solAdiantamento/solAdiantamento.form.html'
                    })
                    .state('solAdiantamentoFormularioEdit', {
                        url: '/solAdiantamento/formulario/:id',
                        templateUrl: 'app/page/solAdiantamento/solAdiantamento.form.html'
                    })

                    // ROTA PARA TRANSFERENCIA PREVIDENCIA FUNCIONARIO
                    .state('transferenciaPrevidenciaFuncionarioGestao', {
                        url: '/transferenciaPrevidenciaFuncionario/gestao',
                        templateUrl: 'app/page/transferenciaPrevidenciaFuncionario/transferenciaPrevidenciaFuncionario.html'
                    })
                    .state('transferenciaPrevidenciaFuncionarioFormulario', {
                        url: '/transferenciaPrevidenciaFuncionario/formulario',
                        templateUrl: 'app/page/transferenciaPrevidenciaFuncionario/transferenciaPrevidenciaFuncionario.form.html'
                    })
                    .state('transferenciaPrevidenciaFuncionarioEdit', {
                        url: '/transferenciaPrevidenciaFuncionario/formulario/:id',
                        templateUrl: 'app/page/transferenciaPrevidenciaFuncionario/transferenciaPrevidenciaFuncionario.form.html'
                    })
                    .state('transferenciaPrevidenciaFuncionarioDetalhes', {
                        url: '/transferenciaPrevidenciaFuncionario/formulario/:id/:detalhes',
                        templateUrl: 'app/page/transferenciaPrevidenciaFuncionario/transferenciaPrevidenciaFuncionario.form.html'
                    })

                    // ROTA PARA CERTIDÃO DE TEMPO DE CONTRIBUIÇÃO  EX SERVIDOR
                    .state('certidaoTempoContribuicaoExServidorGestao', {
                        url: '/certidaoTempoContribuicaoExServidor/gestao',
                        templateUrl: 'app/page/certidaoTempoContribuicaoExServidor/certidaoTempoContribuicaoExServidor.html'
                    })
                    .state('certidaoTempoContribuicaoExServidorFormulario', {
                        url: '/certidaoTempoContribuicaoExServidor/formulario',
                        templateUrl: 'app/page/certidaoTempoContribuicaoExServidor/certidaoTempoContribuicaoExServidor.form.html'
                    })
                    .state('certidaoTempoContribuicaoExServidorEdit', {
                        url: '/certidaoTempoContribuicaoExServidor/formulario/:id',
                        templateUrl: 'app/page/certidaoTempoContribuicaoExServidor/certidaoTempoContribuicaoExServidor.form.html'
                    })
                    .state('certidaoTempoContribuicaoExServidorDetalhes', {
                        url: '/certidaoTempoContribuicaoExServidor/formulario/:id/:detalhes',
                        templateUrl: 'app/page/certidaoTempoContribuicaoExServidor/certidaoTempoContribuicaoExServidor.form.html'
                    })
                    .state('certidaoTempoContribuicaoExServidorRetificar', {
                        url: '/certidaoTempoContribuicaoExServidor/retificar/:id/:retificar',
                        templateUrl: 'app/page/certidaoTempoContribuicaoExServidor/certidaoTempoContribuicaoExServidor.form.html'
                    })

                    //ROTA PARA IMPORTAÇÃO DE VERBAS PARA O FUNCIONARIO
                    .state('importadorVerbaFuncionarioGestao', {
                        url: '/importadorVerbaFuncionario/gestao',
                        templateUrl: 'app/page/importadorVerbaFuncionario/importadorVerbaFuncionario.html'
                    })

                    // Relatórios
                    .state('relatorioRecrutamentoESelecao', {
                        url: '/relatorios/recrutamentoESelecao/relatorioRecrutamentoESelecao',
                        templateUrl: 'app/page/relatorios/recrutamentoESelecao/relatorioRecrutamentoESelecao.html'
                    })

                    //RELATORIO BATIMENTO DA FOLHA DE PAGAMENTO
                    .state('relatorioBatimentoFolhaPagamento', {
                        url: '/batimentoFolhaPagamento/relatorio',
                        templateUrl: 'app/page/batimentoFolhaPagamento/batimentoFolhaPagamento.html'
                    })

                    // ROTA PARA RELATÓRIO FINANCEIRO FOLHA DE PAGAMENTO
                    .state('relatorioFinanceiroFolhaPagamentoGestao', {
                        url: '/relatorio/financeiro/gestao',
                        templateUrl: 'app/page/relatorioFinanceiroFolhaPagamento/relatorioFinanceiroFolhaPagamento.html'
                    })

                    // ROTA PARA DIRF
                    .state('dirfGestao', {
                        url: '/dirf/gestao',
                        templateUrl: 'app/page/dirf/dirf.html'
                    })
                    .state('dirfFormulario', {
                        url: '/dirf/formulario',
                        templateUrl: 'app/page/dirf/dirf.form.html'
                    })
                    .state('dirfEdit', {
                        url: '/dirf/formulario/:id',
                        templateUrl: 'app/page/dirf/dirf.form.html'
                    })
                    .state('dirfDetalhes', {
                        url: '/dirf/formulario/:id/:detalhes',
                        templateUrl: 'app/page/dirf/dirf.form.html'
                    })

                    // ROTA PARA ARRECADAÇÃO ALIQUOTA
                    .state('arrecadacaoAliquotaGestao', {
                        url: '/arrecadacaoAliquota/gestao',
                        templateUrl: 'app/page/arrecadacaoAliquota/arrecadacaoAliquota.html'
                    })
                    .state('arrecadacaoAliquotaFormulario', {
                        url: '/arrecadacaoAliquota/formulario',
                        templateUrl: 'app/page/arrecadacaoAliquota/arrecadacaoAliquota.form.html'
                    })
                    .state('arrecadacaoAliquotaEdit', {
                        url: '/arrecadacaoAliquota/formulario/:id',
                        templateUrl: 'app/page/arrecadacaoAliquota/arrecadacaoAliquota.form.html'
                    })
                    .state('arrecadacaoAliquotaDetalhes', {
                        url: '/arrecadacaoAliquota/formulario/:id/:detalhes',
                        templateUrl: 'app/page/arrecadacaoAliquota/arrecadacaoAliquota.form.html'
                    })
                    
                    // ROTA PARA ARRECADAÇÃO ÍNDICES
                    .state('arrecadacaoIndiceGestao', {
                        url: '/arrecadacaoIndice/gestao',
                        templateUrl: 'app/page/arrecadacaoIndice/arrecadacaoIndice.html'
                    })
                    .state('arrecadacaoIndiceFormulario', {
                        url: '/arrecadacaoIndice/formulario',
                        templateUrl: 'app/page/arrecadacaoIndice/arrecadacaoIndice.form.html'
                    })
                    .state('arrecadacaoIndiceFormularioEdit', {
                        url: '/arrecadacaoIndice/formulario/:id',
                        templateUrl: 'app/page/arrecadacaoIndice/arrecadacaoIndice.form.html'
                    })
                    .state('indicesFormularioDetalhes', {
                        url: '/arrecadacaoIndice/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/arrecadacaoIndice/arrecadacaoIndice.form.html'
                    })


                    //ROTA PARA ESPECIALIDADE MÉDICA - JUNTA MÉDICA
                    .state('especialidadeMedicaGestao', {
                        url: '/especialidadeMedica/gestao',
                        templateUrl: 'app/page/especialidadeMedica/especialidadeMedica.html'
                    })
                    .state('especialidadeMedicaFormulario', {
                        url: '/especialidadeMedica/formulario',
                        templateUrl: 'app/page/especialidadeMedica/especialidadeMedica.form.html'
                    })
                    .state('especialidadeMedicaFormularioEdit', {
                        url: '/especialidadeMedica/formulario/:id',
                        templateUrl: 'app/page/especialidadeMedica/especialidadeMedica.form.html'
                    })
                    .state('especialidadeMedicaFormularioDetalhes', {
                        url: '/especialidadeMedica/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/especialidadeMedica/especialidadeMedica.form.html'
                    })

                    //ROTA PARA MÉDICO - JUNTA MÉDICA
                    .state('medicoGestao', {
                        url: '/medico/gestao',
                        templateUrl: 'app/page/medico/medico.html'
                    })
                    .state('medicoFormulario', {
                        url: '/medico/formulario',
                        templateUrl: 'app/page/medico/medico.form.html'
                    })
                    .state('medicoFormularioEdit', {
                        url: '/medico/formulario/:id',
                        templateUrl: 'app/page/medico/medico.form.html'
                    })
                    .state('medicoFormularioDetalhes', {
                        url: '/medico/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/medico/medico.form.html'
                    })

                    //ROTA PARA AGENDA MÉDICA - JUNTA MÉDICA
                    .state('agendaMedicaGestao', {
                        url: '/agendaMedica/gestao',
                        templateUrl: 'app/page/agendaMedica/agendaMedica.html'
                    })
                    .state('agendaMedicaFormulario', {
                        url: '/agendaMedica/formulario',
                        templateUrl: 'app/page/agendaMedica/agendaMedica.form.html'
                    })
                    .state('agendaMedicaFormularioEdit', {
                        url: '/agendaMedica/formulario/:id',
                        templateUrl: 'app/page/agendaMedica/agendaMedica.form.html'
                    })
                    .state('agendaMedicaFormularioDetalhes', {
                        url: '/agendaMedica/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/agendaMedica/agendaMedica.form.html'
                    })
                    .state('agendaMedicaRelatorio', {
                        url: '/agendaMedica/relatorio',
                        templateUrl: 'app/page/agendaMedica/agendaMedica.relatorio.html'
                    })

                    //ROTA PARA PERÍCIA MÉDICA - JUNTA MÉDICA
                    .state('periciaMedicaGestao', {
                        url: '/periciaMedica/gestao',
                        templateUrl: 'app/page/periciaMedica/periciaMedica.html'
                    })
                    .state('periciaMedicaFormulario', {
                        url: '/periciaMedica/formulario',
                        templateUrl: 'app/page/periciaMedica/periciaMedica.form.html'
                    })
                    .state('periciaMedicaFormularioEdit', {
                        url: '/periciaMedica/formulario/:id/:edit',
                        templateUrl: 'app/page/periciaMedica/periciaMedica.form.html'
                    })
                    .state('periciaMedicaFormularioDetalhes', {
                        url: '/periciaMedica/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/periciaMedica/periciaMedica.form.html'
                    })

                     //ROTA PARA PRONTUÁRIO PERÍCIA MÉDICA - JUNTA MÉDICA
                     .state('prontuarioPericiaMedicaGestao', {
                        url: '/prontuarioPericiaMedica/gestao',
                        templateUrl: 'app/page/prontuarioPericiaMedica/prontuarioPericiaMedica.html'
                    })
                    .state('prontuarioPericiaMedicaFormulario', {
                        url: '/prontuarioPericiaMedica/formulario',
                        templateUrl: 'app/page/prontuarioPericiaMedica/prontuarioPericiaMedica.form.html'
                    })
                    .state('prontuarioPericiaMedicaFormularioEdit', {
                        url: '/prontuarioPericiaMedica/formulario/:id',
                        templateUrl: 'app/page/prontuarioPericiaMedica/prontuarioPericiaMedica.form.html'
                    })
                    .state('prontuarioPericiaMedicaFormularioDetalhes', {
                        url: '/prontuarioPericiaMedica/detalhes/:id/:detalhes',
                        templateUrl: 'app/page/prontuarioPericiaMedica/prontuarioPericiaMedica.form.html'
                    })
                    .state('prontuarioPericiaMedicaFormularioVisualizar', {
                        url: '/prontuarioPericiaMedica/visualizar/:id/:visualizar',
                        templateUrl: 'app/page/prontuarioPericiaMedica/prontuarioPericiaMedicaVisualizar.form.html'
                    })

                    //RELATORIO APOSENTADOS E PENSÕES
                    .state('relatorioAposentadoPensionista', {
                        url: '/relatorioAposentadoPensionista/gestao',
                        templateUrl: 'app/page/relatorioAposentadoPensionista/relatorioAposentadoPensionista.html'
                    })

                    .state('form/editor', {
                        url: '/form/editor',
                        templateUrl: "app/form/editor.html",
                        resolve: {
                            deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                                return $ocLazyLoad.load([
                                    'textAngular'
                                ]);
                            }]
                        }
                    })
                    .state('form/wizard', {
                        url: '/form/wizard',
                        templateUrl: "app/form/wizard.html",
                        resolve: {
                            deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                                return $ocLazyLoad.load([
                                    'angular-wizard'
                                ]);
                            }]
                        }
                    })
                    .state('map/maps', {
                        url: '/map/maps',
                        templateUrl: "app/map/maps.html",
                        resolve: {
                            deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                                return $ocLazyLoad.load([
                                    'googlemap',
                                ]);
                            }]
                        }
                    });

                // $urlRouterProvider
                //     .when('/', '/dashboard')
                //     .otherwise('/dashboard');
                $urlRouterProvider
                    .when('/', '/page/home')
                    .otherwise('/page/home');

            }
        ]);

})();
