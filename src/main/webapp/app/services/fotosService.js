angular.module('app')
.factory("fotosService", ["$resource",  function ($resource) {
    resource = $resource("/foto");
    var service = {
        getFotos: function () {
            return resource.get().$promise;
        },
        save: function (dadoFormatado) {
            return resource.save(dadoFormatado).$promise;
        }
    }
    return service;
}]);