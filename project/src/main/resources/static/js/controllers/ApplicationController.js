var app = angular.module('Application').controller(
		'ApplicationController',
		[
				'$rootScope',
				'$scope',
				'$http',
				'$routeParams',
				'$location',
				'$window',
				
				function($rootScope, $scope, $http, $routeParams, $location, $window) {

					$rootScope.home_btn=false;
					$rootScope.log_out_btn=false;
					$rootScope.return_home=function(){
						var current = JSON.parse(localStorage.getItem("currentUser"));
						if(current!=null &&current.hasOwnProperty("firstName")){
							$location.path('/profile').replace();
						}else if(current!=null &&current.hasOwnProperty("def")){
							$location.path('/system_admin').replace();
						}else if(current!=null && current.hasOwnProperty("cinemas")){
							$location.path('/cinema_admin').replace();
						}else{
							$location.path('/fan_zone_admin').replace();
						}
					}
					$rootScope.logout=function(){
						localStorage.removeItem("currentUser");
						localStorage.removeItem("currentCinema");
						$rootScope.alert('You have successfully logged out!');
						$location.path('/home').replace();
						$rootScope.home_btn=false;
						$rootScope.log_out_btn=false;
						
						localStorage.removeItem("regUser");
						localStorage.removeItem("sysAdm");
						localStorage.removeItem("cinAdm");
						localStorage.removeItem("fzAdm");
					};
				}
		]
);


app.directive('modal', function () {
  
  return {
    restrict: 'E',
    transclude: true,
    scope: {
      showModal: '='
    },
    templateUrl: 'html/seats.html',
    controller: 'SeatsController' ,

  }
});

app.directive('modalPeople', function () {
  
  return {
    restrict: 'E',
    transclude: true,
    scope: {
      showPeopleModal: '='
    },
    templateUrl: 'html/people.html',
    controller: 'PeopleController' ,

  }
});

app.directive('modalAlert', function () {
  
  return {
    restrict: 'E',
    transclude: true,
    scope: {
      showAlert: '='
    },
    templateUrl: 'html/alert.html',
    controller: 'AlertController' ,

  }
});

app.directive('modalRegister', function () {
  
  return {
    restrict: 'E',
    transclude: true,
    scope: {
      showReg: '='
    },
    templateUrl: 'html/register.html',
    controller: 'RegisterController' ,

  }
});

app.directive('modalChangePass', function () {
  
  return {
    restrict: 'E',
    transclude: true,
    scope: {
      showChange: '='
    },
    templateUrl: 'html/changePass.html',
    controller: 'ChangePassController' ,

  }
});

app.directive('modalUpdateInfo', function () {
  
  return {
    restrict: 'E',
    transclude: true,
    scope: {
      showUpdate: '='
    },
    templateUrl: 'html/updateInfo.html',
    controller: 'UpdateInfoController' ,

  }
});