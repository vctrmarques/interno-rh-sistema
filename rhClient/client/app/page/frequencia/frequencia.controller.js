(function () {
    'use strict';

    angular.module('app.page')
        .controller('frequenciaCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', frequenciaCtrl]);

    function frequenciaCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {
        $scope.botoesGestao = false;

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            
            function (response) {
                if (response.status === 200) {
                    $scope.loadList();
                    $scope.botoesGestao = true;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        $scope.query = {
            order: 'id',
            limit: 12,
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
                    nomeFuncionario: null
                }
            };

            $scope.promise = GenericoService.GetAll('/frequencias', config).then(
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
        $scope.accordion = function (id) {
            var accordions = document.getElementById("accordion-frequencia"+id);
            
            var content = accordions.nextElementSibling;
            if (content.style.maxHeight) {
                accordions.classList.remove("is-open");
                content.style.maxHeight = null;
                content.style.paddingTop = null;
            } else {
                accordions.classList.add("is-open");
                content.style.maxHeight = content.scrollHeight + "px";
                content.style.paddingTop = "5px";
            }
        };

        $scope.searchFuncionarioByAno= function (ano) {
            $scope.historicoTemp = $scope.list.data;
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    nomeFuncionario: $scope.nomeFuncionarioBusca,
                    ano: ano
                }
            };
            
            if (ano!=null && ano.toString().length > 3 && ano.toString().length <5) {
                $rootScope.$broadcast('preloader:active');
                $scope.promise = getAll(config);
            } else if (ano == null) {
                $rootScope.$broadcast('preloader:active');
                getAll(config);
            }

        }

        $scope.searchFuncionario = function (nomeFuncionario) {
            $scope.historicoTemp = $scope.list.data;
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    nomeFuncionario: nomeFuncionario,
                    ano: $scope.ano
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

        $scope.limitOptions = [5, 10, 15];

        $scope.nomeFuncionarioBusca;
        $scope.ano;

        $scope.limpaFiltro = function () {
            $scope.nomeFuncionarioBusca = null;
            $scope.loadList();
        }

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/processo/' + $scope.idToDelete, function (response) {
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

        $scope.showConfirm = function (ev, idToDelete) {
            $scope.idToDelete = idToDelete;
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
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: 200
                }
            };
            GenericoService.GetAll('/frequencias', config).then(
                function (response) {
                    if (response.status === 200) {                        
                        GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                            function (dataURL) {
                                // pdfMake.createPdf(getDocDefinition(response.data.content, dataURL)).open()
                            });
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }

        function getAll(config) {
            GenericoService.GetAll('/frequencias', config).then(
                function (response) {
                    $scope.list.data = response.data.content;
                    $scope.list.count = response.data.totalElements;
                    $rootScope.$broadcast('preloader:hide');
                });
        }

    }

})();



