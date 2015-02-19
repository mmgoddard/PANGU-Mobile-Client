/**
 * Created by Mark on 15/02/15.
 */
var myappControllers = angular.module('myappControllers', []);

myappControllers.controller('viewCtrl', ['$scope', '$http',
    function ($scope, $http) {
        $http.get('http://localhost:11000/api/models').
            success(function(data) {
                $scope.pangu = data;
            });
    }]);

myappControllers.controller('viewOneCtrl', ['$scope', '$http',
    function ($scope, $http) {
        $http.get('http://localhost:11000/api/models/3').
            success(function(data) {
                $scope.pangu = data;
            });
    }]);