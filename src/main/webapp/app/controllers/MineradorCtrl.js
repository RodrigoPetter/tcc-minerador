angular.module('app')
.controller('MineradorCtrl', ["$scope", "facebookService", "mineradorService",
    "extratorID", "classifierService",
    function($scope, facebookService, mineradorService, extratorID, classifierService) {

    $scope.data = {};
    $scope.extrairID = extratorID.extrair;
    $scope.albumSelecionado = false;

    atualizarTela();

    $scope.isConnected = function () {
        return $scope.profileData !== undefined;
    };

    $scope.analisar = function (albumId, albumName) {
        $scope.albumName = albumName;
        $scope.albumSelecionado = true;

        //busca todas as fotos do album selecionado
        facebookService.getAlbumPhotos(albumId, null).then(function (response) {
            console.log(response);
            $scope.albumPhotos = response;

            //envia para processamento
            angular.forEach($scope.albumPhotos, function (foto, key) {
                var album = foto.album.id;
                var fotoId = foto.id;
                var perfil = $scope.profileData.id;

                console.log("vai processar foto ID: "+foto.id);

                mineradorService.identificar(perfil, album, fotoId, $scope.selectedClassifier).then(function (response) {
                    console.log("response: ");
                    console.log(response);
                    $scope.albumPhotos[key].resultadoAnalise = response.result;
                    $scope.albumPhotos[key].isAnalisada = response.isAnalisada;
                    console.log($scope.albumPhotos[key]);
                });
            });
        });

    };

    $scope.salvarResultado = function (fotoid, resultadoAnalise) {
        resultadoAnalise.forEach(function (item) {
            mineradorService.salvarResultado(fotoid, item.Prediction).then(function (response) {
                resultadoAnalise.saveResult = response.message;
                atualizarTela();
            })
        })
    };

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

            angular.forEach($scope.listaAlbums, function (album, key) {
                facebookService.getAlbumPhotos(album.id, 10).then(function (photosAlbum) {
                    console.log(photosAlbum);
                    album.preview = photosAlbum;
                });
            });

        });

        classifierService.getClassifiers().then(function (response) {
            $scope.listaClassifier = response._embedded.classifier;
            console.log(response._embedded.classifier);
        });
    }
    
}]);