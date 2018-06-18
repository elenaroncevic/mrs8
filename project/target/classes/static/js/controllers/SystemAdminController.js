angular.module('Application').controller(
		'SystemAdminController',
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
					$scope.show_div = {"div1":false, "div2":true, "div3":false, "div4":false, "div5":false};
				}else{
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
						alert("can't register cinema or theater when there are no cinema/theater admins");
					}else{
						$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":true};
					}
				};
				
				//dodatne funkcije
				fill_cinema_admins=function(){
					$http.get('http://localhost:8181/system_admin/list_cinema_admins').success(function(data, status){
						$scope.cinema_admins=data;
						add_options_cinema_admins();
					}).error(function(){
						alert("error finding cinema admins");
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
					$http.get('http://localhost:8181/system_admin/get_scale').success(function(data, status){
						$scope.scale=data;
					}).error(function(){
						alert("error getting point scale");
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
						alert("Haven't made any changes");
					}
					else if (!new_pass && new_image){
						new_pass=$scope.currentUser.password;
						new_image = new_image.replace(/\//g, "+");
						new_image = new_image.replace(/\?/g, "*");
						$http.post('http://localhost:8181/system_admin/change_user_info/'+$scope.currentUser.email+'/'+new_image+'/'+new_pass).success(function(data, status){
							localStorage.setItem("currentUser",angular.toJson(data));
							$scope.currentUser = data;
							document.getElementById("new_pass").value="";
							document.getElementById("old_pass").value="";
							document.getElementById("changing_user_image").value="";
							alert("Successfuly changed info");
						}).error(function(){
							alert("Can't change system admin info");
						});
					}
					else if (new_pass && old_pass){
						if (old_pass==$scope.currentUser.password){
							if (!new_image){
								new_image=$scope.currentUser.image;
							}
							new_image = new_image.replace(/\//g, "+");
							new_image = new_image.replace(/\?/g, "*");
							$http.post('http://localhost:8181/system_admin/change_user_info/'+$scope.currentUser.email+'/'+new_image+'/'+new_pass).success(function(data, status){
								localStorage.setItem("currentUser",angular.toJson(data));
								$scope.currentUser = data;
								document.getElementById("new_pass").value="";
								document.getElementById("old_pass").value="";
								if (!$scope.currentUser.first_time){
									document.getElementById("registering_admins").style.display="block";
									document.getElementById("setting_scale").style.display="block";
									document.getElementById("registering_cinemas").style.display="block";
								}
								alert("Successfuly changed info");
							}).error(function(){
								alert("Can't change system admin info");
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
							$http.post('http://localhost:8181/system_admin/register_new_admin/'+ $scope.admEmail+'/'+ $scope.admPass1+'/'+strUser).success(function(){
								alert("Successfuly registered admin");
								$scope.admPass1="";
								$scope.admPass2="";
								$scope.admEmail="";
							}).error(function(){							
								alert("Couldn't register this admin");
							});
						}else{
							$scope.admPass1="";
							$scope.admPass2="";
							$scope.admEmail="";
							alert("Passwords don't match! Try again!");
						}
					}else{
						alert("All fields are mandatory!");
					}
					

				};
				
				
				$scope.savePointScale=function(){
					var copper = parseInt(document.getElementById("copper").value);
					var silver = parseInt(document.getElementById("silver").value);
					var golden = parseInt(document.getElementById("golden").value);
					
					if (!isNaN(copper) || !isNaN(silver) || !isNaN(golden)){
						if (golden <= silver || golden <= copper || silver <= copper || golden<0 || silver<0 || copper<0 || !Number.isInteger(golden) || !Number.isInteger(silver) || !Number.isInteger(copper)){
							$scope.scale=fill_scale();
							alert("the comparison must be: copper < silver < golden and all medals must be positive and integer");
						}else{
							$http.get('http://localhost:8181/system_admin/update_scale/'+copper+'/'+silver+'/'+golden).success(function(data, status){
								$scope.scale=data;
								alert("Successfuly updated point scale");
							}).error(function(){
								alert("Unsuccessful scale update");
							});
						}
					}else{
						alert("Medals must be integers!");
					}
				};
				
				
				$scope.registerBuilding=function(){		
					var description=document.getElementById("buildingDescription").value;
					if (!description){
						description="No description";
					}
					var name=document.getElementById("buildingName").value;
					if (name && $scope.lat && $scope.lng){
						var index=document.getElementById("buildingType").selectedIndex;
						var buildingType=document.getElementById("buildingType").options[index].value;
						
						var index2 = document.getElementById("buildingAdmin").selectedIndex;
						var adminEmail = document.getElementById("buildingAdmin").options[index2].value;
						$http.get('http://localhost:8181/system_admin/register_cinema/'+adminEmail+'/'+name+'/'+description+'/'$scope.lat+'/'+$scope.lng+'/'+buildingType).success(function(){
							alert("successfuly registered building!");
							document.getElementById("buildingName").value="";
							document.getElementById("buildingDescription").value="";
						}).error(function(){
							alert("Couldn't register building");
						});
					}else{
						alert("Name field and location can't be blank");
					}
					
					
				};
				
	
				
				
				initMap = function(){
					var options={
						zoom:8,
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
		]
);