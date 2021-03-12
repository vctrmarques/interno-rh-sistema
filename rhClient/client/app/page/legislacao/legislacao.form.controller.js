(function () {
    'use strict';

    angular.module('app.page')
        .controller('legislacaoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'FileUploader', 'configValue', '$http', 'RestService', legislacaoFormCtrl]);

    function legislacaoFormCtrl($scope, $mdToast, $location, $state, $rootScope, FileUploader, configValue, $http, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Legislação' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeCadastrar = response.data.usuarioAdm ? true : response.data.podeCadastrar;
                    $scope.podeAtualizar = response.data.usuarioAdm ? true : response.data.podeAtualizar;
                    $scope.podeVisualizar = response.data.usuarioAdm ? true : response.data.podeVisualizar;
                    $scope.podeExcluir = response.data.usuarioAdm ? true : response.data.podeExcluir;

                    if ($scope.detalhes) {
                        if (!$scope.usuarioAdm && !$scope.podeCadastrar && !$scope.podeAtualizar && !$scope.podeVisualizar && !$scope.podeExcluir)
                            $location.path('page/403');
                    } else {
                        if ($state.params && $state.params.id) {
                            if (!$scope.usuarioAdm && !$scope.podeAtualizar)
                                $location.path('page/403');
                        } else {
                            if (!$scope.usuarioAdm && !$scope.podeCadastrar)
                                $location.path('page/403');
                        }
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        //Construtor do objeto legislacao
        $scope.legislacaoInit = {
            legislacaoAnexoList: []
        }
        $scope.legislacao = angular.copy($scope.legislacaoInit);

        $scope.publicacaoMinDate = moment("1950", "YYYY");

        // Busca de Normas Leis regulamentadas
        RestService.Get('/legislacao/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    if ($state.params && $state.params.id) {
                        $scope.normaLeiList = [];
                        response.data.forEach(element => {
                            if (element.id != $state.params.id) {
                                $scope.normaLeiList.push(element);
                            }
                        });
                    } else {
                        $scope.normaLeiList = response.data;
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Busca dos Ente Federados
        RestService.Get('/legislacao/enteFederado/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.enteFederadoList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Busca dos Normas
        RestService.Get('/legislacao/norma/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.normaList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Busca de Unidade Gestora
        RestService.Get('/legislacao/unidadeGestora/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.unidadeGestoraList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Busca de Assuntos da Norma
        RestService.Get('/legislacao/assuntoNorma/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.assuntoNormaList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Busca de Tipo de Texto Documento
        RestService.Get('/legislacao/textoDocumento/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.textoDocumentoList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Busca dos Detalhamentos Normas
        RestService.Get('/legislacao/detalhamentoNorma/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.detalhamentoNormaList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        $scope.deletePessoalLegislacao = function () {
            delete $scope.legislacao.pessoalLegislacao;
        }

        $scope.changeNorma = function () {

            var codigoNorma = $scope.legislacao.norma.codigo;

            // RN04.2
            if (codigoNorma === 11 || codigoNorma === 14 || codigoNorma === 15 || codigoNorma === 16 || codigoNorma === 17) {

                $scope.detalhamentoNormaList.forEach(element => {
                    if (element.codigo === 1 || element.codigo === 2 || element.codigo === 3 || element.codigo === 6
                        || element.codigo === 8 || element.codigo === 9 || element.codigo === 10) {
                        element.disabled = true;
                        $scope.showTooltipDetalhaDisabled = true;
                        if ($scope.legislacao.detalhamentoNorma && $scope.legislacao.detalhamentoNorma.codigo === element.codigo) {
                            $scope.legislacao.detalhamentoNorma = null;
                        }
                    }
                });

            } else {

                $scope.detalhamentoNormaList.forEach(element => {
                    if (element.codigo === 1 || element.codigo === 2 || element.codigo === 3 || element.codigo === 6
                        || element.codigo === 8 || element.codigo === 9 || element.codigo === 10) {
                        element.disabled = false;
                        $scope.showTooltipDetalhaDisabled = false;
                    }
                });
            }
        };


        $scope.detalhes = false;
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            RestService.Get('/legislacao/' + $state.params.id)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    $scope.legislacao = response.data;
                    $scope.legislacao.inicioEfeitoFinanceiro = moment($scope.legislacao.inicioEfeitoFinanceiro);
                    $scope.legislacao.publicacao = moment($scope.legislacao.publicacao);
                    $scope.legislacao.inicioVigencia = moment($scope.legislacao.inicioVigencia);
                    if ($scope.legislacao.fimVigencia)
                        $scope.legislacao.fimVigencia = moment($scope.legislacao.fimVigencia);
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        }

        //Construtor do objeto legislacaoAnexo
        $scope.legislacaoAnexoInit = {
            anexo: {
                id: null,
                description: null,
                fileName: null,
                size: null
            },
            textoDocumento: null
        }
        $scope.legislacaoAnexo = angular.copy($scope.legislacaoAnexoInit);

        var validateExtensions = ["application/pdf"];

        $scope.uploader = new FileUploader();
        $scope.uploader.url = configValue.baseUrl + "/api/anexo/legislacao";
        $scope.uploader.headers = $http.defaults.headers.common;
        $scope.uploader.removeAfterUpload = true;
        $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.legislacaoAnexo.anexo.id = response.obj.id;
            $scope.legislacaoAnexo.anexo.fileName = response.obj.fileName;
            $scope.legislacaoAnexo.anexo.size = response.obj.size;
            if (!$scope.legislacao.legislacaoAnexoList) {
                $scope.legislacao.legislacaoAnexoList = [];
            }
            $scope.legislacao.legislacaoAnexoList.push($scope.legislacaoAnexo);
            $scope.legislacaoAnexo = angular.copy($scope.legislacaoAnexoInit);
        };

        $scope.uploader.onBeforeUploadItem = function (item) {
            item.formData = [{ description: $scope.legislacaoAnexo.anexo.description }];
        };

        $scope.adicionarAnexo = function () {

            // Validações
            if (!$scope.legislacaoAnexo.anexo.description) {
                $scope.showSimpleToast("Favor preencher a Descrição da Lei / Norma.");
                return;
            }

            if (!$scope.legislacaoAnexo.textoDocumento) {
                $scope.showSimpleToast("Favor preencher o Tipo de Texto do Documento.");
                return;
            }

            // Upload do anexo
            $scope.uploader.uploadAll()
        }

        $scope.removerAnexo = function (item) {
            var index = $scope.legislacao.legislacaoAnexoList.indexOf(item);
            $scope.legislacao.legislacaoAnexoList.splice(index, 1);
        }

        $scope.removerArquivo = function () {
            $scope.uploader.clearQueue();
            var element = angular.element('#fileInput');
            if (element) {
                element.prop('value', null);
            }
        };

        $scope.uploader.onAfterAddingFile = function (fileItem) {
            if (validateExtensions.indexOf(fileItem.file.type) == -1) {
                $scope.showSimpleToast('So é aceito arquivos do tipo: .pdf');
                $scope.uploader.removeFromQueue(fileItem);
            }
        };

        $scope.loadInputFile = function () {
            document.getElementById('fileInput').click();
        };

        $scope.goBack = function () {
            $location.path('/legislacao/gestao');
        }

        $scope.save = function () {

            // Validação de obrigatoriedade de anexos
            if (!$scope.legislacao.legislacaoAnexoList || $scope.legislacao.legislacaoAnexoList.length === 0) {
                $scope.showSimpleToast("Favor inserir ao menos um anexo.");
                return;
            }

            $rootScope.$broadcast('preloader:active');
            if ($scope.legislacao.id) {
                RestService.Update('/legislacao', $scope.legislacao)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/legislacao/gestao');
                        }
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            } else {
                RestService.Create('/legislacao', $scope.legislacao)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/legislacao/gestao');
                        }
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            }
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(10000)
            );
        };

    }

})(); 