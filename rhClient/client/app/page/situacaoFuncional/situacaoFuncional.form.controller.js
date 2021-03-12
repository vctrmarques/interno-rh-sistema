(function () {
    'use strict';

    angular.module('app.page')
        .controller('situacaoFuncionalFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', situacaoFuncionalFormCtrl]);

    function situacaoFuncionalFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        $scope.tipos = [{
                nome: "Falta"
            },
            {
                nome: "Afastamento"
            },
            {
                nome: "Suspensão"
            },
            {
                nome: "Desligamento"
            }
        ];

        $scope.modalidades = [{
                nome: "Reserva"
            },
            {
                nome: "Exoneração"
            },
            {
                nome: "Advertência"
            },
            {
                nome: "Dedução"
            },
            {
                nome: "Com Estabilidade"
            },
            {
                nome: "Rescisão"
            },
            {
                nome: "Transferência"
            },
            {
                nome: "Aposentadoria"
            },
            {
                nome: "Reforma"
            }
        ]

        $scope.acessaTela();
        $scope.detalhes = false;

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/situacaoFuncional/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    console.log(response.data);
                    
                    $scope.situacaoFuncional = response.data;
                    if ($scope.situacaoFuncional.motivoAfastamento) {
                        $scope.situacaoFuncional.motivoAfastamentoId = $scope.situacaoFuncional.motivoAfastamento.id;
                    }

                    if ($scope.situacaoFuncional.motivoDesligamento) {
                        $scope.situacaoFuncional.motivoDesligamentoId = $scope.situacaoFuncional.motivoDesligamento.id;
                    }

                    if ($scope.situacaoFuncional.causaAfastamento) {
                        $scope.situacaoFuncional.causaAfastamentoId = $scope.situacaoFuncional.causaAfastamento.id;
                    }
                   
                } else {
                    $scope.showSimpleToast("Afastamento não encontrado na base");
                }
            });
        }

        $scope.listMotivoAfastamento = {
            "data": []
        };

        $scope.listMotivoDesligamento = {
            "data": []
        };

        $scope.listCausaAfastamento = {
            "data": []
        }


        $scope.tipoSelecionado = function (tipo) {
            if (tipo == "Afastamento")
                $scope.situacaoFuncional.motivoDesligamentoId = null;
            if (tipo == "Desligamento")
                $scope.situacaoFuncional.motivoAfastamentoId = null;
        }

        $scope.MotivoAfastamento = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaMotivosAfastamentos', function (response) {
                if (response.status === 200) {
                    $scope.listMotivoAfastamento.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.MotivoDesligamento = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaMotivosDesligamentos', function (response) {
                if (response.status === 200) {
                    $scope.listMotivoDesligamento.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.CausaAfastamento = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCausasAfastamento', function (response) {
                if (response.status === 200) {
                    $scope.listCausaAfastamento.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.MotivoAfastamento();
        $scope.MotivoDesligamento();
        $scope.CausaAfastamento();

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/situacaoFuncional/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.situacaoFuncional.id) {
                GenericoService.Update('/situacaoFuncional', $scope.situacaoFuncional, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/situacaoFuncional/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('situacaoFuncional/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/situacaoFuncional', $scope.situacaoFuncional, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/situacaoFuncional/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/situacaoFuncional/formulario');
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
