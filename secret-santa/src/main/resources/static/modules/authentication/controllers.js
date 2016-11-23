'use strict';
 
angular.module('Authentication')
 
.controller('LoginController',
    ['$scope', '$rootScope', '$location', 'AuthenticationService',
    function ($scope, $rootScope, $location, AuthenticationService) {
        // reset login status
        //AuthenticationService.ClearCredentials();
 
        $scope.login = function () {
            $scope.dataLoading = true;
            AuthenticationService.Login($scope.username, $scope.password, function(response) {
                if(response.success) {
                    //AuthenticationService.SetCredentials($scope.username, $scope.password);
                	console.log("LOGIN=[SUCCESS]");
                    $location.path('/secret_santa');
                } else {
                    $scope.error = response.message;
                    console.log("LOGIN=[FAILURE]");
                    $scope.dataLoading = false;
                }
            });
        };
    }]);