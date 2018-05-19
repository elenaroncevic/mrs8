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
								//redirekt na pocetnu str fan zone admina dd
							}
							else{
								document.getElementById("adding_promo_official").style.display="block";
								select = document.getElementById('cinemas');	
								for (var i = 0; i<$scope.cinemaList.length; i++){
								    var opt = document.createElement('option');
								    opt.value = $scope.cinemaList[i].id;
								    opt.innerHTML = $scope.cinemaList[i].name+" "+$scope.cinemaList[i].location;
								    select.appendChild(opt);
								}
							}
					}).error(function(){
						alert("error finding cinemas");
					});//
				};
				
				
				$scope.btn_add_promo_official = function(){
					$location.path('/fan_zone_admin/add_po').replace();
				};
			
				$scope.add_promo_official = function() {
					var poPrice = parseFloat($scope.poPrice);
					if($scope.poName && !isNaN(poPrice)){	
						var poDescription = $scope.poDescription;
						if (!poDescription){
							poDescription="No description";
						}
						var poImage = $scope.poImage;
						if (!poImage){
							poImage="https//blog.stylingandroid.com/wp-content/themes/lontano-pro/images/no-image-slide.png";
						}
						poImage = poImage.replace(/\//g, "+");
						poImage = poImage.replace(/\?/g, "*");
						var e = document.getElementById("cinemas");
						var cId = e.options[e.selectedIndex].value;
						$http.get('http://localhost:8181/fan_zone_admin/add_promo_official/'+ $scope.poName+'/'+ poDescription+'/'+poImage+'/'+poPrice+'/'+cId).success(function(){
							alert("you've successfuly added a product");
							$location.path('/fan_zone_admin').replace();
						}).error(function(){
							alert("Couldn't create this promo official");
							
						});
					}else{
						alert("Wrong input in fields!");
					}

				};
				
				
				$scope.btn_list_promos_official = function(){
					$location.path('/fan_zone_admin/list_po').replace();
				};
				
				$scope.btn_list_promos_unapproved = function(){
					$location.path('/fan_zone_admin/list_pu_unapproved').replace();
				}
				
				$scope.list_promos_unapproved=function(){
					$http.get('http://localhost:8181/fan_zone_admin/list_promos_unapproved').success(function(data, status){
						$scope.listOfPromosUnapproved=data;
						if ($scope.listOfPromosUnapproved.length!=0){
							document.getElementById("list_pu_unapproved_fz").style.display="block";
						}else{
							alert("There is no unapproved promo");
							$location.path('/fan_zone_admin').replace();
						}
					}).error(function(){
						alert("Couldn't list unapproved promos");
					});
				};
				
				$scope.list_promos_official = function(){
					$http.get('http://localhost:8181/fan_zone_admin/list_promos_official').success(function(data, status){
						$scope.listOfPromosOfficial=data;
						if ($scope.listOfPromosOfficial.length!=0){
							document.getElementById("list_po_fz").style.display="block";
						}else{
							alert("There is no official product in database");
							$location.path('/fan_zone_admin').replace();
						}
					}).error(function(){
						alert("Couldn't list official promos");
					});
				};
				
				$scope.btn_change_po = function(id){
					$rootScope.changing_po_id=id;
					$location.path('/fan_zone_admin/change_po').replace();
				};
				
				$scope.change_promo_official = function(){
					var poPrice = parseFloat($scope.poPrice);
					if($scope.poName && !isNaN(poPrice)){	
						var poDescription = $scope.poDescription;
						if (poDescription==null){
							poDescription="No description";
						}
						var poImage = $scope.poImage;
						if (!poImage){
							poImage="https//blog.stylingandroid.com/wp-content/themes/lontano-pro/images/no-image-slide.png";
						}
						poImage = poImage.replace(/\//g, "+");
						poImage = poImage.replace(/\?/g, "*");
						var e = document.getElementById("cinemas");
						var cId = e.options[e.selectedIndex].value;
						$http.get('http://localhost:8181/fan_zone_admin/update_promo_official/'+ $scope.poName+'/'+ poDescription+'/'+poImage+'/'+poPrice+'/'+cId+'/'+$rootScope.changing_po_id).success(function(){
							alert("you've successfuly changed a product");
							$location.path('/fan_zone_admin').replace();
						}).error(function(){
							alert("Couldn't change this promo official");
							
						});
					}else{
						alert("Wrong input in fields!");
					}
				};
				
				$scope.btn_cancel_changing_promo_official = function(){
					$location.path('/fan_zone_admin/list_po').replace();
				};
				
				$scope.btn_delete_po = function(id){
					$http.get('http://localhost:8181/fan_zone_admin/get_promo_official/'+id).success(function(data, status){
						$scope.deleting_po=data;
						if ($scope.deleting_po.buyer_email!="none"){
							alert("Can't delete a product which has a buyer");							
						}else{
							$http.get('http://localhost:8181/fan_zone_admin/delete_promo_official/'+id).success(function(){
								alert("sucessfuly deleted a product");
								$location.path('/fan_zone_admin/list_po').replace();
								//kako ovde da napravim da mi se refresh prikaz liste
							}).error(function(){
								alert("can't delete a product");
							});
						}
					}).error(function(){
						alert("Couldn't find official promo");
					});
					
				};
				
				$scope.btn_approve_pu = function(id){
					$http.get('http://localhost:8181/fan_zone_admin/approve_promo_used/'+id).success(function(){
						alert("successfuly approved a product");
						$location.path('/fan_zone_admin').replace();
						//kako ovde da napravim da mi se refresh prikaz liste
					}).error(function(){
						alert("can't approve a product");
					});						
					
				};
				
				$scope.btn_disapprove_pu = function(id){
					$http.get('http://localhost:8181/fan_zone_admin/delete_promo_used/'+id).success(function(){
						alert("successfuly disapproved a product");
						$location.path('/fan_zone_admin').replace();
						//kako ovde da napravim da mi se refresh prikaz liste
					}).error(function(){
						alert("can't disaprove a product");
					});						
					
				};
				
				$scope.get_po = function(){
					$http.get('http://localhost:8181/fan_zone_admin/get_promo_official/'+$rootScope.changing_po_id).success(function(data, status){
						$scope.changing_po=data;
						if ($scope.changing_po.buyer_email=="none"){						
								$http.get('http://localhost:8181/cinemas').success(function(data, status){
									$scope.cinemaList=data;
									
									select = document.getElementById('cinemas');	
									for (var i = 0; i<$scope.cinemaList.length; i++){
									    var opt = document.createElement('option');
									    opt.value = $scope.cinemaList[i].id;
									    opt.innerHTML = $scope.cinemaList[i].name+" "+$scope.cinemaList[i].location;
									    if (opt.value==$scope.changing_po.cinema_id){
									    	opt.selected="true";
									    }				    
									    select.appendChild(opt);
									}
									
									$scope.poName=$scope.changing_po.name;
									$scope.poDescription=$scope.changing_po.description;
									$scope.poPrice=$scope.changing_po.price;
									$scope.poImage=$scope.changing_po.image;
										
								}).error(function(){
									alert("ima neki error");
								});//
							
							document.getElementById("changing_promo_official").style.display="block";
							
						}else{
							alert("You can't change a product which has a buyer");
							$location.path('/fan_zone_admin/list_po').replace()
						}
					}).error(function(){
						alert("Couldn't find official promo");
					});
				};

			}
		]
);