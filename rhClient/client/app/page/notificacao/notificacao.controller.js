(function () {
    'use strict';

    angular.module('app.page')
        .controller('notificacaoCtrl', ['configValue', '$timeout', 'GenericoService', 'NotificacaoService', '$http', '$scope', '$mdToast', '$mdDialog', '$rootScope', '$window', '$location', notificacaoCtrl]);

    function notificacaoCtrl(configValue, $timeout, GenericoService, NotificacaoService, $http, $scope, $mdToast, $mdDialog, $rootScope, $window, $location) {

        $('body').materialScrollTop();

        $scope.items = [];
        $scope.busy = false;
        var page = 0;

        $scope.nextPage = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.busy) return;
            $scope.busy = true;

            var config = {
                params: {
                    page: page,
                    size: 5
                }
            };

            GenericoService.GetAll('/notificacoes', config).then(
                function (response) {
                    if (response.status === 200) {
                        var items = response.data.content;
                        for (var i = 0; i < items.length; i++) {
                            $scope.items.push(items[i]);
                        }
                        page += 1;
                        if (response.data.last) {
                            $scope.busy = true;
                        } else {
                            $scope.busy = false;
                        }
                        $rootScope.$broadcast('preloader:hide');
                    }
                });

        };

        $scope.carregarContadorNotificacao = function () {
            NotificacaoService.ContadorNotificacao(function (response) {
                $scope.contadorNotificacao = response.data;
                $rootScope.contadorNotificacao = response.data;
                if ($scope.contadorNotificacao > 0) {
                    $window.document.title = '(' + $scope.contadorNotificacao + ') '+ $rootScope.pageTitle;
                } else {
                    $window.document.title = $rootScope.pageTitle;
                }
            });
        }
        $scope.carregarContadorNotificacao();

        $scope.visualizarNotificacao = function (notificacao) {
            if (!notificacao.visualizada) {
                notificacao.visualizada = true;
                $rootScope.$broadcast('preloader:active');
                NotificacaoService.VisualizaNotificacao(notificacao.id, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    $scope.carregarContadorNotificacao();
                });
            }
        }

    }

})();



