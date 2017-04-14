angular.module('app')
.factory("classifierService", ["$resource",  function ($resource) {
    var resource = $resource("/classifier");
    var service = {
        getClassifiers: function () {
            return resource.get().$promise;
        }
    }
    return service;
}]);