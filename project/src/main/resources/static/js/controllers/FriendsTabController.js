angular.module('Application').controller(
		'FriendsTabController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {				
				$scope.noMore=false;
				
 				$rootScope.alertShow=false;
 				
				$scope.people=function(){
					$scope.noMore=true;
				};
				
				$scope.friends=function(){
					$rootScope.alert('sadads');
				};
				
				
				$scope.searchFriends=function(){
					var op = document.getElementById("friendsCombo").getElementsByTagName("option");
					for(let i=0;i<op.length;i++){
						if(op[i].value.toLowerCase().includes($scope.searchFriendsText)){
							op[i].seletected=true;
							break;
						};
					};
				};
				
				
				$scope.loadRequests=function(){
					$http.get('/reguser/friendReq/'+$scope.currentUser.email).success(function(data, status){
						$scope.requests=data;
						if(data.length!=0){
							$scope.reqExists=true;
						}else{
							$scope.reqExists=false;
						}
					});
				};
				$scope.acceptReq=function(friend){
					$http.post('/reguser/acceptedReq/'+friend.id).success(function(data, status){
						alert("You've accepted " + friend.firstName+" " + friend.lastName+" as your friend!");
						$scope.loadRequests();
					});
				};
				$scope.declineReq=function(friend){
					$http.post('/reguser/declinedReq/'+friend.id).success(function(data, status){
						alert("You've declined " + friend.firstName+" " + friend.lastName+"'s friend request!");
						$scope.loadRequests();
					});
				};
				$scope.addFriend=function(){
					let idx = document.getElementById("unknownCombo").selectedIndex;
					$http.post('/reguser/addFriend/'+$scope.currentUser.email+'/'+$scope.unknownsShow[idx].email).success(function(data, status){
						alert("You've sent a friend request to "+$scope.unknownsShow[idx].firstName+" " +$scope.unknownsShow[idx].lastName+"!");
						$scope.people();
					});
				};
				$scope.removeFriend=function(){
					let idx = document.getElementById("friendsCombo").selectedIndex;
					$http.post('/reguser/removeFriend/'+$scope.currentUser.email+'/'+$scope.friendsShow[idx].email).success(function(data, status){
						alert("You've removed "+$scope.friendsShow[idx].firstName+" " +$scope.friendsShow[idx].lastName+" from your friends!");
						$scope.friends();
					});
				};
			}
		]
);