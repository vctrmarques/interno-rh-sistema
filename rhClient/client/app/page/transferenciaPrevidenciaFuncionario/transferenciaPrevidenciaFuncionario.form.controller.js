(function () {
    'use strict';

    angular.module('app.page')
        .controller('transferenciaPrevidenciaFuncionarioFormCtrl', ['$scope', '$mdToast', '$q', '$location', '$state', '$rootScope', 'GenericoService', transferenciaPrevidenciaFuncionarioFormCtrl]);

    function transferenciaPrevidenciaFuncionarioFormCtrl($scope, $mdToast, $q, $location, $state, $rootScope, GenericoService) {

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
        $scope.list = {
            'empresa': [],
            'filial': [],
            'tipoAposentadoria': [],
            'situacaoFuncional': [],
            'lotacoes': [],
            'tipoProporcao': [
                { value: "INTEGRAL", label: "Integral" },
                { value: "PROPORCIONAL", label: "Proporcional" }
            ]
        };
        $scope.transferencia = {};
        $scope.chosenFilial = true;
        $scope.detalhes = false;
        $scope.acessaTela();

        //
        $scope.Empresa = () => {
            $scope.loadedEmpresas = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasMatrizes', function (response) {
                if (response.status === 200) {
                    $scope.list.empresa = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast('Falha no carregamento de dados, favor contate o administrador do sistema');
                }
                $scope.loadedEmpresas = true;
            });
        };

        $scope.Filial = () => {
            $scope.loadedFilial = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasNaoMatrizes/', function (response) {
                if (response.status === 200) {
                    $scope.list.filial = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast('Falha no carregamento de dados, favor contate o administrador do sistema');
                }
                $scope.loadedFilial = true;
            });
        };
        $scope.changeFilial = () => {
            if ($scope.transferencia.filialId > 0) {
                $scope.chosenFilial = false;
            }
        };
        $scope.TipoAposentadoria = () => {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaTiposAposentadorias', function (response) {
                if (response.status === 200) {
                    $scope.list.tipoAposentadoria = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast('Falha no carregamento de dados, favor contate o administrador do sistema');
                }
            });
        };
        $scope.SituacaoFuncional = () => {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaSituacoesFuncionais/afastamento', function (response) {
                if (response.status === 200) {
                    $scope.list.situacaoFuncional = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast('Falha no carregamento de dados, favor contate o administrador do sistema');
                }
            });
        };
        $scope.changeSituacaoFuncional = (situacaoFuncionalId) =>{
            $scope.situacaoFuncional = null;
            for (let index = 0; index < $scope.list.situacaoFuncional.length; index++) {
                const element = $scope.list.situacaoFuncional[index];
                if(element.id == situacaoFuncionalId){
                    $scope.situacaoFuncional = element;
                    break;
                }
            }
        }

        $scope.changeFilialDestino = () => {
            $rootScope.$broadcast('preloader:active');
            if ($scope.transferencia.filialDestinoId) {
                GenericoService.GetAllDropdown('listaLotacoes/' + $scope.transferencia.filialDestinoId, function (response) {
                    if (response.status === 200) {
                        $scope.list.lotacoes = response.data;
                        $scope.transferencia.lotacaoDestinoId = null;
                        $rootScope.$broadcast('preloader:hide');
                    } else if (response.status === 500) {
                        $scope.showSimpleToast('Falha no carregamento de dados, favor contate o administrador do sistema');
                    }
                });
            }else{
                $scope.list.lotacoes = [];
                $scope.transferencia.lotacaoDestinoId = null;
            }
        }
        //
        $scope.Empresa();
        $scope.Filial();
        $scope.TipoAposentadoria();
        $scope.SituacaoFuncional();

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/transferenciasPrevidenciaFuncionarios/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.transferencia = response.data;
                    $scope.transferencia.situacaoFuncionalId = $scope.transferencia.historicoSituacaoFuncional.situacaoFuncionalId;
                    $scope.changeSituacaoFuncional($scope.transferencia.situacaoFuncionalId);
                    $scope.transferencia.filialDestinoId = $scope.transferencia.transferenciaFuncionario.empresa.id;
                    $scope.changeFilialDestino();
                    $scope.transferencia.lotacaoDestinoId = $scope.transferencia.transferenciaFuncionario.lotacao.id;
                    $scope.transferencia.dataSolicitacao = moment(response.data.dataSolicitacao);
                    $scope.transferencia.dataAposentadoria = moment(response.data.dataAposentadoria);
                    $scope.funcionario = $scope.transferencia.funcionario;
                    $scope.transferencia.tipoAposentadoriaId = $scope.transferencia.tipoAposentadoria.id;
                    $scope.funcionario.filialId = $scope.transferencia.transferenciaFuncionario.empresaAnterior.id;
                    $scope.funcionario.lotacaoId = $scope.transferencia.transferenciaFuncionario.lotacaoAnterior.id;

                } else {
                    $scope.showSimpleToast("Transferência não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/transferenciaPrevidenciaFuncionario/gestao');
        }

        $scope.querySearchFuncionario = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
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
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };


        $scope.adicionarFuncionario = function () {
            $scope.transferencia.funcionarioId = $scope.funcionario.id;
            $scope.transferencia.empresaFilialId = $scope.funcionario.empresa.id;
            $scope.transferencia.filialId = $scope.funcionario.filial.id;
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.transferencia.id) {
                GenericoService.Update('/transferenciasPrevidenciaFuncionarios', $scope.transferencia, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/transferenciaPrevidenciaFuncionario/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('transferenciaPrevidenciaFuncionario/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/transferenciasPrevidenciaFuncionarios', $scope.transferencia, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/transferenciaPrevidenciaFuncionario/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/transferenciaPrevidenciaFuncionario/formulario');
                    }
                });
            }
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
