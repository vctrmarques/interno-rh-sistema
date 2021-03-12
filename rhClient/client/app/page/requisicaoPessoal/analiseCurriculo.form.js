(function () {
    'use strict';

    angular.module('app.page').controller('analiseCurriculoFormCtrl', 
        ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', '$state', '$location', 'GenericoService', 'EnumService', analiseCurriculoFormCtrl]
    );

    function analiseCurriculoFormCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, $state, $location, GenericoService, EnumService) {
        $scope.entity = undefined;
        $scope.candidatos = [];

        function init() {
            $scope.consultarCandidatos();
        };

        $scope.consultarCandidatos = function() {
            $rootScope.$broadcast('preloader:active');
            $scope.numeroProcesso = $state.params.id;

            GenericoService.GetById('/requisicaoPessoal/analiseCurriculo/candidatos/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                $scope.candidatos = response.data;
            });
        };

        $scope.goBack = function() {
            $location.path('/requisicaoPessoal/gestao');
        };

        $scope.getColorAprovacao = function(item) {
            if (item.situacao == 'APROVADO') {
                return 'green';
            } else {
                return 'black';
            }
        };

        $scope.getColorReprovacao = function(item) {
            if (item.situacao == 'REPROVADO') {
                return 'red';
            } else {
                return 'black';
            }
        };

        $scope.preparAprovar = function(event, item) {
            $scope.showDialog(event, true, item.id);
        };

        $scope.preparReprovar = function(event, item) {
            $scope.showDialog(event, false, item.id);
        };

        $scope.aprovar = function(req) {
            $scope.respostaAnalise('/requisicaoPessoal/analiseCurriculo/aprovar', req);
        };

        $scope.reprovar = function(req) {
            $scope.respostaAnalise('/requisicaoPessoal/analiseCurriculo/reprovar', req);
        };

        $scope.respostaAnalise = function(url, param) {
            GenericoService.Update(url, param, function (response) {
                $rootScope.$broadcast('preloader:hide');

                var result = response.data;
                $scope.consultarCandidatos();
            });
        };

        $scope.concluir = function() {
            $rootScope.$broadcast('preloader:active');

            GenericoService.Update('/requisicaoPessoal/analisarCurriculo', $scope.requisicaoPessoal, function (response) {
                $rootScope.$broadcast('preloader:hide');

                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/requisicaoPessoal/gestao');
                }
            });
        };

        $scope.showDialog = function (event, isAprovar, id) {
            var $this =  $scope;

            $mdDialog.show({
                require: { container: '^^analiseCurriculoFormCtrl' },
                templateUrl: 'app/page/requisicaoPessoal/comentarioCurriculo.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: event,
                clickOutsideToClose: true,
                controller: ['$scope', '$q', function($scope, $q) {

                    $scope.comentario = '';

                    $scope.clickDialog = function() {
                        var req = {
                            idRequisicaoPessoalCandidato: id,
                            comentarioCurriculo: $scope.comentario
                        };

                        if (isAprovar) {
                            $this.aprovar(req);
                        } else {
                            $this.reprovar(req);
                        }

                        $scope.cancel();
                    };

                    $scope.cancel = function() {
                        $mdDialog.cancel();
                    };
                }]
            });
        };

        init();
    }
})();
