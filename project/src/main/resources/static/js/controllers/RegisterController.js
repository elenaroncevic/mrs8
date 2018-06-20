angular.module('Application').controller(
		'RegisterController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.email="";
				$scope.password="";
				$scope.pass2="";
				$scope.firstName="";
				$scope.lastName="";
				$scope.city="";
				$scope.phone="";
				
							
				$scope.close=function(){
					$scope.showReg=false;
				};
				
				$scope.register = function(){
					if($scope.email.length==0 || $scope.password.length==0 || $scope.pass2.length==0 || $scope.firstName.length==0 || $scope.lastName.length==0 || $scope.city.length==0 || $scope.phone.length==0){
						$rootScope.alert('Input all fields!');
						$scope.password="";
						$scope.pass2="";
						return;
					};
				
					if($scope.password==$scope.pass2){
						$http.post('/register/' + $scope.email+'/'+$scope.password+'/'+$scope.firstName+'/'+$scope.lastName+'/'+$scope.city+'/'+$scope.phone).success(function(data, status){
							$scope.showReg=false;
							$location.path('/home').replace();
							$rootScope.alert('Confirmation email has been sent to '+$scope.email+' , please confirm and login to continue using our application.');
						}).error(function(){
							$rootScope.alert("User with the given email already exists!");
							$scope.password="";
							$scope.pass2="";
						});
					}else{
						$rootScope.alert("Passwords don't match! Try again!");
						$scope.password="";
						$scope.pass2="";
					}
				};
				
			}
		]
);

