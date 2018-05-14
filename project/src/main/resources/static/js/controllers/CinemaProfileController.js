angular.module('Application').controller(
		'CinemaProfileController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.getProjections= function(){
					$scope.projections = [];
					$scope.p2=[];
					for ( let i in $rootScope.currentCinema.auditoriums) { 
						for (let p in $rootScope.currentCinema.auditoriums[i].projections){
							let projection = $rootScope.currentCinema.auditoriums[i].projections[p];
							$http.get('http://localhost:8181/getProjectionMovie/'+ projection.id).success(function( data,status){
								projection.auditorium = $rootScope.currentCinema.auditoriums[i].id;
								projection.movie = data;
								$scope.projections.push(projection);
							}).error(function(){
								alert("error movie");
							});
						}
					}
					
					$scope.editBasicInformations=function(){
						$location.path("/edit_cinema_basic").replace();
					};
				}
			}
		]
	);