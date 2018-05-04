angular.module('Application').controller(
		'LoginController',
		[
			'$scope',
			'$window',
			'$location', 
			function($scope, $window, $location) {
				$scope.login = function() {
					$location.path('/home').replace();
				};

			}
		]
);

