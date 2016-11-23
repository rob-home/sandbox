'use strict';
 
angular.module('Authentication')
 
.factory('AuthenticationService',
    ['$http', '$cookieStore', '$rootScope', '$timeout',
    function ($http, $cookieStore, $rootScope, $timeout) {
        var service = {};

        service.Login = function (username, password, callback) {
            $http.post('/login', { username: username, password: password })
              .then(function successCallback(response) {
            	  response.success = true;
            	  callback(response);
              }, function errorCallback(response) {
            	  response.message = response.data ? response.data : 'Username or password is incorrect';
            	  callback(response);
              });
        };
        return service;
}]); 
