(function () {
    'use strict';

    angular.module('app.page')
        .controller('tipoFolhaFormCtrl', ['$scope', '$mdToast', '$q', '$location', '$state', '$rootScope', 'GenericoService', tipoFolhaFormCtrl]);

    function tipoFolhaFormCtrl($scope, $mdToast, $q, $location, $state, $rootScope, GenericoService) {

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
        $scope.tipoFolha = {
                "verbasId": []
            };
        $scope.verbasTipoFolha = [];
        
        $scope.querySearchVerba = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/verba/search', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                $scope.verbas = response.data;
                                deferred.resolve(response.data);

                            }
                        });
                } else {
                    $scope.verbas = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };
        
        $scope.adicionarVerba = function (){
            if($scope.verba){
            	$scope.tipoFolha.verbasId.push($scope.verba.id);
                $scope.verbasTipoFolha.push($scope.verba);
            }   
        }
        
        $scope.removerVerba = function(item) { 
        	  var index = $scope.verbasTipoFolha.indexOf(item);
        	  $scope.verbasTipoFolha.splice(index, 1);
        }
        
        

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/tipoFolha/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.tipoFolha = response.data;
                    $scope.tipoFolha.verbasId = [];
                    angular.forEach($scope.tipoFolha.verbas, function(e){
                    	$scope.tipoFolha.verbasId.push(e.id);
                    });
                    // $scope.consignado.bancoId =  $scope.consignado.banco.id;
                    // $scope.consignado.centroCustoId = $scope.consignado.centroCusto.id; 
                } else {
                    $scope.showSimpleToast("Tipo de Folha não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/tipoFolha/gestao');
        }

        $scope.save = function () {
        	console.log($scope.tipoFolha);
            $rootScope.$broadcast('preloader:active');
            if ($scope.tipoFolha.id) {
                GenericoService.Update('/tipoFolha', $scope.tipoFolha, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoFolha/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('tipoFolha/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/tipoFolha', $scope.tipoFolha, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoFolha/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoFolha/formulario');
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