angular.module('app',['ngRoute', 'ngResource', 'naif.base64'])
.config(function($routeProvider)
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
        .when('/desconectar-facebook', {
            templateUrl : 'partials/desconectar.html',
            controller     : 'DesconectarFacebookCtrl',
        })
        .when('/amizades/:pessoaId?', {
            templateUrl : 'partials/pessoas/amizades.html',
            controller     : 'AmizadesCtrl',
        })
        .when('/resultado-mineracao/:pessoaId?', {
            templateUrl : 'partials/pessoas/resultadoMineracao.html',
            controller     : 'ResultadoMineracaoCtrl',
        })
        .otherwise ({ redirectTo: '/' });
});