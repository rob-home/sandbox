'use strict';
 
angular.module('Home')
 
.factory('HomeService',
    ['$http', '$rootScope',
    function ($http, $rootScope) {
        var service = {};

        service.logout = function () {
        	$http.get('/logout');
        };
        
        return service;
}]); 