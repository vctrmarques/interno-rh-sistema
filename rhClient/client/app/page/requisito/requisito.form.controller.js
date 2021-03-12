(function () {
    'use strict';

    angular.module('app.page')
        .controller('requisitoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', requisitoFormCtrl]);

    function requisitoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
        $scope.myObj = {};

        $scope.getMunicipios = function (uf) {

            $scope.myObj = JSON.parse(uf);
            $scope.getMunicipiosByUf($scope.myObj.id);
        }

        $scope.setLocalResidencia = function(mun){
            var obj = JSON.parse(mun);
            $scope.requisito.valor = obj.regiaoFiscal + ', ' + $scope.myObj.estado;
        }

        $scope.getMunicipiosByUf = function (id) {
            GenericoService.GetAllDropdownById(id, function (response) {
                if (response.status === 200) {
                    $scope.lista.municipio = response.data;
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                }
            });
        }

        $scope.lista = {
            "dadosComparativos": [
                {value:"Idade"},
                {value:"Grau de Instrução"},
                {value:"Local de Residência"},
                {value:"Sexo"},
                {value:"Tempo de Serviço"},
                {value:"Vínculo Empregatício"}
            ],
            "tiposComparacoes": [],
            "sexo": [{value: "Masculino"}, {value: "Feminino"}],
            "grauInstrucao": [
                            {value: "Ensino Médio Completo"},
                            {value: "Ensino Superior Completo"},
                            {value: "Ensino Superior Incompleto"},
                            {value: "Mestrado"},
                            {value: "Doutorado"}
                            ],
            "uf": [],
            "municipio": []
        }

        $scope.getParam = function (parametro) {
            $scope.getTipoComparativoByParametro(parametro);
        }

        $scope.getTipoComparativoByParametro = function (parametro) {
            if(parametro === "Sexo"){
                $scope.lista.tiposComparacoes = [{value: "Igual à"}];
            } else if(parametro === "Idade" || parametro === "Tempo de Serviço"){
                $scope.lista.tiposComparacoes = [{value: "No Mínimo"}, {value: "No Máximo"}, {value: "Intervalo"}];
            } else if(parametro === "Grau de Instrução" || parametro === "Vínculo Empregatício" || parametro === "Local de Residência"){
                $scope.lista.tiposComparacoes = [{value: "Igual à"}, {value: "Diferente de"}];
            } 
        }

        $scope.Uf = function () {
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

        $scope.Uf();

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/requisito/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.requisito = response.data;
                    $scope.getTipoComparativoByParametro($scope.requisito.dadoComparativo)
                } else {
                    $scope.showSimpleToast("Requisito não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/requisito/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.requisito.id) {
                GenericoService.Update('/requisito', $scope.requisito, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/requisito/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('requisito/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/requisito', $scope.requisito, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/requisito/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/requisito/formulario');
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