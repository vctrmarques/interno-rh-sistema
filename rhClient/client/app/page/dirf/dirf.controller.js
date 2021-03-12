(function () {
	'use strict';

	angular.module('app.page')
		.controller('dirfCtrl', ['$location', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$q', '$rootScope', '$timeout', 'RestService', 'EnumService', '$filter', dirfCtrl]);

	function dirfCtrl($location, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $q, $rootScope, $timeout, RestService, EnumService, $filter) {

		$scope.limitOptions = [5, 10, 15];
		$scope.query = {
			order: '-createdAt',
			limit: 10,
			page: 1
		};

    	$scope.list = {
			"count": 0,
			"data": []
		};
        $scope.lista = {};
		$scope.lista.tipoDeclaracao = [];
		$scope.lista.anexos = [];

		$scope.filtro = {};
		
		EnumService.Get("DirfTipoDeclaracaoEnum").then(function (dados) {
			$scope.lista.tipoDeclaracao = $filter('orderBy')(dados, 'label');
		});
        
		$scope.limpaFiltro = function () {
			// colocar os filtros aqui da busca
			$scope.filtro = {};
			$scope.loadList();
		}

		// Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'DIRF' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_DIRF_GESTAO') ? true : false;

                    if ($scope.usuarioAdm || $scope.podeGerenciar)
                        $scope.loadList();
                    else
                        $location.path('page/403');
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

		$scope.loadList = function () {

        	var config = {
                params: {
                    page: $scope.isReport ? 0 : $scope.query.page - 1,
                    size: $scope.isReport ? $scope.list.count : $scope.query.limit,
                    order: $scope.query.order,
                    anoBase: $scope.filtro.anoBase,
                    tipoDeclaracao: $scope.filtro.tipoDeclaracao
                }
			};

			$rootScope.$broadcast('preloader:active');

			$scope.promise = RestService.Get('/dirf', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
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
			}, function () {
			});
		};

		$scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            RestService.Delete('/dirf/' + $scope.idToDelete)
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
		
		$scope.gerarRelatorioTotal = function(id) {
			var config = {
                params: {},
                responseType: 'arraybuffer'
			};
			
			config.params.id = id;

			gerarRelatorio(config);
		}

		$scope.gerarRelatorioFuncionario = function(id, funcionarioId) {
			var config = {
                params: {},
                responseType: 'arraybuffer'
			};
			
			config.params.id = id;
			config.params.funcionarioId = funcionarioId;

			gerarRelatorio(config);
		}
		
		function gerarRelatorio(config) {
			showCarregandoDialog();

			RestService.Get('/dirf/relatorio/pdf', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    var file = new Blob([response.data], { type: 'application/pdf' });
                    var fileURL = URL.createObjectURL(file);
                    $scope.cancel();
                    $timeout(function(){window.open(fileURL);}, 1000);
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                    $scope.cancel();
                });
		}

        function showCarregandoDialog() {
			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'dirfCtrl',
                scope: $scope,
                preserveScope: true,
                controller: function () {
					$scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/layout/dialogCarregando.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: useFullScreen
            });
		}

		$scope.gerarArquivoDirf = function (id) {
			showCarregandoDialog();
			var config = {
				params: {
					dirfId: id
				}
			};

			RestService.Get('/dirf/arquivo', config)
                .then(function successCallback(response) {
                    $scope.cancel();
                    $scope.lista.anexos = response.data;
					showArquivosGerados();
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                    $scope.cancel();
                });
		}

		function showArquivosGerados() {
			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'dirfCtrl',
                scope: $scope,
                preserveScope: true,
                controller: function () {
					$scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/page/dirf/arquivosGerados.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: useFullScreen
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

        /*
         * Recebe duas datas e retorna o tempo entre elas
         * 
         * param dataInicio - recebe uma data tipo LocalDate
         * param dataFim - recebe uma data tipo LocalDate
         * 
         * */
		$scope.calularPeriodo = function (dataInicio, dataFim, report) {
			dataInicio = moment(dataInicio);
			dataFim = moment(dataFim);

			dataFim = dataFim.add(1, 'days');
			var dias = dataFim.diff(dataInicio, 'days');

			var texto = '';

			if (dias >= 365) {
				var anos = dias / 365;
				anos = parseInt(anos);
				texto += anos < 10 ? '0' + anos : anos;
				if (report) {
					texto += '-';
				} else {
					texto += anos == 1 ? ' Ano, ' : ' Anos, ';
				}
				dias = dias % 365;
			} else {
				if (report) {
					texto += '00-';
				} else {
					texto += '00 Anos, ';
				}
			}

			if (dias >= 30) {
				var meses = dias / 30;
				meses = parseInt(meses);
				texto += meses < 10 ? '0' + meses : meses;
				if (report) {
					texto += '-';
				} else {
					texto += meses == 1 ? ' mês e ' : ' meses e ';
				}
				dias = dias % 30;
			} else {
				if (report) {
					texto += '00-';
				} else {
					texto += '00 meses e ';
				}
			}

			if (dias > 0) {
				texto += dias < 10 ? '0' + dias : dias;
				if (!report) {
					texto += dias == 1 ? ' dia.' : ' dias.';
				}
			} else {
				if (report) {
					texto += '00';
				} else {
					texto += '00 dias.';
				}
			}

			return texto;
		}

	}

})();
