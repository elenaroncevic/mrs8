angular.module('Application').controller(
		'CinemaListController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.poy="cao"
				$scope.list_cinemas = function() {
					$http.get('http://localhost:8181/cinemas').success(function(data, status){
							$scope.cinemaList=data;
							//alert(cinemaList);
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

