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
							$scope.cinemaList=[{"name":"ime","location":"negde","description":"caocaocao","rating":4.2,"id":1,"auditoriums":[],"promos":[],"rates":[]},{"name":"ime","location":"negde","description":"caocaocao","rating":4.2,"id":2,"auditoriums":[],"promos":[],"rates":[]}];
							if(cinemaList.isEmpty()=="yes"){
								alert("There is no cinemas yet");
							}
					}).error(function(){
						alert("ima neki error");
					});
				};
			}
		]
);

