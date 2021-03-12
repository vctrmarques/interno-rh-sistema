(function () {
    'use strict';
    angular.module('app.page')
        .controller('faltaFormCtrl', ['$scope', '$http', 'configValue', '$timeout', '$mdDialog', '$q', 'FileUploader', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', faltaFormCtrl]);

    function faltaFormCtrl($scope, $http, configValue, $timeout, $mdDialog, $q, FileUploader, $mdToast, $location, $state, $rootScope, GenericoService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) { }
                },
                function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }
        $scope.acessaTela();
        $scope.detalhes = false;
        $scope.falta = {};
        $scope.anexos = [];
        $scope.jaRegistrado = false;

        $scope.limparCampos = function () {
            $scope.falta = {};
            $scope.funcionario = null;
            $scope.faltas = [];
            $scope.jaRegistrado = false;
            $location.path('/falta/formulario');
        }


        $scope.funcionarioSelect = function () {
            if ($scope.funcionario) {
                $scope.falta.funcionarioId = $scope.funcionario.id;
                if (!$scope.detalhes && !$state.params.id)
                    $scope.frequenciaJaRegistrada($scope.funcionario.id);
            };
        }



        $scope.removeAnexo = function (item) {

            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a remoção deste item?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(item)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.desanexar(item);
            }, function () { });
        };

        $scope.frequenciaJaRegistrada = function (funcionarioId) {
            var config = {
                params: {
                    funcionarioId: funcionarioId
                }
            };
            $scope.promise = GenericoService.GetAll('/frequencia/hoje', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.frequencias = response.data;
                        if (response.data == true) {
                            $mdDialog.show(
                                $mdDialog.alert()
                                    .parent(angular.element(document.querySelector('#popupContainer')))
                                    .clickOutsideToClose(true)
                                    .title('Alerta!')
                                    .textContent('Ja existe um registro para este funcionário hoje')
                                    .ariaLabel('Alert Dialog Demo')
                                    .ok('OK')

                            );
                            $scope.jaRegistrado = true;
                        }
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.desanexar = function (item) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/falta/remover-anexo/' + item, function (response) {
                $rootScope.$broadcast('preloader:hide');

                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.anexos = $scope.anexos.filter(function (e) {
                        return e.id != item;
                    });
                }
            });
        };


        $scope.uploader = {
            "headers": null
        }

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.list = {
            "afastamento": [],
            "motivoAfastamento": [],
            "crm": [],
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/falta/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    console.log(response.data);

                    $scope.funcionario = response.data.funcionario;
                    $scope.falta = response.data;
                    $scope.falta.dataInicio = moment(response.data.dataInicio);
                    $scope.falta.dataRetorno = moment(response.data.dataRetorno);
                    $scope.falta.funcionarioId = response.data.funcionario.id;
                    $scope.falta.afastamentoId = response.data.afastamento.id;
                    $scope.falta.motivoAfastamentoId = response.data.motivoAfastamento.id;
                    if (response.data.anexo) {
                        $scope.falta.anexos = [];
                        $scope.anexos.push(response.data.anexo);
                        $scope.falta.anexoId = response.data.anexo.id;
                    }
                } else {
                    $scope.showSimpleToast("Falta não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            window.history.back();
        }

        $scope.save = function () {
            if ($scope.anexos) {
                angular.forEach($scope.anexos, function (e) {
                    if (e.id != null) {
                        $scope.falta.anexoId = e.id;
                    }
                });
            };
            $rootScope.$broadcast('preloader:active');
            if ($scope.falta.id) {
                GenericoService.Update('/falta', $scope.falta, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/frequencia/formulario/' + $scope.falta.funcionario.id + "/" + new Date().getFullYear());
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/falta/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/falta', $scope.falta, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/frequencia/formulario/' + $scope.falta.funcionarioId + "/" + new Date().getFullYear());
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/frequencia/formulario');
                    }
                });
            }
        }

        $scope.querySearchFuncionario = function (query) {
            var deferred = $q.defer();
            var config = {
                params: {
                    search: query
                }
            }
            if (query) {
                if (query.length > 2) {
                    GenericoService.GetAll('/funcionario/searchNomeOrMatricula', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                $scope.funcionarios = response.data;
                                deferred.resolve(response.data);
                            }
                        });
                } else {
                    $scope.funcionarios = [];
                }
            }
            return deferred.promise;
        };

        GenericoService.GetAll('/listaMotivosAfastamentos')
            .then(function successCallback(response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.motivos = response.data;
                }
            }, function errorCallback(response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 400) {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            });

        GenericoService.GetAll('/listaAfastamentos')
            .then(function successCallback(response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.afastamentos = dados;
                }
            }, function errorCallback(response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 400) {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            });

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        $scope.getFile = function ($file) {
            $scope.file = $file;
        };


        $scope.showConfirm = function (ev) {

            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar o anexo deste item?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.anexar();
            }, function () { });
        };

        $scope.removeAnexo = function (item) {

            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a remoção deste item?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(item)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.desanexar(item);
            }, function () { });
        };

        $scope.desanexar = function (item) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/falta/remover-anexo/' + item, function (response) {
                $rootScope.$broadcast('preloader:hide');

                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.anexos = $scope.anexos.filter(function (e) {
                        return e.id != item;
                    });
                }
            });
        };


        $scope.anexar = function () {
            var file
            if (($scope.file) && $scope.file.length > 0) {
                file = $scope.file[0];
                GenericoService.UploadFile(file, "falta", function (response) {
                    if (!$scope.anexos) {
                        $scope.anexos = [];
                    }
                    if (!$scope.falta.anexos) {
                        $scope.falta.anexos = [];
                    }
                    $scope.anexos.push(response.data.obj)
                    $scope.file = null;
                    var $el = $('#anexo-file');
                    $el.wrap('<form>').closest('form').get(0).reset();
                    $el.unwrap();
                });
            } else {
                $scope.showSimpleToast("Arquivo inválido ou nulo")
            }
        };
    }
})();
