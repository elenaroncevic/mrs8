angular.module('Application').controller(
		'SystemAdminController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$http',
			'$location', 
			function($rootScope, $scope, $window, $http, $location) {				
				$scope.currentUser=JSON.parse(localStorage.getItem("currentUser"));
				
				var path;
				if (localStorage.getItem("regUser")=="true"){
					path="/profile";
				}else if (localStorage.getItem("sysAdm")=="true"){
					path="/system_admin";
				}else if (localStorage.getItem("cinAdm")=="true"){
					path="/cinema_admin";
				}
				if (!$scope.currentUser){
					$location.path('/home').replace();
				}else if (localStorage.getItem("sysAdm")=="false"){
					$rootScope.alert("You don't have privilege to see this account");
					$location.path(path).replace();
				}else{
					if ($scope.currentUser.first_time){
						$scope.show_div = {"div1":false, "div2":true, "div3":false, "div4":false, "div5":false};
					}else{
						$rootScope.home_btn=true;
						$rootScope.log_out_btn=true;
						
						$scope.show_div = {"div1":true, "div2":false, "div3":false, "div4":false, "div5":false};		
						document.getElementById("registering_admins").style.display="block";
						document.getElementById("setting_scale").style.display="block";
						document.getElementById("registering_cinemas").style.display="block";
					}
					
					
					$scope.show_div1=function(){
						$scope.show_div = {"div1":true, "div2":false, "div3":false, "div4":false, "div5":false};
					};
					
					$scope.show_div2=function(){
						$scope.show_div = {"div1":false, "div2":true, "div3":false, "div4":false, "div5":false};
					};
					
					$scope.show_div3=function(){
						if ($scope.currentUser.def==null || $scope.currentUser.def==0){
							var op = document.getElementById("type").getElementsByTagName("option");
							for (var i = 0; i < op.length; i++) {
							  (op[i].value.toLowerCase() == "system") 
							    ? op[i].disabled = true 
							    : op[i].disabled = false ;
							  //
							}
						}
						$scope.show_div = {"div1":false, "div2":false, "div3":true, "div4":false, "div5":false};
					};
					
					$scope.show_div4=function(){
						$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":true, "div5":false};
					};
					
					$scope.show_div5=function(){
						if ($scope.cinema_admins.length==0){
							//$scope.show_adding_po=false;
							$rootScope.alert("can't register cinema or theater when there are no cinema/theater admins");
						}else{
							$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":true};
						}
					};
					
					//dodatne funkcije
					fill_cinema_admins=function(){
						$http.get('/system_admin/list_cinema_admins').success(function(data, status){
							$scope.cinema_admins=data;
							add_options_cinema_admins();
						}).error(function(){
							$rootScope.alert("error finding cinema admins");
						});//
					};
					
					
					add_options_cinema_admins=function(){
						var select = document.getElementById('buildingAdmin');
						for (var i = 0; i<$scope.cinema_admins.length; i++){
						    var opt = document.createElement('option');
						    opt.value = $scope.cinema_admins[i].email;
						    opt.innerHTML = $scope.cinema_admins[i].email;
						    select.appendChild(opt);
						}	
						
					};
					
					fill_scale=function(){
						$http.get('/system_admin/get_scale').success(function(data, status){
							$scope.scale=data;
						}).error(function(){
							$rootScope.alert("error getting point scale");
						});
					};
					
					
					//////////
					//ucitavanje podataka za div5
					$scope.cinema_admins={};
					fill_cinema_admins();
					
					//ucitavanje podataka za div4
					$scope.scale={};
					fill_scale();
					
					//podaci za mapu
					$scope.marker=false; //kad se sacuva lokacija ili cancel dodavanje, onda marker opet stavim na false
					$scope.map;
					
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
							$rootScope.alert("Haven't made any changes");
						}
						else if (!new_pass && new_image){
							new_pass=$scope.currentUser.password;
							new_image = new_image.replace(/\//g, "+");
							new_image = new_image.replace(/\?/g, "*");
							$http.post('/system_admin/change_user_info/'+$scope.currentUser.email+'/'+new_image+'/'+new_pass).success(function(data, status){
								localStorage.setItem("currentUser",angular.toJson(data));
								$scope.currentUser = data;
								document.getElementById("new_pass").value="";
								document.getElementById("old_pass").value="";
								document.getElementById("changing_user_image").value="";
								$rootScope.alert("Successfuly changed info");
							}).error(function(){
								$rootScope.alert("Can't change system admin info");
							});
						}
						else if (new_pass && old_pass){
							if (old_pass==$scope.currentUser.password){
								if (!new_image){
									new_image=$scope.currentUser.image;
								}
								new_image = new_image.replace(/\//g, "+");
								new_image = new_image.replace(/\?/g, "*");
								$http.post('/system_admin/change_user_info/'+$scope.currentUser.email+'/'+new_image+'/'+new_pass).success(function(data, status){
									localStorage.setItem("currentUser",angular.toJson(data));
									$scope.currentUser = data;
									document.getElementById("new_pass").value="";
									document.getElementById("old_pass").value="";
									if (!$scope.currentUser.first_time){
										document.getElementById("registering_admins").style.display="block";
										document.getElementById("setting_scale").style.display="block";
										document.getElementById("registering_cinemas").style.display="block";
									}
									$rootScope.alert("Successfuly changed info");
								}).error(function(){
									$rootScope.alert("Can't change system admin info");
								});
							}else{
								document.getElementById("new_pass").value="";
								document.getElementById("old_pass").value="";
								$rootScope.alert("Old password doesn't match your real old password!");
							}
							
						}
							
						else if (new_pass && !old_pass){
							$rootScope.alert("Can't change password without knowing old password");
							document.getElementById("new_pass").value="";
							document.getElementById("old_pass").value="";
						}		
						
					};
					
					$scope.cancel_changes_user=function(){
						document.getElementById("new_pass").value="";
						document.getElementById("old_pass").value="";
						document.getElementById("changing_user_image").value="";
						$scope.currentUser=JSON.parse(localStorage.getItem("currentUser"));
					};
					
					
					$scope.registerAdmin = function() {
						if ($scope.admEmail && $scope.admPass1 && $scope.admPass2){
							if($scope.admPass1==$scope.admPass2){
								var e = document.getElementById("type");
								var strUser = e.options[e.selectedIndex].value;
								if (strUser=="cinema/theater"){
									strUser="cinema";
								}
								$http.post('/system_admin/register_new_admin/'+ $scope.admEmail+'/'+ $scope.admPass1+'/'+strUser).success(function(){
									$rootScope.alert("Successfuly registered admin");
									$scope.admPass1="";
									$scope.admPass2="";
									$scope.admEmail="";
								}).error(function(){							
									$rootScope.alert("Couldn't register this admin");
								});
							}else{
								$scope.admPass1="";
								$scope.admPass2="";
								$scope.admEmail="";
								$rootScope.alert("Passwords don't match! Try again!");
							}
						}else{
							$rootScope.alert("All fields are mandatory!");
						}
						

					};
					
					
					$scope.cancelAdminRegistration=function(){
						$scope.admPass1="";
						$scope.admPass2="";
						$scope.admEmail="";
					};
					
					
					$scope.savePointScale=function(){
						var copper = parseInt(document.getElementById("copper").value);
						var silver = parseInt(document.getElementById("silver").value);
						var golden = parseInt(document.getElementById("golden").value);
						
						var copper_discount = parseInt(document.getElementById("copper_discount").value);
						var silver_discount = parseInt(document.getElementById("silver_discount").value);
						var golden_discount = parseInt(document.getElementById("golden_discount").value);
						
						if (golden <= silver || golden <= copper || silver <= copper || golden<=0 || silver<=0 || copper<=0 || golden_discount<=silver_discount || golden_discount<=copper_discount || silver_discount<=copper_discount || golden_discount<0 || silver_discount<0 || copper_discount<0 || golden_discount>=100 || silver_discount>=100 || copper_discount>=100){
							$scope.scale=fill_scale();
							$rootScope.alert("the comparison must be: copper < silver < golden and copper discount < silver discount < golden discount and all medals must be positive and all discounts must be >=0 and <100");
						}else{
							$http.get('/system_admin/update_scale/'+copper+'/'+silver+'/'+golden+'/'+copper_discount+'/'+silver_discount+'/'+golden_discount).success(function(data, status){
								$scope.scale=data;
								$rootScope.alert("Successfuly updated point scale");
							}).error(function(){
								$rootScope.alert("Unsuccessful scale update");
							});
						}

					};
					
					$scope.cancelScaleChanges=function(){
						$scope.scale=fill_scale();
					};
					
					
					$scope.registerBuilding=function(){		
						var description=document.getElementById("buildingDescription").value;
						var lat = document.getElementById("lat").value;
						var lng = document.getElementById("lng").value;
						
						if (!description){
							description="No description";
						}
						var name=document.getElementById("buildingName").value;
						if (name && lat && lng){
							var index=document.getElementById("buildingType").selectedIndex;
							var buildingType=document.getElementById("buildingType").options[index].value;
							
							var index2 = document.getElementById("buildingAdmin").selectedIndex;
							var adminEmail = document.getElementById("buildingAdmin").options[index2].value;
							$http.get('/system_admin/register_cinema/'+adminEmail+'/'+name+'/'+description+'/'+lat+'/'+lng+'/'+buildingType).success(function(){
								$rootScope.alert("successfuly registered building!");
								document.getElementById("buildingName").value="";
								document.getElementById("buildingDescription").value="";
								document.getElementById("lat").value="";
								document.getElementById("lng").value="";
								$scope.marker=false;					
								initMap();
							}).error(function(){
								$rootScope.alert("Building with the same name already exists");
							});
						}else{
							$rootScope.alert("Name field and location can't be blank");
						}
						
						
					};
					
					$scope.cancelBuildingRegistration=function(){
						document.getElementById("buildingName").value="";
						document.getElementById("buildingDescription").value="";
						document.getElementById("lat").value="";
						document.getElementById("lng").value="";
						$scope.marker=false;					
						initMap();
					};
		
					
					
					initMap = function(){
						var options={
							zoom:16,
							center:{
								lat:45.2671,lng:19.8335
							}
						}

						$scope.map=new google.maps.Map(document.getElementById('map'),options);

						//Dynamicly add markers on map click
						google.maps.event.addListener($scope.map, 'click', function(event){
							var clickedLocation=event.latLng;
							if ($scope.marker===false){
								$scope.marker=new google.maps.Marker({
									position:clickedLocation,
									map:$scope.map,
									draggable:true
								});
								
								google.maps.event.addListener($scope.marker,'dragend', function(event){
									markerLocation();
								});
							}else{
								$scope.marker.setPosition(clickedLocation);
							}
							markerLocation();
						});
						
					};
					
					markerLocation=function(){
						var currentLocation=$scope.marker.getPosition();
						document.getElementById('lat').value=currentLocation.lat();
						document.getElementById('lng').value=currentLocation.lng();
						
					};
					
					initMap();
				}
			
			}
		]
);