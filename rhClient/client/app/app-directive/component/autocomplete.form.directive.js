(function () {
    'use strict';

    angular.module('app.core').directive('autocompleteComponent', ['$mdToast', '$rootScope', '$q', 'RestService', '$filter',
        function ($mdToast, $rootScope, $q, RestService, $filter) {
            return {
                restrict: 'E',
                template: `
                    <div flex>
                        <md-autocomplete  ng-disabled="disabled" ng-required="required" md-no-cache="true" 
                            md-floating-label="{{label}}"
                            md-selected-item="model" md-search-text="search" 
                            md-require-match md-input-name="{{inputName}}" 
                            md-items="item in querySearch(search)" md-item-text="item.label" md-delay="100" 
                            md-min-length="minLength"
                            md-selected-item-change="change()">
                            <md-item-template>
                                <span md-highlight-text="search" md-highlight-flags="^i">
                                    {{item.label}}</span>
                            </md-item-template>
                            <md-not-found>
                                Item "{{search}}" não encontrado.
                            </md-not-found>
                        </md-autocomplete>
                    </div>
                `,
                replace: true,
                scope: {
                    label: '@',
                    model: '=?',
                    inputName: '@',
                    endPoint: '@',
                    disabled: '=?',
                    required: '=?',
                    change: '&'
                },
                link: function (scope) {

                    scope.minLength = 3;

                    scope.querySearch = function (query) {
                        var deferred = $q.defer();

                        if (query && query.length >= 3) {
                            $rootScope.$broadcast('preloader:active');
                            var config = {
                                params: {
                                    search: query
                                }
                            };
                            RestService.Get(scope.endPoint, config)
                                .then(function successCallback(response) {
                                    $rootScope.$broadcast('preloader:hide');
                                    if (response.data && response.data.length > 0) {
                                        var results = $filter('orderBy')(response.data, 'label');
                                        deferred.resolve(results);
                                    } else {
                                        deferred.resolve([]);
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
                        return deferred.promise;
                    };
                }
            }
        }])
})();

