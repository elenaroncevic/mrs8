angular.module('Application').controller(
		'LoginController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.initUser = function(){
					$scope.user={};
				};
				$scope.login = function() {
					$http.get('api/loguser/' + $scope.user.email).success(function(data, status){
						if(data.pass==$scope.user.pass && data.activated!="not"){
							$rootScope.currentUser=data.content;
							$location.path('/profile').replace();
						}else{
							alert("Wrong password! Try again!");
						}
					}).error(function(){
						alert("User not found! Check your e-mail address!");
					});
				};
			}
		]
);

