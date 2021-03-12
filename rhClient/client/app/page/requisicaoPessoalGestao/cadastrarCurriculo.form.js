(function () {
    'use strict';

    angular.module('app.page').controller('cadastrarCurriculoFormCtrl', 
        ['FileUploader', 'configValue', '$scope', '$http', '$mdToast', '$mdDialog', '$rootScope', '$state', '$location', 'GenericoService', 'EnumService', analiseCurriculoFormCtrl]
    );

    function analiseCurriculoFormCtrl(FileUploader, configValue, $scope, $http, $mdToast, $mdDialog, $rootScope, $state, $location, GenericoService, EnumService) {
        $scope.nome = null;
        $scope.comentario = null;
    	$scope.candidatos = [];
        $scope.numeroProcesso = $state.params.id;
        
        $scope.listaCandidatos = function() {
        	GenericoService.GetById('/requisicaoPessoalGestao/candidatos/' + $state.params.id, function (response) {
        		$rootScope.$broadcast('preloader:hide');
        		$scope.candidatos = response.data;
        	});
        }
        
        $scope.listaCandidatos();

        $scope.goBack = function() {
            $location.path('/requisicaoPessoalGestao/gestao');
        };
        
        $scope.salvar = function() {
            $scope.loading = true;
        	if ($scope.anexo) {
        		salvaDescricaoAnexo();
        	}

        	$scope.candidato = {
        		id : null,
				nome : $scope.nome,
				comentario : $scope.comentario,
				comentarioCurriculo : null,
				situacao : 'Pendente',
				anexoId : $scope.anexo.id,
				requisicaoPessoalId : $state.params.id
			};
        	
        	$rootScope.$broadcast('preloader:active');
        	GenericoService.Create('/requisicaoPessoalGestao/candidato/', $scope.candidato, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                	console.log(response.data.message);
                	$scope.showSimpleToast(response.data.message);
                	$scope.limpaDados();
                    $scope.listaCandidatos();
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
                $scope.loading = false;
            })
        };
        
        $scope.limpaDados = function (){
        	$scope.nome = null;
            $scope.comentario = null;
            $scope.anexo = undefined;
        }
        
        $scope.excluirAnexo = function () {
            $scope.anexo = undefined;
        }
        
        $scope.deleteItem = function (value) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/requisicaoPessoalGestao/candidato/delete/' + value.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.listaCandidatos();
                }
            });
        }

        function salvaDescricaoAnexo() {
            if ($scope.anexo && $scope.anexo.id) {
                var anexo = {
                    id: $scope.anexo.id,
                    description: $scope.anexo.description
                }
                GenericoService.Update('/anexo', anexo, function (response) {
                });
            }
        }
        
        $scope.uploader = new FileUploader();
        $scope.uploader.url = configValue.baseUrl + "/api/anexo/requisicaoPessoalCurriculo";
        $scope.uploader.headers = $http.defaults.headers.common;
        $scope.uploader.removeAfterUpload = true;
        $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.anexo = response.obj;
        };

        var validateExtensions = ["application/pdf"];

        $scope.onFileChange = function (event) {
            if (validateExtensions.indexOf(event.target.files[0].type) == -1) {
                $scope.showSimpleToast('So é aceito arquivos do tipo: .pdf');
                $scope.uploader.removeFromQueue(0);
                $scope.uploader.clearQueue();
            } else {
                $scope.uploader.addToQueue(event.target.files[0]);
                $scope.uploader.uploadItem(0);

            }
        };
        
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
