var app = angular.module('Application').controller(
		'UpdateInfoController',
		[
				'$rootScope',
				'$scope',
				'$http',
				'$routeParams',
				'$location',
				'$window',
				
				function($rootScope, $scope, $http, $routeParams, $location, $window) {
				$scope.currentUser={};
				
				$rootScope.initCurrent=function(){
					$scope.currentUser = JSON.parse(localStorage.getItem("currentUser"));
				};

				$scope.finishEdit=function(){
					$http.post('/reguser/editInfo/'+$scope.currentUser.email+'/'+$scope.currentUser.firstName+'/'+$scope.currentUser.lastName+'/'+$scope.currentUser.city+'/'+$scope.currentUser.phone).success(function(data, status){
						localStorage.setItem("currentUser",angular.toJson(data));
						$rootScope.alert('Info changed!' + $scope.currentUser.city);
						$scope.showUpdate=false;
						$rootScope.refreshUserData();
					});
				};
				
				$scope.close=function(){
					$scope.showUpdate=false;
				};
				
				}
		]
);

