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
<<<<<<< HEAD
				};
				$rootScope.loadReservations();
				showReservation=function(){
					let num=0;
					for (let x in rez){
						let resShow = {};
						resShow["id"]=rez[x].id;
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
											stringy=stringy+data[y].rowNum+data[y].number+",";
										};
										resShow["seats"]=stringy;
										$scope.reservationsShow[num]=resShow;
										num=num+1;
									});
								});
							});
						}
					};
=======
>>>>>>> branch 'master' of https://github.com/elenaroncevic/mrs8
				};
			}
		]
);