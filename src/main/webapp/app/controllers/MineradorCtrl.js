angular.module('app')
.controller('MineradorCtrl', ["$scope", "fotosService", "mineradorService",
    "extratorID", "classifierService",
    function($scope, fotosService, mineradorService, extratorID, classifierService) {

    $scope.data = {};
    $scope.extrairID = extratorID.extrair;
    $scope.selectedClassifier = 0;

    atualizarTela();
    
    $scope.analisar = function (id) {
        mineradorService.identificar(id, $scope.selectedClassifier).then(function (response) {
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

        classifierService.getClassifiers().then(function (response) {
            $scope.listaClassifier = response._embedded.classifier;
            console.log(response._embedded.classifier);
        });
    }
    
}]);