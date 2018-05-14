angular.module('Application').controller(
		'SystemAdminController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.registerAdmin = function() {
					if($scope.admPass1==$scope.admPass2){
						var e = document.getElementById("type");
						var strUser = e.options[e.selectedIndex].value;
						$http.post('http://localhost:8181/system_admin/register_new_admin/'+ $scope.admEmail+'/'+ $scope.admPass1+'/'+strUser).success(function(){
							$location.path('/system_admin/register_new_admin').replace();
						}).error(function(){
							alert("User with the same email already exists");
						});
					}else{
						alert("Passwords don't match! Try again!");
					}

				};
				
				if ($rootScope.currentUser.def==null || $rootScope.currentUser.def==0){
					var op = document.getElementById("type").getElementsByTagName("option");
					for (var i = 0; i < op.length; i++) {
					  (op[i].value.toLowerCase() == "system") 
					    ? op[i].disabled = true 
					    : op[i].disabled = false ;
					  //
					}
				}
				
				
				

			}
		]
);