angular.module('Application').controller(
		'ReservationTabController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$rootScope.reservationsShow={};
				
				var currentUser = JSON.parse(localStorage.getItem("currentUser"));
				
				$scope.cancelReservation=function(reservation){
					$http.delete('/reguser/deleteReservation/'+reservation.id).success(function(data, status){
						alert('Reservation cancelled');
						var idx = $rootScope.reservationsShow.indexOf(reservation);
						$rootScope.reservationsShow.splice(idx , 1);						
					}).error(function(data, status){
						alert('Reservation cannot be cancelled - less then 30 minutes until the show');
					});
				};
				$scope.loadReservations=function(){
					$http.get('/reguser/reservations/'+currentUser.email).success(function(data, status){
						$rootScope.reservationsShow=data;
					});
				};
			}
		]
);