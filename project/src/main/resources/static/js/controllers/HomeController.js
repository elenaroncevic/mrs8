angular.module('Application').controller(
		'HomeController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$scope.email="";
				$scope.pass="";
				
				$scope.signup = function(){
					$scope.showReg=true;
				};
				
				$scope.list_cinemas = function(){
					$location.path('/list_cinemas').replace();
				};
				
				$scope.login = function() {
					if($scope.email.length==0 || $scope.pass.length==0){
						$rootScope.alert('Input all fields!');
						$scope.pass="";
						return;
					};
					
					$http.post('/loguser/'+ $scope.email+'/'+ $scope.pass).success(function(data, status){
						localStorage.setItem("currentUser",angular.toJson(data));
						
						$rootScope.ru=data.hasOwnProperty("firstName");
						$rootScope.cinemaAdmin=data.hasOwnProperty("cinemas");
						$rootScope.systemAdmin=data.hasOwnProperty("def");
						$rootScope.fanZoneAdmin=!($rootScope.ru || $rootScope.cinemaAdmin || $rootScope.systemAdmin);
						
						localStorage.setItem("regUser",$rootScope.ru);
						localStorage.setItem("cinAdm",$rootScope.cinemaAdmin);
						localStorage.setItem("sysAdm",$rootScope.systemAdmin);
						localStorage.setItem("fzAdm",$rootScope.fanZoneAdmin);
						
						if(data.activated!="yes"){
							$rootScope.alert("You haven't activated your account yet! Check your email!");
							$location.path('/home').replace();
						}
						else{
							if($rootScope.ru){
								$location.path('/profile').replace();
							}else if($rootScope.systemAdmin){
								$location.path('/system_admin').replace();
							}else if($rootScope.cinemaAdmin){
								$location.path('/cinema_admin').replace();
							}else{
								$location.path('/fan_zone_admin').replace();
							};
						}
				}).error(function(){
					$rootScope.alert("Error with input data! Check your email address and password!");
					$scope.pass="";
				});
				};
			}
		]
);