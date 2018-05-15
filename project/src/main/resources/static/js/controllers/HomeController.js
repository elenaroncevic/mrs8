angular.module('Application').controller(
		'HomeController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$rootScope.a=true;
				$scope.signup = function(){
					$location.path('/signup').replace();
				};
				$scope.list_cinemas = function(){
					$location.path('/list_cinemas').replace();
				};
				$scope.login = function() {
					$http.post('http://localhost:8181/loguser/'+ $scope.email+'/'+ $scope.pass).success(function(data, status){
							$rootScope.currentUser=data;
							if(data.activated=="yes"){
								if('firstName' in data){
									alert('otkud');
									$location.path('/profile').replace();
								}else if('def' in data){
									$location.path('/system_admin').replace();
								}else if('cinemas' in data){
									alert('dobroe');
									$location.path('/cinema_admin').replace();
								}else{
									$location.path('/fan_zone_admin').replace();
								};
							}else{
								alert("You haven't activated your account yet! Check your email!");
								$location.path('/home').replace();
							};
				}).error(function(){
					alert("Error with input data! Check your email address and password!");
				});
				};
			}
		]
);