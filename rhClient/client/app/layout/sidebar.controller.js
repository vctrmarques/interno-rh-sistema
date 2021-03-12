(function () {
    'use strict';

    angular.module('app.page')
        .controller('sidebarCtrl', ['configValue', '$timeout', '$http', '$scope', '$mdToast', '$mdDialog', '$rootScope', '$window', '$location', sidebarCtrl]);

    function sidebarCtrl(configValue, $timeout, $http, $scope, $mdToast, $mdDialog, $rootScope, $window, $location) {

        $scope.collapseNav = function (ulIndex) {

            var i;
            for (i = 0; i < $rootScope.menus.length; i++) {
                var ul = $('#ul-' + i);
                if (ulIndex === i) {
                    $rootScope.menus[i].exibirFilhos = !$rootScope.menus[i].exibirFilhos;
                    ul.slideToggle(250);
                } else {
                    ul.slideUp(250);
                }

            }

        }

    }

})();