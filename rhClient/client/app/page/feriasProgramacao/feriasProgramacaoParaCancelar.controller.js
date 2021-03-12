(function () {
    'use strict';

    angular.module('app.page')
        .controller('feriasProgramacaoParaCancelarCtrl', ['configValue', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', feriasProgramacaoParaCancelarCtrl]);

    function feriasProgramacaoParaCancelarCtrl(configValue, $scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        $scope.selectedList = [];
        $scope.feriasProgramacaoCancelar = {
            motivo: "",
            ids: []
        };

        $scope.funcionarios = {};

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            if ($scope.dataExericioInicio)
                $scope.dataExericioInicio = moment($scope.dataExericioInicio).format('dd/MM/YYYY');

            var config = {
                params: {
                    matricula: $scope.matriculaBusca,
                    nome: $scope.nomeBusca,
                    dataExericioInicio: $scope.dataExericioInicio
                }
            };

            $scope.promise = GenericoService.GetAll('/funcionario/searchNomeOrMatriculaOrExercicioInicial', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.funcionarios = response.data;
                        $rootScope.$broadcast('preloader:hide');

                        if(response.data.length > 0){
                            $scope.validateForm = false;
                        }else{
                            $scope.showSimpleToast("Não existem funcionários com férias em aberto.")
                        }
                    }
                });

        }

        $scope.limpaFiltro = function () {
            delete $scope.matriculaBusca;
            delete $scope.nomeBusca;
            delete $scope.dataExercicioInicio;

            $scope.loadList();
        }

        $scope.toggleAll = function() {
            var toggleStatus = !$scope.isAllSelected;
            angular.forEach($scope.options, function(itm){ itm.selected = toggleStatus; });
          
         }
         
         $scope.optionToggled = function(){
           $scope.isAllSelected = $scope.options.every(function(itm){ return itm.selected; })
         }        

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            for (var index = 0; index < $scope.selectedList.length; index++) {
                var element = $scope.selectedList[index];

                for (var k in element) {
                    if (typeof element[k] !== 'function') {
                        if (element[k]) {
                            $scope.feriasProgramacaoCancelar.ids.push(parseInt(k));
                        } else {
                            $scope.feriasProgramacaoCancelar.ids.splice(index,1);
                        }
                    }
                }

            }

            GenericoService.Update('/feriasProgramacao/atualizarStatusParaCancelado', $scope.feriasProgramacaoCancelar, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/feriasProgramacao/gestao');
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/feriasProgramacao/feriasParaCancelar');
                }
            });
        }

        $scope.goBack = function () {
            $location.path('/feriasProgramacao/gestao');
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