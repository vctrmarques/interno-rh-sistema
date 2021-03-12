(function () {
	'use strict';

	angular.module('app.page')
		.controller('declaracaoAposentadoriaCtrl', ['$location', 'configValue', '$scope', 'RestService', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', '$filter', declaracaoAposentadoriaCtrl]);

	function declaracaoAposentadoriaCtrl($location, configValue, $scope, RestService, $mdToast, $mdDialog, $rootScope, GenericoService, $filter) {

		// Checa as regras de acesso da tela
		RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Declaração para Aposentados' } }).then(
			function (response) {
				if (response.status === 200 && response.data) {

					$scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeCadastrar = response.data.usuarioAdm ? true : response.data.podeCadastrar;
                    $scope.podeAtualizar = response.data.usuarioAdm ? true : response.data.podeAtualizar;
                    $scope.podeVisualizar = response.data.usuarioAdm ? true : response.data.podeVisualizar;
                    $scope.podeExcluir = response.data.usuarioAdm ? true : response.data.podeExcluir;
					
					if ($scope.usuarioAdm || $scope.podeCadastrar || $scope.podeAtualizar || $scope.podeVisualizar || $scope.podeExcluir)
						$scope.loadList();
					else
						$location.path('page/403');
				}
			}, function errorCallback(response) {
				if (response.status === 400) {
					$scope.showSimpleToast(response.data.message);
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

		$scope.limpaFiltro = function () {
			delete $scope.descricaoBusca;
			delete $scope.descricaoBuscaNumero;
			delete $scope.descricaoBuscaAno;
			$scope.loadList();
		}

		$scope.loadList = function (report) {
			$rootScope.$broadcast('preloader:active');

			if (report && (!$scope.list.count || $scope.list.count === 0))
				report = false;

			var config = {
				params: {
					page: report ? 0 : $scope.query.page - 1,
					size: report ? $scope.list.count : $scope.query.limit,
					order: $scope.query.order,
					descricao: $scope.descricaoBusca,
					descricaoNumero: $scope.descricaoBuscaNumero,
					descricaoAno: $scope.descricaoBuscaAno
				}
			};

			$scope.promise = GenericoService.GetAll('/declaracoesAposentadoria', config).then(
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

		$scope.deleteItem = function () {
			$rootScope.$broadcast('preloader:active');
			GenericoService.Delete('/declaracaoAposentadoria/' + $scope.idToDelete, function (response) {
				$rootScope.$broadcast('preloader:hide');
				if (response.status === 200) {
					$scope.showSimpleToast(response.data.message);
					$scope.loadList();
				}
			});
		}

		$scope.showConfirmRetificar = function (ev, idToRetificar) {
			$scope.idToRetificar = idToRetificar;
			// Appending dialog to document.body to cover sidenav in docs app
			var confirm = $mdDialog.confirm()
				.title('Deseja confirmar a retificação deste item?')
				.textContent('Esta ação não poderá ser desfeita.')
				// .ariaLabel('Lucky day')
				.targetEvent(ev)
				.ok('Sim')
				.cancel('Não');

			$mdDialog.show(confirm).then(function () {
				$scope.retificarItem();
			}, function () {
			});
		};

		$scope.retificarItem = function () {
			$rootScope.$broadcast('preloader:active');
			GenericoService.Create('/declaracaoAposentadoria/retificar', { id: $scope.idToRetificar }, function (response) {
				$rootScope.$broadcast('preloader:hide');
				if (response.status === 201 && response.data.success) {
					$scope.showSimpleToast(response.data.message);
					$location.path('/declaracaoParaAposentados/formulario/' + response.data.obj.id);
				}
			});
		}

		$scope.showRelatorio = function () {
			$scope.loadList(true);
		}

		$scope.imprimirDeclaracao = function (id) {
			$rootScope.$broadcast('preloader:active');
			GenericoService.GetById('/declaracaoAposentadoria/' + id, function (response) {
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

			var titulo = { text: 'Declarações de Aposentadoria', alignment: 'center', margin: [0, 10] };
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
					widths: ['auto', 'auto', '*', 'auto', 'auto', 'auto'],
					body: [
						[{ text: 'Número', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Ano', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Funcionário', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Matrícula', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'CPF', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'PisPasep', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }]
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
					{ text: element.numero, fontSize: 10, alignment: 'center' },
					{ text: element.ano, fontSize: 10, alignment: 'center' },
					{ text: element.funcionario.nome, fontSize: 10, alignment: 'left' },
					{ text: element.funcionario.matricula, fontSize: 10, alignment: 'center' },
					{ text: element.funcionario.cpf, fontSize: 10, alignment: 'center' },
					{ text: element.funcionario.pisPasep, fontSize: 10, alignment: 'center' }
				]
				lista.table.body.push(body)
			}
			docDefinition.content.push(lista);

			return docDefinition;

		}

		function getSituacaoFuncional(situacaoFuncional) {
			if (situacaoFuncional) {
				return situacaoFuncional.descricao;
				// GenericoService.GetById('/situacaoFuncional/' + id, function (response) {
				// 	if (response.status === 200) {
				// 		situacaoFuncional = response.data; 
				// 		return situacaoFuncional.descricao;
				// 	} 
				// });
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

        /*
         * Montagem Declaração para impressão
         * */
		function getDeclaracaoDefinition(content, dataURL) {

			var docDefinition = {
				pageSize: 'A4',
				pageOrientation: 'portrait',
				pageMargins: [40, 40, 40, 40],
				footer: { text: 'Av. B, nº 155 - Setor Oeste - Goiânia-GO\nCep: 74110-030, Tel: 55 62 3524-5831\nipsm@goiania.go.gov.br', style: 'footer' },
				info: {
					title: 'Declaração para Aposentados'
				}
			};

			docDefinition.content = [];

			var titulo = {
				style: 'tableTop',
				table: {
					widths: ['*', 'auto'],
					body: [
						[
							{
								image: dataURL, width: 140, alignment: 'left',
								style: 'logo'
							},
							{
								text: 'Instituto de Previdência dos Servidores do Município de Goiânia',
								style: 'tituloSupDirLogo'
							}
						]
					]
				},
				layout: 'noBorders'
			};

			docDefinition.content.push(titulo);

			docDefinition.content.push({ text: 'INSTITUTO DE PREVIDÊNCIA DOS SERVIDORES DE GOIÂNIA', style: 'subtituloSemMargem', alignment: 'center', decoration: 'underline' });
			docDefinition.content.push({ text: 'DIRETORIA DE BENEFÍCIOS PREVIDENCIÁRIOS', style: 'subtituloSemMargem', alignment: 'center' });
			docDefinition.content.push({ text: 'GERÊNCIA DE COMPENSAÇÃO PREVIDENCIÁRIA', style: 'subtituloSemMargem', alignment: 'center' });
			if (content.tipoDeclaracao === 'RETIFICACAO')
				docDefinition.content.push({ text: 'DECLARAÇÃO Nº ' + content.numero + '/' + content.ano + ' - RETIFICAÇÃO Nº ' + content.numeroRetificacao, style: 'subtitulo', alignment: 'center' });
			else
				docDefinition.content.push({ text: 'DECLARAÇÃO Nº ' + content.numero + '/' + content.ano, style: 'subtitulo', alignment: 'center' });
			if (content.tipoDeclaracao === 'RASCUNHO')
				docDefinition.content.push({ text: 'RASCUNHO', style: 'subtitulo', alignment: 'center' });

			var dadosFuncionais = {
				style: 'tableTop',
				table: {
					widths: ['*', 'auto', 'auto'],
					headerRows: 1,
					// keepWithHeaderRows: 1,
					body: [
						[{ text: [{ text: 'DADOS FUNCIONAIS', bold: true }], colSpan: 3, fillColor: 'lightgray', alignment: 'center' }, {}, {}]
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
			};
			if (content.funcionario) {
				dadosFuncionais.table.body.push([{ text: [{ text: 'ÓRGÃO EXPEDIDOR: ', bold: true }, content.funcionario.empresa.nomeFilial], colSpan: 3, alignment: 'left' }, {}, {}])
				dadosFuncionais.table.body.push([{ text: [{ text: 'CNPJ: ', bold: true }, $filter('cnpj')(content.funcionario.empresa.cnpj)], colSpan: 3, alignment: 'left' }, {}, {}])
				dadosFuncionais.table.body.push([{ text: [{ text: 'NOME DO SERVIDOR: ', bold: true }, content.funcionario.nome], colSpan: 2, alignment: 'left' }, {}, { text: [{ text: 'GÊNERO: ', bold: true }, retornaGenero(content.funcionario.sexo)], alignment: 'left' }])
				dadosFuncionais.table.body.push([{ text: [{ text: 'IDENTIDADE: ', bold: true }, content.funcionario.identidade], colSpan: 1, alignment: 'left' }, { text: [{ text: 'CPF: ', bold: true }, $filter('cpf')(content.funcionario.cpf)], colSpan: 1, alignment: 'left' }, { text: [{ text: 'PIS/PASEP: ', bold: true }, content.funcionario.pisPasep], colSpan: 1, alignment: 'left' }])
				dadosFuncionais.table.body.push([{ text: [{ text: 'SITUAÇÃO FUNCIONAL: ', bold: true }, getSituacaoFuncional(content.funcionario.situacaoFuncional)], colSpan: 3, alignment: 'left' }, {}, {}])
				dadosFuncionais.table.body.push([{ text: [{ text: 'MATRÍCULA: ', bold: true }, content.funcionario.matricula], colSpan: 1, alignment: 'left' }, { text: [{ text: 'DATA DE NASCIMENTO: ', bold: true }, convertDate(content.funcionario.dataNascimento, false)], colSpan: 2, alignment: 'left' }, {}])
				dadosFuncionais.table.body.push([{ text: [{ text: 'FILIAÇÃO: ', bold: true }, getFiliacao(content.funcionario)], colSpan: 3, alignment: 'left' }, {}, {}])
			} else {
				dadosFuncionais.table.body.push([{ text: 'Observação: Dados funcionais indisponíveis. ', fontSize: 10, alignment: 'left', bold: false, colSpan: 3 }, {}, {}]);
			}

			docDefinition.content.push(dadosFuncionais);

			docDefinition.content.push({ text: '', style: 'subtitulo' });

			var averbados = {
				table: {
					widths: ['*', 'auto', 'auto'],
					headerRows: 1,
					// keepWithHeaderRows: 1,
					margin: [20, 20],
					body: [
						[
							{ text: 'Empregador', fontSize: 10, style: 'tableHeader', alignment: 'center' },
							{ text: 'Período', fontSize: 10, style: 'tableHeader', alignment: 'center' },
							{ text: 'Tempo Líquido', fontSize: 10, style: 'tableHeader', alignment: 'center' }
						]
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
			};

			averbados.table.body.unshift([{ text: [{ text: 'PERÍODO AVERBADO PARA FINS DE APOSENTADORIA NESTE INSTITUTO', bold: true }], colSpan: 3, fillColor: 'lightgray', alignment: 'center' }, {}, {}]);

			var existeNaoAverbado = false
			angular.forEach(content.averbacoes, function (e) {
				if (e.averbado) {
					averbados.table.body.push([{ text: e.empregador, fontSize: 10, alignment: 'left' },
					{ text: convertDate(e.periodoInicio, false) + ' a ' + convertDate(e.periodoFim, false), fontSize: 10, alignment: 'center' },
					{ text: calularPeriodoReport(e.periodoInicio, e.periodoFim), fontSize: 10, alignment: 'center' }]);
					if (e.fonteInf)
						averbados.table.body.push([{ text: 'Fonte de Informação: ' + e.fonteInf, fontSize: 10, alignment: 'left', bold: false, colSpan: 3 }, {}, {}]);
				} else {
					existeNaoAverbado = true;
				}
			});

			docDefinition.content.push(averbados);

			docDefinition.content.push({ text: '', style: 'subtitulo' });


			var naoAverbados = {
				style: 'tableTop',
				table: {
					widths: ['*', 'auto', 'auto'],
					headerRows: 1,
					// keepWithHeaderRows: 1,
					margin: [0, 15, 0, 10],
					body: [
						[
							{ text: 'Empregador', fontSize: 10, style: 'tableHeader', alignment: 'center' },
							{ text: 'Período', fontSize: 10, style: 'tableHeader', alignment: 'center' },
							{ text: 'Tempo Líquido', fontSize: 10, style: 'tableHeader', alignment: 'center' }]
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
			};

			if (existeNaoAverbado) {
				angular.forEach(content.averbacoes, function (e) {
					if (!e.averbado) {
						naoAverbados.table.body.push([{ text: e.empregador, fontSize: 10, alignment: 'left' },
						{ text: convertDate(e.periodoInicio, false) + ' a ' + convertDate(e.periodoFim, false), fontSize: 10, alignment: 'center' },
						{ text: calularPeriodoReport(e.periodoInicio, e.periodoFim), fontSize: 10, alignment: 'center' }]);
						if (e.observacao)
							naoAverbados.table.body.push([{ text: 'Observação: ' + e.observacao, fontSize: 10, alignment: 'left', bold: false, colSpan: 3 }, {}, {}]);

					}
				});
			} else {
				naoAverbados.table.body = [];
				naoAverbados.table.body.push([{ text: 'Observação: Não consta período para ser um utilizado em outro regime. ', fontSize: 10, alignment: 'left', bold: false, colSpan: 3 }, {}, {}]);
			}

			naoAverbados.table.body.unshift([{ text: [{ text: 'PERÍODO NÃO AVERBADO', bold: true }], colSpan: 3, fillColor: 'lightgray', alignment: 'center' }, {}, {}]);

			docDefinition.content.push(naoAverbados);

			var data = new Date();
			var dia = $filter('date')(data, 'dd');
			var mes = $filter('date')(data, 'MMMM');
			var ano = $filter('date')(data, 'yyyy');

			docDefinition.content.push({ text: ['Goiânia, ', dia, ' de ', mes, ' de ', ano], style: 'dataLocal' });

			var primeiraLinha = {
				style: 'coluna',
				columns: []
			}
			var segundaLinha = {
				style: 'coluna',
				columns: []
			}
			var terceiraLinha = {
				style: 'coluna',
				columns: []
			}

			angular.forEach(content.assinaturas, function (e, key) {
				if (key < 2) {
					primeiraLinha.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcao.toUpperCase() + '\n' }, { text: 'MATRÍCULA: ' + e.funcionario.matricula }], style: 'assinatura' });
				} else if (key < 4) {
					segundaLinha.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcao.toUpperCase() + '\n' }, { text: 'MATRÍCULA: ' + e.funcionario.matricula }], style: 'assinatura' });
				} else {
					terceiraLinha.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcao.toUpperCase() + '\n' }, { text: 'MATRÍCULA: ' + e.funcionario.matricula }], style: 'assinatura' });
				}
			});

			docDefinition.content.push(primeiraLinha);

			docDefinition.content.push(segundaLinha);

			docDefinition.content.push(terceiraLinha);

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
					alignment: 'center',
					margin: [0, 10, 0, 5]
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
				}
			};

			docDefinition.defaultStyle = {
				alignment: 'justify'
			};
			return docDefinition;
		}

	}

})();
