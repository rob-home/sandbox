'use strict';
 
angular.module('Authentication')
 
.controller('LoginController',
    ['$scope', '$rootScope', '$location', 'AuthenticationService', '$uibModal',
    function ($scope, $rootScope, $location, AuthenticationService, $uibModal) {
        $scope.login = function () {
            $scope.dataLoading = true;
            AuthenticationService.Login($scope.username, $scope.password, function(response) {
                if(response.success) {
                    $location.path('/secret_santa');
                } else {
                	$uibModal.open({
                		animation: false,
                		templateUrl: 'messageModal.html',
                		size: 'sm',
                		controller: function($scope) {
                			$scope.title = 'Error';
                			$scope.message = response.message;
                		}
                	});
                    $scope.dataLoading = false;
                }
            });
        };
    }]);