angular.module('Application').controller(
		'ReservationTabController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$scope.loadReservations = function(){
					$scope.reservationsShow={};
					var rez = $rootScope.currentUser.reservations;
					for (var x in rez){
						var resShow = {};
						if(rez[x].state=="Active"){
							$http.get('http://localhost:8181/reguser/projection/'+rez[x].tickets[0].id).success(function(data,status){
								resShow["price"]=data.price;
								resShow["date"]=data.date;
								resShow["num"]=data.tickets.length;
								resShow["seats"] = "seats";
							});
							$http.get('http://localhost:8181/reguser/movie/'+rez[x].tickets[0].id).success(function(data,status){
								resShow["title"]=data.title;
							});
							$scope.reservationsShow[rez[x].id]=resShow;
						}
					};
				};
			}
		]
);