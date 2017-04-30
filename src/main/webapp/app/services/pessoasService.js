angular.module('app')
.factory("pessoasService", ["$resource",  function ($resource) {
    var resource = $resource("/pessoa", null,
        {
            getAmizades:
                {
                    method:'GET',
                    url: '/pessoa/:pessoaId/amizades',
                },
            addAmizade:
                {
                    method:'GET',
                    url: '/pessoa/addAmizade',
                },
        });

    var service = {
        getPessoas: function () {
            return resource.get().$promise;
        },
        getAmizades: function (pessoaId) {
            return resource.getAmizades({"pessoaId": pessoaId}).$promise;
        },
        savePessoas: function (dadoFormatado) {
            return resource.save(dadoFormatado).$promise;
        },
        addAmizade: function (pessoaId, novaAmizade) {
            return resource.addAmizade({"pessoaId1": pessoaId, "pessoaId2": novaAmizade}).$promise;
        }
    }
    return service;
}]);