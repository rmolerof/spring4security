var coreModule = angular.module('login', []);

coreModule.controller('home', function($http) {
	var self = this;
	$http.get('resource/').then(function(response) {
		self.greeting = response.data;
	})
})

coreModule.controller('mainController', function($scope, $http) {
	$scope.user = {};
	$scope.error = false;

	$scope.submitRegister = function() {
		console.log("Register-> name: " + $scope.user.name + " email: " + $scope.user.email + " password: ********");
		
	};
	
	$scope.submitCheckin = function() {
		console.log("Check-in-> email: " + $scope.user.email + " password: ********");
		
	};
	
	$scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    };

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    };
})
