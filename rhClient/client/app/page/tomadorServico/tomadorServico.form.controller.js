(function () {
    'use strict';

    angular.module('app.page')
        .controller('tomadorServicoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', tomadorServicoFormCtrl]);

    function tomadorServicoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/tomadorServico/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {

                    $scope.tomadorServico = response.data;
                    if($scope.tomadorServico.codigoRecolhimento)
                        $scope.tomadorServico.codigoRecolhimentoId =  $scope.tomadorServico.codigoRecolhimento.id;
                    if($scope.tomadorServico.codigoPagamentoGps)
                        $scope.tomadorServico.codigoPagamentoGpsId = $scope.tomadorServico.codigoPagamentoGps.id; 
                } else {
                    $scope.showSimpleToast("Tomador de Serviço não encontrado na base");
                }
            });
        } else {
            $scope.tomadorServico = {
                tipoInscricao: "CNPJ"
            }
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/tomadorServico/gestao');
        }

        $scope.detalhesCompensacao = function (){
            $location.path('compensacao/detalhes/'+$scope.tomadorServico.id+'/true')
        }

        $scope.listCodigoPagamentoGps = {
            "data": []
        };

        $scope.listCodigoRecolhimento = {
            "data": []
        };

        $scope.CodigosRecolhimento = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCodigosRecolhimento', function (response) {
                if (response.status === 200) {
                    $scope.listCodigoRecolhimento.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.CodigoPagamentoGps = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCodigosPagamentosGps', function (response) {
                if (response.status === 200) {
                    $scope.listCodigoPagamentoGps.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.CodigoPagamentoGps();
        $scope.CodigosRecolhimento();

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.tomadorServico.id) {
                GenericoService.Update('/tomadorServico',$scope.tomadorServico, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tomadorServico/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('tomadorServico/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/tomadorServico', $scope.tomadorServico, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tomadorServico/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tomadorServico/formulario');
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