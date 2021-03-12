(function () {
    'use strict';

    angular.module('app.page')
        .controller('licencaFormCtrl', ['$scope', '$http', '$timeout', '$q', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', licencaFormCtrl]);

    function licencaFormCtrl($scope, $timeout, $http, $q, $mdToast, $location, $state, $rootScope, GenericoService) {

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
        $scope.licencaMedica = {};
        $scope.funcionarios = [];
        $scope.cids = [];

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.list = {
            "afastamento": [],
            "motivoAfastamento": [],
            "crm": [],
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/licencaMedica/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    console.log(response.data);
                    
                    $scope.licencaMedica = response.data;
                    if(response.data.crm)
                        $scope.licencaMedica.crmId = response.data.crm.id;
                    $scope.licencaMedica.periodoInicial = moment(response.data.perioInicial);
                    $scope.licencaMedica.periodoFinal =  moment(response.data.periodoFinal);
                    $scope.licencaMedica.dataInspecao =  moment(response.data.dataInspecao);
                    //$scope.funcionario.empresa = response.data.funcionario.empresa;
                   // $scope.funcionario.filial = response.data.funcionario.filial;
                    $scope.licencaMedica.motivoAfastamentoId = response.data.motivoAfastamento.id;
                    $scope.licencaMedica.afastamentoId = response.data.afastamento.id;
                    $scope.funcionario = response.data.funcionario;
                    $scope.cid = response.data.cid;
                    $scope.licencaMedica.funcionarioId = response.data.funcionario.id; 
                    $scope.licencaMedica.cidId = response.data.cid.id; 
                } else {
                    $scope.showSimpleToast("Licença Médica não encontrada na base");
                }
            });
        }


        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/licencaMedica/gestao');
        }

        $scope.Afastamento = function () {

            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaAfastamentos', function (response) {
                if (response.status === 200) {
                    $scope.list.afastamento = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.Afastamento();

        $scope.MotivoAfastamento = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaMotivosAfastamentos', function (response) {
                if (response.status === 200) {
                    $scope.list.motivoAfastamento = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.MotivoAfastamento();

        $scope.save = function () {  
            $rootScope.$broadcast('preloader:active');
            if ($scope.licencaMedica.id) {
                GenericoService.Update('/licencaMedica', $scope.licencaMedica, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/licencaMedica/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('licencaMedica/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/licencaMedica', $scope.licencaMedica, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/licencaMedica/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/licencaMedica/formulario');
                    }
                });
            }
        }

        $scope.funcionarioSelect = function () {
            $rootScope.$broadcast('preloader:hide');
            $scope.licencaMedica.funcionarioId = $scope.funcionario.id;
        }

        $scope.cidSelect = function () {
            $rootScope.$broadcast('preloader:hide');
            $scope.licencaMedica.cidId = $scope.cid.id;
        }

        $scope.cidSearch = function (seach) {
            $rootScope.$broadcast('preloader:active');
            var deferred = $q.defer();
            var config = { params: { descricao: seach } }
            if (seach.length > 1) {
                GenericoService.GetAll('/classificacaoInternacionalDoencas', config).then(
                    function (response) {
                        
                        if (response.data && response.data.content.length > 0) {
                            $scope.cids = response.data.content;
                            deferred.resolve(response.data.content);
                        }
                    });
            } else if (seach.length < 3) {
                $scope.cids = [];
            }
            return deferred.promise;
        }

        $scope.searchFuncionario = function (seach) {
            $rootScope.$broadcast('preloader:active');
            var deferred = $q.defer();
            var config = { params: { search: seach } }
            if (seach.length > 2) {
                GenericoService.GetAll('/funcionario/searchNomeOrMatricula', config).then(
                    function (response) {
                        
                        if (response.data && response.data.length > 0) {
                            $scope.funcionarios = response.data;
                            deferred.resolve(response.data);
                        }
                    });
            } else if (seach.length < 3) {
                $scope.funcionarios = [];
            }
            return deferred.promise;
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        $scope.Crm = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    tipo: 'CRM'
                }
            };

            $scope.promise = GenericoService.GetAll('/crmCreas/porTipo', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.crm = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }
        
        $scope.Crm();

    }

})();