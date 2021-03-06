/**
 * Created by Mark on 15/02/15.
 */
var webAppControllers = angular.module('webAppControllers', [])

//Global Variables
var addModelAddress = 'http://restapi-panguapp.rhcloud.com/api/models/add';
var updateModelAddress = 'http://restapi-panguapp.rhcloud.com/api/models/update';
var deleteViewModelAddress = 'http://restapi-panguapp.rhcloud.com/api/models/';

webAppControllers.controller('viewModelsController', [
    '$scope',
    '$http',
    '$location',
    function ($scope, $http, $location) {
        $http.get('http://restapi-panguapp.rhcloud.com/api/models/').
            success(function (data) {
                $scope.pangu = data;
            });

        $scope.setSelected = function (x) {
            var url = '/models/' + x.id;
            console.log(url);
            $location.path(url);
        };
    }]);

webAppControllers.controller('editModelController', [
    '$scope',
    '$http',
    '$routeParams',
    '$location',
    function ($scope, $http, $routeParams, $location) {
        var selectedId = $routeParams.id;
        console.log("View Model: " + selectedId);

        //Get model based on ID
        $http.get(deleteViewModelAddress + selectedId).
            success(function (data) {
                $scope.pangu = data;
            });

        //Update Model
        $scope.updateModel = function (x) {
            if ($scope.editModelForm.$valid) {
                console.log("Update Model: " + x.id)
                $scope.json = angular.toJson(x);
                $http.post(updateModelAddress, $scope.json).
                    success(function (data, status, headers, config) {

                    }).
                    error(function (data, status, headers, config) {

                    });
            }
        };

        //Delete Model
        $scope.deleteModel = function (id) {
            console.log("Delete Model: " + id);
            $http.delete(deleteViewModelAddress + id).
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
    function ($scope, $http, $location) {
        $scope.addModel = function (panguRecord) {
            if ($scope.addModelForm.$valid) {
                console.log("Add New Model");
                $scope.json = angular.toJson(panguRecord);
                $http.post(addModelAddress, $scope.json).
                    success(function (data, status, headers, config) {

                    }).
                    error(function (data, status, headers, config) {

                    });
                $location.path("/models");
            }
        }
    }]);

webAppControllers.controller('loginController', [
    '$scope',
    '$http',
    '$location',
    function ($scope, $http, $location) {
        $scope.setLogin = function (credentials) {
            console.log("Username: " + credentials.username);
            console.log("Password: " + credentials.pass);
            if ($scope.loginForm.$valid) {
                console.log("Login");
                $location.path("/models");
            }
        }
    }]);