angular.module('Application').controller(
		'UserProfileController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
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
				}
				
				$scope.tabs = {"home":true, "theaters":false, "cinemas":false, "friends":false, "reservations":false, "settings":false, "fanzone":false};
				$scope.showCinema = function(data){
					$rootScope.currentCinema=data;
					$location.path('/cinema_profile').replace();
				};
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
								
				$scope.reservationView=function(){
					$scope.reserve=true;
					$scope.regular=false;
					$scope.everythingElse=false;
				}
				$scope.searchCinemas=function(){
					var op = document.getElementById("cinemaCombo").getElementsByTagName("option");
					for(var i =0;i<op.length;i++){
						if(op[i].value.toLowerCase().includes($scope.searchCinemaText.toLowerCase())){
							op[i].selected=true;
							break;
						}
					};
				};
				$scope.continueReservation=function(){
					$scope.everythingElse=true;
					var selCinema = document.getElementById("cinemaCombo").selectedIndex;
					$scope.cinemaSelected = $scope.cinemasShow[selCinema];
					$http.get('http://localhost:8181/reguser/movies/'+$scope.cinemaSelected.id).success(function(data,status){
						$scope.moviesShow=data;
					});
				};
				
				$scope.home=function(){
					$scope.tabs = {"home":true, "theaters":false, "cinemas":false, "friends":false, "reservations":false, "settings":false, "fanzone":false};
				};
				$scope.theaters=function(){
					$scope.tabs = {"home":false, "theaters":true, "cinemas":false, "friends":false, "reservations":false, "settings":false, "fanzone":false};
					$scope.reserve=false;
					$scope.regular=true;
				};
				$scope.cinemas=function(){
					$scope.tabs = {"home":false, "theaters":false, "cinemas":true, "friends":false, "reservations":false, "settings":false, "fanzone":false};
					$scope.cinemasShow={};
					$http.get('/cinemas').success(function(data, status){
							$scope.cinemasShow=data;
					});
					$scope.reserve=false;
					$scope.regular=true;
				};
				$scope.friends=function(){
					$scope.tabs = {"home":false, "theaters":false, "cinemas":false, "friends":true, "reservations":false, "settings":false, "fanzone":false};
				};
				$scope.reservations=function(){
					$scope.tabs = {"home":false, "theaters":false, "cinemas":false, "friends":false, "reservations":true, "settings":false, "fanzone":false};
				};
				$scope.settings=function(){
					$scope.tabs = {"home":false, "theaters":false, "cinemas":false, "friends":false, "reservations":false, "settings":true, "fanzone":false};
				};
				$scope.fanzone=function(){
					$scope.tabs = {"home":false, "theaters":false, "cinemas":false, "friends":false, "reservations":false, "settings":false, "fanzone":true};
				};
			}
		]
);