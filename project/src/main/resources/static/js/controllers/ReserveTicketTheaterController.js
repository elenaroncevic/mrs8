angular.module('Application').controller(
		'ReserveTicketTheaterController',
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
				$('#datepicker2').datepicker({
     				onSelect: function(d,i){
          				if(d !== i.lastVal){
              				$scope.showAvailable();
          				}
     				},
     				minDate: 0,
     				defaultDate: null
				});
				
				
				$rootScope.refreshReservationTheater=function(){
					$("#datepicker2").datepicker('disable');
					var first = 1;
				
					$scope.showContinueTheater=false;
					$scope.disMovComboTheater=true;
					$scope.disProjComboTheater = true;
					$rootScope.modal.show = false;
				};
				
				$scope.searchTheaters=function(){
					if(first==1){
						document.getElementById("theater").remove(0);
					}
					var op = document.getElementById("theaterCombo").getElementsByTagName("option");
					for(let i =0;i<op.length;i++){
						if(op[i].value.toLowerCase().includes($scope.searchTheaterText.toLowerCase())){
							op[i].selected=true;
							$scope.showProjectionsDates();
							break;
						}
					};
				};
				
				$scope.showProjectionsDates=function(){
					first=0;
					let selTheater = document.getElementById("theaterCombo").selectedIndex;
					$scope.theaterSelected = $scope.theatersShow[selTheater];
					
					$http.get('/reguser/movies/'+$scope.theaterSelected.id).success(function(data,status){
						if(data.length==0){
							alert('Selected theater currently has no plays.');
							$scope.projsShow={};
							$("#datepicker2").datepicker('disable');
							$scope.disMovComboTheater = true;
						}else{
							$scope.projsShow=data;
							document.getElementById("projsCombo").remove(0);
							$scope.disMovComboTheater=false;
							$("#datepicker2").datepicker('enable');
						}
					});
				};
				
				
				$scope.showAvailable=function(){					
					var chosenDate=$("#datepicker2").datepicker().val();
				
					var chosenPlay = $scope.projsShow[document.getElementById("projsCombo").selectedIndex];
					$scope.perfShow={};
					var send = chosenDate.replace("/", ",");
					var send = send.replace("/", ",");
					$http.get('/reguser/projections/'+chosenPlay.id+'/'+send).success(function(data, status){
						if(data.length==0){
							alert('No projections for chosen movie and date');
							$scope.perfShow={};
							$scope.disProjComboTheater = true;
						}else{
							$scope.perfShow=data;
							$scope.perf="";
							$scope.disProjComboTheater = false;
						}
					});
				};
				
				$scope.continueReservationTheater=function(){
					$rootScope.modal.show = true;
				};
 				
				$scope.projectionChangedTheater=function(){
					var idx = document.getElementById("timeRoom").selectedIndex;
					if(idx==0){
						return;
					}
					var chosenProjection = $scope.perfShow[idx-1];
					$rootScope.modal.show = true;
					$scope.showContinueTheater = true;
					$rootScope.loadModal(chosenProjection);
				};

			}
		]
);