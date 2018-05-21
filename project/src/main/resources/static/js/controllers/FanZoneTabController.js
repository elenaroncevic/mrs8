angular.module('Application').controller(
		'FanZoneTabController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
				$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":false, "div6":false, "div7":false};
				
				$scope.show_div1=function(){
					$scope.show_div = {"div1":true, "div2":false, "div3":false, "div4":false, "div5":false, "div6":false, "div7":false};
				};
				$scope.show_div2=function(){
					$scope.show_div = {"div1":false, "div2":true, "div3":false, "div4":false, "div5":false, "div6":false, "div7":false};
				};
				$scope.show_div3=function(){
					$scope.show_div = {"div1":false, "div2":false, "div3":true, "div4":false, "div5":false, "div6":false, "div7":false};
				};
				$scope.show_div4=function(){
					$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":true, "div5":false, "div6":false, "div7":false};
				};
				$scope.show_div5=function(){
					$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":true, "div6":false, "div7":false};
				};
				$scope.show_div6=function(){
					$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":false, "div6":true, "div7":false};
				};
				$scope.show_div7=function(){
					$scope.show_div = {"div1":false, "div2":false, "div3":false, "div4":false, "div5":false, "div6":false, "div7":true};
				};
				
				
				//dodatne funkcije
				fill_unreserved_promos_official=function(){
					$http.get('http://localhost:8181/reg_user/list_unreserved_po').success(function(data, status){
						$scope.unreservedPromosOfficial=data;
					}).error(function(){
						alert("couldn't list unreserved official promos");
					});
				};
				////
			
				
				$scope.currentUser=JSON.parse(localStorage.getItem("currentUser"));
				
				//ucitavanje podataka za div2
				$scope.unreservedPromosOfficial={};
				fill_unreserved_promos_official();
				
				$scope.add_promo_used = function(){
					//proveravam podatke - name, description, image, date, time
					
					var date = $scope.puEndingDate;
					var time=$scope.puEndingTime;
					
					if ($scope.puName && date && time){
						var puDescription = $scope.puDescription;					
						if (!puDescription){
							puDescription="No description";
						}
						var puImage = $scope.puImage;
						if (!puImage){
							puImage="https//blog.stylingandroid.com/wp-content/themes/lontano-pro/images/no-image-slide.png";
						}
						puImage = puImage.replace(/\//g, "+");
						puImage = puImage.replace(/\?/g, "*");
						
						
						$http.get('http://localhost:8181/reg_user/add_pu/'+$scope.puName+'/'+puDescription+'/'+puImage+'/'+date+'/'+time+'/'+$scope.currentUser.email).success(function(){
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
					$http.get('http://localhost:8181/reg_user/reserve_po/'+id+'/'+$scope.currentUser.email).success(function(){
						fill_unreserved_promos_official();
						alert('successfuly reserved a product');				
					}).error(function(){
						alert("couldn't create used product");
					});
				};
				
				
				
				
				
			}
		]
);