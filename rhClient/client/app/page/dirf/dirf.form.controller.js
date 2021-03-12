(function () {
    'use strict';

    angular.module('app.page')
        .controller('dirfFormCtrl', ['$scope', '$mdToast', '$location', '$filter', '$q', '$mdDialog', '$mdMedia', '$state', '$timeout', '$rootScope', 'RestService', 'EnumService', dirfFormCtrl]);

    function dirfFormCtrl($scope, $mdToast, $location, $filter, $q, $mdDialog, $mdMedia, $state, $timeout, $rootScope, RestService, EnumService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'DIRF' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_DIRF_CADASTRAR', 'ROLE_DIRF_ATUALIZAR', 'ROLE_DIRF_VISUALIZAR') ? true : false;

                    $scope.autorizado = ($scope.usuarioAdm || $scope.podeGerenciar);

                    if ($scope.autorizado){
                        $scope.loadList();
                    } else {
                        $location.path('page/403');
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        $scope.detalhes = false;
        $scope.bloquearNumero = true;

        $scope.dirf = {};
        $scope.lista = {};
        $scope.lista.tipoDeclaracao = [];

        $scope.lista.naturezaDeclarante = [
            {value: 0, label: 'Pessoa jurídica de direito privado'},
            {value: 1, label: 'Órgãos, autarquias e fundações da administração pública federal'},
            {value: 2, label: 'Órgãos, autarquias e fundações da administração pública estadual, municipal ou do Distrito Federal'},
            {value: 3, label: 'Empresa pública ou sociedade de economia mista federal'},
            {value: 4, label: 'Empresa pública ou sociedade de economia mista estadual, municipal ou do Distrito Federal'},
            {value: 8, label: 'Entidade com alteração de natureza jurídica (uso restrito)'}
        ];

        $scope.dirf.naturezaDeclarante = 2;

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
        $scope.beneficiario = {};
        $scope.beneficiario.limitOptions = [5, 10, 15];
		$scope.beneficiario.query = {
			order: 'nome',
			limit: 10,
			page: 1
		};

    	$scope.beneficiario.list = {
			"count": 0,
			"data": []
		};
 
        $scope.limiteAnoBase = new Date().getFullYear() - 1;

        EnumService.Get("DirfTipoDeclaracaoEnum").then(function (dados) {
			$scope.lista.tipoDeclaracao = $filter('orderBy')(dados, 'label');
        });
        
        $scope.querySearchResponsavel = function (query) {
            var deferred = $q.defer();
            if (query && query.length > 10) {
                $rootScope.$broadcast('preloader:active');
                var config = { params: { search: query } };
                $scope.cpf = query;
                RestService.Get('/dirf/responsavel/search', config)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.data && response.data.length > 0)
                            deferred.resolve(response.data);
                        else
                            deferred.resolve([]);
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400)
                            $scope.showSimpleToast(response.data.message);
                    });
            }
            return deferred.promise;
        };

        $scope.responsavelSelecionado = function () {
            if ($scope.responsavel) {
                $scope.dirf.responsavelReceita = $scope.responsavel;
            }
        };
        
        $scope.loadList = function () {

        	var config = {
                params: {
                    page: $scope.isReport ? 0 : $scope.query.page - 1,
                    size: $scope.isReport ? $scope.list.count : $scope.query.limit,
                    order: $scope.query.order
                }
			};

			$rootScope.$broadcast('preloader:active');

			$scope.promise = RestService.Get('/dirf/filiais', config)
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

        $scope.beneficiarioLimpaFiltro = function () {
			// colocar os filtros aqui da busca
			delete $scope.nomeCpf;
			$scope.beneficiarioLoadList();
		}

        $scope.beneficiarioLoadList = function () {

        	var config = {
                params: {
                    page: $scope.beneficiario.query.page - 1,
                    size: $scope.beneficiario.query.limit,
                    order: $scope.beneficiario.query.order,
                    dirfId: $scope.dirf.id,
                    query: $scope.nomeCpf
                }
			};

			$rootScope.$broadcast('preloader:active');

			$scope.beneficiarioPromise = RestService.Get('/dirf/beneficiarios', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.beneficiario.list.data = response.data.content;
                        $scope.beneficiario.list.count = response.data.totalElements;
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
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
        
        $scope.$watch("dirf.tipoDeclaracao",function(newValue,oldValue){
            if($scope.dirf.tipoDeclaracao){
                if($scope.dirf.tipoDeclaracao == 'ORIGINAL'){
                    $scope.bloquearNumero = true;
                    if($scope.dirf.numeroDeclaracao)
                        delete $scope.dirf.numeroDeclaracao;
                } else {
                    $scope.bloquearNumero = false;
                }
            }
        });

        $scope.goBack = function () {
            $location.path('/dirf/gestao');
        }

        $scope.selectFilial = function(obj) {
            $scope.dirf.filial = obj;
        }

        $scope.deSelectFilial = function() {
            delete $scope.dirf.filial;
        }

        $scope.checkTipoDeclaracao = function() {
            if($scope.dirf.id) {
                return $scope.detalhes;
            } else {
                $scope.dirf.tipoDeclaracao = 'ORIGINAL';
                return true;
            }
        }

        $scope.checkSelect = function() {
            if($scope.detalhes){
                return false;
            } else {
                return $scope.autorizado ? ($scope.dirf.filial ? false : true) : false;
            }
        }
        
        $scope.checkDeSelectFilial = function(obj) {
            if($scope.detalhes){
                return false;
            } else {
                return $scope.dirf.filial ? ($scope.dirf.filial.id == obj.id ? true : false) : false;
            }
        }

        $scope.checkEstilo = function(obj) {
            $scope.estilo = {'background-color':'#81cbc4'};
            return $scope.dirf.filial ? ($scope.dirf.filial.id == obj.id ? $scope.estilo : '') : '';
        }

        $scope.checkDirf = function() {
            return $scope.dirf.id ? !$scope.detalhes : true;
        }


        $scope.save = function () {
            $scope.podeSalvar = true;

            if(!$scope.dirf.tipoDeclaracao) {
                $scope.showSimpleToast("Selecione a tipo de declaração");
                $scope.podeSalvar = false;
            }
            if(!$scope.dirf.filial) {
                $scope.showSimpleToast("Selecione uma filial");
                $scope.podeSalvar = false;
            }
            if($scope.dirf.tipoDeclaracao && $scope.dirf.tipoDeclaracao == 'RETIFICADORA' && !$scope.dirf.numeroDeclaracao) {
                $scope.showSimpleToast("Sua declaração é retificadora, informe o número da declaração");
                $scope.podeSalvar = false;
            }

            if(!$scope.dirf.responsavelReceita.cpf && $scope.cpf) {
                $scope.dirf.responsavelReceita.cpf = $scope.cpf;
                delete $scope.dirf.responsavelReceita.id;
            }

            if($scope.podeSalvar) {

                $rootScope.$broadcast('preloader:active');
                if ($scope.dirf.id) {
                    RestService.Update('/dirf', $scope.dirf)
                        .then(function successCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 201 && response.data.success) {
                                $scope.showSimpleToast(response.data.message);
                                $scope.goBack();
                            }
                        }, function errorCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 400) {
                                $scope.showSimpleToast(response.data.message);
                            }
                        });
                } else {
                    RestService.Create('/dirf', $scope.dirf)
                        .then(function successCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 201 && response.data.success) {
                                $scope.showSimpleToast(response.data.message);
                                $scope.goBack();
                            }
                        }, function errorCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 400) {
                                $scope.showSimpleToast(response.data.message);
                            }
                        });
                }
            }


        }

        $scope.GetById = function (id) {
            $rootScope.$broadcast('preloader:active');
            RestService.Get('/dirf/' + $state.params.id).then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    $scope.dirf = response.data;
                    $scope.dirf.tipoDeclaracao =  angular.uppercase($scope.dirf.tipoDeclaracao);
                    $scope.responsavel = $scope.dirf.responsavelReceita;
                    $scope.beneficiarioLoadList();
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
            });
        }

        if ($state.params && $state.params.id) {
            $scope.detalhes = true;
            $scope.GetById($state.params.id);
        }
        

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                .textContent(textContent)
                .position('top right')
                .hideDelay(3000)
            );
        };

        /*
         * Recebe data e converte para valor com hora ou não
         * 
         * param data - recebe uma data tipo LocalDate
         * param comHora - recebe um valor boolean
         * 
         * */
        $scope.convertDate = function (data, comHora) {
            if (data) {
                var valor = moment(data);
                if (comHora) {
                    return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
                } else {
                    return $filter('date')(new Date(valor), 'dd/MM/yyyy');
                }
            } else {
                return null;
            }
        }

        $scope.showInfoResponsavel = function(ev) {
            // Appending dialog to document.body to cover sidenav in docs app
            // Modal dialogs should fully cover application
            // to prevent interaction outside of dialog
            $mdDialog.show(
              $mdDialog.alert()
                .title('Informação Responsável')
                .textContent('Ao informar o CPF, o sistema irá buscar o responsável já cadastrado! Caso não seja sugerido nenhum nome, o usuário deve preencher os campos!')
                .ok('Fechar')
                .targetEvent(ev)
            );
          };

    }

})();
