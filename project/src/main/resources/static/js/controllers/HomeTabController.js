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
				
				$scope.loadHistory=function(){
					var currentUser = JSON.parse(localStorage.getItem("currentUser"));
					$http.get('/reguser/history/'+currentUser.email).success(function(data, status){
						$scope.history=data;
					});
				};
				$scope.editInfo=function(){
					$scope.edit=true;
					$scope.home=false;
				};
				$scope.finishEdit=function(){
					var currentUser = JSON.parse(localStorage.getItem("currentUser"));
					$http.post('/reguser/editInfo/'+currentUser.email+'/'+currentUser.firstName+'/'+currentUser.lastName+'/'+currentUser.city+'/'+currentUser.phone).success(function(data, status){
						localStorage.setItem("currentUser",angular.toJson(data));
						$rootScope.alert('Info changed!');
					});
					$scope.edit=false;
					$scope.home=true;
				};
				$scope.changePassword=function(){
					$scope.passChange=true;
					$scope.home=false;
				};
				$scope.finishPass=function(){
					var currentUser = JSON.parse(localStorage.getItem("currentUser"));
					$http.post('/reguser/editPass/'+currentUser.email+'/'+$scope.oldPass+'/'+$scope.newPass+'/'+$scope.newPass2).success(function(data, status){
						localStorage.setItem("currentUser",angular.toJson(data));
						$rootScope.alert('Password changed!');
					}).error(function(data, status){
						$rootScope.alert('Error with input data');
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