angular.module('app')
.controller('PessoasCtrl', ["$scope", "$window", "pessoasService", "extratorID",
function($scope, $window, pessoasService, extratorID) {

    $scope.extrairID = extratorID.extrair;
    $scope.mensagem = "";
    atualizarTela();

    $scope.resultadoMineracao = function (pessoaId) {
        $window.location.href = "#/resultado-mineracao/"+pessoaId;
    }

    function atualizarTela(){
        pessoasService.getPessoas().then(function (response) {
            $scope.listaPessoas = response._embedded.pessoa;
            console.log($scope.listaPessoas);
        });
    }
}]);