angular.module('Application').controller(
		'ReserveTicketController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
			
 				$rootScope.modal= {
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
				
				$rootScope.refreshReservation=function(){
					$("#datepicker").datepicker('disable');
					$scope.moviesShow={};
					$scope.projShow={};
					$scope.searchCinemaText="";
					
					$scope.cin="";
					$scope.showContinue=false;
					$scope.disMovCombo=true;
					$scope.disProjCombo = true;
					$rootScope.modal.show = false;
				};
				
				
				$scope.searchCinemas=function(){
					if($scope.searchCinemaText==""){
						alert('Please, input search text.');
						return;
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
					let selCinema = document.getElementById("cinemaCombo").selectedIndex;
					if(selCinema==0){
						return;
					};
					$scope.cinemaSelected = $scope.cinemasShow[selCinema-1];
					
					$http.get('/reguser/movies/'+$scope.cinemaSelected.id).success(function(data,status){
						if(data.length==0){
							alert('Selected cinema currently has no movies.');
							$scope.moviesShow={};
							$("#datepicker").datepicker('disable');
							$scope.disMovCombo = true;
							$scope.disProjCombo=true;
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
							$scope.proj="";
							$scope.disProjCombo = false;
						}
					});
					
				};
				
				$scope.continueReservation=function(){
					$rootScope.modal.show = true;
				};
 				
				$scope.projectionChanged=function(){
					var idx = document.getElementById("projCombo").selectedIndex;
					if(idx==0){
						return;
					}
					var chosenProjection = $scope.projShow[idx-1];
					$rootScope.modal.show = true;
					$scope.showContinue = true;
					$rootScope.loadModal(chosenProjection);
				};
			}
		]
);

