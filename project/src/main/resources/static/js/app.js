angular.module('Application', ['angular-jwt' ,'ngRoute']).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/home', {
				templateUrl : 'html/homepage.html',
				controller : 'HomeController'
			}).when('/login', {
				templateUrl : 'html/homepage.html',
				controller : 'HomeController'
			}).when('/register', {
				templateUrl : 'html/homepage.html',
				controller : 'HomeController'
			}).when('/signup', {
				templateUrl : 'html/register.html',
				controller : 'RegisterController'
			}).when('/profile', {
				templateUrl : 'html/profile.html',
				controller : 'UserProfileController'
			}).when('/edit_cinema_basic', {
				templateUrl : 'html/edit_cinema_basic.html',
				controller : 'CinemaProfileController'
			}).when('/add_movie', {
				templateUrl : 'html/add_movie.html',
				controller : 'CinemaProfileController'
			}).when('/make_qt', {
				templateUrl : 'html/form_make_qt.html',
				controller : 'CinemaProfileController'
			}).when('/add_projection', {
				templateUrl : 'html/form_add_projection.html',
				controller : 'CinemaProfileController'
			}).when('/list_cinemas', {
				templateUrl : 'html/list.html',
				controller : 'CinemaListController'
			}).when('/cinema_profile', {
				templateUrl : 'html/cinema_profile.html',
				controller : 'CinemaProfileController'						
			}).when('/fan_zone_admin', {
				templateUrl : 'html/fan_zone_admin.html',
				controller : 'FanZoneAdminController'			
			}).when('/system_admin', {
				templateUrl: 'html/system_admin.html',
				controller : 'SystemAdminController'
			}).when('/cinema_admin', {
				templateUrl : 'html/cinema_admin.html',
				controller : 'CinemaAdminController'
			}).when('/cinema_admin/change_password', {
				templateUrl : 'html/change_pass.html',
				controller : 'CinemaAdminController'
			}).when('/error', {
				templateUrl : 'html/error.html'
			}).otherwise({
				redirectTo : '/home'
			});
		} ]); 
		
	