angular.module('directives', [])

.directive('ngSnow', function () {
    console.log("Directive was run");
    return {
    	restrict: 'AEC',
        link: function (scope, elem, attrs) {
        	console.log(elem);
            console.log("Link was called");
            
            scope.data = {
            	'w': $(elem).width,
            	'a': '111',
            	'b': '999'
            };
            
            console.log(scope.data);
        }
}});