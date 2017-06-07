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
        alert("função não implementada");
    };

    function atualizarTela(){
        resultadosService.getTotalFotos($scope.pessoaId).then(function (response) {
            console.log(response);
            $scope.totalFotos = response[0]
        });

        resultadosService.getAparicoes($scope.pessoaId).then(function (response) {
            console.log(response);
            $scope.listaAparicoes = response;

            var a = [];

            angular.forEach(response, function (element) {
                a.push(element);
            });

            a = JSON.stringify(a);
            console.log("vai enviar:");
            console.log(a);
            resultadosService.getAnaliseCondicional($scope.pessoaId, a).then(function () {
               console.log("ENVIADO!");
            });
        });
    }

}]);