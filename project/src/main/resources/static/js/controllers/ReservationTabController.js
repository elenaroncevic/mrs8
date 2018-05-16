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
					let rez = $rootScope.currentUser.reservations;
					let num=0;
					for (let x in rez){
						let resShow = {};
						if(rez[x].state=="Active"){
							$http.get('http://localhost:8181/reguser/projection/'+rez[x].tickets[0].id).success(function(data,status){
								resShow["price"]=data.price;
								resShow["date"]=data.date+" " +data.time;
								$http.get('http://localhost:8181/reguser/movie/'+rez[x].tickets[0].id).success(function(data,status){
									resShow["title"]=data.title;
									$http.get('http://localhost:8181/reguser/reservseats/'+rez[x].id).success(function(data, status){
										resShow["num"]=data.length;
										let stringy="";
										for(let y in data){
											stringy=stringy+data[y].number+",";
										};
										resShow["seats"]=stringy;
										$scope.reservationsShow[num]=resShow;
										num=num+1;
									});
								});
							});
						}
					};
				};
			}
		]
);