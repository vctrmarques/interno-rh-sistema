(function () {
    'use strict';
    angular.module('app.page')
        .controller('requisicaoPessoalFormCtrl', ['$scope', '$http', 'configValue', '$timeout', '$mdDialog', '$q', 'FileUploader', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', requisicaoPessoalFormCtrl]);

    function requisicaoPessoalFormCtrl($scope, $http, configValue, $timeout, $mdDialog, $q, FileUploader, $mdToast, $location, $state, $rootScope, GenericoService, EnumService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {}
                },
                function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }
        $scope.acessaTela();
        EnumService.Get("TipoContratacaoEnum").then(function (dados) {
            $scope.list.tipoContratacao = dados;
        });

        EnumService.Get("SituacaoRequisicaoPessoalEnum").then(function (dados) {
            $scope.list.situacoes = dados;
        });

        $scope.detalhes = false;
        $scope.funcaoesView = [];
        $scope.requisicaoPessoal = {
            "funcoes": []
        };
        $scope.funcaoRequest = {};
        $scope.habilitar = true;
        $scope.aumentoDeQuadro = true;
        $scope.requisicaoPessoal.motivoSolicitacao = "AUMENTO_QUADRO"
        $scope.requisicaoPessoal.situacao = "Aberto"
        $scope.uploader = {
            "headers": null
        }
        $scope.list = {
            "count": 0,
            "data": []
        };
        $scope.list = {
            "turnos": [],
            "funcoes": []
        }

        $scope.funcionarioSolicitanteSelecionado = function () {  
            if ($scope.funcionarioSolicitante) {
                $scope.requisicaoPessoal.solicitanteId = $scope.funcionarioSolicitante.id;
            }
        };

        $scope.funcionarioSubstituidoSelecionado = function () {   
            if ($scope.funcionarioSubstituido) {
                $scope.requisicaoPessoal.funcionarioSubstituidoId = $scope.funcionarioSubstituido.id;
            }            
        };


        $scope.check = function(motivo){
            if(motivo=='SUBSTITUICAO'){
                $scope.aumentoDeQuadro = false;
                $scope.substituicao = true;
                $scope.requisicaoPessoal.motivoSolicitacao = "SUBSTITUICAO";
            }else{
                $scope.aumentoDeQuadro = true;
                $scope.substituicao = false;
                $scope.requisicaoPessoal.motivoSolicitacao = "AUMENTO_QUADRO";
            }    
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/requisicaoPessoal/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.requisicaoPessoal = response.data;
                    console.log($scope.requisicaoPessoal);
                    if(response.data.funcionarioSubstituido)
                        $scope.funcionarioSubstituido = response.data.funcionarioSubstituido;
                    $scope.getRequisicaoDePessoalFuncao($scope.requisicaoPessoal.id);
                    $scope.requisicaoPessoal.dataEntrada = moment(response.data.dataEntrada);
                    $scope.requisicaoPessoal.dataLimite = moment(response.data.dataLimite);
                    $scope.requisicaoPessoal.dataPrevistaAdimissao = moment(response.data.dataPrevistaAdimissao);
                    $scope.funcionarioSolicitante = response.data.solicitante;
                    $scope.check(response.data.motivoSolicitacao);
                    
                } else {
                    $scope.showSimpleToast("Requisição de pessoal não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/requisicaoPessoal/gestao');
        }

        $scope.adicionarFuncao = function(){
            $scope.habilitar = false;
            var exist;
            angular.forEach($scope.funcaoesView, function(e){
                if(e.funcaoId ==  $scope.funcaoRequest.funcaoId){
                    $scope.showSimpleToast("Esta função já conta na requisiçào");
                    exist = true;
                    return;
                }
            });
            if(!exist){
                $scope.funcaoRequest.funcao = $scope.funcao;
                $scope.funcaoRequest.turno = $scope.turno;
                $scope.funcaoesView.push($scope.funcaoRequest);
            } 
            $scope.funcaoRequest = {};           
            $scope.funcao = {};
            $scope.turno = {};    
            setTimeout(function(){$scope.habilitar = true;}, 1000);  
        }

        $scope.removerFuncao = function(id){
            $scope.funcaoesView = $scope.funcaoesView.filter(function (e) {
                return e.funcaoId != id;
            });    
        }

        $scope.save = function (situacao) {
            if(situacao!=null)
             $scope.requisicaoPessoal.situacao = situacao;
            $rootScope.$broadcast('preloader:active');
            $scope.requisicaoPessoal.funcoes = $scope.funcaoesView;
            if ($scope.requisicaoPessoal.id) {
                GenericoService.Update('/requisicaoPessoal', $scope.requisicaoPessoal, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/requisicaoPessoal/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('requisicaoPessoal/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/requisicaoPessoal', $scope.requisicaoPessoal, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/requisicaoPessoal/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/requisicaoPessoal/formulario');
                    }
                });
            }
        }

        $scope.querySearchFuncionario = function (query) {

            var deferred = $q.defer();
            var config = {
                params: {
                    search: query
                }
            }
            if (query) {
                GenericoService.GetAll('/funcionario/searchNomeOrMatricula', config).then(
                    function (response) {
                        if (response.data && response.data.length > 0) {
                            $scope.funcionarios = response.data;
                            deferred.resolve(response.data);
                        }
                    });
            }
            return deferred.promise;
        };


        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                .textContent(textContent)
                .position('top right')
                .hideDelay(3000)
            );
        };

        $scope.getRequisicaoDePessoalFuncao = function (requisicaoPessoalId) {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/requisicoesDePessoalFuncoes/'+requisicaoPessoalId, null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.requisicaoPessoal.funcoes = response.data; 
                        angular.forEach($scope.requisicaoPessoal.funcoes, function(e){
                            e.funcaoId = e.funcao.id;
                            e.turnoId = e.turno.id;
                        });    
                        $scope.funcaoesView = response.data;              
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.funcoes = function () {
            $scope.loadedFuncoes = false;
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/listaFuncoes', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.funcoes = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                    $scope.loadedFuncoes = true;
                });
        }
        $scope.funcoes();

        $scope.funcaoSelecionada = function(funcao){
            $scope.funcao = funcao;        
        }

        $scope.turnoSelecionado = function(turno){
            $scope.turno = turno;        
        }

        $scope.turnos = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/listaTurnos', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.turnos = response.data;
                        console.log($scope.list.turnos);
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }
        $scope.turnos();

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

        $scope.showAlterarSituacao = function (situacao, id) {
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja Alterar a situação desta Requisição?')
                // .ariaLabel('Lucky day')
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.alterarSituacao(situacao, id);
            }, function () {
            });
        };

        $scope.alterarSituacao = function (situacao, id){
            var situacao = situacao;
            GenericoService.Update('/requisicaoPessoal/alterar/'+id, situacao, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/requisicaoPessoal/gestao');
                }
            });
        }

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/requisicaoDePessoalFuncao/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.funcaoesView = $scope.funcaoesView.filter(function (e) {
                        return e.id != $scope.idToDelete;
                    }); 
                }
            });
        }
    }
})();
