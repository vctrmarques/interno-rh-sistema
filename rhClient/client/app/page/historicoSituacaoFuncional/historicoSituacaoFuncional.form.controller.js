(function () {
    'use strict';

    angular.module('app.page')
        .controller('historicoSituacaoFuncionalFormCtrl', ['$scope', '$mdDialog', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', historicoSituacaoFuncionalFormCtrl]);

    function historicoSituacaoFuncionalFormCtrl($scope, $mdDialog, $mdToast, $location, $state, $rootScope, GenericoService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {}
                },
                function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }
        $scope.situacaoFuncional = {};

        $scope.list = {
            "data": [],
            "tipoSituacaoFuncional": [],
            "funcao": [],
            "nivelSalarial": [],
            "motivo": [],
            "filialDestino": [],
            "situacao": [],
            "tipoExoneracaoDemissao": [],
            "situacaoFuncional": []
        }

        $scope.acessaTela();
        $scope.detalhes = false;


        if ($state.params && $state.params.id && $state.params.add) {

            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcionario/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {

                    $scope.funcionario = response.data;
                    $scope.situacaoFuncional.funcionarioId = response.data.id;
                    $scope.nomeFuncionario = response.data.nome;
                    $scope.matricula = response.data.matricula;
                    $scope.nomeFilial = response.data.empresa.nomeFilial;
                    if(response.data.funcao){
                        $scope.situacaoFuncional.funcaoId = response.data.funcao.id;
                        $scope.getFaixasSalariais(response.data.funcao.grupoSalarialId)
                    }

                } else {
                    $scope.showSimpleToast("Funcionário não encontrado na base");
                }
            });
        } else {
            GenericoService.GetById('/funcionario/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.situacaoFuncional.funcionarioId = response.data.id;
                    $scope.nomeFuncionario = response.data.nome;
                    $scope.matricula = response.data.matricula;
                    $scope.nomeFilial = response.data.empresa.nomeFilial;

                } else {
                    $scope.showSimpleToast("Funcionário não encontrado na base");
                }
            });
        }

        $scope.listaSituacoesFuncionais = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    funcionarioId: $scope.situacaoFuncional.funcionarioId,
                    tipoSituacao: $scope.situacaoFuncional.tipoSituacaoFuncional
                }
            };

            $scope.promise = GenericoService.GetAll('/listaHistoricoSituacoesFuncionais', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data;
                        console.log(response.data);


                        angular.forEach($scope.list.data, function (i) {
                            if (i.tipoSituacaoFuncional == "SITUACAO_AFASTAMENTO")
                                i.dataFim = i.dataRetorno;
                        });

                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.getFaixasSalariais = function (id) {
            if (id) {
                $rootScope.$broadcast('preloader:active');
                GenericoService.GetAllDropdown('faixasSalariais/porGrupo/' + id, function (response) {
                    if (response.status === 200) {


                        $scope.faixasSalariais = response.data;
                        console.log($scope.faixasSalariais);
                        $rootScope.$broadcast('preloader:hide');
                    } else if (response.status === 500) {
                        $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                    }
                });
            }
        }

        $scope.TipoSituacaoFuncional = function () {

            var config = {
                params: {
                    nomeEnum: "TipoHistoricoSituacaoFuncionalEnum"
                }
            };

            $scope.promise = GenericoService.GetAll('/listaEnums', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.tipoSituacaoFuncional = response.data;
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }
        $scope.TipoSituacaoFuncional();

        $scope.TipoExoneracaoDemissao = function () {

            var config = {
                params: {
                    nomeEnum: "TipoExoneracaoDemissaoEnum"
                }
            };

            $scope.promise = GenericoService.GetAll('/listaEnums', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.tipoExoneracaoDemissao = response.data;
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }
        $scope.TipoExoneracaoDemissao();

        $scope.Funcao = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaFuncoes', function (response) {
                if (response.status === 200) {
                    console.log(response.data);

                    $scope.list.funcao = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.Funcao();

        $scope.Situacao = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaMotivosAfastamentos', function (response) {
                if (response.status === 200) {
                    $scope.list.situacao = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.Situacao();

        $scope.SituacoesFuncionais = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaSituacoesFuncionais', function (response) {
                if (response.status === 200) {
                    $scope.list.situacaoFuncional = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.SituacoesFuncionais();

        $scope.niveisSalariais = function (niveis) {
            $scope.list.nivelSalarial = niveis;
        }

        $scope.Motivo = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaMotivos', function (response) {
                if (response.status === 200) {
                    $scope.list.motivo = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.Motivo();

        $scope.Filial = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasNaoMatrizes', function (response) {
                if (response.status === 200) {
                    $scope.list.filialDestino = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.Filial();

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/historicoSituacaoFuncional/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Create('/historicoSituacaoFuncional', $scope.situacaoFuncional, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/historicoSituacaoFuncional/gestao');
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
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

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/historicoSituacaoFuncional/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.listaSituacoesFuncionais();
                }
            });
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
            }, function () {});
        };

    }

})();
