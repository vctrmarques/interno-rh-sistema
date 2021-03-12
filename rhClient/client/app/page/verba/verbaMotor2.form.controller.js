(function () {
    'use strict';

    angular.module('app.page')
        .controller('verbaMotor2FormCtrl', ['$q', '$scope', '$mdDialog', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', 'RestService', '$filter', verbaMotor2FormCtrl]);


    function verbaMotor2FormCtrl($q, $scope, $mdDialog, $mdToast, $location, $state, $rootScope, GenericoService, EnumService, RestService, $filter) {

        //Construtor do objeto legislacaoAnexo
        $scope.verbaInit = {
            formulas: []
        }
        $scope.verba = angular.copy($scope.verbaInit);

        // Busca dos Atributos
        RestService.Get('/atributosFormula')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.atributoList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Busca de IdentificacaoVerbaEnum
        EnumService.Get("IdentificacaoVerbaEnum").then(function (dados) {
            $scope.identificadorVerbas = dados;
        });

        $scope.itemFormulaSelecionada = []

        // Montagem dos Operadores
        $scope.operacoesFormula = [];
        $scope.operacoesFormula.push({ value: '+', label: '+' });
        $scope.operacoesFormula.push({ value: '-', label: '-' });
        $scope.operacoesFormula.push({ value: '/', label: '/' });
        $scope.operacoesFormula.push({ value: '*', label: '*' });
        $scope.operacoesFormula.push({ value: '%', label: '%' });
        $scope.operacoesFormula.push({ value: '(', label: '(' });
        $scope.operacoesFormula.push({ value: ')', label: ')' });
        $scope.operacoesFormula.push({ value: '>', label: '>' });
        $scope.operacoesFormula.push({ value: '<', label: '<' });
        $scope.operacoesFormula.push({ value: '>=', label: '>=' });
        $scope.operacoesFormula.push({ value: '<=', label: '<=' });

        // Montagem dos Condicionais
        $scope.condicoesFormula = [];
        $scope.condicoesFormula.push({ value: 'if (', label: 'SE' });
        $scope.condicoesFormula.push({ value: ') {', label: 'ENTÃO' });
        $scope.condicoesFormula.push({ value: '} else {', label: 'SENÃO' });
        $scope.condicoesFormula.push({ value: '} else if (', label: 'SENÃO SE' });
        $scope.condicoesFormula.push({ value: '}', label: 'FIM DO SE' });
        $scope.condicoesFormula.push({ value: '&&', label: 'E' });
        $scope.condicoesFormula.push({ value: '||', label: 'OU' });
        $scope.condicoesFormula.push({ value: '==', label: 'IGUAL' });

        // Rotina responsável pela edição da verba.
        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');

            GenericoService.GetById('/verba/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');

                if (response.status === 200) {
                    $scope.verba = response.data;

                    if (!$scope.verba.formulas)
                        $scope.verba.formulas = [];
                    else
                        $scope.verba.formulas = $filter('orderBy')($scope.verba.formulas, 'ordem');

                    if ($scope.verba.vigenciaInicial)
                        $scope.verba.vigenciaInicial = moment(response.data.vigenciaInicial);

                    if ($scope.verba.vigenciaFinal)
                        $scope.verba.vigenciaFinal = moment(response.data.vigenciaFinal);

                    if ($scope.verba.incidenciasResponse.length)
                        $scope.incidencias = $scope.verba.incidenciasResponse;
                    $scope.verba.centroCustoId = response.data.centroCusto.id;

                } else {
                    $scope.showSimpleToast("Verba não encontrado na base");
                }
            });
        }

        // Checagem da página de detalhes
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        // Ação de retorno.
        $scope.goBack = function () {
            $location.path('/verba/gestao');
        }

        // Método de busca de verba associada.
        $scope.querySearchVerba = function (query) {
            var deferred = $q.defer();
            if (query && query.length > 2) {
                $rootScope.$broadcast('preloader:active');
                var config = {
                    params: {
                        search: query
                    }
                };
                RestService.Get('/verba/search', config)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.data && response.data.length > 0)
                            deferred.resolve(response.data);
                        else
                            deferred.resolve([]);
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400)
                            $mdToast.show(
                                $mdToast.simple()
                                    .textContent(response.data.message)
                                    .position('top right')
                                    .hideDelay(10000)
                            );
                    });
            }
            return deferred.promise;
        };

        // Rotina responsável por salvar a verba.
        $scope.save = function () {

            $scope.verba.incidenciasIds = [];
            $scope.incidencias.forEach(function (value) {
                if (value) {
                    $scope.verba.incidenciasIds.push(value.id);
                }
            });

            // Valida o preenchimento de fórmula.
            if (!$scope.verba.formulas || $scope.verba.formulas.length === 0) {
                $scope.showSimpleToast("Favor preencher a fórmula da verba.");
                return;
            }

            // Set verbaAssociadaId no request
            if ($scope.verba.verbaAssociada) {
                $scope.verba.verbaAssociadaId = $scope.verba.verbaAssociada.id;
            }

            $rootScope.$broadcast('preloader:active');

            if ($scope.verba.id) {
                GenericoService.Update('/verba', $scope.verba, function (response) {
                    $rootScope.$broadcast('preloader:hide');

                    if (response.status === 200) {
                        //Refactore
                    } else if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/verba/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('verba/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/verba', $scope.verba, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/verba/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/verba/formulario');
                    }
                });
            }
        }

        // Método acionado ao selecionar um atributo
        $scope.changeAtributo = function () {
            var atributoSelecinado = $scope.atributoSelecinado;
            var itemFormula = {
                ordem: $scope.verba.formulas.length,
                valor: atributoSelecinado.name,
                tipo: "ATRIBUTO",
                rotina: null,
                descricao: atributoSelecinado.label
            }
            $scope.adicionarItemFormula(itemFormula);
            delete $scope.atributoSelecinado;
        }

        // Método acionado ao selecionar uma rotina
        $scope.changeRotina = function () {
            var rotinaSelecionada = $scope.rotinaSelecionada;
            var itemFormula = {
                ordem: $scope.verba.formulas.length,
                valor: null,
                tipo: "ROTINA",
                rotina: rotinaSelecionada,
                descricao: rotinaSelecionada.label.trim()
            }
            $scope.adicionarItemFormula(itemFormula);
            delete $scope.rotinaSelecionada;

        }

        // Método acionado ao selecionar um operador
        $scope.changeOperadorOuCondicao = function (operadorOuCondicao) {
            var itemFormula = {
                ordem: $scope.verba.formulas.length,
                valor: operadorOuCondicao.value,
                tipo: "CODIGO",
                rotina: null,
                descricao: operadorOuCondicao.label
            }
            $scope.adicionarItemFormula(itemFormula);

        }

        $scope.adicionarItemFormula = function (itemFormula) {
            if ($scope.itemFormulaSelecionada && $scope.itemFormulaSelecionada.length === 1) {
                var index = $scope.itemFormulaSelecionada[0].ordem;
                $scope.verba.formulas.splice(index, 0, itemFormula);
                $scope.verba.formulas.forEach((item, index) => {
                    item.ordem = index;
                });
            } else
                $scope.verba.formulas.push(itemFormula);
            $scope.verba.formulas = $filter('orderBy')($scope.verba.formulas, 'ordem');
        }

        $scope.showDialogDado = function (ev, tipo) {
            $scope.tipoDado = tipo;
            $scope.data = null;
            $scope.texto = null;
            $scope.numeral = null;
            $scope.feedbackError = null;
            $scope.decimal = 0;

            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                    $scope.inserirDado = function () {
                        if ($scope.tipoDado === 'texto') {
                            if ($scope.texto) {
                                var itemFormula = {
                                    ordem: $scope.verba.formulas.length,
                                    valor: $scope.texto,
                                    tipo: "TEXTO",
                                    rotina: null,
                                    descricao: $scope.texto
                                }
                                $scope.adicionarItemFormula(itemFormula);
                                $mdDialog.hide();
                            } else {
                                $scope.feedbackError = "Texto inválido! Digite um texto para inserir.";
                            }

                        } else if ($scope.tipoDado === 'numeral') {

                            if ($scope.numeral) {
                                if (isNaN($scope.numeral)) {
                                    $scope.feedbackError = "Número inválido! Use apenas caracteres numéricos. Para número decimal, use ponto ou invés de vírgula.";
                                } else {
                                    var itemFormula = {
                                        ordem: $scope.verba.formulas.length,
                                        valor: $scope.numeral,
                                        tipo: "NUMERAL",
                                        rotina: null,
                                        descricao: $scope.numeral
                                    }
                                    $scope.adicionarItemFormula(itemFormula);
                                    $mdDialog.hide();
                                }
                            } else {
                                $scope.feedbackError = "Número inválido! Digite um númeral para inserior.";
                            }

                        } else if ($scope.tipoDado === 'data') {
                            if ($scope.data) {
                                if ($scope.data.isValid()) {
                                    var itemFormula = {
                                        ordem: $scope.verba.formulas.length,
                                        valor: $scope.data.format("DD/MM/YYYY"),
                                        tipo: "DATA",
                                        rotina: null,
                                        descricao: $scope.data.format("DD/MM/YYYY")
                                    }
                                    $scope.adicionarItemFormula(itemFormula);
                                    $mdDialog.hide();
                                } else {
                                    $scope.feedbackError = "Data inválida! Selecione ou digite uma data válida.";
                                }
                            } else {
                                $scope.feedbackError = "Data inválida! Selecione ou digite uma data para inserior.";
                            }
                        }
                    }
                }],
                template: `
                <md-dialog aria-label="Insira a Informação" flex="40">
                    <form ng-cloak>
                        <md-toolbar>
                            <div class="md-toolbar-tools">
                                <h2>Insira a Informação</h2>
                                <span flex></span>
                                <md-button class="md-icon-button" ng-click="cancel()">
                                    <md-icon class="material-icons">close</md-icon>
                                </md-button>
                            </div>
                        </md-toolbar>
                        <md-dialog-content>
                            <div class="md-dialog-content">
                                <div layout layout-sm="column" ng-show="tipoDado === 'texto'">
                                    <md-input-container flex>
                                        <label>Texto</label>
                                        <input ng-model="texto">
                                        <div ng-messages="feedbackError"
                                            role="alert">
                                            <div>{{feedbackError}}</div>
                                        </div>
                                    </md-input-container>
                                </div>
                                <div layout layout-sm="column" ng-show="tipoDado === 'numeral'">
                                    <md-input-container flex>
                                        <label>Numeral</label>
                                        <input ng-model="numeral">
                                        <div ng-messages="feedbackError"
                                            role="alert">
                                            <div>{{feedbackError}}</div>
                                        </div>
                                    </md-input-container>
                                </div>
                                <div layout layout-sm="column" ng-show="tipoDado === 'data'">
                                    <md-input-container flex>
                                        <label>Data</label>
                                        <md-icon class="material-icons icon-momentpicker"
                                            ng-class="md-datepicker-calendar-icon"
                                            aria-label="md-calendar">
                                            date_range 
                                        </md-icon>
                                        <input
                                            name="vigenciaInicial"
                                            moment-picker="data"
                                            placeholder="Selecione a data"
                                            ng-model="data"
                                            format="DD/MM/YYYY"
                                            locale="pt-br"
                                            start-view="month"
                                            ng-model-options="{ updateOn: 'blur' }">
                                        <div ng-messages="feedbackError"
                                            role="alert">
                                            <div>{{feedbackError}}</div>
                                        </div>
                                    </md-input-container>
                                </div>
                            </div>
                        </md-dialog-content>
                        <md-dialog-actions layout="row">
                            <md-button md-no-ink class="md-primary" ng-click="inserirDado()">
                                Inserir
                            </md-button>
                            <md-button ng-click="cancel()">
                                Cancelar
                            </md-button>
                        </md-dialog-actions>
                    </form>
                </md-dialog>
            `,
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true
            });
        }

        $scope.sobe = function (item) {
            if (item.ordem !== 0) {
                item.ordem = item.ordem - 1;
                $scope.verba.formulas[item.ordem].ordem = $scope.verba.formulas[item.ordem].ordem + 1;
                $scope.verba.formulas = $filter('orderBy')($scope.verba.formulas, 'ordem');
            }
        }

        $scope.desce = function (item) {
            if (item.ordem + 1 < $scope.verba.formulas.length) {
                item.ordem = item.ordem + 1;
                $scope.verba.formulas[item.ordem].ordem = $scope.verba.formulas[item.ordem].ordem - 1;
                $scope.verba.formulas = $filter('orderBy')($scope.verba.formulas, 'ordem');
            }
        }

        $scope.exclui = function (index) {
            $scope.verba.formulas.splice(index, 1);
            $scope.verba.formulas.forEach((item, index) => {
                item.ordem = index;
            });
            $scope.verba.formulas = $filter('orderBy')($scope.verba.formulas, 'ordem');
            $scope.itemFormulaSelecionada = [];
        }






















        $scope.list = {
            "listaAliquotas": []
        };

        $scope.verbaIncidencia = {};
        $scope.incidencias = [];

        GenericoService.GetAll('/aliquotas').then(
            function (response) {
                if (response.data && response.data.length > 0) {
                    $scope.list.listaAliquotas = response.data;
                }
            }
        );

        EnumService.Get("TipoVerba").then(function (dados) {
            $scope.tipoVerbas = dados;
        });

        EnumService.Get("FaixaEnum").then(function (dados) {
            $scope.faixasEnum = dados;
        });

        EnumService.Get("DestinacaoExterna").then(function (dados) {
            $scope.destinacaoExternas = dados;
        });

        EnumService.Get("TipoValorEnum").then(function (dados) {
            $scope.tipoValores = dados;
        });

        EnumService.Get("RecorrenciaEnum").then(function (dados) {
            $scope.recorrencias = dados;
        });

        $scope.adicionarVerbaIncidente = function () {
            if ($scope.verbaIncidencia) {
                var validador = true;
                for (var index = 0; index < $scope.incidencias.length; index++) {
                    var id = $scope.incidencias[index].id;

                    if (id == $scope.verbaIncidencia.id) {
                        validador = false;
                    }

                }
                if (validador) {
                    $scope.incidencias.push($scope.verbaIncidencia);
                    delete $scope.verbaIncidencia;
                } else {
                    $scope.showSimpleToast("A verba já foi adicionada a listagem");
                }
            }
        }

        $scope.removerVerbaIncidente = function (index) {
            $scope.incidencias.splice(index, 1);
        }

        $scope.carregarFormulaParaValidar = function () {
            var formulaTemp = $scope.verba.formula;
            for (i = 0; i < $scope.chaves.length; i++) {
                var chave = $scope.chaves[i].chave;
                while (formulaTemp.indexOf(chave) >= 0) {
                    formulaTemp = formulaTemp.replace(chave, $scope.chaves[i].value);
                }
            }
            return formulaTemp;
        }

        $scope.validarFormula = function () {
            var formulaASerValidada = $scope.carregarFormulaParaValidar();
            if ($scope.isVariaveisPreenchidas()) {
                $rootScope.$broadcast('preloader:active');
                GenericoService.ValidaFormula('/verba/validarFormula', formulaASerValidada, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200 && response.data.success) {
                        $scope.showSimpleToast('Resultado: ' + response.data.obj);
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
            }
        }

        $scope.extrairVariaveis = function () {
            var formulaTemp = $scope.verba.formula;
            var lista = [];
            $scope.chaves = [];

            //var variaveis = [/o{[a-z][\w\.]+}/i,/a{[a-z][\w\.]+}/i,/r{[a-z][\w\.]+}/i];
            var variaveis = /\w{[a-z][\w\.]+}/i;


            var reg = new RegExp(variaveis);
            while (reg.test(formulaTemp)) {

                console.log(variaveis);
                console.log(reg.test(formulaTemp));
                var variavel = formulaTemp.match(variaveis);
                if (Array.isArray(variavel)) {
                    variavel = variavel[0];
                }
                console.log(variavel);
                var label = variavel.replace('o{', '');
                label = label.replace('r{', '');
                label = label.replace('a{', '');
                label = label.replace('}', '');
                console.log(label);
                var chave = { chave: variavel, label: label, value: null };
                formulaTemp = formulaTemp.replace(variavel, '');
                if (!$scope.contains(lista, variavel)) {
                    $scope.chaves.push(chave);
                    lista.push(variavel);
                    console.log("adicionado");
                }
                reg = new RegExp(variaveis);
            }

        }

        $scope.contains = function (lista, valor) {
            if (null != lista && null != valor) {
                for (i = 0; i < lista.length; i++) {
                    var valorLista = lista[i];
                    if (null != valorLista && valorLista == valor) {
                        return true;
                    }
                }
            }
            return false;
        }

        $scope.isVariaveisPreenchidas = function () {
            $rootScope.$broadcast('preloader:hide');
            for (i = 0; i < $scope.chaves.length; i++) {
                var valores = $scope.chaves[i].value;
                if (valores == null || valores == undefined) {
                    $scope.showSimpleToast('As variáveis precisam ser todas preenchidas');
                    return false;
                }
            }
            return true;
        }

        $scope.querySearchConta = function (valor, tipo) {

            var deferred = $q.defer();
            var config = {
                params: {
                    tipo: tipo,
                    valor: valor
                }
            }
            GenericoService.GetAll('/contasContabeis/porTipo', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.contas = response.data;
                        deferred.resolve(response.data);
                    } else {
                        $scope.contas = [];
                    }
                });
            return deferred.promise;
        };

        $scope.anoAtual = function () {
            var hoje = new Date();
            return hoje.getFullYear();
        }

        $scope.contaDebitoSelecionada = function (cd) {
            if (cd.id)
                $scope.verba.contaDebito = cd.conta;
            else
                $scope.verba.contaDebito = cd;
        }

        $scope.contaCreditoSelecionada = function (cc) {
            if (cc.id)
                $scope.verba.contaCredito = cc.conta;
            else
                $scope.verba.contaCredito = cc
        }

        GenericoService.GetAll('/centrosCustos')
            .then(
                function (response) {
                    if (response.status === 200) {
                        $scope.centros = response.data.content;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

    }

})();
