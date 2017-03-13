var myapp = angular.module('epi', [ 'ui.bootstrap',
		'ui.bootstrap.datetimepicker', 'ngRoute', 'ngAnimate', 'toastr',
		'puigcerber.countryPicker' ]);
myapp.constant('domain', 'http://localhost:8080/epi').constant('api', '/')
		.service('urls', function(domain, api) {
			this.apiUrl = domain + api;
			this.domaineUrl = domain;
		});
myapp.config([ '$routeProvider', '$locationProvider', '$compileProvider',
		function($routeProvider, $locationProvider, $compileProvider) {
			$compileProvider.debugInfoEnabled(false);
			$routeProvider.when('/home', {
				templateUrl : 'partial/client.html',
				controller : "clientCtrl"
			}).otherwise({
				redirectTo : '/home'
			});
		} ]);
myapp.controller('appCtrl', [
		'$scope',
		'$http',
		'urls',
		function($scope, $http, urls) {
			$scope.user = {};
			$scope.init = function() {
				$("#sub-menu").hide();
				$http.get(urls.apiUrl + 'client/getClientLoged').success(
						function(data, status) {
							$scope.user = data;
							console.log(data);
						}).error(function(data, status) {
				});
			}
		} ]);