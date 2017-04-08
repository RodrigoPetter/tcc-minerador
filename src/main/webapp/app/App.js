var app = angular.module('app',['ngRoute']);

app.config(function($routeProvider, $locationProvider)
{

    $routeProvider
        .when('/', {
            templateUrl : 'partials/home.html',
        })
        .when('/pessoas', {
            templateUrl : 'partials/pessoas/pessoas.html',
            controller     : 'PessoasCtrl',
        })
        .otherwise ({ redirectTo: '/' });
});

app.controller('PessoasCtrl', function($scope) {

    $scope.message = 'Everyone come and see how good I look!';
});