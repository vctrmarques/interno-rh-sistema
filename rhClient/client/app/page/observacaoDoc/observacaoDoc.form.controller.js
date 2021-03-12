(function () {
    'use strict';

    angular.module('app.page')
        .controller('observacaoDocFormCtrl', ['$scope', 'configValue', '$mdToast', '$location', '$http', '$state', '$rootScope', 'FileUploader', 'GenericoService', observacaoDocFormCtrl]);

    function observacaoDocFormCtrl($scope, configValue, $mdToast, $location, $http, $state, $rootScope, FileUploader, GenericoService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                    }
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }
        $scope.acessaTela();

        $scope.observacaoDoc = {}; 
        $scope.detalhes = false;
        $scope.editar = false;

        if ($state.params && $state.params.anexoId && $state.params.detalhes) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/anexo/' + $state.params.anexoId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.anexo = response.data;
                    $scope.detalhes = true;
                } else {
                    $scope.showSimpleToast("Anexo não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.anexoId && $state.params.editar) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/anexo/' + $state.params.anexoId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.observacaoDoc.description = response.data.description;
                    $scope.observacaoDoc.id = response.data.id;
                    $scope.anexo = response.data;

                    $scope.editar = true;
                } else {
                    $scope.showSimpleToast("Anexo não encontrado na base");
                }
            });
        }        

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if($scope.editar){
                GenericoService.Update('/anexo', $scope.observacaoDoc, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/observacaoDoc/list/'+$state.params.funcionarioId);
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/observacaoDoc/editar/'+$state.params.funcionarioId/$state.params.anexoId+'/'+$state.params.editar);
                    }
                }); 
            }else{
                GenericoService.Update('/funcionarioAnexo', $scope.observacaoDoc, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/observacaoDoc/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/observacaoDoc/list' + $state.params.funcionarioId);
                    }
                }); 
            }
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        $scope.goBack = function () {
            $location.path('/observacaoDoc/list/'+$state.params.funcionarioId);
        } 
        
        var validateExtensions = ["image/jpeg", "application/pdf", "image/jpg"];

        $scope.uploader = new FileUploader();
        $scope.uploader.url = configValue.baseUrl + "/api/anexo/funcionario";
        $scope.uploader.headers = $http.defaults.headers.common;
        $scope.uploader.removeAfterUpload = true;
        $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.observacaoDoc.anexoId = response.obj.id;
            $scope.observacaoDoc.funcionarioId = $state.params.funcionarioId;
            $scope.file = response.obj;
        };         

        $scope.onFileChange = function (event) {
            var filename = event.target.files[0].name;
            if(validateExtensions.indexOf(event.target.files[0].type) == -1){
                $scope.showSimpleToast('So é aceito arquivos do tipo: .jpeg, .jpg, .pdf');
                $scope.uploader.removeFromQueue(0);
                $scope.uploader.clearQueue();
            }else{
                $scope.uploader.addToQueue(event.target.files[0]);
                $scope.uploader.uploadItem(0);
               
            }
        };
    }
})(); 