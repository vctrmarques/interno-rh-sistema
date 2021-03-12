(function () {
    'use strict';

    angular.module('app.page')
        .controller('funcaoFormCtrl', ['$q', '$scope', '$mdToast', '$location', '$mdDialog', '$state', '$rootScope', 'GenericoService', 'EnumService', 'RestService', funcaoFormCtrl]);

    function funcaoFormCtrl($q, $scope, $mdToast, $location, $mdDialog, $state, $rootScope, GenericoService, EnumService, RestService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                    }
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }

        $scope.acessaTela();
        $scope.detalhes = false;
        $scope.extinguir = false;
        $scope.vinculos = [];
        var i = 0;

        if ($state.params && $state.params.id) {
            $scope.funcao = { vinculosIds: [], cursosIds: [], verbasIds: [], vinculosIds: [], requisitosIds: [], habilidadesIds: [], atividadesIds: [] };
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcao/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcao = response.data;
                    $scope.funcao.vinculosIds = [];
                    $scope.funcao.cursosIds = [];
                    $scope.funcao.verbasIds = [];
                    $scope.funcao.vinculosIds = [],
                        $scope.funcao.requisitosIds = [];
                    $scope.funcao.habilidadesIds = [];
                    $scope.funcao.atividadesIds = [];

                    if ($scope.funcao.dataCriacao) {
                        $scope.funcao.dataCriacao = moment(response.data.dataCriacao);
                    }

                    if ($scope.funcao.dataExtincao) {
                        $scope.funcao.dataExtincao = moment(response.data.dataExtincao);
                    }

                    if ($scope.funcao.dataLei) {
                        $scope.funcao.dataLei = moment(response.data.dataLei);
                    }

                    if ($scope.funcao.faixaSalarial) {
                        $scope.funcao.faixaSalarialId = $scope.funcao.faixaSalarial.id;
                    }

                    $scope.vinculos = $scope.funcao.vinculos;

                    for (i = 0; i < $scope.funcao.vinculos.length; i++) {
                        $scope.funcao.vinculosIds.push($scope.funcao.vinculos[i].id)
                    }

                    $scope.cursos = $scope.funcao.cursos;
                    console.log(response.data);

                    for (i = 0; i < $scope.funcao.cursos.length; i++) {
                        $scope.funcao.cursosIds.push($scope.funcao.cursos[i].id)
                    }

                    for (i = 0; i < $scope.funcao.verbas.length; i++) {
                        $scope.funcao.verbasIds.push($scope.funcao.verbas[i].id)
                    }

                    for (i = 0; i < $scope.funcao.vinculos.length; i++) {
                        $scope.funcao.vinculosIds.push($scope.funcao.vinculos[i].id)
                    }

                    for (i = 0; i < $scope.funcao.requisitos.length; i++) {
                        $scope.funcao.requisitosIds.push($scope.funcao.requisitos[i].id)
                    }

                    $scope.habilidades = $scope.funcao.habilidades;

                    for (i = 0; i < $scope.funcao.habilidades.length; i++) {
                        $scope.funcao.habilidadesIds.push($scope.funcao.habilidades[i].id)
                    }

                    $scope.atividades = $scope.funcao.atividades;

                    for (i = 0; i < $scope.funcao.atividades.length; i++) {
                        $scope.funcao.atividadesIds.push($scope.funcao.atividades[i].id)
                    }

                    if (response.data.centroCusto) {
                        $scope.funcao.centroCustoId = response.data.centroCusto.id;
                    }
                    console.log(response.data);

                    if ($scope.funcao.grupoSalarialId) {
                        $scope.faixaSalarial($scope.funcao.grupoSalarialId);
                    }



                } else {
                    $scope.showSimpleToast("Natureza da Função não encontrado na base");
                }
            });
        } else {
            $scope.funcao = { vinculosIds: [], cursosIds: [], verbasIds: [], vinculosIds: [], habilidadesIds: [], atividadesIds: [], requisitosIds: [] };
        }

        $scope.list = {
            "vinculo": [],
            "naturezaFuncao": [],
            "classeSalarial": [],
            "grupoSalarial": [],
            "curso": [],
            "requisitos": [],
            "habilidade": [],
            "atividade": [],
            "categoriaProfissional": [],
            "processoFuncao": []

        }

        EnumService.Get("MotivoLeiEnum").then(function (dados) {
            $scope.motivoLeiTemp = dados
            $scope.list.motivoLei = dados;
        });

        EnumService.Get("FuncaoAcumulavelEnum").then(function (dados) {
            $scope.list.funcaoAcumulavel = dados;
        });

        EnumService.Get("ContagemTempoEspecialEnum").then(function (dados) {
            $scope.list.contagemTempoEspecial = dados;
        });

        GenericoService.GetAll('/centrosCustos')
            .then(
                function (response) {
                    if (response.status === 200) {
                        $scope.centros = response.data.content;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });

        $scope.Vinculo = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaVinculos', function (response) {
                if (response.status === 200) {
                    $scope.list.vinculo = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.NaturezaFuncao = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaNaturezaFuncoes', function (response) {
                if (response.status === 200) {
                    $scope.list.naturezaFuncao = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.CategoriaProfissional = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCategoriasProfissionais', function (response) {
                if (response.status === 200) {
                    $scope.list.categoriaProfissional = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.ProcessoFuncao = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaProcessosFuncoes', function (response) {
                if (response.status === 200) {
                    $scope.list.processoFuncao = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.carregarFaixasSalariais = function () {
            $scope.faixaSalarial($scope.funcao.grupoSalarialId);
        }

        $scope.faixaSalarial = function (grupoId) {
            if (grupoId) {
                $rootScope.$broadcast('preloader:active');
                GenericoService.GetAllDropdown('faixasSalariais/porGrupo/' + grupoId, function (response) {
                    if (response.status === 200) {
                        $scope.faixasSalariais = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else if (response.status === 500) {
                        $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                    }
                });
            }
        }

        $scope.accordion = function (id) {
            var accordions = document.getElementById(id);

            var content = accordions.nextElementSibling;
            if (content.style.maxHeight) {
                accordions.classList.remove("is-open");
                content.style.maxHeight = null;
                content.style.paddingTop = null;
            } else {
                accordions.classList.add("is-open");
                content.style.maxHeight = content.scrollHeight + "px";
                content.style.paddingTop = "5px";
            }
        };

        // $scope.ClasseSalarial = function (grupoId) {
        //     if (grupoId) {
        //         $scope.list.classeSalarial = [];
        //         $rootScope.$broadcast('preloader:active');
        //         GenericoService.GetAllDropdown('listaClassesSalariais/grupo/' + grupoId, function (response) {
        //             if (response.status === 200) {
        //                 $scope.list.classeSalarial = response.data;
        //                 console.log($scope.list.classeSalarial);

        //                 $rootScope.$broadcast('preloader:hide');
        //             } else if (response.status === 500) {
        //                 $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
        //             }
        //         });
        //     }
        // }

        $scope.GrupoSalarial = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaGruposSalariais', function (response) {
                if (response.status === 200) {
                    $scope.list.grupoSalarial = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.Curso = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('cursos', function (response) {
                if (response.status === 200) {
                    $scope.list.curso = response.data.content;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.requisito = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('requisitos', function (response) {
                if (response.status === 200) {
                    $scope.list.requisitos = response.data.content;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.requisito();

        $scope.verbas = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('verbas', function (response) {
                if (response.status === 200) {
                    $scope.list.verbas = response.data.content;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.verbas();

        $scope.Habilidade = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('habilidades', function (response) {
                if (response.status === 200) {
                    $scope.list.habilidade = response.data.content;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.Atividade = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('atividades', function (response) {
                if (response.status === 200) {
                    $scope.list.atividade = response.data.content;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.filtrarCursos = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    classeSalarialId: $scope.funcao.classeSalarialId,
                    grupoSalarialId: $scope.funcao.grupoSalarialId
                }
            };

            $scope.promise = GenericoService.GetAll('/filtrarCursos', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.curso = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.showDialog = function (ev, id) {

            $mdDialog.show({
                //locals: { af: $scope.areaFormacao },
                require: { container: '^^funcaoFormCtrl' },
                controller: ['$scope', 'GenericoService', '$q', function ($scope, GenericoService, $q) {

                    $scope.filtro = { areasFormacao: [], grausAcademicos: [] };
                    $scope.lista = { areasFormacao: [], grausAcademicos: [] };

                    $scope.clickDialog = function () {
                        window.sessionStorage.setItem('filtros', JSON.stringify($scope.filtro));
                        $mdDialog.cancel();
                    }

                    $scope.areaFormacao = function () {
                        GenericoService.GetAllDropdown('areasFormacao', function (response) {
                            if (response.status === 200) {
                                $scope.lista.areasFormacao = response.data.content;
                            } else {
                                $scope.showSimpleToast("Não foi possível carregar os dados.")
                            }
                        });
                    }

                    $scope.grauAcademico = function () {
                        GenericoService.GetAllDropdown('grausAcademicos', function (response) {
                            if (response.status === 200) {
                                $scope.lista.grausAcademicos = response.data.content;
                            } else {
                                $scope.showSimpleToast("Não foi possível carregar os dados.")
                            }
                        });
                    }

                    function indexOf(item, list) {
                        if (!item || !list) {
                            return -1;
                        }
                        for (var i = 0; i < list.length; i++) {
                            var item2 = list[i];
                            if (item2.id == item.id) {
                                return i;
                            }
                        }
                        return -1;
                    }

                    $scope.toggle = function (item, list) {
                        var idx = indexOf(item, list);

                        if (idx > -1) {
                            list.splice(idx, 1);
                        } else {
                            list.push(item);
                        }
                    };

                    $scope.exists = function (item, list) {
                        return indexOf(item, list) > -1;
                    };

                    $scope.isIndeterminate = function (list, list2) {
                        return (list2 && list) && (list2 && list2.length !== 0 && list2.length !== list.length);
                    };

                    $scope.isChecked = function (list, list2) {
                        return (list2 && list) && (list2.length === list.length);
                    };

                    $scope.toggleAll = function (list, list2) {
                        if (list2.length === list.length) {
                            if (list2[0].areaFormacao) {
                                $scope.filtro.areasFormacao = [];
                            }
                            if (list2[0].nome) {
                                $scope.filtro.grausAcademicos = [];
                            }
                        } else if (list2.length === 0 || list2.length > 0) {
                            if (list[0].areaFormacao) {
                                $scope.filtro.areasFormacao = list.slice(0);
                            }
                            if (list[0].nome) {
                                $scope.filtro.grausAcademicos = list.slice(0);
                            }
                        }
                    };

                    $scope.areaFormacao();
                    $scope.grauAcademico();

                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/funcao/dialog1.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
            });

        }

        $scope.Vinculo();
        $scope.NaturezaFuncao();
        $scope.CategoriaProfissional();
        $scope.ProcessoFuncao();
        // $scope.ClasseSalarial();
        $scope.GrupoSalarial();
        $scope.Curso();
        $scope.Habilidade();
        $scope.Atividade();

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.extinguirFuncao = function () {
            $scope.list.motivoLei = [];
            if ($scope.extinguir) {
                $scope.funcao.motivoLei = null;
                $scope.extinguir = false;
                $scope.list.motivoLei = $scope.motivoLeiTemp;
            } else {
                $scope.extinguir = true;
                $scope.list.motivoLei.push({ value: "EXTINCAO", label: "Extinção" });
                $scope.funcao.motivoLei = "EXTINCAO";
            }
        }

        $scope.confirmarExtincaoFuncao = function () {
            GenericoService.Update('/extinguirFuncao', $scope.funcao, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/funcao/gestao');
                }
            });
        }

        $scope.goBack = function () {
            $location.path('/funcao/gestao');
        }

        $scope.addItem = function () {
            $scope.vinc = JSON.parse($scope.vinculo);

            var naoExiste = true;

            for (var index = 0; index < $scope.vinculos.length; index++) {
                var element = $scope.vinculos[index];
                if (element.id === $scope.vinc.id) {
                    naoExiste = false;
                    break;
                }
            }

            if (naoExiste) {
                $scope.vinculos.push($scope.vinc);
                $scope.funcao.vinculosIds.push($scope.vinc.id)
            }

            $scope.vinculo = undefined;

        }

        $scope.filtrarFaixaSalarial = function () {

            var grupoSalarialId = $scope.funcao.grupoSalarialId;
            var classeSalarialId = $scope.funcao.classeSalarialId;

            if (grupoSalarialId && classeSalarialId) {

                $rootScope.$broadcast('preloader:active');

                var config = {
                    params: {
                        grupoSalarialId: grupoSalarialId,
                        classeSalarialId: classeSalarialId
                    }
                };

                $scope.funcao.faixaSalarial = null;
                $scope.funcao.faixaSalarialId = null;

                GenericoService.Get('/faixaSalarial', config, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.funcao.faixaSalarial = response.data;
                        $scope.funcao.faixaSalarialId = $scope.funcao.faixaSalarial.id;
                    } else if (response.status == 404) {
                        $scope.showSimpleToast("Não existe faixa salarial para o grupo e classes selecionados.");
                    }
                });

            }

        }

        $scope.removeItem = function (index) {
            $scope.vinculos.splice(index, 1);
            $scope.funcao.vinculosIds.splice(index, 1);
        }

        $scope.save = function () {
            console.log($scope.teste);

            $rootScope.$broadcast('preloader:active');

            if ($scope.funcao.cbo) {
                $scope.funcao.cboId = $scope.funcao.cbo.id;
            }

            if ($scope.funcao.id) {
                GenericoService.Update('/funcao', $scope.funcao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/funcao/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('funcao/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/funcao', $scope.funcao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/funcao/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/funcao/formulario');
                    }
                });
            }
        }

        $scope.querySearch = function (query) {
            var deferred = $q.defer();
            var config = { params: { nome: query } };
            RestService.Get('/cbo/search', config)
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
            return deferred.promise;
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

    }

})(); 