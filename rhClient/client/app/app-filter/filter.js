(function () {
    'use strict';


    angular.module('app').filter('mes', function () {
        var mes =  [
            { name: 'Janeiro' },
            { name: 'Fevereiro' },
            { name: 'Março' },
            { name: 'Abril' },
            { name: 'Maio' },
            { name: 'Junho' },
            { name: 'Julho' },
            { name: 'Agosto' },
            { name: 'Setembro' },
            { name: 'Outubro' },
            { name: 'Novembro' },
            { name: 'Dezembro' }
        ];
        return function(value) {
            if(value)
                var v = mes[value-1].name;
            return v;
        };
    });

    angular.module('app').filter('moment', function () {
        return function (input, momentFn /*, param1, param2, ...param n */ ) {
            var args = Array.prototype.slice.call(arguments, 2),
                momentObj = moment(input);
            return momentObj[momentFn].apply(momentObj, args);
        };
    });

    angular.module('app').filter('cep', function () {
        return function (input) {
            var str = input + '';
            str = str.replace(/\D/g, '');
            str = str.replace(/^(\d{2})(\d{3})(\d)/, '$1.$2-$3');
            return str;
        };
    });
    // Source: dist/.temp/brasil/filters/cnpj.js
    angular.module('app').filter('cnpj', function () {
        return function (input) {
            // regex créditos Matheus Biagini de Lima Dias
            var str = input + '';
            str = str.replace(/\D/g, '');
            str = str.replace(/^(\d{2})(\d)/, '$1.$2');
            str = str.replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3');
            str = str.replace(/\.(\d{3})(\d)/, '.$1/$2');
            str = str.replace(/(\d{4})(\d)/, '$1-$2');
            return str;
        };
    });
    // Source: dist/.temp/brasil/filters/cpf.js
    angular.module('app').filter('cpf', function () {
        return function (input) {
            var str = input + '';
            str = str.replace(/\D/g, '');
            str = str.replace(/(\d{3})(\d)/, '$1.$2');
            str = str.replace(/(\d{3})(\d)/, '$1.$2');
            str = str.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
            return str;
        };
    });
    // Source: dist/.temp/brasil/filters/realbrasileiro.js
    function formatReal(int) {
        var tmp = int.toString().indexOf('.') !== -1 ? int + '' : int + '.00';
        var res = tmp.replace('.', '');
        tmp = res.replace(',', '');
        var neg = false;
        if (tmp.indexOf('-') === 0) {
            neg = true;
            tmp = tmp.replace('-', '');
        }
        if (tmp.length === 1) {
            tmp = '0' + tmp;
        }
        tmp = tmp.replace(/([0-9]{2})$/g, ',$1');
        if (tmp.length > 6) {
            tmp = tmp.replace(/([0-9]{3}),([0-9]{2}$)/g, '.$1,$2');
        }
        if (tmp.length > 9) {
            tmp = tmp.replace(/([0-9]{3}).([0-9]{3}),([0-9]{2}$)/g, '.$1.$2,$3');
        }
        if (tmp.length > 12) {
            tmp = tmp.replace(/([0-9]{3}).([0-9]{3}).([0-9]{3}),([0-9]{2}$)/g, '.$1.$2.$3,$4');
        }
        if (tmp.indexOf('.') === 0) {
            tmp = tmp.replace('.', '');
        }
        if (tmp.indexOf(',') === 0) {
            tmp = tmp.replace(',', '0,');
        }
        return neg ? '-' + tmp : tmp;
    }
    angular.module('app').filter('realbrasileiro', function () {
        return function (input) {
            return 'R$ ' + formatReal(input);
        };
    });
    // Source: dist/.temp/brasil/filters/tel.js
    angular.module('app').filter('tel', function () {
        return function (input) {
            var str = input + '';
            str = str.replace(/\D/g, '');
            if (str.length === 11) {
                str = str.replace(/^(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
            }
            if (str.length === 10) {
                str = str.replace(/^(\d{2})(\d{4})(\d{4})/, '($1) $2-$3');
            }
            if (str.length === 8) {
                str = str.replace(/^(\d{4})(\d{4})/, '$1-$2');
            }
            if (str.length === 9) {
                str = str.replace(/^(\d{5})(\d{4})/, '$1-$2');
            }
            return str;
        };
    });

})();
