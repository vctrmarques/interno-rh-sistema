(function () {
    'use strict';
    angular.module('app.page')
        .controller('processoFormCtrl', ['$scope', '$http', 'configValue', '$timeout', '$mdDialog', '$q', 'EnumService', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', processoFormCtrl]);

    function processoFormCtrl($scope, $http, configValue, $timeout, $mdDialog, $q, EnumService, $mdToast, $location, $state, $rootScope, GenericoService) {

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
        $scope.processo = {};
        $scope.processo.impactoFinanceiro = false;
        $scope.funcionarios = [];
        $scope.anexos = [];
        $scope.habilitar = false;


        $scope.funcionarioSelect = function () {
            if ($scope.funcionario) {
                $scope.processo.funcionarioId = $scope.funcionario.id;
            }
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
            GenericoService.GetById('/processo/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.processo = response.data;
                    $scope.funcionario = response.data.funcionario;
                    $scope.processo.assunto = response.data.assunto;
                    $scope.processo.atoPortaria = response.data.atoPortaria;
                    $scope.processo.dataAto = moment(response.data.dataAto);
                    $scope.processo.dataDoe = moment(response.data.dataDoe);
                    $scope.processo.dataFim = moment(response.data.dataFim);
                    $scope.processo.dataInicio = moment(response.data.dataInicio);
                    $scope.processo.doe = response.data.doe;
                    $scope.processo.funcionarioId = response.data.funcionario.id;
                    $scope.processo.id = response.data.id;
                    $scope.processo.situacao = response.data.situacao.label;
                    if (response.data.tipoReflexo)
                        $scope.processo.tipoReflexo = response.data.tipoReflexo.label;
                    $scope.processo.impactoFinanceiro = response.data.impactoFinanceiro;
                    $scope.processo.inicioImpactoFinanco = moment(response.data.inicioImpactoFinanco);
                    $scope.processo.fimImpactoFinanco = moment(response.data.inicioImpactoFinanco);
                    $scope.processo.numeroProcesso = response.data.numeroProcesso;
                    $scope.processo.requerente = response.data.requerente;
                    $scope.anexos = response.data.anexos;
                } else {
                    $scope.showSimpleToast("Processo não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/processo/gestao');
        }

        $scope.save = function () {


            if ($scope.anexos) {
                $scope.processo.anexos = [];
                angular.forEach($scope.anexos, function (e) {
                    $scope.processo.anexos.push(e.id);
                });
            };

            $rootScope.$broadcast('preloader:active');

            if ($scope.processo.id) {
                GenericoService.Update('/processo', $scope.processo, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/processo/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('processo/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/processo', $scope.processo, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/processo/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/processo/formulario');
                    }
                });
            }
        }

        $scope.querySearchFuncionario = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
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




        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        $scope.Situacoes = function () {
            $rootScope.$broadcast('preloader:active');

            // Busca do enum de SituacaoProcessoEnum
            EnumService.Get("SituacaoProcessoEnum").then(function (dados) {
                $scope.situacoes = dados;
                $rootScope.$broadcast('preloader:hide');
            });

        }
        $scope.Situacoes();

        $scope.Reflexos = function () {
            $rootScope.$broadcast('preloader:active');

            // Busca do enum de TipoReflexoEnum
            EnumService.Get("TipoReflexoEnum").then(function (dados) {
                $scope.reflexos = dados;
                $rootScope.$broadcast('preloader:hide');
            });

        }
        $scope.Reflexos();



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
            }, function () {
            });
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
            }, function () {
            });
        };

        $scope.desanexar = function (item) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/processo/remover-anexo/' + item, function (response) {
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
            $scope.url = configValue.baseUrl + "/api/anexo/processo";
            if (($scope.file) && $scope.file.length > 0) {
                file = $scope.file[0];
                GenericoService.UploadFile(file, "processo", function (response) {
                    if (!$scope.anexos) {
                        $scope.anexos = [];
                    }
                    if (!$scope.processo.anexos) {
                        $scope.processo.anexos = [];
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