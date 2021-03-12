(function () {
	'use strict';

	angular.module('app.page')
		.controller('arquivoRemessaPagamentoCtrl', ['$location', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$q', '$rootScope', 'GenericoService', 'EnumService', '$filter', arquivoRemessaPagamentoCtrl]);

	function arquivoRemessaPagamentoCtrl($location, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $q, $rootScope, GenericoService, EnumService, $filter) {

		$scope.botoesGestao = false;

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
		$scope.lista.situacao = [];
		$scope.lista.historicoSituacoes = [];
		$scope.lista.anexos = [];

		$scope.isReport = false;

		$scope.filtro = {};
		
		$scope.novaSituacao = {};
        
        $scope.selectedSituacoes = [];
		EnumService.Get("ArquivoRemessaPagamentoSituacaoEnum").then(function (dados) {
            $scope.lista.situacao = $filter('orderBy')(dados, 'label');
            getSituacoesIniciais();
		});
        
        function getSituacoesIniciais() {
            angular.forEach($scope.lista.situacao, function (e) {
                if(e.value != 'REJEITADO' && e.value != 'CANCELADO' && e.value != 'ERRO_GERACAO_ARQUIVO'){
                    $scope.selectedSituacoes.push(e);
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
            if ($scope.tipoProcessamento) {
                
            }
        };
        
		$scope.limpaFiltro = function () {
			// colocar os filtros aqui da busca
			$scope.filtro = {};
			getSituacoesIniciais();
			$scope.isReport = false;
			$scope.loadList();
		}

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

		$scope.loadList = function () {

        	var config = {
                params: {
                    page: $scope.isReport ? 0 : $scope.query.page - 1,
                    size: $scope.isReport ? $scope.list.count : $scope.query.limit,
                    order: $scope.query.order,
                    competencia: $scope.competencia,
                    tipoProcessamento: $scope.tipoProcessamento ? $scope.tipoProcessamento.id : null,
                    situacao: $scope.selectedSituacoes
                }
			};

			$rootScope.$broadcast('preloader:active');

			$scope.promise = GenericoService.GetAll('/arquivosRemessaPagamento', config).then(
				function (response) {
					if (response.status === 200) {
						if ($scope.isReport) {
							GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
								function (dataURL) {
									pdfMake.createPdf(getDocDefinition(response.data.content, dataURL)).open();
									$scope.isReport = false;
								});
						} else {
							$scope.list.data = response.data.content;
						}

						$scope.list.count = response.data.totalElements;
						$rootScope.$broadcast('preloader:hide');
					} else {
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
			GenericoService.Delete('/arquivoRemessaPagamento/' + $scope.idToDelete, function (response) {
				$rootScope.$broadcast('preloader:hide');
				if (response.status === 410) {
					$scope.showSimpleToast(response.data.message);
					$scope.loadList();
				}
			});
		}

		$scope.showConfirmAlterarSituacao = function (ev, arquivoRemessa) {
			$scope.novaSituacao.arquivoRemessa = angular.copy(arquivoRemessa);
			// Appending dialog to document.body to cover sidenav in docs app
			var confirm = $mdDialog.confirm()
				.title('Deseja alterar a situação?')
				.textContent('Esta ação não poderá ser desfeita.')
				// .ariaLabel('Lucky day')
				.targetEvent(ev)
				.ok('Sim')
				.cancel('Não');

			$mdDialog.show(confirm).then(function () {
				showAlterarSituacaoDialog();
			}, function () {
			});
		};

		$scope.alterarSituacao = function () {
			$rootScope.$broadcast('preloader:active');
			GenericoService.Update('/arquivoRemessaPagamento', $scope.novaSituacao.arquivoRemessa, function (response) {
				$rootScope.$broadcast('preloader:hide');
				if (response.status === 201 && response.data.success) {
					$scope.showSimpleToast(response.data.message);
					$scope.cancel();
					$scope.loadList();
				} else if (response.status === 400) {
					$scope.showSimpleToast(response.data.message);
				}
			});
		}

		function showAlterarSituacaoDialog() {
			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'arquivoRemessaPagamentoCtrl',
                scope: $scope,
                preserveScope: true,
                controller: function () {

					$scope.updateSituacao = function () {
						$scope.alterarSituacao();
					};

                    $scope.cancel = function () {
                        $scope.novaSituacao = {};
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/page/arquivoRemessaPagamento/alterarSituacao.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: useFullScreen
            });
		}

		$scope.historicoSituacoes = function (id) {
			
			var config = {
				params: {
					arquivoRemessaId: id
				}
			};
			
			$rootScope.$broadcast('preloader:active');
			$scope.historico = GenericoService.GetAll('/arquivoRemessaPagamento/historicoSituacoes', config).then(
				function (response) {
					$rootScope.$broadcast('preloader:hide');
					if (response.status === 200) {
						$scope.lista.historicoSituacoes = response.data;
						showHistoricoSituacoesDialog();
					} else {
						$scope.showSimpleToast("Não foi possível carregar os dados.")
					}
				});
            
		};

		function showHistoricoSituacoesDialog() {
			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'arquivoRemessaPagamentoCtrl',
                scope: $scope,
                preserveScope: true,
                controller: function () {
					$scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/page/arquivoRemessaPagamento/historicoSituacao.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: useFullScreen
            });
		}

		$scope.gerarArquivoRemessa = function (id) {
			showCarregandoDialog();
			var config = {
				params: {
					arquivoRemessaId: id
				}
			};
			
			
			$scope.arquivoRemessa = GenericoService.GetAll('/arquivoRemessaPagamento/arquivo', config).then(
				function (response) {
					$scope.cancel();
					if (response.status === 200) {
						$scope.lista.anexos = response.data;
						showArquivosGerados();
					} else {
						$scope.showSimpleToast("Não foi possível gerar o arquivo.")
					}
				});
		}

		function showCarregandoDialog() {
			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'arquivoRemessaPagamentoCtrl',
                scope: $scope,
                preserveScope: true,
                controller: function () {
					$scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/page/arquivoRemessaPagamento/carregando.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: useFullScreen
            });
		}

		function showArquivosGerados() {
			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'arquivoRemessaPagamentoCtrl',
                scope: $scope,
                preserveScope: true,
                controller: function () {
					$scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/page/arquivoRemessaPagamento/arquivosGerados.html',
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

		$scope.showRelatorioLiquidos = function (id) {
			
			var config = {
				params: {
					arquivoRemessaId: id
				}
			};
			
			$rootScope.$broadcast('preloader:active');
			$scope.relatorioLiquido = GenericoService.GetAll('/arquivoRemessaPagamento/relatorioLiquidos', config).then(
				function (response) {
					$rootScope.$broadcast('preloader:hide');
					if (response.status === 200) {
						GenericoService.ToDataURL($rootScope.defaultPath + configValue.logoReport1,
							function (dataURL) {
								pdfMake.createPdf(getDocDefinition(response.data, dataURL)).open()
							});
					} else {
						$scope.showSimpleToast("Não foi possível abrir o relatório");
					}
				});
		}

		function getDocDefinition(content, dataURL) {
			
			var docDefinition = {
				pageSize: 'A4',
				pageOrientation: 'portrait',
				pageMargins: [5, 5, 5, 5],
				info: {
					title: 'Relatório dos créditos em conta padrão FEBRABAN'
				}
			};

			docDefinition.content = [];

			var cabecalho = {
				style: 'table',
				table: {
					widths: ['auto', '*', 'auto', 'auto'],
					headerRows: 0,
					
					body: [
						[{text: 'Ref: ' + content.processamento, alignment: 'left'},{text: content.empresaFilial, colSpan: 2, alignment: 'center'}, {}, {text: 'pag:', alignment: 'right'}],
						[{text: 'RELATÓRIO DOS CRÉDITOS EM CONTA PADRÃO FEBRABAN (CEF)', colSpan: 3, alignment: 'center'}, {}, {}, {text: 'Data: ' + $scope.convertDate(content.dataAtual, false), alignment: 'right'}],
						[{text: 'Usuário: ' + content.usuario, alignment: 'left'},{text: 'Convênio: ' + content.codigoConvenio, alignment: 'center'},{text: 'Remessa: ' + content.numeroRemessa, alignment: 'center'},{text: 'Data de Pagamento: ' + $scope.convertDate(content.dataPagamento), alignment: 'right'}],
					]
				},
				layout: 'noBorders'
			};
			
			docDefinition.content.push(cabecalho);

			var lista = {
				style: 'tableTitulo',
				table: {
					widths: ['auto', 'auto', '*', 'auto','auto','auto'],
					headerRows: 1,
					body: [
						[{text: 'SECRETARIA', alignment: 'left'},
						{text: 'MATRÍCULA', alignment: 'left'},
						{text: 'SERVIDOR', alignment: 'left'},
						{text: 'CONTA', alignment: 'left'},
						{text: 'AGÊNCIA', alignment: 'left'},
						{text: 'VALOR LÍQUIDO', alignment: 'left'}],
					]
				},
				layout: {
					hLineWidth: function (i, node) {
						return 1;
					},
					vLineWidth: function (i, node) {
						return 0;
					},
					hLineColor: function (i, node) {
						return (i === 0 || i === 1) ? 'black' : 'white';
					},
					vLineColor: function (i, node) {
						return 'white';
					},
					hLineStyle: function (i, node) { 
						return {dash: { length: 10, space: 4 }};
					},
					paddingTop: function(i, node) { 
						return 1; 
					},
					paddingBottom: function(i, node) { 
						return 1; 
					},
				}
			};

			var i;
			for (i = 0; i < content.lista.length; i++) {
				var element = content.lista[i];
				var body = [
					{ text: content.codigoSecretaria, alignment: 'left' },
					{ text: element.matricula, alignment: 'left' },
					{ text: element.nome, alignment: 'left' },
					{ text: element.conta, alignment: 'left' },
					{ text: element.agencia, alignment: 'left' },
					{ text: (element.valorLiquido).toLocaleString('pt-BR'), alignment: 'right' }
				]
				lista.table.body.push(body)
			}
			docDefinition.content.push(lista);


			var rodape = {
				style: 'tableRodape',
				table: {
					widths: ['auto', 'auto', 'auto'],
					headerRows: 0,
					
					body: [
						[{text: 'TOTAL SERVIDORES: ', alignment: 'left'},{text: content.totalServidores, alignment: 'left'}, {text: (content.totalValorServidores).toLocaleString('pt-BR'), alignment: 'right'}],
						[{text: 'PENSÃO ALIMENTO: ', alignment: 'left'}, {text: content.totalPensaoAlim, alignment: 'left'}, {text: (content.totalValorPensaoAlim).toLocaleString('pt-BR'), alignment: 'right'}],
						[{text: 'TOTAL GERAL: ', alignment: 'left'},{text: content.total, alignment: 'left'}, {text: (content.totalValor).toLocaleString('pt-BR'), alignment: 'right'}],
					]
				},
				layout: 'noBorders'
			};
			
			docDefinition.content.push(rodape);

			docDefinition.styles = {
				table: {
					
				},
				tableRodape: {
					margin: [0, 10, 0, 0]
				}
			};

			docDefinition.defaultStyle = {
				fontSize: 7
			};

			return docDefinition;

		}

	}

})();
