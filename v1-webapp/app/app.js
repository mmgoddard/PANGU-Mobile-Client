var webApp = angular.module('web-app', ['webAppControllers', 'ngRoute']);

//Configure our routes
webApp.config(function($routeProvider, $locationProvider) {
    $routeProvider

        //Route for the homepage
        .when('/', {
            templateUrl : 'views/login.html',
            controller : 'loginController'
        })

        //Route for viewing all models
        .when('/models', {
            templateUrl : 'views/viewModels.html',
            controller  : 'viewModelsController'
        })

        //Route for editing an model
        .when('/models/:id', {
            templateUrl : 'views/editModel.html',
            controller  : 'editModelController'
        })

        //Route for adding an new model
        .when('/add', {
            templateUrl : 'views/addNewModel.html',
            controller : 'addNewModelController'
        })

    //Check browser support
    //if(window.history && window.history.pushState){
    //    //Use the HTML5 History API clean URLs (no has tag in URL)
    //    $locationProvider.html5Mode({
    //        enabled: true,
    //        requireBase: false
    //    });
    //}
});