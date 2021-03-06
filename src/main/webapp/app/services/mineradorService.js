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
                },
            apagarBase:
                {
                    method:'DELETE',
                    url: '/minerador/apagar-base',
                }
        });
    var service = {
        identificar: function (perfil, album, foto) {
            return resource.get({"profile-id":perfil, "album-id":album, "foto-id": foto}).$promise;
        },
        gerarClassifiers: function () {
            return resource.gerarTreinamentos().$promise;
        },
        salvarResultado: function (fotoId, pessoaNome) {
            console.log("Vai salvar: "+fotoId+' - '+pessoaNome);
            return resource.salvarResultado({"foto-id": fotoId, "pessoa-nome": pessoaNome}).$promise;
        },
        apagarBase: function () {
            return resource.apagarBase().$promise;
        }
    }
    return service;
}]);