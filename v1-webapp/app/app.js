/**
 * Created by Mark on 15/02/15.
 */
'use strict';
// Declare app level module which depends on views, and components
var myapp = angular.module('myapp', [
    'myappControllers',
    'myapp.view',
    'myapp.viewOne'
]);

myapp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/view', {
                templateUrl: 'views/view.html',
                controller: 'viewCtrl'
            }).
            when('/viewOne', {
                templateUrl: 'views/viewOne.html',
                controller: 'viewOneCtrl'
            }).
            otherwise({
                redirectTo: '/index.html'
            });
    }]);