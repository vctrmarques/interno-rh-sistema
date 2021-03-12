(function () {
    'use strict';

    angular.module('app.page')
        .controller('arrecadacaoIndiceFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'EnumService', 'RestService', '$filter', arrecadacaoIndiceFormCtrl]);

    function arrecadacaoIndiceFormCtrl($scope, $mdToast, $location, $state, $rootScope, EnumService, RestService, $filter) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Índice' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                    $scope.podeGerenciar = response.data.papeis.includes('ROLE_ARRECADACAO_INDICE_CADASTRAR', 'ROLE_ARRECADACAO_INDICE_ATUALIZAR', 'ROLE_ARRECADACAO_INDICE_VISUALIZAR') ? true : false;

                    $scope.autorizado = ($scope.usuarioAdm || $scope.podeGerenciar);

                    if ($scope.autorizado){
                        //$scope.loadList();
                    } else {
                        $location.path('page/403');
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });
            
            $scope.list = {
                "bimestral": [
                    { value: "JANEIRO/FEVEREIRO", label: "JAN/FEV" },
                    { value: "MARÇO/ABRIL", label: "MAR/ABR" },
                    { value: "MAIO/JUNHO", label: "MAI/JUN" },
                    { value: "JULHO/AGOSTO", label: "JUL/AGO" },
                    { value: "SETEMBRO/OUTUBRO", label: "SET/OUT" },
                    { value: "NOVEMBRO/DEZEMBRO", label: "NOV/DEZ" }
                ],
                "trimestral": [
                    { value: "JANEIRO/FEVEREIRO/MARÇO", label: "JAN/FEV/MAR" },
                    { value: "ABRIL/MAIO/JUNHO", label: "ABR/MAI/JUN" },
                    { value: "JULHO/AGOSTO/SETEMBRO", label: "JUL/AGO/SET" },
                    { value: "OUTUBRO/NOVEMBRO/DEZEMBRO", label: "OUT/NOV/DEZ" }
                ],
                "anual": [
                    { value:"JANEIRO/FEVEREIRO/MARÇO/ABRIL/MAIO/JUNHO/JULHO/AGOSTO/SETEMBRO/OUTUBRO/NOVEMBRO/DEZEMBRO", label: "Todos os meses" }
                ],
                "listaAnos": []                      
            };

            $scope.mesesList = [];
            $scope.arrecadacaoIndice = {};
            $scope.arrecadacaoIndice.anos = [];
            $scope.arrecadacaoIndiceDetalhe = [];


            $scope.indicesLista=[];
            $scope.indicesLista.ano=[];
            $scope.regraDeBloqueioDeIndice = false;

            $scope.limiteAnoBase = new Date().getFullYear();

            EnumService.Get("MesEnum").then(function (dados) {
                $scope.list.mes = dados;
            });

            EnumService.Get("PeriodicidadeMesEnum").then(function (dados) {
                $scope.list.periodicidade = dados;
            });

            $scope.carregarListaDeAnos = function(){
                for (let anoInicial = 1980 ; anoInicial <= $scope.limiteAnoBase ; $scope.limiteAnoBase--) {
                    $scope.list.listaAnos.push($scope.limiteAnoBase);
                }
            };

            $scope.carregarListaDeAnos(); 

            RestService.Get('/competencia/anos')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.anoCompetenciaList = response.data;
                } else if (response.status === 200 && !response.data) {
                    $scope.showSimpleToast("Não existem anos de competência disponíveis.");
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });
            
        $scope.changeAno = function (){
            var existe = false;
            $scope.regraDeBloqueioDePeriodicidade = false;
            delete $scope.arrecadacaoIndice.periodicidade;
            delete $scope.arrecadacaoIndice.mes;
            delete $scope.arrecadacaoIndice.aliquota;

            angular.forEach($scope.arrecadacaoIndice.anos, function (e) {
                if (!existe && e.ano == $scope.arrecadacaoIndice.ano) {
                    $scope.arrecadacaoIndice.periodicidade = e.periodicidade.toUpperCase();
                    $scope.regraDeBloqueioDePeriodicidade = true;

                    $scope.getPeriodicidade(e.periodicidade.toUpperCase(), $scope.arrecadacaoIndice.ano);
                    
                    for (let index = 0; index < e.meses.length; index++) {
                        let obj = e.meses[index];
                        getCheckMes(obj.mes);
                    }
                }
            });

        }

        function getCheckMes(mes) {
            angular.forEach($scope.mesesList, function (f) {
                if(f.value == mes){
                    var index = $scope.mesesList.indexOf(f);
                    $scope.mesesList.splice(index, 1);
                }
            });
        }

        $scope.getPeriodicidade = function (periodicidade, ano) {
            if(periodicidade == "MENSAL"){
                $scope.mesesList = angular.copy($scope.list.mes);
            }else if(periodicidade == "ANUAL"){
                $scope.mesesList = angular.copy($scope.list.anual);
            }else if(periodicidade == "BIMESTRAL"){
                $scope.mesesList = angular.copy($scope.list.bimestral);
            }else if(periodicidade == "TRIMESTRAL"){
                $scope.mesesList = angular.copy($scope.list.trimestral);
            }else if(periodicidade == "SEMESTRAL"){
                angular.copy($scope.mesesList = [
                    { value: "JANEIRO/FEVEREIRO/MARÇO/ABRIL/MAIO/JUNHO", label: ano +".1" },
                    { value: "JULHO/AGOSTO/SETEMBRO/OUTUBRO/NOVEMBRO/DEZEMBRO", label: ano +".2" }
                ])
            }
        }

        $scope.accordion = function (ano) {
            var accordions = document.getElementById("accordion-indices" + ano);

            var content = accordions.nextElementSibling;
            if (content.style.maxHeight) {
                accordions.classList.remove("is-open");
                // accordion is currently open, so close it
                content.style.maxHeight = null;
                content.style.paddingTop = null;
            } else {
                // accordion is currently closed, so open it
                accordions.classList.add("is-open");
                content.style.maxHeight = content.scrollHeight + "px";
                content.style.paddingTop = "5px";
            }
        };

        $scope.getAccordionClass = function (index){
            if(index % 2 == 0){
                return 'accordion col-lg-12';
            } else {
                return 'accordion odd col-lg-12';
            }
        }
        
        $scope.removerIndice = function (ano, meses) {
            angular.forEach($scope.arrecadacaoIndice.anos, function (a) {
                var indexAno = a.meses.indexOf(a);
                if(a.ano == ano ){
                    angular.forEach(a.meses, function (m) {
                        if(m.mes == meses ){
                            var index = a.meses.indexOf(m);
                            a.meses.splice(index, 1);
                        }
                    });
                    if ($state.params && $state.params.id) {
                        delete $scope.arrecadacaoIndice.mes;
                        delete $scope.arrecadacaoIndice.aliquota;
                        //carrega todos
                        $scope.getPeriodicidade(a.periodicidade.toUpperCase(),a.ano);
                        angular.forEach($scope.mesesList, function (f) {
                            angular.forEach(a.meses, function (m) {
                                if(m.mes == f.value ){
                                    var index = a.meses.indexOf(m);
                                    $scope.mesesList.splice(index, 1);
                                }
                            });
                        });
                    }else{
                        delete $scope.arrecadacaoIndice.ano
                        delete $scope.arrecadacaoIndice.periodicidade;
                        delete $scope.arrecadacaoIndice.mes;
                        delete $scope.arrecadacaoIndice.aliquota;
                        // chamar a function que recolocará os meses removido novamente.
                        $scope.getPeriodicidade(a.periodicidade.toUpperCase(),a.ano);
                    }

                    // se vazio remova o acordion todo do ano
                    if(a.meses.length == 0){
                        $scope.arrecadacaoIndice.anos.splice(indexAno, 1);
                    }
                }
            });
            // desbloqueia o field índice.
            if($scope.arrecadacaoIndice.anos.length == 0){
                $scope.regraDeBloqueioDeIndice = false;

                if ($state.params && $state.params.id) {
                    delete $scope.arrecadacaoIndice.ano
                    delete $scope.arrecadacaoIndice.periodicidade;
                }
            }
           
        }

        $scope.regraDeBloqueioIndice = function () {
            if($scope.arrecadacaoIndice.anos > 0){
                return true;
            }else{
                return false;
            }
        }

        $scope.adicionarArrecadacaoIndece = function () {
            if (!$scope.checkPodeAdicionar()) {
               if($scope.arrecadacaoIndice.anos.length > 0){
                   var gravar = false;
                    angular.forEach($scope.arrecadacaoIndice.anos, function (f) {
                        if(f.ano == $scope.arrecadacaoIndice.ano){
                            var ix = $scope.arrecadacaoIndice.anos.indexOf(f);
                            let mesLista = {
                                mesOrdem: mesPorNumero($scope.arrecadacaoIndice.mes.substring(0,3)),// variavel criada só pra servi de ordenação, não persiste.
                                mes: $scope.arrecadacaoIndice.mes == 'MARCO' ? 'MARÇO' : $scope.arrecadacaoIndice.mes,
                                aliquota: $scope.arrecadacaoIndice.aliquota
                            }
                            $scope.arrecadacaoIndice.anos[ix].meses.push(mesLista);
                            // ordena a lista por ordem de meses.
                            $scope.arrecadacaoIndice.anos[ix].meses.sort(function (a, b) {
                                return a.mesOrdem - b.mesOrdem;
                              });
                            fecharAcordion(f.ano);

                            gravar = true;
                        }
                    });

                    if(!gravar){
                        inserirNewAno();
                    }

                }else{
                    inserirNewAno();
    
                }

                delete $scope.arrecadacaoIndice.ano;
                delete $scope.arrecadacaoIndice.periodicidade;
                delete $scope.arrecadacaoIndice.mes;
                delete $scope.arrecadacaoIndice.aliquota;
                $scope.regraDeBloqueioDeIndice = true;
            }
        }

        function fecharAcordion(ano) {
            var accordions = document.getElementById("accordion-indices" + ano);
            var content = accordions.nextElementSibling;

            //Se o acordion estiver aberto na hora de inserir um novo registro, ele fecha
            //para que haja a renderização do novo elemento no greed.
            if (content.style.maxHeight) {
                accordions.classList.remove("is-open");
                content.style.maxHeight = null;
                content.style.paddingTop = null;
            }
        }

        function mesPorNumero(mes){
            var numeroMes= 0;
            if(mes == 'JAN'){
                numeroMes = 1;
            }else if(mes == 'FEV'){
                numeroMes = 2;
            }else if(mes == 'MAR'){
                numeroMes = 3;
            }else if(mes == 'ABR'){
                numeroMes = 4;
            }else if(mes == 'MAI'){
                numeroMes = 5;
            }else if(mes == 'JUN'){
                numeroMes = 6;
            }else if(mes == 'JUL'){
                numeroMes = 7;
            }else if(mes == 'AGO'){
                numeroMes = 8;
            }else if(mes == 'SET'){
                numeroMes = 9;
            }else if(mes == 'OUT'){
                numeroMes = 10;
            }else if(mes == 'NOV'){
                numeroMes = 11;
            }else if(mes == 'DEZ'){
                numeroMes = 12;
            }
            return numeroMes;
            }

        function inserirNewAno(){
            let mesLista = {
                mesOrdem: mesPorNumero($scope.arrecadacaoIndice.mes.substring(0,3)),// variavel criada só pra servi de ordenação, não persiste.
                mes: $scope.arrecadacaoIndice.mes == 'MARCO' ? 'MARÇO' : $scope.arrecadacaoIndice.mes,
                aliquota: $scope.arrecadacaoIndice.aliquota
            }
            let anoList = {
                ano: $scope.arrecadacaoIndice.ano,
                periodicidade: $scope.arrecadacaoIndice.periodicidade,
                meses: [],
            }
            anoList.meses.push(mesLista);

            $scope.arrecadacaoIndice.anos.push(anoList);
            // ordena a lista por ordem Descrecente de 'ANOS'.
            $scope.arrecadacaoIndice.anos = $filter('orderBy')($scope.arrecadacaoIndice.anos, 'ano', 'DESC');
        }

        $scope.checkPodeAdicionar = function() {
            if($scope.detalhes){
                return false;
            } else {
                if($scope.arrecadacaoIndice.indice && $scope.arrecadacaoIndice.ano && $scope.arrecadacaoIndice.periodicidade && $scope.arrecadacaoIndice.mes && $scope.arrecadacaoIndice.aliquota){
                    return false;
                }else{
                    $scope.showSimpleToast("Necessário adicionar todos os campos existentes de Índice.");
                    return true;
                }
            }
        }

        $scope.detalhes = false;
        $scope.regraDeBloqueioDeIndice = false;
        $scope.checkDisabled = false;
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            RestService.Get('/arrecadacaoIndice/' + $state.params.id)
            .then(function successCallback(response) {
                $rootScope.$broadcast('preloader:hide');
                $scope.arrecadacaoIndice = angular.copy(response.data);
                $scope.regraDeBloqueioDeIndice = true;
                $scope.regraDeBloqueioDePeriodicidade = true;
                
                angular.forEach($scope.arrecadacaoIndice.anos, function (a) {
                        var ix = $scope.arrecadacaoIndice.anos.indexOf(a);

                        $scope.listaMeses = [];
                        for (let index = 0; index < a.meses.length;) {
                            if(a.periodicidade.toUpperCase() == "MENSAL"){
                                let objMes = { 
                                    mesOrdem: mesPorNumero(a.meses[0+index].mes.toUpperCase().substring(0,3)),// variavel criada só pra servi de ordenação, não persiste.
                                    mes: a.meses[0+index].mes.toUpperCase(),
                                    aliquota: a.meses[index].aliquota,
                                }
                                $scope.listaMeses.push(objMes);
                                index = index + 1;
                            }
                            if(a.periodicidade.toUpperCase() == "BIMESTRAL"){
                                let objMes = { 
                                    mesOrdem: mesPorNumero(a.meses[0+index].mes.toUpperCase().substring(0,3)),// variavel criada só pra servi de ordenação, não persiste.
                                    mes: a.meses[0+index].mes.toUpperCase() + "/" + a.meses[1+index].mes.toUpperCase(),
                                    aliquota: a.meses[index].aliquota,
                                }
                                $scope.listaMeses.push(objMes);
                                index = index + 2;
                            }
                            if(a.periodicidade.toUpperCase() == "TRIMESTRAL"){
                                let objMes = { 
                                    mesOrdem: mesPorNumero(a.meses[0+index].mes.toUpperCase().substring(0,3)),// variavel criada só pra servi de ordenação, não persiste.
                                    mes: a.meses[0+index].mes.toUpperCase() + "/" + a.meses[1+index].mes.toUpperCase() + "/" + a.meses[2+index].mes.toUpperCase(),
                                    aliquota: a.meses[index].aliquota,
                                }
                                $scope.listaMeses.push(objMes);
                                index = index + 3;
                            }
                            if(a.periodicidade.toUpperCase() == "SEMESTRAL"){
                                let objMes = { 
                                    mesOrdem: mesPorNumero(a.meses[0+index].mes.toUpperCase().substring(0,3)),// variavel criada só pra servi de ordenação, não persiste.
                                    mes: a.meses[0+index].mes.toUpperCase() + "/" + a.meses[1+index].mes.toUpperCase() + "/" + a.meses[2+index].mes.toUpperCase() + "/" + a.meses[3+index].mes.toUpperCase() + "/" + a.meses[4+index].mes.toUpperCase() + "/" + a.meses[5+index].mes.toUpperCase(),
                                    aliquota: a.meses[index].aliquota,
                                }
                                $scope.listaMeses.push(objMes);
                                index = index + 6;
                            }
                            if(a.periodicidade.toUpperCase() == "ANUAL"){
                                let mes = "";
                                for (let count = 0; count < 12; count++) {
                                    mes += a.meses[count].mes.toUpperCase();
                                    if(count < 11){
                                        mes += "/"
                                    }
                                }
                                let objMes = { 
                                    mesOrdem: mesPorNumero(a.meses[0+index].mes.toUpperCase().substring(0,3)),// variavel criada só pra servi de ordenação, não persiste.
                                    mes: mes,
                                    aliquota: a.meses[0].aliquota,
                                }
                                $scope.listaMeses.push(objMes);
                                index = index + 12;
                            }
                        }  
                        // carrega os dados do greed na tela de editar
                        $scope.arrecadacaoIndice.anos[ix].meses = [];
                        $scope.arrecadacaoIndice.anos[ix].meses = angular.copy($scope.listaMeses);

                        // carrega os dados do greed na tela de detalhar
                        $scope.arrecadacaoIndiceDetalhe.meses = angular.copy($scope.listaMeses);

                        // carrega os dados no formulário na tela de editar
                        $scope.arrecadacaoIndice.ano = $scope.arrecadacaoIndice.anos[ix].ano;
                        $scope.arrecadacaoIndice.periodicidade = $scope.arrecadacaoIndice.anos[ix].periodicidade.toUpperCase();
                        $scope.getPeriodicidade($scope.arrecadacaoIndice.periodicidade,$scope.arrecadacaoIndice.ano)
                        for (let index = 0; index < $scope.arrecadacaoIndice.anos[ix].meses.length; index++) {
                            let obj = $scope.arrecadacaoIndice.anos[ix].meses[index];
                            getCheckMes(obj.mes);
                        }

                        // ordena a lista por ordem Descrecente de 'ANOS'.
                        $scope.arrecadacaoIndice.anos = $filter('orderBy')($scope.arrecadacaoIndice.anos, 'ano', 'DESC');
                    });
               
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        }

        $scope.podeSalvar = function () {
            if($scope.arrecadacaoIndice.anos.length > 0){
                return $scope.detalhes ? false : true;
            } else {
                return false;
            }
        }

        $scope.save = function () {
            if($scope.podeSalvar()) {
                $rootScope.$broadcast('preloader:active');
                if ($scope.arrecadacaoIndice.id) {
                    RestService.Update('/arrecadacaoIndice', $scope.arrecadacaoIndice)
                        .then(function successCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 201 && response.data.success) {
                                $scope.showSimpleToast(response.data.message);
                                $location.path('/arrecadacaoIndice/gestao');
                            }
                        }, function errorCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 400) {
                                $scope.showSimpleToast(response.data.message);
                            }
                        });
                } else {
                    RestService.Create('/arrecadacaoIndice', $scope.arrecadacaoIndice)
                        .then(function successCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 201 && response.data.success) {
                                $scope.showSimpleToast(response.data.message);
                                $location.path('/arrecadacaoIndice/gestao');
                            }
                        }, function errorCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 400) {
                                $scope.showSimpleToast(response.data.message);
                            }
                        });
                }
            }else{
                $scope.showSimpleToast("Necessário adicionar ao menos um Registro de Índices");
            }

        }

        $scope.goBack = function () {
            $location.path('/arrecadacaoIndice/gestao');
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

    }

})(); 