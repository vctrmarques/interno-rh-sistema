(function () {
    'use strict';

    angular.module('app.page')
        .controller('contSindicalFormCtrl', ['$scope', 'configValue', 'FileUploader', '$http', '$q', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', contSindicalFormCtrl]);

    function contSindicalFormCtrl($scope, configValue, FileUploader, $http, $q, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        $scope.list = {
            "sindicato": [],
            "contribuicoesDetalhes": []
        };

        $scope.resetContribuicao = function () {
            $scope.contribuicao = {
                "permiteDesconto": false
            }
        }
        $scope.resetContribuicao();

        $scope.Sindicato = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaSindicatos', function (response) {
                if (response.status === 200) {
                    $scope.list.sindicato = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.Sindicato();

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/contribuicaoSindical/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.contribuicao = response.data;
                    $scope.funcionario = $scope.contribuicao.funcionario;
                    $scope.sindicato = $scope.contribuicao.sindicato;
                    if ($scope.sindicato)
                        $scope.sindicatoId = $scope.sindicato.id;

                    $scope.getAllContribuicoesByFuncionario($scope.funcionario.id);
                } else {
                    $scope.showSimpleToast("Contribuição não encontrado na base");
                }
            });
        };

        $scope.getAllContribuicoesByFuncionario = function (idFuncionarioDetalhes) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/contribuicoesSindicais/funcionario/' + idFuncionarioDetalhes, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.list.contribuicoesDetalhes = response.data;
                } else {
                    $scope.showSimpleToast("Contribuição não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        };

        $scope.goBack = function () {
            $location.path('/contribuicaoSindical/gestao');
        };

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.contribuicao.id) {
                GenericoService.Update('/contribuicaoSindical', $scope.contribuicao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/contribuicaoSindical/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('contribuicaoSindical/formulario/:id')
                    }
                });
            } else {
                GenericoService.Create('/contribuicaoSindical', $scope.contribuicao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/contribuicaoSindical/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/contribuicaoSindical/formulario');
                    }
                });
            }
        };

        $scope.sindicatoObject = function () {
            var list = $scope.list.sindicato;
            for (var i = 0; i < list.length; i++) {
                if (list[i].id == $scope.sindicatoId) {
                    $scope.sindicato = list[i];
                    $scope.contribuicao.sindicatoId = $scope.sindicatoId;
                    console.log($scope.sindicato);
                }
            }
        };

        $scope.querySearchFuncionario = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/funcionario/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.funcionarios = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        };

        $scope.funcionarioSelecionado = function () {
            if ($scope.funcionario) {
                $scope.contribuicao.funcionarioId = $scope.funcionario.id;
            }
        };

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        //COMEÇO DO CÓDIGO DE DE ANEXO

        $scope.uploader = new FileUploader();
        $scope.uploader.autoUpload = true;
        $scope.uploader.url = configValue.baseUrl + "/api/anexo/descontosindical";
        $scope.uploader.headers = $http.defaults.headers.common;
        $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.contribuicao.anexoId = response.obj.id;
            $scope.file = response.obj;
        };

        //FINAL DO CODIGO DE ANEXO
    }

})(); 