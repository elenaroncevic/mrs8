angular.module('Application').controller(
		'LoginController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.login = function() {
					$http.post('http://localhost:8080/loguser/'+ $scope.email+'/'+ $scope.pass).success(function(data, status){
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

