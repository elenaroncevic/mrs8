angular.module('Application').controller(
		'CinemaProfileController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$rootScope.currentCinema = JSON.parse(localStorage.getItem("currentCinema"));
				$rootScope.currentUser = JSON.parse(localStorage.getItem("currentUser"));
				//alert($rootScope.currentCinema.id+"\n"+$rootScope.currentUser.email);
				
				$scope.refreshUser=function(){
					$http.get('http://localhost:8181/refreshCinema/'+ $rootScope.currentCinema.id).success(function( data1,status){
						$http.get('http://localhost:8181/refreshUser/'+ $rootScope.currentUser.email).success(function( data2,status){
							$rootScope.currentUser=data2;
							localStorage.setItem("currentUser",angular.toJson(data2));
							$rootScope.currentCinema=data1;
							//alert(data1.auditoriums[0].projections.length);
							localStorage.setItem("currentCinema",angular.toJson(data1));
							$scope.getProjections();
							//alert("ovo je bio u refreshy");
						}).error(function(){
							alert("error refreshUser");
						});
					}).error(function(){
						alert("error refreshCinema");
					});	
					
					
				}
				$scope.getProjections= function(){
					//alert("getP");
					$scope.projections = [];
					for ( let i in $rootScope.currentCinema.auditoriums) { 
						for (let p in $rootScope.currentCinema.auditoriums[i].projections){
							//alert(i+" "+p);
							let projection = $rootScope.currentCinema.auditoriums[i].projections[p];
							$http.get('http://localhost:8181/getProjectionMovie/'+ projection.id).success(function( data,status){
								projection.auditorium = $rootScope.currentCinema.auditoriums[i].id;
								projection.movie = data;
								$scope.projections.push(projection);
							}).error(function(){
								alert("error movie");
							});
						}
					}
				};
				
				$scope.removeProjection= function(projection){
					if(projection.tickets.length ==0){
						$http.get('http://localhost:8181/removeProjection/'+ projection.id).success(function( status){
							/*let newProjections=[]
							for(let i in $scope.projections){
								if($scope.projections[i].id != projection.id){
									newProjections.push($scope.projections[i]);
								}								
							}
							$scope.projections=newProjections;*/
							$scope.refreshUser();

						}).error(function(){
							alert("error remove");
						});	
					}
					else{
						alert("Ne moze, ma rezervacija!");
					}

					//$location.path("/cinema_profile").replace();
				};
				$scope.drugo= function(){
					$location.path("/add_projection").replace();
				};
				$scope.editBasicInformations=function(){
					$location.path("/edit_cinema_basic").replace();
				};
				$scope.changeBasic = function() {
					if($scope.password == $rootScope.currentUser.password){
						$http.post('http://localhost:8181/changeBasic/'+ $rootScope.currentCinema.id + '/' + $scope.name+'/'+ $scope.location +'/'+$scope.description).success(function( data,status){
							//$rootScope.currentCinema=data;
							$scope.refreshUser();
							$location.path("/cinema_profile").replace();

						}).error(function(){
							alert("Error in change basic");
							$location.path("/cinema_profile").replace();

						});
					}
					else{
						alert("pogresan password basic!");
					}

					//$location.path("/cinema_profile").replace();
				};
				$scope.getMovies = function(){
					$http.get('http://localhost:8181/getMovies/').success(function( data,status){
						$scope.movies=data;
					}).error(function(){
						alert("Error in  movies");
					});
				};
				$scope.addProjection=function(){
					if($scope.proj_password == $rootScope.currentUser.password){
						let id = $rootScope.currentCinema.id;
						
						let aidEl = document.getElementById("proj_auditorium")
						let aid= JSON.parse(aidEl.options[aidEl.selectedIndex].value);
						
						let date =$scope.proj_date;
						let time = $scope.proj_time;
						let price = $scope.proj_price;
						
						let movieEl = document.getElementById("proj_movie")
						movie = JSON.parse(movieEl.options[movieEl.selectedIndex].value);
						
						$http.post('http://localhost:8181/addProjection/'+ id + '/' +aid.id+'/'+ date.getYear() +'/'+ date.getMonth() +'/'+date.getDay() +'/'+time.getHours() +'/'+time.getMinutes() + '/'+ price +'/'+ movie.id).success(function(data,status){
							/*let projection = data;
							projection.movie = movie;
							aid.projections.push(data);*/
							$scope.refreshUser();
							$location.path("/cinema_profile").replace();

						}).error(function(){
							alert("Error in change basic");
							$location.path("/cinema_profile").replace();

						});
					}
					else{
						alert("pogresan password projection ne znam kako!");
					}
					//$scope.refreshUser();

				};
				
			}
		]
	);