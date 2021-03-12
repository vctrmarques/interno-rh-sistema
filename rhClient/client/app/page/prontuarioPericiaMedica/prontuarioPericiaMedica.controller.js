(function () {
    'use strict';

    angular.module('app.page')
        .controller('prontuarioPericiaMedicaCtrl', ['configValue', '$q', '$mdDialog', '$mdMedia', '$scope', '$mdToast', '$rootScope', '$filter', 'EnumService', 'GenericoService', 'RestService', prontuarioPericiaMedicaCtrl]);

    function prontuarioPericiaMedicaCtrl(configValue, $q, $mdDialog, $mdMedia, $scope, $mdToast, $rootScope, $filter, EnumService, GenericoService, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Perícias Médicas Agendadas' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_PRONTUARIO_PERICIA_MEDICA_GESTAO') ? true : false;

                    if ($scope.usuarioAdm  || $scope.podeGerenciar){
                        $scope.loadList();
                    } else{
                        $location.path('page/403');
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        var tabs = [];
        $scope.tabs = tabs;
        $scope.search = "";
        $scope.situacoesFuncionais = "";
        $scope.menorData = new Date();

        $scope.query = {
            order: '',
            limit: 10,
            page: 1
        };

        $scope.consutlaPericiaMedica = {
            nome : ""
        };

        $scope.list = {
            "especialidadeMedicaList": [],
            "count": 0,
            "data": [],
            "status": [
                { value: "COMPARECEU", label: "Compareceu" },
                { value: "NAO_COMPARECEU", label: "Não Compareceu" }
            ]
        };

        $scope.limitOptions = [5, 10, 15];
        $scope.selectedEspecialidadeMedica = [];

        $scope.limpaFiltro = function () {
            delete $scope.dataAgendamento;
            delete $scope.search;
            delete $scope.compareceu;
            $scope.loadList();
        }

        EnumService.Get("TipoAnaliseEnum").then(function (dados) {
            $scope.list.tipoAnaliseList = dados;
        });


        $scope.loadList = function () {
            var isCompacereu = false;
            $rootScope.$broadcast('preloader:active');

            if($scope.compareceu == "Compareceu"){
                isCompacereu = true;
            } else if($scope.compareceu == "Não Compareceu"){
                isCompacereu = false;
            }else {
                isCompacereu = null;
            }

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    search: $scope.search,
                    dtAgendamento: $scope.dataAgendamento ? $scope.dataAgendamento.format('YYYY-MM-DD') : null,
                    compareceu : isCompacereu
                }
            };
            
            $scope.promise = RestService.Get('/consultasPericiasMedicas', config)
            .then(function successCallback(response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.list.consultaPericiaMedicaAgendadoList = response.data.content;

                    if($scope.list.consultaPericiaMedicaAgendadoList.length > 0){
                        var consultaPericia = $scope.list.consultaPericiaMedicaAgendadoList[0];
                        $scope.nomeCoordenador = consultaPericia.nomeMedico;
                    }
                }
            }, function errorCallback(response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 400) {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            });

        }


         /*
         * Recebe data e converte para valor com hora ou não
         * 
         * param data - recebe uma data tipo LocalDate
         * param comHora - recebe um valor boolean
         * 
         * */
		$scope.convertDate = function(data, comHora) {
			var valor = moment(data);
			if (comHora) {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
			} else {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy');
			}
		}

        $scope.showConfirm = function (ev, idToDelete) {
            $scope.idToDelete = idToDelete;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão desta Agenda Médica?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.deleteItem();
            }, function () {
            });
        };

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            RestService.Delete('/consultaPericiaMedica/' + $scope.idToDelete)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.showSimpleToast(response.data.message);
                        $scope.loadList();
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        }

        $scope.showConfirmNaoAtendimento = function (ev, cpfToUpdate) {
            $scope.cpfToUpdate = cpfToUpdate;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar o não atendimento do Agendamento?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.updateItem();
            }, function () {
            });
        };

        $scope.updateItem = function () {
            $rootScope.$broadcast('preloader:active');
                RestService.Update('/consultaPericiaMedica/naoCompareceu', $scope.cpfToUpdate)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/periciaMedica/gestao');
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
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

    }

})();



