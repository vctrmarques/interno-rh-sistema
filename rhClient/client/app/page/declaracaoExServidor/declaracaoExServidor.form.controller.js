(function () {
    'use strict';

    angular.module('app.page')
        .controller('declaracaoExServidorFormCtrl', ['$interval', '$state', '$location', '$q', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$rootScope', 'GenericoService', 'EnumService', '$filter', 'FileUploader', '$http', declaracaoExServidorFormCtrl]);

    function declaracaoExServidorFormCtrl($interval, $state, $location, $q, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $rootScope, GenericoService, EnumService, $filter, FileUploader, $http) {

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
        $scope.detalhes = false;
        $scope.dados = {};
        $scope.lista = {};


        // Init Basic
        $scope.declaracaoExServidor = {
            dadosFuncionais: []
        };

        $scope.$on("$destroy", function () {
            if ($scope.loopRascunho) {
                $interval.cancel($scope.loopRascunho);
            }
        });

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        /*Início - Aba Declaração*/

        $scope.querySearchFuncionario = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    GenericoService.GetAll('/funcionario/searchComplete', config).then(
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

        $scope.funcionarioSelecionado = function () {

            if ($scope.funcionario) {
                $scope.declaracaoExServidor.funcionarioId = $scope.funcionario.id;

                if ($scope.funcionario.nomeMae && $scope.funcionario.nomePai) {
                    $scope.filiacao = $scope.funcionario.nomeMae + " e " + $scope.funcionario.nomePai;
                } else if ($scope.funcionario.nomeMae) {
                    $scope.filiacao = $scope.funcionario.nomeMae;
                } else {
                    $scope.filiacao = $scope.funcionario.nomePai;
                }

                if ($scope.funcionario.sexo) {
                    if ($scope.funcionario.sexo == 'F') {
                        $scope.genero = 'Feminino';
                    } else {
                        $scope.genero = 'Masculino';
                    }
                }

                $scope.funcionarioMatricula = $scope.funcionario.matricula + ' - ' + $scope.funcionario.nome;

                $scope.endereco = montaEndereco($scope.funcionario);

                $scope.podeSalvarRascunho = true;
            }
        };

        function montaEndereco(f) {
            var valor = '';

            if (f.logradouro) {
                valor += f.logradouro
            }
            if (f.numero) {
                valor += ', ' + f.numero;
            }
            if (f.bairro) {
                valor += ' - ' + f.bairro;
            }

            return valor;
        }

        $scope.querySearchCargo = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    GenericoService.GetAll('/cargo/searchComplete', config).then(
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

        $scope.cargoSelecionado = function () {
            if ($scope.cargo) {
                $scope.dados.cargo = $scope.cargo;
            }
        }

        $scope.adicionarDadosFuncionais = function () {
            $scope.dados.cargoId = $scope.dados.cargo.id;
            if ($scope.declaracaoExServidor.id) {
                $scope.dados.declaracaoExServidorId = $scope.declaracaoExServidor.id;
            }
            $scope.declaracaoExServidor.dadosFuncionais.push($scope.dados);
            delete $scope.dados;
            delete $scope.cargo;
            $scope.podeSalvarRascunho = true;
        }

        $scope.removerDadosFuncionais = function (item) {
            var index = $scope.declaracaoExServidor.dadosFuncionais.indexOf(item);
            $scope.declaracaoExServidor.dadosFuncionais.splice(index, 1);
            $scope.podeSalvarRascunho = true;
        }

        $scope.responsavelSelecionado = function () {
            if ($scope.responsavel) {
                $scope.declaracaoExServidor.responsavel = $scope.responsavel;
                $scope.declaracaoExServidor.responsavelId = $scope.responsavel.id;
                $scope.podeSalvarRascunho = true;
            }
        }

        $scope.dirigenteSelecionado = function () {
            if ($scope.dirigente) {
                $scope.declaracaoExServidor.dirigente = $scope.dirigente;
                $scope.declaracaoExServidor.dirigenteId = $scope.dirigente.id;
                $scope.podeSalvarRascunho = true;
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
            if (data) {
                var valor = moment(data);
                if (comHora) {
                    return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
                } else {
                    return $filter('date')(new Date(valor), 'dd/MM/yyyy');
                }
            } else {
                return null;
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
            $interval.cancel($scope.loopRascunho);
            $location.path('/declaracaoExServidor/gestao');
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        $scope.GetById = function (id, primeiroAcesso) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/declaracaoExServidor/' + id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    if (primeiroAcesso) {
                        $scope.declaracaoExServidor = response.data;

                        if (!$scope.declaracaoExServidor.dadosFuncionais)
                            $scope.declaracaoExServidor.dadosFuncionais = [];

                        $scope.funcionario = $scope.declaracaoExServidor.funcionario;
                        $scope.funcionarioSelecionado();

                        $scope.responsavel = $scope.declaracaoExServidor.responsavel;
                        $scope.responsavelSelecionado();

                        $scope.dirigente = $scope.declaracaoExServidor.dirigente;
                        $scope.dirigenteSelecionado();
                    }
                    $scope.declaracaoExServidor.rascunho = response.data.rascunho;
                    $scope.salvarComoRascunho = $scope.declaracaoExServidor.rascunho;

                } else {
                    $scope.showSimpleToast("Declaração não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.id)
            $scope.GetById($state.params.id, true);
        else {
            $scope.salvarComoRascunho = true;
            $scope.declaracaoExServidor.rascunho = true;
        }

        $scope.loopRascunho = $interval(function () {
            var podeSalvarRascunho = $scope.podeSalvarRascunho;
            var dirty = $scope.declaracaoExServidorForm.$dirty;
            var rascunho = $scope.declaracaoExServidor.rascunho;

            if ($scope.salvarComoRascunho && podeSalvarRascunho && dirty && rascunho)
                $scope.save(true);

        }, 120000);

        $scope.save = function (salvarRascunho) {

            if (!$scope.declaracaoExServidor.funcionarioId) {
                $scope.showSimpleToast('Preencha os dados do funcionário.');
                return;
            }

            $scope.declaracaoExServidor.rascunho = salvarRascunho || $scope.salvarComoRascunho ? true : false;

            $rootScope.$broadcast('preloader:active');
            if ($scope.declaracaoExServidor.id) {
                GenericoService.Update('/declaracaoExServidor', $scope.declaracaoExServidor, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        if (salvarRascunho) {
                            $scope.podeSalvarRascunho = false;
                            $scope.showSimpleToast('Rascunho salvo');
                            $scope.GetById(response.data.obj.id, false);
                        } else {
                            $scope.showSimpleToast(response.data.message);
                            $scope.goBack();
                        }
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('declaracaoExServidor/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/declaracaoExServidor', $scope.declaracaoExServidor, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        if (salvarRascunho) {
                            $scope.podeSalvarRascunho = false;
                            $scope.showSimpleToast('Rascunho salvo');
                            $scope.GetById(response.data.obj.id, false);
                        } else {
                            $scope.showSimpleToast(response.data.message);
                            $scope.goBack();
                        }
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/declaracaoExServidor/formulario');
                    }
                });
            }
        }
    }

})();