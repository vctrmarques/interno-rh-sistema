(function () {
    'use strict';

    angular
        .module('app')
        .factory('AutenticacaoService', AutenticacaoService);

    AutenticacaoService.$inject = ['$http', '$window', '$cookies', '$mdDialog', '$rootScope', 'GenericoService', 'configValue', 'NotificacaoService', 'appConfig'];
    function AutenticacaoService($http, $window, $cookies, $mdDialog, $rootScope, GenericoService, configValue, NotificacaoService, appConfig) {
        var service = {};
        $rootScope.validadorRequisicao = true;
        service.Login = Login;
        service.GetUsuarioLogado = GetUsuarioLogado;
        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;
        service.loadMenus = loadMenus;
        return service;

        function Login(username, password, callback) {

            /* Use this for Autenticacao
             ----------------------------------------------*/
            $http.post(configValue.baseUrl + '/api/autenticacao/login', { usernameOrEmail: username, password: password })
                .then(function successCallback(response) {
                    callback(response);
                    $mdDialog.cancel();
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function GetUsuarioLogado(callback) {
            $http.get(configValue.baseUrl + '/api/usuario/logado')
                .then(function successCallback(response) {
                    callback(response);
                }, function errorCallback(response) {
                    callback(response);
                });
        }

        function setNomeUsuarioLogado() {
            GetUsuarioLogado(function (response) {
                if (response.status === 401) {
                    //$scope.showErrorOnSubmitFailed = true;
                } else {
                    $rootScope.globals.currentUser.userName = response.data.login;
                    $rootScope.globals.currentUser.nome = response.data.nome;
                    $rootScope.globals.currentUser.email = response.data.email;
                    $rootScope.globals.currentUser.foto = response.data.anexoFileDownloadUri;
                    // store user details in globals cookie that keeps user logged in for 1 week (or until they logout)
                    var cookieExp = new Date();
                    cookieExp.setDate(cookieExp.getDate() + 1);
                    $cookies.putObject('globals', $rootScope.globals, { expires: cookieExp });
                }
            })
        }

        function loadMenus() {
            $http.get(configValue.baseUrl + '/api/menus/sidebar').then(
                function (response) {
                    var menus = response.data;

                    var i;
                    for (i = 0; i < menus.length; i++) {
                        menus[i].faIcon = getFaIcon(menus[i]);
                        var j;
                        for (j = 0; j < menus[i].subMenus.length; j++) {
                            menus[i].subMenus[j].url = menus[i].subMenus[j].url;
                        }
                    }

                    $rootScope.menus = menus;
                    localStorage.removeItem('menus');
                    localStorage.setItem('menus', JSON.stringify($rootScope.menus));
                });
        }

        function carregarContadorNotificacao() {
            NotificacaoService.ContadorNotificacao(function (response) {
                $rootScope.contadorNotificacao = response.data;
                if ($rootScope.contadorNotificacao > 0) {
                    $window.document.title = '(' + $rootScope.contadorNotificacao + ') document.title';
                    $rootScope.pageTitle = appConfig.main.brand;
                } else {
                    $window.document.title = appConfig.main.brand;
                    $rootScope.pageTitle = appConfig.main.brand;
                }
                var cookieExp = new Date();
                cookieExp.setDate(cookieExp.getDate() + 1);
                $cookies.putObject('contadorNotificacao', $rootScope.contadorNotificacao, { expires: cookieExp });
            });
        }

        function carregarNotificacoesHeader() {
            var config = {
                params: {
                    page: 0,
                    size: 3
                }
            };
            GenericoService.GetAll('/notificacoes', config).then(
                function (response) {
                    if (response.status === 200) {
                        $rootScope.notificacoesHeader = response.data.content;
                        var cookieExp = new Date();
                        cookieExp.setDate(cookieExp.getDate() + 1);
                        $cookies.putObject('notificacoesHeader', $rootScope.notificacoesHeader, { expires: cookieExp });
                    }
                });
        }

        function getFaIcon(menu) {
            var result;
            switch (menu.nome) {
                case "Gestão":
                    result = "fa-cog";
                    break;
                case "Módulo RH":
                    result = "fa-users";
                    break;
                case "Folha de Pgt":
                    result = "fa-usd";
                    break;
                case "Recrutamento e Seleção":
                    result = "fa fa-check-square-o";
                    break;
                case "Módulo Avaliação":
                    result = "fa-bar-chart";
                    break;
                case "Consultas Gerenciais":
                    result = "fa-sticky-note";
                    break;
                case "Relatório":
                    result = "fa-file-text-o";
                    break;
                default:
                    result = "fa-cog";
                    break;
            }
            return result;
        }

        function SetCredentials(responseData) {
            var authdata = responseData.tokenType + ' ' + responseData.accessToken;
            $rootScope.validadorRequisicao = true;
            $rootScope.globals = {
                currentUser: {
                    authdata: authdata
                }
            };

            // set default auth header for http requests
            $http.defaults.headers.common['Authorization'] = responseData.tokenType + ' ' + responseData.accessToken;

            setNomeUsuarioLogado();
            loadMenus();
            carregarContadorNotificacao();
            carregarNotificacoesHeader();
        }

        function ClearCredentials() {
            $window.document.title = appConfig.main.brand;
            $cookies.putObject('pageTitle', appConfig.main.brand);

            $http.defaults.headers.common.Authorization = 'Basic';

            $rootScope.notificacoesHeader = {};
            $cookies.remove('notificacoesHeader');

            $rootScope.contadorNotificacao = {};
            $cookies.remove('contadorNotificacao');

            $rootScope.menus = {};
            $cookies.remove('menus');

            $rootScope.globals = {};
            $cookies.remove('globals');

        }
    }

})();
