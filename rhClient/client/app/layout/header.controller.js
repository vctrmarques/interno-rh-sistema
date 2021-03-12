(function () {
    'use strict';

    angular.module('app.page')
        .controller('headerCtrl', ['$scope', '$timeout', '$q', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'AutenticacaoService', headerCtrl]);

    function headerCtrl($scope, $timeout, $q, $mdToast, $location, $state, $rootScope, GenericoService, AutenticacaoService) {

        $scope.querySearchMenu = function (query) {
            var deferred = $q.defer();
            var config = { params: { nomeSearch: query } }
            GenericoService.GetAll('/menu/search', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }

        $scope.submitSearch = function () {
            if ($scope.subMenu) {
                var route = $scope.subMenu.url;
                $location.path(route);
                delete $scope.subMenu;
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