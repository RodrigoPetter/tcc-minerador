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
    
}]);