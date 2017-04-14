angular.module('app',['ngRoute', 'ngResource', 'naif.base64'])
.config(function($routeProvider, $locationProvider)
{
    $routeProvider
        .when('/', {
            templateUrl : 'partials/home.html',
            controller     : 'MineradorCtrl',
        })
        .when('/pessoas', {
            templateUrl : 'partials/pessoas/pessoas.html',
            controller     : 'PessoasCtrl',
        })
        .when('/fotos', {
            templateUrl : 'partials/fotos/fotos.html',
            controller     : 'FotosCtrl',
        })
        .when('/config', {
            templateUrl : 'partials/config.html',
            controller     : 'ConfigCtrl',
        })
        .otherwise ({ redirectTo: '/' });
});