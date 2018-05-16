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
						localStorage.setItem("currentUser",angular.toJson(data));
						//alert(JSON.parse(localStorage.getItem("currentUser"))
						$rootScope.currentUser=data;
						$rootScope.ru=data.hasOwnProperty("firstName");
						$rootScope.cinemaAdmin=data.hasOwnProperty("cinemas");
						$rootScope.systemAdmin=data.hasOwnProperty("def");
						$rootScope.fanZoneAdmin=!($rootScope.ru || $rootScope.cinemaAdmin || $rootScope.systemAdmin);
						if(data.activated!="yes"){
							alert("You haven't activated your account yet! Check your email!");
							$location.path('/home').replace();
						}
						else{
							if($rootScope.ru){
								alert('otkud');
								$location.path('/profile').replace();
							}else if($rootScope.systemAdmin){
								alert("system");
								$location.path('/system_admin').replace();
							}else if($rootScope.cinemaAdmin){
								alert('dobroe');
								$location.path('/cinema_admin').replace();
							}else{
								alert("fza");
								$location.path('/fan_zone_admin').replace();
							};
						}
				}).error(function(){
					alert("Error with input data! Check your email address and password!");
				});
				};
			}
		]
);