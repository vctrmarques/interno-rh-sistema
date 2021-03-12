(function () {
    'use strict';

    angular.module('app.page')
        .controller('recadastramentoFormCtrl', ['$interval', '$state', '$location', '$q', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$rootScope', 'GenericoService', 'EnumService', 'ParametroService', '$filter', 'FileUploader', '$http', recadastramentoFormCtrl]);

    function recadastramentoFormCtrl($interval, $state, $location, $q, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $rootScope, GenericoService, EnumService, ParametroService, $filter, FileUploader, $http) {

        $scope.botoesGestao = false;

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                $scope.botoesGestao = true;
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
        });

        $scope.limitOptions = [5, 10, 15];
        $scope.list = {
            "count": 0,
            "data": []
        };
        $scope.detalhes = false;
        $scope.visualizar = false;
        $scope.lista = {};
        $scope.lista.anexos = [];

        $scope.query = {
            order: '-createdAt',
            limit: 10,
            page: 1
        };

        $scope.lista.historico = {
            "count": 0,
            "data": []
        };

        // Init Basic
        
        $scope.recadastramento = {};
        $scope.recadastramento.dados = {};
        $scope.recadastramento.endereco = {};
        $scope.recadastramento.contato = {};
        $scope.recadastramento.endereco.telefones = [];
        $scope.recadastramento.contato.telefones = [];

        $scope.pesquisa = {};

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        if ($state.params && $state.params.visualizar) {
            $scope.detalhes = true;
            $scope.visualizar = true;
        }

        $scope.limpaFiltro = function () {
            delete $scope.pesquisa.dt;
            $scope.loadHistorico();
        }

         /*Início - Aba Recadastramento*/

        EnumService.Get("GeneroEnum").then(function (dados) {
            $scope.lista.genero = $filter('orderBy')(dados, 'label');
        });

        EnumService.Get("EstadoCivilEnum").then(function (dados) {
            $scope.lista.estadoCivil = $filter('orderBy')(dados, 'label');
        });

        EnumService.Get("CorPeleEnum").then(function (dados) {
            $scope.lista.raca = $filter('orderBy')(dados, 'label');
        });

        EnumService.Get("GrauInstrucaoEnum").then(function (dados) {
            $scope.lista.grauInstrucao = $filter('orderBy')(dados, 'label');
        });

        EnumService.Get("TipoTelefoneEnum").then(function (dados) {
            $scope.lista.tipoTelefone = $filter('orderBy')(dados, 'label');
        });

        $scope.getUnidadesFederativas = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUfs', function (response) {
                if (response.status === 200) {
                    $scope.lista.uf = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        
        $scope.getUnidadesFederativas();

        $scope.getNacionalidades = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaNacionalidades', function (response) {
                if (response.status === 200) {
                    $scope.lista.nacionalidade = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.getNacionalidades();
        
        $scope.getMunicipios = function (id) {
            $scope.getMunicipiosByUf(id, false);
        }

        $scope.getMunicipiosEndereco = function (id) {
            $scope.getMunicipiosByUf(id, true);
        }

        $scope.getMunicipiosByUf = function (id, isEndereco) {
            GenericoService.GetAllDropdownById(id, function (response) {
                if (response.status === 200) {
                    if(isEndereco){
                        $scope.lista.municipioEndereco = response.data;
                    } else {
                        $scope.lista.municipio = response.data;
                    }
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                }
            });
        }

        $scope.adicionarTelefoneEndereco = function () {
            adicionarTelefone(true);
        }

        $scope.adicionarTelefoneContato = function () {
            adicionarTelefone(false);
        }

        $scope.abaAtual = function (valor) {
            $scope.aba = valor;
        };

        function adicionarTelefone(isEndereco) {
            var msg = "Não foi possível adicionar o telefone, verifique se os campos estão preenchidos";
            
            if(isEndereco) {
                if($scope.numeroTelefone && $scope.tipoTelefone) {
                    let telefone = { numero: $scope.numeroTelefone, tipo: $scope.tipoTelefone }
                    $scope.recadastramento.endereco.telefones.push(telefone);
                    
                    delete $scope.numeroTelefone;
                    delete $scope.tipoTelefone;
                } else {
                    $scope.showSimpleToast(msg);
                }
            } else {
                if($scope.numeroTelefoneContato && $scope.tipoTelefoneContato) {
                    let telefone = { numero: $scope.numeroTelefoneContato, tipo: $scope.tipoTelefoneContato }
                    $scope.recadastramento.contato.telefones.push(telefone);
                    
                    delete $scope.numeroTelefoneContato;
                    delete $scope.tipoTelefoneContato;
                } else {
                    $scope.showSimpleToast(msg);
                }
            }
        }

        $scope.removerTelefoneEndereco = function (index) {
            removerTelefone(index, true);
        }

        $scope.removerTelefoneContato = function (index) {
            removerTelefone(index, false);
        }

        function removerTelefone(index, isEndereco) {
            if(isEndereco){
                $scope.recadastramento.endereco.telefones.splice(index, 1);
            } else {
                $scope.recadastramento.contato.telefones.splice(index, 1);
            }
        }

        $scope.removerAnexo = function (item) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/recadastramento/remover-anexo/' + item.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 410) {
                    $scope.showSimpleToast(response.data.message);
                    var index = $scope.recadastramento.anexos.indexOf(item);
                    $scope.recadastramento.anexos.splice(index, 1);

                    $scope.lista.anexos = [];
                    $scope.lista.anexos = $scope.recadastramento.anexos;

                }
            });
        }

        /*Fim - Aba Recadastramento*/

        /*Início - Aba Anexos*/

        var validateExtensions = ["application/pdf"];

        $scope.uploader = new FileUploader();
        $scope.uploader.url = configValue.baseUrl + "/api/anexo/recadastramento";
        $scope.uploader.headers = $http.defaults.headers.common;
        $scope.uploader.removeAfterUpload = true;
        $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.anexo = response.obj;
            if ($scope.anexo && $scope.anexo.id) {
                $scope.lista.anexos.push($scope.anexo);
                
            }
        };

        $scope.uploader.onAfterAddingFile = function (fileItem) {
            if (validateExtensions.indexOf(fileItem.file.type) == -1) {
                $scope.showSimpleToast('So é aceito arquivos do tipo: .pdf');
                $scope.uploader.removeFromQueue(fileItem);
            }
        };

        /*Fim - Aba Anexos*/

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

        function calularPeriodoReport(dataInicio, dataFim) {
            return $scope.calularPeriodo(dataInicio, dataFim, true)
        }

        /*
         * Retorna para a listagem
         * 
         * */
        $scope.goBack = function () {
            if($scope.recadastramento.status){
                ParametroService.setParametroVoltar(true);
                $location.path('/recadastramento/gestao');
            } else {
                $location.path(ParametroService.getParametroHistorico());
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
					$scope.loadHistorico();
				}
			});
		}

        $scope.GetById = function (id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/recadastramento/' + id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.recadastramento = response.data;
                    $scope.lista.anexos = $scope.recadastramento.anexos;

                    if(response.data.funcionario){
                        $scope.recadastramento.funcionarioId = response.data.funcionario.id;
                    }

                    if(response.data.pensao){
                        $scope.recadastramento.pensaoId = response.data.pensao.id;
                    }

                    if(!$scope.recadastramento.endereco) {
                        $scope.recadastramento.endereco = {};
                        $scope.recadastramento.endereco.telefones = [];
                    }

                    if(!$scope.recadastramento.contato) {
                        $scope.recadastramento.contato = {};
                        $scope.recadastramento.contato.telefones = [];
                    }

                    $scope.recadastramento.dataNascimento = $scope.convertDate(response.data.dataNascimento, false);
                    
                    $scope.getUnidadesFederativas();

                    $scope.getNacionalidades();

                    if($scope.recadastramento.dados.municipio) {
                        $scope.unidadeFederativa = $scope.recadastramento.dados.municipio.uf.id;
                        $scope.getMunicipios($scope.recadastramento.dados.municipio.uf.id);
                        $scope.recadastramento.dados.municipioId = $scope.recadastramento.dados.municipio.id;
                    }

                    if($scope.recadastramento.endereco.municipio) {
                        $scope.unidadeFederativaEndereco = $scope.recadastramento.endereco.municipio.uf.id;
                        $scope.getMunicipiosEndereco($scope.recadastramento.endereco.municipio.uf.id);
                        $scope.recadastramento.endereco.municipioId = $scope.recadastramento.endereco.municipio.id;
                    }

                    if($scope.recadastramento.dados.nacionalidade)
                        $scope.recadastramento.dados.nacionalidadeId = $scope.recadastramento.dados.nacionalidade.id;

                    if($scope.detalhes) {
                        $scope.loadHistorico();
                        if($scope.recadastramento.status){
                            ParametroService.setParametroHistorico('/recadastramento/detalhes/' + $scope.recadastramento.id + '/true');
                        }
                    }

                } else {
                    $scope.showSimpleToast("Recadastramento não encontrado na base");
                }
            });
        }

        $scope.GetByIdVisualizar = function (id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/recadastramento/visualizar/' + id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.recadastramento = response.data;
                    $scope.lista.anexos = $scope.recadastramento.anexos;

                    if(response.data.funcionario){
                        $scope.recadastramento.funcionarioId = response.data.funcionario.id;
                    }

                    if(response.data.pensao){
                        $scope.recadastramento.pensaoId = response.data.pensao.id;
                    }

                    if(!$scope.recadastramento.endereco) {
                        $scope.recadastramento.endereco = {};
                        $scope.recadastramento.endereco.telefones = [];
                    }

                    if(!$scope.recadastramento.contato) {
                        $scope.recadastramento.contato = {};
                        $scope.recadastramento.contato.telefones = [];
                    }

                    $scope.recadastramento.dataNascimento = $scope.convertDate(response.data.dataNascimento, false);
                    
                    $scope.getUnidadesFederativas();

                    $scope.getNacionalidades();

                    if($scope.recadastramento.dados.municipio) {
                        $scope.unidadeFederativa = $scope.recadastramento.dados.municipio.uf.id;
                        $scope.getMunicipios($scope.recadastramento.dados.municipio.uf.id);
                        $scope.recadastramento.dados.municipioId = $scope.recadastramento.dados.municipio.id;
                    }

                    if($scope.recadastramento.endereco.municipio) {
                        $scope.unidadeFederativaEndereco = $scope.recadastramento.endereco.municipio.uf.id;
                        $scope.getMunicipiosEndereco($scope.recadastramento.endereco.municipio.uf.id);
                        $scope.recadastramento.endereco.municipioId = $scope.recadastramento.endereco.municipio.id;
                    }

                    if($scope.recadastramento.dados.nacionalidade)
                        $scope.recadastramento.dados.nacionalidadeId = $scope.recadastramento.dados.nacionalidade.id;

                    if($scope.detalhes) {
                        $scope.loadHistorico();
                    }

                } else {
                    $scope.showSimpleToast("Recadastramento não encontrado na base");
                }
            });
        }

        $scope.GetByIdVisualizar = function (id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/recadastramento/visualizar/' + id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.recadastramento = response.data;
                    $scope.lista.anexos = $scope.recadastramento.anexos;

                    if(response.data.funcionario){
                        $scope.recadastramento.funcionarioId = response.data.funcionario.id;
                    }

                    if(response.data.pensao){
                        $scope.recadastramento.pensaoId = response.data.pensao.id;
                    }

                    if(!$scope.recadastramento.endereco) {
                        $scope.recadastramento.endereco = {};
                        $scope.recadastramento.endereco.telefones = [];
                    }

                    if(!$scope.recadastramento.contato) {
                        $scope.recadastramento.contato = {};
                        $scope.recadastramento.contato.telefones = [];
                    }

                    $scope.recadastramento.dataNascimento = $scope.convertDate(response.data.dataNascimento, false);
                    
                    $scope.getUnidadesFederativas();

                    $scope.getNacionalidades();

                    if($scope.recadastramento.dados.municipio) {
                        $scope.unidadeFederativa = $scope.recadastramento.dados.municipio.uf.id;
                        $scope.getMunicipios($scope.recadastramento.dados.municipio.uf.id);
                        $scope.recadastramento.dados.municipioId = $scope.recadastramento.dados.municipio.id;
                    }

                    if($scope.recadastramento.endereco.municipio) {
                        $scope.unidadeFederativaEndereco = $scope.recadastramento.endereco.municipio.uf.id;
                        $scope.getMunicipiosEndereco($scope.recadastramento.endereco.municipio.uf.id);
                        $scope.recadastramento.endereco.municipioId = $scope.recadastramento.endereco.municipio.id;
                    }

                    if($scope.recadastramento.dados.nacionalidade)
                        $scope.recadastramento.dados.nacionalidadeId = $scope.recadastramento.dados.nacionalidade.id;

                    if($scope.detalhes) {
                        $scope.loadHistorico();
                    }

                } else {
                    $scope.showSimpleToast("Recadastramento não encontrado na base");
                }
            });
        }

        $scope.loadHistorico = function () {

            $rootScope.$broadcast('preloader:active');

			var config = {
				params: {
					page: $scope.query.page - 1,
					size: $scope.query.limit,
					order: $scope.query.order,
                    funcionarioId: $scope.recadastramento.funcionario.id,
                    data: $scope.pesquisa.dt ? $scope.pesquisa.dt.format('YYYY-MM-DD') : null
				}
			};

			$scope.historico = GenericoService.GetAll('/recadastramento/historico', config).then(
				function (response) {
					if (response.status === 200) {
						$scope.lista.historico.data = response.data.content;
						$scope.lista.historico.count = response.data.totalElements;

						$rootScope.$broadcast('preloader:hide');

					} else {
						$scope.showSimpleToast("Não foi possível carregar os dados.")
					}
				});
        }

        if ($state.params && $state.params.id) {
            $scope.GetById($state.params.id);
        }

        if ($state.params && $state.params.idFuncionario) {
            $scope.GetByIdVisualizar($state.params.idFuncionario);
        }

        $scope.save = function () {

            $rootScope.$broadcast('preloader:active');

            if ($scope.lista.anexos) {
                $scope.recadastramento.anexos = [];
                angular.forEach($scope.lista.anexos, function (e) {
                    $scope.recadastramento.anexos.push(e.id);
                });
            }

            if ($scope.recadastramento.id) {
                GenericoService.Update('/recadastramento', $scope.recadastramento, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $scope.goBack();
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('recadastramento/formulario/' + $state.params.id)
                    }
                });
            }
        }

    }

})();