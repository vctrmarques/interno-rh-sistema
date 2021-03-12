(function () {
	'use strict';

	angular.module('app.page')
		.controller('declaracaoExServidorCtrl', ['$location', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$rootScope', 'GenericoService', '$filter', 'EnumService', '$q', declaracaoExServidorCtrl]);

	function declaracaoExServidorCtrl($location, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $rootScope, GenericoService, $filter, EnumService, $q) {

		$scope.botoesGestao = false;

		GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
			function (response) {
				if (response.status === 200) {
					$scope.loadList();
					$scope.botoesGestao = true;
				}
			}, function errorCallback(response) {
				if (response.status === 400) {
					$location.path('page/403');
				}
			});

		$scope.descricaoBusca = "";
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

		$scope.limpaFiltro = function () {
			delete $scope.descricaoBusca;
			delete $scope.selectedStatus;
			$scope.loadList();
		}

		$scope.selectedStatus = [];
		EnumService.Get("StatusDeclaracaoExServidorEnum").then(function (dados) {
			$scope.lista.status = dados;
		});

		$scope.loadFeedback = function (type) {
			$scope.tootipFeedback = {
				title: '',
				description: ''
			}

			if (type === 'status') {
				if ($scope.selectedStatus && $scope.selectedStatus.length > 0) {
					$scope.tootipFeedback.title = ' * Status Selecionados: '
					$scope.selectedStatus.forEach(element => {
						$scope.tootipFeedback.description += $scope.tootipFeedback.description.length === 0 ? element.label : ', ' + element.label;
					});
					$scope.tootipFeedback.description += '.';
				} else {
					$scope.tootipFeedback.title = ' * Busca por Status: ';
					$scope.tootipFeedback.description = 'Selecione os status.';
				}

			}
		}

		$scope.loadList = function (report) {
			$rootScope.$broadcast('preloader:active');

			if (report && (!$scope.list.count || $scope.list.count === 0))
				report = false;

			// Filtro de status
			var statusList = [];
			if ($scope.selectedStatus && $scope.selectedStatus.length > 0) {
				$scope.selectedStatus.forEach(element => {
					statusList.push(element.value);
				});
			}

			var config = {
				params: {
					page: report ? 0 : $scope.query.page - 1,
					size: report ? $scope.list.count : $scope.query.limit,
					order: $scope.query.order,
					descricao: $scope.descricaoBusca,
					statusList: statusList
				}
			};

			$scope.promise = GenericoService.GetAll('/declaracoesExServidor', config).then(
				function (response) {
					if (response.status === 200) {

						if (report)
							GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
								function (dataURL) {
									pdfMake.createPdf(getDocDefinition(response.data.content, dataURL)).open()
								});
						else
							$scope.list.data = response.data.content;
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

		$scope.dialogObservacao = function (item) {
			delete $scope.observacaoOcorrencia;

			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));
			$scope.declaracaoId = item;

			$mdDialog.show({
				require: { container: 'declaracaoExServidorCtrl' },
				scope: $scope,
				preserveScope: true,
				controller: ['$scope', '$q', function ($scope, $q) {

					$scope.imprimir = function () {
						$scope.imprimirDeclaracao($scope.declaracaoId);
						$scope.cancel();
					};

					$scope.cancel = function () {
						$mdDialog.cancel();
					};
				}],
				templateUrl: 'app/page/declaracaoExServidor/dialogObservacaoRelatorio.html',
				parent: angular.element(document.body),
				clickOutsideToClose: true,
				fullscreen: useFullScreen
			});

		}

		$scope.deleteItem = function () {
			$rootScope.$broadcast('preloader:active');
			GenericoService.Delete('/declaracaoExServidor/' + $scope.idToDelete, function (response) {
				$rootScope.$broadcast('preloader:hide');
				if (response.status === 200) {
					$scope.showSimpleToast(response.data.message);
					$scope.loadList();
				}
			});
		}

		$scope.showRelatorio = function () {
			$scope.loadList(true);
		}

		$scope.imprimirDeclaracao = function (id) {
			$rootScope.$broadcast('preloader:active');

			GenericoService.Update('/declaracaoExServidor/arquivar/' + id, null, function (response) {
				$rootScope.$broadcast('preloader:hide');
				if (response.status === 201 && response.data.success) {
					$scope.showSimpleToast(response.data.message);
					$scope.loadList();
					GenericoService.GetById('/declaracaoExServidor/' + id, function (response) {
						$rootScope.$broadcast('preloader:hide');
						if (response.status === 200) {
							GenericoService.ToDataURL($rootScope.defaultPath + configValue.logoReport1,
								function (dataURL) {
									pdfMake.createPdf(getDeclaracaoDefinition(response.data, dataURL)).open()
								});
						} else {
							$scope.showSimpleToast("Declaração não encontrado na base");
						}
					});
				} else if (response.status === 400) {
					$scope.showSimpleToast(response.data.message);
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
		function convertDate(data, comHora) {
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

		function calularPeriodoReport(dataInicio, dataFim) {
			return $scope.calularPeriodo(dataInicio, dataFim, true)
		}

        /*
         * Recebe um char e verifica se é Feminino ou Masculino
         * 
         * param valor - char de genero
         * 
         * */
		function retornaGenero(valor) {
			if (valor == 'F') {
				return 'Feminino';
			} else {
				return 'Masculino';
			}
		}

		function getDocDefinition(content, dataURL) {

			var docDefinition = {};
			docDefinition.content = [];

			var brasao = { image: dataURL, width: 70, alignment: 'center' };
			docDefinition.content.push(brasao);

			var orgao = { text: 'RH', alignment: 'center', margin: [0, 10], bold: true };
			docDefinition.content.push(orgao);

			var titulo = { text: 'Declarações de Tempo Contribuição - Ex-Servidor', alignment: 'center', margin: [0, 10] };
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
					widths: ['auto', '*', 'auto', 'auto', 'auto'],
					body: [
						[{ text: 'Matrícula', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Funcionário', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Filial', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Data cadastro', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Status', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }]
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
				var body = [
					{ text: element.funcionario.matricula, fontSize: 10, alignment: 'center' },
					{ text: element.funcionario.nome, fontSize: 10, alignment: 'left' },
					{ text: element.funcionario.empresa.nomeFilial, fontSize: 10, alignment: 'center' },
					{ text: element.criadoEm, fontSize: 10, alignment: 'center' },
					{ text: element.status, fontSize: 10, alignment: 'center' }
				]
				lista.table.body.push(body)
			}
			docDefinition.content.push(lista);

			return docDefinition;

		}

		function getSituacaoFuncional(situacaoFuncional) {
			if (situacaoFuncional) {
				return situacaoFuncional.descricao;
			} else {
				return '';
			}
		}

		function getFiliacao(funcionario) {
			if (funcionario.nomeMae) {
				return funcionario.nomeMae;
			} else if (funcionario.nomePai) {
				return funcionario.nomePai;
			} else {
				return 'Não Declarado';
			}
		}

		function getLotacao(lotacao) {
			if (lotacao) {
				return lotacao.descricao.toUpperCase();
			} else {
				return '';
			}
		}

		function getEndereco(funcionario) {
			if (funcionario) {
				return getValor(funcionario.logradouro) + ', ' + getValor(funcionario.numero) + ' - ' + getValor(funcionario.bairro) + ' - ' + getMunicipio(funcionario);
			} else {
				return '';
			}
		}

		function getValor(valor) {
			if (valor) {
				return valor.toUpperCase();
			} else {
				return "";
			}
		}

		function getValorNumero(valor) {
			if (valor) {
				return valor;
			} else {
				return "";
			}
		}

		function getMunicipio(funcionario) {
			if (funcionario.municipio) {
				return funcionario.municipio.descricao + '/' + funcionario.municipio.uf.sigla;
			} else {
				return '';
			}
		}

        /*
         * Montagem Declaração para impressão
         * */
		function getDeclaracaoDefinition(content, dataURL) {

			var docDefinition = {
				pageSize: 'A4',
				pageOrientation: 'portrait',
				pageMargins: [40, 40, 40, 40],
				info: {
					title: 'Declaração de tempo de contribuição'
				}
			};

			docDefinition.content = [];

			var titulo = {
				style: 'tableTop',
				table: {
					widths: ['*'],
					body: [
						[
							{
								image: dataURL, width: 140, alignment: 'center',
								style: 'logo'
							}
						]
					]
				},
				layout: 'noBorders'
			};

			docDefinition.content.push(titulo);

			docDefinition.content.push({ text: 'ANEXO III', style: 'subtituloSemMargem', alignment: 'center' });
			docDefinition.content.push({ text: 'DECLARAÇÃO DE TEMPO DE CONTRIBUIÇÃO', style: 'subtituloSemMargem', alignment: 'center' });
			docDefinition.content.push({ text: 'PARA FINS DE OBTENÇÃO DE BENEFÍCIO JUNTO AO INSS', style: 'subtituloSemMargem', alignment: 'center' });

			if (content.rascunho)
				docDefinition.content.push({ text: 'RASCUNHO', style: 'rascunho' });

			var dadosOrgao = {
				style: 'tableTop',
				table: {
					widths: ['*', 'auto', 'auto'],
					headerRows: 1,
					body: [
						[{ text: [{ text: 'ÓRGÃO EMITENTE: ', bold: true }, 'Instituto de Previdência dos Servidores Municipais de Goiânia - GOIANIAPREV'], colSpan: 3, alignment: 'left' }, {}, {}],
						[{ text: [{ text: 'CNPJ / CGC: ', bold: true }, '08.948.407/0001-57'], colSpan: 3, alignment: 'left' }, {}, {}]
					],
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
			};

			docDefinition.content.push(dadosOrgao);

			docDefinition.content.push({ text: 'DADOS PESSOAIS', style: 'subtitulo' });

			var dadosPessoais = {
				style: 'tableTop',
				table: {
					widths: ['*', 'auto', 'auto'],
					headerRows: 1,
					body: [
						[{ text: [{ text: 'NOME: ', bold: true }, getValor(content.funcionario.nome)], colSpan: 3, alignment: 'left' }, {}, {}],
						[{ text: [{ text: 'IDENTIDADE: ', bold: true }, getValor(content.funcionario.identidade)], colSpan: 1, alignment: 'left' }, { text: [{ text: 'ÓRGÃO EXPEDITOR: ', bold: true }, getValor(content.funcionario.orgaoExpeditor)], colSpan: 1, alignment: 'left' }, { text: [{ text: 'DATA EXPEDIÇÃO: ', bold: true }, convertDate(content.funcionario.dataExpedicaoRg, false)], colSpan: 1, alignment: 'left' }],
						[{ text: [{ text: 'CPF: ', bold: true }, $filter('cpf')(content.funcionario.cpf)], colSpan: 1, alignment: 'left' }, { text: [{ text: 'TÍTULO ELEITOR: ', bold: true }, getValorNumero(content.funcionario.tituloEleitor)], colSpan: 1, alignment: 'left' }, { text: [{ text: 'PIS/PASEP: ', bold: true }, getValorNumero(content.funcionario.pisPasep)], colSpan: 1, alignment: 'left' }],
						[{ text: [{ text: 'DATA NASCIMENTO: ', bold: true }, convertDate(content.funcionario.dataNascimento, false)], colSpan: 1, alignment: 'left' }, { text: [{ text: 'FILIAÇÃO: ', bold: true }, getFiliacao(content.funcionario).toUpperCase()], colSpan: 2, alignment: 'left' }],
						[{ text: [{ text: 'ENDEREÇO: ', bold: true }, getEndereco(content.funcionario).toUpperCase()], colSpan: 3, alignment: 'left' }, {}, {}],
					],
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
			};

			docDefinition.content.push(dadosPessoais);

			if (content.dadosFuncionais && content.dadosFuncionais.length > 0)
				docDefinition.content.push({ text: 'DADOS FUNCIONAIS', style: 'subtitulo' });

			angular.forEach(content.dadosFuncionais, function (e) {

				var dadosFuncionais = {
					table: {
						widths: ['*', 'auto'],
						headerRows: 0,
						body: [
							[{ text: [{ text: 'CARGO EM COMISSÃO EXERCIDO: ', bold: true }, e.cargo.nome.toUpperCase()], colSpan: 2, alignment: 'left' }, {}],
							[{ text: [{ text: 'Nº ATO NOMEAÇÃO: ', bold: true }, e.atoNomeacao], colSpan: 1, alignment: 'left' }, { text: [{ text: 'DATA PUBLICAÇÃO DIÁRIO OFICIAL:\n', bold: true }, convertDate(e.dataDiarioOficialEntrada, false)], rowSpan: 2, alignment: 'center' }],
							[{ text: [{ text: 'DATA ENTRADA EM EXERCÍCIO: ', bold: true }, convertDate(e.dataEntrada, false)], colSpan: 1, alignment: 'left' }, {}],
							[{ text: [{ text: 'DATA ENCERRAMENTO: ', bold: true }, convertDate(e.dataEncerramento, false)], colSpan: 1, alignment: 'left' }, { text: [{ text: 'DATA EXONERAÇÃO DIÁRIO OFICIAL:\n', bold: true }, convertDate(e.dataDiarioOficialEncerramento, false)], rowSpan: 2, alignment: 'center' }],
							[{ text: [{ text: 'Nº ATO DE EXONERAÇÃO / DISPENSA OU DEMISSÃO: ', bold: true }, e.atoEncerramento], colSpan: 1, alignment: 'left' }, {}]
						],
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
				};

				docDefinition.content.push(dadosFuncionais);
				docDefinition.content.push({ text: '', style: 'subtitulo' });
			});

			var primeiraLinha = {
				style: 'coluna',
				columns: [],
			}

			if (content.responsavel) {
				primeiraLinha.columns.push({ text: ['RESPONSÁVEL PELAS INFORMAÇÕES\n\n', 'NOME: ' + getValor(content.responsavel.nome) + '\n', 'MATRÍCULA: ' + getValorNumero(content.responsavel.matricula) + '\n\n\n', '______________________________________________\n', 'ASSINATURA'] });
			}

			if (content.dirigente) {
				primeiraLinha.columns.push({ text: ['VISTO PELO DIRIGENTE DO ÓRGÃO DE PESSOAL\n\n', 'NOME: ' + getValor(content.dirigente.nome) + '\n', 'MATRÍCULA: ' + getValorNumero(content.dirigente.matricula) + '\n', 'CARGO: ' + getValor(content.dirigente.cargo.nome) + '\n\n', '______________________________________________\n', 'ASSINATURA'] });
			}

			docDefinition.content.push(primeiraLinha);

			var data = new Date();
			var dia = $filter('date')(data, 'dd');
			var mes = $filter('date')(data, 'MMMM');
			var ano = $filter('date')(data, 'yyyy');

			docDefinition.content.push({ text: ['Goiânia, ', dia, ' de ', mes, ' de ', ano], style: 'dataLocal' });

			var obs = {
				table: {
					widths: ['*'],
					headerRows: 0,
					body: [
						[{ text: [{ text: 'OBSERVAÇÃO / OCORRÊNCIA:\n\n ', bold: true }, getValor($scope.observacaoOcorrencia)], colSpan: 1, alignment: 'left' }],
					],
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
			};

			docDefinition.content.push(obs);

			docDefinition.content.push({ text: 'ESTA DECLARAÇÃO NÃO CONTÉM EMENDAS NEM RASURAS.', style: 'subtitulo' });

			docDefinition.styles = {
				header: {
					fontSize: 18,
					bold: true,
					margin: [0, 0, 0, 10]
				},
				subheader: {
					fontSize: 28,
					bold: false,
					margin: [0, 10, 0, 5]
				},
				subheaderName: {
					fontSize: 28,
					bold: true,
					italics: true,
					margin: [0, 10, 0, 5]
				},
				tableTop: {
					margin: [0, 0, 0, 0],
					fontSize: 11
				},
				tableBottom: {
					margin: [0, 60, 0, 0],
					alignment: 'center'
				},
				tableHeader: {
					bold: true,
					fontSize: 11,
					color: 'black'
				},
				assinatura: {
					bold: true,
					fontSize: 7,
					color: 'black',
					italics: true
				},
				responsavelLabel: {
					bold: false,
					italics: true,
					fontSize: 11,
					color: 'black'
				},
				tituloSupDirLogo: {
					bold: true,
					fontSize: 10,
					decoration: 'underline',
					color: 'grey',
					margin: [0, 0, 0, 0],
					alignment: 'right'
				},
				titulo: {
					bold: true,
					fontSize: 14,
					margin: [100, 20, 0, 0]
				},
				subtitulo: {
					bold: true,
					fontSize: 11,
					alignment: 'left',
					margin: [0, 10, 0, 10]
				},
				dataLocal: {
					bold: false,
					fontSize: 11,
					alignment: 'left',
					margin: [0, 20, 0, 5]
				},
				subtituloSemMargem: {
					bold: true,
					fontSize: 11,
					alignment: 'center',
					margin: [0, 0, 0, 0]
				},
				footer: {
					fontSize: 8,
					alignment: 'left',
					height: 200,
					margin: [40, 0, 0, 10]
				},
				coluna: {
					fontSize: 11,
					alignment: 'center',
					margin: [0, 20, 0, 0]
				},
				rascunho: {
					bold: true,
					fontSize: 10,
					decoration: 'underline',
					color: 'red',
					margin: [0, 10, 0, 10],
					alignment: 'center'
				}
			};

			docDefinition.defaultStyle = {
				alignment: 'justify'
			};
			return docDefinition;
		}

	}

})();
