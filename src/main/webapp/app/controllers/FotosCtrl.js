angular.module('app')
.controller('FotosCtrl', ["$scope", "fotosService", "pessoasService", "extratorID",  function($scope, fotosService, pessoasService, extratorID) {

    $scope.data = {};
    $scope.extrairID = extratorID.extrair;

    atualizarTela();
    pessoasService.getPessoas().then(function (response) {
        $scope.listaPessoas = response._embedded.pessoa;
        console.log($scope.listaPessoas);
    });


    $scope.incluir = function () {
        dadoFormatado = {
            imagem: $scope.data.imagem.base64,
            analisada: false,
            owner: $scope.listaPessoas[$scope.data.owner]._links.self.href
        }
        console.log("dadoFormatado:");
        console.log(dadoFormatado);

        fotosService.save(dadoFormatado)
            .then(function (response) {
                $scope.mensagem = "Foto incluida com sucesso.";
                atualizarTela();
            })
            .catch(function (erro) {
                $scope.mensagem = "Erro ao incluir a foto. Mais detalhes no console.";
                console.log(erro);
            });
    }

    function atualizarTela(){
        fotosService.getFotos().then(function (response) {
            $scope.listaFotos = response._embedded.foto;
            console.log($scope.listaFotos);
        });
    }
}]);