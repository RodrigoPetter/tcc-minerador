angular.module('app')
.factory("resultadosService", ["$resource",  function ($resource) {
    var resource = $resource("/resultados/:pessoaId", null,
        {
            getAparicoes:
                {
                    method:'GET',
                    url: '/resultados/:pessoaId/aparicoes',
                    isArray: true
                },
            getTotalFotos:
                {
                    method:'GET',
                    url: '/resultados/:pessoaId/total-fotos',
                    isArray: false
                },
            getAnaliseCondicional:
                {
                    method:'POST',
                    url: '/resultados/:pessoaId/condicional',
                    isArray: false
                }
        });

    var service = {
        getAparicoes: function (pessoaId) {
            return resource.getAparicoes({"pessoaId": pessoaId}).$promise;
        },
        getTotalFotos: function (pessoaId) {
            return resource.getTotalFotos({"pessoaId": pessoaId}).$promise;
        },
        getAnaliseCondicional: function (pessoaId, listAparicoes) {
            return resource.getAnaliseCondicional({"pessoaId": pessoaId}, listAparicoes).$promise;
        }
    };
    return service;
}]);