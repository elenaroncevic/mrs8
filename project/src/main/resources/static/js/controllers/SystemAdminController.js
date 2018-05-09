angular.module('Application').controller(
		'SystemAdminController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.register = function() {
					if($scope.adm-pass1==$scope.adm-pass2){
						$http.post('http://localhost:8181/register_new_admin/'+ $scope.adm-email+'/'+ $scope.adm-pass1+'/'+($scope.adm-type).options[($scope.adm-type).selectedIndex].value).success(function(){
							$location.path('/register_new_user').replace();
						}).error(function(){
							alert("User with the same email already exists");
						});
					}else{
						alert("Passwords don't match! Try again!");
					}

				};
				
				if ($rootScope.currentUser.def=="false"){
					($scope.adm-type).options[0].disabled=true;
				}

			}
		]
);