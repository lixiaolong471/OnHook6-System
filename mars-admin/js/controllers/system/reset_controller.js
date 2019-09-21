/**
 * Created by lixl on 2017/7/24.
 */
angular.module('app',['service.system','service.utils','toaster']).controller('ResetController', function ($system,$scope,$rootScope,$utils,$location,$state,toaster) {
    $scope.refresh = function () {
        var url = '/sys/system/login/captcha.do';
        $("#codImg").attr("src",url+"?v="+ new Date().getTime());
    }
    $scope.reset = function(){
        if(!$scope.user||!$scope.user.loginName){
            $scope.errorMessage = "账号不能为空";
            $("#account").focus();
            return;
        }
        if(!$scope.user.oldPasswd){
            $scope.errorMessage = "原始密码不能为空";
            $("#pwd").focus();
            return;
        }
        if(!$scope.user.newPasswd){
            $scope.errorMessage = "新密码不能为空";
            $("#newPwd").focus();
            return;
        }
        if(!isNaN($scope.user.newPasswd)){
            $scope.errorMessage = "新密码不能为纯数字";
            $("#newPwd").focus();
            return;
        }
        if($scope.user.oldPasswd == $scope.user.newPasswd){
            $scope.errorMessage = "新密码与原始密码相同";
            $("#newPwd").focus();
            return;
        }
        if(!$scope.user.authCode){
            $scope.errorMessage = "验证码不能为空";
            $("#imgCode").focus();
            return;
        }
       // $scope.user.oldPasswd = $.md5($scope.user.oldPasswd );
        $scope.user.newPasswd = $.md5($scope.user.newPasswd );

        $system.resetPwd($scope.user,function (response) {
            if(response.code < 1){
                $scope.refresh();
                $scope.errorMessage = response.message;
            }else if(response.code == 1){
               alert("重置密码成功,前往登录！")
               $state.go("login",{},{reload:true});
            }
        });
    }

    $scope.cancel = function () {
        $state.go("login");
    }
    $scope.enter = function (e) {
        var keycode = window.event?e.keyCode:e.which;
        if(keycode==13){
            $scope.login();
        }
    }
    $scope.refresh();
});