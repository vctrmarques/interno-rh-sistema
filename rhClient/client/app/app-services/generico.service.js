(function () {
    'use strict';

    angular
        .module('app.page')
        .factory('GenericoService', GenericoService);

    GenericoService.$inject = ['$q', '$http', '$cookies', '$rootScope', '$timeout', 'configValue'];
    function GenericoService($q, $http, $cookies, $rootScope, $timeout, configValue) {

        var service = {};

        service.GetAll = GetAll;
        service.GetAllRelatorio = GetAllRelatorio;
        service.VerificaPermissao = VerificaPermissao;
        service.GetById = GetById;
        service.Create = Create;
        service.ValidaFormula = ValidaFormula;
        service.Update = Update;
        service.Delete = Delete;
        service.UploadFile = UploadFile;
        service.GetAllDropdown = GetAllDropdown;
        service.GetNumeroConta = GetNumeroConta;
        service.GetAllDropdownById = GetAllDropdownById;
        service.Filtro = Filtro;
        service.Get = Get;
        service.ToDataURL = ToDataURL;

        return service;

        function GetAllDropdownById(id, callback) {
            var config = { params: { id: id } };
            $http.get(configValue.baseUrl + '/api/listaMunicipios', config)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function GetNumeroConta(creditoDebito, centroCustoId, callback) {
            var config = { params: { creditoDebito: creditoDebito, centroCustoId: centroCustoId } };
            $http.get(configValue.baseUrl + '/api/centroCusto/numeroConta', config)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function VerificaPermissao(endpoint, role) {
            var config = { params: { role: role } };

            return $http.get(configValue.baseUrl + '/api' + endpoint, config);
        }

        function GetAll(endpoint, config) {
            if (config) {
                return $http.get(configValue.baseUrl + '/api' + endpoint, config);
            } else {
                return $http.get(configValue.baseUrl + '/api' + endpoint);
            }
        }

        function GetAllRelatorio(endpoint) {
            return $http.get(configValue.baseUrl + '/api' + endpoint);
        }

        function GetAllDropdown(endpoint, callback) {
            $http.get(configValue.baseUrl + '/api/' + endpoint)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function GetById(endpoint, callback) {
            $http.get(configValue.baseUrl + '/api' + endpoint)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function Get(endpoint, config, callback) {
            $http.get(configValue.baseUrl + '/api' + endpoint, config)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function Create(endpoint, objeto, callback) {
            $http.post(configValue.baseUrl + '/api' + endpoint, objeto)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });

        }
        
        function ValidaFormula(endpoint, objeto, callback) {
            $http.post(configValue.baseUrl + '/api' + endpoint, objeto)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });

        }

        function Filtro(endpoint, objeto, callback) {
            $http.post(configValue.baseUrl + '/api' + endpoint, objeto)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });

        }

        function Update(endpoint, objeto, callback) {
            $http.put(configValue.baseUrl + '/api' + endpoint, objeto)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function Delete(endpoint, callback) {
            $http.delete(configValue.baseUrl + '/api' + endpoint)
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function UploadFile(file, pasta, callback) {
            var formData = new FormData();
            formData.append("file", file);
            $http.post(configValue.baseUrl + '/api/anexo/' + pasta, formData, {
                transformRequest: angular.identity,
                headers: { 'Content-Type': undefined }
            }).then(function successCallback(response) {
                callback(response);
            }, function errorCallback(response) {
                callback(response);
            });

        }

        function ToDataURL(src, callback, outputFormat) {
            var img = new Image();
            img.crossOrigin = 'Anonymous';
            img.onload = function () {
                var canvas = document.createElement('CANVAS');
                var ctx = canvas.getContext('2d');
                var dataURL;
                canvas.height = this.naturalHeight;
                canvas.width = this.naturalWidth;
                ctx.drawImage(this, 0, 0);
                dataURL = canvas.toDataURL(outputFormat);
                callback(dataURL);
            };
            img.src = src;
            if (img.complete || img.complete === undefined) {
                img.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                img.src = src;
            }
        }

    }
})();