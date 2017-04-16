angular.module('app')
.factory("mineradorService", ["$resource",  function ($resource) {
    var resource = $resource("/minerador", null,
        {
            gerarTreinamentos:
                {
                    method:'GET',
                    url: '/minerador/treinar',
                },
            salvarResultado:
                {
                    method:'GET',
                    url: '/minerador/salvarResultado',
                }
        });
    var service = {
        identificar: function (fotoId, classifierId) {
            return resource.get({"foto-id": fotoId, "classifier-id":classifierId}).$promise;
        },
        gerarClassifiers: function () {
            return resource.gerarTreinamentos().$promise;
        },
        salvarResultado: function (fotoId, pessoaNome) {
            console.log("Vai salvar: "+fotoId+' - '+pessoaNome);
            return resource.salvarResultado({"foto-id": fotoId, "pessoa-nome": pessoaNome}).$promise;
        }
    }
    return service;
}]);