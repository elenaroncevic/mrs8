angular.module('Application').controller(
		'HomeTabController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$scope.currentUser = JSON.parse(localStorage.getItem("currentUser"));
				
				$scope.loadHistory=function(){
					var currentUser = JSON.parse(localStorage.getItem("currentUser"));
					$http.get('/reguser/history/'+currentUser.email).success(function(data, status){
						$scope.history=data;
					});
				};
				
				$scope.editInfo=function(){
					$rootScope.initCurrent();
					$scope.showUpd=true;
				};
				
				$scope.changePassword=function(){
					$scope.showChng=true;
				};
				
				$rootScope.refreshUserData=function(){
					$scope.currentUser = JSON.parse(localStorage.getItem("currentUser"));
				};
				
			}
			
		]
);