(function () {
    'use strict';

    angular.module('app.page')
        .controller('tempoServicoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', tempoServicoCtrl]);

    function tempoServicoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {

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

        $scope.botoesGestao = false;
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.botoesGestao = true;
                }
            });
        $scope.exclusaoPermit = true;
        $scope.buttonPermit = {};
        $scope.definicaoOrganico = "";
        $scope.nomeBusca = "";
        $scope.query = {
            order: 'numeroProcesso',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.limpaFiltro = function () {
            delete $scope.definicaoOrganico;
        }

        $scope.loadList = function () {
          $rootScope.$broadcast('preloader:active');

          
          var config = {
              params: {
                  page: $scope.query.page - 1,
                  size: $scope.query.limit,
                  order: $scope.query.order,
                  nome: $scope.nomeBusca
              }
          };

          $scope.promise = GenericoService.GetAll('/funcionariosTempoServico', config).then(
              function (response) {
                  if (response.status === 200) {
                      $scope.list.data = response.data.content;
                      $scope.list.count = response.data.totalElements;
                     
                      $rootScope.$broadcast('preloader:hide');
                  } else {
                      $scope.showSimpleToast("Não foi possível carregar os dados.")
                  }
              });
      }

        $scope.limitOptions = [5, 10, 15];

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/tempoServico/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
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

    }

})();