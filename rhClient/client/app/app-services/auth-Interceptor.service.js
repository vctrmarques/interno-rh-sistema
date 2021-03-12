(function () {
    'use strict';

    var authInteceptor = angular.module('app');
    authInteceptor.service('AuthInterceptor', AuthInterceptor);

    AuthInterceptor.$inject = ['$q', '$injector', '$rootScope', '$location'];
    function AuthInterceptor($q, $injector, $rootScope, $location) {
        var service = this;

        service.responseError = function (response) {
            if ($location.path() != '/login' && $location.path() != '/simulador/aposentadoria') {
                if (response.status == 401 && $rootScope.validadorRequisicao) {
                    var mdDialog = $injector.get('$mdDialog');
                    $rootScope.validadorRequisicao = false;
                    mdDialog.show({
                        templateUrl: 'app/page/signinDialog.html',
                        parent: angular.element(document.body),
                        clickOutsideToClose: true,
                    });
                }
            }
            return $q.reject(response);
        }

    }



})();