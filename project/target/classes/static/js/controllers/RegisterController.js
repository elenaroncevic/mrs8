angular.module('Application').controller(
		'RegisterController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.initUser = function(){
					$scope.user={};				
				};
				$scope.register = function(){
					if($scope.user.password==$scope.pass2){
						$http.post('http://localhost:8181/register/' + $scope.user.email+'/'+$scope.user.password+'/'+$scope.user.firstName+'/'+$scope.user.lastName+'/'+$scope.user.city+'/'+$scope.user.phone).success(function(data, status){
							$location.path('/home').replace();
						}).error(function(){
							alert("User with the given email already exists!");
						});
					}else{
						alert("Passwords don't match! Try again!");
					}
				};
				
			}
		]
);

