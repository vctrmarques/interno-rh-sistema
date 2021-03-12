(function () {
    'use strict';
    angular.module('app')
        .run(['$rootScope', '$location', '$cookies', '$http',
            function ($rootScope, $location, $cookies, $http) {
                // keep user logged in after page refresh
                $rootScope.globals = $cookies.getObject('globals');
                $rootScope.menus = JSON.parse(localStorage.getItem('menus'));
                $rootScope.contadorNotificacao = $cookies.getObject('contadorNotificacao');
                $rootScope.notificacoesHeader = $cookies.getObject('notificacoesHeader');
                $rootScope.pageTitle = $cookies.getObject('pageTitle');
                var path = $location.path();
                var defaultLocalPath = $location.absUrl();
                $rootScope.defaultPath = defaultLocalPath.replace($location.path(), "").replace("#!", "");
                $rootScope.logoSrc = confiImageLogo;
                $rootScope.inicialFrase = inicialFrase;
                
                var telaprivada = true;
                if (path === "/simulador/aposentadoria" || path === "/simulador/aposentadoria/resultado") {
                    telaprivada = false;
                }

                if ($rootScope.globals === undefined && telaprivada) {
                    $location.path('/login');
                } else {
                    if ($rootScope.globals && $rootScope.globals.currentUser) {
                        $http.defaults.headers.common['Authorization'] = $rootScope.globals.currentUser.authdata;
                    }
                }
            }
        ]);

})(); 