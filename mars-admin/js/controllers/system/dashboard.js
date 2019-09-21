'use strict';
/* Controllers */
var app = angular.module('app',['service.system','service.utils','service.user']);
app.controller('DashboardController',function($system,$scope,$utils,$user) {
    $scope.registerX = new Array();
    $scope.registerY = new Array();
    $scope.amountX = new Array();
    $scope.amountY = new Array();


});