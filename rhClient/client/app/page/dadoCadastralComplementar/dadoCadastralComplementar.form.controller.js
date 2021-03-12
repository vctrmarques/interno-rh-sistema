(function () {
    'use strict';

    angular.module('app.page')
        .controller('dadoCadastralComplFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'UsuarioService', dadoCadastralComplFormCtrl]);

    function dadoCadastralComplFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService, UsuarioService) {

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

        if ($state.params && $state.params.id) {
            localStorage.setItem('idFuncionario', JSON.stringify($state.params.id));
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/dadoCadastralCompl/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.dadoCadastralCompl = response.data;

                    if ($scope.dadoCadastralCompl.id) {
                        $scope.dadoCadastralCompl.dataAposentadoria = moment(response.data.dataAposentadoria);


                        if (response.data.previdenciaEspecial === true)
                            $scope.dadoCadastralCompl.previdenciaEspecial = true;
                        else
                            $scope.dadoCadastralCompl.previdenciaEspecial = false;

                        if (response.data.emProcessoDeAposentadoria === true)
                            $scope.dadoCadastralCompl.emProcessoDeAposentadoria = true;
                        else
                            $scope.dadoCadastralCompl.emProcessoDeAposentadoria = false;

                        if (response.data.dataInicioDeficiencia)
                            $scope.dadoCadastralCompl.dataInicioDeficiencia = moment(response.data.dataInicioDeficiencia);

                        if (response.data.dataFimDeficiencia)
                            $scope.dadoCadastralCompl.dataFimDeficiencia = moment(response.data.dataFimDeficiencia);

                        if (response.data.dataDistribuicaoCedencia)
                            $scope.dadoCadastralCompl.dataDistribuicaoCedencia = moment(response.data.dataDistribuicaoCedencia);

                        if (response.data.dataFalecimento)
                            $scope.dadoCadastralCompl.dataFalecimento = moment(response.data.dataFalecimento);

                        if (response.data.dataInicialIsencaoIr)
                            $scope.dadoCadastralCompl.dataInicialIsencaoIr = moment(response.data.dataInicialIsencaoIr);

                        if (response.data.dataFinalIsencaoIr)
                            $scope.dadoCadastralCompl.dataFinalIsencaoIr = moment(response.data.dataFinalIsencaoIr);

                        if (response.data.dataInicialIsencaoPrevidencia)
                            $scope.dadoCadastralCompl.dataInicialIsencaoPrevidencia = moment(response.data.dataInicialIsencaoPrevidencia);

                        if (response.data.dataFinalIsencaoPrevidencia)
                            $scope.dadoCadastralCompl.dataFinalIsencaoPrevidencia = moment(response.data.dataFinalIsencaoPrevidencia);

                    }
                } else {
                    $location.path('/dadoCadastralComplementar/formulario');
                }
            });

        }

        $scope.changeInsecoesPrev = function () {
            if ($scope.dadoCadastralCompl.dataInicialIsencaoPrevidencia || $scope.dadoCadastralCompl.dataFinalIsencaoPrevidencia) {
                $scope.dadoCadastralCompl.previdenciaEspecial = false;
            }
        }

        $scope.changePrevidenciEspecial = function () {
            if ($scope.dadoCadastralCompl.previdenciaEspecial) {
                $scope.dadoCadastralCompl.dataInicialIsencaoPrevidencia = null;
                $scope.dadoCadastralCompl.dataFinalIsencaoPrevidencia = null;
            }
        }

        $scope.emProcessoDeAposentadoria = function () {
            return $scope.dadoCadastralCompl && $scope.dadoCadastralCompl.emProcessoDeAposentadoria ? $scope.dadoCadastralCompl.emProcessoDeAposentadoria : false;
        }

        $scope.list = {

            "fardamento": [
                { value: "PP", label: "PP" },
                { value: "P", label: "P" },
                { value: "M", label: "M" },
                { value: "G", label: "G" },
                { value: "GG", label: "GG" },
                { value: "XG", label: "XG" }
            ],
            "condicaoRetornoTrabalho": [
                { value: "BENEFICIARIO", label: "Beneficiário" },
                { value: "REABILITADO", label: "Reabilitado" },
                { value: "PORTADOR_DEFICIENCIA_HABILITADO", label: "Portador de Deficiência Habilitado" },
                { value: "NAO_APLICAVEL", label: "Não Aplicável" }
            ],
            "tipoAposentadoria": [
                { value: "VOLUNTARIA", label: "Voluntária" },
                { value: "COMPULSORIA", label: "Compulsória" },
                { value: "INVALIDEZ", label: "Invalidez" },
                { value: "TEMPO_SERVICO_INTEGRAL", label: "Tempo de Serviço Integral" },
                { value: "TEMPO_SERVICO_PROPORCIAL", label: "Tempo de Serviço Proporcinal" },
                { value: "IDADE_INTEGRAL", label: "Idade Integral" },
                { value: "IDADE_PROPORCIONAL", label: "Idade Proporcional" },
                { value: "INVALIDEZ_INTEGRAL", label: "Invalidez Integral" },
                { value: "INVALIDEZ_PROPORCIONAL", label: "Invalidez Proporcional" },
                { value: "ESPECIAL_PROFESSOR", label: "Especial Professor Regente" },
                { value: "ESPECIAL_ATIVIDADE_INSALUBRE", label: "Especial Atividade Insalubre" }
            ],
            "tipoProporcao": [
                { value: "AVOS", label: "Avos" },
                { value: "PERCENTUAL", label: "Percental" }
            ]

        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/dadoCadastralComplementar/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.dadoCadastralCompl.id) {
                $scope.dadoCadastralCompl.funcionarioId = JSON.parse(localStorage.getItem('idFuncionario'));
                GenericoService.Update('/dadoCadastralCompl', $scope.dadoCadastralCompl, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dadoCadastralComplementar/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dadoCadastralComplementar/formulario/');
                    }
                });
            } else {
                $scope.dadoCadastralCompl.funcionarioId = JSON.parse(localStorage.getItem('idFuncionario'));
                GenericoService.Create('/dadoCadastralCompl', $scope.dadoCadastralCompl, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dadoCadastralComplementar/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dadoCadastralComplementar/formulario');
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