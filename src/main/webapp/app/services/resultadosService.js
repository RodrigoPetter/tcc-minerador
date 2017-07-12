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
                },
            currentUserId:{
                method: 'GET',
                url: '/resultados/currentUserId/:facebookId',
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
        },
        currentUserId: function (facebookId) {
            return resource.currentUserId({"facebookId": facebookId}).$promise;
        }
    };
    return service;
}]);