(function () {
    'use strict';

    angular.module('app.page')
        .controller('pensaoAlimenticiaFormCtrl', ['$state', '$location', '$q', 'configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', pensaoAlimenticiaFormCtrl]);

    function pensaoAlimenticiaFormCtrl($state, $location, $q, configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {

            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        $scope.botoesGestao = false;
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.botoesGestao = true;
                }
            });

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.pensao = {};

        $scope.list = {
            "count": 0,
            "data": []
        };

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/pensao/funcionario/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcionario = response.data;
                    angular.forEach($scope.funcionario.pensoes, function (p) {
                        if (p.dataFinalPagamento == null || moment(p.dataFinalPagamento) > new Date()) {
                            p.situacao = 'Vigente';
                        } else {
                            p.situacao = 'Encerrada';
                        }
                    });
                } else {
                    $scope.showSimpleToast("Funcionário não encontrado na base");
                }
            });
        }

        $scope.funcionarioSelect = function () {
            $rootScope.$broadcast('preloader:hide');
            if ($scope.funcionario) {
                $scope.pensao.funcionarioId = $scope.funcionario.id;
            }
        };

        $scope.pensaoSelelecionada = function (item) {
            $scope.pensao = item;
            $scope.pensao.nascimentoAlimentando = moment($scope.pensao.nascimentoAlimentando);
            $scope.pensao.dataFinal = moment($scope.pensao.dataFinal);
            $scope.pensao.dataInicial = moment($scope.pensao.dataInicial);
            $scope.pensao.dataInicialPagamento = moment($scope.pensao.dataInicialPagamento);
            $scope.pensao.dataFinalPagamento = moment($scope.pensao.dataFinalPagamento);
            $scope.pensao.vencimento = moment($scope.pensao.vencimento);
            if (item.responsavel)
                $scope.pensao.responsavelId = item.responsavel.id;
            $scope.responsavel = item.responsavel;
            $scope.pensao.estadoId = item.estado.id;
            $scope.pensao.municipioId = item.municipio.id;
            $scope.pensao.estadoDocumentoId = item.estadoDocumento.id;
            $scope.pensao.bancoId = item.banco.id;
            $scope.pensao.verbaId = item.verba.id;
            $scope.pensao.centroCustoId = item.centroCusto.id;
            $scope.pensao.funcionarioId = item.funcionario.id;
            if (item.agencia)
                $scope.pensao.agenciaId = item.agencia.id;
        };

        $scope.querySearchFuncionario = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/funcionario/searchNomeOrMatricula', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                $scope.funcionarios = response.data;
                                deferred.resolve(response.data);

                            }
                        });
                } else {
                    $scope.funcionarios = [];
                }
            }
            return deferred.promise;
        };
        $scope.tiposPensao = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/pensao/tipo-pensao', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.tiposPensao = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }
        $scope.tiposPensao();

        $scope.tiposContaPensao = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/pensao/tipo-conta-pensao', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.tiposContaPensao = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }
        $scope.tiposContaPensao();

        $scope.tiposDesconto = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/pensao/tipo-desconto', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.tiposDesconto = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }
        $scope.tiposDesconto();

        $scope.tiposIncidenciaPrincipal = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/pensao/tipo-incidencia-pensao', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.tiposIncidenciaPrincipal = response.data;
                        $scope.tiposIncidencias = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }
        $scope.tiposIncidenciaPrincipal();

        $scope.bancos = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/bancos', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.bancos = response.data.content;
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }
        $scope.bancos();

        $scope.verbas = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/listaVerbas', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.verbas = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }
        $scope.verbas();

        $scope.centrosCusto = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/centrosCustos', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.centros = response.data.content;
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }
        $scope.centrosCusto();

        $scope.municipios = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/municipios', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.municipios = response.data.content;
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }
        $scope.municipios();

        $scope.ufs = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/unidadesFederativas', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.ufs = response.data.content;
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }
        $scope.ufs();

        $scope.responsaveis = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/responsaveisLegais', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.responsaveis = response.data.content;
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }
        $scope.responsaveis();

        $scope.agencias = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/agencias', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.agencias = response.data.content;
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }
        $scope.agencias();

        $scope.responsavelSelecionado = function (responsavel) {
            $scope.responsavel = responsavel;
        }

        $scope.regraTipoPensao = function (label) {
            if (label === 'Auxílio-reclusão')
                $scope.pensao.tipoIncidenciaPrincipalPensao = "Valor Informado";
        }
        $scope.regraTipoDesconto = function (label) {
            $scope.tiposIncidencias = [];
            if(label=="Percentual"){
                angular.forEach($scope.tiposIncidenciaPrincipal, function (t) {
                    if (t.label == "Salário Base da Categoria") {
                        $scope.tiposIncidencias.push(t);
                    } else if (t.label == "Salário Bruto") {
                        $scope.tiposIncidencias.push(t);
                    } else if (t.label == "Salário Líquido") {
                        $scope.tiposIncidencias.push(t);
                    }
                });
            }else if(label = "Valor Fixo"){
                angular.forEach($scope.tiposIncidenciaPrincipal, function (t) {
                    if (t.label == "Salário Mínimo") {
                        $scope.tiposIncidencias.push(t);
                    } else if (t.label == "Valor Informado") {
                        $scope.tiposIncidencias.push(t);
                    }
                });
            }
            
        }




        $scope.limparResponsavel = function () {
            $scope.responsavel = null;
            $scope.pensao.responsavelId = null;
            $scope.pensao.dataInicial = null;
            $scope.pensao.dataFinal = null;
            $scope.pensao.numeroProcessoResponsavel = null;
        }

        $scope.limitOptions = [5, 10, 15];

        $scope.goBack = function () {
            $location.path('/pensaoAlimenticia/gestao');
        }



        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        $scope.showConfirm = function (ev, idToDelete) {
            $scope.idToDelete = idToDelete;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão deste item?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.deleteItem();
            }, function () {
            });
        };

        $scope.deleteItem = function () {

            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/pensao/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.funcionario.pensoes = $scope.funcionario.pensoes.filter(function (e) {
                        return e.id != $scope.idToDelete;
                    });
                    $scope.pensao = null;
                }
                if (!$scope.funcionario.pensoes || $scope.funcionario.pensoes.length == 0) {
                    $location.path('/pensaoAlimenticia/gestao');
                }
            });
        }

        $scope.save = function () {
            console.log($scope.pensao);


            $rootScope.$broadcast('preloader:active');

            if ($scope.pensao.id) {
                GenericoService.Update('/pensao', $scope.pensao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/pensaoAlimenticia/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('pensaoAlimenticia/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/pensao', $scope.pensao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/pensaoAlimenticia/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/pensaoAlimenticia/formulario');
                    }
                });
            }
        }
    }



})();