angular.module('Application').controller(
		'ApplicationController',
		[
				'$rootScope',
				'$scope',
				'$http',
				'$routeParams',
				'$location',
				'$window',
				
				function($rootScope, $scope, $http, $routeParams, $location, $window) {
					$rootScope.home_btn=false;
					$rootScope.log_out_btn=false;
					$rootScope.return_home=function(){
						var current = JSON.parse(localStorage.getItem("currentUser"));
						if(current.hasOwnProperty("firstName")){
							$location.path('/profile').replace();
						}else if(current.hasOwnProperty("def")){
							$location.path('/system_admin').replace();
						}else if(current.hasOwnProperty("cinemas")){
							$location.path('/cinema_admin').replace();
						}else{
							$location.path('/fan_zone_admin').replace();
						}
					}
				}
		]
);