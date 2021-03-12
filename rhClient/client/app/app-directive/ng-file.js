(function () {
    'use strict';

    angular.module('app.core').directive('ngFile', ['$parse', function ($parse) {

        function returnImage(scope, element, attrs) {
            var onChange = $parse(attrs.ngFile);            
            element.on('change', function (event) {                
                onChange(scope, { $file: event.target.files });
            });
        };

        return {
            link: returnImage
        }
    }])

})();

