(function () {
    'use strict';

    angular.module('app.page')
        .controller('dependenteBeneficioFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', '$q', 'GenericoService', dependenteBeneficioFormCtrl]);

    function dependenteBeneficioFormCtrl($scope, $mdToast, $location, $state, $rootScope, $q, GenericoService) {

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
        moment.locale('pt-BR');
        $scope.list = {
            
        }
        $scope.funcionarioId = "";
        $scope.consignados = [];
        $scope.dependenteBeneficio = {
            dependente: {},
            consignados: []
        };

        $scope.validateButton = false;

        $scope.validarBotao = function() {
            if(($scope.consignado && $scope.consignado != "") && ($scope.valor && $scope.valor != "")){
                $scope.validateButton = true;
            }
        }

        $scope.consignadoSelecionado = function() {
            if($scope.consignado){
                $scope.consignado.dependenteId = $scope.dependenteBeneficio.dependente.id;
                $scope.consignado.consignadoId = $scope.consignado.id;
                $scope.consignado.valor = $scope.valor;
                $scope.consignados.push($scope.consignado);
                $scope.dependenteBeneficio.consignados.push($scope.consignado)
                delete $scope.consignado;
                delete $scope.valor;
                $scope.validateButton = false;
            } 
        }

        $scope.removerConsignado = function (index) {
            $scope.dependenteBeneficio.consignados.splice(index, 1)
            $scope.consignados.splice(index, 1);
        } 
        




        $scope.detalhes = false;
        $scope.edit = false;
        var i = 0;

        if ($state.params && $state.params.dependenteId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/dependente/' + $state.params.dependenteId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.dependenteBeneficio.dependente = response.data;
                    $scope.funcionarioId = response.data.funcionario.id;
                } else {
                    $scope.showSimpleToast("Dependente não encontrado na base");
                }
            });

            GenericoService.GetById('/dependenteBeneficios/' + $state.params.dependenteId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    for (var i = 0; i < response.data.length; i++) {
                        $scope.consignado = {}

                        $scope.consignado.descricao = response.data[i].consignado.descricao;
                        $scope.consignado.dependenteId = response.data[i].dependente.id;
                        $scope.consignado.consignadoId = response.data[i].consignado.id;
                        $scope.consignado.valor = response.data[i].valor;

                        $scope.consignados.push($scope.consignado);
                        $scope.dependenteBeneficio.consignados.push($scope.consignado);
                        delete $scope.consignado;
                    }
                } 
            });            
        }        

        $scope.querySearchConsignado = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/consignado/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        } 

        $scope.goBack = function () {
            $location.path('/dependente/formulario/'+$scope.dependenteBeneficio.dependente.funcionario.id);
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if($scope.dependenteBeneficio.consignados.length > 0){
                GenericoService.Update('/dependenteBeneficio', $scope.dependenteBeneficio.consignados, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dependente/formulario/'+$scope.funcionarioId);
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dependente/formulario');
                    }
                });
            }else {
                GenericoService.Delete('/dependenteBeneficio/' + $state.params.dependenteId, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    $scope.showSimpleToast("Benefícios do Dependente Atualizado com sucesso."); 
                    $location.path('/dependente/formulario/'+$scope.funcionarioId);
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