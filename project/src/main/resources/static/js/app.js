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
				controller : 'EditCinemaController'
			}).when('/list_cinemas', {
				templateUrl : 'html/list.html',
				controller : 'CinemaListController'
			}).when('/cinema_profile', {
				templateUrl : 'html/cinema_profile.html',
				controller : 'CinemaProfileController'
			}).when('/system_admin/register_new_admin', {
				templateUrl : 'html/register_new_admin.html',
				controller : 'SystemAdminController'
			}).when('/fan_zone_admin/add_promo_official', {
				templateUrl : 'html/add_promo_official.html',
				controller : 'FanZoneAdminController'
			}).when('/error', {
				templateUrl : 'html/error.html'
			}).otherwise({
				redirectTo : '/home'
			});
		} ]);
		
	