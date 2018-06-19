angular.module('Application').controller(
		'SeatsController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				var projId;
				var currentUser = JSON.parse(localStorage.getItem("currentUser"));
				var friendsChosen=0;
				var friendsSelected=[];
				var selectedSeats=[];
			    var seatsSel=0;
				
				
				$rootScope.loadModal=function(chosenProjection){
					$scope.seatsFriends=true;
					for(let x in $scope.friendsShow){
						$scope.friendsShow[x].invite=false;
						$scope.frirendsShow[x].remove=true;
					};
					loadSeats(chosenProjection);
				};
				
				
				$scope.close=function(){
					$scope.showModal=false;
				};
				
				loadSeats = function(chosenProjection){
					var myEl = angular.element( document.querySelector( '#seat-map' ) );
					myEl.empty();
					projId = chosenProjection.id;
					
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
					$scope.friends = [];
					
					$http.get('/reguser/friends/'+currentUser.email).success(function(data, status){
						for(let x in data){
							let friend = {};		
							friend['name']=data[x].firstName+" " +data[x].lastName;
							friend['email']=data[x].email;
							friend['invite']=true;
							friend['remove']=true;
							$scope.friends[i] = friend;
							i=i+1;
						};
					});
				};
				
				$scope.inviteFriends=function(){
					if(selectedSeats.length<2){
						alert('No enough seats selected for friends. Please, select more seats.');
					}else{
						for(let x in $scope.friends){
							$scope.friends[x].invite = false;
							$scope.friends[x].remove=true;
						};
						alert('hm');
						$scope.seatsFriends=false;
					}
				};
				
				$scope.backToSeats=function(){
					friendsChosen=0;
					friendsSelected=[];
					$scope.seatsFriends=true;
				};
				
				
				$scope.inviteFriend=function(friend){
					friendsChosen=friendsChosen+1;
					if(friendsChosen==seatsSel-1){
						for(let x in $scope.friends){
							$scope.friends[x].invite=true;
						};
					}
					
					var idx = $scope.friends.indexOf(friend);
					$scope.friends[idx].invite = true;
					$scope.friends[idx].remove=false;
					friendsSelected.push(idx);
					y=y+1;
					
				}
				$scope.removeFriend=function(friend){
					friendsChosen=friendsChosen-1;
					if(friendsChosen==seatsSel-2){
						for(let x in $scope.friends){
							if($scope.friends[x].remove==true){
								$scope.friends[x].invite=false;
							}
						};
					}
					var idx = $scope.friends.indexOf(friend);
					$scope.friends[idx].remove = true;
					$scope.friends[idx].invite=false;
					friendsSelected.remove(idx);
				};
				
				
				$scope.finishReservation=function(){
					if(seatsSel==0){
						alert('Cant make a reservation without any seats chosen. Please, choose your seats!');
						return;
					}
					$http.post('/reguser/makeReservation/'+currentUser.email).success(function(data, status){
						$scope.reservId=data;
						saveTickets();
					});
				};
				
				saveTickets=function(){
					var seatsStr = '';
					for(let s in selectedSeats){
						if(s==0){
							seatsStr = seatsStr+selectedSeats[s].id;
							continue;
						}
						seatsStr = seatsStr+','+selectedSeats[s].id;
					};
					$http.post('/reguser/makeTicket/'+projId+'/'+$scope.reservId+'/'+seatsStr+'/'+friendsSelected.length).success(function(data, status){
							sendEmails();
					});									
				};				
				
				sendEmails=function(){
					if(friendsSelected.length==0){
						$http.post('/reguser/sendEmail/'+$scope.reservId).success(function(data, status){
							$rootScope.refreshReservation();
							$rootScope.reservationsShow.push(data);
						});						
						return;
					}
					var friendsStr = '';
					for(let s in friendsSelected){
						if(s==0){
							friendsStr=friendsStr+$scope.friends[friendsSelected[s]].email;
							continue;
						}
						friendsStr=friendsStr+','+$scope.friends[friendsSelected[s]].email;
					};
					$http.post('/reguser/sendEmails/'+friendsStr+'/'+$scope.reservId).success(function(data, status){
						$rootScope.refreshReservation();
						$rootScope.reservationsShow.push(data);
					});
					
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
					
					if(allSeats==[] || takenSeats==[]){
						alert('no seats');
						return;
					}
					
					var $cart = $('#selected-seats');
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
								selectedSeats[seatsSel]=ss;
								seatsSel=seatsSel+1;
								return 'selected';
							} 
							else if (this.status() == 'selected') {
									$('#cart-item-'+this.settings.id).remove();
									var index = selectedSeats.indexOf(""+(this.settings.row+1)+this.settings.label);
									selectedSeats.splice(index, 1);
									seatsSel=seatsSel-1;
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
				
			}
		]
);