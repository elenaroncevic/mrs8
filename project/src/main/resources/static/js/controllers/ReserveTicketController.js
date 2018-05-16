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
					$scope.chosenDate=$("#datepicker").datepicker().val();
					$scope.chosenMovie = $scope.moviesShow[document.getElementById("movieCombo").selectedIndex];
					$scope.projShow={};
					$scope.audShow={};
					var num=0;
					for(var x in $scope.chosenMovie.projections){
						alert($scope.chosenDate);
						if($scope.chosenMovie.projections[x].date==$scope.chosenDate){
							$http.get('http://localhost:8181/reguser/auditorium/'+$scope.chosenMovie.projections[x].id).success(function(data,status){
								$scope.audShow[num]=data;
								$scope.projShow[num]=$scope.chosenMovie.projections[x];
								num=num+1;
							});
						}
					};
					$scope.timeAud=true;
				};
				$scope.chosenProjection= null;
				$scope.audChanged=function(ac){
					$scope.chosenProjection = $scope.projShow[document.getElementById("audCombo").selectedIndex];
					$scope.tc=$scope.chosenProjection.time;
				};
				$scope.projChanged=function(tc){
					$scope.chosenProjection = $scope.projShow[document.getElementById("projCombo").selectedIndex];
					$scope.ac="Room "+$scope.chosenProjection.id;
				};		
				$scope.passed=false;
				$scope.loadSeats=function(){
					$scope.chosenProjection = $scope.projShow[document.getElementById("projCombo").selectedIndex];
					if($scope.chosenProjection==null){
						alert('Projection not chosen!');
					}else{
						$scope.showSeatsPreview=true;
						$http.get('http://localhost:8181/reguser/seats/'+$scope.chosenProjection.id).success(function(data,status){
							$scope.seatovi=data[0];
							$scope.takenSeatovi=data[1];
							$scope.passed=true;
							arrangement();
						});
					}
				};
				arrangement=function(){
					var allSeats = [];
					var takenSeats=[];
					for(var i=0;i<3;i++){
						allSeats.push('');
					};
					var numrow=0;
					for(var x in $scope.seatovi){
						numrow=parseInt($scope.seatovi[x].number[0]);
						allSeats[numrow-1]=allSeats[numrow-1]+'a';
						alert(allSeats[numrow-1]);
					};
					$scope.h=allSeats;
					var numcol;
					for(var x in $scope.takenSeatovi){
						numrow=$scope.takenSeatovi[x].number[0];
						numcol = $scope.takenSeatovi[x].number[1].charCodeAt(0)-64;
						takenSeats.push(numrow+"_"+numcol);		
					};
					
					var $cart = $('#selected-seats');
					var sc = $('#seat-map').seatCharts({
						map: allSeats,
						naming : {
							top : false,
							getLabel : function (character, row, column) {
								return column;
							}
						},
						legend :{
							node : $('#legend'),
							items : [
								[ 'a', 'available',   'Available' ],
								[ 'a', 'unavailable', 'Sold'],
								[ 'a', 'selected', 'Selected']
							]					
						},
						click: function () {
							if (this.status() == 'available') { 
								$('<li>Row'+(this.settings.row+1)+' Seat'+this.settings.label+'</li>')
									.attr('id', 'cart-item-'+this.settings.id)
									.data('seatId', this.settings.id)
									.appendTo($cart);
								return 'selected';
							} 
							else if (this.status() == 'selected') {
									$('#cart-item-'+this.settings.id).remove();
									return 'available';
							} 
							else if (this.status() == 'unavailable') {
								return 'unavailable';
							}
							else {
								return this.style();
							}
						}
					});
					var prob = ["1_2", "1_3"];
					sc.get(takenSeats).status('unavailable');
					
				};
	

			}
		]
);