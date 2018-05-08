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
			}).when('/signin', {
				templateUrl : 'html/login.html',
				controller : 'LoginController'
			}).when('/signup', {
				templateUrl : 'html/register.html',
				controller : 'RegisterController'
			}).when('/profile', {
				templateUrl : 'html/profile.html',
				controller : 'HomeController'
			}).when('/error', {
				templateUrl : 'html/error.html'
			}).otherwise({
				redirectTo : '/home'
			});
		} ]);
		
	