(function () {
    'use strict';

    angular.module('app.page')
        .controller('historicoSituacaoFuncionalCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', 'EnumService', historicoSituacaoFuncionalCtrl]);

    function historicoSituacaoFuncionalCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService, EnumService) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.loadList();
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        $scope.botoesGestao = false;
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.botoesGestao = true;
                }
            });


        $scope.nomeBusca = "";
        $scope.query = {
            order: 'nome',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.limpaFiltro = function () {
            delete $scope.nomeBusca;
        }
        EnumService.Get("TipoHistoricoSituacaoFuncionalEnum").then(function (dados) {  
            $scope.list.situacao = dados;
        });

        $scope.searchFuncionario = function (nomeFuncionario) {
            console.log(nomeFuncionario);
            
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    funcionarioNome: nomeFuncionario
                }
            };
            if (nomeFuncionario.length > 2) {
                $rootScope.$broadcast('preloader:active');
                $scope.promise = getAll(config);
            } else if (nomeFuncionario.length == 0) {
                $rootScope.$broadcast('preloader:active');
                getAll(config);
            }

        }

        function getAll(config) {
            GenericoService.GetAll('/historicoSituacoesFuncionais/lista', config).then(
                function (response) {

                    $scope.list.data = response.data.content;

                    angular.forEach($scope.list.data, function (i) {
                        angular.forEach($scope.list.situacao, function (s) {
                            if(i.situacao == s.value)
                                i.situacao = s.label;
                        });
                    });
                    $scope.list.count = response.data.totalElements;
                    $rootScope.$broadcast('preloader:hide');
                });
        }

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');


            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    funcionarioNome: $scope.nomeBusca
                }
            };

            $scope.promise = getAll(config)
        }

        $scope.limitOptions = [5, 10, 15];

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };


    }

})();