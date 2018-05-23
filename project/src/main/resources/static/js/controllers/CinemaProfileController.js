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
					//$http.get('http://localhost:8181/refreshCinema/'+ $rootScope.currentCinema.id).success(function( data1,status){
						$http.get('http://localhost:8181/refreshUser/'+ $rootScope.currentUser.email).success(function( data2,status){
							$rootScope.currentUser=data2;
							//localStorage.setItem("currentUser",angular.toJson(data2));
							$rootScope.currentCinema=$rootScope.currentUser.cinemas.find(x => x.id === $rootScope.currentCinema.id);
							//alert(data1.auditoriums[0].projections.length);
							localStorage.setItem("currentCinema",angular.toJson($rootScope.currentCinema));
							$scope.getProjections();
							//alert("ovo je bio u refreshy");
						}).error(function(){
							alert("error refreshUser");
						});
					//}).error(function(){
					//	alert("error refreshCinema");
					//});						
				}
				$scope.refreshUserQT=function(){
					//$http.get('http://localhost:8181/refreshCinema/'+ $rootScope.currentCinema.id).success(function( data1,status){
						$http.get('http://localhost:8181/refreshUser/'+ $rootScope.currentUser.email).success(function( data2,status){
							$rootScope.currentUser=data2;
							//localStorage.setItem("currentUser",angular.toJson(data2));
							$rootScope.currentCinema=$rootScope.currentUser.cinemas.find(x => x.id === $rootScope.currentCinema.id);
							//alert(data1.auditoriums[0].projections.length);
							localStorage.setItem("currentCinema",angular.toJson($rootScope.currentCinema));
							$scope.qtGet();
							//alert("ovo je bio u refreshy");
						}).error(function(){
							alert("error refreshUser");
						});
					//}).error(function(){
					//	alert("error refreshCinema");
					//});						
				}
				$scope.refreshUserAudi=function(){
					//$http.get('http://localhost:8181/refreshCinema/'+ $rootScope.currentCinema.id).success(function( data1,status){
						$http.get('http://localhost:8181/refreshUser/'+ $rootScope.currentUser.email).success(function( data2,status){
							$rootScope.currentUser=data2;
							//localStorage.setItem("currentUser",angular.toJson(data2));
							$rootScope.currentCinema=$rootScope.currentUser.cinemas.find(x => x.id === $rootScope.currentCinema.id);
							//alert(data1.auditoriums[0].projections.length);
							localStorage.setItem("currentCinema",angular.toJson($rootScope.currentCinema));
							//$scope.getProjections();
							//alert("ovo je bio u refreshy");
						}).error(function(){
							alert("error refreshUser");
						});
					//}).error(function(){
					//	alert("error refreshCinema");
					//});						
				}
				$scope.getProjections= function(){
					//alert("getP");
					$scope.projections = [];
					for ( let i in $rootScope.currentCinema.auditoriums) { 
						for (let p in $rootScope.currentCinema.auditoriums[i].projections){
							//alert(i+" "+p);
							let projection = $rootScope.currentCinema.auditoriums[i].projections[p];
							$http.get('http://localhost:8181/getProjectionMovie/'+ projection.id).success(function( data,status){
								projection.auditorium = $rootScope.currentCinema.auditoriums[i];
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
							$scope.refreshUser();
						}).error(function(){
							alert("error remove");
						});	
					}
					else{
						alert("Ne moze, ma rezervacija!");
					}
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
						let movieEl = document.getElementById("proj_movie");
						movie = JSON.parse(movieEl.options[movieEl.selectedIndex].value);
						alert(date.getFullYear() +'/'+ date.getMonth() +'/'+date.getDate() +'/'+time.getHours() +'/'+time.getMinutes());
						$http.post('http://localhost:8181/addProjection/'+ id + '/' +aid.id+'/'+ date.getFullYear() +'/'+ date.getMonth() +'/'+date.getDate() +'/'+time.getHours() +'/'+time.getMinutes() + '/'+ price +'/'+ movie.id).success(function(data,status){
							/*let projection = data;
							projection.movie = movie;
							aid.projections.push(data);*/
							//$scope.refreshUser();
							$location.path("/cinema_profile").replace();

						}).error(function(){
							alert("Invalid date");
							$location.path("/cinema_profile").replace();

						});
					}
					else{
						alert("pogresan password projection ne znam kako!");
					}
					//$scope.refreshUser();

				};
				$scope.buttonValue =function(row,seat){
					if (seat.active){
						return row.number+'|'+seat.number;
					}
					else{
						return "X|X";
					}
				};
				$scope.disableSeat=function(seat){
					$http.post('http://localhost:8181/disableSeat/'+ seat.id).success(function( status){
						$scope.refreshUserAudi();

					}).error(function(){
						alert("Seat has active reservations!");

					});
				};
				
				$scope.addSeat=function(audi){
					$http.post('http://localhost:8181/addSeat/'+ audi.rowAdd+"/"+audi.seatAdd).success(function( status){
						$scope.refreshUserAudi();

					}).error(function(){
						alert("Row has active reservations!");

					});	
				};
				$scope.removeSeat=function(audi){
					let row = JSON.parse(audi.rowRemove);
					if(row.seats.length > audi.seatRemove){
						$http.post('http://localhost:8181/removeSeat/'+ row.id+"/"+audi.seatRemove).success(function( status){
							$scope.refreshUserAudi();
	
						}).error(function(){
							alert("Row has active reservations!");
	
						});
					}
					else{
						alert("too much seats!");
					}
				};
				
				$scope.addRow=function(audi){
					$http.post('http://localhost:8181/addRow/'+ audi.addRow+"/"+audi.id).success(function( status){
						$scope.refreshUserAudi();

					}).error(function(){
						alert("nzm!");

					});						
				};
				
				$scope.removeRow=function(audi){
					let row = JSON.parse(audi.removeRow);
					$http.post('http://localhost:8181/removeRow/'+ row.id+"/"+audi.id).success(function( status){
						$scope.refreshUserAudi();

					}).error(function(){
						alert("Row has active reservations!");

					});						
				};
				
				//poziva se klikom na dugme kraj projekcije
				$scope.qtMake=function(projection){
					$rootScope.qtAudi = projection.auditorium;
					$rootScope.Elena="cao";
					$rootScope.qtProjection = projection;
					$rootScope.chooseSeat=true;
					$rootScope.qtViewSubmit=false;
					console.log($rootScope.qtAudi);
					$location.path("/make_qt").replace();
				}
				
				//submit forme
				$scope.qtAdd=function(projection){
					alert("cap");
					$http.post('http://localhost:8181/qtAdd/'+ $scope.qtProjection.id +"/"+ $scope.qtSeat.id + "/"+ $scope.qtDiscount).success(function( status){
						$scope.refreshUserAudi();
						$location.path("/cinema_profile").replace();

					}).error(function(){
						alert("Seat has active reservations!");
						$location.path("/cinema_profile").replace();

					});		
				};
				
				//kad se izabere sediste na njegov klik
				$scope.qtSeat=function(seat){
					$scope.qtSeat = seat;
					$scope.qtViewSubmit = true;
				};
				
				//listanje svih qt
				$scope.qtGet=function(){
					$http.get('http://localhost:8181/qtGet/'+ $rootScope.currentCinema.id).success(function(data, status){
						$scope.qtList=data;
						//$scope.refreshUserAudi();
						//console.log($scope.qtList);
					}).error(function(){
						alert("nesto je fejl");

					});	
				};
				
				$scope.qtRemove=function(qt){
					$http.post('http://localhost:8181/qtRemove/'+ qt.id).success(function(data, status){
						$scope.refreshUserQT();
					}).error(function(){
						alert("nesto je fejl");

					});	
				}
			}
		]
	);