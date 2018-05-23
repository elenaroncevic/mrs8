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
    				$( "#datepicker" ).datepicker();
				} );		
				$scope.projectionDate=false;
				$scope.timeAud=false;
				$scope.showSeatsPreview=false;
				$scope.friendsBox=false;
				$scope.disableButtons=false;
				$scope.searchTheaters=function(){
					var op = document.getElementById("theaterCombo").getElementsByTagName("option");
					for(let i =0;i<op.length;i++){
						if(op[i].value.toLowerCase().includes($scope.searchTheaterText.toLowerCase())){
							op[i].selected=true;
							break;
						}
					};
				};
				$scope.projectionAndDate=function(){
					$scope.projectionDate=true;
					let selTheater = document.getElementById("theaterCombo").selectedIndex;
					$scope.theaterSelected = $rootScope.theatersShow[selTheater];
					$http.get('http://localhost:8181/reguser/movies/'+$scope.theaterSelected.id).success(function(data,status){
						$scope.showsShow=data;
						alert('hm');
					});
					$scope.showSeatsPreview=false;
				};
				$scope.timeAndAud=function(){
					$rootScope.h=$scope.showsShow;
					if($scope.showsShow.length==0){
						alert('No shows found');
						return;
					};				
					$scope.chosenDate=$("#datepicker").datepicker().val();
					$scope.chosenShow = $scope.showsShow[document.getElementById("showCombo").selectedIndex];
					let num=0;
					$scope.projShow={};
					
					$http.get('http://localhost:8181/reguser/projections/'+$scope.chosenShow.id).success(function(data, status){
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
							$rootScope.h=$scope.takenSeatovi;
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
						numrow=$scope.seatovi[x].rowNum-1;
						allSeats[numrow]=allSeats[numrow]+'a';
					};
					let numcol;
					for(let x in $scope.takenSeatovi){
						numrow=$scope.takenSeatovi[x].rowNum;
						numcol = $scope.takenSeatovi[x].number;
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
									if($scope.seatovi[x].rowNum==""+(this.settings.row+1) && $scope.seatovi[x].number==this.settings.label){
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
							
						});
						s=s+1;
					};
					alert('Successfully reserved a ticket!');
					$rootScope.loadReservations();
					$scope.projectionDate=false;
					$scope.timeAud=false;
					$scope.showSeatsPreview=false;
					$scope.friendsBox=false;
				};
			}
		]
);