angular.module('Application').controller(
		'HomeController',
		[
			'$scope',
			'$window',
			'$location', 
			function($scope, $window, $location) {
				$scope.signin = function() {
					$location.path('/signin').replace();
				};
				$scope.signup = function(){
					$location.path('/signup').replace();
				};
			}
		]
);