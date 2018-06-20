angular.module('Application').controller( 
		'CinemaAdminController',
		[ 
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				
				$scope.changeInfo=function(){
					$location.path('cinema_admin/change_password').replace();
				};
				
				$scope.changePass=function(){
					if($scope.newPass == $scope.confirmedPass && $scope.oldPass==$rootScope.currentUser.password 
							&& $scope.newPass != $scope.oldPass){
						$http.post('/changePass/'+$rootScope.currentUser.email+"/"+$scope.newPass).success(function( data,status){
							localStorage.setItem("currentUser",angular.toJson(data));
							$location.path("/cinema_admin").replace();

						}).error(function(){
							$rootScope.alert("Error in changePassword");
							$location.path("/cinema_admin").replace();

						});

					}
					else{
						$rootScope.alert("wrong password!");
					}
				}
				
				$scope.showCinema = function(data){
					$rootScope.currentCinema=data;
					localStorage.setItem("currentCinema",angular.toJson(data));
					$location.path('/cinema_profile').replace();
				};
				$rootScope.currentUser = JSON.parse(localStorage.getItem("currentUser"));
				$scope.cinemaList = [];
				$scope.theaterList = [];
				var all = $rootScope.currentUser.cinemas;
				for(var i in all){
					if(all[i].type=="Cinema"){
						$scope.cinemaList.push(all[i]);
					}
					else{
						$scope.theaterList.push(all[i]);
						
					}
				};
				console.log($rootScope.currentUser.first_time);
				console.log($rootScope.currentUser!=null);

				if ($rootScope.currentUser!= null && $rootScope.currentUser.first_time){
					$rootScope.alert("morate promeniti lozinku nakon prvog logina");
					$location.path('cinema_admin/change_password').replace();

				}
			}
		]
);