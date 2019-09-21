'use strict';

/* Controllers */

angular.module('app')
  .controller('AppCtrl', ['$scope','$uibModal','$localStorage', '$window','$system',"$location","$log","$rootScope",
    function($scope,$uibModal,$localStorage,$window,$system,$location,$log,$rootScope) {
      $system.getLoginUser($rootScope,function (res) {
         if(res.code == 1){
            // console.log("验证登录成功！");
         }else{
            // console.log("验证登录失败！");
         }
      });
      // add 'ie' classes to html
      var isIE = !!navigator.userAgent.match(/MSIE/i);
      isIE && angular.element($window.document.body).addClass('ie');
      isSmartDevice( $window ) && angular.element($window.document.body).addClass('smart');
      // config
      $scope.app = {
        name: '托管辅助程序',
        fullName: '托管辅助程序管理平台',
        version: '1.0.0',
        // for chart colors
        color: {
          primary: '#7266ba',
          info:    '#23b7e5',
          success: '#27c24c',
          warning: '#fad733',
          danger:  '#f05050',
          light:   '#e8eff0',
          dark:    '#3a3f51',
          black:   '#1c2b36'
        },
        settings: {
          themeID: 1,
          navbarHeaderColor: 'bg-black',
          navbarCollapseColor: 'bg-white-only',
          asideColor: 'bg-black',
          headerFixed: true,
          asideFixed: false,
          asideFolded: false,
          asideDock: false,
          container: false
        }
      }
        // save settings to local storage
        if ( angular.isDefined($localStorage.settings) ) {
            $scope.app.settings = $localStorage.settings;
        } else {
            $localStorage.settings = $scope.app.settings;
        }
        $scope.$watch('app.settings', function(){
            if( $scope.app.settings.asideDock  &&  $scope.app.settings.asideFixed ){
                // aside dock and fixed must set the header fixed.
                $scope.app.settings.headerFixed = true;
            }
            // save to local storage
            $localStorage.settings = $scope.app.settings;
        }, true);
      function isSmartDevice( $window ){
          // Adapted from http://www.detectmobilebrowsers.com
          var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
          // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
          return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
      }
      // $system.loadCodeList({},function (response) {
      //     if(response.code == 1){
      //         $localStorage.systemCodeList = response.data;
      //     }
      // })

      
      $scope.logOut = function(){
          $system.logOut(function () {
              $location.path("/login");
          });
      }
      $scope.settings = function(){
          var modalInstance = $uibModal.open({
              ariaLabelledBy: 'modal-title',
              ariaDescribedBy: 'modal-body',
              templateUrl: 'tpl/system/user_settings.html',
              controller: 'SettingsDialogController',
              controllerAs: '$ctrl',
              backdrop:'static',
              size: 'lg'
          });

          modalInstance.result.then(function (selectedItem) {
              $ctrl.selected = selectedItem;
          }, function () {
              $log.info('Modal dismissed at: ' + new Date());
          });
      }

      $scope.pwdUpdate = function(){
          var modalInstance = $uibModal.open({
              ariaLabelledBy: 'modal-title',
              ariaDescribedBy: 'modal-body',
              templateUrl: 'tpl/system/pwd_update.html',
              controller: 'PwdUpdateDialogController',
              controllerAs: '$ctrl',
              backdrop:'static',
              size: 'lg'
          });
      }
      $system.loadMenus($rootScope);
  }]);

app.controller('SettingsDialogController', function ($uibModalInstance,$system) {
    var $ctrl = this;

    $system.getLoginUser($ctrl);
    $ctrl.save = function () {
        $system.userSetting($ctrl.loginUser,function (result) {
            if(result.code == 1){
                $uibModalInstance.close();
            }else{
                alert(result.message);
            }
        });
    };
    $ctrl.cancel = function () {
        $uibModalInstance.close();
    };
});
app.controller('PwdUpdateDialogController', function ($uibModalInstance,$system) {
    var $ctrl = this;

    $ctrl.save = function () {
        $system.userPwd($ctrl.loginUser,function (result) {
            if(result.code == 1){
                $uibModalInstance.close();
            }else{
                alert(result.message);
            }
        });
    };
    $ctrl.cancel = function () {
        $uibModalInstance.close();
    };
});