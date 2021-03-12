(function () {
    'use strict';

    angular.module('app.page')
        .controller('recisaoContratoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', '$q', '$mdDialog', '$mdMedia', 'GenericoService', recisaoContratoFormCtrl]);

    function recisaoContratoFormCtrl($scope, $mdToast, $location, $state, $rootScope, $q, $mdDialog, $mdMedia, GenericoService) {

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

        $scope.list = { 
            "motivoDesligamento": []
        };

        $scope.acessaTela();
        $scope.bloquear = true;

        $scope.recisaoContrato = {};

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/recisaoContrato/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.recisaoContrato = response.data;
                    $scope.funcionario = $scope.recisaoContrato.funcionarioResponse;
                    $scope.funcionarioSelecionado();
                    $scope.recisaoContrato.dataDesligamento = moment($scope.recisaoContrato.dataDesligamento);
                    $scope.recisaoContrato.dataHomologacao = moment($scope.recisaoContrato.dataHomologacao);
                    $scope.recisaoContrato.dataPagamento = moment($scope.recisaoContrato.dataPagamento);
                    $scope.bloquear = false;
                } else {
                    $scope.showSimpleToast("Recisão não encontrado na base");
                }
            });
        } else {
            $scope.recisaoContrato.avisoPrevio = false;
            $scope.recisaoContrato.feriasVencidas = false;
        }

        $scope.querySearchFuncionario = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/funcionario/semRecisaoEfetivada', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.funcionarios = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        } 
        
        $scope.funcionarioSelecionado = function() {
            if($scope.funcionario){
                $scope.recisaoContrato.funcionarioId = $scope.funcionario.id;
                $scope.recisaoContrato.funcionarioNome = $scope.funcionario.nome;
                $scope.recisaoContrato.emailResponsavel = $scope.funcionario.emailPessoal;
                $scope.funcionario.dataAdmissao = moment($scope.funcionario.dataAdmissao);
            } 
        }  

        $scope.MotivoDesligamento = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaMotivosDesligamentos', function (response) {
                if (response.status === 200) {
                    $scope.list.motivoDesligamento = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            console.log($scope.recisaoContrato);
            
            if ($scope.recisaoContrato.id) {
                GenericoService.Update('/recisaoContrato', $scope.recisaoContrato, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/recisaoContrato/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
            } else {
                GenericoService.Create('/recisaoContrato', $scope.recisaoContrato, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/recisaoContrato/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/recisaoContrato/formulario');
                    }
                });
            }
        }

        $scope.MotivoDesligamento();

        $scope.goBack = function () {
            $location.path('/recisaoContrato/gestao');
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