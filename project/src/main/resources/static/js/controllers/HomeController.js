angular.module('Application').controller(
		'HomeController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$scope.signup = function(){
					$location.path('/signup').replace();
				};
				$scope.list_cinemas = function(){
					$location.path('/list_cinemas').replace();
				};
				$scope.login = function() {
					$http.post('http://localhost:8181/loguser/'+ $scope.email+'/'+ $scope.pass).success(function(data, status){
							$rootScope.currentUser=data;
							$rootScope.ru=false;
							$rootScope.admin=false;
							if(Object.keys(data).length>3){
								if(data.activated=="yes"){
									$rootScope.ru=true;
								}else{
									alert("You haven't activated your account yet! Check your email!");
									$location.path('/home').replace();
								}
							}else{
								$rootScope.admin=true;
							}
							$location.path('/profile').replace();
					}).error(function(){
						alert("Error with input data! Check your email address and password!");
					});
				};
			}
		]
);