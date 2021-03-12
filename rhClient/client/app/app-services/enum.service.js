(function () {
    'use strict';

    angular
        .module('app.page')
        .factory('EnumService', EnumService);

    EnumService.$inject = ['$q', '$http', 'configValue'];
    function EnumService($q, $http, configValue) {

        var service = {};

        service.Get = getEnum;

        return service;

        function getEnum(nome) {
            var config = { params: { nomeEnum: nome } };
            var deferred = $q.defer();
            get('/listaEnums', config).then(
                function (response) {
                    deferred.resolve(response.data);
                });
            return deferred.promise;
        }


        function get(endpoint, config) {
            if (config) {
                return $http.get(configValue.baseUrl + '/api' + endpoint, config);
            } else {
                return $http.get(configValue.baseUrl + '/api' + endpoint);
            }
        }

    }
})();