(function () {
	'use strict';

	angular.module('app.page')
		.controller('batimentoFolhaPagamentoRelatorioCtrl', ['$location', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$q', '$rootScope', '$timeout', 'GenericoService', 'EnumService', 'RestService', '$filter', batimentoFolhaPagamentoRelatorioCtrl]);

	function batimentoFolhaPagamentoRelatorioCtrl($location, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $q, $rootScope, $timeout, GenericoService, EnumService, RestService, $filter) {

        $scope.botoesGestao = false;
        
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
			function (response) {
				if (response.status === 200) {
					$scope.botoesGestao = true;
					$scope.loadList();
				}
			}, function errorCallback(response) {
				if (response.status === 400) {
					$location.path('page/403');
				}
			});

        /* // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Rel. Gerencial' } })
        .then(function (response) {
            if (response.status === 200 && response.data) {

                $scope.usuarioAdm = response.data.usuarioAdm;
                $scope.podeGerenciar = false;

                if (response.data.papeis)
                    $scope.podeGerenciar = response.data.papeis.includes('ROLE_RELATORIO_GERENCIAL_GESTAO') ? true : false;

                if (!$scope.usuarioAdm && !$scope.podeGerenciar)
                    $location.path('page/403');
            }
        }, function errorCallback(response) {
            if (response.status === 400) {
                $scope.showSimpleToast(response.data.message);
            }
        }); */

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

        $scope.filtro = {};
        
        $scope.customizacao = {};
        $scope.contraCheque = {};
        $scope.contraCheque.todos = false;
        $scope.verbasSituacao = {};
        $scope.verbasSituacao.todos = false;
        $scope.verbasLotacao = {};
        $scope.verbasLotacao.todos = false;
        $scope.verbasFilial = {};
        $scope.verbasFilial.todos = false;
        $scope.verbasEmpresa = {};
        $scope.verbasEmpresa.todos = false;

        $scope.customizacao.orgao = false;

        $scope.aba = 'CC'
        $scope.nomeBotaoSelecionar = 'Desmarcar todos';
        $scope.classeBotao = 'btn-danger';

        // Busca dos Anos das Comptetências Fechadas
        RestService.Get('/competencia/anos')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.anoCompetenciaList = response.data;
                } else if (response.status === 200 && !response.data) {
                    $scope.showSimpleToast("Não existem anos de competência disponíveis.");
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });
        
        // Busca Comptetências Fechadas por Ano
        $scope.changeAno = function () {
            if ($scope.filtroAno) {
                $scope.competenciaList = [];
                $scope.filtroCompetencia = null;
                $scope.tipoProcessamentoList = [];
                $scope.filtroTipoProcessamento = null;

                $rootScope.$broadcast('preloader:active');
                RestService.Get('/competencia/porAno/folhaBloqueadaConcluida/' + $scope.filtroAno)
                    .then(function (response) {
                        if (response.status === 200 && response.data) {
                            $scope.competenciaList = response.data;
                            $scope.competenciaList.forEach(comp => {
                                comp.mesCompetenciaLabel = get(comp.mesCompetencia);
                            });
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 200 && !response.data) {
                            $scope.showSimpleToast("Não existem meses de competência para o filtro aplicado.");
                        }
                    }, function errorCallback(response) {
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            }
        };

        // Busca Tipos de Processamentos por Competencia
        $scope.changeMes = function () {
            if ($scope.filtroCompetencia) {
                $scope.tipoProcessamentoList = [];
                $scope.filtroTipoProcessamento = null;

                $rootScope.$broadcast('preloader:active');
                RestService.Get('/tipoProcessamento/folha/pagamento/competencia/' + $scope.filtroCompetencia.id)
                    .then(function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 200 && response.data) {
                            $scope.tipoProcessamentoList = response.data;
                        } else if (response.status === 200 && !response.data) {
                            $scope.showSimpleToast("Não existem tipos de processamentos para o filtro aplicado.");
                        }
                    }, function errorCallback(response) {
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            }
        };

        // Carga de meses 
        var meseLabel = {}
        function add(mes, mesLabel) {
            meseLabel[mes] = mesLabel
        }
        function get(mes) {
            return meseLabel[mes]
        }
        add(1, 'Janeiro');
        add(2, 'Fevereiro');
        add(3, 'Março');
        add(4, 'Abril');
        add(5, 'Maio');
        add(6, 'Junho');
        add(7, 'Julho');
        add(8, 'Agosto');
        add(9, 'Setembro');
        add(10, 'Outubro');
        add(11, 'Novembro');
        add(12, 'Dezembro');

        // Rotina responsável por desabilitar o combo de ano da competência.
        $scope.anoDisabled = function () {
            if (!$scope.anoCompetenciaList || $scope.anoCompetenciaList.length === 0)
                return true;
            return false;
        }

        // Rotina responsável por desabilitar o combo de mês da competencia
        $scope.competenciaDisabled = function () {
            if (!$scope.filtroAno
                || !$scope.competenciaList
                || $scope.competenciaList.length === 0)
                return true;
            return false;
        }

        // Rotina responsável por desabilitar o combo de tipo de processamento.
        $scope.tipoProcessamentoDisabled = function () {
            if (!$scope.filtroAno
                || !$scope.filtroCompetencia
                || !$scope.tipoProcessamentoList
                || $scope.tipoProcessamentoList.length === 0)
                return true;
            return false;
        }

        $scope.abaAtual = function (tipo) {

            switch (tipo) {
                case 'CC':
                    selectContraCheque();
                    break;
                case 'VS':
                    selectVerbasSituacao();
                    break;
                case 'VL':
                    selectVerbasLotacao();
                    break;
                case 'VF':
                    selectVerbasFilial();
                    break;
                case 'VE':
                    selectVerbasEmpresa();
                    break;
            }
        }

        function selectContraCheque() {
            $scope.aba = 'CC'
            $scope.nomeBotaoSelecionar = $scope.contraCheque.todos ? 'Desmarcar todos' : 'Selecionar todos';
            $scope.classeBotao = $scope.contraCheque.todos ? 'btn-danger' : 'btn-primary';
        }

        function selectVerbasSituacao() {
            $scope.aba = 'VS'
            $scope.nomeBotaoSelecionar = $scope.verbasSituacao.todos ? 'Desmarcar todos' : 'Selecionar todos';
            $scope.classeBotao = $scope.verbasSituacao.todos ? 'btn-danger' : 'btn-primary';
        }

        function selectVerbasLotacao() {
            $scope.aba = 'VL'
            $scope.nomeBotaoSelecionar = $scope.verbasLotacao.todos ? 'Desmarcar todos' : 'Selecionar todos';
            $scope.classeBotao = $scope.verbasLotacao.todos ? 'btn-danger' : 'btn-primary';
        }

        function selectVerbasFilial() {
            $scope.aba = 'VF'
            $scope.nomeBotaoSelecionar = $scope.verbasFilial.todos ? 'Desmarcar todos' : 'Selecionar todos';
            $scope.classeBotao = $scope.verbasFilial.todos ? 'btn-danger' : 'btn-primary';
        }

        function selectVerbasEmpresa() {
            $scope.aba = 'VE'
            $scope.nomeBotaoSelecionar = $scope.verbasEmpresa.todos ? 'Desmarcar todos' : 'Selecionar todos';
            $scope.classeBotao = $scope.verbasEmpresa.todos ? 'btn-danger' : 'btn-primary';
        }

        $scope.selecionarDesmarcarTodos = function (tipo) {
            switch (tipo) {
                case 'CC':
                    stContraCheque();
                    break;
                case 'VS':
                    stVerbasSituacao();
                    break;
                case 'VL':
                    stVerbasLotacao();
                    break;
                case 'VF':
                    stVerbasFilial();
                    break;
                case 'VE':
                    stVerbasEmpresa();
                    break;
            }
        }

        function stContraCheque() {
            var check;
            if($scope.contraCheque.todos){
                check = $scope.contraCheque.todos ? false : true;
            } else {
                check = true;
                $scope.contraCheque.todos = check;
            }
            $scope.contraCheque = {
                dadosBancarios: check,
                matricula: check,
                funcionario: check,
                lotacao: check,
                funcao: check,
                dependenteIR: check,
                dependenteSalarioFamilia: check,
                dataAdmissao: check,
                situacaoFuncional: check,
                cargaHoraria: check,
                verbasVantagem: check,
                verbasDesconto: check,
                totalizadoresVerbas: check,
                todos: check
            };
            selectContraCheque();
        }

        function stVerbasSituacao() {
            var check;
            if($scope.verbasSituacao.todos){
                check = $scope.verbasSituacao.todos ? false : true;
            } else {
                check = true;
                $scope.verbasSituacao.todos = check;
            }

            $scope.verbasSituacao = {
                resumoLotacao: check,
                resumoFilial: check,
                resumoEmpresa: check,
                vantagens: check,
                descontos: check,
                totaisFuncionarios: check,
                totalizadoresVerbas: check,
                todos: check
            };

            selectVerbasSituacao();
        }

        function stVerbasLotacao() {
            var check;
            if($scope.verbasLotacao.todos){
                check = $scope.verbasLotacao.todos ? false : true;
            } else {
                check = true;
                $scope.verbasLotacao.todos = check;
            }

            $scope.verbasLotacao = {
                vantagens: check,
                descontos: check,
                totaisFuncionarios: check,
                totalizadoresVerbas: check,
                todos: check
            };

            selectVerbasLotacao();
        }

        function stVerbasFilial() {
            var check;
            if($scope.verbasFilial.todos){
                check = $scope.verbasFilial.todos ? false : true;
            } else {
                check = true;
                $scope.verbasFilial.todos = check;
            }

            $scope.verbasFilial = {
                vantagens: check,
                descontos: check,
                totaisFuncionarios: check,
                totalizadoresVerbas: check,
                todos: check
            };

            selectVerbasFilial();
        }

        function stVerbasEmpresa() {
            var check;
            if($scope.verbasEmpresa.todos){
                check = $scope.verbasEmpresa.todos ? false : true;
            } else {
                check = true;
                $scope.verbasEmpresa.todos = check;
            }

            $scope.verbasEmpresa = {
                vantagens: check,
                descontos: check,
                totaisFuncionarios: check,
                totalizadoresVerbas: check,
                todos: check
            };

            selectVerbasEmpresa();
        }

        function selecionarTodos(isOrgao) {
            stContraCheque();
            stVerbasSituacao();
            stVerbasLotacao();
            stVerbasFilial();
            if(isOrgao){
                stVerbasEmpresa();
            }
        }

        function resetaMarcadores() {
            $scope.contraCheque.todos = false;
            $scope.verbasSituacao.todos = false;
            $scope.verbasLotacao.todos = false;
            $scope.verbasFilial.todos = false;
            $scope.verbasEmpresa.todos = false;
        }
		
   		$scope.limpaFiltro = function () {
			// colocar os filtros aqui da busca
            delete $scope.filtroCompetencia;
            delete $scope.filtroAno;
            delete $scope.filtroTipoProcessamento;
            delete $scope.dadosMatriz;
			$scope.list = {
                "count": 0,
                "data": []
            };
			
		}

		$scope.loadList = function () {
            if($scope.filtroCompetencia && $scope.filtroAno && $scope.filtroTipoProcessamento) {
                var config = {
                    params: {
                        page: $scope.query.page - 1,
                        size: $scope.query.limit,
                        order: $scope.query.order,
                        competencia: $scope.filtroCompetencia.mesCompetencia,
                        tipoProcessamento: $scope.filtroTipoProcessamento ? $scope.filtroTipoProcessamento.id : null,
                        ano: $scope.filtroAno
                    }
                };

                var configOrgao = {
                    params: {
                        competencia: $scope.filtroCompetencia.mesCompetencia,
                        tipoProcessamento: $scope.filtroTipoProcessamento ? $scope.filtroTipoProcessamento.id : null,
                        ano: $scope.filtroAno
                    }
                };
    
                $rootScope.$broadcast('preloader:active');
                $scope.promiseOrgao = GenericoService.GetAll('/batimentoFolhaPagamento/orgao', configOrgao).then(
                    function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 200) {
                            $scope.dadosMatriz = response.data;
                        } else {
                            $scope.showSimpleToast("Não foi possível carregar os dados.")
                        }
                    });
                
                $rootScope.$broadcast('preloader:active');
                $scope.promise = GenericoService.GetAll('/batimentoFolhaPagamento', config).then(
                    function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 200) {
                            $scope.list.data = response.data.content;
                            $scope.list.count = response.data.totalElements;
                        } else {
                            $scope.showSimpleToast("Não foi possível carregar os dados.")
                        }
                    });
            } else {
                $scope.showSimpleToast("Preenchimento dos filtros é obrigatório para realizar a pesquisa")
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

		$scope.showCustomizacao = function (isOrgao, id, nome) {
            $scope.customizacao.orgao = isOrgao;
            $scope.customizacao.id = id;
            $scope.nome = nome;
            resetaMarcadores();
            selecionarTodos(isOrgao);

			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'batimentoFolhaPagamentoRelatorioCtrl',
                scope: $scope,
                preserveScope: true,
                bindToController: true,
                controller: function () {
					$scope.cancel = function () {
                        $mdDialog.cancel();
                    };

                    $scope.imprimir = function (isPdf) {
                        if(isPdf){
                            imprimirPdf();
                        } else {
                            imprimirXls();
                        }
                        
                    }
                },
                templateUrl: 'app/page/batimentoFolhaPagamento/dialogAcoes.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: useFullScreen
            });
        }
        
        function imprimirPdf() {

            $scope.cancel();
            showCarregandoDialog();

            var config = {
                params: {},
                responseType: 'arraybuffer'
            };

            config.params.id = $scope.customizacao.id;
            config.params.orgao = $scope.customizacao.orgao;
            config.params.competencia = $scope.filtroCompetencia.mesCompetencia;
            config.params.tipoProcessamentoId = $scope.filtroTipoProcessamento.id;
            config.params.ano = $scope.filtroAno;
            
            // Filtro para customização de contracheque
            if($scope.contraCheque) {
                config.params.dadosBancariosContracheque = $scope.contraCheque.dadosBancarios;
                config.params.matriculaContracheque = $scope.contraCheque.matricula;
                config.params.funcionarioContracheque = $scope.contraCheque.funcionario;
                config.params.lotacaoContracheque = $scope.contraCheque.lotacao;
                config.params.funcaoContracheque = $scope.contraCheque.funcao;
                config.params.dependenteIrContracheque = $scope.contraCheque.dependenteIR;
                config.params.dependenteSalarioFamiliaContracheque = $scope.contraCheque.dependenteSalarioFamilia;
                config.params.dataAdmissaoContracheque = $scope.contraCheque.dataAdmissao;
                config.params.situacaoFuncionalContracheque = $scope.contraCheque.situacaoFuncional;
                config.params.cargaHorariaContracheque = $scope.contraCheque.cargaHoraria;
                config.params.vantagensContracheque = $scope.contraCheque.verbasVantagem;
                config.params.descontosContracheque = $scope.contraCheque.verbasDesconto;
                config.params.totalizadoresContracheque = $scope.contraCheque.totalizadoresVerbas;
            }

            // Filtro para customização de verbas situação
            if($scope.verbasSituacao) {
                config.params.resumoLotacaoSituacao = $scope.verbasSituacao.resumoLotacao;
                config.params.resumoFilialSituacao = $scope.verbasSituacao.resumoFilial;
                config.params.resumoEmpresaSituacao = $scope.verbasSituacao.resumoEmpresa;
                config.params.vantagensSituacao = $scope.verbasSituacao.vantagens;
                config.params.descontosSituacao = $scope.verbasSituacao.descontos;
                config.params.totaisFuncionariosSituacao = $scope.verbasSituacao.totaisFuncionarios;
                config.params.totalizadoresSituacao = $scope.verbasSituacao.totalizadoresVerbas;
            }

            // Filtro para customização de verbas lotação
            if($scope.verbasLotacao) {
                config.params.vantagensLotacao = $scope.verbasLotacao.vantagens;
	            config.params.descontosLotacao = $scope.verbasLotacao.descontos;
                config.params.totaisFuncionariosLotacao = $scope.verbasLotacao.totaisFuncionarios;
                config.params.totalizadoresLotacao = $scope.verbasLotacao.totalizadoresVerbas;
            }

            // Filtro para customização de verbas filial
            if($scope.verbasFilial) {
                config.params.vantagensFilial = $scope.verbasFilial.vantagens;
	            config.params.descontosFilial = $scope.verbasFilial.descontos;
                config.params.totalizadoresFilial = $scope.verbasFilial.totalizadoresVerbas;
                config.params.totaisFuncionariosFilial = $scope.verbasFilial.totaisFuncionarios;
            }

            // Filtro para customização de verbas empresa caso o orgão seja escolhido
            if(config.params.orgao && $scope.verbasEmpresa) {
                config.params.vantagensEmpresa = $scope.verbasEmpresa.vantagens;
	            config.params.descontosEmpresa = $scope.verbasEmpresa.descontos;
                config.params.totalizadoresEmpresa = $scope.verbasEmpresa.totalizadoresVerbas;
                config.params.totaisFuncionariosEmpresa = $scope.verbasEmpresa.totaisFuncionarios;
            }

            RestService.Get('/batimentoFolhaPagamento/relatorio/pdf', config)
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
                require: 'batimentoFolhaPagamentoRelatorioCtrl',
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


	}

})();
