var app = angular.module('Application').controller(
		'ChangePassController',
		[
				'$rootScope',
				'$scope',
				'$http',
				'$routeParams',
				'$location',
				'$window',
				
				function($rootScope, $scope, $http, $routeParams, $location, $window) {
					
					
					
					$scope.oldPass="";
					$scope.newPass="";
					$scope.newPass2="";

				$scope.finishPass=function(){
					
					var currentUser = JSON.parse(localStorage.getItem("currentUser"));
					$http.post('/reguser/editPass/'+currentUser.email+'/'+$scope.oldPass+'/'+$scope.newPass+'/'+$scope.newPass2).success(function(data, status){
						localStorage.setItem("currentUser",angular.toJson(data));
						$rootScope.alert('Password changed!');
						$scope.showChange=false;
						$rootScope.refreshUserData();
					}).error(function(data, status){
						$rootScope.alert('Error with input data');
					});
					$scope.oldPass="";
					$scope.newPass="";
					$scope.newPass2="";
				};
				
				$scope.close=function(){
					$scope.showChange=false;
				};
				}
		]
);

