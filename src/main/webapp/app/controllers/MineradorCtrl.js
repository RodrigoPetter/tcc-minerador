angular.module('app')
.controller('MineradorCtrl', ["$scope", "facebookService", "mineradorService",
    "extratorID", "classifierService",
    function($scope, facebookService, mineradorService, extratorID, classifierService) {

    $scope.data = {};
    $scope.extrairID = extratorID.extrair;
    $scope.albumSelecionado = false;

    atualizarTela();
    
    $scope.analisar = function (albumId, albumName) {
        $scope.albumName = albumName;
        $scope.albumSelecionado = true;

        //busca todas as fotos do album selecionado
        facebookService.getAlbumPhotos(albumId).then(function (response) {
            console.log(response);
            $scope.albumPhotos = response;
        });

    }

    $scope.salvarResultado = function (foto, resultadoAnalise) {
        resultadoAnalise.forEach(function (item) {
            mineradorService.salvarResultado(foto, item.Prediction).then(function (response) {
                atualizarTela();
            })
        })
    }

    function atualizarTela(){
        facebookService.getData().then(function (response) {
           $scope.profileData = response;
           console.log(response);
        });

        facebookService.getProfilePhoto().then(function (response) {
            console.log(response);
            $scope.profilePhoto = response.photo;
        });

        facebookService.getAlbums().then(function (response) {
            $scope.listaAlbums = response;
            console.log($scope.listaAlbums);
        });

        classifierService.getClassifiers().then(function (response) {
            $scope.listaClassifier = response._embedded.classifier;
            console.log(response._embedded.classifier);
        });
    }
    
}]);