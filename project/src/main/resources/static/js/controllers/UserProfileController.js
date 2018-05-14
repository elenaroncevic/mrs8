angular.module('Application').controller(
		'UserProfileController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$( function() {
    				$( "#datepicker" ).datepicker();
				} );
				
				
				if($rootScope.cinemaAdmin){
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
					};
				}
			
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
				};
				$scope.tabs = {"home":true, "theaters":false, "cinemas":false, "friends":false, "reservations":false, "settings":false, "fanzone":false};
				$scope.showCinema = function(data){
					$rootScope.currentCinema=data;
					$location.path('/cinema_profile').replace();
				};
				$scope.reservationView=function(){
					$scope.reserve=true;
					$scope.regular=false;
				}
				
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
					$rootScope.cinemasShow={};
					$http.get('/cinemas').success(function(data, status){
							$rootScope.cinemasShow=data;
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