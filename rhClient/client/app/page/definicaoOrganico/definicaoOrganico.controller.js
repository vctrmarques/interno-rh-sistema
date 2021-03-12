(function () {
    'use strict';

    angular.module('app.page')
        .controller('definicaoOrganicoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', definicaoOrganicoCtrl]);

    function definicaoOrganicoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {

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
        $scope.query = {
            order: 'nomeFilial',
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
                  siglaNome: $scope.filialBusca
              }
          };

          $scope.promise = GenericoService.GetAll('/empresasFiliaisSemMatrizPaginado', config).then(
              function (response) {
                  if (response.status === 200) {
                      $scope.list.data = response.data.content;
                      $scope.list.count = response.data.totalElements;
                      for (var index = 0; index < $scope.list.data.length; index++) {
                        $scope.buttonPermit[$scope.list.data[index].id] = false;
                        if($scope.list.data[index].definicaoOrganicoId){
                            $scope.buttonPermit[$scope.list.data[index].id] = true;
                        }
                        for (var j = 0; j < $scope.list.data[index].lotacoes.length; j++) {
                            $scope.exclusaoPermit = false;
                        }
                      }  
                      $rootScope.$broadcast('preloader:hide');
                  } else {
                      $scope.showSimpleToast("Não foi possível carregar os dados.")
                  }
              });
      }

        $scope.limitOptions = [5, 10, 15];

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/deletarDefinicaoOrganico/' + $scope.idToDelete, function (response) {
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