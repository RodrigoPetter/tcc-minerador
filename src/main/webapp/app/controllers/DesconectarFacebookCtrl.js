angular.module('app')
.controller('DesconectarFacebookCtrl', ["$resource", "$location",
function($resource, $location) {

    var resource = $resource("/connect/facebook", null,
        {
            desconectar: {
                method: 'DELETE',
                url: '/connect/facebook',
            }
        });

    resource.desconectar().$promise.then(function (response) {
        console.log(response);
        $location.url("/");
    }).catch(function (response) {
        console.log(response);
        $location.url("/");
    });

}]);