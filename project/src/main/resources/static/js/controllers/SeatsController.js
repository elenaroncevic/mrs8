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
				var friendsChosen=0;
				var friendsSelected=[];
				var selectedSeats=[];
			    var seatsSel=0;
			    
			    var first = 0;
				var userChange = "";
				
				
				$rootScope.loadModal=function(chosenProjection){
					
					var currentUser = JSON.parse(localStorage.getItem("currentUser"));
				
					if(first==0 || currentUser.email==userChange){
						$scope.friendsSeats=[];
						loadFriendsSeats();
						first=1;
						userChange=currentUser.email;
					}else{
						for(let x in $scope.friendsSeats){
							$scope.friendsSeats[x].invite = false;
							$scope.friendsSeats[x].remove=true;
						};
					};
					friendsChosen=0;
					friendsSelected=[];
					selectedSeats=[];
			    	seatsSel=0;
					
					$scope.seatsFriends=true;
					loadSeats(chosenProjection);
				};
				
				
				$scope.close=function(){
					$rootScope.modal.show=false;
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
				
				$rootScope.changeInFriends=function(friend, del){
					for(let x in $scope.friendsSeats){
						$scope.friendsSeats[x].invite = false;
						$scope.friendsSeats[x].remove=true;
					};
					
					var cf = {"name":friend.firstName+" "+friend.lastName,"email":friend.email,"invite": false, "remove": true};
					$rootScope.h=$scope.friendsSeats;
					if(del==true){
						var idx= $scope.friendsSeats.indexOf(cf);
						$scope.friendsSeats.splice(idx,1);
					}else{
						$scope.friendsSeats.push(cf);
					};
				};
				
				loadFriendsSeats=function(){
					let i = 0;
					$scope.friendsSeats = [];
					
					var currentUser = JSON.parse(localStorage.getItem("currentUser"));
						for(let x in $rootScope.friends){
							let friend = {};		
							friend['name']=$rootScope.friends[x].firstName+" " +$rootScope.friends[x].lastName;
							friend['email']=$rootScope.friends[x].email;
							friend['invite']=true;
							friend['remove']=true;
							$scope.friendsSeats[i] = friend;
							i=i+1;
						};
						$rootScope.h=$scope.friendsSeats;
				};
				
				$scope.inviteFriends=function(){
					if($scope.friendsSeats.length==0){
						$rootScope.alert('You have no friends. Sorry.');
						return;
					};
					$rootScope.h=selectedSeats;
					if(selectedSeats.length<2){
						$rootScope.alert('No enough seats selected for friends. Please, select more seats.');
					}else{
						for(let x in $scope.friendsSeats){
							$scope.friendsSeats[x].invite = false;
							$scope.friendsSeats[x].remove=true;
						};
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
						for(let x in $scope.friendsSeats){
							$scope.friendsSeats[x].invite=true;
						};
					}
					
					var idx = $scope.friendsSeats.indexOf(friend);
					$scope.friendsSeats[idx].invite = true;
					$scope.friendsSeats[idx].remove=false;
					friendsSelected.push(idx);
					
				}
				$scope.removeFriend=function(friend){
					friendsChosen=friendsChosen-1;
					if(friendsChosen==seatsSel-2){
						for(let x in $scope.friendsSeats){
							if($scope.friendsSeats[x].remove==true){
								$scope.friendsSeats[x].invite=false;
							}
						};
					}
					var idx = $scope.friendsSeats.indexOf(friend);
					$scope.friendsSeats[idx].remove = true;
					$scope.friendsSeats[idx].invite=false;
					
					var remIdx = friendsSelected.indexOf(idx);
					friendsSelected.splice(remIdx,1);
				};
				
				
				$scope.finishReservation=function(){
					if(seatsSel==0){
						$rootScope.alert("Can't make a reservation without any seats chosen. Please, choose your seats!");
						return;
					} 
					
					var currentUser = JSON.parse(localStorage.getItem("currentUser"));
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
							if($rootScope.current=="cinema"){
								$rootScope.refreshReservation();
							}else{
								$rootScope.refreshReservationTheater();
							};						
							$rootScope.reservationsShow.push(data);
						});						
						return;
					}
					var friendsStr = '';
					for(let s in friendsSelected){
						if(s==0){
							friendsStr=friendsStr+$scope.friendsSeats[friendsSelected[s]].email;
							continue;
						}
						friendsStr=friendsStr+','+$scope.friendsSeats[friendsSelected[s]].email;
					};
					$http.post('/reguser/sendEmails/'+friendsStr+'/'+$scope.reservId).success(function(data, status){
							if($rootScope.current=="cinema"){
								$rootScope.refreshReservation();
							}else{
								$rootScope.refreshReservationTheater();
							};						
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
						$rootScope.alert('no seats');
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