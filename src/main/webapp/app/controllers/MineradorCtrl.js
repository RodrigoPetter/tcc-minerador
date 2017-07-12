angular.module('app')
.controller('MineradorCtrl', ["$scope", "facebookService", "mineradorService",
    "extratorID", "$location", "resultadosService",
    function($scope, facebookService, mineradorService, extratorID, $location, resultadosService) {

    $scope.data = {};
    $scope.extrairID = extratorID.extrair;
    $scope.albumSelecionado = false;
    $scope.currentUserId = 0;

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

                mineradorService.identificar(perfil, album, fotoId).then(function (response) {
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

            if($scope.profileData !== undefined) {
                resultadosService.currentUserId($scope.profileData.id).then(function (response) {
                    console.log("currentUserId");
                    console.log(response);
                    $scope.currentUserId = response.currentUserId;
                });
            }
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

    }

    $scope.ver = function () {
        $location.url('/resultado-mineracao/'+$scope.currentUserId);
    }
    
}]);