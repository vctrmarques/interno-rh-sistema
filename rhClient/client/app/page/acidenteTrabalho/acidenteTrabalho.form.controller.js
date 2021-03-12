(function () {
    'use strict';

    angular.module('app.page')
        .controller('acidenteTrabalhoFormCtrl', ['FileUploader', 'configValue', '$http', '$q', '$mdDialog', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', acidenteTrabalhoFormCtrl]);

    function acidenteTrabalhoFormCtrl(FileUploader, configValue, $http, $q, $mdDialog, $scope, $mdToast, $location, $state, $rootScope, GenericoService) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        $scope.detalhes = false;
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/acidenteTrabalho/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.acidenteTrabalho = response.data;

                    if ($scope.acidenteTrabalho.dataHoraAcidente)
                        $scope.acidenteTrabalho.dataHoraAcidente = moment($scope.acidenteTrabalho.dataHoraAcidente);

                    if ($scope.acidenteTrabalho.dataPrevistaVolta)
                        $scope.acidenteTrabalho.dataPrevistaVolta = moment($scope.acidenteTrabalho.dataPrevistaVolta);

                    if ($scope.acidenteTrabalho.dataEmissaoDocumento)
                        $scope.acidenteTrabalho.dataEmissaoDocumento = moment($scope.acidenteTrabalho.dataEmissaoDocumento);

                } else {
                    $scope.showSimpleToast("AcidenteTrabalho não encontrado na base");
                }
            });
        }

        $scope.querySearchFuncionario = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/funcionario/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }

        $scope.querySearchTomadorServico = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/tomadorServico/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }

        $scope.goBack = function () {
            $location.path('/acidenteTrabalho/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            if ($scope.acidenteTrabalho.funcionario) {
                $scope.acidenteTrabalho.funcionarioId = $scope.acidenteTrabalho.funcionario.id
            }
            if ($scope.acidenteTrabalho.entidade) {
                $scope.acidenteTrabalho.entidadeId = $scope.acidenteTrabalho.entidade.id
            }
            if ($scope.acidenteTrabalho.tomadorServico) {
                $scope.acidenteTrabalho.tomadorServicoId = $scope.acidenteTrabalho.tomadorServico.id
            }
            if ($scope.acidenteTrabalho.anexo) {
                $scope.acidenteTrabalho.anexoId = $scope.acidenteTrabalho.anexo.id
                salvaDescricaoAnexo();
            }

            if ($scope.acidenteTrabalho.id) {
                GenericoService.Update('/acidenteTrabalho', $scope.acidenteTrabalho, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {

                        var confirm = $mdDialog.alert()
                            .clickOutsideToClose(false)
                            .title(response.data.message)
                            .textContent('Favor atualizar a tabela de situação funcional.')
                            .ok('OK');

                        $mdDialog.show(confirm).then(function () {
                            $location.path('/acidenteTrabalho/gestao');
                        }, function () {
                        });

                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('acidenteTrabalho/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/acidenteTrabalho', $scope.acidenteTrabalho, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {

                        var confirm = $mdDialog.alert()
                            .clickOutsideToClose(false)
                            .title(response.data.message)
                            .textContent('Favor atualizar a tabela de situação funcional.')
                            .ok('OK');

                        $mdDialog.show(confirm).then(function () {
                            $location.path('/acidenteTrabalho/gestao');
                        }, function () {
                        });

                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/acidenteTrabalho/formulario');
                    }
                });
            }
        }


        $scope.excluirAnexo = function () {
            $scope.acidenteTrabalho.anexo = undefined;
        }

        function salvaDescricaoAnexo() {
            if ($scope.acidenteTrabalho.anexo && $scope.acidenteTrabalho.anexo.id) {
                var anexo = {
                    id: $scope.acidenteTrabalho.anexo.id,
                    description: $scope.acidenteTrabalho.anexo.description
                }
                GenericoService.Update('/anexo', anexo, function (response) {
                });
            }
        }

        $scope.uploader = new FileUploader();
        $scope.uploader.url = configValue.baseUrl + "/api/anexo/acidentetrabalho";
        $scope.uploader.headers = $http.defaults.headers.common;
        $scope.uploader.removeAfterUpload = true;
        $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.acidenteTrabalho.anexo = response.obj;
        };

        var validateExtensions = ["image/jpeg", "application/pdf", "image/jpg"];

        $scope.onFileChange = function (event) {
            if (validateExtensions.indexOf(event.target.files[0].type) == -1) {
                $scope.showSimpleToast('So é aceito arquivos do tipo: .jpeg, .jpg, .pdf');
                $scope.uploader.removeFromQueue(0);
                $scope.uploader.clearQueue();
            } else {
                $scope.uploader.addToQueue(event.target.files[0]);
                $scope.uploader.uploadItem(0);

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

    }

})(); 