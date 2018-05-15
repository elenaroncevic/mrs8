angular.module('Application').controller(
		'CinemaAdminController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				if($rootScope.cinemaAdmin){
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
				$scope.showCinema = function(data){
					$rootScope.currentCinema=data;
					$location.path('/cinema_profile').replace();
				};
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