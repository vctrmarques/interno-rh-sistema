angular.module('app.core').directive('accordionBefore', [function () {

    function mano(scope, element, attrs) {
        var accordionsBefore = element[0];
        element.on('click', function () {                
            var content = accordionsBefore.nextElementSibling; 
            if (content.style.maxHeight) {
                
                accordionsBefore.classList.remove("is-open");
                content.style.maxHeight = null;
                content.style.paddingTop = null;
            } else {
                accordionsBefore.classList.add("is-open");
                content.style.maxHeight = content.scrollHeight + "px";
                content.style.paddingTop = "5px";
            }
        });
    };

    return {
        link: mano
    }
}]);
