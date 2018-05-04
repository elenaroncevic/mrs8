angular.module('Application').controller(
		'RegisterController',
		[
			'$scope',
			'$window',
			'$location', 
			function($scope, $window, $location) {
				$scope.register = function() {
					$location.path('/home').replace();
				};

			}
		]
);

