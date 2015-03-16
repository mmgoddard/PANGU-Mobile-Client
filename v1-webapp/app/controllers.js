/**
 * Created by Mark on 15/02/15.
 */
var webAppControllers = angular.module('webAppControllers', [])

webAppControllers.controller('viewModelsController',  [
    '$scope',
    '$http',
    '$location',
    function($scope, $http, $location) {
        $http.get('http://localhost:11000/api/models').
            success(function(data) {
                $scope.pangu = data;
            });

        $scope.setSelected = function(x) {
            var url = '/models/'+x.id;
            console.log(url);
            $location.path(url);
        };
    }]);

webAppControllers.controller('editModelController', [
    '$scope',
    '$http',
    '$routeParams',
    function($scope, $http, $routeParams) {
        var selectedId = $routeParams.id;
        console.log(selectedId);

        $http.get('http://localhost:11000/api/models/'+selectedId).
            success(function(data) {
                $scope.pangu = data;
            });

        $scope.setSubmit = function(x) {
            $scope.json = angular.toJson(x);
            $http.post('http://localhost:11000/api/models/add', $scope.json).
                success(function (data, status, headers, config) {

                }).
                error(function (data, status, headers, config) {

                });
        };
    }]);