angular.module('Application').controller(
		'CinemaProfileController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.projections=[];
				var auditorium = {};
				var projection = {};
				for(var a in $rootScope.currentCinema.auditoriums ){
					auditorium = $rootScope.currentCinema.auditoriums[a].projections;
					for (var p in auditorium){
						projection = auditorium[p];
						$scope.projections.push(projection);
						//alert(projection.movie);
					}
				}
			}
		]
	);