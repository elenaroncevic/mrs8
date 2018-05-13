angular.module('Application').controller(
		'FanZoneAdminController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$scope.list_cinemas = function() {
					$http.get('http://localhost:8181/cinemas').success(function(data, status){
							$scope.cinemaList=data;
							if($scope.cinemaList.length==0){
							
								alert("You can't add official promo because there is no cinema");
								//redirekt na pocetnu str fan zone admina
							}
							else{
								document.getElementById("adding_promo_official").style.display="block";
								select = document.getElementById('cinemas');	
								for (var i = 0; i<=$scope.cinemaList.length; i++){
								    var opt = document.createElement('option');
								    opt.value = $scope.cinemaList[i].id;
								    opt.innerHTML = $scope.cinemaList[i].name+" "+$scope.cinemaList[i].location;
								    select.appendChild(opt);
								}
							}
					}).error(function(){
						alert("ima neki error");
					});
				};
			
				$scope.add_promo_official = function() {
					var poPrice = parseFloat($scope.poPrice);
					if($scope.poName.length!=null && !isNaN(poPrice)){	
						var poDescription = $scope.poDescription;
						if (poDescription==null){
							poDescription="No description";
						}
						var poImage = $scope.poImage;
						if (poImage==null){
							poImage="https://blog.stylingandroid.com/wp-content/themes/lontano-pro/images/no-image-slide.png";
						}
						
						var e = document.getElementById("cinemas");
						var cId = e.options[e.selectedIndex].value;
						$http.post('http://localhost:8181/add_promo_official/'+ $scope.poName+'/'+ poDescription+'/'+poImage+'/'+poPrice+'/'+cId).success(function(){
							//ovde ce se zameniti da ode na stranicu koja javlja uspesno dodavanje
							$location.path('/register_new_user').replace();
						}).error(function(){
							alert("Couldn't create this promo official");
						});
					}else{
						alert("Wrong input in fields!");
					}

				};

			}
		]
);