angular.module('Application').controller(
		'ReserveTicketController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$scope.projectionDate=false;
				$scope.timeSeats=false;
				$scope.searchCinemas=function(){
					var op = document.getElementById("cinemaCombo").getElementsByTagName("option");
					for(var i =0;i<op.length;i++){
						if(op[i].value.toLowerCase().includes($scope.searchCinemaText.toLowerCase())){
							op[i].selected=true;
							break;
						}
					};
				};
				$scope.projectionAndDate=function(){
					$scope.projectionDate=true;
					var selCinema = document.getElementById("cinemaCombo").selectedIndex;
					$scope.cinemaSelected = $rootScope.cinemasShow[selCinema];
					$http.get('http://localhost:8181/reguser/movies/'+$scope.cinemaSelected.id).success(function(data,status){
						$scope.moviesShow=data;
					});
				};
				$scope.timeAndSeats=function(){
					$scope.timeSeats=true;
					$scope.chosenDate=$("#datepicker").datepicker().val();
					$scope.chosenMovie = $scope.moviesShow[document.getElementById("movieCombo").selectedIndex];
					$scope.timeShow={};
					$scope.audShow={};
					var num=0;
					for(var x in $scope.chosenMovie.projections){
						if($scope.chosenMovie.projections[x].date==$scope.chosenDate){
							$http.get('http://localhost:8181/reguser/auditorium/'+$scope.chosenMovie.projections[x].id).success(function(data,status){
								$scope.audShow[num]=data;
								$scope.timeShow[num]=$scope.chosenMovie.projections[x].time;
								num=num+1;
							});
						}
					};
				};
				$scope.audChanged=function(ac){
					$scope.tc=$scope.timeShow[document.getElementById("audCombo").selectedIndex];
				};
				$scope.timeChanged=function(tc){
					$scope.ac=$scope.audShow[document.getElementById("timeCombo").selectedIndex];
				};
			}
		]
);