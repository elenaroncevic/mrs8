angular.module('Application').controller(
		'UserProfileController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$rootScope.home_btn=true;
				$rootScope.log_out_btn=true;
				$scope.currentUser=JSON.parse(localStorage.getItem("currentUser"));
				$( function() {
    				$( "#datepicker" ).datepicker();
				} );
				$scope.tabs = {"home":true, "theaters":false, "cinemas":false, "friends":false, "reservations":false, "settings":false, "fanzone":false};
				$scope.showCinema = function(data){
					$rootScope.currentCinema=data;
					localStorage.setItem("currentCinema",angular.toJson(data));
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