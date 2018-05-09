angular.module('Application').controller(
		'UserProfileController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.homeTab=true;
				$scope.theatersTab = false;
				$scope.cinemasTab = false;
				$scope.friendsTab = false;
				$scope.reservationsTab = false;
				$scope.settingsTab = false;
				$scope.fanzoneTab = false;
				$scope.home=function(){
					$scope.homeTab=true;
					$scope.theatersTab = false;
					$scope.cinemasTab = false;
					$scope.friendsTab = false;
					$scope.reservationsTab = false;
					$scope.settingsTab = false;
					$scope.fanzoneTab = false;
				};
				$scope.theaters=function(){
					$scope.homeTab=false;
					$scope.theatersTab = true;
					$scope.cinemasTab = false;
					$scope.friendsTab = false;
					$scope.reservationsTab = false;
					$scope.settingsTab = false;
					$scope.fanzoneTab = false;
				};
				$scope.cinemas=function(){
					$scope.homeTab=false;
					$scope.theatersTab = false;
					$scope.cinemasTab = true;
					$scope.friendsTab = false;
					$scope.reservationsTab = false;
					$scope.settingsTab = false;
					$scope.fanzoneTab = false;
				};
				$scope.friends=function(){
					$scope.homeTab=false;
					$scope.theatersTab = false;
					$scope.cinemasTab = false;
					$scope.friendsTab = true;
					$scope.reservationsTab = false;
					$scope.settingsTab = false;
					$scope.fanzoneTab = false;
				};
				$scope.reservations=function(){
					$scope.homeTab=false;
					$scope.theatersTab = false;
					$scope.cinemasTab = false;
					$scope.friendsTab = false;
					$scope.reservationsTab = true;
					$scope.settingsTab = false;
					$scope.fanzoneTab = false;
				};
				$scope.settings=function(){
					$scope.homeTab=false;
					$scope.theatersTab = false;
					$scope.cinemasTab = false;
					$scope.friendsTab = false;
					$scope.reservationsTab = false;
					$scope.settingsTab = true;
					$scope.fanzoneTab = false;
				};
				$scope.fanzone=function(){
					$scope.homeTab=false;
					$scope.theatersTab = false;
					$scope.cinemasTab = false;
					$scope.friendsTab = false;
					$scope.reservationsTab = false;
					$scope.settingsTab = false;
					$scope.fanzoneTab = true;
				};
			}
		]
);