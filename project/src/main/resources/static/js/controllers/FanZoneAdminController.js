angular.module('Application').controller(
		'FanZoneAdminController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {
				$rootScope.home_btn=true;
				$rootScope.log_out_btn=true;
				$scope.currentUser=JSON.parse(localStorage.getItem("currentUser"));
				
				if ($scope.currentUser.first_time){
					$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":true, "div6":false};
				}else{
					$scope.show_div = {"div1":true, "div2":false, "div3":false, "div4":false, "div5":false, "div6":false};		
					document.getElementById("adding_promo_official").style.display="block";
					document.getElementById("official_products").style.display="block";
					document.getElementById("used_products").style.display="block";
				}
				
				
				$scope.show_div1=function(){
					$scope.show_div = {"div1":true, "div2":false, "div3":false, "div4":false, "div5":false, "div6":false};
				};
				$scope.show_div2=function(){
					if ($scope.cinemas_and_theaters.length==0){
						//$scope.show_adding_po=false;
						alert("can't add official product when there is no cinema");
					}else{
						$scope.show_div = {"div1":false, "div2":true, "div3":false, "div4":false, "div5":false, "div6":false};
					}					
				};
				$scope.show_div3=function(){
					$scope.show_div = {"div1":false, "div2":false, "div3":true, "div4":false, "div5":false, "div6":false};
				};
				$scope.show_div4=function(){
					$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":true, "div5":false, "div6":false};
				};
				$scope.show_div5=function(){
					$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":true, "div6":false};
				};
				
				
				
				//dodatne funkcije
				fill_promos_official=function(){
					$http.get('http://localhost:8181/fan_zone_admin/list_promos_official').success(function(data, status){
						$scope.promosOfficial=data;
					}).error(function(){
						alert("couldn't list official promos");
					});
				};
				
				fill_unapproved_promos_used=function(){
					$http.get('http://localhost:8181/fan_zone_admin/list_promos_unapproved').success(function(data, status){
						$scope.unapprovedPromosUsed=data;
					}).error(function(){
						alert("couldn't list unapproved used promos");
					});
				};
				
				
				fill_changing_promo=function(po_id){
					$http.get('http://localhost:8181/fan_zone_admin/get_promo_official/'+po_id).success(function(data, status){
						$scope.changing_promo=data;
						
						if ($scope.changing_promo.activity=="reserved"){ //ne moze da menja jer vec postoji buyer ili je proslo vreme
							alert("can't change reserved official product");
						}else{
							var select = document.getElementById('cinemas_changing');	
							for (var i = 0; i<$scope.cinemas_and_theaters.length; i++){
							    if (select.options[i].value==$scope.changing_promo.cinema_id){
							    	select.options[i].selected="true";
							    }				    
							    
							}
							document.getElementById("changing_user_image").value=$scope.currentUser.image;
							$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":false, "div6":true};
						}				
					}).error(function(){
						alert("couldn't get changing promo");
					});
				};
				
				fill_cinemas=function(){
					$http.get('http://localhost:8181/cinemas').success(function(data, status){
						var count=data.length;
						for (var i=0; i<count;i++){
							$scope.cinemas_and_theaters.push(data[i]);
						}
						fill_theaters();
					}).error(function(){
						alert("error finding cinemas");
					});//
				};
				
				fill_theaters=function(){
					$http.get('http://localhost:8181/theaters').success(function(data, status){
						var count=data.length;
						for (var i=0; i<count;i++){
							$scope.cinemas_and_theaters.push(data[i]);
						}
						add_options_cinemas_and_theaters();
					}).error(function(){
						alert("error finding theaters");
					});//
				};
				
				add_options_cinemas_and_theaters=function(){
					var select = document.getElementById('cinemas');
					for (var i = 0; i<$scope.cinemas_and_theaters.length; i++){
					    var opt = document.createElement('option');
					    opt.value = $scope.cinemas_and_theaters[i].id;
					    opt.innerHTML = $scope.cinemas_and_theaters[i].name;
					    select.appendChild(opt);
					}
					
					var select2 = document.getElementById('cinemas_changing');	
					for (var i = 0; i<$scope.cinemas_and_theaters.length; i++){
					    var opt = document.createElement('option');
					    opt.value = $scope.cinemas_and_theaters[i].id;
					    opt.innerHTML = $scope.cinemas_and_theaters[i].name;			    
					    select2.appendChild(opt);
					}
					
				};
				
				
				//////////prebaci pozive metoda u funkcije za divove
				
				//ucitavanje podataka za div2
				$scope.cinemas_and_theaters=[];
				fill_cinemas();
				
				
				//ucitavanje podataka za div3
				$scope.promosOfficial={};
				fill_promos_official();
				
				//ucitavanje podataka za div4
				$scope.unapprovedPromosUsed={};
				fill_unapproved_promos_used();
				
				//ucitavanje podataka za div6
				$scope.changing_promo={};
				
			
				
				$scope.add_po=function(){
					var poPrice = parseFloat($scope.poPrice);
					if($scope.poName && !isNaN(poPrice)){	
						if (poPrice>0){
							var poDescription = $scope.poDescription;
							if (!poDescription){
								poDescription="No description";
							}
							var poImage = $scope.poImage;
							if (!poImage){
								poImage="http://cdn7.bigcommerce.com/s-viqdwewl26/stencil/8f903ed0-76e7-0135-12e4-525400970412/icons/icon-no-image.svg";
							}
							poImage = poImage.replace(/\//g, "+");
							poImage = poImage.replace(/\?/g, "*");
							var e = document.getElementById("cinemas");
							var cId = e.options[e.selectedIndex].value;
							$http.get('http://localhost:8181/fan_zone_admin/add_promo_official/'+ $scope.poName+'/'+ poDescription+'/'+poImage+'/'+poPrice+'/'+cId).success(function(){
								$scope.poName="";
								$scope.poDescription="";
								$scope.poPrice="";
								$scope.poImage="";
								fill_promos_official();
								alert("successfuly added a product");
							}).error(function(){
								alert("Couldn't create this promo official");
								
							});
						}else{
							alert("Wrong input in fields!");
						}
						
					}else{
						alert("Wrong input in fields!");
					}
				};
				
				$scope.delete_po=function(poid){
					$http.get('http://localhost:8181/fan_zone_admin/get_promo_official/'+poid).success(function(data, status){
						var promo=data;
						if (promo.buyer_email!="none"){
							alert("Can't delete a product which has a buyer");							
						}else{
							$http.get('http://localhost:8181/fan_zone_admin/delete_promo_official/'+poid).success(function(){
								fill_promos_official();
								alert("successfuly deleted a product");
							}).error(function(){
								alert("Couldn't delete official promo");
							});
						}
					}).error(function(){
						alert("Couldn't find official promo");
					});
					
				};
				
				$scope.change_po=function(poid){
					fill_changing_promo(poid);
				};
				
				$scope.save_changes_po=function(poid){
					var poPrice = parseFloat(document.getElementById("changing_promo_price").value);
					var poName = document.getElementById("changing_promo_name").value;
					if(poName && !isNaN(poPrice)){	
						if (poPrice>0){
							var poDescription = document.getElementById("changing_promo_description").value;
							if (!poDescription){
								poDescription="No description";
							}
							var poImage = document.getElementById("changing_promo_image").value;
							if (!poImage){
								poImage="http://cdn7.bigcommerce.com/s-viqdwewl26/stencil/8f903ed0-76e7-0135-12e4-525400970412/icons/icon-no-image.svg";
							}
							poImage = poImage.replace(/\//g, "+");
							poImage = poImage.replace(/\?/g, "*");
							var e = document.getElementById("cinemas_changing");
							var cId = e.options[e.selectedIndex].value;
							$http.get('http://localhost:8181/fan_zone_admin/update_promo_official/'+ poName+'/'+ poDescription+'/'+poImage+'/'+poPrice+'/'+cId+'/'+poid).success(function(){
								fill_changing_promo(poid);
								fill_promos_official();
								alert("you've successfuly updated a product");
							}).error(function(){
								alert("Couldn't update this promo official");
								
							});
						}else{
							alert("Wrong input in fields!");
						}
						
					}else{
						alert("Wrong input in fields!");
					}
				};
				
				$scope.cancel_changes_po=function(poid){
					fill_changing_promo(poid);
				};
				
				$scope.approve_pu=function(poid){
					$http.get('http://localhost:8181/fan_zone_admin/approve_promo_used/'+poid).success(function(){
						fill_unapproved_promos_used();
					}).error(function(){
						alert("Couldn't approve official promo");
					});
				};
				
				$scope.disapprove_pu=function(poid){
					$http.get('http://localhost:8181/fan_zone_admin/delete_promo_used/'+poid).success(function(){			
						fill_unapproved_promos_used();
						alert("successfully disapproved product");
					}).error(function(){
						alert("Couldn't disapprove official promo");
					});
				};
				
				
				$scope.save_changes_user=function(){
					var new_image = document.getElementById("changing_user_image").value;
					var new_pass = document.getElementById("new_pass").value;
					var old_pass = document.getElementById("old_pass").value;
					//ukoliko je za sliku prazno, salje se stara slika
					//ukoliko je new_pass popunjeno, old_pass ne sme biti prazno
					//ukoliko ni new_image ni new_pass nisu popunjeni, alert da nista nije promenio
					if (!new_image && !new_pass){
						document.getElementById("old_pass").value="";
						$scope.currentUser=JSON.parse(localStorage.getItem("currentUser"));
						alert("Haven't made any changes");
					}
					else if (!new_pass && new_image){
						new_pass=$scope.currentUser.password;
						new_image = new_image.replace(/\//g, "+");
						new_image = new_image.replace(/\?/g, "*");
						$http.post('http://localhost:8181/fan_zone_admin/change_user_info/'+$scope.currentUser.email+'/'+new_image+'/'+new_pass).success(function(data, status){
							localStorage.setItem("currentUser",angular.toJson(data));
							$scope.currentUser = data;
							document.getElementById("new_pass").value="";
							document.getElementById("old_pass").value="";
							document.getElementById("changing_user_image").value="";
							alert("Successfuly changed info");
						}).error(function(){
							alert("Can't change fan zone admin info");
						});
					}
					else if (new_pass && old_pass){
						if (old_pass==$scope.currentUser.password){
							if (!new_image){
								new_image=$scope.currentUser.image;
							}
							new_image = new_image.replace(/\//g, "+");
							new_image = new_image.replace(/\?/g, "*");
							$http.post('http://localhost:8181/fan_zone_admin/change_user_info/'+$scope.currentUser.email+'/'+new_image+'/'+new_pass).success(function(data, status){
								localStorage.setItem("currentUser",angular.toJson(data));
								$scope.currentUser = data;
								document.getElementById("new_pass").value="";
								document.getElementById("old_pass").value="";
								document.getElementById("changing_user_image").value="";
								if (!$scope.currentUser.first_time){
									document.getElementById("adding_promo_official").style.display="block";
									document.getElementById("official_products").style.display="block";
									document.getElementById("used_products").style.display="block";
								}
								alert("Successfuly changed info");
							}).error(function(){
								alert("Can't change fan zone admin info");
							});
						}else{
							document.getElementById("new_pass").value="";
							document.getElementById("old_pass").value="";
							alert("Old password doesn't match your real old password!");
						}
						
					}
						
					else if (new_pass && !old_pass){
						alert("Can't change password without knowing old password");
						document.getElementById("new_pass").value="";
						document.getElementById("old_pass").value="";
					}		
					
				};
				
				$scope.cancel_changes_user=function(){
					$scope.currentUser=JSON.parse(localStorage.getItem("currentUser"));
				};
			}
		]
);