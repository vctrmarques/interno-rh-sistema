(function () {
    'use strict';

    angular.module('app.core').directive('selectMultiploComponent', ['$mdToast', '$rootScope', 'RestService', '$filter',
        function ($mdToast, $rootScope, RestService, $filter) {
            return {
                restrict: 'E',
                template: `
                <md-select data-md-container-class="selectdemoSelectHeader" md-on-close="searchTerm = ''" multiple
                    ng-model-options="{trackBy: '$value.label' }">
                    <md-select-header class="demo-select-header">
                        <input ng-model="searchTerm" type="search"
                            ng-keydown="$event.stopPropagation();"
                            placeholder="Busca..."
                            class="demo-header-searchbox md-text">
                    </md-select-header>
                    <md-option ng-value="obj" ng-repeat="obj in results | filter:searchTerm">
                        {{obj.label}}
                    </md-option>
                </md-select>
                `,
                replace: true,
                scope: {
                    endPoint: '@'
                },
                link: function (scope) {
                    $rootScope.$broadcast('preloader:active');

                    RestService.Get(scope.endPoint)
                        .then(function successCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.data && response.data.length > 0) {
                                scope.results = $filter('orderBy')(response.data, 'label');
                            } else {
                                scope.results = [];
                            }
                        }, function errorCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 400)
                                $mdToast.show(
                                    $mdToast.simple()
                                        .textContent(response.data.message)
                                        .position('top right')
                                        .hideDelay(10000)
                                );
                        });
                }
            }
        }])
})();

