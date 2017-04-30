angular.module('app')
.controller('PessoasCtrl', ["$scope", "$window", "pessoasService", "extratorID",
function($scope, $window, pessoasService, extratorID) {

    $scope.extrairID = extratorID.extrair;
    $scope.mensagem = "";
    $scope.incluirNome = "";
    atualizarTela();

    $scope.amizades = function (pessoaId) {
        $window.location.href = "#/amizades/"+pessoaId;
    }

    $scope.incluir = function () {
        dadoFormatado = {
            "nomeCompleto": $scope.incluirNome,
        };

        pessoasService.savePessoas(dadoFormatado)
        .then(function (response) {
            $scope.mensagem = "Pessoa incluida com sucesso.";
            atualizarTela();
        })
        .catch(function (erro) {
            $scope.mensagem = "Erro ao incluir a pessoa. Mais detalhes no console.";
            console.log(erro);
        });
    };

    function atualizarTela(){
        pessoasService.getPessoas().then(function (response) {
            $scope.listaPessoas = response._embedded.pessoa;
            console.log($scope.listaPessoas);
        });
    }
}]);