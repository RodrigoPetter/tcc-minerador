angular.module('app')
.factory("fotosService", ["$resource",  function ($resource) {
    var resource = $resource("/foto", null,
        {
            getFotosAnalise:
                {
                    method:'GET',
                    url: '/foto/search/findByAnalisada',
                },

        });

    var service = {
        getFotos: function () {
            return resource.get().$promise;
        },
        save: function (dadoFormatado) {
            return resource.save(dadoFormatado).$promise;
        },
        getFotosAnalise: function () {
            return resource.getFotosAnalise({"analisada": false}).$promise;
        }
    }
    return service;
}]);