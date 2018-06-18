angular.module('Application').controller(
		'SeatsController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$scope.showSeatsPreview = true;
				
				var currentUser = JSON.parse(localStorage.getItem("currentUser"));
				
				$scope.close=function(){
					$scope.showModal=false;
				};
				$scope.show = false;
				$rootScope.loadSeats = function(chosenProjection){
					var myEl = angular.element( document.querySelector( '#seat-map' ) );
					myEl.empty();
					var myEl2 = angular.element(document.querySelector( '#selected-seats'));
					myEl2.empty();
					
					$http.get('/reguser/seats/'+chosenProjection.id).success(function(data,status){
						$scope.seatovi=data[0];
						$scope.takenSeatovi=data[1];
						$http.get('/reguser/auditorium/'+chosenProjection.audId).success(function(data, status){
							arrangement(data.rowNumber);
						});		
					});
				};
				
				
				$scope.loadFriends=function(){
					let i = 0;
					$scope.friends = {};
					$http.get('http://localhost:8181/reguser/friends/'+currentUser.email).success(function(data, status){
						for(let x in data){
							let friend = [];		
							friend['name']=data[x].firstName+" " +data[x].lastName;
							friend['email']=data[x].email;
							friend['invite']=true;
							friend['remove']=true;
							$scope.friends[i] = friend;
							i=i+1;
						};
					});
				};
				
				
				var friendsChosen=0;
				var friendsSelected={};
				let x = 0;
				
				$scope.inviteFriend=function(friend){
					var idx = $scope.friends.indexOf(friend);
					$scope.friends[idx].invite = true;
					$scope.friends[idx].remove=false;
					return;
					
					friendsChosen=friendsChosen+1;
					if(friendsChosen==seatsSel-1){
						$scope.disableButtons=true;
					}
					friendsSelected[x]=friend;
					x=x+1;
				}
				$scope.removeFriend=function(friend){
					var idx = $scope.friends.indexOf(friend);
					$scope.friends[idx].remove = true;
					$scope.friends[idx].invite=false;
					return;
				};
				
				
				var s=0;
				$scope.finishReservation=function(){	
					return;	
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

				
				
				
				
				arrangement=function(rowNumber){
					var allSeats = [];
					var takenSeats=[];
					for(let i=0;i<rowNumber;i++){
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
					seatsSel=0;
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
								$scope.selectedSeats[seatsSel]=ss;
								seatsSel=seatsSel+1;
								if(seatsSel==2){
									alert('mhm');
									$scope.allowInvite();
								}
								return 'selected';
							} 
							else if (this.status() == 'selected') {
									$('#cart-item-'+this.settings.id).remove();
									var index = $scope.selectedSeats.indexOf(""+(this.settings.row+1)+this.settings.label);
									$scope.selectedSeats.splice(index, 1);
									seatsSel=seatsSel-1;
									if(seatsSel==1){
										alert('mhm');
										$scope.forbid();
									};
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
				};
				
				$scope.allowInvite=function(){
				
					$scope.show=true;
					alert('ss');
					for(let x in $scope.friends){
						$scope.friends[x].invite = false;
						alert($scope.friends[x].invite);
					};
				};
				$scope.forbid = function(){
					alert('ss');
					for(let x in $scope.friends){
						$scope.friends[x].invite = true;
						$scope.friends[x].remove=true;
					};	
				};
				
				
			}
		]
);