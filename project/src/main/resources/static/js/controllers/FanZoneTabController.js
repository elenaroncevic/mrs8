angular.module('Application').controller(
		'FanZoneTabController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
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
				}else if (localStorage.getItem("regUser")=="false"){
					alert("You don't have privilege to see this account");
					$location.path(path).replace();
				}else{
					$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":false, "div6":false, "div7":false, "div8":false, "div9":false};
					
					$scope.show_div1=function(){
						$scope.show_div = {"div1":true, "div2":false, "div3":false, "div4":false, "div5":false, "div6":false, "div7":false, "div8":false, "div9":false};
					};
					$scope.show_div2=function(){
						$scope.show_div = {"div1":false, "div2":true, "div3":false, "div4":false, "div5":false, "div6":false, "div7":false, "div8":false, "div9":false};
					};
					$scope.show_div3=function(){
						$scope.show_div = {"div1":false, "div2":false, "div3":true, "div4":false, "div5":false, "div6":false, "div7":false, "div8":false, "div9":false};
					};
					$scope.show_div4=function(){
						$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":true, "div5":false, "div6":false, "div7":false, "div8":false, "div9":false};
					};
					$scope.show_div5=function(){
						$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":true, "div6":false, "div7":false, "div8":false, "div9":false};
					};
					$scope.show_div6=function(){
						$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":false, "div6":true, "div7":false, "div8":false, "div9":false};
					};
					$scope.show_div7=function(){
						$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":false, "div6":false, "div7":true, "div8":false, "div9":false};
					};
					
					
					
					//dodatne funkcije
					fill_unreserved_promos_official=function(){
						$http.get('/reg_user/list_unreserved_po').success(function(data, status){
							$scope.unreservedPromosOfficial=data;
						}).error(function(){
							alert("couldn't list unreserved official promos");
						});
					};
					
					fill_my_reserved_promos_official=function(){
						$http.get('/reg_user/list_reserved_po/'+$scope.currentUser.email+'/nesto').success(function(data, status){
							$scope.myReservedPromosOfficial=data;
						}).error(function(){
							alert("couldn't list my reserved official promos");
						});
					};
					
					fill_others_promos_used=function(){
						$http.get('/reg_user/list_others_approved_pu/'+$scope.currentUser.email+'/nesto').success(function(data, status){
							$scope.othersPromosUsed=data;
						}).error(function(){
							alert("couldn't list others used promos");
						});
					};
					
					
					fill_my_bidded_promos_used=function(){
						$http.get('/reg_user/list_my_bids_pu/'+$scope.currentUser.email+'/nesto').success(function(data, status){
							$scope.myBiddedPromosUsed=data;
						}).error(function(){
							alert("couldn't list my bidded promos");
						});
					};
					
					fill_my_posted_promos_used=function(){
						$http.get('/reg_user/list_my_posted_pu/'+$scope.currentUser.email+'/nesto').success(function(data, status){
							$scope.myPostedPromosUsed=data;
						}).error(function(){
							alert("couldn't list my posted promos");
						});
					};
					
					fill_my_won_promos_used=function(){
						$http.get('/reg_user/list_my_won_pu/'+$scope.currentUser.email+'/nesto').success(function(data, status){
							$scope.myWonPromosUsed=data;
						}).error(function(){
							alert("couldn't list my won promos");
						});
					};
					
					
					
					fill_licitation=function(pu_id){
						$http.get('/reg_user/get_pu/'+pu_id).success(function(data, status){
							$scope.licitation=data;
							
							var lic_date=($scope.licitation.ending_date).replace(' ','T');
							lic_date=lic_date+"Z";
							var licitations_date=new Date(lic_date);
							var now=new Date();
							if ($scope.licitation.activity=="bought" || licitations_date.getTime()<now.getTime()){ //ne moze da biduje jer vec postoji buyer ili je proslo vreme
								document.getElementById("adding_bid").style.display="none";
							}else{
								document.getElementById("adding_bid").style.display="block";
							}
							$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":false, "div6":false, "div7":false, "div8":true, "div9":false};
						}).error(function(){
							alert("couldn't get promo licitation");
						});
					};
					
					fill_bids_in_licitation=function(pu_id){
						$http.get('/reg_user/list_bids/'+pu_id).success(function(data, status){
							$scope.bidsInLicitation=data;
						}).error(function(){
							alert("couldn't list bids in promo used");
						});
					};
					
					fill_changing_promo=function(pu_id){
						$http.get('/reg_user/get_pu/'+pu_id).success(function(data, status){
							$scope.changing_promo=data;
							
							var lic_date=($scope.changing_promo.ending_date).replace(' ','T');
							lic_date=lic_date+"Z";
							var licitations_date=new Date(lic_date);
							var now=new Date();
							if ($scope.changing_promo.activity=="bought" || licitations_date.getTime()<now.getTime()){ //ne moze da menja jer vec postoji buyer ili je proslo vreme
								document.getElementById("able_to_change").style.display="none";
								document.getElementById("unable_to_change").style.display="block";
								document.getElementById("save_or_reset").style.display="none";
								$scope.can_choose_buyer=false;
							}else{
								document.getElementById("able_to_change").style.display="block";
								document.getElementById("unable_to_change").style.display="none";
								document.getElementById("save_or_reset").style.display="block";
								$scope.can_choose_buyer=true;
							}
							$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":false, "div6":false, "div7":false, "div8":false, "div9":true};
						}).error(function(){
							alert("couldn't get changing promo");
						});
					};
					
				
					
					////
				
					
					//ucitavanje podataka za div2
					$scope.unreservedPromosOfficial={};
					fill_unreserved_promos_official();
					
					//ucitavanje podataka za div3
					$scope.myReservedPromosOfficial={};
					fill_my_reserved_promos_official();
					
					//ucitavanje podataka za div4
					$scope.otersPromosUsed={};
					fill_others_promos_used();
					
					//ucitavanje podataka za div5
					$scope.myBiddedPromosUsed={};
					fill_my_bidded_promos_used();
					
					//ucitavanje podatka za div6
					$scope.myPostedPromosUsed={};
					fill_my_posted_promos_used();
					
					//ucitavanje podataka za div7
					$scope.myWonPromosUsed={};
					fill_my_won_promos_used();
					
					//ucitavanje podataka za div8
					$scope.licitation={};
					$scope.bidsInLicitation={};
					
					//ucitavanje podataka za div9
					$scope.changing_promo={};
					
					
					$scope.add_promo_used = function(){
						//proveravam podatke - name, description, image, date, time
						
						var date = document.getElementById("ending_date").value; //da li ovde treba .value  
						
						if ($scope.puName && date){
							var lic_date=date;
							lic_date=lic_date+"Z";
							var licitations_date=new Date(lic_date);
							var now = new Date();
							if (now.getTime()>licitations_date.getTime()){
								alert("you must select date and time in the future");
							}else{
								var puDescription = $scope.puDescription;					
								if (!puDescription){
									puDescription="No description";
								}
								var puImage = $scope.puImage;
								if (!puImage){
									puImage="http://cdn7.bigcommerce.com/s-viqdwewl26/stencil/8f903ed0-76e7-0135-12e4-525400970412/icons/icon-no-image.svg";
								}
								puImage = puImage.replace(/\//g, "+");
								puImage = puImage.replace(/\?/g, "*");
								
								
								$http.get('/reg_user/add_pu/'+$scope.currentUser.email+'/'+$scope.puName+'/'+puDescription+'/'+puImage+'/'+date).success(function(){
									alert('successfuly added a product');
									$scope.puName="";
									$scope.puDescription="";
									$scope.puImage="";
								}).error(function(){
									alert("couldn't create used product");
								});
							}	
						}else{
							alert("invalid data in fields");
						}	
					};
					
					$scope.reserve_po=function(id){
						$http.get('/reg_user/reserve_po/'+$scope.currentUser.email+'/'+id).success(function(){
							fill_unreserved_promos_official();
							fill_my_reserved_promos_official();
							alert('successfuly reserved a product');				
						}).error(function(){
							alert("couldn't create used product");
						});
					};
					
					
					$scope.unreserve_po=function(id){
						$http.get('/reg_user/unreserve_po/'+id).success(function(){
							fill_my_reserved_promos_official();
							fill_unreserved_promos_official();
							alert('successfuly unreserved a product');				
						}).error(function(){
							alert("couldn't create used product");
						});
					};
					
					
					$scope.see_pu=function(id){
						fill_licitation(id);
						fill_bids_in_licitation(id);
					};
					
					
					$scope.add_my_bid=function(id, ending_date){
						//provera valjanog unosa za ponudu i opet provera za datum
						var offer=parseFloat(document.getElementById("given_price").value);
						var lic_date=(ending_date).replace(' ','T');   
						lic_date=lic_date+"Z";
						var licitations_date=new Date(lic_date);
						var now = new Date();
						if (!isNaN(offer) && offer>=0 && now.getTime()<licitations_date.getTime()){
							//dodavanje/update bida
							$http.get('/reg_user/update_bid/'+$scope.currentUser.email+'/'+offer+'/'+id).success(function(){
								fill_licitation(id);
								fill_bids_in_licitation(id);
								fill_my_bidded_promos_used();
								document.getElementById("given_price").value="";
								alert('successfuly given a bid');				
							}).error(function(){
								alert("couldn't give a bid");
							});
						}else{
							alert("time for bidding is over or you have wrong input in field");
							fill_licitation(id);
						}
					};
					
					
					$scope.see_my_pu=function(id){
						fill_changing_promo(id);
						fill_bids_in_licitation(id);
					};
					
					$scope.save_changed_pu=function(puid, ending_date){
						var name=document.getElementById("changing_promo_name").value;
						var description=document.getElementById("changing_promo_description").value;
						var image=document.getElementById("changing_promo_image").value;
						var lic_date=(ending_date).replace(' ','T');   
						lic_date=lic_date+"Z";
						var licitations_date=new Date(lic_date);
						var now = new Date();
						if (name && now.getTime()<licitations_date.getTime()){
							if (!description){
								description="No description";
							}
							if (!image){
								image="http://cdn7.bigcommerce.com/s-viqdwewl26/stencil/8f903ed0-76e7-0135-12e4-525400970412/icons/icon-no-image.svg";
							}
							image = image.replace(/\//g, "+");
							image = image.replace(/\?/g, "*");
							
							$http.get('/reg_user/update_pu/'+puid+'/'+name+'/'+image+'/'+description).success(function(){
								fill_changing_promo(puid);
								fill_my_posted_promos_used();
								alert('successfuly updated a product');				
							}).error(function(){
								alert("couldn't update a product");
							});
						}else{
							alert("time for changing promo is over or you have wrong input in field");
							fill_changing_promo(puid);
						}
					};
					
					$scope.cancel_changes_pu= function(puid, ending_date){
						var lic_date=(ending_date).replace(' ','T');   
						lic_date=lic_date+"Z";
						var licitations_date=new Date(lic_date);
						var now = new Date();
						if (now.getTime()<licitations_date.getTime()){
							fill_changing_promo(puid);						
						}else{
							alert("time for changing promo is over or you have wrong input in field");
							fill_changing_promo(puid);
						}
					};
					
					$scope.choose_buyer = function(bid, ending_date, puid){
						var lic_date = (ending_date).replace(' ','T');
						lic_date=lic_date+"Z";
						var licitations_date=new Date(lic_date);
						var now = new Date();
						if (now.getTime()<licitations_date.getTime()){
							$http.get('/reg_user/choose_winner/'+bid).success(function(){
								fill_changing_promo(puid);
								fill_my_posted_promos_used();					
								alert('you\'ve successfuly chosen a winner');	
								mailWinnerAndLosers(bid);
							}).error(function(){
								alert("couldn't choose a buyer");
							});
						}else{
							alert("time for choosing winner is over");
							fill_changing_promo(puid);
						}
					};
					
					mailWinnerAndLosers=function(bid){
						$http.get('/reg_user/mail_winner_and_losers/'+bid).success(function(){	
						}).error(function(){
							alert("couldn't mail about win and loss");
						});
					};
				}
			
			}
		]
);