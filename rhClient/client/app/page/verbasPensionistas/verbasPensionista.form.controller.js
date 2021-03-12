(function () {
    'use strict';

    angular.module('app.page')
        .controller('verbasPensionistaFormCtrl', ['$scope', '$mdDialog', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', verbasPensionistaFormCtrl]);

    function verbasPensionistaFormCtrl($scope, $mdDialog, $mdToast, $location, $state, $rootScope, GenericoService) {

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
        $scope.verbaPensionista = {};
        $scope.verbaPensionista.id = '';
        $scope.verbasPensionista = [];

        $scope.adicionar = function () {

            if (!$scope.verbaPensionista.verba) {
                $scope.showSimpleToast("Favor selecionar uma verba.");
                return;
            }

            if (!$scope.verbaPensionista.tipo) {
                $scope.showSimpleToast("Favor selecionar o tipo.");
                return;
            }

            if (!$scope.verbaPensionista.recorrencia) {
                $scope.showSimpleToast("Favor selecionar a recorrência.");
                return;
            }

            if (!$scope.verbaPensionista.valor) {
                $scope.showSimpleToast("Favor inserior o valor.");
                return;
            }

            if ($scope.verbaPensionista.recorrencia === 'EM_PARCELA' && !$scope.verbaPensionista.parcelas) {
                $scope.showSimpleToast("Favor inserir as parcelas.");
                return;
            }

            $scope.verbaPensionista.pensionistaId = $state.params.pensionistaId;
            $scope.verbaPensionista.verbaId = $scope.verbaPensionista.verba.id;
            $scope.verbaPensionista.tipo = $scope.verbaPensionista.tipo.label;
            $scope.verbaPensionista.recorrencia = $scope.verbaPensionista.recorrencia.label;
            $scope.verbasPensionista.push($scope.verbaPensionista);
            $scope.verbaPensionista = {};
        }

        $scope.removerVerba = function (index) {
            $scope.verbasPensionista.splice(index, 1);
        }

        if ($state.params && $state.params.pensionistaId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/pensionistaVerbas/' + $state.params.pensionistaId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.verbasPensionista = response.data;
                    $scope.carregaPensionista();
                } else {
                    $scope.showSimpleToast("PensionistaVerbas não encontrado na base");
                }
            });
        }

        $scope.carregaPensionista = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/pensaoPrevidenciaria/' + $state.params.pensionistaId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.pensionista = response.data;
                } else {
                    $scope.showSimpleToast("Pensionista não encontrado na base");
                }
            });
        }

        $scope.goBack = function () {
            $location.path('/verbasPensionistas/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.listaRequest = {
                "pensionista": $scope.pensionista,
                "verbasPensionista": $scope.verbasPensionista
            };

            GenericoService.Create('/pensionistaVerbas/', $scope.listaRequest, function (response) {
                console.log($scope.verbasPensionista);
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/verbasPensionistas/gestao');
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/verbasPensionistas/formulario/' + $state.params.pensionistaId);
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