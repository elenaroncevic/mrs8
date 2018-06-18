angular.module('Application').controller(
		'ReserveTicketController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$scope.modal= {
    				show: false
 				};
				$('#datepicker').datepicker({
     				onSelect: function(d,i){
          				if(d !== i.lastVal){
              				$scope.timeAndAud();
          				}
     				},
     				minDate: 0,
     				defaultDate: null
				});
				$("#datepicker").datepicker('disable');
				var first = 1;
				$scope.showSeatsPreview=false;
				$scope.friendsBox=false;
				
				$scope.disMovCombo=true;
				$scope.disProjCombo = true;
				
				

				
				$scope.disableButtons=false;
				
				$scope.searchCinemas=function(){
					if(first==1){
						document.getElementById("cinemaCombo").remove(0);
					}
					var op = document.getElementById("cinemaCombo").getElementsByTagName("option");
					for(let i =0;i<op.length;i++){
						if(op[i].value.toLowerCase().includes($scope.searchCinemaText.toLowerCase())){
							op[i].selected=true;
							$scope.projectionAndDate();
							break;
						}
					};
				};
				
				$scope.projectionAndDate=function(){
					first=0;
					let selCinema = document.getElementById("cinemaCombo").selectedIndex;
					$scope.cinemaSelected = $scope.cinemasShow[selCinema];
					
					$http.get('http://localhost:8181/reguser/movies/'+$scope.cinemaSelected.id).success(function(data,status){
						if(data.length==0){
							alert('Selected cinema currently has no movies.');
							$scope.moviesShow={};
							$("#datepicker").datepicker('disable');
							$scope.disMovCombo = true;
						}else{
							$scope.moviesShow=data;
							document.getElementById("movieCombo").remove(0);
							$scope.disMovCombo=false;
							$("#datepicker").datepicker('enable');
						}
					});
				};
				
				$scope.timeAndAud=function(){
					var chosenDate=$("#datepicker").datepicker().val();
				
					
					var chosenMovie = $scope.moviesShow[document.getElementById("movieCombo").selectedIndex];
					$scope.projShow={};
					var send = chosenDate.replace("/", ",");
					var send = send.replace("/", ",");
					$http.get('/reguser/projections/'+chosenMovie.id+'/'+send).success(function(data, status){
						if(data.length==0){
							alert('No projections for chosen movie and date');
							$scope.projShow={};
							$scope.disProjCombo = true;
						}else{
							$scope.projShow=data;
							$scope.disProjCombo = false;
						}
					});
					
				};
 				
				$scope.projectionChanged=function(){
					var idx = document.getElementById("projCombo").selectedIndex;
					if(idx==0){
						return;
					}
					var chosenProjection = $scope.projShow[idx-1];
					$scope.modal.show = true;
					$rootScope.loadSeats(chosenProjection);

				};
			}
		]
);

