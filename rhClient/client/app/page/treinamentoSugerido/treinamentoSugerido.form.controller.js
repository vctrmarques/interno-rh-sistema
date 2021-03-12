(function () {
    'use strict';

    angular.module('app.page')
        .controller('treinamentoSugeridoFormCtrl', ['$state', '$location', '$q', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$rootScope', 'GenericoService', 'EnumService', '$filter', treinamentoSugeridoFormCtrl]);

    function treinamentoSugeridoFormCtrl($state, $location, $q, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $rootScope, GenericoService, EnumService, $filter) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {

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
        $scope.treinamentoSugerido = {};
        
        $scope.treinamentoSugerido.funcionariosId = [];
        
        $scope.botoesGestao = false;
        $scope.funcionariosTreinamento = [];
        $scope.ch = null;

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.botoesGestao = true;
                }
            });

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.limpaFiltro = function () {
            delete $scope.descricaoBusca;
        }

        EnumService.Get("SituacaoTreinamentoSugeridoEnum").then(function (dados) {
            $scope.list.situacoes = dados;
        });

        EnumService.Get("TipoTreinamentoSugerido").then(function (dados) {
            $scope.list.tipos = dados;
        });
        
        $scope.getUnidadesFederativas = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUfs', function (response) {
                if (response.status === 200) {
                    $scope.list.uf = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        
        $scope.getUnidadesFederativas();
        
        $scope.getMunicipios = function (id) {
            $scope.getMunicipiosByUf(id);
        }
        
        $scope.getPeriodo = function (){
        	var resultado = '';
        	if($scope.treinamentoSugerido.dataInicio && $scope.treinamentoSugerido.dataFinal){
        		var inicio = $scope.treinamentoSugerido.dataInicio;
        		var fim = $scope.treinamentoSugerido.dataFinal;
        		
        		resultado = fim.diff(inicio, 'Days') + 1;
        	}
        	if (resultado == 1) {
        		return resultado + ' Dia'
        	} else if (resultado > 1){
        		return resultado + ' Dias';
        	} else {
        		return '';
        	}
        	
        }

        $scope.getMunicipiosByUf = function (id) {
            GenericoService.GetAllDropdownById(id, function (response) {
                if (response.status === 200) {
                    $scope.list.municipio = response.data;
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                }
            });
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/treinamentoSugerido/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.treinamentoSugerido = response.data;
                    $scope.dataInicial = response.data.dataInicio;
                    $scope.dataFinal = response.data.dataFinal;
                    $scope.treinamentoSugerido.dataInicio = moment(response.data.dataInicio);
                    $scope.treinamentoSugerido.dataFinal = moment(response.data.dataFinal);
                    $scope.funcionariosTreinamento = $scope.treinamentoSugerido.funcionarios;
                    $scope.treinamentoSugerido.funcionariosId = [];
                    angular.forEach($scope.treinamentoSugerido.funcionarios, function(e){
                    	$scope.treinamentoSugerido.funcionariosId.push(e.id);
                    });
                    console.log($scope.treinamentoSugerido);
                    $scope.curso = $scope.treinamentoSugerido.curso;
                    $scope.ch = $scope.treinamentoSugerido.curso.cargaHoraria;
                    $scope.getComplemento();
                    $scope.getValores();
                } else {
                    $scope.showSimpleToast("Treinamento não encontrado na base");
                }
            });
        }
        
        $scope.getComplemento = function () {
        	$rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/treinamentoSugeridoComplemento/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.treinamentoSugeridoComplemento = response.data;
                    console.log($scope.treinamentoSugeridoComplemento);
                    $scope.unidadeFederativa = $scope.treinamentoSugeridoComplemento.municipio.uf;
                    $scope.getMunicipios($scope.treinamentoSugeridoComplemento.municipio.uf.id);
                } else {
                    $scope.showSimpleToast("Treinamento não encontrado na base");
                }
            });
        }
        
        $scope.getValores = function () {
        	$rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/treinamentoSugeridoValores/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.treinamentoSugeridoValores = response.data;
                } else {
                    $scope.showSimpleToast("Treinamento não encontrado na base");
                }
            });
        }

        $scope.cursoSelecionado = function () {  
            if ($scope.curso) {
                $scope.treinamentoSugerido.cursoId = $scope.curso.id;
                $scope.ch = $scope.curso.cargaHoraria;
            }
        };

        $scope.querySearchFuncionario = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/funcionario/searchNomeOrMatricula', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                $scope.funcionarios = response.data;
                                deferred.resolve(response.data);

                            }
                        });
                } else {
                    $scope.funcionarios = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };
        
        $scope.querySearchCurso = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/cursos/searchComplete', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                $scope.cursos = response.data;
                                deferred.resolve(response.data);

                            }
                        });
                } else {
                    $scope.cursos = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };

        $scope.goBack = function () {
            $location.path('/treinamentoSugerido/gestao');
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

        $scope.adicionarFuncionario = function (){
            if($scope.funcionario){
            	var existe;
            	angular.forEach($scope.funcionariosTreinamento, function(e){
                    if(e.id ==  $scope.funcionario.id){
                        $scope.showSimpleToast("Este funcionario já foi adicionado");
                        existe = true;
                        return;
                    }
                });
                if(!existe){
                    $scope.treinamentoSugerido.funcionariosId.push($scope.funcionario.id);
                    $scope.funcionariosTreinamento.push($scope.funcionario);
                }  
            }   
        }
        
        $scope.removerFuncionario = function(item) { 
        	  var index = $scope.funcionariosTreinamento.indexOf(item);
        	  $scope.funcionariosTreinamento.splice(index, 1);
        }
        
        $scope.validaBotaoCertificado = function() {
        	return $state.params && $state.params.id;
        }
        
        $scope.validaAbaComplemento = function() {
        	return $state.params && $state.params.id;
        }
        
        $scope.exibirFuncionario = function (item) {
        	var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));
        	$scope.funcionarioDetalhe = item;
            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$q', function ($scope, $q) {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/treinamentoSugerido/dialogFuncionario.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: useFullScreen
            });

        }
        
        $scope.save = function () {
            console.log($scope.treinamentoSugerido);

             $rootScope.$broadcast('preloader:active');
             
             $scope.resultado = angular.equals($scope.ch, $scope.curso.cargaHoraria);
             
             if(!$scope.resultado){
            	 
            	 $scope.curso.cargaHoraria = $scope.ch;
            	 
            	 GenericoService.Update('/curso', $scope.curso, function (response) {
                     $rootScope.$broadcast('preloader:hide');
                     if (response.status === 201 && response.data.success) {
                         $scope.showSimpleToast(response.data.message);
                     } else if (response.status === 400) {
                         $scope.showSimpleToast(response.data.message);
                     }
                 });
             }

             if ($scope.treinamentoSugerido.id) {
                 GenericoService.Update('/treinamentoSugerido', $scope.treinamentoSugerido, function (response) {
                     $rootScope.$broadcast('preloader:hide');
                     if (response.status === 201 && response.data.success) {
                         $scope.saveComplemento();
                     } else if (response.status === 400) {
                         $scope.showSimpleToast(response.data.message);
                         $location.path('treinamentoSugerido/formulario/' + $state.params.id)
                     }
                 });
             } else {
            	 $scope.treinamentoSugerido.situacao = 'Em Aberto';
                 GenericoService.Create('/treinamentoSugerido', $scope.treinamentoSugerido, function (response) {
                     $rootScope.$broadcast('preloader:hide');
                     if (response.status === 201 && response.data.success) {
                         $scope.showSimpleToast(response.data.message);
                         $location.path('/treinamentoSugerido/gestao');
                     } else if (response.status === 400) {
                         $scope.showSimpleToast(response.data.message);
                         $location.path('/treinamentoSugerido/formulario');
                     }
                 });
             }
        }
        
        $scope.saveComplemento = function () {
        	
        	if($scope.treinamentoSugeridoComplemento.municipio) {
        		$scope.treinamentoSugeridoComplemento.municipioId = $scope.treinamentoSugeridoComplemento.municipio 
        	}
        	$scope.treinamentoSugeridoComplemento.treinamentoSugeridoId = $scope.treinamentoSugerido.id
        	$rootScope.$broadcast('preloader:active');

        	if ($scope.treinamentoSugeridoComplemento.id) {
                GenericoService.Update('/treinamentoSugeridoComplemento', $scope.treinamentoSugeridoComplemento, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                    	$scope.saveValores();
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
            } else {
                GenericoService.Create('/treinamentoSugeridoComplemento', $scope.treinamentoSugeridoComplemento, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                    	$scope.saveValores();
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
            }
        }
        
        $scope.saveValores = function () {
        	
        	$scope.treinamentoSugeridoValores.treinamentoSugeridoId = $scope.treinamentoSugerido.id
        	
        	console.log($scope.treinamentoSugeridoValores);

        	$rootScope.$broadcast('preloader:active');

        	if ($scope.treinamentoSugeridoValores.id) {
                GenericoService.Update('/treinamentoSugeridoValores', $scope.treinamentoSugeridoValores, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/treinamentoSugerido/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/treinamentoSugerido/formulario');
                    }
                });
            } else {
                GenericoService.Create('/treinamentoSugeridoValores', $scope.treinamentoSugeridoValores, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/treinamentoSugerido/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/treinamentoSugerido/formulario');
                    }
                });
            }
        }
        
        $scope.showAta = function () {
            $rootScope.$broadcast('preloader:active');
            
            GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo, function (dataURL) {
            	pdfMake.createPdf(getAtaDefinition(dataURL)).open();
            });
            
            $rootScope.$broadcast('preloader:hide');
        
        }
        
        
        $scope.showCertificado = function () {
            $rootScope.$broadcast('preloader:active');
            
            GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo, function (dataURL) {
            	pdfMake.createPdf(getCertificadoDefinition(dataURL)).open();
            });
            
            $rootScope.$broadcast('preloader:hide');
        
        }
        
        function getCertificadoDefinition(dataURL) {

            var docDefinition = {
	    		pageSize: 'A4',
	    	    pageOrientation: 'landscape',
	    	    pageMargins: [ 40, 60, 40, 20 ],
	    	    info: {
	    	        title: 'Certificado treinamento'
	    	    }
            };
            docDefinition.content = [];
            
            var dataInicialFormatada = $filter('date')($scope.dataInicial, 'dd/MM/yyyy');
            var dataFinalFormatada = $filter('date')($scope.dataFinal, 'dd/MM/yyyy');
            
            angular.forEach($scope.funcionariosTreinamento, function (e) {
            	 var titulo = {
                 		style: 'tableTop',
                 		table: {
                 			widths: '*',
                 			body: [
                 				[{image: dataURL, width: 100, alignment: 'center'}, {text: 'Certificado', style: 'titulo', alignment: 'left'}]
                 				]
                 		},
                 		layout: 'noBorders'
                 };

	            docDefinition.content.push(titulo);

            	var texto = {text: ['Certificamos que, ', {text: e.nome, style: 'subheaderName'}, ' concluiu o curso de ', {text: $scope.curso.nomeCurso, style: 'subheaderName'}, ' com carga horária de ', {text: $scope.curso.cargaHoraria, style: 'subheaderName'}, 
        			' horas no período de ', {text: dataInicialFormatada + ' a ' + dataFinalFormatada + '.', style: 'subheaderName'}], style: 'subheader'};
            	
            	docDefinition.content.push(texto);
            	
            	var assinaturas = {
        				style: 'tableBottom',
        				table: {
        				    widths: '*',
        					body: [
        						['__________________________________________________________', '__________________________________________________________'],
        						[{text: e.empresa.nomeFilial, style: 'responsavel', alignment: 'center'}, {text: $scope.treinamentoSugerido.instrutor, style: 'responsavel', alignment: 'center'}],
        						[{text: 'Empresa Responsável', style: 'responsavelLabel', alignment: 'center'}, {text: 'Instrutor Responsável', style: 'responsavelLabel', alignment: 'center'}]
        					]
        				},
        				layout: 'noBorders'
        			};
            	
            	docDefinition.content.push(assinaturas);
                
            });
            
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
        				margin: [0, 0, 0, 50]
        			},
        			tableBottom: {
        				margin: [0, 120, 0, 0],
        				alignment: 'center'
        			},
        			tableHeader: {
        				bold: true,
        				fontSize: 13,
        				color: 'black'
        			},
        			responsavel: {
        				bold: true,
        				fontSize: 18,
        				color: 'black'
        			},
        			responsavelLabel: {
        				bold: false,
        				italics: true,
        				fontSize: 18,
        				color: 'black'
        			},
        			titulo: {
        			    bold: true,
        			    fontSize:45,
        			    margin: [100, 20, 0, 0]
        			}
        		};
            
            docDefinition.defaultStyle = {
        		alignment: 'justify'
        	};
            return docDefinition;
        };
        
        function getAtaDefinition(dataURL) {

            var docDefinition = {
	    		pageSize: 'A4',
	    	    pageOrientation: 'portrait',
	    	    pageMargins: [ 40, 60, 40, 20 ],
	    	    info: {
	    	        title: 'Ata Treinamento'
	    	    }
            };
            docDefinition.content = [];
            
            var dataInicialFormatada = $filter('date')($scope.dataInicial, 'dd/MM/yyyy');
            var dataFinalFormatada = $filter('date')($scope.dataFinal, 'dd/MM/yyyy');
            
            	 var titulo = {
                 		style: 'tableTop',
                 		table: {
                 			widths: '*',
                 			body: [
                 				[{image: dataURL, width: 100, alignment: 'center'}],
                 				[{text: 'Ata do curso ' + $scope.curso.nomeCurso, alignment: 'center'}]
                 				]
                 		},
                 		layout: 'noBorders'
                 };

	            docDefinition.content.push(titulo);
	            
	            var lista = {
	                    style: 'tableMiddle',
	                    table: {
	                        headerRows: 1,
	                        // dontBreakRows: true,
	                        // keepWithHeaderRows: 1,
	                        widths: [180,80,80,'*'],
	                        body: [
	                            [
	                                { text: 'Nome Funcionário', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
	                                { text: 'Matrícula', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
	                                { text: 'Lotação/Filial', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
	                                { text: 'Assinatura', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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

	                }

                    angular.forEach($scope.funcionariosTreinamento, function (e) {
                        lista.table.body.push([{ text: e.nome, fontSize: 10, alignment: 'left' },
                        	{ text: e.matricula, fontSize: 10, alignment: 'center' },
                        	{ text: e.empresa.nomeFilial, fontSize: 10, alignment: 'left' },
                        	{ text: ''}]);
                    });
	                
	                docDefinition.content.push(lista);
            	
            	var assinaturas = {
        				style: 'tableBottom',
        				table: {
        				    widths: '*',
        					body: [
        						['__________________________________________________________', '__________________________________________________________'],
        						[{text: 'temp', style: 'responsavel', alignment: 'center'}, {text: $scope.treinamentoSugerido.instrutor, style: 'responsavel', alignment: 'center'}],
        						[{text: 'Empresa Responsável', style: 'responsavelLabel', alignment: 'center'}, {text: 'Instrutor Responsável', style: 'responsavelLabel', alignment: 'center'}]
        					]
        				},
        				layout: 'noBorders'
        			};
            	
            	docDefinition.content.push(assinaturas);
                
            
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
        				margin: [0, 0, 0, 50]
        			},
        			tableMiddle: {
        				margin: [0, 50, 0, 50]
        			},
        			tableBottom: {
        				margin: [0, 120, 0, 0],
        				alignment: 'center'
        			},
        			tableHeader: {
        				bold: true,
        				fontSize: 13,
        				color: 'black'
        			},
        			responsavel: {
        				bold: true,
        				fontSize: 18,
        				color: 'black'
        			},
        			responsavelLabel: {
        				bold: false,
        				italics: true,
        				fontSize: 18,
        				color: 'black'
        			},
        			titulo: {
        			    bold: true,
        			    fontSize:45,
        			    margin: [100, 20, 0, 0]
        			}
        		};
            
            docDefinition.defaultStyle = {
        		alignment: 'justify'
        	};
            return docDefinition;
        }
        
    }

})();