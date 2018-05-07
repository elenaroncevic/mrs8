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
					$scope.helpy={};		
				};
				$scope.register = function() {
					if($scope.user.pass==$scope.pass2){
						$http.post('api/register/', $scope.helpy).success(function(data){
							$location.path('/profile').replace();
						}).error(function(data){
							alert("There is already an account signed-up with that e-mail address! Try signing-in or use different address!");
							$rootScope.currentUser = "ptikaz";
							$location.path('/profile').replace();
						});
					}else{
						alert("Passwords don't match, try again!");
					}
				};
				$scope.help = function(){
					$http.post('help/', $scope.helpy).success(function(data,status){
						$rootScope.currentUser=data.ime;
					});
				};
			}
		]
);

