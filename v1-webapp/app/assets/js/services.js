/**
 * Created by Mark on 15/02/15.
 */
function findAll($scope, $http) {
    $http.get('http://localhost:11000/api/findAll').
        success(function(data) {
            $scope.pangu = data;
        });
}