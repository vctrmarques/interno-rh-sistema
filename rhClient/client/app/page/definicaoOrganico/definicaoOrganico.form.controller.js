(function () {
    'use strict';

    angular.module('app.page')
        .controller('definicaoOrganicoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', '$q', '$mdDialog', '$mdMedia', 'GenericoService', definicaoOrganicoFormCtrl]);

    function definicaoOrganicoFormCtrl($scope, $mdToast, $location, $state, $rootScope, $q, $mdDialog, $mdMedia, GenericoService) {

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

        $scope.lotacoes = [];
        $scope.funcoes = [];
        $scope.cargos = [];
        $scope.list ={
            "lotacao": [],
            "funcao": [],
            "cargo": []
        }     
        $scope.empresaFilial;
        $scope.selectRadioButton = "Avisar";

        $scope.definicaoOrganico = { lotacoes: []};

        $scope.acessaTela();
        $scope.detalhes = false;

        $scope.calcularFuncionarios = function () {
            var previsaoFuncionarios = $scope.definicaoOrganico.previsaoFuncionarios;
            var funcionariosAtuais = $scope.definicaoOrganico.funcionariosAtuais;
  
            $scope.definicaoOrganico.totalFuncionarios = previsaoFuncionarios - funcionariosAtuais;
        };
        
        $scope.calcularCustos = function () {
            var previsaoCustos = $scope.definicaoOrganico.previsaoCustos;
            var custosAtuais = $scope.definicaoOrganico.custosAtuais;
  
            $scope.definicaoOrganico.custoTotal = previsaoCustos - custosAtuais;
        };          

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/empresaFilial/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.empresaFilial = response.data;
                    $scope.definicaoOrganico.empresaFilialId = response.data.id;
                } else {
                    $scope.showSimpleToast("Empresa Filial não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.empresaFilialId && $state.params.editar){
            GenericoService.GetById('/definicaoOrganico/' + $state.params.empresaFilialId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {                    
                    
                    $scope.funcionario = response.data.funcionario;
                    $scope.funcionarioSelecionado();

                    $scope.definicaoOrganico = response.data;
                    $scope.empresaFilial = response.data.empresaFilial;

                    $scope.lotacoes = $scope.definicaoOrganico.lotacoes;

                    for(var i = 0; i < $scope.lotacoes.length; i++){
                        if(!$scope.definicaoOrganico.lotacoes[i].cargos){
                            $scope.definicaoOrganico.lotacoes[i].cargos = [];
                        }
                        
                        if(!$scope.definicaoOrganico.lotacoes[i].funcoes){
                            $scope.definicaoOrganico.lotacoes[i].funcoes = [];
                        }
                    }
                    $scope.definicaoOrganico.vigenciaInicial = moment($scope.definicaoOrganico.vigenciaInicial);
                    $scope.definicaoOrganico.vigenciaFinal = moment($scope.definicaoOrganico.vigenciaFinal);
                    $scope.definicaoOrganico.empresaFilialId = response.data.empresaFilial.id;
                } else {
                    $scope.showSimpleToast("Empresa Filial não encontrada na base");
                }
            });
        }


        if ($state.params && $state.params.empresaFilialId && $state.params.detalhes){
            $scope.detalhes = true;
            GenericoService.GetById('/definicaoOrganico/' + $state.params.empresaFilialId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {                    

                    $scope.funcionario = response.data.funcionario;
                    $scope.funcionarioSelecionado();

                    $scope.definicaoOrganico = response.data;
                    $scope.lotacoes = $scope.definicaoOrganico.lotacoes;

                    for(var i = 0; i < $scope.lotacoes.length; i++){
                        if(!$scope.definicaoOrganico.lotacoes[i].cargos){
                            $scope.definicaoOrganico.lotacoes[i].cargos = [];
                        }
                        
                        if(!$scope.definicaoOrganico.lotacoes[i].funcoes){
                            $scope.definicaoOrganico.lotacoes[i].funcoes = [];
                        }
                    }

                    $scope.definicaoOrganico.vigenciaInicial = moment($scope.definicaoOrganico.vigenciaInicial);
                    $scope.definicaoOrganico.vigenciaFinal = moment($scope.definicaoOrganico.vigenciaFinal);
                    $scope.definicaoOrganico.empresaFilialId = response.data.empresaFilial.id;
                } else {
                    $scope.showSimpleToast("Empresa Filial não encontrada na base");
                }
            });
        }        

        $scope.querySearchFuncionario = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/funcionario/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.funcionarios = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        } 
        
        $scope.funcionarioSelecionado = function() {
            if($scope.funcionario){
                $scope.definicaoOrganico.funcionarioId = $scope.funcionario.id;
                $scope.definicaoOrganico.funcionarioNome = $scope.funcionario.nome;
                $scope.definicaoOrganico.emailResponsavel = $scope.funcionario.emailPessoal;
            } 
        }        

        $scope.querySearchLotacao = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/lotacao/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        } 
        
        $scope.lotacaoSelecionada = function() {
            if($scope.lotacao){
                $scope.lotacoes.push($scope.lotacao);
                $scope.definicaoOrganico.lotacoes.push($scope.lotacao)
                delete $scope.lotacao;
            } 
        }

        $scope.removeLotacao = function(index){
            $scope.lotacoes.splice(index,1);

            delete $scope.definicaoOrganico.lotacoes[index].funcoes;
            delete $scope.definicaoOrganico.lotacoes[index].cargos;

            $scope.definicaoOrganico.lotacoes.splice(index,1);
        }        

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/definicaoOrganico/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            if($scope.selectRadioButton == 'Avisar'){
                $scope.definicaoOrganico.confCriticaAvisar = true;
            }else if($scope.selectRadioButton == 'Criticar'){
                $scope.definicaoOrganico.confCriticaCriticar = true;
            }else if($scope.selectRadioButton == 'Nenhum'){
                $scope.definicaoOrganico.confCriticaNenhum = true;
            }

            if ($scope.definicaoOrganico.id) {
                GenericoService.Update('/definicaoOrganico', $scope.definicaoOrganico, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/definicaoOrganico/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
            } else {
                GenericoService.Create('/definicaoOrganico', $scope.definicaoOrganico, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/definicaoOrganico/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/definicaoOrganico/formulario');
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

        $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
        $scope.showDialogFuncao = function (index) {

            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && $scope.customFullscreen;
            $scope.index = index;
            $mdDialog.show({
                scope: $scope,
                preserveScope:true,
                controller: ['$scope', '$q', '$mdDialog', function ($scope, $q, $mdDialog) {
                    delete $scope.funcao;
                    delete $scope.item;   
                    delete $scope.itens;
                    delete $scope.cargo;
                    $scope.clickDialog = function () {
                        $mdDialog.cancel();
                    }
            
                    $scope.querySearchFuncao = function (query) {
                        var deferred = $q.defer();
                        var config = { params: { search: query } }
                        GenericoService.GetAll('/funcao/searchComplete', config).then(
                            function (response) {
                                if (response.data && response.data.length > 0) {
                                    $scope.itens = response.data;
                                    deferred.resolve(response.data);
                                }
                            });
                        return deferred.promise;
                    }
            
                    $scope.funcaoSelecionada = function() {
                        if($scope.funcao){

                            $scope.funcaoTemp = {};
                            $scope.funcaoTemp.empresaFilialId = $scope.definicaoOrganico.empresaFilialId;
                            $scope.funcaoTemp.lotacaoId = $scope.definicaoOrganico.lotacoes[$scope.index].id;
                            $scope.funcaoTemp.funcaoId = $scope.funcao.id;
                            $scope.funcaoTemp.quantPermitido = $scope.funcao.quantPermitido;
                            $scope.funcaoTemp.nome = $scope.funcao.nome;
                        
                            $scope.funcoes.push($scope.funcaoTemp);
                            if(!$scope.definicaoOrganico.lotacoes[$scope.index].funcoes){
                                $scope.definicaoOrganico.lotacoes[$scope.index].funcoes = [];
                            }
                            $scope.definicaoOrganico.lotacoes[$scope.index].funcoes.push($scope.funcaoTemp)
                            delete $scope.funcao;
                            delete $scope.item;                            
                        } 
                    }   
                    
                    $scope.removeFuncao = function(index){
                        $scope.funcoes.splice(index,1);
                        $scope.definicaoOrganico.lotacoes[$scope.index].funcoes.splice(index,1);
                    }
            
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/definicaoOrganico/modalFuncoes.tmpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: useFullScreen
            });

        }      
        
        $scope.showDialogCargo = function (index) {

            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && $scope.customFullscreen;
            $scope.index = index;
            $mdDialog.show({
                scope: $scope,
                preserveScope:true,
                controller: ['$scope', '$q', '$mdDialog', function ($scope, $q, $mdDialog) {
                    delete $scope.cargo;
                    delete $scope.item;
                    delete $scope.itens;
                    delete $scope.funcao;
                    $scope.clickDialog = function () {
                        $mdDialog.cancel();
                    }
        
                    $scope.querySearchCargo = function (query) {
                        var deferred = $q.defer();
                        var config = { params: { search: query } }
                        GenericoService.GetAll('/cargo/searchComplete', config).then(
                            function (response) {
                                if (response.data && response.data.length > 0) {
                                    $scope.itens = response.data;
                                    deferred.resolve(response.data);
                                }
                            });
                        return deferred.promise;
                    }
        
                    $scope.cargoSelecionado = function() {
                        if($scope.cargo){
                            $scope.cargoTemp = {};
                            $scope.cargoTemp.empresaFilialId = $scope.definicaoOrganico.empresaFilialId;
                            $scope.cargoTemp.lotacaoId = $scope.definicaoOrganico.lotacoes[$scope.index].id;
                            $scope.cargoTemp.cargoId = $scope.cargo.id;
                            $scope.cargoTemp.quantPermitido = $scope.cargo.quantPermitido;
                            $scope.cargoTemp.nome = $scope.cargo.nome;
                        
                            $scope.cargos.push($scope.cargoTemp);       
                            if(!$scope.definicaoOrganico.lotacoes[$scope.index].cargos){
                                $scope.definicaoOrganico.lotacoes[$scope.index].cargos = [];
                            }                     
                            $scope.definicaoOrganico.lotacoes[$scope.index].cargos.push($scope.cargoTemp)
                            delete $scope.cargo;
                            delete $scope.item;
                        } 
                    }   
                    
                    $scope.removeCargo = function(index){
                        $scope.cargos.splice(index,1);
                        $scope.definicaoOrganico.lotacoes[$scope.index].cargos.splice(index,1);
                    }
        
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/definicaoOrganico/modalCargos.tmpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: useFullScreen
            });

        }         

    }   

})(); 