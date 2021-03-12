(function () {
    'use strict';

    angular.module('app.page')
        .controller('verbaFormCtrl', ['$q', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', verbaFormCtrl]);


    function verbaFormCtrl($q, $scope, $mdToast, $location, $state, $rootScope, GenericoService, EnumService) {
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) { }
            },
            function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        $scope.list = {
            "listRotinas": [],
            "listaAliquotas": [],
            "listaAtributos": [],
            "listaVerbas": []
        };

        $scope.verba = {
            formula: ''
        }

        $scope.detalhes = false;
        $scope.verbaIncidencia = {};
        $scope.incidencias = [];

        GenericoService.GetAll('/listaVerbas').then(
            function (response) {
                if (response.data && response.data.length > 0) {
                    response.data.forEach(item => {
                        $scope.list.listRotinas.push({ valor: item.descricaoFormulaFormatada, basico: false });
                    });
                }
            }
        );

        GenericoService.GetAll('/aliquotas').then(
            function (response) {
                if (response.data && response.data.length > 0) {
                    $scope.list.listaAliquotas = response.data;
                }
            }
        );

        GenericoService.GetAll('/atributosFormula').then(
            function (response) {
                if (response.data && response.data.length > 0) {
                    response.data.forEach(item => {
                        $scope.list.listaAtributos.push({ valor: item.label, basico: false });
                    });
                }
            }
        );

        EnumService.Get("TipoVerba").then(function (dados) {
            $scope.tipoVerbas = dados;
        });

        EnumService.Get("OperadoresFormulaEnum").then(function (dados) {
            $scope.operadoresFormula = [];

            dados.forEach(item => {
                $scope.operadoresFormula.push({ valor: item.label, basico: false });
            });

            $scope.operadoresFormula.push({ valor: '+', basico: true });
            $scope.operadoresFormula.push({ valor: '-', basico: true });
            $scope.operadoresFormula.push({ valor: '/', basico: true });
            $scope.operadoresFormula.push({ valor: '*', basico: true });
            $scope.operadoresFormula.push({ valor: '%', basico: true });
            $scope.operadoresFormula.push({ valor: '(', basico: true });
            $scope.operadoresFormula.push({ valor: ')', basico: true });
            $scope.operadoresFormula.push({ valor: '>', basico: true });
            $scope.operadoresFormula.push({ valor: '<', basico: true });
            $scope.operadoresFormula.push({ valor: '<=', basico: true });
            $scope.operadoresFormula.push({ valor: '>=', basico: true });
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

        EnumService.Get("IdentificacaoVerbaEnum").then(function (dados) {
            $scope.identificadorVerbas = dados;
        });

        $scope.searchAttribuites = function (term) {
            var choicesMatch = [];
            var choices = [];

            angular.forEach($scope.operadoresFormula, function (item) {
                choices.push(item);
            });

            angular.forEach($scope.list.listaAtributos, function (item) {
                choices.push(item);
            });

            angular.forEach($scope.list.listRotinas, function (item) {
                choices.push(item);
            });

            angular.forEach(choices, function (item) {
                if (item.valor.toUpperCase().indexOf(term.toUpperCase()) >= 0)
                    choicesMatch.push(item);
            });
            $scope.choices = choicesMatch;
            return choicesMatch;
        };

        $scope.getAttribuiteTextRaw = function (operador) {
            if (operador.valor === 'DATA')
                return operador.basico ? operador.valor + "('YYYY-MM-DD') " : "@" + operador.valor + "('YYYY-MM-DD') ";
            else
                return operador.basico ? operador.valor + " " : "@" + operador.valor + " ";
        };

        $scope.montaFormula = function (operador) {
            if ($scope.verba.formula == undefined)
                $scope.verba.formula = '';

            if (operador.valor === 'DATA')
                $scope.verba.formula += operador.basico ? operador.valor + "('YYYY-MM-DD') " : "@" + operador.valor + "('YYYY-MM-DD') ";
            else
                $scope.verba.formula += operador.basico ? operador.valor + " " : "@" + operador.valor + " ";

        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');

            GenericoService.GetById('/verba/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');

                if (response.status === 200) {
                    $scope.verba = response.data;

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

        $scope.querySearchVerbaAssociada = function (query) {
            var deferred = $q.defer();
            var config = { params: { codigo: query } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/verba/search', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);

                            }
                        });
                } else {
                    $scope.verbaAssociada = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };

        $scope.verbaAssociadaSelecionada = function () {
            if ($scope.verbaAssociada) {
                $scope.verba.verbaAssociadaId = $scope.verbaAssociada.id;
            }
        };

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/verba/gestao');
        }

        $scope.save = function () {
            $scope.preencheIncidencias();
            $rootScope.$broadcast('preloader:active');

            if ($scope.verba.identificadorVerba && !$scope.verba.verbaAssociadaId) {
                $scope.showSimpleToast("Favor selecionar uma verba Associada ou desmarque a Identificação da Verba.");
                return;
            }

            if ($scope.verba.verbaAssociadaId) {
                $scope.verba.verbaAssociadaId = $scope.verba.verbaAssociadaId.id;
            }
            
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

        $scope.preencheIncidencias = function () {
            $scope.verba.incidenciasIds = [];
            $scope.incidencias.forEach(function (value) {
                if (value) {
                    $scope.verba.incidenciasIds.push(value.id);
                }
            });
        }

        $scope.adicionarVerbaIncidente = function () {
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

        $scope.carregarListaVerbas = function () {
            GenericoService.GetAllDropdown('verbas', function (response) {
                if (response.status === 200) {
                    $scope.list.listaVerbas = response.data.content;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            })
        };
        $scope.carregarListaVerbas();

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
