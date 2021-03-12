(function () {
	'use strict';

	angular.module('app.page')
		.controller('recadastramentoCtrl', ['$location', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$rootScope', 'GenericoService', 'EnumService', 'ParametroService', '$filter', recadastramentoCtrl]);

	function recadastramentoCtrl($location, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $rootScope, GenericoService, EnumService, ParametroService, $filter) {

		$scope.botoesGestao = false;

		$scope.descricaoBusca = "";
		$scope.limitOptions = [5, 10, 15];
		$scope.query = {
			order: '-createdAt',
			limit: 10,
			page: 1
		};

		$scope.queryHL = {
			order: '-createdAt',
			limit: 5,
			page: 1
		};

		$scope.list = {
			"count": 0,
			"data": []
		};
		$scope.lista = {};
		$scope.lista.ligacoes = {
			"count": 0,
			"data": []
		};
		$scope.isLoad = false;

		$scope.isReport = false;

		$scope.filtro = {};

		$scope.selectedFundos = [];
		EnumService.Get("FundoEnum").then(function (dados) {
			$scope.lista.fundo = $filter('orderBy')(dados, 'label');
			$scope.selectedFundos = $scope.lista.fundo;
		});

		EnumService.Get("RecadastramentoTipoEnum").then(function (dados) {
			$scope.lista.tipo = $filter('orderBy')(dados, 'label');
			getPensionista();
		});

		function getPensionista() {
			angular.forEach($scope.lista.tipo, function (e) {
				if (e.value === 'PENSIONISTA') {
					$scope.selectedTipo = e;
				}
			});
		}

		$scope.selectedFiltros = [];
		EnumService.Get("RecadastramentoFiltroEnum").then(function (dados) {
			$scope.lista.filtro = $filter('orderBy')(dados, 'label');
			$scope.selectedFiltros = $scope.lista.filtro;
		});

		$scope.limpaFiltro = function () {
			delete $scope.descricaoBusca;
			delete $scope.selectedTipo;
			$scope.filtro = {};
			ParametroService.setParametroFiltro(null);
			$scope.selectedFundos = $scope.lista.fundo;
			getPensionista();
			$scope.selectedFiltros = $scope.lista.filtro;
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

			// var fundoList = [];
			// if ($scope.selectedFundos && $scope.selectedFundos.length > 0) {
			// 	$scope.selectedFundos.forEach(element => {
			// 		fundoList.push(element.value);
			// 	});
			// }

			var tipo = null;
			if ($scope.selectedTipo) {
				tipo = $scope.selectedTipo.value;
			}

			var config = {}

			if (ParametroService.getParametroVoltar()) {
				config = ParametroService.getParametroFiltro();
				ParametroService.setParametroVoltar(false);
			} else {
				config = {
					params: {
						page: $scope.isReport ? 0 : $scope.query.page - 1,
						size: $scope.isReport ? $scope.list.count : $scope.query.limit,
						order: $scope.query.order,
						descricao: $scope.descricaoBusca,
						// fundo: fundoList,
						tipo: tipo
					}
				};
				ParametroService.setParametroFiltro(config);
			}

			$rootScope.$broadcast('preloader:active');

			$scope.promise = GenericoService.GetAll('/recadastramentos', config).then(
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
			GenericoService.Delete('/recadastramento/' + $scope.idToDelete, function (response) {
				$rootScope.$broadcast('preloader:hide');
				if (response.status === 410) {
					$scope.showSimpleToast(response.data.message);
					$scope.loadList();
				}
			});
		}



		function deleteHistoricoLigacaoItem() {
			$rootScope.$broadcast('preloader:active');
			GenericoService.Delete('/historicoLigacao/' + $scope.idHistoricoLigacaoToDelete, function (response) {
				$rootScope.$broadcast('preloader:hide');

				if (response.data.success) {
					$scope.showSimpleToast(response.data.message);
					$scope.historicoLigacoes();
					delete $scope.idHistoricoLigacaoToDelete;
					$scope.mostraDivAlerta = false;
				}
			});
		}

		$scope.showConfirmRecadastrar = function (ev, recadastramentoId, pensaoId, funcionarioId) {
			$scope.recadastro = {
				recadastramentoId: recadastramentoId,
				funcionarioId: funcionarioId,
				pensaoId: pensaoId
			}
			// Appending dialog to document.body to cover sidenav in docs app
			var confirm = $mdDialog.confirm()
				.title('Deseja realizar o recadastramento?')
				.textContent('Esta ação não poderá ser desfeita.')
				// .ariaLabel('Lucky day')
				.targetEvent(ev)
				.ok('Sim')
				.cancel('Não');

			$mdDialog.show(confirm).then(function () {
				$scope.recadastrar();
			}, function () {
			});
		};

		$scope.recadastrar = function () {
			$rootScope.$broadcast('preloader:active');
			GenericoService.Create('/recadastramento/novo', $scope.recadastro, function (response) {
				$rootScope.$broadcast('preloader:hide');
				if (response.status === 201 && response.data.success) {
					$scope.showSimpleToast(response.data.message);
					$location.path('/recadastramento/formulario/' + response.data.obj);
				}
			});
		}

		$scope.loadHistoricoLigacoes = function (item) {
			$scope.selectedItem = item;
			$scope.historicoLigacao = {};
			$scope.historicoLigacao.funcionarioId = item.funcionarioId;
			$scope.isLoad = true;
			$scope.mostraDivAlerta = false;

			$scope.queryHL = {
				order: '-createdAt',
				limit: 5,
				page: 1
			};

			$scope.historicoLigacoes();
		}

		$scope.historicoLigacoes = function () {

			$rootScope.$broadcast('preloader:active');

			var config = {
				params: {
					page: $scope.queryHL.page - 1,
					size: $scope.queryHL.limit,
					order: $scope.queryHL.order,
					funcionarioId: $scope.selectedItem.funcionarioId
				}
			};

			$scope.ligacoes = GenericoService.GetAll('/recadastramento/historicoLigacoes', config).then(
				function (response) {
					if (response.status === 200) {
						$scope.lista.ligacoes.data = response.data.content;
						$scope.lista.ligacoes.count = response.data.totalElements;

						$rootScope.$broadcast('preloader:hide');
						if ($scope.isLoad) {
							showHistoricoLigacoesDialog();
						}
					} else {
						$scope.showSimpleToast("Não foi possível carregar os dados.")
					}
				});

		};

		function showHistoricoLigacoesDialog() {
			$scope.isLoad = false;

			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

			$mdDialog.show({
				require: 'recadastramentoCtrl',
				scope: $scope,
				preserveScope: true,
				controller: function () {

					$scope.adicionarObservacao = function () {
						salvarHistoricoLigacao();
					};

					$scope.editarHistoricoLigacao = function (item) {
						editarHistoricoLigacao(item);
					}

					$scope.showConfirmHistoricoLigacao = function (idToDelete) {
						$scope.idHistoricoLigacaoToDelete = idToDelete;

						$scope.mostraDivAlerta = true;
					};

					$scope.confirmarExclusaoHistoricoLigacao = function () {
						deleteHistoricoLigacaoItem();
					}

					$scope.cancelarExclusaoHistoricoLigacao = function () {
						delete $scope.idHistoricoLigacaoToDelete;
						$scope.mostraDivAlerta = false;
					}

					$scope.cancel = function () {
						$scope.historicoLigacao = {};
						$mdDialog.cancel();
					};
				},
				templateUrl: 'app/page/recadastramento/dialogHistoricoLigacao.html',
				parent: angular.element(document.body),
				clickOutsideToClose: false,
				fullscreen: useFullScreen
			});
		}

		function editarHistoricoLigacao(item) {
			$scope.historicoLigacao = {};
			$scope.historicoLigacao.id = item.id;
			$scope.historicoLigacao.funcionarioId = item.funcionario.id;
			$scope.historicoLigacao.observacao = item.observacao;
		}

		function salvarHistoricoLigacao() {
			if ($scope.historicoLigacao && $scope.historicoLigacao.observacao) {
				$rootScope.$broadcast('preloader:active');
				GenericoService.Create('/historicoLigacao', $scope.historicoLigacao, function (response) {
					$rootScope.$broadcast('preloader:hide');
					if (response.status === 201 && response.data.success) {
						$scope.showSimpleToast(response.data.message);
						$scope.historicoLigacoes();
						$scope.historicoLigacao.observacao = "";
					} else if (response.status === 400) {
						$scope.showSimpleToast(response.data.message);
					}
				});
			} else {
				$scope.showSimpleToast("Preencha o campo observação");
			}
		}

		/*
		 * Recebe data e converte para valor com hora ou não
		 * 
		 * param data - recebe uma data tipo LocalDate
		 * param comHora - recebe um valor boolean
		 * 
		 * */
		$scope.convertDate = function (data, comHora) {
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

		$scope.showRelatorio = function () {
			if (!$scope.isReport)
				$scope.isReport = true;

			$scope.loadList();
		}

		function getDocDefinition(content, dataURL) {

			var docDefinition = {};
			docDefinition.content = [];

			var brasao = { image: dataURL, width: 70, alignment: 'center' };
			docDefinition.content.push(brasao);

			var orgao = { text: 'RH', alignment: 'center', margin: [0, 10], bold: true };
			docDefinition.content.push(orgao);

			var titulo = { text: 'Relatório de Aposentadoso e Pensionistas', alignment: 'center', margin: [0, 10] };
			docDefinition.content.push(titulo);

			var listaTitulo = {
				style: 'tableExample',
				table: {
					widths: ["*"],
					body: [
						[
							{ text: 'Listagem', bold: true, fillColor: 'lightblue' }
						]
					]
				},
				margin: [0, 15, 0, 10],
				layout: 'noBorders'
			}
			docDefinition.content.push(listaTitulo)

			var lista = {
				style: 'tableExample',
				table: {
					headerRows: 1,
					widths: ['auto', '*', 'auto'],
					body: [
						[{ text: 'Matrícula', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Aposentados/Pensionistas', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Telefones', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }]
					]
				},
				layout: {
					hLineWidth: function (i, node) {
						return 1;
					},
					vLineWidth: function (i, node) {
						return 1;
					},
					hLineColor: function (i, node) {
						return 'gray';
					},
					vLineColor: function (i, node) {
						return 'gray';
					}
				}

			}

			var i;
			for (i = 0; i < content.length; i++) {
				var element = content[i];
				var telefones = '';

				angular.forEach(element.telefones, function (e) {
					telefones += e + ", ";
				});

				var body = [
					{ text: element.matricula, fontSize: 10, alignment: 'center' },
					{ text: element.nome, fontSize: 10, alignment: 'center' },
					{ text: telefones, fontSize: 10, alignment: 'left' }
				]
				lista.table.body.push(body)
			}
			docDefinition.content.push(lista);

			return docDefinition;

		}

	}

})();
