(function () {
    'use strict';

    angular.module('app.core').directive('auditData', function () {
        return {
            template: `
                <div ng-show="show">
                    <table md-table>
                        <thead md-head>
                            <tr md-row>
                                <th md-column>
                                    <span>Criado em:</span>
                                </th>
                                <th md-column>
                                    <span>Atualizado em:</span>
                                </th>
                                <th md-column>
                                    <span>Criado por:</span>
                                </th>
                                <th md-column>
                                    <span>Atualizado por:</span>
                                </th>
                            </tr>
                        </thead>
                        <tbody md-body>
                            <tr md-row>
                                <td md-cell>{{data.criadoEm | date:'dd/MM/yy - HH:mm'}}</td>
                                <td md-cell>{{data.alteradoEm | date:'dd/MM/yy - HH:mm'}}
                                </td>
                                <td md-cell>{{data.criadoPor}}</td>
                                <td md-cell>{{data.alteradoPor}}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            `,
            restrict: 'E',
            scope: {
                show: '=',
                data: '='
            }
        }

    })

})();

