angular.module('Application').controller(
		'FriendsTabController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {	
				var firstPpl=0;
				var firstFriends=0;
				
 				$rootScope.alertShow=false;
 				
				$scope.people=function(){
					$rootScope.showPeople(true);
				};
				
				$scope.friends=function(){
					$rootScope.showPeople(false);
				};				
				
				$scope.loadRequests=function(){
					var currentUser = JSON.parse(localStorage.getItem("currentUser"));
					$http.get('/reguser/friendReq/'+currentUser.email).success(function(data, status){
						if(data.length==0){
							$scope.requests={};
							$scope.reqs=false;
						}else{
							$scope.requests=data;
							$scope.reqs=true;
						};
					});
				};
				
				
				$scope.acceptRequest=function(friend){
					$http.post('/reguser/acceptedReq/'+friend.id).success(function(data, status){
						$rootScope.alert("You've accepted " + friend.firstName+" " + friend.lastName+" as your friend!");
						removeFromList($scope.requests.indexOf(friend));
						$rootScope.friends.push(friend);
					});
					$rootScope.changeInFriends(friend, false);
				};
				$scope.declineRequest=function(friend){
					$http.post('/reguser/declinedReq/'+friend.id).success(function(data, status){
						$rootScope.alert("You've declined " + friend.firstName+" " + friend.lastName+"'s friend request!");
						removeFromList($scope.requests.indexOf(friend));
						$rootScope.unknownPeople.push(friend);
					});
				};
				
				removeFromList=function(idx){
					$scope.requests.splice(idx , 1);
					if($scope.requests.length==0){
						$scope.reqs=false;
					}
				};
				
			}
		]
);