angular.module('app')
.factory("pessoasService", ["$resource",  function ($resource) {
    resource = $resource("/pessoa");
    var service = {
        getPessoas: function () {
            return resource.get().$promise;
        },
        savePessoas: function (dadoFormatado) {
            return resource.save(dadoFormatado).$promise;
        }
    }
    return service;
}]);