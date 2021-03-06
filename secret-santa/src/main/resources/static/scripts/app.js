'use strict';

// declare modules
angular.module('Authentication', []);
angular.module('Home', []);

angular.module('SimpleServer', [
    'directives',
    'Authentication',
    'Home',
    'ngRoute',
    'ngCookies',
    'ui.bootstrap'
])
 
.config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/login', {
            controller: 'LoginController',
            templateUrl: 'modules/authentication/views/index.html',
            hideMenus: true
        })
 
        .when('/secret_santa', {
            controller: 'HomeController',
            templateUrl: 'modules/home/views/index.html'
        })
 
        .otherwise({ redirectTo: '/login' });
}])

.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
        	$http.get('/validate').then(function(response){
        	},
        	function(response){
        		console.log("FAILED_VALIDATE=[STATUS=[" + response.status + "], DATA=[" + response.data + "]]")
        		//event.preventDefault();
        		$location.path('/login')
        	});
        });
}]);
