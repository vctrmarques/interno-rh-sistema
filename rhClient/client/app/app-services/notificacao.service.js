(function () {
    'use strict';

    angular
        .module('app.page')
        .factory('NotificacaoService', NotificacaoService);

    NotificacaoService.$inject = ['$q', '$http', '$cookies', '$rootScope', '$timeout', 'configValue'];
    function NotificacaoService($q, $http, $cookies, $rootScope, $timeout, configValue) {

        var service = {};

        service.GetAll = GetAll;
        service.GetById = GetById;
        service.VisualizaNotificacao = VisualizaNotificacao;
        service.ContadorNotificacao = ContadorNotificacao;

        return service;

        function GetAll(pageValue, sizeValue, order, nomeBusca) {
            var config = { params: { order: order, page: pageValue - 1, size: sizeValue } };
            if (nomeBusca && nomeBusca.length > 0) {
                config.params.nome = nomeBusca;
            }
            return $http.get(configValue.baseUrl + '/api/notificacaos', config);
        }

        function GetById(id, callback) {
            $http.get(configValue.baseUrl + '/api/notificacao/' + id)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function VisualizaNotificacao(notificacaoId, callback) {
            $http.get(configValue.baseUrl + '/api/visualizaNotificacao/' + notificacaoId
            ).then(function successCallback(response) {
                callback(response);
            }, function errorCallback(response) {
                callback(response);
            });
        }

        function ContadorNotificacao(callback) {
            $http.get(configValue.baseUrl + '/api/contadorNotificacao/'
            ).then(function successCallback(response) {
                callback(response);
            }, function errorCallback(response) {
                callback(response);
            });
        }

    }
})();