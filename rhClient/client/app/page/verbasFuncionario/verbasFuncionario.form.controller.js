(function () {
    'use strict';

    angular.module('app.page')
        .controller('verbasFuncionarioFormCtrl', ['$scope', '$mdDialog', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', verbasFuncionarioFormCtrl]);

    function verbasFuncionarioFormCtrl($scope, $mdDialog, $mdToast, $location, $state, $rootScope, GenericoService) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        moment.locale('pt-BR');
        $scope.detalhes = false;
        $scope.verbaFuncionario = {};
        $scope.verbaFuncionario.id = '';
        $scope.verbasFuncionario = [];

        $scope.adicionar = function () {

            if (!$scope.verbaFuncionario.verba) {
                $scope.showSimpleToast("Favor selecionar uma verba.");
                return;
            }

            if (!$scope.verbaFuncionario.tipo) {
                $scope.showSimpleToast("Favor selecionar o tipo.");
                return;
            }

            if (!$scope.verbaFuncionario.recorrencia) {
                $scope.showSimpleToast("Favor selecionar a recorrência.");
                return;
            }

            if (!$scope.verbaFuncionario.valor) {
                $scope.showSimpleToast("Favor inserior o valor.");
                return;
            }

            if ($scope.verbaFuncionario.recorrencia === 'EM_PARCELA' && !$scope.verbaFuncionario.parcelas) {
                $scope.showSimpleToast("Favor inserir as parcelas.");
                return;
            }

            $scope.verbaFuncionario.funcionarioId = $state.params.funcionarioId;
            $scope.verbaFuncionario.verbaId = $scope.verbaFuncionario.verba.id;
            $scope.verbaFuncionario.tipo = $scope.verbaFuncionario.tipo.label;
            $scope.verbaFuncionario.recorrencia = $scope.verbaFuncionario.recorrencia.label;
            $scope.verbasFuncionario.push($scope.verbaFuncionario);
            $scope.verbaFuncionario = {};
        }

        $scope.removerVerba = function (index) {
            $scope.verbasFuncionario.splice(index, 1);
        }

        if ($state.params && $state.params.funcionarioId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcionarioVerbas/' + $state.params.funcionarioId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.verbasFuncionario = response.data;
                    $scope.carregaFuncionario();
                } else {
                    $scope.showSimpleToast("FuncionárioVerbas não encontrado na base");
                }
            });
        }

        $scope.carregaFuncionario = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcionario/' + $state.params.funcionarioId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcionario = response.data;
                } else {
                    $scope.showSimpleToast("FuncionárioVerbas não encontrado na base");
                }
            });
        }

        $scope.goBack = function () {
            $location.path('/verbasFuncionario/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.listaRequest = {
                "funcionario": $scope.funcionario,
                "verbasFuncionario": $scope.verbasFuncionario
            };

            GenericoService.Create('/funcionarioVerbas/', $scope.listaRequest, function (response) {
                console.log($scope.verbasFuncionario);
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/verbasFuncionario/gestao');
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/verbasFuncionario/formulario/' + $state.params.funcionarioId);
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

        $scope.showConfirm = function (ev, index) {
            $scope.index = index;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão deste item?')
                .textContent('Ao clicar em "Salvar", essa ação não poderá ser desfeita')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.removerVerba(index);
            }, function () { });
        };
    }
})(); 