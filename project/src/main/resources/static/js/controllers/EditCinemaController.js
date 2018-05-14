angular.module('Application').controller(
		'EditCinemaController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$scope.changeBasic = function() {
					if($scope.password == $rootScope.currentUser.password){
						$http.post('http://localhost:8181/changeBasic/'+ $scope.currentCinema.id + '/' + $scope.name+'/'+ $scope.location +'/'+$scope.description).success(function( data,status){
							$rootScope.currentCinema=data;
							alert("gotovo");
						}).error(function(){
							alert("Error with input data! Check your email address and password!");
						});
					}
					else{
						alert("pogresan password!");
					}
					$location.path("/cinema_profile").replace();
				};
			}
		]
	);