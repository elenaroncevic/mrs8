angular.module('Application').controller(
		'RegisterController',
		[
			'$scope',
			'$window',
			'$location', 
			function($scope, $window, $location) {
				$scope.initUser = function(){
					$scope.user={};				
				};
				$scope.register = function() {
					if($scope.user.pass==pass2){
						$http.post('register/', $scope.user).success(function(){
							$location.path('/home').replace();
						}).error(function(){
							alert("There is already an account signed-up with that e-mail address! Try signing-in or use different address!");
						});
					}else{
						alert("Passwords don't match, try again!");
				};

			}
		]
);

