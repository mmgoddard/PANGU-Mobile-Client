/**
 * Created by Mark on 15/02/15.
 */
var webAppControllers = angular.module('webAppControllers', [])

//Global Variables
var addUpdateModelAddress = 'http://localhost:11000/api/models/add';
var deleteViewModelAddress = 'http://localhost:11000/api/models/';

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
    '$location',
    function($scope, $http, $routeParams, $location) {
        var selectedId = $routeParams.id;
        console.log("View Model: "+selectedId);

        //Get model based on ID
        $http.get(deleteViewModelAddress+selectedId).
            success(function(data) {
                $scope.pangu = data;
            });

        //Update Model
        $scope.updateModel = function(x) {
            console.log("Update Model: "+ x.id)
            $scope.json = angular.toJson(x);
            $http.post(addUpdateModelAddress, $scope.json).
                success(function (data, status, headers, config) {

                }).
                error(function (data, status, headers, config) {

                });
        };

        //Delete Model
        $scope.deleteModel = function(id) {
            console.log("Delete Model: "+id);
            $http.delete(deleteViewModelAddress+id).
                success(function (data, status, headers, config) {

                }).
                error(function (data, status, headers, config) {

                });
            $location.path("/models");
        }
    }]);

webAppControllers.controller('addNewModelController', [
    '$scope',
    '$http',
    '$location',
    function($scope, $http, $location) {
        console.log("Add New Model");

        $scope.addModel = function(panguRecord) {
            $scope.json = angular.toJson(panguRecord);
            $http.post(addUpdateModelAddress, $scope.json).
                success(function (data, status, headers, config) {

                }).
                error(function (data, status, headers, config) {

                });
            $location.path("/models");
        }
    }]);

webAppControllers.controller('loginController', [
    '$scope',
    '$http',
    '$location',
    function($scope, $http, $location) {
        $scope.setLogin = function(login) {
            if ($scope.loginForm.$valid) {
                console.log("Login");
                $location.path("/models");
            }
        }
    }]);