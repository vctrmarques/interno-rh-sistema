(function () {
    'use strict';

    angular.module('app.page')
        .controller('tempoServicoFormCtrl', ['$q', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', tempoServicoFormCtrl]);

    function tempoServicoFormCtrl($q, $scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
        $scope.tempoServico ={
            averbado: false
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/tempoServico/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.tempoServico = response.data;

                    $scope.tempoServico.dataInicio = moment().add(response.data.dataInicio);
                    $scope.tempoServico.dataTermino = moment().add(response.data.dataTermino);
                    $scope.tempoServico.dataDiarioOficial = moment().add(response.data.dataDiarioOficial);
                    $scope.tempoServico.data = moment().add(response.data.data);
                } else {
                    $location.path('/tempoServicoForm/formulario');
                }
            });
        }

        $scope.list = {
            "indiceContribuicaoPrevidencia": [],
            "tipoAverbacao": [],
            "sefip": [],
            "efeitoAverbacao": [],
            "classificacaoAto": [],
            "unidadeFederativa": []

        }

        //Início do carregamento dos dropdowns
        $scope.IndiceContribuicaoPrevidencia = function () {

            var config = {
                params: { nomeEnum: "IndiceContribuicaoPrevidenciaEnum" }
            };

            $scope.promise = GenericoService.GetAll('/listaEnums', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.indiceContribuicaoPrevidencia = response.data;
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }
        $scope.IndiceContribuicaoPrevidencia();

        $scope.TipoAverbacao = function () {

            var config = {
                params: { nomeEnum: "TipoAverbacaoEnum" }
            };

            $scope.promise = GenericoService.GetAll('/listaEnums', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.tipoAverbacao = response.data;
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }
        $scope.TipoAverbacao();

        $scope.Sefip = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaSefips', function (response) {
                if (response.status === 200) {
                    $scope.list.sefip = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.Sefip();

        $scope.EfeitoAverbacao = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaTipoAverbacoes', function (response) {
                if (response.status === 200) {
                    $scope.list.efeitoAverbacao = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.EfeitoAverbacao();

        $scope.Uf = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUnidadesFederativas', function (response) {
                if (response.status === 200) {
                    $scope.list.unidadeFederativa = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.Uf();

        $scope.ClassificacaoAto = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaClassificacoesAtos', function (response) {
                if (response.status === 200) {
                    $scope.list.classificacaoAto = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.ClassificacaoAto();


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
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/tempoServico/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.tempoServico.id) {
                GenericoService.Update('/tempoServico', $scope.tempoServico, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tempoServico/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tempoServico/formulario/');
                    }
                });
            } else {
                GenericoService.Create('/tempoServico', $scope.tempoServico, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tempoServico/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tempoServico/formulario');
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