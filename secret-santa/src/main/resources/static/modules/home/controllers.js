(function(angular) {
	'use strict';

	angular.module('Home')

	.controller('HomeController',
			[ '$scope', '$uibModal', '$http', function($scope, $uibModal, $http) {
				
				$http.get('/userdata').then(
						function(response){
							$scope.data = response.data;
						},
						function(response){
							$scope.data = {vote: 0};
						});
				
				$scope.$watch('data.vote', function(){
					$http.post('/userdata', $scope.data).then(
							function(response){
								
							},
							function(response){
								
							});
				});

				$scope.changePasswordOpen = function() {
					$uibModal.open({
						animation : false,
						templateUrl : 'changePasswordModal.html',
						size : 'sm',
						controller : function($uibModalInstance, $scope) {
							$scope.message = "";
							$scope.data = {
									password1: "",
									password2: ""
							};
							
							$scope.changePasswordSave = function() {
								$scope.message = "";
								$http.post('/change/password', $scope.data).then(
										function(response){
											$scope.message = response.data;
										},
										function(response){
											$scope.message = response.data;
										});
							};

							$scope.changePasswordExit = function() {
								$uibModalInstance.dismiss('cancel');
							};
						}
					});
				};

			} ])
})(angular);