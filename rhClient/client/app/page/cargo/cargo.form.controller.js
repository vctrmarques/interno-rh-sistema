(function () {
    'use strict';

    angular.module('app.page')
        .controller('cargoFormCtrl', ['$scope', '$q', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', cargoFormCtrl]);

    function cargoFormCtrl($scope, $q, $mdToast, $location, $state, $rootScope, GenericoService) {

        $scope.cargo = {
            vinculosIds: [],
            atividadesIds: [],
            habilidadesIds: [],
            cursosIds: [],
            verbasIds: []
        }

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

        $scope.carregarFaixasSalariais = function () {
            $scope.faixaSalarial($scope.cargo.grupoSalarialId);
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

        $scope.acessaTela();
        $scope.detalhes = false;

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/cargo/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.cargo = response.data;

                    if (response.data.atividadesIds) {
                        $scope.cargo.atividadesIds = response.data.atividadesIds;
                    } else {
                        $scope.cargo.atividadesIds = [];
                    }

                    if (response.data.habilidadesIds) {
                        $scope.cargo.habilidadesIds = response.data.habilidadesIds;
                    } else {
                        $scope.cargo.habilidadesIds = [];
                    }

                    if (response.data.cursosIds) {
                        $scope.cargo.cursosIds = response.data.cursosIds;
                    } else {
                        $scope.cargo.cursosIds = [];
                    }                    

                    if (response.data.verbasIds) {
                        $scope.cargo.verbasIds = response.data.verbasIds;
                    } else {
                        $scope.cargo.verbasIds = [];
                    }
                    
                    if($scope.cargo.grupoSalarial){
                        $scope.cargo.grupoSalarialId = $scope.cargo.grupoSalarial.id;
                        $scope.faixaSalarial($scope.cargo.grupoSalarial.id);
                    }

                    $scope.cargo.vinculosIds = [];
                    var index;
                    for (index = 0; index < $scope.cargo.vinculos.length; index++) {
                        var element = $scope.cargo.vinculos[index];
                        $scope.cargo.vinculosIds.push(element.id);
                    }

                } else {
                    $scope.showSimpleToast("Cargo não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/cargo/gestao');
        }

        $scope.contagemTempoEspecialList = [
            { label: 'Não', value: 'NAO' },
            { label: 'Professor', value: 'PROFESSOR' },
            { label: 'Atividade de Risco', value: 'ATIVIDADE_RISCO' }
        ];

        $scope.motivoLeiList = [
            { label: 'Criação', value: 'CRIACAO' },
            { label: 'Extinção', value: 'EXTINCAO' },
            { label: 'Reestruturação', value: 'REESTRUTURACAO' }
        ];

        $scope.list = {
            "classeSalarial": [],
            "grupoSalarial": []
        }

        $scope.ClasseSalarial = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaClassesSalariais', function (response) {
                if (response.status === 200) {
                    $scope.list.classeSalarial = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.ClasseSalarial();

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
        $scope.GrupoSalarial();


        GenericoService.GetAll('/listaVinculos').then(
            function (response) {
                if (response.data && response.data.length > 0) {
                    $scope.vinculos = response.data;
                }
            });

        GenericoService.GetAll('/listaAtividades').then(
            function (response) {
                if (response.data && response.data.length > 0) {
                    $scope.atividades = response.data;
                }
            });

        GenericoService.GetAll('/listaHabilidades').then(
            function (response) {
                if (response.data && response.data.length > 0) {
                    $scope.habilidades = response.data;
                }
            });

        GenericoService.GetAll('/listaCursos').then(
            function (response) {
                if (response.data && response.data.length > 0) {
                    $scope.cursos = response.data;
                }
            });

            GenericoService.GetAll('/listaVerbas').then(
                function (response) {
                    console.log(response.data);
                    
                    if (response.data && response.data.length > 0) {
                        $scope.verbas = response.data;
                    }
                });

        $scope.nomeCursoBusca = "";
        $scope.descricaoAtivBusca = "";
        $scope.descricaoHabBusca = "";


        $scope.loadCursos = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    nomeCurso: $scope.nomeCursoBusca
                }
            };

            $scope.promise = GenericoService.GetAll('/cursos', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.cursos = response.data.content;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                }
            );
        }

        $scope.loadAtividades = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    descricao: $scope.descricaoAtivBusca
                }
            };

            $scope.promise = GenericoService.GetAll('/atividades', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.atividades = response.data.content;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                }
            );
        }

        $scope.loadHabilidades = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    descricao: $scope.descricaoHabBusca
                }
            };

            $scope.promise = GenericoService.GetAll('/habilidades', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.habilidades = response.data.content;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                }
            );
        }

        $scope.querySearch = function (query, resourcePath) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll(resourcePath + '/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            if ($scope.cargo.cbo)
                $scope.cargo.cboId = $scope.cargo.cbo.id;

            if ($scope.cargo.naturezaFuncao)
                $scope.cargo.naturezaFuncaoId = $scope.cargo.naturezaFuncao.id;

            if ($scope.cargo.processoFuncao)
                $scope.cargo.processoFuncaoId = $scope.cargo.processoFuncao.id;

            if ($scope.cargo.categoriaProfissional)
                $scope.cargo.categoriaProfissionalId = $scope.cargo.categoriaProfissional.id;

            if ($scope.cargo.sindicato)
                $scope.cargo.sindicatoId = $scope.cargo.sindicato.id;

            if ($scope.cargo.id) {
                GenericoService.Update('/cargo', $scope.cargo, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/cargo/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('cargo/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/cargo', $scope.cargo, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/cargo/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/cargo/formulario');
                    }
                });

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

    }

})(); 