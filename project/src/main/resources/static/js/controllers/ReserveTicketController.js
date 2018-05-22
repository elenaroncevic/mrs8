angular.module('Application').controller(
		'ReserveTicketController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
  				$( function() {
    				$( "#datepicker" ).datepicker();
				} );		
				$scope.projectionDate=false;
				$scope.timeAud=false;
				$scope.showSeatsPreview=false;
				$scope.friendsBox=false;
				$scope.disableButtons=false;
				$scope.searchCinemas=function(){
					var op = document.getElementById("cinemaCombo").getElementsByTagName("option");
					for(let i =0;i<op.length;i++){
						if(op[i].value.toLowerCase().includes($scope.searchCinemaText.toLowerCase())){
							op[i].selected=true;
							break;
						}
					};
				};
				$scope.projectionAndDate=function(){
					$scope.projectionDate=true;
					let selCinema = document.getElementById("cinemaCombo").selectedIndex;
					$scope.cinemaSelected = $rootScope.cinemasShow[selCinema];
					$http.get('http://localhost:8181/reguser/movies/'+$scope.cinemaSelected.id).success(function(data,status){
						$scope.moviesShow=data;
					});
					$scope.showSeatsPreview=false;
				};
				$scope.timeAndAud=function(){
					$scope.chosenDate=$("#datepicker").datepicker().val();
					$scope.chosenMovie = $scope.moviesShow[document.getElementById("movieCombo").selectedIndex];
					let num=0;
					$scope.projShow={};
					
					$http.get('http://localhost:8181/reguser/projections/'+$scope.chosenMovie.id).success(function(data, status){
						for(let x in data){
							if($scope.chosenDate==data[x].date){
								$scope.projShow[num]=data[x];
								num=num+1;
							}
						};
					});
					$scope.timeAud=true;	
				};
				$scope.chosenProjection= null;
				$scope.loadSeats=function(){
					var myEl = angular.element( document.querySelector( '#seat-map' ) );
					myEl.empty();
					$scope.chosenProjection = $scope.projShow[document.getElementById("projCombo").selectedIndex];
					if($scope.chosenProjection==null){
						alert('Projection not chosen!');
					}else{
						$http.get('http://localhost:8181/reguser/seats/'+$scope.chosenProjection.id).success(function(data,status){
							$scope.seatovi=data[0];
							$scope.takenSeatovi=data[1];
							$http.get('http://localhost:8181/reguser/auditorium/'+$scope.chosenProjection.audId).success(function(data, status){
								$scope.rNum=data.rowNumber;
								arrangement();
							});		
						});
					}
				};
				arrangement=function(){
					var allSeats = [];
					var takenSeats=[];
					for(let i=0;i<$scope.rNum;i++){
						allSeats.push('');
					};
					let numrow=0;
					for(let x in $scope.seatovi){
						numrow=parseInt($scope.seatovi[x].number[0]);
						allSeats[numrow-1]=allSeats[numrow-1]+'a';
					};
					$rootScope.h=allSeats;
					let numcol;
					for(let x in $scope.takenSeatovi){
						numrow=$scope.takenSeatovi[x].number[0];
						numcol = $scope.takenSeatovi[x].number[1];
						takenSeats.push(numrow+"_"+numcol);		
					};
					var $cart = $('#selected-seats');
					$scope.selectedSeats=[];
					$scope.seatsSel=0;
					var $sc = $('#seat-map').seatCharts({
						map: allSeats,
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
								var ss = {};
								for(let x in $scope.seatovi){
									if($scope.seatovi[x].number==""+(this.settings.row+1)+this.settings.label){
										ss['id']=$scope.seatovi[x].id;
										break;
									}
								};
								ss['num']=""+(this.settings.row+1)+this.settings.label;
								$scope.selectedSeats[$scope.seatsSel]=ss;
								$scope.seatsSel=$scope.seatsSel+1;
								return 'selected';
							} 
							else if (this.status() == 'selected') {
									$('#cart-item-'+this.settings.id).remove();
									var index = $scope.selectedSeats.indexOf(""+(this.settings.row+1)+this.settings.label);
									$scope.selectedSeats.splice(index, 1);
									$scope.seatsSel=$scope.seatsSel-1;
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
					$sc.get(takenSeats).status('unavailable');
					
					
					$scope.showSeatsPreview=true;
					$scope.projectionDate=false;
					$scope.timeAud=false;
				};
				var currentUser =JSON.parse(localStorage.getItem("currentUser"));
				$scope.inviteFriends=function(){
					if($scope.seatsSel==1){
						$scope.finishReservation();
					}else{
						$http.get('http://localhost:8181/reguser/friends/'+currentUser.email).success(function(data, status){
						$scope.friends=data;
						$scope.friendsBox=true;
					});
					}
				};
				var friendsChosen=0;
				var friendsSelected={};
				let x = 0;
				$scope.inviteFriend=function(friend){
					//disable pojedinacni
					friendsChosen=friendsChosen+1;
					if(friendsChosen==$scope.seatsSel-1){
						$scope.disableButtons=true;
					}
					friendsSelected[x]=friend;
					x=x+1;
				}
				var s=0;
				$scope.finishReservation=function(){		
					$http.post('http://localhost:8181/reguser/makeReservation/'+currentUser.email+'/'+$scope.selectedSeats[s].id+'/'+$scope.chosenProjection.id).success(function(data, status){
						$scope.reservId=data;
						s=s+1;
						saveData();
					});
				};
				saveData=function(){
					for(let x in friendsSelected){
						$http.post('http://localhost:8181/reguser/makeTicket/'+friendsSelected[x].email+'/'+$scope.chosenProjection.id+'/'+$scope.reservId+'/'+$scope.selectedSeats[s].id).success(function(data, status){
							alert('Successfully reserved a ticket!');
						});
						s=s+1;
					};
					//redirekcija
				};
			}
		]
);