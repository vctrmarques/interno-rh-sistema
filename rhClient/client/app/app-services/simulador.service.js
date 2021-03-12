(function () {
    'use strict';

    angular
        .module('app.page')
        .service('SimuladorService', function () {
            var simuladorAposentadoria = null;
            var simuladorAposentadoriaResultado = null;

            var setSimuladorAposentadoria = function (newObj) {
                simuladorAposentadoria = newObj;
            };

            var getSimuladorAposentadoria = function () {
                return simuladorAposentadoria;
            };

            var setSimuladorAposentadoriaResultado = function (newObj) {
                simuladorAposentadoriaResultado = newObj;
            };

            var getSimuladorAposentadoriaResultado = function () {
                return simuladorAposentadoriaResultado;
            };

            return {
                setSimuladorAposentadoria: setSimuladorAposentadoria,
                getSimuladorAposentadoria: getSimuladorAposentadoria,
                setSimuladorAposentadoriaResultado: setSimuladorAposentadoriaResultado,
                getSimuladorAposentadoriaResultado: getSimuladorAposentadoriaResultado
            };

        });

})();