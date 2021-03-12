(function () {
    'use strict';

    angular.module('app.page')
        .controller('arquivoRemessaPagamentoFormCtrl', ['$scope', '$mdToast', '$location', '$filter', '$q', '$state', '$rootScope', 'GenericoService', arquivoRemessaPagamentoFormCtrl]);

    function arquivoRemessaPagamentoFormCtrl($scope, $mdToast, $location, $filter, $q, $state, $rootScope, GenericoService) {

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

        $scope.acessaTela();
        $scope.detalhes = false;

        $scope.folhaSelected = [];

        $scope.lista = {};
        $scope.lista.folhaPagamento = [];

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

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }
        
        $scope.loadList = function () {

        	var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    processamento: $scope.tipoProcessamento ? $scope.tipoProcessamento.id : null,
                    filial: $scope.filial ? $scope.filial.id : null
                }
			};

			$rootScope.$broadcast('preloader:active');

			$scope.promise = GenericoService.GetAll('/arquivoRemessaPagamento/folhaPagamento', config).then(
				function (response) {
                    $rootScope.$broadcast('preloader:hide');
					if (response.status === 200) {
                            $scope.list.data = response.data.content;
    						$scope.list.count = response.data.totalElements;
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
            });
		}

        $scope.querySearchProcessamento = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    GenericoService.GetAll('/tipoProcessamento/searchComplete', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);
                            } else {
                                $scope.showSimpleToast("Nenhum registro encontrado");
                            }
                        });
                }
            }
            return deferred.promise;
        };

        $scope.tipoProcessamentoSelecionado = function () {
            if ($scope.tipoProcessamento && !$scope.detalhes) {
                $scope.loadList();
            }
        };

        $scope.querySearchFilial = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    GenericoService.GetAll('/filiais/searchComplete', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);
                            } else {
                                $scope.showSimpleToast("Nenhum registro encontrado");
                            }
                        });
                }
            }
            return deferred.promise;
        };

        $scope.filialSelecionada = function () {
            if ($scope.filial && !$scope.detalhes) {
                $scope.loadList();
            }
        };

        $scope.goBack = function () {
            $location.path('/arquivoRemessaPagamento/gestao');
        }

        $scope.save = function () {
            if($scope.folhaSelected.length == 0) {
                $scope.showSimpleToast("Selecione a folha de pagamento");
            }
            if($scope.folhaSelected.length > 1) {
                $scope.showSimpleToast("Deve ser selecionada apenas 1 folha de pagamento");
            }
            
            if($scope.folhaSelected.length == 1) {
                $scope.arquivoRemessa.folhaPagamentoId = $scope.folhaSelected[0].id;

                $rootScope.$broadcast('preloader:active');
                if ($scope.arquivoRemessa.id) {
                    GenericoService.Update('/arquivoRemessaPagamento', $scope.arquivoRemessa, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $scope.goBack();
                        } else if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
                } else {
                    GenericoService.Create('/arquivoRemessaPagamento', $scope.arquivoRemessa, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $scope.goBack();
                        } else if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
                }
            }


        }

        $scope.GetById = function (id) {
            $rootScope.$broadcast('preloader:active');
            
            GenericoService.GetById('/arquivoRemessaPagamento/' + id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.arquivoRemessa = response.data;
                    $scope.arquivoRemessa.dataPagamento = moment(response.data.dataPagamento);

                    $scope.tipoProcessamento = $scope.arquivoRemessa.folhaPagamento.tipoProcessamento;
                    $scope.filial = $scope.arquivoRemessa.folhaPagamento.filial;

                    $scope.lista.folhaPagamento.push($scope.arquivoRemessa.folhaPagamento);

                } else {
                    $scope.showSimpleToast("Arquivo remessa não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.id) {
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

    }

})();
