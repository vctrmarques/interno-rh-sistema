(function () {
    'use strict';
    angular.module('app.page')
        .controller('aliquotaFormCtrl', ['$scope', '$http', 'configValue', '$timeout', '$mdDialog', '$q', 'FileUploader', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', aliquotaFormCtrl]);

    function aliquotaFormCtrl($scope, $http, configValue, $timeout, $mdDialog, $q, FileUploader, $mdToast, $location, $state, $rootScope, GenericoService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                    }
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }
        $scope.acessaTela();
        $scope.detalhes = false;
        $scope.aliquota = {};
        $scope.obj = {};
        $scope.aliquota.ano = new Date().getFullYear();
        $scope.faixa = $state.params.faixa;
        $scope.obj.ano = new Date().getFullYear();
        $scope.ano = new Date().getFullYear();

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.buscar = function (faixa, ano) {
            $scope.ano = ano;
            $scope.loadListByAnoAndFaixa(faixa, ano)
        }

        $scope.loadListByAnoAndFaixa = function (faixa, ano) {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetById("/aliquota/" + faixa + "/" + ano, function (response) {
                if (response.status === 200) {
                    $scope.list.data = response.data;
                    $scope.aliquota = {};
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $rootScope.$broadcast('preloader:hide');
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            });
        }





        if ($state.params && $state.params.faixa) {
            $scope.anos = function () {
                var config = {
                    params: {
                        faixa: $scope.faixa,
                    }
                };
                $scope.promise = GenericoService.GetAll('/aliquotas/anos', config).then(
                    function (response) {
                        if (response.status === 200) {
                            $scope.anos = response.data;
                        } else {
                            $scope.showSimpleToast("Não foi possível carregar os dados.")
                        }
                    });
            }
            $scope.anos();

            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById("/aliquota/" + $state.params.faixa, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.list.data = response.data;

                } else {
                    $scope.showSimpleToast("Alíquota não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.aliquotaSelectionada = function (item) {
            $scope.aliquota = item;
        }

        $scope.goBack = function () {
            $location.path('/aliquota/gestao');
        }
        $scope.save = function () {

            $rootScope.$broadcast('preloader:active');
            if ($scope.aliquota.id) {
                GenericoService.Update('/aliquota', $scope.aliquota, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/aliquota/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('aliquota/formulario/' + $state.params.faixa)
                    }
                });
            } else {
                GenericoService.Create('/aliquota', $scope.aliquota, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/aliquota/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/aliquota/formulario');
                    }
                });
            }
        }

        $scope.showConfirm = function (ev, idToDelete) {
            $scope.idToDelete = idToDelete;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão deste item?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.deleteItem();
            }, function () {
            });
        };

        $scope.deleteItem = function () {

            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/aliquota/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.list.data = $scope.list.data.filter(function (e) {
                        return e.id != $scope.idToDelete;
                    });
                    if ($scope.list.data.length == 0) {
                        $location.path('/aliquota/gestao');
                    }
                    $scope.aliquota = null;
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

    }
})();