(function () {
    'use strict';

    angular.module('app.page')
        .controller('responsavelLegalFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', responsavelLegalFormCtrl]);

    function responsavelLegalFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                    }
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }

        $scope.list ={
            "banco": [],
            "unidadeFederativa": [],
            "municipio": []
        }

        $scope.acessaTela();
        $scope.detalhes = false;

        $scope.getMunicipios = function (id) {
            $scope.getMunicipiosByUnidadeFederativa(id);
        }

        $scope.getMunicipiosByUnidadeFederativa = function (id) {
            GenericoService.GetAllDropdownById(id, function (response) {
                if (response.status === 200) {
                    $scope.list.municipio = response.data;
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                }
            });
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/responsavelLegal/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.responsavelLegal = response.data;

                    if($scope.responsavelLegal.agenciaBancaria){
                        $scope.banco = $scope.responsavelLegal.agenciaBancaria.banco;
                        $scope.bancoSelecionado();
                        $scope.agencia = $scope.responsavelLegal.agenciaBancaria;
                        $scope.agenciaSelecionada();
                    } else {
                        $scope.banco = $scope.responsavelLegal.banco;
                        $scope.bancoSelecionado();
                    }
                    
                    if($scope.responsavelLegal.unidadeFederativa){
                        $scope.responsavelLegal.unidadeFederativaId = $scope.responsavelLegal.unidadeFederativa.id;
                        $scope.getMunicipios($scope.responsavelLegal.unidadeFederativa.id);
                    }
                    if($scope.responsavelLegal.municipio){
                        $scope.responsavelLegal.municipioId = $scope.responsavelLegal.municipio.id;
                    }

                } else {
                    $scope.showSimpleToast("Responsáve lLegal não encontrada na base");
                }
            });
        }

        $scope.tiposResponsaveis = [
            {tipo: "Curador"},
            {tipo: "Genitor"},
            {tipo: "Guardião"},
            {tipo: "Menor"},
            {tipo: "Procurador"},
            {tipo: "Tutor"},
            {tipo: "Outros"}
        ]

        $scope.tiposContas = [
            {tipo: "Antiga Conta Salário"},
            {tipo: "Cheque Salário"},
            {tipo: "Conta Corrente"},
            {tipo: "Conta Poupança"},
            {tipo: "Conta Salário"},
            {tipo: "EM BRANCO"}
        ]


        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/responsavelLegal/gestao');
        }

        $scope.querySearchBanco = function (query) {
            var deferred = $q.defer();
            var config = {
                params: {
                    search: query,
                }
            }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/banco/searchComplete', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);
                            }
                        });
                } else {
                    $scope.banco = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };

        $scope.bancoSelecionado = function () {
            if ($scope.banco) {
                $scope.responsavelLegal.bancoId = $scope.banco.id;
            }
        };

        $scope.querySearchAgencia = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query, bancoId: $scope.responsavelLegal.bancoId } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/agencia/searchComBanco', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);

                            }
                        });
                } else {
                    $scope.agencia = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };

        $scope.agenciaSelecionada = function () {
            if ($scope.agencia) {
                $scope.responsavelLegal.agenciaBancariaId = $scope.agencia.id;
            }
        };

        $scope.CodigoPagamentoGps = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCodigosPagamentosGps', function (response) {
                if (response.status === 200) {
                    $scope.list.codigoPagamentoGps = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.Cnae = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCnaes', function (response) {
                if (response.status === 200) {
                    $scope.list.cnae = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.UnidadeFederativa = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUnidadesFederativas', function (response) {
                if (response.status === 200) {
                    $scope.list.unidadeFederativa = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.UnidadeFederativa();

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.responsavelLegal.id) {
                GenericoService.Update('/responsavelLegal',$scope.responsavelLegal, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/responsavelLegal/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('responsavelLegal/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/responsavelLegal', $scope.responsavelLegal, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/responsavelLegal/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/responsavelLegal/formulario');
                    }
                });
            }
        }

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