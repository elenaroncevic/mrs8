angular.module('Application').controller(
		'ReserveTicketTheaterController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$( function() {
    				$( "#datepicker2" ).datepicker();
				} );		
				$scope.showProjCombo=false;
				$scope.dates=false;
				$scope.disableButtonsT=false;
				$scope.showingSeats=false;
				$scope.searchTheaters=function(){
					var op = document.getElementById("theaterCombo").getElementsByTagName("option");
					for(let i =0;i<op.length;i++){
						if(op[i].value.toLowerCase().includes($scope.searchTheaterText.toLowerCase())){
							op[i].selected=true;
							break;
						}
					};
				};
				$scope.showProjectionsDates=function(){
					$scope.showProjCombo=true;
					let selTheater = document.getElementById("theaterCombo").selectedIndex;
					$scope.theaterSelected = $rootScope.theatersShow[selTheater];
					$http.get('/reguser/movies/'+$scope.theaterSelected.id).success(function(data,status){
						$scope.projsShow=data;
					});
					$scope.showingSeats=false;
				};
				$scope.showAvailable=function(){
					if($scope.projsShow.length==0){
						alert('No movies found!');
						return;
					};
					$scope.chosenDate=$("#datepicker2").datepicker().val();
					$scope.chosenPerformance = $scope.projsShow[document.getElementById("theaterCombo").selectedIndex];
					let num=0;
					$scope.perfShow={};
					
					$http.get('http://localhost:8181/reguser/projections/'+$scope.chosenPerformance.id).success(function(data, status){
						$rootScope.h=data;
						for(let x in data){
							var lista = data[x].date.split(' ');
							if($scope.chosenDate==lista[0]){
								$scope.perfShow[num]=data[x];
								num=num+1;
							}
						};
					});
					$scope.dates=true;
				};
				$scope.chosenProjection= null;
				$scope.loadSeatsT=function(){
					var myEl = angular.element( document.querySelector( '#seat-map2' ) );
					myEl.empty();
					var myEl2 = angular.element(document.querySelector( '#selected-seats2'));
					myEl2.empty();
					$scope.chosenProjection = $scope.perfShow[document.getElementById("timeRoom").selectedIndex];
					if($scope.chosenProjection==null){
						alert('Projection not chosen!');
					}else{
						$http.get('http://localhost:8181/reguser/seats/'+$scope.chosenProjection.id).success(function(data,status){
							$scope.seatoviT=data[0];
							$scope.takenSeatoviT=data[1];
							$http.get('http://localhost:8181/reguser/auditorium/'+$scope.chosenProjection.audId).success(function(data, status){
								$scope.rNumT=data.rowNumber;
								$rootScope.h=data;
								arrangementT();
							});		
						});
					}
				};
				arrangementT=function(){
					var allSeatsT = [];
					var takenSeatsT=[];
					for(let i=0;i<$scope.rNumT;i++){
						allSeatsT.push('');
					};
					let numrow=0;
					for(let y in $scope.seatoviT){
						numrow=$scope.seatoviT[y].rowNum-1;
						allSeatsT[numrow]=allSeatsT[numrow]+'a';
					};
					let numcol;
					for(let y in $scope.takenSeatoviT){
						numrow=$scope.takenSeatoviT[y].rowNum;
						numcol = $scope.takenSeatoviT[y].number;
						takenSeatsT.push(numrow+"_"+numcol);		
					};
					var $cart = $('#selected-seats2');
					$scope.selectedSeatsT=[];
					$scope.seatsSelT=0;
					var $sc2 = $('#seat-map2').seatCharts({
						map: allSeatsT,
						naming : {
							top : false,
							getLabel : function (character, row, column) {
								return column;
							}
						},
						legend :{
							node : $('#legend'),
							items : [
								[ 'a', 'available',   'Available' ],
								[ 'u', 'unavailable', 'Sold'],
								[ 's', 'selected', 'Selected']
							]					
						},
						click: function () {
							if (this.status() == 'available') { 
								$('<li>Row'+(this.settings.row+1)+' Seat'+this.settings.label+'</li>')
									.attr('id', 'cart-item-'+this.settings.id)
									.data('seatId', this.settings.id)
									.appendTo($cart);
								var ssT = {};
								for(let x in $scope.seatoviT){
									if($scope.seatoviT[x].rowNum==""+(this.settings.row+1) && $scope.seatoviT[x].number==this.settings.label){
										ssT['id']=$scope.seatoviT[x].id;
										break;
									}
								};
								ssT['num']=""+(this.settings.row+1)+this.settings.label;
								$scope.selectedSeatsT[$scope.seatsSelT]=ssT;
								$scope.seatsSelT=$scope.seatsSelT+1;
								return 'selected';
							} 
							else if (this.status() == 'selected') {
									$('#cart-item-'+this.settings.id).remove();
									var index = $scope.selectedSeatsT.indexOf(""+(this.settings.row+1)+this.settings.label);
									$scope.selectedSeatsT.splice(index, 1);
									$scope.seatsSelT=$scope.seatsSelT-1;
									return 'available';
							} 
							else if (this.status() == 'unavailable') {
								return 'unavailable';
							}
							else {
								return this.style();
							}
						}
					});
					$sc2.get(takenSeatsT).status('unavailable');
					
					
					$scope.showProjCombo=false;
					$scope.dates=false;
					$scope.showingSeats=true;
				};
				var currentUser = JSON.parse(localStorage.getItem("currentUser"));
				$scope.invitePeople=function(){
					if($scope.seatsSelT==1){
						$scope.finishThis();
					}else{
						$http.get('http://localhost:8181/reguser/friends/'+currentUser.email).success(function(data, status){
							$scope.friendsT=data;
							$scope.friendsBoxT=true;
						});
					}
				};				


				var friendsChosenT=0;
				var friendsSelectedT={};
				let x = 0;
				$scope.inviteFriendT=function(friend){
					//disable pojedinacni
					friendsChosenT=friendsChosenT+1;
					if(friendsChosenT==$scope.seatsSelT-1){
						$scope.disableButtonsT=true;
					}
					friendsSelectedT[x]=friend;
					x=x+1;
				}
				
				var s=0;
				$scope.finishThis=function(){
					$http.post('http://localhost:8181/reguser/makeReservation/'+currentUser.email+'/'+$scope.selectedSeatsT[s].id+'/'+$scope.chosenProjection.id).success(function(data, status){
						$scope.reservIdT=data;
						s=s+1;
						saveDataT();
					});
				};
				
				saveDataT=function(){
					for(let x in friendsSelectedT){
						$http.post('http://localhost:8181/reguser/makeTicket/'+friendsSelectedT[x].email+'/'+$scope.chosenProjection.id+'/'+$scope.reservIdT+'/'+$scope.selectedSeatsT[s].id).success(function(data, status){
							
						});
						s=s+1;
					};
					
					alert('Successfully reserved a ticket!');
					$rootScope.loadReservations();
					$scope.dates=false;
					$scope.showProjCombo=false;
					$scope.showingSeats=false;
					$scope.friendsBoxT=false;
				};
				//
			}
		]
);