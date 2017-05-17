angular.module('app')
.controller('ResultadoMineracaoCtrl', ["$scope", "$routeParams", "pessoasService", "resultadosService", "extratorID",
function($scope, $routeParams, pessoasService, resultadosService, extratorID) {

    $scope.extrairID = extratorID.extrair;
    $scope.pessoaId = $routeParams.pessoaId;
    pessoasService.getPessoa($scope.pessoaId).then(function (response) {
        $scope.nomePessoa =  response.nomeCompleto;
        atualizarTela();
    });

    $scope.addAmizade = function (pessoaIdNovaAmizade) {
        pessoasService.addAmizade($scope.pessoaId, pessoaIdNovaAmizade).then(function (response) {
            console.log(response);
            atualizarTela();
        });
    };

    function atualizarTela(){
        resultadosService.getTotalFotos($scope.pessoaId).then(function (response) {
            console.log(response);
            $scope.totalFotos = response[0]
        });

        resultadosService.getAparicoes($scope.pessoaId).then(function (response) {
            console.log(response);
            $scope.listaAparicoes = response
        });
    }

}]);