(function () {
    'use strict';

    angular.module('app.page')
        .controller('arrecadacaoAliquotaFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', '$filter', 'RestService', 'EnumService', arrecadacaoAliquotaFormCtrl]);

    function arrecadacaoAliquotaFormCtrl($scope, $mdToast, $location, $state, $rootScope, $filter, RestService, EnumService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Alíquota e encagos' } })
        .then(function (response) {
            if (response.status === 200 && response.data) {
                $scope.permissao = response.data;
                if (!$scope.permissao.podeGerenciar)
                    $location.path('page/403');
            }
        }, function errorCallback(response) {
            if (response.status === 400) {
                $scope.showSimpleToast(response.data.message);
            }
        });

        $scope.lista = {};
        $scope.lista.todos = [];
        $scope.arrecadacaoAliquota = {}
        $scope.arrecadacaoAliquota.encargos = [];
        $scope.lista.encargo = [];

        EnumService.Get("ArrecadacaoAliquotaEncargoEnum").then(function (dados) {
            angular.forEach(dados, function (e) {
                if(e.value != 'JUROS_PATRONAL_13' && e.value != 'JUROS_SERVIDOR' && e.value != 'SERVIDOR_13'){
                    $scope.lista.todos.push(e);
                    $scope.lista.encargo = angular.copy($scope.lista.todos);
                }
            });
        });
        
        function carregaEncargos() {
            $scope.lista.encargo = [];
            $scope.lista.encargo = angular.copy($scope.lista.todos);
        }

        
        carregaEncargos();
        
        function getCheckEncargos() {
            if($scope.checkListEncargos()){
                angular.forEach($scope.arrecadacaoAliquota.encargos, function (e) {
                    angular.forEach($scope.lista.encargo, function (f) {
                        if(e.aliquotaEncargo == f.label){
                            var index = $scope.lista.encargo.indexOf(f);
                            $scope.lista.encargo.splice(index, 1);
                        }
                    });
                });
            }
        }
        
        function carregaEncargosRemocao() {
            $scope.lista.encargo = [];
            $scope.lista.encargo = angular.copy($scope.lista.todos);
            getCheckEncargos();
        }

        $scope.detalhes = false;
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            RestService.Get('/arrecadacaoAliquota/' + $state.params.id)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    $scope.arrecadacaoAliquota = response.data;
                    $scope.arrecadacaoAliquota.inicioVigencia = moment(response.data.inicioVigencia);
                    $scope.arrecadacaoAliquota.fimVigencia = moment(response.data.fimVigencia);
                    
                    if($scope.arrecadacaoAliquota.encargos && $scope.arrecadacaoAliquota.encargos.length > 0){
                        getCheckEncargos();
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        }

        $scope.adicionarEncargo = function () {
            if (!$scope.checkPodeAdicionar()) {
                if(!existeEncargo($scope.encargo)){
                    
                    let encargo = { 
                        aliquotaEncargo: $scope.encargo.label,
                        aliquota: $scope.aliquota,
                        other: $scope.encargo.other,
                    };
                    $scope.arrecadacaoAliquota.encargos.push(encargo);
                    
                    if($scope.encargo.value == 'JUROS_PATRONAL'){
                        let encargo1 = {
                            aliquotaEncargo: 'Juros patronal 13º',
                            aliquota: $scope.aliquota,
                            other: 'e',
                        }
                        $scope.arrecadacaoAliquota.encargos.push(encargo1);
                        let encargo2 = {
                            aliquotaEncargo: 'Juros servidor',
                            aliquota: $scope.aliquota,
                            other: 'h',
                        }
                        $scope.arrecadacaoAliquota.encargos.push(encargo2);
                        let encargo3 = {
                            aliquotaEncargo: 'Servidor 13º',
                            aliquota: $scope.aliquota,
                            other: 'j',
                        }
                        $scope.arrecadacaoAliquota.encargos.push(encargo3);
                    }
                    

                    
                    delete $scope.encargo;
                    delete $scope.aliquota;

                    getCheckEncargos();
                }
            }
        }

        $scope.removerEncargo = function (item) {
            if(item.aliquotaEncargo == 'Juros patronal'){
                let listaJurosPatronal = ['Juros patronal', 'Juros patronal 13º', 'Juros servidor', 'Servidor 13º'];
                angular.forEach(listaJurosPatronal, function (f) {
                    limpaEncargo(f);
                });
            } else {
                limpaEncargo(item.aliquotaEncargo);
            }
            carregaEncargosRemocao();
        }

        function limpaEncargo(valor){
            angular.forEach($scope.arrecadacaoAliquota.encargos, function (f) {
                if(f.aliquotaEncargo == valor){
                    var ix = $scope.arrecadacaoAliquota.encargos.indexOf(f);
                    $scope.arrecadacaoAliquota.encargos.splice(ix, 1);
                }
            });
        }

        function existeEncargo(encargo) {
            var existe = false;
            angular.forEach($scope.arrecadacaoAliquota.encargos, function (e) {
                if (!existe && e.aliquotaEncargo == encargo) {
                    $scope.showSimpleToast("Este encargo já foi adicionado");
                    existe = true;
                }
            });
            return existe;
        }

        $scope.checkPodeExcluir = function(item) {
            if(item != 'Juros patronal 13º' && item != 'Juros servidor' && item != 'Servidor 13º'){
                return true;
            } else {
                return false;
            }
        }

        $scope.checkPodeAdicionar = function() {
            if($scope.detalhes){
                return false;
            } else {
                return ($scope.encargo && $scope.aliquota) ? false : true;
            }
        }

        $scope.checkListEncargos = function() {
            return $scope.arrecadacaoAliquota.encargos.length > 0 ? true : false;
        }

        $scope.podeSalvar = function () {
            if($scope.arrecadacaoAliquota.encargos.length == 8){
                return $scope.detalhes ? false : true;
            } else {
                return false;
            }
        }

        $scope.save = function () {
            if($scope.podeSalvar()) {
           
                $rootScope.$broadcast('preloader:active');
                if ($scope.arrecadacaoAliquota.id) {
                    RestService.Update('/arrecadacaoAliquota', $scope.arrecadacaoAliquota)
                        .then(function successCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 201 && response.data.success) {
                                $scope.showSimpleToast(response.data.message);
                                $location.path('/arrecadacaoAliquota/gestao');
                            }
                        }, function errorCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 400) {
                                $scope.showSimpleToast(response.data.message);
                            }
                        });
                } else {
                    RestService.Create('/arrecadacaoAliquota', $scope.arrecadacaoAliquota)
                        .then(function successCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 201 && response.data.success) {
                                $scope.showSimpleToast(response.data.message);
                                $location.path('/arrecadacaoAliquota/gestao');
                            }
                        }, function errorCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 400) {
                                $scope.showSimpleToast(response.data.message);
                            }
                        });
                }
            } else {
                $scope.showSimpleToast("Necessário adicionar o número total de Alíquotas/Encargos disponíveis na seleção");
            }

        }

        $scope.goBack = function () {
            $location.path('/arrecadacaoAliquota/gestao');
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