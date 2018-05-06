angular.module('Application').controller(
		'LoginController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			function($rootScope, $scope, $window, $location) {
				$scope.login = function() {
					$http.get('loguser/' + email).success(function(data, status){
						if(data.pass==pass){
							$rootScope.currentUser=data;
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

