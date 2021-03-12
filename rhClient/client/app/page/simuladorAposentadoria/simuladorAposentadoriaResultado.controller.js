(function () {
    'use strict';

    angular.module('app.page')
        .controller('simuladorAposentadoriaResultadoFormCtrl', ['$scope', '$mdToast', '$location', 'SimuladorService', simuladorAposentadoriaResultadoFormCtrl]);

    function simuladorAposentadoriaResultadoFormCtrl($scope, $mdToast, $location, SimuladorService) {

        $scope.resultado = {};
        $scope.init = function () {

            $scope.simuladorAposentadoria = SimuladorService.getSimuladorAposentadoria();
            $scope.resultado = SimuladorService.getSimuladorAposentadoriaResultado();

            if (!$scope.resultado) {
                window.scrollTo(0, 0);
                $location.path('/simulador/aposentadoria');
            }

        }
        $scope.init();

        $scope.novaConsulta = function () {
            window.scrollTo(0, 0);
            SimuladorService.setSimuladorAposentadoria(null);
            $location.path('/simulador/aposentadoria');
        }

        $scope.alterarInforacoes = function () {
            window.scrollTo(0, 0);
            SimuladorService.setSimuladorAposentadoria($scope.simuladorAposentadoria);
            $location.path('/simulador/aposentadoria');
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(5000)
            );
        };

    }

})(); 