(function () {
    'use strict';

    angular.module('app.page')
        .controller('consignadoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', consignadoFormCtrl]);

    function consignadoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        $scope.modalidades = [{nome: "Aposentados"}, 
        {nome: "Pensionistas do INSS"},
        {nome: "Servidores Públicos"}];

        $scope.acessaTela();
        $scope.detalhes = false;

        $scope.states = [
            { value: "AC", label: "Acre" },
            { value: "AL", label: "Alagoas" },
            { value: "AP", label: "Amapá" },
            { value: "AM", label: "Amazonas" },
            { value: "BA", label: "Bahia" },
            { value: "CE", label: "Ceará" },
            { value: "DF", label: "Distrito Federal" },
            { value: "ES", label: "Espírito Santo" },
            { value: "GO", label: "Goiás" },
            { value: "MA", label: "Maranhão" },
            { value: "MT", label: "Mato Grosso" },
            { value: "MS", label: "Mato Grosso do Sul" },
            { value: "MG", label: "Minas Gerais" },
            { value: "PA", label: "Pará" },
            { value: "PB", label: "Paraíba" },
            { value: "PR", label: "Paraná" },
            { value: "PE", label: "Pernambuco" },
            { value: "PI", label: "Piauí" },
            { value: "RJ", label: "Rio de Janeiro" },
            { value: "RN", label: "Rio Grande do Norte" },
            { value: "RS", label: "Rio Grande do Sul" },
            { value: "RO", label: "Rondônia" },
            { value: "RR", label: "Roraima" },
            { value: "SC", label: "Santa Catarina" },
            { value: "SP", label: "São Paulo" },
            { value: "SE", label: "Sergipe" },
            { value: "TO", label: "Tocantins" }
        ];

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/consignado/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.consignado = response.data;
                    if($scope.consignado.banco != null){
                        $scope.consignado.bancoId =  $scope.consignado.banco.id;
                    }
                    $scope.consignado.centroCustoId = $scope.consignado.centroCusto.id;
                    if($scope.consignado.unidadeFederativa != null){
                        $scope.consignado.unidadeFederativaId = $scope.consignado.unidadeFederativa.id;
                    }

                    if($scope.consignado.dataCompetenciaProcessamento){
                        $scope.consignado.dataCompetenciaProcessamento = moment().add(response.data.dataCompetenciaProcessamento);
                    }
                } else {
                    $scope.showSimpleToast("Consignado não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/consignado/gestao');
        }

        $scope.listCentroCusto = {
            "data": []
        };

        $scope.listBanco = {
            "data": []
        };

        $scope.listUnidadeFederativa = {
            "data": []
        };

        $scope.CentroCusto = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCentroCustos', function (response) {
                if (response.status === 200) {
                    $scope.listCentroCusto.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.Banco = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaBancos', function (response) {
                if (response.status === 200) {
                    $scope.listBanco.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.unidadeFederativa = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUnidadesFederativas', function (response) {
                if (response.status === 200) {
                    $scope.listUnidadeFederativa.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.Banco();
        $scope.CentroCusto();
        $scope.unidadeFederativa();

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.consignado.id) {
                GenericoService.Update('/consignado',$scope.consignado, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/consignado/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('consignado/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/consignado', $scope.consignado, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/consignado/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/consignado/formulario');
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