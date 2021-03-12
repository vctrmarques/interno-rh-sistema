angular.module('app.core').directive('accordion', [function () {

    function mano(scope, element, attrs) {
        var accordions = element[0];
        element.on('click', function () {                
            var content = accordions.nextElementSibling; 
            if (content.style.maxHeight) {
                
                accordions.classList.remove("is-open");
                content.style.maxHeight = null;
                content.style.paddingTop = null;
            } else {
                accordions.classList.add("is-open");
                content.style.maxHeight = content.scrollHeight + "px";
                content.style.paddingTop = "5px";
            }
        });
    };

    return {
        link: mano
    }
}]);
