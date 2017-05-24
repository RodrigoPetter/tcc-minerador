angular.module('app')
.controller('DesconectarFacebookCtrl', ["$resource",
function($resource) {

    var resource = $resource("/perfil/", null,
        {
            desconectar: {
                method: 'DELETE',
                url: '/connect/facebook',
            }
        });

    console.log('desconectar1');
    resource.desconectar().$promise.then(function (response) {
        console.log('desconectar2');
        console.log(response)
    });
    console.log('desconectar3');

}]);