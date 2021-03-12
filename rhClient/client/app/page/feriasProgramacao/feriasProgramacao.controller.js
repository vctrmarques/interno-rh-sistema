(function () {
    'use strict';

    angular.module('app.page')
        .controller('feriasProgramacaoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', '$location', 'GenericoService', feriasProgramacaoCtrl]);

    function feriasProgramacaoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, $location, GenericoService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                        $scope.loadList();
                    }
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }

        $scope.acessaTela();
        $scope.botoesGestao = false;

        $scope.habilitaBotoesGestao = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                        $scope.botoesGestao = true;
                    }
                });
        }

        $scope.habilitaBotoesGestao();

        $scope.search = "";
        $scope.query = {
            order: 'matricula',
            limit: 10,
            page: 1
        };

        $scope.query = {
            order: 'nome',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.limpaFiltro = function(){
            delete $scope.search;
        }

        $scope.carregarFuncionarios = function () {
            $scope.query.page = 1;
            $scope.loadList();
        }

        $scope.accordion = function () {
            var accordions = document.getElementsByClassName("accordion");

            for (var i = 0; i < accordions.length; i++) {
                accordions[i].onclick = function() {
                    this.classList.toggle('is-open');
            
                    var content = this.nextElementSibling;
                    if (content.style.maxHeight) {
                    // accordion is currently open, so close it
                    content.style.maxHeight = null;
                    content.style.paddingTop = null;
                    } else {
                    // accordion is currently closed, so open it
                    content.style.maxHeight = content.scrollHeight + "px";
                    content.style.paddingTop = "5px";
                    }
                }
            }            
        };         

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    search: $scope.search
                }
            };           

            $scope.promise = GenericoService.GetAll('/funcionarios/feriasProgramacao', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });

                $scope.accordion();
        }

        $scope.updateFeriasProgramacao = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Update('/feriasProgramacao/atualizarStatusParaAgendado/', $scope.feriasProgramacao, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadList();
                } else if (response.status === 400) {
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

        $scope.showConfirm = function (ev, feriasProgramacao) {
            $scope.feriasProgramacao = feriasProgramacao;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar esta ação?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                // .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');
            $mdDialog.show(confirm).then(function () {                
                $scope.updateFeriasProgramacao();
            });
        };
    }
})();



