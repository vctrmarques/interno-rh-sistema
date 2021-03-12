(function () {
    'use strict';

    angular.module('app.page')
    .controller('invoiceCtrl', ['$scope', '$window', invoiceCtrl])
    .controller('authCtrl', ['$scope', '$mdDialog', '$mdMedia', '$window', '$location', 'GenericoService', 'AutenticacaoService', authCtrl]);

    function invoiceCtrl($scope, $window) {
        var printContents, originalContents, popupWin;
        
        $scope.printInvoice = function() {
            printContents = document.getElementById('invoice').innerHTML;
            originalContents = document.body.innerHTML;        
            popupWin = window.open();
            popupWin.document.open();
            popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="styles/main.css" /></head><body onload="window.print()">' + printContents + '</html>');
            popupWin.document.close();
        }
    }

    function authCtrl($scope, $mdDialog, $mdMedia, $window, $location, GenericoService, AutenticacaoService) {

        // reset login status
        (function initController() {
			AutenticacaoService.ClearCredentials();
        })();

        var original;

        $scope.user = {
            username: '',
            passowrd: '',
            email: ''
        }   

        $scope.type = '';

        original = angular.copy($scope.user);

        $scope.canSubmit = function() {
            return $scope.material_login_form.$valid && !angular.equals($scope.user, original);
        };    

        $scope.submitForm = function() {
			AutenticacaoService.Login($scope.user.username, $scope.user.password, function (response) {
				if (response.status === 401) {
                    $scope.showErrorOnSubmitFailed = true;
                    $scope.showEmailMessage = false;
                }else if(response.status === 400){
                    $scope.showInactiveError = true;
                    $scope.showEmailMessage = false;
                } else {
                    AutenticacaoService.SetCredentials(response.data);
                    if ($scope.type == 'login'){
                        $location.path('/');
                    }
				}
			});
        }
        
        $scope.showDialog = function () {
            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'authCtrl',
                scope: $scope,
                preserveScope: true,
                bindToController: true,
                controller: function () {
                    $scope.salvar = function () {
                        debugger
                        if ($scope.user.email.length > 0) {
                            var exclude = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                            var emailValido = exclude.test(String($scope.user.email).toLowerCase());
                            if(!emailValido){
                                $scope.showEmailInvalido = true;
                                $scope.showEmailNaoEcontrado = false;
                                delete $scope.user.email;
                                return;
                            }
                        }else{
                            $scope.showEmailInvalido = true;
                            return;
                        }

                        AutenticacaoService.Login($scope.user.email, '0', function (response) {
                            delete $scope.user.email;
                            if (response.status === 401) {
                                $mdDialog.cancel();
                                $scope.showEmailInvalido = false;
                                $scope.showEmailNaoEcontrado = true;
                                $scope.showEmailMessage = false;
                            }else if(response.status === 400){
                                $mdDialog.cancel();
                                $scope.showEmailInvalido = false;
                                $scope.showEmailNaoEcontrado = true;
                                $scope.showEmailMessage = false;
                            } else {
                                $mdDialog.cancel();
                                $scope.showEmailInvalido = false;
                                $scope.showEmailNaoEcontrado = false;
                                $scope.showEmailMessage = true;
                            }
                        });
                    };
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/page/email.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: useFullScreen
            });
        }

        $scope.showDialogCadastroUsuario = function () {
            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'authCtrl',
                scope: $scope,
                preserveScope: true,
                bindToController: true,
                controller: function () {
                    $scope.salvar = function () {
                        
                    };
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/page/cadastroUsuario.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: useFullScreen
            });
        }
    }

})(); 



