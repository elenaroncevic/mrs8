angular.module('Application').controller(
		'HomeTabController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$scope.home=true;
				$scope.edit=false;
				$scope.passChange=false;
				$scope.currentUser=JSON.parse(localStorage.getItem("currentUser"));
				$scope.loadHistory=function(){
					$http.get('/reguser/history/'+$scope.currentUser.email).success(function(data, status){
						$scope.history=data;
						$rootScope.h=data;
					});
				};
				$scope.editInfo=function(){
					$scope.edit=true;
					$scope.home=false;
				};
				$scope.finishEdit=function(){
					$http.post('/reguser/editInfo/'+$scope.currentUser.email+'/'+$scope.currentUser.firstName+'/'+$scope.currentUser.lastName+'/'+$scope.currentUser.city+'/'+$scope.currentUser.phone).success(function(data, status){
						localStorage.setItem("currentUser",angular.toJson(data));
						alert('Info changed!');
					});
					$scope.edit=false;
					$scope.home=true;
				};
				$scope.changePassword=function(){
					$scope.passChange=true;
					$scope.home=false;
				};
				$scope.finishPass=function(){
					$http.post('/reguser/editPass/'+$scope.currentUser.email+'/'+$scope.oldPass+'/'+$scope.newPass+'/'+$scope.newPass2).success(function(data, status){
						localStorage.setItem("currentUser",angular.toJson(data));
						alert('Password changed!');
					}).error(function(data, status){
						alert('Error with input data');
					});
					$scope.oldPass="";
					$scope.newPass="";
					$scope.newPass2="";
					$scope.passChange=false;
					$scope.home=true;
				};
			}
		]
);