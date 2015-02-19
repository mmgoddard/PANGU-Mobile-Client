/**
 * Created by Mark on 15/02/15.
 */
var webAppControllers = angular.module('webAppControllers', []);

// create the controller and inject Angular's $scope
webAppControllers.controller('viewAllController',
    function($scope, $http) {
        $http.get('http://localhost:11000/api/models').
            success(function(data) {
                $scope.pangu = data;
            });
    });

webAppControllers.controller('viewOneController',
    function($scope, $http) {
        $http.get('http://localhost:11000/api/models/3').
            success(function(data) {
                $scope.pangu = data;
            });
    });
