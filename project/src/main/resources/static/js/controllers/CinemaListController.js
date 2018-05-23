angular.module('Application').controller(
		'CinemaListController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.list_cinemas = function() {
					$http.get('http://localhost:8181/cinemas').success(function(data, status){
							$scope.cinemaList=data;
							if(data.length==0){
								alert("There is no cinemas yet");
							}
					}).error(function(){
						alert("ima neki error");
					});
				};
			
				$scope.list_theaters = function() {
					$http.get('http://localhost:8181/theaters').success(function(data, status){
							$scope.theaterList=data;
							if(data.length==0){
								alert("There is no theaters yet");
							}
					}).error(function(){
						alert("ima neki error");
					});
				};
			}
		]
);

