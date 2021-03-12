(function () {
    'use strict';

    angular.module('app.core').directive('clearButton', [
        function () {
            return {
                restrict: 'E',
                template: `
                <div>
                    <md-button class="md-icon-button" style="margin: 18px 0px;"
                        ng-show="model" ng-disabled="disabled"
                        ng-click="remove();">
                        <md-icon>close</md-icon>
                    </md-button>
                </div>
                `,
                replace: true,
                scope: {
                    model: '=?',
                    disabled: '=?'
                },
                link: function (scope) {
                    scope.remove = function () {
                        scope.model = null;
                    };
                }
            }
        }])
})();

