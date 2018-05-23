angular.module('Application').controller(
		'FriendsTabController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				
				$scope.explore=false;
				$scope.my=false;
				$scope.back=function(){
					$scope.explore = false;
					$scope.my= false
					$scope.showBack=false;
				};
				$scope.friends=function(){
					$scope.friendsShow={};
					$http.get('/reguser/friends/'+$scope.currentUser.email).success(function(data, status){
						$scope.friendsShow=data;
						$scope.showBack=true;
						$scope.my=true;
					});
				};
				$scope.people=function(){
					$scope.unknownsShow={};
					$http.get('/reguser/people/'+$scope.currentUser.email).success(function(data, status){
						$scope.unknownsShow=data;
						$scope.showBack=true;
						$scope.explore=true;
					});
				};
				
				$scope.searchUnknown=function(){
				
				};
				
				
			}
		]
);