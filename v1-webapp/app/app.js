var webApp = angular.module('web-app', ['ngRoute', 'webAppControllers']);

//Configure our routes
webApp.config(function($routeProvider, $locationProvider) {
    $routeProvider

        //Route for the home page
        .when('/', {
            templateUrl : 'views/viewAll.html',
            controller  : 'viewAllController'
        })

        // route for the about page
        .when('/viewOne', {
            templateUrl : 'views/viewOne.html',
            controller  : 'viewOneController'
        });

    //Use the HTML5 History API
    $locationProvider.html5Mode(true);
});