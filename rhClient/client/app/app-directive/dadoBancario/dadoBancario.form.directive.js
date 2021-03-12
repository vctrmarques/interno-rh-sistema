(function () {
    'use strict';

    angular.module('app.core').directive('dadoBancarioForm', ['$mdToast', '$rootScope', '$q', 'RestService', 'EnumService',
        function ($mdToast, $rootScope, $q, RestService, EnumService) {
            return {
                templateUrl: 'app/app-directive/dadoBancario/dadoBancario.form.html',
                restrict: 'E',
                scope: {
                    show: '=',
                    requiredTipoConta: '=',
                    requiredBanco: '=',
                    requiredAgencia: '=',
                    requiredConta: '=',
                    requiredDigito: '=',
                    data: '='
                },
                link: function (scope) {

                    // Busca do enum de DadoBancarioTipoContaEnum
                    EnumService.Get("DadoBancarioTipoContaEnum").then(function (dados) {
                        scope.dadoBancarioTipoContaEnumList = dados;
                    });

                    // Busca de Bancos
                    RestService.Get('/banco/search')
                        .then(function (response) {
                            if (response.status === 200 && response.data)
                                scope.bancoList = response.data;
                        }, function errorCallback(response) {
                            if (response.status === 400)
                                $mdToast.show(
                                    $mdToast.simple()
                                        .textContent(response.data.message)
                                        .position('top right')
                                        .hideDelay(10000)
                                );
                        });

                    // Busca por agencia 
                    scope.querySearchAgencia = function (query) {
                        var deferred = $q.defer();
                        if (scope.data.banco)
                            if (query && query.length > 2) {
                                $rootScope.$broadcast('preloader:active');
                                var config = {
                                    params: {
                                        search: query,
                                        bancoId: scope.data.banco.id
                                    }
                                };
                                RestService.Get('/agencia/search', config)
                                    .then(function successCallback(response) {
                                        $rootScope.$broadcast('preloader:hide');
                                        if (response.data && response.data.length > 0)
                                            deferred.resolve(response.data);
                                        else
                                            deferred.resolve([]);
                                    }, function errorCallback(response) {
                                        $rootScope.$broadcast('preloader:hide');
                                        if (response.status === 400)
                                            $mdToast.show(
                                                $mdToast.simple()
                                                    .textContent(response.data.message)
                                                    .position('top right')
                                                    .hideDelay(10000)
                                            );
                                    });
                            }
                        return deferred.promise;
                    };
                }
            }
        }])
})();

