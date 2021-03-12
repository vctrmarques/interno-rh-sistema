(function () {
    'use strict';

    angular.module('app.page')
        .controller('entidadeExameFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', '$q', 'GenericoService', entidadeExameFormCtrl]);

    function entidadeExameFormCtrl($scope, $mdToast, $location, $state, $rootScope, $q, GenericoService) {

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

        $scope.entidadeExame = { exames: [] };
        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/entidadeExame/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.entidadeExame = response.data;
                    $scope.getMunicipiosByUf();
                } else {
                    $scope.showSimpleToast("EntidadeExame não encontrado na base");
                }
            });
        }

        $scope.entidadeExameTipos = [
            { label: 'Externo', value: 'EXTERNO' },
            { label: 'Interno (custo)', value: 'INTERNO_CUSTO' },
            { label: 'Interno (funcionário)', value: 'INTERNO_FUNCIONARIO' },
            { label: 'Junta Médica', value: 'JUNTA_MEDICA' }
        ];

        $scope.list = {
            "uf": [],
            "municipio": []
        }

        $scope.getMunicipiosByUf = function () {
            $rootScope.$broadcast('preloader:active');
            var config = { params: { id: $scope.entidadeExame.endereco.ufId } };
            GenericoService.GetAll('/listaMunicipios', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.list.municipio = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                    }
                });
        }

        $rootScope.$broadcast('preloader:active');
        GenericoService.GetAll('/listaUfs').then(
            function (response) {
                if (response.data && response.data.length > 0) {
                    $scope.list.uf = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/entidadeExame/gestao');
        }

        $scope.querySearchTomadorServico = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/tomadorServico/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }

        $scope.transformChip = function (chip) {
            if (angular.isObject(chip)) {
                return chip;
            }
        }

        $scope.querySearchExame = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/exame/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            $scope.entidadeExame.examesIds = [];
            var index;
            for (index = 0; index < $scope.entidadeExame.exames.length; index++) {
                var element = $scope.entidadeExame.exames[index];
                $scope.entidadeExame.examesIds.push(element.id);
            }

            $scope.entidadeExame.tomadorServicoId = $scope.entidadeExame.tomadorServico.id;

            if ($scope.entidadeExame.id) {
                GenericoService.Update('/entidadeExame', $scope.entidadeExame, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/entidadeExame/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('entidadeExame/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/entidadeExame', $scope.entidadeExame, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/entidadeExame/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/entidadeExame/formulario');
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