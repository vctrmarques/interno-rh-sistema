(function () {
	'use strict';

	angular.module('app.page')
		.controller('certidaoTempoContribuicaoExServidorCtrl', ['configValue', '$scope', 'RestService', '$mdToast', '$mdDialog', '$mdMedia', '$rootScope', 'GenericoService', '$filter', 'EnumService', '$location', '$q', certidaoTempoContribuicaoExServidorCtrl]);

	function certidaoTempoContribuicaoExServidorCtrl(configValue, $scope, RestService, $mdToast, $mdDialog, $mdMedia, $rootScope, GenericoService, $filter, EnumService, $location, $q) {

		// Checa as regras de acesso da tela
		RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'CTC - Ex-Servidor' } }).then(
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
		$scope.query = {
			order: 'descricao',
			limit: 10,
			page: 1
		};

		$scope.list = {
			"count": 0,
			"data": []
		};
		$scope.lista = {};

		EnumService.Get("StatusSituacaoCertidaoExSeguradoEnum").then(function (dados) {
			$scope.lista.status = dados;
			$scope.abaActive = $scope.lista.status.length;
			$scope.lista.abas = [
				{ label: 'Rascunho', value: 'RASCUNHO' },
				{ label: 'Aguardando impressão', value: 'AGUARDANDO_IMPRESSAO' },
				{ label: 'Arquivada', value: 'ARQUIVADO' }];
		});

		$scope.abaAtual = function (valor) {
			$scope.aba = valor;
			if ($scope.aba == 'TODAS') {
				delete $scope.status;
			} else {
				$scope.status = $scope.aba;
			}
			$scope.loadList();
		};

		$scope.limpaFiltro = function () {
			delete $scope.descricaoBusca;
			delete $scope.status;
			$scope.abaActive = $scope.lista.status.length;
			$scope.loadList();
		};

		$scope.loadList = function () {
			$rootScope.$broadcast('preloader:active');
			var config = {
				params: {
					page: $scope.query.page - 1,
					size: $scope.query.limit,
					order: '-createdAt',
					descricao: $scope.descricaoBusca,
					status: $scope.status
				}
			};

			$scope.promise = GenericoService.GetAll('/certidoesExSegurado', config).then(
				function (response) {
					if (response.status === 200) {
						$scope.list.data = response.data.content;
						$scope.list.count = response.data.totalElements;
						$rootScope.$broadcast('preloader:hide');
					} else {
						$scope.showSimpleToast("Não foi possível carregar os dados.")
					}
				});
		}

		$scope.limitOptions = [5, 10, 15];

		$scope.deleteItem = function () {
			$rootScope.$broadcast('preloader:active');
			GenericoService.Delete('/certidaoExSegurado/' + $scope.idToDelete, function (response) {
				$rootScope.$broadcast('preloader:hide');
				if (response.status === 200) {
					$scope.showSimpleToast(response.data.message);
					$scope.loadList();
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
			GenericoService.Create('/certidaoExSegurado/retificar', { id: $scope.idToRetificar }, function (response) {
				$rootScope.$broadcast('preloader:hide');
				if (response.status === 201 && response.data.success) {
					$scope.showSimpleToast(response.data.message);
					$location.path('/certidaoTempoContribuicaoExServidor/formulario/' + response.data.obj);
				}
			});
		}

		$scope.showRelatorio = function () {
			$rootScope.$broadcast('preloader:active');
			GenericoService.GetAllRelatorio('/certidoesExSegurado').then(
				function (response) {
					if (response.status === 200) {
						GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
							function (dataURL) {
								pdfMake.createPdf(getDocDefinition(response.data.content, dataURL)).open()
							});
						$rootScope.$broadcast('preloader:hide');
					}
				});
		}

		$scope.showConfirmImprimir = function (ev, idCertidao, arquivado) {
			var msg = 'Esta ação arquivará a certidão atual.';
			if (arquivado) {
				msg = '';
			}
			var confirm = $mdDialog.confirm()
				.title('Deseja imprimir a certidão deste item?')
				.textContent(msg)
				// .ariaLabel('Lucky day')
				.targetEvent(ev)
				.ok('Sim')
				.cancel('Não');

			$mdDialog.show(confirm).then(function () {
				$scope.imprimirCertidao(idCertidao);
			}, function () {
			});
		};

		$scope.imprimirCertidao = function (id) {
			$rootScope.$broadcast('preloader:active');
			GenericoService.GetById('/certidaoExSegurado/relatorio/' + id, function (response) {
				$rootScope.$broadcast('preloader:hide');
				if (response.status === 200) {
					GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
						function (dataURL) {
							pdfMake.createPdf(getCertidaoDefinition(response.data, dataURL)).open()
						});
					$scope.loadList();
				} else {
					$scope.showSimpleToast("Certidão não encontrado na base");
				}
			});
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

		function calularPeriodoDias(dataInicio, dataFim) {
			dataInicio = moment(dataInicio);
			dataFim = moment(dataFim);
			var diferenca = moment.duration({
				years: dataFim.year() - dataInicio.year(),
				months: dataFim.month() - dataInicio.month(),
				days: (dataFim.date() - dataInicio.date()) + 1
			});

			return diferenca.asDays();
		}

		function somatorioPeriodos(lista) {
			var dias = 0;
			var meses = 0;
			var anos = 0;

			angular.forEach(lista, function (e) {
				var inicio = moment(e.periodoInicio);
				var fim = moment(e.periodoFim)
				var diferenca = moment.duration({
					years: fim.year() - inicio.year(),
					months: fim.month() - inicio.month(),
					days: (fim.date() - inicio.date()) + 1
				});

				dias = dias + diferenca.days();
				meses = meses + diferenca.months();
				anos = anos + diferenca.years();
			});

			var total = moment.duration({
				years: anos,
				months: meses,
				days: dias
			});

			$scope.totalDias = total.asDays();

			var texto = '';

			if (total.years() > 0) {
				if (total.years() == 1) {
					texto += total.years() + ' (' + getExtenso(total.years()) + ') ano, ';
				} else {
					texto += total.years() + ' (' + getExtenso(total.years()) + ') anos, ';
				}
			} else {
				texto += ' 0 (zero) anos e';
			}

			if (total.months() > 0) {
				if (total.months() == 1) {
					texto += total.months() + ' (' + getExtenso(total.months()) + ') mês e ';
				} else {
					texto += total.months() + ' (' + getExtenso(total.months()) + ') meses e ';
				}
			} else {
				texto += ' 0 (zero) meses e ';
			}

			if (total.days() > 0) {
				if (total.days() == 1) {
					texto += total.days() + ' (' + getExtenso(total.days()) + ') dia';
				} else {
					texto += total.days() + ' (' + getExtenso(total.days()) + ') dias';
				}
			} else {
				texto += ' 0 (zero) dias';
			}

			return texto.toUpperCase();

		}

		function getTempoLiquido(frequencia) {
			var dias = 0;
			angular.forEach(frequencia, function (e) {
				dias = dias + e.tempoLiquido;
			});
			return dias;
		}

		function somatorioTempo(frequencia) {
			var dias = 0;
			angular.forEach(frequencia, function (e) {
				dias = dias + e.tempoLiquido;
			});

			var total = moment.duration(dias, 'days');

			$scope.totalDiasFrequencia = total.asDays();

			var texto = '';

			if (total.years() > 0) {
				if (total.years() == 1) {
					texto += total.years() + ' (' + getExtenso(total.years()) + ') ano, ';
				} else {
					texto += total.years() + ' (' + getExtenso(total.years()) + ') anos, ';
				}
			} else {
				texto += ' 0 (zero) anos e';
			}

			if (total.months() > 0) {
				if (total.months() == 1) {
					texto += total.months() + ' (' + getExtenso(total.months()) + ') mês e ';
				} else {
					texto += total.months() + ' (' + getExtenso(total.months()) + ') meses e ';
				}
			} else {
				texto += ' 0 (zero) meses e ';
			}

			if (total.days() > 0) {
				if (total.days() == 1) {
					texto += total.days() + ' (' + getExtenso(total.days()) + ') dia';
				} else {
					texto += total.days() + ' (' + getExtenso(total.days()) + ') dias';
				}
			} else {
				texto += ' 0 (zero) dias';
			}

			return texto.toUpperCase();

		}

		function getCargos(lista) {
			var texto = '';

			angular.forEach(lista, function (e) {
				texto = texto + e.descricaoCargo + ', ';
			});

			return texto;
		}

		function getLotacoes(lista) {
			var texto = '';

			angular.forEach(lista, function (e) {
				texto = texto + e.descricaoOrgaoLotacao + ', ';
			});

			return texto;
		}

		function getDocDefinition(content, dataURL) {

			var docDefinition = {};
			docDefinition.content = [];

			var brasao = { image: dataURL, width: 70, alignment: 'center' };
			docDefinition.content.push(brasao);

			var orgao = { text: 'RH', alignment: 'center', margin: [0, 10], bold: true };
			docDefinition.content.push(orgao);

			var titulo = { text: 'Certidão de tempo de contribuição (Ex-Servidor)', alignment: 'center', margin: [0, 10] };
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
					widths: ['*', 'auto', 'auto', 'auto', 'auto'],
					body: [
						[{ text: 'Funcionário', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Matrícula', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'PIS/PASEP', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Status', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'Data Criação', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }]
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
						return 'black';
					},
					vLineColor: function (i, node) {
						return 'black';
					}
				}

			}

			var i;
			for (i = 0; i < content.length; i++) {
				var element = content[i];
				var body = [
					{ text: element.funcionario.nome, fontSize: 10, alignment: 'left' },
					{ text: element.funcionario.matricula, fontSize: 10, alignment: 'center' },
					{ text: element.funcionario.pisPasep, fontSize: 10, alignment: 'center' },
					{ text: element.statusSituacaoCertidao, fontSize: 10, alignment: 'center' },
					{ text: convertDate(element.criadoEm, false), fontSize: 10, alignment: 'center' }
				]
				lista.table.body.push(body)
			}
			docDefinition.content.push(lista);

			return docDefinition;

		}

		function getFiliacao(funcionario) {
			if (funcionario.nomeMae && funcionario.nomePai) {
				return funcionario.nomeMae + " e " + funcionario.nomePai;
			} else if (funcionario.nomeMae) {
				return funcionario.nomeMae;
			} else {
				return funcionario.nomePai;
			}
		}

		function getLotacao(lotacao) {
			if (lotacao) {
				return lotacao.descricao.toUpperCase();
			} else {
				return '';
			}
		}

		function getFuncao(funcao) {
			if (funcao) {
				return funcao.nome.toUpperCase();
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

		function getMunicipio(funcionario) {
			if (funcionario.municipio) {
				return funcionario.municipio.descricao + '/' + funcionario.municipio.uf.sigla;
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

		String.prototype.extenso = function (c) {
			var ex = [
				["zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove"],
				["dez", "vinte", "trinta", "quarenta", "cinqüenta", "sessenta", "setenta", "oitenta", "noventa"],
				["cem", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos"],
				["mil", "milhão", "bilhão", "trilhão", "quadrilhão", "quintilhão", "sextilhão", "setilhão", "octilhão", "nonilhão", "decilhão", "undecilhão", "dodecilhão", "tredecilhão", "quatrodecilhão", "quindecilhão", "sedecilhão", "septendecilhão", "octencilhão", "nonencilhão"]
			];
			var a, n, v, i, n = this.replace(c ? /[^,\d]/g : /\D/g, "").split(","), e = " e ", $ = "real", d = "centavo", sl;
			for (var f = n.length - 1, l, j = -1, r = [], s = [], t = ""; ++j <= f; s = []) {
				j && (n[j] = (("." + n[j]) * 1).toFixed(2).slice(2));
				if (!(a = (v = n[j]).slice((l = v.length) % 3).match(/\d{3}/g), v = l % 3 ? [v.slice(0, l % 3)] : [], v = a ? v.concat(a) : v).length) continue;
				for (a = -1, l = v.length; ++a < l; t = "") {
					if (!(i = v[a] * 1)) continue;
					i % 100 < 20 && (t += ex[0][i % 100]) ||
						i % 100 + 1 && (t += ex[1][(i % 100 / 10 >> 0) - 1] + (i % 10 ? e + ex[0][i % 10] : ""));
					s.push((i < 100 ? t : !(i % 100) ? ex[2][i == 100 ? 0 : i / 100 >> 0] : (ex[2][i / 100 >> 0] + e + t)) +
						((t = l - a - 2) > -1 ? " " + (i > 1 && t > 0 ? ex[3][t].replace("ão", "ões") : ex[3][t]) : ""));
				}
				a = ((sl = s.length) > 1 ? (a = s.pop(), s.join(" ") + e + a) : s.join("") || ((!j && (n[j + 1] * 1 > 0) || r.length) ? "" : ex[0][0]));
				a && r.push(a + (c ? (" " + (v.join("") * 1 > 1 ? j ? d + "s" : (/0{6,}$/.test(n[0]) ? "de " : "") + $.replace("l", "is") : j ? d : $)) : ""));
			}
			return r.join(e);
		}

		function getExtenso(valor) {
			return String(valor).extenso(false).toUpperCase();
		}

		function getValorOrX(valor) {
			if (valor) {
				return valor;
			} else {
				return 'X';
			}
		}

		function getNumberOrX(valor) {
			if (valor) {
				return Number(valor).toFixed(2).replace('.', ',');
			} else {
				return 'X';
			}
		}

		function transpose(a) {
			return Object.keys(a[0]).map(function (c) {
				return a.map(function (r) {
					return r[c];
				});
			});
		}

		function separarEmGrupos(base, maximo) {
			var resultado = [[]];
			var grupo = 0;

			for (var indice = 0; indice < base.length; indice++) {
				if (resultado[grupo] === undefined) {
					resultado[grupo] = [];
				}

				resultado[grupo].push(base[indice]);

				if ((indice + 1) % maximo === 0) {
					grupo = grupo + 1;
				}
			}

			return resultado;
		}

		function organizaListaRemuneracoes(lista, maximo) {
			var valores = [];

			var colunas = {
				ano: 'ANO', janeiro: 'JANEIRO', fevereiro: 'FEVEREIRO',
				marco: 'MARÇO', abril: 'ABRIL', maio: 'MAIO',
				junho: 'JUNHO', julho: 'JULHO', agosto: 'AGOSTO',
				setembro: 'SETEMBRO', outubro: 'OUTUBRO', novembro: 'NOVEMBRO',
				dezembro: 'DEZEMBRO', decimoTerceiro: '13º'
			};

			angular.forEach(lista, function (e) {
				var linha = {
					ano: getValorOrX(e.ano),
					janeiro: getNumberOrX(e.janeiro),
					fevereiro: getNumberOrX(e.fevereiro),
					marco: getNumberOrX(e.marco),
					abril: getNumberOrX(e.abril),
					maio: getNumberOrX(e.maio),
					junho: getNumberOrX(e.junho),
					julho: getNumberOrX(e.julho),
					agosto: getNumberOrX(e.agosto),
					setembro: getNumberOrX(e.setembro),
					outubro: getNumberOrX(e.outubro),
					novembro: getNumberOrX(e.novembro),
					dezembro: getNumberOrX(e.dezembro),
					decimoTerceiro: getNumberOrX(e.decimoTerceiro)
				};

				valores.push(linha);
			});
			var ordenado = $filter('orderBy')(valores, 'ano');
			var numeroDeTabelas = Math.ceil(lista.length / maximo);
			var count = 0;
			for (var o = 0; o < numeroDeTabelas; o++) {
				ordenado.splice(count, 0, colunas);
				count = count + maximo;
				numeroDeTabelas = Math.ceil(ordenado.length / maximo);
			}
			var numeroDeGrupos = Math.ceil(ordenado.length / maximo);

			var grupos = separarEmGrupos(ordenado, maximo);

			for (var o = 0; o < numeroDeGrupos; o++) {
				grupos[o] = transpose(grupos[o]);
			}

			return grupos;
		}

        /*
         * Montagem Declaração para impressão
         * */
		function getCertidaoDefinition(content, dataURL) {

			var docDefinition = {
				pageSize: 'A4',
				pageOrientation: 'portrait',
				pageMargins: [40, 40, 40, 40],
				header: [],
				footer: { text: 'Av. Professor Alfredo de Castro, Qd - C, Lt - 16/18\n Setor Oeste - Goiânia-GO - CEP: 74110-030\nTel: 55 62 3524-5800 - ipsm@goiania.go.gov.br', style: 'footer' },
				info: {
					title: 'Certidão de tempo de contribuição - Ex-Servidor'
				}
			};

			docDefinition.content = [];

			var titulo = {
				style: 'tableTop',
				table: {
					widths: [100, '*'],
					body: [
						[{ image: dataURL, width: 80, alignment: 'center' }, { text: 'Instituto de Previdência dos Servidores do Município de Goiânia', style: 'tituloLogo' }]
					]
				},
				layout: 'noBorders'
			};

			docDefinition.content.push(titulo);

			docDefinition.content.push({ text: 'CERTIDÃO DE TEMPO DE CONTRIBUIÇÃO', style: 'subtituloSemMargem', alignment: 'center' });
			docDefinition.content.push({ text: 'NÚMERO DA CERTIDÃO: ' + content.numeroCertidao + '-' + content.anoCertidao, style: 'subtituloSemMargem', alignment: 'center' });
			if (content.numeroRetificacao) {
				docDefinition.content.push({ text: 'NÚMERO DA RETIFICAÇÃO: ' + content.numeroRetificacao, style: 'subtituloSemMargem', alignment: 'center' });
			}

			docDefinition.content.push({ text: 'DADOS FUNCIONAIS', style: 'subtitulo' });
			var dadosFuncionais = {
				style: 'tableTop',
				table: {
					widths: ['*', 'auto', 'auto'],
					headerRows: 1,
					body: [
						[{ text: [{ text: 'ORGÃO EXPEDITOR: ', bold: true }, 'Instituto de Previdência dos Servidores Municipais de Goiânia - GOIANIAPREV'], colSpan: 3, alignment: 'left' }, {}, {}],
						[{ text: [{ text: 'CNPJ: ', bold: true }, '08.948.407/0001-57'], colSpan: 3, alignment: 'left' }, {}, {}],
						[{ text: [{ text: 'NOME DO SERVIDOR: ', bold: true }, content.funcionario.nome.toUpperCase()], colSpan: 2, alignment: 'left' }, {}, { text: [{ text: 'GÊNERO: ', bold: true }, retornaGenero(content.funcionario.sexo)], colSpan: 1, alignment: 'left' }],
						[{ text: [{ text: 'IDENTIDADE: ', bold: true }, content.funcionario.identidade], colSpan: 1, alignment: 'left' }, { text: [{ text: 'CPF: ', bold: true }, $filter('cpf')(content.funcionario.cpf)], colSpan: 1, alignment: 'left' }, { text: [{ text: 'PIS/PASEP: ', bold: true }, content.funcionario.pisPasep], colSpan: 1, alignment: 'left' }],
						[{ text: [{ text: 'MATRÍCULA: ', bold: true }, content.funcionario.matricula], colSpan: 1, alignment: 'left' }, { text: [{ text: 'DATA NASCIMENTO: ', bold: true }, convertDate(content.funcionario.dataNascimento, false)], colSpan: 2, alignment: 'left' }, {}],
						[{ text: [{ text: 'FILIAÇÃO: ', bold: true }, getFiliacao(content.funcionario).toUpperCase()], colSpan: 3, alignment: 'left' }, {}, {}],
						[{ text: [{ text: 'ENDEREÇO: ', bold: true }, getEndereco(content.funcionario).toUpperCase()], colSpan: 3, alignment: 'left' }, {}, {}],
						[{ text: [{ text: 'CARGOS EXERCIDO: ', bold: true }, getCargos(content.cargos)], colSpan: 3, alignment: 'left' }, {}, {}],
						[{ text: [{ text: 'ÓRGÃOS LOTAÇÃO: ', bold: true }, getLotacoes(content.orgaosLotacao)], colSpan: 3, alignment: 'left' }, {}, {}],
						[{ text: [{ text: 'DATA ADMISSÃO: ', bold: true }, convertDate(content.funcionario.dataAdmissao, false)], colSpan: 1, alignment: 'left' }, { text: [{ text: 'DATA DE EXONERAÇÃO/DEMISSÃO: ', bold: true }, convertDate(content.dataExoneracao, false)], colSpan: 2, alignment: 'left' }, {}],
						[{ text: [{ text: 'PERÍODO EFETIVO DE CONTRIBUIÇÃO COMPREENDIDO NESTA CERTIDÃO DE: ', bold: true }, convertDate(content.funcionario.dataAdmissao, false) + ' A ' + convertDate(content.dataExoneracao, false)], colSpan: 3, alignment: 'left' }, {}, {}],
						[{ text: [{ text: 'FONTE DE INFORMAÇÃO: ', bold: true }, content.fonteInformacao], colSpan: 3, alignment: 'left' }, {}, {}],
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
						return 'black';
					},
					vLineColor: function (i, node) {
						return 'black';
					}
				}
			};
			var periodosOrdenados = $filter('orderBy')(content.periodos, 'periodoInicio');
			angular.forEach(periodosOrdenados, function (e) {
				dadosFuncionais.table.body.push([{
					text: [{ text: 'DESTINAÇÃO DO TEMPO DE CONTRIBUIÇÃO PERÍODO DE: ', bold: true }, convertDate(e.periodoInicio, false) + ' A ' + convertDate(e.periodoFinal, false),
					{ text: '\nPARA APROVEITAMENTO: ', bold: true }, e.aproveitamento.toUpperCase()], colSpan: 3, alignment: 'left'
				}, {}, {}]);
			});

			docDefinition.content.push(dadosFuncionais);

			docDefinition.content.push({ text: 'FREQUÊNCIA', style: 'subtitulo', alignment: 'center' });

			var frequencia = {
				style: 'tableTop',
				table: {
					widths: ['auto', 'auto', 'auto', 'auto', 'auto', 'auto', '*', 'auto', 'auto'],
					headerRows: 1,
					body: [
						[{ text: 'ANO', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'TEMPO BRUTO\n(Dias)', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'FALTAS\n(Dias)', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'LICENÇAS\n(Dias)', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'LICENÇA SEM\nVENCIMENTO\n(Dias)', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'SUSPENSÕES\n(Dias)', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'DISPONIBILIDADE\n(Dias)', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'OUTRAS\n(Dias)', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
						{ text: 'TEMPO\nLIQUIDO\n(Dias)', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }]
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
						return 'black';
					},
					vLineColor: function (i, node) {
						return 'black';
					}
				}
			};
			var frequenciasOrdenadas = $filter('orderBy')(content.frequencias, 'ano');
			$scope.totalTempoLiquido = 0;
			angular.forEach(frequenciasOrdenadas, function (e) {
				$scope.totalTempoLiquido = $scope.totalTempoLiquido + e.tempoLiquido;
				frequencia.table.body.push([
					{ text: e.ano, fontSize: 10, alignment: 'center' },
					{ text: e.tempoBruto, fontSize: 10, alignment: 'center' },
					{ text: e.faltas, fontSize: 10, alignment: 'center' },
					{ text: e.licencas, fontSize: 10, alignment: 'center' },
					{ text: e.licencasSemVenc, fontSize: 10, alignment: 'center' },
					{ text: e.suspensoes, fontSize: 10, alignment: 'center' },
					{ text: e.disponibilidade, fontSize: 10, alignment: 'center' },
					{ text: e.outros, fontSize: 10, alignment: 'center' },
					{ text: e.tempoLiquido, fontSize: 10, alignment: 'center' }
				]);
			});

			frequencia.table.body.push([{ text: 'TOTAL: ', fontSize: 10, alignment: 'right', bold: true, colSpan: 8 }, {}, {}, {}, {}, {}, {}, {}, { text: $scope.totalTempoLiquido, fontSize: 10, alignment: 'center', bold: true }]);

			docDefinition.content.push(frequencia);

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

			angular.forEach(content.assinaturasCertidao, function (e, key) {
				if (key < 2) {
					primeiraLinha.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcaoAssinaturaCertidao + '\n', italics: true, bold: false }, { text: 'Matrícula: ' + e.funcionario.matricula, bold: false }], style: 'responsavel', alignment: 'center' });
				} else if (key < 4) {
					segundaLinha.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcaoAssinaturaCertidao + '\n', italics: true, bold: false }, { text: 'Matrícula: ' + e.funcionario.matricula, bold: false }], style: 'responsavel', alignment: 'center' });
				} else {
					terceiraLinha.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcaoAssinaturaCertidao + '\n', italics: true, bold: false }, { text: 'Matrícula: ' + e.funcionario.matricula, bold: false }], style: 'responsavel', alignment: 'center' });
				}
			});

			docDefinition.content.push(primeiraLinha);

			docDefinition.content.push(segundaLinha);

			docDefinition.content.push(terceiraLinha);

			docDefinition.content.push([{ text: '', pageBreak: 'before' }]);

			docDefinition.content.push(titulo);

			docDefinition.content.push({ text: 'CERTIDÃO DE TEMPO DE CONTRIBUIÇÃO', style: 'subtituloSemMargem', alignment: 'center' });
			docDefinition.content.push({ text: 'NÚMERO DA CERTIDÃO Nº: ' + content.numeroCertidao + '-' + content.anoCertidao, style: 'subtituloSemMargem', alignment: 'center' });
			if (content.numeroRetificacao) {
				docDefinition.content.push({ text: 'NÚMERO DA RETIFICAÇÃO: ' + content.numeroRetificacao, style: 'subtituloSemMargem', alignment: 'center' });
			}

			docDefinition.content.push({ text: 'FREQUÊNCIA - Discriminação das deduções do tempo bruto', style: 'subtitulo' });

			var frequenciaDeducao = {
				style: 'tableTop',
				table: {
					widths: ['*', 'auto', 'auto'],
					headerRows: 1,
					body: [
						[
							{ text: 'Períodos', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
							{ text: 'Tempos em dias', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
							{ text: 'Identificação da ocorrência', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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
						return 'black';
					},
					vLineColor: function (i, node) {
						return 'black';
					}
				}
			};

			var frequenciaLicenca = {
				style: 'tableTopM',
				table: {
					widths: ['*', 'auto', 'auto'],
					headerRows: 1,
					body: [
						[
							{ text: 'Períodos', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
							{ text: 'Tempos em dias', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
							{ text: 'Identificação da ocorrência', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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
						return 'black';
					},
					vLineColor: function (i, node) {
						return 'black';
					}
				}
			};
			if (content.detalhamentosFrequencia.length > 0) {
				angular.forEach(content.detalhamentosFrequencia, function (e) {
					if (e.tipo == 'FALTA' || e.tipo == 'LICENCA_SEM_VENCIMENTO' || e.tipo == 'SUSPENSAO') {
						frequenciaDeducao.table.body.push([
							{ text: 'De ' + getValorOrX(convertDate(e.periodoInicio, false)) + ' a ' + getValorOrX(convertDate(e.periodoFinal, false)), fontSize: 10, alignment: 'center' },
							{ text: getValorOrX(e.tempo), fontSize: 10, alignment: 'center' },
							{ text: getValorOrX(e.descricao), fontSize: 10, alignment: 'center' }
						]);
					} else {
						frequenciaLicenca.table.body.push([
							{ text: 'De ' + getValorOrX(convertDate(e.periodoInicio, false)) + ' a ' + getValorOrX(convertDate(e.periodoFinal, false)), fontSize: 10, alignment: 'center' },
							{ text: getValorOrX(e.tempo), fontSize: 10, alignment: 'center' },
							{ text: getValorOrX(e.descricao), fontSize: 10, alignment: 'center' }
						]);
					}
				});
			}
			if (frequenciaDeducao.table.body.length == 0) {
				frequenciaDeducao.table.body.push([
					{ text: 'X', fontSize: 10, alignment: 'center' },
					{ text: 'X', fontSize: 10, alignment: 'center' },
					{ text: 'X', fontSize: 10, alignment: 'center' }
				]);

			}
			if (frequenciaLicenca.table.body.length == 0) {
				frequenciaLicenca.table.body.push([
					{ text: 'X', fontSize: 10, alignment: 'center' },
					{ text: 'X', fontSize: 10, alignment: 'center' },
					{ text: 'X', fontSize: 10, alignment: 'center' }
				]);
			}

			docDefinition.content.push(frequenciaDeducao);

			docDefinition.content.push(frequenciaLicenca);

			var tempoEspecial = {
				style: 'tableTop',
				table: {
					widths: ['*', 'auto', 'auto'],
					headerRows: 1,
					body: [
						[{ text: 'Tempo especial incluído, sem conversão no período de contribuição compreendido nesta certidão (P. Único do Art. 5º)', colSpan: 3, alignment: 'center', bold: true }, {}, {}],
						[{ text: '', colSpan: 1, alignment: 'center' }, { text: 'Período', colSpan: 1, alignment: 'center' }, { text: 'T. em dias', colSpan: 1, alignment: 'center' }],
						[{ text: 'I - Exercício na condição de pessoa com deficiência a: ', colSpan: 3, alignment: 'left' }, {}, {}],
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
						return 'black';
					},
					vLineColor: function (i, node) {
						return 'black';
					}
				}
			};

			if (content.tempoEspecialGrauGrave.length > 0) {
				tempoEspecial.table.body.push([{ text: 'a) Grave: ', colSpan: 1, alignment: 'left' }, {}, {}]);

				angular.forEach(content.tempoEspecialGrauGrave, function (e) {
					tempoEspecial.table.body.push([
						{},
						{ text: convertDate(e.periodoInicio, false) + ' a ' + convertDate(e.periodoFinal, false), alignment: 'center', colSpan: 1 },
						{ text: e.tempo, alignment: 'center', colSpan: 1 }
					]);
				});
			} else {
				tempoEspecial.table.body.push([{ text: 'a) Grave: ', colSpan: 1, alignment: 'left' }, { text: 'X', alignment: 'center', colSpan: 1 }, { text: 'X', alignment: 'center', colSpan: 1 }]);
			}
			if (content.tempoEspecialGrauModerado.length > 0) {
				tempoEspecial.table.body.push([{ text: 'b) Moderada: ', colSpan: 1, alignment: 'left' }, {}, {}]);
				angular.forEach(content.tempoEspecialGrauModerado, function (e) {
					tempoEspecial.table.body.push([
						{},
						{ text: convertDate(e.periodoInicio, false) + ' a ' + convertDate(e.periodoFinal, false), alignment: 'center', colSpan: 1 },
						{ text: e.tempo, alignment: 'center', colSpan: 1 }
					]);
				});
			} else {
				tempoEspecial.table.body.push([{ text: 'b) Moderada: ', colSpan: 1, alignment: 'left' }, { text: 'X', alignment: 'center', colSpan: 1 }, { text: 'X', alignment: 'center', colSpan: 1 }]);
			}

			if (content.tempoEspecialGrauLeve.length > 0) {
				tempoEspecial.table.body.push([{ text: 'c) Leve: ', colSpan: 1, alignment: 'left' }, {}, {}]);
				angular.forEach(content.tempoEspecialGrauLeve, function (e) {
					tempoEspecial.table.body.push([
						{},
						{ text: convertDate(e.periodoInicio, false) + ' a ' + convertDate(e.periodoFinal, false), alignment: 'center', colSpan: 1 },
						{ text: e.tempo, alignment: 'center', colSpan: 1 }
					]);
				});
			} else {
				tempoEspecial.table.body.push([{ text: 'c) Leve: ', colSpan: 1, alignment: 'left' }, { text: 'X', alignment: 'center', colSpan: 1 }, { text: 'X', alignment: 'center', colSpan: 1 }]);
			}

			if (content.tempoEspecialAtividadeRisco.length > 0) {
				tempoEspecial.table.body.push([{ text: 'II - Exercício em atividades de risco: ', colSpan: 3, alignment: 'left' }, {}, {}]);
				angular.forEach(content.tempoEspecialAtividadeRisco, function (e) {
					tempoEspecial.table.body.push([
						{},
						{ text: convertDate(e.periodoInicio, false) + ' a ' + convertDate(e.periodoFinal, false), alignment: 'center', colSpan: 1 },
						{ text: e.tempo, alignment: 'center', colSpan: 1 }
					]);
				});
			} else {
				tempoEspecial.table.body.push([{ text: 'II - Exercício em atividades de risco: ', colSpan: 1, alignment: 'left' }, { text: 'X', alignment: 'center', colSpan: 1 }, { text: 'X', alignment: 'center', colSpan: 1 }]);
			}

			if (content.tempoEspecialCondicoesEspeciais.length > 0) {
				tempoEspecial.table.body.push([{ text: 'III - Exercício em atividades sob condições especiais que prejudicam a saúde ou a integridade física: ', colSpan: 3, alignment: 'left' }, {}, {}]);
				angular.forEach(content.tempoEspecialCondicoesEspeciais, function (e) {
					tempoEspecial.table.body.push([
						{},
						{ text: convertDate(e.periodoInicio, false) + ' a ' + convertDate(e.periodoFinal, false), alignment: 'center', colSpan: 1 },
						{ text: e.tempo, alignment: 'center', colSpan: 1 }
					]);
				});

			} else {
				tempoEspecial.table.body.push([{ text: 'III - Exercício em atividades sob condições especiais que prejudicam a saúde ou a integridade física: ', colSpan: 1, alignment: 'left' }, { text: 'X', alignment: 'center', colSpan: 1 }, { text: 'X', alignment: 'center', colSpan: 1 }]);
			}

			docDefinition.content.push(tempoEspecial);

			var textoCertifico = {
				style: 'tableTop',
				table: {
					widths: ['*'],
					headerRows: 1,
					body: []
				},

				layout: {
					hLineWidth: function (i, node) {
						return 1;
					},
					vLineWidth: function (i, node) {
						return 1;
					},
					hLineColor: function (i, node) {
						return 'black';
					},
					vLineColor: function (i, node) {
						return 'black';
					}
				}
			};

			somatorioPeriodos(content.periodos);

			var certifico = 'CERTIFICO em face do apurado, que o(a) requerente conta, de efetivo exercício prestado neste Órgão, o tempo de contribuição de ' + getTempoLiquido(content.frequencias) + ' (' + getExtenso(getTempoLiquido(content.frequencias)) + ') dias, correspondente a ' + somatorioTempo(content.frequencias) + '. ' +
				'Informamos que no período compreendido o recolhimento foi feito em favor do Regime Próprio de Previdência – RPPS. ' +
				'O(A) servidor(a) admitido(a) em ' + convertDate(content.funcionario.dataAdmissao, false) + ' conforme Decreto Nº 1494 de 07/08/2006 sob regime da Estatutário exerceu o(s) cargo(s) de ' + getCargos(content.cargos) + ' lotado na ' + getLotacoes(content.orgaosLotacao) + ', conforme Decreto nº 728 de 26/02/2019 foi exonerado(a) a pedido desde ' + convertDate(content.dataExoneracao, false) + '. ' +
				'CERTIFICO que a Lei nº 8095 de 26/04/2002, assegura aos servidores do Estado/Município de Goiânia Aposentadorias voluntárias, por invalidez e compulsórias, e pensão por morte, com aproveitamento de tempo de contribuição para o Regime Geral de Previdência Social ou outro Regime Próprio de Previdência Social, na forma da contagem recíproca, conforme Lei Federal nº6.226, de 14/07/75, com alteração dada pela Lei Federal nº6.864, de 01/12/80. ' +
				'Certifico ainda para os devidos fins e dou fé as informações aqui prestadas. Averbações asseguradas pelo artigo 4º da Emenda Constitucional nº 20 de 15/12/1998.';

			textoCertifico.table.body.push([{ text: certifico.toUpperCase(), style: 'observacao', fontSize: 8, border: [true, true, true, false] }]);

			var textoHomologo = 'Homologo o presente documento e declaro que as informações nele constantes correspondem com a verdade.';

			textoCertifico.table.body.push([{ text: textoHomologo.toUpperCase(), alignment: 'center', fontSize: 8, border: [true, false, true, false] }]);

			var data = new Date();
			var dia = $filter('date')(data, 'dd');
			var mes = $filter('date')(data, 'MMMM');
			var ano = $filter('date')(data, 'yyyy');

			var dataExtenso = 'Goiânia, ' + dia + ' de ' + mes + ' de ' + ano;

			textoCertifico.table.body.push([{ text: dataExtenso.toUpperCase(), alignment: 'center', fontSize: 8, border: [true, false, true, false] }]);

			textoCertifico.table.body.push([{ text: 'UNIDADE GESTORA DO RPPS', alignment: 'center', fontSize: 8, border: [true, false, true, false] }]);

			textoCertifico.table.body.push([{ text: 'ESTE DOCUMENTO POSSUI ________ FOLHA(S) E NÃO CONTEM EMENDAS E RASURAS', alignment: 'center', fontSize: 8, border: [true, false, true, true] }]);

			docDefinition.content.push(textoCertifico);

			var primeiraLinha2 = {
				style: 'coluna',
				columns: []
			}
			var segundaLinha2 = {
				style: 'coluna',
				columns: []
			}
			var terceiraLinha2 = {
				style: 'coluna',
				columns: []
			}

			angular.forEach(content.assinaturasDetalhamento, function (e, key) {
				if (key < 2) {
					primeiraLinha2.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcaoAssinaturaCertidao + '\n', italics: true, bold: false }, { text: 'Matrícula: ' + e.funcionario.matricula, bold: false }], style: 'responsavel', alignment: 'center' });
				} else if (key < 4) {
					segundaLinha2.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcaoAssinaturaCertidao + '\n', italics: true, bold: false }, { text: 'Matrícula: ' + e.funcionario.matricula, bold: false }], style: 'responsavel', alignment: 'center' });
				} else {
					terceiraLinha2.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcaoAssinaturaCertidao + '\n', italics: true, bold: false }, { text: 'Matrícula: ' + e.funcionario.matricula, bold: false }], style: 'responsavel', alignment: 'center' });
				}
			});

			docDefinition.content.push(primeiraLinha2);

			docDefinition.content.push(segundaLinha2);

			docDefinition.content.push(terceiraLinha2);

			docDefinition.content.push([{ text: '', pageBreak: 'before' }]);

			docDefinition.content.push(titulo);

			docDefinition.content.push({ text: 'Anexo II', style: 'subtituloSemMargem', alignment: 'center' });
			docDefinition.content.push({ text: 'RELAÇÃO DAS REMUNERAÇÕES DE CONTRIBUIÇÕES', style: 'subtituloSemMargem', alignment: 'center' });
			docDefinition.content.push({ text: 'REFERENTE À CERTIDÃO DE TEMPO DE CONTRIBUIÇÃO Nº: ' + content.numeroCertidao + '-' + content.anoCertidao, style: 'subtituloSemMargem', alignment: 'center' });
			if (content.numeroRetificacao) {
				docDefinition.content.push({ text: 'NÚMERO DA RETIFICAÇÃO: ' + content.numeroRetificacao, style: 'subtituloSemMargem', alignment: 'center' });
			}

			var dadosAnexo = {
				style: 'tableTopM',
				table: {
					widths: ['*', 'auto'],
					headerRows: 1,
					body: [
						[{ text: [{ text: 'ORGÃO EXPEDITOR: ', bold: true }, 'Instituto de Previdência dos Servidores Municipais de Goiânia - GOIANAPREV'], colSpan: 1, alignment: 'left' }, { text: [{ text: 'CNPJ: ', bold: true }, '08.948.407/0001-57'], colSpan: 1, alignment: 'left' }],
						[{ text: [{ text: 'NOME DO SERVIDOR: ', bold: true }, content.funcionario.nome.toUpperCase()], colSpan: 1, alignment: 'left' }, { text: [{ text: 'MATRÍCULA: ', bold: true }, content.funcionario.matricula], colSpan: 1, alignment: 'left' }],
						[{ text: [{ text: 'FILIAÇÃO: ', bold: true }, getFiliacao(content.funcionario).toUpperCase()], colSpan: 1, alignment: 'left' }, { text: [{ text: 'DATA NASCIMENTO: ', bold: true }, convertDate(content.funcionario.dataNascimento, false)], colSpan: 1, alignment: 'left' }],
						[{ text: [{ text: 'DATA INÍCIO DA CONTRIBUIÇÃO/ADMISSÃO: ', bold: true }, convertDate(content.funcionario.dataAdmissao, false)], colSpan: 1, alignment: 'left' }, { text: [{ text: 'CPF: ', bold: true }, $filter('cpf')(content.funcionario.cpf)], colSpan: 1, alignment: 'left' }],
						[{ text: [{ text: 'DATA DE EXONERAÇÃO/DEMISSÃO: ', bold: true }, convertDate(content.dataExoneracao, false)], colSpan: 1, alignment: 'left' }, { text: [{ text: 'PIS/PASEP: ', bold: true }, content.funcionario.pisPasep], colSpan: 1, alignment: 'left' }],
					]
				},
			};

			docDefinition.content.push(dadosAnexo);

			if (content.relacaoRemuneracoes.length > 0) {
				var maximo = 8;
				var listaRemuneracoes = organizaListaRemuneracoes(content.relacaoRemuneracoes, maximo);
				for (var g = 0; g < listaRemuneracoes.length; g++) {
					var col = [];
					var tamanhoColunas = listaRemuneracoes[g][g].length;

					for (var c = 0; c < tamanhoColunas; c++) {
						if (c == 0) {
							col.push(60);
						} else {
							col.push('*');
						}
					}

					var remuneracoes = {
						style: 'tableTopM',
						table: {
							widths: col,
							headerRows: 1,
							body: []
						},

						layout: {
							hLineWidth: function (i, node) {
								return 1;
							},
							vLineWidth: function (i, node) {
								return 1;
							},
							hLineColor: function (i, node) {
								return 'black';
							},
							vLineColor: function (i, node) {
								return 'black';
							}
						}
					};

					angular.forEach(listaRemuneracoes[g], function (e, index) {
						var montagem = [];
						for (var i = 0; i < e.length; i++) {
							var texto = { text: e[i], fontSize: 10, alignment: (i == 0) ? 'left' : 'center', bold: (i == 0 || index == 0) ? true : false, fillColor: (i == 0 || index == 0) ? 'lightgray' : '' };
							montagem.push(texto);
						}
						remuneracoes.table.body.push(montagem);
					});

					docDefinition.content.push(remuneracoes);
				}

			}


			docDefinition.content.push({ text: dataExtenso.toUpperCase(), alignment: 'center', fontSize: 8 });

			var primeiraLinha3 = {
				style: 'coluna',
				columns: []
			}
			var segundaLinha3 = {
				style: 'coluna',
				columns: []
			}
			var terceiraLinha3 = {
				style: 'coluna',
				columns: []
			}

			angular.forEach(content.assinaturasRelacao, function (e, key) {
				if (key < 2) {
					primeiraLinha3.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcaoAssinaturaCertidao + '\n', italics: true, bold: false }, { text: 'Matrícula: ' + e.funcionario.matricula, bold: false }], style: 'responsavel', alignment: 'center' });
				} else if (key < 4) {
					segundaLinha3.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcaoAssinaturaCertidao + '\n', italics: true, bold: false }, { text: 'Matrícula: ' + e.funcionario.matricula, bold: false }], style: 'responsavel', alignment: 'center' });
				} else {
					terceiraLinha3.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcaoAssinaturaCertidao + '\n', italics: true, bold: false }, { text: 'Matrícula: ' + e.funcionario.matricula, bold: false }], style: 'responsavel', alignment: 'center' });
				}
			});

			docDefinition.content.push(primeiraLinha3);

			docDefinition.content.push(segundaLinha3);

			docDefinition.content.push(terceiraLinha3);

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
					margin: [0, 0, 0, 0]
				},
				tableTopM: {
					margin: [0, 10, 0, 10]
				},
				tableMiddle: {
					margin: [0, 50, 0, 50]
				},
				tableBottom: {
					margin: [0, 60, 0, 0],
					alignment: 'center'
				},
				tableHeader: {
					bold: true,
					color: 'black'
				},
				responsavel: {
					bold: true,
					color: 'black'
				},
				responsavelLabel: {
					bold: false,
					italics: true,
					color: 'black'
				},
				tituloLogo: {
					bold: true,
					margin: [0, 0, 0, 0],
					alignment: 'right',
					decoration: 'underline',
					color: 'grey'
				},
				titulo: {
					bold: true,
					fontSize: 14,
					margin: [100, 20, 0, 0]
				},
				subtitulo: {
					bold: true,
					alignment: 'left',
					margin: [0, 10, 0, 5]
				},
				subtituloSemMargem: {
					bold: true,
					alignment: 'center',
					margin: [0, 0, 0, 0]
				},
				subtituloUltimo: {
					bold: true,
					alignment: 'center',
					margin: [0, 0, 0, 10]
				},
				footer: {
					fontSize: 8,
					alignment: 'left',
					height: 200,
					margin: [40, 0, 0, 10]
				},
				coluna: {
					alignment: 'center',
					margin: [0, 20, 0, 0]
				},
				observacao: {
					alignment: 'justify',
					margin: [0, 10, 0, 10]
				}
			};

			docDefinition.defaultStyle = {
				alignment: 'justify',
				fontSize: 10
			};
			return docDefinition;
		}


	}

})();
