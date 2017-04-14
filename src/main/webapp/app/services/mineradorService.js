angular.module('app')
.factory("mineradorService", ["$resource",  function ($resource) {
    var resource = $resource("/minerador", null,
        {
            gerarTreinamentos:
                {
                    method:'GET',
                    url: '/minerador/treinar',
                }
        });
    var service = {
        identificar: function (fotoId, classifierId) {
            return resource.get({"foto-id": fotoId, "classifier-id":classifierId}).$promise;
        },
        gerarClassifiers: function () {
            return resource.gerarTreinamentos().$promise;
        }
    }
    return service;
}]);