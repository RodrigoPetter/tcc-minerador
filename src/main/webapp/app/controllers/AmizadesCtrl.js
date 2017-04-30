angular.module('app')
.controller('AmizadesCtrl', ["$scope", "$routeParams", "$window", "pessoasService", "extratorID",
function($scope, $routeParams, $window, pessoasService, extratorID) {

    $scope.extrairID = extratorID.extrair;
    $scope.pessoaId = $routeParams.pessoaId;
    atualizarTela();

    $scope.amizades = function (pessoaId) {
        $window.location.href = "#/amizades/"+pessoaId;
    }

    $scope.addAmizade = function () {
        pessoasService.addAmizade($scope.pessoaId, $scope.data.novaAmizadeId).then(function (response) {
            console.log(response);
            atualizarTela();
        });
    };

    function atualizarTela(){
        pessoasService.getAmizades($scope.pessoaId).then(function (response) {
            $scope.listaPessoas = response._embedded.pessoa;
            console.log($scope.listaPessoas);
        });

        pessoasService.getPessoas().then(function (response) {
            var listaNovosAmigos = response._embedded.pessoa;

            $scope.listaNovosAmigos = _.filter(listaNovosAmigos, function (item) {
                if(_.findIndex($scope.listaPessoas, {"nomeCompleto": item.nomeCompleto}) === -1){
                    return item;
                }
                return null;
            });
            console.log($scope.listaNovosAmigos);
        });
    }

}]);