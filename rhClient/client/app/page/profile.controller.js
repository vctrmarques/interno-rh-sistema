(function () {
    'use strict';

    angular.module('app.page')
        .controller('profileCtrl', ['configValue', '$http', '$scope', '$mdToast', '$mdDialog', '$rootScope', '$window', '$location', profileCtrl]);

    function profileCtrl(configValue, $http, $scope, $mdToast, $mdDialog, $rootScope, $window, $location) {

        $scope.showAdvanced = function (ev) {
            $mdDialog.show({
                controller: DialogController,
                templateUrl: 'app/page/editar-foto.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
            })
                .then(function (answer) {
                    $scope.status = 'You said the information was "' + answer + '".';
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        function DialogController($scope, $rootScope, $mdDialog, $timeout, GenericoService, UsuarioService, $cookies, $mdToast) {

            $scope.myImage = '';
            $scope.myCroppedImage = '';
            $scope.currentUser = $rootScope.globals.currentUser;

            var name;

            var handleFileSelect = function (evt) {
                var file = evt.currentTarget.files[0];
                name = file.name;

                var reader = new FileReader();
                reader.onload = function (evt) {
                    $scope.$apply(function ($scope) {
                        $scope.myImage = evt.target.result;
                    });
                };
                reader.readAsDataURL(file);
            };

            $timeout(function () {
                angular.element(document.querySelector('#fileInput')).on('change', handleFileSelect);
            }, 1000, false);

            $scope.loadInputFile = function () {
                document.getElementById('fileInput').click();
            };

            $scope.cancel = function () {
                $mdDialog.cancel();
            };

            $scope.aplicarRecorte = function () {
                $rootScope.$broadcast('preloader:active');
                uploadSingleFile(dataURLtoFile($scope.myCroppedImage, name));
            };

            function dataURLtoFile(dataurl, filename) {
                var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
                    bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
                while (n--) {
                    u8arr[n] = bstr.charCodeAt(n);
                }
                return new File([u8arr], filename, { type: mime });
            }

            function uploadSingleFile(file) {
                $mdDialog.hide();
                GenericoService.UploadFile(file, 'usuario', function (response) {
                    if (response.status === 200 && response.data.success) {
                        $rootScope.globals.currentUser.foto = response.data.obj.fileDownloadUri;
                        salvarFoto(response.data.obj.id);
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
            }

            function salvarFoto(idFotoAnexo) {
                var usuarioPerfil = {
                    anexoId: idFotoAnexo
                }
                UsuarioService.UpdatePerfil(usuarioPerfil, function (response) {
                    if (response.status === 201 && response.data.success) {
                        $rootScope.$broadcast('preloader:hide');
                        $scope.showSimpleToast(response.data.message);

                        $cookies.remove('globals');
                        var cookieExp = new Date();
                        cookieExp.setDate(cookieExp.getDate() + 1);
                        $cookies.putObject('globals', $rootScope.globals, { expires: cookieExp });

                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });

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

    }

})();



