angular.module('Application').controller(
		'FanZoneTabController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
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
				
				$scope.currentUser=JSON.parse(localStorage.getItem("currentUser"));
				
				
				//dodatne funkcije
				fill_unreserved_promos_official=function(){
					$http.get('http://localhost:8181/reg_user/list_unreserved_po').success(function(data, status){
						$scope.unreservedPromosOfficial=data;
					}).error(function(){
						alert("couldn't list unreserved official promos");
					});
				};
				
				fill_my_reserved_promos_official=function(){
					$http.get('http://localhost:8181/reg_user/list_reserved_po/'+$scope.currentUser.email+'/nesto').success(function(data, status){
						$scope.myReservedPromosOfficial=data;
					}).error(function(){
						alert("couldn't list my reserved official promos");
					});
				};
				
				fill_others_promos_used=function(){
					$http.get('http://localhost:8181/reg_user/list_others_approved_pu/'+$scope.currentUser.email+'/nesto').success(function(data, status){
						$scope.othersPromosUsed=data;
					}).error(function(){
						alert("couldn't list others used promos");
					});
				};
				
				
				fill_my_bidded_promos_used=function(){
					$http.get('http://localhost:8181/reg_user/list_my_bids_pu/'+$scope.currentUser.email+'/nesto').success(function(data, status){
						$scope.myBiddedPromosUsed=data;
					}).error(function(){
						alert("couldn't list my bidded promos");
					});
				};
				
				fill_my_posted_promos_used=function(){
					$http.get('http://localhost:8181/reg_user/list_my_posted_pu/'+$scope.currentUser.email+'/nesto').success(function(data, status){
						$scope.myPostedPromosUsed=data;
					}).error(function(){
						alert("couldn't list my posted promos");
					});
				};
				
				fill_my_won_promos_used=function(){
					$http.get('http://localhost:8181/reg_user/list_my_won_pu/'+$scope.currentUser.email+'/nesto').success(function(data, status){
						$scope.myWonPromosUsed=data;
					}).error(function(){
						alert("couldn't list my won promos");
					});
				};
				
				
				
				fill_licitation=function(pu_id){
					$http.get('http://localhost:8181/reg_user/get_pu/'+pu_id).success(function(data, status){
						$scope.licitation=data;
					}).error(function(){
						alert("couldn't get promo licitation");
					});
				}
				
				fill_bids_in_licitation=function(pu_id){
					$http.get('http://localhost:8181/reg_user/list_bids/'+pu_id).success(function(data, status){
						$scope.bids_in_licitation=data;
					}).error(function(){
						alert("couldn't list bids in promo used");
					});
				}
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
				
				
				
				$scope.add_promo_used = function(){
					//proveravam podatke - name, description, image, date, time
					
					var date = $scope.puEndingDate; //da li ovde treba .value
					
					if ($scope.puName && date){
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
						
						
						$http.get('http://localhost:8181/reg_user/add_pu/'+$scope.currentUser.email+'/'+$scope.puName+'/'+puDescription+'/'+puImage+'/'+date).success(function(){
							alert('successfuly added a product');
							$scope.puName="";
							$scope.puDescription="";
							$scope.puImage="";
						}).error(function(){
							alert("couldn't create used product");
						});
					}else{
						alert("invalid data in fields");
					}	
				};
				
				$scope.reserve_po=function(id){
					$http.get('http://localhost:8181/reg_user/reserve_po/'+$scope.currentUser.email+'/'+id).success(function(){
						fill_unreserved_promos_official();
						fill_my_reserved_promos_official();
						alert('successfuly reserved a product');				
					}).error(function(){
						alert("couldn't create used product");
					});
				};
				
				
				$scope.unreserve_po=function(id){
					$http.get('http://localhost:8181/reg_user/unreserve_po/'+id).success(function(){
						fill_my_reserved_promos_official();
						fill_unreserved_promos_official();
						alert('successfuly unreserved a product');				
					}).error(function(){
						alert("couldn't create used product");
					});
				};
				
				
				$scope.see_pu=function(id){
					fill_licitation(id);
					var licitations_date=new Date((($scope.licitation.date).replace(' ','T')).concate("Z"));
					var now=new Date();
					if ($scope.licitation.activity="bought" || now<licitations_date){ //ne moze da biduje jer vec postoji buyer ili je proslo vreme
						document.getElementById("adding_bid").style.display="none";
					}
					$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":false, "div6":false, "div7":false, "div8":true, "div9":false};
				}
				
				
			}
		]
);