angular.module('app')
.factory("pessoasService", ["$resource",  function ($resource) {
    var resource = $resource("/pessoa", null,
        {
            getById:
                {
                    method:'GET',
                    url: '/pessoa/:pessoaId',
                }
        });

    var service = {
        getPessoas: function () {
            return resource.get().$promise;
        },
        getPessoa: function (pessoaId) {
            return resource.getById({"pessoaId": pessoaId}).$promise;
        }
    }
    return service;
}]);