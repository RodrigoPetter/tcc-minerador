angular.module('app')
.controller('ConfigCtrl', ["$scope", "mineradorService", function($scope, mineradorService) {

    $scope.gerando = false;
    $scope.concluido = false;
    $scope.erro = false;

    $scope.gerarClassifiers = function () {

        $scope.gerando = true;

        mineradorService.gerarClassifiers()
            .then(function (response) {
                $scope.concluido = true;
            })
            .catch(function (error) {
                $scope.erro = true;
                console.log(error);
            })
            .finally(function () {
                $scope.gerando = false;
            });
    }

    $scope.zerarBase = function () {
        console.log("vai apagar");
        $scope.msgZerarBase = "Apagando base de dados...";

        mineradorService.apagarBase().then(function (response) {
            console.log(response);
           $scope.msgZerarBase = response.message;
        })
        .catch(function (error) {
            console.log(error);
            $scope.msgZerarBase = error;
        });
    }
    
}]);