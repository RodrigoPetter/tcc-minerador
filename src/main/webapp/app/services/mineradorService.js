angular.module('app')
.factory("mineradorService", ["$resource",  function ($resource) {
    var resource = $resource("/minerador");
    var service = {
        identificar: function (fotoId) {
            return resource.get({"foto-id": fotoId}).$promise;
        }
    }
    return service;
}]);