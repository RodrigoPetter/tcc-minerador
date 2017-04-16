angular.module('app')
.controller('MineradorCtrl', ["$scope", "fotosService", "mineradorService",
    "extratorID", "classifierService",
    function($scope, fotosService, mineradorService, extratorID, classifierService) {

    $scope.data = {};
    $scope.extrairID = extratorID.extrair;

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

    $scope.salvarResultado = function (foto, resultadoAnalise) {
        resultadoAnalise.forEach(function (item) {
            mineradorService.salvarResultado(foto, item.Prediction).then(function (response) {
                atualizarTela();
            })
        })
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