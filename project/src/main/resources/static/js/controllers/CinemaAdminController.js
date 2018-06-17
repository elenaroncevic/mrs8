angular.module('Application').controller( 
		'CinemaAdminController',
		[ 
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				/*if($rootScope.cinemaAdmin){
					$scope.cinemaList = [];
					$scope.theaterList = [];
					var all = $rootScope.currentUser.cinemas;
					for(var i in all){
						if(all[i].type=="Cinema"){
							$scope.cinemaList.push(all[i]);
						}
						else{
							$scope.theaterList.push(all[i]);
							
						}
					};
				}*/
				$scope.showCinema = function(data){
					$rootScope.currentCinema=data;
					localStorage.setItem("currentCinema",angular.toJson(data));
					$location.path('/cinema_profile').replace();
				};
				$rootScope.currentUser = JSON.parse(localStorage.getItem("currentUser"));
				$scope.cinemaList = [];
				$scope.theaterList = [];
				var all = $rootScope.currentUser.cinemas;
				for(var i in all){
					if(all[i].type=="Cinema"){
						$scope.cinemaList.push(all[i]);
					}
					else{
						$scope.theaterList.push(all[i]);
						
					}
				};
			}
		]
);