angular.module('Application').controller(
		'ReserveTicketController',
		[
			'$rootScope',
			'$scope',
			'$window',
			'$location', 
			'$http',
			function($rootScope, $scope, $window, $location, $http) {
  				$( function() {
    				$( "#datepicker" ).datepicker();
				} );
				$scope.projectionDate=false;
				$scope.timeAud=false;
				$scope.showSeatsPreview=false;
				$scope.searchCinemas=function(){
					var op = document.getElementById("cinemaCombo").getElementsByTagName("option");
					for(var i =0;i<op.length;i++){
						if(op[i].value.toLowerCase().includes($scope.searchCinemaText.toLowerCase())){
							op[i].selected=true;
							break;
						}
					};
				};
				$scope.projectionAndDate=function(){
					$scope.projectionDate=true;
					var selCinema = document.getElementById("cinemaCombo").selectedIndex;
					$scope.cinemaSelected = $rootScope.cinemasShow[selCinema];
					$http.get('http://localhost:8181/reguser/movies/'+$scope.cinemaSelected.id).success(function(data,status){
						$scope.moviesShow=data;
					});
				};
				$scope.timeAndAud=function(){
					$scope.timeAud=true;
					$scope.chosenDate=$("#datepicker").datepicker().val();
					$scope.chosenMovie = $scope.moviesShow[document.getElementById("movieCombo").selectedIndex];
					$scope.timeShow={};
					$scope.audShow={};
					var num=0;
					for(var x in $scope.chosenMovie.projections){
						if($scope.chosenMovie.projections[x].date==$scope.chosenDate){
							$http.get('http://localhost:8181/reguser/auditorium/'+$scope.chosenMovie.projections[x].id).success(function(data,status){
								$scope.audShow[num]=data;
								$scope.timeShow[num]=$scope.chosenMovie.projections[x].time;
								num=num+1;
							});
						}
					};
				};
				$scope.seatsView=function(){
					$scope.showSeatsPreview=true;
				};
				$scope.audChanged=function(ac){
					$scope.tc=$scope.timeShow[document.getElementById("audCombo").selectedIndex];
				};
				$scope.timeChanged=function(tc){
					$scope.ac=$scope.audShow[document.getElementById("timeCombo").selectedIndex];
				};		
				
				$(document).ready(function() {
					var ho = [  
							'aaaaaaaaaa',
							'aaaaaaaaaa',
							'__________',
							'aaaaaaaa__',
							'aaaaaaaaaa',
							'aaaaaaaaaa',
							'aaaaaaaaaa',
							'aaaaaaaaaa',
							'aaaaaaaaaa',
							'__aaaaaa__'
						];
					var $cart = $('#selected-seats');
					var sc = $('#seat-map').seatCharts({
						map: ho,
						naming : {
							top : false,
							getLabel : function (character, row, column) {
								return column;
							}
						},
						legend : { //Definition legend
							node : $('#legend'),
							items : [
								[ 'a', 'available',   'Available' ],
								[ 'a', 'unavailable', 'Sold'],
								[ 'a', 'selected', 'Selected']
							]					
						},
						click: function () { //Click event
							if (this.status() == 'available') { //optional seat
								$('<li>Row'+(this.settings.row+1)+' Seat'+this.settings.label+'</li>')
									.attr('id', 'cart-item-'+this.settings.id)
									.data('seatId', this.settings.id)
									.appendTo($cart);
											
								return 'selected';
							} else if (this.status() == 'selected') { //Checked

										
									//Delete reservation
									$('#cart-item-'+this.settings.id).remove();
									//optional
									return 'available';
							} else if (this.status() == 'unavailable') { //sold
								return 'unavailable';
							} else {
								return this.style();
							}
						}
					});
						
				});
	

			}
		]
);