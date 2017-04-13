angular.module('app')
.controller('MineradorCtrl', ["$scope", "fotosService", "mineradorService", "extratorID",  function($scope, fotosService, mineradorService, extratorID) {

    $scope.data = {};
    $scope.extrairID = extratorID.extrair;

    atualizarTela();
    
    $scope.analisar = function (id) {
        mineradorService.identificar(id).then(function (response) {
            $scope.listaFotos.map(function (item, index, array) {
                if($scope.extrairID(item) === id){
                    array[index].resultadoAnalise = response.result;
                }
            });

        });
    }

    function atualizarTela(){
        fotosService.getFotosAnalise().then(function (response) {
            $scope.listaFotos = response._embedded.foto;
            console.log($scope.listaFotos);
        });
    }
    
}]);