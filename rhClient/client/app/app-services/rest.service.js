(function () {
    'use strict';

    angular
        .module('app.page')
        .factory('RestService', RestService);

    RestService.$inject = ['$http', 'configValue'];
    function RestService($http, configValue) {

        var service = {};

        service.Get = Get;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;
        service.UploadFile = UploadFile;
        service.ToDataURL = ToDataURL;

        return service;

        function Get(endpoint, config) {
            if (config)
                return $http.get(configValue.baseUrl + '/api' + endpoint, config);
            else
                return $http.get(configValue.baseUrl + '/api' + endpoint);
        }

        function Create(endpoint, objeto) {
            return $http.post(configValue.baseUrl + '/api' + endpoint, objeto);
        }

        function Update(endpoint, objeto) {
            return $http.put(configValue.baseUrl + '/api' + endpoint, objeto);
        }

        function Delete(endpoint) {
            return $http.delete(configValue.baseUrl + '/api' + endpoint);
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