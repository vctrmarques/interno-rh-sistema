(function () {
    'use strict';

    angular.module('app.page')
        .controller('importadorVerbaFuncionarioCrtl', ['$mdDialog', 'FileUploader', 'configValue', '$http', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', importadorVerbaFuncionarioCrtl]);

    function importadorVerbaFuncionarioCrtl($mdDialog, FileUploader, configValue, $http, $scope, $mdToast, $location, $state, $rootScope, GenericoService) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        $scope.progress = "50%";
        // if (!folha.progress || folha.progress !== '100%') {
        //     GenericoService.GetAll('/folhaPagamento/status/' + folha.id)
        //         .then(function (response) {
        //             var progress = (100 * response.data.totalContrachequesConcluidos) / response.data.totalContracheques
        //             folha.progress = progress.toFixed(0) + "%"
        //         });
        // }


        $scope.abaActive = 0;
        $scope.uploader = new FileUploader();
        $scope.uploader.url = configValue.baseUrl + "/api/importadorVerbasFuncionario/validacao/arquivo"
        $scope.uploader.headers = $http.defaults.headers.common;
        $scope.uploader.removeAfterUpload = false;
        $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.showSimpleToast(response.message)
            $scope.erroList = [];
            if (response.success === false && response.obj && response.obj.erroList) {
                $scope.erroList = response.obj.erroList;
            } else if (response.success === true) {
                $scope.abaActive = 1;
                $scope.loadList();
            }
        };

        $scope.loadInputFile = function () {
            document.getElementById('fileInput').click();
        };

        $scope.limitOptions = [5, 10, 15];

        $scope.query = {
            order: "-createdAt",
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order
                }
            };

            $scope.promise = GenericoService.GetAll('/importadorVerbaFuncionarios', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }
        $scope.loadList();

        $scope.goBack = function () {
            $location.path('verbasFuncionario/gestao');
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
            GenericoService.Delete('/importadorVerbasFuncionario/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadList();
                }
            });
        }

        $scope.showLayoutEntrada = function (ev) {
            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/importadorVerbaFuncionario/importadorVerbaFuncionarioLayout.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: true // Only for -xs, -sm breakpoints.
            });
        };

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
