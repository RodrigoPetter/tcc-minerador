angular.module('app')
.controller('FotosCtrl', ["$scope", "fotosService", "pessoasService", "extratorID",  function($scope, fotosService, pessoasService, extratorID) {

    $scope.extrairID = extratorID.extrair;

    atualizarTela();
    pessoasService.getPessoas().then(function (response) {
        $scope.listaPessoas = response._embedded.pessoa;
        console.log($scope.listaPessoas);
    });

    function atualizarTela(){
        fotosService.getFotos().then(function (response) {
            $scope.listaFotos = response._embedded.foto;
            console.log($scope.listaFotos);
        });
    }
}]);