(function () {
    'use strict';

    angular
        .module('app.page')
        .service('ParametroService', function () {
            var parametroFiltro = null;
            var parametroVoltar = false;
            var parametroHistorico = '';
    
            var setParametroFiltro = function (newObj) {
                parametroFiltro = newObj;
            };

            var getParametroFiltro = function () {
                return parametroFiltro;
            };

            var setParametroVoltar = function (newObj) {
                parametroVoltar = newObj;
            };

            var getParametroVoltar = function () {
                return parametroVoltar;
            };

            var setParametroHistorico = function (newObj) {
                parametroHistorico = newObj;
            };

            var getParametroHistorico = function () {
                return parametroHistorico;
            };

            return {
                setParametroFiltro: setParametroFiltro,
                getParametroFiltro: getParametroFiltro,
                setParametroVoltar: setParametroVoltar,
                getParametroVoltar: getParametroVoltar,
                setParametroHistorico: setParametroHistorico,
                getParametroHistorico: getParametroHistorico
            };
        });

})();


