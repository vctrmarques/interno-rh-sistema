(function () {
    'use strict';

    angular.module('app.page')
        .controller('observacaoDocListCtrl', ['$scope', '$rootScope', '$state', '$location', '$mdDialog', '$mdToast', 'GenericoService', observacaoDocListCtrl]);

    function observacaoDocListCtrl($scope, $rootScope, $state, $location, $mdDialog, $mdToast, GenericoService) {

        $scope.acessaTela = function () {
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
        }

        $scope.acessaTela();
        $scope.botoesGestao = false;

        $scope.habilitaBotoesGestao = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                        $scope.botoesGestao = true;
                    }
                });
        }

        $scope.habilitaBotoesGestao();

        $scope.list = {
            "data": []
        };

        $scope.loadList = function () {

            $rootScope.$broadcast('preloader:active');

            GenericoService.GetById('/funcionario/' + $state.params.funcionarioId, function (response) {
                if (response.status === 200) {
                    $scope.list.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            });
        }

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/funcionario/'+ $scope.funcionarioId + '/anexo/' + $scope.anexoId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadList();
                }
            });
        }


        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        $scope.goBack = function () {
            $location.path('/observacaoDoc/gestao');
        }        

        $scope.showConfirm = function (ev, funcionarioId, anexoId) {
            $scope.funcionarioId = funcionarioId;
            $scope.anexoId = anexoId;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão deste item?')
                .textContent('Esta ação não poderá ser desfeita.')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.deleteItem();
            }, function () {
            });
        };

        $scope.showRelatorio = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllRelatorio('/dependentes').then(
                function (response) {
                    if (response.status === 200) {
                        GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                            function (dataURL) {
                                pdfMake.createPdf(getDocDefinition(response.data.content, dataURL)).open()
                            });
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }


    }
})();



