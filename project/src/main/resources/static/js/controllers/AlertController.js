var app = angular.module('Application').controller(
		'AlertController',
		[
				'$rootScope',
				'$scope',
				'$http',
				'$routeParams',
				'$location',
				'$window',
				
				function($rootScope, $scope, $http, $routeParams, $location, $window) {

					$rootScope.alert=function(textytj){
						$scope.texty=textytj;
						$rootScope.alertShow=true;
					};
					
					$scope.close=function(){
						$rootScope.alertShow = false;
					};
				}
		]
);

