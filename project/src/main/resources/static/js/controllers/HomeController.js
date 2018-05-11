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
						$rootScope.ru=data.hasOwnProperty("firstName");
						$rootScope.cinemaAdmin=data.hasOwnProperty("cinemas");
						$rootScope.systemAdmin=data.hasOwnProperty("def");
						$rootScope.fanZoneAdmin=!($rootScope.ru || $rootScope.cinemaAdmin || $rootScope.systemAdmin);
						if($rootScope.ru){
							if(data.activated!="yes"){
								$rootScope.ru = false;
								alert("You haven't activated your account yet! Check your email!");
								$location.path('/home').replace();
							}
						}	
						$location.path('/profile').replace();
				}).error(function(){
					alert("Error with input data! Check your email address and password!");
				});
				};
			}
		]
);