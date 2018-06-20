var app = angular.module('Application').controller(
		'PeopleController',
		[
				'$rootScope',
				'$scope',
				'$http',
				'$routeParams',
				'$location',
				'$window',
				
				function($rootScope, $scope, $http, $routeParams, $location, $window) {
					
					$rootScope.unknownPeople=[];
					$rootScope.friends=[];
					$scope.addBtn = false;
					
					$scope.load=function(){
						loadPeople();
						loadFriends();
					};
					
					loadPeople=function(){
						var currentUser = JSON.parse(localStorage.getItem("currentUser"));
						$http.get('/reguser/people/' + currentUser.email).success(function(data, status){
							$rootScope.unknownPeople=data;
						});
					};
					
					loadFriends=function(){
						var currentUser = JSON.parse(localStorage.getItem("currentUser"));
						$http.get('/reguser/friends/'+currentUser.email).success(function(data, status){
							$rootScope.friends=data;
						});
					};
					
					$scope.close=function(){
						$scope.showPeopleModal=false;
					};	
					
					$rootScope.showPeople=function(what){
						$scope.addBtn=what;
						
						if(what==true){
							$scope.showing=$rootScope.unknownPeople;
						}else{
							$scope.showing=$rootScope.friends;
						};
						
						
						if($scope.showing.length==0){
							$rootScope.alert('No people to show.');
							$scope.showPeopleModal=false;
						}else{
							$scope.showPeopleModal=true;
						};
					};
					
					$scope.addFriend=function(friend){
						var currentUser = JSON.parse(localStorage.getItem("currentUser"));
						$http.post('/reguser/addFriend/'+currentUser.email+'/'+friend.email).success(function(data, status){
							$rootScope.alert("You've sent a friend request to "+friend.firstName+" " +friend.lastName+"!");
							
							var idx = $rootScope.unknownPeople.indexOf(friend);
							$rootScope.unknownPeople.splice(idx , 1);		
							
							if($rootScope.unknownPeople.length==0){
								$scope.showPeopleModal=false;
							};	
						});
					};
					$scope.removeFriend=function(friend){
						var currentUser = JSON.parse(localStorage.getItem("currentUser"));
						$http.post('/reguser/removeFriend/'+currentUser.email+'/'+friend.email).success(function(data, status){
							$rootScope.alert("You've removed "+friend.firstName+" " +friend.lastName+" from your friends!");
							
							var idx = $rootScope.friends.indexOf(friend);
							$rootScope.friends.splice(idx , 1);	
							$rootScope.unknownPeople.push(friend);

							if($rootScope.friends.length==0){
								$scope.showPeopleModal=false;
							};	
							
							$rootScope.changeInFriends(friend, true);
						});
					};
				}
		]
);

