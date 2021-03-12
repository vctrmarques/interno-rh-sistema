(function () {
    'use strict';

    angular
        .module('app.page')
        .factory('UsuarioService', UsuarioService);

    UsuarioService.$inject = ['$http', '$cookies', '$rootScope', '$timeout', 'configValue'];
    function UsuarioService($http, $cookies, $rootScope, $timeout, configValue) {

        var service = {};

        service.GetAll = GetAll;
        service.GetAllRelatorio = GetAllRelatorio;
        service.GetById = GetById;
        service.Create = Create;
        service.Update = Update;
        service.UpdatePerfil = UpdatePerfil;
        service.Delete = Delete;
        service.UpdatePapeis = UpdatePapeis;

        return service;

        function UpdatePapeis(usuarioPapel, callback) {
            $http.put(configValue.baseUrl + '/api/usuario/atualizar/papeis', usuarioPapel)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function GetAll(pageValue, sizeValue, nomeBusca, cpfBusca, loginBusca, order) {
            var config = { params: { order: order, page: pageValue - 1, size: sizeValue } };
            if (nomeBusca && nomeBusca.length > 0) {
                config.params.nome = nomeBusca;
            }
            if (cpfBusca && cpfBusca.length > 0) {
                config.params.cpf = cpfBusca;
            }
            if (loginBusca && loginBusca.length > 0) {
                config.params.login = loginBusca;
            }
            return $http.get(configValue.baseUrl + '/api/usuarios', config);
        }

        function GetAllRelatorio() {
            return $http.get(configValue.baseUrl + '/api/usuarios');
        }

        function GetById(id, callback) {
            $http.get(configValue.baseUrl + '/api/usuario/' + id)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function Create(usuario, callback) {
            $http.post(configValue.baseUrl + '/api/usuario', usuario)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });

        }

        function Update(usuario, callback) {
            $http.put(configValue.baseUrl + '/api/usuario', usuario)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function UpdatePerfil(usuario, callback) {
            $http.put(configValue.baseUrl + '/api/usuario/perfil', usuario)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function Delete(id, callback) {
            $http.delete(configValue.baseUrl + '/api/usuario/' + id)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }
    }
})();