angular.module('app',['ngRoute', 'ngResource'])
.config(function($routeProvider, $locationProvider)
{
    $routeProvider
        .when('/', {
            templateUrl : 'partials/home.html',
        })
        .when('/pessoas', {
            templateUrl : 'partials/pessoas/pessoas.html',
            controller     : 'PessoasCtrl',
        })
        .when('/fotos', {
            templateUrl : 'partials/fotos/fotos.html',
            controller     : 'FotosCtrl',
        })
        .otherwise ({ redirectTo: '/' });
});