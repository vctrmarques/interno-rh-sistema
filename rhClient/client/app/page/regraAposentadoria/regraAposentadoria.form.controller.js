(function () {
    'use strict';

    angular.module('app.page')
        .controller('regraAposentadoriaFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', regraAposentadoriaFormCtrl]);

    function regraAposentadoriaFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

        $scope.modalidadesAposentadoria = [
            { label: 'Geral', value: 'GERAL' },
            { label: 'Específica', value: 'ESPECIFICA' }
        ];

        $scope.tiposVigencia = [
            { label: 'Ignorar', value: 'IGNORAR' },
            { label: 'Até', value: 'ATE' },
            { label: 'Antes', value: 'ANTES' },
            { label: 'Depois', value: 'DEPOIS' }
        ];

        var tempoDeContribuicaoTotal = 35;


        // do {
        //     debugger

        //     var idade = 60 - Math.abs((Math.abs(23 - 11417) / 365.25) - tempoDeContribuicaoTotal);
        //     var idadeMathFlor = Math.floor(idade);

        //     // alert(idadeMathFlor);

        //     var tempoDeContribuicaoUsada = tempoDeContribuicaoTotal;

        //     tempoDeContribuicaoTotal = tempoDeContribuicaoTotal + 1;

        //     var soma = idadeMathFlor + tempoDeContribuicaoUsada;

        // } while (soma < 95)

        $scope.tiposAposentadoria = [{ label: 'Voluntária', value: 'VOLUNTARIA' }, { label: 'Compulsória', value: 'COMPULSORIA' }, { label: 'Invalidez', value: 'INVALIDEZ' }];
        $scope.tiposRegra = [{ label: 'Convencional', value: 'CONVENCIONAL' }, { label: 'Transição', value: 'TRANSICAO' }];

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

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/regraAposentadoria/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.regraAposentadoria = response.data;

                    if ($scope.regraAposentadoria.vigencia) {
                        $scope.regraAposentadoria.vigencia = moment(response.data.vigencia);
                    }
                } else {
                    $scope.showSimpleToast("RegraAposentadoria não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/regraAposentadoria/gestao');
        }

        $scope.updateNomeModalidade = function () {
            if ($scope.regraAposentadoria.modalidadeAposentadoria === 'GERAL') {
                $scope.regraAposentadoria.modalidadeAposentadoriaNome = null;
            }
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.regraAposentadoria.id) {
                GenericoService.Update('/regraAposentadoria', $scope.regraAposentadoria, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/regraAposentadoria/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/regraAposentadoria/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/regraAposentadoria', $scope.regraAposentadoria, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/regraAposentadoria/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/regraAposentadoria/formulario/' + $state.params.id);
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